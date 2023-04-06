// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public abstract class LayoutPowerOrElectricityBinding extends ViewDataBinding {
  @NonNull
  public final EditText enterEmailAddress;

  @NonNull
  public final EditText enterMeterNumber;

  @NonNull
  public final EditText enterMobileNumber;

  @NonNull
  public final TextView header;

  @NonNull
  public final Spinner meterType;

  @NonNull
  public final LinearLayout priceInputLayout;

  @NonNull
  public final Spinner productsSpinner;

  @NonNull
  public final Spinner selectACategory;

  @Bindable
  protected UtilitiesViewModel mViewmodel;

  protected LayoutPowerOrElectricityBinding(Object _bindingComponent, View _root,
      int _localFieldCount, EditText enterEmailAddress, EditText enterMeterNumber,
      EditText enterMobileNumber, TextView header, Spinner meterType, LinearLayout priceInputLayout,
      Spinner productsSpinner, Spinner selectACategory) {
    super(_bindingComponent, _root, _localFieldCount);
    this.enterEmailAddress = enterEmailAddress;
    this.enterMeterNumber = enterMeterNumber;
    this.enterMobileNumber = enterMobileNumber;
    this.header = header;
    this.meterType = meterType;
    this.priceInputLayout = priceInputLayout;
    this.productsSpinner = productsSpinner;
    this.selectACategory = selectACategory;
  }

  public abstract void setViewmodel(@Nullable UtilitiesViewModel viewmodel);

  @Nullable
  public UtilitiesViewModel getViewmodel() {
    return mViewmodel;
  }

  @NonNull
  public static LayoutPowerOrElectricityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_power_or_electricity, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutPowerOrElectricityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutPowerOrElectricityBinding>inflateInternal(inflater, R.layout.layout_power_or_electricity, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutPowerOrElectricityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_power_or_electricity, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutPowerOrElectricityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutPowerOrElectricityBinding>inflateInternal(inflater, R.layout.layout_power_or_electricity, null, false, component);
  }

  public static LayoutPowerOrElectricityBinding bind(@NonNull View view) {
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
  public static LayoutPowerOrElectricityBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (LayoutPowerOrElectricityBinding)bind(component, view, R.layout.layout_power_or_electricity);
  }
}
