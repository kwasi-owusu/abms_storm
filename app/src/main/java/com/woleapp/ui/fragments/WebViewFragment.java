package com.woleapp.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslCertificate;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.*;
import android.webkit.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.woleapp.R;
import com.woleapp.databinding.LayoutWebviewBinding;
import com.woleapp.model.MerchantTransaction;
import com.woleapp.model.PaymentResponseFromnNetpluspay;
import com.woleapp.model.User;
import com.woleapp.network.MerchantsApiClient;
import com.woleapp.network.StormAPIClient;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.util.*;
import com.woleapp.viewmodels.PaymentViewModel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import static com.woleapp.ui.activity.PaymentProgressActivity.KEY_AMOUNT;
import static com.woleapp.util.UtilsAndExtensionsKt.encodeQueryValue;
import static com.woleapp.util.UtilsAndExtensionsKt.encodeToBase64String;
import static com.woleapp.viewmodels.PaymentViewModelKt.PAYMENT_MODE_CARD;
import static com.woleapp.viewmodels.PaymentViewModelKt.PAYMENT_STATUS_PENDING;
import static com.woleapp.viewmodels.PaymentViewModelKt.PAYMENT_STATUS_SUCCESS;


public class WebViewFragment extends BaseFragment implements View.OnClickListener {
    Context context;
    private LayoutWebviewBinding binding;
    User user;
    Utilities utilities;
    ProgressDialog mProgressDialog;
    private double amount = 0.0;
    private String ref;
    private String productId;
    private String agentId;
    private String sellerId, sellerName;
    private PaymentViewModel paymentViewModel;

