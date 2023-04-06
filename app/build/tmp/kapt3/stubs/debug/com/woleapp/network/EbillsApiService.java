package com.woleapp.network;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0003H\'J\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u000b\u001a\u00020\fH\'\u00a8\u0006\r"}, d2 = {"Lcom/woleapp/network/EbillsApiService;", "", "getBiller", "Lio/reactivex/Single;", "", "p", "", "getBillerList", "", "Lcom/woleapp/model/EBiller;", "validateForm", "biller", "Lcom/woleapp/model/EBillerResponse;", "app_debug"})
public abstract interface EbillsApiService {
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "getAllBillers")
    public abstract io.reactivex.Single<java.util.List<com.woleapp.model.EBiller>> getBillerList();
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "getForm/{billerId}")
    public abstract io.reactivex.Single<java.lang.String> getBiller(@retrofit2.http.Path(value = "billerId")
    int p);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.POST(value = "validateForm")
    public abstract io.reactivex.Single<java.lang.String> validateForm(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.woleapp.model.EBillerResponse biller);
}