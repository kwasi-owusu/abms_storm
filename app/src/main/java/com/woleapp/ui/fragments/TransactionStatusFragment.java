package com.woleapp.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.netpluspay.netpossdk.NetPosSdk;
import com.pos.sdk.printer.POIPrinterManage;
import com.pos.sdk.printer.models.BitmapPrintLine;
import com.pos.sdk.printer.models.PrintLine;
import com.pos.sdk.printer.models.TextPrintLine;
import com.woleapp.R;
import com.woleapp.databinding.LayoutTransactionStatusBinding;
import com.woleapp.network.AgencyBankingService;
import com.woleapp.network.TokenInterceptor;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class TransactionStatusFragment extends BaseFragment{
    Context context;
    ProgressDialog mProgressDialog;
    Utilities utilities;
    private String transactionId;
    private String token;
    private String name;
    private String number;
    private String ref;
    private String issuer;
    private String amount;
    private Timer timer;
    private final int INTERVAL = 3000;
    private static String merchantName = SharedPrefManager.getAgencyUser().getMerchantName();

    public TransactionStatusFragment(String transactionId, String token, String name, String number, String ref, String issuer, String amount){
        this.transactionId = transactionId;
        this.token = token;
        this.name = name;
        this.number = number;
        this.ref = ref;
        this.issuer = issuer;
        this.amount = amount;
    }

    private LayoutTransactionStatusBinding binding;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
       Log.e("TransId", "TransId" + transactionId);
        Log.e("Amount", "Amount" + amount);

        showTimeoutProgressBar();
        transactionStatus();

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_transaction_status, container, false);
        return binding.getRoot();
    }

    public void transactionStatus() {
        showTimeoutProgressBar();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Disposable subscribe = transRetrofit.transactionStatus(transactionId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( res ->{
                            Object body = res.body();
                            JSONObject responses = new JSONObject(new Gson().toJson(body));

                            Log.e("TRANSACTION_Status_Load", "response body: " + responses);
                            Log.e("TransID", "TransID " + transactionId);
                            Log.e("TransID", "TransID " + responses.optString("transactionId"));
                            ref = responses.optString("reference");

                            if (res.code() != 200) {
                                Toast.makeText(context, "Failed to process transaction, Please try again", Toast.LENGTH_SHORT).show();
                                addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                                dismissProgressBar();
                                return;
                            }
                            if (responses.optString("status").equals("paid")) {
                                // addFragmentWithoutRemove(R.id.container_main, new PaymentSuccessfulFragment(name, number, ref, issuer, amount), PaymentSuccessfulFragment.class.getSimpleName());
                                printReceipt(ref,amount);
                                addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                                timer.cancel();
                                dismissProgressBar();
                                Log.e("TRANSACTION_Status_Paid", "paid " + responses.optString("status"));
                                //break;
                            }
                            else if (responses.optString("status").equals("pending")) {
                                // timer.cancel();
                                showTimeoutProgressBar();
                                //showProgressBar();
                                Log.e("TRANSACTION_Status_Pend", "pending " + responses.optString("status"));
                            }
                           else if (responses.optString("status").equals("failed")) {
                                timer.cancel();
                                addFragmentWithoutRemove(R.id.container_main, new PaymentFailedFragment(), PaymentFailedFragment.class.getSimpleName());                                timer.cancel();
                                dismissProgressBar();
                                Log.e("TRANS_Status_Failed", "failed " + responses.optString("status"));
                            }
                            else {
                                timer.cancel();
                                addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                                Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                                dismissProgressBar();
                            }
                        }, err -> {
                            Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                           // dismissProgressBar();
                        });
            }
        }, 0, INTERVAL);
    }
    private void printReceipt( String ref, String amnt) {
        POIPrinterManage printerManage = NetPosSdk.getPrinterManager(context);
        printerManage.setLineSpace(2);
        printerManage.setPrintGray(3000);
        printerManage.cleanCache();

        TextPrintLine textPrintLine = new TextPrintLine();
        textPrintLine.setType(PrintLine.TEXT);
        textPrintLine.setContent(merchantName);
        textPrintLine.setBold(true);
        textPrintLine.setItalic(false);
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(TextPrintLine.FONT_LARGE);

        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("----------------------------------------------------" +
                "--------------------------------------------");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(" ");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Amount: " + "GH₵" +(amount));
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Ref No.: " + ref);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Date: " + utilities.getTodaysDate());
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Time: " + utilities.getTodaysTime());
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

        Bitmap bitmap =
                BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.bsystemslogo);
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 100, false);

        BitmapPrintLine bitmapPrintLine = new BitmapPrintLine();
        bitmapPrintLine.setType(PrintLine.BITMAP);
        bitmapPrintLine.setBitmap(bitmap);
        bitmapPrintLine.setPosition(PrintLine.CENTER);

        printerManage.addPrintLine(bitmapPrintLine);

        printerManage.beginPrint(new POIPrinterManage.IPrinterListener() {
            @Override
            public void onError(int i, String s) {
                Timber.e("Printer error with code " + i + " and message" + s);
                Toast.makeText(context, "Printer Error", Toast.LENGTH_SHORT).show();
                addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
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

    private AgencyBankingService transRetrofit = new Retrofit.Builder()
            .baseUrl("https://peoplespay.com.gh")
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
    public void showTimeoutProgressBar(){
        if(mProgressDialog != null && mProgressDialog.isShowing())
            return;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Checking Payment Status"); // Setting Message
        mProgressDialog.setTitle("Status"); // Setting Title
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
    }

}
