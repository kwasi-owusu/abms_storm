package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutMarketplaceReceivedOrdersBindingImpl extends LayoutMarketplaceReceivedOrdersBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.product_image, 3);
        sViewsWithIds.put(R.id.product_description, 4);
        sViewsWithIds.put(R.id.product_amount, 5);
        sViewsWithIds.put(R.id.quantity_, 6);
        sViewsWithIds.put(R.id.status_label, 7);
        sViewsWithIds.put(R.id.delivery_status, 8);
        sViewsWithIds.put(R.id.update_progress, 9);
        sViewsWithIds.put(R.id.pending, 10);
        sViewsWithIds.put(R.id.shipped, 11);
        sViewsWithIds.put(R.id.sold_by, 12);
        sViewsWithIds.put(R.id.update_status, 13);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutMarketplaceReceivedOrdersBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private LayoutMarketplaceReceivedOrdersBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[8]
            , (android.widget.RadioButton) bindings[10]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[4]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[2]
            , (android.widget.RadioButton) bindings[11]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[7]
            , (android.widget.RadioGroup) bindings[9]
            , (android.widget.Button) bindings[13]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.productName.setTag(null);
        this.quantityPurchased.setTag(null);
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
        if (BR.saleOrder == variableId) {
            setSaleOrder((com.woleapp.model.SalesOrder) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setSaleOrder(@Nullable com.woleapp.model.SalesOrder SaleOrder) {
        this.mSaleOrder = SaleOrder;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.saleOrder);
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
        java.lang.String saleOrderProductCount = null;
        java.lang.String saleOrderProductDetailsProductName = null;
        com.woleapp.model.SalesOrder saleOrder = mSaleOrder;
        com.woleapp.model.Inventory saleOrderProductDetails = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (saleOrder != null) {
                    // read saleOrder.productCount
                    saleOrderProductCount = saleOrder.getProductCount();
                    // read saleOrder.productDetails
                    saleOrderProductDetails = saleOrder.getProductDetails();
                }


                if (saleOrderProductDetails != null) {
                    // read saleOrder.productDetails.product_name
                    saleOrderProductDetailsProductName = saleOrderProductDetails.getProduct_name();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.productName, saleOrderProductDetailsProductName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.quantityPurchased, saleOrderProductCount);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): saleOrder
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}