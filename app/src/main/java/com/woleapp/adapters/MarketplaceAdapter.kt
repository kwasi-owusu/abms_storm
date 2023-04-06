package com.woleapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woleapp.databinding.LayoutMarketplaceDealerItemBinding
import com.woleapp.model.Marketplace
import com.woleapp.util.loadImageWithGlide
import com.woleapp.util.setDecodedImageToImageView

typealias MarketPlaceListener = (Marketplace) -> Unit

object MarketplaceItemDiffUtil : DiffUtil.ItemCallback<Marketplace>() {
    override fun areItemsTheSame(oldItem: Marketplace, newItem: Marketplace): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Marketplace, newItem: Marketplace): Boolean {
        return oldItem.id == newItem.id
    }

}

class MarketplaceAdapter(val listener: MarketPlaceListener) :
    ListAdapter<Marketplace, MarketplaceViewHolder>(MarketplaceItemDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketplaceViewHolder =
        MarketplaceViewHolder.from(parent)

    override fun onBindViewHolder(holder: MarketplaceViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.root.setOnClickListener { listener.invoke(getItem(position)) }
    }
}

class MarketplaceViewHolder private constructor(val binding: LayoutMarketplaceDealerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup) = MarketplaceViewHolder(
            LayoutMarketplaceDealerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    fun bind(marketplace: Marketplace) {
        binding.apply {
            executePendingBindings()
            this.marketplace = marketplace
        }
        //val logo = "http://images.storm.netpluspay.com/logo.jpeg"
        if (URLUtil.isValidUrl(marketplace.logo))
            loadImageWithGlide(marketplace.logo, binding.dealerLogo)
        else
            setDecodedImageToImageView(marketplace.logo, binding.dealerLogo)
    }

}