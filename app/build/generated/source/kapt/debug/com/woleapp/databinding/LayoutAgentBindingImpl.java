package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutAgentBindingImpl extends LayoutAgentBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linearFields, 1);
        sViewsWithIds.put(R.id.textInputName, 2);
        sViewsWithIds.put(R.id.etName, 3);
        sViewsWithIds.put(R.id.textInputUname, 4);
        sViewsWithIds.put(R.id.etEmail, 5);
        sViewsWithIds.put(R.id.textInputPwd, 6);
        sViewsWithIds.put(R.id.etPwd, 7);
        sViewsWithIds.put(R.id.textInputConfirmPwd, 8);
        sViewsWithIds.put(R.id.etConfirmPwd, 9);
        sViewsWithIds.put(R.id.linearAgentView, 10);
        sViewsWithIds.put(R.id.textInputPhone, 11);
        sViewsWithIds.put(R.id.etPhone, 12);
        sViewsWithIds.put(R.id.textInputAccNumber, 13);
        sViewsWithIds.put(R.id.etAccNumber, 14);
        sViewsWithIds.put(R.id.spnBank, 15);
        sViewsWithIds.put(R.id.textInputBVNNumber, 16);
        sViewsWithIds.put(R.id.etBVNNumber, 17);
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutAgentBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }
    private LayoutAgentBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.EditText) bindings[14]
            , (android.widget.EditText) bindings[17]
            , (android.widget.EditText) bindings[9]
            , (android.widget.EditText) bindings[5]
            , (android.widget.EditText) bindings[3]
            , (android.widget.EditText) bindings[12]
            , (android.widget.EditText) bindings[7]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.LinearLayout) bindings[1]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[15]
            , (com.google.android.material.textfield.TextInputLayout) bindings[13]
            , (com.google.android.material.textfield.TextInputLayout) bindings[16]
            , (com.google.android.material.textfield.TextInputLayout) bindings[8]
            , (com.google.android.material.textfield.TextInputLayout) bindings[2]
            , (com.google.android.material.textfield.TextInputLayout) bindings[11]
            , (com.google.android.material.textfield.TextInputLayout) bindings[6]
            , (com.google.android.material.textfield.TextInputLayout) bindings[4]
            );
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
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