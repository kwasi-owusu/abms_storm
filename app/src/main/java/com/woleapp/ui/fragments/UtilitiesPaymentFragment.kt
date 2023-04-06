package com.woleapp.ui.fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.woleapp.R
import com.woleapp.adapters.ServicesSpinnerAdapter
import com.woleapp.databinding.*
import com.woleapp.db.DataGenerator
import com.woleapp.model.Biller
import com.woleapp.model.ErrorNetworkResponse
import com.woleapp.model.SuccessNetworkResponse
import com.woleapp.network.StormAPIClient
import com.woleapp.network.StormUtilitiesApiClient
import com.woleapp.ui.activity.HomeActivity
import com.woleapp.util.SharedPrefManager
import com.woleapp.util.copyTextToClipboard
import com.woleapp.viewmodels.UtilitiesViewModel


class UtilitiesPaymentFragment : BaseFragment() {

    private lateinit var binding: LayoutPowerOrElectricityBinding
    private lateinit var cableBinding: LayoutCableTvBinding
    private lateinit var internetSubscriptionBinding: LayoutInternetSubscriptionBinding
    private lateinit var airtimeOrDataBinding: LayoutAirtimeOrDataBinding
    private lateinit var viewModel: UtilitiesViewModel
    private var progressDialog: ProgressDialog? = null
    private var alertDialog: AlertDialog? = null
    private var verifyBillDialog: BottomSheetDialog? = null

