package com.woleapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import com.woleapp.R
import com.woleapp.databinding.LayoutMarketplaceItemDescriptionBinding
import com.woleapp.model.Inventory
import com.woleapp.util.*
import org.parceler.Parcels

const val INVENTORY_ITEM = "inventory_item"

class MarketplaceProductDetailsFragment : BaseMarketplaceFragment() {

    lateinit var inventory: Inventory
    private lateinit var binding: LayoutMarketplaceItemDescriptionBinding

    companion object {
        fun newInstance(inventory: Inventory) = MarketplaceProductDetailsFragment().apply {
            arguments = Bundle()
                .apply { putParcelable(INVENTORY_ITEM, Parcels.wrap(inventory)) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            LayoutMarketplaceItemDescriptionBinding.inflate(inflater, container, false)
                .apply {
                    executePendingBindings()
                    lifecycleOwner = viewLifecycleOwner
                }
        if (arguments == null || !arguments!!.containsKey(INVENTORY_ITEM))
            requireActivity().onBackPressed()
        inventory = Parcels.unwrap(arguments!!.getParcelable(INVENTORY_ITEM))
        binding.inventory = inventory
        binding.deliveryFee.text = getString(R.string.delivery_fee_template, inventory.deliveryFee)
        binding.amount.text = getString(R.string.amount_template, inventory.price)
        if (URLUtil.isValidUrl(inventory.image_path))
            loadImageWithGlide(inventory.image_path, binding.productImage)
        else
            setDecodedImageToImageView(inventory.image_path, binding.productImage)
        if (inventory.merchantId == SharedPrefManager.getUser().netplus_id) {
            binding.editButton.visibility = View.VISIBLE
            binding.addToCart.text = getString(R.string.sell_item)
            binding.addToCart.setOnClickListener {
                val frag = QuickTransactionFragment()
                val b = Bundle()
                b.putParcelable("inventory", Parcels.wrap(inventory))
                frag.arguments = b
                addFragmentWithoutRemove(
                    R.id.container_main,
                    frag,
                    QuickTransactionFragment::class.java.simpleName
                )
            }
        } else
            binding.addToCart.setOnClickListener {
                goToPayment()
            }
        binding.editButton.setOnClickListener {
            addFragmentWithoutRemove(
                R.id.container_main,
                AddInventoryFragment.NewInstance(inventory),
                AddInventoryFragment::class.java.simpleName
            )
        }
        binding.quantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (it.toString().toIntOrNull() ?: 1 > inventory.quantity){
                        binding.quantity.setText(inventory.quantity.toString())
                    }
                    if (s.isNullOrEmpty())
                        binding.quantity.setText("1")

                    if (it.toString().toIntOrNull()?:0 <= 0)
                        binding.quantity.setText(1.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.increaseButton.setOnClickListener {
            binding.quantity.setText(((binding.quantity.text.toString().toIntOrNull() ?: 0) + 1).toString())
        }

        binding.decreaseButton.setOnClickListener {
            binding.quantity.setText(((binding.quantity.text.toString().toIntOrNull() ?: 0) - 1).toString())
        }

        return binding.root
    }

    private fun goToPayment() {
        val amount: String = inventory.price
        val qty = binding.quantity.text.toString().toIntOrNull() ?: 1
        val customerName: String = SharedPrefManager.getUser().name
        val totalAmt = amount.toDouble() * qty.toDouble() + inventory.deliveryFee
        val b1 = Bundle()
        b1.putString("amount", Utilities(requireContext()).getFormattedAmount(totalAmt))

        b1.putInt("transaction_type", Constants.TRANSACTION_QUICK)
        b1.putString("COMMODITY_NAME", "sale")
        b1.putString("DESCRIPTION", "merchant sale")
        b1.putString("CUSTOMER_NAME", customerName)
        b1.putString("QTY", qty.toString())
        b1.putString("SELLER_ID", inventory.merchantId)
        b1.putString("SELLER_NAME", inventory.storeName)
        b1.putBoolean("external", true)

        if (inventory.productId != null) b1.putInt("PRODUCTID", inventory.productId.toInt())

        b1.putString("PRODUCTNAME", inventory.product_name)
        b1.putInt("QUANTITY", Integer.valueOf(qty))

        val paymentModeFragment = PaymentModeFragment()
        paymentModeFragment.arguments = b1

        addFragmentWithoutRemove(
            R.id.container_main, paymentModeFragment,
            PaymentModeFragment::class.java.simpleName
        )
    }
}