package com.woleapp.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.woleapp.R;
import com.woleapp.databinding.ActivityPaymentProgressBinding;
import com.woleapp.model.User;
import com.woleapp.network.APIServiceClient;
import com.woleapp.ui.dialog_fragments.BluetoothDialogFragment;
import com.woleapp.ui.dialog_fragments.BluetoothDialogFragmentPrinter;
import com.woleapp.ui.dialog_fragments.PrinterDialogFragment;
import com.woleapp.ui.dialog_fragments.TerminalDialogFragment;
import com.woleapp.ui.fragments.DashboardFragment;
import com.woleapp.util.Constants;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.SmartPesaTransactionType;
import com.woleapp.util.Utilities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import smartpesa.sdk.ServiceManager;
import smartpesa.sdk.SmartPesa;
import smartpesa.sdk.devices.SpPrinterDevice;
import smartpesa.sdk.devices.SpTerminal;
import smartpesa.sdk.error.SpException;
import smartpesa.sdk.error.SpPrinterException;
import smartpesa.sdk.error.SpSessionException;
import smartpesa.sdk.error.SpTransactionException;
import smartpesa.sdk.interfaces.PrinterScanningCallback;
import smartpesa.sdk.interfaces.PrintingCallback;
import smartpesa.sdk.interfaces.TerminalScanningCallback;
import smartpesa.sdk.interfaces.TransactionCallback;
import smartpesa.sdk.models.loyalty.Loyalty;
import smartpesa.sdk.models.loyalty.LoyaltyTransaction;
import smartpesa.sdk.models.merchant.VerifiedMerchantInfo;
import smartpesa.sdk.models.merchant.VerifyMerchantCallback;
import smartpesa.sdk.models.operator.LogoutCallback;
import smartpesa.sdk.models.printing.AbstractPrintingDefinition;
import smartpesa.sdk.models.receipt.GetReceiptCallback;
import smartpesa.sdk.models.transaction.Card;
import smartpesa.sdk.models.transaction.Transaction;
import smartpesa.sdk.models.version.GetVersionCallback;
import smartpesa.sdk.models.version.SpVersionException;
import smartpesa.sdk.models.version.Version;

import static com.woleapp.util.Constants.USER_TYPE_MERCHANT;


public class PaymentProgressActivity extends AppCompatActivity {

    public static final String KEY_AMOUNT = "amount";
    private static final String BLUETOOTH_FRAGMENT_TAG = "bluetooth";
    private SmartPesaTransactionType transactionType;
    ProgressDialog mProgressDialog;

    private User user;

