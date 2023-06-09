// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutCheckOutPageBinding extends ViewDataBinding {
  @NonNull
  public final Button btnBack;

  @NonNull
  public final LinearLayout cardAccountTransfer;

  @NonNull
  public final LinearLayout cardAccountTransfer1;

  @NonNull
  public final LinearLayout cardCashCollection;

  @NonNull
  public final TextView cardFee;

  @NonNull
  public final LinearLayout cardPosPayment;

  @NonNull
  public final LinearLayout cardQRPay;

  @NonNull
  public final TextView choosePaymentMethod;

  @NonNull
  public final Guideline guideline;

  @NonNull
  public final LinearLayout payWithPaylink;

  @NonNull
  public final LinearLayout payWithStormWallet;

  @NonNull
  public final TextView paylinkFee;

  @NonNull
  public final ScrollView paymentMethods;

  @NonNull
  public final TextView posFee;

  @NonNull
  public final TextView tvAmount;

  @NonNull
  public final TextView tvConvenienceFee;

  protected LayoutCheckOutPageBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnBack, LinearLayout cardAccountTransfer, LinearLayout cardAccountTransfer1,
      LinearLayout cardCashCollection, TextView cardFee, LinearLayout cardPosPayment,
      LinearLayout cardQRPay, TextView choosePaymentMethod, Guideline guideline,
      LinearLayout payWithPaylink, LinearLayout payWithStormWallet, TextView paylinkFee,
      ScrollView paymentMethods, TextView posFee, TextView tvAmount, TextView tvConvenienceFee) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnBack = btnBack;
    this.cardAccountTransfer = cardAccountTransfer;
    this.cardAccountTransfer1 = cardAccountTransfer1;
    this.cardCashCollection = cardCashCollection;
    this.cardFee = cardFee;
    this.cardPosPayment = cardPosPayment;
    this.cardQRPay = cardQRPay;
    this.choosePaymentMethod = choosePaymentMethod;
    this.guideline = guideline;
    this.payWithPaylink = payWithPaylink;
    this.payWithStormWallet = payWithStormWallet;
    this.paylinkFee = paylinkFee;
    this.paymentMethods = paymentMethods;
    this.posFee = posFee;
    this.tvAmount = tvAmount;
    this.tvConvenienceFee = tvConvenienceFee;
  }

  @NonNull
  public static LayoutCheckOutPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_check_out_page, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutCheckOutPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutCheckOutPageBinding>inflateInternal(inflater, R.layout.layout_check_out_page, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutCheckOutPageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_check_out_page, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutCheckOutPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutCheckOutPageBinding>inflateInternal(inflater, R.layout.layout_check_out_page, null, false, component);
  }

  public static LayoutCheckOutPageBinding bind(@NonNull View view) {
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
  public static LayoutCheckOutPageBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutCheckOutPageBinding)bind(component, view, R.layout.layout_check_out_page);
  }
}
