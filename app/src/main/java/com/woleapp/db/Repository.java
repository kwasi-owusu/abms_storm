package com.woleapp.db;


import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.woleapp.model.Business;
import com.woleapp.model.Inventory;
import com.woleapp.model.User;
import com.woleapp.network.APIService;
import com.woleapp.network.StormAPIClient;
import com.woleapp.network.StormAPIService;
import com.woleapp.util.SharedPrefManager;

import java.util.List;

/**
 * Repository class that works with local and remote data sources.
 *
 * @author Kaushik N Sanji
 */
public class Repository {
    //Constant used for logs
    private static final String LOG_TAG = Repository.class.getSimpleName();
    //Constant for the number of items to be loaded at once from the DataSource by the PagedList
    private static final int DATABASE_PAGE_SIZE = 20;
    private StormAPIService apiService;
    private LocalCache localCache;
    int jobId = 0;


    public Repository(LocalCache localCache) {
        this.localCache = localCache;
    }

    public Repository(StormAPIService apiService,LocalCache localCache) {
        this.apiService = apiService;
        this.localCache = localCache;
    }

    public Repository(StormAPIService apiService, LocalCache localCache, int jobId)
    {
        this.apiService = apiService;
        this.localCache = localCache;
        this.jobId = jobId;
    }

    public LiveData<List<Inventory>> searchInventories(Long id,String query) {
        return localCache.searchInventory(id,query);
    }
    public SearchResult searchInventoryOnline(Long id, String query)
    {
        Log.e(LOG_TAG, "search: New query: " + query);

        // Get data source factory from the local cache
        DataSource.Factory<Integer, Inventory> reposByName = localCache.searchInventoryOnline(id,query);

        // Construct the boundary callback
        BoundaryCallbackForInventories boundaryCallback = new BoundaryCallbackForInventories(query, apiService, localCache);
        LiveData<String> networkErrors = boundaryCallback.getNetworkErrors();
        //LiveData<Boolean> isRefresh = boundaryCallback.getIsRefresh();
        // Set the Page size for the Paged list
        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(DATABASE_PAGE_SIZE)
                .build();

        // Get the Live Paged list
        LiveData<PagedList<Inventory>> data = new LivePagedListBuilder<>(reposByName, pagedConfig)

                /*Uncomment the line below if the data is to be fetched from the server*/
                .setBoundaryCallback(boundaryCallback)
                .build();

        // Get the Search result with the network errors exposed by the boundary callback
        return new SearchResult(data, networkErrors);
    }

    /**
     * Search repositories whose names match the query.
     */
    /*public WordSearchResult search(String query)
    {
        Log.e(LOG_TAG, "search: New query: " + query);

        // Get data source factory from the local cache
        DataSource.Factory<Integer, ProjectDTO> reposByName = localCache.reposByName(query);

        // Construct the boundary callback
        WordBoundaryCallback boundaryCallback = new WordBoundaryCallback(query, apiService, localCache);
        LiveData<String> networkErrors = boundaryCallback.getNetworkErrors();
        //LiveData<Boolean> isRefresh = boundaryCallback.getIsRefresh();
        // Set the Page size for the Paged list
        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(DATABASE_PAGE_SIZE)
                .build();

        // Get the Live Paged list
        LiveData<PagedList<ProjectDTO>> data = new LivePagedListBuilder<>(reposByName, pagedConfig)
                .setBoundaryCallback(boundaryCallback)
                .build();

        // Get the Search result with the network errors exposed by the boundary callback
        return new WordSearchResult(data, networkErrors);
    }*/


