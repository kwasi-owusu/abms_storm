package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutItemListInventoryBindingImpl extends LayoutItemListInventoryBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ivProduct, 10);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutItemListInventoryBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private LayoutItemListInventoryBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageButton) bindings[9]
            , (android.widget.TextView) bindings[2]
            , (android.widget.ImageButton) bindings[8]
            , (android.widget.Button) bindings[7]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[5]
            );
        this.btnDelete.setTag(null);
        this.btnDetails.setTag(null);
        this.btnEdit.setTag(null);
        this.btnSell.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.relativeDetails.setTag(null);
        this.tvCategory.setTag(null);
        this.tvName.setTag(null);
        this.tvPrice.setTag(null);
        this.tvQuantity.setTag(null);
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
        if (BR.isOpen == variableId) {
            setIsOpen((java.lang.Boolean) variable);
        }
        else if (BR.inventory == variableId) {
            setInventory((com.woleapp.model.Inventory) variable);
        }
        else if (BR.onClickAttachment == variableId) {
            setOnClickAttachment((com.woleapp.util.OnItemClickListener) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setIsOpen(@Nullable java.lang.Boolean IsOpen) {
        this.mIsOpen = IsOpen;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.isOpen);
        super.requestRebind();
    }
    public void setInventory(@Nullable com.woleapp.model.Inventory Inventory) {
        this.mInventory = Inventory;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.inventory);
        super.requestRebind();
    }
    public void setOnClickAttachment(@Nullable com.woleapp.util.OnItemClickListener OnClickAttachment) {
        this.mOnClickAttachment = OnClickAttachment;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.onClickAttachment);
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
        boolean androidxDatabindingViewDataBindingSafeUnboxIsOpen = false;
        java.lang.Boolean isOpen = mIsOpen;
        java.lang.String inventoryPrice = null;
        java.lang.Integer inventoryQuantity = null;
        android.graphics.drawable.Drawable isOpenBtnDetailsAndroidDrawablePullUpRedBtnDetailsAndroidDrawableDropDownRed = null;
        com.woleapp.model.Inventory inventory = mInventory;
        com.woleapp.util.OnItemClickListener onClickAttachment = mOnClickAttachment;
        java.lang.String tvPriceAndroidStringLblCurrencyNairaInventoryPrice = null;
        java.lang.String javaLangStringQUANTITYJavaLangStringInventoryQuantity = null;
        java.lang.String inventoryProductName = null;
        java.lang.String inventoryDescription = null;

        if ((dirtyFlags & 0x9L) != 0) {



                // read androidx.databinding.ViewDataBinding.safeUnbox(isOpen)
                androidxDatabindingViewDataBindingSafeUnboxIsOpen = androidx.databinding.ViewDataBinding.safeUnbox(isOpen);
            if((dirtyFlags & 0x9L) != 0) {
                if(androidxDatabindingViewDataBindingSafeUnboxIsOpen) {
                        dirtyFlags |= 0x20L;
                }
                else {
                        dirtyFlags |= 0x10L;
                }
            }


                // read androidx.databinding.ViewDataBinding.safeUnbox(isOpen) ? @android:drawable/pull_up_red : @android:drawable/drop_down_red
                isOpenBtnDetailsAndroidDrawablePullUpRedBtnDetailsAndroidDrawableDropDownRed = ((androidxDatabindingViewDataBindingSafeUnboxIsOpen) ? (androidx.appcompat.content.res.AppCompatResources.getDrawable(btnDetails.getContext(), R.drawable.pull_up_red)) : (androidx.appcompat.content.res.AppCompatResources.getDrawable(btnDetails.getContext(), R.drawable.drop_down_red)));
        }
        if ((dirtyFlags & 0xaL) != 0) {



                if (inventory != null) {
                    // read inventory.price
                    inventoryPrice = inventory.getPrice();
                    // read inventory.quantity
                    inventoryQuantity = inventory.getQuantity();
                    // read inventory.product_name
                    inventoryProductName = inventory.getProduct_name();
                    // read inventory.description
                    inventoryDescription = inventory.getDescription();
                }


                // read (@android:string/lbl_currency_naira) + (inventory.price)
                tvPriceAndroidStringLblCurrencyNairaInventoryPrice = (tvPrice.getResources().getString(R.string.lbl_currency_naira)) + (inventoryPrice);
                // read (("QUANTITY:") + (" ")) + (inventory.quantity)
                javaLangStringQUANTITYJavaLangStringInventoryQuantity = (("QUANTITY:") + (" ")) + (inventoryQuantity);
        }
        if ((dirtyFlags & 0xcL) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            com.woleapp.adapters.ImageDataBindingAdapter.setClickListener(this.btnDelete, onClickAttachment);
            com.woleapp.adapters.ImageDataBindingAdapter.setClickListener(this.btnEdit, onClickAttachment);
            com.woleapp.adapters.ImageDataBindingAdapter.setClickListener(this.btnSell, onClickAttachment);
        }
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setDrawableRight(this.btnDetails, isOpenBtnDetailsAndroidDrawablePullUpRedBtnDetailsAndroidDrawableDropDownRed);
            com.woleapp.adapters.DataBindingAnimation1.setVisibility(this.relativeDetails, isOpen);
        }
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvCategory, inventoryDescription);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvName, inventoryProductName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvPrice, tvPriceAndroidStringLblCurrencyNairaInventoryPrice);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvQuantity, javaLangStringQUANTITYJavaLangStringInventoryQuantity);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): isOpen
        flag 1 (0x2L): inventory
        flag 2 (0x3L): onClickAttachment
        flag 3 (0x4L): null
        flag 4 (0x5L): androidx.databinding.ViewDataBinding.safeUnbox(isOpen) ? @android:drawable/pull_up_red : @android:drawable/drop_down_red
        flag 5 (0x6L): androidx.databinding.ViewDataBinding.safeUnbox(isOpen) ? @android:drawable/pull_up_red : @android:drawable/drop_down_red
    flag mapping end*/
    //end
}