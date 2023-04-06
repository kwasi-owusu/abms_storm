package com.woleapp.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netpluspay.netpossdk.NetPosSdk;
import com.pos.sdk.printer.POIPrinterManage;
import com.pos.sdk.printer.models.BitmapPrintLine;
import com.pos.sdk.printer.models.PrintLine;
import com.pos.sdk.printer.models.TextPrintLine;
import com.woleapp.R;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.databinding.LayoutCashInBinding;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.BankList;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.network.StormAPIClient;
import com.woleapp.network.soap.request.FundsTransferRequestModel;
import com.woleapp.network.soap.request.ProcessModel;
import com.woleapp.network.soap.request.RequestBody;
import com.woleapp.network.soap.request.RequestEnvelope;
import com.woleapp.network.soap.request.TransactionModel;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.ui.activity.SignatureActivity;
import com.woleapp.ui.activity.SuccessActivity;
import com.woleapp.util.Constants;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import static com.woleapp.ui.activity.PaymentProgressActivity.KEY_AMOUNT;

import com.netpluspay.netpossdk.emv.CardReadResult;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class CashInFragment extends BaseFragment implements View.OnClickListener, Constants {
    Context context;
    private LayoutCashInBinding binding;

    AgencyUser user;
    Drawable customErrorDrawable;

    Signature mSignature;
    View signature_view;
    Bitmap bitmap;
    boolean hasSigned = false;

    LinearLayout mContent;
    File file;
    int result0, transaction_type = 0;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String mode = "", selected_bank_code = "";
    double amount = 0.0;
    String name;
    Utilities utilities;
    String[] IDs;
    //    String[] bankCodes;
    ProgressDialog mProgressDialog;
    private String ID = "";

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Gson gson = new Gson();
    CardReadResult readResult;
    AlertDialog alertDialog;
    List<BankList> banks = new ArrayList<>();
    List<String> bankNames = new ArrayList<>();
    List<String> bankCodes = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", CashInFragment.class.getSimpleName() + "--");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause", CashInFragment.class.getSimpleName() + "--");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        user = SharedPrefManager.getAgencyUser();
        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
