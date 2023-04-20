package com.woleapp.ui.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.JsonObject;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent;
import com.woleapp.R;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.databinding.LayoutFuneralDonationsBinding;
import com.woleapp.db.Injection;
import com.woleapp.db.UserViewModel;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.User;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FuneralDonationsFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutFuneralDonationsBinding binding;
    Utilities utilities;
    AgencyUser user;
    String[] networkTypes;
    private String networkType = "";
    Drawable customErrorDrawable;
    String currency_symbol = "";
    int len = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);

        networkTypes = context.getResources().getStringArray(R.array.network_type);
        user = SharedPrefManager.getAgencyUser();
        Log.e("AgencyData", "user" + user);
        ((HomeActivity) getActivity()).setTitleWithNoNavigation("Dashboard");

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);

        setNetworkSpinner();
     //   prepareCanvas();

        binding.cashCollection.setOnClickListener(this);
        binding.cardCollection.setOnClickListener(this);
        binding.btnPrint.setOnClickListener(this);
        binding.readCard.setOnClickListener(this);
        binding.tvRetake.setOnClickListener(this);

        binding.etCVV.setVisibility(View.GONE);
        binding.cardNumber.setVisibility(View.GONE);
        binding.cardExpiry.setVisibility(View.GONE);
        binding.btnPrint.setVisibility(View.GONE);
        binding.readCard.setVisibility(View.GONE);
        binding.tvConvinienceFee.setVisibility(View.GONE);
        binding.linearCanvas.setVisibility(View.GONE);
        binding.linearCanvasMain.setVisibility(View.GONE);

        binding.momo.setChecked(true);
        binding.etCVV.setTypeface(Typeface.DEFAULT);
        binding.etCVV.setTransformationMethod(new PasswordTransformationMethod());

        RadioGroup radioGroup = binding.transtype;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (binding.momo.isChecked()) {
                    binding.cardCollection.setVisibility(View.GONE);
                    binding.cashCollection.setVisibility(View.VISIBLE);
                    binding.btnPrint.setVisibility(View.GONE);
                    binding.etCVV.setVisibility(View.GONE);
                    binding.accountNumber.setVisibility(View.VISIBLE);
                    binding.networkSpinner.setVisibility(View.VISIBLE);
                    binding.cardNumber.setVisibility(View.GONE);
                    binding.cardExpiry.setVisibility(View.GONE);
                    binding.readCard.setVisibility(View.GONE);
                } else if (binding.cardPayment.isChecked()) {
                    binding.cashCollection.setVisibility(View.GONE);
                    binding.cardCollection.setVisibility(View.VISIBLE);
                    binding.btnPrint.setVisibility(View.GONE);
                    binding.etCVV.setVisibility(View.VISIBLE);
                    binding.accountNumber.setVisibility(View.GONE);
                    binding.networkSpinner.setVisibility(View.GONE);
                    binding.cardNumber.setVisibility(View.VISIBLE);
                    binding.cardExpiry.setVisibility(View.VISIBLE);
                    binding.cardNumber.setEnabled(false);
                    binding.cardExpiry.setEnabled(false);
                    binding.readCard.setVisibility(View.VISIBLE);
                }
                else if(binding.cashPayment.isChecked()){
                    binding.cardCollection.setVisibility(View.GONE);
                    binding.cashCollection.setVisibility(View.GONE);
                    binding.btnPrint.setVisibility(View.VISIBLE);
                    binding.etCVV.setVisibility(View.GONE);
                    binding.accountNumber.setVisibility(View.VISIBLE);
                    binding.networkSpinner.setVisibility(View.VISIBLE);
                    binding.cardNumber.setVisibility(View.GONE);
                    binding.cardExpiry.setVisibility(View.GONE);
                    binding.readCard.setVisibility(View.GONE);
                    binding.networkSpinner.setVisibility(View.GONE);
                }
            }
        });

        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
        Float convenience_fee = SharedPrefManager.getTransfeeConvenienceFee();
        //binding.etFee.setText(utilities.getFormattedAmount(convenience_fee));
     //   addTextChangeListener();
      //  processToken();


        int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("agency_ID", agencyID);

      //  getAgencyDetails(jsonObject);
        Log.e("RES_CODE", "Payload: " + jsonObject);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_funeral_donations, container, false);
        return binding.getRoot();
    }
    public void onClick(View v){

    }
    public void setNetworkSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.network_type, R.layout.spinner_view_network_type);
        adapter.setDropDownViewResource(R.layout.spinner_view_network_type);
        binding.spnNetworkType.setPrompt(context.getResources().getString(R.string.hint_choose_network));

        binding.spnNetworkType.setAdapter(
                // adapter
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_network_type,
                        getActivity()
                )
        );

        binding.spnNetworkType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View parent, int position, long l) {
                if (position > 0) {
                    //binding.spnIdType.setEnabled(false);
                    networkType = networkTypes[position - 1];
                    Log.e("selected_id", networkType + "---");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void addTextChangeListener() {

        RxTextView.afterTextChangeEvents(this.binding.etAmt)
                .skip(1)
                .debounce(1000, TimeUnit.MILLISECONDS)
                //.toFlowable(BackpressureStrategy.BUFFER)
                .cache()
                .filter(textViewTextChangeEvent -> this.binding.etAmt.hasFocus())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void accept(TextViewAfterTextChangeEvent textViewTextChangeEvent) throws Exception {
                        Log.e("tag", "text chanege");
                        Editable editable = textViewTextChangeEvent.getEditable();
                        if (editable.length() != 0 && editable.length() != len) {
                            String text = editable.toString();
                            if (text.length() > 0) {
                                text = text.replace(currency_symbol, "").replaceAll(",", "");

                                if (text.length() > 0) {
                                    boolean result = priceValidation(text);
                                    if (!result) {
                                        //binding.etPrice.setError("Invalid input");
                                        binding.etAmt.setError("Invalid input", customErrorDrawable);
                                        //binding.etPrice.requestFocus();
                                        len = text.length();
                                    } else {
                                        Double amt = Double.parseDouble(text);
                                        if (amt < 0) {
                                            binding.etAmt.setError("Minimum amount of transaction is " + currency_symbol + 0, customErrorDrawable);
                                            //binding.etPrice.requestFocus();
                                        } else if (amt > 100000) {
                                            binding.etAmt.setError("Maximum amount of transaction is " + currency_symbol + 100000, customErrorDrawable);
                                        } else {
                                            binding.etAmt.setError(null);
                                            String amt1 = utilities.getFormattedAmount(amt);
                                            binding.etAmt.setText(amt1);
                                            try {
                                                /*binding.etPrice.setSelection(amt1.length());*/
                                                binding.etAmt.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        binding.etAmt.setSelection(binding.etAmt.getText().length());
                                                    }
                                                });
                                                len = amt1.length();
                                                Log.e("Set_Selection", "Set_Selection");
                                            } catch (IndexOutOfBoundsException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    }
                                }

                            }
                        }
                    }
                });

    }
    public boolean priceValidation(String price) {
        String regex = "[+-]?([0-9]*[.])?[0-9]+";
        Pattern numberPattern = Pattern.compile(regex);
        boolean result = numberPattern.matcher(price).matches();
        Log.e("Result: ", result + "--");
        return result;
    }

}
