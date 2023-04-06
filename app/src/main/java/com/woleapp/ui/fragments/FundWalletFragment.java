package com.woleapp.ui.fragments;

import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_ICC;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_MAG;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_PICC;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent;
import com.netpluspay.netpossdk.emv.CardReaderEvent;
import com.netpluspay.netpossdk.emv.CardReaderService;
import com.woleapp.R;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.databinding.LayoutFundWalletBinding;
import com.woleapp.model.User;
import com.woleapp.util.Constants;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_ICC;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_MAG;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_PICC;
import com.netpluspay.netpossdk.emv.CardReadResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class FundWalletFragment extends BaseFragment implements View.OnClickListener, Constants {

    Context context;
    private LayoutFundWalletBinding binding;
    User user;
    Drawable customErrorDrawable;
    int transaction_type = 0;
    Utilities utilities;
    String currency_symbol = "";
    int len = 0;
    String[] IDs;
    private String ID = "";


    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", FundWalletFragment.class.getSimpleName() + "--");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause", FundWalletFragment.class.getSimpleName() + "--");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        user = SharedPrefManager.getUser();
        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
        IDs = context.getResources().getStringArray(R.array.idcard);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);
        hideKeyBoard();
        Bundle b1 = getArguments();
        if (b1 != null) {
            if (b1.containsKey("transaction_type")) {
                transaction_type = b1.getInt("transaction_type", 0);
                if (transaction_type == Constants.TRANSACTION_CASH_OUT) {
                    binding.tvTitle.setText("CASH-OUT");
                } else {
                    binding.tvTitle.setText("FUND WALLET");
                }
            }
        }

        setIdSpinner();

        setListeners();


    }

    public void setIdSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.idtype, R.layout.spinner_view_id_type);
        adapter.setDropDownViewResource(R.layout.item_drop_down_id);
        binding.spnIdType.setPrompt(context.getResources().getString(R.string.hint_choose_id_type));

        binding.spnIdType.setAdapter(
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
                    ID = IDs[position - 1];
                    Log.e("selected_id", ID + "---");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fund_wallet, container, false);
        return binding.getRoot();
    }

    public void setListeners() {
        binding.etFee.setKeyListener(null);
        Float convenience_fee = SharedPrefManager.getPOSConvenienceFee();
        Log.d("Fundwalet", "convenience fee is " + convenience_fee);
        binding.etFee.setText(utilities.getFormattedAmount(convenience_fee));

        binding.btnContinue.setOnClickListener(this);

        RxTextView.afterTextChangeEvents(this.binding.etPrice)
                .skip(1)
                .debounce(1000, TimeUnit.MILLISECONDS)
                //.toFlowable(BackpressureStrategy.BUFFER)
                .cache()
                .filter(textViewTextChangeEvent -> this.binding.etPrice.hasFocus())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void accept(TextViewAfterTextChangeEvent textViewTextChangeEvent) throws Exception {
                        Editable editable = textViewTextChangeEvent.getEditable();
                        if (editable.length() != 0 && editable.length() != len) {
                            String text = editable.toString();
                            if (text.length() > 0) {
                                text = text.replace(currency_symbol, "").replaceAll(",", "");

                                if (text.length() > 0) {
                                    boolean result = priceValidation(text);
                                    if (!result) {
                                        //binding.etPrice.setError("Invalid input");
                                        binding.etPrice.setError("Invalid input", customErrorDrawable);
                                        //binding.etPrice.requestFocus();
                                        len = text.length();
                                    } else {
                                        Double amt = Double.parseDouble(text);
                                        if (amt < 1) {
                                            binding.etPrice.setError("Minimum amount of transaction is " + currency_symbol + 1, customErrorDrawable);
                                            //binding.etPrice.requestFocus();
                                        } else {
                                            binding.etPrice.setError(null);
                                            String amt1 = utilities.getFormattedAmount(amt);
                                            binding.etPrice.setText(amt1);
                                            float finalAmt = (Float.parseFloat(text) * convenience_fee) / 100;
                                            binding.etFee.setText(utilities.getFormattedAmount(Double.parseDouble(Float.toString(finalAmt))));
                                            try {
                                                /*binding.etPrice.setSelection(amt1.length());*/
                                                binding.etPrice.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        binding.etPrice.setSelection(binding.etPrice.getText().length());
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
        if (v == binding.btnContinue) {
//            if (isValid()) {
            String amounts = binding.etPrice.getText().toString().trim();
            String idNumber = binding.etIDNo.getText().toString().trim();
            String customerName = binding.etName.getText().toString().trim();
            if (TextUtils.isEmpty(amounts)) {
                binding.etPrice.setError("Amount is required", customErrorDrawable);
                binding.etPrice.requestFocus();
            } else if (TextUtils.isEmpty(idNumber)) {
                binding.etIDNo.setError("ID Number is required", customErrorDrawable);
                binding.etIDNo.requestFocus();
            } else if (TextUtils.isEmpty(customerName)) {
                binding.etName.setError("Name is required", customErrorDrawable);
                binding.etName.requestFocus();
            }
            Log.d("CONTINUE", "continue");
            Bundle b1 = new Bundle();
            b1.putString("amount", binding.etPrice.getText().toString());
            String amount = binding.etPrice.getText().toString();
            b1.putString("amount", amount);
            String name = binding.etName.getText().toString().trim();
            b1.putString("name", name);
            String idCard = ID;
            b1.putString("idCard", idCard);
            String idNum = binding.etIDNo.getText().toString().trim();
            b1.putString("idNum", idNum);
            b1.putString("convenience_fee", binding.etFee.getText().toString());
            b1.putInt("transaction_type", 2);
            PaymentModeFragment paymentModeFragment = new PaymentModeFragment();
            paymentModeFragment.setArguments(b1);
            addFragmentWithoutRemove(R.id.container_main, paymentModeFragment, PaymentModeFragment.class.getSimpleName());
            hideKeyBoard();

            Log.d("name", name);
            Log.d("id", idCard);
            Log.d("num", idNum);
            Log.d("amount", amount);
//                if (transaction_type == Constants.TRANSACTION_CASH_OUT) {
//                    String amount = binding.etPrice.getText().toString().replace("₵", "").trim();
//
//                    Bundle b1 = new Bundle();
//                    b1.putString("amount", amount);
//                    b1.putString("convenience_fee", binding.etFee.getText().toString());
//                    b1.putInt("transaction_type", Constants.TRANSACTION_CASH_OUT);
//                    PaymentModeFragment paymentModeFragment = new PaymentModeFragment();
//                    paymentModeFragment.setArguments(b1);
//                    addFragmentWithoutRemove(R.id.container_main, paymentModeFragment, PaymentModeFragment.class.getSimpleName());
//                } else {
//
//                    String price = binding.etPrice.getText().toString().replace("₦", "").replace(",","").trim();
//                    String fee = binding.etFee.getText().toString().replace("₦", "").replace(","," ").trim();
//                    Log.d(getTag(), "price is " + price);
//                    Log.d(getTag(), "fee is " + fee);
//                    double amount = Double.parseDouble(price) + Double.parseDouble(fee);
//                    Utilities utilities = new Utilities(context);
//                    Bundle b1 = new Bundle();
//                    b1.putString("amount", binding.etPrice.getText().toString());
//                    b1.putString("convenience_fee", binding.etFee.getText().toString());
//                    b1.putInt("transaction_type", Constants.TRANSACTION_FUND_WALLET);
//                    PaymentModeFragment paymentModeFragment = new PaymentModeFragment();
//                    paymentModeFragment.setArguments(b1);
//                    addFragmentWithoutRemove(R.id.container_main, paymentModeFragment, PaymentModeFragment.class.getSimpleName());
//
//                }

            //  }
        }
    }

    public void hideKeyBoard() {
        try {
            if (context != null) {
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

                // check if no view has focus:
                View v = ((Activity) context).getCurrentFocus();
                if (v == null) return;

                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isValid() {
        return true;
//        String amount = binding.etPrice.getText().toString().trim();
//        String idNumber = binding.etIDNo.getText().toString().trim();
//        if(TextUtils.isEmpty(amount)){
//            binding.etPrice.setError("Amount is required", customErrorDrawable);
//            binding.etPrice.requestFocus();
//        }
//        else if(TextUtils.isEmpty(idNumber)){
//            binding.etPrice.setError("Amount is required", customErrorDrawable);
//            binding.etPrice.requestFocus();
//        }
//        String price = binding.etPrice.getText().toString().trim();
//        price = price.replace(currency_symbol, "").replaceAll(",", "");
//        if (TextUtils.isEmpty(price)) {
//            binding.etPrice.setError("Invalid input", customErrorDrawable);
//            binding.etPrice.requestFocus();
//        } else if (!priceValidation(price)) {
//            binding.etPrice.setError("Invalid input", customErrorDrawable);
//            binding.etPrice.requestFocus();
//        } else if (Double.parseDouble(price) < 10) {
//            binding.etPrice.setError("Minimum amount of transaction is " + currency_symbol + 10, customErrorDrawable);
//            binding.etPrice.requestFocus();
//        }
//        else {
//            return true;
//        }
//        return false;
        //}
    }
}