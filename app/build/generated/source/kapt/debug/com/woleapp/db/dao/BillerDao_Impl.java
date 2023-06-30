package com.woleapp.db.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.woleapp.model.Biller;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BillerDao_Impl implements BillerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Biller> __insertionAdapterOfBiller;

  public BillerDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBiller = new EntityInsertionAdapter<Biller>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `billers` (`id`,`service_type`,`biller_name`,`biller_code`,`operation`,`status`,`verification_status`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Biller value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getService_type() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getService_type());
        }
        if (value.getBiller_name() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBiller_name());
        }
        if (value.getBiller_code() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getBiller_code());
        }
        if (value.getOperation() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getOperation());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getStatus());
        }
        if (value.getVerification_status() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getVerification_status());
        }
      }
    };
  }

  @Override
  public void insertAll(final List<Biller> billers) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfBiller.insert(billers);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Biller>> getAllBillers() {
    final String _sql = "SELECT * FROM billers";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"billers"}, false, new Callable<List<Biller>>() {
      @Override
      public List<Biller> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfServiceType = CursorUtil.getColumnIndexOrThrow(_cursor, "service_type");
          final int _cursorIndexOfBillerName = CursorUtil.getColumnIndexOrThrow(_cursor, "biller_name");
          final int _cursorIndexOfBillerCode = CursorUtil.getColumnIndexOrThrow(_cursor, "biller_code");
          final int _cursorIndexOfOperation = CursorUtil.getColumnIndexOrThrow(_cursor, "operation");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfVerificationStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "verification_status");
          final List<Biller> _result = new ArrayList<Biller>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Biller _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpService_type;
            if (_cursor.isNull(_cursorIndexOfServiceType)) {
              _tmpService_type = null;
            } else {
              _tmpService_type = _cursor.getString(_cursorIndexOfServiceType);
            }
            final String _tmpBiller_name;
            if (_cursor.isNull(_cursorIndexOfBillerName)) {
              _tmpBiller_name = null;
            } else {
              _tmpBiller_name = _cursor.getString(_cursorIndexOfBillerName);
            }
            final String _tmpBiller_code;
            if (_cursor.isNull(_cursorIndexOfBillerCode)) {
              _tmpBiller_code = null;
            } else {
              _tmpBiller_code = _cursor.getString(_cursorIndexOfBillerCode);
            }
            final String _tmpOperation;
            if (_cursor.isNull(_cursorIndexOfOperation)) {
              _tmpOperation = null;
            } else {
              _tmpOperation = _cursor.getString(_cursorIndexOfOperation);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpVerification_status;
            if (_cursor.isNull(_cursorIndexOfVerificationStatus)) {
              _tmpVerification_status = null;
            } else {
              _tmpVerification_status = _cursor.getString(_cursorIndexOfVerificationStatus);
            }
            _item = new Biller(_tmpId,_tmpService_type,_tmpBiller_name,_tmpBiller_code,_tmpOperation,_tmpStatus,_tmpVerification_status);
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

  @Override
  public LiveData<List<Biller>> getBillersForService(final String service_type) {
    final String _sql = "select * from billers where service_type = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (service_type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, service_type);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"billers"}, false, new Callable<List<Biller>>() {
      @Override
      public List<Biller> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfServiceType = CursorUtil.getColumnIndexOrThrow(_cursor, "service_type");
          final int _cursorIndexOfBillerName = CursorUtil.getColumnIndexOrThrow(_cursor, "biller_name");
          final int _cursorIndexOfBillerCode = CursorUtil.getColumnIndexOrThrow(_cursor, "biller_code");
          final int _cursorIndexOfOperation = CursorUtil.getColumnIndexOrThrow(_cursor, "operation");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfVerificationStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "verification_status");
          final List<Biller> _result = new ArrayList<Biller>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Biller _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpService_type;
            if (_cursor.isNull(_cursorIndexOfServiceType)) {
              _tmpService_type = null;
            } else {
              _tmpService_type = _cursor.getString(_cursorIndexOfServiceType);
            }
            final String _tmpBiller_name;
            if (_cursor.isNull(_cursorIndexOfBillerName)) {
              _tmpBiller_name = null;
            } else {
              _tmpBiller_name = _cursor.getString(_cursorIndexOfBillerName);
            }
            final String _tmpBiller_code;
            if (_cursor.isNull(_cursorIndexOfBillerCode)) {
              _tmpBiller_code = null;
            } else {
              _tmpBiller_code = _cursor.getString(_cursorIndexOfBillerCode);
            }
            final String _tmpOperation;
            if (_cursor.isNull(_cursorIndexOfOperation)) {
              _tmpOperation = null;
            } else {
              _tmpOperation = _cursor.getString(_cursorIndexOfOperation);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpVerification_status;
            if (_cursor.isNull(_cursorIndexOfVerificationStatus)) {
              _tmpVerification_status = null;
            } else {
              _tmpVerification_status = _cursor.getString(_cursorIndexOfVerificationStatus);
            }
            _item = new Biller(_tmpId,_tmpService_type,_tmpBiller_name,_tmpBiller_code,_tmpOperation,_tmpStatus,_tmpVerification_status);
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

  @Override
  public LiveData<List<Biller>> getBillersForService(final String service_type,
      final String status) {
    final String _sql = "select * from billers where service_type = ? AND status =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (service_type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, service_type);
    }
    _argIndex = 2;
    if (status == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"billers"}, false, new Callable<List<Biller>>() {
      @Override
      public List<Biller> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfServiceType = CursorUtil.getColumnIndexOrThrow(_cursor, "service_type");
          final int _cursorIndexOfBillerName = CursorUtil.getColumnIndexOrThrow(_cursor, "biller_name");
          final int _cursorIndexOfBillerCode = CursorUtil.getColumnIndexOrThrow(_cursor, "biller_code");
          final int _cursorIndexOfOperation = CursorUtil.getColumnIndexOrThrow(_cursor, "operation");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfVerificationStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "verification_status");
          final List<Biller> _result = new ArrayList<Biller>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Biller _item;
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            final String _tmpService_type;
            if (_cursor.isNull(_cursorIndexOfServiceType)) {
              _tmpService_type = null;
            } else {
              _tmpService_type = _cursor.getString(_cursorIndexOfServiceType);
            }
            final String _tmpBiller_name;
            if (_cursor.isNull(_cursorIndexOfBillerName)) {
              _tmpBiller_name = null;
            } else {
              _tmpBiller_name = _cursor.getString(_cursorIndexOfBillerName);
            }
            final String _tmpBiller_code;
            if (_cursor.isNull(_cursorIndexOfBillerCode)) {
              _tmpBiller_code = null;
            } else {
              _tmpBiller_code = _cursor.getString(_cursorIndexOfBillerCode);
            }
            final String _tmpOperation;
            if (_cursor.isNull(_cursorIndexOfOperation)) {
              _tmpOperation = null;
            } else {
              _tmpOperation = _cursor.getString(_cursorIndexOfOperation);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpVerification_status;
            if (_cursor.isNull(_cursorIndexOfVerificationStatus)) {
              _tmpVerification_status = null;
            } else {
              _tmpVerification_status = _cursor.getString(_cursorIndexOfVerificationStatus);
            }
            _item = new Biller(_tmpId,_tmpService_type,_tmpBiller_name,_tmpBiller_code,_tmpOperation,_tmpStatus,_tmpVerification_status);
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
