// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import com.woleapp.viewmodels.UtilitiesViewModel;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutAirtimeOrDataBinding extends ViewDataBinding {
  @NonNull
  public final EditText confirmMobileNumber;

  @NonNull
  public final TextView currencyTextbox;

  @NonNull
  public final Spinner dataOrAirtimeSpinner;

  @NonNull
  public final ImageView dataOrAirtimeSpinnerIcon;

  @NonNull
  public final EditText enterMobileNumber;

  @NonNull
  public final TextView header;

  @NonNull
  public final Button pay;

  @NonNull
  public final LinearLayout priceInputLayout;

  @NonNull
  public final EditText priceTextbox;

  @NonNull
  public final Spinner selectDataBundleSpinner;

  @NonNull
  public final ImageView selectDataBundleSpinnerIcon;

  @NonNull
  public final Spinner selectNetworkSpinner;

  @Bindable
  protected UtilitiesViewModel mViewmodel;

  protected LayoutAirtimeOrDataBinding(Object _bindingComponent, View _root, int _localFieldCount,
      EditText confirmMobileNumber, TextView currencyTextbox, Spinner dataOrAirtimeSpinner,
      ImageView dataOrAirtimeSpinnerIcon, EditText enterMobileNumber, TextView header, Button pay,
      LinearLayout priceInputLayout, EditText priceTextbox, Spinner selectDataBundleSpinner,
      ImageView selectDataBundleSpinnerIcon, Spinner selectNetworkSpinner) {
    super(_bindingComponent, _root, _localFieldCount);
    this.confirmMobileNumber = confirmMobileNumber;
    this.currencyTextbox = currencyTextbox;
    this.dataOrAirtimeSpinner = dataOrAirtimeSpinner;
    this.dataOrAirtimeSpinnerIcon = dataOrAirtimeSpinnerIcon;
    this.enterMobileNumber = enterMobileNumber;
    this.header = header;
    this.pay = pay;
    this.priceInputLayout = priceInputLayout;
    this.priceTextbox = priceTextbox;
    this.selectDataBundleSpinner = selectDataBundleSpinner;
    this.selectDataBundleSpinnerIcon = selectDataBundleSpinnerIcon;
    this.selectNetworkSpinner = selectNetworkSpinner;
  }

  public abstract void setViewmodel(@Nullable UtilitiesViewModel viewmodel);

  @Nullable
  public UtilitiesViewModel getViewmodel() {
    return mViewmodel;
  }

  @NonNull
  public static LayoutAirtimeOrDataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_airtime_or_data, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutAirtimeOrDataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutAirtimeOrDataBinding>inflateInternal(inflater, R.layout.layout_airtime_or_data, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutAirtimeOrDataBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_airtime_or_data, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutAirtimeOrDataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutAirtimeOrDataBinding>inflateInternal(inflater, R.layout.layout_airtime_or_data, null, false, component);
  }

  public static LayoutAirtimeOrDataBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static LayoutAirtimeOrDataBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutAirtimeOrDataBinding)bind(component, view, R.layout.layout_airtime_or_data);
  }
}
