// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutFinesBailsBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout linearTitle;

  @NonNull
  public final ScrollView scrollView;

  @NonNull
  public final TextView tvTitle;

  protected LayoutFinesBailsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout linearTitle, ScrollView scrollView, TextView tvTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.linearTitle = linearTitle;
    this.scrollView = scrollView;
    this.tvTitle = tvTitle;
  }

  @NonNull
  public static LayoutFinesBailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_fines_bails, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutFinesBailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutFinesBailsBinding>inflateInternal(inflater, R.layout.layout_fines_bails, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutFinesBailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_fines_bails, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutFinesBailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutFinesBailsBinding>inflateInternal(inflater, R.layout.layout_fines_bails, null, false, component);
  }

  public static LayoutFinesBailsBinding bind(@NonNull View view) {
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
  public static LayoutFinesBailsBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutFinesBailsBinding)bind(component, view, R.layout.layout_fines_bails);
  }
}