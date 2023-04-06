@file:Suppress("DEPRECATION")

package com.woleapp.ui.fragments

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.woleapp.R

open class BaseMarketplaceFragment : BaseFragment() {
    lateinit var mProgressDialog: ProgressDialog
    open fun showProgressDialog() {
        mProgressDialog = ProgressDialog.show(activity, null, null)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mProgressDialog.setCancelable(false)
    }

    open fun hideProgressDialog() {
        if (::mProgressDialog.isInitialized && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }
}