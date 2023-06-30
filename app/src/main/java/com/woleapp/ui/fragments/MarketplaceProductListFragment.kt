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
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.pixplicity.easyprefs.library.Prefs
//import com.theartofdev.edmodo.cropper.CropImage
//import com.theartofdev.edmodo.cropper.CropImageView
import com.woleapp.R
import com.woleapp.adapters.MarketplaceProductAdapter
import com.woleapp.databinding.LayoutMarketplaceProductListBinding
import com.woleapp.databinding.LayoutMarketplaceStoreBinding
import com.woleapp.model.Inventory
import com.woleapp.model.Marketplace
import com.woleapp.network.MerchantsApiClient
import com.woleapp.util.SharedPrefManager
import com.woleapp.viewmodels.MarketplaceViewModel
import org.parceler.Parcels
import java.util.*

const val MARKETPLACE_STORE = "marketplace_store"
const val STORE_CREATED = "store_created"

class MarketplaceProductListFragment : BaseFragment() {
    private lateinit var viewModel: MarketplaceViewModel
    private var inventoryList: List<Inventory>? = null

    companion object {
        @JvmStatic
        @JvmOverloads
        fun newInstance(marketplace: Marketplace? = null) =
            MarketplaceProductListFragment()
                .apply {
                    marketplace?.let { marketplace ->
                        arguments = Bundle().apply {
                            putParcelable(MARKETPLACE_STORE, Parcels.wrap(marketplace))
                        }
                    }
                }
    }

    private lateinit var binding: LayoutMarketplaceProductListBinding
    private lateinit var storeBinding: LayoutMarketplaceStoreBinding
    private lateinit var marketplace: Marketplace
    private lateinit var marketplaceAdapter: MarketplaceProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        marketplace = if (arguments != null) {
            if (requireArguments().containsKey(MARKETPLACE_STORE))
                Parcels.unwrap(requireArguments().getParcelable(MARKETPLACE_STORE))
            else
                SharedPrefManager.getMerchantStore()
        } else {
            SharedPrefManager.getMerchantStore()
        }
        viewModel = ViewModelProvider(this).get(MarketplaceViewModel::class.java)
            .apply {
                setMerchantApiService(MerchantsApiClient.getMerchantApiService(requireContext()))
                setMarketPlace(marketplace)
            }
        binding = LayoutMarketplaceProductListBinding.inflate(inflater, container, false)
        storeBinding = LayoutMarketplaceStoreBinding.inflate(inflater, null, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                executePendingBindings()
                viewmodel = viewModel
            }

//        storeBinding.storeLogo.setOnClickListener {
//            CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
//                .setGuidelinesColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
//                .setAspectRatio(4, 3)
//                .setAutoZoomEnabled(true)
//                .start(requireContext(), this)
//        }
        marketplaceAdapter = MarketplaceProductAdapter {
            it.deliveryFee = if (marketplace.deliveryFee.isEmpty()) 0f else marketplace.deliveryFee.toFloat()
            it.storeName = marketplace.name
            addFragmentWithoutRemove(
                R.id.container_main,
                MarketplaceProductDetailsFragment.newInstance(it),
                MarketplaceProductDetailsFragment::class.java.simpleName
            )
        }
        binding.products.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = marketplaceAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Prefs.getString("current_user", "") != marketplace.merchantId) {
            binding.addProduct.visibility = View.GONE
        } else {
            binding.addProduct.setOnClickListener {
                addFragmentWithoutRemove(
                    R.id.container_main,
                    AddInventoryFragment(),
                    AddInventoryFragment::class.java.simpleName
                )
            }
        }
        viewModel.getInventories()
        storeBinding.createStore.setOnClickListener {
            viewModel.saveImage(requireContext())
        }
        viewModel.message.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.showProgressDialog.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                if (it)
                    showProgressDialog() else hideProgressDialog()
            }
        })

        binding.inventorySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                inventoryList?.let {
                    marketplaceAdapter.submitList(
                        if (newText.isEmpty())
                            it
                        else {
                            it.filter { inventory ->
                                inventory.product_name?.toLowerCase(Locale.getDefault())?.contains(newText.toLowerCase(Locale.getDefault())) ?: false
                                        ||
                                        inventory.description?.toLowerCase(Locale.getDefault())?.contains(newText.toLowerCase(Locale.getDefault())) ?: false
                                        ||
                                        inventory.category_name?.toLowerCase(Locale.getDefault())?.contains(newText.toLowerCase(Locale.getDefault())) ?: false
                            }
                        }
                    )
                }
                return false
            }

        })

        viewModel.inventories.observe(viewLifecycleOwner, { inventories ->
            inventories?.let {
                this.inventoryList = it
                if (it.isEmpty())
                    Toast.makeText(
                        requireContext(),
                        "There are no items in this store",
                        Toast.LENGTH_SHORT
                    ).show()
                marketplaceAdapter.submitList(it)
            }
        })
        viewModel.isRefreshing.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                binding.swipeRefresh.isRefreshing = it
            }
        })
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getInventories()
        }
        viewModel.fatalError.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    hideProgressDialog()
                    Toast.makeText(
                        requireContext(),
                        "An unexpected error has occurred, Try Again!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                requireActivity().onBackPressed()
            }
        })
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