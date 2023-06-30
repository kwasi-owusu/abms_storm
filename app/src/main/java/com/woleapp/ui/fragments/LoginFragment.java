package com.woleapp.ui.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netpluspay.netpossdk.NetPosSdk;
import com.woleapp.R;
import com.woleapp.databinding.DialogResetPasswordBinding;
import com.woleapp.databinding.DialogSupportBinding;
import com.woleapp.databinding.LayoutLoginBinding;
import com.woleapp.db.Injection;
import com.woleapp.db.UserViewModel;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.Business;
import com.woleapp.model.User;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.network.StormAPIClient;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.ui.activity.MainActivity;
import com.woleapp.util.ConnectivityReceiver;
import com.woleapp.util.JWTHelper;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import org.json.JSONObject;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static com.woleapp.util.Constants.USER_TYPE_AGENT;
import static com.woleapp.util.Constants.USER_TYPE_MERCHANT;
import static com.woleapp.util.Constants.USER_TYPE_NONE;


public class LoginFragment extends BaseFragment implements View.OnClickListener {


    private static final String tag = "LoginFragment";
    Context context;
    Utilities utilities;
    private LayoutLoginBinding binding;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    int user_type = 1;
    private UserViewModel mViewModel;
    Drawable customErrorDrawable;
    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
        mViewModel = new ViewModelProvider(this, Injection.provideViewModelFactory(getActivity()))
                .get(UserViewModel.class);
        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.etPwd.setTypeface(Typeface.DEFAULT);
        binding.etPwd.setTransformationMethod(new PasswordTransformationMethod());

        binding.etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.textInputUname.setHint(context.getResources().getString(R.string.hint_email));
                } else {
                    binding.textInputUname.setHint(context.getResources().getString(R.string.hint_enter_email));

                }
            }
        });

        binding.etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //binding.textInputPwd.setPasswordVisibilityToggleEnabled(true);
                    binding.textInputPwd.setHint(context.getResources().getString(R.string.hint_password));
                } else {
                    //binding.textInputPwd.setPasswordVisibilityToggleEnabled(false);
                    if (TextUtils.isEmpty(binding.etPwd.getText())) {
                        binding.etPwd.setError(null);
                        //binding.textInputPwd.setPasswordVisibilityToggleEnabled(false);
                        binding.etPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_pwd, 0);
                        binding.textInputPwd.setHint(context.getResources().getString(R.string.hint_enter_password));

                    } else {
                        //binding.textInputPwd.setPasswordVisibilityToggleEnabled(true);
                        binding.textInputPwd.setHint(context.getResources().getString(R.string.hint_password));
                    }
                    //binding.textInputPwd.setHint(context.getResources().getString(R.string.hint_enter_password));//

                    //editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawableRight, 0);
                    //binding.textInputPwd.setDrawa
                }
            }
        });
        binding.btnMerchant.setOnClickListener(this);
        binding.signUpButton.setOnClickListener(this);
        binding.tvSignUp.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.txtForgotPwd.setOnClickListener(this);
