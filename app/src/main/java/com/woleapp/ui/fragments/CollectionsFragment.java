package com.woleapp.ui.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent;
import com.woleapp.R;
import com.woleapp.databinding.LayoutCollectionsBinding;
import com.woleapp.db.Injection;
import com.woleapp.db.LocalCache;
import com.woleapp.db.UserViewModel;
import com.woleapp.model.Agency;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.Balance;
import com.woleapp.model.Branch;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.ui.activity.PaymentProgressActivity;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;
import com.woleapp.util.mobilevision.BarcodeCaptureActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import smartpesa.sdk.ServiceManager;
import smartpesa.sdk.ServiceManagerConfig;

import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.woleapp.util.Constants.*;

import org.json.JSONObject;


public class CollectionsFragment extends BaseFragment implements View.OnClickListener {
    Context context;
    private LayoutCollectionsBinding binding;
    String amount = "";
    AgencyUser user;
    private UserViewModel mViewModel;
    float amt = 0.0f;
    int transaction_type = 0;
    final int SCAN_QR_CODE = 1001, PAY_VIA_SMARTPESA = 1024;
    int result0, result1, result2;
    private static final int PERMISSION_REQUEST_CODE_CAMERA = 200,
            PERMISSION_REQUEST_CODE_BLUETOOTH = 201;
    String currency_symbol = "";

    ServiceManager mServiceManager;
    Utilities utilities;
    Drawable customErrorDrawable;
    ProgressDialog mProgressDialog;
    int len = 0;

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Log.e("CashInOptionsFragment", "onReceive");
//            user = SharedPrefManager.getUser();
            user = SharedPrefManager.getAgencyUser();
        }
    };

    boolean skip = false;

    @Override
    public void onStart() {
        Log.e("onStart", "onStart");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myReceiver,
                new IntentFilter("UpdateUser"));
        super.onStart();
    }

    @Override
    public void onStop() {

        Log.e("onStop", "onStop");
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myReceiver);
        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

//        user = SharedPrefManager.getUser();
        user = SharedPrefManager.getAgencyUser();
        Log.e("AgencyData", "user"+ user);
        ((HomeActivity) getActivity()).setTitleWithNoNavigation("Dashboard");

        mViewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(getActivity()))
                .get(UserViewModel.class);

        //calling balance endpoint here
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);

        binding.cardCashCollection.setOnClickListener(this);
//        binding.cardPosPayment.setOnClickListener(this);
        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
        Float convenience_fee = SharedPrefManager.getTransfeeConvenienceFee();
        binding.etFee.setText(utilities.getFormattedAmount(convenience_fee));
        addTextChangeListener();

        int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();
