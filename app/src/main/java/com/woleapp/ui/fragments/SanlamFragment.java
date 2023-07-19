package com.woleapp.ui.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.woleapp.R;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.databinding.LayoutSanlamBinding;
import com.woleapp.databinding.PageIndicatorLayoutBinding;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.util.Utilities;

import org.json.JSONObject;

import java.util.Calendar;

import io.reactivex.disposables.Disposable;

public class SanlamFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    Drawable customErrorDrawable;
    private LayoutSanlamBinding binding;
    Utilities utilities;
    private String gender = "";
    String[] genderArray;
    private String maritalStatus = "";
    String[] maritalStatusArray;
    ProgressDialog mProgressDialog;
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
        binding.memberAlready.setOnClickListener(this);
        binding.claimHere.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        binding.addressDetails.setVisibility(View.GONE);
        binding.employmentDetails.setVisibility(View.GONE);
        binding.personalDetails.setVisibility(View.GONE);
        binding.pageNumber.setVisibility(View.GONE);
        binding.policy.setVisibility(View.GONE);

        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_sanlam, container, false);
        return binding.getRoot();
    }
    public void onClick(View v) {
        if(v == binding.dateOfBirth){
            selectDate();
        }
        else if(v == binding.familyDateOfBirth){
            selectFamilyDOB();
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
        else if(v == binding.btnSubmit){
            validateUserInputs();
        }
        else if(v == binding.memberAlready){
            addFragmentWithoutRemove(R.id.container_main, new SanlamPolicyFragment(), SanlamPolicyFragment.class.getSimpleName());
        }
        else if(v == binding.claimHere){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Disclaimer")
                    .setMessage(this.getString(R.string.disclaimer))
                    .setCancelable(false)
                    .setIcon(R.drawable.caution)
                    .setPositiveButton("Agree", new DialogInterface.OnClickListener() {

                        public void onClick (DialogInterface dialog, int id) {
                            addFragmentWithoutRemove(R.id.container_main, new SanlamClaimsFragment(), SanlamClaimsFragment.class.getSimpleName());
                        }

                    })
                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    public void validateUserInputs(){
        String firstName = binding.firstName.getText().toString().trim();
        String lastName = binding.surname.getText().toString().trim();
        String DOB = binding.dateOfBirth.getText().toString().trim();
        String contactNumber = binding.contactNumber.getText().toString().trim();
        String ghanaCardNumber = binding.ghanaCard.getText().toString().trim();
        String fMemberFName = binding.familyFirstName.getText().toString().trim();
        String fMemberLName = binding.familySurname.getText().toString().trim();
        String fMemberDOB = binding.familyDateOfBirth.getText().toString().trim();
        String fMemberPhone = binding.familyContactNumber.getText().toString().trim();
        String beneficiaryFName = binding.benFirstName.getText().toString().trim();
        String beneficiaryLName = binding.benSurname.getText().toString().trim();
        String beneficiaryPhone = binding.benContactNumber.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            binding.firstName.setError("Please enter your first name", customErrorDrawable);
            binding.firstName.requestFocus();
        }
        else if (TextUtils.isEmpty(lastName)) {
            binding.surname.setError("Please enter your last name", customErrorDrawable);
            binding.surname.requestFocus();
        }
        else if (TextUtils.isEmpty(DOB)) {
            binding.dateOfBirth.setError("Please enter your date of birth", customErrorDrawable);
            binding.dateOfBirth.requestFocus();
        }
        else if (TextUtils.isEmpty(contactNumber)) {
            binding.contactNumber.setError("Please enter your contact number", customErrorDrawable);
            binding.contactNumber.requestFocus();
        }
        else if (contactNumber.length() != 10) {
            binding.contactNumber.setError("Contact number should be 10 digits", customErrorDrawable);
            binding.contactNumber.requestFocus();
        }
        else if (TextUtils.isEmpty(ghanaCardNumber)) {
            binding.ghanaCard.setError("Please enter your Ghana card number", customErrorDrawable);
            binding.ghanaCard.requestFocus();
        }
        else if (TextUtils.isEmpty(fMemberFName)) {
            binding.familyFirstName.setError("Please enter first name", customErrorDrawable);
            binding.familyFirstName.requestFocus();
        }
        else if (TextUtils.isEmpty(fMemberLName)) {
            binding.familySurname.setError("Please enter last name", customErrorDrawable);
            binding.familySurname.requestFocus();
        }
        else if (TextUtils.isEmpty(fMemberDOB)) {
            binding.familyDateOfBirth.setError("Please enter date of birth", customErrorDrawable);
            binding.familyDateOfBirth.requestFocus();
        }
        else if (TextUtils.isEmpty(fMemberPhone)) {
            binding.familyContactNumber.setError("Please enter contact number", customErrorDrawable);
            binding.familyContactNumber.requestFocus();
        }
        else if (fMemberPhone.length() != 10) {
            binding.familyContactNumber.setError("Contact number should be 10 digits", customErrorDrawable);
            binding.familyContactNumber.requestFocus();
        }
        else if (TextUtils.isEmpty(beneficiaryFName)) {
            binding.benFirstName.setError("Please enter first name", customErrorDrawable);
            binding.benFirstName.requestFocus();
        }
        else if (TextUtils.isEmpty(beneficiaryLName)) {
            binding.benSurname.setError("Please enter last name", customErrorDrawable);
            binding.benSurname.requestFocus();
        }
        else if (TextUtils.isEmpty(beneficiaryPhone)) {
            binding.benContactNumber.setError("Please enter contact number", customErrorDrawable);
            binding.benContactNumber.requestFocus();
        }
        else if (beneficiaryPhone.length() != 10) {
            binding.benContactNumber.setError("Contact number should be 10 digits", customErrorDrawable);
            binding.benContactNumber.requestFocus();
        }
        else{
          //  Toast.makeText(context, "We're live!!", Toast.LENGTH_SHORT).show();
            processInputs(firstName, lastName, DOB, contactNumber, ghanaCardNumber, fMemberFName, fMemberLName, fMemberDOB, fMemberPhone, beneficiaryFName, beneficiaryLName, beneficiaryPhone);
        }

    }
    public void processInputs(String firstName, String lastName, String dateOfBirth, String phone, String ghanaCard,
                              String famFName, String famLName, String famDOB, String famPhone, String benFName, String benLName, String benPhone) {
        showProgressBar();

        String first_name = firstName;
        String surname = lastName;
        String dob = dateOfBirth;
        String contact_number = phone;
        String gh_card_no = ghanaCard;
        String fam_first_name = famFName;
        String fam_sur_name = famLName;
        String fam_dob = famDOB;
        String fam_contact_number = famPhone;
        String ben_first_name = benFName;
        String ben_surname = benLName;
        String ben_contact_number = benPhone;

        JsonObject userDetails = new JsonObject();
        userDetails.addProperty("first_name", first_name);
        userDetails.addProperty("surname", surname);
        userDetails.addProperty("dob", dob);
        userDetails.addProperty("contact_number", contact_number);
        userDetails.addProperty("gh_card_no", gh_card_no);

        JsonObject familyDetails = new JsonObject();
        familyDetails.addProperty("fam_first_name", fam_first_name);
        familyDetails.addProperty("fam_sur_name", fam_sur_name);
        familyDetails.addProperty("fam_dob", fam_dob);
        familyDetails.addProperty("fam_contact_number", fam_contact_number);

        JsonObject beneficiaryDetails = new JsonObject();
        beneficiaryDetails.addProperty("ben_first_name", ben_first_name);
        beneficiaryDetails.addProperty("ben_surname", ben_surname);
        beneficiaryDetails.addProperty("ben_contact_number", ben_contact_number);

        JsonObject userInputs = new JsonObject();
        userInputs.add("user_details", userDetails);
        userInputs.add("family_member_details", familyDetails);
        userInputs.add("beneficiary_details", beneficiaryDetails);

        Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + userInputs);
        Disposable subscribe = AgencyBankingAPIClient
                .sanlamSignUp(userInputs, context)
                .subscribe(res -> {
                    if (res.code() != 200) {
                        Toast.makeText(context, "Failed to process transaction, Please try again", Toast.LENGTH_SHORT).show();
                        addFragmentWithoutRemove(R.id.container_main, new SanlamFragment(), SanlamFragment.class.getSimpleName());
                        dismissProgressBar();
                        Log.e("Failed to process", res.message());
                        return;
                    }
                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("TRANSACTION_RESPONSE", "transaction response: " + response);
                    if (response.optString("error").equals(true)) {
                        Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                        //addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                        dismissProgressBar();
                        return;
                    }
                    dismissProgressBar();
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
                    addFragmentWithoutRemove(R.id.container_main, new SanlamPolicyFragment(), SanlamPolicyFragment.class.getSimpleName());
                }, err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                    addFragmentWithoutRemove(R.id.container_main, new SanlamFragment(), SanlamFragment.class.getSimpleName());
                    dismissProgressBar();
                });

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
                        binding.dateOfBirth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
    public void selectFamilyDOB() {
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
                        binding.familyDateOfBirth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                },
                year, month, day);
        datePickerDialog.show();
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