//        getFCMToken();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
//        user = SharedPrefManager.getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_login, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
    }

    public void showProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            return;
        mProgressDialog = ProgressDialog.show(context, null, null);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ffffff")));
        mProgressDialog.setCancelable(false);

    }

    public void dismissProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
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

    public boolean isValidEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {


        if (v == binding.txtForgotPwd) {
            showDialogReset();
        } else if (v == binding.signUpButton) {
            if (user_type != USER_TYPE_AGENT) {
                //binding.txtForgotPwd.setVisibility(View.VISIBLE);
                user_type = USER_TYPE_AGENT;
                binding.signUpButton.setCardElevation(3f);
                binding.signUpButton.setElevation(3f);
                binding.signUpButton.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

                binding.btnMerchant.setCardElevation(8f);
                binding.btnMerchant.setElevation(8f);
                binding.btnMerchant.setCardBackgroundColor(context.getResources().getColor(R.color.text_color_gray_dark));
            }


//            binding.btnAgent.setBackgroundResource(R.drawable.ripple_theme2);
//            binding.btnMerchant.setBackgroundResource(R.drawable.ripple_gray2);
        } else if (v == binding.btnMerchant) {
            if (user_type != USER_TYPE_MERCHANT) {
                //binding.txtForgotPwd.setVisibility(View.GONE);
                user_type = USER_TYPE_MERCHANT;
                binding.btnMerchant.setCardElevation(3f);
                binding.btnMerchant.setElevation(3f);
                binding.btnMerchant.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

                binding.signUpButton.setCardElevation(8f);
                binding.signUpButton.setElevation(8f);
                binding.signUpButton.setCardBackgroundColor(context.getResources().getColor(R.color.text_color_gray_dark));
            }

//            binding.btnAgent.setBackgroundResource(R.drawable.ripple_gray2);
//            binding.btnMerchant.setBackgroundResource(R.drawable.ripple_theme2);
        } else if (v == binding.tvSignUp) {
            showFragment(new SignupFragment(), SignupFragment.class.getSimpleName());
        } else if (v == binding.btnLogin) {
            validateInputsAndProceed();
            Log.d(tag, "button working");
        }
    }

    public void getFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(SharedPrefManager::saveDeviceToken);
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(getActivity(), new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String notificationID = instanceIdResult.getToken();
//                Log.e("newToken", notificationID + "--");
//                SharedPrefManager.saveDeviceToken(notificationID);
//                /*String pushToken = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();*/
//
//            }
//        });

        /*String id  = FirebaseInstanceId.getInstance().getId();
        Log.e("FB_Id",id+"--");*/
    }

    //validating the inputs and proceeding to login
    public void validateInputsAndProceed() {
        String appToken = SharedPrefManager.getAppToken();
//        if (appToken == null || JWTHelper.isExpired(appToken)) {
//            ((MainActivity) requireActivity()).checkToken(true);
//            showProgressBar();
//        }
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPwd.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            binding.etEmail.setError("Email is required", customErrorDrawable);
            binding.etEmail.requestFocus();
        } else if (!isValidEmail(email)) {
            binding.etEmail.setError("Invalid Email Address", customErrorDrawable);
            binding.etEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            binding.etPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            binding.etPwd.setError("Password is required", customErrorDrawable);
            binding.etPwd.requestFocus();
        } else {
            if (ConnectivityReceiver.isConnected()) {
//                JsonObject jsonObject = new JsonObject();
  //              jsonObject.addProperty("email", email);
    //            jsonObject.addProperty("password", password);

//                login(jsonObject);
      //          agencyLogin(jsonObject);
                merchantLogin(email, password);
            } else {
                utilities.showDialogNoNetwork("You need an active internet connection to proceed. Would you like to enable it ?", getActivity());
            }

        }
    }

    private void gotoHomeScreen(User user) {
        if (user.getUser_type() == USER_TYPE_MERCHANT && user.getQRRegistered()) {
            mViewModel.getBusinessInfo(user.getUser_id()).observe(LoginFragment.this, new Observer<Business>() {
                @Override
                public void onChanged(Business business) {
                    user.setBusiness_info(business);
                    SharedPrefManager.setUser(user);
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        } else {
            SharedPrefManager.setUser(user);
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        }

    }

    public void showDialogReset() {
        DialogResetPasswordBinding binding;
        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*dialog2.setContentView(R.layout.dialog_record_payment);
        binding = DataBindingUtil.setContentView( R.layout.dialog_record_payment);*/
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_reset_password, null, false);
        dialog2.setContentView(binding.getRoot());
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        binding.btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(context, "Email is required", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(context, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                } else {
                    dialog2.dismiss();
                    resetPassword(email);
                }

            }
        });

        dialog2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        dialog2.setCanceledOnTouchOutside(false);
        dialog2.setCancelable(true);
        dialog2.getWindow().setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        dialog2.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog2.show();
    }

    public void showDialogMailSent(String message) {
        DialogSupportBinding binding;
        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_support, null, false);
        dialog2.setContentView(binding.getRoot());
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.tvMessage.setText(message);
        binding.btnDone.setVisibility(View.VISIBLE);
        binding.btnDone.setText("Dismiss");
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        dialog2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        dialog2.setCanceledOnTouchOutside(false);
        dialog2.setCancelable(true);
        dialog2.getWindow().setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        dialog2.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog2.show();
    }

    public void resetPassword(String email) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", email);

        String appToken = SharedPrefManager.getAppToken();
        showProgressBar();
        Disposable subscribe = StormAPIClient
                .passwordReset(jsonObject, context)
                .subscribe(res -> {
                    Log.e("TAG", "code: " + res.code());
                    if (res.code() != 200) {
                        Toast.makeText(context, "Reset failed, please check email and try again.", Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    Object body = res.body();
                    Log.e("TAG", "body: " + body);

//                    JSONObject payload = new JSONObject(new Gson().toJson(body));
//
//                    if (!payload.getBoolean("success")) {
//                        Toast.makeText(context, "Reset failed, please check email and try again.", Toast.LENGTH_SHORT).show();
//                        dismissProgressBar();
//                        return;
//                    }

                    dismissProgressBar();
                    showDialogMailSent("Check your registered email address for your new password");

                }, t -> {
                    dismissProgressBar();
                    Log.e(getTag(), "An unexpected error occurred", t);
                    Toast.makeText(context, "An unexpected error occured", Toast.LENGTH_LONG).show();
                });

    }

    public void merchantLogin(String email, String Password){
        showProgressBar();
        String merchantEmail = email;
        String merchantPassword = Password;

        JsonObject loginDetails = new JsonObject();
        loginDetails.addProperty("email", merchantEmail);
        loginDetails.addProperty("password", merchantPassword);

        Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + loginDetails);
        Disposable subscribe = AgencyBankingAPIClient
                .agencyLogin(loginDetails, context)
                .subscribe(res -> {
                    if (res.code() != 200) {
                        Toast.makeText(context, "Failed to process", Toast.LENGTH_SHORT).show();
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
                    //addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                    AgencyUser user = new AgencyUser();
                    user.setMerchToken(response.optString("token"));
                    user.setMerchID(response.optJSONObject("data").optJSONObject("merchantId").optString("_id"));
                    user.setMerchantName(response.optJSONObject("data").optJSONObject("merchantId").optString("merchant_tradeName"));
                    SharedPrefManager.setAgencyLoginStatus(true);

                    SharedPrefManager.setAgencyUser(user);

                    dismissProgressBar();
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();

                    Log.e("TOKEN", "token " + response.optString("token"));
                    Log.e("Response Body", "body " + response);
                    Log.e("Merch ID", "merch id " + response.optJSONObject("data").optJSONObject("merchantId").optString("_id"));
                }, err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                    dismissProgressBar();
                });
    }
    public void agencyLogin(JsonObject payload) {
        showProgressBar();
        Disposable subscribe = AgencyBankingAPIClient
                .agencyLogin(payload, context)
                .subscribe(res -> {
                    Log.e("RES_CODE","msg" + "code: " + res.code());
                    if (res.code() != 200) {
                        Toast.makeText(context, "Login failed, please check email and try again.", Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("RES_CODE", "code: " + response);

                    if (response.optInt("responseCode") != 112) {
                        Toast.makeText(context, response.optString("msg"), Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    AgencyUser user = new AgencyUser();
                    user.setOfficerID(response.optString("id"));
                    user.setEmail(response.optString("email"));
                    user.setFirstName(response.optString("firstName"));
                    user.setLastName(response.optString("lastName"));
                    user.setAgencyID(response.optInt("agencyID"));
                    user.setUserBranch(response.optInt("userBranch"));
                    user.setUserLevel(response.optInt("userLevel"));
                    SharedPrefManager.setAgencyLoginStatus(true);

                    SharedPrefManager.setAgencyUser(user);
                    dismissProgressBar();

                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();

                }, err -> {
                    dismissProgressBar();
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                });
    }


    public void login(JsonObject jsonObject) {
        Log.e(tag, jsonObject.toString());
        showProgressBar();
        String appToken = SharedPrefManager.getAppToken();
        //get to
        Log.e(tag, "Token sending to server: " + appToken);
        Single.fromObservable(StormAPIClient
                        .getUserTokenObservable(appToken, jsonObject, context))
                .flatMap(res -> {
                    Object o = res.body();
                    //Log.e("TAG", "Log from getting user Token" + o.toString());
                    JSONObject payload = new JSONObject(new Gson().toJson(o));

                    if (!payload.getBoolean("success")) {
                        Log.e(tag, "login error");
                        throw new Exception("failed");
                    }

                    SharedPrefManager.setUserToken(payload.getString("token"));
                    JsonObject pushData = new JsonObject();
                    String token = payload.getString("token");
                    Log.e(tag, "token: " + token);
                    pushData.addProperty("pushToken", SharedPrefManager.getDeviceToken());
                    pushData.addProperty("stormId", JWTHelper.getStormId(token));
                    return Single.fromObservable(StormAPIClient.registerPushTokenObservable(pushData, context));

                })
                .flatMap(res -> {

                    String token = SharedPrefManager.getUserToken();
                    String stormId = JWTHelper.getStormId(token);
                    Log.e(tag, stormId);

                    if (stormId == null) {
                        throw new Exception("Error");
                    }
                    Log.e(tag, stormId);
                    Observable<Response<Object>> observable = null;
                    /*if (JWTHelper.isAgent(token)) {
                        observable = StormAPIClient.getAgentDetails(stormId, context);
                    } else if (JWTHelper.isMerchant(token)) {
                        observable = StormAPIClient.getMerchantDetails(stormId, context);
                    }
                    if (observable == null)
                        throw new RuntimeException("Error");*/
                    observable = StormAPIClient.getAgentDetails(stormId, context);
                    return Single.fromObservable(observable);
                })
                .doFinally(this::dismissProgressBar)
                .subscribe(new SingleObserver<Response<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<Object> res) {
                        Object body = res.body();
                        Log.e(tag, "on success called " + body);
                        try {

                            JSONObject payload = new JSONObject(new Gson().toJson(body));
                            //Toast.makeText(context, payload.toString(), Toast.LENGTH_SHORT).show();
                            if (payload.length() < 1) {
                                throw new Exception("Error");
                            }
                            Log.i(tag, "payload" + payload);

                            User user = new User();
                            user.setAccount_number(payload.optString("bankAccountNumber", ""));
                            user.setBank(payload.optString("bankName", ""));
                            user.setBvn(payload.optString("bvnNumber", ""));
                            user.setIs_verified(payload.getBoolean("activated"));
                            user.setBusiness_name(payload.getString("businessName"));
                            user.setEmail(payload.getString("username"));
                            user.setName(payload.getString("businessName"));
                            user.setNetplus_id(payload.getString("stormId"));
                            user.setTerminal_id(payload.has("terminalId") ? payload.getString("terminalId") : "");
                            user.setPhone(payload.optString("phoneNumber", ""));
                            String token = SharedPrefManager.getUserToken();

                            /*if (user_type == USER_TYPE_AGENT && JWTHelper.isAgent(token)) {
                                user.setUser_type(USER_TYPE_AGENT);
                            }

                            if (user_type == USER_TYPE_MERCHANT && JWTHelper.isMerchant(token)) {
                                user.setUser_type(USER_TYPE_MERCHANT);
                            }*/
                            if (SharedPrefManager.getLastLoggedInUser() != null && SharedPrefManager.getLastLoggedInUser().equals(user.getNetplus_id())) {
                                user.setUser_type(USER_TYPE_NONE);
                            } else if (SharedPrefManager.getLastLoggedInUser() != null && SharedPrefManager.getLastLoggedInUser().equals(user.getNetplus_id())) {
                                user.setUser_type(SharedPrefManager.getUserType());
                            } else
                                user.setUser_type(USER_TYPE_NONE);

                            SharedPrefManager.setUser(user);
                            SharedPrefManager.setLoginStatus(true);
                            Log.d(tag, "user type " + user.getUser_type());
                            dismissProgressBar();
                            SharedPrefManager.setLoginStatus(true);

                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(tag, e.getLocalizedMessage());
                            //Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "Unexpected error occured", Toast.LENGTH_LONG).show();
                            SharedPrefManager.setUserToken(null);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressBar();
                        Log.e(tag, "login error occurred", e);
                        SharedPrefManager.setUserToken(null);
                        if (e.getMessage().equals("failed")) {
                            Toast.makeText(context, "Authentication failed, Please check your login credentials and try again.", Toast.LENGTH_LONG).show();
                            return;
                        } else if (e.getMessage().equals("Error")) {
                            Toast.makeText(context, "An unexpected error occurred, please try again later.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(context, "Please try again and ensure you're connected", Toast.LENGTH_LONG).show();
                    }
                });
        /*.subscribe(new io.reactivex.Observer<Response<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<Object> res) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        dismissProgressBar();
                        SharedPrefManager.setLoginStatus(true);

                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });*/
    }

}
