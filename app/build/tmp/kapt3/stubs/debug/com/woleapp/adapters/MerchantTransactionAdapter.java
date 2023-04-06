package com.woleapp.adapters;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000fH\u0016J-\u0010\u0014\u001a\u00020\n2%\u0010\u0015\u001a!\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0006j\u0002`\u000bR-\u0010\u0005\u001a!\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0006j\u0002`\u000bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/woleapp/adapters/MerchantTransactionAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/woleapp/model/MerchantTransaction;", "Lcom/woleapp/adapters/MerchantTransactionViewHolder;", "()V", "transactionListener", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "transaction", "", "Lcom/woleapp/adapters/MerchantTransactionListener;", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setTransactionListener", "listener", "app_debug"})
public final class MerchantTransactionAdapter extends androidx.recyclerview.widget.ListAdapter<com.woleapp.model.MerchantTransaction, com.woleapp.adapters.MerchantTransactionViewHolder> {
    private kotlin.jvm.functions.Function1<? super com.woleapp.model.MerchantTransaction, kotlin.Unit> transactionListener;
    
    public final void setTransactionListener(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.woleapp.model.MerchantTransaction, kotlin.Unit> listener) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.woleapp.adapters.MerchantTransactionViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.woleapp.adapters.MerchantTransactionViewHolder holder, int position) {
    }
    
    public MerchantTransactionAdapter() {
        super(null);
    }
}