    @Override
    public void onResume() {
        super.onResume();
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
        //user = SharedPrefManager.getUser();
        paymentViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
        paymentViewModel.setStormMerchantApiService(MerchantsApiClient.getMerchantApiService(requireContext()));
        paymentViewModel.setStormAPIService(StormAPIClient.create(requireContext()));

//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        /*mViewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(getActivity()))
                .get(UserViewModel.class);*/
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Intent intent = getIntent();
      //  intent.getExtras()
       // <iframe id=\"challengeFrame\" name=\"challengeFrame\" width=\"100%\" height=\"100%\" ></iframe>
        Bundle bundle = this.getArguments();
//        String chqUrl = bundle.getString("url");
       // String html = "<iframe width=\"450\" height=\"260\" style=\"border: 1px solid #cccccc;\" src=\""+chqUrl+"></iframe>";
       // String html = "<iframe id=\\\"challengeFrame\\\" name=\\\"challengeFrame\\\" width=\\\"100%\\\" height=\\\"100%\\\" src=\\\""+chqUrl+"\\\"></iframe>";

      //  binding.webView.loadDataWithBaseURL(chqUrl, html, "text/html", null, null);
       // binding.webView.loadData(html, "text/html", null);
        binding.webView.loadUrl("www.google.com");
        binding.ivBack.setOnClickListener(this);
        paymentViewModel.getPaymentResponse().observe(getViewLifecycleOwner(), booleanEvent -> {
            Toast.makeText(context, "from payment response", Toast.LENGTH_SHORT).show();
            Boolean b = booleanEvent.getContentIfNotHandled();
            if (b != null) {
                dismissProgressBar();
                proceed(1, "Payment Successful");
            }
        });
        Bundle b1 = getArguments();
        if (b1 != null) {
            amount = b1.getDouble(KEY_AMOUNT, 0.0);
            ref = b1.getString("ref", null);
         //   sellerId = b1.getString("SELLER_ID", user.getNetplus_id());
          //  sellerName = b1.getString("SELLER_NAME", user.getBusiness_name());
            productId = b1.getString("id", null);
            agentId = b1.getString("a_id", null);
            String paylinkBaseUrl = "https://paylink.netpluspay.com?p=";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("a_id", sellerId);
            jsonObject.addProperty("ref", ref);
            jsonObject.addProperty("amount", amount);
            jsonObject.addProperty("type", "card");
            jsonObject.addProperty("agent", sellerName);
            String generatedPaylink = paylinkBaseUrl.concat(encodeQueryValue(encodeToBase64String(jsonObject.toString())));
            //Log.e("TAG: paylink", generatedPaylink);
            initWebView();
            showProgressBar();
          //  binding.webView.loadUrl(chqUrl);
        }


        String id = "MID5a8173ee134a69.88093801";
        //String javaScript = "javascript:document.activeElement.setAttribute('merchant','"+id+"');";
        String postData = "";
        try {

            /*String myAction = Uri.parse("upi://pay")
                    .buildUpon()
                    .appendQueryParameter("pa", custUPiDetails.getVpa())
                    .appendQueryParameter("pn", name)
                    .appendQueryParameter("tn", transaction_note)
                    .appendQueryParameter("am", pendingAmount.intValue()+"")
                    .appendQueryParameter("mam", "1")
                    .appendQueryParameter("cu", "INR")
                    .build().toString();*/

            /*postData = "merchant=" + URLEncoder.encode("TEST5d6530e4312c8"*//*"MID5a8173ee1388093801"*//*, "UTF-8")
                    + "&customer_name=" + URLEncoder.encode("test customer", "UTF-8")
                    + "&email=" + URLEncoder.encode("testcustomer@gmail.com", "UTF-8")
                    + "&amount=" + URLEncoder.encode(amount+""*//*"100"*//*, "UTF-8")
                    + "&currency_code=" + URLEncoder.encode("NGN", "UTF-8")
                    + "&narration=" + URLEncoder.encode("test order", "UTF-8")
                    + "&order_id=" + URLEncoder.encode("ORD1000001", "UTF-8")
                    + "&return_url=" + URLEncoder.encode("http://testsite.com/netplusresponse/", "UTF-8")
                    + "&recurring=" + URLEncoder.encode("no", "UTF-8")
            +"&key=" + URLEncoder.encode("test_ce9c597a059a5faa9c25e2e52d064f0d", "UTF-8");*/

            /*Test Merchant ID = TEST5d6530e4312c8
            Test Key = test_ce9c597a059a5faa9c25e2e52d064f0d*/


            postData = "merchantid=" + URLEncoder.encode(/*"TESTGTB800668C02"*/"TESTGTB800668C02"/*"TEST5d6530e4312c8"*/  /*"MID5a8173ee1388093801"*/, "UTF-8")
                    + "&full_name=" + URLEncoder.encode("test customer", "UTF-8")
                    + "&email=" + URLEncoder.encode("testcustomer@gmail.com", "UTF-8")
                    + "&amount=" + URLEncoder.encode(amount + ""/*"100"*/, "UTF-8")
                    + "&currency=" + URLEncoder.encode("NGN", "UTF-8")
                    + "&narration=" + URLEncoder.encode("test order", "UTF-8")
                    + "&order_id=" + URLEncoder.encode("ORD1000001", "UTF-8")
                    + "&return_url=" + URLEncoder.encode("https://netpluspay.com/testpayment/class/fcmb_response"/*"http://testsite.com/netplusresponse/"*/, "UTF-8")
                    + "&recurring=" + URLEncoder.encode("no", "UTF-8")
                    + "&payment_mode=" + URLEncoder.encode("API", "UTF-8")
                    + "&secure_secret="/*secret_key*/ + URLEncoder.encode("5F6E719AF9E76796FE313ECCDC6FDDC2"/*"test_ce9c597a059a5faa9c25e2e52d064f0d"*/, "UTF-8");

            //webview.postUrl(url,postData.getBytes());
            //binding.webView.postUrl("https://netpluspay.com/pay/test/", postData.getBytes());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /*StringBuilder sb = new StringBuilder();
        sb.append("?merchant=MID5a8173ee134a69.88093801");
        sb.append("&customer_name=test customer");
        sb.append("&email=testcustomer@email.com");
        sb.append("&amount=100");
        sb.append("&currency_code=NGN");
        sb.append("&narration=TestOrder");
        sb.append("&order_id=ORD1000001");
        sb.append("&return_url=http://testsite.com/netplusresponse/");
        sb.append("&recurring=no");
        String params = sb.toString();

        // Get cert from raw resource...
        binding.webView.loadUrl("https://netpluspay.com/pay/test"+params);*/
    }

    public Certificate getCertificate() {
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = getResources().openRawResource(R.raw.netpluspay_com); // stored at \app\src\main\res\raw
            final Certificate certificate = cf.generateCertificate(caInput);
            caInput.close();
            return certificate;
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("JavascriptInterface")
    public void initWebView() {
        binding.webView.setVerticalScrollBarEnabled(true);
        binding.webView.setHorizontalScrollBarEnabled(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setDatabaseEnabled(true);
        binding.webView.getSettings().setGeolocationEnabled(true);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }
        //loads the WebView completely zoomed out
        binding.webView.getSettings().setLoadWithOverviewMode(true);

        //true makes the Webview have a normal viewport such as a normal desktop browser
        //when false the webview will have a viewport constrained to it's own dimensions
        binding.webView.getSettings().setUseWideViewPort(false);

        binding.webView.getSettings().setSupportZoom(true);
        binding.webView.getSettings().setDisplayZoomControls(true);
        binding.webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        //binding.webView.setInitialScale(100);

        //override the web client to open all links in the same webview
        binding.webView.setWebViewClient(new MyWebViewClient());
        binding.webView.setWebChromeClient(new MyWebChromeClient());

        //Injects the supplied Java object into this WebView. The object is injected into the
        //JavaScript context of the main frame, using the supplied name. This allows the
        //Java object's public methods to be accessed from JavaScript.
        binding.webView.addJavascriptInterface(new WebAppInterface(getActivity(), resp -> {
            Log.e("TAG", "Received here");
            Log.e("TAG", "product id: " + productId);
            Log.e("TAG", resp.getStatus());
            Log.e("TAG", resp.toString());

            if (resp != null) {
                String success = "captured";
                if (resp.getStatus().equalsIgnoreCase(success)) {
                    if (productId != null) {
                        MerchantTransaction merchantTransaction =
                                new MerchantTransaction(
                                        ref,
                                        user.getNetplus_id(),
                                        PAYMENT_MODE_CARD,
                                        "",
                                        PAYMENT_STATUS_SUCCESS,
                                        "" + amount,
                                        "" + productId,
                                        "" + 1,
                                        sellerId
                                );
                        showProgressBar();
                        paymentViewModel.logSalesOrder(merchantTransaction);
                    } else
                        proceed(1, "Payment successful");
                } else {
                    Toast.makeText(requireContext(), "Payment Failed: " + resp.getDescription(), Toast.LENGTH_SHORT).show();
                    proceed(0, "Payment Failed: " + resp.getDescription());
                }

            } else {
                Toast.makeText(requireContext(), "Response received from server could not be interpreted", Toast.LENGTH_SHORT).show();
                proceed(0, "Fatal Error");
            }
        }), "Android");

        //binding.webView.addJavascriptInterface(new SampleWebAppInterface(), "Android");
    }

    public interface PaymentResponseListener {
        void onPaymentResponse(PaymentResponseFromnNetpluspay paymentResponseFromnNetpluspay);
    }

    private void proceed(int status, String message) {
        Log.e("TAG", message.concat(" ").concat("" + status));
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Payment Response");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Proceed", (dialog, which) -> {
            showFragment(new DashboardFragment(), DashboardFragment.class.getSimpleName());
        });
        builder.show();
    }

    public static class SampleWebAppInterface{
        public void sendData(String data){
            Log.d("TAG", "Data Received from WebPage: "+data);
        }
    }

    public static class WebAppInterface {
        Context mContext;
        String data;
        PaymentResponseListener paymentResponseListener;

        WebAppInterface(Context ctx, PaymentResponseListener paymentResponseListener) {
            this.mContext = ctx;
            this.paymentResponseListener = paymentResponseListener;
        }

        @JavascriptInterface
        public void sendData(String data) {
            //Get the string value to process
            this.data = data;
            if (data != null) {
                Log.e("TAG FROM SendData", data);
                PaymentResponseFromnNetpluspay paymentResponseFromnNetpluspay = new Gson().fromJson(data, PaymentResponseFromnNetpluspay.class);
                Log.e("TAG from Send Data", paymentResponseFromnNetpluspay.toString());
                paymentResponseListener.onPaymentResponse(paymentResponseFromnNetpluspay);
            } else
                paymentResponseListener.onPaymentResponse(null);
        }
    }


    //customize your web view client to open links from your own site in the
    //same web view otherwise just open the default browser activity with the URL
    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
//            if (Uri.parse(url).getHost().contains("concord") || Uri.parse(url).getHost().contains("mobipaid"))//demo.mysamplecode.com
//            {
//                return false;
//            }
//
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            startActivity(intent);
//            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //loaderDialog.dismissDialog(getActivity());
            dismissProgressBar();
            Log.e("--URL--", url + "--");

            /*if(url.contains("transaction/success") || url.contains("success"))////https://concorde.futurebuzz.mu/
            {
                //showResultDialog(true);
                finalizeBooking();

            }
            else if(url.contains("/transaction/paymentFailed")  || url.contains("fail"))
            {
                showResultDialog(false,"");

            }
            else if(url.contains("transaction/cancel")  || url.contains("cancel"))//transaction/cancel | url.contains("cancel")
            {
                Log.e("onPageFinished","No_match");
                DataController.getInstance().setDisableBackButton(false);
                activity.onBackPressed();

            }*/
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.e("Received Error TAG", error.toString());
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            String error = "Code: " + errorResponse.getStatusCode() + " Message: " + errorResponse.getReasonPhrase() + " URL:" + request.getUrl().toString();
            Log.e("received http TAG:", error);
        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            String message = "SSL Certificate error.";
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = "The certificate authority is not trusted.";
                    break;
                case SslError.SSL_EXPIRED:
                    message = "The certificate has expired.";
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = "The certificate Hostname mismatch.";
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = "The certificate is not yet valid.";
                    break;
            }
            message += " Do you want to continue anyway?";

