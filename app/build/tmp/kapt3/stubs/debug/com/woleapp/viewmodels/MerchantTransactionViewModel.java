package com.woleapp.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\bJ\u000e\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\bJ\u0014\u0010\u001d\u001a\u00020\u00172\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00190\u001fJ\u000e\u0010 \u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010!\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\"\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0015R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\f8F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\f8F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\rR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/woleapp/viewmodels/MerchantTransactionViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_isLoading", "Landroidx/lifecycle/MutableLiveData;", "Lcom/woleapp/util/Event;", "", "_message", "", "disposables", "Lio/reactivex/disposables/CompositeDisposable;", "isLoading", "Landroidx/lifecycle/LiveData;", "()Landroidx/lifecycle/LiveData;", "merchantsApiService", "Lcom/woleapp/network/MerchantsApiService;", "message", "getMessage", "stormApiService", "Lcom/woleapp/network/StormAPIService;", "transactionDao", "Lcom/woleapp/db/dao/MerchantTransactionDao;", "getNotification", "", "merchantTransaction", "Lcom/woleapp/model/MerchantTransaction;", "access", "refreshTransactions", "merchantId", "saveTransactionsInLocalDatabase", "transactions", "", "setMerchantApiService", "setStormApiService", "setTransactionDao", "app_debug"})
public final class MerchantTransactionViewModel extends androidx.lifecycle.ViewModel {
    private com.woleapp.network.MerchantsApiService merchantsApiService;
    private com.woleapp.network.StormAPIService stormApiService;
    private com.woleapp.db.dao.MerchantTransactionDao transactionDao;
    private final io.reactivex.disposables.CompositeDisposable disposables = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.util.Event<java.lang.Boolean>> _isLoading = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.util.Event<java.lang.String>> _message = null;
    
    public MerchantTransactionViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.woleapp.util.Event<java.lang.String>> getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.woleapp.util.Event<java.lang.Boolean>> isLoading() {
        return null;
    }
    
    public final void setStormApiService(@org.jetbrains.annotations.NotNull()
    com.woleapp.network.StormAPIService stormApiService) {
    }
    
    public final void setMerchantApiService(@org.jetbrains.annotations.NotNull()
    com.woleapp.network.MerchantsApiService merchantsApiService) {
    }
    
    public final void setTransactionDao(@org.jetbrains.annotations.NotNull()
    com.woleapp.db.dao.MerchantTransactionDao transactionDao) {
    }
    
    public final void refreshTransactions(@org.jetbrains.annotations.NotNull()
    java.lang.String merchantId) {
    }
    
    public final void getNotification(@org.jetbrains.annotations.NotNull()
    com.woleapp.model.MerchantTransaction merchantTransaction, @org.jetbrains.annotations.NotNull()
    java.lang.String access) {
    }
    
    public final void saveTransactionsInLocalDatabase(@org.jetbrains.annotations.NotNull()
    java.util.List<com.woleapp.model.MerchantTransaction> transactions) {
    }
}