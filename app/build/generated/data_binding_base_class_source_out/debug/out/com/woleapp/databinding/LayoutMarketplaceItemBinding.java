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
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import com.woleapp.model.Inventory;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutMarketplaceItemBinding extends ViewDataBinding {
  @NonNull
  public final TextView amount;

  @NonNull
  public final ImageView productImage;

  @NonNull
  public final TextView productName;

  @NonNull
  public final Button view;

  @Bindable
  protected Inventory mInventory;

  protected LayoutMarketplaceItemBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView amount, ImageView productImage, TextView productName, Button view) {
    super(_bindingComponent, _root, _localFieldCount);
    this.amount = amount;
    this.productImage = productImage;
    this.productName = productName;
    this.view = view;
  }

  public abstract void setInventory(@Nullable Inventory inventory);

  @Nullable
  public Inventory getInventory() {
    return mInventory;
  }

  @NonNull
  public static LayoutMarketplaceItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_marketplace_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutMarketplaceItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutMarketplaceItemBinding>inflateInternal(inflater, R.layout.layout_marketplace_item, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutMarketplaceItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_marketplace_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutMarketplaceItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutMarketplaceItemBinding>inflateInternal(inflater, R.layout.layout_marketplace_item, null, false, component);
  }

  public static LayoutMarketplaceItemBinding bind(@NonNull View view) {
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
  public static LayoutMarketplaceItemBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutMarketplaceItemBinding)bind(component, view, R.layout.layout_marketplace_item);
  }
}