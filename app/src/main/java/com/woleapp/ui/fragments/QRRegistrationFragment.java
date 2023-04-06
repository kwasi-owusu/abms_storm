package com.woleapp.ui.fragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.woleapp.R;
import com.woleapp.databinding.LayoutQrRegistrationBinding;
import com.woleapp.db.Injection;
import com.woleapp.db.LocalCache;
import com.woleapp.db.UserViewModel;
import com.woleapp.model.Business;
import com.woleapp.model.User;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QRRegistrationFragment extends BaseFragment implements View.OnClickListener, Animation.AnimationListener
{



    Context context;
    private LayoutQrRegistrationBinding binding;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    int user_type = 1;
    boolean freezeButtons = false;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private UserViewModel mViewModel;
    Utilities utilities;
    Drawable customErrorDrawable;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        user = SharedPrefManager.getUser();
        mViewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(getActivity()))
                .get(UserViewModel.class);
        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
        //getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        binding.etPwd.setTypeface(Typeface.DEFAULT);
//        binding.etPwd.setTransformationMethod(new PasswordTransformationMethod());

        binding.tvSignUp.setOnClickListener(this);
        //loadAnimations();

        //binding.spnBank.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        setSpinner();

        binding.etPwd.setTypeface(Typeface.DEFAULT);
        binding.etPwd.setTransformationMethod(new PasswordTransformationMethod());

        binding.etConfirmPwd.setTypeface(Typeface.DEFAULT);
        binding.etConfirmPwd.setTransformationMethod(new PasswordTransformationMethod());


        binding.etPwd.setTypeface(Typeface.DEFAULT);
        binding.etPwd.setTransformationMethod(new PasswordTransformationMethod());

        binding.etConfirmPwd.setTypeface(Typeface.DEFAULT);
        binding.etConfirmPwd.setTransformationMethod(new PasswordTransformationMethod());
        setListeners();

    }

    public void setSpinner()
    {
        /*String[] stringArray = context.getResources().getStringArray(R.array.banks);
        //showPopupQuestions(stringArray);
        ArrayAdapter adapter = new ArrayAdapter<>(context,
                R.layout.spinner_view,stringArray);
        adapter.setDropDownViewResource(R.layout.spinner_view_drop_down);
        binding.layoutAgent.spnBank.setAdapter(adapter);*/

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.states, R.layout.spinner_view_state);
        adapter.setDropDownViewResource(R.layout.item_drop_down_state);
        binding.spnState.setPrompt(context.getResources().getString(R.string.hint_select_state));

        binding.spnState.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_state,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        getActivity()));
    }

    public void setListeners()
    {
        binding.btnSubmit.setOnClickListener(this);


        /*binding.layoutMerchant.etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    binding.layoutMerchant.textInputPwd.setPasswordVisibilityToggleEnabled(true);
                    binding.layoutMerchant.textInputPwd.setHint(context.getResources().getString(R.string.hint_password));
                }
                else
                {
                    //binding.textInputPwd.setPasswordVisibilityToggleEnabled(false);
                    if(TextUtils.isEmpty(binding.layoutMerchant.etPwd.getText()))
                    {
                        binding.layoutMerchant.etPwd.setError(null);
                        binding.layoutMerchant.textInputPwd.setPasswordVisibilityToggleEnabled(false);
                        binding.layoutMerchant.etPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_pwd, 0);
                        binding.layoutMerchant.textInputPwd.setHint(context.getResources().getString(R.string.hint_create_password));

                    }
                    else
                    {
                        binding.layoutMerchant.textInputPwd.setPasswordVisibilityToggleEnabled(true);
                        binding.layoutMerchant.textInputPwd.setHint(context.getResources().getString(R.string.hint_password));
                    }
                    //binding.textInputPwd.setHint(context.getResources().getString(R.string.hint_enter_password));//

                    //editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawableRight, 0);
                    //binding.textInputPwd.setDrawa
                }
            }
        });

        binding.layoutMerchant.etConfirmPwd.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    binding.layoutMerchant.textInputConfirmPwd.setPasswordVisibilityToggleEnabled(true);
                    binding.layoutMerchant.textInputConfirmPwd.setHint(context.getResources().getString(R.string.hint_password));
                }
                else
                {
                    //binding.textInputPwd.setPasswordVisibilityToggleEnabled(false);
                    if(TextUtils.isEmpty(binding.layoutMerchant.etConfirmPwd.getText()))
                    {
                        binding.layoutMerchant.etConfirmPwd.setError(null);
                        binding.layoutMerchant.textInputConfirmPwd.setPasswordVisibilityToggleEnabled(false);
                        binding.layoutMerchant.etConfirmPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_pwd, 0);
                        binding.layoutMerchant.textInputConfirmPwd.setHint(context.getResources().getString(R.string.hint_re_enter_password));
                    }
                    else
                    {
                        binding.layoutMerchant.textInputConfirmPwd.setPasswordVisibilityToggleEnabled(true);
                        binding.layoutMerchant.textInputConfirmPwd.setHint(context.getResources().getString(R.string.hint_re_enter_password));
                    }
                    //binding.textInputPwd.setHint(context.getResources().getString(R.string.hint_enter_password));//

                    //editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawableRight, 0);
                    //binding.textInputPwd.setDrawa
                }
            }
        });*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //user = SharedPrefManager.getInstance(context).getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_qr_registration, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
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
    public void onClick(View v) {


        /*if(v == binding.tvSignUp)
        {
            showFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
        }*/

        if(v==binding.btnSubmit)
        {
            validateInputsAndProceed();
        }


        /*if(v == binding.btnAgent)
        {
            binding.btnAgent.setBackgroundResource(R.drawable.ripple_theme);
            binding.btnMerchant.setBackgroundResource(R.drawable.ripple_gray);
        }
        else if(v == binding.btnMerchant)
        {
            binding.btnAgent.setBackgroundResource(R.drawable.ripple_gray);
            binding.btnMerchant.setBackgroundResource(R.drawable.ripple_theme);
        }*/
    }

