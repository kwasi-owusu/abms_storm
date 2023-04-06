package com.woleapp.ui.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.netpluspay.netpossdk.NetPosSdk;
import com.netpluspay.netpossdk.emv.CardReaderEvent;
import com.netpluspay.netpossdk.emv.CardReaderService;
import com.netpluspay.netpossdk.emv.CardReadResult;
import com.pos.sdk.printer.POIPrinterManage;
import com.pos.sdk.printer.models.BitmapPrintLine;
import com.pos.sdk.printer.models.PrintLine;
import com.pos.sdk.printer.models.TextPrintLine;
import com.woleapp.R;
import com.woleapp.databinding.DialogBinding;
import com.woleapp.databinding.LayoutBankDetailsBinding;
import com.woleapp.databinding.LayoutCheckOutPageBinding;
import com.woleapp.databinding.QrBottomSheetDialogBinding;
import com.woleapp.db.AppDatabase;
import com.woleapp.db.Injection;
import com.woleapp.db.LocalCache;
import com.woleapp.db.UserViewModel;
import com.woleapp.db.dao.MerchantTransactionDao;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.CardData;
import com.woleapp.model.MerchantTransaction;
import com.woleapp.model.User;
import com.woleapp.network.APIServiceClient;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.ui.activity.PaymentProgressActivity;
import com.woleapp.util.Constants;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;
import com.woleapp.util.UtilsAndExtensionsKt;
import com.woleapp.util.mobilevision.BarcodeCaptureActivity;
import com.woleapp.viewmodels.PaymentViewModel;
import com.woleapp.viewmodels.PaymentViewModelKt;

import org.json.JSONObject;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_ICC;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_MAG;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_PICC;
import static com.woleapp.ui.activity.PaymentProgressActivity.KEY_AMOUNT;
import static com.woleapp.util.Constants.USER_TYPE_AGENT;
import static com.woleapp.util.UtilsAndExtensionsKt.encodeQueryValue;
import static com.woleapp.util.UtilsAndExtensionsKt.encodeToBase64String;
import static com.woleapp.viewmodels.PaymentViewModelKt.PAYMENT_MODE_CARD;
import static com.woleapp.viewmodels.PaymentViewModelKt.PAYMENT_MODE_CASH;
import static com.woleapp.viewmodels.PaymentViewModelKt.PAYMENT_MODE_PAYLINK;
import static com.woleapp.viewmodels.PaymentViewModelKt.PAYMENT_MODE_QR;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PaymentModeFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutCheckOutPageBinding binding;
    String amount = "";
    double amt_dbl = 0.0;
    private User user;
    private AgencyUser agencyUser;
    private UserViewModel mViewModel;
    float amt = 0.0f;
    double transaction_amount = 0.0;
    int transaction_type = 1;
    final int SCAN_QR_CODE = 1001, PAY_VIA_SMARTPESA = 1024;
    int result0, result1, result2;
    String currency_symbol = "";
    private static final int PERMISSION_REQUEST_CODE_CAMERA = 200,
            PERMISSION_REQUEST_CODE_BLUETOOTH = 201;
    String amnt;
    String commodityName;
    String description;
    String productName;
    String sellerId, sellerName;
    Integer productId;
    Integer quantity;
    String name;
    String idCard;
    String idNum;
    String cardNumber;
    String cardExpiry;
    private String transactionRef = "";
    private String customerName;
    private float posFee;
    private float netplusFee;

    DecimalFormat format = new DecimalFormat("###,###.##");
    Utilities utilities;
    ProgressDialog mProgressDialog;
    private boolean external = false;
    private PaymentViewModel viewModel;
    private String generatedPaylink = null;
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Log.e("PaymentModeFragment", "onReceive");
            user = SharedPrefManager.getUser();
        }
    };
    private String convenienceFee;

    CardReadResult readResult;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Gson gson = new Gson();

    @Override
    public void onStart() {
        Log.e("onStart", "onStart");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myReceiver,
                new IntentFilter("UpdateUser"));
        super.onStart();
    }

    @Override
    public void onStop() {

        Log.e("onStop", "onStop");
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myReceiver);
        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        user = SharedPrefManager.getUser();
        ((HomeActivity) getActivity()).setTitleWithNoNavigation("Dashboard");

        mViewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(getActivity()))
                .get(UserViewModel.class);


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
                           // Timber.d("CARD_DATA I==>%s", gson.toJson(readResult.Z));

                            CardData cardData = new CardData();
                            cardData.setCardNumber(readResult.d);
                           // cardData.setExpiry(readResult.A);
                            cardData.setCvv(readResult.s);
                            cardData.setCardExpiryDate(readResult.A);

                            Log.e("cardDetails", cardData+" ");

                            SharedPrefManager.setCardData(cardData);
                            //addFragmentWithoutRemove(R.id.container_main, new CardDetailsFragment(), CardDetailsFragment.class.getSimpleName());
                            processCashoutTransactions(name, idCard, idNum, amount, readResult.d, readResult.s, readResult.A);


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
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("CRASH", "crash");
        currency_symbol = context.getResources().getString(R.string.lbl_currency_naira);
        Bundle b1 = getArguments();
        productId = b1.getInt("PRODUCTID");
        productName = b1.getString("PRODUCTNAME");
        quantity = b1.getInt("QUANTITY");
        customerName = b1.getString("CUSTOMER_NAME", "");
        sellerId = b1.getString("SELLER_ID", null);
        sellerName = b1.getString("SELLER_NAME", null);
        name = b1.getString("name", "");
        idCard = b1.getString("idCard", "");
        idNum = b1.getString("idNum", "");
        ;
