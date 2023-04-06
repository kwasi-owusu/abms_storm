package com.woleapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.woleapp.db.LoadState
import com.woleapp.db.PAGE_SIZE
import com.woleapp.db.TransactionsBoundaryCallBack
import com.woleapp.db.dao.TransactionsDao
import com.woleapp.model.AgentTransactions
import com.woleapp.model.Transactions
import com.woleapp.network.StormAPIService
import com.woleapp.util.SharedPrefManager
import com.woleapp.util.disposeWith
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TransactionsViewModel : ViewModel() {

    private lateinit var transactionsDao: TransactionsDao
    private lateinit var stormApiService: StormAPIService
    private lateinit var boundaryCallBack: TransactionsBoundaryCallBack
    private var disposables: CompositeDisposable = CompositeDisposable()
    val loadState = MutableLiveData<LoadState>()
    private val fetchedAgentTransactions = MutableLiveData<AgentTransactions>()

    fun setTransactionsDao(transactionsDao: TransactionsDao) {
        this.transactionsDao = transactionsDao
    }

    fun setStormApiService(stormAPIService: StormAPIService) {
        this.stormApiService = stormAPIService
    }

    fun getTransactions(): LiveData<PagedList<Transactions>> {
        boundaryCallBack =
            TransactionsBoundaryCallBack(
                stormApiService,
                transactionsDao
            ) {
                loadState.value = it
            }
        val dataSourceFactory = transactionsDao.getTransactions()
        val pagedListBuilder = LivePagedListBuilder<Int, Transactions>(dataSourceFactory, PAGE_SIZE)
            .apply {
                setBoundaryCallback(boundaryCallBack)
            }
        return pagedListBuilder.build()
    }

    fun getAgencyTransactions() {

    }

    fun forceRefresh() {
//        stormApiService.getTransactions(
//            SharedPrefManager.getUser().netplus_id,
//            1, PAGE_SIZE
//        ).flatMap {
//            SharedPrefManager.setNextAgentTransactionsPage(2)
//            fetchedAgentTransactions.postValue(it)
//            transactionsDao.deleteOldTransactions()
//        }.flatMap {
//            transactionsDao.insertTransactions(fetchedAgentTransactions.value!!.transactions)
//        }.subscribeOn(Schedulers.io())
//            .doOnSubscribe { loadState.value = LoadState.LOADING_INITIAL }
//            .doOnError { loadState.value = LoadState.LOADING_ERROR }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { items, throwable ->
//                throwable?.let {
//                    loadState.value = LoadState.LOADING_FINISHED
//                }
//                items?.let {
//                    Log.e("TAG", "Success")
//                    loadState.value = LoadState.LOADING_FINISHED
//                    boundaryCallBack.loadItemsAfterRefresh()
//                }
//
//            }.disposeWith(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}