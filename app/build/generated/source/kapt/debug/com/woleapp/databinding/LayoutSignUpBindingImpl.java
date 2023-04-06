package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutSignUpBindingImpl extends LayoutSignUpBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(11);
        sIncludes.setIncludes(1, 
            new String[] {"layout_agent"},
            new int[] {2},
            new int[] {com.woleapp.R.layout.layout_agent});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.container, 3);
        sViewsWithIds.put(R.id.tvTitle, 4);
        sViewsWithIds.put(R.id.btnAgent, 5);
        sViewsWithIds.put(R.id.btnMerchant, 6);
        sViewsWithIds.put(R.id.parentFrame, 7);
        sViewsWithIds.put(R.id.btn_login, 8);
        sViewsWithIds.put(R.id.tvNoAccount, 9);
        sViewsWithIds.put(R.id.tvSignUp, 10);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutSignUpBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private LayoutSignUpBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (androidx.cardview.widget.CardView) bindings[5]
            , (android.widget.Button) bindings[8]
            , (androidx.cardview.widget.CardView) bindings[6]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.FrameLayout) bindings[1]
            , (android.widget.FrameLayout) bindings[7]
            , (android.widget.ScrollView) bindings[0]
            , (com.woleapp.databinding.LayoutAgentBinding) bindings[2]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[4]
            );
        this.linearFields.setTag(null);
        this.scrollView.setTag(null);
        setContainedBinding(this.signUpLayout);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        signUpLayout.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (signUpLayout.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        signUpLayout.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeSignUpLayout((com.woleapp.databinding.LayoutAgentBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeSignUpLayout(com.woleapp.databinding.LayoutAgentBinding SignUpLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
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
        executeBindingsOn(signUpLayout);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): signUpLayout
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}