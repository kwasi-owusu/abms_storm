// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.woleapp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class AppBarMainBinding extends ViewDataBinding {
  @NonNull
  public final ImageView ivLogo;

  @NonNull
  public final LinearLayout logoutLayout;

  @NonNull
  public final AppBarLayout mainAppBar;

  @NonNull
  public final ImageButton refreshButton;

  @NonNull
  public final LinearLayout switchButton;

  @NonNull
  public final TextView switchLa;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView tvAvailableBalance;

  @NonNull
  public final TextView tvAvailableBalanceTitle;

  @NonNull
  public final TextView tvLedgerBalance;

  @NonNull
  public final TextView tvLedgerBalanceTitle;

  @NonNull
  public final TextView tvLogout;

  @NonNull
  public final TextView tvTitle;

  protected AppBarMainBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView ivLogo, LinearLayout logoutLayout, AppBarLayout mainAppBar,
      ImageButton refreshButton, LinearLayout switchButton, TextView switchLa, Toolbar toolbar,
      TextView tvAvailableBalance, TextView tvAvailableBalanceTitle, TextView tvLedgerBalance,
      TextView tvLedgerBalanceTitle, TextView tvLogout, TextView tvTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivLogo = ivLogo;
    this.logoutLayout = logoutLayout;
    this.mainAppBar = mainAppBar;
    this.refreshButton = refreshButton;
    this.switchButton = switchButton;
    this.switchLa = switchLa;
    this.toolbar = toolbar;
    this.tvAvailableBalance = tvAvailableBalance;
    this.tvAvailableBalanceTitle = tvAvailableBalanceTitle;
    this.tvLedgerBalance = tvLedgerBalance;
    this.tvLedgerBalanceTitle = tvLedgerBalanceTitle;
    this.tvLogout = tvLogout;
    this.tvTitle = tvTitle;
  }

  @NonNull
  public static AppBarMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.app_bar_main, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static AppBarMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<AppBarMainBinding>inflateInternal(inflater, R.layout.app_bar_main, root, attachToRoot, component);
  }

  @NonNull
  public static AppBarMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.app_bar_main, null, false, component)
   */
  @NonNull
  @Deprecated
  public static AppBarMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<AppBarMainBinding>inflateInternal(inflater, R.layout.app_bar_main, null, false, component);
  }

  public static AppBarMainBinding bind(@NonNull View view) {
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
  public static AppBarMainBinding bind(@NonNull View view, @Nullable Object component) {
    return (AppBarMainBinding)bind(component, view, R.layout.app_bar_main);
  }
}