package com.woleapp.db

import androidx.paging.PagedList
import com.woleapp.db.dao.TransactionsDao
import com.woleapp.model.AgentTransactions
import com.woleapp.model.Transactions
import com.woleapp.network.StormAPIService
import com.woleapp.util.SharedPrefManager
import com.woleapp.util.disposeWith
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

const val PAGE_SIZE = 20

enum class LoadState {
    LOADING_INITIAL,
    LOADING_MORE,
    LOADING_FINISHED,
    LOADING_ERROR
}

typealias LoadStateListener = (LoadState) -> Unit

class TransactionsBoundaryCallBack(
    private val stormAPIService: StormAPIService,
    private val transactionsDao: TransactionsDao,
    private val loadStateListener: LoadStateListener
) : PagedList.BoundaryCallback<Transactions>() {

    private var isLoading = false
    private var hasLoadedAllItems = false
    private var compositeDisposable = CompositeDisposable()

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
//        fetchTransactions(LoadState.LOADING_INITIAL)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Transactions) {
        super.onItemAtEndLoaded(itemAtEnd)
        fetchTransactions(LoadState.LOADING_MORE)
    }

    private fun fetchTransactions(loadingState: LoadState) {
        if (isLoading || hasLoadedAllItems)
            return
        stormAPIService.getTransactions(
            SharedPrefManager.getUser().netplus_id,
            SharedPrefManager.getNextAgentTransactionsPage(),
            PAGE_SIZE
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { isLoading = false }
            .subscribe(object : SingleObserver<AgentTransactions> {
                override fun onSuccess(t: AgentTransactions) {
                    if (t.transactions.isEmpty()) {
                        hasLoadedAllItems = true
                        loadStateListener.invoke(LoadState.LOADING_FINISHED)
                        return
                    }
                    SharedPrefManager.setNextAgentTransactionsPage(SharedPrefManager.getNextAgentTransactionsPage() + 1)
                    insertTransactionsToDatabase(t.transactions)
                }

                override fun onSubscribe(d: Disposable) {
                    isLoading = true
                    loadStateListener.invoke(loadingState)
                    d.disposeWith(compositeDisposable)
                }

                override fun onError(e: Throwable) {
                    loadStateListener.invoke(LoadState.LOADING_ERROR)
                }

            })
    }

    private fun insertTransactionsToDatabase(transactions: List<Transactions>) {
        transactionsDao.insertTransactions(transactions)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _, _ ->
                loadStateListener.invoke(LoadState.LOADING_FINISHED)
            }.disposeWith(compositeDisposable)
    }

    fun loadItemsAfterRefresh() {
        isLoading = false
        hasLoadedAllItems = false
        fetchTransactions(LoadState.LOADING_MORE)
    }
}