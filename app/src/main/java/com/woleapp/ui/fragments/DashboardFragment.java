package com.woleapp.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.gms.common.util.Strings;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;
import com.woleapp.R;
import com.woleapp.adapters.DashboardAdapter;
import com.woleapp.databinding.LayoutDashboardBinding;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.Service;

import com.woleapp.network.StormAPIClient;
import com.woleapp.network.soap.request.*;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.ui.activity.MainActivity;
import com.woleapp.util.*;

import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

import static android.content.Context.WIFI_SERVICE;
import static com.woleapp.util.Constants.USER_LEVEL;


public class DashboardFragment extends BaseFragment implements View.OnClickListener {
    private static final String tag = "DashboardFragment";
    Context context;
    private LayoutDashboardBinding binding;
    DashboardAdapter adapter;
    List<Service> serviceList;
//    User user;
    AgencyUser user;
    ProgressDialog mProgressDialog;

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", DashboardFragment.class.getSimpleName() + "--");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause", DashboardFragment.class.getSimpleName() + "--");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Integer a1=null;
        String s1 = a1.toString();*/

        setHasOptionsMenu(true);
        context = getActivity();
        try {
//            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        } catch (Exception e) {
            //Crashlytics.log(e.toString());
        }

//        user = SharedPrefManager.getUser();
        user = SharedPrefManager.getAgencyUser();
        if (user == null) {
            Prefs.clear();

            Intent intent = new Intent(this.getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.getActivity().finish();
            return;
        }
//        getSettings();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populate();
//        if (user.getUser_level() != USER_LEVEL)
//            populate();
//        else showUserTypeSelectionDialog();

//        Prefs.putString("current_user", user.getAgentKey());
        Log.e("APP TOKEN WITH PREFS", Prefs.getString("current_user", "token haha"));
        String strDouble = String.format("%.2f", 1.9999);

        String ip = getWifiIPAddress();
        Log.e("IP", ip + "---");
        HomeActivity activity = (HomeActivity) getActivity();
        //Toast.makeText(context,user.getUser_id()+"",Toast.LENGTH_SHORT).show();
        /*if (user.getUser_type() == USER_TYPE_MERCHANT) {
            activity.setMerchantToolbar();
            String email = user.getEmail();
            String name = email.substring(0, email.indexOf('@'));
            activity.setUserName(name);
        } else {
            String email = user.getEmail();
            String name = email.substring(0, email.indexOf('@'));
            activity.setUserName(user.getName());
            activity.setUserName(name);
            activity.setAvailableBalance(user.getAvailableBalance());
        }*/
        String name = user.getFirstName();
//        String name = email.substring(0, email.indexOf('@'));
        /*activity.setUserName(user.getName());*/
        activity.setUserName("Test");
        activity.setAvailableBalance(String.valueOf(user.getUserBranch()));
        ((HomeActivity) getActivity()).setTitleWithNoNavigation("Dashboard");
    }

