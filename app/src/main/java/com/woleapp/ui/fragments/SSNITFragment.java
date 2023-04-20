package com.woleapp.ui.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.woleapp.R;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.databinding.LayoutSsnitBinding;
import com.woleapp.model.BankList;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SSNITFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutSsnitBinding binding;
    Utilities utilities;
    String[] IDs;
    private String ID = "";
    ProgressDialog mProgressDialog;
    List<BankList> banks = new ArrayList<>();
    List<String> bankNames = new ArrayList<>();
    List<String> bankCodes = new ArrayList<>();
    Dialog dialog;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
        IDs = context.getResources().getStringArray(R.array.states);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setIdSpinner();
        getBankListNew();
        binding.etAccountNo.setOnClickListener(this);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_ssnit, container, false);
        return binding.getRoot();
    }
    public void onClick(View v){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_searchable_spinner);
        dialog.getWindow().setLayout(650,800);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        EditText editText = dialog.findViewById(R.id.edit_text);
        ListView listView = dialog.findViewById(R.id.list_view);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,bankNames);

        // set adapter
        listView.setAdapter(adapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // when item selected from list
                // set selected item on textView
                binding.etAccountNo.setText(adapter.getItem(position));

                // Dismiss dialog
                dialog.dismiss();
            }
        });
    }
   /* public void setIdSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.states, R.layout.spinner_view_id_type);
        adapter.setDropDownViewResource(R.layout.item_drop_down_id);
        binding.spnIdType.setPrompt(context.getResources().getString(R.string.hint_choose_id_type));
      //  searchableSpinner.setTitle("Select State");
       // searchableSpinner.setPositiveButton("OK");

        binding.spnIdType.setAdapter(
                //adapter
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_id_type,
                        getActivity()
                )
        );

        binding.spnIdType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View parent, int position, long l) {
                if (position > 0) {
                    //binding.spnIdType.setEnabled(false);
                    ID = IDs[position - 1];
                    Log.e("selected_id", ID + "---");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }*/

    public void getBankListNew(){
        showProgressBar();
        Disposable subscribe = AgencyBankingAPIClient
                .getBankList(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res ->{
                    if(res.code() != 200) {
                        Toast.makeText(context, "Failed to get banks", Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("RES_CODE", "Banks: " + response);
                    if(response.optString("success").equals("false")){
                        Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray jsonArray = response.optJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String partnerID = item.optString("id");
                        String partnerName = item.optString("name");
                        String shortName = item.optString("shortName");

                        if(item.optString("type").equals("bank")) {
                            String name = item.optString("name");
                            bankNames.add(name);
                            Log.e("Bank Name", "Bank Name " + name);
                            String code = item.optString("id");
                            bankCodes.add(code);
                            Log.e("Bank Code", "Bank Code " + code);
                        }

                        //setSpinner();
                        dismissProgressBar();
                    }
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
