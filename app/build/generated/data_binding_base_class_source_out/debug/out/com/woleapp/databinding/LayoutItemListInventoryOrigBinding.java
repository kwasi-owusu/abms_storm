// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public abstract class LayoutItemListInventoryOrigBinding extends ViewDataBinding {
  @NonNull
  public final Button btnDetails;

  @NonNull
  public final Button btnSell;

  @NonNull
  public final ImageView ivProduct;

  @NonNull
  public final RelativeLayout relativeDetails;

  @NonNull
  public final TextView tvCategory;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView tvQuantity;

  @Bindable
  protected Inventory mInventory;

  @Bindable
  protected Boolean mIsOpen;

  protected LayoutItemListInventoryOrigBinding(Object _bindingComponent, View _root,
      int _localFieldCount, Button btnDetails, Button btnSell, ImageView ivProduct,
      RelativeLayout relativeDetails, TextView tvCategory, TextView tvName, TextView tvQuantity) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnDetails = btnDetails;
    this.btnSell = btnSell;
    this.ivProduct = ivProduct;
    this.relativeDetails = relativeDetails;
    this.tvCategory = tvCategory;
    this.tvName = tvName;
    this.tvQuantity = tvQuantity;
  }

  public abstract void setInventory(@Nullable Inventory inventory);

  @Nullable
  public Inventory getInventory() {
    return mInventory;
  }

  public abstract void setIsOpen(@Nullable Boolean isOpen);

  @Nullable
  public Boolean getIsOpen() {
    return mIsOpen;
  }

  @NonNull
  public static LayoutItemListInventoryOrigBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_item_list_inventory_orig, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutItemListInventoryOrigBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutItemListInventoryOrigBinding>inflateInternal(inflater, R.layout.layout_item_list_inventory_orig, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutItemListInventoryOrigBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_item_list_inventory_orig, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutItemListInventoryOrigBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutItemListInventoryOrigBinding>inflateInternal(inflater, R.layout.layout_item_list_inventory_orig, null, false, component);
  }

  public static LayoutItemListInventoryOrigBinding bind(@NonNull View view) {
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
  public static LayoutItemListInventoryOrigBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (LayoutItemListInventoryOrigBinding)bind(component, view, R.layout.layout_item_list_inventory_orig);
  }
}