package com.woleapp.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0018\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J\u000e\u00103\u001a\u00020\'2\u0006\u00104\u001a\u000205J\u001e\u00106\u001a\u0002072\u0006\u00108\u001a\u0002052\u0006\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020\u0007J\u0006\u0010<\u001a\u000207J\u0006\u0010=\u001a\u000207J\u0006\u0010>\u001a\u00020\u0007J\u0006\u0010?\u001a\u000207J\b\u0010@\u001a\u000207H\u0002J\u0006\u0010A\u001a\u000207J\"\u0010B\u001a\u0002072\u0006\u0010C\u001a\u0002052\u0006\u0010D\u001a\u0002052\b\u0010E\u001a\u0004\u0018\u00010FH\u0015J\b\u0010G\u001a\u000207H\u0016J\b\u0010H\u001a\u000207H\u0016J\u0012\u0010I\u001a\u0002072\b\u0010J\u001a\u0004\u0018\u00010KH\u0016J\u0012\u0010L\u001a\u0002072\b\u0010M\u001a\u0004\u0018\u00010NH\u0015J\u0010\u0010O\u001a\u00020\'2\u0006\u0010P\u001a\u00020QH\u0016J\b\u0010R\u001a\u000207H\u0014J\u0010\u0010S\u001a\u00020\'2\u0006\u0010T\u001a\u00020UH\u0016J\u0010\u0010V\u001a\u0002072\u0006\u0010W\u001a\u00020FH\u0014J\u0010\u0010X\u001a\u00020\'2\u0006\u0010T\u001a\u00020UH\u0016J\b\u0010Y\u001a\u000207H\u0014J\b\u0010Z\u001a\u000207H\u0014J\b\u0010[\u001a\u000207H\u0014J\u000e\u0010\\\u001a\u0002072\u0006\u00104\u001a\u000205J\b\u0010]\u001a\u000207H\u0002J\u000e\u0010^\u001a\u0002072\u0006\u0010_\u001a\u00020\u0007J\u0006\u0010`\u001a\u000207J\u000e\u0010a\u001a\u0002072\u0006\u0010b\u001a\u00020\u0007J\u000e\u0010c\u001a\u0002072\u0006\u0010d\u001a\u00020\u0007J\u0018\u0010e\u001a\u0002072\u0006\u0010f\u001a\u00020\'2\b\u0010g\u001a\u0004\u0018\u00010\u0007J\u0018\u0010h\u001a\u0002072\u0006\u0010i\u001a\u00020:2\u0006\u0010j\u001a\u00020\u0007H\u0002J\u0006\u0010k\u001a\u000207J\b\u0010l\u001a\u000207H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u001cR\u000e\u0010\u001f\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\"\u001a\u00020#\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\'X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u000e\u0010,\u001a\u00020\'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010-\u001a\u00020.X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102\u00a8\u0006m"}, d2 = {"Lcom/woleapp/ui/activity/HomeActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/google/android/material/navigation/NavigationView$OnNavigationItemSelectedListener;", "Landroidx/fragment/app/FragmentManager$OnBackStackChangedListener;", "Landroid/view/View$OnClickListener;", "()V", "availableBalance", "", "binding", "Lcom/woleapp/databinding/LayoutHomeBinding;", "getBinding", "()Lcom/woleapp/databinding/LayoutHomeBinding;", "setBinding", "(Lcom/woleapp/databinding/LayoutHomeBinding;)V", "compositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "getCompositeDisposable", "()Lio/reactivex/disposables/CompositeDisposable;", "setCompositeDisposable", "(Lio/reactivex/disposables/CompositeDisposable;)V", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "getAvailableBalance", "getGetAvailableBalance", "()Ljava/lang/String;", "getLedgerBalance", "getGetLedgerBalance", "ledgerBalance", "progressDialog", "Landroid/app/ProgressDialog;", "receiver", "Landroid/content/BroadcastReceiver;", "getReceiver", "()Landroid/content/BroadcastReceiver;", "refreshUserInfo", "", "getRefreshUserInfo", "()Z", "setRefreshUserInfo", "(Z)V", "refreshingWalletBalance", "utilities", "Lcom/woleapp/util/Utilities;", "getUtilities", "()Lcom/woleapp/util/Utilities;", "setUtilities", "(Lcom/woleapp/util/Utilities;)V", "abc", "a", "", "addFragmentWithoutRemove", "", "containerViewId", "fragment", "Landroidx/fragment/app/Fragment;", "fragmentName", "dismissProgressBar", "exitApp", "getFragmentNameOnTop", "getWalletBalance", "gotoLoginScreen", "initNavigationDrawer", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onBackPressed", "onBackStackChanged", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "onDestroy", "onNavigationItemSelected", "item", "Landroid/view/MenuItem;", "onNewIntent", "intent", "onOptionsItemSelected", "onResume", "onStart", "onStop", "pqrGroup", "setActivityOrientation", "setAvailableBalance", "amount", "setMerchantToolbar", "setTitleWithNoNavigation", "title", "setUserName", "name", "showD", "isNotification", "message", "showFragment", "targetFragment", "className", "showProgressBar", "toggleAppBarLayout", "app_debug"})
public final class HomeActivity extends androidx.appcompat.app.AppCompatActivity implements com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener, androidx.fragment.app.FragmentManager.OnBackStackChangedListener, android.view.View.OnClickListener {
    public com.woleapp.databinding.LayoutHomeBinding binding;
    public com.woleapp.util.Utilities utilities;
    @org.jetbrains.annotations.Nullable()
    private android.content.Context context;
    private boolean refreshUserInfo = false;
    private android.app.ProgressDialog progressDialog;
    @org.jetbrains.annotations.NotNull()
    private io.reactivex.disposables.CompositeDisposable compositeDisposable;
    private boolean refreshingWalletBalance = false;
    private java.lang.String availableBalance = "0";
    private java.lang.String ledgerBalance = "0";
    @org.jetbrains.annotations.NotNull()
    private final android.content.BroadcastReceiver receiver = null;
    
