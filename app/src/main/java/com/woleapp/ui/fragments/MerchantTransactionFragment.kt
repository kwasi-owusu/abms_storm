package com.woleapp.ui.fragments

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.woleapp.R
import com.woleapp.adapters.MerchantTransactionAdapter
import com.woleapp.databinding.LayoutTransactionListBinding
import com.woleapp.db.AppDatabase
import com.woleapp.network.MerchantsApiClient
import com.woleapp.network.StormAPIClient
import com.woleapp.util.SharedPrefManager
import com.woleapp.viewmodels.MerchantTransactionViewModel
import com.woleapp.viewmodels.PAYMENT_MODE_TRANSFER

class MerchantTransactionFragment : Fragment() {
    private lateinit var binding: LayoutTransactionListBinding
    private lateinit var viewModel: MerchantTransactionViewModel
    private var progressDialog: ProgressDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutTransactionListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MerchantTransactionViewModel::class.java)
        viewModel.setMerchantApiService(MerchantsApiClient.getMerchantApiService(context!!))
        viewModel.setStormApiService(StormAPIClient.create(context!!))
        viewModel.setTransactionDao(AppDatabase.getInstance(context!!).transactionsDao())
        val transactionDao = AppDatabase.getInstance(context).transactionsDao()
        val adapter = MerchantTransactionAdapter()
        adapter.setTransactionListener {
            if (it.paymentMethod == PAYMENT_MODE_TRANSFER) {
                Toast.makeText(context, it.paymentMethod, Toast.LENGTH_SHORT).show()
                viewModel.getNotification(it, SharedPrefManager.getUserToken())
            }
        }
        binding.rvInventories.adapter = adapter
        transactionDao.transactions.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                val n = if (it) ::showProgressBar else ::dismissProgressBar
                n.invoke()
            }
        })
        viewModel.message.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                val n = if (it) ::showProgressBar else ::dismissProgressBar
                n.invoke()
            }
        })
        viewModel.refreshTransactions(SharedPrefManager.getUser().netplus_id)
        return binding.root
    }

    fun showProgressBar() {
        if (progressDialog != null && progressDialog!!.isShowing) return
        progressDialog = ProgressDialog.show(context, null, null)
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
}