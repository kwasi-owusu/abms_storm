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
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.woleapp.R;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.databinding.LayoutSanlamClaimsBinding;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.util.Utilities;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.disposables.Disposable;

public class SanlamClaimsFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    ProgressDialog mProgressDialog;
    private LayoutSanlamClaimsBinding binding;
    Drawable customErrorDrawable;
    Utilities utilities;
    private String claim, selectedDeathCert = "";
    String[] claimOption;
    private String causeOfDeath = "";
    String[] causeOfDeathOption;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
        claimOption = context.getResources().getStringArray(R.array.choose_claim);
        causeOfDeathOption = context.getResources().getStringArray(R.array.choose_cause_of_death);

    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClaimSpinner();
        setCauseOfDeathSpinner();

        binding.date.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

        binding.option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectedDeathCert = getSelectedItemAsString(i);
                Log.e("details", selectedDeathCert);
            }
        });

        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_sanlam_claims, container, false);
        return binding.getRoot();
    }

    public void onClick(View v){



        if(v == binding.date){
            selectDate();
        }
        else if(v == binding.btnSubmit){
           validateInputs();
        }
    }
    private String getSelectedItemAsString(int checkedId) {
        RadioButton selectedRadioButton = binding.option.findViewById(checkedId);
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText().toString();
        }
        return null; // Return null or some default value if nothing is selected
    }
    public void validateInputs(){
        String claimOption = claim;
        String phone = binding.phoneNumber.getText().toString().trim();
        String date = binding.date.getText().toString().trim();
        String deathCert = binding.option.toString();

        if (claim == null || claim.isEmpty()) {
            Toast.makeText(context, "Please select claim option", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(phone)) {
            binding.phoneNumber.setError("Please enter your contact number", customErrorDrawable);
            binding.phoneNumber.requestFocus();
        }
        else if (phone.length() != 10) {
            binding.phoneNumber.setError("Contact number should be 10 digits", customErrorDrawable);
            binding.phoneNumber.requestFocus();
        }
        else if (TextUtils.isEmpty(date)) {
            binding.date.setError("Please enter date of death", customErrorDrawable);
            binding.date.requestFocus();
        }
        else if (causeOfDeath == null || causeOfDeath.isEmpty()) {
            Toast.makeText(context, "Please select cause of death", Toast.LENGTH_LONG).show();
        }
        else{
            processInputs(claim, phone, date, selectedDeathCert, causeOfDeath);
        }
    }
public void processInputs(String claimOption, String phoneNumber, String dateOfDeath, String deathCert, String causeOfDeath) {
    showProgressBar();

    String claim_options = claimOption;
    String phone_number = phoneNumber;
    String date_of_death = dateOfDeath;
    String any_death_certificate = deathCert;
    String cause_of_death = causeOfDeath;

    JsonObject userDetails = new JsonObject();
    userDetails.addProperty("claim_options", claim_options);
    userDetails.addProperty("phone_number", phone_number);
    userDetails.addProperty("date_of_death", date_of_death);
    userDetails.addProperty("any_death_certificate", any_death_certificate);
    userDetails.addProperty("cause_of_death", cause_of_death);

    JsonObject userInputs = new JsonObject();
    userInputs.add("claims", userDetails);

    Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + userInputs);
    Disposable subscribe = AgencyBankingAPIClient
            .sanlamClaims(userInputs, context)
            .subscribe(res -> {
                if (res.code() != 200) {
                    Toast.makeText(context, "Failed to process transaction, Please try again", Toast.LENGTH_SHORT).show();
                    dismissProgressBar();
                    return;
                }
                Object body = res.body();
                JSONObject response = new JSONObject(new Gson().toJson(body));
                Log.e("TRANSACTION_RESPONSE", "transaction response: " + response);
                if (response.optString("error").equals(true)) {
                    Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                    dismissProgressBar();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Message")
                        .setMessage(this.getString(R.string.thank_you))
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick (DialogInterface dialog, int id) {
                                 addFragmentWithRemove(R.id.container_main, new SanlamFragment(), SanlamFragment.class.getSimpleName());
                            }

                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                dismissProgressBar();
            }, err -> {
                Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                Log.e("Error", err.getMessage());
                dismissProgressBar();
            });

}
    private void saveToTxtFile(String mPhone, String mDate, String mCauseOfDeath, String mDeathCert) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        try {
            File dir = new File((Environment.DIRECTORY_DOCUMENTS), "MyFiles");
            dir.mkdirs();

            String fileName = "MyFile_" + timeStamp + ".txt";

            File file = new File(dir, fileName);

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(mPhone);
            bw.newLine();
            bw.write(mDate);
            bw.newLine();
            bw.write(mCauseOfDeath);
            bw.newLine();
            bw.write(mDeathCert);
            bw.close();

            Toast.makeText(context, fileName + " is saved to\n" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("error", e.getMessage());
        }
    }
    public void setClaimSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.choose_claim, R.layout.spinner_view_choose_claim);
        adapter.setDropDownViewResource(R.layout.spinner_view_choose_claim);
        binding.spnClaim.setPrompt(context.getResources().getString(R.string.hint_choose_claim));

        binding.spnClaim.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_choose_claim,
                        getActivity()
                )
        );

        binding.spnClaim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View parent, int position, long l) {
                if (position > 0) {
                    //binding.spnIdType.setEnabled(false);
                    claim = claimOption[position - 1];
                    Log.e("selected_id", claim + "---");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public void setCauseOfDeathSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.choose_cause_of_death, R.layout.spinner_view_choose_claim);
        adapter.setDropDownViewResource(R.layout.spinner_view_choose_cause_of_death);
        binding.spnCauseOfDeath.setPrompt(context.getResources().getString(R.string.hint_choose_cause_of_death));

        binding.spnCauseOfDeath.setAdapter(
                // adapter
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_choose_cause_of_death,
                        getActivity()
                )
        );

        binding.spnCauseOfDeath.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View parent, int position, long l) {
                if (position > 0) {
                    //binding.spnIdType.setEnabled(false);
                    causeOfDeath = causeOfDeathOption[position - 1];
                    Log.e("selected_id", causeOfDeath + "---");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public void selectDate() {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, year1, monthOfYear, dayOfMonth) -> binding.date.setText(year1+ "-" + (monthOfYear + 1) + "-" + dayOfMonth),
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
