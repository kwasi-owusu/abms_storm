package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutSignUpOrigBindingImpl extends LayoutSignUpOrigBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvTitle, 1);
        sViewsWithIds.put(R.id.signUpButton, 2);
        sViewsWithIds.put(R.id.btnMerchant, 3);
        sViewsWithIds.put(R.id.btnAgent1, 4);
        sViewsWithIds.put(R.id.btnMerchant1, 5);
        sViewsWithIds.put(R.id.linearFields, 6);
        sViewsWithIds.put(R.id.textInputName, 7);
        sViewsWithIds.put(R.id.etName, 8);
        sViewsWithIds.put(R.id.textInputUname, 9);
        sViewsWithIds.put(R.id.etEmail, 10);
        sViewsWithIds.put(R.id.linearMerchantView, 11);
        sViewsWithIds.put(R.id.textInputPwd, 12);
        sViewsWithIds.put(R.id.etPwd, 13);
        sViewsWithIds.put(R.id.textInputConfirmPwd, 14);
        sViewsWithIds.put(R.id.etConfirmPwd, 15);
        sViewsWithIds.put(R.id.linearAgentView, 16);
        sViewsWithIds.put(R.id.textInputPhone, 17);
        sViewsWithIds.put(R.id.etPhone, 18);
        sViewsWithIds.put(R.id.textInputAccNumber, 19);
        sViewsWithIds.put(R.id.etAccNumber, 20);
        sViewsWithIds.put(R.id.spnBank, 21);
        sViewsWithIds.put(R.id.textInputBVNNumber, 22);
        sViewsWithIds.put(R.id.etBVNNumber, 23);
        sViewsWithIds.put(R.id.btn_login, 24);
        sViewsWithIds.put(R.id.tvNoAccount, 25);
        sViewsWithIds.put(R.id.tvSignUp, 26);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutSignUpOrigBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 27, sIncludes, sViewsWithIds));
    }
    private LayoutSignUpOrigBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[4]
            , (android.widget.Button) bindings[24]
            , (androidx.cardview.widget.CardView) bindings[3]
            , (android.widget.Button) bindings[5]
            , (android.widget.EditText) bindings[20]
            , (android.widget.EditText) bindings[23]
            , (android.widget.EditText) bindings[15]
            , (android.widget.EditText) bindings[10]
            , (android.widget.EditText) bindings[8]
            , (android.widget.EditText) bindings[18]
            , (android.widget.EditText) bindings[13]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[11]
            , (androidx.cardview.widget.CardView) bindings[2]
            , (com.woleapp.ui.widgets.CustomSpinner) bindings[21]
            , (com.google.android.material.textfield.TextInputLayout) bindings[19]
            , (com.google.android.material.textfield.TextInputLayout) bindings[22]
            , (com.google.android.material.textfield.TextInputLayout) bindings[14]
            , (com.google.android.material.textfield.TextInputLayout) bindings[7]
            , (com.google.android.material.textfield.TextInputLayout) bindings[17]
            , (com.google.android.material.textfield.TextInputLayout) bindings[12]
            , (com.google.android.material.textfield.TextInputLayout) bindings[9]
            , (android.widget.TextView) bindings[25]
            , (android.widget.TextView) bindings[26]
            , (android.widget.TextView) bindings[1]
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