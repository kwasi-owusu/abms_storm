package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutQuickTransactionBindingImpl extends LayoutQuickTransactionBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvTitle, 1);
        sViewsWithIds.put(R.id.etProductName, 2);
        sViewsWithIds.put(R.id.tvPrice, 3);
        sViewsWithIds.put(R.id.etPrice, 4);
        sViewsWithIds.put(R.id.tvQuantity, 5);
        sViewsWithIds.put(R.id.spnQuantity, 6);
        sViewsWithIds.put(R.id.edt_customer_name, 7);
        sViewsWithIds.put(R.id.etNote, 8);
        sViewsWithIds.put(R.id.cardToggle, 9);
        sViewsWithIds.put(R.id.roleSwitch, 10);
        sViewsWithIds.put(R.id.tvSave, 11);
        sViewsWithIds.put(R.id.btn_continue, 12);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutQuickTransactionBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }
    private LayoutQuickTransactionBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[12]
            , (androidx.cardview.widget.CardView) bindings[9]
            , (android.widget.AutoCompleteTextView) bindings[7]
            , (android.widget.EditText) bindings[8]
            , (android.widget.EditText) bindings[4]
            , (android.widget.AutoCompleteTextView) bindings[2]
            , (belka.us.androidtoggleswitch.widgets.ToggleSwitch) bindings[10]
            , (android.widget.EditText) bindings[6]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[1]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
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