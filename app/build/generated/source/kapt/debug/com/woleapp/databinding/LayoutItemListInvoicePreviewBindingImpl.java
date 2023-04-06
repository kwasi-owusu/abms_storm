package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutItemListInvoicePreviewBindingImpl extends LayoutItemListInvoicePreviewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutItemListInvoicePreviewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private LayoutItemListInvoicePreviewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[2]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.tvAmtRatePerUnitHeader.setTag(null);
        this.tvSubtotalQtyHeader.setTag(null);
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
        if (BR.item == variableId) {
            setItem((com.woleapp.model.Inventory) variable);
        }
        else if (BR.index == variableId) {
            setIndex((java.lang.Integer) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setItem(@Nullable com.woleapp.model.Inventory Item) {
        this.mItem = Item;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }
    public void setIndex(@Nullable java.lang.Integer Index) {
        this.mIndex = Index;
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
        com.woleapp.model.Inventory item = mItem;
        java.lang.String itemProductName = null;
        java.lang.Integer itemQuantity = null;
        java.lang.String javaLangStringXItemPrice = null;
        java.lang.String javaLangStringItemQuantity = null;
        java.lang.String itemPrice = null;

        if ((dirtyFlags & 0x5L) != 0) {



                if (item != null) {
                    // read item.product_name
                    itemProductName = item.getProduct_name();
                    // read item.quantity
                    itemQuantity = item.getQuantity();
                    // read item.price
                    itemPrice = item.getPrice();
                }


                // read ("") + (item.quantity)
                javaLangStringItemQuantity = ("") + (itemQuantity);
                // read (" X ") + (item.price)
                javaLangStringXItemPrice = (" X ") + (itemPrice);
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, itemProductName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvAmtRatePerUnitHeader, javaLangStringXItemPrice);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvSubtotalQtyHeader, javaLangStringItemQuantity);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): item
        flag 1 (0x2L): index
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}