package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutInvoicePreviewBindingImpl extends LayoutInvoicePreviewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvError, 1);
        sViewsWithIds.put(R.id.cardItem, 2);
        sViewsWithIds.put(R.id.container, 3);
        sViewsWithIds.put(R.id.tvTag, 4);
        sViewsWithIds.put(R.id.tvInvoiceNumber, 5);
        sViewsWithIds.put(R.id.linearInvoiceDates, 6);
        sViewsWithIds.put(R.id.tvInvoiceDate, 7);
        sViewsWithIds.put(R.id.tvSenderName, 8);
        sViewsWithIds.put(R.id.tvContact, 9);
        sViewsWithIds.put(R.id.tvAddress, 10);
        sViewsWithIds.put(R.id.iv, 11);
        sViewsWithIds.put(R.id.tvCustomerName, 12);
        sViewsWithIds.put(R.id.tvTotal, 13);
        sViewsWithIds.put(R.id.tvCustContact, 14);
        sViewsWithIds.put(R.id.tvCustomerAddress, 15);
        sViewsWithIds.put(R.id.linearItems, 16);
        sViewsWithIds.put(R.id.rvItems, 17);
        sViewsWithIds.put(R.id.tvTotalAmt, 18);
        sViewsWithIds.put(R.id.relativeDiscount, 19);
        sViewsWithIds.put(R.id.tvDiscountTitle, 20);
        sViewsWithIds.put(R.id.tvDiscount, 21);
        sViewsWithIds.put(R.id.relativeTax, 22);
        sViewsWithIds.put(R.id.tvTaxTitle, 23);
        sViewsWithIds.put(R.id.tvTaxAmt, 24);
        sViewsWithIds.put(R.id.etNote, 25);
        sViewsWithIds.put(R.id.tvSign, 26);
        sViewsWithIds.put(R.id.tvRetake, 27);
        sViewsWithIds.put(R.id.linearCanvas, 28);
        sViewsWithIds.put(R.id.tvShareVia, 29);
        sViewsWithIds.put(R.id.linearOptions, 30);
        sViewsWithIds.put(R.id.btn_share, 31);
        sViewsWithIds.put(R.id.cardMail, 32);
        sViewsWithIds.put(R.id.ivShare, 33);
        sViewsWithIds.put(R.id.cardOthers, 34);
        sViewsWithIds.put(R.id.ivClose, 35);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutInvoicePreviewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 36, sIncludes, sViewsWithIds));
    }
    private LayoutInvoicePreviewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[31]
            , (androidx.cardview.widget.CardView) bindings[2]
            , (androidx.cardview.widget.CardView) bindings[32]
            , (androidx.cardview.widget.CardView) bindings[34]
            , (android.widget.LinearLayout) bindings[3]
            , (com.woleapp.ui.widgets.EditTextNoEnter) bindings[25]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.ImageView) bindings[35]
            , (android.widget.ImageView) bindings[33]
            , (android.widget.LinearLayout) bindings[28]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.LinearLayout) bindings[30]
            , (android.widget.RelativeLayout) bindings[19]
            , (android.widget.RelativeLayout) bindings[22]
            , (androidx.recyclerview.widget.RecyclerView) bindings[17]
            , (androidx.core.widget.NestedScrollView) bindings[0]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[10]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[9]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[14]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[15]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[12]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[27]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[8]
            , (android.widget.TextView) bindings[29]
            , (android.widget.TextView) bindings[26]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[18]
            );
        this.scroll.setTag(null);
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