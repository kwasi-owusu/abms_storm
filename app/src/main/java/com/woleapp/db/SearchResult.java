package com.woleapp.db;


import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import com.woleapp.model.Inventory;

/**
 * WordSearchResult from a search, which contains LiveData<PagedList<Word>> holding query data,
 * and a LiveData<String> of network error state.
 *
 * @author Kaushik N Sanji
 */
public class SearchResult {
    //LiveData for Search Results
    private final LiveData<PagedList<Inventory>> data;
    //LiveData for the Network Errors
    private final LiveData<String> networkErrors;
    //private final LiveData<Boolean> isRefresh;

    public SearchResult(LiveData<PagedList<Inventory>> data, LiveData<String> networkErrors) {
        this.data = data;
        this.networkErrors = networkErrors;

    }

//    public WordSearchResult(LiveData<PagedList<ProjectDTO>> data, LiveData<String> networkErrors,LiveData<Boolean> isRefresh)
//    {
//        this.data = data;
//        this.networkErrors = networkErrors;
//        this.isRefresh = isRefresh;
//    }

    public LiveData<PagedList<Inventory>> getData() {
        return data;
    }

    public LiveData<String> getNetworkErrors() {
        return networkErrors;
    }

//    public LiveData<Boolean> getIsRefresh() {
//        return isRefresh;
//    }
}
