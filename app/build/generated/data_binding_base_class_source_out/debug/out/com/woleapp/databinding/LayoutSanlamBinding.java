// Generated by data binding compiler. Do not edit!
package com.woleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.woleapp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutSanlamBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout addressDetails;

  @NonNull
  public final EditText benContactNumber;

  @NonNull
  public final EditText benFirstName;

  @NonNull
  public final EditText benSurname;

  @NonNull
  public final Button btnGetStarted;

  @NonNull
  public final Button btnJoinNow;

  @NonNull
  public final Button btnNext;

  @NonNull
  public final Button btnNextMain;

  @NonNull
  public final Button btnPrevious;

  @NonNull
  public final Button btnPreviousPage;

  @NonNull
  public final Button btnSubmit;

  @NonNull
  public final TextView circleActive;

  @NonNull
  public final TextView circleInactive1;

  @NonNull
  public final TextView circleInactive2;

  @NonNull
  public final TextView claimHere;

  @NonNull
  public final EditText contactNumber;

  @NonNull
  public final EditText dateOfBirth;

  @NonNull
  public final LinearLayout employmentDetails;

  @NonNull
  public final EditText familyContactNumber;

  @NonNull
  public final EditText familyDateOfBirth;

  @NonNull
  public final EditText familyFirstName;

  @NonNull
  public final EditText familySurname;

  @NonNull
  public final EditText firstName;

  @NonNull
  public final LinearLayout getStarted;

  @NonNull
  public final EditText ghanaCard;

  @NonNull
  public final ImageView imageService;

  @NonNull
  public final LinearLayout pageNumber;

  @NonNull
  public final LinearLayout personalDetails;

  @NonNull
  public final LinearLayout policy;

  @NonNull
  public final TextView policyTitle;

  @NonNull
  public final RelativeLayout relativeOptions;

  @NonNull
  public final ScrollView scrollView;

  @NonNull
  public final EditText surname;

  @NonNull
  public final TextView tvBank;

  @NonNull
  public final TextView tvBeneficiary;

  @NonNull
  public final TextView tvTitle;

  protected LayoutSanlamBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout addressDetails, EditText benContactNumber, EditText benFirstName,
      EditText benSurname, Button btnGetStarted, Button btnJoinNow, Button btnNext,
      Button btnNextMain, Button btnPrevious, Button btnPreviousPage, Button btnSubmit,
      TextView circleActive, TextView circleInactive1, TextView circleInactive2, TextView claimHere,
      EditText contactNumber, EditText dateOfBirth, LinearLayout employmentDetails,
      EditText familyContactNumber, EditText familyDateOfBirth, EditText familyFirstName,
      EditText familySurname, EditText firstName, LinearLayout getStarted, EditText ghanaCard,
      ImageView imageService, LinearLayout pageNumber, LinearLayout personalDetails,
      LinearLayout policy, TextView policyTitle, RelativeLayout relativeOptions,
      ScrollView scrollView, EditText surname, TextView tvBank, TextView tvBeneficiary,
      TextView tvTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.addressDetails = addressDetails;
    this.benContactNumber = benContactNumber;
    this.benFirstName = benFirstName;
    this.benSurname = benSurname;
    this.btnGetStarted = btnGetStarted;
    this.btnJoinNow = btnJoinNow;
    this.btnNext = btnNext;
    this.btnNextMain = btnNextMain;
    this.btnPrevious = btnPrevious;
    this.btnPreviousPage = btnPreviousPage;
    this.btnSubmit = btnSubmit;
    this.circleActive = circleActive;
    this.circleInactive1 = circleInactive1;
    this.circleInactive2 = circleInactive2;
    this.claimHere = claimHere;
    this.contactNumber = contactNumber;
    this.dateOfBirth = dateOfBirth;
    this.employmentDetails = employmentDetails;
    this.familyContactNumber = familyContactNumber;
    this.familyDateOfBirth = familyDateOfBirth;
    this.familyFirstName = familyFirstName;
    this.familySurname = familySurname;
    this.firstName = firstName;
    this.getStarted = getStarted;
    this.ghanaCard = ghanaCard;
    this.imageService = imageService;
    this.pageNumber = pageNumber;
    this.personalDetails = personalDetails;
    this.policy = policy;
    this.policyTitle = policyTitle;
    this.relativeOptions = relativeOptions;
    this.scrollView = scrollView;
    this.surname = surname;
    this.tvBank = tvBank;
    this.tvBeneficiary = tvBeneficiary;
    this.tvTitle = tvTitle;
  }

  @NonNull
  public static LayoutSanlamBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_sanlam, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutSanlamBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutSanlamBinding>inflateInternal(inflater, R.layout.layout_sanlam, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutSanlamBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_sanlam, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutSanlamBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutSanlamBinding>inflateInternal(inflater, R.layout.layout_sanlam, null, false, component);
  }

  public static LayoutSanlamBinding bind(@NonNull View view) {
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
  public static LayoutSanlamBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutSanlamBinding)bind(component, view, R.layout.layout_sanlam);
  }
}
