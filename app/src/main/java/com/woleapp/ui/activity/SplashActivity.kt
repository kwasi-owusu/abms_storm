package com.woleapp.ui.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.woleapp.R
import com.woleapp.databinding.SplashBinding
import com.woleapp.util.SharedPrefManager
import com.woleapp.util.Utilities
import java.util.regex.Pattern

class SplashActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,FragmentManager.OnBackStackChangedListener
{
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var binding : SplashBinding
    lateinit var utilities : Utilities;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityOrientation();
        //setContentView(R.layout.splash)
        binding = DataBindingUtil.setContentView(this, R.layout.splash);

        binding.ivLogo.getViewTreeObserver().addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    // Layout has happened here.

                    animate(binding.ivLogo)
                    // Don't forget to remove your listener when you are done with it.
                    binding.ivLogo.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                }
            })

        utilities =  Utilities(this);
        var fam = utilities.getFormattedAmount1(5054406.445)
        Log.e("Res",fam+"**")

        var bool = TextUtils.isDigitsOnly("AFFASFAF8OD")
        var bool1 = TextUtils.isDigitsOnly("NHFKHFLH")
        var bool2 = TextUtils.isDigitsOnly("65456465")
        var bool3 = hasNumbers("AFFASFAF8OD")
        var bool4 = hasNumbers("AFFASFAF0OD")
        var bool5 = hasNumbers("kfjhfkjhakl")
        /*Log.e("Bool","**"+bool)
        Log.e("Bool1","**"+bool1)
        Log.e("Bool2","**"+bool2)*/

        Log.e("Bool3","**"+bool3)
        Log.e("Bool4","**"+bool4)
        Log.e("Bool5","**"+bool5)
        Log.e("Res1","**"+getLastDigitPos("ABCDEFGH"))//7
        Log.e("Res2","**"+getLastDigitPos("ABCDEF9"))//5
        Log.e("Res3","**"+getLastDigitPos("0ABCDEF"))//6
        Log.e("Res4","**"+getLastDigitPos("045646"))//-1 No alphabet
        Log.e("Res5","**"+getLastDigitPos("VABC3EF"))//6

        Log.e("Res2","**"+"ABCDEF6".substring(5+1))


        Log.e("Res5","**"+getLastDigitPos("VABC3EF"))//6

        Log.e("PV1","**"+priceValidation(".545454"))//6
        Log.e("PV2","**"+priceValidation("....."))//6
        Log.e("PV3","**"+priceValidation("0000"))//6
        Log.e("PV4","**"+priceValidation("1234"))//6
        Log.e("PV5","**"+priceValidation(",,.."))//6
        Log.e("PV6","**"+priceValidation(".0"))//6
        Log.e("PV7","**"+priceValidation("0."))//6
        Log.e("PV8","**"+priceValidation("1.0"))//6
        Log.e("PV9","**"+priceValidation("1..2"))//6
        Log.e("PV9","**"+priceValidation("..2"))//6
        //val toolbar: Toolbar = findViewById(R.id.toolbar)
        /*toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportFragmentManager.addOnBackStackChangedListener(this)
        Toast.makeText(this,"Greetings !!",Toast.LENGTH_LONG).show()*/
//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        //initNavigationDrawer()

    }


    fun hasAlphabets(str: String?): Boolean {
        return str != null && str.matches("^[a-zA-Z]*$".toRegex())
    }

    fun hasNumbers(str: String?): Boolean {
        return str != null && str.matches(".*\\d.*".toRegex()) //("[0-9]+") //[0-9]+
    }

    fun getLastDigitPos(aString: String): Int {
        val charArray = aString.toCharArray()
        if (aString != null && !aString.isEmpty()) {
            for (i in charArray.indices.reversed()) {
                val c = charArray[i]
                if (!Character.isDigit(c)) {
                    return i
                }
            }
        }
        return -1
    }

    fun priceValidation(price: String): Boolean {
        //^[1-9][0-9]{12,16}$ (old regular expression)
        //		String regex = "^[+][0-9]{12,16}$";
        val regex = "[+-]?([0-9]*[.])?[0-9]+"//"[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)";
        //String regex = "^[+][0-9]{10,13}$";
        val numberPattern = Pattern.compile(regex)
        val result = numberPattern.matcher(price).matches()
        Log.e("Result: ", "$result--")
        return result
    }


    private fun goToLogin() {
        finish()// close this activity

        if(SharedPrefManager.getUser()!=null && SharedPrefManager.getUser().is_verified)
        {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }
        else
        {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        //val i = Intent(this, MainActivity::class.java)
        //startActivity(i)
    }
    fun animate(view: View) {
        val set = AnimatorSet()
        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator)
            {
                goToLogin();
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
        set.playTogether(
            ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
                .setDuration(1500),
            ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f)
                .setDuration(1500),
            ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f) //0.5
                .setDuration(1500),
            ObjectAnimator.ofFloat(view, "rotation", 360f, 0f)
                .setDuration(1500),
            ObjectAnimator.ofFloat(view, "rotation", 360f, 0f)
                .setDuration(1500)
            //                ,
            //                ObjectAnimator.ofFloat(fragmentView, "X", 0).setDuration(800), //200
            //                ObjectAnimator.ofFloat(fragmentView, "X", 0).setDuration(800)
        )
        set.start()

        //StaticData.toggle = 0;
    }

    fun setActivityOrientation()
    {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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

    fun initNavigationDrawer()
    {
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
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onBackStackChanged() {
         //To change body of created functions use File | Settings | File Templates.
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

    fun abc(a : Int) : Boolean
    {
        return true
    }

    fun pqrGroup(a : Int)
    {
        //return true
    }
}
