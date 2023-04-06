package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutQrRegistrationBindingImpl extends LayoutQrRegistrationBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ivQRCode, 1);
        sViewsWithIds.put(R.id.tvTitle1, 2);
        sViewsWithIds.put(R.id.textInputName, 3);
        sViewsWithIds.put(R.id.etName, 4);
        sViewsWithIds.put(R.id.textInputAddress, 5);
        sViewsWithIds.put(R.id.etAddress, 6);
        sViewsWithIds.put(R.id.textInputPhone, 7);
        sViewsWithIds.put(R.id.etPhone, 8);
        sViewsWithIds.put(R.id.textInputPwd, 9);
        sViewsWithIds.put(R.id.etPwd, 10);
        sViewsWithIds.put(R.id.textInputConfirmPwd, 11);
        sViewsWithIds.put(R.id.etConfirmPwd, 12);
        sViewsWithIds.put(R.id.spnState, 13);
        sViewsWithIds.put(R.id.btn_submit, 14);
        sViewsWithIds.put(R.id.tvNoAccount, 15);
        sViewsWithIds.put(R.id.tvSignUp, 16);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutQrRegistrationBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private LayoutQrRegistrationBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[14]
            , (android.widget.EditText) bindings[6]
            , (android.widget.EditText) bindings[12]
            , (android.widget.EditText) bindings[4]
            , (android.widget.EditText) bindings[8]
            , (android.widget.EditText) bindings[10]
            , (android.widget.ImageView) bindings[1]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[13]
            , (com.google.android.material.textfield.TextInputLayout) bindings[5]
            , (com.google.android.material.textfield.TextInputLayout) bindings[11]
            , (com.google.android.material.textfield.TextInputLayout) bindings[3]
            , (com.google.android.material.textfield.TextInputLayout) bindings[7]
            , (com.google.android.material.textfield.TextInputLayout) bindings[9]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[2]
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