//package com.woleapp.ui.fragments;
//
//import android.animation.Animator;
//import android.animation.AnimatorInflater;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.AnimatorSet;
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.os.Build;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.text.method.PasswordTransformationMethod;
//import android.view.*;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.databinding.DataBindingUtil;
//import com.woleapp.R;
//import com.woleapp.databinding.LayoutSignUpBinding;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
//public class SignupFragment_Orig extends BaseFragment implements View.OnClickListener {
//
//
//
//    Context context;
//    private LayoutSignUpBinding binding;
//    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
//    int user_type = 1;
//
//    private AnimatorSet mSetRightOut;
//    private AnimatorSet mSetLeftIn;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//        //getActivity().invalidateOptionsMenu();
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
////        binding.etPwd.setTypeface(Typeface.DEFAULT);
////        binding.etPwd.setTransformationMethod(new PasswordTransformationMethod());
//        binding.tvSignUp.setOnClickListener(this);
//
//        //binding.spnBank.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//
//        String[] stringArray = context.getResources().getStringArray(R.array.banks);
//        //showPopupQuestions(stringArray);
//        ArrayAdapter adapter = new ArrayAdapter<>(context,
//                R.layout.spinner_view,stringArray);
//        binding.spnBank.setAdapter(adapter);
//
//        binding.btnMerchant.setOnClickListener(this);
//        binding.btnAgent.setOnClickListener(this);
//        binding.tvSignUp.setOnClickListener(this);
//        binding.btnLogin.setOnClickListener(this);
//
//
//        binding.etPwd.setTypeface(Typeface.DEFAULT);
//        binding.etPwd.setTransformationMethod(new PasswordTransformationMethod());
//
//        binding.etConfirmPwd.setTypeface(Typeface.DEFAULT);
//        binding.etConfirmPwd.setTransformationMethod(new PasswordTransformationMethod());
//
//        binding.etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener()
//        {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus)
//                {
//                    binding.textInputPwd.setPasswordVisibilityToggleEnabled(true);
//                    binding.textInputPwd.setHint(context.getResources().getString(R.string.hint_password));
//                }
//                else
//                {
//                    //binding.textInputPwd.setPasswordVisibilityToggleEnabled(false);
//                    if(TextUtils.isEmpty(binding.etPwd.getText()))
//                    {
//                        binding.etPwd.setError(null);
//                        binding.textInputPwd.setPasswordVisibilityToggleEnabled(false);
//                        binding.etPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_pwd, 0);
//                        binding.textInputPwd.setHint(context.getResources().getString(R.string.hint_enter_password));
//
//                    }
//                    else
//                    {
//                        binding.textInputPwd.setPasswordVisibilityToggleEnabled(true);
//                        binding.textInputPwd.setHint(context.getResources().getString(R.string.hint_password));
//                    }
//                    //binding.textInputPwd.setHint(context.getResources().getString(R.string.hint_enter_password));//
//
//                    //editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawableRight, 0);
//                    //binding.textInputPwd.setDrawa
//                }
//            }
//        });
//
//        binding.etConfirmPwd.setOnFocusChangeListener(new View.OnFocusChangeListener()
//        {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus)
//                {
//                    binding.textInputConfirmPwd.setPasswordVisibilityToggleEnabled(true);
//                    binding.textInputConfirmPwd.setHint(context.getResources().getString(R.string.hint_password));
//                }
//                else
//                {
//                    //binding.textInputPwd.setPasswordVisibilityToggleEnabled(false);
//                    if(TextUtils.isEmpty(binding.etConfirmPwd.getText()))
//                    {
//                        binding.etConfirmPwd.setError(null);
//                        binding.textInputConfirmPwd.setPasswordVisibilityToggleEnabled(false);
//                        binding.etConfirmPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_pwd, 0);
//                        binding.textInputConfirmPwd.setHint(context.getResources().getString(R.string.hint_enter_password));
//
//                    }
//                    else
//                    {
//                        binding.textInputConfirmPwd.setPasswordVisibilityToggleEnabled(true);
//                        binding.textInputConfirmPwd.setHint(context.getResources().getString(R.string.hint_password));
//                    }
//                    //binding.textInputPwd.setHint(context.getResources().getString(R.string.hint_enter_password));//
//
//                    //editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawableRight, 0);
//                    //binding.textInputPwd.setDrawa
//                }
//            }
//        });
//    }
//
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
//    {
//        context = getActivity();
//        //user = SharedPrefManager.getInstance(context).getUser();
//        binding = DataBindingUtil.inflate(inflater, R.layout.layout_sign_up, container, false);
//        //((GPHMainActivity)getActivity()).createBackButton(job_title);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//
//        menu.clear();
//
////        menu.findItem(R.id.action_edit).setVisible(true);
////        menu.findItem(R.id.action_search).setVisible(false);
//        //menu.removeItem(R.id.action_search);//clear();
//
//        /*if(showDeleteMenu)
//        {
//            menu.findItem(R.id.action_search).setVisible(false);
//            menu.removeItem(R.id.action_search);//clear();
//        }
//        else
//        {
//            menu.clear();
//
////            menu.removeItem(R.id.action_search);//clear();
////            menu.removeItem(R.id.action_search);//clear();
////
////            menu.findItem(R.id.action_search).setVisible(false);
////
////            menu.findItem(R.id.action_search).setVisible(false);
//        }*/
//
//    }
//    @Override
//    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
//        //inflater.inflate(R.menu.menu_fragment, menu);
////        MenuItem searchMenu = menu.findItem(R.id.action_edit);
////        searchMenu.setVisible(true);
//        //MenuItem delete = menu.findItem(R.id.action_delete);
////        if(showDeleteMenu)
////        {
////            delete.setVisible(true);
////        }
////        else
////        {
////            delete.setVisible(false);
////        }
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//
//            /*case R.id.action_edit:
//                mDataBinding.btnUpdate.setVisibility(View.VISIBLE);
//                return true;*/
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void onClick(View v) {
//
//
//        if(v == binding.btnAgent)
//        {
//            if(user_type!=1)
//            {
//                user_type = 1;
//                //showViewAgent();
////                startAnim(1);
//                binding.textInputName.setVisibility(View.VISIBLE);
//                binding.linearAgentView.setVisibility(View.VISIBLE);
//                binding.linearMerchantView.setVisibility(View.GONE);
//            }
//
//            binding.btnAgent.setCardElevation(3f);
//            binding.btnAgent.setElevation(3f);
//            binding.btnAgent.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
//
//            binding.btnMerchant.setCardElevation(8f);
//            binding.btnMerchant.setElevation(8f);
//            binding.btnMerchant.setCardBackgroundColor(context.getResources().getColor(R.color.text_color_gray_dark));
//
////            binding.btnAgent.setBackgroundResource(R.drawable.ripple_theme2);
////            binding.btnMerchant.setBackgroundResource(R.drawable.ripple_gray2);
//        }
//        else if(v == binding.btnMerchant)
//        {
//            if(user_type!=2)
//            {
//                user_type = 2;
//                //showViewMerchant();
//                //startAnim(2);
//                binding.textInputName.setVisibility(View.GONE);
//                binding.linearAgentView.setVisibility(View.GONE);
//                binding.linearMerchantView.setVisibility(View.VISIBLE);
//            }
//            binding.btnMerchant.setCardElevation(3f);
//            binding.btnMerchant.setElevation(3f);
//            binding.btnMerchant.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
//
//            binding.btnAgent.setCardElevation(8f);
//            binding.btnAgent.setElevation(8f);
//            binding.btnAgent.setCardBackgroundColor(context.getResources().getColor(R.color.text_color_gray_dark));
//
////            binding.btnAgent.setBackgroundResource(R.drawable.ripple_gray2);
////            binding.btnMerchant.setBackgroundResource(R.drawable.ripple_theme2);
//        }
//
//        else if(v == binding.tvSignUp)
//        {
//            showFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
//        }
//        else if(v==binding.btnLogin)
//        {
//            validateInputsAndProceed();
//        }
//
//
//        /*if(v == binding.btnAgent)
//        {
//            binding.btnAgent.setBackgroundResource(R.drawable.ripple_theme);
//            binding.btnMerchant.setBackgroundResource(R.drawable.ripple_gray);
//        }
//        else if(v == binding.btnMerchant)
//        {
//            binding.btnAgent.setBackgroundResource(R.drawable.ripple_gray);
//            binding.btnMerchant.setBackgroundResource(R.drawable.ripple_theme);
//        }*/
//    }
//
////    private void gotoLoginScreen()
////    {
////        Intent intent = new Intent(getActivity(), LoginActivity.class);
////        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////        startActivity(intent);
////        getActivity().finish();
////    }
//
//
////    public void alphaShowView(View view,View hideView)
////    {
////        Animation show = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_show);
////        Animation hide = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);//slide_out_right
////
////        hide.setAnimationListener(new Animation.AnimationListener() {
////            @Override
////            public void onAnimationStart(Animation animation) {
////
////            }
////
////            @Override
////            public void onAnimationEnd(Animation animation) {
////                currentHighlightedView.setAlpha(1.0f);
////                currentHighlightedView = view;
////            }
////
////            @Override
////            public void onAnimationRepeat(Animation animation) {
////
////            }
////        });
////
//////        view.setAnimation(show);
////
//////        Animation hide = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_hide);
//////        currentHighlightedView.setAnimation(hide);
////
//////        Animation fadeIn = new AlphaAnimation(1, 0);
//////        fadeIn.setInterpolator(new AccelerateInterpolator());
//////        fadeIn.setStartOffset(500);
//////        fadeIn.setDuration(1000);
////
////        show.setAnimationListener(new Animation.AnimationListener() {
////            @Override
////            public void onAnimationStart(Animation animation) {
////
////            }
////
////            @Override
////            public void onAnimationEnd(Animation animation)
////            {
////                view.setAlpha(1.0f);
//////                Animation hide = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);//slide_out_right
////                currentHighlightedView.setAnimation(hide);
////                //currentHighlightedView = view;
////            }
////
////            @Override
////            public void onAnimationRepeat(Animation animation) {
////
////            }
////        });
////        //tvLogo.setAnimation(fadeIn);
////
////        view.startAnimation(show);
////    }
//
//
//    public void startAnim(final int a)
//    {
//        AnimatorSet mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.card_flip_left_out);
//        AnimatorSet mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.card_flip_right_in);
//        //Animation show = AnimationUtils.loadAnimation(getActivity(), R.anim.card_flip_left_out);
//        mSetRightOut.setTarget(binding.linearFields);
//
//
////        mSetLeftIn.setTarget(binding.linearFields);
////        mSetLeftIn.start();
//
//        mSetRightOut.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                if(a==1)
//                {
//                    binding.textInputName.setVisibility(View.VISIBLE);
//                    binding.linearAgentView.setVisibility(View.VISIBLE);
//                    binding.linearMerchantView.setVisibility(View.GONE);
//                }
//                else
//                {
//                    binding.textInputName.setVisibility(View.GONE);
//                    binding.linearAgentView.setVisibility(View.GONE);
//                    binding.linearMerchantView.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                /*if(a==1)
//                {
//                    binding.textInputName.setVisibility(View.VISIBLE);
//                    binding.linearAgentView.setVisibility(View.VISIBLE);
//                    binding.linearMerchantView.setVisibility(View.GONE);
//                }
//                else
//                {
//                    binding.textInputName.setVisibility(View.GONE);
//                    binding.linearAgentView.setVisibility(View.GONE);
//                    binding.linearMerchantView.setVisibility(View.VISIBLE);
//                }*/
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//
//        mSetRightOut.start();
//
//        /*mSetRightOut.setAnimationListener(new Animation.AnimationListener()
//        {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//                if(a==1)
//                {
//                    binding.textInputName.setVisibility(View.VISIBLE);
//                    binding.linearAgentView.setVisibility(View.VISIBLE);
//                    binding.linearMerchantView.setVisibility(View.GONE);
//                }
//                else
//                {
//                    binding.textInputName.setVisibility(View.GONE);
//                    binding.linearAgentView.setVisibility(View.GONE);
//                    binding.linearMerchantView.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation)
//            {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });*/
//        //binding.linearFields.setAnimation(show);
//
//
//    }
//    private void showViewMerchant()
//    {
//
//        // Set the content view to 0% opacity but visible, so that it is visible
//        // (but fully transparent) during the animation.
//        binding.linearMerchantView.setAlpha(0f);
//        binding.linearMerchantView.setVisibility(View.VISIBLE);
//
//        long duration =  getResources().getInteger(
//                android.R.integer.config_shortAnimTime);
//
//        // Animate the content view to 100% opacity, and clear any animation
//        // listener set on the view.
//        binding.linearMerchantView.animate()
//                .alpha(1f)
//                .setDuration(duration)
//                .setListener(null);
//
//        // Animate the loading view to 0% opacity. After the animation ends,
//        // set its visibility to GONE as an optimization step (it won't
//        // participate in layout passes, etc.)
//        binding.linearAgentView.animate()
//                .alpha(0f)
//                .setDuration(duration)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        binding.linearAgentView.setVisibility(View.GONE);
//                    }
//                });
//    }
//
//    private void showViewAgent()
//    {
//
//        // Set the content view to 0% opacity but visible, so that it is visible
//        // (but fully transparent) during the animation.
//        binding.linearAgentView.setAlpha(0f);
//        binding.linearAgentView.setVisibility(View.VISIBLE);
//
//        long duration =  getResources().getInteger(
//                android.R.integer.config_shortAnimTime);
//
//        // Animate the content view to 100% opacity, and clear any animation
//        // listener set on the view.
//        binding.linearAgentView.animate()
//                .alpha(1f)
//                .setDuration(duration)
//                .setListener(null);
//
//        // Animate the loading view to 0% opacity. After the animation ends,
//        // set its visibility to GONE as an optimization step (it won't
//        // participate in layout passes, etc.)
//        binding.linearMerchantView.animate()
//                .alpha(0f)
//                .setDuration(duration)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        binding.linearMerchantView.setVisibility(View.GONE);
//                    }
//                });
//    }
//
//    public void validateInputsAndProceed()
//    {
//        String name = binding.etName.getText().toString().trim();
//        String email = binding.etEmail.getText().toString().trim();
//        String phone = binding.etPhone.getText().toString().trim();
//        String acc_no = binding.etAccNumber.getText().toString().trim();
//        String bvn_no = binding.etBVNNumber.getText().toString().trim();
//        int selected_bank = binding.spnBank.getSelectedItemPosition();
//
//        if (TextUtils.isEmpty(name))
//        {
//            binding.etName.setError("Name is required");
//            binding.etName.requestFocus();
//        }
//        else if (TextUtils.isEmpty(email))
//        {
//            binding.etEmail.setError("Email is required");
//            binding.etEmail.requestFocus();
//        }
//        else if(!isValidEmail(email))
//        {
//            binding.etEmail.setError("Invalid Email");
//            binding.etEmail.requestFocus();
//        }
//        else if(TextUtils.isEmpty(phone))
//        {
//            binding.etPhone.setError("Phone number is required");
//            binding.etPhone.requestFocus();
//        }
//        else if(phone.length()<9)
//        {
//            binding.etPhone.setError("Invalid phone number");
//            binding.etPhone.requestFocus();
//        }
//        else if(TextUtils.isEmpty(acc_no))
//        {
//            binding.etAccNumber.setError("Acccount number is required");
//            binding.etAccNumber.requestFocus();
//        }
//        else if(acc_no.length()<10)
//        {
//            binding.etAccNumber.setError("Invalid Acccount number");
//            binding.etAccNumber.requestFocus();
//        }
//        else if(selected_bank<0)
//        {
//
//            //TextView errorText = (binding.spnBank.getVie().findViewById(android.R.id.text1));
//            TextView errorText = (TextView)binding.spnBank.getRootView().findViewById(android.R.id.text1);
//            errorText.setError("");
//            errorText.setTextColor(Color.RED);//just to highlight that this is an error
//            errorText.setText("Bank name is required");//changes the selected item text to this
//        }
//        else if(TextUtils.isEmpty(bvn_no))
//        {
//            binding.etBVNNumber.setError("BVN number is required");
//            binding.etBVNNumber.requestFocus();
//        }
//        else if(bvn_no.length()<10)
//        {
//            binding.etBVNNumber.setError("Invalid BVN number");
//            binding.etBVNNumber.requestFocus();
//        }
//        else
//        {
//
//            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
//            /*if (ConnectivityReceiver.isConnected())
//            {
//
//            }*/
//        }
//    }
//    public boolean isValidEmail(String emailStr) {
//        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
//        return matcher.find();
//    }
//
//    private void loadAnimations() {
//        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.out_animation);
//        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.in_animation);
//    }
//
//    /*private void changeCameraDistance() {
//        int distance = 8000;
//        float scale = getResources().getDisplayMetrics().density * distance;
//        mCardFrontLayout.setCameraDistance(scale);
//        mCardBackLayout.setCameraDistance(scale);
//    }*/
//}
