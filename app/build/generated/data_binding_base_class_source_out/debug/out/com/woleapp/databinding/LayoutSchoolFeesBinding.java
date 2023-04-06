// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import com.woleapp.ui.widgets.CustomEditText;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutSchoolFeesBinding extends ViewDataBinding {
  @NonNull
  public final Button btnContinue;

  @NonNull
  public final CustomEditText etAmt;

  @NonNull
  public final EditText etFee;

  @NonNull
  public final EditText etIDNo;

  @NonNull
  public final LinearLayout linearCanvas;

  @NonNull
  public final LinearLayout linearTitle;

  @NonNull
  public final ScrollView scrollView;

  @NonNull
  public final AppCompatSpinner spnIdType;

  @NonNull
  public final AppCompatSpinner spnSchools;

  @NonNull
  public final TextView tvRetake;

  @NonNull
  public final TextView tvSign;

  @NonNull
  public final TextView tvTitle;

  protected LayoutSchoolFeesBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnContinue, CustomEditText etAmt, EditText etFee, EditText etIDNo,
      LinearLayout linearCanvas, LinearLayout linearTitle, ScrollView scrollView,
      AppCompatSpinner spnIdType, AppCompatSpinner spnSchools, TextView tvRetake, TextView tvSign,
      TextView tvTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnContinue = btnContinue;
    this.etAmt = etAmt;
    this.etFee = etFee;
    this.etIDNo = etIDNo;
    this.linearCanvas = linearCanvas;
    this.linearTitle = linearTitle;
    this.scrollView = scrollView;
    this.spnIdType = spnIdType;
    this.spnSchools = spnSchools;
    this.tvRetake = tvRetake;
    this.tvSign = tvSign;
    this.tvTitle = tvTitle;
  }

  @NonNull
  public static LayoutSchoolFeesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_school_fees, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutSchoolFeesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutSchoolFeesBinding>inflateInternal(inflater, R.layout.layout_school_fees, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutSchoolFeesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_school_fees, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutSchoolFeesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutSchoolFeesBinding>inflateInternal(inflater, R.layout.layout_school_fees, null, false, component);
  }

  public static LayoutSchoolFeesBinding bind(@NonNull View view) {
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
  public static LayoutSchoolFeesBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutSchoolFeesBinding)bind(component, view, R.layout.layout_school_fees);
  }
}