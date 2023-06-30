package com.woleapp.ui.activity

import android.app.Dialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.JsonObject
import com.woleapp.BuildConfig
import com.woleapp.R
import com.woleapp.databinding.DialogSupportBinding
import com.woleapp.databinding.LayoutMainBinding
import com.woleapp.network.StormAPIClient
import com.woleapp.ui.fragments.LoginFragment
import com.woleapp.util.JWTHelper
import com.woleapp.util.SharedPrefManager
//import kotlinx.android.synthetic.main.dialog_fragment_bluetooth_list.view.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    FragmentManager.OnBackStackChangedListener {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var binding: LayoutMainBinding
    var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (savedInstanceState == null)
//            setActivityOrientation();
        binding = DataBindingUtil.setContentView(this, R.layout.layout_main);
        //val toolbar: Toolbar = findViewById(R.id.toolbar)

//        checkToken()

        showFragment(LoginFragment(), LoginFragment::class.java.simpleName)

    }

//    fun checkToken(boolean: Boolean = false) {
//        val credentials = JsonObject()
//        credentials.addProperty("appname", context.getString(R.string._app_name))
//        credentials.addProperty("password", context.getString(R.string._password))
//
//        val appToken: String? = SharedPrefManager.getAppToken()
//        if (appToken.isNullOrEmpty() || JWTHelper.isExpired(appToken)) {
//            getAppToken(credentials, boolean)
//        }
//    }
//
//    private fun getAppToken(credentials: JsonObject, boolean: Boolean) {
//        StormAPIClient.getAppToken(credentials, object : StormAPIClient.ApiCallback2 {
//            override fun onStart() {
//                Log.e("MainActivity", "getting app token")
//            }
//
//            override fun onEnd() {
//            }
//
//            override fun onResponse(response: Any?) {
//                Log.d("MainActivity", "call to api/token came back")
//                val payload = response as JSONObject
//                if (payload.getBoolean("success") != null && payload.getBoolean("success") == true) {
//                    SharedPrefManager.setAppToken(payload.getString("token"))
//                    //tell login fragment to login
//                    if (boolean) {
//                        sendResponseToLoginFragment(true)
//                    }
//                } else {
//                    Log.d("MainActivity", "authentication failed")
//                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
//                    sendResponseToLoginFragment(false)
//                }
//            }
//
//            override fun onFail(msg: String?) {
//                sendResponseToLoginFragment(false)
//                Log.e("MainActivity", msg!!)
//                Toast.makeText(
//                    context,
//                    "Please check that you are connected to the internet.",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//
//        }, this)
//    }

    private fun sendResponseToLoginFragment(success: Boolean) {
        val fragments = supportFragmentManager.fragments
        if (fragments.isNotEmpty()) {
            val loginFragment = fragments[fragments.size - 1]
            if (loginFragment is LoginFragment) {
                loginFragment.dismissProgressBar()
                if (success)
                    loginFragment.validateInputsAndProceed()
            }

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
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        //super.onBackPressed()

        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        //val fragmentLast = fragmentManager.findFragmentById(R.id.container)
        //val tag = fragmentLast!!.tag
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    override fun onBackStackChanged() {

        val fragmentOnTop = getFragmentNameOnTop()
        Log.e("BackStackChanged", "fragmentOnTop:- " + fragmentOnTop)
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

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
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

            /*binding.tvMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new  Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.google.android.youtube");
                    intent.setData(Uri.parse(INTRO_VIDEO_URL));
                    startActivity(intent);
                }
            });*/

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

    fun abc(a: Int): Boolean {
        return true
    }

    fun pqrGroup(a: Int) {
        //return true
    }

    override fun onPostResume() {
        super.onPostResume()
    }


}
