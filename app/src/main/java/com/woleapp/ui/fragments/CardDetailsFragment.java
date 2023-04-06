package com.woleapp.ui.fragments;

import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_ICC;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_MAG;
import static com.pos.sdk.emvcore.POIEmvCoreManager.DEV_PICC;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netpluspay.netpossdk.emv.CardReadResult;
import com.netpluspay.netpossdk.emv.CardReaderEvent;
import com.netpluspay.netpossdk.emv.CardReaderService;
import com.woleapp.R;
import com.woleapp.databinding.LayoutCardDetailsBinding;
import com.woleapp.db.Injection;
import com.woleapp.db.UserViewModel;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.CardData;
import com.woleapp.network.AgencyBankingAPIClient;
import com.woleapp.ui.activity.HomeActivity;
import com.woleapp.util.SharedPrefManager;
import com.woleapp.util.Utilities;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CardDetailsFragment extends BaseFragment implements View.OnClickListener{
    Context context;
    private LayoutCardDetailsBinding binding;
    AgencyUser user;
    String name;
    String idCard;
    String idNum;
    String amnt;
    String amount = "";
    private UserViewModel mViewModel;
    Utilities utilities;
    CardReadResult readResult;
    int transaction_type = 1;
    double amt_dbl = 0.0;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Gson gson = new Gson();
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Log.e("CashInOptionsFragment", "onReceive");
//            user = SharedPrefManager.getUser();
            user = SharedPrefManager.getAgencyUser();
        }
    };

    @Override
    public void onStart() {
        Log.e("onStart", "onStart");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myReceiver,
                new IntentFilter("UpdateUser"));
        super.onStart();
    }

    @Override
    public void onStop() {

        Log.e("onStop", "onStop");
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myReceiver);
        super.onStop();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        utilities = new Utilities(context);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        //user = SharedPrefManager.getUser();
        ((HomeActivity) getActivity()).setTitleWithNoNavigation("Dashboard");

        mViewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(getActivity()))
                .get(UserViewModel.class);


    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        //user = SharedPrefManager.getInstance(context).getUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_card_details, container, false);
        //((GPHMainActivity)getActivity()).createBackButton(job_title);
        return binding.getRoot();
    }

    private void readCard() {
//        alertDialog.show();
        List<Integer> preferredModes = new ArrayList<>();
        preferredModes.add(DEV_ICC);
        preferredModes.add(DEV_PICC);
        new CardReaderService(getActivity(), preferredModes, 45)
                .initiateICCCardPayment(200, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CardReaderEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CardReaderEvent cardReaderEvent) {
                        if (cardReaderEvent instanceof CardReaderEvent.CardDetected) {
                            // Card Detected
                            int mode = ((CardReaderEvent.CardDetected) cardReaderEvent).getMode();
                            String cardReadVia = "";
                            switch (mode) {
                                case DEV_ICC:
                                    cardReadVia = "Emv Chip";
                                    break;
                                case DEV_PICC:
                                    cardReadVia = "Contactless";
                                    break;
                                case DEV_MAG:
                                    cardReadVia = "Magnetic Stripe";
                                    break;
                            }
//                            alertDialog.dismiss();
                            Toast.makeText(context, cardReadVia + " Card detected", Toast.LENGTH_LONG).show();
                        } else if (cardReaderEvent instanceof CardReaderEvent.CardRead) {
                            // Card Read
//                            alertDialog.dismiss();
                            Toast.makeText(context, "Card Read", Toast.LENGTH_LONG).show();
                            readResult = ((CardReaderEvent.CardRead) cardReaderEvent).getData();
                            Timber.d("ENCRYPTED_PIN_BLOCK==>  " + readResult.getEncryptedPinBlock());
                            // Calling makePayment method

                            Timber.d("CARD_DATA READ==>%s", gson.toJson(readResult));
                            Timber.d("CARD_DATA A==>%s", gson.toJson(readResult.A));
                            Timber.d("CARD_DATA B==>%s", gson.toJson(readResult.B));
                            Timber.d("CARD_DATA C==>%s", gson.toJson(readResult.C));
                            Timber.d("CARD_DATA D==>%s", gson.toJson(readResult.D));
                            Timber.d("CARD_DATA E==>%s", gson.toJson(readResult.E));
                            Timber.d("CARD_DATA F==>%s", gson.toJson(readResult.F));
                            Timber.d("CARD_DATA G==>%s", gson.toJson(readResult.G));
                            Timber.d("CARD_DATA H==>%s", gson.toJson(readResult.H));
                            Timber.d("CARD_DATA I==>%s", gson.toJson(readResult.I));
                            Timber.d("CARD_DATA a==>%s", gson.toJson(readResult.a));
                            Timber.d("CARD_DATA b==>%s", gson.toJson(readResult.b));
                            Timber.d("CARD_DATA c==>%s", gson.toJson(readResult.c));
                            Timber.d("CARD_DATA d==>%s", gson.toJson(readResult.d));
                            Timber.e("CARD_DATA e==>%s", gson.toJson(readResult.e));
                            Timber.d("CARD_DATA f==>%s", gson.toJson(readResult.f));
                            Timber.d("CARD_DATA g==>%s", gson.toJson(readResult.g));
                            Timber.d("CARD_DATA h==>%s", gson.toJson(readResult.h));
                            Timber.d("CARD_DATA i==>%s", gson.toJson(readResult.i));
                            Timber.d("CARD_DATA j==>%s", gson.toJson(readResult.j));
                            Timber.d("CARD_DATA k==>%s", gson.toJson(readResult.k));
                            Timber.d("CARD_DATA l==>%s", gson.toJson(readResult.l));
                            Timber.d("CARD_DATA m==>%s", gson.toJson(readResult.m));
                            Timber.d("CARD_DATA n==>%s", gson.toJson(readResult.n));
                            Timber.d("CARD_DATA o==>%s", gson.toJson(readResult.o));
                            Timber.d("CARD_DATA p==>%s", gson.toJson(readResult.p));
                            Timber.d("CARD_DATA q==>%s", gson.toJson(readResult.q));
                            Timber.d("CARD_DATA r==>%s", gson.toJson(readResult.r));
                            Timber.d("CARD_DATA s==>%s", gson.toJson(readResult.s));
                            Timber.d("CARD_DATA t==>%s", gson.toJson(readResult.t));
                            Timber.d("CARD_DATA u==>%s", gson.toJson(readResult.u));
                            Timber.d("CARD_DATA v==>%s", gson.toJson(readResult.v));
                            Timber.d("CARD_DATA w==>%s", gson.toJson(readResult.w));
                            Timber.d("CARD_DATA x==>%s", gson.toJson(readResult.x));
                            Timber.d("CARD_DATA y==>%s", gson.toJson(readResult.y));
                            Timber.d("CARD_DATA z==>%s", gson.toJson(readResult.z));
                            // Timber.d("CARD_DATA I==>%s", gson.toJson(readResult.Z));

                            CardData cardData = new CardData();
                            cardData.setCardNumber(readResult.d);
                            // cardData.setExpiry(readResult.A);
                            cardData.setCvv(readResult.s);
                            cardData.setCardExpiryDate(readResult.A);

                            Log.e("cardDetails", cardData + " ");

                            SharedPrefManager.setCardData(cardData);

                             processCashoutTransactions(name, idCard, idNum, Double.parseDouble(amnt), readResult.d, readResult.A);


                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("CARD_DATA_ERROR==>%s", e.getLocalizedMessage());
                        Timber.d("CARD_DATA_ERROR_2==>%s", e.getMessage());
                        Timber.d("CARD_DATA_ERROR_3==>%s", e.toString());
                        Toast.makeText(context, "Error Reading Card", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Done Reading Card", Toast.LENGTH_LONG).show();
                    }

                });
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("CRASH", "crash");

        Bundle b1 = getArguments();
        name = b1.getString("name", "");
        idCard = b1.getString("idCard", "");
        idNum = b1.getString("idNum", "");
        amount = b1.getString("amount", "");
        amt_dbl = Double.parseDouble(amount);
        amnt = String.valueOf(amt_dbl);
        if (b1.containsKey("transaction_type")) {
            transaction_type = b1.getInt("transaction_type", 1);
        }
        binding.btnContinue.setOnClickListener(this);
    }
    public void onClick(View v){
        if(v == binding.btnContinue){

            readCard();
        }
    }
    private void processCashoutTransactions(String name, String idCard, String idNo, double amt, String account, String card_date_expiry) {
        //payload
        String officer_ID = "13";
        String branch_code = "1";
        String inst_ID = "1";
        String branch_key = SharedPrefManager.getBranchDetails().getBranchKey();
        String agency_key = SharedPrefManager.getAgencyDetails().getAgentKey();
        String trans_cat = "1";
        String trans_type = "2";
        double total_amnt = amt;
        String narration = "Test Narration";
        String customer_name = name;
        String account_number = account;
        String customer_cvv = "123";
        String cardExpiryDate = card_date_expiry;

        String depositor_payee = name;
        String ID_type = idCard;
        String ID_number = idNo;

        int branchID = SharedPrefManager.getAgencyUser().getUserBranch();
        int agencyID = SharedPrefManager.getAgencyUser().getAgencyID();
        String branchName = SharedPrefManager.getBranchDetails().getBranchName();
        String a_name = SharedPrefManager.getAgencyDetails().getAgencyName();

        JsonObject agency = new JsonObject();
        agency.addProperty("agent_id", agencyID);
        agency.addProperty("branch_id", branchID);
        agency.addProperty("branch_key", branch_key);
        agency.addProperty("agent", branch_key);
        agency.addProperty("agent_key", agency_key);

        JsonObject transaction = new JsonObject();
        transaction.addProperty("trans_cat", Integer.parseInt(trans_cat));
        transaction.addProperty("trans_type", Integer.parseInt(trans_type));
        transaction.addProperty("amount", Double.parseDouble(String.valueOf(total_amnt)));
        transaction.addProperty("narration", narration);
        transaction.addProperty("id_type", ID_type);
        transaction.addProperty("id_number", ID_number);
        transaction.addProperty("depositor_payee", depositor_payee);
        transaction.addProperty("customer_name", customer_name);
        transaction.addProperty("product_ID", 6);

        JsonObject customer = new JsonObject();
        customer.addProperty("account_name", customer_name);
        customer.addProperty("account_number", account_number);
        customer.addProperty("cvv", customer_cvv);
        customer.addProperty("card_expiry_date", cardExpiryDate);

        JsonObject teller = new JsonObject();
        teller.addProperty("teller_id", Integer.parseInt(officer_ID));

        JsonObject meta = new JsonObject();
        meta.addProperty("mobile_transaction_reference", "521wd542d86");

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("Agent_Details", agency);
        jsonObject.add("transaction", transaction);
        jsonObject.add("account_details", customer);
        jsonObject.add("teller_details", teller);
        jsonObject.add("meta_data", meta);
        Log.e("TRANSACTION_PAYLOAD", "transaction payload: " + jsonObject);
        Disposable subscribe = AgencyBankingAPIClient
                .sendTransaction(jsonObject, context)
                .subscribe(res -> {
                    if(res.code() != 200) {
                        Toast.makeText(context, "Failed to process transaction", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Object body = res.body();
                    JSONObject response = new JSONObject(new Gson().toJson(body));
                    Log.e("TRANSACTION_RESPONSE", "transaction response: " + response);
                    if(response.optInt("code") != 112) {
                        Toast.makeText(context, response.optString("msg"), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String ref = response.optString("ref");
                    //printReceipt(customer_name, ID_type, ID_number, a_name, branchName ,ref);

                    //process transaction
                },err -> {
                    Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show();
                });

    }
}
