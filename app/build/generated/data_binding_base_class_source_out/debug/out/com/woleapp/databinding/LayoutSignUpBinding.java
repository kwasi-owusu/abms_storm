// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutSignUpBinding extends ViewDataBinding {
  @NonNull
  public final CardView btnAgent;

  @NonNull
  public final Button btnLogin;

  @NonNull
  public final CardView btnMerchant;

  @NonNull
  public final LinearLayout container;

  @NonNull
  public final FrameLayout linearFields;

  @NonNull
  public final FrameLayout parentFrame;

  @NonNull
  public final ScrollView scrollView;

  @NonNull
  public final LayoutAgentBinding signUpLayout;

  @NonNull
  public final TextView tvNoAccount;

  @NonNull
  public final TextView tvSignUp;

  @NonNull
  public final TextView tvTitle;

  protected LayoutSignUpBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CardView btnAgent, Button btnLogin, CardView btnMerchant, LinearLayout container,
      FrameLayout linearFields, FrameLayout parentFrame, ScrollView scrollView,
      LayoutAgentBinding signUpLayout, TextView tvNoAccount, TextView tvSignUp, TextView tvTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnAgent = btnAgent;
    this.btnLogin = btnLogin;
    this.btnMerchant = btnMerchant;
    this.container = container;
    this.linearFields = linearFields;
    this.parentFrame = parentFrame;
    this.scrollView = scrollView;
    this.signUpLayout = signUpLayout;
    this.tvNoAccount = tvNoAccount;
    this.tvSignUp = tvSignUp;
    this.tvTitle = tvTitle;
  }

  @NonNull
  public static LayoutSignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_sign_up, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutSignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutSignUpBinding>inflateInternal(inflater, R.layout.layout_sign_up, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutSignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_sign_up, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutSignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutSignUpBinding>inflateInternal(inflater, R.layout.layout_sign_up, null, false, component);
  }

  public static LayoutSignUpBinding bind(@NonNull View view) {
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
  public static LayoutSignUpBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutSignUpBinding)bind(component, view, R.layout.layout_sign_up);
  }
}