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
import com.woleapp.databinding.LayoutTransactionFailureBinding;
import com.woleapp.util.Utilities;

public class PaymentFailedFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutTransactionFailureBinding binding;
    Utilities utilities;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnContinue.setOnClickListener(this);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_transaction_failure, container, false);
        return binding.getRoot();
    }
    public void onClick(View v){
        if(v == binding.btnContinue){
            addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
        }
    }
}