//        bankCodes = context.getResources().getStringArray(R.array.bank_codes);
        IDs = context.getResources().getStringArray(R.array.idcard);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //populate();
        binding.etName.setVisibility(View.GONE);
        Bundle b1 = getArguments();
        if (b1 != null) {
            if (b1.containsKey("transaction_type")) {
                transaction_type = b1.getInt("transaction_type", 0);
                mode = b1.getString("mode", "");
                if (mode.equalsIgnoreCase("cash")) {
                    binding.etFee.setVisibility(View.GONE);
                    binding.tvConvinienceFee.setVisibility(View.GONE);
                }
                amount = b1.getDouble(KEY_AMOUNT);
                //name = b1.getString("customer");
                //binding.etName.setText(name);


                binding.tvAmount.setText(utilities.getFormattedAmount(amount));
                if (transaction_type == Constants.TRANSACTION_CASH_IN) {
                    binding.tvTitle.setText("CASH-IN / BANK TRANSFER");
                } else {
                    binding.tvTitle.setText("CASH-OUT");
                }
            }
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("agency_id", 1);
        jsonObject.addProperty("branch_id", 1);

        getBankListNew();

        setListeners();

        setIdSpinner();

        prepareCanvas();

        if (checkPermissions()) {
            String filePath = getFilePath();
            file = new File(filePath);
        } else {
            requestPermission();
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_cash_in, container, false);
        return binding.getRoot();
    }

    public void setListeners() {
        binding.tvRetake.setOnClickListener(this);
        binding.etFee.setKeyListener(null);
        binding.btnContinue.setOnClickListener(this);
//        binding.etAccountNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus) {
//                    return;
//                }
//
//                if(selected_bank_code == null || selected_bank_code.isEmpty()) {
//                    return;
//                }
//                //return if bank account not selected
//                String acc_no = binding.etAccountNo.getText().toString().trim();
//                if(acc_no == null || acc_no.isEmpty() || acc_no.length() != 10) {
//                    return;
//                }
//
//                //call get account name
//                getAccountName();
//
//            }
//        });

    }
    public void getBankListNew(){
        showProgressBar();
        Disposable subscribe = AgencyBankingAPIClient
                .getBankList(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res ->{
            if(res.code() != 200) {
                Toast.makeText(context, "Failed to get banks", Toast.LENGTH_SHORT).show();
                dismissProgressBar();
                return;
            }
                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("RES_CODE", "Banks: " + response);
                    if(response.optString("success").equals("false")){
                        Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray jsonArray = response.optJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String partnerID = item.optString("id");
                        String partnerName = item.optString("name");
                        String shortName = item.optString("shortName");
                       // String partnerCode = item.optString("partner_code");

                        if(item.optString("type").equals("bank")) {
                            String name = item.optString("name");
                            bankNames.add(name);
                            Log.e("Bank Name", "Bank Name " + name);
                            String code = item.optString("id");
                            bankCodes.add(code);
                            Log.e("Bank Code", "Bank Code " + code);
                        }

                       // banks.add(new BankList(partnerID, partnerName, shortName));

                        setSpinner();
                        dismissProgressBar();
                    }
        });
    }

    public void getBankList (JsonObject payload) {
        Disposable subscribe = AgencyBankingAPIClient
                .bankList(payload, context)
                .subscribe(res -> {
                    if(res.code() != 200) {
                        Toast.makeText(context, "Failed to get banks", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("RES_CODE", "Banks: " + response);
                    if(response.optInt("code") != 112) {
                        Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //pass banks to drop down view element
                    JSONArray jsonArray = response.optJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String partnerID = item.optString("partner_ID");
                        String partnerName = item.optString("partner_name");
                        String shortName = item.optString("short_name");
                        String partnerCode = item.optString("partner_code");

                        String name = item.optString("partner_name");
                        bankNames.add(name);
                        String code = item.optString("partner_code");
                        bankCodes.add(code);

                        banks.add(new BankList(partnerID, partnerName, shortName, partnerCode));

                        setSpinner();
                    }

                }, err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                });
    }

    public void setIdSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.idtype, R.layout.spinner_view_id_type);
        adapter.setDropDownViewResource(R.layout.item_drop_down_id);
        binding.spnIdType.setPrompt(context.getResources().getString(R.string.hint_choose_id_type));

        binding.spnIdType.setAdapter(
               // adapter
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_id_type,
                        getActivity()
                )
        );
