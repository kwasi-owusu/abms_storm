package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutVerifyOtpBindingImpl extends LayoutVerifyOtpBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linearForm, 1);
        sViewsWithIds.put(R.id.txt_login, 2);
        sViewsWithIds.put(R.id.tv_email, 3);
        sViewsWithIds.put(R.id.tvSeparator, 4);
        sViewsWithIds.put(R.id.tv_mobile_no, 5);
        sViewsWithIds.put(R.id.et_Otp_1, 6);
        sViewsWithIds.put(R.id.et_Otp_2, 7);
        sViewsWithIds.put(R.id.et_Otp_3, 8);
        sViewsWithIds.put(R.id.et_Otp_4, 9);
        sViewsWithIds.put(R.id.ivTimer, 10);
        sViewsWithIds.put(R.id.tv_timer, 11);
        sViewsWithIds.put(R.id.linearResend, 12);
        sViewsWithIds.put(R.id.tv_title_otp, 13);
        sViewsWithIds.put(R.id.tv_resend, 14);
        sViewsWithIds.put(R.id.btnVerify, 15);
        sViewsWithIds.put(R.id.btn_continue, 16);
        sViewsWithIds.put(R.id.ivBack, 17);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutVerifyOtpBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }
    private LayoutVerifyOtpBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[16]
            , (android.widget.Button) bindings[15]
            , (android.widget.EditText) bindings[6]
            , (android.widget.EditText) bindings[7]
            , (android.widget.EditText) bindings[8]
            , (android.widget.EditText) bindings[9]
            , (android.widget.ImageView) bindings[17]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[2]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
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