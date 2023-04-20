package com.woleapp.ui.fragments;

import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_ICC;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_MAG;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_PICC;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent;
import com.netpluspay.netpossdk.NetPosSdk;
import com.netpluspay.netpossdk.emv.CardReadResult;
import com.netpluspay.netpossdk.emv.CardReaderEvent;
import com.netpluspay.netpossdk.emv.CardReaderService;
import com.pos.sdk.printer.POIPrinterManage;
import com.pos.sdk.printer.models.BitmapPrintLine;
import com.pos.sdk.printer.models.PrintLine;
import com.pos.sdk.printer.models.TextPrintLine;
import com.woleapp.R;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.databinding.LayoutCashoutBinding;
import com.woleapp.model.CardData;
import com.woleapp.model.User;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.util.Constants;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CashoutFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutCashoutBinding binding;
    String[] IDs;
    private String ID = "";
    Drawable customErrorDrawable;
    User user;
    Utilities utilities;
    String currency_symbol = "";
    int len = 0;
    String amount = "";
    String name;
    String cvv;
    String idCard;
    double amt_dbl = 0.0;
    String idNum;
    String amnt;
    CardReadResult readResult;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Gson gson = new Gson();

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.clear();

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        //user = SharedPrefManager.getInstance(context).getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_cashout, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);
        binding.etCVV.setTypeface(Typeface.DEFAULT);
        binding.etCVV.setTransformationMethod(new PasswordTransformationMethod());

        setIdSpinner();

        setListeners();


    }
    public void setIdSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.idtype, R.layout.spinner_view_id_type);
        adapter.setDropDownViewResource(R.layout.item_drop_down_id);
        binding.spnIdType.setPrompt(context.getResources().getString(R.string.hint_choose_id_type));
        //binding.spnIdType.setSelection(1);

        binding.spnIdType.setAdapter(
                //adapter
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_id_type,
                        getActivity()
                )
        );
