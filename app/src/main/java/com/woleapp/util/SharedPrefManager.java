package com.woleapp.util;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.woleapp.model.Agency;
import com.woleapp.model.AgencyUser;
import com.woleapp.model.Balance;
import com.woleapp.model.Branch;
import com.woleapp.model.CardData;
import com.woleapp.model.Marketplace;
import com.woleapp.model.Token;
import com.woleapp.model.User;
import java.lang.reflect.Type;

import static com.woleapp.ui.fragments.MarketplaceProductListFragmentKt.MARKETPLACE_STORE;
import static com.woleapp.util.Constants.USER_TYPE_NONE;

public class SharedPrefManager {

    //Start Agency Banking
    public static void setAgencyUser(AgencyUser user) {
        String userJSONString = new Gson().toJson(user);
        Prefs.putString("agencyUser", userJSONString);
    }

    public static void setAgencyDetails(Agency agency) {
        String agencyJSONString = new Gson().toJson(agency);
        Prefs.putString("agency", agencyJSONString);
    }

    public static void setBalanceDetails(Balance balance) {
        String balanceJSONString = new Gson().toJson(balance);
        Prefs.putString("balance", balanceJSONString);
    }

    public static Balance getBalanceDetails() {
        String balanceJSONString = Prefs.getString("balance", "");
        if (TextUtils.isEmpty(balanceJSONString)) return null;
        Type type = new TypeToken<Balance>() {
        }.getType();
        Balance balance = new Gson().fromJson(balanceJSONString, type);
        return balance;
    }

    public static Agency getAgencyDetails() {
        String agencyJSONString = Prefs.getString("agency", "");
        if (TextUtils.isEmpty(agencyJSONString)) return null;
        Type type = new TypeToken<Agency>() {
        }.getType();
        Agency agency = new Gson().fromJson(agencyJSONString, type);
        return agency;
    }

    public static void setBranchDetails(Branch branch) {
        String userJSONString = new Gson().toJson(branch);
        Prefs.putString("branch", userJSONString);
    }

    public static CardData getCardData() {
        String cardJsonString = Prefs.getString("cardData", "");
        if (TextUtils.isEmpty(cardJsonString)) return null;
        Type type = new TypeToken<CardData>() {
        }.getType();
        CardData carddata = new Gson().fromJson(cardJsonString, type);
        return carddata;
    }

    public static void setCardData(CardData cardData) {
        String cardJSONString = new Gson().toJson(cardData);
        Prefs.putString("cardData", cardJSONString);
    }

    public static Branch getBranchDetails() {
        String branchJSONString = Prefs.getString("branch", "");
        if(TextUtils.isEmpty(branchJSONString)) return null;
        Type type = new TypeToken<Branch>() {
        }.getType();
        Branch branch = new Gson().fromJson(branchJSONString, type);
        return branch;
    }

    public static AgencyUser getAgencyUser() {
        String userJSONString = Prefs.getString("agencyUser", "");
        if (TextUtils.isEmpty(userJSONString))
            return null;
        Type type = new TypeToken<AgencyUser>() {
        }.getType();
        AgencyUser user = new Gson().fromJson(userJSONString, type);
        return user;
    }

    public static Token getToken() {
        String userJSONString = Prefs.getString("data", "");
        if (TextUtils.isEmpty(userJSONString))
            return null;
        Type type = new TypeToken<AgencyUser>() {
        }.getType();
        Token token = new Gson().fromJson(userJSONString, type);
        return token;
    }

        public static void setAgencyLoginStatus(boolean status) {
        if (!status) {
            setUser(null);
        }
        Prefs.putBoolean("agency_login", status);
    }

    public static boolean getAgencyLoginStatus() {
        return Prefs.getBoolean("agency_login", false);
    }
    //End of Agency Banking


    private static final String TAG_TOKEN = "push_token";

    public static void setMerchantStore(Marketplace marketplace) {
        Prefs.putString(MARKETPLACE_STORE, Singletons.getGsonInstance().toJson(marketplace));
    }

    public static Marketplace getMerchantStore() {
        return Singletons.getGsonInstance().fromJson(Prefs.getString(MARKETPLACE_STORE, null), Marketplace.class);
    }

    public static void setNetPlusPayConvenienceFee(Float netpluspayConvenienceFee) {
        Prefs.putFloat("netpluspay_convenience_fee", netpluspayConvenienceFee);
    }

    public static Float getNetPlusPayConvenienceFee() {
        return Prefs.getFloat("netpluspay_convenience_fee", 1.5f);
    }

    public static void setNextAgentTransactionsPage(int lastLoadedAgentTransactionsPage) {
        Prefs.putInt("LLATP", lastLoadedAgentTransactionsPage);
    }

    public static int getNextAgentTransactionsPage() {
        return Prefs.getInt("LLATP", 1);
    }

    public static void setLoginStatus(boolean status) {
        if (!status) {
            setUser(null);
        }
        Prefs.putBoolean("is_login", status);
    }

    public static boolean isLogin() {
        return Prefs.getBoolean("is_login", false);
    }

    public static void setAppToken(String appToken) {
        Prefs.putString("app_token", appToken);
    }


    public static String getLastLoggedInUser() {
        return Prefs.getString("last_logged_in_user", null);
    }

    public static void setLastLoggedInUser(String lastLoggedInUserId) {
        Prefs.putString("last_logged_in_user", lastLoggedInUserId);
    }

    public static void setUserType(int loginType) {
        Prefs.putInt("user_login_type", loginType);
    }

    public static int getUserType() {
        return Prefs.getInt("user_login_type", USER_TYPE_NONE);
    }

    public static String getAppToken() {
        return Prefs.getString("app_token", null);
    }

    public static Boolean hasAppToken() {
        return Prefs.getString("app_token", null) != null;
    }

    public static void setUserToken(String userToken) {
        Prefs.putString("user_token", userToken);
    }

    public static String getUserToken() {
        return Prefs.getString("user_token", null);
    }

    public static void setPOSConvenienceFee(Float convenience_fee) {
        Prefs.putFloat("pos_convenience_fee", convenience_fee);
    }

    public static Float getPOSConvenienceFee() {
        return Prefs.getFloat("pos_convenience_fee", 0.0f);
    }

    public static void setTransfeeConvenienceFee(Float convenience_fee) {
        Prefs.putFloat("transfer_convenience_fee", convenience_fee);
    }

    public static Float getTransfeeConvenienceFee() {
        return Prefs.getFloat("transfer_convenience_fee", 0.0f);
    }

    //this method will save the device token to shared preferences
    public static boolean saveDeviceToken(String token) {
        Prefs.putString(TAG_TOKEN, token);
        return true;
    }

    //this method will fetch the device token from shared preferences
    public static String getDeviceToken() {
        return Prefs.getString(TAG_TOKEN, null);
    }


    public static User getUser() {
        String userJSONString = Prefs.getString("user", "");
        if (TextUtils.isEmpty(userJSONString))
            return null;
        Type type = new TypeToken<User>() {
        }.getType();
        User user = new Gson().fromJson(userJSONString, type);
        return user;
    }

    public static void temp(String temp) {
        Prefs.putString("temp", temp);
    }

    public static String getTemp() {
        return Prefs.getString("temp", null);
    }

    public static void setUser(User user) {
        String userJSONString = new Gson().toJson(user);
        Prefs.putString("user", userJSONString);
    }
}
