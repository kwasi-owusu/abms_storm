// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import com.woleapp.model.Marketplace;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutMarketplaceDealerItemBinding extends ViewDataBinding {
  @NonNull
  public final TextView dealerDescription;

  @NonNull
  public final ImageView dealerLogo;

  @NonNull
  public final TextView dealerName;

  @Bindable
  protected Marketplace mMarketplace;

  protected LayoutMarketplaceDealerItemBinding(Object _bindingComponent, View _root,
      int _localFieldCount, TextView dealerDescription, ImageView dealerLogo, TextView dealerName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.dealerDescription = dealerDescription;
    this.dealerLogo = dealerLogo;
    this.dealerName = dealerName;
  }

  public abstract void setMarketplace(@Nullable Marketplace marketplace);

  @Nullable
  public Marketplace getMarketplace() {
    return mMarketplace;
  }

  @NonNull
  public static LayoutMarketplaceDealerItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_marketplace_dealer_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutMarketplaceDealerItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutMarketplaceDealerItemBinding>inflateInternal(inflater, R.layout.layout_marketplace_dealer_item, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutMarketplaceDealerItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_marketplace_dealer_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutMarketplaceDealerItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutMarketplaceDealerItemBinding>inflateInternal(inflater, R.layout.layout_marketplace_dealer_item, null, false, component);
  }

  public static LayoutMarketplaceDealerItemBinding bind(@NonNull View view) {
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
  public static LayoutMarketplaceDealerItemBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (LayoutMarketplaceDealerItemBinding)bind(component, view, R.layout.layout_marketplace_dealer_item);
  }
}
