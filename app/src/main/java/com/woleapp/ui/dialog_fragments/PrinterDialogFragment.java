package com.woleapp.ui.dialog_fragments;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import smartpesa.sdk.ServiceManager;
import smartpesa.sdk.devices.SpPrinterDevice;

public class PrinterDialogFragment extends BluetoothDialogFragmentPrinter<SpPrinterDevice> {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.preferredTerminalCB.setVisibility(View.GONE);//preferredCB

        binding.bluetoothTitleTv.setText("Select a printer");

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
                ServiceManager.get(getActivity()).stopScan();
            }
        });

    }
}
