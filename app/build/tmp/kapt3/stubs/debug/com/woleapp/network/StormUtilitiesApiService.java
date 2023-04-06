package com.woleapp.network;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u0006\t"}, d2 = {"Lcom/woleapp/network/StormUtilitiesApiService;", "", "payBills", "Lio/reactivex/Single;", "Lcom/woleapp/model/SuccessNetworkResponse;", "payload", "Lcom/woleapp/model/UtilitiesPayload;", "validateBill", "Lcom/woleapp/model/ValidateBillResponse;", "app_debug"})
public abstract interface StormUtilitiesApiService {
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.POST(value = "api/utility/pay_bill")
    public abstract io.reactivex.Single<com.woleapp.model.SuccessNetworkResponse> payBills(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.woleapp.model.UtilitiesPayload payload);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.POST(value = "api/utility/validate_bill")
    public abstract io.reactivex.Single<com.woleapp.model.ValidateBillResponse> validateBill(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.woleapp.model.UtilitiesPayload payload);
}