package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutMoneygramBindingImpl extends LayoutMoneygramBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvTitle, 1);
        sViewsWithIds.put(R.id.referenceNumber, 2);
        sViewsWithIds.put(R.id.tvReceiver, 3);
        sViewsWithIds.put(R.id.firstName, 4);
        sViewsWithIds.put(R.id.middleName, 5);
        sViewsWithIds.put(R.id.lastName, 6);
        sViewsWithIds.put(R.id.dateOfBirth, 7);
        sViewsWithIds.put(R.id.countryOfBirth, 8);
        sViewsWithIds.put(R.id.address, 9);
        sViewsWithIds.put(R.id.city, 10);
        sViewsWithIds.put(R.id.postCode, 11);
        sViewsWithIds.put(R.id.country, 12);
        sViewsWithIds.put(R.id.countyCodePicker, 13);
        sViewsWithIds.put(R.id.contactTelNumber, 14);
        sViewsWithIds.put(R.id.spnBankLayout, 15);
        sViewsWithIds.put(R.id.spnBank, 16);
        sViewsWithIds.put(R.id.etAccountNumber, 17);
        sViewsWithIds.put(R.id.spnPurpose, 18);
        sViewsWithIds.put(R.id.tvSender, 19);
        sViewsWithIds.put(R.id.senderFirstName, 20);
        sViewsWithIds.put(R.id.senderMiddleName, 21);
        sViewsWithIds.put(R.id.senderLastName, 22);
        sViewsWithIds.put(R.id.tvTransaction, 23);
        sViewsWithIds.put(R.id.etAmt, 24);
        sViewsWithIds.put(R.id.etFee, 25);
        sViewsWithIds.put(R.id.sendCountry, 26);
        sViewsWithIds.put(R.id.date, 27);
        sViewsWithIds.put(R.id.time, 28);
        sViewsWithIds.put(R.id.tvSign, 29);
        sViewsWithIds.put(R.id.tvRetake, 30);
        sViewsWithIds.put(R.id.linearCanvas, 31);
        sViewsWithIds.put(R.id.btn_continue, 32);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutMoneygramBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 33, sIncludes, sViewsWithIds));
    }
    private LayoutMoneygramBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.EditText) bindings[9]
            , (android.widget.Button) bindings[32]
            , (android.widget.EditText) bindings[10]
            , (android.widget.EditText) bindings[14]
            , (android.widget.EditText) bindings[12]
            , (android.widget.EditText) bindings[8]
            , (com.hbb20.CountryCodePicker) bindings[13]
            , (android.widget.EditText) bindings[27]
            , (android.widget.EditText) bindings[7]
            , (android.widget.EditText) bindings[17]
            , (com.woleapp.ui.widgets.CustomEditText) bindings[24]
            , (android.widget.EditText) bindings[25]
            , (android.widget.EditText) bindings[4]
            , (android.widget.EditText) bindings[6]
            , (android.widget.LinearLayout) bindings[31]
            , (android.widget.EditText) bindings[5]
            , (android.widget.EditText) bindings[11]
            , (android.widget.EditText) bindings[2]
            , (android.widget.ScrollView) bindings[0]
            , (android.widget.EditText) bindings[26]
            , (android.widget.EditText) bindings[20]
            , (android.widget.EditText) bindings[22]
            , (android.widget.EditText) bindings[21]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[16]
            , (android.widget.LinearLayout) bindings[15]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[18]
            , (android.widget.EditText) bindings[28]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[30]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[29]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[23]
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