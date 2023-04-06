package com.woleapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woleapp.R
import com.woleapp.databinding.LayoutMarketplaceItemBinding
import com.woleapp.model.Inventory
import com.woleapp.util.loadImageWithGlide
import com.woleapp.util.setDecodedImageToImageView

typealias ProductClickListener = (Inventory) -> Unit

object MarketplaceProductItemDiffUtil : DiffUtil.ItemCallback<Inventory>() {
    override fun areItemsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
        return oldItem.productId == newItem.productId
    }

}

class MarketplaceProductAdapter(val productClickListener: ProductClickListener) :
    ListAdapter<Inventory, MarketplaceProductViewHolder>(MarketplaceProductItemDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarketplaceProductViewHolder =
        MarketplaceProductViewHolder.from(parent)

    override fun onBindViewHolder(holder: MarketplaceProductViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.view.setOnClickListener { productClickListener.invoke(getItem(position)) }
    }
}

class MarketplaceProductViewHolder private constructor(val binding: LayoutMarketplaceItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup) = MarketplaceProductViewHolder(
            LayoutMarketplaceItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    fun bind(inventory: Inventory) {
        binding.apply {
            executePendingBindings()
            this.inventory = inventory
            this.amount.text = root.context.getString(R.string.amount_template, inventory.price)
            if (URLUtil.isValidUrl(inventory.image_path))
                loadImageWithGlide(inventory.image_path, binding.productImage)
            else
                setDecodedImageToImageView(inventory.image_path, binding.productImage)
        }
    }

}
