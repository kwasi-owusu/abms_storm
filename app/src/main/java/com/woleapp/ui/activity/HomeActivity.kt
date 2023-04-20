package com.woleapp.ui.activity

//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.google.android.material.snackbar.Snackbar
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import com.woleapp.BuildConfig
import com.woleapp.R
import com.woleapp.databinding.DialogSupportBinding
import com.woleapp.databinding.LayoutHomeBinding
import com.woleapp.model.BaseResponse
import com.woleapp.model.SalesOrder
import com.woleapp.model.toFieldMap
import com.woleapp.network.MerchantsApiClient
import com.woleapp.network.StormAPIClient
import com.woleapp.ui.fragments.*
import com.woleapp.util.*
import com.woleapp.viewmodels.PAYMENT_MODE_POS
import com.woleapp.viewmodels.PAYMENT_STATUS_SUCCESS
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,

    FragmentManager.OnBackStackChangedListener, View.OnClickListener {
    lateinit var binding: LayoutHomeBinding
    lateinit var utilities: Utilities
    var context: Context? = null;
    var refreshUserInfo = false
    private var progressDialog: ProgressDialog? = null
    var compositeDisposable = CompositeDisposable()
    private var refreshingWalletBalance = false
    private var availableBalance = "0"
    private var ledgerBalance = "0"


    val getAvailableBalance: String
        get() = availableBalance
    val getLedgerBalance: String
        get() = ledgerBalance

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("HomeActivity", "onCreate")
        super.onCreate(savedInstanceState)

//        if (savedInstanceState == null)
//            setActivityOrientation();
        binding = DataBindingUtil.setContentView(this, R.layout.layout_home)
        this.context = this.applicationContext;
        Log.e("TAG", BuildConfig.BASE_URL_STORM)
//        val token = SharedPrefManager.getUserToken();
//
//        if (token == null || token.isEmpty() || JWTHelper.isExpired(token)) {
//            Prefs.clear()
//
//            val intent = Intent(this, MainActivity::class.java)
//            intent.flags =
//                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//            finish()
//            return
//        }



        supportFragmentManager.addOnBackStackChangedListener {
            toggleAppBarLayout()
        }
        SoftInputAssist(this)
//        setSupportActionBar(binding.appBar.toolbar)
        utilities = Utilities(this)
//        binding.appBar.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
//
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)//true
//        supportActionBar?.setShowHideAnimationEnabled(true);
        supportFragmentManager.addOnBackStackChangedListener(this)
        Toast.makeText(this, "Greetings !!", Toast.LENGTH_LONG).show()

        initNavigationDrawer()
//        binding.appBar.tvFundWallet.setOnClickListener(this)
        binding.appBar.tvLogout.setOnClickListener(this)
        showFragment(DashboardFragment(), DashboardFragment::class.java.simpleName)

        val bun = intent.getBundleExtra("notification_data")

        if (bun != null) {
            val message = bun.getString("message")
            Handler().postDelayed({ showD(true, message) }, 1000)
        }

