package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutVerifyUtilityPaymentBindingImpl extends LayoutVerifyUtilityPaymentBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.summary, 7);
        sViewsWithIds.put(R.id.provider, 8);
        sViewsWithIds.put(R.id.bill_package, 9);
        sViewsWithIds.put(R.id.account_id, 10);
        sViewsWithIds.put(R.id.account_id_details, 11);
        sViewsWithIds.put(R.id.amount, 12);
        sViewsWithIds.put(R.id.fee, 13);
        sViewsWithIds.put(R.id.proceed, 14);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutVerifyUtilityPaymentBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private LayoutVerifyUtilityPaymentBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[2]
            , (android.widget.Button) bindings[14]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[7]
            );
        this.accountIdDetailsValue.setTag(null);
        this.accountIdValue.setTag(null);
        this.amountValue.setTag(null);
        this.feeValue.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.packageValue.setTag(null);
        this.providerValue.setTag(null);
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
        if (BR.billResponse == variableId) {
            setBillResponse((com.woleapp.model.ValidateBillResponse) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setBillResponse(@Nullable com.woleapp.model.ValidateBillResponse BillResponse) {
        this.mBillResponse = BillResponse;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.billResponse);
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
        java.lang.String billResponseAmount = null;
        java.lang.String billResponseFees = null;
        java.lang.String billResponseProvider = null;
        java.lang.String billResponseBillAccountId = null;
        com.woleapp.model.ValidateBillResponse billResponse = mBillResponse;
        java.lang.String billResponseBillPackage = null;
        java.lang.String billResponseBillAccountIdDetails = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (billResponse != null) {
                    // read billResponse.amount
                    billResponseAmount = billResponse.getAmount();
                    // read billResponse.fees
                    billResponseFees = billResponse.getFees();
                    // read billResponse.provider
                    billResponseProvider = billResponse.getProvider();
                    // read billResponse.billAccountId
                    billResponseBillAccountId = billResponse.getBillAccountId();
                    // read billResponse.billPackage
                    billResponseBillPackage = billResponse.getBillPackage();
                    // read billResponse.billAccountIdDetails
                    billResponseBillAccountIdDetails = billResponse.getBillAccountIdDetails();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.accountIdDetailsValue, billResponseBillAccountIdDetails);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.accountIdValue, billResponseBillAccountId);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.amountValue, billResponseAmount);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.feeValue, billResponseFees);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.packageValue, billResponseBillPackage);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.providerValue, billResponseProvider);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): billResponse
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}