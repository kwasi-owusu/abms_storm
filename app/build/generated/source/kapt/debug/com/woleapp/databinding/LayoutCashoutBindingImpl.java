package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutCashoutBindingImpl extends LayoutCashoutBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ivSmiley, 1);
        sViewsWithIds.put(R.id.tvMessage, 2);
        sViewsWithIds.put(R.id.btn_continue, 3);
        sViewsWithIds.put(R.id.relativeOptions, 4);
        sViewsWithIds.put(R.id.linearTitle, 5);
        sViewsWithIds.put(R.id.tvTitle, 6);
        sViewsWithIds.put(R.id.linearPrice, 7);
        sViewsWithIds.put(R.id.etAmt, 8);
        sViewsWithIds.put(R.id.etFee, 9);
        sViewsWithIds.put(R.id.id, 10);
        sViewsWithIds.put(R.id.spnIdType, 11);
        sViewsWithIds.put(R.id.etIDNo, 12);
        sViewsWithIds.put(R.id.etName, 13);
        sViewsWithIds.put(R.id.etCVV, 14);
        sViewsWithIds.put(R.id.tvConvinienceFee, 15);
        sViewsWithIds.put(R.id.cardCashCollection, 16);
        sViewsWithIds.put(R.id.tvCashCollection, 17);
        sViewsWithIds.put(R.id.firstimage, 18);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutCashoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private LayoutCashoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[3]
            , (androidx.cardview.widget.CardView) bindings[16]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[8]
            , (android.widget.EditText) bindings[14]
            , (android.widget.EditText) bindings[9]
            , (android.widget.EditText) bindings[12]
            , (android.widget.EditText) bindings[13]
            , (android.widget.ImageView) bindings[18]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[4]
            , (android.widget.ScrollView) bindings[0]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[11]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[6]
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