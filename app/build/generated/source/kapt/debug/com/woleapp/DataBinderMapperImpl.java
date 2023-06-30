package com.woleapp;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.woleapp.databinding.ActivityPaymentProgressBindingImpl;
import com.woleapp.databinding.ActivitySignature2BindingImpl;
import com.woleapp.databinding.ActivitySignature2BindingLandImpl;
import com.woleapp.databinding.AppBarMainBindingImpl;
import com.woleapp.databinding.DialogBindingImpl;
import com.woleapp.databinding.DialogFragmentBluetoothListBindingImpl;
import com.woleapp.databinding.DialogFragmentBluetoothListPrinterBindingImpl;
import com.woleapp.databinding.DialogLogoutBindingImpl;
import com.woleapp.databinding.DialogResetPasswordBindingImpl;
import com.woleapp.databinding.DialogSupportBindingImpl;
import com.woleapp.databinding.DialogUtilitiesStatusBindingImpl;
import com.woleapp.databinding.FragmentAgencyTransactionBindingImpl;
import com.woleapp.databinding.FragmentEbillsListBindingImpl;
import com.woleapp.databinding.FragmentEbillsWebviewBindingImpl;
import com.woleapp.databinding.FragmentHealthCheckerBindingImpl;
import com.woleapp.databinding.FragmentRemittanceBindingImpl;
import com.woleapp.databinding.ItemServicesDropdownBindingImpl;
import com.woleapp.databinding.LayoutAddInventoryBindingImpl;
import com.woleapp.databinding.LayoutAgentBindingImpl;
import com.woleapp.databinding.LayoutAirtimeOrDataBindingImpl;
import com.woleapp.databinding.LayoutBankDetailsBindingImpl;
import com.woleapp.databinding.LayoutCableTvBindingImpl;
import com.woleapp.databinding.LayoutCameraBindingImpl;
import com.woleapp.databinding.LayoutCardDetailsBindingImpl;
import com.woleapp.databinding.LayoutCardWebviewBindingImpl;
import com.woleapp.databinding.LayoutCashInBindingImpl;
import com.woleapp.databinding.LayoutCashInOptionsBindingImpl;
import com.woleapp.databinding.LayoutCashInOptionsNewBindingImpl;
import com.woleapp.databinding.LayoutCashoutBindingImpl;
import com.woleapp.databinding.LayoutCheckOutPageBindingImpl;
import com.woleapp.databinding.LayoutCollectionsBindingImpl;
import com.woleapp.databinding.LayoutCollectionsNewBindingImpl;
import com.woleapp.databinding.LayoutDashboardBindingImpl;
import com.woleapp.databinding.LayoutFinesBailsBindingImpl;
import com.woleapp.databinding.LayoutFundWalletBindingImpl;
import com.woleapp.databinding.LayoutFuneralDonationsBindingImpl;
import com.woleapp.databinding.LayoutHomeBindingImpl;
import com.woleapp.databinding.LayoutInternetSubscriptionBindingImpl;
import com.woleapp.databinding.LayoutInventoryListBindingImpl;
import com.woleapp.databinding.LayoutInvoicePreviewBindingImpl;
import com.woleapp.databinding.LayoutItemListInventory2BindingImpl;
import com.woleapp.databinding.LayoutItemListInventoryBindingImpl;
import com.woleapp.databinding.LayoutItemListInventoryOrigBindingImpl;
import com.woleapp.databinding.LayoutItemListInvoicePreviewBindingImpl;
import com.woleapp.databinding.LayoutItemListTransactionBindingImpl;
import com.woleapp.databinding.LayoutItemListTransactionsBindingImpl;
import com.woleapp.databinding.LayoutLoginBindingImpl;
import com.woleapp.databinding.LayoutMainBindingImpl;
import com.woleapp.databinding.LayoutMarketplaceContainerBindingImpl;
import com.woleapp.databinding.LayoutMarketplaceDealerItemBindingImpl;
import com.woleapp.databinding.LayoutMarketplaceHomeBindingImpl;
import com.woleapp.databinding.LayoutMarketplaceItemBindingImpl;
import com.woleapp.databinding.LayoutMarketplaceItemDescriptionBindingImpl;
import com.woleapp.databinding.LayoutMarketplaceProductListBindingImpl;
import com.woleapp.databinding.LayoutMarketplaceReceivedOrdersBindingImpl;
import com.woleapp.databinding.LayoutMarketplaceStoreBindingImpl;
import com.woleapp.databinding.LayoutMarketplaceStoreDashboardBindingImpl;
import com.woleapp.databinding.LayoutMerchantBindingImpl;
import com.woleapp.databinding.LayoutMerchantSignInBindingImpl;
import com.woleapp.databinding.LayoutMerchantTransactionListItemBindingImpl;
import com.woleapp.databinding.LayoutMoneygramBindingImpl;
import com.woleapp.databinding.LayoutPassportBindingImpl;
import com.woleapp.databinding.LayoutPaymentCardSuccessfulBindingImpl;
import com.woleapp.databinding.LayoutPaymentCompleteBindingImpl;
import com.woleapp.databinding.LayoutPaymentModeBindingImpl;
import com.woleapp.databinding.LayoutPaymentSuccessfulBindingImpl;
import com.woleapp.databinding.LayoutPowerOrElectricityBindingImpl;
import com.woleapp.databinding.LayoutQrActivationBindingImpl;
import com.woleapp.databinding.LayoutQrRegistrationBindingImpl;
import com.woleapp.databinding.LayoutQuickTransactionBindingImpl;
import com.woleapp.databinding.LayoutRiaBindingImpl;
import com.woleapp.databinding.LayoutSanlamBindingImpl;
import com.woleapp.databinding.LayoutSanlamClaimsBindingImpl;
import com.woleapp.databinding.LayoutScanToPayBindingImpl;
import com.woleapp.databinding.LayoutSchoolFeesBindingImpl;
import com.woleapp.databinding.LayoutSelectUserTypeBindingImpl;
import com.woleapp.databinding.LayoutSignUpBindingImpl;
import com.woleapp.databinding.LayoutSignUpOrigBindingImpl;
import com.woleapp.databinding.LayoutSsnitBindingImpl;
import com.woleapp.databinding.LayoutTransactionFailureBindingImpl;
import com.woleapp.databinding.LayoutTransactionListBindingImpl;
import com.woleapp.databinding.LayoutTransactionStatusBindingImpl;
import com.woleapp.databinding.LayoutTransactionSuccessBindingImpl;
import com.woleapp.databinding.LayoutVerifyOtpBindingImpl;
import com.woleapp.databinding.LayoutVerifyUtilityPaymentBindingImpl;
import com.woleapp.databinding.LayoutWebviewBindingImpl;
import com.woleapp.databinding.LayoutWesternUnionBindingImpl;
import com.woleapp.databinding.PageIndicatorLayoutBindingImpl;
import com.woleapp.databinding.QrBottomSheetDialogBindingImpl;
import com.woleapp.databinding.SplashBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYPAYMENTPROGRESS = 1;

  private static final int LAYOUT_ACTIVITYSIGNATURE2 = 2;

  private static final int LAYOUT_APPBARMAIN = 3;

  private static final int LAYOUT_DIALOG = 4;

  private static final int LAYOUT_DIALOGFRAGMENTBLUETOOTHLIST = 5;

  private static final int LAYOUT_DIALOGFRAGMENTBLUETOOTHLISTPRINTER = 6;

  private static final int LAYOUT_DIALOGLOGOUT = 7;

  private static final int LAYOUT_DIALOGRESETPASSWORD = 8;

  private static final int LAYOUT_DIALOGSUPPORT = 9;

  private static final int LAYOUT_DIALOGUTILITIESSTATUS = 10;

  private static final int LAYOUT_FRAGMENTAGENCYTRANSACTION = 11;

  private static final int LAYOUT_FRAGMENTEBILLSLIST = 12;

  private static final int LAYOUT_FRAGMENTEBILLSWEBVIEW = 13;

  private static final int LAYOUT_FRAGMENTHEALTHCHECKER = 14;

  private static final int LAYOUT_FRAGMENTREMITTANCE = 15;

  private static final int LAYOUT_ITEMSERVICESDROPDOWN = 16;

  private static final int LAYOUT_LAYOUTADDINVENTORY = 17;

  private static final int LAYOUT_LAYOUTAGENT = 18;

  private static final int LAYOUT_LAYOUTAIRTIMEORDATA = 19;

  private static final int LAYOUT_LAYOUTBANKDETAILS = 20;

  private static final int LAYOUT_LAYOUTCABLETV = 21;

  private static final int LAYOUT_LAYOUTCAMERA = 22;

  private static final int LAYOUT_LAYOUTCARDDETAILS = 23;

  private static final int LAYOUT_LAYOUTCARDWEBVIEW = 24;

  private static final int LAYOUT_LAYOUTCASHIN = 25;

  private static final int LAYOUT_LAYOUTCASHINOPTIONS = 26;

  private static final int LAYOUT_LAYOUTCASHINOPTIONSNEW = 27;

  private static final int LAYOUT_LAYOUTCASHOUT = 28;

  private static final int LAYOUT_LAYOUTCHECKOUTPAGE = 29;

  private static final int LAYOUT_LAYOUTCOLLECTIONS = 30;

  private static final int LAYOUT_LAYOUTCOLLECTIONSNEW = 31;

  private static final int LAYOUT_LAYOUTDASHBOARD = 32;

  private static final int LAYOUT_LAYOUTFINESBAILS = 33;

  private static final int LAYOUT_LAYOUTFUNDWALLET = 34;

  private static final int LAYOUT_LAYOUTFUNERALDONATIONS = 35;

  private static final int LAYOUT_LAYOUTHOME = 36;

  private static final int LAYOUT_LAYOUTINTERNETSUBSCRIPTION = 37;

  private static final int LAYOUT_LAYOUTINVENTORYLIST = 38;

  private static final int LAYOUT_LAYOUTINVOICEPREVIEW = 39;

  private static final int LAYOUT_LAYOUTITEMLISTINVENTORY = 40;

  private static final int LAYOUT_LAYOUTITEMLISTINVENTORY2 = 41;

  private static final int LAYOUT_LAYOUTITEMLISTINVENTORYORIG = 42;

  private static final int LAYOUT_LAYOUTITEMLISTINVOICEPREVIEW = 43;

  private static final int LAYOUT_LAYOUTITEMLISTTRANSACTION = 44;

  private static final int LAYOUT_LAYOUTITEMLISTTRANSACTIONS = 45;

  private static final int LAYOUT_LAYOUTLOGIN = 46;

  private static final int LAYOUT_LAYOUTMAIN = 47;

  private static final int LAYOUT_LAYOUTMARKETPLACECONTAINER = 48;

  private static final int LAYOUT_LAYOUTMARKETPLACEDEALERITEM = 49;

  private static final int LAYOUT_LAYOUTMARKETPLACEHOME = 50;

  private static final int LAYOUT_LAYOUTMARKETPLACEITEM = 51;

  private static final int LAYOUT_LAYOUTMARKETPLACEITEMDESCRIPTION = 52;

  private static final int LAYOUT_LAYOUTMARKETPLACEPRODUCTLIST = 53;

  private static final int LAYOUT_LAYOUTMARKETPLACERECEIVEDORDERS = 54;

  private static final int LAYOUT_LAYOUTMARKETPLACESTORE = 55;

  private static final int LAYOUT_LAYOUTMARKETPLACESTOREDASHBOARD = 56;

  private static final int LAYOUT_LAYOUTMERCHANT = 57;

  private static final int LAYOUT_LAYOUTMERCHANTSIGNIN = 58;

  private static final int LAYOUT_LAYOUTMERCHANTTRANSACTIONLISTITEM = 59;

  private static final int LAYOUT_LAYOUTMONEYGRAM = 60;

  private static final int LAYOUT_LAYOUTPASSPORT = 61;

  private static final int LAYOUT_LAYOUTPAYMENTCARDSUCCESSFUL = 62;

  private static final int LAYOUT_LAYOUTPAYMENTCOMPLETE = 63;

  private static final int LAYOUT_LAYOUTPAYMENTMODE = 64;

  private static final int LAYOUT_LAYOUTPAYMENTSUCCESSFUL = 65;

  private static final int LAYOUT_LAYOUTPOWERORELECTRICITY = 66;

  private static final int LAYOUT_LAYOUTQRACTIVATION = 67;

  private static final int LAYOUT_LAYOUTQRREGISTRATION = 68;

  private static final int LAYOUT_LAYOUTQUICKTRANSACTION = 69;

  private static final int LAYOUT_LAYOUTRIA = 70;

  private static final int LAYOUT_LAYOUTSANLAM = 71;

  private static final int LAYOUT_LAYOUTSANLAMCLAIMS = 72;

  private static final int LAYOUT_LAYOUTSCANTOPAY = 73;

  private static final int LAYOUT_LAYOUTSCHOOLFEES = 74;

  private static final int LAYOUT_LAYOUTSELECTUSERTYPE = 75;

  private static final int LAYOUT_LAYOUTSIGNUP = 76;

  private static final int LAYOUT_LAYOUTSIGNUPORIG = 77;

  private static final int LAYOUT_LAYOUTSSNIT = 78;

  private static final int LAYOUT_LAYOUTTRANSACTIONFAILURE = 79;

  private static final int LAYOUT_LAYOUTTRANSACTIONLIST = 80;

  private static final int LAYOUT_LAYOUTTRANSACTIONSTATUS = 81;

  private static final int LAYOUT_LAYOUTTRANSACTIONSUCCESS = 82;

  private static final int LAYOUT_LAYOUTVERIFYOTP = 83;

  private static final int LAYOUT_LAYOUTVERIFYUTILITYPAYMENT = 84;

  private static final int LAYOUT_LAYOUTWEBVIEW = 85;

  private static final int LAYOUT_LAYOUTWESTERNUNION = 86;

  private static final int LAYOUT_PAGEINDICATORLAYOUT = 87;

  private static final int LAYOUT_QRBOTTOMSHEETDIALOG = 88;

  private static final int LAYOUT_SPLASH = 89;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(89);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.activity_payment_progress, LAYOUT_ACTIVITYPAYMENTPROGRESS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.activity_signature_2, LAYOUT_ACTIVITYSIGNATURE2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.app_bar_main, LAYOUT_APPBARMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.dialog, LAYOUT_DIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.dialog_fragment_bluetooth_list, LAYOUT_DIALOGFRAGMENTBLUETOOTHLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.dialog_fragment_bluetooth_list_printer, LAYOUT_DIALOGFRAGMENTBLUETOOTHLISTPRINTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.dialog_logout, LAYOUT_DIALOGLOGOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.dialog_reset_password, LAYOUT_DIALOGRESETPASSWORD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.dialog_support, LAYOUT_DIALOGSUPPORT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.dialog_utilities_status, LAYOUT_DIALOGUTILITIESSTATUS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.fragment_agency_transaction, LAYOUT_FRAGMENTAGENCYTRANSACTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.fragment_ebills_list, LAYOUT_FRAGMENTEBILLSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.fragment_ebills_webview, LAYOUT_FRAGMENTEBILLSWEBVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.fragment_health_checker, LAYOUT_FRAGMENTHEALTHCHECKER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.fragment_remittance, LAYOUT_FRAGMENTREMITTANCE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.item_services_dropdown, LAYOUT_ITEMSERVICESDROPDOWN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_add_inventory, LAYOUT_LAYOUTADDINVENTORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_agent, LAYOUT_LAYOUTAGENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_airtime_or_data, LAYOUT_LAYOUTAIRTIMEORDATA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_bank_details, LAYOUT_LAYOUTBANKDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_cable_tv, LAYOUT_LAYOUTCABLETV);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_camera, LAYOUT_LAYOUTCAMERA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_card_details, LAYOUT_LAYOUTCARDDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_card_webview, LAYOUT_LAYOUTCARDWEBVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_cash_in, LAYOUT_LAYOUTCASHIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_cash_in_options, LAYOUT_LAYOUTCASHINOPTIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_cash_in_options_new, LAYOUT_LAYOUTCASHINOPTIONSNEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_cashout, LAYOUT_LAYOUTCASHOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_check_out_page, LAYOUT_LAYOUTCHECKOUTPAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_collections, LAYOUT_LAYOUTCOLLECTIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_collections_new, LAYOUT_LAYOUTCOLLECTIONSNEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_dashboard, LAYOUT_LAYOUTDASHBOARD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_fines_bails, LAYOUT_LAYOUTFINESBAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_fund_wallet, LAYOUT_LAYOUTFUNDWALLET);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_funeral_donations, LAYOUT_LAYOUTFUNERALDONATIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_home, LAYOUT_LAYOUTHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_internet_subscription, LAYOUT_LAYOUTINTERNETSUBSCRIPTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_inventory_list, LAYOUT_LAYOUTINVENTORYLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_invoice_preview, LAYOUT_LAYOUTINVOICEPREVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_item_list_inventory, LAYOUT_LAYOUTITEMLISTINVENTORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_item_list_inventory_2, LAYOUT_LAYOUTITEMLISTINVENTORY2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_item_list_inventory_orig, LAYOUT_LAYOUTITEMLISTINVENTORYORIG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_item_list_invoice_preview, LAYOUT_LAYOUTITEMLISTINVOICEPREVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_item_list_transaction, LAYOUT_LAYOUTITEMLISTTRANSACTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_item_list_transactions, LAYOUT_LAYOUTITEMLISTTRANSACTIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_login, LAYOUT_LAYOUTLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_main, LAYOUT_LAYOUTMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_marketplace_container, LAYOUT_LAYOUTMARKETPLACECONTAINER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_marketplace_dealer_item, LAYOUT_LAYOUTMARKETPLACEDEALERITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_marketplace_home, LAYOUT_LAYOUTMARKETPLACEHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_marketplace_item, LAYOUT_LAYOUTMARKETPLACEITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_marketplace_item_description, LAYOUT_LAYOUTMARKETPLACEITEMDESCRIPTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_marketplace_product_list, LAYOUT_LAYOUTMARKETPLACEPRODUCTLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_marketplace_received_orders, LAYOUT_LAYOUTMARKETPLACERECEIVEDORDERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_marketplace_store, LAYOUT_LAYOUTMARKETPLACESTORE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_marketplace_store_dashboard, LAYOUT_LAYOUTMARKETPLACESTOREDASHBOARD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_merchant, LAYOUT_LAYOUTMERCHANT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_merchant_sign_in, LAYOUT_LAYOUTMERCHANTSIGNIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_merchant_transaction_list_item, LAYOUT_LAYOUTMERCHANTTRANSACTIONLISTITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_moneygram, LAYOUT_LAYOUTMONEYGRAM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_passport, LAYOUT_LAYOUTPASSPORT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_payment_card_successful, LAYOUT_LAYOUTPAYMENTCARDSUCCESSFUL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_payment_complete, LAYOUT_LAYOUTPAYMENTCOMPLETE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_payment_mode, LAYOUT_LAYOUTPAYMENTMODE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_payment_successful, LAYOUT_LAYOUTPAYMENTSUCCESSFUL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_power_or_electricity, LAYOUT_LAYOUTPOWERORELECTRICITY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_qr_activation, LAYOUT_LAYOUTQRACTIVATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_qr_registration, LAYOUT_LAYOUTQRREGISTRATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_quick_transaction, LAYOUT_LAYOUTQUICKTRANSACTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_ria, LAYOUT_LAYOUTRIA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_sanlam, LAYOUT_LAYOUTSANLAM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_sanlam_claims, LAYOUT_LAYOUTSANLAMCLAIMS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_scan_to_pay, LAYOUT_LAYOUTSCANTOPAY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_school_fees, LAYOUT_LAYOUTSCHOOLFEES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_select_user_type, LAYOUT_LAYOUTSELECTUSERTYPE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_sign_up, LAYOUT_LAYOUTSIGNUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_sign_up_orig, LAYOUT_LAYOUTSIGNUPORIG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_ssnit, LAYOUT_LAYOUTSSNIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_transaction_failure, LAYOUT_LAYOUTTRANSACTIONFAILURE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_transaction_list, LAYOUT_LAYOUTTRANSACTIONLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_transaction_status, LAYOUT_LAYOUTTRANSACTIONSTATUS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_transaction_success, LAYOUT_LAYOUTTRANSACTIONSUCCESS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_verify_otp, LAYOUT_LAYOUTVERIFYOTP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_verify_utility_payment, LAYOUT_LAYOUTVERIFYUTILITYPAYMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_webview, LAYOUT_LAYOUTWEBVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.layout_western_union, LAYOUT_LAYOUTWESTERNUNION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.page_indicator_layout, LAYOUT_PAGEINDICATORLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.qr_bottom_sheet_dialog, LAYOUT_QRBOTTOMSHEETDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.woleapp.R.layout.splash, LAYOUT_SPLASH);
  }

  private final ViewDataBinding internalGetViewDataBinding0(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_ACTIVITYPAYMENTPROGRESS: {
        if ("layout/activity_payment_progress_0".equals(tag)) {
          return new ActivityPaymentProgressBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_payment_progress is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSIGNATURE2: {
        if ("layout/activity_signature_2_0".equals(tag)) {
          return new ActivitySignature2BindingImpl(component, view);
        }
        if ("layout-land/activity_signature_2_0".equals(tag)) {
          return new ActivitySignature2BindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_signature_2 is invalid. Received: " + tag);
      }
      case  LAYOUT_APPBARMAIN: {
        if ("layout/app_bar_main_0".equals(tag)) {
          return new AppBarMainBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for app_bar_main is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOG: {
        if ("layout/dialog_0".equals(tag)) {
          return new DialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGFRAGMENTBLUETOOTHLIST: {
        if ("layout/dialog_fragment_bluetooth_list_0".equals(tag)) {
          return new DialogFragmentBluetoothListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_fragment_bluetooth_list is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGFRAGMENTBLUETOOTHLISTPRINTER: {
        if ("layout/dialog_fragment_bluetooth_list_printer_0".equals(tag)) {
          return new DialogFragmentBluetoothListPrinterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_fragment_bluetooth_list_printer is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGLOGOUT: {
        if ("layout/dialog_logout_0".equals(tag)) {
          return new DialogLogoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_logout is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGRESETPASSWORD: {
        if ("layout/dialog_reset_password_0".equals(tag)) {
          return new DialogResetPasswordBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_reset_password is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGSUPPORT: {
        if ("layout/dialog_support_0".equals(tag)) {
          return new DialogSupportBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_support is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGUTILITIESSTATUS: {
        if ("layout/dialog_utilities_status_0".equals(tag)) {
          return new DialogUtilitiesStatusBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_utilities_status is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTAGENCYTRANSACTION: {
        if ("layout/fragment_agency_transaction_0".equals(tag)) {
          return new FragmentAgencyTransactionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_agency_transaction is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTEBILLSLIST: {
        if ("layout/fragment_ebills_list_0".equals(tag)) {
          return new FragmentEbillsListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_ebills_list is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTEBILLSWEBVIEW: {
        if ("layout/fragment_ebills_webview_0".equals(tag)) {
          return new FragmentEbillsWebviewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_ebills_webview is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTHEALTHCHECKER: {
        if ("layout/fragment_health_checker_0".equals(tag)) {
          return new FragmentHealthCheckerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_health_checker is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTREMITTANCE: {
        if ("layout/fragment_remittance_0".equals(tag)) {
          return new FragmentRemittanceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_remittance is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSERVICESDROPDOWN: {
        if ("layout/item_services_dropdown_0".equals(tag)) {
          return new ItemServicesDropdownBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_services_dropdown is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTADDINVENTORY: {
        if ("layout/layout_add_inventory_0".equals(tag)) {
          return new LayoutAddInventoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_add_inventory is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTAGENT: {
        if ("layout/layout_agent_0".equals(tag)) {
          return new LayoutAgentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_agent is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTAIRTIMEORDATA: {
        if ("layout/layout_airtime_or_data_0".equals(tag)) {
          return new LayoutAirtimeOrDataBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_airtime_or_data is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTBANKDETAILS: {
        if ("layout/layout_bank_details_0".equals(tag)) {
          return new LayoutBankDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_bank_details is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTCABLETV: {
        if ("layout/layout_cable_tv_0".equals(tag)) {
          return new LayoutCableTvBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_cable_tv is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTCAMERA: {
        if ("layout/layout_camera_0".equals(tag)) {
          return new LayoutCameraBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_camera is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTCARDDETAILS: {
        if ("layout/layout_card_details_0".equals(tag)) {
          return new LayoutCardDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_card_details is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTCARDWEBVIEW: {
        if ("layout/layout_card_webview_0".equals(tag)) {
          return new LayoutCardWebviewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_card_webview is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTCASHIN: {
        if ("layout/layout_cash_in_0".equals(tag)) {
          return new LayoutCashInBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_cash_in is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTCASHINOPTIONS: {
        if ("layout/layout_cash_in_options_0".equals(tag)) {
          return new LayoutCashInOptionsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_cash_in_options is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTCASHINOPTIONSNEW: {
        if ("layout/layout_cash_in_options_new_0".equals(tag)) {
          return new LayoutCashInOptionsNewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_cash_in_options_new is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTCASHOUT: {
        if ("layout/layout_cashout_0".equals(tag)) {
          return new LayoutCashoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_cashout is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTCHECKOUTPAGE: {
        if ("layout/layout_check_out_page_0".equals(tag)) {
          return new LayoutCheckOutPageBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_check_out_page is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTCOLLECTIONS: {
        if ("layout/layout_collections_0".equals(tag)) {
          return new LayoutCollectionsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_collections is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTCOLLECTIONSNEW: {
        if ("layout/layout_collections_new_0".equals(tag)) {
          return new LayoutCollectionsNewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_collections_new is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTDASHBOARD: {
        if ("layout/layout_dashboard_0".equals(tag)) {
          return new LayoutDashboardBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_dashboard is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTFINESBAILS: {
        if ("layout/layout_fines_bails_0".equals(tag)) {
          return new LayoutFinesBailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_fines_bails is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTFUNDWALLET: {
        if ("layout/layout_fund_wallet_0".equals(tag)) {
          return new LayoutFundWalletBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_fund_wallet is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTFUNERALDONATIONS: {
        if ("layout/layout_funeral_donations_0".equals(tag)) {
          return new LayoutFuneralDonationsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_funeral_donations is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTHOME: {
        if ("layout/layout_home_0".equals(tag)) {
          return new LayoutHomeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_home is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTINTERNETSUBSCRIPTION: {
        if ("layout/layout_internet_subscription_0".equals(tag)) {
          return new LayoutInternetSubscriptionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_internet_subscription is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTINVENTORYLIST: {
        if ("layout/layout_inventory_list_0".equals(tag)) {
          return new LayoutInventoryListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_inventory_list is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTINVOICEPREVIEW: {
        if ("layout/layout_invoice_preview_0".equals(tag)) {
          return new LayoutInvoicePreviewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_invoice_preview is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTITEMLISTINVENTORY: {
        if ("layout/layout_item_list_inventory_0".equals(tag)) {
          return new LayoutItemListInventoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_item_list_inventory is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTITEMLISTINVENTORY2: {
        if ("layout/layout_item_list_inventory_2_0".equals(tag)) {
          return new LayoutItemListInventory2BindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_item_list_inventory_2 is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTITEMLISTINVENTORYORIG: {
        if ("layout/layout_item_list_inventory_orig_0".equals(tag)) {
          return new LayoutItemListInventoryOrigBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_item_list_inventory_orig is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTITEMLISTINVOICEPREVIEW: {
        if ("layout/layout_item_list_invoice_preview_0".equals(tag)) {
          return new LayoutItemListInvoicePreviewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_item_list_invoice_preview is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTITEMLISTTRANSACTION: {
        if ("layout/layout_item_list_transaction_0".equals(tag)) {
          return new LayoutItemListTransactionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_item_list_transaction is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTITEMLISTTRANSACTIONS: {
        if ("layout/layout_item_list_transactions_0".equals(tag)) {
          return new LayoutItemListTransactionsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_item_list_transactions is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTLOGIN: {
        if ("layout/layout_login_0".equals(tag)) {
          return new LayoutLoginBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_login is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMAIN: {
        if ("layout/layout_main_0".equals(tag)) {
          return new LayoutMainBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_main is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMARKETPLACECONTAINER: {
        if ("layout/layout_marketplace_container_0".equals(tag)) {
          return new LayoutMarketplaceContainerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_marketplace_container is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMARKETPLACEDEALERITEM: {
        if ("layout/layout_marketplace_dealer_item_0".equals(tag)) {
          return new LayoutMarketplaceDealerItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_marketplace_dealer_item is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMARKETPLACEHOME: {
        if ("layout/layout_marketplace_home_0".equals(tag)) {
          return new LayoutMarketplaceHomeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_marketplace_home is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding1(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_LAYOUTMARKETPLACEITEM: {
        if ("layout/layout_marketplace_item_0".equals(tag)) {
          return new LayoutMarketplaceItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_marketplace_item is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMARKETPLACEITEMDESCRIPTION: {
        if ("layout/layout_marketplace_item_description_0".equals(tag)) {
          return new LayoutMarketplaceItemDescriptionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_marketplace_item_description is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMARKETPLACEPRODUCTLIST: {
        if ("layout/layout_marketplace_product_list_0".equals(tag)) {
          return new LayoutMarketplaceProductListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_marketplace_product_list is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMARKETPLACERECEIVEDORDERS: {
        if ("layout/layout_marketplace_received_orders_0".equals(tag)) {
          return new LayoutMarketplaceReceivedOrdersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_marketplace_received_orders is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMARKETPLACESTORE: {
        if ("layout/layout_marketplace_store_0".equals(tag)) {
          return new LayoutMarketplaceStoreBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_marketplace_store is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMARKETPLACESTOREDASHBOARD: {
        if ("layout/layout_marketplace_store_dashboard_0".equals(tag)) {
          return new LayoutMarketplaceStoreDashboardBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_marketplace_store_dashboard is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMERCHANT: {
        if ("layout/layout_merchant_0".equals(tag)) {
          return new LayoutMerchantBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_merchant is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMERCHANTSIGNIN: {
        if ("layout/layout_merchant_sign_in_0".equals(tag)) {
          return new LayoutMerchantSignInBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_merchant_sign_in is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMERCHANTTRANSACTIONLISTITEM: {
        if ("layout/layout_merchant_transaction_list_item_0".equals(tag)) {
          return new LayoutMerchantTransactionListItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_merchant_transaction_list_item is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMONEYGRAM: {
        if ("layout/layout_moneygram_0".equals(tag)) {
          return new LayoutMoneygramBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_moneygram is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTPASSPORT: {
        if ("layout/layout_passport_0".equals(tag)) {
          return new LayoutPassportBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_passport is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTPAYMENTCARDSUCCESSFUL: {
        if ("layout/layout_payment_card_successful_0".equals(tag)) {
          return new LayoutPaymentCardSuccessfulBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_payment_card_successful is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTPAYMENTCOMPLETE: {
        if ("layout/layout_payment_complete_0".equals(tag)) {
          return new LayoutPaymentCompleteBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_payment_complete is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTPAYMENTMODE: {
        if ("layout/layout_payment_mode_0".equals(tag)) {
          return new LayoutPaymentModeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_payment_mode is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTPAYMENTSUCCESSFUL: {
        if ("layout/layout_payment_successful_0".equals(tag)) {
          return new LayoutPaymentSuccessfulBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_payment_successful is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTPOWERORELECTRICITY: {
        if ("layout/layout_power_or_electricity_0".equals(tag)) {
          return new LayoutPowerOrElectricityBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_power_or_electricity is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTQRACTIVATION: {
        if ("layout/layout_qr_activation_0".equals(tag)) {
          return new LayoutQrActivationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_qr_activation is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTQRREGISTRATION: {
        if ("layout/layout_qr_registration_0".equals(tag)) {
          return new LayoutQrRegistrationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_qr_registration is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTQUICKTRANSACTION: {
        if ("layout/layout_quick_transaction_0".equals(tag)) {
          return new LayoutQuickTransactionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_quick_transaction is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTRIA: {
        if ("layout/layout_ria_0".equals(tag)) {
          return new LayoutRiaBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_ria is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTSANLAM: {
        if ("layout/layout_sanlam_0".equals(tag)) {
          return new LayoutSanlamBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_sanlam is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTSANLAMCLAIMS: {
        if ("layout/layout_sanlam_claims_0".equals(tag)) {
          return new LayoutSanlamClaimsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_sanlam_claims is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTSCANTOPAY: {
        if ("layout/layout_scan_to_pay_0".equals(tag)) {
          return new LayoutScanToPayBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_scan_to_pay is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTSCHOOLFEES: {
        if ("layout/layout_school_fees_0".equals(tag)) {
          return new LayoutSchoolFeesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_school_fees is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTSELECTUSERTYPE: {
        if ("layout/layout_select_user_type_0".equals(tag)) {
          return new LayoutSelectUserTypeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_select_user_type is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTSIGNUP: {
        if ("layout/layout_sign_up_0".equals(tag)) {
          return new LayoutSignUpBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_sign_up is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTSIGNUPORIG: {
        if ("layout/layout_sign_up_orig_0".equals(tag)) {
          return new LayoutSignUpOrigBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_sign_up_orig is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTSSNIT: {
        if ("layout/layout_ssnit_0".equals(tag)) {
          return new LayoutSsnitBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_ssnit is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTTRANSACTIONFAILURE: {
        if ("layout/layout_transaction_failure_0".equals(tag)) {
          return new LayoutTransactionFailureBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_transaction_failure is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTTRANSACTIONLIST: {
        if ("layout/layout_transaction_list_0".equals(tag)) {
          return new LayoutTransactionListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_transaction_list is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTTRANSACTIONSTATUS: {
        if ("layout/layout_transaction_status_0".equals(tag)) {
          return new LayoutTransactionStatusBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_transaction_status is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTTRANSACTIONSUCCESS: {
        if ("layout/layout_transaction_success_0".equals(tag)) {
          return new LayoutTransactionSuccessBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_transaction_success is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTVERIFYOTP: {
        if ("layout/layout_verify_otp_0".equals(tag)) {
          return new LayoutVerifyOtpBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_verify_otp is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTVERIFYUTILITYPAYMENT: {
        if ("layout/layout_verify_utility_payment_0".equals(tag)) {
          return new LayoutVerifyUtilityPaymentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_verify_utility_payment is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTWEBVIEW: {
        if ("layout/layout_webview_0".equals(tag)) {
          return new LayoutWebviewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_webview is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTWESTERNUNION: {
        if ("layout/layout_western_union_0".equals(tag)) {
          return new LayoutWesternUnionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_western_union is invalid. Received: " + tag);
      }
      case  LAYOUT_PAGEINDICATORLAYOUT: {
        if ("layout/page_indicator_layout_0".equals(tag)) {
          return new PageIndicatorLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for page_indicator_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_QRBOTTOMSHEETDIALOG: {
        if ("layout/qr_bottom_sheet_dialog_0".equals(tag)) {
          return new QrBottomSheetDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for qr_bottom_sheet_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_SPLASH: {
        if ("layout/splash_0".equals(tag)) {
          return new SplashBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for splash is invalid. Received: " + tag);
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      // find which method will have it. -1 is necessary becausefirst id starts with 1;
      int methodIndex = (localizedLayoutId - 1) / 50;
      switch(methodIndex) {
        case 0: {
          return internalGetViewDataBinding0(component, view, localizedLayoutId, tag);
        }
        case 1: {
          return internalGetViewDataBinding1(component, view, localizedLayoutId, tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(2);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    result.add(new com.github.gcacace.signaturepad.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(14);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "billResponse");
      sKeys.put(2, "index");
      sKeys.put(3, "inventory");
      sKeys.put(4, "isLoading");
      sKeys.put(5, "isOpen");
      sKeys.put(6, "item");
      sKeys.put(7, "marketplace");
      sKeys.put(8, "onClickAttachment");
      sKeys.put(9, "saleOrder");
      sKeys.put(10, "transaction");
      sKeys.put(11, "transactionsViewModel");
      sKeys.put(12, "viewModel");
      sKeys.put(13, "viewmodel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(90);

    static {
      sKeys.put("layout/activity_payment_progress_0", com.woleapp.R.layout.activity_payment_progress);
      sKeys.put("layout/activity_signature_2_0", com.woleapp.R.layout.activity_signature_2);
      sKeys.put("layout-land/activity_signature_2_0", com.woleapp.R.layout.activity_signature_2);
      sKeys.put("layout/app_bar_main_0", com.woleapp.R.layout.app_bar_main);
      sKeys.put("layout/dialog_0", com.woleapp.R.layout.dialog);
      sKeys.put("layout/dialog_fragment_bluetooth_list_0", com.woleapp.R.layout.dialog_fragment_bluetooth_list);
      sKeys.put("layout/dialog_fragment_bluetooth_list_printer_0", com.woleapp.R.layout.dialog_fragment_bluetooth_list_printer);
      sKeys.put("layout/dialog_logout_0", com.woleapp.R.layout.dialog_logout);
      sKeys.put("layout/dialog_reset_password_0", com.woleapp.R.layout.dialog_reset_password);
      sKeys.put("layout/dialog_support_0", com.woleapp.R.layout.dialog_support);
      sKeys.put("layout/dialog_utilities_status_0", com.woleapp.R.layout.dialog_utilities_status);
      sKeys.put("layout/fragment_agency_transaction_0", com.woleapp.R.layout.fragment_agency_transaction);
      sKeys.put("layout/fragment_ebills_list_0", com.woleapp.R.layout.fragment_ebills_list);
      sKeys.put("layout/fragment_ebills_webview_0", com.woleapp.R.layout.fragment_ebills_webview);
      sKeys.put("layout/fragment_health_checker_0", com.woleapp.R.layout.fragment_health_checker);
      sKeys.put("layout/fragment_remittance_0", com.woleapp.R.layout.fragment_remittance);
      sKeys.put("layout/item_services_dropdown_0", com.woleapp.R.layout.item_services_dropdown);
      sKeys.put("layout/layout_add_inventory_0", com.woleapp.R.layout.layout_add_inventory);
      sKeys.put("layout/layout_agent_0", com.woleapp.R.layout.layout_agent);
      sKeys.put("layout/layout_airtime_or_data_0", com.woleapp.R.layout.layout_airtime_or_data);
      sKeys.put("layout/layout_bank_details_0", com.woleapp.R.layout.layout_bank_details);
      sKeys.put("layout/layout_cable_tv_0", com.woleapp.R.layout.layout_cable_tv);
      sKeys.put("layout/layout_camera_0", com.woleapp.R.layout.layout_camera);
      sKeys.put("layout/layout_card_details_0", com.woleapp.R.layout.layout_card_details);
      sKeys.put("layout/layout_card_webview_0", com.woleapp.R.layout.layout_card_webview);
      sKeys.put("layout/layout_cash_in_0", com.woleapp.R.layout.layout_cash_in);
      sKeys.put("layout/layout_cash_in_options_0", com.woleapp.R.layout.layout_cash_in_options);
      sKeys.put("layout/layout_cash_in_options_new_0", com.woleapp.R.layout.layout_cash_in_options_new);
      sKeys.put("layout/layout_cashout_0", com.woleapp.R.layout.layout_cashout);
      sKeys.put("layout/layout_check_out_page_0", com.woleapp.R.layout.layout_check_out_page);
      sKeys.put("layout/layout_collections_0", com.woleapp.R.layout.layout_collections);
      sKeys.put("layout/layout_collections_new_0", com.woleapp.R.layout.layout_collections_new);
      sKeys.put("layout/layout_dashboard_0", com.woleapp.R.layout.layout_dashboard);
      sKeys.put("layout/layout_fines_bails_0", com.woleapp.R.layout.layout_fines_bails);
      sKeys.put("layout/layout_fund_wallet_0", com.woleapp.R.layout.layout_fund_wallet);
      sKeys.put("layout/layout_funeral_donations_0", com.woleapp.R.layout.layout_funeral_donations);
      sKeys.put("layout/layout_home_0", com.woleapp.R.layout.layout_home);
      sKeys.put("layout/layout_internet_subscription_0", com.woleapp.R.layout.layout_internet_subscription);
      sKeys.put("layout/layout_inventory_list_0", com.woleapp.R.layout.layout_inventory_list);
      sKeys.put("layout/layout_invoice_preview_0", com.woleapp.R.layout.layout_invoice_preview);
      sKeys.put("layout/layout_item_list_inventory_0", com.woleapp.R.layout.layout_item_list_inventory);
      sKeys.put("layout/layout_item_list_inventory_2_0", com.woleapp.R.layout.layout_item_list_inventory_2);
      sKeys.put("layout/layout_item_list_inventory_orig_0", com.woleapp.R.layout.layout_item_list_inventory_orig);
      sKeys.put("layout/layout_item_list_invoice_preview_0", com.woleapp.R.layout.layout_item_list_invoice_preview);
      sKeys.put("layout/layout_item_list_transaction_0", com.woleapp.R.layout.layout_item_list_transaction);
      sKeys.put("layout/layout_item_list_transactions_0", com.woleapp.R.layout.layout_item_list_transactions);
      sKeys.put("layout/layout_login_0", com.woleapp.R.layout.layout_login);
      sKeys.put("layout/layout_main_0", com.woleapp.R.layout.layout_main);
      sKeys.put("layout/layout_marketplace_container_0", com.woleapp.R.layout.layout_marketplace_container);
      sKeys.put("layout/layout_marketplace_dealer_item_0", com.woleapp.R.layout.layout_marketplace_dealer_item);
      sKeys.put("layout/layout_marketplace_home_0", com.woleapp.R.layout.layout_marketplace_home);
      sKeys.put("layout/layout_marketplace_item_0", com.woleapp.R.layout.layout_marketplace_item);
      sKeys.put("layout/layout_marketplace_item_description_0", com.woleapp.R.layout.layout_marketplace_item_description);
      sKeys.put("layout/layout_marketplace_product_list_0", com.woleapp.R.layout.layout_marketplace_product_list);
      sKeys.put("layout/layout_marketplace_received_orders_0", com.woleapp.R.layout.layout_marketplace_received_orders);
      sKeys.put("layout/layout_marketplace_store_0", com.woleapp.R.layout.layout_marketplace_store);
      sKeys.put("layout/layout_marketplace_store_dashboard_0", com.woleapp.R.layout.layout_marketplace_store_dashboard);
      sKeys.put("layout/layout_merchant_0", com.woleapp.R.layout.layout_merchant);
      sKeys.put("layout/layout_merchant_sign_in_0", com.woleapp.R.layout.layout_merchant_sign_in);
      sKeys.put("layout/layout_merchant_transaction_list_item_0", com.woleapp.R.layout.layout_merchant_transaction_list_item);
      sKeys.put("layout/layout_moneygram_0", com.woleapp.R.layout.layout_moneygram);
      sKeys.put("layout/layout_passport_0", com.woleapp.R.layout.layout_passport);
      sKeys.put("layout/layout_payment_card_successful_0", com.woleapp.R.layout.layout_payment_card_successful);
      sKeys.put("layout/layout_payment_complete_0", com.woleapp.R.layout.layout_payment_complete);
      sKeys.put("layout/layout_payment_mode_0", com.woleapp.R.layout.layout_payment_mode);
      sKeys.put("layout/layout_payment_successful_0", com.woleapp.R.layout.layout_payment_successful);
      sKeys.put("layout/layout_power_or_electricity_0", com.woleapp.R.layout.layout_power_or_electricity);
      sKeys.put("layout/layout_qr_activation_0", com.woleapp.R.layout.layout_qr_activation);
      sKeys.put("layout/layout_qr_registration_0", com.woleapp.R.layout.layout_qr_registration);
      sKeys.put("layout/layout_quick_transaction_0", com.woleapp.R.layout.layout_quick_transaction);
      sKeys.put("layout/layout_ria_0", com.woleapp.R.layout.layout_ria);
      sKeys.put("layout/layout_sanlam_0", com.woleapp.R.layout.layout_sanlam);
      sKeys.put("layout/layout_sanlam_claims_0", com.woleapp.R.layout.layout_sanlam_claims);
      sKeys.put("layout/layout_scan_to_pay_0", com.woleapp.R.layout.layout_scan_to_pay);
      sKeys.put("layout/layout_school_fees_0", com.woleapp.R.layout.layout_school_fees);
      sKeys.put("layout/layout_select_user_type_0", com.woleapp.R.layout.layout_select_user_type);
      sKeys.put("layout/layout_sign_up_0", com.woleapp.R.layout.layout_sign_up);
      sKeys.put("layout/layout_sign_up_orig_0", com.woleapp.R.layout.layout_sign_up_orig);
      sKeys.put("layout/layout_ssnit_0", com.woleapp.R.layout.layout_ssnit);
      sKeys.put("layout/layout_transaction_failure_0", com.woleapp.R.layout.layout_transaction_failure);
      sKeys.put("layout/layout_transaction_list_0", com.woleapp.R.layout.layout_transaction_list);
      sKeys.put("layout/layout_transaction_status_0", com.woleapp.R.layout.layout_transaction_status);
      sKeys.put("layout/layout_transaction_success_0", com.woleapp.R.layout.layout_transaction_success);
      sKeys.put("layout/layout_verify_otp_0", com.woleapp.R.layout.layout_verify_otp);
      sKeys.put("layout/layout_verify_utility_payment_0", com.woleapp.R.layout.layout_verify_utility_payment);
      sKeys.put("layout/layout_webview_0", com.woleapp.R.layout.layout_webview);
      sKeys.put("layout/layout_western_union_0", com.woleapp.R.layout.layout_western_union);
      sKeys.put("layout/page_indicator_layout_0", com.woleapp.R.layout.page_indicator_layout);
      sKeys.put("layout/qr_bottom_sheet_dialog_0", com.woleapp.R.layout.qr_bottom_sheet_dialog);
      sKeys.put("layout/splash_0", com.woleapp.R.layout.splash);
    }
  }
}
