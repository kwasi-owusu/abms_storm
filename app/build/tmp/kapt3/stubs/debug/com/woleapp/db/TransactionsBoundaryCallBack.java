package com.woleapp.db;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0016\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\tH\u0002J\u0016\u0010\u0014\u001a\u00020\n2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00020\u0016H\u0002J\u0006\u0010\u0017\u001a\u00020\nJ\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u0002H\u0016J\b\u0010\u001a\u001a\u00020\nH\u0016R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/woleapp/db/TransactionsBoundaryCallBack;", "Landroidx/paging/PagedList$BoundaryCallback;", "Lcom/woleapp/model/Transactions;", "stormAPIService", "Lcom/woleapp/network/StormAPIService;", "transactionsDao", "Lcom/woleapp/db/dao/TransactionsDao;", "loadStateListener", "Lkotlin/Function1;", "Lcom/woleapp/db/LoadState;", "", "Lcom/woleapp/db/LoadStateListener;", "(Lcom/woleapp/network/StormAPIService;Lcom/woleapp/db/dao/TransactionsDao;Lkotlin/jvm/functions/Function1;)V", "compositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "hasLoadedAllItems", "", "isLoading", "fetchTransactions", "loadingState", "insertTransactionsToDatabase", "transactions", "", "loadItemsAfterRefresh", "onItemAtEndLoaded", "itemAtEnd", "onZeroItemsLoaded", "app_debug"})
public final class TransactionsBoundaryCallBack extends androidx.paging.PagedList.BoundaryCallback<com.woleapp.model.Transactions> {
    private boolean isLoading = false;
    private boolean hasLoadedAllItems = false;
    private io.reactivex.disposables.CompositeDisposable compositeDisposable;
    private final com.woleapp.network.StormAPIService stormAPIService = null;
    private final com.woleapp.db.dao.TransactionsDao transactionsDao = null;
    private final kotlin.jvm.functions.Function1<com.woleapp.db.LoadState, kotlin.Unit> loadStateListener = null;
    
    @java.lang.Override()
    public void onZeroItemsLoaded() {
    }
    
    @java.lang.Override()
    public void onItemAtEndLoaded(@org.jetbrains.annotations.NotNull()
    com.woleapp.model.Transactions itemAtEnd) {
    }
    
    private final void fetchTransactions(com.woleapp.db.LoadState loadingState) {
    }
    
    private final void insertTransactionsToDatabase(java.util.List<? extends com.woleapp.model.Transactions> transactions) {
    }
    
    public final void loadItemsAfterRefresh() {
    }
    
    public TransactionsBoundaryCallBack(@org.jetbrains.annotations.NotNull()
    com.woleapp.network.StormAPIService stormAPIService, @org.jetbrains.annotations.NotNull()
    com.woleapp.db.dao.TransactionsDao transactionsDao, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.woleapp.db.LoadState, kotlin.Unit> loadStateListener) {
        super();
    }
}