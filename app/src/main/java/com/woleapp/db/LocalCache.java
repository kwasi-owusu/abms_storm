package com.woleapp.db;

import android.util.Log;
import androidx.lifecycle.LiveData;

import androidx.paging.DataSource;
import com.woleapp.db.dao.BillerDao;
import com.woleapp.db.dao.BusinessDao;
import com.woleapp.db.dao.InventoryDao;
import com.woleapp.db.dao.UserDao;
import com.woleapp.model.Biller;
import com.woleapp.model.Business;
import com.woleapp.model.Inventory;
import com.woleapp.model.User;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Class that handles the DAO local data source. This ensures that methods are triggered on the
 * correct executor.
 *
 * @author Kaushik N Sanji
 */
public class LocalCache
{
    //Constant used for Logs
    private static final String LOG_TAG = LocalCache.class.getSimpleName();

    //Dao for Word Entity

    private UserDao userDao;
    private BusinessDao businessDao;
    private InventoryDao inventoryDao;
    private BillerDao billerDao;

    //private MessageDao messageDao;
    //Single Thread Executor for database operations
    private Executor ioExecutor;
    private int jobId;

    public LocalCache(UserDao userDao,BusinessDao businessDao, Executor ioExecutor)
    {
        this.userDao = userDao;
        this.businessDao = businessDao;
        this.ioExecutor = ioExecutor;
    }
    public LocalCache(UserDao userDao, Executor ioExecutor)
    {
        this.userDao = userDao;
        this.ioExecutor = ioExecutor;
    }

    public LocalCache(BusinessDao businessDao, Executor ioExecutor)
    {
        this.businessDao = businessDao;
        this.ioExecutor = ioExecutor;
    }

    public LocalCache(InventoryDao inventoryDao, Executor ioExecutor)
    {
        this.inventoryDao = inventoryDao;
        this.ioExecutor = ioExecutor;
    }

    public LocalCache(BillerDao billerDao, Executor ioExecutor)
    {
        this.billerDao = billerDao;
        this.ioExecutor = ioExecutor;
    }

    public LiveData<Integer> getMaxID() {
        return userDao.getMaxID();
    }

    public LiveData<Integer> doesUserExist(String email) {
        return userDao.doesUserExist(email);
    }

    public LiveData<User> getUser(String email, String password) {
        return userDao.getUser(email, password);
    }

    public LiveData<Business> getBusinessInfo(Long id)
    {
        return businessDao.getBusinessInfo(id);
    }

    public void insertUser(final User user, LocalCache.InsertCallback callback)
    {
        ioExecutor.execute(() ->
        {
            //Log.e(LOG_TAG, "insert: inserting " + messages.size() + " messages");
            Long result = userDao.insertUser(user);
            Log.e("Result: ",result+"--");
            callback.insertFinished(result);
            //return 1l;
        });
        //return messageDao.insert(message);
    }


    public void updateUser(Long user_id,LocalCache.UpdateCallback callback)
    {
        ioExecutor.execute(() ->
        {
            //Log.e(LOG_TAG, "insert: inserting " + messages.size() + " messages");
            int result = userDao.updateUser(user_id,true);
            Log.e("Result: ",result+"--");
            callback.updateFinished(result);
            //return 1l;
        });
        //return messageDao.insert(message);
    }

    public void updateWalletAmt(Long user_id,String amount, LocalCache.UpdateCallback callback)
    {
        ioExecutor.execute(() ->
        {
            //Log.e(LOG_TAG, "insert: inserting " + messages.size() + " messages");
            int result = userDao.updateWalletAmount(user_id,amount);
            Log.e("Result: ",result+"--");
            callback.updateFinished(result);
            //return 1l;
        });
        //return messageDao.insert(message);
    }

    public void updateUser(final User user, LocalCache.UpdateCallback callback)
    {
        ioExecutor.execute(() ->
        {
            //Log.e(LOG_TAG, "insert: inserting " + messages.size() + " messages");
            int result = userDao.updateUser(user);
            Log.e("Result: ",result+"--");
            callback.updateFinished(result);
            //return 1l;
        });
        //return messageDao.insert(message);
    }

