package com.woleapp.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\rJ\u000e\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020\rJ\u0006\u0010\u001b\u001a\u00020-J\u000e\u00101\u001a\u00020-2\u0006\u00100\u001a\u00020\rJ\u0006\u0010+\u001a\u00020-J\b\u00102\u001a\u00020-H\u0014J\u000e\u00103\u001a\u00020-2\u0006\u00104\u001a\u000205J\u000e\u00106\u001a\u00020-2\u0006\u0010\u0019\u001a\u00020\rJ\u000e\u00107\u001a\u00020-2\u0006\u00108\u001a\u00020\u0012J\u000e\u00109\u001a\u00020-2\u0006\u0010:\u001a\u00020!J\u000e\u0010;\u001a\u00020-2\u0006\u0010<\u001a\u00020\u000fJ\b\u0010=\u001a\u00020\u0006H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00168F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00168F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0018R\u001d\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00168F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u0018R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00120\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u001d\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00168F\u00a2\u0006\u0006\u001a\u0004\b#\u0010\u0018R\u001d\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00050\u00168F\u00a2\u0006\u0006\u001a\u0004\b%\u0010\u0018R\u001d\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\b0\u00168F\u00a2\u0006\u0006\u001a\u0004\b\'\u0010\u0018R\u001d\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00168F\u00a2\u0006\u0006\u001a\u0004\b)\u0010\u0018R\u001d\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\b0\u00168F\u00a2\u0006\u0006\u001a\u0004\b+\u0010\u0018\u00a8\u0006>"}, d2 = {"Lcom/woleapp/viewmodels/MarketplaceViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_fatalError", "Landroidx/lifecycle/MutableLiveData;", "Lcom/woleapp/util/Event;", "", "_inventories", "", "Lcom/woleapp/model/Inventory;", "_isRefreshing", "_merchantStoreExist", "_message", "", "_salesOrderList", "Lcom/woleapp/model/SalesOrder;", "_showProgressDialog", "_stores", "Lcom/woleapp/model/Marketplace;", "compositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "fatalError", "Landroidx/lifecycle/LiveData;", "getFatalError", "()Landroidx/lifecycle/LiveData;", "imagePath", "inventories", "getInventories", "isRefreshing", "marketPlace", "getMarketPlace", "()Landroidx/lifecycle/MutableLiveData;", "merchantApiService", "Lcom/woleapp/network/MerchantsApiService;", "merchantStoreExists", "getMerchantStoreExists", "message", "getMessage", "saleOrder", "getSaleOrder", "showProgressDialog", "getShowProgressDialog", "stores", "getStores", "checkIfMerchantHasCreatedStore", "", "merchantId", "getBuyersOrders", "netplusId", "getSellerOrders", "onCleared", "saveImage", "context", "Landroid/content/Context;", "setImagePath", "setMarketPlace", "marketplace", "setMerchantApiService", "apiService", "updateSalesOrder", "it", "validateFields", "app_debug"})
public final class MarketplaceViewModel extends androidx.lifecycle.ViewModel {
    private final androidx.lifecycle.MutableLiveData<com.woleapp.util.Event<java.lang.String>> _message = null;
    private final io.reactivex.disposables.CompositeDisposable compositeDisposable = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.util.Event<java.lang.Boolean>> _showProgressDialog = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.util.Event<java.lang.Boolean>> _merchantStoreExist = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.woleapp.model.Marketplace>> _stores = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.woleapp.model.Inventory>> _inventories = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.util.Event<java.lang.Boolean>> _isRefreshing = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.util.Event<java.lang.Boolean>> _fatalError = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.woleapp.model.SalesOrder>> _salesOrderList = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.woleapp.model.Marketplace> marketPlace = null;
    private java.lang.String imagePath;
    private com.woleapp.network.MerchantsApiService merchantApiService;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.woleapp.model.SalesOrder>> getSaleOrder() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.woleapp.util.Event<java.lang.Boolean>> getFatalError() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.woleapp.util.Event<java.lang.Boolean>> isRefreshing() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.woleapp.model.Inventory>> getInventories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.woleapp.model.Marketplace>> getStores() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.woleapp.util.Event<java.lang.Boolean>> getMerchantStoreExists() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.woleapp.util.Event<java.lang.Boolean>> getShowProgressDialog() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.woleapp.util.Event<java.lang.String>> getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.woleapp.model.Marketplace> getMarketPlace() {
        return null;
    }
    
    public final void setMarketPlace(@org.jetbrains.annotations.NotNull()
    com.woleapp.model.Marketplace marketplace) {
    }
    
    public final void setMerchantApiService(@org.jetbrains.annotations.NotNull()
    com.woleapp.network.MerchantsApiService apiService) {
    }
    
    private final boolean validateFields() {
        return false;
    }
    
    public final void setImagePath(@org.jetbrains.annotations.NotNull()
    java.lang.String imagePath) {
    }
    
    public final void saveImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
    
    public final void checkIfMerchantHasCreatedStore(@org.jetbrains.annotations.NotNull()
    java.lang.String merchantId) {
    }
    
    public final void getStores() {
    }
    
    public final void getInventories() {
    }
    
    public final void getSellerOrders(@org.jetbrains.annotations.NotNull()
    java.lang.String netplusId) {
    }
    
    public final void getBuyersOrders(@org.jetbrains.annotations.NotNull()
    java.lang.String netplusId) {
    }
    
    public final void updateSalesOrder(@org.jetbrains.annotations.NotNull()
    com.woleapp.model.SalesOrder it) {
    }
    
    public MarketplaceViewModel() {
        super();
    }
}