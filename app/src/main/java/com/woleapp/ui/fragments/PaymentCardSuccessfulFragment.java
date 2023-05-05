package com.woleapp.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.netpluspay.netpossdk.NetPosSdk;
import com.pos.sdk.printer.POIPrinterManage;
import com.pos.sdk.printer.models.BitmapPrintLine;
import com.pos.sdk.printer.models.PrintLine;
import com.pos.sdk.printer.models.TextPrintLine;
import com.woleapp.R;
import com.woleapp.databinding.LayoutPaymentCardSuccessfulBinding;
import com.woleapp.databinding.LayoutPaymentSuccessfulBinding;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import timber.log.Timber;

public class PaymentCardSuccessfulFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    //private LayoutPaymentSuccessfulBinding binding;
    private LayoutPaymentCardSuccessfulBinding binding;
    Utilities utilities;
   // String branchName = SharedPrefManager.getBranchDetails().getBranchName();
   // String a_name = SharedPrefManager.getAgencyDetails().getAgencyName();
   // private String name;
    private String ref;
    private String amount;
    private static String merchantName = SharedPrefManager.getAgencyUser().getMerchantName();

//    public PaymentCardSuccessfulFragment(String name, String ref, String amount){
        public PaymentCardSuccessfulFragment(String ref, String amount){
    //   this.name = name;
        this.ref = ref;
        this.amount = amount;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);

        Log.e("REFERENCE", "Reference: " + ref);

    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnContinue.setOnClickListener(this);
        binding.btnMerchant.setOnClickListener(this);
        binding.btnHome.setOnClickListener(this);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_payment_card_successful, container, false);
        return binding.getRoot();
    }
    public void onClick(View v){
        if(v == binding.btnContinue){
            printReceipt(ref);
        }
        else if(v == binding.btnMerchant){
            printSecondReceipt(ref);
        }
        else if(v == binding.btnHome){
            addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
        }
    }

    private void printReceipt(String ref) {
        POIPrinterManage printerManage = NetPosSdk.getPrinterManager(context);
        printerManage.setLineSpace(2);
        printerManage.setPrintGray(3000);
        printerManage.cleanCache();

        TextPrintLine textPrintLine = new TextPrintLine();
        textPrintLine.setType(PrintLine.TEXT);
        textPrintLine.setContent(merchantName);
        textPrintLine.setBold(true);
        textPrintLine.setItalic(false);
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(TextPrintLine.FONT_LARGE);

        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("----------------------------------------------------" +
                "--------------------------------------------");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(" ");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Amount: " + "GH₵" +(amount));
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Ref No.: " + ref);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Date: " + utilities.getTodaysDate());
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Time: " + utilities.getTodaysTime());
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(" ");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Transaction Successful");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("--------------------------------------------------------------");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("THANK YOU");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Powered by Bsystems Limited");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("0302-254-340");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        Bitmap bitmap =
                BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.bsystemslogo);
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 100, false);

        BitmapPrintLine bitmapPrintLine = new BitmapPrintLine();
        bitmapPrintLine.setType(PrintLine.BITMAP);
        bitmapPrintLine.setBitmap(bitmap);
        bitmapPrintLine.setPosition(PrintLine.CENTER);

        printerManage.addPrintLine(bitmapPrintLine);

        printerManage.beginPrint(new POIPrinterManage.IPrinterListener() {
            @Override
            public void onError(int i, String s) {
                Timber.e("Printer error with code " + i + " and message" + s);
                Toast.makeText(context, "Printer Error", Toast.LENGTH_SHORT).show();
                //addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
            }

            @Override
            public void onFinish() {
                //   printSecondReceipt(ref);
                 Toast.makeText(context, "Printing job finished", Toast.LENGTH_SHORT).show();
               // binding.btnMerchant.setVisibility(View.VISIBLE);
               // addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                // addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
            }

            @Override
            public void onStart() {
                Toast.makeText(context, "Printing job started", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void printSecondReceipt(String ref) {
        POIPrinterManage printerManage = NetPosSdk.getPrinterManager(context);
        printerManage.setLineSpace(2);
        printerManage.setPrintGray(3000);
        printerManage.cleanCache();

        TextPrintLine textPrintLine = new TextPrintLine();
        textPrintLine.setType(PrintLine.TEXT);
        textPrintLine.setContent(merchantName);
        textPrintLine.setBold(true);
        textPrintLine.setItalic(false);
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(TextPrintLine.FONT_LARGE);

        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("----------------------------------------------------" +
                "--------------------------------------------");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(" ");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Amount: " + "GH₵" +(amount));
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Ref No.: " + ref);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Date: " + utilities.getTodaysDate());
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Time: " + utilities.getTodaysTime());
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(" ");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Payment Successful");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("--------------------------------------------------------------");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("THANK YOU");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Powered by Bsystems Limited");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("0302-254-340");
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        textPrintLine.setItalic(true);
        printerManage.addPrintLine(textPrintLine);

        Bitmap bitmap =
                BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.bsystemslogo);
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 100, false);

        BitmapPrintLine bitmapPrintLine = new BitmapPrintLine();
        bitmapPrintLine.setType(PrintLine.BITMAP);
        bitmapPrintLine.setBitmap(bitmap);
        bitmapPrintLine.setPosition(PrintLine.CENTER);

        printerManage.addPrintLine(bitmapPrintLine);

        printerManage.beginPrint(new POIPrinterManage.IPrinterListener() {
            @Override
            public void onError(int i, String s) {
                Timber.e("Printer error with code " + i + " and message" + s);
                Toast.makeText(context, "Printer Error", Toast.LENGTH_SHORT).show();
              //  addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
            }

            @Override
            public void onFinish() {
                //   printSecondReceipt(ref);
                Toast.makeText(context, "Printing job finished", Toast.LENGTH_SHORT).show();
                // binding.btnMerchant.setVisibility(View.VISIBLE);
               //  addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
                // addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
            }

            @Override
            public void onStart() {
                Toast.makeText(context, "Printing job started", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
