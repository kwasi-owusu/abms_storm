package com.woleapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woleapp.R
import com.woleapp.databinding.LayoutMarketplaceReceivedOrdersBinding
import com.woleapp.model.SalesOrder
import com.woleapp.util.loadImageWithGlide
import com.woleapp.util.setDecodedImageToImageView
import timber.log.Timber

typealias MarketplaceOrderListener = (order: SalesOrder) -> Unit

object OrderItemDiffUtil : DiffUtil.ItemCallback<SalesOrder>() {
    override fun areItemsTheSame(oldItem: SalesOrder, newItem: SalesOrder): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SalesOrder, newItem: SalesOrder): Boolean {
        return oldItem.id == newItem.id
    }

}

class MarketplaceOrderAdapter(private val mode: Int, val listener: MarketplaceOrderListener) :
    ListAdapter<SalesOrder, MarketplaceOrderViewHolder>(OrderItemDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketplaceOrderViewHolder {
        return MarketplaceOrderViewHolder.from(parent, mode, listener)
    }

    override fun onBindViewHolder(holder: MarketplaceOrderViewHolder, position: Int) {
        getItem(position)?.let { order ->
            holder.bind(order)
            holder.binding.updateStatus.setOnClickListener {
                listener.invoke(order)
            }
        }
    }
}

class MarketplaceOrderViewHolder(
    val binding: LayoutMarketplaceReceivedOrdersBinding,
    private val mode: Int,
    val listener: MarketplaceOrderListener
) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(
            parent: ViewGroup,
            mode: Int,
            listener: MarketplaceOrderListener
        ): MarketplaceOrderViewHolder =
            MarketplaceOrderViewHolder(
                LayoutMarketplaceReceivedOrdersBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), mode, listener
            )
    }

    fun bind(order: SalesOrder) {
        if (mode != 1) {
            binding.apply {
                updateProgress.visibility = View.GONE
                updateStatus.visibility = View.GONE
                binding.deliveryStatus.text = saleOrder?.deliveryStatus
                binding.soldBy.text =
                    root.context.getString(R.string.sold_by, order.storeName ?: "Seller")
            }
        } else {
            binding.soldBy.visibility = View.GONE
            binding.executePendingBindings()
            binding.deliveryStatus.visibility = View.GONE
            binding.updateProgress.check(
                if (order.deliveryStatus.isNullOrEmpty() || order.deliveryStatus.equals(
                        "pending",
                        ignoreCase = true
                    )
                ) R.id.pending else
                    R.id.shipped
            )
            binding.updateProgress.setOnCheckedChangeListener { _, checkedId ->
                Timber.e("Checked: $checkedId ${if (checkedId == R.id.shipped) "Shipped" else "Pending"}")
                order.deliveryStatus = if (checkedId == R.id.shipped) "Shipped" else "Pending"
            }
            binding.updateStatus.setOnClickListener {
                listener.invoke(order)
            }
        }
        binding.saleOrder = order
        order.productDetails?.let {
            binding.productName.text = it.product_name
            binding.productDescription.text = it.description
        }
        binding.productAmount.text =
            binding.root.context.getString(R.string.amount_template, order.totalCost)
        binding.deliveryStatus.text =
            if (order.deliveryStatus.isNullOrEmpty()) "Pending" else order.deliveryStatus

        val imageUrl = order.productDetails?.image_path ?: ""
        if (URLUtil.isValidUrl(imageUrl))
            loadImageWithGlide(imageUrl, binding.productImage)
        else
            setDecodedImageToImageView(imageUrl, binding.productImage)
    }

}