//        netplusFee = SharedPrefManager.getNetPlusPayConvenienceFee();
        external = b1.containsKey("external");

        Log.e("TAG", "Product id: " + productId + " Product Name: " + productName + " Quantity: " + quantity + " Customer Name: " + customerName);

//        if (b1.containsKey("amount")) {
            amount = b1.getString("amount", "");
            amount = amount.replaceAll(",", "").replace(currency_symbol, "");
            convenienceFee = format.format(Double.valueOf(amount) > 200000 ? 1000 : (SharedPrefManager.getPOSConvenienceFee() / 100) * Double.valueOf(amount));
            amt_dbl = Double.parseDouble(amount);
            Log.d("amt_bdl", String.valueOf(amt_dbl));
            amnt = String.valueOf(amt_dbl);
//        posFee = SharedPrefManager.getPOSConvenienceFee()
//            double amountPosFee = (posFee * amt_dbl) / 100;
//            binding.posFee.setText(getString(R.string.convenience_fee, amountPosFee));
//            double amountNetPlusFee = (netplusFee * amt_dbl) / 100;
//            binding.cardFee.setText(getString(R.string.convenience_fee, amountNetPlusFee));
//            binding.paylinkFee.setText(getString(R.string.convenience_fee, amountNetPlusFee));
            binding.tvAmount.setText(utilities.getFormattedAmount(amt_dbl));
            binding.tvConvenienceFee.setText("(Convenience Fee Included: " + currency_symbol + convenienceFee + ")");
            binding.tvConvenienceFee.setVisibility(View.VISIBLE);
            Log.e("TAG", ""+convenienceFee);
//
//        }

        if (b1.containsKey("transaction_type")) {
            transaction_type = b1.getInt("transaction_type", 1);
        }
        Log.e("TAG", "" + transaction_type);

        if (transaction_type == Constants.TRANSACTION_FUND_WALLET) {
            binding.payWithPaylink.setVisibility(View.VISIBLE);
            binding.cardAccountTransfer.setVisibility(View.VISIBLE);
            binding.cardCashCollection.setVisibility(View.GONE);
            binding.cardAccountTransfer1.setVisibility(View.VISIBLE);

        } else if (transaction_type == Constants.TRANSACTION_QUICK) {
            binding.cardAccountTransfer.setVisibility(View.VISIBLE);
            binding.cardCashCollection.setVisibility(View.VISIBLE);

        } else if (transaction_type == Constants.TRANSACTION_CASH_OUT) {
            binding.payWithPaylink.setVisibility(View.VISIBLE);
            binding.cardCashCollection.setVisibility(View.GONE);
            binding.cardAccountTransfer.setVisibility(View.VISIBLE);
            binding.cardAccountTransfer1.setVisibility(View.VISIBLE);

        } else {
            binding.cardAccountTransfer.setVisibility(View.VISIBLE);
            binding.cardAccountTransfer.setOnClickListener(this);
        }

        if (external) {
            binding.cardAccountTransfer.setVisibility(View.GONE);
            binding.payWithStormWallet.setVisibility(View.VISIBLE);
            binding.cardPosPayment.setVisibility(View.GONE);
            binding.payWithPaylink.setVisibility(View.GONE);
            binding.cardCashCollection.setVisibility(View.GONE);
        }


        binding.cardPosPayment.setOnClickListener(this);
        binding.cardAccountTransfer1.setOnClickListener(this);
        binding.cardAccountTransfer.setOnClickListener(this);
        binding.cardQRPay.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
        binding.cardCashCollection.setOnClickListener(this);
        binding.payWithPaylink.setOnClickListener(this);
        description = b1.getString("DESCRIPTION");
        commodityName = b1.getString("COMMODITY_NAME");
