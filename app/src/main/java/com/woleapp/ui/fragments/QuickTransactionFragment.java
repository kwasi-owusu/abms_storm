package com.woleapp.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import com.woleapp.R;
import com.woleapp.adapters.DashboardAdapter;
import com.woleapp.databinding.LayoutQuickTransactionBinding;
import com.woleapp.model.Inventory;
import com.woleapp.model.Service;
import com.woleapp.model.User;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.network.APIServiceClient;
import com.woleapp.util.*;

import org.parceler.Parcels;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class QuickTransactionFragment extends BaseFragment implements View.OnClickListener, Constants {
    Context context;
    private LayoutQuickTransactionBinding binding;
    User user;
    Drawable customErrorDrawable;
    Utilities utilities;
    int len = 0;
    String currency_symbol = "";
    List<Inventory> searchProductList = new ArrayList<>();
    Inventory inventory;
    String productName, sellerId, sellerName;
    Integer productId;
    private CompositeDisposable disposable = new CompositeDisposable();

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

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);
        setListeners();

        productId = getArguments() == null ? null : getArguments().getInt("productId");
        productName = getArguments() == null ? null : getArguments().getString("productName");
        sellerId = user.getNetplus_id();
        sellerName = user.getBusiness_name();

        inventory = getArguments() == null ? null : Parcels.unwrap(getArguments().getParcelable("inventory"));
        binding.etProductName.addTextChangedListener(new AutoCompleteTextWatcher());
        binding.etProductName.setThreshold(2);
        binding.etProductName.setOnItemClickListener((parent, view1, position, id) -> {
            if (searchProductList != null && searchProductList.size() > 0) {
                inventory = searchProductList.get(position);
                binding.etPrice.setText(utilities.getFormattedAmount(Double.parseDouble(searchProductList.get(position).getPrice())));
            }
        });
        if (inventory != null) {
            productId = inventory.getProductId() == null ? 1 : Integer.parseInt(inventory.getProductId());
            productName = inventory.getProduct_name();
            sellerId = inventory.getMerchantId();
            sellerName = inventory.getStoreName();
            binding.etProductName.setText(inventory.getProduct_name());
            binding.etPrice.setText(inventory.getPrice());
            String amount = inventory.getPrice();
            amount = amount.replaceAll(",", "").replace(currency_symbol, "");
            binding.etPrice.setText(utilities.getFormattedAmount(Double.parseDouble(amount)));
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_quick_transaction, container, false);
        return binding.getRoot();
    }

    public void setListeners() {

        binding.etNote.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.etNote.setRawInputType(InputType.TYPE_CLASS_TEXT);

        binding.btnContinue.setOnClickListener(this);

        Disposable rxTextChangeEvent = RxTextView.afterTextChangeEvents(this.binding.etPrice)
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
                                        if (amt < 10) {
                                            binding.etPrice.setError("Minimum amount of transaction is " + currency_symbol + 10, customErrorDrawable);
                                            //binding.etPrice.requestFocus();
                                        } else {
                                            binding.etPrice.setError(null);
                                            String amt1 = utilities.getFormattedAmount(amt);
                                            binding.etPrice.setText(amt1);
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
        disposable.add(rxTextChangeEvent);

    }

    public boolean priceValidation(String price) {
        //^[1-9][0-9]{12,16}$ (old regular expression)
//		String regex = "^[+][0-9]{12,16}$";
        String regex = "[+-]?([0-9]*[.])?[0-9]+";//"[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)";
        //String regex = "^[+][0-9]{10,13}$";
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
        // Handle item selection
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v == binding.btnContinue) {
            if (isValid()) {

                hideKeyBoard();

                String amount = binding.etPrice.getText().toString().trim();
                amount = amount.replace(currency_symbol, "").replaceAll(",", "");

                String qty = binding.spnQuantity.getText().toString();
                String note  = binding.etNote.getText().toString().trim();
                String customerName = binding.edtCustomerName.getText().toString();
                Double totalAmt = Double.parseDouble(amount) * Double.parseDouble(qty);

                Bundle b1 = new Bundle();
                b1.putString("amount",/*amount*/utilities.getFormattedAmount(totalAmt));

                b1.putInt("transaction_type", Constants.TRANSACTION_QUICK);
                b1.putString("COMMODITY_NAME", "sale");
                b1.putString("DESCRIPTION", note);
                b1.putString("CUSTOMER_NAME", customerName);
                b1.putString("SELLER_ID", sellerId);
                b1.putString("SELLER_NAME", sellerName);
                b1.putString("QTY", qty);

                if (productId != null)
                    b1.putInt("PRODUCTID", productId);

                b1.putString("PRODUCTNAME", productName);
                if (qty != null)
                    b1.putInt("QUANTITY", Integer.valueOf(qty));

                PaymentModeFragment paymentModeFragment = new PaymentModeFragment();
                paymentModeFragment.setArguments(b1);

                if (inventory != null) {
                    Integer qty1 = Integer.parseInt(qty);
                    inventory.setQuantity(qty1);
                }

                addFragmentWithoutRemove(R.id.container_main, paymentModeFragment, PaymentModeFragment.class.getSimpleName());

            }
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
        String product_name = binding.etProductName.getText().toString().trim();
        String price = binding.etPrice.getText().toString().trim();
        String custName = binding.edtCustomerName.getText().toString().trim();
        price = price.replace(currency_symbol, "").replaceAll(",", "");
        int selected_quantity = 0;
        try {
            selected_quantity = Integer.parseInt(binding.spnQuantity.getText().toString());
        }catch (Exception e){
            Timber.e(e);
        }
        String note = binding.etNote.getText().toString().trim();

        if (TextUtils.isEmpty(product_name)) {
            binding.etProductName.setError("Product name is required", customErrorDrawable);
            binding.etProductName.requestFocus();
        } else if (TextUtils.isEmpty(price)) {
            binding.etPrice.setError("Price is required", customErrorDrawable);
            binding.etPrice.requestFocus();
        } else if (!priceValidation(price)) {
            binding.etPrice.setError("Invalid input", customErrorDrawable);
            binding.etPrice.requestFocus();
        } else if (selected_quantity <= 0) {
            binding.spnQuantity.setError("Quantity  is required", customErrorDrawable);
            binding.spnQuantity.setTextColor(Color.RED);//just to highlight that this is an error
            //errorText.setText(R.string.quantity_required_label);//changes the selected item text to this

        } else if (TextUtils.isEmpty(note)) {
            binding.etNote.setError("A brief note will help you remember a transaction", customErrorDrawable);
            binding.etNote.requestFocus();
        } else if (TextUtils.isEmpty(custName)) {
            binding.edtCustomerName.setError("Customer name is required", customErrorDrawable);
            binding.edtCustomerName.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    static class AutoCompleteTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() % 3 == 0 && s.length() <= 12) {
                String query = s.toString();
                Log.e("onClick: ", "-- " + query);
                //onSearch(query);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        disposable.clear();
    }
}