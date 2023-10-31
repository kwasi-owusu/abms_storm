package com.woleapp.ui.fragments;

import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_ICC;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_MAG;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_PICC;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.woleapp.databinding.LayoutCollectionsNewBinding;
import com.woleapp.db.Injection;
import com.woleapp.db.UserViewModel;
import com.woleapp.model.Agency;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.Balance;
import com.woleapp.model.Branch;
import com.woleapp.model.CardData;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.network.AgencyBankingService;
import com.woleapp.network.TokenInterceptor;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.reactivex.Observable;
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

public class CollectionsNewFragment extends BaseFragment implements View.OnClickListener {
    Context context;
    AgencyUser user;
    private LayoutCollectionsNewBinding binding;
    Utilities utilities;
    ProgressDialog mProgressDialog;
    private UserViewModel mViewModel;
    Drawable customErrorDrawable;
    String currency_symbol = "";
    int len = 0;
    Bitmap bitmap;
    String amount = "";
    String chargedAmount = "";
    String chargedAmountVisa = "";
    CardReadResult readResult;
    private String networkType = "";
    String[] networkTypes;
    boolean hasSigned = false;
    private static String token = "";
    private static String transactionId = "";
    private static String cardTransactionId = "";
    private static String ref = "";
    private static String name = "";
    private static String number = "";
    private static String issuer = "";
    String month = "";
    String year = "";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Gson gson = new Gson();
    LinearLayout mContent;
    Signature mSignature;
    private Timer timer;
    private final int INTERVAL = 3000;
    long maxWaitTime = 2 * 60 * 1000; // 2 minutes

