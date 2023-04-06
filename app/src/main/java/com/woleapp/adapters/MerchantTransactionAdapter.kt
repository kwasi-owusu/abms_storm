package com.woleapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woleapp.databinding.LayoutMerchantTransactionListItemBinding
import com.woleapp.model.MerchantTransaction

typealias MerchantTransactionListener = (transaction: MerchantTransaction) -> Unit

class MerchantTransactionAdapter :
    ListAdapter<MerchantTransaction, MerchantTransactionViewHolder>(TransactionItemCallBack) {
    private lateinit var transactionListener: MerchantTransactionListener

    fun setTransactionListener(listener: MerchantTransactionListener) {
        this.transactionListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MerchantTransactionViewHolder {
        return MerchantTransactionViewHolder.from(parent, transactionListener)
    }

    override fun onBindViewHolder(holder: MerchantTransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

object TransactionItemCallBack : DiffUtil.ItemCallback<MerchantTransaction>() {
    override fun areItemsTheSame(
        oldItem: MerchantTransaction,
        newItem: MerchantTransaction
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: MerchantTransaction,
        newItem: MerchantTransaction
    ): Boolean {
        return oldItem.id == newItem.id
    }
}

class MerchantTransactionViewHolder private constructor(
    private val binding: LayoutMerchantTransactionListItemBinding,
    private val merchantTransactionListener: MerchantTransactionListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(transaction: MerchantTransaction) {
        binding.sn.text = adapterPosition.plus(1).toString()
        binding.transaction = transaction
        binding.root.setOnClickListener { merchantTransactionListener.invoke(transaction) }
        binding.executePendingBindings()
    }

    companion object {
        fun from(
            parent: ViewGroup,
            transactionListener: MerchantTransactionListener
        ): MerchantTransactionViewHolder {
            return MerchantTransactionViewHolder(
                LayoutMerchantTransactionListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                transactionListener
            )
        }
    }
}