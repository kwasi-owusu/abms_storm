package com.woleapp.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;

import com.google.gson.Gson;
import com.woleapp.R;
import com.woleapp.adapters.InventoryAdapter;
import com.woleapp.databinding.DialogBinding;
import com.woleapp.databinding.LayoutInventoryListBinding;
import com.woleapp.db.Injection;
import com.woleapp.network.MerchantsApiClient;
import com.woleapp.network.MerchantsApiService;
import com.woleapp.viewmodels.InventoryViewModel;
import com.woleapp.db.LocalCache;
import com.woleapp.db.SearchKey;
import com.woleapp.model.Inventory;
import com.woleapp.model.User;
import com.woleapp.util.*;

import org.parceler.Parcels;


public class InventoryListFragment extends BaseFragment implements View.OnClickListener, Constants {
    Context context;
    private LayoutInventoryListBinding binding;

    User user;
    Utilities utilities;
    private InventoryAdapter adapter;
    private InventoryViewModel mViewModel;
    private static final SearchKey searchKey = new SearchKey();
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        user = SharedPrefManager.getUser();
        searchKey.setId(user.getUser_id());
        searchKey.setQuery("%%");
        //((HomeActivity)getActivity()).setTitleWithNoNavigation("Dashboard");

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this, Injection.provideViewModelFactoryForInventoryOnline(context))//provideViewModelFactoryForInventory
                .get(InventoryViewModel.class);

        mViewModel.setMerchantApiService(MerchantsApiClient.getMerchantApiService(requireContext()));
        mViewModel.setLocalCache(Injection.provideCacheForInventory(requireContext()));
        mViewModel.message().observe(getViewLifecycleOwner(), new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> stringEvent) {
                String message = stringEvent.getContentIfNotHandled();
                if (message != null)
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
        setListeners();
        mViewModel.searchInventory(searchKey);
        initAdapter();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        //user = SharedPrefManager.getInstance(context).getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_inventory_list, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.clear();

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {


    }

    public void hideKeyBoard() {
        try {
            if (context != null) {
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

                // check if no view has focus:
                View v = ((Activity) context).getCurrentFocus();
                if (v == null) return;

                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveInventory(Inventory inventory) {
        LocalCache localCache = Injection.provideCacheForInventory(context);
        localCache.insertInventoryInfo(inventory, new LocalCache.InsertCallback() {
            @Override
            public void insertFinished(Long result) {
                if (result > 0) {
                    Log.e("Inventory_Saved", result + "--");
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Inventory saved successfully", Toast.LENGTH_LONG).show();
                        }
                    });
                    getActivity().getSupportFragmentManager().popBackStack();

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "Failed to update your information. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    private void showEmptyMessage(boolean show) {
        if (show) {
            //binding.rvInventories.setVisibility(View.GONE);
            binding.tvNoResults.setVisibility(View.VISIBLE);
        } else {
            //mDataBinding.recyclerview.setVisibility(View.VISIBLE);
            binding.tvNoResults.setVisibility(View.GONE);
        }
    }


    private void onSearch(String queryEntered, boolean forceRefresh) {
        //String queryEntered = mDataBinding.searchRepo.getText().toString().trim();
        if (!TextUtils.isEmpty(queryEntered) || forceRefresh) {
            binding.rvInventories.scrollToPosition(0);
            //Posts the query to be searched
            searchKey.setQuery(queryEntered);
            mViewModel.searchInventory(searchKey);
            //Resets the old list
            adapter.submitList(null);
        }
    }

    public void setListeners() {
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //run query to the server
                    String query = binding.etSearch.getText().toString().trim();
                    Log.e("onClick: ", "-- " + query);
                    onSearch(query, true);
                }
                return false;
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() % 3 == 0 && s.length() <= 12) {
                    String query = s.toString();
                    Log.e("onClick: ", "-- " + query);
                    onSearch(query, true);
                }

            }
        });
    }

    private void initAdapter() {
        adapter = new InventoryAdapter();
        adapter.setClickListeners(onClick/*,onClickAttachment*/);
        //setAnimation();
        binding.rvInventories.setAdapter(adapter);

        showProgressBar();
        //Subscribing to receive the new PagedList Repos

        mViewModel.getInventories().observe(getViewLifecycleOwner(), repos -> {
            Log.e("onChanged", "onChanged");
            //binding.setIsLoading(false);
            searchKey.setRefresh(false);
            if (repos != null) {
                if (repos.size() > 0) {
                    Inventory inventory = repos.get(0);
                    //Log.e("inventory_json", new Gson().toJson(inventory));
                }
                Log.e("initAdapter: Size: ", repos.size() + "--");
                showEmptyMessage(repos.size() == 0);
                adapter.submitList(repos);
            } else {
                showEmptyMessage(true);
            }
        });
        mViewModel.refreshInventoryList(SharedPrefManager.getUser().getNetplus_id());

        //Subscribing to receive the recent Network Errors if any
        /*mViewModel.getNetworkErrors().observe(this, errorMsg ->
        {
            dismissProgressBar();
            Toast.makeText(getActivity(), "\uD83D\uDE28 Wooops " + errorMsg, Toast.LENGTH_LONG).show();
        });*/

        mViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                //Log.e("OnChanged");
                if (aBoolean) {
                    showProgressBar();
                } else {
                    dismissProgressBar();
                }
            }
        });

    }

    public void showProgressBar() {
        Log.e("showProgressBar", "showProgressBar");
        if (progressDialog != null && progressDialog.isShowing()) return;
        progressDialog = ProgressDialog.show(context, null, null);
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
    }

    public void dismissProgressBar() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    OnItemClickListener.OnItemClickCallback onClick = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {

            Log.e("ClickedPos: ", position + " ##");
            Inventory inventory = adapter.getCurrentList().get(position);
            String name = inventory.getProduct_name();
            Integer productId = inventory.getInventory_id();

            Log.e("Inventory: ", name + " ##");
            switch (view.getId()) {
                case R.id.btnSell:
                    if (ConnectivityReceiver.isConnected()) {
                        StormApplication.getInstance().setSelectedInventory(inventory);
                        QuickTransactionFragment frag = new QuickTransactionFragment();
                        Bundle b = new Bundle();
                        b.putParcelable("inventory", Parcels.wrap(inventory));
                        frag.setArguments(b);
                        addFragmentWithoutRemove(R.id.container_main, frag, QuickTransactionFragment.class.getSimpleName());
                    } else {
                        utilities.showDialogNoNetwork("You need an active internet connection to proceed. Would you like to enable it ?", getActivity());
                    }

                    break;
                case R.id.btn_edit:
                    AddInventoryFragment fragment = AddInventoryFragment.NewInstance(inventory);
                    addFragmentWithoutRemove(R.id.container_main, fragment, AddInventoryFragment.class.getSimpleName());
                    break;
                case R.id.btn_delete:
                    Toast.makeText(context, "Delete item from inventory", Toast.LENGTH_SHORT).show();
                    DialogBinding dialogBinding = DialogBinding.inflate(getLayoutInflater(), null, false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setView(dialogBinding.getRoot());
                    dialogBinding.tvTitle.setText("Delete");
                    dialogBinding.tvMessage.setText(R.string.delete_inventory_confirmation);
                    AlertDialog dialog = builder.create();
                    dialogBinding.tvNo.setOnClickListener((v) -> dialog.dismiss());
                    dialogBinding.tvYes.setOnClickListener((v) -> {
                        String merchantId = SharedPrefManager.getUser().getNetplus_id();
                        mViewModel.deleteInventory(merchantId, inventory);
                        dialog.dismiss();
                    });
                    dialog.show();
                    break;
            }
        }
    };

    OnItemClickListener.OnItemClickCallback onClickAttachment = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            Log.e("onClickAttachment ", position + " ##");
            /*if(mWordsAdapter!=null)//mWordsAdapter.getItemCount()>position
            {
                ArrayList<String> list = mWordsAdapter.getMessageAttachments(position);
                if(list!=null && list.size()>0)
                {
                    launchImageViewer(list);
                }
                else
                {
                    Log.e("AttachmentList","Null");
                }
            }
            else
            {
                Log.e("MessageAdapter","Null");
            }*/

        }
    };

    public void clickOnSell(int position) {

    }
}