    public HomeActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.woleapp.databinding.LayoutHomeBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull()
    com.woleapp.databinding.LayoutHomeBinding p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.woleapp.util.Utilities getUtilities() {
        return null;
    }
    
    public final void setUtilities(@org.jetbrains.annotations.NotNull()
    com.woleapp.util.Utilities p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.content.Context getContext() {
        return null;
    }
    
    public final void setContext(@org.jetbrains.annotations.Nullable()
    android.content.Context p0) {
    }
    
    public final boolean getRefreshUserInfo() {
        return false;
    }
    
    public final void setRefreshUserInfo(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.disposables.CompositeDisposable getCompositeDisposable() {
        return null;
    }
    
    public final void setCompositeDisposable(@org.jetbrains.annotations.NotNull()
    io.reactivex.disposables.CompositeDisposable p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGetAvailableBalance() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGetLedgerBalance() {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"RestrictedApi"})
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void showFragment(androidx.fragment.app.Fragment targetFragment, java.lang.String className) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final void toggleAppBarLayout() {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    @java.lang.Override()
    protected void onStop() {
    }
    
    public final void getWalletBalance() {
    }
    
    public final void setAvailableBalance(@org.jetbrains.annotations.NotNull()
    java.lang.String amount) {
    }
    
    private final void setActivityOrientation() {
    }
    
    public final void initNavigationDrawer() {
    }
    
    public final void setMerchantToolbar() {
    }
    
    public final void setUserName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void setTitleWithNoNavigation(@org.jetbrains.annotations.NotNull()
    java.lang.String title) {
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    public final void exitApp() {
    }
    
    @java.lang.Override()
    protected void onNewIntent(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    public final void showD(boolean isNotification, @org.jetbrains.annotations.Nullable()
    java.lang.String message) {
    }
    
    @java.lang.Override()
    @java.lang.Deprecated()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    public final void showProgressBar() {
    }
    
    public final void dismissProgressBar() {
    }
    
    @java.lang.Override()
    public void onBackStackChanged() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFragmentNameOnTop() {
        return null;
    }
    
    @java.lang.Override()
    public boolean onCreateOptionsMenu(@org.jetbrains.annotations.NotNull()
    android.view.Menu menu) {
        return false;
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    @java.lang.Override()
    public boolean onNavigationItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    public final boolean abc(int a) {
        return false;
    }
    
    public final void pqrGroup(int a) {
    }
    
    private final void gotoLoginScreen() {
    }
    
    @java.lang.Override()
    public void onClick(@org.jetbrains.annotations.Nullable()
    android.view.View v) {
    }
    
    public final void addFragmentWithoutRemove(int containerViewId, @org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment fragment, @org.jetbrains.annotations.NotNull()
    java.lang.String fragmentName) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.BroadcastReceiver getReceiver() {
        return null;
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
}