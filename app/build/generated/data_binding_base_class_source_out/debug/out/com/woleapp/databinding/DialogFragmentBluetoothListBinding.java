// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogFragmentBluetoothListBinding extends ViewDataBinding {
  @NonNull
  public final TextView bluetoothDeviceHint;

  @NonNull
  public final Button cancelBtn;

  @NonNull
  public final ListView list;

  protected DialogFragmentBluetoothListBinding(Object _bindingComponent, View _root,
      int _localFieldCount, TextView bluetoothDeviceHint, Button cancelBtn, ListView list) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bluetoothDeviceHint = bluetoothDeviceHint;
    this.cancelBtn = cancelBtn;
    this.list = list;
  }

  @NonNull
  public static DialogFragmentBluetoothListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_bluetooth_list, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogFragmentBluetoothListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogFragmentBluetoothListBinding>inflateInternal(inflater, R.layout.dialog_fragment_bluetooth_list, root, attachToRoot, component);
  }

  @NonNull
  public static DialogFragmentBluetoothListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_bluetooth_list, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogFragmentBluetoothListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogFragmentBluetoothListBinding>inflateInternal(inflater, R.layout.dialog_fragment_bluetooth_list, null, false, component);
  }

  public static DialogFragmentBluetoothListBinding bind(@NonNull View view) {
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
  public static DialogFragmentBluetoothListBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (DialogFragmentBluetoothListBinding)bind(component, view, R.layout.dialog_fragment_bluetooth_list);
  }
}