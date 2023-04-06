package com.woleapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.woleapp.model.*
import com.woleapp.network.StormUtilitiesApiService
import com.woleapp.util.Event
import com.woleapp.util.disposeWith
import com.woleapp.util.getResponseBody
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import timber.log.Timber


class UtilitiesViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private lateinit var stormUtilitiesApiService: StormUtilitiesApiService
    private lateinit var walletCheckObservable: Observable<retrofit2.Response<Any>>
    private val gson = Gson()
    val payloadMutableLiveData: MutableLiveData<UtilitiesPayload> by lazy {
        MutableLiveData<UtilitiesPayload>().also {
            it.value = UtilitiesPayload()
        }
    }
    private val _showProgressMutableLiveData: MutableLiveData<Event<Boolean>> by lazy {
        MutableLiveData<Event<Boolean>>().also {
            it.value = Event(false)
        }
    }

    private val _validateResponse = MutableLiveData<Event<ValidateBillResponse>>()

    val validateBillResponse: LiveData<Event<ValidateBillResponse>>
        get() = _validateResponse

    val showProgress: LiveData<Event<Boolean>> = _showProgressMutableLiveData

    fun setStormUtilitiesApiService(stormUtilitiesApiService: StormUtilitiesApiService) {
        this.stormUtilitiesApiService = stormUtilitiesApiService
    }

    fun setWalletCheckObservable(walletCheckObservable: Observable<retrofit2.Response<Any>>) {
        this.walletCheckObservable = walletCheckObservable
    }

    private val _message = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = _message

    private val _result = MutableLiveData<Event<NetworkResponse>>()
    val result: LiveData<Event<NetworkResponse>> = _result

    private val _billResponse = MutableLiveData<ValidateBillResponse>()

    /*fun setUtilityPrice(utilityAmount: Long) {
        val utilitiesPayload = payloadMutableLiveData.value
        payloadMutableLiveData.value = utilitiesPayload?.apply {
            amount = utilityAmount
        }
    }*/

    fun setUtilityService(utilityService: String) {
        val utilitiesPayload = payloadMutableLiveData.value
        payloadMutableLiveData.value = utilitiesPayload?.apply {
            service = utilityService.replace(" ", "")
        }
    }

    fun setUtilityBillType(utilityBillType: String) {
        val utilitiesPayload = payloadMutableLiveData.value
        payloadMutableLiveData.value = utilitiesPayload?.apply {
            billType = utilityBillType
        }
    }

    fun setUtilityProvider(utilityProvider: String) {
        val utilitiesPayload = payloadMutableLiveData.value
        //_message.value = Event(utilityProvider)
        payloadMutableLiveData.value = utilitiesPayload?.apply {
            provider = utilityProvider.replace(" ", "")
        }
    }

    fun setUtilityPackage(utilityPackage: String) {
        val utilitiesPayload = payloadMutableLiveData.value
        payloadMutableLiveData.value = utilitiesPayload?.apply {
            billPackage = utilityPackage
        }
    }

    fun setUtilityServiceType(utilityServiceType: String) {
        val utilitiesPayload = payloadMutableLiveData.value
        payloadMutableLiveData.value = utilitiesPayload?.apply {
            serviceType = utilityServiceType
        }
    }

    fun validateUtilityBill() {
        val utilitiesPayload = payloadMutableLiveData.value
        if (utilitiesPayload?.destinationAccount.isNullOrEmpty()) {
            _message.value = Event("Please enter the destination number")
            return
        }
        if (utilitiesPayload?.stringAmount.isNullOrEmpty())
            utilitiesPayload?.stringAmount = "0"
        utilitiesPayload?.amount = utilitiesPayload?.stringAmount?.replace(",", "")?.toFloat() ?: 0f
        if (utilitiesPayload?.billType == "POWER" && utilitiesPayload.amount < 1000) {
            _message.value = Event("Amount should not be less than \u20A61000")
            return
        }
        if (utilitiesPayload?.amount!! < 25) {
            _message.value = Event("Amount should not be less than \u20A625")
            return
        }
        Timber.e("Principal Amount: ${utilitiesPayload.amount}")
        val next = when (utilitiesPayload.billType) {
            "POWER", "TV" -> ::verifyDestinationAccount
            else -> ::checkHasSufficientBalance
        }
        payloadMutableLiveData.value = utilitiesPayload
        next.invoke()
    }

    private fun verifyDestinationAccount() {
        val utilitiesPayload = payloadMutableLiveData.value
        Timber.e(gson.toJson(utilitiesPayload))
        stormUtilitiesApiService.validateBill(utilitiesPayload!!)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _showProgressMutableLiveData.value = Event(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ValidateBillResponse> {
                override fun onSuccess(t: ValidateBillResponse) {
                    Timber.e(t.toString())
                    if (t.billVerifiedOnline == "FALSE" || t.billVerifiedOnline == null) {
                        val errorResponse =
                            ErrorNetworkResponse("Could not verify destination Number: ${utilitiesPayload.destinationAccount}")
                        _result.value = Event(errorResponse)
                        return
                    }
                    _billResponse.value = t
                    checkHasSufficientBalance()
                }

                override fun onSubscribe(d: Disposable) {
                    d.disposeWith(compositeDisposable)
                }

                override fun onError(e: Throwable) {
                    val errorResponse =
                        ErrorNetworkResponse("Could not verify destination Number: ${utilitiesPayload.destinationAccount}")
                    _result.value = Event(errorResponse)
                }

            })
    }

    fun checkHasSufficientBalance() {
        val validateBillResponse = _billResponse.value
        val utilitiesPayload = payloadMutableLiveData.value!!
        val disposable = walletCheckObservable
            .doOnSubscribe { _showProgressMutableLiveData.value = Event(true) }
            .subscribe({
                val data = JSONObject(Gson().toJson(it.body()))
                if (data.getDouble("availableBalance") < utilitiesPayload.amount) {
                    _result.value =
                        Event(ErrorNetworkResponse("Your wallet balance of \u20A6${data.getDouble("availableBalance")} is not sufficient for this transaction"))
                    return@subscribe
                }
                val billResponse = validateBillResponse ?: ValidateBillResponse(
                    billAccountId = utilitiesPayload.destinationAccount
                )
                billResponse.apply {
                    provider = utilitiesPayload.provider
                    billPackage = utilitiesPayload.billPackage
                    fees = "\u20A6${0}"
                    amount = "\u20A6${utilitiesPayload.amount}"
                }
                _validateResponse.value = Event(billResponse)
            }, {
                Timber.e("error occurred: ${it.localizedMessage}")
                _result.value = Event(ErrorNetworkResponse("An unexpected error occurred"))
            })
       disposable.disposeWith(compositeDisposable)
    }


    /*fun getServiceFee() {
        val validateBillResponse = _billResponse.value
        val utilitiesPayload = payloadMutableLiveData.value!!
        stormUtilitiesApiService.getServiceFee(utilitiesPayload.provider)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _showProgressMutableLiveData.value = Event(true) }
            .subscribe(object : SingleObserver<ServiceFeeResponse> {
                override fun onSuccess(t: ServiceFeeResponse) {
                    Log.e("tag", t.toString())
                    val billResponse = validateBillResponse ?: ValidateBillResponse(
                        billAccountId = utilitiesPayload.destinationAccount
                    )
                    billResponse.apply {
                        provider = utilitiesPayload.provider
                        billPackage = utilitiesPayload.billPackage
                        fees = "\u20A6${0}"
                        amount = "\u20A6${utilitiesPayload.amount}"
                    }
                    _validateResponse.value = Event(billResponse)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    val error = e.getResponseBody()
                    val errorResponse =
                        gson.fromJson<ErrorResponse>(error, ErrorResponse::class.java)
                    _result.value = Event(errorResponse)
                }

            })
    }*/

    fun payBill() {
        val utilitiesPayload = payloadMutableLiveData.value
        Timber.e(gson.toJson(utilitiesPayload))
        stormUtilitiesApiService.payBills(utilitiesPayload!!)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _showProgressMutableLiveData.value = Event(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { _showProgressMutableLiveData.value = Event(false) }
            .subscribe(object : SingleObserver<SuccessNetworkResponse> {
                override fun onSuccess(t: SuccessNetworkResponse) {
                    val data = t.data
                    Timber.e("ref: ${data?.reference}")
                    if (utilitiesPayload.billType == "POWER") {
                        t.message = if (data?.token.isNullOrEmpty())
                            "Your power payment was successful but no token was generated, please contact support"
                        else {
                            "${t.message}\n\nMeter Token: ${data?.token}"
                        }
                    }
                    _result.value = Event(t)
                }

                override fun onSubscribe(d: Disposable) {
                   d.disposeWith(compositeDisposable)
                }

                override fun onError(e: Throwable) {
                    val error = e.getResponseBody()
                    val errorResponse =
                        gson.fromJson(error, ErrorNetworkResponse::class.java)
                    _result.value = Event(errorResponse)
                }

            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
