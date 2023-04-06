package com.woleapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woleapp.db.dao.MerchantTransactionDao
import com.woleapp.model.BaseResponse
import com.woleapp.model.MerchantTransaction
import com.woleapp.network.MerchantsApiService
import com.woleapp.network.StormAPIService
import com.woleapp.util.Event
import com.woleapp.util.disposeWith
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber

class MerchantTransactionViewModel : ViewModel() {

    private lateinit var merchantsApiService: MerchantsApiService
    private lateinit var stormApiService: StormAPIService
    private lateinit var transactionDao: MerchantTransactionDao
    private val disposables = CompositeDisposable()
    private val _isLoading = MutableLiveData<Event<Boolean>>()
    private val _message = MutableLiveData<Event<String>>()


    val message: LiveData<Event<String>>
        get() = _message

    val isLoading: LiveData<Event<Boolean>>
        get() = _isLoading

    fun setStormApiService(stormApiService: StormAPIService) {
        this.stormApiService = stormApiService
    }

    fun setMerchantApiService(merchantsApiService: MerchantsApiService) {
        this.merchantsApiService = merchantsApiService
    }

    fun setTransactionDao(transactionDao: MerchantTransactionDao) {
        this.transactionDao = transactionDao
    }

    fun refreshTransactions(merchantId: String) {
        merchantsApiService.getTransactions(merchantId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { _isLoading.value = Event(false) }
            .subscribe(object : SingleObserver<BaseResponse<List<MerchantTransaction>>> {
                override fun onSuccess(t: BaseResponse<List<MerchantTransaction>>) {
                    t.data?.let {
                        if (it.isNotEmpty()) {
                            saveTransactionsInLocalDatabase(it)
                        }
                    }
                }

                override fun onSubscribe(d: Disposable) {
                    d.disposeWith(disposables)
                    _isLoading.value = Event(true)
                }

                override fun onError(e: Throwable) {
                    _message.value =
                        Event("Cannot refresh your transactions at the moment ${e.localizedMessage}")
                }

            })
    }

    fun getNotification(merchantTransaction: MerchantTransaction, access: String) {
        Timber.e(merchantTransaction.reference)
        stormApiService.getNotificationByReference(
            merchantTransaction.merchantId,
            access,
            merchantTransaction.reference
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { _isLoading.value = Event(false) }
            .subscribe(object : SingleObserver<Response<Any>> {
                override fun onSuccess(t: Response<Any>) {
                   Timber.e(t.toString())
                }

                override fun onSubscribe(d: Disposable) {
                    d.disposeWith(disposables)
                    _isLoading.value = Event(true)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e.localizedMessage ?: "Error")
                    _message.value = Event("Could not verify this payment ${e.localizedMessage}")
                }

            })
    }


    fun saveTransactionsInLocalDatabase(transactions: List<MerchantTransaction>) {
        transactionDao.insertNewTransactions(transactions)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                    d.disposeWith(disposables)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e.localizedMessage ?: "Error")
                }

            })
    }
}