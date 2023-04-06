package com.woleapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.woleapp.model.*

import com.woleapp.network.MerchantsApiService
import com.woleapp.network.StormAPIService
import com.woleapp.util.*
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

const val PAYMENT_MODE_CASH = "CASH"
const val PAYMENT_MODE_TRANSFER = "TRANSFER"
const val PAYMENT_MODE_POS = "POS"
const val PAYMENT_MODE_QR = "QR"
const val PAYMENT_MODE_CARD = "CARD"
const val PAYMENT_MODE_PAYLINK = "PAYLINK"
const val PAYMENT_STATUS_PENDING = "PENDING"
const val PAYMENT_STATUS_SUCCESS = "SUCCESS"

class PaymentViewModel : ViewModel() {
    private lateinit var stormAPIService: StormAPIService
    private lateinit var merchantsApiService: MerchantsApiService
    private val disposables = CompositeDisposable()
    private val _loadingStatus = MutableLiveData<Event<Boolean>>()
    private val _paymentResponse = MutableLiveData<Event<Boolean>>()
    private val _merchantTransaction = MutableLiveData<MerchantTransaction>()
    private val _cashTransaction = MutableLiveData<CashTransaction>()
    private val _proceedToPayment = MutableLiveData<Event<MerchantTransaction>>()

    val proceedToPayment: LiveData<Event<MerchantTransaction>>
        get() = _proceedToPayment

    fun setCashTransaction(cashTransaction: CashTransaction) {
        _cashTransaction.value = cashTransaction
    }

    fun setMerchantTransaction(merchantTransaction: MerchantTransaction) {
        _merchantTransaction.value = merchantTransaction
    }

    val merchantTransaction: MerchantTransaction?
        get() = _merchantTransaction.value

    val paymentResponse: LiveData<Event<Boolean>>
        get() = _paymentResponse

    val loadingStatus: LiveData<Event<Boolean>>
        get() = _loadingStatus

    fun setStormAPIService(stormAPIService: StormAPIService) {
        this.stormAPIService = stormAPIService
    }

    fun setStormMerchantApiService(merchantsApiService: MerchantsApiService) {
        this.merchantsApiService = merchantsApiService
    }

    fun logCashTransaction() {
        val payload = _cashTransaction.value!!.toTransactionJson()
        Log.e("tag", Gson().toJson(payload))
        stormAPIService.logNewTransaction(payload)
            .subscribeOn(Schedulers.io())
            .doFinally { _loadingStatus.postValue(Event(false)) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<Any>> {
                override fun onSuccess(t: Response<Any>) {
                    Log.e("tag", t.toString())
                    logSalesOrder(
                        merchantTransaction!!,
                        merchantTransaction!!.productCount!!,
                        merchantTransaction!!.productId!!
                    )
                }

                override fun onSubscribe(d: Disposable) {
                    d.disposeWith(disposables)
                    _loadingStatus.value = Event(true)
                }

                override fun onError(e: Throwable) {
                    Log.e("tag", e.localizedMessage!!)
                    Log.e("tag", e.getResponseBody())
                    _paymentResponse.value = Event(false)
                }

            })
        Log.e("tag", payload.toString())
    }

    @JvmOverloads
    fun logSalesOrder(
        merchantTransaction: MerchantTransaction,
        productCount: String = "1",
        productId: String = "1"
    ) {
        val salesOrder = SalesOrder(
            merchantTransaction.reference,
            PAYMENT_STATUS_SUCCESS,
            productCount,
            merchantTransaction.amount,
            merchantTransaction.paymentMethod,
            merchantTransaction.productId ?: productId,
            merchantTransaction.sellerId ?: SharedPrefManager.getUser().netplus_id
        )
        //Log.e("TAG", salesOrder.toString())
        Log.e("TAG", salesOrder.toFieldMap().toString())
        merchantsApiService.checkout(
            merchantTransaction.merchantId,
            salesOrder.toFieldMap()
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { _loadingStatus.value = Event(false) }
            .subscribe(object : SingleObserver<BaseResponse<Any>> {
                override fun onSuccess(t: BaseResponse<Any>) {
                    _paymentResponse.value = Event(true)
                    Log.e("TAG", "Sales order success")
                }

                override fun onSubscribe(d: Disposable) {
                    d.disposeWith(disposables)
                }

                override fun onError(e: Throwable) {
                    _paymentResponse.value = Event(false)
                    Log.e("TAG", "Sales order error ${e.localizedMessage!!}")
                }

            })
    }

    fun logNewTransaction() {
        merchantsApiService.logTransaction(
            merchantTransaction!!.merchantId,
            merchantTransaction!!.toFieldMap()
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { _loadingStatus.value = Event(false) }
            .subscribe(object : SingleObserver<BaseResponse<Any>> {
                override fun onSuccess(t: BaseResponse<Any>) {
                    _proceedToPayment.value = Event(merchantTransaction!!)
                }

                override fun onSubscribe(d: Disposable) {
                    d.disposeWith(disposables)
                    _loadingStatus.value = Event(true)
                }

                override fun onError(e: Throwable) {
                    Log.e("TAG", e.localizedMessage!!)
                    _paymentResponse.value = Event(false)
                }

            })
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun getSessionCode(transaction_type: Int) {
        stormAPIService.sessionCode.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<SessionCode> {
                override fun onSuccess(t: SessionCode) {
                    Log.e("TAG", t.toString())
                    val transaction = _merchantTransaction.value
                    transaction?.reference = t.sessionCode
                    _merchantTransaction.value = transaction
                    if (transaction_type == Constants.TRANSACTION_CASH_OUT || transaction_type == Constants.TRANSACTION_FUND_WALLET)
                        _proceedToPayment.value = Event(merchantTransaction!!)
                    else
                        logNewTransaction()

                }

                override fun onSubscribe(d: Disposable) {
                    _loadingStatus.value = Event(true)
                    d.disposeWith(disposables)
                }

                override fun onError(e: Throwable) {
                    _loadingStatus.value = Event(false)
                    _paymentResponse.value = Event(false)
                    Log.e("TAG", "Session code: " + e.localizedMessage!!)
                }

            })
    }
}