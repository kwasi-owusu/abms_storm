package com.woleapp.network;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/woleapp/network/MerchantsApiClient;", "", "()V", "BASE_URL", "", "INSTANCE", "Lcom/woleapp/network/MerchantsApiService;", "getMerchantApiService", "context", "Landroid/content/Context;", "app_debug"})
public final class MerchantsApiClient {
    @org.jetbrains.annotations.NotNull()
    public static final com.woleapp.network.MerchantsApiClient INSTANCE = null;
    private static final java.lang.String BASE_URL = "https://storm-merchants.netpluspay.com";
    @kotlin.jvm.Volatile()
    private static volatile com.woleapp.network.MerchantsApiService INSTANCE;
    
    private MerchantsApiClient() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @kotlin.jvm.JvmStatic()
    public static final com.woleapp.network.MerchantsApiService getMerchantApiService(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
}