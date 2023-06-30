package com.woleapp.db.dao;

import android.database.Cursor;
import androidx.paging.DataSource;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetDataSource;
import androidx.room.util.CursorUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.woleapp.model.Transactions;
import io.reactivex.Single;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TransactionsDao_Impl implements TransactionsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Transactions> __insertionAdapterOfTransactions;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldTransactions;

  public TransactionsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransactions = new EntityInsertionAdapter<Transactions>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `transactions` (`tId`,`id`,`external_reference`,`our_reference`,`transaction_type`,`transaction_date`,`description`,`beneficiary_name`,`amount`,`status`,`destination_account`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Transactions value) {
        stmt.bindLong(1, value.getTId());
        if (value.getId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getId());
        }
        if (value.getTransaction_id() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTransaction_id());
        }
        if (value.getReference_no_Etranzact() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getReference_no_Etranzact());
        }
        if (value.getTransaction_type() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTransaction_type());
        }
        if (value.getTransaction_date() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getTransaction_date());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDescription());
        }
        if (value.getBeneficiary_name() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getBeneficiary_name());
        }
        if (value.getAmount() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindDouble(9, value.getAmount());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getStatus());
        }
        if (value.getDestination_account() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getDestination_account());
        }
      }
    };
    this.__preparedStmtOfDeleteOldTransactions = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM transactions";
        return _query;
      }
    };
  }

  @Override
  public Single<List<Long>> insertTransactions(final List<? extends Transactions> transactions) {
    return Single.fromCallable(new Callable<List<Long>>() {
      @Override
      public List<Long> call() throws Exception {
        __db.beginTransaction();
        try {
          List<Long> _result = __insertionAdapterOfTransactions.insertAndReturnIdsList(transactions);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public Single<Integer> deleteOldTransactions() {
    return Single.fromCallable(new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldTransactions.acquire();
        __db.beginTransaction();
        try {
          final java.lang.Integer _result = _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteOldTransactions.release(_stmt);
        }
      }
    });
  }

  @Override
  public DataSource.Factory<Integer, Transactions> getTransactions() {
    final String _sql = "SELECT * FROM transactions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new DataSource.Factory<Integer, Transactions>() {
      @Override
      public LimitOffsetDataSource<Transactions> create() {
        return new LimitOffsetDataSource<Transactions>(__db, _statement, false, true , "transactions") {
          @Override
          protected List<Transactions> convertRows(Cursor cursor) {
            final int _cursorIndexOfTId = CursorUtil.getColumnIndexOrThrow(cursor, "tId");
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
            final int _cursorIndexOfTransactionId = CursorUtil.getColumnIndexOrThrow(cursor, "external_reference");
            final int _cursorIndexOfReferenceNoEtranzact = CursorUtil.getColumnIndexOrThrow(cursor, "our_reference");
            final int _cursorIndexOfTransactionType = CursorUtil.getColumnIndexOrThrow(cursor, "transaction_type");
            final int _cursorIndexOfTransactionDate = CursorUtil.getColumnIndexOrThrow(cursor, "transaction_date");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(cursor, "description");
            final int _cursorIndexOfBeneficiaryName = CursorUtil.getColumnIndexOrThrow(cursor, "beneficiary_name");
            final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(cursor, "amount");
            final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(cursor, "status");
            final int _cursorIndexOfDestinationAccount = CursorUtil.getColumnIndexOrThrow(cursor, "destination_account");
            final List<Transactions> _res = new ArrayList<Transactions>(cursor.getCount());
            while(cursor.moveToNext()) {
              final Transactions _item;
              _item = new Transactions();
              final int _tmpTId;
              _tmpTId = cursor.getInt(_cursorIndexOfTId);
              _item.setTId(_tmpTId);
              final String _tmpId;
              if (cursor.isNull(_cursorIndexOfId)) {
                _tmpId = null;
              } else {
                _tmpId = cursor.getString(_cursorIndexOfId);
              }
              _item.setId(_tmpId);
              final String _tmpTransaction_id;
              if (cursor.isNull(_cursorIndexOfTransactionId)) {
                _tmpTransaction_id = null;
              } else {
                _tmpTransaction_id = cursor.getString(_cursorIndexOfTransactionId);
              }
              _item.setTransaction_id(_tmpTransaction_id);
              final String _tmpReference_no_Etranzact;
              if (cursor.isNull(_cursorIndexOfReferenceNoEtranzact)) {
                _tmpReference_no_Etranzact = null;
              } else {
                _tmpReference_no_Etranzact = cursor.getString(_cursorIndexOfReferenceNoEtranzact);
              }
              _item.setReference_no_Etranzact(_tmpReference_no_Etranzact);
              final String _tmpTransaction_type;
              if (cursor.isNull(_cursorIndexOfTransactionType)) {
                _tmpTransaction_type = null;
              } else {
                _tmpTransaction_type = cursor.getString(_cursorIndexOfTransactionType);
              }
              _item.setTransaction_type(_tmpTransaction_type);
              final String _tmpTransaction_date;
              if (cursor.isNull(_cursorIndexOfTransactionDate)) {
                _tmpTransaction_date = null;
              } else {
                _tmpTransaction_date = cursor.getString(_cursorIndexOfTransactionDate);
              }
              _item.setTransaction_date(_tmpTransaction_date);
              final String _tmpDescription;
              if (cursor.isNull(_cursorIndexOfDescription)) {
                _tmpDescription = null;
              } else {
                _tmpDescription = cursor.getString(_cursorIndexOfDescription);
              }
              _item.setDescription(_tmpDescription);
              final String _tmpBeneficiary_name;
              if (cursor.isNull(_cursorIndexOfBeneficiaryName)) {
                _tmpBeneficiary_name = null;
              } else {
                _tmpBeneficiary_name = cursor.getString(_cursorIndexOfBeneficiaryName);
              }
              _item.setBeneficiary_name(_tmpBeneficiary_name);
              final Double _tmpAmount;
              if (cursor.isNull(_cursorIndexOfAmount)) {
                _tmpAmount = null;
              } else {
                _tmpAmount = cursor.getDouble(_cursorIndexOfAmount);
              }
              _item.setAmount(_tmpAmount);
              final String _tmpStatus;
              if (cursor.isNull(_cursorIndexOfStatus)) {
                _tmpStatus = null;
              } else {
                _tmpStatus = cursor.getString(_cursorIndexOfStatus);
              }
              _item.setStatus(_tmpStatus);
              final String _tmpDestination_account;
              if (cursor.isNull(_cursorIndexOfDestinationAccount)) {
                _tmpDestination_account = null;
              } else {
                _tmpDestination_account = cursor.getString(_cursorIndexOfDestinationAccount);
              }
              _item.setDestination_account(_tmpDestination_account);
              _res.add(_item);
            }
            return _res;
          }
        };
      }
    };
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
