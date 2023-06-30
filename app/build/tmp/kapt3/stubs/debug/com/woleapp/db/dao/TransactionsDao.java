package com.woleapp.db.dao;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\t\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\'J\u0014\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006H\'J\"\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u00032\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\'\u00a8\u0006\f"}, d2 = {"Lcom/woleapp/db/dao/TransactionsDao;", "", "deleteOldTransactions", "Lio/reactivex/Single;", "", "getTransactions", "Landroidx/paging/DataSource$Factory;", "Lcom/woleapp/model/Transactions;", "insertTransactions", "", "", "transactions", "app_debug"})
public abstract interface TransactionsDao {
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM transactions")
    public abstract androidx.paging.DataSource.Factory<java.lang.Integer, com.woleapp.model.Transactions> getTransactions();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Insert(onConflict = 1)
    public abstract io.reactivex.Single<java.util.List<java.lang.Long>> insertTransactions(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.woleapp.model.Transactions> transactions);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "DELETE FROM transactions")
    public abstract io.reactivex.Single<java.lang.Integer> deleteOldTransactions();
}