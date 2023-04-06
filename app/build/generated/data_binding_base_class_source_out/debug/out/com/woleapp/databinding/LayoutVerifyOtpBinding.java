// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutVerifyOtpBinding extends ViewDataBinding {
  @NonNull
  public final Button btnContinue;

  @NonNull
  public final Button btnVerify;

  @NonNull
  public final EditText etOtp1;

  @NonNull
  public final EditText etOtp2;

  @NonNull
  public final EditText etOtp3;

  @NonNull
  public final EditText etOtp4;

  @NonNull
  public final ImageView ivBack;

  @NonNull
  public final ImageView ivTimer;

  @NonNull
  public final LinearLayout linearForm;

  @NonNull
  public final LinearLayout linearResend;

  @NonNull
  public final TextView tvEmail;

  @NonNull
  public final TextView tvMobileNo;

  @NonNull
  public final TextView tvResend;

  @NonNull
  public final TextView tvSeparator;

  @NonNull
  public final TextView tvTimer;

  @NonNull
  public final TextView tvTitleOtp;

  @NonNull
  public final TextView txtLogin;

  protected LayoutVerifyOtpBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnContinue, Button btnVerify, EditText etOtp1, EditText etOtp2, EditText etOtp3,
      EditText etOtp4, ImageView ivBack, ImageView ivTimer, LinearLayout linearForm,
      LinearLayout linearResend, TextView tvEmail, TextView tvMobileNo, TextView tvResend,
      TextView tvSeparator, TextView tvTimer, TextView tvTitleOtp, TextView txtLogin) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnContinue = btnContinue;
    this.btnVerify = btnVerify;
    this.etOtp1 = etOtp1;
    this.etOtp2 = etOtp2;
    this.etOtp3 = etOtp3;
    this.etOtp4 = etOtp4;
    this.ivBack = ivBack;
    this.ivTimer = ivTimer;
    this.linearForm = linearForm;
    this.linearResend = linearResend;
    this.tvEmail = tvEmail;
    this.tvMobileNo = tvMobileNo;
    this.tvResend = tvResend;
    this.tvSeparator = tvSeparator;
    this.tvTimer = tvTimer;
    this.tvTitleOtp = tvTitleOtp;
    this.txtLogin = txtLogin;
  }

  @NonNull
  public static LayoutVerifyOtpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_verify_otp, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutVerifyOtpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutVerifyOtpBinding>inflateInternal(inflater, R.layout.layout_verify_otp, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutVerifyOtpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_verify_otp, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutVerifyOtpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutVerifyOtpBinding>inflateInternal(inflater, R.layout.layout_verify_otp, null, false, component);
  }

  public static LayoutVerifyOtpBinding bind(@NonNull View view) {
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
  public static LayoutVerifyOtpBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutVerifyOtpBinding)bind(component, view, R.layout.layout_verify_otp);
  }
}