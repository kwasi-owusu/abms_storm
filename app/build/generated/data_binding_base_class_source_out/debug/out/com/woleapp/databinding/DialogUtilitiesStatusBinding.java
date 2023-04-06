// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogUtilitiesStatusBinding extends ViewDataBinding {
  @NonNull
  public final ImageView imageView;

  @NonNull
  public final TextView message;

  @NonNull
  public final Button okay;

  protected DialogUtilitiesStatusBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView imageView, TextView message, Button okay) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imageView = imageView;
    this.message = message;
    this.okay = okay;
  }

  @NonNull
  public static DialogUtilitiesStatusBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_utilities_status, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogUtilitiesStatusBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogUtilitiesStatusBinding>inflateInternal(inflater, R.layout.dialog_utilities_status, root, attachToRoot, component);
  }

  @NonNull
  public static DialogUtilitiesStatusBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_utilities_status, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogUtilitiesStatusBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogUtilitiesStatusBinding>inflateInternal(inflater, R.layout.dialog_utilities_status, null, false, component);
  }

  public static DialogUtilitiesStatusBinding bind(@NonNull View view) {
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
  public static DialogUtilitiesStatusBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogUtilitiesStatusBinding)bind(component, view, R.layout.dialog_utilities_status);
  }
}