package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutSanlamClaimsBindingImpl extends LayoutSanlamClaimsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvTitle, 1);
        sViewsWithIds.put(R.id.situationDescription, 2);
        sViewsWithIds.put(R.id.spnClaim, 3);
        sViewsWithIds.put(R.id.phoneNumber, 4);
        sViewsWithIds.put(R.id.date, 5);
        sViewsWithIds.put(R.id.option, 6);
        sViewsWithIds.put(R.id.yes, 7);
        sViewsWithIds.put(R.id.no, 8);
        sViewsWithIds.put(R.id.causeOfDeath, 9);
        sViewsWithIds.put(R.id.spnCauseOfDeath, 10);
        sViewsWithIds.put(R.id.btn_submit, 11);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutSanlamClaimsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private LayoutSanlamClaimsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[11]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.EditText) bindings[5]
            , (android.widget.RadioButton) bindings[8]
            , (android.widget.RadioGroup) bindings[6]
            , (android.widget.EditText) bindings[4]
            , (android.widget.ScrollView) bindings[0]
            , (android.widget.LinearLayout) bindings[2]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[10]
            , (androidx.appcompat.widget.AppCompatSpinner) bindings[3]
            , (android.widget.TextView) bindings[1]
            , (android.widget.RadioButton) bindings[7]
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