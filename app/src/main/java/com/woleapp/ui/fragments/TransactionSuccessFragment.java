package com.woleapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.woleapp.R;
import com.woleapp.databinding.LayoutTransactionSuccessBinding;
import com.woleapp.util.Utilities;

public class TransactionSuccessFragment extends BaseFragment {
    Context context;
    private LayoutTransactionSuccessBinding binding;
    Utilities utilities;
    private boolean canGoBack = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);

        canGoBack = false;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onBackButtonClick(view);
       // binding.btnContinue.setOnClickListener(this);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_transaction_success, container, false);
        return binding.getRoot();
    }

   /* @Override
    public void onClick(View v){
        if(v == binding.btnContinue){
            addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
            canGoBack = true;
        }
    }*/
    public void onBackButtonClick(View view) {
        addFragmentWithoutRemove(R.id.container_main, new DashboardFragment(), DashboardFragment.class.getSimpleName());
        canGoBack = true;
        getActivity().finish();
    }
    /*@Override
    public void onBackPressed() {
        if (canGoBack) {
            super.onBackPressed();
        }
        // do nothing
    }*/
}