//        String selection = "National ID";
//        int spinnerPosition = adapter.getPosition(selection);
//        binding.spnIdType.setSelection(spinnerPosition);

        binding.spnIdType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View parent, int position, long l) {
                if (position > 0) {
                    //binding.spnIdType.setEnabled(false);
                    ID = IDs[position - 1];
                    Log.e("selected_id", ID + "---");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

//    public void setSpinner() {
//
//        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), R.layout.spinner_view_bank, bankNames);
//        adapter.setDropDownViewResource(R.layout.item_drop_down_bank);
//        binding.spnBank.setPrompt(context.getResources().getString(R.string.hint_select_bank_caps));
//
//        binding.spnBank.setAdapter(
//                new NothingSelectedSpinnerAdapter(
//                        adapter,
//                        R.layout.spinner_view_bank,
//                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
//                        getActivity()));
//        Log.e("works","works");
//
//        binding.spnBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//
//                if (bankCodes != null) {
//
//                    if (position > 0) {
//                        selected_bank_code = bankCodes.get(position - 1);
//                    }
//                    Log.e("selected_bank_code","Selected bank code");
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                Log.e("selection shows", "Nothing Selected");
//            }
//        });

    public void setSpinner() {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), R.layout.spinner_view_bank, bankNames);
        adapter.setDropDownViewResource(R.layout.item_drop_down_bank);
        binding.spnBank.setPrompt(context.getResources().getString(R.string.hint_choose_bank_caps));

        binding.spnBank.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_bank,
                        getActivity()
                )
        );

        binding.spnBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View parent, int position, long l) {
                if (position > 0) {
                    selected_bank_code = bankCodes.get(position -1);
                    Log.e("selected_bank_code", selected_bank_code + "---");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        Log.e("selection skips", "It just skips");

//        binding.spnBank.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus)
//                    return;
//
//                //return if bank code not selected
//                if(selected_bank_code == null || selected_bank_code.isEmpty()) {
//                    return;
//                }
//                //return if bank account not selected
//                String acc_no = binding.etAccountNo.getText().toString().trim();
//                if(acc_no == null || acc_no.isEmpty() || acc_no.length() != 10) {
//                    return;
//                }
//
//                //call get account name
//                getAccountName();
//
//            }
//        });
    }

    private void processTransactions(String name, String account, String idCard, String idNo, String depName, double amt, String selected_bank_code) {
        //payload
        String officer_ID = "13";
        String branch_code = "1";
        String inst_ID = "1";
        String branch_key = SharedPrefManager.getBranchDetails().getBranchKey();
        String agency_key = SharedPrefManager.getAgencyDetails().getAgentKey();
        String trans_cat = "1";
        String trans_type = "2";
        double total_amnt = amt;
        String narration = "Test Narration";
        String customer_name = name;
        String account_number = account;
        String depositor_payee = depName;
        String ID_type = idCard;
        String ID_number = idNo;
        String Bank_code = selected_bank_code;

        int branchID = SharedPrefManager.getAgencyUser().getUserBranch();
        int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();
        String branchName = SharedPrefManager.getBranchDetails().getBranchName();
        String a_name = SharedPrefManager.getAgencyDetails().getAgencyName();

        JsonObject agency = new JsonObject();
        agency.addProperty("agent_id", agencyID);
        agency.addProperty("branch_id", branchID);
        agency.addProperty("branch_key", branch_key);
        agency.addProperty("agent_key", agency_key);

        JsonObject transaction = new JsonObject();
        transaction.addProperty("trans_cat", Integer.parseInt(trans_cat));
        transaction.addProperty("trans_type", Integer.parseInt(trans_type));
        transaction.addProperty("amount", Double.parseDouble(String.valueOf(total_amnt)));

        transaction.addProperty("narration", narration);
        transaction.addProperty("id_type", ID_type);
        transaction.addProperty("id_number", ID_number);
        transaction.addProperty("depositor_payee", depositor_payee);
        transaction.addProperty("customer_name", customer_name);
        transaction.addProperty("product_ID", Bank_code);

        JsonObject customer = new JsonObject();
        customer.addProperty("account_name", customer_name);
        customer.addProperty("account_number", account_number);

        JsonObject teller = new JsonObject();
        teller.addProperty("teller_id", Integer.parseInt(officer_ID));

        JsonObject meta = new JsonObject();
        meta.addProperty("mobile_transaction_reference", "521wd542d86");

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("Agent_Details", agency);
        jsonObject.add("transaction", transaction);
        jsonObject.add("account_details", customer);
        jsonObject.add("teller_details", teller);
        jsonObject.add("meta_data", meta);
        Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + jsonObject);
        Disposable subscribe = AgencyBankingAPIClient
                .sendTransaction(jsonObject, context)
                .subscribe(res -> {
                    if (res.code() != 200) {
                        Toast.makeText(context, "Failed to process transaction", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("TRANSACTION_RESPONSE", "transaction response: " + response);
                    if (response.optInt("code") != 112) {
                        Toast.makeText(context, response.optString("msg"), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String ref = response.optString("ref");
                    printReceipt(customer_name, depositor_payee, ID_type, ID_number, a_name, branchName, ref);
                    //process transaction
                }, err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                });

    }
    private void printReceipt(String CustomerName, String depositor_payee, String ID, String IDnum, String a_name, String branchName, String ref) {
        POIPrinterManage printerManage = NetPosSdk.getPrinterManager(context);
        printerManage.setLineSpace(2);
        printerManage.setPrintGray(3000);
        printerManage.cleanCache();

        Bitmap bitmap =
                BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.bsyslogo);
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 100, false);

        BitmapPrintLine bitmapPrintLine = new BitmapPrintLine();
        bitmapPrintLine.setType(PrintLine.BITMAP);
        bitmapPrintLine.setBitmap(bitmap);
        bitmapPrintLine.setPosition(PrintLine.CENTER);

        TextPrintLine textPrintLine = new TextPrintLine();
        textPrintLine.setType(PrintLine.TEXT);
        textPrintLine.setContent("Agency Banking");
        textPrintLine.setBold(true);
        textPrintLine.setItalic(false);
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(TextPrintLine.FONT_LARGE);

        printerManage.addPrintLine(bitmapPrintLine);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("----------------------------------------------------" +
                "--------------------------------------------");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(utilities.getTodaysDate() + "    " + utilities.getTodaysTime());
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(" ");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Ref No.: " + ref);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Transaction Type: CASH-IN");
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);


        textPrintLine.setContent("Amount: " + utilities.getFormattedAmount(amount));
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("ID Type: " + ID);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("ID Number: " + IDnum);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

