package com.woleapp.ui.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0006\u0018\u0000 $2\u00020\u0001:\u0003$%&B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u000fH\u0003J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0012H\u0002J$\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u000fH\u0016J \u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020!H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/woleapp/ui/fragments/EBillerWebView;", "Lcom/woleapp/ui/fragments/BaseFragment;", "()V", "binding", "Lcom/woleapp/databinding/FragmentEbillsWebviewBinding;", "disposable", "Lio/reactivex/disposables/CompositeDisposable;", "hasDataLoaded", "", "hasLoadedSecond", "progressDialog", "Landroid/app/ProgressDialog;", "utilities", "Lcom/woleapp/util/Utilities;", "getBiller", "", "getWalletBalance", "billerResponse", "Lcom/woleapp/model/EBillerResponse;", "initWebView", "loadSecond", "b", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDetach", "setNewBalance", "availableBalance", "", "ledgerBalance", "amountPaid", "Companion", "MyWebChromeClient", "WebAppInterface", "app_debug"})
public final class EBillerWebView extends com.woleapp.ui.fragments.BaseFragment {
    private boolean hasLoadedSecond = false;
    private final io.reactivex.disposables.CompositeDisposable disposable = null;
    private android.app.ProgressDialog progressDialog;
    private boolean hasDataLoaded = false;
    private com.woleapp.util.Utilities utilities;
    @org.jetbrains.annotations.NotNull()
    public static final com.woleapp.ui.fragments.EBillerWebView.Companion Companion = null;
    private com.woleapp.databinding.FragmentEbillsWebviewBinding binding;
    
    public EBillerWebView() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    private final void getBiller() {
    }
    
    @android.annotation.SuppressLint(value = {"SetJavaScriptEnabled"})
    private final void initWebView() {
    }
    
    private final void getWalletBalance(com.woleapp.model.EBillerResponse billerResponse) {
    }
    
    private final void setNewBalance(double availableBalance, double ledgerBalance, double amountPaid) {
    }
    
    private final void loadSecond(com.woleapp.model.EBillerResponse b) {
    }
    
    @java.lang.Override()
    public void onDetach() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016\u00a8\u0006\f"}, d2 = {"Lcom/woleapp/ui/fragments/EBillerWebView$MyWebChromeClient;", "Landroid/webkit/WebChromeClient;", "()V", "onJsAlert", "", "view", "Landroid/webkit/WebView;", "url", "", "message", "result", "Landroid/webkit/JsResult;", "app_debug"})
    static final class MyWebChromeClient extends android.webkit.WebChromeClient {
        
        public MyWebChromeClient() {
            super();
        }
        
        @java.lang.Override()
        public boolean onJsAlert(@org.jetbrains.annotations.NotNull()
        android.webkit.WebView view, @org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        java.lang.String message, @org.jetbrains.annotations.NotNull()
        android.webkit.JsResult result) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B0\b\u0000\u0012\'\u0010\u0002\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0004\u00a2\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0003j\u0002`\t\u00a2\u0006\u0002\u0010\nJ\u0012\u0010\u0012\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0007R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR;\u0010\u0002\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0004\u00a2\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0003j\u0002`\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\n\u00a8\u0006\u0013"}, d2 = {"Lcom/woleapp/ui/fragments/EBillerWebView$WebAppInterface;", "", "l", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "data", "", "Lcom/woleapp/ui/fragments/WebInterfaceListener;", "(Lkotlin/jvm/functions/Function1;)V", "getData", "()Ljava/lang/String;", "setData", "(Ljava/lang/String;)V", "getL", "()Lkotlin/jvm/functions/Function1;", "setL", "sendData", "app_debug"})
    public static final class WebAppInterface {
        @org.jetbrains.annotations.NotNull()
        private kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> l;
        @org.jetbrains.annotations.Nullable()
        private java.lang.String data;
        
        public WebAppInterface(@org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> l) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final kotlin.jvm.functions.Function1<java.lang.String, kotlin.Unit> getL() {
            return null;
        }
        
        public final void setL(@org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> p0) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getData() {
            return null;
        }
        
        public final void setData(@org.jetbrains.annotations.Nullable()
        java.lang.String p0) {
        }
        
        @android.webkit.JavascriptInterface()
        public final void sendData(@org.jetbrains.annotations.Nullable()
        java.lang.String data) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/woleapp/ui/fragments/EBillerWebView$Companion;", "", "()V", "newInstance", "Lcom/woleapp/ui/fragments/EBillerWebView;", "id", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.woleapp.ui.fragments.EBillerWebView newInstance(@org.jetbrains.annotations.NotNull()
        java.lang.String id) {
            return null;
        }
    }
}