    /*public WordSearchResult search(RequestDTO query)
    {
        Log.e(LOG_TAG, "search: New query: " + query);
//        if(query.isRefresh())
//        {
//
//        }

        localCache.deleteProjects(new WordLocalCache.DeleteCallback() {
            @Override
            public void deleteFinished() {
                //Updating the last requested page number when the request was successful
                //and the results were inserted successfully
                query.setRefresh(false);
                //search(query.getQuery());
                return;
            }
        });
//        else
//        {
//// Construct the boundary callback
//            WordBoundaryCallback boundaryCallback = new WordBoundaryCallback(query.getQuery(), apiService, localCache);
//            LiveData<String> networkErrors = boundaryCallback.getNetworkErrors();
//            //LiveData<Boolean> isRefresh = boundaryCallback.getIsRefresh();
//            // Set the Page size for the Paged list
//            PagedList.Config pagedConfig = new PagedList.Config.Builder()
//                    .setPageSize(DATABASE_PAGE_SIZE)
//                    .build();
//
//            // Get the Live Paged list
//            LiveData<PagedList<ProjectDTO>> data = new LivePagedListBuilder<>(reposByName, pagedConfig)
//                    .setBoundaryCallback(boundaryCallback)
//                    .build();
//
//            // Get the Search result with the network errors exposed by the boundary callback
//            return new WordSearchResult(data, networkErrors);
//        }

        // Get data source factory from the local cache
        DataSource.Factory<Integer, ProjectDTO> reposByName = localCache.reposByName(query.getQuery());
        // Construct the boundary callback
        WordBoundaryCallback boundaryCallback = new WordBoundaryCallback(query.getQuery(), apiService, localCache);
        LiveData<String> networkErrors = boundaryCallback.getNetworkErrors();
        //LiveData<Boolean> isRefresh = boundaryCallback.getIsRefresh();
        // Set the Page size for the Paged list
        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(DATABASE_PAGE_SIZE)
                .build();

        // Get the Live Paged list
        LiveData<PagedList<ProjectDTO>> data = new LivePagedListBuilder<>(reposByName, pagedConfig)
                .setBoundaryCallback(boundaryCallback)
                .build();

        // Get the Search result with the network errors exposed by the boundary callback
        return new WordSearchResult(data, networkErrors);

    }*/

    public LiveData<User> getUser(String email, String password) {
        return localCache.getUser(email,password);
    }

    public LiveData<Business> getBusinessInfo(Long id)
    {
        return localCache.getBusinessInfo(id);
    }

    /*public LiveData<Business> getBusinessInfo(String email, String password) {
        return localCache.getUser(email,password);
    }*/

    public LiveData<Integer> doesUserExist(String email) {
        return localCache.doesUserExist(email);
    }

    public LiveData<Integer> getMaxID()
    {
        return localCache.getMaxID();
    }

    public void insertUser(User user, LocalCache.InsertCallback callback)
    {
        localCache.insertUser(user,callback);//return
    }

    public void deleteUser(User user)
    {
        localCache.deleteUser(user);//return
    }

    public void updateUser(User user, LocalCache.UpdateCallback callback)
    {
        localCache.updateUser(user,callback);//return
    }

    public void updateUser(Long id,LocalCache.UpdateCallback callback)
    {
        localCache.updateUser(id,callback);//return
    }

    public void updateUser(Long id,String amount, LocalCache.UpdateCallback callback)
    {
        localCache.updateWalletAmt(id,amount,callback);//return
    }

    /*public void deleteMarkedMessages(Set<Integer> filterValues, WordLocalCache.DeleteMessagesCallback callback)//List<ProjectDTO> words
    {
        localCache.deleteMarkedMessages(filterValues,callback);
    }*/


    /*public MessagesSearchResult searchMessages(String query)*//*,int jobId*//*
    {
        Log.e(LOG_TAG, "search: New query: " + query);

        // Get data source factory from the local cache
        DataSource.Factory<Integer, MessageDTO> reposByName = localCache.getMessages(jobId);//query

        // Construct the boundary callback
        MessagesBoundaryCallback boundaryCallback = new MessagesBoundaryCallback(query, apiService, localCache,jobId);
        LiveData<String> networkErrors = boundaryCallback.getNetworkErrors();
        //LiveData<Boolean> isRefresh = boundaryCallback.getIsRefresh();
        // Set the Page size for the Paged list
        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(DATABASE_PAGE_SIZE)
                .build();

        // Get the Live Paged list
        LiveData<PagedList<MessageDTO>> data = new LivePagedListBuilder<>(reposByName, pagedConfig)
                .setBoundaryCallback(boundaryCallback)
                .build();

        // Get the Search result with the network errors exposed by the boundary callback
        return new MessagesSearchResult(data, networkErrors);
    }*/



