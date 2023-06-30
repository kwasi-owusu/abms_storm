package com.woleapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woleapp.model.Inventory
import com.woleapp.model.Marketplace
import com.woleapp.model.SalesOrder
import com.woleapp.model.toFieldMap
import com.woleapp.network.MerchantsApiService
import com.woleapp.util.*
//import id.zelory.compressor.Compressor
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.File

class MarketplaceViewModel : ViewModel() {

    private val _message = MutableLiveData<Event<String>>()
    private val compositeDisposable = CompositeDisposable()
    private val _showProgressDialog = MutableLiveData<Event<Boolean>>()
    private val _merchantStoreExist = MutableLiveData<Event<Boolean>>()
    private val _stores = MutableLiveData<List<Marketplace>>()
    private val _inventories = MutableLiveData<List<Inventory>>()
    private val _isRefreshing = MutableLiveData<Event<Boolean>>()
    private val _fatalError = MutableLiveData<Event<Boolean>>()
    private val _salesOrderList = MutableLiveData<List<SalesOrder>>()

    val saleOrder: LiveData<List<SalesOrder>>
        get() = _salesOrderList

    val fatalError: LiveData<Event<Boolean>>
        get() = _fatalError

    val isRefreshing: LiveData<Event<Boolean>>
        get() = _isRefreshing

    val inventories: LiveData<List<Inventory>>
        get() = _inventories

    val stores: LiveData<List<Marketplace>>
        get() = _stores

    val merchantStoreExists: LiveData<Event<Boolean>>
        get() = _merchantStoreExist

    val showProgressDialog: LiveData<Event<Boolean>>
        get() = _showProgressDialog

    val message: LiveData<Event<String>>
        get() = _message

    val marketPlace = MutableLiveData<Marketplace>()
    private var imagePath: String? = null

    fun setMarketPlace(marketplace: Marketplace) {
        marketPlace.value = marketplace
    }

    private lateinit var merchantApiService: MerchantsApiService
    fun setMerchantApiService(apiService: MerchantsApiService) {
        merchantApiService = apiService
    }

    private fun validateFields(): Boolean {
        marketPlace.value?.let {
            if (it.name.isEmpty() || it.description.isEmpty() || imagePath.isNullOrEmpty()) {
                _message.value = Event("Please fill up all fields and add a logo from 1")
                return false
            }
            return true
        }
        _message.value = Event("Please fill up all fields and add a logo from 2.")
        return false
    }

    fun setImagePath(imagePath: String) {
        this.imagePath = imagePath
    }

    fun saveImage(context: Context) {
        if (!validateFields()) {
            return
        }
        val extension = getExtension(imagePath!!)
        Log.e("extension", "$extension--")
        val filePath = getFilePath(
            context, marketPlace.value!!.name,
            extension
        )!!
        Log.e("inventory_img_path", "$filePath--")
        val sourceFile = File(imagePath!!)
//        val encoderObservable = Compressor(context)
//            .setQuality(50)
//            .compressToFileAsFlowable(sourceFile)
//            .toObservable()
//            .flatMap<MultipartBody.Part> { file: File? ->
//                saveAndGetMultipart(
//                    "store_logo",
//                    file!!,
//                    File(filePath)
//                )
//            }
//        Single.fromObservable(encoderObservable)
//            .doOnSubscribe { _showProgressDialog.postValue(Event(true)) }
//            .doFinally { _showProgressDialog.postValue(Event(false)) }
//            .flatMap {
//                val payload = marketPlace.value!!
//                merchantApiService.createStore(payload.merchantId, payload.toMap(), it)
//            }.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { t1, t2 ->
//                t1?.let {
//                    _merchantStoreExist.value = Event(true)
//                    SharedPrefManager.setMerchantStore(marketPlace.value)
//                    Log.e("TAG", it.toString())
//                }
//                t2?.let {
//                    _message.value = Event("Error ${it.localizedMessage}")
//                    it.printStackTrace()
//                    val httpE : HttpException? = it as? HttpException
//                    httpE?.let {
//                        Log.e("TAG", it.message())
//                        Log.e("TAG", "" + it.code())
//                    }
//                    //Log.e("MarketplaceViewModel", it.localizedMessage!!)
//                }
//            }
        //    .disposeWith(compositeDisposable)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun checkIfMerchantHasCreatedStore(merchantId: String) =
        merchantApiService.checkStoreExists(merchantId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                t1?.let {
                    if (it.storeExists!!) {
                        SharedPrefManager.setMerchantStore(it.data)
                        _merchantStoreExist.value = Event(true)
                    } else
                        _merchantStoreExist.value = Event(false)
                }
                t2?.let {
                    _fatalError.value = Event(true)
                    _message.value = Event("Error: ${it.localizedMessage}")
                }
            }.disposeWith(compositeDisposable)

    fun getStores() =
        merchantApiService.getStores()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                t1?.let {
                    _stores.value = it.data
                }
                t2?.let {
                    _message.value = Event("Error: ${it.localizedMessage}")
                }
            }
            .disposeWith(compositeDisposable)

    fun getInventories() =
        merchantApiService.getAllProducts(marketPlace.value!!.merchantId)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _isRefreshing.postValue(Event(true)) }
            .doFinally { _isRefreshing.postValue(Event(false)) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                t1?.let {
                    _inventories.value = it.data
                }
                t2?.let {
                    _message.value = Event("Error: ${it.localizedMessage}")
                }
            }
            .disposeWith(compositeDisposable)

    fun getSellerOrders(netplusId: String) {
        merchantApiService.getSellersOrders(netplusId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                t1?.let {
                    it.data?.let { orders ->
                        if (orders.isEmpty())
                            _message.value = Event("You haven't received any orders")
                    }
                    _salesOrderList.value = it.data
                    Log.e("TAG", "data: ${it.data.toString()}")
                }
                t2?.let {
                    _message.value = Event("Error: ${it.localizedMessage}")
                }
            }.disposeWith(compositeDisposable)
    }

    fun getBuyersOrders(netplusId: String) {
        merchantApiService.getBuyerOrder(netplusId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                t1?.let {
                    it.data?.let { orders ->
                        if (orders.isEmpty())
                            _message.value = Event("You haven't made any orders.")
                    }
                    _salesOrderList.value = it.data
                    Log.e("TAG", "data: ${it.data.toString()}")
                }
                t2?.let {
                    _message.value = Event("Error: ${it.localizedMessage}")
                }
            }.disposeWith(compositeDisposable)
    }

    fun updateSalesOrder(it: SalesOrder) {
        Log.e("TAG", it.toFieldMap().toString())
        merchantApiService.updateSaleOrder(it.buyerId!!, it.sellerId, it.toFieldMap())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { _showProgressDialog.postValue(Event(false)) }
            .doOnSubscribe { _showProgressDialog.postValue(Event(true)) }
            .subscribe { t1, t2 ->
                t1?.let {
                    _message.value = Event("Status updated")
                }
                t2?.let {
                    _message.value = Event("Error: ${it.localizedMessage}")
                }
            }.disposeWith(compositeDisposable)
    }

}