package com.woleapp.adapters

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.woleapp.R
import com.woleapp.model.EBiller
import com.woleapp.util.decodeBase64ToBitmap
import timber.log.Timber
typealias EbillsListener = (biller: EBiller) -> Unit

class EBillsAdapter(private val billerList: MutableList<EBiller>, val billerListener: EbillsListener) : BaseAdapter() {

    fun setItems(list: List<EBiller>){
        billerList.addAll(list)
        notifyDataSetChanged()
    }


    override fun getCount(): Int = billerList.size

    override fun getItem(position: Int) = billerList[position]


    override fun getItemId(position: Int) = billerList[position].billerId.toLongOrNull() ?: 0


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(
                R.layout.item_list_dashboard,
                parent,
                false
            )
        }
        val biller = billerList[position]
        val ivService = view!!.findViewById<ImageView>(R.id.ivService).apply {
            if (biller.billerImage.startsWith("data:image/png;base64,")){
                val bitmap: Bitmap? = try {
                    decodeBase64ToBitmap(biller.billerImage.split("data:image/png;base64,")[1])
                }catch (e: Exception){
                    null
                }
                bitmap?.let {
                    setImageBitmap(it)
                }
            }
            else
                Glide.with(this.context)
                    .load(biller.billerImage)
                    .into(this)
        }
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle).apply {
            this.text = biller.biller
        }
        val tvServiceProviders = view.findViewById<TextView>(R.id.tvServiceProviders)
        val cardItem = view.findViewById<CardView>(R.id.cardItem)
        cardItem.setOnClickListener {
            billerListener.invoke(biller)
        }
        return view
    }
}