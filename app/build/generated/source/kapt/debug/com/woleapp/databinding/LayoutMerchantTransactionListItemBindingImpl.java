package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutMerchantTransactionListItemBindingImpl extends LayoutMerchantTransactionListItemBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.sn, 5);
    }
    // views
    @NonNull
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView3;
    @NonNull
    private final android.widget.TextView mboundView4;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutMerchantTransactionListItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private LayoutMerchantTransactionListItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[5]
            , (android.widget.LinearLayout) bindings[0]
            );
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.tableHeader.setTag(null);
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
        if (BR.transaction == variableId) {
            setTransaction((com.woleapp.model.MerchantTransaction) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setTransaction(@Nullable com.woleapp.model.MerchantTransaction Transaction) {
        this.mTransaction = Transaction;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.transaction);
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
        java.lang.String transactionStatus = null;
        java.lang.String transactionPaymentMethod = null;
        java.lang.String transactionCustomerName = null;
        java.lang.String stringValueOfTransactionAmount = null;
        com.woleapp.model.MerchantTransaction transaction = mTransaction;
        java.lang.String transactionAmount = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (transaction != null) {
                    // read transaction.status
                    transactionStatus = transaction.getStatus();
                    // read transaction.paymentMethod
                    transactionPaymentMethod = transaction.getPaymentMethod();
                    // read transaction.customerName
                    transactionCustomerName = transaction.getCustomerName();
                    // read transaction.amount
                    transactionAmount = transaction.getAmount();
                }


                // read String.valueOf(transaction.amount)
                stringValueOfTransactionAmount = java.lang.String.valueOf(transactionAmount);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, transactionPaymentMethod);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, transactionCustomerName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, stringValueOfTransactionAmount);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, transactionStatus);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): transaction
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}