    /*@BindView(R.id.amount_tv) TextView amountTv;
    @BindView(R.id.progress_tv) TextView progressTv;
    @BindView(R.id.receipt_btn) Button receiptBtn;*/
    double amount;
    String commodityName;
    String description;
    ServiceManager mServiceManager;
    List<AbstractPrintingDefinition> dataToPrint;
    ActivityPaymentProgressBinding binding;
    Utilities utilities;
    String merchantCode = "STORM", operatorCode = "100", operatorPin = "3607";
    Transaction transactionObj;
    int transaction_type = 0;
    Context context = this;
    String productName;
    String reference;
    int productId;
    int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_progress);
        user = SharedPrefManager.getUser();

        mServiceManager = ServiceManager.get(context);
        if (mServiceManager.getCachedMerchant() == null || !Objects.equals(mServiceManager.getCachedMerchant().getMerchantCode(), merchantCode))
            mServiceManager.getVersion(new GetVersionCallback() {
                @Override
                public void onSuccess(Version version) {
                    // Session has been established,
                    // you can now call other methods. e.g. ServiceManager.verifyMerchant
                    verifyMerchant();
                }

                @Override
                public void onError(SpException exception) {
                    if (exception instanceof SpVersionException) {
                        // Handle specific Version error
                        SpVersionException e = (SpVersionException) exception;
                        switch (e.getReason()) {

                            case UPGRADE_OPTIONAL:
                                // There's a new SDK version available but it's not necessary to upgrade. You can treat this as a success.
                                verifyMerchant();
                                break;
                            case UPGRADE_MANDATORY:
                                // There's a mandatory upgrade. This SDK is no longer able to transact.
                                break;

                            case VERSION_CHECK_ERROR:
                                // Invalid SDK version.
                                break;

                            default:

                        }
                    } else {
                        String errorMessage = exception.getMessage();
                        utilities.showAlertDialogOk(errorMessage);
                        // Display the message to the user.

                    }
                }
            });
        else {
            Log.e("TAG", mServiceManager.getCachedMerchant().getMerchantCode());
            scanTerminal();
        }

        utilities = new Utilities(this);
        transactionType = SmartPesaTransactionType.fromEnumId(1);
        binding.receiptBtn.setVisibility(View.INVISIBLE);
        binding.closeBtn.setVisibility(View.INVISIBLE);
        amount = getIntent().getExtras().getDouble(KEY_AMOUNT);
        transaction_type = getIntent().getExtras().getInt("transaction_type", 0);
        commodityName = getIntent().getExtras().getString("COMMODITY_NAME");
        description = getIntent().getExtras().getString("DESCRIPTION");
        productId = getIntent().getExtras().getInt("PRODUCTID");
        quantity = getIntent().getExtras().getInt("QUANTITY");
        reference = getIntent().getExtras().getString("TRANSACTION_REF");

        Float convenience_fee = Float.parseFloat(Double.toString(getIntent().getExtras().getDouble("convenience_fee")));
        binding.amountTv.setText("Amount: " + utilities.getFormattedAmount(amount));
        binding.tvConvenienceFee.setVisibility(View.VISIBLE);
        binding.tvConvenienceFee.setText("Convenience Fee Included: " + utilities.getFormattedAmount(convenience_fee));
        //fakePayment();
    }

    private void fakePayment() {
        Intent intent = new Intent(context, HomeActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("completed", true);
        intent.putExtra("productId", productId);
        intent.putExtra("quantity", quantity);
        intent.putExtra("reference", reference);
        intent.putExtra("amount", amount);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void verifyMerchant() {
        //Log.e("TAG", "verifying merchant with pin: "+operatorPin);
        //Toast.makeText(context, "Verifying merchant with pin: "+operatorPin, Toast.LENGTH_SHORT).show();
        mServiceManager.verifyMerchant(merchantCode, operatorCode, operatorPin, new VerifyMerchantCallback() {


            @Override
            public void onSuccess(VerifiedMerchantInfo verifiedMerchantInfo) {
                //scan for bluetooth device
                scanTerminal();
            }

            @Override
            public void onError(SpException exception) {
                Toast.makeText(context, "SP  verify merchant  exception", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void scanTerminal() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        if (mServiceManager != null) {
            mServiceManager.scanTerminal(new TerminalScanningCallback() {
                @Override
                public void onDeviceListRefresh(Collection<SpTerminal> collection) {
                    displayBluetoothDevice(collection);
                }

                @Override
                public void onScanStopped() {

                }

                @Override
                public void onScanTimeout() {

                }

                @Override
                public void onEnablingBluetooth(String s) {

                }

                @Override
                public void onBluetoothPermissionDenied(String[] strings) {

                }

            });
        }

    }

    public SmartPesa.TransactionParam buildTransactionParam(SpTerminal spTerminal) {

        SmartPesa.TransactionType smartpesa_transaction_type;
        if (transaction_type == Constants.TRANSACTION_CASH_IN) {
            smartpesa_transaction_type = SmartPesa.TransactionType.GOODS_AND_SERVICES;
        } else {
            smartpesa_transaction_type = SmartPesa.TransactionType.GOODS_AND_SERVICES;
        }
        //start the transaction
        SmartPesa.TransactionParam param = null;
        try {
            param = SmartPesa.TransactionParam.newBuilder()
                    .transactionType(smartpesa_transaction_type)//SmartPesa.TransactionType.GOODS_AND_SERVICES
                    .terminal(spTerminal)
                    .amount(new BigDecimal(Double.valueOf(amount)))
                    .from(SmartPesa.AccountType.DEFAULT)
                    .to(SmartPesa.AccountType.SAVINGS)
                    .cashBack(BigDecimal.ZERO)
                    .cardMode(SmartPesa.CardMode.SWIPE_OR_INSERT)
                    .extraParams(new HashMap<String, Object>())
                    .build();
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            binding.error.setText(e.getMessage());
        }

        return param;

    }

    private void performPayment(SpTerminal spTerminal) {
        //start the transaction
        SmartPesa.TransactionParam param;
        try {
            param = buildTransactionParam(spTerminal);
        } catch (Exception e) {
            Toast.makeText(context, "Unable to initialize a transaction with the selected terminal: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            binding.error.setText(e.getMessage());
            return;
        }
        if (param == null) {
            Toast.makeText(context, "Can't initialize a transaction with the selected terminal", Toast.LENGTH_SHORT).show();
            binding.error.setText("Can't initialize a transaction with the selected terminal");
            return;
        }
        try {
            mServiceManager.performTransaction(param, new TransactionCallback() {
                @Override
                public void onProgressTextUpdate(String s) {
                    Log.e(PaymentProgressActivity.class.getName(), "======================================>" + s);
                    binding.progressTv.setText(s);
                }

                @Override
                public void onDeviceConnected(SpTerminal spTerminal) {

                }

                @Override
                public void onDeviceDisconnected(SpTerminal spTerminal) {

                }

                @Override
                public void onBatteryStatus(SmartPesa.BatteryStatus batteryStatus) {

                }

                @Override
                public void onShowSelectApplicationPrompt(List<String> list) {

                }

                @Override
                public void onWaitingForCard(String s, SmartPesa.CardMode cardMode) {
                    binding.progressTv.setText("Insert/swipe card");
                }

                @Override
                public void onShowInsertChipAlertPrompt() {
                    binding.progressTv.setText("Insert chip card");
                }

                @Override
                public void onReadCard(Card card) {

                }

                @Override
                public void onShowPinAlertPrompt() {
                    binding.progressTv.setText("Enter PIN on pesaPOD");
                }

                @Override
                public void onPinEntered() {

                }

                @Override
                public void onShowInputPrompt() {

                }

                @Override
                public void onReturnInputStatus(SmartPesa.InputStatus inputStatus, String s) {

                }

                @Override
                public void onShowConfirmAmountPrompt() {
                    binding.progressTv.setText("Confirm amount on pesaPOD");
                }

                @Override
                public void onAmountConfirmed(boolean b) {

                }

                @Override
                public void onTransactionFinished(SmartPesa.TransactionType transactionType, boolean isSuccess, @Nullable Transaction transaction, @Nullable SmartPesa.Verification verification, @Nullable SpTransactionException exception) {
                    final Transaction transactionData = transaction;
                    transactionObj = transaction;
                    if (isSuccess) {

                        binding.progressTv.setText("Transaction successful");
                        binding.receiptBtn.setVisibility(View.VISIBLE);
                        binding.closeBtn.setVisibility(View.VISIBLE);

                        binding.closeBtn.setOnClickListener(v -> {

                            Intent intent = new Intent(context, HomeActivity.class);

                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("completed", true);
                            intent.putExtra("productId", productId);
                            intent.putExtra("quantity", quantity);
                            intent.putExtra("reference", reference);
                            intent.putExtra("amount", amount);

                            setResult(RESULT_OK, intent);
                            finish();

                        });

                        binding.receiptBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                printReceipt(transactionData.getTransactionResult().getTransactionId());
                            }
                        });

                    } else {


                        binding.progressTv.setText(exception.getMessage());
                        binding.closeBtn.setVisibility(View.VISIBLE);
                        binding.closeBtn.setText("Go Back");

                        binding.closeBtn.setOnClickListener(v -> {
                            onBackPressed();
                        });
                        if (transactionData != null) {

                            binding.receiptBtn.setVisibility(View.VISIBLE);

                            binding.receiptBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    printReceipt(transactionData.getTransactionResult().getTransactionId());
                                }
                            });
                        }
                    }
                }

                @Override
                public void onError(SpException exception) {
                    Toast.makeText(context, "An error occurred: " + exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.e(PaymentProgressActivity.class.getName(), "------------------------------------>" + exception.getMessage());
                    binding.progressTv.setText(exception.getMessage());
                    binding.error.setText(exception.getMessage());
//                if("Canceled.".equalsIgnoreCase(exception.getMessage())) {
//                    Intent intent = new Intent(context, HomeActivity.class);
//
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra("completed", true);
//                    intent.putExtra("productId", 54);
//                    intent.putExtra("quantity", 3);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                }

                }


                @Override
                public void onStartPostProcessing(String providerName, Transaction transaction) {

                }

                @Override
                public void onReturnLoyaltyBalance(Loyalty loyalty) {

                }

                @Override
                public void onShowLoyaltyRedeemablePrompt(LoyaltyTransaction loyaltyTransaction) {

                }

                @Override
                public void onLoyaltyCancelled() {

                }

                @Override
                public void onLoyaltyApplied(LoyaltyTransaction loyaltyTransaction) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            binding.error.setText(e.getMessage());
        }
    }

    private void printReceipt(final UUID transactionId) {

        final SmartPesa.ReceiptFormat[] receiptFormats = {
                SmartPesa.ReceiptFormat.CUSTOMER,
                SmartPesa.ReceiptFormat.MERCHANT
        };
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.select_receipt_format))
                .setAdapter(
                        new ArrayAdapter<SmartPesa.ReceiptFormat>(
                                context,
                                android.R.layout.simple_list_item_1,
                                receiptFormats
                        ),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SmartPesa.ReceiptFormat receiptFormat = receiptFormats[which];
                                fetchReceiptAndPrint(transactionId, receiptFormat);
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        String jsonString = new Gson().toJson(transactionObj);
                        Intent intent = new Intent();
                        intent.putExtra("transaction", jsonString);
                        ((Activity) context).setResult(Activity.RESULT_OK, intent);
                    }
                })
                .show();

    }

    public void showDialog(String message) {
        new AlertDialog.Builder(context)
                .setTitle("Alert")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    //print receipt start here
    protected void fetchReceiptAndPrint(UUID transactionId, SmartPesa.ReceiptFormat receiptFormat) {

        HashMap<String, Object> config = new HashMap<>();
        final ProgressDialog mp = new ProgressDialog(this);
        mp.setTitle(getString(R.string.app_name));
        mp.setMessage(getString(R.string.loading_receipt));
        mp.show();
        mServiceManager.getReceipt(transactionId, receiptFormat, config, new GetReceiptCallback() {
            @Override
            public void onSuccess(List<AbstractPrintingDefinition> abstractPrintingDefinitions) {

                mp.dismiss();
                dataToPrint = abstractPrintingDefinitions;

                mServiceManager.scanPrinter(new PrinterScanningCallback() {
                    @Override
                    public void onDeviceListRefresh(Collection<SpPrinterDevice> collection) {

                        displayPrinterDevice(collection);
                    }

                    @Override
                    public void onScanStopped() {

                    }

                    @Override
                    public void onScanTimeout() {

                    }

                    @Override
                    public void onEnablingBluetooth(String s) {
                        Log.e("onEnablingBluetooth", s + "**");
                    }

                    @Override
                    public void onBluetoothPermissionDenied(String[] strings) {
                        if (strings != null) {
                            Log.e("onEnablingBluetooth", strings[0] + "**");
                        } else {
                            Log.e("onEnablingBluetooth", null + "**");
                        }

                    }
                });
            }

            @Override
            public void onError(SpException exception) {


                if (exception instanceof SpSessionException) {
                    mp.dismiss();
                    //show the expired message
                    //UIHelper.showToast(context, getResources().getString(R.string.session_expired));
                    Toast.makeText(context, getResources().getString(R.string.session_expired), Toast.LENGTH_LONG).show();
                    //finish
                    ((Activity) context).setResult(Activity.RESULT_CANCELED, null);
                    finish();

                } else {
                    mp.dismiss();
                    showDialog(exception.getMessage());

                }
            }
        });
    }

    //display the list of bluetooth devices
    public void displayBluetoothDevice(Collection<SpTerminal> devices) {
        TerminalDialogFragment dialog;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(BLUETOOTH_FRAGMENT_TAG);
        if (fragment == null) {
            dialog = new TerminalDialogFragment();
            dialog.show(getSupportFragmentManager(), BLUETOOTH_FRAGMENT_TAG);
        } else {
            dialog = (TerminalDialogFragment) fragment;
        }
        dialog.setSelectedListener(new DeviceSelectedListenerImpl());
        dialog.updateDevices(devices);
    }

    //start the transaction when the bluetooth device is selected
    private class DeviceSelectedListenerImpl implements BluetoothDialogFragment.DeviceSelectedListener<SpTerminal> {
        @Override
        public void onSelected(SpTerminal device) {
            performPayment(device);
        }
    }

    private void displayPrinterDevice(Collection<SpPrinterDevice> devices) {
        //try {
        PrinterDialogFragment dialog;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(BLUETOOTH_FRAGMENT_TAG);
        if (fragment == null) {

            dialog = new PrinterDialogFragment();
            dialog.show(getSupportFragmentManager(), BLUETOOTH_FRAGMENT_TAG);
        } else {
            dialog = (PrinterDialogFragment) fragment;
        }
        dialog.setSelectedListener(new PrinterSelectedImpl());
        dialog.updateDevices(devices);
    }

    private class PrinterSelectedImpl implements BluetoothDialogFragmentPrinter.TerminalSelectedListener<SpPrinterDevice> {
        @Override
        public void onSelected(SpPrinterDevice device) {
            performPrint(device);
            if (mServiceManager != null) {
                mServiceManager.stopScan();
            }
        }

        @Override
        public void onCancelled() {

        }
    }

    private void performPrint(SpPrinterDevice device) {
        closeDialogFragment();
        mServiceManager.performPrint(SmartPesa.PrintingParam.withData(dataToPrint).printerDevice(device).build(), new PrintingCallback() {
            @Override
            public void onPrinterError(SpPrinterException errorMessage) {
                //if (isActivityDestroyed()) return;
                //UIHelper.showErrorDialog(context, getResources().getString(R.string.app_name), errorMessage.getMessage());
                showDialog(errorMessage.getMessage());
            }

            @Override
            public void onPrinterSuccess() {
                //if (isActivityDestroyed()) return;
                //UIHelper.log("here");
                closeDialogFragment();
            }
        });
    }

    //close the printer bluetooth list if already one is present
    private void closeDialogFragment() {
        Fragment dialogBluetoothList = getSupportFragmentManager().findFragmentByTag(BLUETOOTH_FRAGMENT_TAG);
        if (dialogBluetoothList != null) {
            DialogFragment dialogFragment = (DialogFragment) dialogBluetoothList;
            if (dialogFragment != null) {
                dialogFragment.dismiss();
            }
        }
    }
}