//    private void gotoLoginScreen()
//    {
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        getActivity().finish();
//    }


//    public void alphaShowView(View view,View hideView)
//    {
//        Animation show = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_show);
//        Animation hide = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);//slide_out_right
//
//        hide.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                currentHighlightedView.setAlpha(1.0f);
//                currentHighlightedView = view;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
////        view.setAnimation(show);
//
////        Animation hide = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_hide);
////        currentHighlightedView.setAnimation(hide);
//
////        Animation fadeIn = new AlphaAnimation(1, 0);
////        fadeIn.setInterpolator(new AccelerateInterpolator());
////        fadeIn.setStartOffset(500);
////        fadeIn.setDuration(1000);
//
//        show.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation)
//            {
//                view.setAlpha(1.0f);
////                Animation hide = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);//slide_out_right
//                currentHighlightedView.setAnimation(hide);
//                //currentHighlightedView = view;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        //tvLogo.setAnimation(fadeIn);
//
//        view.startAnimation(show);
//    }


    /*public void startAnim(final int a)
    {
        AnimatorSet mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.card_flip_left_out);
        AnimatorSet mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.card_flip_right_in);
        //Animation show = AnimationUtils.loadAnimation(getActivity(), R.anim.card_flip_left_out);
        mSetRightOut.setTarget(binding.linearFields);


//        mSetLeftIn.setTarget(binding.linearFields);
//        mSetLeftIn.start();

        mSetRightOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(a==1)
                {
                    binding.textInputName.setVisibility(View.VISIBLE);
                    binding.linearAgentView.setVisibility(View.VISIBLE);
                    binding.linearMerchantView.setVisibility(View.GONE);
                }
                else
                {
                    binding.textInputName.setVisibility(View.GONE);
                    binding.linearAgentView.setVisibility(View.GONE);
                    binding.linearMerchantView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                *//*if(a==1)
                {
                    binding.textInputName.setVisibility(View.VISIBLE);
                    binding.linearAgentView.setVisibility(View.VISIBLE);
                    binding.linearMerchantView.setVisibility(View.GONE);
                }
                else
                {
                    binding.textInputName.setVisibility(View.GONE);
                    binding.linearAgentView.setVisibility(View.GONE);
                    binding.linearMerchantView.setVisibility(View.VISIBLE);
                }*//*
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mSetRightOut.start();

        *//*mSetRightOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {

                if(a==1)
                {
                    binding.textInputName.setVisibility(View.VISIBLE);
                    binding.linearAgentView.setVisibility(View.VISIBLE);
                    binding.linearMerchantView.setVisibility(View.GONE);
                }
                else
                {
                    binding.textInputName.setVisibility(View.GONE);
                    binding.linearAgentView.setVisibility(View.GONE);
                    binding.linearMerchantView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*//*
        //binding.linearFields.setAnimation(show);


    }*/


    /*private void showViewMerchant()
    {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        binding.linearMerchantView.setAlpha(0f);
        binding.linearMerchantView.setVisibility(View.VISIBLE);

        long duration =  getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        binding.linearMerchantView.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        binding.linearAgentView.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.linearAgentView.setVisibility(View.GONE);
                    }
                });
    }*/

    /*private void showViewAgent()
    {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        binding.linearAgentView.setAlpha(0f);
        binding.linearAgentView.setVisibility(View.VISIBLE);

        long duration =  getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        binding.linearAgentView.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        binding.linearMerchantView.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.linearMerchantView.setVisibility(View.GONE);
                    }
                });
    }*/

    public void validateInputsAndProceed()
    {
        String name = binding.etName.getText().toString().trim();
        //String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPwd.getText().toString().trim();
        String confirm_password = binding.etConfirmPwd.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();
        String address = binding.etAddress.getText().toString().trim();
        //String acc_no = binding.etAccNumber.getText().toString().trim();
        //String bvn_no = binding.etBVNNumber.getText().toString().trim();
        //int selected_bank = binding.spnBank.getSelectedItemPosition();
        int selected_state = binding.spnState.getSelectedItemPosition();
        Log.e("selected_state_pos: ",selected_state+"---");
        if (TextUtils.isEmpty(name))
        {
            binding.etName.setError("Business name is required",customErrorDrawable);
            binding.etName.requestFocus();
        }
        else if (TextUtils.isEmpty(address))
        {
            binding.etAddress.setError("Business address is required",customErrorDrawable);
            binding.etAddress.requestFocus();
        }
        else if(TextUtils.isEmpty(password))
        {
            binding.etPwd.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            binding.etPwd.setError("Password is required",customErrorDrawable);
            binding.etPwd.requestFocus();
        }
        else if(TextUtils.isEmpty(confirm_password))
        {
            binding.etConfirmPwd.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            binding.etConfirmPwd.setError("Password is required",customErrorDrawable);
            binding.etConfirmPwd.requestFocus();
        }
        else if(!TextUtils.equals(password,confirm_password))
        {
            binding.etConfirmPwd.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            binding.etConfirmPwd.setError("Passwords do not match",customErrorDrawable);
            binding.etConfirmPwd.requestFocus();
        }
        else if(TextUtils.isEmpty(phone))
        {
            binding.etPhone.setError("Mobile number is required",customErrorDrawable);
            binding.etPhone.requestFocus();
        }
        else if(phone.length()<9)
        {
            binding.etPhone.setError("Invalid mobile number",customErrorDrawable);
            binding.etPhone.requestFocus();
        }
            /*else if(TextUtils.isEmpty(acc_no))
            {
                binding.layoutAgent.etAccNumber.setError("Acccount number is required",customErrorDrawable);
                binding.layoutAgent.etAccNumber.requestFocus();
            }
            else if(acc_no.length()<10)
            {
                binding.layoutAgent.etAccNumber.setError("Invalid Acccount number",customErrorDrawable);
                binding.layoutAgent.etAccNumber.requestFocus();
            }*/
            /*else if(selected_bank<=0) //<0
            {
                //TextView errorText = (binding.spnBank.getVie().findViewById(android.R.id.text1));
                TextView errorText = (TextView)binding.layoutAgent.spnBank.getRootView().findViewById(android.R.id.text1);
                errorText.setError("Bank name is required",customErrorDrawable);
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Bank name is required");//changes the selected item text to this
            }*/
        else if(selected_state<=0) //<0
        {
            //TextView errorText = (binding.spnBank.getVie().findViewById(android.R.id.text1));
            TextView errorText = binding.spnState.getRootView().findViewById(android.R.id.text1);
            errorText.setError("Required",customErrorDrawable);
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Required");//changes the selected item text to this
            Toast.makeText(context,"Please select a State",Toast.LENGTH_SHORT).show();
        }
            /*else if(TextUtils.isEmpty(bvn_no))
            {
                binding.layoutAgent.etBVNNumber.setError("BVN number is required",customErrorDrawable);
                binding.layoutAgent.etBVNNumber.requestFocus();
            }
            else if(bvn_no.length()<10)
            {
                binding.layoutAgent.etBVNNumber.setError("Invalid BVN number",customErrorDrawable);
                binding.layoutAgent.etBVNNumber.requestFocus();
            }*/
        else
        {

            String state = binding.spnState.getSelectedItem().toString();

            Business business = new Business();
            business.setUser_id(user.getUser_id());
            business.setBusiness_name(name);
            business.setBusiness_address(address);
            business.setPhone(phone);
            business.setPassword(password);
            business.setBusiness_state(state);

            LocalCache localCache = Injection.provideCacheForBusinessInfo(context);
            localCache.insertBusinessInfo(business, new LocalCache.InsertCallback() {
                @Override
                public void insertFinished(Long result) {
                    if(result>0)
                    {
                        user.setBusiness_info(business);
                        updateUser();
                    }
                    else
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(context,"Failed to update your information. Please try again later.",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });


            /*if (ConnectivityReceiver.isConnected())
            {

            }*/
        }
    }

    public void updateUser()
    {
        user.setQRRegistered(true);
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

                    /*Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();*/

                    Intent broadcast = new Intent("UpdateUser");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(broadcast);

                    //replacefragmentWithAddStack(R.id.container_main,new AccountActivationFragment(),AccountActivationFragment.class.getSimpleName());

                    getActivity().getSupportFragmentManager().popBackStack();
                    addFragmentWithoutRemove(R.id.container_main,new AccountActivationFragment(),QRRegistrationFragment.class.getSimpleName());
                    //addFragmentWithRemove(R.id.container_main,new AccountActivationFragment(),QRRegistrationFragment.class.getSimpleName());
                }
                else
                {
                    user.setQRRegistered(false);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context,"Failed to update your information. Please try again later.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };

        Log.e("UserIdToBeUpdated",user.getUser_id()+"--");
        mViewModel.updateUser(user.getUser_id(),callback);
    }

    public boolean isValidEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.in_animation);


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
}
