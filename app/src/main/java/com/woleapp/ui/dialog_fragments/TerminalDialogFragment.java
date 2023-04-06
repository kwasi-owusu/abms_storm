package com.woleapp.ui.dialog_fragments;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import smartpesa.sdk.ServiceManager;
import smartpesa.sdk.devices.SpTerminal;

public class TerminalDialogFragment extends BluetoothDialogFragment<SpTerminal> {

    ServiceManager mServiceManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceManager = ServiceManager.get(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //cancelBtn
        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mServiceManager.stopScan();
                getActivity().finish();
            }
        });
    }
}
