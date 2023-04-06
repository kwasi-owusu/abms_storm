package com.woleapp.ui.dialog_fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.databinding.DataBindingUtil;
import com.woleapp.R;
import com.woleapp.databinding.DialogFragmentBluetoothListPrinterBinding;
import com.woleapp.util.PreferredTerminalUtils;
import smartpesa.sdk.ServiceManager;
import smartpesa.sdk.devices.SpDevice;
import smartpesa.sdk.devices.SpTerminal;

import java.util.*;

public class BluetoothDialogFragmentPrinter<T extends SpDevice> extends BaseDialogFragment {

    /*@BindView(R.id.cancel_btn) Button cancelBtn;
    @BindView(R.id.list) ListView list;
    @BindView(R.id.preferredTerminalCB) CheckBox preferredCB;
    @BindView(R.id.bluetoothTitleTv) TextView bluetoothTitleTv;*/

    protected TerminalSelectedListener<T> mListener;
    protected BluetoothAdapter<T> adapter;
    protected List<T> data = new ArrayList<>();
    PreferredTerminalUtils mPreferredTerminalUtils;

    DialogFragmentBluetoothListPrinterBinding binding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_bluetooth_list_printer, container, false);
        return binding.getRoot();
        //return inflater.inflate(R.layout.dialog_fragment_bluetooth_list_printer, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.setCancelable(false);
        return d;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*if (getMerchantComponent(getActivity()) != null) {
            VerifiedMerchantInfo merchantComponent = getMerchantComponent(getActivity()).provideMerchant();
            if (merchantComponent != null) {
                mPreferredTerminalUtils = new PreferredTerminalUtils(getActivity(), merchantComponent.getMerchantCode(), merchantComponent.getOperatorCode());
            }
        }*/

        adapter = new BluetoothAdapter<>(getActivity(), data);
        binding.list.setAdapter(adapter);

        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onSelected(data.get(position));

                //check if the checkbox is checked, if so save as preferred terminal
                if (binding.preferredTerminalCB.isChecked()) {
                    mPreferredTerminalUtils.saveTerminal((SpTerminal) data.get(position));
                }

                dismiss();
            }
        });

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
                ServiceManager.get(getActivity()).stopScan();
                mListener.onCancelled();
                getActivity().finish();
            }
        });
    }

    public void updateDevices(Collection<T> devices) {
        data.clear();
        data.addAll(devices);

        ObjectComparator comparator = new ObjectComparator();
        Collections.sort(data, comparator);

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public class ObjectComparator implements Comparator<T> {
        public int compare(T obj1, T obj2) {
            return obj1.getName().compareTo(obj2.getName());
        }
    }

    public void setSelectedListener(TerminalSelectedListener<T> listener) {
        mListener = listener;
    }

    public interface TerminalSelectedListener<T> {
        void onSelected(T device);
        void onCancelled();
    }

    protected static class BluetoothAdapter<T extends SpDevice> extends ArrayAdapter<T> {

        public BluetoothAdapter(Context context, List<T> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            ((TextView) view).setText(getItem(position).getName());
            return view;
        }
    }
}
