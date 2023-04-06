package com.woleapp.util;

import static com.netpluspay.netpossdk.NetPosSdk.loadEmvParams;

import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideCustomImageLoader;
import com.github.piasy.biv.loader.glide.GlideModel;
import com.google.firebase.FirebaseApp;
import com.netpluspay.netpossdk.NetPosSdk;
import com.netpluspay.netpossdk.utils.DeviceConfig;
import com.netpluspay.netpossdk.utils.TerminalParameters;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.pixplicity.easyprefs.library.Prefs;
import com.woleapp.model.Inventory;

import smartpesa.sdk.ServiceManager;
import smartpesa.sdk.ServiceManagerConfig;
import smartpesa.sdk.models.transaction.Transaction;
import timber.log.Timber;


/**
 * Created by asus on 13-Jul-18.
 */

public class StormApplication extends MultiDexApplication {

    //private SharedPreferences appPreferences;
    //private Post post;

    private static StormApplication mInstance;
    Inventory selectedInventory;

    //private String mGlobalCheckButton = null;
    Transaction transaction;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FirebaseApp.initializeApp(this);
        Timber.plant(new Timber.DebugTree());
        NetPosSdk.init();
        TerminalParameters terminalParameters = new TerminalParameters();
        terminalParameters.setMerchantCode("3099");
        terminalParameters.setMerchantName("NetPlus");
        terminalParameters.setMerchantId("2033LAGPOOO7885");
        terminalParameters.setTerminalId("2033ALZP");
        terminalParameters.setTerminalCapability("E06868");
        loadEmvParams(terminalParameters);
        NetPosSdk.loadProvidedCapksAndAids();
        NetPosSdk.writeTpkKey(DeviceConfig.TPKIndex, "290bc46ef2043bb6d9a1579d980dbc10");
        /*RxJavaPlugins.setErrorHandler(throwable -> {
            throwable.printStackTrace();
            Log.e("app", "" + throwable.getLocalizedMessage());
        });*/
        //setupLeakCanary();
        setupLogLevel();
        BigImageViewer.initialize(GlideCustomImageLoader.with(getApplicationContext(), GlideModel.class));
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        //setupMainSettings();
        initServiceManager();
    }

    public void initServiceManager() {
        ServiceManagerConfig config = ServiceManagerConfig.newBuilder(getApplicationContext())
                .endPoint("netplus.prod.smartpesa.com")
                .withoutSsl()
                .build();
        ServiceManager.init(config);


        //use this to get serviceManager instance anywhere
        //mServiceManager = ServiceManager.get(context);
    }

    public Inventory getSelectedInventory() {
        return selectedInventory;
    }

    public void setSelectedInventory(Inventory selectedInventory) {
        this.selectedInventory = selectedInventory;
    }


    /*public static Boolean isInDebugMode() {
        return BuildConfig.DEBUG;
    }*/

    /*private void setupLeakCanary() {
        if (LeakCanary.INSTANCE.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);
        // Normal app init code...
    }*/

    private void setupLogLevel() {
        if (isInDebugMode()) {
            Logger.addLogAdapter(new AndroidLogAdapter());
        }
    }

    private boolean isInDebugMode() {
        boolean isDebug = ((this.getApplicationInfo().flags &
                ApplicationInfo.FLAG_DEBUGGABLE) != 0);

        Log.e("isDebugMode: ", isDebug + "--");

        return isDebug;
    }
    /*private void setupMainSettings() {
        appPreferences = getSharedPreferences(AppConstant.APP_PREF_FILE, MODE_PRIVATE);
        post = new PostImpl(this);

    }

    public SharedPreferences getAppPreferences() {
        return appPreferences;
    }


    public Post getPost() {
        return post;
    }
*/

    public static synchronized StormApplication getInstance() {
        return mInstance;
    }


    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}