package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutWesternUnionBindingImpl extends LayoutWesternUnionBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvTitle, 1);
        sViewsWithIds.put(R.id.cardNumber, 2);
        sViewsWithIds.put(R.id.transactionPurpose, 3);
        sViewsWithIds.put(R.id.business, 4);
        sViewsWithIds.put(R.id.senderRelation, 5);
        sViewsWithIds.put(R.id.controlNumber, 6);
        sViewsWithIds.put(R.id.tvReceiver, 7);
        sViewsWithIds.put(R.id.firstName, 8);
        sViewsWithIds.put(R.id.lastName, 9);
        sViewsWithIds.put(R.id.city, 10);
        sViewsWithIds.put(R.id.street, 11);
        sViewsWithIds.put(R.id.postCode, 12);
        sViewsWithIds.put(R.id.country, 13);
        sViewsWithIds.put(R.id.receiverCountryCode, 14);
        sViewsWithIds.put(R.id.contactTelNumber, 15);
        sViewsWithIds.put(R.id.tvSender, 16);
        sViewsWithIds.put(R.id.senderFirstName, 17);
        sViewsWithIds.put(R.id.senderLastName, 18);
        sViewsWithIds.put(R.id.senderCity, 19);
        sViewsWithIds.put(R.id.senderStreet, 20);
        sViewsWithIds.put(R.id.senderPostCode, 21);
        sViewsWithIds.put(R.id.senderCountry, 22);
        sViewsWithIds.put(R.id.senderCountryCode, 23);
        sViewsWithIds.put(R.id.senderTelNo, 24);
        sViewsWithIds.put(R.id.tvTransaction, 25);
        sViewsWithIds.put(R.id.etAmt, 26);
        sViewsWithIds.put(R.id.etFee, 27);
        sViewsWithIds.put(R.id.citySentFrom, 28);
        sViewsWithIds.put(R.id.sendCountry, 29);
        sViewsWithIds.put(R.id.testQuestion, 30);
        sViewsWithIds.put(R.id.testAnswer, 31);
        sViewsWithIds.put(R.id.date, 32);
        sViewsWithIds.put(R.id.tvSign, 33);
        sViewsWithIds.put(R.id.tvRetake, 34);
        sViewsWithIds.put(R.id.linearCanvas, 35);
        sViewsWithIds.put(R.id.btn_continue, 36);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutWesternUnionBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 37, sIncludes, sViewsWithIds));
    }
    private LayoutWesternUnionBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[36]
            , (android.widget.EditText) bindings[4]
            , (android.widget.EditText) bindings[2]
            , (android.widget.EditText) bindings[10]
            , (android.widget.EditText) bindings[28]
            , (android.widget.EditText) bindings[15]
            , (android.widget.EditText) bindings[6]
            , (android.widget.EditText) bindings[13]
            , (android.widget.EditText) bindings[32]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[26]
            , (android.widget.EditText) bindings[27]
            , (android.widget.EditText) bindings[8]
            , (android.widget.EditText) bindings[9]
            , (android.widget.LinearLayout) bindings[35]
            , (android.widget.EditText) bindings[12]
            , (com.hbb20.CountryCodePicker) bindings[14]
            , (android.widget.ScrollView) bindings[0]
            , (android.widget.EditText) bindings[29]
            , (android.widget.EditText) bindings[19]
            , (android.widget.EditText) bindings[22]
            , (com.hbb20.CountryCodePicker) bindings[23]
            , (android.widget.EditText) bindings[17]
            , (android.widget.EditText) bindings[18]
            , (android.widget.EditText) bindings[21]
            , (android.widget.EditText) bindings[5]
            , (android.widget.EditText) bindings[20]
            , (android.widget.EditText) bindings[24]
            , (android.widget.EditText) bindings[11]
            , (android.widget.EditText) bindings[31]
            , (android.widget.EditText) bindings[30]
            , (android.widget.EditText) bindings[3]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[34]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[33]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[25]
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