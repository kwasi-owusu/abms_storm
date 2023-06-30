package com.woleapp.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.FtsTableInfo;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.woleapp.db.dao.BillerDao;
import com.woleapp.db.dao.BillerDao_Impl;
import com.woleapp.db.dao.BusinessDao;
import com.woleapp.db.dao.BusinessDao_Impl;
import com.woleapp.db.dao.InventoryDao;
import com.woleapp.db.dao.InventoryDao_Impl;
import com.woleapp.db.dao.MerchantTransactionDao;
import com.woleapp.db.dao.MerchantTransactionDao_Impl;
import com.woleapp.db.dao.TransactionsDao;
import com.woleapp.db.dao.TransactionsDao_Impl;
import com.woleapp.db.dao.UserDao;
import com.woleapp.db.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile BusinessDao _businessDao;

  private volatile InventoryDao _inventoryDao;

  private volatile BillerDao _billerDao;

  private volatile MerchantTransactionDao _merchantTransactionDao;

  private volatile TransactionsDao _transactionsDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(6) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`user_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_type` INTEGER, `name` TEXT, `email` TEXT, `password` TEXT, `phone` TEXT, `account_number` TEXT, `bank` TEXT, `bvn` TEXT, `terminal_id` TEXT, `is_verified` INTEGER, `availableBalance` TEXT, `ledgerBalance` TEXT, `isQRRegistered` INTEGER, `state` TEXT, `netplus_id` TEXT, `business_name` TEXT, `business_address` TEXT, `business_state` TEXT, `business_phone_number` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `business_info` (`user_id` INTEGER NOT NULL, `business_name` TEXT, `business_address` TEXT, `business_state` TEXT, `password` TEXT, `phone` TEXT, PRIMARY KEY(`user_id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `inventory` (`rowid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` TEXT, `merchantId` TEXT, `product_name` TEXT, `category_name` TEXT, `price` TEXT, `quantity` INTEGER, `description` TEXT, `image_path` TEXT, `imageLocalPath` TEXT, `color` TEXT, `size` TEXT, `deliveryFee` REAL NOT NULL, `storeName` TEXT)");
        _db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `inventoryFts` USING FTS4(`product_name` TEXT, content=`inventory`)");
        _db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_inventoryFts_BEFORE_UPDATE BEFORE UPDATE ON `inventory` BEGIN DELETE FROM `inventoryFts` WHERE `docid`=OLD.`rowid`; END");
        _db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_inventoryFts_BEFORE_DELETE BEFORE DELETE ON `inventory` BEGIN DELETE FROM `inventoryFts` WHERE `docid`=OLD.`rowid`; END");
        _db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_inventoryFts_AFTER_UPDATE AFTER UPDATE ON `inventory` BEGIN INSERT INTO `inventoryFts`(`docid`, `product_name`) VALUES (NEW.`rowid`, NEW.`product_name`); END");
        _db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_inventoryFts_AFTER_INSERT AFTER INSERT ON `inventory` BEGIN INSERT INTO `inventoryFts`(`docid`, `product_name`) VALUES (NEW.`rowid`, NEW.`product_name`); END");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `billers` (`id` INTEGER NOT NULL, `service_type` TEXT, `biller_name` TEXT, `biller_code` TEXT, `operation` TEXT, `status` TEXT, `verification_status` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `merchant_transaction` (`id` INTEGER NOT NULL, `reference` TEXT NOT NULL, `merchantId` TEXT NOT NULL, `paymentMethod` TEXT NOT NULL, `customerName` TEXT NOT NULL, `status` TEXT NOT NULL, `amount` TEXT NOT NULL, `productId` TEXT, `productCount` TEXT, `sellerId` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `transactions` (`tId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id` TEXT, `external_reference` TEXT, `our_reference` TEXT, `transaction_type` TEXT, `transaction_date` TEXT, `description` TEXT, `beneficiary_name` TEXT, `amount` REAL, `status` TEXT, `destination_account` TEXT)");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_external_reference_our_reference` ON `transactions` (`external_reference`, `our_reference`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '74a649b28a6e128397ff812f667c0b20')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `users`");
        _db.execSQL("DROP TABLE IF EXISTS `business_info`");
        _db.execSQL("DROP TABLE IF EXISTS `inventory`");
        _db.execSQL("DROP TABLE IF EXISTS `inventoryFts`");
        _db.execSQL("DROP TABLE IF EXISTS `billers`");
        _db.execSQL("DROP TABLE IF EXISTS `merchant_transaction`");
        _db.execSQL("DROP TABLE IF EXISTS `transactions`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_inventoryFts_BEFORE_UPDATE BEFORE UPDATE ON `inventory` BEGIN DELETE FROM `inventoryFts` WHERE `docid`=OLD.`rowid`; END");
        _db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_inventoryFts_BEFORE_DELETE BEFORE DELETE ON `inventory` BEGIN DELETE FROM `inventoryFts` WHERE `docid`=OLD.`rowid`; END");
        _db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_inventoryFts_AFTER_UPDATE AFTER UPDATE ON `inventory` BEGIN INSERT INTO `inventoryFts`(`docid`, `product_name`) VALUES (NEW.`rowid`, NEW.`product_name`); END");
        _db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_inventoryFts_AFTER_INSERT AFTER INSERT ON `inventory` BEGIN INSERT INTO `inventoryFts`(`docid`, `product_name`) VALUES (NEW.`rowid`, NEW.`product_name`); END");
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(20);
        _columnsUsers.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("user_type", new TableInfo.Column("user_type", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("password", new TableInfo.Column("password", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("account_number", new TableInfo.Column("account_number", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("bank", new TableInfo.Column("bank", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("bvn", new TableInfo.Column("bvn", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("terminal_id", new TableInfo.Column("terminal_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("is_verified", new TableInfo.Column("is_verified", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("availableBalance", new TableInfo.Column("availableBalance", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("ledgerBalance", new TableInfo.Column("ledgerBalance", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isQRRegistered", new TableInfo.Column("isQRRegistered", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("state", new TableInfo.Column("state", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("netplus_id", new TableInfo.Column("netplus_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("business_name", new TableInfo.Column("business_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("business_address", new TableInfo.Column("business_address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("business_state", new TableInfo.Column("business_state", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("business_phone_number", new TableInfo.Column("business_phone_number", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(_db, "users");
        if (! _infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.woleapp.model.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsBusinessInfo = new HashMap<String, TableInfo.Column>(6);
        _columnsBusinessInfo.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBusinessInfo.put("business_name", new TableInfo.Column("business_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBusinessInfo.put("business_address", new TableInfo.Column("business_address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBusinessInfo.put("business_state", new TableInfo.Column("business_state", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBusinessInfo.put("password", new TableInfo.Column("password", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBusinessInfo.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBusinessInfo = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBusinessInfo = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBusinessInfo = new TableInfo("business_info", _columnsBusinessInfo, _foreignKeysBusinessInfo, _indicesBusinessInfo);
        final TableInfo _existingBusinessInfo = TableInfo.read(_db, "business_info");
        if (! _infoBusinessInfo.equals(_existingBusinessInfo)) {
          return new RoomOpenHelper.ValidationResult(false, "business_info(com.woleapp.model.Business).\n"
                  + " Expected:\n" + _infoBusinessInfo + "\n"
                  + " Found:\n" + _existingBusinessInfo);
        }
        final HashMap<String, TableInfo.Column> _columnsInventory = new HashMap<String, TableInfo.Column>(14);
        _columnsInventory.put("rowid", new TableInfo.Column("rowid", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("productId", new TableInfo.Column("productId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("merchantId", new TableInfo.Column("merchantId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("product_name", new TableInfo.Column("product_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("category_name", new TableInfo.Column("category_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("price", new TableInfo.Column("price", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("quantity", new TableInfo.Column("quantity", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("image_path", new TableInfo.Column("image_path", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("imageLocalPath", new TableInfo.Column("imageLocalPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("color", new TableInfo.Column("color", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("size", new TableInfo.Column("size", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("deliveryFee", new TableInfo.Column("deliveryFee", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventory.put("storeName", new TableInfo.Column("storeName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysInventory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesInventory = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoInventory = new TableInfo("inventory", _columnsInventory, _foreignKeysInventory, _indicesInventory);
        final TableInfo _existingInventory = TableInfo.read(_db, "inventory");
        if (! _infoInventory.equals(_existingInventory)) {
          return new RoomOpenHelper.ValidationResult(false, "inventory(com.woleapp.model.Inventory).\n"
                  + " Expected:\n" + _infoInventory + "\n"
                  + " Found:\n" + _existingInventory);
        }
        final HashSet<String> _columnsInventoryFts = new HashSet<String>(1);
        _columnsInventoryFts.add("product_name");
        final FtsTableInfo _infoInventoryFts = new FtsTableInfo("inventoryFts", _columnsInventoryFts, "CREATE VIRTUAL TABLE IF NOT EXISTS `inventoryFts` USING FTS4(`product_name` TEXT, content=`inventory`)");
        final FtsTableInfo _existingInventoryFts = FtsTableInfo.read(_db, "inventoryFts");
        if (!_infoInventoryFts.equals(_existingInventoryFts)) {
          return new RoomOpenHelper.ValidationResult(false, "inventoryFts(com.woleapp.model.InventoryFts).\n"
                  + " Expected:\n" + _infoInventoryFts + "\n"
                  + " Found:\n" + _existingInventoryFts);
        }
        final HashMap<String, TableInfo.Column> _columnsBillers = new HashMap<String, TableInfo.Column>(7);
        _columnsBillers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillers.put("service_type", new TableInfo.Column("service_type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillers.put("biller_name", new TableInfo.Column("biller_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillers.put("biller_code", new TableInfo.Column("biller_code", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillers.put("operation", new TableInfo.Column("operation", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillers.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillers.put("verification_status", new TableInfo.Column("verification_status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBillers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBillers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBillers = new TableInfo("billers", _columnsBillers, _foreignKeysBillers, _indicesBillers);
        final TableInfo _existingBillers = TableInfo.read(_db, "billers");
        if (! _infoBillers.equals(_existingBillers)) {
          return new RoomOpenHelper.ValidationResult(false, "billers(com.woleapp.model.Biller).\n"
                  + " Expected:\n" + _infoBillers + "\n"
                  + " Found:\n" + _existingBillers);
        }
        final HashMap<String, TableInfo.Column> _columnsMerchantTransaction = new HashMap<String, TableInfo.Column>(10);
        _columnsMerchantTransaction.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMerchantTransaction.put("reference", new TableInfo.Column("reference", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMerchantTransaction.put("merchantId", new TableInfo.Column("merchantId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMerchantTransaction.put("paymentMethod", new TableInfo.Column("paymentMethod", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMerchantTransaction.put("customerName", new TableInfo.Column("customerName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMerchantTransaction.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMerchantTransaction.put("amount", new TableInfo.Column("amount", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMerchantTransaction.put("productId", new TableInfo.Column("productId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMerchantTransaction.put("productCount", new TableInfo.Column("productCount", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMerchantTransaction.put("sellerId", new TableInfo.Column("sellerId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMerchantTransaction = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMerchantTransaction = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMerchantTransaction = new TableInfo("merchant_transaction", _columnsMerchantTransaction, _foreignKeysMerchantTransaction, _indicesMerchantTransaction);
        final TableInfo _existingMerchantTransaction = TableInfo.read(_db, "merchant_transaction");
        if (! _infoMerchantTransaction.equals(_existingMerchantTransaction)) {
          return new RoomOpenHelper.ValidationResult(false, "merchant_transaction(com.woleapp.model.MerchantTransaction).\n"
                  + " Expected:\n" + _infoMerchantTransaction + "\n"
                  + " Found:\n" + _existingMerchantTransaction);
        }
        final HashMap<String, TableInfo.Column> _columnsTransactions = new HashMap<String, TableInfo.Column>(11);
        _columnsTransactions.put("tId", new TableInfo.Column("tId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("id", new TableInfo.Column("id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("external_reference", new TableInfo.Column("external_reference", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("our_reference", new TableInfo.Column("our_reference", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("transaction_type", new TableInfo.Column("transaction_type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("transaction_date", new TableInfo.Column("transaction_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("beneficiary_name", new TableInfo.Column("beneficiary_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("amount", new TableInfo.Column("amount", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("destination_account", new TableInfo.Column("destination_account", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTransactions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTransactions = new HashSet<TableInfo.Index>(1);
        _indicesTransactions.add(new TableInfo.Index("index_transactions_external_reference_our_reference", false, Arrays.asList("external_reference","our_reference"), Arrays.asList("ASC","ASC")));
        final TableInfo _infoTransactions = new TableInfo("transactions", _columnsTransactions, _foreignKeysTransactions, _indicesTransactions);
        final TableInfo _existingTransactions = TableInfo.read(_db, "transactions");
        if (! _infoTransactions.equals(_existingTransactions)) {
          return new RoomOpenHelper.ValidationResult(false, "transactions(com.woleapp.model.Transactions).\n"
                  + " Expected:\n" + _infoTransactions + "\n"
                  + " Found:\n" + _existingTransactions);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "74a649b28a6e128397ff812f667c0b20", "4f0114662f975d97e4e6d7584d0b519d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(1);
    _shadowTablesMap.put("inventoryFts", "inventory");
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","business_info","inventory","inventoryFts","billers","merchant_transaction","transactions");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `business_info`");
      _db.execSQL("DELETE FROM `inventory`");
      _db.execSQL("DELETE FROM `inventoryFts`");
      _db.execSQL("DELETE FROM `billers`");
      _db.execSQL("DELETE FROM `merchant_transaction`");
      _db.execSQL("DELETE FROM `transactions`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BusinessDao.class, BusinessDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(InventoryDao.class, InventoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BillerDao.class, BillerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MerchantTransactionDao.class, MerchantTransactionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TransactionsDao.class, TransactionsDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public BusinessDao businessDao() {
    if (_businessDao != null) {
      return _businessDao;
    } else {
      synchronized(this) {
        if(_businessDao == null) {
          _businessDao = new BusinessDao_Impl(this);
        }
        return _businessDao;
      }
    }
  }

  @Override
  public InventoryDao inventoryDao() {
    if (_inventoryDao != null) {
      return _inventoryDao;
    } else {
      synchronized(this) {
        if(_inventoryDao == null) {
          _inventoryDao = new InventoryDao_Impl(this);
        }
        return _inventoryDao;
      }
    }
  }

  @Override
  public BillerDao billerDao() {
    if (_billerDao != null) {
      return _billerDao;
    } else {
      synchronized(this) {
        if(_billerDao == null) {
          _billerDao = new BillerDao_Impl(this);
        }
        return _billerDao;
      }
    }
  }

  @Override
  public MerchantTransactionDao transactionsDao() {
    if (_merchantTransactionDao != null) {
      return _merchantTransactionDao;
    } else {
      synchronized(this) {
        if(_merchantTransactionDao == null) {
          _merchantTransactionDao = new MerchantTransactionDao_Impl(this);
        }
        return _merchantTransactionDao;
      }
    }
  }

  @Override
  public TransactionsDao agentTransactionDao() {
    if (_transactionsDao != null) {
      return _transactionsDao;
    } else {
      synchronized(this) {
        if(_transactionsDao == null) {
          _transactionsDao = new TransactionsDao_Impl(this);
        }
        return _transactionsDao;
      }
    }
  }
}
