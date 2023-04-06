package com.woleapp.ui.fragments;

import static com.woleapp.util.Constants.USER_LEVEL;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.pixplicity.easyprefs.library.Prefs;
import com.woleapp.R;
import com.woleapp.adapters.LocalGovernmentAdapter;
import com.woleapp.adapters.RemittanceAdapter;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.Service;
import com.woleapp.ui.activity.MainActivity;
import com.woleapp.ui.widgets.GridRecyclerView;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class LocalGovernmentFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    LocalGovernmentAdapter adapter;
    List<Service> serviceList;

    AgencyUser user;

    GridRecyclerView gridRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();

        user = SharedPrefManager.getAgencyUser();
        if (user == null) {
            Prefs.clear();

            Intent intent = new Intent(this.getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.getActivity().finish();
            return;
        }
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridRecyclerView = view.findViewById(R.id.remittanceItems);
        populateCards();
    }
    public void populateCards() {
        serviceList = new ArrayList<>();
        Service service;
        service = new Service(1, "BUSINESS PERMITS", R.drawable.ic_user_merchant);
        serviceList.add(service);
        service = new Service(2, "PROPERTY TAX", R.drawable.ic_user_merchant);
        serviceList.add(service);

        gridRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        gridRecyclerView.setLayoutManager(gridLayoutManager);

        adapter = new LocalGovernmentAdapter(getActivity(), serviceList, onClick);
        gridRecyclerView.setAdapter(adapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remittance, container, false);
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
    @Override
    public void onClick(View view) {

    }
    private OnItemClickListener.OnItemClickCallback onClick = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {

            if (user.getUser_level() == USER_LEVEL) {
                if (position == 0) {
                    //addFragmentWithoutRemove(R.id.container_main, new WesternUnionFragment(), WesternUnionFragment.class.getSimpleName());
                    Toast.makeText(requireContext(), "Business Permits", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(requireContext(), "Property Tax", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(requireContext(), "More", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };
}
