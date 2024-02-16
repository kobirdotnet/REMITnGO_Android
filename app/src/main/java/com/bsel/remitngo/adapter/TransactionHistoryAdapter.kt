package com.bsel.remitngo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemTransactionHistoryBinding
import com.bsel.remitngo.model.TransactionItem

class TransactionHistoryAdapter(
    private val selectedItem: (TransactionItem) -> Unit
) : RecyclerView.Adapter<TransactionHistoryViewHolder>() {

    private val transactionList = ArrayList<TransactionItem>()
    private var filteredTransactionList = ArrayList<TransactionItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTransactionHistoryBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_transaction_history, parent, false)
        return TransactionHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredTransactionList.size
    }

    override fun onBindViewHolder(holder: TransactionHistoryViewHolder, position: Int) {
        holder.bind(filteredTransactionList[position], selectedItem)
    }

    fun setList(transactionItem: List<TransactionItem>) {
        transactionList.clear()
        transactionList.addAll(transactionItem)
        filter("")
    }

    fun filter(query: String) {
        filteredTransactionList.clear()
        for (transactions in transactionList) {
            if (transactions.transactionName!!.contains(query, ignoreCase = true)) {
                filteredTransactionList.add(transactions)
            }
        }
        notifyDataSetChanged()
    }

}

class TransactionHistoryViewHolder(val binding: ItemTransactionHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        transactionItem: TransactionItem,
        selectedItem: (TransactionItem) -> Unit
    ) {
        binding.benName.text = transactionItem.transactionName
        binding.itemTransactionHistoryLayout.setOnClickListener {
            selectedItem(transactionItem)
        }
    }

}