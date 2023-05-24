package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutFuneralDonationsBindingImpl extends LayoutFuneralDonationsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.relativeOptions, 1);
        sViewsWithIds.put(R.id.linearTitle, 2);
        sViewsWithIds.put(R.id.collectionTitle, 3);
        sViewsWithIds.put(R.id.linearPrice, 4);
        sViewsWithIds.put(R.id.uid, 5);
        sViewsWithIds.put(R.id.etAmt, 6);
        sViewsWithIds.put(R.id.accountSpinner, 7);
        sViewsWithIds.put(R.id.spnAccountType, 8);
        sViewsWithIds.put(R.id.accountName, 9);
        sViewsWithIds.put(R.id.networkSpinner, 10);
        sViewsWithIds.put(R.id.spnNetworkType, 11);
        sViewsWithIds.put(R.id.accountNumber, 12);
        sViewsWithIds.put(R.id.cardNumber, 13);
        sViewsWithIds.put(R.id.cardExpiry, 14);
        sViewsWithIds.put(R.id.etCVV, 15);
        sViewsWithIds.put(R.id.tvConvinienceFee, 16);
        sViewsWithIds.put(R.id.cardCollection, 17);
        sViewsWithIds.put(R.id.tvCardCollection, 18);
        sViewsWithIds.put(R.id.firstimage, 19);
        sViewsWithIds.put(R.id.cashCollection, 20);
        sViewsWithIds.put(R.id.tvCashCollection, 21);
        sViewsWithIds.put(R.id.readCard, 22);
        sViewsWithIds.put(R.id.tvReadCard, 23);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutFuneralDonationsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }
    private LayoutFuneralDonationsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.woleapp.ui.widgets.CustomEditText) bindings[9]
            , (android.widget.EditText) bindings[12]
            , (android.widget.LinearLayout) bindings[7]
            , (androidx.cardview.widget.CardView) bindings[17]
            , (android.widget.EditText) bindings[14]
            , (android.widget.EditText) bindings[13]
            , (androidx.cardview.widget.CardView) bindings[20]
            , (android.widget.TextView) bindings[3]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[6]
            , (android.widget.EditText) bindings[15]
            , (android.widget.ImageView) bindings[19]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[10]
            , (androidx.cardview.widget.CardView) bindings[22]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.ScrollView) bindings[0]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[8]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[11]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[23]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[5]
            );
        this.scrollView.setTag(null);
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