//        viewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
//        viewModel.setStormAPIService(StormAPIClient.create(requireContext()));
//        viewModel.setStormMerchantApiService(MerchantsApiClient.getMerchantApiService(requireContext()));
//        viewModel.getLoadingStatus().observe(getViewLifecycleOwner(), booleanEvent -> {
//            Boolean showLoading = booleanEvent.getContentIfNotHandled();
//            if (showLoading == null)
//                return;
//            Log.e("show dialog", "" + showLoading);
//            if (showLoading)
//                showProgressBar();
//            else
//                dismissProgressBar();
//        });
//        viewModel.getProceedToPayment().observe(getViewLifecycleOwner(), paymentResponseEvent -> {
//            Toast.makeText(context, "OKAY", Toast.LENGTH_SHORT).show();
//            dismissProgressBar();
//            MerchantTransaction merchantTransaction = paymentResponseEvent.getContentIfNotHandled();
//            if (merchantTransaction != null) {
//                proceedToPayment(merchantTransaction);
//            } else {
//                Toast.makeText(getContext(), "Failed, Try again", Toast.LENGTH_SHORT).show();
//            }
//        });
//        viewModel.getPaymentResponse().observe(getViewLifecycleOwner(), booleanEvent -> {
//            Boolean e = booleanEvent.getContentIfNotHandled();
//            if (e != null && e) {
//                addFragmentWithoutRemove(R.id.container_main, new PaymentSuccessFragment
//                        (), PaymentModeFragment.class.getSimpleName());
//            }
//        });
    }

    private void proceedToPayment(MerchantTransaction merchantTransaction) {
        if (merchantTransaction.getPaymentMethod().equals(PAYMENT_MODE_CASH))
            viewModel.logCashTransaction();
        else if (merchantTransaction.getPaymentMethod().equals(PaymentViewModelKt.PAYMENT_MODE_TRANSFER)) {
            showDialogForAccountTransfer(merchantTransaction);
        } else if (merchantTransaction.getPaymentMethod().equals(PaymentViewModelKt.PAYMENT_MODE_POS)) {
            transactionRef = merchantTransaction.getReference();
            Intent broadcast = new Intent("UpdateUser");
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(broadcast);
            //TODO Removed the AccountActivation process for merchant
            requestPermissionForBluetooth();
        } else if (merchantTransaction.getPaymentMethod().equals(PAYMENT_MODE_PAYLINK)) {
            String paylinkBaseUrl = "https://paylink.netpluspay.com?p=";
            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("a_id", user.getNetplus_id());
//            jsonObject.addProperty("ref", merchantTransaction.getReference());
            //jsonObject.addProperty("amount", amount);
            String pName;
            if (transaction_type == Constants.TRANSACTION_FUND_WALLET) {
                pName = "FUND WALLET";
            } else if (transaction_type == Constants.TRANSACTION_CASH_OUT)
                pName = "CASH OUT";
            else {
                pName = productName == null ? "PRODUCT" : productName;
            }
            jsonObject.addProperty("amount", "" + amt_dbl);
            jsonObject.addProperty("p_name", pName);
            jsonObject.addProperty("type", "paylink");
//            jsonObject.addProperty("agent", user.getBusiness_name());
            //jsonObject.addProperty("product_name", productName);
            Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                    .setLink(Uri.parse(paylinkBaseUrl.concat(encodeQueryValue(encodeToBase64String(jsonObject.toString())))))
                    .setDomainUriPrefix("https://plink.netpluspay.com")
                    // Set parameters
                    // ...
                    .buildShortDynamicLink()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Uri shortLink = task.getResult().getShortLink();
                            this.generatedPaylink = shortLink.toString();
                            showGeneratedPaylinkShareIntent();
//                            Log.e("TAG shortlink", shortLink.toString());
//                            Uri flowchartLink = task.getResult().getPreviewLink();
//                            Log.e("TAG flowchart link", flowchartLink.toString());
                        } else {
                            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (merchantTransaction.getPaymentMethod().equals(PAYMENT_MODE_QR)) {
            showQRBottomSheetDialog();
        } else if (merchantTransaction.getPaymentMethod().equals(PAYMENT_MODE_CARD)) {
            Bundle b1 = new Bundle();
            b1.putDouble(KEY_AMOUNT, amt_dbl);
            b1.putString("ref", merchantTransaction.getReference());
            b1.putString("a_id", merchantTransaction.getMerchantId());
            b1.putString("agent", user.getBusiness_name());
            b1.putString("type", "card");
            b1.putString("id", String.valueOf(productId));
            b1.putString("SELLER_ID", sellerId);
            b1.putString("SELLER_NAME", sellerName);
            proceedToCardPayment(b1);
        }
    }

    private void showQRBottomSheetDialog() {
        QrBottomSheetDialogBinding qrBottomSheetDialogBinding = QrBottomSheetDialogBinding.inflate(getLayoutInflater(), null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.SheetDialog);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(qrBottomSheetDialogBinding.getRoot());
        qrBottomSheetDialogBinding.closeBtn.setOnClickListener(v -> bottomSheetDialog.cancel());
        bottomSheetDialog.show();
    }

    private void showDialogForAccountTransfer(MerchantTransaction merchantTransaction) {
        LayoutBankDetailsBinding bankDetailsBinding = LayoutBankDetailsBinding.inflate(getLayoutInflater(), null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.SheetDialog);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(bankDetailsBinding.getRoot());
        String bank = "GTB";
        String accountNumber = "0597024646";
        String accountName = "NETPLUS/STORM";
        String accountNumber2 = "2684362099";
        String bank2 = "FCMB";
        bankDetailsBinding.accountNumber2.setText(accountNumber2);
        bankDetailsBinding.bank2.setText(bank2);
        bankDetailsBinding.accountNumber.setText(accountNumber);
        bankDetailsBinding.bank.setText(bank);
        bankDetailsBinding.accountName.setText(accountName);
        String ref = "Please use the code " + merchantTransaction.getReference() + " as a reference during transfer or payment";
        bankDetailsBinding.reference.setText(ref);
        bankDetailsBinding.accountNumber.setOnClickListener(v -> {
            UtilsAndExtensionsKt.copyTextToClipboard(requireContext(), "Account Number", accountNumber);
            Toast.makeText(requireContext(), "Account number copied to clipboard", Toast.LENGTH_SHORT).show();
        });
        bankDetailsBinding.accountNumber2.setOnClickListener(v -> {
            UtilsAndExtensionsKt.copyTextToClipboard(requireContext(), "Account Number", accountNumber2);
            Toast.makeText(requireContext(), "Account number copied to clipboard", Toast.LENGTH_SHORT).show();
        });
        bankDetailsBinding.tap.setOnClickListener(v -> {
            UtilsAndExtensionsKt.copyTextToClipboard(requireContext(), "Reference", "" + ref);
            Toast.makeText(requireContext(), "Reference copied to clipboard", Toast.LENGTH_SHORT).show();
        });
        bankDetailsBinding.btnDone.setOnClickListener(v -> {
            if (bottomSheetDialog.isShowing())
                bottomSheetDialog.cancel();
            showFragment(new DashboardFragment(), DashboardFragment.class.getSimpleName());
        });
        bottomSheetDialog.show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        //user = SharedPrefManager.getInstance(context).getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_check_out_page, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
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

    private void proceedToCardPayment(Bundle b1) {
        WebViewFragment webViewFragment = new WebViewFragment();
        webViewFragment.setArguments(b1);
        addFragmentWithoutRemove(R.id.container_main, webViewFragment, WebViewFragment.class.getSimpleName());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        if (v == binding.cardPosPayment) //cardAccountTransfer // Etranzact Funds transfer API
        {
            //Toast.makeText(context,"This feature shall be available soon",Toast.LENGTH_SHORT).show();
            Log.d("POS_CHECK", "POS WORKS");

            if (transaction_type == Constants.TRANSACTION_FUND_WALLET || transaction_type == Constants.TRANSACTION_CASH_OUT) {
                //updateUser(amount);
//                requestPermissionForBluetooth();
            } else {
//                MerchantTransaction merchantTransaction =
//                        new MerchantTransaction(
//                                UtilsAndExtensionsKt.getSixDigitRandomNumber(),
//                                user.getNetplus_id(),
//                                PAYMENT_MODE_POS,
//                                customerName,
//                                PAYMENT_STATUS_PENDING,
//                                "" + amt_dbl,
//                                "" + productId,
//                                "" + quantity
//                        );
//                viewModel.setMerchantTransaction(merchantTransaction);
//                viewModel.logNewTransaction();
            }

        } else if (v == binding.cardAccountTransfer1) {
            Log.d("Card Payment Works", "Card Payment Works");
            //addFragmentWithoutRemove(R.id.container_main, new CardDetailsFragment(), CardDetailsFragment.class.getSimpleName());
            readCard();
            if(cardNumber != null) {

            }
            if (transaction_type == Constants.TRANSACTION_QUICK) {
//                MerchantTransaction merchantTransaction =
//                        new MerchantTransaction(
//                                "SC".concat(sellerId.substring(0, 4)).concat(UtilsAndExtensionsKt.getSixDigitRandomNumber()),
//                                user.getNetplus_id(),
//                                PAYMENT_MODE_CARD,
//                                customerName,
//                                PAYMENT_STATUS_PENDING,
//                                "" + amt_dbl,
//                                "" + productId,
//                                "" + quantity
//                        );
//                viewModel.setMerchantTransaction(merchantTransaction);
//                viewModel.logNewTransaction();
            } else {
//                Bundle b1 = new Bundle();
//                b1.putDouble(KEY_AMOUNT, amt_dbl);
//                b1.putString("ref", "SC".concat(user.getNetplus_id().substring(0, 4)).concat(UtilsAndExtensionsKt.getSixDigitRandomNumber()));
//                b1.putString("a_id", user.getNetplus_id());
//                b1.putString("type", "card");
//                b1.putString("agent", user.getBusiness_name());
//                proceedToCardPayment(b1);
            }
        } else if (v == binding.cardQRPay) {
            Log.d("QR Work", "QR Works");
//            MerchantTransaction merchantTransaction =
//                    new MerchantTransaction(
//                            UtilsAndExtensionsKt.getSixDigitRandomNumber(),
//                            user.getNetplus_id(),
//                            PAYMENT_MODE_QR,
//                            customerName,
//                            PAYMENT_STATUS_PENDING,
//                            "" + amt_dbl,
//                            "" + productId,
//                            "" + quantity
//                    );
//            viewModel.setMerchantTransaction(merchantTransaction);
//            viewModel.logNewTransaction();

        } else if (v == binding.btnBack) {
            getActivity().getSupportFragmentManager().popBackStack();
        } else if (v == binding.payWithPaylink) {
            if (generatedPaylink != null) {
                showGeneratedPaylinkShareIntent();
                return;
            }
//            MerchantTransaction merchantTransaction =
//                    new MerchantTransaction(
//                            "SP".concat(user.getNetplus_id().substring(0, 4)).concat(UtilsAndExtensionsKt.getSixDigitRandomNumber()),
//                            user.getNetplus_id(),
//                            PAYMENT_MODE_PAYLINK,
//                            customerName,
//                            PAYMENT_STATUS_PENDING,
//                            "" + amt_dbl,
//                            "" + productId,
//                            "" + quantity
//                    );
//            viewModel.setMerchantTransaction(merchantTransaction);
            if (transaction_type == Constants.TRANSACTION_FUND_WALLET || transaction_type == Constants.TRANSACTION_CASH_OUT)
//                proceedToPayment(merchantTransaction);
                Log.d("OPTIONS", "Paylink Works");
            else
                Log.d("OPTIONS", "option2");
//                viewModel.logNewTransaction();
            //Toast.makeText(context, "Generating a PayLink, Please Wait", Toast.LENGTH_SHORT).show();

        } else if (v == binding.cardCashCollection) {
            Toast.makeText(getContext(), "Cash collection", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "name" + customerName, Toast.LENGTH_SHORT).show();
            /*if (productId == 0 || productId == null) {
                addFragmentWithoutRemove(R.id.container_main, new PaymentSuccessFragment
                        (), PaymentSuccessFragment.class.getSimpleName());
                return;
            }*/
            if (productId != null) {
                MerchantTransactionDao transactionsDao = AppDatabase.getInstance(requireContext()).transactionsDao();
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                DialogBinding dialogBinding = DialogBinding.inflate(getLayoutInflater(), null, false);
                builder.setView(dialogBinding.getRoot());
                AlertDialog dialog = builder.create();
                dialogBinding.tvTitle.setText("Payment Confirmation");
                dialogBinding.tvMessage.setText("Tap yes to confirm this transaction");
                dialogBinding.tvNo.setOnClickListener(v12 -> {
                    if (dialog.isShowing())
                        dialog.cancel();
                });
                long time = System.currentTimeMillis();
                dialogBinding.tvYes.setOnClickListener(v1 -> {
                    if (dialog.isShowing())
                        dialog.dismiss();
//                    CashTransaction cashTransaction =
//                            new CashTransaction(
//                                    user.getNetplus_id(),
//                                    user.getTerminal_id(),
//                                    user.getBusiness_name(),
//                                    "csh-" + time,
//                                    "UTL-" + time,
//                                    "Success",
//                                    amt_dbl,
//                                    "Sales-Cash",
//                                    description,
//                                    "Sales",
//                                    "storm_app",
//                                    ""
//
//                            );
//                    MerchantTransaction merchantTransaction =
//                            new MerchantTransaction(
//                                    UtilsAndExtensionsKt.getSixDigitRandomNumber(),
//                                    user.getNetplus_id(),
//                                    PAYMENT_MODE_CASH,
//                                    customerName,
//                                    PAYMENT_STATUS_PENDING,
//                                    "" + amt_dbl,
//                                    "" + productId,
//                                    "" + quantity,
//                                    sellerId
//                            );
//                    viewModel.setMerchantTransaction(merchantTransaction);
//                    viewModel.setCashTransaction(cashTransaction);
//                    viewModel.logNewTransaction();
//                    //showProgressBar();
                });
                dialog.show();
            }
        } else if (v == binding.cardAccountTransfer) {
            Log.d("Account Transfer", "Account Transfer Works");
//            MerchantTransaction merchantTransaction =
//                    new MerchantTransaction(
//                            "",
//                            user.getNetplus_id(),
//                            PAYMENT_MODE_TRANSFER,
//                            customerName,
//                            PAYMENT_STATUS_PENDING,
//                            "" + amt_dbl,
//                            "" + productId,
//                            "" + quantity
//                    );
//            viewModel.setMerchantTransaction(merchantTransaction);
//            viewModel.getSessionCode(transaction_type);
        }
    }

    private void processCashoutTransactions(String name, String idCard, String idNo, String amt, String account, String cvv, String card_date_expiry) {
        //payload
        String officer_ID = "13";
        String branch_code = "1";
        String inst_ID = "1";
        String branch_key = SharedPrefManager.getBranchDetails().getBranchKey();
        String agency_key = SharedPrefManager.getAgencyDetails().getAgentKey();
        String trans_cat = "1";
        String trans_type = "2";
        String total_amnt = amt;
        String narration = "Test Narration";
        String customer_name = name;
        String account_number = account;
        String customer_cvv = cvv;
        String cardExpiryDate = card_date_expiry;

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
        transaction.addProperty("amount", total_amnt);
        transaction.addProperty("narration", narration);
        transaction.addProperty("id_type", ID_type);
        transaction.addProperty("id_number", ID_number);
        transaction.addProperty("depositor_payee", depositor_payee);
        transaction.addProperty("customer_name", customer_name);
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
                    //printReceipt(customer_name, ID_type, ID_number, a_name, branchName ,ref);

                    //process transaction
                },err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                });

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
                Toast.makeText(context, "Printing job finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart() {
                Toast.makeText(context, "Printing job started", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showGeneratedPaylinkShareIntent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        try {
            sendIntent.putExtra(Intent.EXTRA_TEXT, generatedPaylink);
        } catch (Exception e) {
            e.printStackTrace();
            sendIntent.putExtra(Intent.EXTRA_TEXT, generatedPaylink);
        }
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }


    public void showProgressBar() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(context, null, null);
            mProgressDialog.setContentView(R.layout.dialog_progress);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ffffff")));
            mProgressDialog.setCancelable(false);


        } else {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        }
    }

    public void dismissProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void notifyWalletCredits(JsonObject jsonObject) {


        APIServiceClient.notifyWalletCredits(jsonObject, new APIServiceClient.ApiCallback2() {
            @Override
            public void onStart() {
                showProgressBar();
                Log.e("onStart", "onStart");
            }

            @Override
            public void onEnd() {
                Log.e("onEnd", "onEnd");
                dismissProgressBar();
            }


            @Override
            public void onResponse(Object response)//ResponseEnvelope response
            {
                if (response instanceof JSONObject) {
                    JSONObject jsonObject1 = (JSONObject) response;
                    String message = jsonObject1.optString("message", "No response obtained");
                    if (!message.equalsIgnoreCase("No response obtained")) {
                        String amount = jsonObject1.optString("amount", "0.0");
                        message = "Your wallet has been credited. New amount is " + utilities.getFormattedAmount(Double.valueOf(amount));
                        ((HomeActivity) getActivity()).showD(true, message);
                    }
                }
            }

            @Override
            public void onFail(String msg) {
                Log.e("onFail", msg + "###");
                Toast.makeText(context, msg + "", Toast.LENGTH_SHORT).show();
                dismissProgressBar();
            }
        }, context);
    }


    private void proceedToPayment() {

        Double amt = Double.parseDouble(amount.replaceAll(",", ""));
        Double fee = Double.parseDouble(convenienceFee.replaceAll(",", ""));

        Bundle bundle = new Bundle();
        bundle.putDouble(KEY_AMOUNT, amt);
        bundle.putDouble("convenience_fee", fee);
        bundle.putInt("transaction_type", Constants.TRANSACTION_CASH_OUT);
        bundle.putString("COMMODITY_NAME", commodityName);
        bundle.putString("DESCRIPTION", description);
        bundle.putInt("QUANTITY", quantity);
        bundle.putInt("PRODUCTID", productId);
        bundle.putString("PRODUCTNAME", productName);
        bundle.putString("TRANSACTION_REF", transactionRef);
        bundle.putString("SELLERID", sellerId);
        Intent i = new Intent(getActivity(), PaymentProgressActivity.class);
        i.putExtras(bundle);
        getActivity().startActivityForResult(i, PAY_VIA_SMARTPESA);
    }

    public void updateUser(String amount) {

        if (amount != null) {

            amt = Float.parseFloat(amount) + Float.parseFloat(user.getAvailableBalance());

        } else {

            amt = Float.parseFloat(amount);

        }

        //user.setIs_verified(true);
        LocalCache.UpdateCallback callback = new LocalCache.UpdateCallback() {
            @Override
            public void updateFinished(int result) {
                if (result > 0) {
                    user.setAvailableBalance(String.valueOf(amt));
                    SharedPrefManager.setUser(user);
                    //Toast.makeText(context,"Registration successful",Toast.LENGTH_SHORT).show();

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            addFragmentWithoutRemove(R.id.container_main, new PaymentSuccessFragment(), PaymentModeFragment.class.getSimpleName());
                            ((HomeActivity) getActivity()).setAvailableBalance(String.valueOf(amt));

                        }
                    });
                    //showFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
                    //showFragment(new OTPFragment(),OTPFragment.class.getSimpleName());
                    //addFragmentWithoutRemove(R.id.container_main,new OTPFragment(), OTPFragment.class.getSimpleName());
                } else {
                    /*getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            utilities.showAlertDialogOk("Record already exists. Please login instead.");
                        }
                    });*/

                }
            }
        };

        Log.e("UserIdToBeUpdated", user.getUser_id() + "--");
        mViewModel.updateWalletAmount(user.getUser_id(), String.valueOf(amt), callback);
    }

    private OnItemClickListener.OnItemClickCallback onClick = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {

            if (user.getUser_type() == USER_TYPE_AGENT) {
                if (position == 0) {
                    addFragmentWithoutRemove(R.id.container_main, new FundWalletFragment(), FundWalletFragment.class.getSimpleName());
                }
            } else {
                if (position == 0) {
                    addFragmentWithoutRemove(R.id.container_main, new QuickTransactionFragment(), QuickTransactionFragment.class.getSimpleName());
                }
            }

        }
    };

    private void requestPermissionForBluetooth() {
        result0 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result0 == PackageManager.PERMISSION_GRANTED) {
            proceedToPayment();
        } else {
            String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};//, Manifest.permission.BLUETOOTH
            requestPermissions(
                    permissions,
                    PERMISSION_REQUEST_CODE_BLUETOOTH);
        }

    }

    private void requestPermissionForCamera() {

        //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE_CAMERA);

        requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE_CAMERA);
        //,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_NETWORK_STATE
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted)// && phoneStateAccepted && networkAccepted)
                    {

                        Intent i1 = new Intent(getActivity(), BarcodeCaptureActivity.class);
                        startActivityForResult(i1, SCAN_QR_CODE);

                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE},
                                    PERMISSION_REQUEST_CODE_CAMERA);
                        }
                    }
                }
                break;

            case PERMISSION_REQUEST_CODE_BLUETOOTH:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //showLoginActivity();
                            proceedToPayment();
                        }
                    }, 300);
                } else {
                    Toast.makeText(context, "Cannot proceed until the permission is granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void scanQRCode() {
        try {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M) {
                result0 = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);

                if (result0 == 0)// && result1 == 0 && result2 == 0)//result0 == 0 && result1 == 0
                {

                    Intent i1 = new Intent(getActivity(), BarcodeCaptureActivity.class);
                    startActivityForResult(i1, SCAN_QR_CODE);
                } else {
                    requestPermissionForCamera();
                }
            } else {

                Intent i1 = new Intent(getActivity(), BarcodeCaptureActivity.class);
                startActivityForResult(i1, SCAN_QR_CODE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//Barcode read: MERCHANT_MERCHANT#1
        Log.e("PaymentModeFragment", "onActivityResult: " + requestCode + " , " + resultCode);
        if (data != null) {

        }
        if (requestCode == SCAN_QR_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                    String result = barcode.displayValue;
                    if (result != null) {
                        Log.e("QR", "Barcode read: " + result);////                try
                        Toast.makeText(context, "Your Payment to merchant " + result + " was successful", Toast.LENGTH_LONG).show();
                        showFragment(new DashboardFragment(), DashboardFragment.class.getSimpleName());
                    } else {
                        //Log.e("QR", "Barcode read: " + result);////                try
                        Toast.makeText(context, "Payment Failed", Toast.LENGTH_SHORT).show();
                    }
                    /*try
                    {
                        JSONObject jobj = new JSONObject(result);
                        String propertyId = jobj.optString("propertyId","");
                        getPropertyDetails(propertyId);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        showDialog("");
                    }*/

                } else {
                    Log.e("QR", "No barcode captured, intent data is null");
                    //showDialog("");
                }
            } else {
//                statusMessage.setText(String.format(getString(R.string.barcode_error),
//                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    private Bitmap textToImage(String text, int width, int height) throws WriterException, NullPointerException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.DATA_MATRIX.QR_CODE,
                    width, height, null);
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        int colorWhite = 0xFFFFFFFF;
        int colorBlack = 0xFF000000;

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? colorBlack : colorWhite;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, width, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
