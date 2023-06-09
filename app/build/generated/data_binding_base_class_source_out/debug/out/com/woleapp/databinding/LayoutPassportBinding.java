// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.woleapp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutPassportBinding extends ViewDataBinding {
  @NonNull
  public final Button btnContinue;

  @NonNull
  public final RelativeLayout buttonsContainer;

  @NonNull
  public final LinearLayout linearCanvas;

  @NonNull
  public final ScrollView scrollView;

  @NonNull
  public final SignaturePad signaturePad;

  @NonNull
  public final RelativeLayout signaturePadContainer;

  @NonNull
  public final TextView tvRetake;

  @NonNull
  public final TextView tvSign;

  protected LayoutPassportBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnContinue, RelativeLayout buttonsContainer, LinearLayout linearCanvas,
      ScrollView scrollView, SignaturePad signaturePad, RelativeLayout signaturePadContainer,
      TextView tvRetake, TextView tvSign) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnContinue = btnContinue;
    this.buttonsContainer = buttonsContainer;
    this.linearCanvas = linearCanvas;
    this.scrollView = scrollView;
    this.signaturePad = signaturePad;
    this.signaturePadContainer = signaturePadContainer;
    this.tvRetake = tvRetake;
    this.tvSign = tvSign;
  }

  @NonNull
  public static LayoutPassportBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_passport, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutPassportBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutPassportBinding>inflateInternal(inflater, R.layout.layout_passport, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutPassportBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_passport, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutPassportBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutPassportBinding>inflateInternal(inflater, R.layout.layout_passport, null, false, component);
  }

  public static LayoutPassportBinding bind(@NonNull View view) {
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
  public static LayoutPassportBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutPassportBinding)bind(component, view, R.layout.layout_passport);
  }
}
