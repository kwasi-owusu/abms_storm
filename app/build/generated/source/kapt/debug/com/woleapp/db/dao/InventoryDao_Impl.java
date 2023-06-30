package com.woleapp.db.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetDataSource;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.woleapp.model.Inventory;
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
public final class InventoryDao_Impl implements InventoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Inventory> __insertionAdapterOfInventory;

  private final EntityDeletionOrUpdateAdapter<Inventory> __deletionAdapterOfInventory;

  private final EntityDeletionOrUpdateAdapter<Inventory> __updateAdapterOfInventory;

  private final SharedSQLiteStatement __preparedStmtOfUpdateImagePath;

  private final SharedSQLiteStatement __preparedStmtOfUpdateInv;

  private final SharedSQLiteStatement __preparedStmtOfUpdateInventoriesFTSTable;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteInventories;

  public InventoryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfInventory = new EntityInsertionAdapter<Inventory>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `inventory` (`rowid`,`productId`,`merchantId`,`product_name`,`category_name`,`price`,`quantity`,`description`,`image_path`,`imageLocalPath`,`color`,`size`,`deliveryFee`,`storeName`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Inventory value) {
        if (value.getInventory_id() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getInventory_id());
        }
        if (value.getProductId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getProductId());
        }
        if (value.getMerchantId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMerchantId());
        }
        if (value.getProduct_name() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getProduct_name());
        }
        if (value.getCategory_name() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCategory_name());
        }
        if (value.getPrice() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPrice());
        }
        if (value.getQuantity() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getQuantity());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getDescription());
        }
        if (value.getImage_path() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getImage_path());
        }
        if (value.getImageLocalPath() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getImageLocalPath());
        }
        if (value.getColor() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getColor());
        }
        if (value.getSize() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getSize());
        }
        stmt.bindDouble(13, value.getDeliveryFee());
        if (value.getStoreName() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getStoreName());
        }
      }
    };
    this.__deletionAdapterOfInventory = new EntityDeletionOrUpdateAdapter<Inventory>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `inventory` WHERE `rowid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Inventory value) {
        if (value.getInventory_id() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getInventory_id());
        }
      }
    };
    this.__updateAdapterOfInventory = new EntityDeletionOrUpdateAdapter<Inventory>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `inventory` SET `rowid` = ?,`productId` = ?,`merchantId` = ?,`product_name` = ?,`category_name` = ?,`price` = ?,`quantity` = ?,`description` = ?,`image_path` = ?,`imageLocalPath` = ?,`color` = ?,`size` = ?,`deliveryFee` = ?,`storeName` = ? WHERE `rowid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Inventory value) {
        if (value.getInventory_id() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getInventory_id());
        }
        if (value.getProductId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getProductId());
        }
        if (value.getMerchantId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMerchantId());
        }
        if (value.getProduct_name() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getProduct_name());
        }
        if (value.getCategory_name() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCategory_name());
        }
        if (value.getPrice() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPrice());
        }
        if (value.getQuantity() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getQuantity());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getDescription());
        }
        if (value.getImage_path() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getImage_path());
        }
        if (value.getImageLocalPath() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getImageLocalPath());
        }
        if (value.getColor() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getColor());
        }
        if (value.getSize() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getSize());
        }
        stmt.bindDouble(13, value.getDeliveryFee());
        if (value.getStoreName() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getStoreName());
        }
        if (value.getInventory_id() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindLong(15, value.getInventory_id());
        }
      }
    };
    this.__preparedStmtOfUpdateImagePath = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE inventory SET image_path =  ?  WHERE image_path LIKE ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateInv = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE inventory SET rowid = ? WHERE rowid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateInventoriesFTSTable = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "INSERT INTO inventoryFts(inventoryFts) VALUES ('rebuild')";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM inventory";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteInventories = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM inventory WHERE rowid = ? ";
        return _query;
      }
    };
  }

  @Override
  public Long insertInventory(final Inventory inventory) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfInventory.insertAndReturnId(inventory);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Long[] insertAll(final List<Inventory> inventories) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      Long[] _result = __insertionAdapterOfInventory.insertAndReturnIdsArrayBox(inventories);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteInventory(final Inventory inventory) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfInventory.handle(inventory);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateInventory(final Inventory inventory) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfInventory.handle(inventory);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateImagePath(final String image_path, final String query) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateImagePath.acquire();
    int _argIndex = 1;
    if (image_path == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, image_path);
    }
    _argIndex = 2;
    if (query == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, query);
    }
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateImagePath.release(_stmt);
    }
  }

  @Override
  public int updateInv(final Integer old_id, final Integer new_id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateInv.acquire();
    int _argIndex = 1;
    if (new_id == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindLong(_argIndex, new_id);
    }
    _argIndex = 2;
    if (old_id == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindLong(_argIndex, old_id);
    }
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateInv.release(_stmt);
    }
  }

  @Override
  public void updateInventoriesFTSTable() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateInventoriesFTSTable.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeInsert();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateInventoriesFTSTable.release(_stmt);
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
  public int deleteInventories(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteInventories.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteInventories.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Inventory>> getUserInventories() {
    final String _sql = "select * from inventory";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"inventory"}, false, new Callable<List<Inventory>>() {
      @Override
      public List<Inventory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfInventoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "rowid");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfMerchantId = CursorUtil.getColumnIndexOrThrow(_cursor, "merchantId");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "product_name");
          final int _cursorIndexOfCategoryName = CursorUtil.getColumnIndexOrThrow(_cursor, "category_name");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "image_path");
          final int _cursorIndexOfImageLocalPath = CursorUtil.getColumnIndexOrThrow(_cursor, "imageLocalPath");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
          final int _cursorIndexOfDeliveryFee = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryFee");
          final int _cursorIndexOfStoreName = CursorUtil.getColumnIndexOrThrow(_cursor, "storeName");
          final List<Inventory> _result = new ArrayList<Inventory>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Inventory _item;
            _item = new Inventory();
            final Integer _tmpInventory_id;
            if (_cursor.isNull(_cursorIndexOfInventoryId)) {
              _tmpInventory_id = null;
            } else {
              _tmpInventory_id = _cursor.getInt(_cursorIndexOfInventoryId);
            }
            _item.setInventory_id(_tmpInventory_id);
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            _item.setProductId(_tmpProductId);
            final String _tmpMerchantId;
            if (_cursor.isNull(_cursorIndexOfMerchantId)) {
              _tmpMerchantId = null;
            } else {
              _tmpMerchantId = _cursor.getString(_cursorIndexOfMerchantId);
            }
            _item.setMerchantId(_tmpMerchantId);
            final String _tmpProduct_name;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProduct_name = null;
            } else {
              _tmpProduct_name = _cursor.getString(_cursorIndexOfProductName);
            }
            _item.setProduct_name(_tmpProduct_name);
            final String _tmpCategory_name;
            if (_cursor.isNull(_cursorIndexOfCategoryName)) {
              _tmpCategory_name = null;
            } else {
              _tmpCategory_name = _cursor.getString(_cursorIndexOfCategoryName);
            }
            _item.setCategory_name(_tmpCategory_name);
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            _item.setPrice(_tmpPrice);
            final Integer _tmpQuantity;
            if (_cursor.isNull(_cursorIndexOfQuantity)) {
              _tmpQuantity = null;
            } else {
              _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            }
            _item.setQuantity(_tmpQuantity);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final String _tmpImage_path;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImage_path = null;
            } else {
              _tmpImage_path = _cursor.getString(_cursorIndexOfImagePath);
            }
            _item.setImage_path(_tmpImage_path);
            final String _tmpImageLocalPath;
            if (_cursor.isNull(_cursorIndexOfImageLocalPath)) {
              _tmpImageLocalPath = null;
            } else {
              _tmpImageLocalPath = _cursor.getString(_cursorIndexOfImageLocalPath);
            }
            _item.setImageLocalPath(_tmpImageLocalPath);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            _item.setColor(_tmpColor);
            final String _tmpSize;
            if (_cursor.isNull(_cursorIndexOfSize)) {
              _tmpSize = null;
            } else {
              _tmpSize = _cursor.getString(_cursorIndexOfSize);
            }
            _item.setSize(_tmpSize);
            final float _tmpDeliveryFee;
            _tmpDeliveryFee = _cursor.getFloat(_cursorIndexOfDeliveryFee);
            _item.setDeliveryFee(_tmpDeliveryFee);
            final String _tmpStoreName;
            if (_cursor.isNull(_cursorIndexOfStoreName)) {
              _tmpStoreName = null;
            } else {
              _tmpStoreName = _cursor.getString(_cursorIndexOfStoreName);
            }
            _item.setStoreName(_tmpStoreName);
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
  public LiveData<List<Inventory>> searchInventory(final String query) {
    final String _sql = "SELECT inventory.* FROM inventory JOIN inventoryFts ON (inventory.rowid = inventoryFts.rowid) WHERE inventoryFts MATCH ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"inventory","inventoryFts"}, false, new Callable<List<Inventory>>() {
      @Override
      public List<Inventory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfInventoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "rowid");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfMerchantId = CursorUtil.getColumnIndexOrThrow(_cursor, "merchantId");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "product_name");
          final int _cursorIndexOfCategoryName = CursorUtil.getColumnIndexOrThrow(_cursor, "category_name");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "image_path");
          final int _cursorIndexOfImageLocalPath = CursorUtil.getColumnIndexOrThrow(_cursor, "imageLocalPath");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
          final int _cursorIndexOfDeliveryFee = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryFee");
          final int _cursorIndexOfStoreName = CursorUtil.getColumnIndexOrThrow(_cursor, "storeName");
          final List<Inventory> _result = new ArrayList<Inventory>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Inventory _item;
            _item = new Inventory();
            final Integer _tmpInventory_id;
            if (_cursor.isNull(_cursorIndexOfInventoryId)) {
              _tmpInventory_id = null;
            } else {
              _tmpInventory_id = _cursor.getInt(_cursorIndexOfInventoryId);
            }
            _item.setInventory_id(_tmpInventory_id);
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            _item.setProductId(_tmpProductId);
            final String _tmpMerchantId;
            if (_cursor.isNull(_cursorIndexOfMerchantId)) {
              _tmpMerchantId = null;
            } else {
              _tmpMerchantId = _cursor.getString(_cursorIndexOfMerchantId);
            }
            _item.setMerchantId(_tmpMerchantId);
            final String _tmpProduct_name;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProduct_name = null;
            } else {
              _tmpProduct_name = _cursor.getString(_cursorIndexOfProductName);
            }
            _item.setProduct_name(_tmpProduct_name);
            final String _tmpCategory_name;
            if (_cursor.isNull(_cursorIndexOfCategoryName)) {
              _tmpCategory_name = null;
            } else {
              _tmpCategory_name = _cursor.getString(_cursorIndexOfCategoryName);
            }
            _item.setCategory_name(_tmpCategory_name);
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            _item.setPrice(_tmpPrice);
            final Integer _tmpQuantity;
            if (_cursor.isNull(_cursorIndexOfQuantity)) {
              _tmpQuantity = null;
            } else {
              _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            }
            _item.setQuantity(_tmpQuantity);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final String _tmpImage_path;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImage_path = null;
            } else {
              _tmpImage_path = _cursor.getString(_cursorIndexOfImagePath);
            }
            _item.setImage_path(_tmpImage_path);
            final String _tmpImageLocalPath;
            if (_cursor.isNull(_cursorIndexOfImageLocalPath)) {
              _tmpImageLocalPath = null;
            } else {
              _tmpImageLocalPath = _cursor.getString(_cursorIndexOfImageLocalPath);
            }
            _item.setImageLocalPath(_tmpImageLocalPath);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            _item.setColor(_tmpColor);
            final String _tmpSize;
            if (_cursor.isNull(_cursorIndexOfSize)) {
              _tmpSize = null;
            } else {
              _tmpSize = _cursor.getString(_cursorIndexOfSize);
            }
            _item.setSize(_tmpSize);
            final float _tmpDeliveryFee;
            _tmpDeliveryFee = _cursor.getFloat(_cursorIndexOfDeliveryFee);
            _item.setDeliveryFee(_tmpDeliveryFee);
            final String _tmpStoreName;
            if (_cursor.isNull(_cursorIndexOfStoreName)) {
              _tmpStoreName = null;
            } else {
              _tmpStoreName = _cursor.getString(_cursorIndexOfStoreName);
            }
            _item.setStoreName(_tmpStoreName);
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
  public DataSource.Factory<Integer, Inventory> searchInventoryOnline(final String query) {
    final String _sql = "SELECT inventory.* FROM inventory JOIN inventoryFts ON (inventory.rowid = inventoryFts.rowid) WHERE inventoryFts MATCH ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return new DataSource.Factory<Integer, Inventory>() {
      @Override
      public LimitOffsetDataSource<Inventory> create() {
        return new LimitOffsetDataSource<Inventory>(__db, _statement, true, true , "inventory", "inventoryFts") {
          @Override
          protected List<Inventory> convertRows(Cursor cursor) {
            final int _cursorIndexOfInventoryId = CursorUtil.getColumnIndexOrThrow(cursor, "rowid");
            final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(cursor, "productId");
            final int _cursorIndexOfMerchantId = CursorUtil.getColumnIndexOrThrow(cursor, "merchantId");
            final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(cursor, "product_name");
            final int _cursorIndexOfCategoryName = CursorUtil.getColumnIndexOrThrow(cursor, "category_name");
            final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(cursor, "price");
            final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(cursor, "quantity");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(cursor, "description");
            final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(cursor, "image_path");
            final int _cursorIndexOfImageLocalPath = CursorUtil.getColumnIndexOrThrow(cursor, "imageLocalPath");
            final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(cursor, "color");
            final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(cursor, "size");
            final int _cursorIndexOfDeliveryFee = CursorUtil.getColumnIndexOrThrow(cursor, "deliveryFee");
            final int _cursorIndexOfStoreName = CursorUtil.getColumnIndexOrThrow(cursor, "storeName");
            final List<Inventory> _res = new ArrayList<Inventory>(cursor.getCount());
            while(cursor.moveToNext()) {
              final Inventory _item;
              _item = new Inventory();
              final Integer _tmpInventory_id;
              if (cursor.isNull(_cursorIndexOfInventoryId)) {
                _tmpInventory_id = null;
              } else {
                _tmpInventory_id = cursor.getInt(_cursorIndexOfInventoryId);
              }
              _item.setInventory_id(_tmpInventory_id);
              final String _tmpProductId;
              if (cursor.isNull(_cursorIndexOfProductId)) {
                _tmpProductId = null;
              } else {
                _tmpProductId = cursor.getString(_cursorIndexOfProductId);
              }
              _item.setProductId(_tmpProductId);
              final String _tmpMerchantId;
              if (cursor.isNull(_cursorIndexOfMerchantId)) {
                _tmpMerchantId = null;
              } else {
                _tmpMerchantId = cursor.getString(_cursorIndexOfMerchantId);
              }
              _item.setMerchantId(_tmpMerchantId);
              final String _tmpProduct_name;
              if (cursor.isNull(_cursorIndexOfProductName)) {
                _tmpProduct_name = null;
              } else {
                _tmpProduct_name = cursor.getString(_cursorIndexOfProductName);
              }
              _item.setProduct_name(_tmpProduct_name);
              final String _tmpCategory_name;
              if (cursor.isNull(_cursorIndexOfCategoryName)) {
                _tmpCategory_name = null;
              } else {
                _tmpCategory_name = cursor.getString(_cursorIndexOfCategoryName);
              }
              _item.setCategory_name(_tmpCategory_name);
              final String _tmpPrice;
              if (cursor.isNull(_cursorIndexOfPrice)) {
                _tmpPrice = null;
              } else {
                _tmpPrice = cursor.getString(_cursorIndexOfPrice);
              }
              _item.setPrice(_tmpPrice);
              final Integer _tmpQuantity;
              if (cursor.isNull(_cursorIndexOfQuantity)) {
                _tmpQuantity = null;
              } else {
                _tmpQuantity = cursor.getInt(_cursorIndexOfQuantity);
              }
              _item.setQuantity(_tmpQuantity);
              final String _tmpDescription;
              if (cursor.isNull(_cursorIndexOfDescription)) {
                _tmpDescription = null;
              } else {
                _tmpDescription = cursor.getString(_cursorIndexOfDescription);
              }
              _item.setDescription(_tmpDescription);
              final String _tmpImage_path;
              if (cursor.isNull(_cursorIndexOfImagePath)) {
                _tmpImage_path = null;
              } else {
                _tmpImage_path = cursor.getString(_cursorIndexOfImagePath);
              }
              _item.setImage_path(_tmpImage_path);
              final String _tmpImageLocalPath;
              if (cursor.isNull(_cursorIndexOfImageLocalPath)) {
                _tmpImageLocalPath = null;
              } else {
                _tmpImageLocalPath = cursor.getString(_cursorIndexOfImageLocalPath);
              }
              _item.setImageLocalPath(_tmpImageLocalPath);
              final String _tmpColor;
              if (cursor.isNull(_cursorIndexOfColor)) {
                _tmpColor = null;
              } else {
                _tmpColor = cursor.getString(_cursorIndexOfColor);
              }
              _item.setColor(_tmpColor);
              final String _tmpSize;
              if (cursor.isNull(_cursorIndexOfSize)) {
                _tmpSize = null;
              } else {
                _tmpSize = cursor.getString(_cursorIndexOfSize);
              }
              _item.setSize(_tmpSize);
              final float _tmpDeliveryFee;
              _tmpDeliveryFee = cursor.getFloat(_cursorIndexOfDeliveryFee);
              _item.setDeliveryFee(_tmpDeliveryFee);
              final String _tmpStoreName;
              if (cursor.isNull(_cursorIndexOfStoreName)) {
                _tmpStoreName = null;
              } else {
                _tmpStoreName = cursor.getString(_cursorIndexOfStoreName);
              }
              _item.setStoreName(_tmpStoreName);
              _res.add(_item);
            }
            return _res;
          }
        };
      }
    };
  }

  @Override
  public DataSource.Factory<Integer, Inventory> searchInventoryOnline1(final String query) {
    final String _sql = "SELECT inventory.* FROM inventory JOIN inventoryFts ON (inventory.rowid = inventoryFts.rowid) WHERE inventoryFts.product_name LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return new DataSource.Factory<Integer, Inventory>() {
      @Override
      public LimitOffsetDataSource<Inventory> create() {
        return new LimitOffsetDataSource<Inventory>(__db, _statement, true, true , "inventory", "inventoryFts") {
          @Override
          protected List<Inventory> convertRows(Cursor cursor) {
            final int _cursorIndexOfInventoryId = CursorUtil.getColumnIndexOrThrow(cursor, "rowid");
            final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(cursor, "productId");
            final int _cursorIndexOfMerchantId = CursorUtil.getColumnIndexOrThrow(cursor, "merchantId");
            final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(cursor, "product_name");
            final int _cursorIndexOfCategoryName = CursorUtil.getColumnIndexOrThrow(cursor, "category_name");
            final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(cursor, "price");
            final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(cursor, "quantity");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(cursor, "description");
            final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(cursor, "image_path");
            final int _cursorIndexOfImageLocalPath = CursorUtil.getColumnIndexOrThrow(cursor, "imageLocalPath");
            final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(cursor, "color");
            final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(cursor, "size");
            final int _cursorIndexOfDeliveryFee = CursorUtil.getColumnIndexOrThrow(cursor, "deliveryFee");
            final int _cursorIndexOfStoreName = CursorUtil.getColumnIndexOrThrow(cursor, "storeName");
            final List<Inventory> _res = new ArrayList<Inventory>(cursor.getCount());
            while(cursor.moveToNext()) {
              final Inventory _item;
              _item = new Inventory();
              final Integer _tmpInventory_id;
              if (cursor.isNull(_cursorIndexOfInventoryId)) {
                _tmpInventory_id = null;
              } else {
                _tmpInventory_id = cursor.getInt(_cursorIndexOfInventoryId);
              }
              _item.setInventory_id(_tmpInventory_id);
              final String _tmpProductId;
              if (cursor.isNull(_cursorIndexOfProductId)) {
                _tmpProductId = null;
              } else {
                _tmpProductId = cursor.getString(_cursorIndexOfProductId);
              }
              _item.setProductId(_tmpProductId);
              final String _tmpMerchantId;
              if (cursor.isNull(_cursorIndexOfMerchantId)) {
                _tmpMerchantId = null;
              } else {
                _tmpMerchantId = cursor.getString(_cursorIndexOfMerchantId);
              }
              _item.setMerchantId(_tmpMerchantId);
              final String _tmpProduct_name;
              if (cursor.isNull(_cursorIndexOfProductName)) {
                _tmpProduct_name = null;
              } else {
                _tmpProduct_name = cursor.getString(_cursorIndexOfProductName);
              }
              _item.setProduct_name(_tmpProduct_name);
              final String _tmpCategory_name;
              if (cursor.isNull(_cursorIndexOfCategoryName)) {
                _tmpCategory_name = null;
              } else {
                _tmpCategory_name = cursor.getString(_cursorIndexOfCategoryName);
              }
              _item.setCategory_name(_tmpCategory_name);
              final String _tmpPrice;
              if (cursor.isNull(_cursorIndexOfPrice)) {
                _tmpPrice = null;
              } else {
                _tmpPrice = cursor.getString(_cursorIndexOfPrice);
              }
              _item.setPrice(_tmpPrice);
              final Integer _tmpQuantity;
              if (cursor.isNull(_cursorIndexOfQuantity)) {
                _tmpQuantity = null;
              } else {
                _tmpQuantity = cursor.getInt(_cursorIndexOfQuantity);
              }
              _item.setQuantity(_tmpQuantity);
              final String _tmpDescription;
              if (cursor.isNull(_cursorIndexOfDescription)) {
                _tmpDescription = null;
              } else {
                _tmpDescription = cursor.getString(_cursorIndexOfDescription);
              }
              _item.setDescription(_tmpDescription);
              final String _tmpImage_path;
              if (cursor.isNull(_cursorIndexOfImagePath)) {
                _tmpImage_path = null;
              } else {
                _tmpImage_path = cursor.getString(_cursorIndexOfImagePath);
              }
              _item.setImage_path(_tmpImage_path);
              final String _tmpImageLocalPath;
              if (cursor.isNull(_cursorIndexOfImageLocalPath)) {
                _tmpImageLocalPath = null;
              } else {
                _tmpImageLocalPath = cursor.getString(_cursorIndexOfImageLocalPath);
              }
              _item.setImageLocalPath(_tmpImageLocalPath);
              final String _tmpColor;
              if (cursor.isNull(_cursorIndexOfColor)) {
                _tmpColor = null;
              } else {
                _tmpColor = cursor.getString(_cursorIndexOfColor);
              }
              _item.setColor(_tmpColor);
              final String _tmpSize;
              if (cursor.isNull(_cursorIndexOfSize)) {
                _tmpSize = null;
              } else {
                _tmpSize = cursor.getString(_cursorIndexOfSize);
              }
              _item.setSize(_tmpSize);
              final float _tmpDeliveryFee;
              _tmpDeliveryFee = cursor.getFloat(_cursorIndexOfDeliveryFee);
              _item.setDeliveryFee(_tmpDeliveryFee);
              final String _tmpStoreName;
              if (cursor.isNull(_cursorIndexOfStoreName)) {
                _tmpStoreName = null;
              } else {
                _tmpStoreName = cursor.getString(_cursorIndexOfStoreName);
              }
              _item.setStoreName(_tmpStoreName);
              _res.add(_item);
            }
            return _res;
          }
        };
      }
    };
  }

  @Override
  public LiveData<Integer> getUserInventoryCount() {
    final String _sql = "select COUNT(*) from inventory";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"inventory"}, false, new Callable<Integer>() {
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
    final String _sql = "select MAX(rowid) from inventory";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"inventory"}, false, new Callable<Integer>() {
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
    final String _sql = "select COUNT(*) from inventory";
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
  public Integer getRowCountFTS() {
    final String _sql = "select COUNT(*) from inventoryFts";
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
