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
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogBinding extends ViewDataBinding {
  @NonNull
  public final ImageButton copy;

  @NonNull
  public final LinearLayout linearOptions;

  @NonNull
  public final LinearLayout linearOptions2;

  @NonNull
  public final ImageView responseIcon;

  @NonNull
  public final TextView tvMessage;

  @NonNull
  public final TextView tvNo;

  @NonNull
  public final TextView tvTitle;

  @NonNull
  public final TextView tvYes;

  protected DialogBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageButton copy, LinearLayout linearOptions, LinearLayout linearOptions2,
      ImageView responseIcon, TextView tvMessage, TextView tvNo, TextView tvTitle, TextView tvYes) {
    super(_bindingComponent, _root, _localFieldCount);
    this.copy = copy;
    this.linearOptions = linearOptions;
    this.linearOptions2 = linearOptions2;
    this.responseIcon = responseIcon;
    this.tvMessage = tvMessage;
    this.tvNo = tvNo;
    this.tvTitle = tvTitle;
    this.tvYes = tvYes;
  }

  @NonNull
  public static DialogBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogBinding>inflateInternal(inflater, R.layout.dialog, root, attachToRoot, component);
  }

  @NonNull
  public static DialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogBinding>inflateInternal(inflater, R.layout.dialog, null, false, component);
  }

  public static DialogBinding bind(@NonNull View view) {
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
  public static DialogBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogBinding)bind(component, view, R.layout.dialog);
  }
}
