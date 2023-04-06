package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutCashInBindingImpl extends LayoutCashInBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvTitle, 1);
        sViewsWithIds.put(R.id.tvAmount, 2);
        sViewsWithIds.put(R.id.spnBank, 3);
        sViewsWithIds.put(R.id.spnIdType, 4);
        sViewsWithIds.put(R.id.etIDNo, 5);
        sViewsWithIds.put(R.id.etAccountNo, 6);
        sViewsWithIds.put(R.id.etName, 7);
        sViewsWithIds.put(R.id.etFee, 8);
        sViewsWithIds.put(R.id.etdepositor, 9);
        sViewsWithIds.put(R.id.tvConvinienceFee, 10);
        sViewsWithIds.put(R.id.tvSign, 11);
        sViewsWithIds.put(R.id.tvRetake, 12);
        sViewsWithIds.put(R.id.linearCanvas, 13);
        sViewsWithIds.put(R.id.btn_continue, 14);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutCashInBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private LayoutCashInBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[14]
            , (android.widget.EditText) bindings[6]
            , (android.widget.EditText) bindings[8]
            , (android.widget.EditText) bindings[5]
            , (android.widget.EditText) bindings[7]
            , (android.widget.EditText) bindings[9]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.ScrollView) bindings[0]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[3]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[4]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[1]
            );
        this.scrollView.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
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
            return variableSet;
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
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}