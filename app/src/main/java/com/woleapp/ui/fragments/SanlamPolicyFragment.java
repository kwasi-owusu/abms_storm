package com.woleapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.woleapp.R;
import com.woleapp.databinding.LayoutSanlamPolicyBinding;
import com.woleapp.util.Utilities;

public class SanlamPolicyFragment extends BaseFragment{
    Context context;
    LayoutSanlamPolicyBinding binding;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_sanlam_policy, container, false);
        return binding.getRoot();
    }
}
