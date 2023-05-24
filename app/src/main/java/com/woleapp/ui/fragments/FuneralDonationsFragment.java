package com.woleapp.ui.fragments;

import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_ICC;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_MAG;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_PICC;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent;
import com.netpluspay.netpossdk.emv.CardReadResult;
import com.netpluspay.netpossdk.emv.CardReaderEvent;
import com.netpluspay.netpossdk.emv.CardReaderService;
import com.woleapp.R;
import com.woleapp.adapters.NothingSelectedSpinnerAdapter;
import com.woleapp.databinding.LayoutFuneralDonationsBinding;
import com.woleapp.db.Injection;
import com.woleapp.db.UserViewModel;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.CardData;
import com.woleapp.model.User;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.network.AgencyBankingService;
import com.woleapp.network.TokenInterceptor;
import com.woleapp.ui.activity.HomeActivity;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class FuneralDonationsFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutFuneralDonationsBinding binding;
    Utilities utilities;
    ProgressDialog mProgressDialog;
    AgencyUser user;
    CardReadResult readResult;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Gson gson = new Gson();
    String[] networkTypes;
    String[] transactionTypes;
    private String networkType = "";
    private String transactionType = "";
    Drawable customErrorDrawable;
    String currency_symbol = "";
    int len = 0;
    private static String transactionId = "";
    private static String merchantId = "";
    private static String amount = "";
    private static String accountName = "";
    private static String networkIssuer = "";
    private static String phoneNumber = "";
    private static String ref = "";
    private static String payment_mode = "";
    String month = "";
    String year = "";
    String card_number = "";
    String card_expiry = "";
    String card_cvv = "";
    String token = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);

        networkTypes = context.getResources().getStringArray(R.array.network_type);
        transactionTypes = context.getResources().getStringArray(R.array.payment_type);
        user = SharedPrefManager.getAgencyUser();
        Log.e("AgencyData", "user" + user);
        ((HomeActivity) getActivity()).setTitleWithNoNavigation("Dashboard");

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);

        generateToken();
        setNetworkSpinner();
        setModeOfTransaction();
        addTextChangeListener();

        binding.cashCollection.setOnClickListener(this);
        binding.cardCollection.setOnClickListener(this);
        binding.readCard.setOnClickListener(this);

        binding.accountNumber.setVisibility(View.GONE);
        binding.networkSpinner.setVisibility(View.GONE);
        binding.accountNumber.setVisibility(View.GONE);
        binding.accountName.setVisibility(View.GONE);
        binding.etCVV.setVisibility(View.GONE);
        binding.cardNumber.setVisibility(View.GONE);
        binding.cardExpiry.setVisibility(View.GONE);
        binding.readCard.setVisibility(View.GONE);
        binding.tvConvinienceFee.setVisibility(View.GONE);
        binding.cashCollection.setVisibility(View.GONE);
        binding.cardCollection.setVisibility(View.GONE);
        binding.readCard.setVisibility(View.GONE);


        binding.etCVV.setTypeface(Typeface.DEFAULT);
        binding.etCVV.setTransformationMethod(new PasswordTransformationMethod());


        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
        Float convenience_fee = SharedPrefManager.getTransfeeConvenienceFee();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_funeral_donations, container, false);
        return binding.getRoot();
    }
    public void onClick(View v){
        if(v == binding.cashCollection){
            validateMomoCollection();
        }
        else if(v == binding.readCard){
            validateCardCollections();
        }
        else if(v == binding.cardCollection){
            String readMonth = readResult.f;
            String[] parts = readMonth.split("D");
            month = parts[1].substring(2,4);
            year = parts[1].substring(0, 2);
            String cardDateNew = month + "/" + year;
            processCardCollections(merchantId, amount, payment_mode, accountName, readResult.d, cardDateNew, card_cvv);
        }
    }
    public void validateMomoCollection(){
        merchantId = binding.uid.getText().toString().trim();
        amount = binding.etAmt.getText().toString().trim();
        amount = amount.replace(currency_symbol, "").replaceAll(",", "");
        accountName = binding.accountName.getText().toString().trim();
        networkIssuer = networkType.toLowerCase();
        payment_mode = transactionType.toLowerCase();
        phoneNumber = binding.accountNumber.getText().toString().trim();

        if (TextUtils.isEmpty(merchantId)) {
            binding.uid.setError("Please enter Merchant ID", customErrorDrawable);
            binding.uid.requestFocus();
        } else if (TextUtils.isEmpty(amount)) {
            binding.etAmt.setError("Please enter the amount you want to deposit", customErrorDrawable);
            binding.etAmt.requestFocus();
        } else if (!priceValidation(amount)) {
            binding.etAmt.setError("Invalid input", customErrorDrawable);
            binding.etAmt.requestFocus();
        } else if (Double.parseDouble(amount) < 0 || Double.parseDouble(amount) > 100000) {
            binding.etAmt.setError("Amount should be between " + currency_symbol + 0 + " and " + currency_symbol + 100000, customErrorDrawable);
            binding.etAmt.requestFocus();
        } else if (TextUtils.isEmpty(accountName)) {
            binding.accountName.setError("Please enter the account name", customErrorDrawable);
            binding.accountName.requestFocus();
        } else if (networkType == null || networkType.isEmpty()) {
            Toast.makeText(context, "Please select Network type", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(phoneNumber)) {
            binding.accountNumber.setError("Mobile Number is required", customErrorDrawable);
            binding.accountNumber.requestFocus();
        } else if (phoneNumber.length() != 10) {
            binding.accountNumber.setError("Number should be 10 digits", customErrorDrawable);
            binding.accountNumber.requestFocus();
        }
        else{
            processMomoCollections(merchantId, amount, payment_mode, accountName, networkIssuer, phoneNumber);
        }
    }
    public void validateCardCollections(){
        merchantId = binding.uid.getText().toString().trim();
        amount = binding.etAmt.getText().toString().trim();
        amount = amount.replace(currency_symbol, "").replaceAll(",", "");
        accountName = binding.accountName.getText().toString().trim();
        card_number = binding.cardNumber.getText().toString().trim();
        card_expiry = binding.cardExpiry.getText().toString().trim();
        card_cvv = binding.etCVV.getText().toString().trim();
        String cardCVV = binding.etCVV.getText().toString().trim();

        if (TextUtils.isEmpty(merchantId)) {
            binding.uid.setError("Please enter Merchant ID", customErrorDrawable);
            binding.uid.requestFocus();
        } else if (TextUtils.isEmpty(amount)) {
            binding.etAmt.setError("Please enter the amount you want to deposit", customErrorDrawable);
            binding.etAmt.requestFocus();
        } else if (!priceValidation(amount)) {
            binding.etAmt.setError("Invalid input", customErrorDrawable);
            binding.etAmt.requestFocus();
        } else if (Double.parseDouble(amount) < 0 || Double.parseDouble(amount) > 100000) {
            binding.etAmt.setError("Amount should be between " + currency_symbol + 0 + " and " + currency_symbol + 100000, customErrorDrawable);
            binding.etAmt.requestFocus();
        } else if (TextUtils.isEmpty(accountName)) {
            binding.accountName.setError("Please enter the account name", customErrorDrawable);
            binding.accountName.requestFocus();
        } else if (TextUtils.isEmpty(cardCVV)) {
            binding.etCVV.setError("Please enter CVV number", customErrorDrawable);
            binding.etCVV.requestFocus();
        } else if (cardCVV.length() != 3) {
            binding.etCVV.setError("CVV number should be 3 digits", customErrorDrawable);
            binding.etCVV.requestFocus();
        }
        else{
            readCard();
        }
    }
    public void generateToken() {
        showProgressBar();
        String merchantID = "63b59e41530aeeaec59a045f";
        String apikey = "93064247-4668-4c73-ac43-4dcc28773a86";

        JsonObject object = new JsonObject();
        object.addProperty("merchantId", merchantID);
        object.addProperty("apikey", apikey);

        Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + object);
        Disposable subscribe = AgencyBankingAPIClient
                .collectionsInit(object, context)
                .subscribe(res -> {
                    if (res.code() != 200) {
                        Toast.makeText(context, "Failed to process transaction, Please try again", Toast.LENGTH_SHORT).show();
                        addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                        dismissProgressBar();
                        return;
                    }
                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("TRANSACTION_RESPONSE", "transaction response: " + response);
                    if (response.optString("success").equals("false")) {
                        Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                        addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                        dismissProgressBar();
                        return;
                    }
                    token = response.optString("data");
                    Toast.makeText(context, "Token Generated", Toast.LENGTH_SHORT).show();
                    Log.e("TOKEN", "token: " + token);
                    dismissProgressBar();
                }, err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                    addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                    dismissProgressBar();
                });

    }
    public void processMomoCollections(String merchantId, String amnt, String paymentType, String name, String issuer, String number){
        showProgressBar();

        String genToken = token;
        Log.e("TOKEN", "TOKEN" + genToken);

        String uid = merchantId;
        String amount = amnt;
        String paymentMode = paymentType;
        String account_name = name;
        String account_number = number;
        String account_issuer = issuer;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uid", uid);
        jsonObject.addProperty("amount", amount);
        jsonObject.addProperty("account_type", paymentMode);
        jsonObject.addProperty("account_name", account_name);
        jsonObject.addProperty("account_number", account_number);
        jsonObject.addProperty("account_issuer", account_issuer);

        Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + jsonObject);

        Disposable subscribe = retrofit.donationCollections(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    if (res.code() != 200) {
                        Toast.makeText(context, "Failed to process transaction", Toast.LENGTH_SHORT).show();
                        Log.e("process_error", res.message());
                        dismissProgressBar();
                        return;
                    }
                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("TRANSACTION_RESPONSE", "transaction response: " + response);
                    if (response.optString("success").equals("false")) {
                        Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    transactionId = response.optString("transactionId");
                    Log.e("TRANSACTION_ID_MOMO", "transaction response: " + transactionId);
                    addFragmentWithoutRemove(R.id.container_main, new TransactionStatusFragment(transactionId, token, name, number, ref, issuer, amount), TransactionStatusFragment.class.getSimpleName());
                    dismissProgressBar();
                }, err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                    dismissProgressBar();
                    Log.e("Error", "error " + err.getMessage());
                });
    }
    public void processCardCollections(String merchantId, String amnt, String paymentType, String name, String cardNumber, String cardExpiry, String cvv){
        showProgressBar();

        String genToken = token;
        Log.e("TOKEN", "TOKEN" + genToken);

        String uid = merchantId;
        String amount = amnt;
        String paymentMode = "card";
        String account_name = name;
        String card_number = cardNumber;
        String card_expiry = cardExpiry;
        String card_cvv = cvv;

        JsonObject card = new JsonObject();
        card.addProperty("cvc", card_cvv);
        card.addProperty("expiry", card_expiry);
        card.addProperty("number", card_number);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uid", uid);
        jsonObject.addProperty("amount", amount);
        jsonObject.addProperty("account_type", paymentMode);
        jsonObject.addProperty("account_name", account_name);
        jsonObject.add("card", card);

        Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + jsonObject);

        Disposable subscribe = retrofit.donationCollections(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    if (res.code() != 200) {
                        Toast.makeText(context, "Failed to process transaction", Toast.LENGTH_SHORT).show();
                        Log.e("process_error", res.message());
                        dismissProgressBar();
                        return;
                    }
                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("TRANSACTION_RESPONSE", "transaction response: " + response);
                    if (response.optString("success").equals("false")) {
                        Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                        dismissProgressBar();
                        return;
                    }
                    transactionId = response.optString("transactionId");
                    Log.e("TRANSACTION_ID_MOMO", "transaction response: " + transactionId);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", response.optString("redirectUrl"));
                    CardWebViewFragment redirecting = new CardWebViewFragment(transactionId,token,name,ref,amount);
                    redirecting.setArguments(bundle);
                    replaceFragmentWithBack(R.id.container_main, redirecting, CardWebViewFragment.class.getSimpleName());                    dismissProgressBar();
                }, err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                    dismissProgressBar();
                    Log.e("Error", "error " + err.getMessage());
                });
    }
    private void readCard() {
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
                            switch (mode) {
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
                            Toast.makeText(context, cardReadVia + " Card detected", Toast.LENGTH_LONG).show();
                        } else if (cardReaderEvent instanceof CardReaderEvent.CardRead) {
                            Toast.makeText(context, "Card Read", Toast.LENGTH_LONG).show();
                            readResult = ((CardReaderEvent.CardRead) cardReaderEvent).getData();
                            Timber.d("ENCRYPTED_PIN_BLOCK==>  " + readResult.getEncryptedPinBlock());

                            Timber.d("CARD_DATA I==>%s", gson.toJson(readResult));

                            CardData cardData = new CardData();
                            cardData.setCardNumber(readResult.d);
                            cardData.setCardExpiryDate(readResult.A);

                            Log.e("cardDetails", cardData + " ");

                            SharedPrefManager.setCardData(cardData);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("CARD_DATA_ERROR==>%s", e.getLocalizedMessage());
                        Timber.d("CARD_DATA_ERROR_2==>%s", e.getMessage());
                        Timber.d("CARD_DATA_ERROR_3==>%s", e.toString());
                        Toast.makeText(context, "Error Reading Card", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onComplete() {
                        String readMonth = readResult.f;
                        String[] parts = readMonth.split("D");
                        month = parts[1].substring(2,4);
                        year = parts[1].substring(0, 2);
                        binding.cardNumber.setText(readResult.d);
                        binding.cardExpiry.setText(month + "/" + year);
                        binding.readCard.setVisibility(View.GONE);
                        binding.cardCollection.setVisibility(View.VISIBLE);

                    }
                });
    }

    public void setModeOfTransaction(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.payment_type, R.layout.spinner_view_payment_mode);
        adapter.setDropDownViewResource(R.layout.spinner_view_payment_mode);
        binding.spnAccountType.setPrompt(context.getResources().getString(R.string.hint_choose_trans_type));

        binding.spnAccountType.setAdapter(
                // adapter
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_view_payment_mode,
                        getActivity()
                )
        );
        binding.spnAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View parent, int position, long l) {
                if (position > 0) {
                    //binding.spnIdType.setEnabled(false);
                    transactionType = transactionTypes[position - 1];

                    if(position == 1){
                        binding.networkSpinner.setVisibility(View.VISIBLE);
                        binding.accountNumber.setVisibility(View.VISIBLE);
                        binding.etCVV.setVisibility(View.GONE);
                        binding.cardNumber.setVisibility(View.GONE);
                        binding.cardExpiry.setVisibility(View.GONE);
                        binding.accountName.setVisibility(View.VISIBLE);
                        binding.cashCollection.setVisibility(View.VISIBLE);
                        binding.readCard.setVisibility(View.GONE);
                        binding.cardCollection.setVisibility(View.GONE);
                    }
                   else if (position == 2){
                        binding.networkSpinner.setVisibility(View.GONE);
                        binding.accountNumber.setVisibility(View.GONE);
                        binding.etCVV.setVisibility(View.VISIBLE);
                        binding.cardNumber.setVisibility(View.VISIBLE);
                        binding.cardExpiry.setVisibility(View.VISIBLE);
                        binding.accountName.setVisibility(View.VISIBLE);
                        binding.cashCollection.setVisibility(View.GONE);
                        binding.readCard.setVisibility(View.VISIBLE);
                        binding.cardCollection.setVisibility(View.GONE);
                    }
                    Log.e("selected_id", transactionType + "---");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
    private AgencyBankingService retrofit = new Retrofit.Builder()
            .baseUrl("http://test.peoplepay.com.gh:9000")
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AgencyBankingService.class);

    private OkHttpClient getClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);

        builder.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(request);
        });
        builder.connectTimeout(2, TimeUnit.MINUTES);
        builder.readTimeout(2, TimeUnit.MINUTES);
        builder.writeTimeout(2, TimeUnit.MINUTES);
        return builder.build();
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
