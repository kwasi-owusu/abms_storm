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
import com.woleapp.model.User;
import java.lang.Boolean;
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
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfUpdateUser;

  private final SharedSQLiteStatement __preparedStmtOfUpdateWalletAmount;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUser;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `users` (`user_id`,`user_type`,`name`,`email`,`password`,`phone`,`account_number`,`bank`,`bvn`,`terminal_id`,`is_verified`,`availableBalance`,`ledgerBalance`,`isQRRegistered`,`state`,`netplus_id`,`business_name`,`business_address`,`business_state`,`business_phone_number`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        if (value.getUser_id() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getUser_id());
        }
        if (value.getUser_type() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getUser_type());
        }
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEmail());
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
        if (value.getAccount_number() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getAccount_number());
        }
        if (value.getBank() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getBank());
        }
        if (value.getBvn() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getBvn());
        }
        if (value.getTerminal_id() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getTerminal_id());
        }
        final Integer _tmp = value.getIs_verified() == null ? null : (value.getIs_verified() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, _tmp);
        }
        if (value.getAvailableBalance() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getAvailableBalance());
        }
        if (value.getLedgerBalance() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getLedgerBalance());
        }
        final Integer _tmp_1 = value.getQRRegistered() == null ? null : (value.getQRRegistered() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindLong(14, _tmp_1);
        }
        if (value.getState() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getState());
        }
        if (value.getNetplus_id() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getNetplus_id());
        }
        if (value.getBusiness_name() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getBusiness_name());
        }
        if (value.getBusiness_address() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getBusiness_address());
        }
        if (value.getBusiness_state() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getBusiness_state());
        }
        if (value.getBusiness_phone_number() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getBusiness_phone_number());
        }
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `users` WHERE `user_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        if (value.getUser_id() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getUser_id());
        }
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `users` SET `user_id` = ?,`user_type` = ?,`name` = ?,`email` = ?,`password` = ?,`phone` = ?,`account_number` = ?,`bank` = ?,`bvn` = ?,`terminal_id` = ?,`is_verified` = ?,`availableBalance` = ?,`ledgerBalance` = ?,`isQRRegistered` = ?,`state` = ?,`netplus_id` = ?,`business_name` = ?,`business_address` = ?,`business_state` = ?,`business_phone_number` = ? WHERE `user_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        if (value.getUser_id() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getUser_id());
        }
        if (value.getUser_type() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getUser_type());
        }
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEmail());
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
        if (value.getAccount_number() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getAccount_number());
        }
        if (value.getBank() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getBank());
        }
        if (value.getBvn() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getBvn());
        }
        if (value.getTerminal_id() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getTerminal_id());
        }
        final Integer _tmp = value.getIs_verified() == null ? null : (value.getIs_verified() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, _tmp);
        }
        if (value.getAvailableBalance() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getAvailableBalance());
        }
        if (value.getLedgerBalance() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getLedgerBalance());
        }
        final Integer _tmp_1 = value.getQRRegistered() == null ? null : (value.getQRRegistered() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindLong(14, _tmp_1);
        }
        if (value.getState() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getState());
        }
        if (value.getNetplus_id() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getNetplus_id());
        }
        if (value.getBusiness_name() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getBusiness_name());
        }
        if (value.getBusiness_address() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getBusiness_address());
        }
        if (value.getBusiness_state() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getBusiness_state());
        }
        if (value.getBusiness_phone_number() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getBusiness_phone_number());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindLong(21, value.getUser_id());
        }
      }
    };
    this.__preparedStmtOfUpdateUser = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE users SET is_verified = ?  WHERE user_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateWalletAmount = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE users SET availableBalance = ?  WHERE user_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteUser = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM users WHERE user_id = ? ";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM users";
        return _query;
      }
    };
  }

  @Override
  public Long insertUser(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfUser.insertAndReturnId(user);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteInvoice(final User user) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteUser(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(final User user) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(final Long id, final boolean isVerified) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateUser.acquire();
    int _argIndex = 1;
    final int _tmp = isVerified ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    if (id == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindLong(_argIndex, id);
    }
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateUser.release(_stmt);
    }
  }

  @Override
  public int updateWalletAmount(final Long id, final String wallet_amount) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateWalletAmount.acquire();
    int _argIndex = 1;
    if (wallet_amount == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, wallet_amount);
    }
    _argIndex = 2;
    if (id == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindLong(_argIndex, id);
    }
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateWalletAmount.release(_stmt);
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
  public LiveData<List<User>> loadAll() {
    final String _sql = "select * from users ORDER BY user_id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"users"}, false, new Callable<List<User>>() {
      @Override
      public List<User> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final int _cursorIndexOfUserType = CursorUtil.getColumnIndexOrThrow(_cursor, "user_type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfAccountNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "account_number");
          final int _cursorIndexOfBank = CursorUtil.getColumnIndexOrThrow(_cursor, "bank");
          final int _cursorIndexOfBvn = CursorUtil.getColumnIndexOrThrow(_cursor, "bvn");
          final int _cursorIndexOfTerminalId = CursorUtil.getColumnIndexOrThrow(_cursor, "terminal_id");
          final int _cursorIndexOfIsVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "is_verified");
          final int _cursorIndexOfAvailableBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "availableBalance");
          final int _cursorIndexOfLedgerBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "ledgerBalance");
          final int _cursorIndexOfIsQRRegistered = CursorUtil.getColumnIndexOrThrow(_cursor, "isQRRegistered");
          final int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, "state");
          final int _cursorIndexOfNetplusId = CursorUtil.getColumnIndexOrThrow(_cursor, "netplus_id");
          final int _cursorIndexOfBusinessName = CursorUtil.getColumnIndexOrThrow(_cursor, "business_name");
          final int _cursorIndexOfBusinessAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "business_address");
          final int _cursorIndexOfBusinessState = CursorUtil.getColumnIndexOrThrow(_cursor, "business_state");
          final int _cursorIndexOfBusinessPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "business_phone_number");
          final List<User> _result = new ArrayList<User>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final User _item;
            _item = new User();
            final Long _tmpUser_id;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUser_id = null;
            } else {
              _tmpUser_id = _cursor.getLong(_cursorIndexOfUserId);
            }
            _item.setUser_id(_tmpUser_id);
            final Integer _tmpUser_type;
            if (_cursor.isNull(_cursorIndexOfUserType)) {
              _tmpUser_type = null;
            } else {
              _tmpUser_type = _cursor.getInt(_cursorIndexOfUserType);
            }
            _item.setUser_type(_tmpUser_type);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _item.setEmail(_tmpEmail);
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
            final String _tmpAccount_number;
            if (_cursor.isNull(_cursorIndexOfAccountNumber)) {
              _tmpAccount_number = null;
            } else {
              _tmpAccount_number = _cursor.getString(_cursorIndexOfAccountNumber);
            }
            _item.setAccount_number(_tmpAccount_number);
            final String _tmpBank;
            if (_cursor.isNull(_cursorIndexOfBank)) {
              _tmpBank = null;
            } else {
              _tmpBank = _cursor.getString(_cursorIndexOfBank);
            }
            _item.setBank(_tmpBank);
            final String _tmpBvn;
            if (_cursor.isNull(_cursorIndexOfBvn)) {
              _tmpBvn = null;
            } else {
              _tmpBvn = _cursor.getString(_cursorIndexOfBvn);
            }
            _item.setBvn(_tmpBvn);
            final String _tmpTerminal_id;
            if (_cursor.isNull(_cursorIndexOfTerminalId)) {
              _tmpTerminal_id = null;
            } else {
              _tmpTerminal_id = _cursor.getString(_cursorIndexOfTerminalId);
            }
            _item.setTerminal_id(_tmpTerminal_id);
            final Boolean _tmpIs_verified;
            final Integer _tmp;
            if (_cursor.isNull(_cursorIndexOfIsVerified)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(_cursorIndexOfIsVerified);
            }
            _tmpIs_verified = _tmp == null ? null : _tmp != 0;
            _item.setIs_verified(_tmpIs_verified);
            final String _tmpAvailableBalance;
            if (_cursor.isNull(_cursorIndexOfAvailableBalance)) {
              _tmpAvailableBalance = null;
            } else {
              _tmpAvailableBalance = _cursor.getString(_cursorIndexOfAvailableBalance);
            }
            _item.setAvailableBalance(_tmpAvailableBalance);
            final String _tmpLedgerBalance;
            if (_cursor.isNull(_cursorIndexOfLedgerBalance)) {
              _tmpLedgerBalance = null;
            } else {
              _tmpLedgerBalance = _cursor.getString(_cursorIndexOfLedgerBalance);
            }
            _item.setLedgerBalance(_tmpLedgerBalance);
            final Boolean _tmpIsQRRegistered;
            final Integer _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIsQRRegistered)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(_cursorIndexOfIsQRRegistered);
            }
            _tmpIsQRRegistered = _tmp_1 == null ? null : _tmp_1 != 0;
            _item.setQRRegistered(_tmpIsQRRegistered);
            final String _tmpState;
            if (_cursor.isNull(_cursorIndexOfState)) {
              _tmpState = null;
            } else {
              _tmpState = _cursor.getString(_cursorIndexOfState);
            }
            _item.setState(_tmpState);
            final String _tmpNetplus_id;
            if (_cursor.isNull(_cursorIndexOfNetplusId)) {
              _tmpNetplus_id = null;
            } else {
              _tmpNetplus_id = _cursor.getString(_cursorIndexOfNetplusId);
            }
            _item.setNetplus_id(_tmpNetplus_id);
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
            final String _tmpBusiness_phone_number;
            if (_cursor.isNull(_cursorIndexOfBusinessPhoneNumber)) {
              _tmpBusiness_phone_number = null;
            } else {
              _tmpBusiness_phone_number = _cursor.getString(_cursorIndexOfBusinessPhoneNumber);
            }
            _item.setBusiness_phone_number(_tmpBusiness_phone_number);
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
  public List<User> searchForUser(final int type, final String key) {
    final String _sql = "select * from users WHERE  user_type = ? AND name LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, type);
    _argIndex = 2;
    if (key == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, key);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
      final int _cursorIndexOfUserType = CursorUtil.getColumnIndexOrThrow(_cursor, "user_type");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfAccountNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "account_number");
      final int _cursorIndexOfBank = CursorUtil.getColumnIndexOrThrow(_cursor, "bank");
      final int _cursorIndexOfBvn = CursorUtil.getColumnIndexOrThrow(_cursor, "bvn");
      final int _cursorIndexOfTerminalId = CursorUtil.getColumnIndexOrThrow(_cursor, "terminal_id");
      final int _cursorIndexOfIsVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "is_verified");
      final int _cursorIndexOfAvailableBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "availableBalance");
      final int _cursorIndexOfLedgerBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "ledgerBalance");
      final int _cursorIndexOfIsQRRegistered = CursorUtil.getColumnIndexOrThrow(_cursor, "isQRRegistered");
      final int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, "state");
      final int _cursorIndexOfNetplusId = CursorUtil.getColumnIndexOrThrow(_cursor, "netplus_id");
      final int _cursorIndexOfBusinessName = CursorUtil.getColumnIndexOrThrow(_cursor, "business_name");
      final int _cursorIndexOfBusinessAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "business_address");
      final int _cursorIndexOfBusinessState = CursorUtil.getColumnIndexOrThrow(_cursor, "business_state");
      final int _cursorIndexOfBusinessPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "business_phone_number");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        _item = new User();
        final Long _tmpUser_id;
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _tmpUser_id = null;
        } else {
          _tmpUser_id = _cursor.getLong(_cursorIndexOfUserId);
        }
        _item.setUser_id(_tmpUser_id);
        final Integer _tmpUser_type;
        if (_cursor.isNull(_cursorIndexOfUserType)) {
          _tmpUser_type = null;
        } else {
          _tmpUser_type = _cursor.getInt(_cursorIndexOfUserType);
        }
        _item.setUser_type(_tmpUser_type);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        _item.setEmail(_tmpEmail);
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
        final String _tmpAccount_number;
        if (_cursor.isNull(_cursorIndexOfAccountNumber)) {
          _tmpAccount_number = null;
        } else {
          _tmpAccount_number = _cursor.getString(_cursorIndexOfAccountNumber);
        }
        _item.setAccount_number(_tmpAccount_number);
        final String _tmpBank;
        if (_cursor.isNull(_cursorIndexOfBank)) {
          _tmpBank = null;
        } else {
          _tmpBank = _cursor.getString(_cursorIndexOfBank);
        }
        _item.setBank(_tmpBank);
        final String _tmpBvn;
        if (_cursor.isNull(_cursorIndexOfBvn)) {
          _tmpBvn = null;
        } else {
          _tmpBvn = _cursor.getString(_cursorIndexOfBvn);
        }
        _item.setBvn(_tmpBvn);
        final String _tmpTerminal_id;
        if (_cursor.isNull(_cursorIndexOfTerminalId)) {
          _tmpTerminal_id = null;
        } else {
          _tmpTerminal_id = _cursor.getString(_cursorIndexOfTerminalId);
        }
        _item.setTerminal_id(_tmpTerminal_id);
        final Boolean _tmpIs_verified;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfIsVerified)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfIsVerified);
        }
        _tmpIs_verified = _tmp == null ? null : _tmp != 0;
        _item.setIs_verified(_tmpIs_verified);
        final String _tmpAvailableBalance;
        if (_cursor.isNull(_cursorIndexOfAvailableBalance)) {
          _tmpAvailableBalance = null;
        } else {
          _tmpAvailableBalance = _cursor.getString(_cursorIndexOfAvailableBalance);
        }
        _item.setAvailableBalance(_tmpAvailableBalance);
        final String _tmpLedgerBalance;
        if (_cursor.isNull(_cursorIndexOfLedgerBalance)) {
          _tmpLedgerBalance = null;
        } else {
          _tmpLedgerBalance = _cursor.getString(_cursorIndexOfLedgerBalance);
        }
        _item.setLedgerBalance(_tmpLedgerBalance);
        final Boolean _tmpIsQRRegistered;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfIsQRRegistered)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfIsQRRegistered);
        }
        _tmpIsQRRegistered = _tmp_1 == null ? null : _tmp_1 != 0;
        _item.setQRRegistered(_tmpIsQRRegistered);
        final String _tmpState;
        if (_cursor.isNull(_cursorIndexOfState)) {
          _tmpState = null;
        } else {
          _tmpState = _cursor.getString(_cursorIndexOfState);
        }
        _item.setState(_tmpState);
        final String _tmpNetplus_id;
        if (_cursor.isNull(_cursorIndexOfNetplusId)) {
          _tmpNetplus_id = null;
        } else {
          _tmpNetplus_id = _cursor.getString(_cursorIndexOfNetplusId);
        }
        _item.setNetplus_id(_tmpNetplus_id);
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
        final String _tmpBusiness_phone_number;
        if (_cursor.isNull(_cursorIndexOfBusinessPhoneNumber)) {
          _tmpBusiness_phone_number = null;
        } else {
          _tmpBusiness_phone_number = _cursor.getString(_cursorIndexOfBusinessPhoneNumber);
        }
        _item.setBusiness_phone_number(_tmpBusiness_phone_number);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> doesUserExist(final String email) {
    final String _sql = "select COUNT(*) from users where email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"users"}, false, new Callable<Integer>() {
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
  public LiveData<User> getUser(final String email, final String password) {
    final String _sql = "select * from users where email = ? AND password = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    _argIndex = 2;
    if (password == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, password);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"users"}, false, new Callable<User>() {
      @Override
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final int _cursorIndexOfUserType = CursorUtil.getColumnIndexOrThrow(_cursor, "user_type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfAccountNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "account_number");
          final int _cursorIndexOfBank = CursorUtil.getColumnIndexOrThrow(_cursor, "bank");
          final int _cursorIndexOfBvn = CursorUtil.getColumnIndexOrThrow(_cursor, "bvn");
          final int _cursorIndexOfTerminalId = CursorUtil.getColumnIndexOrThrow(_cursor, "terminal_id");
          final int _cursorIndexOfIsVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "is_verified");
          final int _cursorIndexOfAvailableBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "availableBalance");
          final int _cursorIndexOfLedgerBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "ledgerBalance");
          final int _cursorIndexOfIsQRRegistered = CursorUtil.getColumnIndexOrThrow(_cursor, "isQRRegistered");
          final int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, "state");
          final int _cursorIndexOfNetplusId = CursorUtil.getColumnIndexOrThrow(_cursor, "netplus_id");
          final int _cursorIndexOfBusinessName = CursorUtil.getColumnIndexOrThrow(_cursor, "business_name");
          final int _cursorIndexOfBusinessAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "business_address");
          final int _cursorIndexOfBusinessState = CursorUtil.getColumnIndexOrThrow(_cursor, "business_state");
          final int _cursorIndexOfBusinessPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "business_phone_number");
          final User _result;
          if(_cursor.moveToFirst()) {
            _result = new User();
            final Long _tmpUser_id;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUser_id = null;
            } else {
              _tmpUser_id = _cursor.getLong(_cursorIndexOfUserId);
            }
            _result.setUser_id(_tmpUser_id);
            final Integer _tmpUser_type;
            if (_cursor.isNull(_cursorIndexOfUserType)) {
              _tmpUser_type = null;
            } else {
              _tmpUser_type = _cursor.getInt(_cursorIndexOfUserType);
            }
            _result.setUser_type(_tmpUser_type);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _result.setName(_tmpName);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _result.setEmail(_tmpEmail);
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
            final String _tmpAccount_number;
            if (_cursor.isNull(_cursorIndexOfAccountNumber)) {
              _tmpAccount_number = null;
            } else {
              _tmpAccount_number = _cursor.getString(_cursorIndexOfAccountNumber);
            }
            _result.setAccount_number(_tmpAccount_number);
            final String _tmpBank;
            if (_cursor.isNull(_cursorIndexOfBank)) {
              _tmpBank = null;
            } else {
              _tmpBank = _cursor.getString(_cursorIndexOfBank);
            }
            _result.setBank(_tmpBank);
            final String _tmpBvn;
            if (_cursor.isNull(_cursorIndexOfBvn)) {
              _tmpBvn = null;
            } else {
              _tmpBvn = _cursor.getString(_cursorIndexOfBvn);
            }
            _result.setBvn(_tmpBvn);
            final String _tmpTerminal_id;
            if (_cursor.isNull(_cursorIndexOfTerminalId)) {
              _tmpTerminal_id = null;
            } else {
              _tmpTerminal_id = _cursor.getString(_cursorIndexOfTerminalId);
            }
            _result.setTerminal_id(_tmpTerminal_id);
            final Boolean _tmpIs_verified;
            final Integer _tmp;
            if (_cursor.isNull(_cursorIndexOfIsVerified)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(_cursorIndexOfIsVerified);
            }
            _tmpIs_verified = _tmp == null ? null : _tmp != 0;
            _result.setIs_verified(_tmpIs_verified);
            final String _tmpAvailableBalance;
            if (_cursor.isNull(_cursorIndexOfAvailableBalance)) {
              _tmpAvailableBalance = null;
            } else {
              _tmpAvailableBalance = _cursor.getString(_cursorIndexOfAvailableBalance);
            }
            _result.setAvailableBalance(_tmpAvailableBalance);
            final String _tmpLedgerBalance;
            if (_cursor.isNull(_cursorIndexOfLedgerBalance)) {
              _tmpLedgerBalance = null;
            } else {
              _tmpLedgerBalance = _cursor.getString(_cursorIndexOfLedgerBalance);
            }
            _result.setLedgerBalance(_tmpLedgerBalance);
            final Boolean _tmpIsQRRegistered;
            final Integer _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIsQRRegistered)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(_cursorIndexOfIsQRRegistered);
            }
            _tmpIsQRRegistered = _tmp_1 == null ? null : _tmp_1 != 0;
            _result.setQRRegistered(_tmpIsQRRegistered);
            final String _tmpState;
            if (_cursor.isNull(_cursorIndexOfState)) {
              _tmpState = null;
            } else {
              _tmpState = _cursor.getString(_cursorIndexOfState);
            }
            _result.setState(_tmpState);
            final String _tmpNetplus_id;
            if (_cursor.isNull(_cursorIndexOfNetplusId)) {
              _tmpNetplus_id = null;
            } else {
              _tmpNetplus_id = _cursor.getString(_cursorIndexOfNetplusId);
            }
            _result.setNetplus_id(_tmpNetplus_id);
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
            final String _tmpBusiness_phone_number;
            if (_cursor.isNull(_cursorIndexOfBusinessPhoneNumber)) {
              _tmpBusiness_phone_number = null;
            } else {
              _tmpBusiness_phone_number = _cursor.getString(_cursorIndexOfBusinessPhoneNumber);
            }
            _result.setBusiness_phone_number(_tmpBusiness_phone_number);
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
  public Integer getUserCount() {
    final String _sql = "select COUNT(*) from users";
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

  @Override
  public LiveData<Integer> getMaxID() {
    final String _sql = "select MAX(user_id) from users";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"users"}, false, new Callable<Integer>() {
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
