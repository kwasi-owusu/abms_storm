package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutTransactionListBindingImpl extends LayoutTransactionListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linearHeader, 2);
        sViewsWithIds.put(R.id.tvTitle, 3);
        sViewsWithIds.put(R.id.channel_spinner, 4);
        sViewsWithIds.put(R.id.search_by_spinner, 5);
        sViewsWithIds.put(R.id.etSearch, 6);
        sViewsWithIds.put(R.id.table_header, 7);
        sViewsWithIds.put(R.id.rvInventories, 8);
        sViewsWithIds.put(R.id.tvNoResults, 9);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutTransactionListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private LayoutTransactionListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.wang.avi.AVLoadingIndicatorView) bindings[1]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[4]
            , (android.widget.EditText) bindings[6]
            , (android.widget.LinearLayout) bindings[2]
            , (androidx.recyclerview.widget.RecyclerView) bindings[8]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[5]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[3]
            );
        this.avloadingIndicatorView.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
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
        if (BR.isLoading == variableId) {
            setIsLoading((java.lang.Boolean) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setIsLoading(@Nullable java.lang.Boolean IsLoading) {
        this.mIsLoading = IsLoading;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.isLoading);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
        boolean androidxDatabindingViewDataBindingSafeUnboxIsLoading = false;
        int androidxDatabindingViewDataBindingSafeUnboxIsLoadingViewVISIBLEViewGONE = 0;
        java.lang.Boolean isLoading = mIsLoading;

        if ((dirtyFlags & 0x3L) != 0) {



                // read androidx.databinding.ViewDataBinding.safeUnbox(isLoading)
                androidxDatabindingViewDataBindingSafeUnboxIsLoading = androidx.databinding.ViewDataBinding.safeUnbox(isLoading);
            if((dirtyFlags & 0x3L) != 0) {
                if(androidxDatabindingViewDataBindingSafeUnboxIsLoading) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }


                // read androidx.databinding.ViewDataBinding.safeUnbox(isLoading) ? View.VISIBLE : View.GONE
                androidxDatabindingViewDataBindingSafeUnboxIsLoadingViewVISIBLEViewGONE = ((androidxDatabindingViewDataBindingSafeUnboxIsLoading) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            this.avloadingIndicatorView.setVisibility(androidxDatabindingViewDataBindingSafeUnboxIsLoadingViewVISIBLEViewGONE);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): isLoading
        flag 1 (0x2L): null
        flag 2 (0x3L): androidx.databinding.ViewDataBinding.safeUnbox(isLoading) ? View.VISIBLE : View.GONE
        flag 3 (0x4L): androidx.databinding.ViewDataBinding.safeUnbox(isLoading) ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}