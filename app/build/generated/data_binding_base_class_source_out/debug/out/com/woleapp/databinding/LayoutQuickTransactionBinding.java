// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import com.woleapp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutQuickTransactionBinding extends ViewDataBinding {
  @NonNull
  public final Button btnContinue;

  @NonNull
  public final CardView cardToggle;

  @NonNull
  public final AutoCompleteTextView edtCustomerName;

  @NonNull
  public final EditText etNote;

  @NonNull
  public final EditText etPrice;

  @NonNull
  public final AutoCompleteTextView etProductName;

  @NonNull
  public final ToggleSwitch roleSwitch;

  @NonNull
  public final EditText spnQuantity;

  @NonNull
  public final TextView tvPrice;

  @NonNull
  public final TextView tvQuantity;

  @NonNull
  public final TextView tvSave;

  @NonNull
  public final TextView tvTitle;

  protected LayoutQuickTransactionBinding(Object _bindingComponent, View _root,
      int _localFieldCount, Button btnContinue, CardView cardToggle,
      AutoCompleteTextView edtCustomerName, EditText etNote, EditText etPrice,
      AutoCompleteTextView etProductName, ToggleSwitch roleSwitch, EditText spnQuantity,
      TextView tvPrice, TextView tvQuantity, TextView tvSave, TextView tvTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnContinue = btnContinue;
    this.cardToggle = cardToggle;
    this.edtCustomerName = edtCustomerName;
    this.etNote = etNote;
    this.etPrice = etPrice;
    this.etProductName = etProductName;
    this.roleSwitch = roleSwitch;
    this.spnQuantity = spnQuantity;
    this.tvPrice = tvPrice;
    this.tvQuantity = tvQuantity;
    this.tvSave = tvSave;
    this.tvTitle = tvTitle;
  }

  @NonNull
  public static LayoutQuickTransactionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_quick_transaction, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutQuickTransactionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutQuickTransactionBinding>inflateInternal(inflater, R.layout.layout_quick_transaction, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutQuickTransactionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_quick_transaction, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutQuickTransactionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutQuickTransactionBinding>inflateInternal(inflater, R.layout.layout_quick_transaction, null, false, component);
  }

  public static LayoutQuickTransactionBinding bind(@NonNull View view) {
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
  public static LayoutQuickTransactionBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutQuickTransactionBinding)bind(component, view, R.layout.layout_quick_transaction);
  }
}