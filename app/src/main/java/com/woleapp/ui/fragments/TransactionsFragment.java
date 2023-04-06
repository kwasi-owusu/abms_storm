package com.woleapp.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.woleapp.R;
import com.woleapp.adapters.DashboardAdapter;
import com.woleapp.adapters.TransactionAdapter;
import com.woleapp.databinding.LayoutDashboardBinding;
import com.woleapp.databinding.LayoutInventoryListBinding;
import com.woleapp.db.AppDatabase;
import com.woleapp.db.LoadState;
import com.woleapp.model.Service;
import com.woleapp.model.Transactions;
import com.woleapp.model.User;
import com.woleapp.network.StormAPIClient;
import com.woleapp.util.Constants;
import com.woleapp.util.JWTHelper;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;
import com.woleapp.viewmodels.TransactionsViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TransactionsFragment extends Fragment implements Constants, View.OnClickListener {

    //Declaration of variables
    private LayoutInventoryListBinding binding;
    Drawable customErrorDrawable;
    private Utilities utilities;
    private ProgressDialog mProgressDialog;
    private TransactionAdapter adapter;
    String customer_type = "customer";
    private TransactionsViewModel transactionsViewModel;
    private SearchView searchView;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //return inflater.inflate(R.layout.layout_introduction, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_inventory_list, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.executePendingBindings();
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        utilities = new Utilities(requireContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //((AppCompatActivity) getActivity()).getSupportActionBar().showNewUserView();
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((PreLoginActivity) getActivity()).createBackButton("Sign up");*/
        transactionsViewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);
        binding.setTransactionsViewModel(transactionsViewModel);
        transactionsViewModel.setStormApiService(StormAPIClient.create(requireContext()));
        transactionsViewModel.setTransactionsDao(AppDatabase.getInstance(requireContext()).agentTransactionDao());
        adapter = new TransactionAdapter(onClick);
        binding.rvInventories.setAdapter(adapter);
        transactionsViewModel.getTransactions().observe(getViewLifecycleOwner(), transactions -> adapter.submitList(transactions));
        transactionsViewModel.getLoadState().observe(getViewLifecycleOwner(), loadState -> {
            if (loadState == LoadState.LOADING_ERROR) {
                Toast.makeText(requireContext(), "An error occurred while loading transactions", Toast.LENGTH_SHORT).show();
            }
        });
        binding.swipeRefresh.setOnRefreshListener(() -> transactionsViewModel.forceRefresh());
        transactionsViewModel.forceRefresh();
        binding.tvTitle.setText(R.string.tv_transactions);
        binding.etSearch.setVisibility(View.GONE);
        Bundle b1 = getArguments();
        /*if(b1!=null)
        {
            customer_type = b1.getString("customer_type","customer");
        }

        if(customer_type.equalsIgnoreCase("customer"))
        {
            binding.tvNoResults.setText("You don't have any customers yet");
        }
        else
        {
            binding.tvNoResults.setText("You don't have any suppliers yet");
        }*/
        binding.tvNoResults.setText(R.string.no_transactions_yet);
//        getTransactions();
    }


    private OnItemClickListener.OnItemClickCallback onClick = (view, position) -> {

        /*Transaction selectedCustomer = adapter.getSelectedCustomer(position);

        SMEApplication.getInstance().setCustomerToEdit(selectedCustomer*//*items.get(position)*//*);
        Bundle b1 = new Bundle();
        b1.putBoolean("isEditIntent",true);
        b1.putString("customer_type",customer_type);
        NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
        navController.navigate(R.id.action_bottom_nav_customers_to_nav_fragment_add_customer,b1);*/

        //Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_add_invoice_to_add_customer);
        //Toast.makeText(context,"Position:- "+position,Toast.LENGTH_SHORT).show();
    };

    @Override
    public void onClick(View view) {

    }


    public void showProgressBar() {
        mProgressDialog = ProgressDialog.show(requireContext(), null, null);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ffffff")));
        mProgressDialog.setCancelable(false);
    }

    public void dismissProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


//    public void getTransactions() {
//        User user = SharedPrefManager.getUser();
//        JsonObject jsonObject = new JsonObject();
//
//        if (user.getUser_type() == USER_TYPE_AGENT) {
//            jsonObject.addProperty("user_type", "agent");
//        } else {
//            jsonObject.addProperty("user_type", "merchant");
//        }
//        jsonObject.addProperty("user_id", user.getUser_id());
//
//        //data.put(PN_USER_ID,"1");
//        //getTransactions(jsonObject);
//    }

//    public void getTransactions(JsonObject data) {
//        String userToken = SharedPrefManager.getUserToken();
//        String stormId = JWTHelper.getStormId(userToken);
//        StormAPIClient.getTransactions(stormId, requireContext())
//                .subscribe(res -> {
//
//                });
//    }
    /*@Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);
        Log.e("onCreateOptionsMenu","onCreateOptionsMenu");
        *//*final MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(true);*//*


        MenuItem searchMenu = menu.findItem(R.id.action_search);
        searchMenu.setVisible(true);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                if(adapter!=null)// && adapter.getItemCount()>0
                {
                    adapter.getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                if(adapter!=null)// && adapter.getItemCount()>0
                {
                    adapter.getFilter().filter(query);
                }
                return false;
            }
        });

        *//*item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(item);
                //Toast.makeText(getActivity(), "click on menu", Toast.LENGTH_SHORT).show();
            }
        });*//*


        final MenuItem itemNewCustomer = menu.findItem(R.id.action_notification);
        itemNewCustomer.setVisible(true);
        *//*itemNewCustomer.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(itemNewCustomer);
                //Toast.makeText(getActivity(), "click on menu", Toast.LENGTH_SHORT).show();
            }
        });*//*

//        MenuItem searchMenu = menu.findItem(R.id.action_edit);
//        searchMenu.setVisible(true);
        //MenuItem delete = menu.findItem(R.id.action_delete);
//        if(showDeleteMenu)
//        {
//            delete.setVisible(true);
//        }
//        else
//        {
//            delete.setVisible(false);
//        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_search:
                //mDataBinding.btnUpdate.setVisibility(View.VISIBLE);

//                showAlertDialog(context,"Confirmation","Are you sure you want to logout?");
                //showDailogLogout("Confirmation","Are you sure you want to logout?");
                //Toast.makeText(context,"action_search",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_new_customer:
                //mDataBinding.btnUpdate.setVisibility(View.VISIBLE);

//                showAlertDialog(context,"Confirmation","Are you sure you want to logout?");
                //showDailogLogout("Confirmation","Are you sure you want to logout?");

                //Toast.makeText(context,"New Customer",Toast.LENGTH_SHORT).show();


                //addFragmentWithoutRemove(R.id.container_main,new AddCustomerFragment(),AddCustomerFragment.class.getSimpleName());


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

}
