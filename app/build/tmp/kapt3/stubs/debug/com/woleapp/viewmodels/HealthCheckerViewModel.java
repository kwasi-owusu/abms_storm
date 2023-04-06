package com.woleapp.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010(\u001a\u00020)R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001f\u0010\u0014\u001a\u0010\u0012\f\u0012\n \u0016*\u0004\u0018\u00010\u00150\u00150\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001f\u0010\u001d\u001a\u0010\u0012\f\u0012\n \u0016*\u0004\u0018\u00010\u001e0\u001e0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0018R\u001a\u0010 \u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0011\"\u0004\b\"\u0010\u0013R\u001d\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\n8F\u00a2\u0006\u0006\u001a\u0004\b$\u0010\fR\u001a\u0010%\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0011\"\u0004\b\'\u0010\u0013\u00a8\u0006*"}, d2 = {"Lcom/woleapp/viewmodels/HealthCheckerViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_dialogMessage", "Landroidx/lifecycle/MutableLiveData;", "Lcom/woleapp/util/Event;", "Lcom/woleapp/util/DialogHelper;", "_message", "", "dialogMessage", "Landroidx/lifecycle/LiveData;", "getDialogMessage", "()Landroidx/lifecycle/LiveData;", "disposables", "Lio/reactivex/disposables/CompositeDisposable;", "genderCheckId", "getGenderCheckId", "()Ljava/lang/String;", "setGenderCheckId", "(Ljava/lang/String;)V", "healthCheckerModel", "Lcom/woleapp/model/HealthCheckerModel;", "kotlin.jvm.PlatformType", "getHealthCheckerModel", "()Landroidx/lifecycle/MutableLiveData;", "healthCheckerServices", "Lcom/woleapp/network/HealthCheckerServices;", "getHealthCheckerServices", "()Lcom/woleapp/network/HealthCheckerServices;", "inProgress", "", "getInProgress", "initialOrFollowUpCheck", "getInitialOrFollowUpCheck", "setInitialOrFollowUpCheck", "message", "getMessage", "prefCommId", "getPrefCommId", "setPrefCommId", "payAndSubmit", "", "app_debug"})
public final class HealthCheckerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.woleapp.model.HealthCheckerModel> healthCheckerModel = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String initialOrFollowUpCheck = "Initial";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String genderCheckId = "male";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String prefCommId = "SMS";
    @org.jetbrains.annotations.NotNull()
    private final com.woleapp.network.HealthCheckerServices healthCheckerServices = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> inProgress = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.util.Event<java.lang.String>> _message = null;
    private final androidx.lifecycle.MutableLiveData<com.woleapp.util.Event<com.woleapp.util.DialogHelper>> _dialogMessage = null;
    private final io.reactivex.disposables.CompositeDisposable disposables = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.woleapp.model.HealthCheckerModel> getHealthCheckerModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getInitialOrFollowUpCheck() {
        return null;
    }
    
    public final void setInitialOrFollowUpCheck(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGenderCheckId() {
        return null;
    }
    
    public final void setGenderCheckId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPrefCommId() {
        return null;
    }
    
    public final void setPrefCommId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.woleapp.network.HealthCheckerServices getHealthCheckerServices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> getInProgress() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.woleapp.util.Event<com.woleapp.util.DialogHelper>> getDialogMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.woleapp.util.Event<java.lang.String>> getMessage() {
        return null;
    }
    
    public final void payAndSubmit() {
    }
    
    public HealthCheckerViewModel() {
        super();
    }
}