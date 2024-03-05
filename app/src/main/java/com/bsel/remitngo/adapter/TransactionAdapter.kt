package com.bsel.remitngo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.transaction.TransactionData
import com.bsel.remitngo.databinding.ItemTransactionBinding

class TransactionAdapter(
    private val selectedItem: (TransactionData) -> Unit
) : RecyclerView.Adapter<TransactionViewHolder>() {

    private val transactionList = ArrayList<TransactionData>()
    private var filteredTransactionList = ArrayList<TransactionData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTransactionBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_transaction, parent, false)
        return TransactionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredTransactionList.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(filteredTransactionList[position], selectedItem)
    }

    fun setList(transactionItem: List<TransactionData>) {
        transactionList.clear()
        transactionList.addAll(transactionItem)
        filter("")
    }

    fun filter(query: String) {
        filteredTransactionList.clear()
        for (transactions in transactionList) {
            if (transactions.benName!!.contains(query, ignoreCase = true)) {
                filteredTransactionList.add(transactions)
            }
        }
        notifyDataSetChanged()
    }

}

class TransactionViewHolder(val binding: ItemTransactionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        transactionItem: TransactionData,
        selectedItem: (TransactionData) -> Unit
    ) {
        binding.benName.text = transactionItem.benName.toString()
        binding.orderType.text = transactionItem.orderTypeName.toString()
        binding.transactionCode.text = transactionItem.transactionCode.toString()
        binding.transactionDate.text = transactionItem.transactionDateTime12hr.toString()

        binding.bankName.text = transactionItem.bankName.toString()
        binding.accountNo.text = transactionItem.accountNo.toString()
        binding.currencyToAmount.text = transactionItem.currencyToAmount.toString()

        binding.itemTransactionLayout.setOnClickListener {
            selectedItem(transactionItem)
        }
    }

}