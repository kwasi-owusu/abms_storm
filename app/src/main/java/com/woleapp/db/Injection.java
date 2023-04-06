package com.woleapp.db;

import android.content.Context;
import androidx.annotation.NonNull;
import com.woleapp.network.APIServiceClient;
import com.woleapp.network.StormAPIClient;
import com.woleapp.util.SharedPrefManager;
//import com.gph.geeksperhour.network.APIServiceClient;
//import com.gph.geeksperhour.network.WordLocalCache;

import java.util.concurrent.Executors;

/**
 * Class that handles object creation.
 * <p>
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 *
 * @author Kaushik N Sanji
 */
public class Injection {

    /**
     * Creates an instance of {@link LocalCache} based on the database DAO.
     */
    @NonNull
    public static LocalCache provideCache(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        return new LocalCache(database.userDao(),database.businessDao(), Executors.newSingleThreadExecutor());
    }

    @NonNull
    public static LocalCache provideCacheForBusinessInfo(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        return new LocalCache(database.businessDao(), Executors.newSingleThreadExecutor());
    }

    @NonNull
    public static LocalCache provideCacheForInventory(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        return new LocalCache(database.inventoryDao(), Executors.newSingleThreadExecutor());
    }

    /*@NonNull
    private static LocalCache provideCacheForMessages(Context context,int jobId) {
        AppDatabase wordRoomDatabase = AppDatabase.getInstance(context);
        return new LocalCache(wordRoomDatabase.messageDao(), Executors.newSingleThreadExecutor(),jobId);
    }*/

    /**
     * Creates an instance of {@link Repository} based on the
     * {@link .APIService} and a
     * {@link LocalCache}
     */
    @NonNull
    private static Repository provideGithubRepository(Context context)
    {
        return new Repository(StormAPIClient.create(context), provideCache(context));
    }

    /**
     * Provides the {@link ViewModelFactory} that is then used to get a reference to
     * {@link .SearchRepositoriesViewModel} objects.
     */
    @NonNull
    public static ViewModelFactory provideViewModelFactory(Context context)
    {
        return new ViewModelFactory(provideGithubRepository(context));
    }

    @NonNull
    public static ViewModelFactory provideViewModelFactoryForInventory(Context context)
    {
        return new ViewModelFactory(new Repository(provideCacheForInventory(context)));
    }

    @NonNull
    public static ViewModelFactory provideViewModelFactoryForInventoryOnline(Context context)
    {
        return new ViewModelFactory(new Repository(StormAPIClient.create(context), provideCacheForInventory(context)));
    }

    /*@NonNull
    private static WordRepository provideGithubRepositoryForMessages(Context context,int jobId)
    {
        return new WordRepository(APIServiceClient.create(context), provideCacheForMessages(context,jobId),jobId);
    }*/

    /**
     * Provides the {@link ViewModelFactory} that is then used to get a reference to
     * {@link .SearchRepositoriesViewModel} objects.
     */


    /*@NonNull
    public static ViewModelFactory provideViewModelFactoryForMessages(Context context,int jobId)
    {
        return new ViewModelFactory(provideGithubRepositoryForMessages(context, jobId));
    }*/
}
