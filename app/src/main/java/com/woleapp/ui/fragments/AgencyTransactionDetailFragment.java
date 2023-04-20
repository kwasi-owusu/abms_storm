package com.woleapp.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;
import com.woleapp.R;
import com.woleapp.adapters.AgencyTransactionDetailAdapter;
import com.woleapp.model.AgencyTransactions;
import com.woleapp.model.AgencyUser;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.ui.activity.MainActivity;
import com.woleapp.util.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class AgencyTransactionDetailFragment extends BaseFragment implements View.OnClickListener {

    Context context;
    AgencyTransactionDetailAdapter adapter;
    AgencyUser user;
    RecyclerView recyclerView;
    ProgressDialog mProgressDialog;
    List<AgencyTransactions> transactions = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();

        user = SharedPrefManager.getAgencyUser();
        if (user == null) {
            Prefs.clear();

            Intent intent = new Intent(this.getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.getActivity().finish();
            return;
        }
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.agencyTransactionDetailsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        String officerID = SharedPrefManager.getAgencyUser().getOfficerID();
        int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tellerID", Integer.parseInt(officerID));
        jsonObject.addProperty("agent_ID", agencyID);
        Log.e("TAG", "transPayload: "+jsonObject);
        fetchTransactions(jsonObject);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agency_transaction_detail, container, false);
    }

    @Override
    public void onClick(View view) {

    }

    public void fetchTransactions(JsonObject payload) {
//        Disposable subscribe;
        showProgressBar();
        Bundle bundle = this.getArguments();
        String chqNum = bundle.getString("data");
        Log.e("TAG", "bundle check: "+chqNum);
        switch (chqNum) {
            case "1":
               Disposable subscribe =  AgencyBankingAPIClient.weeklyReports(payload, context).subscribe(res -> {
                    if(res.code() != 200) {
                        Toast.makeText(context, "could not get transactions", Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    Object body = res.body();
                    Log.e("TAG", "responseBody: "+body);
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(body));
                    if(jsonObject.optInt("code") == 111) {
                        Toast.makeText(context, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String transID = item.optString("trans_ID");
                        String transCat = item.optString("trans_cat");
                        String productCat = item.optString("product_cat");
                        String productName = item.optString("product_name");
                        String branchName = item.optString("branch_name");
                        String officer = item.optString("officer");
                        String transactionAmount = item.optString("transaction_amount");
                        String customerName = item.optString("customer_name");
                        String accountNumber = item.optString("account_number");
                        String idType = item.optString("id_type");
                        String idNumber = item.optString("id_number");
                        String transDate = item.optString("transaction_date");
                        String depositorPayee = item.optString("depositor_payee");
                        String agencyName = item.optString("agency_name");

                        transactions.add(new AgencyTransactions(transID, transCat, productCat, productName, branchName, officer, transactionAmount, customerName, accountNumber, idType, idNumber, transDate, depositorPayee, agencyName));
                    }
                    adapter = new AgencyTransactionDetailAdapter(getContext(), transactions);
                    recyclerView.setAdapter(adapter);
                    dismissProgressBar();
                }, err -> {
                    dismissProgressBar();
                    Toast.makeText(context, "could not get", Toast.LENGTH_LONG).show();
                });
                break;
            case "2":
                AgencyBankingAPIClient.monthlyReport(payload, context).subscribe(res -> {
                    if(res.code() != 200) {
                        Toast.makeText(context, "could not get transactions", Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    Object body = res.body();
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(body));
                    if(jsonObject.optInt("code") == 111) {
                        Toast.makeText(context, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String transID = item.optString("trans_ID");
                        String transCat = item.optString("trans_cat");
                        String productCat = item.optString("product_cat");
                        String productName = item.optString("product_name");
                        String branchName = item.optString("branch_name");
                        String officer = item.optString("officer");
                        String transactionAmount = item.optString("transaction_amount");
                        String customerName = item.optString("customer_name");
                        String accountNumber = item.optString("account_number");
                        String idType = item.optString("id_type");
                        String idNumber = item.optString("id_number");
                        String transDate = item.optString("transaction_date");
                        String depositorPayee = item.optString("depositor_payee");
                        String agencyName = item.optString("agency_name");

                        transactions.add(new AgencyTransactions(transID, transCat, productCat, productName, branchName, officer, transactionAmount, customerName, accountNumber, idType, idNumber, transDate, depositorPayee, agencyName));
                    }
                    adapter = new AgencyTransactionDetailAdapter(getContext(), transactions);
                    recyclerView.setAdapter(adapter);
                    dismissProgressBar();
                }, err -> {
                    dismissProgressBar();
                    Toast.makeText(context, "Could not get", Toast.LENGTH_LONG).show();
                });
                break;
            case "3":
                AgencyBankingAPIClient.yearlyReport(payload, context).subscribe(res -> {
                    if(res.code() != 200) {
                        Toast.makeText(context, "could not get transactions", Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    Object body = res.body();
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(body));
                    if(jsonObject.optInt("code") == 111) {
                        Toast.makeText(context, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String transID = item.optString("trans_ID");
                        String transCat = item.optString("trans_cat");
                        String productCat = item.optString("product_cat");
                        String productName = item.optString("product_name");
                        String branchName = item.optString("branch_name");
                        String officer = item.optString("officer");
                        String transactionAmount = item.optString("transaction_amount");
                        String customerName = item.optString("customer_name");
                        String accountNumber = item.optString("account_number");
                        String idType = item.optString("id_type");
                        String idNumber = item.optString("id_number");
                        String transDate = item.optString("transaction_date");
                        String depositorPayee = item.optString("depositor_payee");
                        String agencyName = item.optString("agency_name");

                        transactions.add(new AgencyTransactions(transID, transCat, productCat, productName, branchName, officer, transactionAmount, customerName, accountNumber, idType, idNumber, transDate, depositorPayee, agencyName));
                    }
                    adapter = new AgencyTransactionDetailAdapter(getContext(), transactions);
                    recyclerView.setAdapter(adapter);
                    dismissProgressBar();
                }, err -> {
                    dismissProgressBar();
                    Toast.makeText(context, "Could not get", Toast.LENGTH_LONG).show();
                });
                break;
            case "4":
                AgencyBankingAPIClient.allTime(payload, context).subscribe(res -> {
                    if(res.code() != 200) {
                        Toast.makeText(context, "could not get transactions", Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    Object body = res.body();
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(body));
                    if(jsonObject.optInt("code") == 111) {
                        Toast.makeText(context, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String transID = item.optString("trans_ID");
                        String transCat = item.optString("trans_cat");
                        String productCat = item.optString("product_cat");
                        String productName = item.optString("product_name");
                        String branchName = item.optString("branch_name");
                        String officer = item.optString("officer");
                        String transactionAmount = item.optString("transaction_amount");
                        String customerName = item.optString("customer_name");
                        String accountNumber = item.optString("account_number");
                        String idType = item.optString("id_type");
                        String idNumber = item.optString("id_number");
                        String transDate = item.optString("transaction_date");
                        String depositorPayee = item.optString("depositor_payee");
                        String agencyName = item.optString("agency_name");

                        transactions.add(new AgencyTransactions(transID, transCat, productCat, productName, branchName, officer, transactionAmount, customerName, accountNumber, idType, idNumber, transDate, depositorPayee, agencyName));
                    }
                    adapter = new AgencyTransactionDetailAdapter(getContext(), transactions);
                    recyclerView.setAdapter(adapter);
                    dismissProgressBar();
                }, err -> {
                    dismissProgressBar();
                    Toast.makeText(context, "Could not get", Toast.LENGTH_LONG).show();
                });
                break;
            default:
                Toast.makeText(context, "Getting transactions", Toast.LENGTH_LONG).show();
        }
    }
}