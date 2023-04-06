package com.woleapp.ui.fragments;

import static com.woleapp.ui.activity.PaymentProgressActivity.KEY_AMOUNT;
import static com.woleapp.util.UtilsAndExtensionsKt.encodeQueryValue;
import static com.woleapp.util.UtilsAndExtensionsKt.encodeToBase64String;
import static com.woleapp.viewmodels.PaymentViewModelKt.PAYMENT_MODE_CARD;
import static com.woleapp.viewmodels.PaymentViewModelKt.PAYMENT_STATUS_SUCCESS;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.woleapp.R;
import com.woleapp.databinding.LayoutCardWebviewBinding;
import com.woleapp.model.MerchantTransaction;
import com.woleapp.network.AgencyBankingService;
import com.woleapp.network.MerchantsApiClient;
import com.woleapp.network.StormAPIClient;
import com.woleapp.util.Utilities;
import com.woleapp.viewmodels.PaymentViewModel;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardWebViewFragment extends BaseFragment{
    Context context;
    private LayoutCardWebviewBinding binding;
    Utilities utilities;
    ProgressDialog mProgressDialog;
    private Timer timer;
    private final int INTERVAL = 3000;
    private String cardTransactionId;
    private String token;
    private String name;
    private String number;
    private String ref;
    private String issuer;
    private String amount;
    public CardWebViewFragment(String cardTransactionId, String token, String name, String number, String ref, String issuer, String amount){
        this.cardTransactionId = cardTransactionId;
        this.token = token;
        this.name = name;
        this.number = number;
        this.ref = ref;
        this.issuer = issuer;
        this.amount = amount;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.addJavascriptInterface(new AndroidInterface(), "AndroidInterface");

        Bundle bundle = this.getArguments();
        String chqUrl = bundle.getString("url");
        Log.e("WEB VIEW", "Redirect Url: " + chqUrl);
        Log.e("TransCardId", "TransCardId: " + cardTransactionId);

        String iframeHtml = "<html><body>" + chqUrl + "</body></html>";

        binding.webView.loadData(iframeHtml, "text/html", "utf-8");
      //  binding.webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8",null);

        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webView.loadUrl(webResourceRequest.getUrl().toString());
                }
                return true;
            }@Override
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                transactionStatus();
                dismissProgressBar();
            }
        });

           // showProgressBar();
        }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_card_webview, container, false);
        return binding.getRoot();
    }

        public void onClick(View v) {
        if (v == binding.ivBack) {
            //addFragmentWithoutRemove(R.id.container_main, new PaymentSuccessfulFragment(), PaymentSuccessfulFragment.class.getSimpleName());
        }
    }
    private class AndroidInterface {
        @JavascriptInterface
        public void onFormSubmitted() {
           // transactionStatus();
        }
    }
    public void transactionStatus() {
      //  showProgressBar();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Disposable subscribe = transRetrofit.transactionStatus(cardTransactionId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( ress ->{
                            Object bodyy = ress.body();
                            JSONObject responses = new JSONObject(new Gson().toJson(bodyy));

                            Log.e("TRANSACTION_Status_Load", "response body: " + responses);
                            Log.e("TransCardID", "TransCardID " + cardTransactionId);
                            Log.e("TransID", "TransID " + responses.optString("transactionId"));
                            ref = responses.optString("reference");

                           /* if (ress.code() != 200) {
                                Toast.makeText(context, "Failed to process transaction, Please try again", Toast.LENGTH_SHORT).show();
                                addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                                dismissProgressBar();
                                return;
                            }*/

                            if (responses.optString("status").equals("paid")) {
                                //addFragmentWithoutRemove(R.id.container_main, new PaymentSuccessfulFragment(name, number, ref, issuer, amount), PaymentSuccessfulFragment.class.getSimpleName());
                                addFragmentWithoutRemove(R.id.container_main, new PaymentCardSuccessfulFragment(name,ref, amount), PaymentCardSuccessfulFragment.class.getSimpleName());
                                timer.cancel();
                                dismissProgressBar();
                                Log.e("TRANSACTION_Status_Paid", "paid " + responses.optString("status"));
                                //break;
                            }
                            else if (responses.optString("status").equals("pending")) {
                                // timer.cancel();
                                //showProgressBar();
                                Log.e("TRANSACTION_Status_Pend", "pending " + responses.optString("status"));
                            }
                            else if (responses.optString("status").equals("failed")) {
                                addFragmentWithoutRemove(R.id.container_main, new PaymentFailedFragment(), PaymentFailedFragment.class.getSimpleName());
                                timer.cancel();
                                dismissProgressBar();
                                Log.e("TRANS_Status_Failed", "failed " + responses.optString("status"));
                            }
                            else {
                                Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                                timer.cancel();
                                dismissProgressBar();
                                addFragmentWithoutRemove(R.id.container_main, new PaymentFailedFragment(), PaymentFailedFragment.class.getSimpleName());
                            }
                        }, err -> {
                            //Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                            dismissProgressBar();
                        });
            }
        }, 0, INTERVAL);
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