    long startTime = System.currentTimeMillis();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);

        networkTypes = context.getResources().getStringArray(R.array.network_type);
        user = SharedPrefManager.getAgencyUser();
        Log.e("AgencyData", "user" + user);
        ((HomeActivity) getActivity()).setTitleWithNoNavigation("Dashboard");

        mViewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(getActivity()))
                .get(UserViewModel.class);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);

        setNetworkSpinner();
        prepareCanvas();

        binding.cashCollection.setOnClickListener(this);
        binding.cardCollection.setOnClickListener(this);
        binding.readCard.setOnClickListener(this);
        binding.tvRetake.setOnClickListener(this);

        binding.cashCollection.setVisibility(View.VISIBLE);
        binding.cardCollection.setVisibility(View.GONE);
        binding.readCard.setVisibility(View.GONE);
        binding.etCVV.setVisibility(View.GONE);
        binding.cardNumber.setVisibility(View.GONE);
        binding.cardExpiry.setVisibility(View.GONE);
        binding.readCard.setVisibility(View.GONE);
        binding.etFee.setVisibility(View.GONE);
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
                    binding.etCVV.setVisibility(View.GONE);
                    binding.accountNumber.setVisibility(View.VISIBLE);
                    binding.networkSpinner.setVisibility(View.VISIBLE);
                    binding.cardNumber.setVisibility(View.GONE);
                    binding.cardExpiry.setVisibility(View.GONE);
                    binding.readCard.setVisibility(View.GONE);
                } else if (binding.cardPayment.isChecked()) {
                    binding.cashCollection.setVisibility(View.GONE);
                    binding.cardCollection.setVisibility(View.GONE);
                    binding.etCVV.setVisibility(View.VISIBLE);
                    binding.accountNumber.setVisibility(View.GONE);
                    binding.networkSpinner.setVisibility(View.GONE);
                    binding.cardNumber.setVisibility(View.VISIBLE);
                    binding.cardExpiry.setVisibility(View.VISIBLE);
                    binding.cardNumber.setEnabled(false);
                    binding.cardExpiry.setEnabled(false);
                    binding.readCard.setVisibility(View.VISIBLE);
                }
            }
        });

        customErrorDrawable = context.getResources().getDrawable(R.drawable.error_small);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
        Float convenience_fee = SharedPrefManager.getTransfeeConvenienceFee();
        binding.etFee.setText(utilities.getFormattedAmount(convenience_fee));
        addTextChangeListener();
        processToken();


       // int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();

       // JsonObject jsonObject = new JsonObject();
   //     jsonObject.addProperty("agency_ID", agencyID);

     //   getAgencyDetails(jsonObject);
       // Log.e("RES_CODE", "Payload: " + jsonObject);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_collections_new, container, false);
        return binding.getRoot();
    }

    public void onClick(View v) {

        if (v == binding.cashCollection) {
            validateCashCollections();
        } else if (v == binding.readCard) {
            validateCardCollections();
        } else if (v == binding.linearCanvas) {
            showSignaturePad();
        } else if (v == binding.tvRetake) {
            mSignature.clear();
            hasSigned = false;
        } else if (v == binding.cardCollection) {
            String name = binding.payeeName.getText().toString().trim();
            String cvv = binding.etCVV.getText().toString().trim();
            String readMonth = readResult.f;
            String[] parts = readMonth.split("D");
            month = parts[1].substring(2,4);
            year = parts[1].substring(0, 2);
            String cardDateNew = month + "/" + year;
            chargedAmountVisa = String.valueOf(Double.parseDouble(amount) + 0.025 * Double.parseDouble(amount));
           // Log.e("Charged Amount", chargedAmountVisa);
            processCardCollections(amount, name, cvv, readResult.d, cardDateNew);
        }
    }

    public void validateCashCollections() {
        Float convenienceFee = SharedPrefManager.getTransfeeConvenienceFee();

       // String availableBalance = SharedPrefManager.getBalanceDetails().getBalance();
         name = binding.payeeName.getText().toString().trim();
         number = binding.accountNumber.getText().toString().trim();
         issuer = networkType.toLowerCase();

        amount = binding.etAmt.getText().toString();
        amount = amount.replace(currency_symbol, "").replaceAll(",", "");
        chargedAmount = String.valueOf(Double.parseDouble(amount) + 0.01 * Double.parseDouble(amount));

        if (TextUtils.isEmpty(amount)) {
            binding.etAmt.setError("Please enter the amount you want to deposit", customErrorDrawable);
            binding.etAmt.requestFocus();
        } else if (TextUtils.isEmpty(name)) {
            binding.payeeName.setError("Name is required", customErrorDrawable);
            binding.payeeName.requestFocus();
        } else if (!priceValidation(amount)) {
            binding.etAmt.setError("Invalid input", customErrorDrawable);
            binding.etAmt.requestFocus();
        } else if (Double.parseDouble(amount) < 0 || Double.parseDouble(amount) > 100000) {
            // binding.etAmt.setError("Amount should be between \u20A610 and \u20A6100000", customErrorDrawable);
            binding.etAmt.setError("Amount should be between " + currency_symbol + 0 + " and " + currency_symbol + 100000, customErrorDrawable);
            binding.etAmt.requestFocus();
       // } else if (Double.parseDouble(amount) + convenienceFee > Double.parseDouble(availableBalance)) {
         //   binding.etAmt.setError("Insufficient balance to proceed", customErrorDrawable);
           // binding.etAmt.requestFocus();
        } else if (networkType == null || networkType.isEmpty()) {
            Toast.makeText(context, "Please select Network type", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(number)) {
            binding.accountNumber.setError("Number is required", customErrorDrawable);
            binding.accountNumber.requestFocus();
        } else if (number.length() != 10) {
            binding.accountNumber.setError("Number should be 10 digits", customErrorDrawable);
            binding.accountNumber.requestFocus();
        } else {
            //Log.e("Total amount", chargedAmount);
            processCollections(chargedAmount, name, number, issuer);
        }
    }

    public void validateCardCollections() {
        Float convenienceFee = SharedPrefManager.getTransfeeConvenienceFee();

       // String availableBalance = SharedPrefManager.getBalanceDetails().getBalance();
        String name = binding.payeeName.getText().toString().trim();
        String cardCVV = binding.etCVV.getText().toString().trim();

        amount = binding.etAmt.getText().toString();
        amount = amount.replace(currency_symbol, "").replaceAll(",", "");
        if (TextUtils.isEmpty(amount)) {
            binding.etAmt.setError("Please enter the amount you want to deposit", customErrorDrawable);
            binding.etAmt.requestFocus();
        } else if (TextUtils.isEmpty(name)) {
            binding.payeeName.setError("Name is required", customErrorDrawable);
            binding.payeeName.requestFocus();
        } else if (!priceValidation(amount)) {
            binding.etAmt.setError("Invalid input", customErrorDrawable);
            binding.etAmt.requestFocus();
        } else if (Double.parseDouble(amount) < 0 || Double.parseDouble(amount) > 100000) {
            // binding.etAmt.setError("Amount should be between \u20A610 and \u20A6100000", customErrorDrawable);
            binding.etAmt.setError("Amount should be between " + currency_symbol + 0 + " and " + currency_symbol + 100000, customErrorDrawable);
            binding.etAmt.requestFocus();
       // } else if (Double.parseDouble(amount) + convenienceFee > Double.parseDouble(availableBalance)) {
         //   binding.etAmt.setError("Insufficient balance to proceed", customErrorDrawable);
           // binding.etAmt.requestFocus();
        } else if (TextUtils.isEmpty(cardCVV)) {
            binding.etCVV.setError("Please enter CVV number", customErrorDrawable);
            binding.etCVV.requestFocus();
        } else if (cardCVV.length() != 3) {
            binding.etCVV.setError("CVV number should be 3 digits", customErrorDrawable);
            binding.etCVV.requestFocus();
        }
        else {
            readCard();
        }
    }
    public void processCollections(String amnt, String name, String number, String issuer) {
        showProgressBar();

        String genToken = token;
        Log.e("TOKEN", "TOKEN" + genToken);

        String amount = amnt;
        String account_name = name;
        String account_number = number;
        String account_issuer = issuer;
        String description = "Collections";
        String callbackUrl = " ";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", amount);
        jsonObject.addProperty("account_name", account_name);
        jsonObject.addProperty("account_number", account_number);
        jsonObject.addProperty("account_issuer", account_issuer);
        jsonObject.addProperty("description", description);
        jsonObject.addProperty("callbackUrl", callbackUrl);

        Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + jsonObject);

        Disposable subscribe = retrofit.momoInit(jsonObject)
               // .doOnSubscribe(disposable -> showProgressBar())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    if (res.code() != 200) {
                        Toast.makeText(context, "Failed to process transaction", Toast.LENGTH_SHORT).show();
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
                    addFragmentWithoutRemove(R.id.container_main, new TransactionStatusFragment(transactionId, token, name, number, ref, issuer, chargedAmount), TransactionStatusFragment.class.getSimpleName());
                    dismissProgressBar();
                    //transactionStatus();
                }, err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                    dismissProgressBar();
                    Log.e("Error", "error " + err.getMessage());
                });
    }

    public void processCardCollections(String amnt, String name, String cardCvv, String cardNumber, String expiryDate) {
        showProgressBar();
        String amount = amnt;
        String account_name = name;
        String description = "Card Payment";
        String callbackUrl = " ";
        String clientRedirectUrl = "https://google.com";
        String cvv = cardCvv;
        String number = cardNumber;
        String expiry = expiryDate;

        JsonObject card = new JsonObject();
        card.addProperty("cvc", cvv);
        card.addProperty("expiry", expiry);
        card.addProperty("number", number);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", amount);
        jsonObject.addProperty("account_name", account_name);
        jsonObject.addProperty("description", description);
        jsonObject.addProperty("callbackUrl", callbackUrl);
        jsonObject.addProperty("clientRedirectUrl", clientRedirectUrl);
        jsonObject.add("card", card);

        Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + jsonObject);

        Disposable subscribe = retrofit.cardInit(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    if (res.code() != 200) {
                        Toast.makeText(context, "Failed to process transaction", Toast.LENGTH_SHORT).show();
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
                    cardTransactionId= response.optString("transactionId");
                    Bundle bundle = new Bundle();
                    bundle.putString("url", response.optString("redirectUrl"));
                    CardWebViewFragment redirecting = new CardWebViewFragment(cardTransactionId,token,name,ref,amount);
                    redirecting.setArguments(bundle);
                    replaceFragmentWithBack(R.id.container_main, redirecting, CardWebViewFragment.class.getSimpleName());
                    //addFragmentWithoutRemove(R.id.container_main, new CardWebViewFragment(), CardWebViewFragment.class.getSimpleName());
                    //transactionStatus();
                    dismissProgressBar();

                    Log.e("TRANSACTION_ID_CARD", "transaction response: " + cardTransactionId);
                }, err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                    dismissProgressBar();
                    Log.e("Error", "error " + err.getMessage());
                });

    }

    public void processToken() {
        showProgressBar();
       // String merchantID = "63b59e41530aeeaec59a045f";
        //String apikey = "93064247-4668-4c73-ac43-4dcc28773a86";
        String merchantID = "6538f84ce043fa5bfc4d49df";
        String apikey = "3dc3c9c8-be4e-49fb-831b-ce64ab571909";

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
                   // TransactionStatusFragment transactionStatusFragment = new TransactionStatusFragment(token, token);

                    dismissProgressBar();
                }, err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                    addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                    dismissProgressBar();
                });

    }

    private void readCard() {
//        alertDialog.show();
        List<Integer> preferredModes = new ArrayList<>();
        preferredModes.add(DEV_ICC);
        preferredModes.add(DEV_PICC);
        //  new CardReaderService(null, preferredModes, 45)
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
//                            alertDialog.dismiss();
                            Toast.makeText(context, cardReadVia + " Card detected", Toast.LENGTH_LONG).show();
                        } else if (cardReaderEvent instanceof CardReaderEvent.CardRead) {
                            // Card Read
//                            alertDialog.dismiss();
                            Toast.makeText(context, "Card Read", Toast.LENGTH_LONG).show();
                            readResult = ((CardReaderEvent.CardRead) cardReaderEvent).getData();
                            Timber.d("ENCRYPTED_PIN_BLOCK==>  " + readResult.getEncryptedPinBlock());
                            // Calling makePayment method

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
//                        alertDialog.dismiss();
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

                        //processCardCollections(amount, name, cvv, readResult.d,readResult.A);
//                        alertDialog.dismiss();
                        //Toast.makeText(context, "Done Reading Card", Toast.LENGTH_LONG).show();

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

    public void addTextChangeListener() {

        binding.etFee.setKeyListener(null);

        RxTextView.afterTextChangeEvents(this.binding.etAmt)
                .skip(1)
                .debounce(1300, TimeUnit.MILLISECONDS)
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

    private AgencyBankingService retrofit = new Retrofit.Builder()
            .baseUrl("https://peoplespay.com.gh")
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AgencyBankingService.class);

    public static OkHttpClient getHttpClient(Context context, boolean addTokenInterceptor) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(interceptor);
        if(addTokenInterceptor)
            builder.addInterceptor(new TokenInterceptor());
        builder.connectTimeout(2, TimeUnit.MINUTES);
        builder.readTimeout(2, TimeUnit.MINUTES);
        builder.writeTimeout(2, TimeUnit.MINUTES);
        final OkHttpClient okHttpClient = builder.build();

        return okHttpClient;
    }

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

    public void getAgencyDetails(JsonObject payload) {
        Disposable subscribe = AgencyBankingAPIClient
                .agencyDetails(payload, context)
                .subscribe(res -> {
                    if (res.code() != 200) {
                        Toast.makeText(context, "Failed to get Agency Details", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("RES_CODE", "Agency: " + response);
                    if (response.optInt("code") != 112) {
                        Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Agency agency = new Agency();
                    agency.setAgencyCode(response.optJSONObject("agency_data").optString("agency_code"));
                    agency.setAgencyName(response.optJSONObject("agency_data").optString("agency_name"));
                    agency.setAgentKey(response.optJSONObject("agency_data").optString("agency_key"));
                    agency.setAgencyStatus(response.optJSONObject("agency_data").optString("agency_status"));

                    SharedPrefManager.setAgencyDetails(agency);

                    //call function to get branch details
                    int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();
                    int branchID = SharedPrefManager.getAgencyUser().getUserBranch();
                    JsonObject jsonObject = new JsonObject();

                    jsonObject.addProperty("agency_id", agencyID);
                    jsonObject.addProperty("branch_id", branchID);

                    Log.e("RES_CODE", "Branch payload: " + jsonObject);

                    getBranchDetails(jsonObject);


                }, err -> {
                    Toast.makeText(context, "An unexpected error occured", Toast.LENGTH_LONG).show();
                    Log.e("ErrorTimeout", "error " + err.getMessage());
                });
    }

    private void getBranchDetails(JsonObject payload) {
        Disposable subscribe = AgencyBankingAPIClient
                .branchDetails(payload, context)
                .subscribe(res -> {
                    if (res.code() != 200) {
                        Toast.makeText(context, "Failed to get Agent Balance", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("RES_CODE", "Branch: " + response);
                    if (response.optInt("code") != 112) {
                        Toast.makeText(context, response.optString("msg"), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Branch branch = new Branch();
                    branch.setBranchCode(response.optJSONObject("branch_data").optString("branch_code"));
                    branch.setBranchName(response.optJSONObject("branch_data").optString("branch_name"));
                    branch.setBranchStatus(response.optJSONObject("branch_data").optString("branch_status"));
                    branch.setBranchKey(response.optJSONObject("branch_data").optString("branch_key"));

                    SharedPrefManager.setBranchDetails(branch);

                    //call function to get balance
                    String officerID = SharedPrefManager.getAgencyUser().getOfficerID();
                    int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();
                    int branchID = SharedPrefManager.getAgencyUser().getUserBranch();
                    String agentKey = SharedPrefManager.getAgencyDetails().getAgentKey();
                    String branchKey = response.optJSONObject("branch_data").optString("branch_key");

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("teller_ID", Integer.parseInt(officerID));
                    jsonObject.addProperty("agent_ID", agencyID);
                    jsonObject.addProperty("branch_ID", branchID);
                    jsonObject.addProperty("agentKey", agentKey);
                    jsonObject.addProperty("branchKey", branchKey);
                    Log.e("RES_CODE", "Balance payload: " + jsonObject);
                    getAgentBalance(jsonObject);


                }, err -> {
                    Toast.makeText(context, "An unexpected error occured", Toast.LENGTH_LONG).show();
                });
    }

    public void getAgentBalance(JsonObject payload) {
        Disposable subscribe = AgencyBankingAPIClient
                .branchBalance(payload, context)
                .subscribe(res -> {
                    if (res.code() != 200) {
                        Toast.makeText(context, "Failed to get Agent Balance", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("RES_CODE", "Balance: " + response);
                    if (response.optInt("code") != 112) {
                        Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Balance balance = new Balance();
                    balance.setBalance(response.getString("balaance"));
                    balance.setToken(response.optString(("token")));

                    SharedPrefManager.setBalanceDetails(balance);

                    //perform computations and saved balance to shared preferences
                }, err -> {
                    Toast.makeText(context, "An unexpected error occured", Toast.LENGTH_LONG).show();
                });
    }

    public boolean priceValidation(String price) {
        String regex = "[+-]?([0-9]*[.])?[0-9]+";
        Pattern numberPattern = Pattern.compile(regex);
        boolean result = numberPattern.matcher(price).matches();
        Log.e("Result: ", result + "--");
        return result;
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
    public void prepareCanvas() {

        //mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
        mContent = binding.linearCanvas;
        // mSignature = new Signature(context, null);
        mSignature = new CollectionsNewFragment.Signature(context, null);
        mSignature.setBackgroundColor(Color.WHITE);
        //mSignature.getParent().requestDisallowInterceptTouchEvent(true);

        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //binding.linearNext.setEnabled(false);
        hasSigned = false;
    }
    public class Signature extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public Signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {

            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null) {

                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);

            }
            Canvas canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            //binding.linearNext.setEnabled(true);
            this.getParent().requestDisallowInterceptTouchEvent(true);
            hasSigned = true;
            //binding.tvSign.setError(null);
            showOrHideSignatureAlert(false);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }
    public void showSignaturePad() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        //final BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //R.anim.bottom_up_animation;
        dialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());

        LinearLayout linearCanvas = dialog.findViewById(R.id.linearCanvas);
        Button btnRetake = dialog.findViewById(R.id.btnRetake);
        Button btnDone = dialog.findViewById(R.id.btnDone);


        btnRetake.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.2f;
        window.setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    public void showOrHideSignatureAlert(boolean isError) {
        if (isError) {
            binding.tvSign.setText(context.getResources().getString(R.string.lbl_signature_required));
            binding.tvSign.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.error_small, 0);
            binding.tvSign.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            binding.tvSign.setText(context.getResources().getString(R.string.lbl_signature));
            binding.tvSign.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            binding.tvSign.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
    }
}
