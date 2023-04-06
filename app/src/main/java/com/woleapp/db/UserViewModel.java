package com.woleapp.db;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.woleapp.model.Business;
import com.woleapp.model.User;

/**
 * ViewModel for the {@link //SearchRepositoriesActivity} screen.
 * The ViewModel works with the {@link Repository} to get the data.
 *
 * @author Kaushik N Sanji
 */

public class UserViewModel extends ViewModel
{

    public UserViewModel(){}

    private Repository wordRepository;
    private MutableLiveData<String> queryLiveData = new MutableLiveData<>();
    //private MutableLiveData<RequestDTO> queryLiveData2 = new MutableLiveData<>();
    private MutableLiveData<Boolean> isRefresh = new MutableLiveData<>();
    //public final LiveData<Integer> maxID;

    //Applying transformation to get WordSearchResult for the given Search Query
    /*private LiveData<MessagesSearchResult> repoResult2 = Transformations.map(queryLiveData,
            inputQuery -> wordRepository.searchMessages(inputQuery)
    );*/

//    private LiveData<Integer> maxID =  wordRepository.getMaxID();


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
                        return wordRepository.searchMessages(inputQuery);
                    }
                    else
                    {
                        return wordRepository.searchMessages(inputQuery.getQuery());
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



    public UserViewModel(Repository wordRepository) {
        this.wordRepository = wordRepository;
    }



    /*public LiveData<PagedList<MessageDTO>> getRepos() {
        return repos;
    }*/



    /*public LiveData<String> getNetworkErrors() {
        return networkErrors;
    }*/

    /**
     * Search a repository based on a query string.
     */
    public void searchRepo(String queryString) {
        queryLiveData.postValue(queryString);
    }

    /*public void searchRepoR(RequestDTO query) {
        queryLiveData2.postValue(query);
    }*/


    public LiveData<Integer> doesUserExist(String email) {
        return wordRepository.doesUserExist(email);
    }

    public LiveData<User> getUser(String email, String password) {
        return wordRepository.getUser(email,password);
    }

    public LiveData<Business> getBusinessInfo(Long id)
    {
        return wordRepository.getBusinessInfo(id);
    }

    public LiveData<Integer> getMaxID() {
        return wordRepository.getMaxID();
        //private LiveData<Integer> maxID =  wordRepository.getMaxID();
    }


    public void insertUser(User user, LocalCache.InsertCallback callback)
    {
        wordRepository.insertUser(user,callback);//Long a =
        //Log.e("InsertionResult--",a+"--");
    }
//    private Long aa =  Transformations.map(queryLiveData,
//            inputQuery -> wordRepository.insertMessage(inputQuery)
//    );


    public void updateUser(User user, LocalCache.UpdateCallback callback)
    {
        wordRepository.updateUser(user,callback);//Long a =
        //Log.e("InsertionResult--",a+"--");
    }

    public void updateUser(Long id, LocalCache.UpdateCallback callback)
    {
        wordRepository.updateUser(id,callback);//Long a =
        //Log.e("InsertionResult--",a+"--");
    }

    public void updateWalletAmount(Long id, String amount, LocalCache.UpdateCallback callback)
    {
        wordRepository.updateUser(id,amount,callback);//Long a =
        //Log.e("InsertionResult--",a+"--");
    }

    public void deleteMessage(User user)
    {
        wordRepository.deleteUser(user);//Long a =
        //Log.e("InsertionResult--",a+"--");
    }

    /*public void deleteMarkedMessages(Set<Integer> filterValues, WordLocalCache.DeleteMessagesCallback callback)//List<ProjectDTO> words
    {
        wordRepository.deleteMarkedMessages(filterValues,callback);
    }*/

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

}
