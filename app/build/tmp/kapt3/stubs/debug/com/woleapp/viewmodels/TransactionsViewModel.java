package com.woleapp.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0013J\u0012\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00170\u0016J\b\u0010\u0019\u001a\u00020\u0013H\u0014J\u000e\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u000fJ\u000e\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/woleapp/viewmodels/TransactionsViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "boundaryCallBack", "Lcom/woleapp/db/TransactionsBoundaryCallBack;", "disposables", "Lio/reactivex/disposables/CompositeDisposable;", "fetchedAgentTransactions", "Landroidx/lifecycle/MutableLiveData;", "Lcom/woleapp/model/AgentTransactions;", "loadState", "Lcom/woleapp/db/LoadState;", "getLoadState", "()Landroidx/lifecycle/MutableLiveData;", "stormApiService", "Lcom/woleapp/network/StormAPIService;", "transactionsDao", "Lcom/woleapp/db/dao/TransactionsDao;", "forceRefresh", "", "getAgencyTransactions", "getTransactions", "Landroidx/lifecycle/LiveData;", "Landroidx/paging/PagedList;", "Lcom/woleapp/model/Transactions;", "onCleared", "setStormApiService", "stormAPIService", "setTransactionsDao", "app_debug"})
public final class TransactionsViewModel extends androidx.lifecycle.ViewModel {
    private com.woleapp.db.dao.TransactionsDao transactionsDao;
    private com.woleapp.network.StormAPIService stormApiService;
    private com.woleapp.db.TransactionsBoundaryCallBack boundaryCallBack;
    private io.reactivex.disposables.CompositeDisposable disposables;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.woleapp.db.LoadState> loadState = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.model.AgentTransactions> fetchedAgentTransactions = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.woleapp.db.LoadState> getLoadState() {
        return null;
    }
    
    public final void setTransactionsDao(@org.jetbrains.annotations.NotNull()
    com.woleapp.db.dao.TransactionsDao transactionsDao) {
    }
    
    public final void setStormApiService(@org.jetbrains.annotations.NotNull()
    com.woleapp.network.StormAPIService stormAPIService) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<androidx.paging.PagedList<com.woleapp.model.Transactions>> getTransactions() {
        return null;
    }
    
    public final void getAgencyTransactions() {
    }
    
    public final void forceRefresh() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
    
    public TransactionsViewModel() {
        super();
    }
}