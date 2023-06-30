package com.woleapp.ui.fragments;

import android.animation.*;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.woleapp.R;
import com.woleapp.databinding.LayoutSignUpBinding;
import com.woleapp.db.Injection;
import com.woleapp.db.UserViewModel;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.network.StormAPIClient;
import com.woleapp.ui.activity.MainActivity;
import com.woleapp.util.ConnectivityReceiver;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.woleapp.util.Constants.USER_TYPE_AGENT;
import static com.woleapp.util.Constants.USER_TYPE_MERCHANT;


public class SignupFragment extends BaseFragment implements View.OnClickListener, Animation.AnimationListener {


    Context context;
    private LayoutSignUpBinding binding;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    int user_type = USER_TYPE_AGENT;
    boolean freezeButtons = false;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private UserViewModel mViewModel;
    Utilities utilities;
    Drawable customErrorDrawable;
    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
        mViewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(getActivity()))
                .get(UserViewModel.class);
        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
        //getActivity().invalidateOptionsMenu();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.tvSignUp.setOnClickListener(this);
        loadAnimations();
        changeCameraDistance();
        //binding.spnBank.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        setSpinner();

        binding.signUpLayout.etPwd.setTypeface(Typeface.DEFAULT);


        binding.signUpLayout.etPwd.setTypeface(Typeface.DEFAULT);
        binding.signUpLayout.etPwd.setTransformationMethod(new PasswordTransformationMethod());

        binding.signUpLayout.etConfirmPwd.setTypeface(Typeface.DEFAULT);
        binding.signUpLayout.etConfirmPwd.setTransformationMethod(new PasswordTransformationMethod());

        setListeners();
        //getFCMToken();
    }

    public void setSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.banks, R.layout.spinner_view_bank);
        adapter.setDropDownViewResource(R.layout.item_drop_down_bank);
        binding.signUpLayout.spnBank.setPrompt(context.getResources().getString(R.string.hint_choose_bank_caps));

        binding.signUpLayout.spnBank.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_bank,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        getActivity()));
    }

    public void showProgressBar() {
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

    public void setListeners() {
        binding.btnMerchant.setOnClickListener(this);
        binding.btnAgent.setOnClickListener(this);
        binding.tvSignUp.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);

        binding.signUpLayout.etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.signUpLayout.textInputUname.setHint(context.getResources().getString(R.string.hint_email));
                } else {
                    binding.signUpLayout.textInputUname.setHint(context.getResources().getString(R.string.hint_enter_email));

                }
            }
        });

        binding.signUpLayout.etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //binding.layoutMerchant.textInputPwd.setPasswordVisibilityToggleEnabled(true);
                    binding.signUpLayout.textInputPwd.setHint(context.getResources().getString(R.string.hint_password));
                } else {
                    //binding.textInputPwd.setPasswordVisibilityToggleEnabled(false);
                    if (TextUtils.isEmpty(binding.signUpLayout.etPwd.getText())) {
                        binding.signUpLayout.etPwd.setError(null);
                        //binding.layoutMerchant.textInputPwd.setPasswordVisibilityToggleEnabled(false);
                        binding.signUpLayout.etPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_pwd, 0);
                        binding.signUpLayout.textInputPwd.setHint(context.getResources().getString(R.string.hint_create_password));

                    } else {
                        //binding.layoutMerchant.textInputPwd.setPasswordVisibilityToggleEnabled(true);
                        binding.signUpLayout.textInputPwd.setHint(context.getResources().getString(R.string.hint_password));
                    }
                    //binding.textInputPwd.setHint(context.getResources().getString(R.string.hint_enter_password));//

                    //editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawableRight, 0);
                    //binding.textInputPwd.setDrawa
                }
            }
        });

        binding.signUpLayout.etConfirmPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //binding.layoutMerchant.textInputConfirmPwd.setPasswordVisibilityToggleEnabled(true);
                    binding.signUpLayout.textInputConfirmPwd.setHint(context.getResources().getString(R.string.hint_password));
                } else {
                    if (TextUtils.isEmpty(binding.signUpLayout.etConfirmPwd.getText())) {
                        binding.signUpLayout.etConfirmPwd.setError(null);
                        binding.signUpLayout.etConfirmPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_pwd, 0);
                        binding.signUpLayout.textInputConfirmPwd.setHint(context.getResources().getString(R.string.hint_re_enter_password));
                    } else {
                        binding.signUpLayout.textInputConfirmPwd.setHint(context.getResources().getString(R.string.hint_re_enter_password));
                    }

                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_sign_up, container, false);
        return binding.getRoot();
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
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {


        if (v == binding.btnAgent) {
            user_type = USER_TYPE_AGENT;
            binding.btnAgent.setCardElevation(3f);
            binding.btnAgent.setElevation(3f);
            binding.btnAgent.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

            binding.btnMerchant.setCardElevation(8f);
            binding.btnMerchant.setElevation(8f);
            binding.btnMerchant.setCardBackgroundColor(context.getResources().getColor(R.color.text_color_gray_dark));


        } else if (v == binding.btnMerchant) {
            user_type = USER_TYPE_MERCHANT;

            binding.btnMerchant.setCardElevation(3f);
            binding.btnMerchant.setElevation(3f);
            binding.btnMerchant.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            binding.btnAgent.setCardElevation(8f);
            binding.btnAgent.setElevation(8f);
            binding.btnAgent.setCardBackgroundColor(context.getResources().getColor(R.color.text_color_gray_dark));

        } else if (v == binding.tvSignUp) {
            showFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
        } else if (v == binding.btnLogin) {
            validateInputsAndProceed();
        }

    }

