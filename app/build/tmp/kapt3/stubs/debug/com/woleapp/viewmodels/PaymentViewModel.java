package com.woleapp.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u0006\u0010\"\u001a\u00020\u001fJ\u0006\u0010#\u001a\u00020\u001fJ$\u0010$\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\n2\b\b\u0002\u0010%\u001a\u00020&2\b\b\u0002\u0010\'\u001a\u00020&H\u0007J\b\u0010(\u001a\u00020\u001fH\u0014J\u000e\u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u0005J\u000e\u0010+\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\nJ\u000e\u0010,\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010-\u001a\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u0017R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00108F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\n8F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00108F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0012R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u00108F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0012R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/woleapp/viewmodels/PaymentViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_cashTransaction", "Landroidx/lifecycle/MutableLiveData;", "Lcom/woleapp/model/CashTransaction;", "_loadingStatus", "Lcom/woleapp/util/Event;", "", "_merchantTransaction", "Lcom/woleapp/model/MerchantTransaction;", "_paymentResponse", "_proceedToPayment", "disposables", "Lio/reactivex/disposables/CompositeDisposable;", "loadingStatus", "Landroidx/lifecycle/LiveData;", "getLoadingStatus", "()Landroidx/lifecycle/LiveData;", "merchantTransaction", "getMerchantTransaction", "()Lcom/woleapp/model/MerchantTransaction;", "merchantsApiService", "Lcom/woleapp/network/MerchantsApiService;", "paymentResponse", "getPaymentResponse", "proceedToPayment", "getProceedToPayment", "stormAPIService", "Lcom/woleapp/network/StormAPIService;", "getSessionCode", "", "transaction_type", "", "logCashTransaction", "logNewTransaction", "logSalesOrder", "productCount", "", "productId", "onCleared", "setCashTransaction", "cashTransaction", "setMerchantTransaction", "setStormAPIService", "setStormMerchantApiService", "app_debug"})
public final class PaymentViewModel extends androidx.lifecycle.ViewModel {
    private com.woleapp.network.StormAPIService stormAPIService;
    private com.woleapp.network.MerchantsApiService merchantsApiService;
    private final io.reactivex.disposables.CompositeDisposable disposables = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.util.Event<java.lang.Boolean>> _loadingStatus = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.util.Event<java.lang.Boolean>> _paymentResponse = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.model.MerchantTransaction> _merchantTransaction = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.model.CashTransaction> _cashTransaction = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.util.Event<com.woleapp.model.MerchantTransaction>> _proceedToPayment = null;
    
    public PaymentViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.woleapp.util.Event<com.woleapp.model.MerchantTransaction>> getProceedToPayment() {
        return null;
    }
    
    public final void setCashTransaction(@org.jetbrains.annotations.NotNull()
    com.woleapp.model.CashTransaction cashTransaction) {
    }
    
    public final void setMerchantTransaction(@org.jetbrains.annotations.NotNull()
    com.woleapp.model.MerchantTransaction merchantTransaction) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.woleapp.model.MerchantTransaction getMerchantTransaction() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.woleapp.util.Event<java.lang.Boolean>> getPaymentResponse() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.woleapp.util.Event<java.lang.Boolean>> getLoadingStatus() {
        return null;
    }
    
    public final void setStormAPIService(@org.jetbrains.annotations.NotNull()
    com.woleapp.network.StormAPIService stormAPIService) {
    }
    
    public final void setStormMerchantApiService(@org.jetbrains.annotations.NotNull()
    com.woleapp.network.MerchantsApiService merchantsApiService) {
    }
    
    public final void logCashTransaction() {
    }
    
    @kotlin.jvm.JvmOverloads()
    public final void logSalesOrder(@org.jetbrains.annotations.NotNull()
    com.woleapp.model.MerchantTransaction merchantTransaction) {
    }
    
    @kotlin.jvm.JvmOverloads()
    public final void logSalesOrder(@org.jetbrains.annotations.NotNull()
    com.woleapp.model.MerchantTransaction merchantTransaction, @org.jetbrains.annotations.NotNull()
    java.lang.String productCount) {
    }
    
    @kotlin.jvm.JvmOverloads()
    public final void logSalesOrder(@org.jetbrains.annotations.NotNull()
    com.woleapp.model.MerchantTransaction merchantTransaction, @org.jetbrains.annotations.NotNull()
    java.lang.String productCount, @org.jetbrains.annotations.NotNull()
    java.lang.String productId) {
    }
    
    public final void logNewTransaction() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
    
    public final void getSessionCode(int transaction_type) {
    }
}