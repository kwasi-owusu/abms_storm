package com.woleapp.ui.fragments;

import static com.woleapp.util.Constants.USER_LEVEL;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.pixplicity.easyprefs.library.Prefs;
import com.woleapp.R;
import com.woleapp.adapters.RemittanceAdapter;
import com.woleapp.adapters.TicketsAdapter;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.Service;
import com.woleapp.ui.activity.MainActivity;
import com.woleapp.ui.widgets.GridRecyclerView;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class TicketsFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    TicketsAdapter adapter;
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
        //showProgressBar();
        populateCards();
    }
    public void populateCards() {
        serviceList = new ArrayList<>();
        Service service;
        service = new Service(1, "BUS TICKETS", R.drawable.bus_ticket);
        serviceList.add(service);
        service = new Service(2, "SPORTS EVENTS", R.drawable.sports);
        serviceList.add(service);
        service = new Service(3, "OTHER EVENTS", R.drawable.events);
        serviceList.add(service);

        setAnimation();

        gridRecyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        //gridLayoutManager.setSpanSizeLookup(customsSpanSizeLookUp);
        gridRecyclerView.setLayoutManager(gridLayoutManager);

        adapter = new TicketsAdapter(getActivity(), serviceList, onClick);
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

            /*Toast.makeText(context,"This feature shall be available soon",Toast.LENGTH_SHORT).show();*/

            if (user.getUser_level() == USER_LEVEL) {
                if (position == 0) {
                    Toast.makeText(requireContext(), "Bus Tickets", Toast.LENGTH_SHORT).show();
                }
                else if(position == 1){
                    Toast.makeText(requireContext(), "Sports Events", Toast.LENGTH_SHORT).show();
                }
                else if(position == 2){
                    Toast.makeText(requireContext(), "Other Events", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };
    public void setAnimation() {

        int resId = R.anim.grid_layout_animation_from_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        animation.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //binding.rvItems.setLayoutAnimation(animation);
    }

    // Define the timeout duration in milliseconds
    private static final int TIMEOUT_MS = 10000; // 10 seconds

    // Declare the ProgressDialog and Handler objects
    private ProgressDialog mProgressDialog;
    private Handler mHandler;

    // Method to show the progress bar
   /* public void showProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            return;
        mProgressDialog = ProgressDialog.show(context, null, null);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    addFragmentWithoutRemove(R.id.container_main, new PaymentFailedFragment(), PaymentFailedFragment.class.getSimpleName());

                }
            }
        }, TIMEOUT_MS);

    }*/

}
