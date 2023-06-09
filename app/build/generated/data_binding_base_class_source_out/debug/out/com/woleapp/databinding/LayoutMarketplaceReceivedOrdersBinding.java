// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import com.woleapp.model.SalesOrder;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutMarketplaceReceivedOrdersBinding extends ViewDataBinding {
  @NonNull
  public final TextView deliveryStatus;

  @NonNull
  public final RadioButton pending;

  @NonNull
  public final TextView productAmount;

  @NonNull
  public final TextView productDescription;

  @NonNull
  public final ImageView productImage;

  @NonNull
  public final TextView productName;

  @NonNull
  public final TextView quantity;

  @NonNull
  public final TextView quantityPurchased;

  @NonNull
  public final RadioButton shipped;

  @NonNull
  public final TextView soldBy;

  @NonNull
  public final TextView statusLabel;

  @NonNull
  public final RadioGroup updateProgress;

  @NonNull
  public final Button updateStatus;

  @Bindable
  protected SalesOrder mSaleOrder;

  protected LayoutMarketplaceReceivedOrdersBinding(Object _bindingComponent, View _root,
      int _localFieldCount, TextView deliveryStatus, RadioButton pending, TextView productAmount,
      TextView productDescription, ImageView productImage, TextView productName, TextView quantity,
      TextView quantityPurchased, RadioButton shipped, TextView soldBy, TextView statusLabel,
      RadioGroup updateProgress, Button updateStatus) {
    super(_bindingComponent, _root, _localFieldCount);
    this.deliveryStatus = deliveryStatus;
    this.pending = pending;
    this.productAmount = productAmount;
    this.productDescription = productDescription;
    this.productImage = productImage;
    this.productName = productName;
    this.quantity = quantity;
    this.quantityPurchased = quantityPurchased;
    this.shipped = shipped;
    this.soldBy = soldBy;
    this.statusLabel = statusLabel;
    this.updateProgress = updateProgress;
    this.updateStatus = updateStatus;
  }

  public abstract void setSaleOrder(@Nullable SalesOrder saleOrder);

  @Nullable
  public SalesOrder getSaleOrder() {
    return mSaleOrder;
  }

  @NonNull
  public static LayoutMarketplaceReceivedOrdersBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_marketplace_received_orders, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutMarketplaceReceivedOrdersBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutMarketplaceReceivedOrdersBinding>inflateInternal(inflater, R.layout.layout_marketplace_received_orders, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutMarketplaceReceivedOrdersBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_marketplace_received_orders, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutMarketplaceReceivedOrdersBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutMarketplaceReceivedOrdersBinding>inflateInternal(inflater, R.layout.layout_marketplace_received_orders, null, false, component);
  }

  public static LayoutMarketplaceReceivedOrdersBinding bind(@NonNull View view) {
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
  public static LayoutMarketplaceReceivedOrdersBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (LayoutMarketplaceReceivedOrdersBinding)bind(component, view, R.layout.layout_marketplace_received_orders);
  }
}
