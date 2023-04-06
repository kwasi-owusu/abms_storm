package com.woleapp.ui.activity

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.woleapp.R
import com.woleapp.databinding.LayoutMarketplaceContainerBinding
import com.woleapp.ui.fragments.MarketplaceHomeFragment
import com.woleapp.ui.fragments.MarketplaceProductDetailsFragment
import com.woleapp.ui.fragments.MarketplaceProductListFragment

class MarketPlaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)
            setActivityOrientation()
        val binding: LayoutMarketplaceContainerBinding =
            DataBindingUtil.setContentView(this, R.layout.layout_marketplace_container)
        showFragment(MarketplaceProductDetailsFragment(), MarketplaceHomeFragment::class.java.simpleName)
    }

    private fun showFragment(targetFragment: Fragment, className: String) {
        try {
            supportFragmentManager.beginTransaction()
                .apply {
                    replace(R.id.marketplace_container, targetFragment, className)
                    setCustomAnimations(R.anim.right_to_left, android.R.anim.fade_out)
                    commit()
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
}