package com.woleapp.ui.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.woleapp.R;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.databinding.LayoutSanlamBinding;
import com.woleapp.databinding.PageIndicatorLayoutBinding;
import com.woleapp.util.Utilities;

import java.util.Calendar;

public class SanlamFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutSanlamBinding binding;
    Utilities utilities;
    private String gender = "";
    String[] genderArray;
    private String maritalStatus = "";
    String[] maritalStatusArray;
    private int currentCircle = 1; // Assuming the initial active circle is the first one


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
        genderArray = context.getResources().getStringArray(R.array.choose_gender);
        maritalStatusArray = context.getResources().getStringArray(R.array.choose_marital_status);

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setGenderSpinner();
        //setMaritalStatusSpinner();

        binding.btnNextMain.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.btnPrevious.setOnClickListener(this);
        binding.dateOfBirth.setOnClickListener(this);
        binding.familyDateOfBirth.setOnClickListener(this);
        binding.btnPreviousPage.setOnClickListener(this);
        binding.btnGetStarted.setOnClickListener(this);
        binding.btnJoinNow.setOnClickListener(this);
        binding.addressDetails.setVisibility(View.GONE);
        binding.employmentDetails.setVisibility(View.GONE);
        binding.personalDetails.setVisibility(View.GONE);
        binding.pageNumber.setVisibility(View.GONE);
        binding.policy.setVisibility(View.GONE);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_sanlam, container, false);
        return binding.getRoot();
    }
    public void onClick(View v) {
        if(v == binding.dateOfBirth || v == binding.familyDateOfBirth){
            selectDate();
        }
       else if (v == binding.btnNextMain){
            binding.employmentDetails.setVisibility(View.GONE);
            binding.addressDetails.setVisibility(View.VISIBLE);
            binding.personalDetails.setVisibility(View.GONE);
            binding.pageNumber.setVisibility(View.VISIBLE);
            moveToPageTwo();
        }
        else if(v == binding.btnPrevious){
            binding.employmentDetails.setVisibility(View.GONE);
            binding.addressDetails.setVisibility(View.GONE);
            binding.personalDetails.setVisibility(View.VISIBLE);
            binding.pageNumber.setVisibility(View.VISIBLE);
            moveToFirstPage();
        }
        else if (v == binding.btnNext){
            binding.employmentDetails.setVisibility(View.VISIBLE);
            binding.addressDetails.setVisibility(View.GONE);
            binding.personalDetails.setVisibility(View.GONE);
            binding.pageNumber.setVisibility(View.VISIBLE);
            moveToPageThree();
        }
        else if (v == binding.btnPreviousPage){
            binding.employmentDetails.setVisibility(View.GONE);
            binding.addressDetails.setVisibility(View.VISIBLE);
            binding.personalDetails.setVisibility(View.GONE);
            binding.pageNumber.setVisibility(View.VISIBLE);
            moveToPageTwo();
        }
        else if(v == binding.btnGetStarted){
            binding.addressDetails.setVisibility(View.GONE);
            binding.employmentDetails.setVisibility(View.GONE);
            binding.personalDetails.setVisibility(View.GONE);
            binding.pageNumber.setVisibility(View.GONE);
            binding.getStarted.setVisibility(View.GONE);
            binding.policy.setVisibility(View.VISIBLE);
        }
        else if(v == binding.btnJoinNow){
            binding.addressDetails.setVisibility(View.GONE);
            binding.employmentDetails.setVisibility(View.GONE);
            binding.personalDetails.setVisibility(View.VISIBLE);
            binding.pageNumber.setVisibility(View.GONE);
            binding.getStarted.setVisibility(View.GONE);
            binding.policy.setVisibility(View.GONE);
            binding.pageNumber.setVisibility(View.VISIBLE);
        }
    }
    private void moveToPageTwo() {
        int i = 0;
        boolean isActive = (i == currentCircle);
        currentCircle++;

        binding.circleInactive1.setBackgroundResource(isActive ? R.drawable.circle_inactive : R.drawable.circle_active);
        binding.circleInactive1.setTextColor(getResources().getColor(isActive ? R.color.red : R.color.colorWhite));

        binding.circleActive.setBackgroundResource(isActive ? R.drawable.circle_active : R.drawable.circle_inactive);
        binding.circleActive.setTextColor(getResources().getColor(isActive ? R.color.colorWhite : R.color.colorBlack));

        binding.circleInactive2.setBackgroundResource(isActive ? R.drawable.circle_active : R.drawable.circle_inactive);
        binding.circleInactive2.setTextColor(getResources().getColor(isActive ? R.color.colorWhite : R.color.colorBlack));
    }
    private void moveToPageThree() {
        int i = 0;
        boolean isActive = (i == currentCircle);
        currentCircle++;

        binding.circleInactive2.setBackgroundResource(isActive ? R.drawable.circle_inactive : R.drawable.circle_active);
        binding.circleInactive2.setTextColor(getResources().getColor(isActive ? R.color.red : R.color.colorWhite));

        binding.circleActive.setBackgroundResource(isActive ? R.drawable.circle_active : R.drawable.circle_inactive);
        binding.circleActive.setTextColor(getResources().getColor(isActive ? R.color.colorWhite : R.color.colorBlack));

        binding.circleInactive1.setBackgroundResource(isActive ? R.drawable.circle_active : R.drawable.circle_inactive);
        binding.circleInactive1.setTextColor(getResources().getColor(isActive ? R.color.colorWhite : R.color.colorBlack));
    }
    private void moveToFirstPage() {
        int i = 0;
        boolean isActive = (i == currentCircle);
        currentCircle++;

        binding.circleActive.setBackgroundResource(isActive ? R.drawable.circle_inactive : R.drawable.circle_active);
        binding.circleActive.setTextColor(getResources().getColor(isActive ? R.color.red : R.color.colorWhite));

        binding.circleInactive1.setBackgroundResource(isActive ? R.drawable.circle_active : R.drawable.circle_inactive);
        binding.circleInactive1.setTextColor(getResources().getColor(isActive ? R.color.colorWhite : R.color.colorBlack));

        binding.circleInactive2.setBackgroundResource(isActive ? R.drawable.circle_active : R.drawable.circle_inactive);
        binding.circleInactive2.setTextColor(getResources().getColor(isActive ? R.color.colorWhite : R.color.colorBlack));

    }

    /*public void setGenderSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.choose_gender, R.layout.spinner_view_choose_gender);
        adapter.setDropDownViewResource(R.layout.spinner_view_choose_gender);
        binding.spnGender.setPrompt(context.getResources().getString(R.string.hint_choose_gender));

        binding.spnGender.setAdapter(
                // adapter
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_choose_gender,
                        getActivity()
                )
        );

        binding.spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View parent, int position, long l) {
                if (position > 0) {
                    //binding.spnIdType.setEnabled(false);
                    gender = genderArray[position - 1];
                    Log.e("selected_id", gender + "---");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public void setMaritalStatusSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.choose_marital_status, R.layout.spinner_view_marital_status);
        adapter.setDropDownViewResource(R.layout.spinner_view_marital_status);
        binding.spnMaritalStatus.setPrompt(context.getResources().getString(R.string.hint_choose_marital_status));

        binding.spnMaritalStatus.setAdapter(
                // adapter
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_marital_status,
                        getActivity()
                )
        );

        binding.spnMaritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View parent, int position, long l) {
                if (position > 0) {
                    maritalStatus = maritalStatusArray[position - 1];
                    Log.e("selected_id", maritalStatus + "---");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }*/
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

    }