    companion object {
        @JvmStatic
        fun newInstance(serviceId: Int): UtilitiesPaymentFragment {
            val bundle = Bundle()
            bundle.putInt("service_id", serviceId)
            val electricityPaymentFragment = UtilitiesPaymentFragment()
            electricityPaymentFragment.arguments = bundle
            return electricityPaymentFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dialogUtilitiesBinding = DialogBinding.inflate(inflater, null, false)
            .apply {
                tvYes.text = getString(R.string.lbl_continue)
                responseIcon.visibility = View.VISIBLE
                tvNo.visibility = View.GONE
            }
        val alertDialogBuilder = AlertDialog.Builder(context!!)
            .apply {
                setView(dialogUtilitiesBinding.root)
                setCancelable(false)
            }


        val layoutVerifyUtilityPaymentBinding =
            LayoutVerifyUtilityPaymentBinding.inflate(inflater, null, false)
        layoutVerifyUtilityPaymentBinding.lifecycleOwner = viewLifecycleOwner
        layoutVerifyUtilityPaymentBinding.executePendingBindings()
        verifyBillDialog = BottomSheetDialog(context!!, R.style.SheetDialog)
            .apply { setContentView(layoutVerifyUtilityPaymentBinding.root) }
        layoutVerifyUtilityPaymentBinding.proceed.setOnClickListener {
            verifyBillDialog!!.dismiss()
            viewModel.payBill()
        }

        alertDialog = alertDialogBuilder.create()
        viewModel = ViewModelProvider(this).get(UtilitiesViewModel::class.java)
        val stormUtilitiesApiService =
            StormUtilitiesApiClient.getStormUtilitiesApiClientInstance(context!!)
        StormAPIClient.create(context)
        viewModel.setStormUtilitiesApiService(stormUtilitiesApiService)
        val token = SharedPrefManager.getUserToken()
        val stormId = SharedPrefManager.getUser().netplus_id
        viewModel.setWalletCheckObservable(StormAPIClient.getWalletBalance(stormId))
        viewModel.message.observe(
            viewLifecycleOwner,
            Observer { event ->
                event.getContentIfNotHandled()?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            })
        viewModel.validateBillResponse.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                dismissProgressBar()
                layoutVerifyUtilityPaymentBinding.billResponse = it
                verifyBillDialog!!.show()
            }
        })
        viewModel.showProgress.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                if (it)
                    showProgressBar()
                else dismissProgressBar()
            }
        })
        viewModel.result.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                dismissProgressBar()
                when (it) {
                    is ErrorNetworkResponse -> {
                        dialogUtilitiesBinding.responseIcon.setImageResource(R.drawable.ic_error)
                        dialogUtilitiesBinding.tvMessage.text = it.message
                        dialogUtilitiesBinding.tvTitle.text = getString(R.string.error)
                        dialogUtilitiesBinding.tvYes.setOnClickListener {
                            alertDialog?.run {
                                cancel()
                            }
                        }
                    }
                    is SuccessNetworkResponse -> {
                        (activity as HomeActivity).getWalletBalance()
                        dialogUtilitiesBinding.responseIcon.setImageResource(R.drawable.animated_check)
                        (dialogUtilitiesBinding.responseIcon.drawable as Animatable).start()
                        dialogUtilitiesBinding.tvMessage.text = it.message
                        dialogUtilitiesBinding.tvTitle.text = getString(R.string.success)
                        val data = it.data
                        if (!data!!.token.isNullOrEmpty()) {
                            dialogUtilitiesBinding.copy.visibility = View.VISIBLE
                            dialogUtilitiesBinding.copy.setOnClickListener {
                                copyTextToClipboard(
                                    context!!,
                                    "token",
                                    data.token!!
                                )
                                Toast.makeText(context!!, "Token copied to clipboard", Toast.LENGTH_SHORT).show()
                            }
                            copyTextToClipboard(context!!, "token", data.token!!)
                        }
                        dialogUtilitiesBinding.tvYes.setOnClickListener {
                            alertDialog?.run {
                                cancel()
                            }
                            activity!!.onBackPressed()
                        }
                    }
                }
                alertDialog!!.show()
            }
        })
        return when (arguments?.getInt("service_id")) {
            0 -> getCableTvView(inflater, container)
            1 -> getElectricityPageView(inflater, container)
            2 -> getAirtimePageView(inflater, container)
            else -> getInternetPageView(inflater, container)
        }
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

    private fun getElectricityPageView(inflater: LayoutInflater, container: ViewGroup?): View {
        viewModel.setUtilityService("pay_bill")
        viewModel.setUtilityBillType("POWER")
        viewModel.setUtilityServiceType("electricity")
        val powerCategorySpinnerAdapter =
            ServicesSpinnerAdapter(requireActivity().baseContext, listOf("Electricity"))
        val electricityBillers = DataGenerator.electricityBillersList()
        val productsSpinnerAdapter =
            ServicesSpinnerAdapter(
                context!!,
                electricityBillers.map { it.billerName },
                electricityBillers.map { it.imageUrl })
        val meterTypeSpinnerAdapter =
            ServicesSpinnerAdapter(context!!, listOf("prepaid", "postpaid"))
        binding = LayoutPowerOrElectricityBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.meterType.adapter = meterTypeSpinnerAdapter
        binding.productsSpinner.adapter = productsSpinnerAdapter
        binding.selectACategory.adapter = powerCategorySpinnerAdapter

        var selected = 0
        binding.productsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selected = position
                    binding.meterType.setSelection(0)
                    viewModel.setUtilityProvider(electricityBillers[selected].prepaidCode)

                }
            }
        binding.meterType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        viewModel.setUtilityProvider(electricityBillers[selected].prepaidCode)
                        viewModel.setUtilityPackage("Prepaid")
                    }
                    1 -> {
                        viewModel.setUtilityProvider(electricityBillers[selected].postpaidCode)
                        viewModel.setUtilityPackage("Postpaid")
                    }
                }
            }
        }
        return binding.root
    }

    private fun getAirtimePageView(inflater: LayoutInflater, container: ViewGroup?): View {
        viewModel.setUtilityService("vtu")
        viewModel.setUtilityPackage("Airtime")
        airtimeOrDataBinding = LayoutAirtimeOrDataBinding.inflate(inflater, container, false)
        airtimeOrDataBinding.viewmodel = viewModel
        airtimeOrDataBinding.lifecycleOwner = viewLifecycleOwner
        val dataOrAirtimeSpinnerAdapter =
            ServicesSpinnerAdapter(context!!, listOf("Airtime", "Mobile Data"))
        val mobileOperatorsBillers = DataGenerator.generateBillers().filter {
            it.service_type == "Airtime"
        }
        val selectNetworkSpinnerAdapter = ServicesSpinnerAdapter(
            context!!,
            mobileOperatorsBillers.map { it.biller_code },
            mobileOperatorsBillers.map { it.imageUrl }
        )
        val dataPlanList = DataGenerator.getServiceProviderPlans(context)

        airtimeOrDataBinding.dataOrAirtimeSpinner.adapter = dataOrAirtimeSpinnerAdapter
        airtimeOrDataBinding.selectNetworkSpinner.adapter = selectNetworkSpinnerAdapter
        airtimeOrDataBinding.dataOrAirtimeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val visibility = when (position) {
                        0 -> {
                            airtimeOrDataBinding.priceTextbox.isEnabled = true
                            airtimeOrDataBinding.currencyTextbox.setBackgroundResource(R.drawable.services_edittext_background)
                            airtimeOrDataBinding.priceTextbox.setBackgroundResource(R.drawable.services_edittext_background)
                            View.GONE
                        }
                        1 -> {
                            airtimeOrDataBinding.priceTextbox.isEnabled = false
                            airtimeOrDataBinding.currencyTextbox.setBackgroundResource(R.drawable.services_edittext_background_disabled)
                            airtimeOrDataBinding.priceTextbox.setBackgroundResource(R.drawable.services_edittext_background_disabled)
                            View.VISIBLE
                        }
                        else -> View.GONE
                    }
                    airtimeOrDataBinding.selectDataBundleSpinner.visibility = visibility
                    airtimeOrDataBinding.selectDataBundleSpinnerIcon.visibility = visibility
                }

            }
        var selectedNetwork = 0
        airtimeOrDataBinding.selectNetworkSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedNetwork = position
                    val dataBundleTypeSpinnerAdapter =
                        ServicesSpinnerAdapter(
                            context!!,
                            dataPlanList[position].map { "${it.data} - ${it.duration}" })
                    airtimeOrDataBinding.selectDataBundleSpinner.adapter =
                        dataBundleTypeSpinnerAdapter
                    viewModel.setUtilityProvider(mobileOperatorsBillers[position].biller_code)

                }

            }
        airtimeOrDataBinding.selectDataBundleSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.setUtilityPackage("${dataPlanList[selectedNetwork][position].data} - ${dataPlanList[selectedNetwork][position].duration}")
                    airtimeOrDataBinding.priceTextbox.setText(dataPlanList[selectedNetwork][position].price)
                }

            }

        return airtimeOrDataBinding.root
    }

    private fun getInternetPageView(inflater: LayoutInflater, container: ViewGroup?): View {
        viewModel.setUtilityService("pay_bill")
        internetSubscriptionBinding =
            LayoutInternetSubscriptionBinding.inflate(inflater, container, false)
        internetSubscriptionBinding.viewmodel = viewModel
        internetSubscriptionBinding.lifecycleOwner = viewLifecycleOwner
        internetSubscriptionBinding.priceTextbox.isEnabled = false
        val internetBiller = DataGenerator.generateBillers().filter {
            it.service_type == "Internet" && it.status == "active"
        }
        val internetProviderSpinnerAdapter =
            ServicesSpinnerAdapter(
                context!!,
                internetBiller.map { it.biller_name },
                internetBiller.map { it.imageUrl })

        internetSubscriptionBinding.providerSpinner.adapter = internetProviderSpinnerAdapter
        val billerPlans = DataGenerator.getBillerPlans(context!!)
        val smileInternetList = billerPlans.smileInternetList
        val spectranetInternetList = billerPlans.spectranetInternetList
        var selected = 0
        internetSubscriptionBinding.providerSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selected = position
                    val packageSpinnerAdapter =
                        when (position) {
                            0 -> {
                                viewModel.setUtilityProvider("SMILE")
                                ServicesSpinnerAdapter(
                                    context!!,
                                    smileInternetList
                                        .map { it.bundleName })
                            }
                            1 -> {
                                viewModel.setUtilityProvider("SPECTRANET")
                                ServicesSpinnerAdapter(
                                    context!!,
                                    spectranetInternetList.map { it.planName })
                            }
                            else -> ServicesSpinnerAdapter(context!!, ArrayList())
                        }
                    internetSubscriptionBinding.packageSpinner.adapter = packageSpinnerAdapter
                }

            }
        internetSubscriptionBinding.packageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val priceToPay = when (selected) {
                        0 -> {
                            viewModel.setUtilityPackage(smileInternetList[position].bundleName)
                            smileInternetList[position].price.replace("N", "")
                                .replace(",", "")
                        }
                        1 -> {
                            viewModel.setUtilityPackage(spectranetInternetList[position].planName)
                            spectranetInternetList[position].price.replace(",", "")
                        }
                        else -> "0"
                    }
                    internetSubscriptionBinding.priceTextbox.setText(priceToPay)
                }

            }
        return internetSubscriptionBinding.root
    }

    private fun getCableTvView(inflater: LayoutInflater, container: ViewGroup?): View {
        viewModel.setUtilityService("pay_bill")
        viewModel.setUtilityBillType("TV")
        viewModel.setUtilityServiceType("cable_tv")
        cableBinding = LayoutCableTvBinding.inflate(inflater, container, false)
        cableBinding.viewmodel = viewModel
        cableBinding.lifecycleOwner = viewLifecycleOwner
        val cableTvBiller = DataGenerator.generateBillers()
            .filter { it.service_type == "TV" && it.status == "active" }
        val cableTvCategorySpinnerAdapter =
            ServicesSpinnerAdapter(context!!, cableTvBiller.map { it.biller_name }
                , cableTvBiller.map { it.imageUrl })

        val plans: Biller.BillerPlans = DataGenerator.getBillerPlans(context)
        val dstvPlans = plans.multichoicePlans.filter { !it.productCode.startsWith("GO") }
        val gotvPlans = plans.multichoicePlans.filter { it.productCode.startsWith("GO") }
        val starTimesPlan = plans.startTimesPlans
        var selectedCategory = 0
        cableBinding.selectCableTvCategory.adapter = cableTvCategorySpinnerAdapter
        cableBinding.selectCableTvCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedCategory = position
                    val tvPackageAdapter = when (position) {
                        0 -> {
                            viewModel.setUtilityProvider("DSTV")
                            ServicesSpinnerAdapter(context!!, dstvPlans.map { it.product })
                        }
                        1 -> {
                            viewModel.setUtilityProvider("GOTV")
                            ServicesSpinnerAdapter(context!!, gotvPlans.map { it.product })
                        }
                        2 -> {
                            viewModel.setUtilityProvider("STARTIMES")
                            ServicesSpinnerAdapter(context!!, starTimesPlan.map { it.plan })
                        }
                        else -> ServicesSpinnerAdapter(context!!, ArrayList())
                    }
                    cableBinding.selectCableTvPackage.adapter = tvPackageAdapter
                }
            }
        cableBinding.selectCableTvPackage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    var feeToPay = when (selectedCategory) {
                        0 -> {
                            viewModel.setUtilityPackage(dstvPlans[position].product)
                            cableBinding.planValidityOptions.visibility = View.GONE
                            dstvPlans[position].newPrice
                        }
                        1 -> {
                            viewModel.setUtilityPackage(gotvPlans[position].product)
                            cableBinding.planValidityOptions.visibility = View.GONE
                            gotvPlans[position].newPrice
                        }
                        2 -> {
                            cableBinding.planValidityOptions.visibility = View.VISIBLE
                            cableBinding.planValidityOptions.check(-1)
                            0
                        }
                        else -> 0
                    }
                    cableBinding.planValidityOptions.setOnCheckedChangeListener { _, checked ->
                        val starTimesPrice = when (checked) {
                            R.id.daily -> {
                                viewModel.setUtilityPackage("${starTimesPlan[position].plan} - Daily")
                                starTimesPlan[position].dailyFee
                            }
                            R.id.weekly -> {
                                viewModel.setUtilityPackage("${starTimesPlan[position].plan} - Weekly")
                                starTimesPlan[position].weeklyFee
                            }
                            R.id.monthly -> {
                                viewModel.setUtilityPackage("${starTimesPlan[position].plan} - Monthly")
                                starTimesPlan[position].monthlyFee
                            }
                            else -> 0
                        }

                        feeToPay = starTimesPrice
                        cableBinding.priceTextbox.setText(feeToPay.toString())
                    }
                    cableBinding.priceTextbox.setText(feeToPay.toString())
                }
            }
        return cableBinding.root
    }
}