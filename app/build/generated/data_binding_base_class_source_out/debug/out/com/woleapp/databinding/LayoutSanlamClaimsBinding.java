// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutSanlamClaimsBinding extends ViewDataBinding {
  @NonNull
  public final Button btnSubmit;

  @NonNull
  public final LinearLayout causeOfDeath;

  @NonNull
  public final EditText date;

  @NonNull
  public final RadioButton no;

  @NonNull
  public final RadioGroup option;

  @NonNull
  public final EditText phoneNumber;

  @NonNull
  public final ScrollView scrollView;

  @NonNull
  public final LinearLayout situationDescription;

  @NonNull
  public final AppCompatSpinner spnCauseOfDeath;

  @NonNull
  public final AppCompatSpinner spnClaim;

  @NonNull
  public final TextView tvTitle;

  @NonNull
  public final RadioButton yes;

  protected LayoutSanlamClaimsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnSubmit, LinearLayout causeOfDeath, EditText date, RadioButton no, RadioGroup option,
      EditText phoneNumber, ScrollView scrollView, LinearLayout situationDescription,
      AppCompatSpinner spnCauseOfDeath, AppCompatSpinner spnClaim, TextView tvTitle,
      RadioButton yes) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnSubmit = btnSubmit;
    this.causeOfDeath = causeOfDeath;
    this.date = date;
    this.no = no;
    this.option = option;
    this.phoneNumber = phoneNumber;
    this.scrollView = scrollView;
    this.situationDescription = situationDescription;
    this.spnCauseOfDeath = spnCauseOfDeath;
    this.spnClaim = spnClaim;
    this.tvTitle = tvTitle;
    this.yes = yes;
  }

  @NonNull
  public static LayoutSanlamClaimsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_sanlam_claims, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutSanlamClaimsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutSanlamClaimsBinding>inflateInternal(inflater, R.layout.layout_sanlam_claims, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutSanlamClaimsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_sanlam_claims, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutSanlamClaimsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutSanlamClaimsBinding>inflateInternal(inflater, R.layout.layout_sanlam_claims, null, false, component);
  }

  public static LayoutSanlamClaimsBinding bind(@NonNull View view) {
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
  public static LayoutSanlamClaimsBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutSanlamClaimsBinding)bind(component, view, R.layout.layout_sanlam_claims);
  }
}
