package com.woleapp.ui.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pixplicity.easyprefs.library.Prefs
//import com.theartofdev.edmodo.cropper.CropImage
//import com.theartofdev.edmodo.cropper.CropImageView
import com.woleapp.R
import com.woleapp.adapters.DashboardAdapter
import com.woleapp.databinding.LayoutMarketplaceStoreBinding
import com.woleapp.databinding.LayoutMarketplaceStoreDashboardBinding
import com.woleapp.model.Marketplace
import com.woleapp.model.Service
import com.woleapp.network.MerchantsApiClient
import com.woleapp.util.OnItemClickListener
import com.woleapp.util.SharedPrefManager
import com.woleapp.util.setDecodedImageToImageView
import com.woleapp.viewmodels.MarketplaceViewModel

class MarketplaceStoreDashboard : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MarketplaceStoreDashboard()
    }

    private lateinit var binding: LayoutMarketplaceStoreDashboardBinding
    private lateinit var storeBinding: LayoutMarketplaceStoreBinding
    private lateinit var viewModel: MarketplaceViewModel
    private lateinit var marketplace: Marketplace
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        marketplace = SharedPrefManager.getMerchantStore() ?: Marketplace().apply {
            merchantId = SharedPrefManager.getUser().netplus_id
        }
        viewModel = ViewModelProvider(this).get(MarketplaceViewModel::class.java)
            .apply {
                setMerchantApiService(MerchantsApiClient.getMerchantApiService(requireContext()))
                setMarketPlace(marketplace)
            }
        binding = LayoutMarketplaceStoreDashboardBinding.inflate(inflater, container, false)
        storeBinding = LayoutMarketplaceStoreBinding.inflate(inflater, null, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                executePendingBindings()
                viewmodel = viewModel
            }

        storeBinding.storeLogo.setOnClickListener {
//            CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
//                .setGuidelinesColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
//                .setAspectRatio(4, 3)
//                .setAutoZoomEnabled(true)
//                .start(requireContext(), this)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onclick = OnItemClickListener.OnItemClickCallback { _, position ->
            when (position) {
                0 -> {
                    storeBinding.createStore.visibility = View.GONE
                    setDecodedImageToImageView(marketplace.logo, storeBinding.storeLogo)
                    showCreateStoreBottomSheet()
                }
                1 -> addFragmentWithoutRemove(
                    R.id.container_main,
                    MarketplaceProductListFragment.newInstance(),
                    MarketplaceProductListFragment::class.simpleName
                )
                2 -> addFragmentWithoutRemove(
                    R.id.container_main,
                    MarketplaceOrdersFragment.newInstance(),
                    MarketplaceOrdersFragment::class.simpleName
                )
                3 -> addFragmentWithoutRemove(
                    R.id.container_main,
                    QuickTransactionFragment(),
                    QuickTransactionFragment::class.java.simpleName
                )
            }
        }
        val serviceList = ArrayList<Service>()
            .apply {
                add(Service(0, "STORE INFO", R.drawable.ic_user_merchant))
                add(Service(1, "MY INVENTORY", R.drawable.inventory))
                add(Service(2, "RECEIVED ORDERS", R.drawable.ic_order))
                add(Service(3, "QUICK TRANSACTION", R.drawable.quick_transaction))
            }
        if (marketplace.id < 0) {
            showProgressDialog()
            viewModel.checkIfMerchantHasCreatedStore(marketplace.merchantId)
        }
        binding.rvItems.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = DashboardAdapter(requireContext(), serviceList, onclick, false)
        }
        viewModel.merchantStoreExists.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                hideProgressDialog()
                if (!it)
                    showCreateStoreBottomSheet()
                else {
                    marketplace.id = marketplace.id + 10
                    storeBinding.closeDialog.performClick()
                    Prefs.putBoolean(STORE_CREATED, true)
                }
            }
        })
        storeBinding.createStore.setOnClickListener {
            viewModel.saveImage(requireContext())
        }
        viewModel.message.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.showProgressDialog.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                if (it)
                    showProgressDialog() else hideProgressDialog()
            }
        })
        viewModel.fatalError.observe(viewLifecycleOwner, Observer { event ->
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

    private fun showCreateStoreBottomSheet() {
        BottomSheetDialog(requireContext())
            .apply {
                storeBinding.createStoreWelcome.text = getString(
                    R.string.create_store_welcome,
                    SharedPrefManager.getUser().business_name
                )
                storeBinding.closeDialog.setOnClickListener {
                    dismiss()
                    if (marketplace.id == -1)
                        requireActivity().onBackPressed()
                }
                dismissWithAnimation = true
                storeBinding.root.parent?.let {
                    (storeBinding.root.parent as ViewGroup).removeView(storeBinding.root)
                }
                setContentView(storeBinding.root)
                setCancelable(false)
                show()
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            val result = CropImage.getActivityResult(data)
//            if (resultCode == Activity.RESULT_OK) {
//                val resultUri = result.uri
//                val imagePath = resultUri.path!!
//                viewModel.setImagePath(imagePath)
//                Log.e("resultUri", resultUri.toString())
//                storeBinding.storeLogo.setImageURI(resultUri)
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                val error = result.error
//                Log.e("Error", error.toString())
//            }
        }
    }

    lateinit var mProgressDialog: ProgressDialog
    private fun showProgressDialog() {
        mProgressDialog = ProgressDialog.show(null, null, null)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mProgressDialog.setCancelable(false)
    }

    private fun hideProgressDialog() {
        if (::mProgressDialog.isInitialized && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }