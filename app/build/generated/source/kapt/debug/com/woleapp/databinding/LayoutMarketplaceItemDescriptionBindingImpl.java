package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutMarketplaceItemDescriptionBindingImpl extends LayoutMarketplaceItemDescriptionBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.product_image, 3);
        sViewsWithIds.put(R.id.amount, 4);
        sViewsWithIds.put(R.id.delivery_fee, 5);
        sViewsWithIds.put(R.id.decrease_button, 6);
        sViewsWithIds.put(R.id.quantity_, 7);
        sViewsWithIds.put(R.id.increase_button, 8);
        sViewsWithIds.put(R.id.edit_button, 9);
        sViewsWithIds.put(R.id.add_to_cart, 10);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutMarketplaceItemDescriptionBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private LayoutMarketplaceItemDescriptionBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[10]
            , (android.widget.TextView) bindings[4]
            , (android.widget.ImageButton) bindings[6]
            , (android.widget.TextView) bindings[5]
            , (android.widget.Button) bindings[9]
            , (android.widget.ImageButton) bindings[8]
            , (android.widget.TextView) bindings[2]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.TextView) bindings[1]
            , (android.widget.EditText) bindings[7]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.productDescription.setTag(null);
        this.productName.setTag(null);
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
        if (BR.inventory == variableId) {
            setInventory((com.woleapp.model.Inventory) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setInventory(@Nullable com.woleapp.model.Inventory Inventory) {
        this.mInventory = Inventory;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.inventory);
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
        com.woleapp.model.Inventory inventory = mInventory;
        java.lang.String inventoryProductName = null;
        java.lang.String inventoryDescription = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (inventory != null) {
                    // read inventory.product_name
                    inventoryProductName = inventory.getProduct_name();
                    // read inventory.description
                    inventoryDescription = inventory.getDescription();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.productDescription, inventoryDescription);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.productName, inventoryProductName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): inventory
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}