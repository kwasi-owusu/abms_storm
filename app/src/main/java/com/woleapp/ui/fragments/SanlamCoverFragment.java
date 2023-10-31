package com.woleapp.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.woleapp.R;
import com.woleapp.databinding.LayoutSanlamCoverBinding;
import com.woleapp.network.AgencyBankingAPIClient;

import org.json.JSONObject;

import io.reactivex.disposables.Disposable;

public class SanlamCoverFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutSanlamCoverBinding binding;
    ProgressDialog mProgressDialog;
    String amtMonthCover1 = "4";
    String amtMonthCover2 = "14";
    String amtMonthCover3 = "25";
    String amtAnnualCover1 = "48";
    String amtAnnualCover2 = "168";
    String amtAnnualCover3 = "300";
    String amountSelected = "";
    String pFirstName, pLastName, pDOB, pContact, pGhanaCard, famFirstName, famLastName, famDOB, famPhone, benFirstName, benLastName, benPhone = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();

    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle b1 = getArguments();
        if (b1 != null) {
            pFirstName = b1.getString("first_name");
            pLastName = b1.getString("last_name");
            pDOB = b1.getString("DOB");
            pContact = b1.getString("contact");
            pGhanaCard = b1.getString("ghana_card");
            famFirstName = b1.getString("family_first_name");
            famLastName = b1.getString("family_last_name");
            famDOB = b1.getString("family_DOB");
            famPhone = b1.getString("family_phone");
            benFirstName = b1.getString("ben_first_name");
            benLastName = b1.getString("ben_last_name");
            benPhone = b1.getString("ben_phone");
        }
        Log.e(" Details Form", pFirstName + pLastName + pDOB + pContact + pGhanaCard + famFirstName+ famLastName+ famDOB+ famPhone+ benFirstName+ benLastName+ benPhone);

        binding.btnMonthlyCover1.setOnClickListener(this);
        binding.btnMonthlyCover2.setOnClickListener(this);
        binding.btnMonthlyCover3.setOnClickListener(this);
        binding.btnAnnuallyCover1.setOnClickListener(this);
        binding.btnAnnuallyCover2.setOnClickListener(this);
        binding.btnAnnuallyCover3.setOnClickListener(this);

        }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_sanlam_cover, container, false);
        return binding.getRoot();
    }

    public void onClick(View v){
        if(v == binding.btnMonthlyCover1){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("MESSAGE")
                    .setMessage(this.getString(R.string.cover_option_month1))
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick (DialogInterface dialog, int id) {
                            amountSelected = amtMonthCover1;
                            processInputs("GH₵2,000","monthly",amountSelected);
                        }

                    })
                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else if(v == binding.btnMonthlyCover2){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("MESSAGE")
                    .setMessage(this.getString(R.string.cover_option_month2))
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick (DialogInterface dialog, int id) {
                            amountSelected = amtMonthCover2;
                            processInputs("GH₵10,000","monthly",amountSelected);
                        }

                    })
                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else if(v == binding.btnMonthlyCover3){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("MESSAGE")
                    .setMessage(this.getString(R.string.cover_option_month3))
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick (DialogInterface dialog, int id) {
                            amountSelected = amtMonthCover3;
                            processInputs("GH₵20,000","monthly",amountSelected);
                        }

                    })
                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else if(v == binding.btnAnnuallyCover1){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("MESSAGE")
                    .setMessage(this.getString(R.string.cover_option_annual1))
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick (DialogInterface dialog, int id) {
                            amountSelected = amtAnnualCover1;
                            processInputs("GH₵2,000","annual",amountSelected);
                        }

                    })
                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else if(v == binding.btnAnnuallyCover2){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("MESSAGE")
                    .setMessage(this.getString(R.string.cover_option_annual2))
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick (DialogInterface dialog, int id) {
                            amountSelected = amtAnnualCover2;
                            processInputs("GH₵10,000","annual",amountSelected);
                        }

                    })
                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else if(v == binding.btnAnnuallyCover3){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("MESSAGE")
                    .setMessage(this.getString(R.string.cover_option_annual3))
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick (DialogInterface dialog, int id) {
                            amountSelected = amtAnnualCover3;
                            processInputs("GH₵20,000","annual",amountSelected);
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
    public void processInputs(String coverOption, String paymentFrequency, String amnt) {
        showProgressBar();

        String cover_option = coverOption;
        String payment_frequency = paymentFrequency;
        String amount = amnt;

        JsonObject userDetails = new JsonObject();
        userDetails.addProperty("first_name", pFirstName);
        userDetails.addProperty("surname", pLastName);
        userDetails.addProperty("dob", pDOB);
        userDetails.addProperty("contact_number", pContact);
        userDetails.addProperty("gh_card_no", pGhanaCard);

        JsonObject familyDetails = new JsonObject();
        familyDetails.addProperty("fam_first_name", famFirstName);
        familyDetails.addProperty("fam_sur_name", famLastName);
        familyDetails.addProperty("fam_dob", famDOB);
        familyDetails.addProperty("fam_contact_number", famPhone);

        JsonObject beneficiaryDetails = new JsonObject();
        beneficiaryDetails.addProperty("ben_first_name", benFirstName);
        beneficiaryDetails.addProperty("ben_surname", benLastName);
        beneficiaryDetails.addProperty("ben_contact_number", benPhone);

        JsonObject coverDetails = new JsonObject();
        coverDetails.addProperty("cover_option",cover_option);
        coverDetails.addProperty("payment_frequency",payment_frequency);
        coverDetails.addProperty("amount",amount);

        JsonObject userInputs = new JsonObject();
        userInputs.add("user_details", userDetails);
        userInputs.add("family_member_details", familyDetails);
        userInputs.add("beneficiary_details", beneficiaryDetails);
        userInputs.add("cover", coverDetails);

        Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + userInputs);
        Disposable subscribe = AgencyBankingAPIClient
                .sanlamSignUp(userInputs, context)
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
                        //addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                        dismissProgressBar();
                        return;
                    }
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
                    dismissProgressBar();
                    addFragmentWithoutRemove(R.id.container_main, new SanlamPolicyFragment(), SanlamPolicyFragment.class.getSimpleName());
                }, err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                    Log.e("Error", err.getMessage());
                    dismissProgressBar();
                });

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