//        String mId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
//        binding.myTextView.setText(mId);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("agency_ID", agencyID);

        getAgencyDetails(jsonObject);
        Log.e("RES_CODE", "Payload: " + jsonObject);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        //user = SharedPrefManager.getInstance(context).getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_collections, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
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
    @Override
    public void onClick(View v) {

        Float convenienceFee = SharedPrefManager.getTransfeeConvenienceFee();

        String availableBalance = SharedPrefManager.getBalanceDetails().getBalance();
        String name = binding.payeeName.getText().toString().trim();
        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);
       // String description = binding.description.getText().toString().trim();
        if (v == binding.cardCashCollection) {
            amount = binding.etAmt.getText().toString();
            amount = amount.replace(currency_symbol, "").replaceAll(",", "");
            if (TextUtils.isEmpty(amount)) {
                binding.etAmt.setError("Please enter the amount you want to deposit", customErrorDrawable);
                binding.etAmt.requestFocus();
            }
            else if(TextUtils.isEmpty(name)){
                binding.payeeName.setError("Name is required", customErrorDrawable);
                binding.payeeName.requestFocus();
            }
           // else if(TextUtils.isEmpty(description)){
             //   binding.description.setError("Description is required", customErrorDrawable);
               // binding.description.requestFocus();
            //}
            else if (!priceValidation(amount)) {
                binding.etAmt.setError("Invalid input", customErrorDrawable);
                binding.etAmt.requestFocus();
            } else if (Double.parseDouble(amount) < 1 || Double.parseDouble(amount) > 100000) {
               // binding.etAmt.setError("Amount should be between \u20A610 and \u20A6100000", customErrorDrawable);
                binding.etAmt.setError("Amount should be between " + currency_symbol + 1 + " and " + currency_symbol +100000, customErrorDrawable);
                binding.etAmt.requestFocus();
            } else if (Double.parseDouble(amount) + convenienceFee > Double.parseDouble(availableBalance)) {
                binding.etAmt.setError("Insufficient balance to proceed", customErrorDrawable);
                binding.etAmt.requestFocus();
//                SharedPrefManager.getAgencyUser().getAvailableBalance())
            } else {
                processCollections(name, amount);

            }
        }
    }
    public void processCollections(String name, String amount){
        showProgressBar();
        String customerName = name;
        String describe = "Description";
        String total_amnt = amount;

        String merchantID = "63b59e41530aeeaec59a045f";
        String appID = "640afda470740ed7caa8d599";
        String callbackURL = "https://google.com";
        String clientRedirectURL = "https://google.com";

        JsonObject pPay = new JsonObject();
        pPay.addProperty("merchantId", merchantID);
        pPay.addProperty("appId", appID);
        pPay.addProperty("amount", total_amnt);
        pPay.addProperty("description", describe);
        pPay.addProperty("callbackURL", callbackURL);
        pPay.addProperty("clientRedirectURL", clientRedirectURL);
        pPay.addProperty("payer", customerName);

        Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + pPay);
        Disposable subscribe = AgencyBankingAPIClient
                .pPayInit(pPay, context)
                .subscribe(res -> {
                    if (res.code() != 200) {
                        Toast.makeText(context, "Failed to process transaction", Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("TRANSACTION_RESPONSE", "transaction response: " + response);
                    if (response.optString("success").equals("false")){
                        // if (response.optInt("code") != 112) {
                        Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("url", response.optString("checkoutUrl"));
                    WebViewFragment agdf = new WebViewFragment();
                    agdf.setArguments(bundle);
                    replaceFragmentWithBack(R.id.container_main, agdf, WebViewFragment.class.getSimpleName());
                    dismissProgressBar();
                    // String ref = response.optString("ref");
                    //process transaction
                }, err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                    dismissProgressBar();
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

    /*public void updateUser(String amount) {

        if (amount != null) {
            amt = Float.parseFloat(amount) + Float.parseFloat(user.getAvailableBalance());
        } else {
            amt = Float.parseFloat(amount);
        }
        //user.setIs_verified(true);
        LocalCache.UpdateCallback callback = new LocalCache.UpdateCallback() {
            @Override
            public void updateFinished(int result) {
                if (result > 0) {
                    user.setAvailableBalance(String.valueOf(amt));
                    SharedPrefManager.setAgencyUser(user);
                    //Toast.makeText(context,"Registration successful",Toast.LENGTH_SHORT).show();

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            addFragmentWithoutRemove(R.id.container_main, new PaymentSuccessFragment(), CashInOptionsFragment.class.getSimpleName());
//                            ((HomeActivity) getActivity()).setAvailableBalance(String.valueOf(amt));

                        }
                    });
                    //showFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
                    //showFragment(new OTPFragment(),OTPFragment.class.getSimpleName());
                    //addFragmentWithoutRemove(R.id.container_main,new OTPFragment(), OTPFragment.class.getSimpleName());
                } else {

                }
            }
        };

//        Log.e("UserIdToBeUpdated", user.getUser_id() + "--");
//        mViewModel.updateWalletAmount(user.getAgentKey(), String.valueOf(amt), callback);
    }

    private OnItemClickListener.OnItemClickCallback onClick = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {

//            if (user.getUser_type() == USER_TYPE_AGENT) {
//                if (position == 0) {
//                    addFragmentWithoutRemove(R.id.container_main, new FundWalletFragment(), FundWalletFragment.class.getSimpleName());
//                }
//            } else {
//                if (position == 0) {
//                    addFragmentWithoutRemove(R.id.container_main, new QuickTransactionFragment(), QuickTransactionFragment.class.getSimpleName());
//                }
//            }

        }
    };*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("PaymentModeFragment", "onActivityResult: " + requestCode + " , " + resultCode);
        if (data != null) {

        }
        if (requestCode == SCAN_QR_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                    String result = barcode.displayValue;
                    if (result != null) {
                        Log.e("QR", "Barcode read: " + result);////                try
                        Toast.makeText(context, "Your Payment to merchant " + result + " was successful", Toast.LENGTH_LONG).show();
                        showFragment(new DashboardFragment(), DashboardFragment.class.getSimpleName());
                    } else {
                        //Log.e("QR", "Barcode read: " + result);////                try
                        Toast.makeText(context, "Payment Failed", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Log.e("QR", "No barcode captured, intent data is null");
                }
            } else {

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

}
