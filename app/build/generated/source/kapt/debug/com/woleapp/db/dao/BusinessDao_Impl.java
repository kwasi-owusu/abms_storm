package com.woleapp.db.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.woleapp.model.Business;
import java.lang.Class;
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
public final class BusinessDao_Impl implements BusinessDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Business> __insertionAdapterOfBusiness;

  private final EntityDeletionOrUpdateAdapter<Business> __deletionAdapterOfBusiness;

  private final EntityDeletionOrUpdateAdapter<Business> __updateAdapterOfBusiness;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUser;

  public BusinessDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBusiness = new EntityInsertionAdapter<Business>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `business_info` (`user_id`,`business_name`,`business_address`,`business_state`,`password`,`phone`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Business value) {
        if (value.getUser_id() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getUser_id());
        }
        if (value.getBusiness_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getBusiness_name());
        }
        if (value.getBusiness_address() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBusiness_address());
        }
        if (value.getBusiness_state() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getBusiness_state());
        }
        if (value.getPassword() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPassword());
        }
        if (value.getPhone() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPhone());
        }
      }
    };
    this.__deletionAdapterOfBusiness = new EntityDeletionOrUpdateAdapter<Business>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `business_info` WHERE `user_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Business value) {
        if (value.getUser_id() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getUser_id());
        }
      }
    };
    this.__updateAdapterOfBusiness = new EntityDeletionOrUpdateAdapter<Business>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `business_info` SET `user_id` = ?,`business_name` = ?,`business_address` = ?,`business_state` = ?,`password` = ?,`phone` = ? WHERE `user_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Business value) {
        if (value.getUser_id() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getUser_id());
        }
        if (value.getBusiness_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getBusiness_name());
        }
        if (value.getBusiness_address() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBusiness_address());
        }
        if (value.getBusiness_state() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getBusiness_state());
        }
        if (value.getPassword() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPassword());
        }
        if (value.getPhone() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPhone());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getUser_id());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM business_info";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteUser = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM business_info WHERE user_id = ? ";
        return _query;
      }
    };
  }

  @Override
  public Long insertBusinessInfo(final Business business_info) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfBusiness.insertAndReturnId(business_info);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteBusinessInfo(final Business business_info) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfBusiness.handle(business_info);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateBusinessInfo(final Business business_info) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfBusiness.handle(business_info);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public int deleteUser(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteUser.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteUser.release(_stmt);
    }
  }

  @Override
  public LiveData<Business> getBusinessInfo(final Long id) {
    final String _sql = "select * from business_info WHERE user_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, id);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"business_info"}, false, new Callable<Business>() {
      @Override
      public Business call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final int _cursorIndexOfBusinessName = CursorUtil.getColumnIndexOrThrow(_cursor, "business_name");
          final int _cursorIndexOfBusinessAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "business_address");
          final int _cursorIndexOfBusinessState = CursorUtil.getColumnIndexOrThrow(_cursor, "business_state");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final Business _result;
          if(_cursor.moveToFirst()) {
            _result = new Business();
            final Long _tmpUser_id;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUser_id = null;
            } else {
              _tmpUser_id = _cursor.getLong(_cursorIndexOfUserId);
            }
            _result.setUser_id(_tmpUser_id);
            final String _tmpBusiness_name;
            if (_cursor.isNull(_cursorIndexOfBusinessName)) {
              _tmpBusiness_name = null;
            } else {
              _tmpBusiness_name = _cursor.getString(_cursorIndexOfBusinessName);
            }
            _result.setBusiness_name(_tmpBusiness_name);
            final String _tmpBusiness_address;
            if (_cursor.isNull(_cursorIndexOfBusinessAddress)) {
              _tmpBusiness_address = null;
            } else {
              _tmpBusiness_address = _cursor.getString(_cursorIndexOfBusinessAddress);
            }
            _result.setBusiness_address(_tmpBusiness_address);
            final String _tmpBusiness_state;
            if (_cursor.isNull(_cursorIndexOfBusinessState)) {
              _tmpBusiness_state = null;
            } else {
              _tmpBusiness_state = _cursor.getString(_cursorIndexOfBusinessState);
            }
            _result.setBusiness_state(_tmpBusiness_state);
            final String _tmpPassword;
            if (_cursor.isNull(_cursorIndexOfPassword)) {
              _tmpPassword = null;
            } else {
              _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            }
            _result.setPassword(_tmpPassword);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            _result.setPhone(_tmpPhone);
          } else {
            _result = null;
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
  public List<Business> searchByBusinessName(final String key) {
    final String _sql = "select * from business_info WHERE business_name LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (key == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, key);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
      final int _cursorIndexOfBusinessName = CursorUtil.getColumnIndexOrThrow(_cursor, "business_name");
      final int _cursorIndexOfBusinessAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "business_address");
      final int _cursorIndexOfBusinessState = CursorUtil.getColumnIndexOrThrow(_cursor, "business_state");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final List<Business> _result = new ArrayList<Business>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Business _item;
        _item = new Business();
        final Long _tmpUser_id;
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _tmpUser_id = null;
        } else {
          _tmpUser_id = _cursor.getLong(_cursorIndexOfUserId);
        }
        _item.setUser_id(_tmpUser_id);
        final String _tmpBusiness_name;
        if (_cursor.isNull(_cursorIndexOfBusinessName)) {
          _tmpBusiness_name = null;
        } else {
          _tmpBusiness_name = _cursor.getString(_cursorIndexOfBusinessName);
        }
        _item.setBusiness_name(_tmpBusiness_name);
        final String _tmpBusiness_address;
        if (_cursor.isNull(_cursorIndexOfBusinessAddress)) {
          _tmpBusiness_address = null;
        } else {
          _tmpBusiness_address = _cursor.getString(_cursorIndexOfBusinessAddress);
        }
        _item.setBusiness_address(_tmpBusiness_address);
        final String _tmpBusiness_state;
        if (_cursor.isNull(_cursorIndexOfBusinessState)) {
          _tmpBusiness_state = null;
        } else {
          _tmpBusiness_state = _cursor.getString(_cursorIndexOfBusinessState);
        }
        _item.setBusiness_state(_tmpBusiness_state);
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        _item.setPassword(_tmpPassword);
        final String _tmpPhone;
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _tmpPhone = null;
        } else {
          _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        }
        _item.setPhone(_tmpPhone);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> doesBusinessInfoExists(final int id) {
    final String _sql = "select COUNT(*) from business_info where user_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[]{"business_info"}, false, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
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
  public LiveData<Integer> getMaxID() {
    final String _sql = "select MAX(user_id) from business_info";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"business_info"}, false, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
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
  public Integer getRowCount() {
    final String _sql = "select COUNT(*) from business_info";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Integer _result;
      if(_cursor.moveToFirst()) {
        final Integer _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(0);
        }
        _result = _tmp;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
