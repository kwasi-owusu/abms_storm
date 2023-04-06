// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public abstract class LayoutMarketplaceItemDescriptionBinding extends ViewDataBinding {
  @NonNull
  public final Button addToCart;

  @NonNull
  public final TextView amount;

  @NonNull
  public final ImageButton decreaseButton;

  @NonNull
  public final TextView deliveryFee;

  @NonNull
  public final Button editButton;

  @NonNull
  public final ImageButton increaseButton;

  @NonNull
  public final TextView productDescription;

  @NonNull
  public final ImageView productImage;

  @NonNull
  public final TextView productName;

  @NonNull
  public final EditText quantity;

  @Bindable
  protected Inventory mInventory;

  protected LayoutMarketplaceItemDescriptionBinding(Object _bindingComponent, View _root,
      int _localFieldCount, Button addToCart, TextView amount, ImageButton decreaseButton,
      TextView deliveryFee, Button editButton, ImageButton increaseButton,
      TextView productDescription, ImageView productImage, TextView productName,
      EditText quantity) {
    super(_bindingComponent, _root, _localFieldCount);
    this.addToCart = addToCart;
    this.amount = amount;
    this.decreaseButton = decreaseButton;
    this.deliveryFee = deliveryFee;
    this.editButton = editButton;
    this.increaseButton = increaseButton;
    this.productDescription = productDescription;
    this.productImage = productImage;
    this.productName = productName;
    this.quantity = quantity;
  }

  public abstract void setInventory(@Nullable Inventory inventory);

  @Nullable
  public Inventory getInventory() {
    return mInventory;
  }

  @NonNull
  public static LayoutMarketplaceItemDescriptionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_marketplace_item_description, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutMarketplaceItemDescriptionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutMarketplaceItemDescriptionBinding>inflateInternal(inflater, R.layout.layout_marketplace_item_description, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutMarketplaceItemDescriptionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_marketplace_item_description, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutMarketplaceItemDescriptionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutMarketplaceItemDescriptionBinding>inflateInternal(inflater, R.layout.layout_marketplace_item_description, null, false, component);
  }

  public static LayoutMarketplaceItemDescriptionBinding bind(@NonNull View view) {
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
  public static LayoutMarketplaceItemDescriptionBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (LayoutMarketplaceItemDescriptionBinding)bind(component, view, R.layout.layout_marketplace_item_description);
  }
}
