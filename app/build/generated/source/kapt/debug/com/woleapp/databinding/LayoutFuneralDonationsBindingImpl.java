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
        sViewsWithIds.put(R.id.transtype, 4);
        sViewsWithIds.put(R.id.momo, 5);
        sViewsWithIds.put(R.id.cardPayment, 6);
        sViewsWithIds.put(R.id.cashPayment, 7);
        sViewsWithIds.put(R.id.linearPrice, 8);
        sViewsWithIds.put(R.id.etAmt, 9);
        sViewsWithIds.put(R.id.payeeName, 10);
        sViewsWithIds.put(R.id.networkSpinner, 11);
        sViewsWithIds.put(R.id.spnNetworkType, 12);
        sViewsWithIds.put(R.id.accountNumber, 13);
        sViewsWithIds.put(R.id.etCVV, 14);
        sViewsWithIds.put(R.id.cardNumber, 15);
        sViewsWithIds.put(R.id.cardExpiry, 16);
        sViewsWithIds.put(R.id.tvConvinienceFee, 17);
        sViewsWithIds.put(R.id.linearCanvasMain, 18);
        sViewsWithIds.put(R.id.tvSign, 19);
        sViewsWithIds.put(R.id.tvRetake, 20);
        sViewsWithIds.put(R.id.linearCanvas, 21);
        sViewsWithIds.put(R.id.btn_print, 22);
        sViewsWithIds.put(R.id.cardCollection, 23);
        sViewsWithIds.put(R.id.tvCardCollection, 24);
        sViewsWithIds.put(R.id.firstimage, 25);
        sViewsWithIds.put(R.id.cashCollection, 26);
        sViewsWithIds.put(R.id.tvCashCollection, 27);
        sViewsWithIds.put(R.id.readCard, 28);
        sViewsWithIds.put(R.id.tvReadCard, 29);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutFuneralDonationsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 30, sIncludes, sViewsWithIds));
    }
    private LayoutFuneralDonationsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.EditText) bindings[13]
            , (android.widget.Button) bindings[22]
            , (androidx.cardview.widget.CardView) bindings[23]
            , (android.widget.EditText) bindings[16]
            , (android.widget.EditText) bindings[15]
            , (android.widget.RadioButton) bindings[6]
            , (androidx.cardview.widget.CardView) bindings[26]
            , (android.widget.RadioButton) bindings[7]
            , (android.widget.TextView) bindings[3]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[9]
            , (android.widget.EditText) bindings[14]
            , (android.widget.ImageView) bindings[25]
            , (android.widget.LinearLayout) bindings[21]
            , (android.widget.RelativeLayout) bindings[18]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.RadioButton) bindings[5]
            , (android.widget.LinearLayout) bindings[11]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[10]
            , (androidx.cardview.widget.CardView) bindings[28]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.ScrollView) bindings[0]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[12]
            , (android.widget.RadioGroup) bindings[4]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[27]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[29]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[19]
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