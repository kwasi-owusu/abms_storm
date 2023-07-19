package com.woleapp.ui.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.woleapp.R;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.databinding.LayoutSanlamClaimsBinding;
import com.woleapp.util.Utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SanlamClaimsFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutSanlamClaimsBinding binding;
    Utilities utilities;
    private String claim = "";
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

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_sanlam_claims, container, false);
        return binding.getRoot();
    }
    public void onClick(View v){
        String phone = "Phone Number: " + binding.phoneNumber.getText().toString().trim();
        String date = "Date: " + binding.date.getText().toString().trim();
        String cause_of_death = "Cause of death: " + causeOfDeath;
        String deathCert = binding.option.toString();
        if(v == binding.date){
            selectDate();
        }
        else if(v == binding.btnSubmit){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Message")
                    .setMessage(this.getString(R.string.thank_you))
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick (DialogInterface dialog, int id) {
                          saveToTxtFile(phone,date,cause_of_death,deathCert);
                           // addFragmentWithoutRemove(R.id.container_main, new SanlamClaimsFragment(), SanlamClaimsFragment.class.getSimpleName());
                        }

                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
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
                (view, year1, monthOfYear, dayOfMonth) -> binding.date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1),
                year, month, day);
        datePickerDialog.show();
    }

}
