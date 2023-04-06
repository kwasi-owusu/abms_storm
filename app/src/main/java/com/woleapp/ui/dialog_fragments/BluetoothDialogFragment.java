
package com.woleapp.ui.dialog_fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;

import com.woleapp.R;
import com.woleapp.databinding.DialogFragmentBluetoothListBinding;
import smartpesa.sdk.devices.SpDevice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BluetoothDialogFragment<T extends SpDevice> extends BaseDialogFragment {

    /*@BindView(R.id.cancel_btn) Button cancelBtn;
    @BindView(R.id.bluetooth_device_hint) TextView btSelectionHint;
    @BindView(R.id.list) ListView list;*/

    protected DeviceSelectedListener<T> mListener;
    protected BluetoothAdapter<T> adapter;
    protected List<T> data = new ArrayList<>();

    DialogFragmentBluetoothListBinding binding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.dialog_fragment_bluetooth_list, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_bluetooth_list, container, false);
        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.setTitle(R.string.select_bluetooth_device);
        d.setCancelable(false);
        return d;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.bluetoothDeviceHint.setText(getHintResId());

        adapter = new BluetoothAdapter<>(getActivity(), data);
        binding.list.setAdapter(adapter);

        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onSelected(data.get(position));
                dismiss();
            }
        });

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @StringRes
    protected int getHintResId() {
        return R.string.terminal_selection_hint;
    }

    public void updateDevices(Collection<T> devices) {
        data.clear();
        data.addAll(devices);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void setSelectedListener(DeviceSelectedListener<T> listener) {
        mListener = listener;
    }

    public interface DeviceSelectedListener<T> {
        void onSelected(T device);
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
