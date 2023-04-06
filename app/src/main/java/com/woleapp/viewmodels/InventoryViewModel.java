package com.woleapp.viewmodels;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.woleapp.db.LocalCache;
import com.woleapp.db.Repository;
import com.woleapp.db.SearchKey;
import com.woleapp.db.SearchResult;
import com.woleapp.model.Business;
import com.woleapp.model.Inventory;
import com.woleapp.model.User;
import com.woleapp.model.BaseResponse;
import com.woleapp.network.MerchantsApiService;
import com.woleapp.util.Event;

import java.util.Arrays;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * ViewModel for the {@link //SearchRepositoriesActivity} screen.
 * The ViewModel works with the {@link Repository} to get the data.
 *
 * @author Kaushik N Sanji
 */

public class InventoryViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<String> queryLiveData = new MutableLiveData<>();
    //private MutableLiveData<RequestDTO> queryLiveData2 = new MutableLiveData<>();
    private MutableLiveData<Boolean> isRefresh = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<SearchKey> queryLiveDataRecent = new MutableLiveData<>();
    private MutableLiveData<Event<String>> _message = new MutableLiveData<>();

    public LiveData<Event<String>> message() {
        return _message;
    }
    //public final LiveData<Integer> maxID;

    //Applying transformation to get WordSearchResult for the given Search Query
    /*private LiveData<MessagesSearchResult> repoResult2 = Transformations.map(queryLiveData,
            inputQuery -> repository.searchMessages(inputQuery)
    );*/

//    private LiveData<Integer> maxID =  repository.getMaxID();


    //Applying transformation to get WordSearchResult for the given Search Query
    /*private LiveData<MessagesSearchResult> repoResult = Transformations.map(queryLiveData2,
            new Function<RequestDTO, MessagesSearchResult>()
            {
                @Override
                public MessagesSearchResult apply(RequestDTO inputQuery)
                {
                    if(inputQuery.isRefresh())
                    {
                        queryLiveData2.getValue().setRefresh(false);
                        return repository.searchMessages(inputQuery);
                    }
                    else
                    {
                        return repository.searchMessages(inputQuery.getQuery());
                    }
                }
            }
    );*/

    //Applying transformation to get Live PagedList<Word> from the WordSearchResult
    /*private LiveData<PagedList<MessageDTO>> repos = Transformations.switchMap(repoResult,
            MessagesSearchResult::getData
    );*/

    /* everytime the value of repoResult changes, WordSearchResult::getData
     will be called, just like the map function. But WordSearchResult::getData
      returns a LiveData. So everytime that the value of the LiveData returned
      by WordSearchResult::getData changes, the value of repos will change too.
      So the value of repos will depend on changes of repoResult and changes
       of the value of WordSearchResult::getData */

    //Applying transformation to get Live Network Errors from the WordSearchResult
    /*private LiveData<String> networkErrors = Transformations.switchMap(repoResult,
            MessagesSearchResult::getNetworkErrors
    );*/

//    private LiveData<String> refreshState = Transformations.switchMap(repoResult,
//            WordSearchResult::getNetworkErrors
//    );
    //val refreshState = switchMap(repoResult, Listing<RedditPost>::refreshState)!!


    public InventoryViewModel(Repository repository) {
        this.repository = repository;
    }


    /*public LiveData<String> getNetworkErrors() {
        return networkErrors;
    }*/

    /**
     * Search a repository based on a query string.
     */
    public void searchRepo(String queryString) {
        queryLiveData.postValue(queryString);
    }


    public LiveData<Integer> doesUserExist(String email) {
        return repository.doesUserExist(email);
    }

    public LiveData<User> getUser(String email, String password) {
        return repository.getUser(email, password);
    }

    public LiveData<Business> getBusinessInfo(Long id) {
        return repository.getBusinessInfo(id);
    }

    public LiveData<Integer> getMaxID() {
        return repository.getMaxID();
        //private LiveData<Integer> maxID =  repository.getMaxID();
    }

    public void updateUser(User user, LocalCache.UpdateCallback callback) {
        repository.updateUser(user, callback);//Long a =
        //Log.e("InsertionResult--",a+"--");
    }

    public void updateUser(Long id, LocalCache.UpdateCallback callback) {
        repository.updateUser(id, callback);//Long a =
        //Log.e("InsertionResult--",a+"--");
    }

    public void updateWalletAmount(Long id, String amount, LocalCache.UpdateCallback callback) {
        repository.updateUser(id, amount, callback);//Long a =
        //Log.e("InsertionResult--",a+"--");
    }

    public void deleteMessage(User user) {
        repository.deleteUser(user);//Long a =
        //Log.e("InsertionResult--",a+"--");
    }


    public void requestFreshDataFromServer() {
        isRefresh.postValue(true);
    }

    /**
     * Get the last query value.
     */
    @Nullable
    public String lastQueryValue() {
        return queryLiveData.getValue();
    }

//    public void refresh() {
//        //repoResult.getValue().getData()?.refresh?.invoke()
//        repoResult.value?.refresh?.invoke()
//    }


//    public void refresh() {
//
//        itemDataSourceFactory.getItemLiveDataSource().getValue().invalidate();
//    }


    public void searchInventory(SearchKey query) {
        queryLiveDataRecent.postValue(query);
    }


    private LiveData<SearchResult> repoResultRecent = Transformations.map(queryLiveDataRecent,
            new Function<SearchKey, SearchResult>() {
                @Override
                public SearchResult apply(SearchKey inputQuery) {
                    if (inputQuery.isRefresh()) {
                        isLoading.setValue(true);
                        queryLiveDataRecent.getValue().setRefresh(false);
                        return repository.searchInventoryOnline(inputQuery.getId(), inputQuery.getQuery());
                    } else {
                        isLoading.setValue(true);
                        return repository.searchInventoryOnline(inputQuery.getId(), inputQuery.getQuery());
                    }
                }
            }
    );

    private LiveData<PagedList<Inventory>> reposRecent = Transformations.switchMap(repoResultRecent,
            SearchResult::getData
    );

    public LiveData<PagedList<Inventory>> getInventories() {
        return reposRecent;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private MerchantsApiService merchantsApiService;

    public void setMerchantApiService(MerchantsApiService merchantApiService) {
        this.merchantsApiService = merchantApiService;
    }

    private LocalCache localCache;

    public void setLocalCache(LocalCache localCache) {
        this.localCache = localCache;
    }

    public void refreshInventoryList(String merchantId) {
        Log.e("tag","refresh: "+merchantId);
        merchantsApiService.getAllProducts(merchantId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> isLoading.setValue(false))
                .subscribe(new SingleObserver<BaseResponse<List<Inventory>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("tag", "subscribed");
                        isLoading.setValue(true);
                    }

                    @Override
                    public void onSuccess(BaseResponse<List<Inventory>> listBaseResponse) {
                        Log.e("tag", "refresh success");
                        Log.e("tag", listBaseResponse.toString());
                        //Log.e("tag", "resp: " + listBaseResponse.toString());
                        List<Inventory> inventories = listBaseResponse.getData();
                        if (inventories != null && inventories.size() > 0) {
                            updateInventoryListInDb(inventories);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.setValue(false);
                        Log.e("tag", "message"+e.getLocalizedMessage());
                    }
                });
    }

    private void updateInventoryListInDb(List<Inventory> inventories) {
        localCache.deleteAllInventories(() -> localCache.insertInventories(inventories, result -> {
            Log.e("tag", "" + Arrays.toString(result));
            isLoading.postValue(false);
        }));
    }

    public void deleteInventory(String merchantId, Inventory inventory) {
        merchantsApiService.deleteProduct(merchantId, inventory.getProductId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        isLoading.setValue(true);
                    }

                    @Override
                    public void onSuccess(BaseResponse<Object> objectBaseResponse) {
                        deleteInventoryFromDatabase(inventory);
                    }

                    @Override
                    public void onError(Throwable e) {
                        _message.setValue(new Event<>("An error occurred while deleting the item from inventory " + e.getLocalizedMessage()));
                        isLoading.setValue(false);
                    }
                });
    }

    private void deleteInventoryFromDatabase(Inventory inventory) {
        localCache.deleteInventory(inventory, () -> isLoading.postValue(false));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.e("tag", "cleared");
    }
}