//    public void getFCMToken() {
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(getActivity(), new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String notificationID = instanceIdResult.getToken();
//                Log.e("newToken", notificationID + "--");
//                SharedPrefManager.saveDeviceToken(notificationID);
//
//
//            }
//        });
//
//    }

    public void validateInputsAndProceed() {
        String businessName = binding.signUpLayout.etName.getText().toString().trim();
        String email = binding.signUpLayout.etEmail.getText().toString().trim();
        String password = binding.signUpLayout.etPwd.getText().toString().trim();
        String confirm_password = binding.signUpLayout.etConfirmPwd.getText().toString().trim();
        String phone = binding.signUpLayout.etPhone.getText().toString().trim();
        String acc_no = binding.signUpLayout.etAccNumber.getText().toString().trim();
        String bvn_no = binding.signUpLayout.etBVNNumber.getText().toString().trim();
        int selected_bank = binding.signUpLayout.spnBank.getSelectedItemPosition();

        Log.e("selected_bank_pos: ", selected_bank + "---");
        if (TextUtils.isEmpty(businessName)) {

            binding.signUpLayout.etName.setError("Business name is required", customErrorDrawable);
            binding.signUpLayout.etName.requestFocus();

        } else if (TextUtils.isEmpty(email)) {

            binding.signUpLayout.etEmail.setError("Email is required", customErrorDrawable);
            binding.signUpLayout.etEmail.requestFocus();

        } else if (!isValidEmail(email)) {

            binding.signUpLayout.etEmail.setError("Invalid Email", customErrorDrawable);
            binding.signUpLayout.etEmail.requestFocus();

        } else if (password.length() < 6 || password.length() > 12)//TextUtils.getTrimmedLength(password)
        {

            binding.signUpLayout.etPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            binding.signUpLayout.etPwd.setError("Password should be 6-12 characters long", customErrorDrawable);
            binding.signUpLayout.etPwd.requestFocus();

        } else if (TextUtils.isEmpty(password)) {

            binding.signUpLayout.etPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            binding.signUpLayout.etPwd.setError("Password is required", customErrorDrawable);
            binding.signUpLayout.etPwd.requestFocus();

        } else if (TextUtils.isEmpty(confirm_password)) {

            binding.signUpLayout.etConfirmPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            binding.signUpLayout.etConfirmPwd.setError("Password is required", customErrorDrawable);
            binding.signUpLayout.etConfirmPwd.requestFocus();

        } else if (!TextUtils.equals(password, confirm_password)) {

            binding.signUpLayout.etConfirmPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            binding.signUpLayout.etConfirmPwd.setError("Passwords do not match", customErrorDrawable);
            binding.signUpLayout.etConfirmPwd.requestFocus();

        } else if (TextUtils.isEmpty(phone)) {

            binding.signUpLayout.etPhone.setError("Mobile number is required", customErrorDrawable);
            binding.signUpLayout.etPhone.requestFocus();

        } else if (phone.length() < 9) {

            binding.signUpLayout.etPhone.setError("Invalid mobile number", customErrorDrawable);
            binding.signUpLayout.etPhone.requestFocus();

        } else if (TextUtils.isEmpty(acc_no)) {

            binding.signUpLayout.etAccNumber.setError("Acccount number is required", customErrorDrawable);
            binding.signUpLayout.etAccNumber.requestFocus();

        } else if (acc_no.length() < 10) {

            binding.signUpLayout.etAccNumber.setError("Invalid Acccount number", customErrorDrawable);
            binding.signUpLayout.etAccNumber.requestFocus();

        } else if (selected_bank <= 0) //<0
        {
            //TextView errorText = (binding.spnBank.getVie().findViewById(android.R.id.text1));
            TextView errorText = binding.signUpLayout.spnBank.getRootView().findViewById(android.R.id.text1);
            errorText.setError("Bank businessName is required", customErrorDrawable);
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Bank businessName is required");//changes the selected item text to this

        } else if (TextUtils.isEmpty(bvn_no)) {

            binding.signUpLayout.etBVNNumber.setError("BVN number is required", customErrorDrawable);
            binding.signUpLayout.etBVNNumber.requestFocus();

        } else if (!isValidBVN(bvn_no)) {

            binding.signUpLayout.etBVNNumber.setError("Invalid BVN number", customErrorDrawable);
            binding.signUpLayout.etBVNNumber.requestFocus();

        } else if (ConnectivityReceiver.isConnected() == false) {

            utilities.showDialogNoNetwork("You need an active internet connection to proceed. Would you like to enable it ?", getActivity());

        } else {

            String bank = binding.signUpLayout.spnBank.getSelectedItem().toString();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("businessName", businessName);
            jsonObject.addProperty("username", email);
            jsonObject.addProperty("password", password);
            jsonObject.addProperty("phoneNumber", phone);
            jsonObject.addProperty("bankAccountNumber", acc_no);
            jsonObject.addProperty("bvnNumber", bvn_no);
            jsonObject.addProperty("bankName", bank);
            JsonArray roles = new JsonArray();

            if (user_type == USER_TYPE_AGENT) {

                roles.add("agent");
                jsonObject.add("roles", roles);

            } else if (user_type == USER_TYPE_MERCHANT) {

                roles.add("merchant");
                jsonObject.add("roles", roles);

            }

            signUp(jsonObject);

        }

    }


    public boolean isValidEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public boolean isValidBVN(String bvn) {
        Pattern VALID_BVN_REGEX = Pattern.compile("^[0-9]{11}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_BVN_REGEX.matcher(bvn);
        return matcher.find();
    }

    private void loadAnimations() {

        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.in_animation);

    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        binding.linearFields.setCameraDistance(scale);

    }


    @Override
    public void onAnimationStart(Animation animation) {
        freezeButtons = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        freezeButtons = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void showDialogSuccess() {
        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_otp_success);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.semi_transparent_gray)));

        ImageView imageView = dialog2.findViewById(R.id.imageView);
        ((Animatable) imageView.getDrawable()).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog2.dismiss();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        }, 2000);

        dialog2.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog2.show();
    }

    public void signUp(JsonObject jsonObject) {
        Log.e("TAG", jsonObject.toString());
        showProgressBar();
        String appToken = SharedPrefManager.getAppToken();
        StormAPIClient
                .registerUser(jsonObject, context)
                .subscribe(res -> {
                    if (mProgressDialog != null && mProgressDialog.isShowing())
                        mProgressDialog.cancel();
                    Object body = res.body();
                    if (res.code() == 500) {
                        //Log.e("TAG", "raw error body:" + res.errorBody().string());
                        JSONObject payload = new JSONObject(res.errorBody().string().trim());
                        String errorMessage = payload.optString("error");
                        if (errorMessage != null && errorMessage.startsWith("Duplicate entry"))
                            errorMessage = "Account with email " + getEmailAddressFromError(errorMessage) + " already exists";
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (res.code() != 201 && res.code() != 200) {
                        Log.e("TAG", "body: " + body);
                        Log.e("TAG", "Message: " + res.message());
                        Log.e("TAG", "Code: " + res.code());
                        //say something
                        Toast.makeText(context, "Unable to register, please try again later.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    JSONObject payload = new JSONObject(new Gson().toJson(body));

                    Bundle b = new Bundle();
                    b.putString("data", payload.toString());
//                    OTPFragment otpFragment = new OTPFragment();
//                    otpFragment.setArguments(b);
//                    addFragmentWithoutRemove(R.id.container_main, otpFragment, OTPFragment.class.getName());
                    dismissProgressBar();
                    showDialogSuccess();

                }, t -> {
                    if (mProgressDialog != null && mProgressDialog.isShowing())
                        mProgressDialog.cancel();
                    Log.e(getTag(), "An unexpected error occurred", t);
                    Toast.makeText(context, "An unexpected error occured", Toast.LENGTH_LONG).show();
                    ;

                });
    }

    private String getEmailAddressFromError(String message) {
        Pattern pattern = Pattern.compile("'(.*?)'");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

}
