package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutItemListTransactionsBindingImpl extends LayoutItemListTransactionsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.cardCalendar, 10);
        sViewsWithIds.put(R.id.tvMonth, 11);
        sViewsWithIds.put(R.id.tvDate, 12);
        sViewsWithIds.put(R.id.tvYear, 13);
        sViewsWithIds.put(R.id.tvCategory, 14);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutItemListTransactionsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private LayoutItemListTransactionsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[5]
            , (androidx.cardview.widget.CardView) bindings[10]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[6]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[13]
            );
        this.btnDetails.setTag(null);
        this.linearDate.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.relativeDetails.setTag(null);
        this.tvAmount.setTag(null);
        this.tvColor.setTag(null);
        this.tvName.setTag(null);
        this.tvQuantity.setTag(null);
        this.tvSize.setTag(null);
        this.tvStatus.setTag(null);
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
            setInventory((com.woleapp.model.Transactions) variable);
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
    public void setInventory(@Nullable com.woleapp.model.Transactions Inventory) {
        this.mInventory = Inventory;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
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
        java.lang.String javaLangStringReferenceNumberInventoryReferenceNoEtranzact = null;
        java.lang.String inventoryDestinationAccount = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxIsOpen = false;
        java.lang.Boolean isOpen = mIsOpen;
        java.lang.String javaLangStringBeneficiaryNameInventoryBeneficiaryName = null;
        com.woleapp.model.Transactions inventory = mInventory;
        java.lang.String inventoryTransactionType = null;
        java.lang.String inventoryTransactionDate = null;
        java.lang.String inventoryReferenceNoEtranzact = null;
        android.graphics.drawable.Drawable isOpenBtnDetailsAndroidDrawableArrowUpBtnDetailsAndroidDrawableArrowDownBigger = null;
        java.lang.String inventoryStatus = null;
        java.lang.String javaLangStringBeneficiaryAccountNoInventoryDestinationAccount = null;
        java.lang.String inventoryBeneficiaryName = null;
        java.lang.Double inventoryAmount = null;

        if ((dirtyFlags & 0x5L) != 0) {



                // read androidx.databinding.ViewDataBinding.safeUnbox(isOpen)
                androidxDatabindingViewDataBindingSafeUnboxIsOpen = androidx.databinding.ViewDataBinding.safeUnbox(isOpen);
            if((dirtyFlags & 0x5L) != 0) {
                if(androidxDatabindingViewDataBindingSafeUnboxIsOpen) {
                        dirtyFlags |= 0x10L;
                }
                else {
                        dirtyFlags |= 0x8L;
                }
            }


                // read androidx.databinding.ViewDataBinding.safeUnbox(isOpen) ? @android:drawable/arrow_up : @android:drawable/arrow_down_bigger
                isOpenBtnDetailsAndroidDrawableArrowUpBtnDetailsAndroidDrawableArrowDownBigger = ((androidxDatabindingViewDataBindingSafeUnboxIsOpen) ? (androidx.appcompat.content.res.AppCompatResources.getDrawable(btnDetails.getContext(), R.drawable.arrow_up)) : (androidx.appcompat.content.res.AppCompatResources.getDrawable(btnDetails.getContext(), R.drawable.arrow_down_bigger)));
        }
        if ((dirtyFlags & 0x6L) != 0) {



                if (inventory != null) {
                    // read inventory.destination_account
                    inventoryDestinationAccount = inventory.getDestination_account();
                    // read inventory.transaction_type
                    inventoryTransactionType = inventory.getTransaction_type();
                    // read inventory.transaction_date
                    inventoryTransactionDate = inventory.getTransaction_date();
                    // read inventory.reference_no_Etranzact
                    inventoryReferenceNoEtranzact = inventory.getReference_no_Etranzact();
                    // read inventory.status
                    inventoryStatus = inventory.getStatus();
                    // read inventory.beneficiary_name
                    inventoryBeneficiaryName = inventory.getBeneficiary_name();
                    // read inventory.amount
                    inventoryAmount = inventory.getAmount();
                }


                // read ("Beneficiary Account No: ") + (inventory.destination_account)
                javaLangStringBeneficiaryAccountNoInventoryDestinationAccount = ("Beneficiary Account No: ") + (inventoryDestinationAccount);
                // read ("Reference number: ") + (inventory.reference_no_Etranzact)
                javaLangStringReferenceNumberInventoryReferenceNoEtranzact = ("Reference number: ") + (inventoryReferenceNoEtranzact);
                // read ("Beneficiary Name: ") + (inventory.beneficiary_name)
                javaLangStringBeneficiaryNameInventoryBeneficiaryName = ("Beneficiary Name: ") + (inventoryBeneficiaryName);
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setDrawableRight(this.btnDetails, isOpenBtnDetailsAndroidDrawableArrowUpBtnDetailsAndroidDrawableArrowDownBigger);
            com.woleapp.adapters.DataBindingAnimation1.setVisibility(this.relativeDetails, isOpen);
        }
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            com.woleapp.adapters.DataBindingTintColor.setDate(this.linearDate, inventoryTransactionDate);
            com.woleapp.adapters.DataBindingTintColor.setText(this.tvAmount, inventoryAmount);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvColor, javaLangStringReferenceNumberInventoryReferenceNoEtranzact);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvName, inventoryTransactionType);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvQuantity, javaLangStringBeneficiaryAccountNoInventoryDestinationAccount);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvSize, javaLangStringBeneficiaryNameInventoryBeneficiaryName);
            com.woleapp.adapters.DataBindingTintColor.setTextColor(this.tvStatus, inventoryStatus);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvStatus, inventoryStatus);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): isOpen
        flag 1 (0x2L): inventory
        flag 2 (0x3L): null
        flag 3 (0x4L): androidx.databinding.ViewDataBinding.safeUnbox(isOpen) ? @android:drawable/arrow_up : @android:drawable/arrow_down_bigger
        flag 4 (0x5L): androidx.databinding.ViewDataBinding.safeUnbox(isOpen) ? @android:drawable/arrow_up : @android:drawable/arrow_down_bigger
    flag mapping end*/
    //end
}