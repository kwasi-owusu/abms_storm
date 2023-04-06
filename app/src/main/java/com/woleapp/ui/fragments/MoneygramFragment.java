package com.woleapp.ui.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import com.hbb20.CCPCountry;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent;

//import com.mukesh.countrypicker.CountryPicker;
import com.juanpabloprado.countrypicker.CountryPicker;
import com.juanpabloprado.countrypicker.CountryPickerListener;
import com.woleapp.R;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.databinding.LayoutMoneygramBinding;
import com.woleapp.db.Injection;
import com.woleapp.db.UserViewModel;
import com.woleapp.model.Agency;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.Balance;
import com.woleapp.model.BankList;
import com.woleapp.model.Branch;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MoneygramFragment extends BaseFragment implements View.OnClickListener {
    private CountryCodePicker ccp;
    //private CountryPicker countryPicker;
    Context context;
    private LayoutMoneygramBinding binding;
    LinearLayout mContent;
    String currency_symbol = "";
    Drawable customErrorDrawable;
    Utilities utilities;
    Bitmap bitmap;
    Signature mSignature;
    private String purposeID = "";
    String[] purposeIDs;
    int len = 0;
    String mode = "", selected_bank_code = "";
    List<BankList> banks = new ArrayList<>();
    List<String> bankNames = new ArrayList<>();
    List<String> bankCodes = new ArrayList<>();
    boolean hasSigned = false;
    AgencyUser user;
    //List<String> CountryCodePicker = new ArrayList<>();
    //private CountryCodePicker countryCodePicker;
    private UserViewModel mViewModel;
    // val countryCodePicker = findViewById<CountryCodePicker>(R.id.countyCodePicker)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);

        user = SharedPrefManager.getAgencyUser();
        purposeIDs = context.getResources().getStringArray(R.array.trans_purpose);
        Log.e("AgencyData", "user" + user);
        ((HomeActivity) getActivity()).setTitleWithNoNavigation("Dashboard");

        mViewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(getActivity()))
                .get(UserViewModel.class);

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);

        binding.btnContinue.setOnClickListener(this);
        binding.date.setText(getTodaysDate());
        binding.date.setEnabled(false);
        binding.time.setText(getTodaysTime());
        binding.time.setEnabled(false);
        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
        Float convenience_fee = SharedPrefManager.getTransfeeConvenienceFee();
        binding.etFee.setText(utilities.getFormattedAmount(convenience_fee));
        addTextChangeListener();

        int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("agency_id", 1);
        jsonObject.addProperty("branch_id", 1);

        //getAgencyDetails(jsonObject);
        getBankList(jsonObject);
        Log.e("RES_CODE", "Payload: " + jsonObject);

        setListeners();
        prepareCanvas();
        setBank();
        setPurposeSpinner();
        //selectCountry();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_moneygram, container, false);
        return binding.getRoot();
    }

    public void onClick(View v) {
        if (v == binding.btnContinue) {
            Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show();
        } else if (v == binding.dateOfBirth) {
            selectDate();
        }
        else if(v == binding.country){
            selectCountry();
        }
        else if(v == binding.countryOfBirth){
            selectCountryOfBirth();
        }
        else if( v == binding.sendCountry){
            selectSendCountry();
        }
        else if (v == binding.linearCanvas) {
            showSignaturePad();
        } else if (v == binding.tvRetake) {
            mSignature.clear();
            hasSigned = false;
        }
    }
    public void selectDate() {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        binding.dateOfBirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
    public void selectCountry(){
        CountryPicker picker = CountryPicker.getInstance("Select Country", new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code) {
               // Toast.makeText(getActivity(), "Name: " + name, Toast.LENGTH_SHORT).show();
                binding.country.setText(name.toUpperCase());
                DialogFragment dialogFragment =
                        //(DialogFragment) getSupportFragmentManager().findFragmentByTag("CountryPicker");
                        (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("CountryPicker");
                dialogFragment.dismiss();
            }
        });
        picker.show(getActivity().getSupportFragmentManager(), "CountryPicker");
    }
    public void selectCountryOfBirth(){
        CountryPicker picker = CountryPicker.getInstance("Select Country", new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code) {
                binding.countryOfBirth.setText(name.toUpperCase());
                DialogFragment dialogFragment =
                        (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("CountryPicker");
                dialogFragment.dismiss();
            }
        });
        picker.show(getActivity().getSupportFragmentManager(), "CountryPicker");
    }
    public void selectSendCountry(){
        CountryPicker picker = CountryPicker.getInstance("Select Country", new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code) {
                binding.sendCountry.setText(name.toUpperCase());
                DialogFragment dialogFragment =
                        (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("CountryPicker");
                dialogFragment.dismiss();
            }
        });
        picker.show(getActivity().getSupportFragmentManager(), "CountryPicker");
    }
    public void setListeners() {
        binding.tvRetake.setOnClickListener(this);
        binding.etFee.setKeyListener(null);
        binding.btnContinue.setOnClickListener(this);
        binding.dateOfBirth.setOnClickListener(this);
        binding.country.setOnClickListener(this);
        binding.countryOfBirth.setOnClickListener(this);
        binding.sendCountry.setOnClickListener(this);
    }
    public void setPurposeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.trans_purpose, R.layout.spinner_view_trans_purpose);
        adapter.setDropDownViewResource(R.layout.spinner_view_trans_purpose);
        binding.spnPurpose.setPrompt(context.getResources().getString(R.string.hint_choose_purpose_trans));

        binding.spnPurpose.setAdapter(
                // adapter
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_trans_purpose,
                        getActivity()
                )
        );

        binding.spnPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View parent, int position, long l) {
                if (position > 0) {
                    //binding.spnIdType.setEnabled(false);
                    purposeID = purposeIDs[position - 1];
                    Log.e("selected_id", purposeID + "---");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

//    public void setRefSpinner() {
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.reference, R.layout.spinner_view_ref_type);
//        adapter.setDropDownViewResource(R.layout.spinner_view_ref_type);
//        binding.spnReference.setPrompt("SELECT REFERENCE");
//
//        binding.spnReference.setAdapter(
//                 adapter
////                new NothingSelectedSpinnerAdapter(
////                        adapter,
////                        R.layout.spinner_view_ref_type,
////                        getActivity()
////                )
//        );
//        String selection = "If you have a bank account";
//        int spinnerPosition = adapter.getPosition(selection);
//        binding.spnReference.setSelection(spinnerPosition);
//
//        String selection1 = "If you do not have a bank account";
//        int spinnerPosition1 = adapter.getPosition(selection1);
//       // binding.spnReference.setSelection(spinnerPosition1);
//
//        binding.spnReference.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View parent, int position, long l) {
//                if (position == spinnerPosition) {
//                    //binding.spnIdType.setEnabled(false);
//                    binding.spnRefBankLayout.setVisibility(View.GONE);
//                    binding.etRefAccountNo.setVisibility(View.GONE);
//                    binding.etAccountNumber.setVisibility(View.VISIBLE);
//                    binding.spnBankLayout.setVisibility(View.VISIBLE);
//                }
//                else if(position == spinnerPosition1){
//                    binding.etAccountNumber.setVisibility(View.GONE);
//                    binding.spnBankLayout.setVisibility(View.GONE);
//                    binding.spnRefBankLayout.setVisibility(View.VISIBLE);
//                    binding.etRefAccountNo.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
//    }
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

                    setBank();
                    //setRefBank();
                }

            }, err -> {
                Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
            });
}

    public void setBank() {
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
    public void prepareCanvas() {

        //mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
        mContent = binding.linearCanvas;
       // mSignature = new Signature(context, null);
        mSignature = new Signature(context, null);
        mSignature.setBackgroundColor(Color.WHITE);
        //mSignature.getParent().requestDisallowInterceptTouchEvent(true);

        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //binding.linearNext.setEnabled(false);
        hasSigned = false;
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
    public String getTodaysDate() {

        LocalDate date = new LocalDate();
        LocalTime time = new LocalTime();
        DateTimeFormatter toDateTimeFormat
                = DateTimeFormat.forPattern("dd MMM, yyyy");

        String s = toDateTimeFormat.print(date);
        return "" + s;
    }

    public String getTodaysTime() {

        LocalTime time = new LocalTime();
        DateTimeFormatter toDateTimeFormat
                = DateTimeFormat.forPattern("HH:mm a");

        String f = toDateTimeFormat.print(time);
        return "" + f;
    }
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

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
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

}
