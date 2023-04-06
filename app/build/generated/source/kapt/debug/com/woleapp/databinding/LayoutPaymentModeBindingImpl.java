package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutPaymentModeBindingImpl extends LayoutPaymentModeBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.btn_back, 1);
        sViewsWithIds.put(R.id.tvAmount, 2);
        sViewsWithIds.put(R.id.tvConvenienceFee, 3);
        sViewsWithIds.put(R.id.cardCashCollection, 4);
        sViewsWithIds.put(R.id.tvCashCollection, 5);
        sViewsWithIds.put(R.id.cardPosPayment, 6);
        sViewsWithIds.put(R.id.tvPosPayment, 7);
        sViewsWithIds.put(R.id.cardAccountTransfer, 8);
        sViewsWithIds.put(R.id.tvAccTransfer, 9);
        sViewsWithIds.put(R.id.cardAccountTransfer1, 10);
        sViewsWithIds.put(R.id.tvAccTransfer1, 11);
        sViewsWithIds.put(R.id.ivMastercard, 12);
        sViewsWithIds.put(R.id.ivVisa, 13);
        sViewsWithIds.put(R.id.cardQRPay, 14);
        sViewsWithIds.put(R.id.tvQRPayment, 15);
        sViewsWithIds.put(R.id.ivMasterpass, 16);
        sViewsWithIds.put(R.id.ivSeparator, 17);
        sViewsWithIds.put(R.id.ivQR, 18);
        sViewsWithIds.put(R.id.ivmVisa, 19);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutPaymentModeBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds));
    }
    private LayoutPaymentModeBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[1]
            , (androidx.cardview.widget.CardView) bindings[8]
            , (androidx.cardview.widget.CardView) bindings[10]
            , (androidx.cardview.widget.CardView) bindings[4]
            , (androidx.cardview.widget.CardView) bindings[6]
            , (androidx.cardview.widget.CardView) bindings[14]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.ImageView) bindings[18]
            , (android.widget.ImageView) bindings[17]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.ImageView) bindings[19]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[15]
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