package com.woleapp.network;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\'J\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\'\u00a8\u0006\b"}, d2 = {"Lcom/woleapp/network/HealthCheckerServices;", "", "activateAgent", "Lio/reactivex/Single;", "Lcom/woleapp/model/HealthCheckerResponse;", "request", "healthCheckerModel", "Lcom/woleapp/model/HealthCheckerModel;", "app_debug"})
public abstract interface HealthCheckerServices {
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.POST(value = "activate-agent")
    public abstract io.reactivex.Single<com.woleapp.model.HealthCheckerResponse> activateAgent();
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.POST(value = "request")
    public abstract io.reactivex.Single<com.woleapp.model.HealthCheckerResponse> request(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.woleapp.model.HealthCheckerModel healthCheckerModel);
}