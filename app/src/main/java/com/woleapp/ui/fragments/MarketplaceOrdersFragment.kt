@file:Suppress("DEPRECATION")

package com.woleapp.ui.fragments

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.woleapp.R
import com.woleapp.adapters.MarketplaceOrderAdapter
import com.woleapp.databinding.LayoutMarketplaceProductListBinding
import com.woleapp.network.MerchantsApiClient
import com.woleapp.util.SharedPrefManager
import com.woleapp.viewmodels.MarketplaceViewModel
import timber.log.Timber

class MarketplaceOrdersFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(mode: Int = 1) = MarketplaceOrdersFragment()
                .apply {
                    arguments = Bundle()
                            .apply {
                                putInt("MODE", mode)
                            }
                }
    }

    private lateinit var binding: LayoutMarketplaceProductListBinding
    private lateinit var viewModel: MarketplaceViewModel
    private lateinit var adapter: MarketplaceOrderAdapter
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = LayoutMarketplaceProductListBinding.inflate(inflater, container, false)
                .apply {
                    executePendingBindings()
                    lifecycleOwner = viewLifecycleOwner
                    inventorySearch.visibility = View.GONE
                }
        viewModel = ViewModelProvider(this).get(MarketplaceViewModel::class.java)
                .apply {
                    setMerchantApiService(MerchantsApiClient.getMerchantApiService(requireContext()))
                }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addProduct.visibility = View.GONE
        binding.products.layoutManager = LinearLayoutManager(requireContext())
        binding.products.addItemDecoration(
                DividerItemDecoration(
                        requireContext(),
                        VERTICAL
                )
        )
        viewModel.message.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
        val mode = arguments!!.getInt("MODE", 1)
        adapter = MarketplaceOrderAdapter(mode) {
            Timber.e("ID: ${it.id}")
            Timber.e("d s: ${it.deliveryStatus}")
            Timber.e("Seller ID: ${it.sellerId}\nBuyer ID: ${it.buyerId}\nLogged In ID: ${SharedPrefManager.getUser().netplus_id}")
            //it.totalCost = "100"
            Toast.makeText(requireContext(), "ID:${it.id}", Toast.LENGTH_LONG).show()
            viewModel.updateSalesOrder(it)
        }
        binding.products.adapter = adapter
        viewModel.saleOrder.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = false
            adapter.submitList(it.reversed())
        })
        viewModel.message.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.showProgressDialog.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                if (it)
                    showProgressDialog() else hideProgressDialog()
            }
        })
        binding.swipeRefresh.isRefreshing = true
        getOrders(mode)
        binding.swipeRefresh.setOnRefreshListener {
            getOrders(mode)
        }
    }

    private fun getOrders(mode: Int) {
        if (mode == 1)
            viewModel.getSellerOrders(SharedPrefManager.getUser().netplus_id)
        else
            viewModel.getBuyersOrders(SharedPrefManager.getUser().netplus_id)
    }

    lateinit var mProgressDialog: ProgressDialog
    private fun showProgressDialog() {
        mProgressDialog = ProgressDialog.show(activity, null, null)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mProgressDialog.setCancelable(false)
    }

    private fun hideProgressDialog() {
        if (::mProgressDialog.isInitialized && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }
}