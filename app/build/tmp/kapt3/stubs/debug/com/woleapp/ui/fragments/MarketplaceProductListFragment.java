package com.woleapp.ui.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 #2\u00020\u0001:\u0001#B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J$\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u001a\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u00192\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010\"\u001a\u00020\u0017H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/woleapp/ui/fragments/MarketplaceProductListFragment;", "Lcom/woleapp/ui/fragments/BaseFragment;", "()V", "binding", "Lcom/woleapp/databinding/LayoutMarketplaceProductListBinding;", "inventoryList", "", "Lcom/woleapp/model/Inventory;", "mProgressDialog", "Landroid/app/ProgressDialog;", "getMProgressDialog", "()Landroid/app/ProgressDialog;", "setMProgressDialog", "(Landroid/app/ProgressDialog;)V", "marketplace", "Lcom/woleapp/model/Marketplace;", "marketplaceAdapter", "Lcom/woleapp/adapters/MarketplaceProductAdapter;", "storeBinding", "Lcom/woleapp/databinding/LayoutMarketplaceStoreBinding;", "viewModel", "Lcom/woleapp/viewmodels/MarketplaceViewModel;", "hideProgressDialog", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "showProgressDialog", "Companion", "app_debug"})
public final class MarketplaceProductListFragment extends com.woleapp.ui.fragments.BaseFragment {
    private com.woleapp.viewmodels.MarketplaceViewModel viewModel;
    private java.util.List<? extends com.woleapp.model.Inventory> inventoryList;
    private com.woleapp.databinding.LayoutMarketplaceProductListBinding binding;
    private com.woleapp.databinding.LayoutMarketplaceStoreBinding storeBinding;
    private com.woleapp.model.Marketplace marketplace;
    private com.woleapp.adapters.MarketplaceProductAdapter marketplaceAdapter;
    @org.jetbrains.annotations.NotNull()
    public android.app.ProgressDialog mProgressDialog;
    public static final com.woleapp.ui.fragments.MarketplaceProductListFragment.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.ProgressDialog getMProgressDialog() {
        return null;
    }
    
    public final void setMProgressDialog(@org.jetbrains.annotations.NotNull()
    android.app.ProgressDialog p0) {
    }
    
    private final void showProgressDialog() {
    }
    
    private final void hideProgressDialog() {
    }
    
    public MarketplaceProductListFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.woleapp.ui.fragments.MarketplaceProductListFragment newInstance(@org.jetbrains.annotations.Nullable()
    com.woleapp.model.Marketplace marketplace) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.woleapp.ui.fragments.MarketplaceProductListFragment newInstance() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007\u00a8\u0006\u0007"}, d2 = {"Lcom/woleapp/ui/fragments/MarketplaceProductListFragment$Companion;", "", "()V", "newInstance", "Lcom/woleapp/ui/fragments/MarketplaceProductListFragment;", "marketplace", "Lcom/woleapp/model/Marketplace;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.woleapp.ui.fragments.MarketplaceProductListFragment newInstance(@org.jetbrains.annotations.Nullable()
        com.woleapp.model.Marketplace marketplace) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.woleapp.ui.fragments.MarketplaceProductListFragment newInstance() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}