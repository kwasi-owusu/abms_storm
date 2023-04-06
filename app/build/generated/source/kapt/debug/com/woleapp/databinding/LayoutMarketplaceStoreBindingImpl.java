package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutMarketplaceStoreBindingImpl extends LayoutMarketplaceStoreBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.create_store_welcome, 4);
        sViewsWithIds.put(R.id.close_dialog, 5);
        sViewsWithIds.put(R.id.store_logo, 6);
        sViewsWithIds.put(R.id.store_name_wrapper, 7);
        sViewsWithIds.put(R.id.delivery_fee_wrapper, 8);
        sViewsWithIds.put(R.id.create_store, 9);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener deliveryFeeandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewmodel.marketPlace.getValue().deliveryFee
            //         is viewmodel.marketPlace.getValue().setDeliveryFee((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(deliveryFee);
            // localize variables for thread safety
            // viewmodel.marketPlace.getValue()
            com.woleapp.model.Marketplace viewmodelMarketPlaceGetValue = null;
            // viewmodel != null
            boolean viewmodelJavaLangObjectNull = false;
            // viewmodel.marketPlace.getValue() != null
            boolean viewmodelMarketPlaceGetValueJavaLangObjectNull = false;
            // viewmodel.marketPlace
            androidx.lifecycle.MutableLiveData<com.woleapp.model.Marketplace> viewmodelMarketPlace = null;
            // viewmodel.marketPlace != null
            boolean viewmodelMarketPlaceJavaLangObjectNull = false;
            // viewmodel.marketPlace.getValue().deliveryFee
            java.lang.String viewmodelMarketPlaceDeliveryFee = null;
            // viewmodel
            com.woleapp.viewmodels.MarketplaceViewModel viewmodel = mViewmodel;



            viewmodelJavaLangObjectNull = (viewmodel) != (null);
            if (viewmodelJavaLangObjectNull) {


                viewmodelMarketPlace = viewmodel.getMarketPlace();

                viewmodelMarketPlaceJavaLangObjectNull = (viewmodelMarketPlace) != (null);
                if (viewmodelMarketPlaceJavaLangObjectNull) {


                    viewmodelMarketPlaceGetValue = viewmodelMarketPlace.getValue();

                    viewmodelMarketPlaceGetValueJavaLangObjectNull = (viewmodelMarketPlaceGetValue) != (null);
                    if (viewmodelMarketPlaceGetValueJavaLangObjectNull) {




                        viewmodelMarketPlaceGetValue.setDeliveryFee(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener storeDescriptionandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewmodel.marketPlace.getValue().description
            //         is viewmodel.marketPlace.getValue().setDescription((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(storeDescription);
            // localize variables for thread safety
            // viewmodel.marketPlace.getValue()
            com.woleapp.model.Marketplace viewmodelMarketPlaceGetValue = null;
            // viewmodel != null
            boolean viewmodelJavaLangObjectNull = false;
            // viewmodel.marketPlace.getValue() != null
            boolean viewmodelMarketPlaceGetValueJavaLangObjectNull = false;
            // viewmodel.marketPlace
            androidx.lifecycle.MutableLiveData<com.woleapp.model.Marketplace> viewmodelMarketPlace = null;
            // viewmodel.marketPlace != null
            boolean viewmodelMarketPlaceJavaLangObjectNull = false;
            // viewmodel
            com.woleapp.viewmodels.MarketplaceViewModel viewmodel = mViewmodel;
            // viewmodel.marketPlace.getValue().description
            java.lang.String viewmodelMarketPlaceDescription = null;



            viewmodelJavaLangObjectNull = (viewmodel) != (null);
            if (viewmodelJavaLangObjectNull) {


                viewmodelMarketPlace = viewmodel.getMarketPlace();

                viewmodelMarketPlaceJavaLangObjectNull = (viewmodelMarketPlace) != (null);
                if (viewmodelMarketPlaceJavaLangObjectNull) {


                    viewmodelMarketPlaceGetValue = viewmodelMarketPlace.getValue();

                    viewmodelMarketPlaceGetValueJavaLangObjectNull = (viewmodelMarketPlaceGetValue) != (null);
                    if (viewmodelMarketPlaceGetValueJavaLangObjectNull) {




                        viewmodelMarketPlaceGetValue.setDescription(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener storeNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewmodel.marketPlace.getValue().name
            //         is viewmodel.marketPlace.getValue().setName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(storeName);
            // localize variables for thread safety
            // viewmodel.marketPlace.getValue()
            com.woleapp.model.Marketplace viewmodelMarketPlaceGetValue = null;
            // viewmodel != null
            boolean viewmodelJavaLangObjectNull = false;
            // viewmodel.marketPlace.getValue().name
            java.lang.String viewmodelMarketPlaceName = null;
            // viewmodel.marketPlace.getValue() != null
            boolean viewmodelMarketPlaceGetValueJavaLangObjectNull = false;
            // viewmodel.marketPlace
            androidx.lifecycle.MutableLiveData<com.woleapp.model.Marketplace> viewmodelMarketPlace = null;
            // viewmodel.marketPlace != null
            boolean viewmodelMarketPlaceJavaLangObjectNull = false;
            // viewmodel
            com.woleapp.viewmodels.MarketplaceViewModel viewmodel = mViewmodel;



            viewmodelJavaLangObjectNull = (viewmodel) != (null);
            if (viewmodelJavaLangObjectNull) {


                viewmodelMarketPlace = viewmodel.getMarketPlace();

                viewmodelMarketPlaceJavaLangObjectNull = (viewmodelMarketPlace) != (null);
                if (viewmodelMarketPlaceJavaLangObjectNull) {


                    viewmodelMarketPlaceGetValue = viewmodelMarketPlace.getValue();

                    viewmodelMarketPlaceGetValueJavaLangObjectNull = (viewmodelMarketPlaceGetValue) != (null);
                    if (viewmodelMarketPlaceGetValueJavaLangObjectNull) {




                        viewmodelMarketPlaceGetValue.setName(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };

    public LayoutMarketplaceStoreBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private LayoutMarketplaceStoreBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ImageButton) bindings[5]
            , (android.widget.Button) bindings[9]
            , (android.widget.TextView) bindings[4]
            , (android.widget.EditText) bindings[2]
            , (com.google.android.material.textfield.TextInputLayout) bindings[8]
            , (android.widget.EditText) bindings[3]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.EditText) bindings[1]
            , (com.google.android.material.textfield.TextInputLayout) bindings[7]
            );
        this.deliveryFee.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.storeDescription.setTag(null);
        this.storeName.setTag(null);
        setRootTag(root);
        // listeners
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
            setViewmodel((com.woleapp.viewmodels.MarketplaceViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewmodel(@Nullable com.woleapp.viewmodels.MarketplaceViewModel Viewmodel) {
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
                return onChangeViewmodelMarketPlace((androidx.lifecycle.MutableLiveData<com.woleapp.model.Marketplace>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewmodelMarketPlace(androidx.lifecycle.MutableLiveData<com.woleapp.model.Marketplace> ViewmodelMarketPlace, int fieldId) {
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
        java.lang.String viewmodelMarketPlaceDeliveryFee = null;
        java.lang.String viewmodelMarketPlaceDescription = null;
        com.woleapp.model.Marketplace viewmodelMarketPlaceGetValue = null;
        java.lang.String viewmodelMarketPlaceName = null;
        androidx.lifecycle.MutableLiveData<com.woleapp.model.Marketplace> viewmodelMarketPlace = null;
        com.woleapp.viewmodels.MarketplaceViewModel viewmodel = mViewmodel;

        if ((dirtyFlags & 0x7L) != 0) {



                if (viewmodel != null) {
                    // read viewmodel.marketPlace
                    viewmodelMarketPlace = viewmodel.getMarketPlace();
                }
                updateLiveDataRegistration(0, viewmodelMarketPlace);


                if (viewmodelMarketPlace != null) {
                    // read viewmodel.marketPlace.getValue()
                    viewmodelMarketPlaceGetValue = viewmodelMarketPlace.getValue();
                }


                if (viewmodelMarketPlaceGetValue != null) {
                    // read viewmodel.marketPlace.getValue().deliveryFee
                    viewmodelMarketPlaceDeliveryFee = viewmodelMarketPlaceGetValue.getDeliveryFee();
                    // read viewmodel.marketPlace.getValue().description
                    viewmodelMarketPlaceDescription = viewmodelMarketPlaceGetValue.getDescription();
                    // read viewmodel.marketPlace.getValue().name
                    viewmodelMarketPlaceName = viewmodelMarketPlaceGetValue.getName();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.deliveryFee, viewmodelMarketPlaceDeliveryFee);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.storeDescription, viewmodelMarketPlaceDescription);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.storeName, viewmodelMarketPlaceName);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.deliveryFee, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, deliveryFeeandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.storeDescription, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, storeDescriptionandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.storeName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, storeNameandroidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewmodel.marketPlace
        flag 1 (0x2L): viewmodel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}