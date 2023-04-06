package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutInternetSubscriptionBindingImpl extends LayoutInternetSubscriptionBinding implements com.woleapp.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.header, 4);
        sViewsWithIds.put(R.id.provider_spinner, 5);
        sViewsWithIds.put(R.id.package_spinner, 6);
        sViewsWithIds.put(R.id.price_input_layout, 7);
        sViewsWithIds.put(R.id.confirm_mobile_number, 8);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final android.widget.Button mboundView3;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback9;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener enterMobileNumberandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewmodel.payloadMutableLiveData.getValue().destinationAccount
            //         is viewmodel.payloadMutableLiveData.getValue().setDestinationAccount((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(enterMobileNumber);
            // localize variables for thread safety
            // viewmodel.payloadMutableLiveData.getValue()
            com.woleapp.model.UtilitiesPayload viewmodelPayloadMutableLiveDataGetValue = null;
            // viewmodel != null
            boolean viewmodelJavaLangObjectNull = false;
            // viewmodel.payloadMutableLiveData.getValue() != null
            boolean viewmodelPayloadMutableLiveDataGetValueJavaLangObjectNull = false;
            // viewmodel.payloadMutableLiveData != null
            boolean viewmodelPayloadMutableLiveDataJavaLangObjectNull = false;
            // viewmodel.payloadMutableLiveData.getValue().destinationAccount
            java.lang.String viewmodelPayloadMutableLiveDataDestinationAccount = null;
            // viewmodel.payloadMutableLiveData
            androidx.lifecycle.MutableLiveData<com.woleapp.model.UtilitiesPayload> viewmodelPayloadMutableLiveData = null;
            // viewmodel
            com.woleapp.viewmodels.UtilitiesViewModel viewmodel = mViewmodel;



            viewmodelJavaLangObjectNull = (viewmodel) != (null);
            if (viewmodelJavaLangObjectNull) {


                viewmodelPayloadMutableLiveData = viewmodel.getPayloadMutableLiveData();

                viewmodelPayloadMutableLiveDataJavaLangObjectNull = (viewmodelPayloadMutableLiveData) != (null);
                if (viewmodelPayloadMutableLiveDataJavaLangObjectNull) {


                    viewmodelPayloadMutableLiveDataGetValue = viewmodelPayloadMutableLiveData.getValue();

                    viewmodelPayloadMutableLiveDataGetValueJavaLangObjectNull = (viewmodelPayloadMutableLiveDataGetValue) != (null);
                    if (viewmodelPayloadMutableLiveDataGetValueJavaLangObjectNull) {




                        viewmodelPayloadMutableLiveDataGetValue.setDestinationAccount(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener priceTextboxandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewmodel.payloadMutableLiveData.getValue().stringAmount
            //         is viewmodel.payloadMutableLiveData.getValue().setStringAmount((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(priceTextbox);
            // localize variables for thread safety
            // viewmodel.payloadMutableLiveData.getValue()
            com.woleapp.model.UtilitiesPayload viewmodelPayloadMutableLiveDataGetValue = null;
            // viewmodel != null
            boolean viewmodelJavaLangObjectNull = false;
            // viewmodel.payloadMutableLiveData.getValue() != null
            boolean viewmodelPayloadMutableLiveDataGetValueJavaLangObjectNull = false;
            // viewmodel.payloadMutableLiveData != null
            boolean viewmodelPayloadMutableLiveDataJavaLangObjectNull = false;
            // viewmodel.payloadMutableLiveData.getValue().stringAmount
            java.lang.String viewmodelPayloadMutableLiveDataStringAmount = null;
            // viewmodel.payloadMutableLiveData
            androidx.lifecycle.MutableLiveData<com.woleapp.model.UtilitiesPayload> viewmodelPayloadMutableLiveData = null;
            // viewmodel
            com.woleapp.viewmodels.UtilitiesViewModel viewmodel = mViewmodel;



            viewmodelJavaLangObjectNull = (viewmodel) != (null);
            if (viewmodelJavaLangObjectNull) {


                viewmodelPayloadMutableLiveData = viewmodel.getPayloadMutableLiveData();

                viewmodelPayloadMutableLiveDataJavaLangObjectNull = (viewmodelPayloadMutableLiveData) != (null);
                if (viewmodelPayloadMutableLiveDataJavaLangObjectNull) {


                    viewmodelPayloadMutableLiveDataGetValue = viewmodelPayloadMutableLiveData.getValue();

                    viewmodelPayloadMutableLiveDataGetValueJavaLangObjectNull = (viewmodelPayloadMutableLiveDataGetValue) != (null);
                    if (viewmodelPayloadMutableLiveDataGetValueJavaLangObjectNull) {




                        viewmodelPayloadMutableLiveDataGetValue.setStringAmount(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };

    public LayoutInternetSubscriptionBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }
    private LayoutInternetSubscriptionBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.EditText) bindings[8]
            , (android.widget.EditText) bindings[2]
            , (android.widget.TextView) bindings[4]
            , (android.widget.Spinner) bindings[6]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.EditText) bindings[1]
            , (android.widget.Spinner) bindings[5]
            );
        this.enterMobileNumber.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView3 = (android.widget.Button) bindings[3];
        this.mboundView3.setTag(null);
        this.priceTextbox.setTag(null);
        setRootTag(root);
        // listeners
        mCallback9 = new com.woleapp.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
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
        if (BR.viewmodel == variableId) {
            setViewmodel((com.woleapp.viewmodels.UtilitiesViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewmodel(@Nullable com.woleapp.viewmodels.UtilitiesViewModel Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.viewmodel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewmodelPayloadMutableLiveData((androidx.lifecycle.MutableLiveData<com.woleapp.model.UtilitiesPayload>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewmodelPayloadMutableLiveData(androidx.lifecycle.MutableLiveData<com.woleapp.model.UtilitiesPayload> ViewmodelPayloadMutableLiveData, int fieldId) {
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
        com.woleapp.model.UtilitiesPayload viewmodelPayloadMutableLiveDataGetValue = null;
        java.lang.String viewmodelPayloadMutableLiveDataStringAmount = null;
        java.lang.String viewmodelPayloadMutableLiveDataDestinationAccount = null;
        androidx.lifecycle.MutableLiveData<com.woleapp.model.UtilitiesPayload> viewmodelPayloadMutableLiveData = null;
        com.woleapp.viewmodels.UtilitiesViewModel viewmodel = mViewmodel;

        if ((dirtyFlags & 0x7L) != 0) {



                if (viewmodel != null) {
                    // read viewmodel.payloadMutableLiveData
                    viewmodelPayloadMutableLiveData = viewmodel.getPayloadMutableLiveData();
                }
                updateLiveDataRegistration(0, viewmodelPayloadMutableLiveData);


                if (viewmodelPayloadMutableLiveData != null) {
                    // read viewmodel.payloadMutableLiveData.getValue()
                    viewmodelPayloadMutableLiveDataGetValue = viewmodelPayloadMutableLiveData.getValue();
                }


                if (viewmodelPayloadMutableLiveDataGetValue != null) {
                    // read viewmodel.payloadMutableLiveData.getValue().stringAmount
                    viewmodelPayloadMutableLiveDataStringAmount = viewmodelPayloadMutableLiveDataGetValue.getStringAmount();
                    // read viewmodel.payloadMutableLiveData.getValue().destinationAccount
                    viewmodelPayloadMutableLiveDataDestinationAccount = viewmodelPayloadMutableLiveDataGetValue.getDestinationAccount();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.enterMobileNumber, viewmodelPayloadMutableLiveDataDestinationAccount);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.priceTextbox, viewmodelPayloadMutableLiveDataStringAmount);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.enterMobileNumber, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, enterMobileNumberandroidTextAttrChanged);
            this.mboundView3.setOnClickListener(mCallback9);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.priceTextbox, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, priceTextboxandroidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewmodel != null
        boolean viewmodelJavaLangObjectNull = false;
        // viewmodel
        com.woleapp.viewmodels.UtilitiesViewModel viewmodel = mViewmodel;



        viewmodelJavaLangObjectNull = (viewmodel) != (null);
        if (viewmodelJavaLangObjectNull) {


            viewmodel.validateUtilityBill();
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewmodel.payloadMutableLiveData
        flag 1 (0x2L): viewmodel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}