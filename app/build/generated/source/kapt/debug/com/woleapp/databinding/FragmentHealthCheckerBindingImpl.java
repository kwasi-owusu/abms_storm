package com.woleapp.databinding;
import com.woleapp.R;
import com.woleapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentHealthCheckerBindingImpl extends FragmentHealthCheckerBinding implements com.woleapp.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.health_checker_header, 14);
        sViewsWithIds.put(R.id.header_line, 15);
        sViewsWithIds.put(R.id.note, 16);
        sViewsWithIds.put(R.id.note_line, 17);
        sViewsWithIds.put(R.id.type_of_case_header, 18);
        sViewsWithIds.put(R.id.type_of_case_note, 19);
        sViewsWithIds.put(R.id.new_or_follow_up_option, 20);
        sViewsWithIds.put(R.id.follow_up_code, 21);
        sViewsWithIds.put(R.id.customers_details, 22);
        sViewsWithIds.put(R.id.customer_details_case_note, 23);
        sViewsWithIds.put(R.id.lastname, 24);
        sViewsWithIds.put(R.id.firstname, 25);
        sViewsWithIds.put(R.id.dateofbirth, 26);
        sViewsWithIds.put(R.id.telephone, 27);
        sViewsWithIds.put(R.id.gender_header, 28);
        sViewsWithIds.put(R.id.gender_option, 29);
        sViewsWithIds.put(R.id.communication_method_header, 30);
        sViewsWithIds.put(R.id.communication_method_note, 31);
        sViewsWithIds.put(R.id.communication_option, 32);
    }
    // views
    @NonNull
    private final androidx.core.widget.NestedScrollView mboundView0;
    @NonNull
    private final com.google.android.material.textfield.TextInputEditText mboundView3;
    @NonNull
    private final com.google.android.material.textfield.TextInputEditText mboundView4;
    @NonNull
    private final com.google.android.material.textfield.TextInputEditText mboundView5;
    @NonNull
    private final com.google.android.material.textfield.TextInputEditText mboundView7;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback6;
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    @Nullable
    private final android.view.View.OnClickListener mCallback5;
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    @Nullable
    private final android.view.View.OnClickListener mCallback4;
    @Nullable
    private final android.view.View.OnClickListener mCallback7;
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener dobandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.healthCheckerModel.getValue().dateOfBirth
            //         is viewModel.healthCheckerModel.getValue().setDateOfBirth((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(dob);
            // localize variables for thread safety
            // viewModel.healthCheckerModel.getValue().dateOfBirth
            java.lang.String viewModelHealthCheckerModelDateOfBirth = null;
            // viewModel.healthCheckerModel
            androidx.lifecycle.MutableLiveData<com.woleapp.model.HealthCheckerModel> viewModelHealthCheckerModel = null;
            // viewModel.healthCheckerModel.getValue()
            com.woleapp.model.HealthCheckerModel viewModelHealthCheckerModelGetValue = null;
            // viewModel.healthCheckerModel.getValue() != null
            boolean viewModelHealthCheckerModelGetValueJavaLangObjectNull = false;
            // viewModel
            com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.healthCheckerModel != null
            boolean viewModelHealthCheckerModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelHealthCheckerModel = viewModel.getHealthCheckerModel();

                viewModelHealthCheckerModelJavaLangObjectNull = (viewModelHealthCheckerModel) != (null);
                if (viewModelHealthCheckerModelJavaLangObjectNull) {


                    viewModelHealthCheckerModelGetValue = viewModelHealthCheckerModel.getValue();

                    viewModelHealthCheckerModelGetValueJavaLangObjectNull = (viewModelHealthCheckerModelGetValue) != (null);
                    if (viewModelHealthCheckerModelGetValueJavaLangObjectNull) {




                        viewModelHealthCheckerModelGetValue.setDateOfBirth(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener mboundView3androidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.healthCheckerModel.getValue().followUpToken
            //         is viewModel.healthCheckerModel.getValue().setFollowUpToken((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView3);
            // localize variables for thread safety
            // viewModel.healthCheckerModel
            androidx.lifecycle.MutableLiveData<com.woleapp.model.HealthCheckerModel> viewModelHealthCheckerModel = null;
            // viewModel.healthCheckerModel.getValue()
            com.woleapp.model.HealthCheckerModel viewModelHealthCheckerModelGetValue = null;
            // viewModel.healthCheckerModel.getValue().followUpToken
            java.lang.String viewModelHealthCheckerModelFollowUpToken = null;
            // viewModel.healthCheckerModel.getValue() != null
            boolean viewModelHealthCheckerModelGetValueJavaLangObjectNull = false;
            // viewModel
            com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.healthCheckerModel != null
            boolean viewModelHealthCheckerModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelHealthCheckerModel = viewModel.getHealthCheckerModel();

                viewModelHealthCheckerModelJavaLangObjectNull = (viewModelHealthCheckerModel) != (null);
                if (viewModelHealthCheckerModelJavaLangObjectNull) {


                    viewModelHealthCheckerModelGetValue = viewModelHealthCheckerModel.getValue();

                    viewModelHealthCheckerModelGetValueJavaLangObjectNull = (viewModelHealthCheckerModelGetValue) != (null);
                    if (viewModelHealthCheckerModelGetValueJavaLangObjectNull) {




                        viewModelHealthCheckerModelGetValue.setFollowUpToken(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener mboundView4androidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.healthCheckerModel.getValue().lastName
            //         is viewModel.healthCheckerModel.getValue().setLastName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView4);
            // localize variables for thread safety
            // viewModel.healthCheckerModel.getValue().lastName
            java.lang.String viewModelHealthCheckerModelLastName = null;
            // viewModel.healthCheckerModel
            androidx.lifecycle.MutableLiveData<com.woleapp.model.HealthCheckerModel> viewModelHealthCheckerModel = null;
            // viewModel.healthCheckerModel.getValue()
            com.woleapp.model.HealthCheckerModel viewModelHealthCheckerModelGetValue = null;
            // viewModel.healthCheckerModel.getValue() != null
            boolean viewModelHealthCheckerModelGetValueJavaLangObjectNull = false;
            // viewModel
            com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.healthCheckerModel != null
            boolean viewModelHealthCheckerModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelHealthCheckerModel = viewModel.getHealthCheckerModel();

                viewModelHealthCheckerModelJavaLangObjectNull = (viewModelHealthCheckerModel) != (null);
                if (viewModelHealthCheckerModelJavaLangObjectNull) {


                    viewModelHealthCheckerModelGetValue = viewModelHealthCheckerModel.getValue();

                    viewModelHealthCheckerModelGetValueJavaLangObjectNull = (viewModelHealthCheckerModelGetValue) != (null);
                    if (viewModelHealthCheckerModelGetValueJavaLangObjectNull) {




                        viewModelHealthCheckerModelGetValue.setLastName(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener mboundView5androidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.healthCheckerModel.getValue().firstName
            //         is viewModel.healthCheckerModel.getValue().setFirstName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView5);
            // localize variables for thread safety
            // viewModel.healthCheckerModel
            androidx.lifecycle.MutableLiveData<com.woleapp.model.HealthCheckerModel> viewModelHealthCheckerModel = null;
            // viewModel.healthCheckerModel.getValue()
            com.woleapp.model.HealthCheckerModel viewModelHealthCheckerModelGetValue = null;
            // viewModel.healthCheckerModel.getValue().firstName
            java.lang.String viewModelHealthCheckerModelFirstName = null;
            // viewModel.healthCheckerModel.getValue() != null
            boolean viewModelHealthCheckerModelGetValueJavaLangObjectNull = false;
            // viewModel
            com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.healthCheckerModel != null
            boolean viewModelHealthCheckerModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelHealthCheckerModel = viewModel.getHealthCheckerModel();

                viewModelHealthCheckerModelJavaLangObjectNull = (viewModelHealthCheckerModel) != (null);
                if (viewModelHealthCheckerModelJavaLangObjectNull) {


                    viewModelHealthCheckerModelGetValue = viewModelHealthCheckerModel.getValue();

                    viewModelHealthCheckerModelGetValueJavaLangObjectNull = (viewModelHealthCheckerModelGetValue) != (null);
                    if (viewModelHealthCheckerModelGetValueJavaLangObjectNull) {




                        viewModelHealthCheckerModelGetValue.setFirstName(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener mboundView7androidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.healthCheckerModel.getValue().phone
            //         is viewModel.healthCheckerModel.getValue().setPhone((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView7);
            // localize variables for thread safety
            // viewModel.healthCheckerModel
            androidx.lifecycle.MutableLiveData<com.woleapp.model.HealthCheckerModel> viewModelHealthCheckerModel = null;
            // viewModel.healthCheckerModel.getValue()
            com.woleapp.model.HealthCheckerModel viewModelHealthCheckerModelGetValue = null;
            // viewModel.healthCheckerModel.getValue() != null
            boolean viewModelHealthCheckerModelGetValueJavaLangObjectNull = false;
            // viewModel
            com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.healthCheckerModel.getValue().phone
            java.lang.String viewModelHealthCheckerModelPhone = null;
            // viewModel.healthCheckerModel != null
            boolean viewModelHealthCheckerModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelHealthCheckerModel = viewModel.getHealthCheckerModel();

                viewModelHealthCheckerModelJavaLangObjectNull = (viewModelHealthCheckerModel) != (null);
                if (viewModelHealthCheckerModelJavaLangObjectNull) {


                    viewModelHealthCheckerModelGetValue = viewModelHealthCheckerModel.getValue();

                    viewModelHealthCheckerModelGetValueJavaLangObjectNull = (viewModelHealthCheckerModelGetValue) != (null);
                    if (viewModelHealthCheckerModelGetValueJavaLangObjectNull) {




                        viewModelHealthCheckerModelGetValue.setPhone(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };

    public FragmentHealthCheckerBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 33, sIncludes, sViewsWithIds));
    }
    private FragmentHealthCheckerBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.widget.TextView) bindings[30]
            , (android.widget.TextView) bindings[31]
            , (android.widget.RadioGroup) bindings[32]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[22]
            , (com.google.android.material.textfield.TextInputLayout) bindings[26]
            , (com.google.android.material.textfield.TextInputEditText) bindings[6]
            , (android.widget.RadioButton) bindings[9]
            , (com.google.android.material.textfield.TextInputLayout) bindings[25]
            , (android.widget.RadioButton) bindings[2]
            , (com.google.android.material.textfield.TextInputLayout) bindings[21]
            , (android.widget.TextView) bindings[28]
            , (android.widget.RadioGroup) bindings[29]
            , (android.view.View) bindings[15]
            , (android.widget.ImageView) bindings[14]
            , (com.google.android.material.textfield.TextInputLayout) bindings[24]
            , (android.widget.RadioButton) bindings[8]
            , (android.widget.RadioButton) bindings[1]
            , (android.widget.RadioGroup) bindings[20]
            , (android.widget.TextView) bindings[16]
            , (android.view.View) bindings[17]
            , (android.widget.RadioButton) bindings[11]
            , (android.widget.ProgressBar) bindings[13]
            , (android.widget.RadioButton) bindings[10]
            , (android.widget.Button) bindings[12]
            , (com.google.android.material.textfield.TextInputLayout) bindings[27]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[19]
            );
        this.dob.setTag(null);
        this.female.setTag(null);
        this.followUpCase.setTag(null);
        this.male.setTag(null);
        this.mboundView0 = (androidx.core.widget.NestedScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView3 = (com.google.android.material.textfield.TextInputEditText) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (com.google.android.material.textfield.TextInputEditText) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (com.google.android.material.textfield.TextInputEditText) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView7 = (com.google.android.material.textfield.TextInputEditText) bindings[7];
        this.mboundView7.setTag(null);
        this.newCase.setTag(null);
        this.phoneCall.setTag(null);
        this.progress.setTag(null);
        this.sms.setTag(null);
        this.submitButton.setTag(null);
        setRootTag(root);
        // listeners
        mCallback6 = new com.woleapp.generated.callback.OnClickListener(this, 6);
        mCallback2 = new com.woleapp.generated.callback.OnClickListener(this, 2);
        mCallback5 = new com.woleapp.generated.callback.OnClickListener(this, 5);
        mCallback1 = new com.woleapp.generated.callback.OnClickListener(this, 1);
        mCallback4 = new com.woleapp.generated.callback.OnClickListener(this, 4);
        mCallback7 = new com.woleapp.generated.callback.OnClickListener(this, 7);
        mCallback3 = new com.woleapp.generated.callback.OnClickListener(this, 3);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
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
        if (BR.viewModel == variableId) {
            setViewModel((com.woleapp.viewmodels.HealthCheckerViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.woleapp.viewmodels.HealthCheckerViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelHealthCheckerModel((androidx.lifecycle.MutableLiveData<com.woleapp.model.HealthCheckerModel>) object, fieldId);
            case 1 :
                return onChangeViewModelInProgress((androidx.lifecycle.MutableLiveData<java.lang.Boolean>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelHealthCheckerModel(androidx.lifecycle.MutableLiveData<com.woleapp.model.HealthCheckerModel> ViewModelHealthCheckerModel, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelInProgress(androidx.lifecycle.MutableLiveData<java.lang.Boolean> ViewModelInProgress, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
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
        java.lang.String viewModelGenderCheckId = null;
        boolean viewModelPrefCommIdEqualsJavaLangStringSMS = false;
        java.lang.String viewModelHealthCheckerModelDateOfBirth = null;
        java.lang.String viewModelHealthCheckerModelPhone = null;
        boolean viewModelGenderCheckIdEqualsJavaLangStringMale = false;
        com.woleapp.model.HealthCheckerModel viewModelHealthCheckerModelGetValue = null;
        java.lang.String viewModelHealthCheckerModelFollowUpToken = null;
        boolean viewModelGenderCheckIdEqualsJavaLangStringFemale = false;
        boolean viewModelPrefCommIdEqualsJavaLangStringPHONE = false;
        java.lang.Boolean viewModelInProgressGetValue = null;
        java.lang.String viewModelPrefCommId = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelInProgressGetValue = false;
        java.lang.String viewModelInitialOrFollowUpCheck = null;
        androidx.lifecycle.MutableLiveData<com.woleapp.model.HealthCheckerModel> viewModelHealthCheckerModel = null;
        java.lang.String viewModelHealthCheckerModelLastName = null;
        boolean viewModelInitialOrFollowUpCheckEqualsJavaLangStringFollowup = false;
        java.lang.String viewModelHealthCheckerModelFirstName = null;
        boolean viewModelInitialOrFollowUpCheckEqualsJavaLangStringInitial = false;
        com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> viewModelInProgress = null;

        if ((dirtyFlags & 0xfL) != 0) {


            if ((dirtyFlags & 0xcL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.genderCheckId
                        viewModelGenderCheckId = viewModel.getGenderCheckId();
                        // read viewModel.prefCommId
                        viewModelPrefCommId = viewModel.getPrefCommId();
                        // read viewModel.initialOrFollowUpCheck
                        viewModelInitialOrFollowUpCheck = viewModel.getInitialOrFollowUpCheck();
                    }


                    if (viewModelGenderCheckId != null) {
                        // read viewModel.genderCheckId.equals("male")
                        viewModelGenderCheckIdEqualsJavaLangStringMale = viewModelGenderCheckId.equals("male");
                        // read viewModel.genderCheckId.equals("female")
                        viewModelGenderCheckIdEqualsJavaLangStringFemale = viewModelGenderCheckId.equals("female");
                    }
                    if (viewModelPrefCommId != null) {
                        // read viewModel.prefCommId.equals("SMS")
                        viewModelPrefCommIdEqualsJavaLangStringSMS = viewModelPrefCommId.equals("SMS");
                        // read viewModel.prefCommId.equals("PHONE")
                        viewModelPrefCommIdEqualsJavaLangStringPHONE = viewModelPrefCommId.equals("PHONE");
                    }
                    if (viewModelInitialOrFollowUpCheck != null) {
                        // read viewModel.initialOrFollowUpCheck.equals("followup")
                        viewModelInitialOrFollowUpCheckEqualsJavaLangStringFollowup = viewModelInitialOrFollowUpCheck.equals("followup");
                        // read viewModel.initialOrFollowUpCheck.equals("Initial")
                        viewModelInitialOrFollowUpCheckEqualsJavaLangStringInitial = viewModelInitialOrFollowUpCheck.equals("Initial");
                    }
            }
            if ((dirtyFlags & 0xdL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.healthCheckerModel
                        viewModelHealthCheckerModel = viewModel.getHealthCheckerModel();
                    }
                    updateLiveDataRegistration(0, viewModelHealthCheckerModel);


                    if (viewModelHealthCheckerModel != null) {
                        // read viewModel.healthCheckerModel.getValue()
                        viewModelHealthCheckerModelGetValue = viewModelHealthCheckerModel.getValue();
                    }


                    if (viewModelHealthCheckerModelGetValue != null) {
                        // read viewModel.healthCheckerModel.getValue().dateOfBirth
                        viewModelHealthCheckerModelDateOfBirth = viewModelHealthCheckerModelGetValue.getDateOfBirth();
                        // read viewModel.healthCheckerModel.getValue().phone
                        viewModelHealthCheckerModelPhone = viewModelHealthCheckerModelGetValue.getPhone();
                        // read viewModel.healthCheckerModel.getValue().followUpToken
                        viewModelHealthCheckerModelFollowUpToken = viewModelHealthCheckerModelGetValue.getFollowUpToken();
                        // read viewModel.healthCheckerModel.getValue().lastName
                        viewModelHealthCheckerModelLastName = viewModelHealthCheckerModelGetValue.getLastName();
                        // read viewModel.healthCheckerModel.getValue().firstName
                        viewModelHealthCheckerModelFirstName = viewModelHealthCheckerModelGetValue.getFirstName();
                    }
            }
            if ((dirtyFlags & 0xeL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.inProgress
                        viewModelInProgress = viewModel.getInProgress();
                    }
                    updateLiveDataRegistration(1, viewModelInProgress);


                    if (viewModelInProgress != null) {
                        // read viewModel.inProgress.getValue()
                        viewModelInProgressGetValue = viewModelInProgress.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.inProgress.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelInProgressGetValue = androidx.databinding.ViewDataBinding.safeUnbox(viewModelInProgressGetValue);
            }
        }
        // batch finished
        if ((dirtyFlags & 0xdL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.dob, viewModelHealthCheckerModelDateOfBirth);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, viewModelHealthCheckerModelFollowUpToken);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, viewModelHealthCheckerModelLastName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView5, viewModelHealthCheckerModelFirstName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView7, viewModelHealthCheckerModelPhone);
        }
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.dob, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, dobandroidTextAttrChanged);
            this.female.setOnClickListener(mCallback4);
            this.followUpCase.setOnClickListener(mCallback2);
            this.male.setOnClickListener(mCallback3);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView3, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView3androidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView4, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView4androidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView5, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView5androidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView7, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView7androidTextAttrChanged);
            this.newCase.setOnClickListener(mCallback1);
            this.phoneCall.setOnClickListener(mCallback6);
            this.sms.setOnClickListener(mCallback5);
            this.submitButton.setOnClickListener(mCallback7);
        }
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.female, viewModelGenderCheckIdEqualsJavaLangStringFemale);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.followUpCase, viewModelInitialOrFollowUpCheckEqualsJavaLangStringFollowup);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.male, viewModelGenderCheckIdEqualsJavaLangStringMale);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.newCase, viewModelInitialOrFollowUpCheckEqualsJavaLangStringInitial);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.phoneCall, viewModelPrefCommIdEqualsJavaLangStringPHONE);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.sms, viewModelPrefCommIdEqualsJavaLangStringSMS);
        }
        if ((dirtyFlags & 0xeL) != 0) {
            // api target 1

            com.woleapp.util.UtilsAndExtensionsKt.progressBarInProgress(this.progress, androidxDatabindingViewDataBindingSafeUnboxViewModelInProgressGetValue);
            com.woleapp.util.UtilsAndExtensionsKt.buttonInProgress(this.submitButton, androidxDatabindingViewDataBindingSafeUnboxViewModelInProgressGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 6: {
                // localize variables for thread safety
                // viewModel
                com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {



                    viewModel.setPrefCommId("PHONE");
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // viewModel
                com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {



                    viewModel.setInitialOrFollowUpCheck("followup");
                }
                break;
            }
            case 5: {
                // localize variables for thread safety
                // viewModel
                com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {



                    viewModel.setPrefCommId("SMS");
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // viewModel
                com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {



                    viewModel.setInitialOrFollowUpCheck("Initial");
                }
                break;
            }
            case 4: {
                // localize variables for thread safety
                // viewModel
                com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {



                    viewModel.setGenderCheckId("female");
                }
                break;
            }
            case 7: {
                // localize variables for thread safety
                // viewModel
                com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    viewModel.payAndSubmit();
                }
                break;
            }
            case 3: {
                // localize variables for thread safety
                // viewModel
                com.woleapp.viewmodels.HealthCheckerViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {



                    viewModel.setGenderCheckId("male");
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.healthCheckerModel
        flag 1 (0x2L): viewModel.inProgress
        flag 2 (0x3L): viewModel
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}