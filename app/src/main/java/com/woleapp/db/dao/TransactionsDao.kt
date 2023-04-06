package com.woleapp.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.woleapp.model.Transactions
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TransactionsDao {
    @Query("SELECT * FROM transactions")
    fun getTransactions(): DataSource.Factory<Int, Transactions>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactions(transactions: List<Transactions>): Single<List<Long>>

    @Query("DELETE FROM transactions")
    fun deleteOldTransactions(): Single<Int>
}