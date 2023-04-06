package com.woleapp.ui.fragments;

import static com.woleapp.ui.activity.PaymentProgressActivity.KEY_AMOUNT;

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
import android.text.Editable;
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
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent;
import com.netpluspay.netpossdk.NetPosSdk;
import com.netpluspay.netpossdk.emv.CardReadResult;
import com.pos.sdk.printer.POIPrinterManage;
import com.pos.sdk.printer.models.BitmapPrintLine;
import com.pos.sdk.printer.models.PrintLine;
import com.pos.sdk.printer.models.TextPrintLine;
import com.woleapp.R;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.databinding.LayoutCashInOptionsNewBinding;
import com.woleapp.model.Agency;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.Balance;
import com.woleapp.model.BankList;
import com.woleapp.model.Branch;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.network.StormAPIClient;
import com.woleapp.network.soap.request.FundsTransferRequestModel;
import com.woleapp.network.soap.request.ProcessModel;
import com.woleapp.network.soap.request.RequestBody;
import com.woleapp.network.soap.request.RequestEnvelope;
import com.woleapp.network.soap.request.TransactionModel;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.ui.activity.PaymentProgressActivity;
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
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CashInOptionsNewFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutCashInOptionsNewBinding binding;
    AgencyUser user;
    Drawable customErrorDrawable;

    CashInFragment.Signature mSignature;
    View signature_view;
    Bitmap bitmap;
    boolean hasSigned = false;
    int len = 0;
    String amount = "";

    LinearLayout mContent;
    File file;
    String currency_symbol = "";
    int result0, transaction_type = 0;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String mode = "", selected_bank_code = "";
    //double amount = 0.0;
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
        Log.e("AgencyData", "user"+ user);
        ((HomeActivity) getActivity()).setTitleWithNoNavigation("Dashboard");

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //populate();
        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);

        binding.cardCashCollection.setOnClickListener(this);
//        binding.cardPosPayment.setOnClickListener(this);
        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
        Float convenience_fee = SharedPrefManager.getTransfeeConvenienceFee();
        binding.etFee.setText(utilities.getFormattedAmount(convenience_fee));
        addTextChangeListener();

        int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();

//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("agency_ID", agencyID);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("agency_id", 1);
        jsonObject.addProperty("branch_id", 1);

        getAgencyDetails(jsonObject);
        Log.e("RES_CODE", "Payload: " + jsonObject);

        Bundle b1 = getArguments();
        if (b1 != null) {
            if (b1.containsKey("transaction_type")) {
                transaction_type = b1.getInt("transaction_type", 0);
                mode = b1.getString("mode", "");
                if (mode.equalsIgnoreCase("cash")) {
                    binding.etFee.setVisibility(View.GONE);
                    binding.tvConvinienceFee.setVisibility(View.GONE);
                }
                //amount = b1.getDouble(KEY_AMOUNT);

                //binding.tvAmount.setText(utilities.getFormattedAmount(amount));
                if (transaction_type == Constants.TRANSACTION_CASH_IN) {
                    binding.tvTitle.setText("CASH-IN / BANK TRANSFER");
                } else {
                    binding.tvTitle.setText("CASH-OUT");
                }
            }
        }
