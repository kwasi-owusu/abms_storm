package com.woleapp.ui.fragments;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
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
import com.woleapp.databinding.LayoutPaymentSuccessfulBinding;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.ui.activity.MainActivity;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import java.util.Timer;
import java.util.TimerTask;

import timber.log.Timber;

public class PaymentSuccessfulFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutPaymentSuccessfulBinding binding;
    Utilities utilities;
    private Timer timer;
    private final int INTERVAL = 1500;
    private static int SPLASH_TIME_OUT = 1500;
    String branchName = SharedPrefManager.getBranchDetails().getBranchName();
    String a_name = SharedPrefManager.getAgencyDetails().getAgencyName();
    private String name;
    private String number;
    private String ref;
    private String issuer;
    private String amount;
    public PaymentSuccessfulFragment(String name, String number, String ref, String issuer, String amount){
        this.name = name;
        this.number = number;
        this.ref = ref;
        this.issuer = issuer;
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
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_payment_successful, container, false);
        return binding.getRoot();
    }
    public void onClick(View v){
        if(v == binding.btnContinue){
            //addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
            printReceipt(name,number,ref,issuer,amount);
        }
    }
    private void printReceipt(String CustomerName, String CustomerNumber, String ref, String issuer, String amnt) {
        POIPrinterManage printerManage = NetPosSdk.getPrinterManager(context);
        printerManage.setLineSpace(2);
        printerManage.setPrintGray(3000);
        printerManage.cleanCache();

        Bitmap bitmap =
                BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.bsystemslogo);
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 100, false);

        BitmapPrintLine bitmapPrintLine = new BitmapPrintLine();
        bitmapPrintLine.setType(PrintLine.BITMAP);
        bitmapPrintLine.setBitmap(bitmap);
        bitmapPrintLine.setPosition(PrintLine.CENTER);

        TextPrintLine textPrintLine = new TextPrintLine();
        textPrintLine.setType(PrintLine.TEXT);
        textPrintLine.setContent("Agency Banking");
        textPrintLine.setBold(true);
        textPrintLine.setItalic(false);
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setSize(TextPrintLine.FONT_LARGE);

        printerManage.addPrintLine(bitmapPrintLine);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("----------------------------------------------------" +
                "--------------------------------------------");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(utilities.getTodaysDate() + "    " + utilities.getTodaysTime());
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setPosition(PrintLine.CENTER);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent(" ");
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Ref No.: " + ref);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Transaction Type: COLLECTION");
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Amount: " + "GH₵" +(amount));
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Network Issuer " + issuer);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Phone Number " + CustomerNumber);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Customer Name: " + CustomerName);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Agent Name: " + a_name);
        textPrintLine.setPosition(PrintLine.LEFT);
        textPrintLine.setSize(textPrintLine.FONT_NORMAL);
        textPrintLine.setBold(false);
        printerManage.addPrintLine(textPrintLine);

        textPrintLine.setContent("Branch Name: " + branchName);
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

        printerManage.beginPrint(new POIPrinterManage.IPrinterListener() {
            @Override
            public void onError(int i, String s) {
                Timber.e("Printer error with code " + i + " and message" + s);
                Toast.makeText(context, "Printer Error", Toast.LENGTH_SHORT).show();
                addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
            }

            @Override
            public void onFinish() {
                //  Toast.makeText(context, "Printing job finished", Toast.LENGTH_SHORT).show();
                addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
            }

            @Override
            public void onStart() {
                Toast.makeText(context, "Printing job started", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