//        String selection = "National ID";
//        int spinnerPosition = adapter.getPosition(selection);
//        binding.spnIdType.setSelection(spinnerPosition);

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
    }


    public void onClick(View v) {
        Float convenienceFee = SharedPrefManager.getTransfeeConvenienceFee();
        String availableBalance = SharedPrefManager.getBalanceDetails().getBalance();
        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);

        // String amount = binding.etAmt.getText().toString().trim();
        String idNumber = binding.etIDNo.getText().toString().trim();
        String customerName = binding.etName.getText().toString().trim();
        String cardCVV = binding.etCVV.getText().toString().trim();

        if(v==binding.cardCashCollection){
            amount = binding.etAmt.getText().toString();
            amount = amount.replace(currency_symbol, "").replaceAll(",", "");
            if(TextUtils.isEmpty(amount)){
                binding.etAmt.setError("Amount is required", customErrorDrawable);
                binding.etAmt.requestFocus();
            }
            else if(ID == null || ID.isEmpty()){
                Toast.makeText(context, "Please select ID",Toast.LENGTH_LONG).show();
            }
            else if(TextUtils.isEmpty(idNumber)){
                binding.etIDNo.setError("ID Number is required", customErrorDrawable);
                binding.etIDNo.requestFocus();
            } else if(TextUtils.isEmpty(customerName)){
                binding.etName.setError("Name is required", customErrorDrawable);
                binding.etName.requestFocus();
            }
            else if(TextUtils.isEmpty(cardCVV)){
                binding.etCVV.setError("Please enter CVV number", customErrorDrawable);
                binding.etCVV.requestFocus();
            }
            else if(cardCVV.length() != 3){
                binding.etCVV.setError("CVV number should be 3 digits", customErrorDrawable);
                binding.etCVV.requestFocus();
            }
            else if (!priceValidation(amount)) {
                binding.etAmt.setError("Invalid input", customErrorDrawable);
                binding.etAmt.requestFocus();
            } else if (Double.parseDouble(amount) < 1 || Double.parseDouble(amount) > 100000) {
                // binding.etAmt.setError("Amount should be between \u20A610 and \u20A6100000", customErrorDrawable);
                binding.etAmt.setError("Amount should be between " + currency_symbol + 1 + " and " + currency_symbol +100000, customErrorDrawable);
                binding.etAmt.requestFocus();
            } else if (Double.parseDouble(amount) + convenienceFee > Double.parseDouble(availableBalance)) {
                binding.etAmt.setError("Insufficient balance to proceed", customErrorDrawable);
                binding.etAmt.requestFocus();
//                SharedPrefManager.getAgencyUser().getAvailableBalance())
            }
            else {
                // Toast.makeText(context, "Failed to get banks", Toast.LENGTH_SHORT).show();
//                Log.d("CONTINUE", "continue");
//                Bundle b1 = new Bundle();
//                b1.putString("amount", binding.etAmt.getText().toString());
//                String amount = binding.etAmt.getText().toString();
//                b1.putString("amount", amount);
//                String name = binding.etName.getText().toString().trim();
//                b1.putString("name", name);
//                String idCard = ID;
//                b1.putString("idCard", idCard);
//                String idNum = binding.etIDNo.getText().toString().trim();
//                b1.putString("idNum", idNum);
//                b1.putString("convenience_fee", binding.etFee.getText().toString());
//                b1.putInt("transaction_type", 2);
                readCard();
                //PaymentModeFragment paymentModeFragment = new PaymentModeFragment();
                //paymentModeFragment.setArguments(b1);
//                CardDetailsFragment cardDetailsFragment = new CardDetailsFragment();
//                cardDetailsFragment.setArguments(b1);
                //addFragmentWithoutRemove(R.id.container_main, paymentModeFragment, PaymentModeFragment.class.getSimpleName());
                // addFragmentWithoutRemove(R.id.container_main, cardDetailsFragment, PaymentModeFragment.class.getSimpleName());
                hideKeyBoard();
            }
        }

    }
    private void readCard(){
//        alertDialog.show();
        List<Integer> preferredModes = new ArrayList<>();
        preferredModes.add(DEV_ICC);
        preferredModes.add(DEV_PICC);
        new CardReaderService(getActivity(), preferredModes, 45)
                .initiateICCCardPayment(200, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CardReaderEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CardReaderEvent cardReaderEvent) {
                        if (cardReaderEvent instanceof CardReaderEvent.CardDetected) {
                            // Card Detected
                            int mode = ((CardReaderEvent.CardDetected) cardReaderEvent).getMode();
                            String cardReadVia = "";
                            switch (mode){
                                case DEV_ICC:
                                    cardReadVia = "Emv Chip";
                                    break;
                                case DEV_PICC:
                                    cardReadVia = "Contactless";
                                    break;
                                case DEV_MAG:
                                    cardReadVia = "Magnetic Stripe";
                                    break;
                            }
//                            alertDialog.dismiss();
                            Toast.makeText(context,  cardReadVia+" Card detected", Toast.LENGTH_LONG).show();
                        } else if (cardReaderEvent instanceof CardReaderEvent.CardRead) {
                            // Card Read
//                            alertDialog.dismiss();
                            Toast.makeText(context, "Card Read", Toast.LENGTH_LONG).show();
                            readResult = ((CardReaderEvent.CardRead) cardReaderEvent).getData();
                            Timber.d("ENCRYPTED_PIN_BLOCK==>  " + readResult.getEncryptedPinBlock());
                            // Calling makePayment method

                            Timber.d("CARD_DATA READ==>%s", gson.toJson(readResult));
                            Timber.d("CARD_DATA A==>%s", gson.toJson(readResult.A));
                            Timber.d("CARD_DATA B==>%s", gson.toJson(readResult.B));
                            Timber.d("CARD_DATA C==>%s", gson.toJson(readResult.C));
                            Timber.d("CARD_DATA D==>%s", gson.toJson(readResult.D));
                            Timber.d("CARD_DATA E==>%s", gson.toJson(readResult.E));
                            Timber.d("CARD_DATA F==>%s", gson.toJson(readResult.F));
                            Timber.d("CARD_DATA G==>%s", gson.toJson(readResult.G));
                            Timber.d("CARD_DATA H==>%s", gson.toJson(readResult.H));
                            Timber.d("CARD_DATA I==>%s", gson.toJson(readResult.I));
                            Timber.d("CARD_DATA a==>%s", gson.toJson(readResult.a));
                            Timber.d("CARD_DATA b==>%s", gson.toJson(readResult.b));
                            Timber.d("CARD_DATA c==>%s", gson.toJson(readResult.c));
                            Timber.d("CARD_DATA d==>%s", gson.toJson(readResult.d));
                            Timber.e("CARD_DATA e==>%s", gson.toJson(readResult.e));
                            Timber.d("CARD_DATA f==>%s", gson.toJson(readResult.f));
                            Timber.d("CARD_DATA g==>%s", gson.toJson(readResult.g));
                            Timber.d("CARD_DATA h==>%s", gson.toJson(readResult.h));
                            Timber.d("CARD_DATA i==>%s", gson.toJson(readResult.i));
                            Timber.d("CARD_DATA j==>%s", gson.toJson(readResult.j));
                            Timber.d("CARD_DATA k==>%s", gson.toJson(readResult.k));
                            Timber.d("CARD_DATA l==>%s", gson.toJson(readResult.l));
                            Timber.d("CARD_DATA m==>%s", gson.toJson(readResult.m));
                            Timber.d("CARD_DATA n==>%s", gson.toJson(readResult.n));
                            Timber.d("CARD_DATA o==>%s", gson.toJson(readResult.o));
                            Timber.d("CARD_DATA p==>%s", gson.toJson(readResult.p));
                            Timber.d("CARD_DATA q==>%s", gson.toJson(readResult.q));
                            Timber.d("CARD_DATA r==>%s", gson.toJson(readResult.r));
                            Timber.d("CARD_DATA s==>%s", gson.toJson(readResult.s));
                            Timber.d("CARD_DATA t==>%s", gson.toJson(readResult.t));
                            Timber.d("CARD_DATA u==>%s", gson.toJson(readResult.u));
                            Timber.d("CARD_DATA v==>%s", gson.toJson(readResult.v));
                            Timber.d("CARD_DATA w==>%s", gson.toJson(readResult.w));
                            Timber.d("CARD_DATA x==>%s", gson.toJson(readResult.x));
                            Timber.d("CARD_DATA y==>%s", gson.toJson(readResult.y));
                            Timber.d("CARD_DATA z==>%s", gson.toJson(readResult.z));
                            Timber.d("CARD_DATA whole==>%s", gson.toJson(readResult));
                            // Timber.d("CARD_DATA I==>%s", gson.toJson(readResult.Z));

                            CardData cardData = new CardData();
                            cardData.setCardNumber(readResult.d);
                            // cardData.setExpiry(readResult.A);
                            //cardData.setCvv(readResult.s);
                            cardData.setCardExpiryDate(readResult.A);

                            Log.e("cardDetails", cardData+" ");
                            name=binding.etName.getText().toString().trim();
                            idNum=binding.etIDNo.getText().toString().trim();
                            cvv=binding.etCVV.getText().toString().trim();
                            amount=binding.etAmt.getText().toString().trim();
                            amount = amount.replaceAll(",", "").replace(currency_symbol, "");
                            amt_dbl = Double.parseDouble(amount);
                            amnt = String.valueOf(amt_dbl);
                            idCard=binding.spnIdType.getSelectedItem().toString().trim();
                            String deviceID = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

                            SharedPrefManager.setCardData(cardData);
                            //addFragmentWithoutRemove(R.id.container_main, new CardDetailsFragment(), CardDetailsFragment.class.getSimpleName());
                            processCashoutTransactions(name, idCard, idNum, Double.parseDouble(amnt), readResult.d, cvv, readResult.A, deviceID);


                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        alertDialog.dismiss();
                        Timber.d("CARD_DATA_ERROR==>%s", e.getLocalizedMessage());
                        Timber.d("CARD_DATA_ERROR_2==>%s", e.getMessage());
                        Timber.d("CARD_DATA_ERROR_3==>%s", e.toString());
                        Toast.makeText(context, "Error Reading Card", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
//                        alertDialog.dismiss();
                        //Toast.makeText(context, "Done Reading Card", Toast.LENGTH_LONG).show();

                    }
                });
    }
   /* private void readCard() {
//        alertDialog.show();
        List<Integer> preferredModes = new ArrayList<>();
        preferredModes.add(DEV_ICC);
        preferredModes.add(DEV_PICC);
        new CardReaderService(getActivity(), preferredModes, 45)
                .initiateICCCardPayment(200, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CardReaderEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CardReaderEvent cardReaderEvent) {
                        if (cardReaderEvent instanceof CardReaderEvent.CardDetected) {
                            // Card Detected
                            int mode = ((CardReaderEvent.CardDetected) cardReaderEvent).getMode();
                            String cardReadVia = "";
                            switch (mode){
                                case DEV_ICC:
                                    cardReadVia = "Emv Chip";
                                    break;
                                case DEV_PICC:
                                    cardReadVia = "Contactless";
                                    break;
                                case DEV_MAG:
                                    cardReadVia = "Magnetic Stripe";
                                    break;
                            }
//                            alertDialog.dismiss();
                            Toast.makeText(context,  cardReadVia+" Card detected", Toast.LENGTH_LONG).show();
                        } else if (cardReaderEvent instanceof CardReaderEvent.CardRead) {
                            // Card Read
//                            alertDialog.dismiss();
                            Toast.makeText(context, "Card Read", Toast.LENGTH_LONG).show();
                            readResult = ((CardReaderEvent.CardRead) cardReaderEvent).getData();
                            Timber.d("ENCRYPTED_PIN_BLOCK==>  " + readResult.getEncryptedPinBlock());
                            // Calling makePayment method

                            CardData cardData = new CardData();
                            cardData.setCardNumber(readResult.d);
                            // cardData.setExpiry(readResult.A);
                            //cardData.setCvv(readResult.s);
                            cardData.setCardExpiryDate(readResult.A);

                            Log.e("cardDetails", cardData+" ");
                            name=binding.etName.getText().toString().trim();
                            idNum=binding.etIDNo.getText().toString().trim();
                            cvv=binding.etCVV.getText().toString().trim();
                            amount=binding.etAmt.getText().toString().trim();
                            amount = amount.replaceAll(",", "").replace(currency_symbol, "");
                            amt_dbl = Double.parseDouble(amount);
                            amnt = String.valueOf(amt_dbl);
                            idCard=binding.spnIdType.getSelectedItem().toString().trim();
                            String deviceID = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

                            SharedPrefManager.setCardData(cardData);
                            //addFragmentWithoutRemove(R.id.container_main, new CardDetailsFragment(), CardDetailsFragment.class.getSimpleName());
                            processCashoutTransactions(name, idCard, idNum, Double.parseDouble(amnt), readResult.d, cvv, readResult.A, deviceID);




                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        alertDialog.dismiss();
                        Timber.d("CARD_DATA_ERROR==>%s", e.getLocalizedMessage());
                        Timber.d("CARD_DATA_ERROR_2==>%s", e.getMessage());
                        Timber.d("CARD_DATA_ERROR_3==>%s", e.toString());
                        Toast.makeText(context, "Error Reading Card", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
//                        alertDialog.dismiss();
                        Toast.makeText(context, "Done Reading Card", Toast.LENGTH_LONG).show();
                    }
                });
    }*/
    private void processCashoutTransactions(String name, String idCard, String idNo, double amt, String account,String cvv, String card_date_expiry, String deviceID) {
        //payload
        String officer_ID = "13";
        String branch_code = "1";
        String inst_ID = "1";
        String branch_key = SharedPrefManager.getBranchDetails().getBranchKey();
        String agency_key = SharedPrefManager.getAgencyDetails().getAgentKey();
        String trans_cat = "1";
        String trans_type = "2";
        double total_amnt = amt;
        String narration = "Test Narration";
        String customer_name = name;
        String account_number = account;
        String customer_cvv = cvv;
        String cardExpiryDate = card_date_expiry;
        String device_ID = deviceID;

        String depositor_payee = name;
        String ID_type = idCard;
        String ID_number = idNo;

        int branchID = SharedPrefManager.getAgencyUser().getUserBranch();
        int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();
        String branchName = SharedPrefManager.getBranchDetails().getBranchName();
        String a_name = SharedPrefManager.getAgencyDetails().getAgencyName();

        JsonObject agency = new JsonObject();
        agency.addProperty("agent_id", agencyID);
        agency.addProperty("branch_id", branchID);
        agency.addProperty("branch_key", branch_key);
        agency.addProperty("agent", branch_key);
        agency.addProperty("agent_key", agency_key);

        JsonObject transaction = new JsonObject();
        transaction.addProperty("trans_cat", Integer.parseInt(trans_cat));
        transaction.addProperty("trans_type", Integer.parseInt(trans_type));
        transaction.addProperty("amount", Double.parseDouble(String.valueOf(total_amnt)));
        transaction.addProperty("narration", narration);
        transaction.addProperty("id_type", ID_type);
        transaction.addProperty("id_number", ID_number);
        transaction.addProperty("depositor_payee", depositor_payee);
        transaction.addProperty("customer_name", customer_name);
        transaction.addProperty("pos_dna",device_ID);
        transaction.addProperty("product_ID", 6);

        JsonObject customer = new JsonObject();
        customer.addProperty("account_name", customer_name);
        customer.addProperty("account_number", account_number);
        customer.addProperty("cvv", customer_cvv);
        customer.addProperty("card_expiry_date", cardExpiryDate);

        JsonObject teller = new JsonObject();
        teller.addProperty("teller_id", Integer.parseInt(officer_ID));

        JsonObject meta = new JsonObject();
        meta.addProperty("mobile_transaction_reference", "521wd542d86");

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("Agent_Details", agency);
        jsonObject.add("transaction", transaction);
        jsonObject.add("account_details", customer);
        jsonObject.add("teller_details", teller);
        jsonObject.add("meta_data", meta);
        Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + jsonObject);
        Disposable subscribe = AgencyBankingAPIClient
                .sendTransaction(jsonObject, context)
                .subscribe(res -> {
                    if(res.code() != 200) {
                        Toast.makeText(context, "Failed to process transaction", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("TRANSACTION_RESPONSE", "transaction response: " + response);
                    if(response.optInt("code") != 112) {
                        Toast.makeText(context, response.optString("msg"), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String ref = response.optString("ref");
                   // PaymentSuccessfulFragment paymentSuccessfulFragment = new PaymentSuccessfulFragment();
                   // addFragmentWithoutRemove(R.id.container_main, paymentSuccessfulFragment, PaymentSuccessfulFragment.class.getSimpleName());
                    printReceipt(customer_name, ID_type, ID_number, a_name, branchName ,ref);

                    //process transaction
                },err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                });

    }

    public void setListeners() {
        binding.etFee.setKeyListener(null);
        Float convenience_fee = SharedPrefManager.getPOSConvenienceFee();
        Log.d("Fundwalet", "convenience fee is " + convenience_fee);
        binding.etFee.setText(utilities.getFormattedAmount(convenience_fee));

        binding.cardCashCollection.setOnClickListener(this);

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
                                        if (amt < 1) {
                                            binding.etAmt.setError("Minimum amount of transaction is " + currency_symbol + 1, customErrorDrawable);
                                            //binding.etPrice.requestFocus();
                                        } else {
                                            binding.etAmt.setError(null);
                                            String amt1 = utilities.getFormattedAmount(amt);
                                            binding.etAmt.setText(amt1);
                                            float finalAmt = (Float.parseFloat(text) * convenience_fee) / 100;
                                            binding.etFee.setText(utilities.getFormattedAmount(Double.parseDouble(Float.toString(finalAmt))));
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
    private void printReceipt(String CustomerName, String ID, String IDnum, String a_name, String branchName, String ref) {
        POIPrinterManage printerManage = NetPosSdk.getPrinterManager(context);
        printerManage.setLineSpace(2);
        printerManage.setPrintGray(3000);
        printerManage.cleanCache();

        Bitmap bitmap =
                BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.bsyslogo);
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 100, false);

        BitmapPrintLine bitmapPrintLine = new BitmapPrintLine();
        bitmapPrintLine.setType(PrintLine.BITMAP);
        bitmapPrintLine.setBitmap(bitmap);
        bitmapPrintLine.setPosition(PrintLine.CENTER);

        TextPrintLine textPrintLine = new TextPrintLine();
        textPrintLine.setType(PrintLine.TEXT);
        textPrintLine.setContent("Agency Banking");
        textPrintLine.setBold(true);
        textPrintLine.setItalic(false);
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(TextPrintLine.FONT_LARGE);

        printerManage.addPrintLine(bitmapPrintLine);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("----------------------------------------------------" +
                "--------------------------------------------");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(utilities.getTodaysDate() + "    " + utilities.getTodaysTime());
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(" ");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Ref No.: " + ref);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Transaction Type: CASH-OUT");
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Amount: " + utilities.getFormattedAmount(amt_dbl));
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("ID Type: " + ID);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("ID Number: " + IDnum);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Customer Name: " + CustomerName);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Agent Name: " + a_name);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Branch Name: " + branchName);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(" ");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Transaction Successful");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("--------------------------------------------------------------");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("THANK YOU");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Powered by Bsystems Limited");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("0302-254-340");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        printerManage.beginPrint(new POIPrinterManage.IPrinterListener() {
            @Override
            public void onError(int i, String s) {
                Timber.e("Printer error with code " + i + " and message" + s);
                Toast.makeText(context, "Printer Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
              //  Toast.makeText(context, "Printing job finished", Toast.LENGTH_SHORT).show();
                addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
            }

            @Override
            public void onStart() {
                Toast.makeText(context, "Printing job started", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
