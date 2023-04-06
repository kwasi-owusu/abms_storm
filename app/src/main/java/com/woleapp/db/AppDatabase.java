package com.woleapp.db;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.woleapp.db.dao.BillerDao;
import com.woleapp.db.dao.BusinessDao;
import com.woleapp.db.dao.InventoryDao;
import com.woleapp.db.dao.MerchantTransactionDao;
import com.woleapp.db.dao.TransactionsDao;
import com.woleapp.db.dao.UserDao;
import com.woleapp.model.*;
import com.woleapp.util.AppExecutors;

import java.util.List;

/**
 * Database schema that holds the list of repos.
 *
 * @author Kaushik N Sanji
 */
@Database(entities = {User.class, Business.class, Inventory.class, InventoryFts.class, Biller.class, MerchantTransaction.class, Transactions.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    //WordDao is a DAO class annotated with @Dao
    public abstract UserDao userDao();

    public abstract BusinessDao businessDao();

    public abstract InventoryDao inventoryDao();

    public abstract BillerDao billerDao();

    public abstract MerchantTransactionDao transactionsDao();

    public abstract TransactionsDao agentTransactionDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    @VisibleForTesting
    public static final String DATABASE_NAME = "storm_db";

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                    INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
//        return Room.databaseBuilder(
//                context.getApplicationContext(),
//                WordRoomDatabase.class,
//                "Github.db"
//        ).build();

        return Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                DATABASE_NAME)
                /*.addMigrations(Migrations.MIGRATION_3_4)//, MIGRATION_2_3
                .addMigrations(Migrations.MIGRATION_4_5)
                .addMigrations(Migrations.MIGRATION_5_6)
                .addMigrations(Migrations.MIGRATION_6_7)
                .addMigrations(Migrations.MIGRATION_7_8)
                .addMigrations(Migrations.MIGRATION_8_9)
                .addMigrations(Migrations.MIGRATION_9_10)*/

                // Wipes and rebuilds instead of migrating if no Migration object.
                // Migration is not part of this codelab.
                .fallbackToDestructiveMigration()

                /**
                 * Override the onOpen method to populate the database.
                 * We can clear the database every time it is created or opened or
                 * if you want to populate the database only when the database is
                 * created for the 1st time, override RoomDatabase.Callback()#onCreate
                 */
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        Log.e("DB Created", db.getPath() + "--");
                        AppExecutors mAppExecutors = new AppExecutors();
                        mAppExecutors.diskIO().execute(() -> {
                            // Add a delay to simulate a long-running operation
                            //addDelay();
                            // Generate the data for pre-population
                            AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
                            List<Biller> billers = DataGenerator.generateBillers();
                /*List<ProductEntity> products = DataGenerator.generateProducts();
                List<CommentEntity> comments =
                        DataGenerator.generateCommentsForProducts(products);*/

                            insertData(database, billers);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        // If you want to keep the data through app restarts,
                        // comment out the following line.
                        Log.e("DB Opened", db.getPath() + "--");
                        new PopulateDbAsync(INSTANCE).execute();
                    }
                })
                //.addCallback(sRoomDatabaseCallback)
                .build();
    }


    private static void insertData(final AppDatabase database, final List<Biller> billers) {
        database.runInTransaction(() -> {
            database.billerDao().insertAll(billers);
            //database.commentDao().insertAll(comments);
        });
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }
    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    /*private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback()
    {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Log.e("DB Created",db.getPath()+"--");
            AppExecutors mAppExecutors = new AppExecutors();
            mAppExecutors.diskIO().execute(() -> {
                // Add a delay to simulate a long-running operation
                //addDelay();
                // Generate the data for pre-population
                AppDatabase database = AppDatabase.getInstance(db.get);
                List<Biller> billers = DataGenerator.generateBillers();
                *//*List<ProductEntity> products = DataGenerator.generateProducts();
                List<CommentEntity> comments =
                        DataGenerator.generateCommentsForProducts(products);*//*

                insertData(database, products, comments);
                // notify that the database was created and it's ready to be used
                database.setDatabaseCreated();
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            Log.e("DB Opened",db.getPath()+"--");
            new PopulateDbAsync(INSTANCE).execute();
        }
    };*/


    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao mDao;
        private final BusinessDao mBusinessDao;
        private final InventoryDao mInventoryDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.userDao();
            mBusinessDao = db.businessDao();
            mInventoryDao = db.inventoryDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.

            int cnt = mDao.getUserCount();
            int cnt1 = mBusinessDao.getRowCount();
            int cnt2 = mInventoryDao.getRowCount();

            Log.e("UserCnt", cnt + "---");
            Log.e("BusinessCnt", cnt1 + "---");
            Log.e("InventoryCnt", cnt2 + "---");
            /*if(cnt<=0)
            {
                Word word = new Word("Hello","A way to greet people");
                mDao.insert(word);
                word = new Word("World","The planet or ecosystem where which we live or sustain");
                mDao.insert(word);
            }*/
//            mDao.deleteAll();
//            Word word = new Word("Hello");
//            mDao.insert(word);
//            word = new Word("World");
//            mDao.insert(word);

            mInventoryDao.updateInventoriesFTSTable();
            int cnt3 = mInventoryDao.getRowCountFTS();
            Log.e("InventoryFTSCnt", cnt3 + "---");
            return null;
        }
    }


}