//    public void showUserTypeSelectionDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        LayoutSelectUserTypeBinding userTypeBinding = LayoutSelectUserTypeBinding.inflate(getLayoutInflater(), null, false);
//        userTypeBinding.setLifecycleOwner(getViewLifecycleOwner());
//        userTypeBinding.executePendingBindings();
//        builder.setView(userTypeBinding.getRoot())
//                .setCancelable(false);
//        AlertDialog dialog = builder.create();
//        if (user.getUser_type() == USER_TYPE_NONE)
//            userTypeBinding.buttonCancel.setVisibility(View.GONE);
//
//        View.OnClickListener clickListener = v -> {
//            int currentUserType = user.getUser_type();
//            if (v == userTypeBinding.userMerchant)
//                user.setUser_type(USER_TYPE_MERCHANT);
//            else user.setUser_type(USER_TYPE_AGENT);
//            if (currentUserType == user.getUser_type()) {
//                Toast.makeText(requireContext(), "Can't select this again", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (dialog.isShowing())
//                dialog.cancel();
//            user.setAvailableBalance(getActivityCast().getGetAvailableBalance());
//            user.setLedgerBalance(getActivityCast().getGetLedgerBalance());
//            SharedPrefManager.setUser(user);
//            SharedPrefManager.setUserType(user.getUser_type());
//            populate();
//        };
//        userTypeBinding.buttonCancel.setOnClickListener(v -> {
//            if (user.getUser_type() == USER_TYP E_NONE)
//                Toast.makeText(context, "Please select one to continue", Toast.LENGTH_SHORT).show();
//            else
//                dialog.cancel();
//        });
//        userTypeBinding.userAgent.setOnClickListener(clickListener);
//        userTypeBinding.userMerchant.setOnClickListener(clickListener);
//        dialog.show();
//    }

    private HomeActivity getActivityCast() {
        return (HomeActivity) requireActivity();
    }

    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }

    public void populate() {
        serviceList = new ArrayList<>();
        Service service;
        service = new Service(1, "CASH IN", R.drawable.cash_in_new);
        serviceList.add(service);
        service = new Service(2, "CASH-OUT", R.drawable.cash_out_new);
        serviceList.add(service);
       // service = new Service(3, "FUND WALLET", R.drawable.fund_wallet_new);
       // serviceList.add(service);
        service = new Service(3, "VIEW TRANSACTIONS", R.drawable.trans);
        serviceList.add(service);
       // service = new Service(5, "PAY BILLS", R.drawable.bill);
       // serviceList.add(service);
       // service = new Service(6, "HEALTH CHECKER", R.drawable.ic_stethoscope);
        // serviceList.add(service);
        service = new Service(4, "COLLECTIONS", R.drawable.cash_in);
        serviceList.add(service);
        service = new Service(5, "REMITTANCES", R.drawable.remittance);
        serviceList.add(service);
        service = new Service(6, "INSURANCE", R.drawable.insurance);
        serviceList.add(service);
        service = new Service(7, "TICKETS", R.drawable.ticketss);
        serviceList.add(service);
        service = new Service(8, "SSNIT", R.drawable.ssnit);
        serviceList.add(service);
        service = new Service(9, "LOCAL GOVERNMENT", R.drawable.localgov);
        serviceList.add(service);
        service = new Service(10, "FINES & BAILS", R.drawable.fines);
        serviceList.add(service);
        service = new Service(11, "SCHOOL FEES", R.drawable.tuition);
        serviceList.add(service);
        service = new Service(12, "DONATIONS", R.drawable.donate);
        serviceList.add(service);
//        if (user.getUser_level() == USER_LEVEL) {
//            service = new Service(1, "CASH-IN / BANK TRANSFER", R.drawable.cash_in_new);
//            serviceList.add(service);
//            service = new Service(2, "CASH-OUT", R.drawable.cash_out_new);
//            serviceList.add(service);
//            service = new Service(3, "FUND WALLET", R.drawable.fund_wallet_new);
//            serviceList.add(service);
//            service = new Service(4, "VIEW TRANSACTIONS", R.drawable.trans);
//            serviceList.add(service);
//
//            service = new Service(5, "PAY BILLS", R.drawable.bill);
//            serviceList.add(service);
//            service = new Service(6, "HEALTH CHECKER", R.drawable.ic_stethoscope);
//            serviceList.add(service);
//        } else {
//            service = new Service(1, "MY STORE", R.drawable.ic_user_merchant);
//            serviceList.add(service);
//            service = new Service(2, "MARKETPLACE", R.drawable.ic_online_shopping);
//            serviceList.add(service);
//            service = new Service(3, "ORDERS", R.drawable.ic_order);
//            serviceList.add(service);
//            service = new Service(4, "VIEW TRANSACTIONS", R.drawable.trans);
//            serviceList.add(service);
//            service = new Service(5, "CASH-IN / BANK TRANSFER", R.drawable.cash_in_new);
//            serviceList.add(service);
//            service = new Service(5, "PAY BILLS", R.drawable.bill);
//            serviceList.add(service);
//        }
        //setAnimation();
        binding.rvItems.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        //gridLayoutManager.setSpanSizeLookup(customsSpanSizeLookUp);
        binding.rvItems.setLayoutManager(gridLayoutManager);

        adapter = new DashboardAdapter(getActivity(), serviceList, onClick, false);
        binding.rvItems.setAdapter(adapter);
    }

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
        binding.rvItems.setLayoutAnimation(animation);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        //user = SharedPrefManager.getUser();
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
    private OnItemClickListener.OnItemClickCallback onClick = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {

            /*Toast.makeText(context,"This feature shall be available soon",Toast.LENGTH_SHORT).show();*/

            if (2 == USER_LEVEL) {
                if (position == 0) {
                    Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
//                   Bundle b1 = new Bundle();
//                    b1.putInt("transaction_type", Constants.TRANSACTION_CASH_IN);
//                    CashInOptionsFragment cin = new CashInOptionsFragment();
//                    //CashInFragment cin = new CashInFragment();
//                    cin.setArguments(b1);
//                    addFragmentWithoutRemove(R.id.container_main, cin, CashInOptionsFragment.class.getSimpleName());
                    //addFragmentWithoutRemove(R.id.container_main, new CashInOptionsNewFragment(), CashInOptionsNewFragment.class.getSimpleName());
                } else if (position == 1) {
                    Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
//                    Bundle b1 = new Bundle();
//                    b1.putInt("transaction_type", Constants.TRANSACTION_CASH_OUT);
//                    FundWalletFragment cin = new FundWalletFragment();
//                    cin.setArguments(b1);
                    //addFragmentWithoutRemove(R.id.container_main,cin,CashInFragment.class.getSimpleName());
                  //  addFragmentWithoutRemove(R.id.container_main, new CashoutFragment(), CashoutFragment.class.getSimpleName());
                } /*else if (position == 2) {

                    addFragmentWithoutRemove(R.id.container_main, new FundWalletFragment(), FundWalletFragment.class.getSimpleName());

                } else if (position == 4) {

                    addFragmentWithoutRemove(R.id.container_main, new PayServicesFragment(), PayServicesFragment.class.getSimpleName());

                }*/ else if (position == 2) {
                    Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
//                    Bundle b1 = new Bundle();
                    //b1.putInt("transaction_type", Constants.TRANSACTION_CASH_IN);
//                    TransactionsFragment cin = new TransactionsFragment();
                    //CashInFragment cin = new CashInFragment();
//                    cin.setArguments(b1);
//                    addFragmentWithoutRemove(R.id.container_main, cin, TransactionsFragment.class.getSimpleName());
                ///    addFragmentWithoutRemove(R.id.container_main, new AgencyTransactionFragment(), AgencyTransactionFragment.class.getSimpleName());

                    //Toast.makeText(context,"This feature shall be available soon",Toast.LENGTH_SHORT).show();
                } //else if (position == 5) {
                    //addFragmentWithoutRemove(R.id.container_main, new HealthCheckerFragment(), HealthCheckerFragment.class.getSimpleName());
               // }
                else if(position == 3){
                    addFragmentWithoutRemove(R.id.container_main, new CollectionsNewFragment(), CollectionsNewFragment.class.getSimpleName());
                }
                else if(position == 4){
                    Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                  //  addFragmentWithoutRemove(R.id.container_main, new RemittanceFragment(), RemittanceFragment.class.getSimpleName());
                }
                else if(position == 5){
                    Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                  //  addFragmentWithoutRemove(R.id.container_main, new InsuranceFragment(), InsuranceFragment.class.getSimpleName());
                }
                else if(position == 6){
                    Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                    //addFragmentWithoutRemove(R.id.container_main, new CameraFragment(), CameraFragment.class.getSimpleName());
                   // addFragmentWithoutRemove(R.id.container_main, new TicketsFragment(), TicketsFragment.class.getSimpleName());
                   // addFragmentWithoutRemove(R.id.container_main, new PassportFragment(), PassportFragment.class.getSimpleName());
                }
                else if(position == 7){
                    Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                   // addFragmentWithoutRemove(R.id.container_main, new SSNITFragment(), SSNITFragment.class.getSimpleName());
                }
                else if(position == 8){
                    Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                   // addFragmentWithoutRemove(R.id.container_main, new LocalGovernmentFragment(), LocalGovernmentFragment.class.getSimpleName());
                }
                else if(position == 9){
                    Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                    // addFragmentWithoutRemove(R.id.container_main, new FinesBailsFragment(), FinesBailsFragment.class.getSimpleName());
                }
                else if(position == 10){
                    Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                    // addFragmentWithoutRemove(R.id.container_main, new SchoolFeesFragment(), SchoolFeesFragment.class.getSimpleName());
                }
                else if(position == 11){
                   // Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                       addFragmentWithoutRemove(R.id.container_main, new FuneralDonationsFragment(), FuneralDonationsFragment.class.getSimpleName());
                }
                 else {
                    Toast.makeText(requireContext(), "More", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (position == 0) {
                    addFragmentWithoutRemove(R.id.container_main, MarketplaceStoreDashboard.newInstance(), MarketplaceHomeFragment.class.getSimpleName());
                    //login();
                } else if (position == 1) {
                    addFragmentWithoutRemove(R.id.container_main, new MarketplaceHomeFragment(), MarketplaceHomeFragment.class.getSimpleName());
                } else if (position == 2) {
                    addFragmentWithoutRemove(R.id.container_main, MarketplaceOrdersFragment.newInstance(2), QuickTransactionFragment.class.getSimpleName());
                    //Toast.makeText(context, "This feature will be available soon", Toast.LENGTH_LONG).show();
                } else if (position == 3) {
                    Bundle b1 = new Bundle();
                    TransactionsFragment cin = new TransactionsFragment();
                    cin.setArguments(b1);
                    addFragmentWithoutRemove(R.id.container_main, new MerchantTransactionFragment(), MerchantTransactionFragment.class.getSimpleName());
                } else if (position == 4) {
                    Bundle b1 = new Bundle();
                    b1.putInt("transaction_type", Constants.TRANSACTION_CASH_IN);
                    CashInOptionsFragment cin = new CashInOptionsFragment();
                    //CashInFragment cin = new CashInFragment();
                    cin.setArguments(b1);
                    addFragmentWithoutRemove(R.id.container_main, cin, CashInOptionsFragment.class.getSimpleName());
                } else {
                    addFragmentWithoutRemove(R.id.container_main, new PayServicesFragment(), PayServicesFragment.class.getSimpleName());
                }
            }

        }
    };


    public FundsTransferRequestModel getRequestModelForFundingWallet() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setPin("kghxqwveJ3eSQJip/cmaMQ==");
        transactionModel.setBankCode("033");//063
        transactionModel.setAmount(5);
        transactionModel.setDescription("Fund Wallet");
        //transactionModel.setDestination(Constants.ACCOUNT_NUMBER_DESTINATION);//2063449787
        transactionModel.setDestination(Constants.ACCOUNT_NUMBER_SOURCE);//2063449787
        transactionModel.setReference(generateUUID());
        //transactionModel.setSenderName("Ajay:2063449787:Vijay");
        transactionModel.setEndPoint("M");
        transactionModel.setCurrency("NGN");
        FundsTransferRequestModel fundsTransferRequestModel = new FundsTransferRequestModel();
        fundsTransferRequestModel.setDirection("request");
        fundsTransferRequestModel.setAction("FT");
        fundsTransferRequestModel.setTerminalId("7000000001");
        fundsTransferRequestModel.setTransaction(transactionModel);

        return fundsTransferRequestModel;
    }

    public FundsTransferRequestModel getRequestModelForTransactionStatus() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setPin("kghxqwveJ3eSQJip/cmaMQ==");
        transactionModel.setBankCode("033");//063
        //transactionModel.setAmount(5);
        transactionModel.setDescription("Transaction Status");
        //transactionModel.setDestination(Constants.ACCOUNT_NUMBER_DESTINATION);//2063449787
        transactionModel.setDestination(Constants.ACCOUNT_NUMBER_SOURCE);//2063449787
        transactionModel.setLineType("Others");
        transactionModel.setReference("ETZ691572687709360");//generateUUID()
        //transactionModel.setSenderName("Ajay:2063449787:Vijay");
        //transactionModel.setEndPoint("M");
        //transactionModel.setCurrency("NGN");
        FundsTransferRequestModel fundsTransferRequestModel = new FundsTransferRequestModel();
        fundsTransferRequestModel.setDirection("request");
        fundsTransferRequestModel.setAction("TS");
        fundsTransferRequestModel.setTerminalId("7000000001");
        fundsTransferRequestModel.setTransaction(transactionModel);

        return fundsTransferRequestModel;
    }

    public FundsTransferRequestModel getRequestModelForWalletQuery() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setPin("kghxqwveJ3eSQJip/cmaMQ==");
        transactionModel.setBankCode("033");//063
        transactionModel.setAmount(5);
        transactionModel.setDescription("Wallet Query");
        //transactionModel.setDestination(Constants.ACCOUNT_NUMBER_DESTINATION);//2063449787
        transactionModel.setDestination(Constants.ACCOUNT_NUMBER_SOURCE);//2063449787
        transactionModel.setLineType("Others");
        transactionModel.setReference(generateUUID());//"ETZ691572677986285"
        //transactionModel.setSenderName("Ajay:2063449787:Vijay");
        //transactionModel.setEndPoint("M");
        //transactionModel.setCurrency("NGN");
        FundsTransferRequestModel fundsTransferRequestModel = new FundsTransferRequestModel();
        fundsTransferRequestModel.setDirection("request");
        fundsTransferRequestModel.setAction("AQ");
        fundsTransferRequestModel.setTerminalId("7000000001");
        fundsTransferRequestModel.setTransaction(transactionModel);

        return fundsTransferRequestModel;
    }

    public FundsTransferRequestModel getRequestModel() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setPin("kghxqwveJ3eSQJip/cmaMQ==");
        transactionModel.setBankCode("033");//063
        transactionModel.setAmount(5);
        transactionModel.setDescription("Payment Fund Transfer");
        transactionModel.setDestination(Constants.ACCOUNT_NUMBER_DESTINATION);//2063449787
        transactionModel.setReference(generateUUID());
        transactionModel.setSenderName("Ram:" + Constants.ACCOUNT_NUMBER_SOURCE + ":Laxman");
        transactionModel.setEndPoint("A");
        transactionModel.setCurrency("NGN");
        FundsTransferRequestModel fundsTransferRequestModel = new FundsTransferRequestModel();
        fundsTransferRequestModel.setDirection("request");
        fundsTransferRequestModel.setAction("FT");
        fundsTransferRequestModel.setTerminalId("7000000001");
        fundsTransferRequestModel.setTransaction(transactionModel);

        return fundsTransferRequestModel;
    }

    public void encryptDecrypt() {
        try {
            String encryptedVal = AES.encrypt("0006", "KEd4gDNSDdMBxCGliZaC8w==");
            Log.e("EncryptedVal", encryptedVal + "--");
            String decryptedVal = AES.decrypt(encryptedVal, "KEd4gDNSDdMBxCGliZaC8w==");
            Log.e("DecryptedVal", decryptedVal + "--");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (KeyException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generateUUID() {

        Long tsLong = System.currentTimeMillis();///1000;//ETZ691572523694353//ETZ691572523588
        String transaction_ref_id = "ETZ" + user.getAgencyID() + tsLong.toString(); // This is your Transaction Ref id - Here we used as a timestamp -

        //String sOrderId= tsLong +"UPI";// This is your order id - Here we used as a timestamp -

        Log.e("TR Reference ID==>", "" + transaction_ref_id);

        //UUID uniqueKey = UUID.randomUUID();

        //String uniqueID = UUID.randomUUID().toString();

        /*String uniqueID = String.valueOf(UUID.randomUUID());//.getMostSignificantBits());
        Log.e("uniqueID",uniqueID+"--");*/
        return transaction_ref_id;
    }

    public void showProgressBar() {
        mProgressDialog = ProgressDialog.show(context, null, null);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ffffff")));
        mProgressDialog.setCancelable(false);

        /*if (mProgressDialog!=null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
        }*/
    }

    public void dismissProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void getSettings() {
        String token = SharedPrefManager.getAppToken();

        if (token == null || token.isEmpty() || JWTHelper.isExpired(token)) {
            Prefs.clear();
            Intent intent = new Intent(this.getContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.getActivity().finish();
            return;
        }
        Disposable subscribe = StormAPIClient
                .getFee(context, "posFees")
                .flatMap(res -> {
                    if (res.code() != 200) {
                        SharedPrefManager.setPOSConvenienceFee(Float.valueOf("0.75"));
                    } else {
                        JSONObject payload = new JSONObject(new Gson().toJson(res.body()));
                        String value = payload.optString("value");
                        if (Strings.emptyToNull(value) != null)
                            SharedPrefManager.setPOSConvenienceFee(Float.valueOf(payload.getString("value")));
                        else
                            SharedPrefManager.setPOSConvenienceFee(Float.valueOf("0.75"));

                    }
                    return StormAPIClient.getFee(context, "netplusPayTopUpFee");
                })
                .flatMap(res -> {
                    if (res.code() != 200) {
                        SharedPrefManager.setNetPlusPayConvenienceFee(Float.valueOf("1.5"));
                    } else {
                        JSONObject payload = new JSONObject(new Gson().toJson(res.body()));
                        String value = payload.optString("value");
                        if (Strings.emptyToNull(value) != null)
                            SharedPrefManager.setNetPlusPayConvenienceFee(Float.valueOf(value));
                        else
                            SharedPrefManager.setNetPlusPayConvenienceFee(Float.valueOf("1.5"));

                    }
                    return StormAPIClient.getFee(context, "transferFees");
                })
                .subscribe(res -> {
                    if (res.code() != 200) {
                        SharedPrefManager.setTransfeeConvenienceFee(Float.valueOf("40"));
                    } else {
                        JSONObject payload = new JSONObject(new Gson().toJson(res.body()));
                        String value = payload.optString("value");
                        if (Strings.emptyToNull(value) != null)
                            SharedPrefManager.setTransfeeConvenienceFee(Float.valueOf(value));
                        else
                            SharedPrefManager.setTransfeeConvenienceFee(Float.valueOf("40"));
                        SharedPrefManager.setTransfeeConvenienceFee(Float.valueOf("40"));
                    }
                }, t -> {
                    Log.e(getTag(), "An unexpected error occurred", t);
                    Toast.makeText(context, "An unexpected error occured", Toast.LENGTH_LONG).show();
                });
    }
}
