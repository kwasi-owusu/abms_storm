// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import com.woleapp.ui.widgets.CustomEditText;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutFuneralDonationsBinding extends ViewDataBinding {
  @NonNull
  public final EditText accountNumber;

  @NonNull
  public final Button btnPrint;

  @NonNull
  public final CardView cardCollection;

  @NonNull
  public final EditText cardExpiry;

  @NonNull
  public final EditText cardNumber;

  @NonNull
  public final RadioButton cardPayment;

  @NonNull
  public final CardView cashCollection;

  @NonNull
  public final RadioButton cashPayment;

  @NonNull
  public final TextView collectionTitle;

  @NonNull
  public final CustomEditText etAmt;

  @NonNull
  public final EditText etCVV;

  @NonNull
  public final ImageView firstimage;

  @NonNull
  public final LinearLayout linearCanvas;

  @NonNull
  public final RelativeLayout linearCanvasMain;

  @NonNull
  public final LinearLayout linearPrice;

  @NonNull
  public final LinearLayout linearTitle;

  @NonNull
  public final RadioButton momo;

  @NonNull
  public final LinearLayout networkSpinner;

  @NonNull
  public final CustomEditText payeeName;

  @NonNull
  public final CardView readCard;

  @NonNull
  public final RelativeLayout relativeOptions;

  @NonNull
  public final ScrollView scrollView;

  @NonNull
  public final AppCompatSpinner spnNetworkType;

  @NonNull
  public final RadioGroup transtype;

  @NonNull
  public final TextView tvCardCollection;

  @NonNull
  public final TextView tvCashCollection;

  @NonNull
  public final TextView tvConvinienceFee;

  @NonNull
  public final TextView tvReadCard;

  @NonNull
  public final TextView tvRetake;

  @NonNull
  public final TextView tvSign;

  protected LayoutFuneralDonationsBinding(Object _bindingComponent, View _root,
      int _localFieldCount, EditText accountNumber, Button btnPrint, CardView cardCollection,
      EditText cardExpiry, EditText cardNumber, RadioButton cardPayment, CardView cashCollection,
      RadioButton cashPayment, TextView collectionTitle, CustomEditText etAmt, EditText etCVV,
      ImageView firstimage, LinearLayout linearCanvas, RelativeLayout linearCanvasMain,
      LinearLayout linearPrice, LinearLayout linearTitle, RadioButton momo,
      LinearLayout networkSpinner, CustomEditText payeeName, CardView readCard,
      RelativeLayout relativeOptions, ScrollView scrollView, AppCompatSpinner spnNetworkType,
      RadioGroup transtype, TextView tvCardCollection, TextView tvCashCollection,
      TextView tvConvinienceFee, TextView tvReadCard, TextView tvRetake, TextView tvSign) {
    super(_bindingComponent, _root, _localFieldCount);
    this.accountNumber = accountNumber;
    this.btnPrint = btnPrint;
    this.cardCollection = cardCollection;
    this.cardExpiry = cardExpiry;
    this.cardNumber = cardNumber;
    this.cardPayment = cardPayment;
    this.cashCollection = cashCollection;
    this.cashPayment = cashPayment;
    this.collectionTitle = collectionTitle;
    this.etAmt = etAmt;
    this.etCVV = etCVV;
    this.firstimage = firstimage;
    this.linearCanvas = linearCanvas;
    this.linearCanvasMain = linearCanvasMain;
    this.linearPrice = linearPrice;
    this.linearTitle = linearTitle;
    this.momo = momo;
    this.networkSpinner = networkSpinner;
    this.payeeName = payeeName;
    this.readCard = readCard;
    this.relativeOptions = relativeOptions;
    this.scrollView = scrollView;
    this.spnNetworkType = spnNetworkType;
    this.transtype = transtype;
    this.tvCardCollection = tvCardCollection;
    this.tvCashCollection = tvCashCollection;
    this.tvConvinienceFee = tvConvinienceFee;
    this.tvReadCard = tvReadCard;
    this.tvRetake = tvRetake;
    this.tvSign = tvSign;
  }

  @NonNull
  public static LayoutFuneralDonationsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_funeral_donations, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutFuneralDonationsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutFuneralDonationsBinding>inflateInternal(inflater, R.layout.layout_funeral_donations, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutFuneralDonationsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_funeral_donations, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutFuneralDonationsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutFuneralDonationsBinding>inflateInternal(inflater, R.layout.layout_funeral_donations, null, false, component);
  }

  public static LayoutFuneralDonationsBinding bind(@NonNull View view) {
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
  public static LayoutFuneralDonationsBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutFuneralDonationsBinding)bind(component, view, R.layout.layout_funeral_donations);
  }
}