    public void deleteUser(User user)//List<ProjectDTO> words
    {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //Log.e(LOG_TAG, "insert: inserting " + words.size() + " words");
                userDao.deleteUser(user);
            }
        });
    }

    public void insertBusinessInfo(final Business user, LocalCache.InsertCallback callback)
    {
        ioExecutor.execute(() ->
        {
            //Log.e(LOG_TAG, "insert: inserting " + messages.size() + " messages");
            Long result = businessDao.insertBusinessInfo(user);
            Log.e("Result: ",result+"--");
            callback.insertFinished(result);
            //return 1l;
        });
        //return messageDao.insert(message);
    }

    public void updateInv(Integer old_id, Integer new_id, LocalCache.UpdateCallback callback)
    {
        ioExecutor.execute(() ->
        {
            //Log.e(LOG_TAG, "insert: inserting " + messages.size() + " messages");
            int result = inventoryDao.updateInv(old_id,new_id);
            Log.e("Result: ",result+"--");
            callback.updateFinished(result);
            //return 1l;
        });
        //return messageDao.insert(message);
    }
    public void insertInventoryInfo(final Inventory inventory, LocalCache.InsertCallback callback)
    {
        ioExecutor.execute(() ->
        {
            //Log.e(LOG_TAG, "insert: inserting " + messages.size() + " messages");
            Long result = inventoryDao.insertInventory(inventory);
            Log.e("Result: ",result+"--");
            callback.insertFinished(result);
            //return 1l;
        });
        //return messageDao.insert(message);
    }


    public void deleteInventory(final Inventory inventory, LocalCache.DeleteCallback callback)
    {
        ioExecutor.execute(() ->
        {
            //Log.e(LOG_TAG, "insert: inserting " + messages.size() + " messages");
            int result = inventoryDao.deleteInventory(inventory);
            Log.e("Result: ",result+"--");
            callback.deleteFinished();
            //return 1l;
        });
        //return messageDao.insert(message);
    }


    public void insertInventories(List<Inventory> messages, InsertMultipleCallback callback)//List<ProjectDTO> words
    {
        ioExecutor.execute(() ->
        {
            Log.e(LOG_TAG, "insert: inserting " + messages.size() + " messages");
            Long[] result = inventoryDao.insertAll(messages);
            callback.insertFinished(result);
        });
    }

    public void deleteAllInventories(List<Inventory> words, InsertMultipleCallback callback, boolean refresh)//List<ProjectDTO> words
    {
        ioExecutor.execute(() ->
        {
            Log.e(LOG_TAG, "insert: inserting " + words.size() + " words");
            inventoryDao.deleteAll();
            Long[] result = inventoryDao.insertAll(words);
            callback.insertFinished(result);
        });
    }

    public void deleteAllInventories(DeleteCallback callback)//List<ProjectDTO> words
    {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //Log.e(LOG_TAG, "insert: inserting " + words.size() + " words");
                inventoryDao.deleteAll();
                callback.deleteFinished();
            }
        });
    }

    public void updateImagePath(String image_path,String query)//List<ProjectDTO> words
    {
        ioExecutor.execute(() ->
        {
            //Log.e(LOG_TAG, "insert: inserting " + words.size() + " words");
            inventoryDao.updateImagePath(image_path,"%"+query+"%");
            //callback.insertFinished();
        });
    }

    public DataSource.Factory<Integer, Inventory> searchInventoryOnline(Long id, String name) {
        // appending '%' so we can allow other characters to be before and after the query string

        //return inventoryDao.searchInventoryOnline(id,"%" + name.replace(' ', '%') + "%");
        String key = "%" + name.replace(' ', '%') + "%";
        Log.e("SearchKey: ",key);
        return inventoryDao.searchInventoryOnline1(key);
    }

    public LiveData<List<Biller>> getBillersForService(String service_type)
    {
        // appending '%' so we can allow other characters to be before and after the query string

        //return inventoryDao.searchInventoryOnline(id,"%" + name.replace(' ', '%') + "%");
        String key = "%" + service_type.replace(' ', '%') + "%";
        Log.e("SearchKey: ",key);
        return billerDao.getBillersForService(service_type);//searchInventoryOnline1(id,key);
    }


    public LiveData<List<Inventory>> searchInventory(Long id, String name) {
        // appending '%' so we can allow other characters to be before and after the query string
        return inventoryDao.searchInventory("%" + name.replace(' ', '%') + "%");
    }


    /*public void deleteMarkedMessages(Set<Integer> filterValues,DeleteMessagesCallback callback)//List<ProjectDTO> words
    {
        ioExecutor.transferFunds(new Runnable() {
            @Override
            public void run() {
                //Log.e(LOG_TAG, "insert: inserting " + words.size() + " words");
                int a = messageDao.deleteMarkedMessages(filterValues);
                Log.e("deleteMarkedMessages","insideIOExecuter: "+a);
                callback.deleteFinished(a);
            }
        });
        //Log.e("deleteMarkedMessages","outsideIOExecuter");
    }*/

    public interface UpdateCallback {
        /**
         * Callback method invoked when the insert operation
         * completes.
         */
        void updateFinished(int result);
    }

    public interface InsertMultipleCallback {
        /**
         * Callback method invoked when the insert operation
         * completes.
         */
        void insertFinished(Long[] result);
    }

    public interface InsertCallback {
        /**
         * Callback method invoked when the insert operation
         * completes.
         */
        void insertFinished(Long result);
    }

    public interface DeleteCallback {
        /**
         * Callback method invoked when the insert operation
         * completes.
         */
        void deleteFinished();
    }

    public interface DeleteMessagesCallback {
        /**
         * Callback method invoked when the insert operation
         * completes.
         */
        void deleteFinished(int rowsAffected);
    }
}
