package com.woleapp.adapters;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B<\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012%\u0010\u0006\u001a!\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0\u0007j\u0002`\r\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u0013\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R0\u0010\u0006\u001a!\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0\u0007j\u0002`\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/woleapp/adapters/MarketplaceOrderViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/woleapp/databinding/LayoutMarketplaceReceivedOrdersBinding;", "mode", "", "listener", "Lkotlin/Function1;", "Lcom/woleapp/model/SalesOrder;", "Lkotlin/ParameterName;", "name", "order", "", "Lcom/woleapp/adapters/MarketplaceOrderListener;", "(Lcom/woleapp/databinding/LayoutMarketplaceReceivedOrdersBinding;ILkotlin/jvm/functions/Function1;)V", "getBinding", "()Lcom/woleapp/databinding/LayoutMarketplaceReceivedOrdersBinding;", "getListener", "()Lkotlin/jvm/functions/Function1;", "bind", "Companion", "app_debug"})
public final class MarketplaceOrderViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    @org.jetbrains.annotations.NotNull()
    private final com.woleapp.databinding.LayoutMarketplaceReceivedOrdersBinding binding = null;
    private final int mode = 0;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.woleapp.model.SalesOrder, kotlin.Unit> listener = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.woleapp.adapters.MarketplaceOrderViewHolder.Companion Companion = null;
    
    public MarketplaceOrderViewHolder(@org.jetbrains.annotations.NotNull()
    com.woleapp.databinding.LayoutMarketplaceReceivedOrdersBinding binding, int mode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.woleapp.model.SalesOrder, kotlin.Unit> listener) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.woleapp.databinding.LayoutMarketplaceReceivedOrdersBinding getBinding() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.jvm.functions.Function1<com.woleapp.model.SalesOrder, kotlin.Unit> getListener() {
        return null;
    }
    
    public final void bind(@org.jetbrains.annotations.NotNull()
    com.woleapp.model.SalesOrder order) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J=\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2%\u0010\t\u001a!\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f0\nj\u0002`\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/woleapp/adapters/MarketplaceOrderViewHolder$Companion;", "", "()V", "from", "Lcom/woleapp/adapters/MarketplaceOrderViewHolder;", "parent", "Landroid/view/ViewGroup;", "mode", "", "listener", "Lkotlin/Function1;", "Lcom/woleapp/model/SalesOrder;", "Lkotlin/ParameterName;", "name", "order", "", "Lcom/woleapp/adapters/MarketplaceOrderListener;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.woleapp.adapters.MarketplaceOrderViewHolder from(@org.jetbrains.annotations.NotNull()
        android.view.ViewGroup parent, int mode, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.woleapp.model.SalesOrder, kotlin.Unit> listener) {
            return null;
        }
    }
}