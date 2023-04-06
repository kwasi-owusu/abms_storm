package com.woleapp.db;


import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woleapp.model.Inventory;
import com.woleapp.network.APIServiceClient;
import com.woleapp.network.StormAPIService;
import com.woleapp.util.Constants;
import com.woleapp.util.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * PagedList.BoundaryCallbackForInventories class to know when to trigger the Network request for more data
 *
 * @author Kaushik N Sanji
 */
public class BoundaryCallbackForInventories extends PagedList.BoundaryCallback<Inventory> implements APIServiceClient.ApiCallback {

    //Constant used for logs
    private static final String LOG_TAG = BoundaryCallbackForInventories.class.getSimpleName();
    // Constant for the Number of items in a page to be requested from the Github API
    private static final int NETWORK_PAGE_SIZE = 20; //50 //20
    private String query;
    private StormAPIService stormAPIClient;
    private LocalCache localCache;
    // Keep the last requested page. When the request is successful, increment the page number.
    private int lastRequestedPage = 1;
    // Avoid triggering multiple requests in the same time
    private boolean isRequestInProgress = false;
    // LiveData of network errors.
    private MutableLiveData<String> networkErrors = new MutableLiveData<>();
    //private MutableLiveData<Boolean> isRefresh = new MutableLiveData<>();

    boolean refresh = false;

    public BoundaryCallbackForInventories(String query, StormAPIService stormAPIClient, LocalCache localCache) {
        this.query = query;
        this.stormAPIClient = stormAPIClient;
        this.localCache = localCache;
    }

    public BoundaryCallbackForInventories(String query, StormAPIService stormAPIClient, LocalCache localCache, boolean refresh) {
        this.query = query;
        this.stormAPIClient = stormAPIClient;
        this.localCache = localCache;
        this.refresh = refresh;
    }

    public LiveData<String> getNetworkErrors() {
        return networkErrors;
    }

    /**
     * Method to request data from Github API for the given search query
     * and save the results.
     *
     * @param query The query to use for retrieving the repositories from API
     */
    private void requestAndSaveData(String query)
    {
        //Exiting if the request is in progress
        if (isRequestInProgress) return;

        //Set to true as we are starting the network request
        isRequestInProgress = true;

        //Calling the client API to retrieve the Repos for the given search query

        String userToken = SharedPrefManager.getUserToken();
        stormAPIClient.getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    if (res.code() != 200) {
                        //Update the Network error to be shown
                        networkErrors.postValue("Unable to update product list at this time.");
                        //Mark the request progress as completed
                        isRequestInProgress = false;
                        return;
                    }

                    Gson gson = new Gson();
                    JSONObject jsonObject1 = new JSONObject(gson.toJson(res.body()));

                    JSONArray jsonArray = jsonObject1.getJSONArray(Constants.PN_DATA);
                    List<Inventory> items = null;

                    if (jsonArray != null && jsonArray.length() > 0) {
                        items = gson.fromJson(jsonArray.toString(), new TypeToken<List<Inventory>>() {
                        }.getType());
                    } else {
                        items = new ArrayList<>();
                    }

                    localCache.insertInventories(items, new LocalCache.InsertMultipleCallback() {
                        @Override
                        public void insertFinished(Long[] result) {
                            //Updating the last requested page number when the request was successful
                            //and the results were inserted successfully
                            lastRequestedPage++;
                            //Marking the request progress as completed
                            isRequestInProgress = false;
                        }
                    });//,isRefresh.getValue()


                });

//        APIServiceClient.searchOrListInventories(stormAPIClient, query, lastRequestedPage, NETWORK_PAGE_SIZE, this);

    }

    /**
     * Called when zero items are returned from an initial load of the PagedList's data source.
     */
    @Override
    public void onZeroItemsLoaded() {

        Log.e(LOG_TAG, "onZeroItemsLoaded: Started");
        //requestAndSaveData(query);

    }


    /**
     * Called when the item at the end of the PagedList has been loaded, and access has
     * occurred within {@link PagedList.Config#prefetchDistance} of it.
     * <p>
     * No more data will be appended to the PagedList after this item.
     *
     * @param itemAtEnd The first item of PagedList
     */

    @Override
    public void onItemAtEndLoaded(@NonNull Inventory itemAtEnd) {
        Log.e(LOG_TAG, "onItemAtEndLoaded: Started");
        //requestAndSaveData(query);
    }

    /**
     * Callback invoked when the Search Word API Call
     * completed successfully
     *
     * @param response The List of Repos retrieved for the Search done
     */
    @Override
    public void onSuccess(Object response)//List<ProjectDTO> items
    {
        //Inserting records in the database thread
        List<Inventory> items = (List<Inventory>) response;
        localCache.insertInventories(items, new LocalCache.InsertMultipleCallback() {
            @Override
            public void insertFinished(Long[] result) {
                //Updating the last requested page number when the request was successful
                //and the results were inserted successfully
                lastRequestedPage++;
                //Marking the request progress as completed
                isRequestInProgress = false;
            }
        });//,isRefresh.getValue()
    }

    


    /**
     * Callback invoked when the Search Word API Call failed
     *
     * @param errorMessage The Error message captured for the API Call failed
     */
    @Override
    public void onError(String errorMessage) {
        //Update the Network error to be shown
        networkErrors.postValue(errorMessage);
        //Mark the request progress as completed
        isRequestInProgress = false;
    }
}
