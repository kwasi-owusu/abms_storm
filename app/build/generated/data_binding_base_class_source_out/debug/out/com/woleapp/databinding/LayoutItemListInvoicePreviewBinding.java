// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import com.woleapp.model.Inventory;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutItemListInvoicePreviewBinding extends ViewDataBinding {
  @NonNull
  public final TextView tvAmtRatePerUnitHeader;

  @NonNull
  public final TextView tvSubtotalQtyHeader;

  @Bindable
  protected Inventory mItem;

  @Bindable
  protected Integer mIndex;

  protected LayoutItemListInvoicePreviewBinding(Object _bindingComponent, View _root,
      int _localFieldCount, TextView tvAmtRatePerUnitHeader, TextView tvSubtotalQtyHeader) {
    super(_bindingComponent, _root, _localFieldCount);
    this.tvAmtRatePerUnitHeader = tvAmtRatePerUnitHeader;
    this.tvSubtotalQtyHeader = tvSubtotalQtyHeader;
  }

  public abstract void setItem(@Nullable Inventory item);

  @Nullable
  public Inventory getItem() {
    return mItem;
  }

  public abstract void setIndex(@Nullable Integer index);

  @Nullable
  public Integer getIndex() {
    return mIndex;
  }

  @NonNull
  public static LayoutItemListInvoicePreviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_item_list_invoice_preview, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutItemListInvoicePreviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutItemListInvoicePreviewBinding>inflateInternal(inflater, R.layout.layout_item_list_invoice_preview, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutItemListInvoicePreviewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_item_list_invoice_preview, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutItemListInvoicePreviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutItemListInvoicePreviewBinding>inflateInternal(inflater, R.layout.layout_item_list_invoice_preview, null, false, component);
  }

  public static LayoutItemListInvoicePreviewBinding bind(@NonNull View view) {
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
  public static LayoutItemListInvoicePreviewBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (LayoutItemListInvoicePreviewBinding)bind(component, view, R.layout.layout_item_list_invoice_preview);
  }
}
