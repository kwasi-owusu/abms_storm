package com.woleapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.woleapp.R;
import com.woleapp.adapters.AgencyTransactionAdapter;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.Service;

import com.woleapp.ui.activity.MainActivity;
import com.woleapp.ui.widgets.GridRecyclerView;
import com.woleapp.util.Constants;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

//Open this fragment when transaction card is selected
public class AgencyTransactionFragment extends BaseFragment implements View.OnClickListener {

    Context context;
    AgencyTransactionAdapter adapter;
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
        gridRecyclerView = view.findViewById(R.id.agencyTransactionItems);
        populateCards();
    }

    public void populateCards() {
        serviceList = new ArrayList<>();
        Service service;
        service = new Service(1, "WEEKLY", R.drawable.trans);
        serviceList.add(service);
        service = new Service(2, "MONTHLY", R.drawable.trans);
        serviceList.add(service);
        service = new Service(3, "YEARLY", R.drawable.trans);
        serviceList.add(service);
        service = new Service(4, "ALL TIME", R.drawable.trans);
        serviceList.add(service);

        gridRecyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        //gridLayoutManager.setSpanSizeLookup(customsSpanSizeLookUp);
        gridRecyclerView.setLayoutManager(gridLayoutManager);

        adapter = new AgencyTransactionAdapter(getActivity(), serviceList, onClick);
        gridRecyclerView.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agency_transaction, container, false);
    }

    @Override
    public void onClick(View view) {

    }

    private OnItemClickListener.OnItemClickCallback onClick = (view, position) -> {
        String num1 = "1";
        String num2 = "2";
        String num3 = "3";
        String num4 = "4";
        if (position == 0) {
            Bundle bundle = new Bundle();
            bundle.putString("data", num1);
            AgencyTransactionDetailFragment agdf = new AgencyTransactionDetailFragment();
            agdf.setArguments(bundle);
            replaceFragmentWithBack(R.id.container_main, agdf, AgencyTransactionDetailFragment.class.getSimpleName());
        } else if (position == 1) {
            Bundle bundle = new Bundle();
            bundle.putString("data", num2);
            AgencyTransactionDetailFragment agdf = new AgencyTransactionDetailFragment();
            agdf.setArguments(bundle);
            replaceFragmentWithBack(R.id.container_main, agdf, AgencyTransactionDetailFragment.class.getSimpleName());
        } else if (position == 2) {
            Bundle bundle = new Bundle();
            bundle.putString("data", num3);
            AgencyTransactionDetailFragment agdf = new AgencyTransactionDetailFragment();
            agdf.setArguments(bundle);
            replaceFragmentWithBack(R.id.container_main, agdf, AgencyTransactionDetailFragment.class.getSimpleName());
        } else if (position == 3) {
            Bundle bundle = new Bundle();
            bundle.putString("data", num4);
            AgencyTransactionDetailFragment agdf = new AgencyTransactionDetailFragment();
            agdf.setArguments(bundle);
            replaceFragmentWithBack(R.id.container_main, agdf, AgencyTransactionDetailFragment.class.getSimpleName());
        } else {
            Toast.makeText(requireContext(), "More", Toast.LENGTH_SHORT).show();
        }
    };
}