//        getWalletBalance()
        binding.appBar.switchButton.visibility = View.GONE;
        binding.appBar.switchLa.setOnClickListener {
            val fragments = supportFragmentManager.fragments
            if (fragments.size > 0) {
                val fragment = when {
                    fragments.last() is DashboardFragment -> fragments.last()
                    fragments.last() is SupportRequestManagerFragment && fragments[fragments.size - 2] is DashboardFragment -> fragments[fragments.size - 2]
                    else -> {
                        Toast.makeText(
                            baseContext,
                            "Go back to your dashboard to switch",
                            Toast.LENGTH_SHORT
                        ).show()
                        null
                    }
                }
                fragment?.let {
                    (it as DashboardFragment).populate();
                }
            }
        }
        binding.appBar.refreshButton.setOnClickListener {
//            getWalletBalance()
        }

    }

    private fun showFragment(targetFragment: Fragment, className: String) {
        try {
            val ft = supportFragmentManager.beginTransaction()
            if (ft != null) {
                ft.replace(R.id.container_main, targetFragment, className)
                ft.setCustomAnimations(R.anim.right_to_left, android.R.anim.fade_out)
                ft.addToBackStack(className)
                ft.commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onResume() {
        super.onResume()
        val completed: Boolean = intent.getBooleanExtra("completed", false)
        if (completed) {
            supportFragmentManager.popBackStackImmediate(
                DashboardFragment::class.java.simpleName,
                0
            );
        }
    }

    private fun toggleAppBarLayout() {
        val fragments = supportFragmentManager.fragments
        val currentFragment = fragments[fragments.size - 1]
        binding.appBar.mainAppBar.visibility = if (currentFragment is BaseMarketplaceFragment || currentFragment is HealthCheckerFragment ||
            currentFragment is CashInOptionsFragment || currentFragment is CashInFragment || currentFragment is CashoutFragment || currentFragment is CollectionsFragment
            || currentFragment is WebViewFragment || currentFragment is TicketsFragment || currentFragment is RemittanceFragment || currentFragment is InsuranceFragment
            || currentFragment is PaymentSuccessfulFragment || currentFragment is MoneygramFragment || currentFragment is RiaFragment || currentFragment is WesternUnionFragment
            || currentFragment is SSNITFragment  || currentFragment is LocalGovernmentFragment  || currentFragment is FinesBailsFragment  || currentFragment is AgencyTransactionFragment
            || currentFragment is AgencyTransactionDetailFragment  || currentFragment is FundWalletFragment || currentFragment is CollectionsNewFragment
            || currentFragment is CardWebViewFragment || currentFragment is TransactionStatusFragment || currentFragment is PaymentFailedFragment || currentFragment is PaymentCardSuccessfulFragment
            || currentFragment is SchoolFeesFragment || currentFragment is PassportFragment || currentFragment is FuneralMerchantFragment || currentFragment is FuneralDonationsFragment
        )
            View.GONE
        else View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(receiver, IntentFilter("BALANCE_UPDATE"))
        }
        toggleAppBarLayout()
    }


    override fun onStop() {
        super.onStop()
        context?.let { LocalBroadcastManager.getInstance(it).unregisterReceiver(receiver) };
    }

    fun getWalletBalance() {
        if (refreshingWalletBalance)
            return
        val token = SharedPrefManager.getUserToken()
        val stormId = SharedPrefManager.getUser().netplus_id
        if (token != null && token.isNotEmpty() && !JWTHelper.isExpired(
                token
            )
        ) {
            StormAPIClient.create(context)
            StormAPIClient.getWalletBalance(stormId)
                .doOnSubscribe {
                    refreshingWalletBalance = true
                    Toast.makeText(
                        context,
                        "Refreshing wallet balance",
                        Toast.LENGTH_SHORT
                    ).show()
                }.doFinally {
                    refreshingWalletBalance = false
                }
                .subscribe({
                    Toast.makeText(context, "Wallet balance refreshed", Toast.LENGTH_SHORT).show()
                    val data = JSONObject(Gson().toJson(it.body()))
                    Log.e("TAG", data.toString())
                    when (data.getDouble("availableBalance") < 0) {
//                        true -> setAvailableBalance("0.00")
//                        false -> setAvailableBalance(data.getDouble("availableBalance").toString())
                    }
//                    setLedgerBalance(data.getDouble("ledgerBalance").toString())
                }, {
                    Log.e("HomeActivity", "error occured", it)
                    Toast.makeText(
                        context,
                        "An unexpected error occurred while refreshing your wallet balance",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                })

        }

    }

//    fun setLedgerBalance(amount: String) {
//        if (amount != null) {
//            val user1 = SharedPrefManager.getUser()
//            ledgerBalance = amount
//            user1.ledgerBalance = amount
//            SharedPrefManager.setUser(user1)
//            binding.appBar.tvLedgerBalance.setText(
//                utilities.getFormattedAmount(
//                    java.lang.Double.parseDouble(
//                        amount
//                    )
//                ) + ""
//            )
//        }
//    }
//
    fun setAvailableBalance(amount: String) {
//        if (amount != null) {
//            val user1 = SharedPrefManager.getUser()
//            user1.availableBalance = amount
//            availableBalance = amount
//            SharedPrefManager.setUser(user1)
//            binding.appBar.tvAvailableBalance.setText(
//                utilities.getFormattedAmount(
//                    java.lang.Double.parseDouble(
//                        amount
//                    )
//                ) + ""
//
//            )
//        }
    }


    private fun setActivityOrientation() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.colorPrimaryDark)
        }
    }

    fun initNavigationDrawer() {
        /*drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)*/

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.appBar.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.setDrawerIndicatorEnabled(false);
        binding.drawerLayout.addDrawerListener(toggle)
        //drawerLayout.removeDrawerListener(this)
        toggle.syncState()
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        binding.navView.setNavigationItemSelectedListener(this)
    }

    fun setMerchantToolbar() {
        //binding.appBar.tvAvailableBalanceTitle.setVisibility(View.INVISIBLE)
        //binding.appBar.tvAvailableBalance.setVisibility(View.INVISIBLE)
        //binding.appBar.tvLedgerBalance.visibility = View.INVISIBLE;
        //binding.appBar.tvLedgerBalanceTitle.visibility = View.INVISIBLE;
    }

    fun setUserName(name: String) {
        binding.appBar.tvTitle.text = "Welcome, $name"
    }


    fun setTitleWithNoNavigation(title: String) {


    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        Log.e("home", "back pressed");
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val fragmentManager = supportFragmentManager
            Log.e("BackStackCount: ", fragmentManager.backStackEntryCount.toString() + "--")
            if (fragmentManager.backStackEntryCount > 1) {
//                super.onBackPressed()
                supportFragmentManager.popBackStack()
            } else {
                //showDialog22(applicationContext.getString(R.string.do_you_want_close_the_app), this)
                finish()

            }
            //super.onBackPressed()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.e("HomeActivity", "onNewIntent")
        val bun = intent.getBundleExtra("notification_data")
        if (bun != null) {
            val title = bun.getString("title")
            val message = bun.getString("message")
            if (title!!.contentEquals("Wallet Notification")) {
//                getWalletBalance();
                showD(true, message)
            } else {
                showD(true, message)
            }
        }

    }

    fun showD(isNotification: Boolean, message: String?) {
        val binding: DialogSupportBinding
        val dialog2 = Dialog(this)
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
        /*dialog2.setContentView(R.layout.dialog_record_payment);
        binding = DataBindingUtil.setContentView( R.layout.dialog_record_payment);*/
        binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(this),
                R.layout.dialog_support,
                null,
                false
            ) as DialogSupportBinding
        dialog2.setContentView(binding.getRoot())
        dialog2.setCanceledOnTouchOutside(false)
        dialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (isNotification) {

            binding.tvMessage.setText(message/*+" "+INTRO_VIDEO_URL*/)
            binding.tvTitle.setText(this.resources.getString(R.string.app_name))
            binding.btnDone.setVisibility(View.VISIBLE)

        }
        binding.ivClose.setOnClickListener(View.OnClickListener { dialog2.dismiss() })

        binding.btnDone.setOnClickListener(View.OnClickListener { dialog2.dismiss() })

        dialog2.setOnDismissListener { }


        dialog2.setCanceledOnTouchOutside(false)
        dialog2.setCancelable(true)
        dialog2.window!!.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW)
        dialog2.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog2.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("HomeActivity", "onActivityResult: $requestCode , $resultCode")
        val completed: Boolean = data?.getBooleanExtra("completed", false) ?: false;
        if (completed && resultCode == Activity.RESULT_OK) {

            val productId: Int = data?.getIntExtra("productId", 0) ?: 0
            val quantity: Int = data?.getIntExtra("quantity", 0) ?: 0
            val reference: String = data?.getStringExtra("reference") ?: ""
            val amount: Double = data?.getDoubleExtra("amount", 0.0) ?: 0.0

            if (productId < 1 || reference.isEmpty()) {

                supportFragmentManager.popBackStackImmediate(
                    DashboardFragment::class.java.simpleName,
                    0
                )
                return

            }
            val stormId = SharedPrefManager.getUser().netplus_id
            val salesOrder = SalesOrder(
                reference,
                PAYMENT_STATUS_SUCCESS,
                quantity.toString(),
                amount.toString(),
                PAYMENT_MODE_POS,
                productId.toString(),
                stormId
            ).toFieldMap()
            MerchantsApiClient.getMerchantApiService(context!!)
                .checkout(stormId, salesOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { dismissProgressBar() }
                .subscribe(object : SingleObserver<BaseResponse<Any>> {
                    override fun onSuccess(t: BaseResponse<Any>) {
                        Log.d(HomeActivity::class.java.simpleName, t.toString())
                        /*supportFragmentManager.popBackStackImmediate(
                            DashboardFragment::class.java.simpleName,
                            0
                        )*/
                        addFragmentWithoutRemove(
                            R.id.container_main,
                            PaymentSuccessFragment(),
                            PaymentSuccessFragment::class.java.simpleName
                        )
                    }

                    override fun onSubscribe(d: Disposable) {
                        d.disposeWith(compositeDisposable)
                        showProgressBar()
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(
                            this@HomeActivity,
                            "Payment was successful but an error occurred while completing order",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e(HomeActivity::class.java.simpleName, e.localizedMessage!!)
                        supportFragmentManager.popBackStackImmediate(
                            DashboardFragment::class.java.simpleName,
                            0
                        );
                    }

                })

            /*val json: JsonObject = JsonObject();
            json.addProperty("product_id", productId);
            json.addProperty("quantity", quantity)
            APIServiceClient.decrementStockLevel(json, object : ApiCallback2 {

                override fun onStart() {
                }

                override fun onEnd() {
                }

                override fun onResponse(response: Any?) {
                    Log.d(HomeActivity::class.java.simpleName, response.toString())
                    supportFragmentManager.popBackStackImmediate(
                        DashboardFragment::class.java.simpleName,
                        0
                    );
                }

                override fun onFail(msg: String?) {
                    Log.e(HomeActivity::class.java.simpleName, msg)
                    supportFragmentManager.popBackStackImmediate(
                        DashboardFragment::class.java.simpleName,
                        0
                    );
                }

            }, context)*/

        }
    }

    fun showProgressBar() {
        if (progressDialog != null && progressDialog!!.isShowing) return
        progressDialog = ProgressDialog.show(this@HomeActivity, null, null)
            .apply {
                setContentView(R.layout.dialog_progress)
                window!!
                    .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCancelable(false)
            }
    }

    fun dismissProgressBar() {
        progressDialog?.run {
            if (isShowing)
                dismiss()
        }
    }

    override fun onBackStackChanged() {

        val fragmentOnTop = getFragmentNameOnTop()
        Log.e(
            "BackStackChanged",
            "fragmentOnTop:- " + fragmentOnTop + " " + supportFragmentManager.backStackEntryCount
        )
        /*       if (supportActionBar != null && supportFragmentManager.backStackEntryCount > 1 ) {
                   //supportActionBar?.shosetDisplayHomeAsUpEnabled(true)
                   supportActionBar?.setDisplayShowHomeEnabled(true)
                   supportActionBar?.setDisplayHomeAsUpEnabled(true)//true
               } else {
                   supportActionBar?.setDisplayShowHomeEnabled(false)
                   supportActionBar?.setDisplayHomeAsUpEnabled(false)//true

                   //binding.appBar.toolbar.setNavigationIcon(null)
                   //binding.appBar.toolbar.setNavigationIcon(R.draw)
               }
               if (fragmentOnTop.equals(
                       "QRRegistrationFragment",
                       ignoreCase = true
                   ) || fragmentOnTop.equals("AccountActivationFragment", ignoreCase = true)
               )//|| fragmentOnTop.equals("ScanToPayFragment", ignoreCase = true)
               {

                   Handler().postDelayed({ supportActionBar!!.hide() }, 600)

               } else {
                   *//*if (fragmentOnTop.equals("QRRegistrationFragment", ignoreCase = true) || fragmentOnTop.equals("AccountActivationFragment", ignoreCase = true))
            {

            }*//*
//            supportActionBar!!.show()
        }*/
    }

    fun getFragmentNameOnTop(): String {
        val fragment = supportFragmentManager
            .findFragmentById(R.id.container_main)

        if (fragment != null) {
            val name = fragment.javaClass.simpleName//getName();
            Log.e("FragmentOnTop", "$name--")
            return name
        }
        return ""
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_logout -> {
                gotoLoginScreen();
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun abc(a: Int): Boolean {
        return true
    }

    fun pqrGroup(a: Int) {

        //return true
    }

    private fun gotoLoginScreen() {

        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(getString(R.string.logout_confirm_message))
            // if the dialog is cancelable
            .setCancelable(true)
            // positive button text and action
            .setPositiveButton(getString(R.string.yes)) { dialog, id ->
                Prefs.clear()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                this.finishAffinity()

            }
            .setNegativeButton(
                getString(R.string.cancel)
            ) { dialog, id ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Logout")
        alert.show()

    }

    override fun onClick(v: View?) {
        if (v == binding.appBar.tvLogout) {

            gotoLoginScreen();
        }
    }

    fun addFragmentWithoutRemove(containerViewId: Int, fragment: Fragment, fragmentName: String) {
        val tag = fragment.javaClass.simpleName//getTag();
        Log.e("FragmentTag", "$tag---")
        this!!.getSupportFragmentManager().beginTransaction()
            //.setCustomAnimations(R.anim.right_to_left, android.R.anim.fade_out)
            .setCustomAnimations(
                R.anim.right_to_left, R.anim.left_to_right,
                R.anim.right_to_left, R.anim.left_to_right
            )
            // remove fragment from fragment manager
            //fragmentTransaction.remove(getActivity().getSupportFragmentManager().findFragmentByTag(tag));
            // add fragment in fragment manager
            .add(containerViewId, fragment, fragmentName)
            // add to back stack
            .addToBackStack(tag)
            .commit()
    }


    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            Log.d("HomeActivity", "receiver called")

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
