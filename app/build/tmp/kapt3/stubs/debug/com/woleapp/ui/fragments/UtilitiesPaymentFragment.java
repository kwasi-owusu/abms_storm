package com.woleapp.ui.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0013\u001a\u00020\u0014J\u001a\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J\u001a\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J\u001a\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J\u001a\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J&\u0010\u001e\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\u0006\u0010!\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/woleapp/ui/fragments/UtilitiesPaymentFragment;", "Lcom/woleapp/ui/fragments/BaseFragment;", "()V", "airtimeOrDataBinding", "Lcom/woleapp/databinding/LayoutAirtimeOrDataBinding;", "alertDialog", "Landroid/app/AlertDialog;", "binding", "Lcom/woleapp/databinding/LayoutPowerOrElectricityBinding;", "cableBinding", "Lcom/woleapp/databinding/LayoutCableTvBinding;", "internetSubscriptionBinding", "Lcom/woleapp/databinding/LayoutInternetSubscriptionBinding;", "progressDialog", "Landroid/app/ProgressDialog;", "verifyBillDialog", "Lcom/google/android/material/bottomsheet/BottomSheetDialog;", "viewModel", "Lcom/woleapp/viewmodels/UtilitiesViewModel;", "dismissProgressBar", "", "getAirtimePageView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "getCableTvView", "getElectricityPageView", "getInternetPageView", "onCreateView", "savedInstanceState", "Landroid/os/Bundle;", "showProgressBar", "Companion", "app_debug"})
public final class UtilitiesPaymentFragment extends com.woleapp.ui.fragments.BaseFragment {
    private com.woleapp.databinding.LayoutPowerOrElectricityBinding binding;
    private com.woleapp.databinding.LayoutCableTvBinding cableBinding;
    private com.woleapp.databinding.LayoutInternetSubscriptionBinding internetSubscriptionBinding;
    private com.woleapp.databinding.LayoutAirtimeOrDataBinding airtimeOrDataBinding;
    private com.woleapp.viewmodels.UtilitiesViewModel viewModel;
    private android.app.ProgressDialog progressDialog;
    private android.app.AlertDialog alertDialog;
    private com.google.android.material.bottomsheet.BottomSheetDialog verifyBillDialog;
    @org.jetbrains.annotations.NotNull()
    public static final com.woleapp.ui.fragments.UtilitiesPaymentFragment.Companion Companion = null;
    
    public UtilitiesPaymentFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @kotlin.jvm.JvmStatic()
    public static final com.woleapp.ui.fragments.UtilitiesPaymentFragment newInstance(int serviceId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    public final void showProgressBar() {
    }
    
    public final void dismissProgressBar() {
    }
    
    private final android.view.View getElectricityPageView(android.view.LayoutInflater inflater, android.view.ViewGroup container) {
        return null;
    }
    
    private final android.view.View getAirtimePageView(android.view.LayoutInflater inflater, android.view.ViewGroup container) {
        return null;
    }
    
    private final android.view.View getInternetPageView(android.view.LayoutInflater inflater, android.view.ViewGroup container) {
        return null;
    }
    
    private final android.view.View getCableTvView(android.view.LayoutInflater inflater, android.view.ViewGroup container) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2 = {"Lcom/woleapp/ui/fragments/UtilitiesPaymentFragment$Companion;", "", "()V", "newInstance", "Lcom/woleapp/ui/fragments/UtilitiesPaymentFragment;", "serviceId", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @kotlin.jvm.JvmStatic()
        public final com.woleapp.ui.fragments.UtilitiesPaymentFragment newInstance(int serviceId) {
            return null;
        }
    }
}