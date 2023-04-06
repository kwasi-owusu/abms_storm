package com.woleapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.woleapp.model.MerchantTransaction;
import com.woleapp.model.Transactions;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface MerchantTransactionDao {

    @Query("SELECT * FROM merchant_transaction ORDER BY id DESC")
    LiveData<List<MerchantTransaction>> getTransactions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertNewTransactions(List<MerchantTransaction> transactions);
}