//

        getBankList(jsonObject);

        setListeners();

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
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_cash_in_options_new, container, false);
        return binding.getRoot();
    }

    public void setListeners() {
        //binding.tvRetake.setOnClickListener(this);
        binding.etFee.setKeyListener(null);
       // binding.btnContinue.setOnClickListener(this);
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

                //call get account name
               // getAccountName();

            //}
        //});

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

public void getAgencyDetails(JsonObject payload) {
    Disposable subscribe = AgencyBankingAPIClient
            .agencyDetails(payload, context)
            .subscribe(res -> {
                if(res.code() != 200) {
                    Toast.makeText(context, "Failed to get Agency Details", Toast.LENGTH_SHORT).show();
                    return;
                }

                Object body = res.body();
                JSONObject response = new JSONObject(new Gson().toJson(body));
                Log.e("RES_CODE", "Agency: " + response);
                if(response.optInt("code") != 112) {
                    Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                    return;
                }

                Agency agency = new Agency();
                agency.setAgencyCode(response.optJSONObject("agency_data").optString("agency_code"));
                agency.setAgencyName(response.optJSONObject("agency_data").optString("agency_name"));
                agency.setAgentKey(response.optJSONObject("agency_data").optString("agency_key"));
                agency.setAgencyStatus(response.optJSONObject("agency_data").optString("agency_status"));

                SharedPrefManager.setAgencyDetails(agency);

                //call function to get branch details
                int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();
                int branchID = SharedPrefManager.getAgencyUser().getUserBranch();
                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("agency_id", agencyID);
                jsonObject.addProperty("branch_id", branchID);

                Log.e("RES_CODE", "Branch payload: " + jsonObject);

                getBranchDetails(jsonObject);


            }, err -> {
                Toast.makeText(context, "An unexpected error occured", Toast.LENGTH_LONG).show();
            });
}

    private void getBranchDetails(JsonObject payload) {
        Disposable subscribe = AgencyBankingAPIClient
                .branchDetails(payload, context)
                .subscribe(res -> {
                    if(res.code() != 200) {
                        Toast.makeText(context, "Failed to get Agent Balance", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("RES_CODE", "Branch: " + response);
                    if(response.optInt("code") != 112) {
                        Toast.makeText(context, response.optString("msg"), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Branch branch = new Branch();
                    branch.setBranchCode(response.optJSONObject("branch_data").optString("branch_code"));
                    branch.setBranchName(response.optJSONObject("branch_data").optString("branch_name"));
                    branch.setBranchStatus(response.optJSONObject("branch_data").optString("branch_status"));
                    branch.setBranchKey(response.optJSONObject("branch_data").optString("branch_key"));

                    SharedPrefManager.setBranchDetails(branch);

                    //call function to get balance
                    String officerID = SharedPrefManager.getAgencyUser().getOfficerID();
                    int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();
                    int branchID = SharedPrefManager.getAgencyUser().getUserBranch();
                    String agentKey = SharedPrefManager.getAgencyDetails().getAgentKey();
                    String branchKey = response.optJSONObject("branch_data").optString("branch_key");

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("teller_ID", Integer.parseInt(officerID));
                    jsonObject.addProperty("agent_ID", agencyID);
                    jsonObject.addProperty("branch_ID", branchID);
                    jsonObject.addProperty("agentKey", agentKey);
                    jsonObject.addProperty("branchKey", branchKey);
                    Log.e("RES_CODE", "Balance payload: " + jsonObject);
                    getAgentBalance(jsonObject);


                }, err -> {
                    Toast.makeText(context, "An unexpected error occured", Toast.LENGTH_LONG).show();
                });
    }
    public void getAgentBalance (JsonObject payload) {
        Disposable subscribe = AgencyBankingAPIClient
                .branchBalance(payload, context)
                .subscribe(res -> {
                    if(res.code() != 200) {
                        Toast.makeText(context, "Failed to get Agent Balance", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("RES_CODE", "Balance: " + response);
                    if(response.optInt("code") != 112) {
                        Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Balance balance = new Balance();
                    balance.setBalance(response.getString("balaance"));
                    balance.setToken(response.optString(("token")));

                    SharedPrefManager.setBalanceDetails(balance);

                    //perform computations and saved balance to shared preferences
                }, err -> {
                    Toast.makeText(context, "An unexpected error occured", Toast.LENGTH_LONG).show();
                });
    }
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

    }
    public void addTextChangeListener() {

        binding.etFee.setKeyListener(null);

        RxTextView.afterTextChangeEvents(this.binding.etAmt)
                .skip(1)
                .debounce(1000, TimeUnit.MILLISECONDS)
                //.toFlowable(BackpressureStrategy.BUFFER)
                .cache()
                .filter(textViewTextChangeEvent -> this.binding.etAmt.hasFocus())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void accept(TextViewAfterTextChangeEvent textViewTextChangeEvent) throws Exception {
                        Log.e("tag", "text chanege");
                        Editable editable = textViewTextChangeEvent.getEditable();
                        if (editable.length() != 0 && editable.length() != len) {
                            String text = editable.toString();
                            if (text.length() > 0) {
                                text = text.replace(currency_symbol, "").replaceAll(",", "");

                                if (text.length() > 0) {
                                    boolean result = priceValidation(text);
                                    if (!result) {
                                        //binding.etPrice.setError("Invalid input");
                                        binding.etAmt.setError("Invalid input", customErrorDrawable);
                                        //binding.etPrice.requestFocus();
                                        len = text.length();
                                    } else {
                                        Double amt = Double.parseDouble(text);
                                        if (amt < 1) {
                                            binding.etAmt.setError("Minimum amount of transaction is " + currency_symbol + 1, customErrorDrawable);
                                            //binding.etPrice.requestFocus();
                                        } else if (amt > 100000) {
                                            binding.etAmt.setError("Maximum amount of transaction is " + currency_symbol + 100000, customErrorDrawable);
                                        } else {
                                            binding.etAmt.setError(null);
                                            String amt1 = utilities.getFormattedAmount(amt);
                                            binding.etAmt.setText(amt1);
                                            try {
                                                /*binding.etPrice.setSelection(amt1.length());*/
                                                binding.etAmt.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        binding.etAmt.setSelection(binding.etAmt.getText().length());
                                                    }
                                                });
                                                len = amt1.length();
                                                Log.e("Set_Selection", "Set_Selection");
                                            } catch (IndexOutOfBoundsException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    }
                                }

                            }
                        }
                    }
                });

    }

    private void processTransactions(String account, String idCard, String amt, String selected_bank_code) {
        //payload
        String officer_ID = "13";
        String branch_code = "1";
        String inst_ID = "1";
        String branch_key = SharedPrefManager.getBranchDetails().getBranchKey();
        String agency_key = SharedPrefManager.getAgencyDetails().getAgentKey();
        String trans_cat = "1";
        String trans_type = "2";
        String total_amnt = amt;
        String narration = "Test Narration";
       // String customer_name = name;
        String account_number = account;
        //String depositor_payee = depName;
        String ID_type = idCard;
        //String ID_number = idNo;
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
        transaction.addProperty("amount", Integer.parseInt(total_amnt));

        transaction.addProperty("narration", narration);
        transaction.addProperty("id_type", ID_type);
        //transaction.addProperty("id_number", ID_number);
        //transaction.addProperty("depositor_payee", depositor_payee);
        //transaction.addProperty("customer_name", customer_name);
        transaction.addProperty("product_ID", Bank_code);

        JsonObject customer = new JsonObject();
        //customer.addProperty("account_name", customer_name);
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
                    //printReceipt(customer_name, depositor_payee, ID_type, ID_number, a_name, branchName, ref);
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

        textPrintLine.setContent("Account Name: " + CustomerName);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

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
                Toast.makeText(context, "Printing job finished", Toast.LENGTH_SHORT).show();
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
                    //binding.etName.setText(payload.getString("accountName"));
                    dismissProgressBar();

                }, t -> {
                    Log.e(getTag(), "An unexpected error occurred",  t);
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();;
                    dismissProgressBar();
                });

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
    //@Override
    public void onClick(View v) {
        Float convenienceFee = SharedPrefManager.getTransfeeConvenienceFee();
        String availableBalance = SharedPrefManager.getBalanceDetails().getBalance();
        if (v == binding.cardCashCollection) {
//                    if(selected_bank_code == null || selected_bank_code.isEmpty()) {
//                        return;
//                    }
//                    //return if bank account not selected
//                    String acc_no = binding.etAccountNo.getText().toString().trim();
//                    if(acc_no == null || acc_no.isEmpty() || acc_no.length() != 10) {
//                        return;
//                    }
            amount = binding.etAmt.getText().toString();
            amount = amount.replace(currency_symbol, "").replaceAll(",", "");
            String acc_number = binding.etAccountNo.getText().toString().trim();
            if(selected_bank_code == null || selected_bank_code.isEmpty()){
                Toast.makeText(context, "Please select bank",Toast.LENGTH_LONG).show();
            }
            else if(TextUtils.isEmpty(acc_number)){
                binding.etAccountNo.setError("Please enter account number", customErrorDrawable);
                binding.etAccountNo.requestFocus();
            }
            else if (TextUtils.isEmpty(amount)) {
                binding.etAmt.setError("Please enter the amount you want to deposit", customErrorDrawable);
                binding.etAmt.requestFocus();
            } else if (!priceValidation(amount)) {
                binding.etAmt.setError("Invalid input", customErrorDrawable);
                binding.etAmt.requestFocus();
            } else if (Double.parseDouble(amount) < 1 || Double.parseDouble(amount) > 100000) {
                binding.etAmt.setError("Amount should be between " + currency_symbol + 1 + " and " + currency_symbol +100000, customErrorDrawable);
                binding.etAmt.requestFocus();
            } else if (Double.parseDouble(amount) + convenienceFee > Double.parseDouble(availableBalance)) {
                binding.etAmt.setError("Insufficient balance to proceed", customErrorDrawable);
                binding.etAmt.requestFocus();
//                SharedPrefManager.getAgencyUser().getAvailableBalance())
            }
            else {
                utilities.hideKeyboard(getActivity());
                Bundle b1 = new Bundle();
                Double amt = Double.parseDouble(amount);
                b1.putDouble(PaymentProgressActivity.KEY_AMOUNT, amt);
                b1.putInt("transaction_type", Constants.TRANSACTION_CASH_IN);
                b1.putString("mode", "cash");
                b1.putString("customer", acc_number);
                CashInFragment cin = new CashInFragment();
                cin.setArguments(b1);
                addFragmentWithoutRemove(R.id.container_main, cin, CashInFragment.class.getSimpleName());
            }
            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Log.e("RequestCode: " + requestCode, "ResultCode " + resultCode);
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
       // transactionModel.setAmount(amount);
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
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(context, null, null);
            mProgressDialog.setContentView(R.layout.dialog_progress);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ffffff")));
            mProgressDialog.setCancelable(false);

            if (mProgressDialog!=null && !mProgressDialog.isShowing()){
                mProgressDialog.show();
            }
        } else {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        }
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

}
