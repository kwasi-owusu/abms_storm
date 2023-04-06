package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutAddInventoryBindingImpl extends LayoutAddInventoryBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvTitle, 1);
        sViewsWithIds.put(R.id.ivProduct, 2);
        sViewsWithIds.put(R.id.btnUpload, 3);
        sViewsWithIds.put(R.id.etProductName, 4);
        sViewsWithIds.put(R.id.spnCategory, 5);
        sViewsWithIds.put(R.id.etPrice, 6);
        sViewsWithIds.put(R.id.tvQuantity, 7);
        sViewsWithIds.put(R.id.spnQuantity, 8);
        sViewsWithIds.put(R.id.etSize, 9);
        sViewsWithIds.put(R.id.spnColor, 10);
        sViewsWithIds.put(R.id.etNote, 11);
        sViewsWithIds.put(R.id.cardToggle, 12);
        sViewsWithIds.put(R.id.btn_continue, 13);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutAddInventoryBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private LayoutAddInventoryBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[13]
            , (android.widget.Button) bindings[3]
            , (androidx.cardview.widget.CardView) bindings[12]
            , (android.widget.EditText) bindings[11]
            , (android.widget.EditText) bindings[6]
            , (android.widget.EditText) bindings[4]
            , (android.widget.EditText) bindings[9]
            , (android.widget.ImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[5]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[10]
            , (android.widget.EditText) bindings[8]
            , (android.widget.TextView) bindings[7]
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
                mDirtyFlags = 0x4L;
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
        if (BR.isOpen == variableId) {
            setIsOpen((java.lang.Boolean) variable);
        }
        else if (BR.inventory == variableId) {
            setInventory((com.woleapp.model.Inventory) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setIsOpen(@Nullable java.lang.Boolean IsOpen) {
        this.mIsOpen = IsOpen;
    }
    public void setInventory(@Nullable com.woleapp.model.Inventory Inventory) {
        this.mInventory = Inventory;
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
        flag 0 (0x1L): isOpen
        flag 1 (0x2L): inventory
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}