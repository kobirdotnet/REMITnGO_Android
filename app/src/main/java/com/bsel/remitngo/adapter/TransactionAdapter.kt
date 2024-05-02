package com.bsel.remitngo.adapter

import android.util.Log
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
            if (transactions.transactionCode!!.contains(query, ignoreCase = true)) {
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
        binding.benName.text = transactionItem.beneName.toString()
        binding.orderType.text = transactionItem.orderTypeName.toString()
        binding.transactionCode.text = transactionItem.transactionCode.toString()
        binding.transactionDate.text = transactionItem.transactionDateTime12hr.toString()

        binding.bankName.text = transactionItem.beneBankName.toString()
        binding.accountNo.text = transactionItem.beneAccountNo.toString()
        val benAmount = transactionItem.beneAmount.toString()
        binding.benAmount.text = "BDT $benAmount"

        val paymentMode = transactionItem.paymentMode.toString()
        Log.i("info", "paymentMode: $paymentMode")

        val transactionCode = transactionItem.transactionCode.toString()
        Log.i("info", "transactionCode: $transactionCode")

        val paymentStatus = transactionItem.paymentStatus.toString()
        Log.i("info", "paymentStatus: $paymentStatus")

        val orderStatus = transactionItem.orderStatus.toString().toInt()
        Log.i("info", "orderStatus: $orderStatus")

        if (orderStatus == 7 || orderStatus == 8 || orderStatus == 10 || orderStatus == 11) {
            binding.cancelStatus.visibility = View.VISIBLE
            binding.successStatus.visibility = View.GONE
        } else {
            binding.cancelStatus.visibility = View.GONE
            binding.successStatus.visibility = View.VISIBLE
        }

        if(orderStatus == 21){
            binding.btnDownloadReceipt.text = "Pay Now"
        }else{
            binding.btnDownloadReceipt.text = "Download Receipt"
        }

        when (orderStatus) {
            1 -> {
                binding.imgPaymentReceived.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgOnTheWay.setBackgroundResource(R.drawable.circle_background_white)
                binding.imgSendToBank.setBackgroundResource(R.drawable.circle_background_white)
                binding.imgPaidToBeneficiary.setBackgroundResource(R.drawable.circle_background_white)
            }
            2, 3, 4 -> {
                binding.imgPaymentReceived.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgOnTheWay.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgSendToBank.setBackgroundResource(R.drawable.circle_background_white)
                binding.imgPaidToBeneficiary.setBackgroundResource(R.drawable.circle_background_white)
            }
            5 -> {
                binding.imgPaymentReceived.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgOnTheWay.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgSendToBank.setBackgroundResource(R.drawable.circle_background_green)
                binding.imgPaidToBeneficiary.setBackgroundResource(R.drawable.circle_background_white)
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