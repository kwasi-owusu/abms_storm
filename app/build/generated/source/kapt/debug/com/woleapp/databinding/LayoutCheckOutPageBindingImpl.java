package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutCheckOutPageBindingImpl extends LayoutCheckOutPageBinding  {

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
        sViewsWithIds.put(R.id.choose_payment_method, 4);
        sViewsWithIds.put(R.id.guideline, 5);
        sViewsWithIds.put(R.id.payment_methods, 6);
        sViewsWithIds.put(R.id.payWithStormWallet, 7);
        sViewsWithIds.put(R.id.cardCashCollection, 8);
        sViewsWithIds.put(R.id.cardAccountTransfer1, 9);
        sViewsWithIds.put(R.id.card_fee, 10);
        sViewsWithIds.put(R.id.cardQRPay, 11);
        sViewsWithIds.put(R.id.cardPosPayment, 12);
        sViewsWithIds.put(R.id.pos_fee, 13);
        sViewsWithIds.put(R.id.cardAccountTransfer, 14);
        sViewsWithIds.put(R.id.pay_with_paylink, 15);
        sViewsWithIds.put(R.id.paylink_fee, 16);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutCheckOutPageBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private LayoutCheckOutPageBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[1]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.TextView) bindings[10]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.TextView) bindings[4]
            , (androidx.constraintlayout.widget.Guideline) bindings[5]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.TextView) bindings[16]
            , (android.widget.ScrollView) bindings[6]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
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