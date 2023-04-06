// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import com.woleapp.model.Transactions;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutItemListTransactionsBinding extends ViewDataBinding {
  @NonNull
  public final Button btnDetails;

  @NonNull
  public final CardView cardCalendar;

  @NonNull
  public final LinearLayout linearDate;

  @NonNull
  public final RelativeLayout relativeDetails;

  @NonNull
  public final TextView tvAmount;

  @NonNull
  public final TextView tvCategory;

  @NonNull
  public final TextView tvColor;

  @NonNull
  public final TextView tvDate;

  @NonNull
  public final TextView tvMonth;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView tvQuantity;

  @NonNull
  public final TextView tvSize;

  @NonNull
  public final TextView tvStatus;

  @NonNull
  public final TextView tvYear;

  @Bindable
  protected Transactions mInventory;

  @Bindable
  protected Boolean mIsOpen;

  protected LayoutItemListTransactionsBinding(Object _bindingComponent, View _root,
      int _localFieldCount, Button btnDetails, CardView cardCalendar, LinearLayout linearDate,
      RelativeLayout relativeDetails, TextView tvAmount, TextView tvCategory, TextView tvColor,
      TextView tvDate, TextView tvMonth, TextView tvName, TextView tvQuantity, TextView tvSize,
      TextView tvStatus, TextView tvYear) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnDetails = btnDetails;
    this.cardCalendar = cardCalendar;
    this.linearDate = linearDate;
    this.relativeDetails = relativeDetails;
    this.tvAmount = tvAmount;
    this.tvCategory = tvCategory;
    this.tvColor = tvColor;
    this.tvDate = tvDate;
    this.tvMonth = tvMonth;
    this.tvName = tvName;
    this.tvQuantity = tvQuantity;
    this.tvSize = tvSize;
    this.tvStatus = tvStatus;
    this.tvYear = tvYear;
  }

  public abstract void setInventory(@Nullable Transactions inventory);

  @Nullable
  public Transactions getInventory() {
    return mInventory;
  }

  public abstract void setIsOpen(@Nullable Boolean isOpen);

  @Nullable
  public Boolean getIsOpen() {
    return mIsOpen;
  }

  @NonNull
  public static LayoutItemListTransactionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_item_list_transactions, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutItemListTransactionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutItemListTransactionsBinding>inflateInternal(inflater, R.layout.layout_item_list_transactions, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutItemListTransactionsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_item_list_transactions, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutItemListTransactionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutItemListTransactionsBinding>inflateInternal(inflater, R.layout.layout_item_list_transactions, null, false, component);
  }

  public static LayoutItemListTransactionsBinding bind(@NonNull View view) {
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
  public static LayoutItemListTransactionsBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (LayoutItemListTransactionsBinding)bind(component, view, R.layout.layout_item_list_transactions);
  }
}