//        textPrintLine.setContent("Account Name: " + CustomerName);
//        textPrintLine.setPosition(PrintLine.LEFT);
//        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
//        textPrintLine.setBold(false);
//        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Depositor Name: " + depositor_payee);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Agent Name: " + a_name);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Branch Name: " + branchName);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(" ");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Transaction Successful");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("--------------------------------------------------------------");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("THANK YOU");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Powered by Bsystems Limited");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("0302-254-340");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        printerManage.beginPrint(new POIPrinterManage.IPrinterListener() {
            @Override
            public void onError(int i, String s) {
                Timber.e("Printer error with code " + i + " and message" + s);
                Toast.makeText(context, "Printer Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                //Toast.makeText(context, "Printing job finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart() {
                Toast.makeText(context, "Printing job started", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getAccountName() {

        String appToken = SharedPrefManager.getAppToken();
        String acc_no = binding.etAccountNo.getText().toString().trim();

        showProgressBar();
        StormAPIClient.getAccountName(acc_no, selected_bank_code, context)
                .subscribe(res -> {

                    if(res.code() != 200) {
                        Toast.makeText(context, "Unable to retrieve bank account name at this time please try later",Toast.LENGTH_LONG).show();
                        dismissProgressBar();
                        return;
                    }

                    JSONObject payload =  new JSONObject(new Gson().toJson(res.body()));;
                    binding.etName.setText(payload.getString("accountName"));
                    dismissProgressBar();

                }, t -> {
                    Log.e(getTag(), "An unexpected error occurred",  t);
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();;
                    dismissProgressBar();
                });

    }

    private void onFocuOut() {

    }

    public boolean priceValidation(String price) {
        //^[1-9][0-9]{12,16}$ (old regular expression)
//		String regex = "^[+][0-9]{12,16}$";
        String regex = "[+-]?([0-9]*[.])?[0-9]+";//"[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)";
        //String regex = "^[+][0-9]{10,13}$";
        Pattern numberPattern = Pattern.compile(regex);
        boolean result = numberPattern.matcher(price).matches();
        Log.e("Result: ", result + "--");
        return result;
    }

    public void prepareCanvas() {

        //mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
        mContent = binding.linearCanvas;
        mSignature = new Signature(context, null);
        mSignature.setBackgroundColor(Color.WHITE);
        //mSignature.getParent().requestDisallowInterceptTouchEvent(true);

        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //binding.linearNext.setEnabled(false);
        hasSigned = false;
//        mClear = (Button) dialog.findViewById(R.id.clear);
//        mGetSign = (Button) dialog.findViewById(R.id.getsign);
//        mGetSign.setEnabled(false);
//        mCancel = (Button) dialog.findViewById(R.id.cancel);

//        LinearLayout temp = mContent;
//        temp.setBackgroundResource(0);
//        signature_view = temp;
//        signature_view.layout();

        signature_view = mContent; // Original

//        mClear.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.v("log_tag", "Panel Cleared");
//                mSignature.clear();
//                mGetSign.setEnabled(false);
//            }
//        });
//
//        mGetSign.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                Log.v("log_tag", "Panel Saved");
//                signature_view.setDrawingCacheEnabled(true);
//                //mSignature.save(signature_view, StoredPath);
//                Log.e("Path",file.getPath()+"");
//                mSignature.save(signature_view, file.getPath());
//
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
//                // Calling the same class
//                recreate();
//
//            }
//        });
//
//        mCancel.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.v("log_tag", "Panel Canceled");
//                dialog.dismiss();
//                // Calling the same class
//                recreate();
//            }
//        });
//        dialog.show();
    }

    public boolean checkPermissions() {
        try {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M) {
                result0 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                //result0 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
                //result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (result0 == 0)//result0 == 0 && result1 == 0
                {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    //boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    //boolean phoneStateAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    //boolean networkAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        String filePath = getFilePath();
                        file = new File(filePath);//getOutputMediafile(1);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSION_REQUEST_CODE);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.clear();

//        menu.findItem(R.id.action_edit).setVisible(true);
//        menu.findItem(R.id.action_search).setVisible(false);
        //menu.removeItem(R.id.action_search);//clear();

        /*if(showDeleteMenu)
        {
            menu.findItem(R.id.action_search).setVisible(false);
            menu.removeItem(R.id.action_search);//clear();
        }
        else
        {
            menu.clear();

//            menu.removeItem(R.id.action_search);//clear();
//            menu.removeItem(R.id.action_search);//clear();
//
//            menu.findItem(R.id.action_search).setVisible(false);
//
//            menu.findItem(R.id.action_search).setVisible(false);
        }*/

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            /*case R.id.action_edit:
                mDataBinding.btnUpdate.setVisibility(View.VISIBLE);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v == binding.btnContinue) {
            //Reading Card Here
//            readCard();
            if(binding.etName.getText() == null || binding.etName.getText().toString().isEmpty()) {

                //return if bank code not selected


//                String appToken = SharedPrefManager.getAppToken();

//                showProgressBar();
//                StormAPIClient.getAccountName(acc_no, selected_bank_code, context)
//                        .subscribe(res -> {
//
//                            if(res.code() != 200) {
//                                Toast.makeText(context, "Unable to retrieve bank account name at this time please try later",Toast.LENGTH_LONG).show();
//                                dismissProgressBar();
//                                return;
//                            }
//
//                            JSONObject payload =  new JSONObject(new Gson().toJson(res.body()));;
//                            binding.etName.setText(payload.getString("accountName"));
//                            dismissProgressBar();
//                            Toast.makeText(context, "Please confirm account beneficiary name",Toast.LENGTH_LONG).show();
//
//                        }, t -> {
//                            Log.e(getTag(), "An unexpected error occurred",  t);
//                            Toast.makeText(context, "An unexpected error occured", Toast.LENGTH_LONG).show();;
//                        });
//                return;
            }

            validateAndProceed();


        } else if (v == binding.linearCanvas) {
            //proceedToSignature();
            showSignaturePad();
        } else if (v == binding.tvRetake) {
            mSignature.clear();
            hasSigned = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Log.e("RequestCode: " + requestCode, "ResultCode " + resultCode);
    }

    public void proceedToSignature() {
        Intent i1 = new Intent(getActivity(), SignatureActivity.class);
        getActivity().startActivityForResult(i1, 1204);
    }

    public void hideKeyBoard() {
        try {
            if (context != null) {
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

                // check if no view has focus:
                View v = ((Activity) context).getCurrentFocus();
                if (v == null) return;

                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validateAndProceed() {

        //String amount = binding.etPrice.getText().toString().trim();
        String name = binding.etName.getText().toString().trim();
        String acc_no = binding.etAccountNo.getText().toString().trim();
        int selected_bank = binding.spnBank.getSelectedItemPosition();
        String idCard = ID;
        String idNum = binding.etIDNo.getText().toString().trim();
        String depositorName = binding.etdepositor.getText().toString().trim();
        double amnt = amount ;

        Log.e("selected_bank_pos: ", selected_bank + "---");

        //if (TextUtils.isEmpty(name)) {
          //  binding.etName.setError("Name is required", customErrorDrawable);
           // binding.etName.requestFocus();
        //}
        if(selected_bank_code == null || selected_bank_code.isEmpty()){
            Toast.makeText(context, "Please select bank",Toast.LENGTH_LONG).show();
        }
        else if(ID == null || ID.isEmpty()){
            Toast.makeText(context, "Please select ID",Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(idNum)) {
            binding.etIDNo.setError("ID number is required", customErrorDrawable);
            binding.etIDNo.requestFocus();
        }
        else if (TextUtils.isEmpty(acc_no)) {
            binding.etAccountNo.setError("Account number is required", customErrorDrawable);
            binding.etAccountNo.requestFocus();
        } else if (acc_no.length() < 10) {
            binding.etAccountNo.setError("Invalid Account number", customErrorDrawable);
            binding.etAccountNo.requestFocus();
        }
        else if (TextUtils.isEmpty(depositorName)) {
            binding.etName.setError("Depositor name is required", customErrorDrawable);
            binding.etName.requestFocus();
        } else if (!hasSigned) {
            showOrHideSignatureAlert(true);
        }
        //else if (Double.parseDouble(amount) < 0) {
            //binding.etAccountNo.setError("Invalid amount", customErrorDrawable);
            //binding.etAccountNo.requestFocus();
        //}
        else {
            //call transaction function here
            processTransactions(name, acc_no, idCard, idNum, depositorName, amnt, String.valueOf(selected_bank));
        }


//        else if (selected_bank <= 0) //<0
//        {
//
//            //TextView errorText = (binding.spnBank.getVie().findViewById(android.R.id.text1));
//            TextView errorText = binding.spnBank.getRootView().findViewById(android.R.id.text1);
//            errorText.setError("Select a Bank", customErrorDrawable);
//            errorText.setTextColor(Color.RED);//just to highlight that this is an error
//            errorText.setText("Select a Bank");//changes the selected item text to this
//
//        }
        //return false;
    }

    public String generateUUID() {

        Long tsLong = System.currentTimeMillis();///1000;//ETZ691572523694353//ETZ691572523588
        String transaction_ref_id = "ETZ" + user.getAgencyID() + tsLong.toString(); // This is your Transaction Ref id - Here we used as a timestamp -
//
        Log.e("TR Reference ID==>", "" + transaction_ref_id);

        return transaction_ref_id;
    }

    public FundsTransferRequestModel getRequestModel(String name, String account_no) {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setPin("kghxqwveJ3eSQJip/cmaMQ==");
        transactionModel.setBankCode(selected_bank_code);// "033" 063
        transactionModel.setAmount(amount);
        transactionModel.setDescription("Payment Fund Transfer");
        transactionModel.setDestination(account_no);//Constants.ACCOUNT_NUMBER_DESTINATION

        transactionModel.setReference(generateUUID());
        //transactionModel.setSenderName("Ram:"+Constants.ACCOUNT_NUMBER_SOURCE+":Laxman");

        String agent_name = TextUtils.isEmpty(user.getFirstName()) ? "Agent" : user.getFirstName() + user.getLastName();
        transactionModel.setSenderName(agent_name + ":" + Constants.ACCOUNT_NUMBER_SOURCE + ":" + name);
        transactionModel.setEndPoint("A");
        transactionModel.setCurrency("NGN");
        FundsTransferRequestModel fundsTransferRequestModel = new FundsTransferRequestModel();
        fundsTransferRequestModel.setDirection("request");
        fundsTransferRequestModel.setAction("FT");
        fundsTransferRequestModel.setTerminalId("7000000001");
        fundsTransferRequestModel.setTransaction(transactionModel);

        return fundsTransferRequestModel;
    }

    public void showProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            return;
        mProgressDialog = ProgressDialog.show(context, null, null);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
    }

    public void dismissProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

    }
    public void transferFunds(String name, String account_no) {

        RequestEnvelope requestEnvelope = new RequestEnvelope();
        RequestBody requestBody = new RequestBody();
        ProcessModel processModel = new ProcessModel();
        processModel.fundsTransferRequestModel = getRequestModel(name, account_no);//getRequestModelForFundingWallet();//getRequestModel();
        requestBody.processModel = processModel;

        requestEnvelope.body = requestBody;

        FundsTransferRequestModel fundsTransferRequestModel = getRequestModel(name, account_no);
        JsonObject fundsObject = new JsonObject();

        fundsObject.addProperty("bankCode", fundsTransferRequestModel.getTransaction().getBankCode());
        fundsObject.addProperty("amount", fundsTransferRequestModel.getTransaction().getAmount());
        fundsObject.addProperty("description", fundsTransferRequestModel.getTransaction().getDescription());
        fundsObject.addProperty("destination", fundsTransferRequestModel.getTransaction().getDestination());
        fundsObject.addProperty("reference", fundsTransferRequestModel.getTransaction().getReference());
        fundsObject.addProperty("senderName", fundsTransferRequestModel.getTransaction().getSenderName());
        fundsObject.addProperty("endPoint", fundsTransferRequestModel.getTransaction().getEndPoint());
//        fundsObject.addProperty("terminalId", user.getTerminal_id());
//        fundsObject.addProperty("stormId", user.getNetplus_id());

        String userToken = SharedPrefManager.getUserToken();

        showProgressBar();
        StormAPIClient.transfer(fundsObject, context)
                .subscribe(res -> {

                    if(res.code() != 200) {
                        utilities.showAlertDialogOk("Transaction Failure", "Unexpected error occurred, Please contact administrator for further advice", ((HomeActivity) getActivity()), false);
                        dismissProgressBar();
                        return;
                    }

                    JSONObject payload =  new JSONObject(new Gson().toJson(res.body()));;

                    if(!payload.getBoolean("success")) {
                        utilities.showAlertDialogOk("Transaction Failure", payload.getString("message"), ((HomeActivity) getActivity()), false);
                        dismissProgressBar();
                        return;
                    }

                    dismissProgressBar();
                    context.startActivity(new Intent(context, SuccessActivity.class).putExtra("status", true).putExtra("message", payload.getString("message")));
                    ((HomeActivity) context).getSupportFragmentManager().popBackStack(DashboardFragment.class.getSimpleName(), 0);

                }, t -> {
                    Log.e(getTag(), "An unexpected error occurred",  t);
                    dismissProgressBar();
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                });

    }

    public void showOrHideSignatureAlert(boolean isError) {
        if (isError) {
            binding.tvSign.setText(context.getResources().getString(R.string.lbl_signature_required));
            binding.tvSign.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.error_small, 0);
            binding.tvSign.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            binding.tvSign.setText(context.getResources().getString(R.string.lbl_signature));
            binding.tvSign.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            binding.tvSign.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
    }

    public String getFilePath() {

        final File folder = new File(getActivity().getExternalFilesDir(null), "/signature");
        boolean directoryExists = false;
        if (folder.exists()) {
            directoryExists = true;

        } else {
            directoryExists = folder.mkdir();
        }

        if (directoryExists) {
            String path = folder.getAbsolutePath();
            path = path + "/" + "IMG_" + System.currentTimeMillis() + ".png";// path where pdf will be stored
            return path;
        }
        return null;

    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("Base64", encImage);
        return encImage;
    }

    /*public class Signature extends View
    {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public Signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath)
        {

            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null)
            {

                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);

            }
            Canvas canvas = new Canvas(bitmap);
            try
            {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            //binding.linearNext.setEnabled(true);
            hasSigned = true;
            this.getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    *//*binding.scrollView.setEnableScrolling(false);*//*
                    Log.e("Action","ACTION_DOWN");
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:
                    Log.e("Action","ACTION_MOVE");

                case MotionEvent.ACTION_POINTER_UP:
                    *//*binding.scrollView.setEnableScrolling(true);*//*
                    Log.e("Action","ACTION_POINTER_UP");
                    break;
                case MotionEvent.ACTION_UP:
                    //binding.scrollView.setEnableScrolling(true);
                    Log.e("Action","ACTION_UP");
                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }*/

    public class Signature extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public Signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {

            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null) {

                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);

            }
            Canvas canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            //binding.linearNext.setEnabled(true);
            this.getParent().requestDisallowInterceptTouchEvent(true);
            hasSigned = true;
            //binding.tvSign.setError(null);
            showOrHideSignatureAlert(false);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    public void showSignaturePad() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        //final BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //R.anim.bottom_up_animation;
        dialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());


//        Window window = dialog.getWindow();
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.setCancelable(true);
//        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
//        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(
//                Color.TRANSPARENT));


        LinearLayout linearCanvas = dialog.findViewById(R.id.linearCanvas);
        Button btnRetake = dialog.findViewById(R.id.btnRetake);
        Button btnDone = dialog.findViewById(R.id.btnDone);


        btnRetake.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.2f;
        window.setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        //soldshipDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }
}