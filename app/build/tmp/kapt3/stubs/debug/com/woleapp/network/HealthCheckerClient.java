package com.woleapp.network;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\u0007H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/woleapp/network/HealthCheckerClient;", "", "()V", "BASE_URL", "", "BASE_URL_TEMP", "INSTANCE", "Lcom/woleapp/network/HealthCheckerServices;", "getHealthCheckerInstance", "app_debug"})
public final class HealthCheckerClient {
    @org.jetbrains.annotations.NotNull()
    public static final com.woleapp.network.HealthCheckerClient INSTANCE = null;
    private static final java.lang.String BASE_URL = "http://healthcheck.test.netpluspay.com/api/";
    private static final java.lang.String BASE_URL_TEMP = "https://f0f80747f8d1.ngrok.io/api/";
    @kotlin.jvm.Volatile()
    private static volatile com.woleapp.network.HealthCheckerServices INSTANCE;
    
    private HealthCheckerClient() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @kotlin.jvm.JvmStatic()
    public static final com.woleapp.network.HealthCheckerServices getHealthCheckerInstance() {
        return null;
    }
}