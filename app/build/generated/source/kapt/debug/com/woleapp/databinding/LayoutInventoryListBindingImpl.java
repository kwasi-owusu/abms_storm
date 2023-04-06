package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutInventoryListBindingImpl extends LayoutInventoryListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linearHeader, 3);
        sViewsWithIds.put(R.id.tvTitle, 4);
        sViewsWithIds.put(R.id.etSearch, 5);
        sViewsWithIds.put(R.id.rvInventories, 6);
        sViewsWithIds.put(R.id.tvNoResults, 7);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutInventoryListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private LayoutInventoryListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.EditText) bindings[5]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.ProgressBar) bindings[2]
            , (androidx.recyclerview.widget.RecyclerView) bindings[6]
            , (androidx.swiperefreshlayout.widget.SwipeRefreshLayout) bindings[1]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[4]
            );
        this.loadingProgress.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.swipeRefresh.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.transactionsViewModel == variableId) {
            setTransactionsViewModel((com.woleapp.viewmodels.TransactionsViewModel) variable);
        }
        else if (BR.isLoading == variableId) {
            setIsLoading((java.lang.Boolean) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setTransactionsViewModel(@Nullable com.woleapp.viewmodels.TransactionsViewModel TransactionsViewModel) {
        this.mTransactionsViewModel = TransactionsViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.transactionsViewModel);
        super.requestRebind();
    }
    public void setIsLoading(@Nullable java.lang.Boolean IsLoading) {
        this.mIsLoading = IsLoading;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeTransactionsViewModelLoadState((androidx.lifecycle.MutableLiveData<com.woleapp.db.LoadState>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeTransactionsViewModelLoadState(androidx.lifecycle.MutableLiveData<com.woleapp.db.LoadState> TransactionsViewModelLoadState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        androidx.lifecycle.MutableLiveData<com.woleapp.db.LoadState> transactionsViewModelLoadState = null;
        com.woleapp.db.LoadState transactionsViewModelLoadStateGetValue = null;
        com.woleapp.viewmodels.TransactionsViewModel transactionsViewModel = mTransactionsViewModel;

        if ((dirtyFlags & 0xbL) != 0) {



                if (transactionsViewModel != null) {
                    // read transactionsViewModel.loadState
                    transactionsViewModelLoadState = transactionsViewModel.getLoadState();
                }
                updateLiveDataRegistration(0, transactionsViewModelLoadState);


                if (transactionsViewModelLoadState != null) {
                    // read transactionsViewModel.loadState.getValue()
                    transactionsViewModelLoadStateGetValue = transactionsViewModelLoadState.getValue();
                }
        }
        // batch finished
        if ((dirtyFlags & 0xbL) != 0) {
            // api target 1

            com.woleapp.adapters.DataBindingTintColor.loadingMoreProgress(this.loadingProgress, transactionsViewModelLoadStateGetValue);
            com.woleapp.adapters.DataBindingTintColor.transactionRefresh(this.swipeRefresh, transactionsViewModelLoadStateGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): transactionsViewModel.loadState
        flag 1 (0x2L): transactionsViewModel
        flag 2 (0x3L): isLoading
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}