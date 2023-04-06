package com.woleapp.ui.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.woleapp.R;
import com.woleapp.adapters.DashboardAdapter;
import com.woleapp.databinding.LayoutPaymentCompleteBinding;
import com.woleapp.databinding.LayoutPaymentSuccessfulBinding;
import com.woleapp.model.Service;
import com.woleapp.model.User;
import com.woleapp.util.Constants;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.SharedPrefManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PaymentSuccessFragment extends BaseFragment implements View.OnClickListener, Constants {
    Context context;
    private LayoutPaymentCompleteBinding binding;
    DashboardAdapter adapter;
    List<Service> serviceList;
    User user;
    Drawable customErrorDrawable;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        user = SharedPrefManager.getUser();
        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBack.setOnClickListener(this);
//        binding.linearNo.setOnClickListener(this);
        binding.btnSend.setOnClickListener(this);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_payment_complete, container, false);
        return binding.getRoot();
    }


    public boolean priceValidation(String price) {
        String regex = "[+-]?([0-9]*[.])?[0-9]+";
        Pattern numberPattern = Pattern.compile(regex);
        boolean result = numberPattern.matcher(price).matches();
        Log.e("Result: ", result + "--");
        return result;
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

        if (v == binding.btnBack || v == binding.btnSend) {
            showFragment(new DashboardFragment(), DashboardFragment.class.getSimpleName());
        }

//        if (v == binding.btnSend) {
//            if (isValid()) {
//                addFragmentWithoutRemove(R.id.container_main,new InvoicePreviewFragment
//                        (),InvoicePreviewFragment.class.getSimpleName());
//            }
//        } else if (v == binding.btnBack) {
//            showFragment(new DashboardFragment(), DashboardFragment.class.getSimpleName());
//        } else if (v == binding.linearNo) {
//            showFragment(new DashboardFragment(), DashboardFragment.class.getSimpleName());
//        }

    }

    public boolean isValidEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

//    public boolean isValid() {
//        String email = binding.etEmail.getText().toString().trim();
//
//        if (TextUtils.isEmpty(email)) {
//            binding.etEmail.setError("Email is required", customErrorDrawable);
//            binding.etEmail.requestFocus();
//        } else if (!isValidEmail(email)) {
//            binding.etEmail.setError("Invalid Email", customErrorDrawable);
//            binding.etEmail.requestFocus();
//        } else {
//            return true;
//        }
//        return false;
//    }

    private OnItemClickListener.OnItemClickCallback onClick = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {

        }
    };

}
