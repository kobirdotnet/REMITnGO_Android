package com.bsel.remitngo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.transaction.TransactionData
import com.bsel.remitngo.databinding.ItemTransactionBinding

class TransactionAdapter(
    private val selectedItem: (TransactionData) -> Unit,
    private val downloadReceipt: (TransactionData) -> Unit,
    private val sendAgain: (TransactionData) -> Unit
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
        holder.bind(filteredTransactionList[position], selectedItem,downloadReceipt,sendAgain)
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
        selectedItem: (TransactionData) -> Unit,
        downloadReceipt: (TransactionData) -> Unit,
        sendAgain: (TransactionData) -> Unit
    ) {
        binding.benName.text = transactionItem.benName.toString()
        binding.orderType.text = transactionItem.orderTypeName.toString()
        binding.transactionCode.text = transactionItem.transactionCode.toString()
        binding.transactionDate.text = transactionItem.transactionDateTime12hr.toString()

        binding.bankName.text = transactionItem.bankName.toString()
        binding.accountNo.text = transactionItem.accountNo.toString()
        val benAmount = transactionItem.benAmount.toString()
        binding.benAmount.text = "BDT $benAmount"

        val orderStatus = transactionItem.orderStatus.toString()

        if (orderStatus == "7" || orderStatus == "8" || orderStatus == "10" || orderStatus == "11") {
            binding.cancelStatus.visibility = View.VISIBLE
            binding.successStatus.visibility = View.GONE
        } else {
            binding.cancelStatus.visibility = View.GONE
            binding.successStatus.visibility = View.VISIBLE

            if (orderStatus == "1") {
                binding.imgPaymentReceived.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgOnTheWay.setBackgroundResource(R.drawable.circle_background_white)
                binding.imgSendToBank.setBackgroundResource(R.drawable.circle_background_white)
                binding.imgPaidToBeneficiary.setBackgroundResource(R.drawable.circle_background_white)
            } else if (orderStatus == "2" || orderStatus == "3" || orderStatus == "4") {
                binding.imgPaymentReceived.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgOnTheWay.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgSendToBank.setBackgroundResource(R.drawable.circle_background_white)
                binding.imgPaidToBeneficiary.setBackgroundResource(R.drawable.circle_background_white)
            } else if (orderStatus == "5") {
                binding.imgPaymentReceived.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgOnTheWay.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgSendToBank.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgPaidToBeneficiary.setBackgroundResource(R.drawable.circle_background_white)
            } else if (orderStatus == "5") {
                binding.imgPaymentReceived.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgOnTheWay.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgSendToBank.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgPaidToBeneficiary.setBackgroundResource(R.drawable.circle_background_green)
            }
        }

        binding.itemTransactionLayout.setOnClickListener {
            selectedItem(transactionItem)
        }

        binding.btnDownloadReceipt.setOnClickListener {
            downloadReceipt(transactionItem)
        }

        binding.btnSendAgain.setOnClickListener {
            sendAgain(transactionItem)
        }
    }

}