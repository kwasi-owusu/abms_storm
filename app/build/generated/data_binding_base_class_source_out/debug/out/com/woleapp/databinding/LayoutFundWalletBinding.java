// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public abstract class LayoutFundWalletBinding extends ViewDataBinding {
  @NonNull
  public final Button btnContinue;

  @NonNull
  public final EditText etFee;

  @NonNull
  public final EditText etIDNo;

  @NonNull
  public final EditText etName;

  @NonNull
  public final EditText etPrice;

  @NonNull
  public final ScrollView scrollView;

  @NonNull
  public final AppCompatSpinner spnIdType;

  @NonNull
  public final TextView tvConvinienceFee;

  @NonNull
  public final TextView tvTitle;

  protected LayoutFundWalletBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnContinue, EditText etFee, EditText etIDNo, EditText etName, EditText etPrice,
      ScrollView scrollView, AppCompatSpinner spnIdType, TextView tvConvinienceFee,
      TextView tvTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnContinue = btnContinue;
    this.etFee = etFee;
    this.etIDNo = etIDNo;
    this.etName = etName;
    this.etPrice = etPrice;
    this.scrollView = scrollView;
    this.spnIdType = spnIdType;
    this.tvConvinienceFee = tvConvinienceFee;
    this.tvTitle = tvTitle;
  }

  @NonNull
  public static LayoutFundWalletBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_fund_wallet, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutFundWalletBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutFundWalletBinding>inflateInternal(inflater, R.layout.layout_fund_wallet, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutFundWalletBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_fund_wallet, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutFundWalletBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutFundWalletBinding>inflateInternal(inflater, R.layout.layout_fund_wallet, null, false, component);
  }

  public static LayoutFundWalletBinding bind(@NonNull View view) {
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
  public static LayoutFundWalletBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutFundWalletBinding)bind(component, view, R.layout.layout_fund_wallet);
  }
}