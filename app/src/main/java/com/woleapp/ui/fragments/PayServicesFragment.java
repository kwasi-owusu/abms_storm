package com.woleapp.ui.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.woleapp.R;
import com.woleapp.adapters.DashboardAdapter;
import com.woleapp.databinding.LayoutDashboardBinding;
import com.woleapp.model.Service;
import com.woleapp.model.User;
import com.woleapp.network.soap.request.*;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.util.OnItemClickListener;
import com.woleapp.util.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;


public class PayServicesFragment extends BaseFragment implements View.OnClickListener {
    Context context;
    private LayoutDashboardBinding binding;
    DashboardAdapter adapter;
    List<Service> serviceList;
    User user;

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", PayServicesFragment.class.getSimpleName() + "--");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause", PayServicesFragment.class.getSimpleName() + "--");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        /*Utilities utilities = new Utilities(context);
        utilities.getFormatedAmountString()*/
        String strDouble = String.format("%.2f", 1.9999);

       // user = SharedPrefManager.getUser();
        HomeActivity activity = (HomeActivity) getActivity();
//        if (user.getUser_type() == 2) {
//            activity.setMerchantToolbar();
//            String email = user.getEmail();
//            String name = email.substring(0, email.indexOf('@'));
//            activity.setUserName(name);
//        } else {
//            activity.setUserName(user.getName());
//            activity.setAvailableBalance(user.getAvailableBalance());
//        }
        ((HomeActivity) getActivity()).setTitleWithNoNavigation("Dashboard");

//        transferFunds();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populate();
        //transferFunds();
    }

    public void populate() {
        serviceList = new ArrayList<>();

        Service service = new Service(1, "PAY CABLE TV", R.drawable.tv, "DSTV,GOTV,TRend,MyTV & Many More");
        serviceList.add(service);
        service = new Service(2, "PAY UTILITIES", R.drawable.pay_utilities, "EKO Electricity, Water Bills & Many More");
        serviceList.add(service);
        service = new Service(3, "POST PAID & AIRTIME", R.drawable.vtu, "MTN, Airtel,Glo, 9Mobile");
        serviceList.add(service);
        service = new Service(4, "INTERNET SERVICES", R.drawable.vtu, "Smile, Swift & Many More");
        serviceList.add(service);
        service = new Service(5, "E-Bills", R.drawable.bill, "Nigerian Breweries....");
        serviceList.add(service);
        /*service = new Service(4,"VIEW TRANSACTIONS",R.drawable.trans);
        serviceList.add(service);

        service = new Service(4,"PAY BILLS",R.drawable.bill);
        serviceList.add(service);*/

        setAnimation();
        binding.rvItems.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        //gridLayoutManager.setSpanSizeLookup(customsSpanSizeLookUp);
        binding.rvItems.setLayoutManager(gridLayoutManager);

        adapter = new DashboardAdapter(getActivity(), serviceList, onClick, true);
        binding.rvItems.setAdapter(adapter);
    }

    public void setAnimation() {
//        AnimationSet set = new AnimationSet(true);
//
//        Animation animation = new AlphaAnimation(0.0f, 1.0f);
//        animation.setDuration(500);
//        set.addAnimation(animation);
//
//        animation = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f
//        );
//        animation.setDuration(100);
//        set.addAnimation(animation);
//
//        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
//        dashboardBinding.rvItems.setLayoutAnimation(controller);

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
        binding.rvItems.setLayoutAnimation(animation);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        //user = SharedPrefManager.getInstance(context).getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_dashboard, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.clear();

//        menu.findItem(R.id.action_edit).setVisible(true);
//        menu.findItem(R.id.action_search).setVisible(false);
        //menu.removeItem(R.id.action_search);//clear();

        /*if(showDeleteMenu)
        {
            menu.findItem(R.id.action_search).setVisible(false);
            menu.removeItem(R.id.action_search);//clear();
        }
        else
        {
            menu.clear();

//            menu.removeItem(R.id.action_search);//clear();
//            menu.removeItem(R.id.action_search);//clear();
//
//            menu.findItem(R.id.action_search).setVisible(false);
//
//            menu.findItem(R.id.action_search).setVisible(false);
        }*/

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_fragment, menu);
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

            /*case R.id.action_edit:
                mDataBinding.btnUpdate.setVisibility(View.VISIBLE);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

    }

    private final OnItemClickListener.OnItemClickCallback onClick = (view, position) -> {
        if (position <= 3)
            addFragmentWithoutRemove(R.id.container_main, UtilitiesPaymentFragment.newInstance(position), UtilitiesPaymentFragment.class.getSimpleName());
        else
            addFragmentWithoutRemove(R.id.container_main, new EBillsListFragment(), EBillsListFragment.class.getName());

    };

//    private final OnItemClickListener.OnItemClickCallback onClick =
//            (view, position) -> {
//
//            });


    public FundsTransferRequestModel getRequestModel() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setPin("kghxqwveJ3eSQJip/cmaMQ==");
        transactionModel.setBankCode("063");
        transactionModel.setAmount(5);
        transactionModel.setDescription("Payment Fund Transfer");
        transactionModel.setDestination("2001220212");
        transactionModel.setReference("etz1234eddfg115634");
        transactionModel.setSenderName("Ajay:2063449787:Vijay");
        transactionModel.setEndPoint("A");

        FundsTransferRequestModel fundsTransferRequestModel = new FundsTransferRequestModel();
        fundsTransferRequestModel.setDirection("request");
        fundsTransferRequestModel.setAction("FT");
        fundsTransferRequestModel.setTerminalId("7000000001");
        fundsTransferRequestModel.setTransaction(transactionModel);

        return fundsTransferRequestModel;
    }

    public void transferFunds() {
        RequestEnvelope requestEnvelope = new RequestEnvelope();
        RequestBody requestBody = new RequestBody();
        ProcessModel processModel = new ProcessModel();
        processModel.fundsTransferRequestModel = getRequestModel();
        requestBody.processModel = processModel;
        /*fundsTransferRequestModel.theCityName = binding.etCityName.getText().toString();
        fundsTransferRequestModel.cityNameAttribute = "http://WebXml.com.cn/";
        requestBody.getWeatherbyCityName = fundsTransferRequestModel;*/
        requestEnvelope.body = requestBody;

        /*RequestModel_Ref fundsTransferRequestModel = new RequestModel_Ref("CodeData",
                "InfoData",
                "SNumData",
                "yhnmData",
                "ipdzData");
        RequestBody_Ref requestBody = new RequestBody_Ref(fundsTransferRequestModel);
        RequestEnvelope_Ref requestEnvelope = new RequestEnvelope_Ref(requestBody);*/

        //APIServiceClient service = APIServiceClient.create(context);

//        APIServiceClient.transferFunds(requestEnvelope, new APIServiceClient.ApiCallback2()
//        {
//            @Override
//            public void onStart() {
//                Log.e("onStart","onStart");
//            }
//
//            @Override
//            public void onEnd() {
//                Log.e("onEnd","onEnd");
//            }
//
//
//
//            @Override
//            public void onResponse(Object response)
//            {
//                Log.e("onResponse",response.toString()+"###");
//            }
//
//            @Override
//            public void onFail(String msg) {
//                Log.e("onFail",msg+"###");
//            }
//        },context);
    }
}
