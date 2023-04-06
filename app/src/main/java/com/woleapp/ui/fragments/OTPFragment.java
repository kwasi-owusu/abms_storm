package com.woleapp.ui.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.woleapp.R;
import com.woleapp.databinding.LayoutVerifyOtpBinding;
import com.woleapp.db.LocalCache;
import com.woleapp.db.UserViewModel;
import com.woleapp.model.User;
import com.woleapp.network.StormAPIClient;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.ui.activity.MainActivity;
import com.woleapp.util.*;

import org.json.JSONException;
import org.json.JSONObject;


public class OTPFragment extends BaseFragment implements View.OnClickListener
{
    Context context;
    private LayoutVerifyOtpBinding binding;
    User user;
    //CountDownTimer timer;
    public long mLastClickTime =0;
    private UserViewModel mViewModel;
    Utilities utilities;
    JSONObject map;
    String id="",mobile_no="";
    String[] otp = new String[4];
    ProgressDialog mProgressDialog;
    String otp_received = "";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
        user = SharedPrefManager.getUser();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle b1 = getArguments();
        setListeners();
        if(b1!=null)
        {
            try {
                map = new JSONObject(b1.getString("data"));
                id = map.getString("stormId");
                otp_received = map.getString("otp");

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        //initTimer();// Nov 14,2019

    }

    public void populateOTP(String otp)
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!TextUtils.isEmpty(otp))
                {
                    binding.etOtp1.setText(String.valueOf(otp.charAt(0)));
                    binding.etOtp2.setText(String.valueOf(otp.charAt(1)));
                    binding.etOtp3.setText(String.valueOf(otp.charAt(2)));
                    binding.etOtp4.setText(String.valueOf(otp.charAt(3)));
                    binding.etOtp4.setSelection(1);
                    binding.etOtp4.requestFocus();
                    //timer.cancel();

                    binding.tvTimer.setText("00:00");
                    rotate(false);


                    if (ConnectivityReceiver.isConnected())
                    {
                        validateAndProceed();
                    }
                    else
                    {
                        //getActivity().getSupportFragmentManager().popBackStack();
                        utilities.showDialogNoNetwork("You need an active internet connection to proceed. Would you like to enable it ?",getActivity());
                    }

                }
            }
        },3000);
    }

    public void showDialogSuccess()
    {
        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_otp_success);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.semi_transparent_gray)));

        ImageView imageView =  dialog2.findViewById(R.id.imageView);
        ((Animatable) imageView.getDrawable()).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                dialog2.dismiss();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        },2000);

        dialog2.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog2.show();
    }

    public void rotate(boolean start)
    {
        Animation aniRotate = AnimationUtils.loadAnimation(context,R.anim.rotate_clockwise);
        binding.ivTimer.startAnimation(aniRotate);
        aniRotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                if(start)
                {
                    binding.ivTimer.setRotation(180.0f);
                }
                else
                {
                    binding.ivTimer.setRotation(0.0f);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //user = SharedPrefManager.getInstance(context).getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_verify_otp, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
    }

    /*@RequiresApi(api = Build.VERSION_CODES.N)
    public void initChronometer()
    {
        binding.chronometer.setVisibility(View.VISIBLE);
        binding.chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer cArg) {
                //homeMapBinding.chronometer = chronometerChanged;
                long t = SystemClock.elapsedRealtime() - cArg.getBase();
                Log.e("Base",cArg.getBase()+"--");
                Log.e("elapsedRealtime",SystemClock.elapsedRealtime()+"--");
                //long t =  cArg.getBase();
                binding.chronometer.setText(DateFormat.format("kk:mm:ss", t));//kk:mm:ss
            }
        });
//        binding.chronometer.setBase(0l);//SystemClock.elapsedRealtime()
        binding.chronometer.setBase(SystemClock.elapsedRealtime()- 60*1000);
        binding.chronometer.setText("01:00");
        binding.chronometer.setCountDown(true);
        binding.chronometer.start();
    }*/

    public void showProgressBar()
    {
        mProgressDialog = ProgressDialog.show(context, null, null);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ffffff")));
        mProgressDialog.setCancelable(false);

        /*if (mProgressDialog!=null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
        }*/
    }

    public void dismissProgressBar()
    {
        if (mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
    public void initTimer()
    {
        binding.linearResend.setVisibility(View.GONE);
        rotate(true);

        /*timer =  new CountDownTimer(60000, 1000)
        {

            public void onTick(long millisUntilFinished) {
                ///  mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext

                String time ="00:";
                if ((millisUntilFinished / 1000)>=10){
                    time ="00:"+(millisUntilFinished / 1000);
                }else{
                    time ="00:0"+(millisUntilFinished / 1000);
                }


                binding.tvTimer.setText(time);
                //binding.tvResend.setClickable(false);
                //binding.tvResend.setEnabled(false);

            }

            public void onFinish() {
                //binding.linearResend.setVisibility(View.VISIBLE);
                binding.tvTimer.setText("00:00");
                rotate(false);
                //mTextField.setText("done!");

                binding.tvResend.setClickable(true);
                binding.tvResend.setEnabled(true);

            }

        }.start();*/
    }


    public void setListeners()
    {
        binding.btnContinue.setOnClickListener(this);
        binding.tvResend.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.btnVerify.setOnClickListener(this);

        binding.etOtp4.setOnEditorActionListener(editorActionListener);

        addTextChangedListener(binding.etOtp1);
        addTextChangedListener(binding.etOtp2);
        addTextChangedListener(binding.etOtp3);
        addTextChangedListener(binding.etOtp4);
    }

    TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener()
    {
        @Override
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event)
        {
            switch (view.getId())
            {
                case R.id.et_Otp_1:
                    if (actionId == EditorInfo.IME_ACTION_NEXT)
                    {
                        binding.etOtp2.requestFocus();
                    }
                    break;
                case R.id.et_Otp_2:
                    if (actionId == EditorInfo.IME_ACTION_NEXT)
                    {
                        binding.etOtp3.requestFocus();
                    }
                    break;
                case R.id.et_Otp_3:
                    if (actionId == EditorInfo.IME_ACTION_NEXT)
                    {
                        binding.etOtp4.requestFocus();
                    }
                    break;
                case R.id.et_Otp_4:
                    if (actionId == EditorInfo.IME_ACTION_DONE)
                    {
                        utilities.hideKeyboard(getActivity());
                        validateAndProceed();
                    }
                    break;
            }
            /*if (actionId == EditorInfo.IME_ACTION_DONE) {
            //run query to the server
                String query = searchEditText.getText().toString().trim();
                Log.e("onClick: ", "-- " + query);
                updateRepoListFromInput(query,true);
            }*/
            return false;
        }
    };

    public void addTextChangedListener(EditText text)
    {
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length()>0)
                {
                    switch (text.getId())
                    {
                        case R.id.et_Otp_1:
                            otp[0] = s.toString();
                            binding.etOtp2.requestFocus();
                            break;
                        case R.id.et_Otp_2:
                            otp[1] = s.toString();
                            binding.etOtp3.requestFocus();
                            break;
                        case R.id.et_Otp_3:
                            otp[2] = s.toString();
                            binding.etOtp4.requestFocus();
                            break;
                        case R.id.et_Otp_4:
                            otp[3] = s.toString();
                            break;
                    }

                }
                else
                {
                    switch (text.getId())
                    {
                        case R.id.et_Otp_1:
                            otp[0] = "";
                            break;
                        case R.id.et_Otp_2:
                            otp[1] = "";
                            break;
                        case R.id.et_Otp_3:
                            otp[2] = "";
                            break;
                        case R.id.et_Otp_4:
                            otp[3] = "";
                            break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        //inflater.inflate(R.menu.menu_fragment, menu);
//        MenuItem searchMenu = menu.findItem(R.id.action_edit);
//        searchMenu.setVisible(true);
        //MenuItem delete = menu.findItem(R.id.action_delete);
//        if(showDeleteMenu)
//        {
//            delete.setVisible(true);
//        }
//        else
//        {
//            delete.setVisible(false);
//        }

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
    public void onClick(View v)
    {
        if(v==binding.btnVerify)
        {
            StringBuilder b1 = new StringBuilder();

            String o1  = binding.etOtp1.getText().toString();
            String o2  = binding.etOtp2.getText().toString();
            String o3  = binding.etOtp3.getText().toString();
            String o4  = binding.etOtp4.getText().toString();

            String otp = b1.append(o1).append(o2).append(o3).append(o4).toString();
            if(otp.length()==4)
            {
                if(otp.equals(otp_received))
                {
                    //binding.tvVerification.setText("Verified");
                    showDialogSuccess();
                }
                else
                {
                    Toast.makeText(context,"The OTP you have entered is incorrect. Please enter the one received on your registered Email address",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(context,"The OTP you have entered is incorrect. Please enter the one received on your registered Email address",Toast.LENGTH_LONG).show();
            }
        }
        else if(v==binding.btnContinue)
        {
            validateAndProceed();

        }
        else if(v==binding.tvResend)
        {
            try {

                binding.etOtp1.getText().clear();
                binding.etOtp2.getText().clear();
                binding.etOtp3.getText().clear();
                binding.etOtp4.getText().clear();
                binding.etOtp4.clearFocus();

                binding.linearResend.setVisibility(View.GONE);
                rotate(true);
                String otp_received = map.getString("otp");
                populateOTP(otp_received);

            } catch (Exception ex) {
                Toast.makeText(context,"An unexpected error occurred.",Toast.LENGTH_LONG).show();
            }

            /*binding.tvTitleOtp.setVisibility(View.GONE);
            binding.tvResend.setVisibility(View.GONE);*/
            //timer.start();
        }
        else if(v==binding.ivBack)
        {
            getActivity().getSupportFragmentManager().popBackStack();
        }

    }


    public void verifyAndProceed()
    {
        boolean isValid = true;
        for(int i=0;i<4;i++)
        {
            if(TextUtils.isEmpty(otp[i]))
            {
                isValid = false;
                break;
            }
        }
        if(isValid)
        {
            SharedPrefManager.setLoginStatus(true);
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
        else
        {
            Toast.makeText(context,"The OTP you've entered seems incorrect/incomplete.Please enter the OTP you received via text message",Toast.LENGTH_LONG).show();
        }
    }


    public void validateAndProceed()
    {

        boolean isValid = true;
        for(int i=0;i<4;i++)
        {
            if(TextUtils.isEmpty(otp[i]))
            {
                isValid = false;
                break;
            }
        }
        if(isValid)
        {
            /*String otp1 = otp.toString();
            Log.e("OTP_ENTERED",otp1+"---");*/
            StringBuilder s1 = new StringBuilder();

            s1.append(otp[0]);
            s1.append(otp[1]);
            s1.append(otp[2]);
            s1.append(otp[3]);
            String otp1 = s1.toString();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("otp",otp1);
            jsonObject.addProperty("id",id);
            verifyUser(jsonObject);

            /*SharedPrefManager.getInstance(context).setLoginStatus(true);
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();*/
        }
        else
        {
            Toast.makeText(context,"The OTP you've entered seems incorrect/incomplete.Please enter the OTP received on your registered Email address",Toast.LENGTH_LONG).show();
        }

        /*String o1 = binding.etOtp1.getText().toString().trim();
        String o2 = binding.etOtp2.getText().toString().trim();
        String o3 = binding.etOtp3.getText().toString().trim();
        String o4 = binding.etOtp4.getText().toString().trim();
        StringBuilder s1 = new StringBuilder();

        s1.append(o1);
        s1.append(o2);
        s1.append(o3);
        s1.append(o4);

        if(s1.length()==4)
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("otp",otp);
            if(user_type== Constants.USER_TYPE_AGENT)
            {
                jsonObject.addProperty("agent_id",id);
                verifyAgent(jsonObject);
            }
            else
            {
                jsonObject.addProperty("Merchant_id",id);
                verifyMerchant(jsonObject);
            }
            *//*String otp = s1.toString();
            if(otp.equals("1234"))
            {
                return true;
            }
            else
            {
                utilities.showAlertDialogOk("You have entered an incorrect OTP. Please enter the OTP received on your registered Mobile number");//Email address/ Mobile number
            }*//*
        }
        else
        {
            if(user.getUser_type()==1)
            {
                utilities.showAlertDialogOk("You have entered an incorrect OTP. Please enter the OTP received on your registered  Mobile number");//Email address/
            }
            else
            {
                utilities.showAlertDialogOk("You have entered an incomplete OTP. Please enter the OTP received on your registered Email address.");
            }

        }*/
        //return false;
    }
    public void updateUser()
    {
        user.setIs_verified(true);
        LocalCache.UpdateCallback callback = new LocalCache.UpdateCallback()
        {
            @Override
            public void updateFinished(int result)
            {
                if(result>0)
                {
                    //user.setIs_verified(true);
                    SharedPrefManager.setUser(user);
                    //Toast.makeText(context,"Registration successful",Toast.LENGTH_SHORT).show();

                    //showFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();
                    //showFragment(new OTPFragment(),OTPFragment.class.getSimpleName());
                    //addFragmentWithoutRemove(R.id.container_main,new OTPFragment(), OTPFragment.class.getSimpleName());
                }
                else
                {
                    user.setIs_verified(false);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context,"Failed to update your information. Please try again later.",Toast.LENGTH_SHORT).show();
                        }
                    });
                    /*getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            utilities.showAlertDialogOk("Record already exists. Please login instead.");
                        }
                    });*/

                }
            }
        };

        Log.e("UserIdToBeUpdated",user.getUser_id()+"--");
        mViewModel.updateUser(user.getUser_id(),callback);
    }

    private OnItemClickListener.OnItemClickCallback onClick = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {


        }
    };

    public void verifyUser(JsonObject jsonObject)
    {

        showProgressBar();
        String appToken = SharedPrefManager.getAppToken();
        StormAPIClient.verifyUser(jsonObject, context)
                .subscribe(res -> {

                    if(res.code() != 201) {
                        Toast.makeText(context,"Unable to verify your account at this time, please try again later.",Toast.LENGTH_LONG).show();
                        dismissProgressBar();
                        return;
                    }

                    JSONObject payload = new JSONObject(new Gson().toJson(res.body()));
                    if(!payload.getBoolean("success")) {
                        Toast.makeText(context,"Verification failed, please check opt and try again.",Toast.LENGTH_LONG).show();
                        dismissProgressBar();
                        return;
                    }

                    dismissProgressBar();
                    showDialogSuccess();


                }, t -> {

                    Log.e(getTag(), "An unexpected error occurred",  t);
                    Toast.makeText(context, "An unexpected error occured", Toast.LENGTH_LONG).show();;

                });

    }


}
