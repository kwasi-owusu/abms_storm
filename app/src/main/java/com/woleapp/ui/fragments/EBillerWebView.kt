@file:Suppress("DEPRECATION")

package com.woleapp.ui.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.woleapp.databinding.FragmentEbillsWebviewBinding
import com.woleapp.model.EBillerResponse
import com.woleapp.network.StormAPIClient
import com.woleapp.network.getEbillsService
import com.woleapp.ui.activity.HomeActivity
import com.woleapp.util.SharedPrefManager
import com.woleapp.util.Singletons
import com.woleapp.util.Utilities
import com.woleapp.util.disposeWith
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import timber.log.Timber
import kotlin.math.roundToInt

typealias WebInterfaceListener = (data: String?) -> Unit


class EBillerWebView : BaseFragment() {

    private var hasLoadedSecond = false
    private val disposable = CompositeDisposable()
    private lateinit var progressDialog: ProgressDialog
    private var hasDataLoaded = false
    private lateinit var utilities: Utilities

    companion object {
        fun newInstance(id: String) = EBillerWebView().apply {
            arguments = Bundle().apply {
                putString("e_bills_id", id)
            }
        }
    }

    private lateinit var binding: FragmentEbillsWebviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEbillsWebviewBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
            lifecycleOwner = viewLifecycleOwner
        }
        binding.swipeRefresh.isRefreshing = true
        binding.swipeRefresh.setOnRefreshListener {
            if (hasDataLoaded)
                binding.swipeRefresh.isRefreshing = false
            else
                getBiller()
        }
        utilities = Utilities(requireContext())
        progressDialog = ProgressDialog(requireContext())
        initWebView()
        getBiller()
        return binding.root
    }

    private fun getBiller() {
        val eBillId = arguments!!.getString("e_bills_id")?.toIntOrNull() ?: 1
        Timber.e(eBillId.toString())
        getEbillsService().getBiller(eBillId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                binding.swipeRefresh.isRefreshing = false
            }
            .subscribe { t1, t2 ->
                t1?.let {
                    //  Timber.e(it)
                    Timber.e("loading")
                    //Timber.e(it)
                    hasDataLoaded = true
                    binding.webView.loadDataWithBaseURL(null, it, "text/html", "utf-8", null)
                }
                t2?.let {
                    Toast.makeText(
                        requireContext(),
                        "Error Occurred, Pull down to refresh",
                        Toast.LENGTH_SHORT
                    ).show()
                    Timber.e(it)
                }
            }.disposeWith(disposable)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webView.apply {
            isVerticalScrollBarEnabled = true
            isHorizontalScrollBarEnabled = true

            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                databaseEnabled = true
                setGeolocationEnabled(true)
            }
            if (Build.VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webView, true)
            } else {
                CookieManager.getInstance().setAcceptCookie(true)
            }
            //loads the WebView completely zoomed out
            settings.loadWithOverviewMode = true

            //true makes the Webview have a normal viewport such as a normal desktop browser
            //when false the webview will have a viewport constrained to it's own dimensions

            //true makes the Webview have a normal viewport such as a normal desktop browser
            //when false the webview will have a viewport constrained to it's own dimensions
            settings.useWideViewPort = false

            settings.setSupportZoom(true)
            settings.displayZoomControls = true
            settings.defaultZoom = WebSettings.ZoomDensity.CLOSE
            //binding.webView.setInitialScale(100);

            //override the web client to open all links in the same webview
            //binding.webView.webViewClient = MyWebViewClient()
            webChromeClient = MyWebChromeClient()
            addJavascriptInterface(WebAppInterface() {
                Timber.e(it)
                val b = Singletons.getGsonInstance().fromJson(it, EBillerResponse::class.java)
                Timber.e(b.toString())
                if (hasLoadedSecond.not()) {
                    loadSecond(b)
                } else
                    getWalletBalance(b)
            }, "Android")
        }
    }

    private fun getWalletBalance(billerResponse: EBillerResponse) {
        val stormId = SharedPrefManager.getUser().netplus_id
        Single.fromObservable(StormAPIClient.getWalletBalance(stormId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                t1?.let {
                    val data = JSONObject(Gson().toJson(it.body()))
                    Timber.e(data.toString())
                    val availableBalance: Double = data.getDouble("availableBalance")
                    if ((billerResponse.fields.Amount?.toDoubleOrNull()
                            ?: 0.0) > availableBalance
                    ) {
                        utilities.showAlertDialogOk(
                            "Insufficient Funds",
                            "Your available balance of \u20A6${availableBalance.roundToInt()} is not sufficient for this payment",
                            requireActivity() as HomeActivity,
                            true
                        )
                        return@let
                    }
                    utilities.showAlertDialogOk(
                        "Payment Completed",
                        "Your payment of \u20A6${billerResponse.fields.Amount} has been processed successfully",
                        requireActivity() as HomeActivity,
                        true
                    )
                    val ledgerBalance = data.getDouble("ledgerBalance")
                    setNewBalance(
                        availableBalance,
                        ledgerBalance,
                        billerResponse.fields.Amount?.toDoubleOrNull() ?: 0.0
                    )
                }
                t2?.let {
                    utilities
                        .showAlertDialogOk(
                            "Error",
                            "An unfortunate error occurred",
                            requireActivity() as HomeActivity,
                            true
                        )
                }
            }.disposeWith(compositeDisposable = disposable)

    }

    private fun setNewBalance(availableBalance: Double, ledgerBalance: Double, amountPaid: Double) {
        val user = SharedPrefManager.getUser()
        user.availableBalance = (availableBalance - amountPaid).toString()
        user.ledgerBalance = (ledgerBalance - amountPaid).toString()
        SharedPrefManager.setUser(user)
        (requireActivity() as HomeActivity).apply {
//            setAvailableBalance(user.availableBalance)
//            setLedgerBalance(user.ledgerBalance)
        }
    }

    private fun loadSecond(b: EBillerResponse) {
        Timber.e("loading second")
        getEbillsService().validateForm(b)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {

            }
            .doFinally {
                Timber.e("Thread on do finally: ${Thread.currentThread().name}")
            }
            .subscribe { t1, t2 ->
                t1?.let {
                    hasLoadedSecond = true
                    Timber.e("Thread in T1: ${Thread.currentThread().name}")
                    binding.webView.loadDataWithBaseURL(null, it, "text/html", "utf-8", null)
                }
                t2?.let {
                    Timber.e(it)
                }
            }.disposeWith(disposable)
    }

    private class MyWebChromeClient : WebChromeClient() {
        //display alert message in Web View
        override fun onJsAlert(
            view: WebView,
            url: String,
            message: String,
            result: JsResult
        ): Boolean {
            //Log.d(LOG_TAG, message);
            AlertDialog.Builder(view.context)
                .setMessage(message).setCancelable(true).show()
            result.confirm()
            return true
        }
    }

    override fun onDetach() {
        super.onDetach()
        disposable.clear()
    }

    class WebAppInterface internal constructor(
        var l: WebInterfaceListener
    ) {
        var data: String? = null

        @JavascriptInterface
        fun sendData(data: String?) {
            //Get the string value to process
            l.invoke(data)
        }
    }
}