            builder.setTitle("SSL Certificate Error");
            builder.setMessage(message);
            builder.setPositiveButton("continue", (dialog, which) -> handler.proceed());
            builder.setNegativeButton("cancel", (dialog, which) -> handler.cancel());
            final AlertDialog dialog = builder.create();
            dialog.show();
        }

        //@Override
        public void onReceivedSslError1(WebView view, SslErrorHandler handler, SslError error) {

            handler.proceed(); // Ignore SSL certificate errors

            /*// Get cert from SslError
            SslCertificate sslCertificate = error.getCertificate();
            Certificate cert = getX509Certificate(sslCertificate);
            Certificate certificate = getCertificate();
            if (cert != null && certificate != null){
                try {
                    // Reference: https://developer.android.com/reference/java/security/cert/Certificate.html#verify(java.security.PublicKey)
                    cert.verify(certificate.getPublicKey()); // Verify here...
                    handler.proceed();
                } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | SignatureException e) {
                    super.onReceivedSslError(view, handler, error);
                    e.printStackTrace();
                }
            } else {
                super.onReceivedSslError(view, handler, error);
            }*/

            /*super.onReceivedSslError(view, handler, error);
            Log.e("SslError", "onReceivedSslError");
            String message = "Certificate error.";
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = "The certificate authority is not trusted.";
                    break;
                case SslError.SSL_EXPIRED:
                    message = "The certificate has expired.";
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = "The certificate Hostname mismatch.";
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = "The certificate is not yet valid.";
                    break;
            }*/
        }
    }

    private Certificate getX509Certificate(SslCertificate sslCertificate) {
        Bundle bundle = SslCertificate.saveState(sslCertificate);
        byte[] bytes = bundle.getByteArray("x509-certificate");
        if (bytes == null) {
            return null;
        } else {
            try {
                CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
                return certFactory.generateCertificate(new ByteArrayInputStream(bytes));
            } catch (CertificateException e) {
                return null;
            }
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        //display alert message in Web View
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            //Log.d(LOG_TAG, message);
            new AlertDialog.Builder(view.getContext())
                    .setMessage(message).setCancelable(true).show();
            result.confirm();
            return true;
        }

    }


    public void showDialogSuccess() {
        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_otp_success);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.semi_transparent_gray)));

        ImageView imageView = dialog2.findViewById(R.id.imageView);
        ((Animatable) imageView.getDrawable()).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog2.dismiss();
                SharedPrefManager.setLoginStatus(true);
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        }, 2000);
        dialog2.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog2.show();
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_webview, container, false);
        return binding.getRoot();
    }


    public void showProgressBar() {
        mProgressDialog = ProgressDialog.show(context, null, null);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ffffff")));
        mProgressDialog.setCancelable(false);

        /*if (mProgressDialog!=null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
        }*/
    }

    public void dismissProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.clear();

//        menu.findItem(R.id.action_edit).setVisible(true);
//        menu.findItem(R.id.action_search).setVisible(false);
        //menu.removeItem(R.id.action_search);//clear();

        /*if(showDeleteMenu)
        {
            menu.findItem(R.id.action_search).setVisible(false);
            menu.removeItem(R.id.action_search);//clear();
        }
        else
        {
            menu.clear();

//            menu.removeItem(R.id.action_search);//clear();
//            menu.removeItem(R.id.action_search);//clear();
//
//            menu.findItem(R.id.action_search).setVisible(false);
//
//            menu.findItem(R.id.action_search).setVisible(false);
        }*/

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_fragment, menu);
//        MenuItem searchMenu = menu.findItem(R.id.action_edit);
//        searchMenu.setVisible(true);
        //MenuItem delete = menu.findItem(R.id.action_delete);
//        if(showDeleteMenu)
//        {
//            delete.setVisible(true);
//        }
//        else
//        {
//            delete.setVisible(false);
//        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            /*case R.id.action_edit:
                mDataBinding.btnUpdate.setVisibility(View.VISIBLE);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v == binding.ivBack) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }


    private OnItemClickListener.OnItemClickCallback onClick = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {


        }
    };
}
