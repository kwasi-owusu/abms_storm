package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutSanlamBindingImpl extends LayoutSanlamBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.relativeOptions, 1);
        sViewsWithIds.put(R.id.pageNumber, 2);
        sViewsWithIds.put(R.id.circleActive, 3);
        sViewsWithIds.put(R.id.circleInactive1, 4);
        sViewsWithIds.put(R.id.circleInactive2, 5);
        sViewsWithIds.put(R.id.getStarted, 6);
        sViewsWithIds.put(R.id.imageService, 7);
        sViewsWithIds.put(R.id.btn_getStarted, 8);
        sViewsWithIds.put(R.id.memberAlready, 9);
        sViewsWithIds.put(R.id.claimHere, 10);
        sViewsWithIds.put(R.id.policy, 11);
        sViewsWithIds.put(R.id.policyTitle, 12);
        sViewsWithIds.put(R.id.btn_joinNow, 13);
        sViewsWithIds.put(R.id.personalDetails, 14);
        sViewsWithIds.put(R.id.tvTitle, 15);
        sViewsWithIds.put(R.id.firstName, 16);
        sViewsWithIds.put(R.id.surname, 17);
        sViewsWithIds.put(R.id.dateOfBirth, 18);
        sViewsWithIds.put(R.id.contactNumber, 19);
        sViewsWithIds.put(R.id.ghanaCard, 20);
        sViewsWithIds.put(R.id.btn_nextMain, 21);
        sViewsWithIds.put(R.id.addressDetails, 22);
        sViewsWithIds.put(R.id.tvBank, 23);
        sViewsWithIds.put(R.id.familyFirstName, 24);
        sViewsWithIds.put(R.id.familySurname, 25);
        sViewsWithIds.put(R.id.familyDateOfBirth, 26);
        sViewsWithIds.put(R.id.familyContactNumber, 27);
        sViewsWithIds.put(R.id.btn_previous, 28);
        sViewsWithIds.put(R.id.btn_next, 29);
        sViewsWithIds.put(R.id.employmentDetails, 30);
        sViewsWithIds.put(R.id.tvBeneficiary, 31);
        sViewsWithIds.put(R.id.ben_firstName, 32);
        sViewsWithIds.put(R.id.ben_surname, 33);
        sViewsWithIds.put(R.id.ben_contactNumber, 34);
        sViewsWithIds.put(R.id.btn_previousPage, 35);
        sViewsWithIds.put(R.id.btn_submit, 36);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutSanlamBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 37, sIncludes, sViewsWithIds));
    }
    private LayoutSanlamBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[22]
            , (android.widget.EditText) bindings[34]
            , (android.widget.EditText) bindings[32]
            , (android.widget.EditText) bindings[33]
            , (android.widget.Button) bindings[8]
            , (android.widget.Button) bindings[13]
            , (android.widget.Button) bindings[29]
            , (android.widget.Button) bindings[21]
            , (android.widget.Button) bindings[28]
            , (android.widget.Button) bindings[35]
            , (android.widget.Button) bindings[36]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[10]
            , (android.widget.EditText) bindings[19]
            , (android.widget.EditText) bindings[18]
            , (android.widget.LinearLayout) bindings[30]
            , (android.widget.EditText) bindings[27]
            , (android.widget.EditText) bindings[26]
            , (android.widget.EditText) bindings[24]
            , (android.widget.EditText) bindings[25]
            , (android.widget.EditText) bindings[16]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.EditText) bindings[20]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.TextView) bindings[9]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.TextView) bindings[12]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.ScrollView) bindings[0]
            , (android.widget.EditText) bindings[17]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[31]
            , (android.widget.TextView) bindings[15]
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