    /*public MessagesSearchResult searchMessages(RequestDTO query)*//*,int jobId*//*
    {
        Log.e(LOG_TAG, "search: New query: " + query);

//        if(query.isRefresh())
//        {
//
//        }

        localCache.deleteMessages(new WordLocalCache.DeleteCallback() {
            @Override
            public void deleteFinished() {
                //Updating the last requested page number when the request was successful
                //and the results were inserted successfully
                query.setRefresh(false);
                //search(query.getQuery());
                return;
            }
        });
//        else
//        {
//// Construct the boundary callback
//            WordBoundaryCallback boundaryCallback = new WordBoundaryCallback(query.getQuery(), apiService, localCache);
//            LiveData<String> networkErrors = boundaryCallback.getNetworkErrors();
//            //LiveData<Boolean> isRefresh = boundaryCallback.getIsRefresh();
//            // Set the Page size for the Paged list
//            PagedList.Config pagedConfig = new PagedList.Config.Builder()
//                    .setPageSize(DATABASE_PAGE_SIZE)
//                    .build();
//
//            // Get the Live Paged list
//            LiveData<PagedList<ProjectDTO>> data = new LivePagedListBuilder<>(reposByName, pagedConfig)
//                    .setBoundaryCallback(boundaryCallback)
//                    .build();
//
//            // Get the Search result with the network errors exposed by the boundary callback
//            return new WordSearchResult(data, networkErrors);
//        }

        // Get data source factory from the local cache
        DataSource.Factory<Integer, MessageDTO> reposByName = localCache.getMessages(jobId);//query.getQuery()
        // Construct the boundary callback
        MessagesBoundaryCallback boundaryCallback = new MessagesBoundaryCallback(query.getQuery(), apiService, localCache,jobId);
        LiveData<String> networkErrors = boundaryCallback.getNetworkErrors();
        //LiveData<Boolean> isRefresh = boundaryCallback.getIsRefresh();
        // Set the Page size for the Paged list
        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(DATABASE_PAGE_SIZE)
                .build();

        // Get the Live Paged list
        LiveData<PagedList<MessageDTO>> data = new LivePagedListBuilder<>(reposByName, pagedConfig)
                .setBoundaryCallback(boundaryCallback)
                .build();

        // Get the Search result with the network errors exposed by the boundary callback
        return new MessagesSearchResult(data, networkErrors);

    }*/



/*    public WordSearchResult searchR(RequestDTO query) {
        Log.e(LOG_TAG, "search: New query: " + query);

        // Get data source factory from the local cache
        DataSource.Factory<Integer, ProjectDTO> reposByName = localCache.reposByName(query.getQuery());

        localCache.deleteProjects(new WordLocalCache.DeleteCallback() {
            @Override
            public WordSearchResult deleteFinished() {
                //Updating the last requested page number when the request was successful
                //and the results were inserted successfully
                query.setRefresh(false);
                return search(query.getQuery());
            }
        });
//        else
//        {
//// Construct the boundary callback
//            WordBoundaryCallback boundaryCallback = new WordBoundaryCallback(query.getQuery(), apiService, localCache);
//            LiveData<String> networkErrors = boundaryCallback.getNetworkErrors();
//            //LiveData<Boolean> isRefresh = boundaryCallback.getIsRefresh();
//            // Set the Page size for the Paged list
//            PagedList.Config pagedConfig = new PagedList.Config.Builder()
//                    .setPageSize(DATABASE_PAGE_SIZE)
//                    .build();
//
//            // Get the Live Paged list
//            LiveData<PagedList<ProjectDTO>> data = new LivePagedListBuilder<>(reposByName, pagedConfig)
//                    .setBoundaryCallback(boundaryCallback)
//                    .build();
//
//            // Get the Search result with the network errors exposed by the boundary callback
//            return new WordSearchResult(data, networkErrors);
//        }

    }*/

}
