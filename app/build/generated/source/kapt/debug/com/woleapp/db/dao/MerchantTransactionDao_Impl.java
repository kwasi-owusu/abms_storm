package com.woleapp.db.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.woleapp.model.MerchantTransaction;
import io.reactivex.Completable;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.Void;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MerchantTransactionDao_Impl implements MerchantTransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MerchantTransaction> __insertionAdapterOfMerchantTransaction;

  public MerchantTransactionDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMerchantTransaction = new EntityInsertionAdapter<MerchantTransaction>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `merchant_transaction` (`id`,`reference`,`merchantId`,`paymentMethod`,`customerName`,`status`,`amount`,`productId`,`productCount`,`sellerId`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MerchantTransaction value) {
        stmt.bindLong(1, value.getId());
        if (value.getReference() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getReference());
        }
        if (value.getMerchantId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMerchantId());
        }
        if (value.getPaymentMethod() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPaymentMethod());
        }
        if (value.getCustomerName() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCustomerName());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getStatus());
        }
        if (value.getAmount() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getAmount());
        }
        if (value.getProductId() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getProductId());
        }
        if (value.getProductCount() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getProductCount());
        }
        if (value.getSellerId() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getSellerId());
        }
      }
    };
  }

  @Override
  public Completable insertNewTransactions(final List<MerchantTransaction> transactions) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMerchantTransaction.insert(transactions);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public LiveData<List<MerchantTransaction>> getTransactions() {
    final String _sql = "SELECT * FROM merchant_transaction ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"merchant_transaction"}, false, new Callable<List<MerchantTransaction>>() {
      @Override
      public List<MerchantTransaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfReference = CursorUtil.getColumnIndexOrThrow(_cursor, "reference");
          final int _cursorIndexOfMerchantId = CursorUtil.getColumnIndexOrThrow(_cursor, "merchantId");
          final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
          final int _cursorIndexOfCustomerName = CursorUtil.getColumnIndexOrThrow(_cursor, "customerName");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductCount = CursorUtil.getColumnIndexOrThrow(_cursor, "productCount");
          final int _cursorIndexOfSellerId = CursorUtil.getColumnIndexOrThrow(_cursor, "sellerId");
          final List<MerchantTransaction> _result = new ArrayList<MerchantTransaction>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MerchantTransaction _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpReference;
            if (_cursor.isNull(_cursorIndexOfReference)) {
              _tmpReference = null;
            } else {
              _tmpReference = _cursor.getString(_cursorIndexOfReference);
            }
            final String _tmpMerchantId;
            if (_cursor.isNull(_cursorIndexOfMerchantId)) {
              _tmpMerchantId = null;
            } else {
              _tmpMerchantId = _cursor.getString(_cursorIndexOfMerchantId);
            }
            final String _tmpPaymentMethod;
            if (_cursor.isNull(_cursorIndexOfPaymentMethod)) {
              _tmpPaymentMethod = null;
            } else {
              _tmpPaymentMethod = _cursor.getString(_cursorIndexOfPaymentMethod);
            }
            final String _tmpCustomerName;
            if (_cursor.isNull(_cursorIndexOfCustomerName)) {
              _tmpCustomerName = null;
            } else {
              _tmpCustomerName = _cursor.getString(_cursorIndexOfCustomerName);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpAmount;
            if (_cursor.isNull(_cursorIndexOfAmount)) {
              _tmpAmount = null;
            } else {
              _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            }
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            final String _tmpProductCount;
            if (_cursor.isNull(_cursorIndexOfProductCount)) {
              _tmpProductCount = null;
            } else {
              _tmpProductCount = _cursor.getString(_cursorIndexOfProductCount);
            }
            final String _tmpSellerId;
            if (_cursor.isNull(_cursorIndexOfSellerId)) {
              _tmpSellerId = null;
            } else {
              _tmpSellerId = _cursor.getString(_cursorIndexOfSellerId);
            }
            _item = new MerchantTransaction(_tmpId,_tmpReference,_tmpMerchantId,_tmpPaymentMethod,_tmpCustomerName,_tmpStatus,_tmpAmount,_tmpProductId,_tmpProductCount,_tmpSellerId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
