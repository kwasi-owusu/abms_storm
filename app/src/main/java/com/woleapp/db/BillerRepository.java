package com.woleapp.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.woleapp.model.Biller;

import java.util.List;

/**
 * Repository handling the work with products and comments.
 */
public class BillerRepository {

    private static BillerRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<Biller>> mObservableProducts;

    private BillerRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.addSource(mDatabase.billerDao().getBillersForService(""),
                billers -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableProducts.postValue(billers);
                    }
                });
    }

    public static BillerRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (BillerRepository.class) {
                if (sInstance == null) {
                    sInstance = new BillerRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<Biller>> getBillers() {
        return mObservableProducts;
    }

    /*public LiveData<ProductEntity> loadProduct(final int productId) {
        return mDatabase.productDao().loadProduct(productId);
    }

    public LiveData<List<CommentEntity>> loadComments(final int productId) {
        return mDatabase.commentDao().loadComments(productId);
    }

    public LiveData<List<ProductEntity>> searchProducts(String query) {
        return mDatabase.productDao().searchAllProducts(query);
    }*/
}
