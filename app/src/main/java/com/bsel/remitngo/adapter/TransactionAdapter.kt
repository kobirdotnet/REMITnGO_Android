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
        holder.bind(filteredTransactionList[position], selectedItem, downloadReceipt, sendAgain)
    }

    fun setList(transactionItem: List<TransactionData>) {
        transactionList.clear()
        transactionList.addAll(transactionItem)
        transactionFilter("")
    }

    fun transactionFilter(query: String) {
        filteredTransactionList.clear()
        for (transactions in transactionList) {
            if (transactions.transactionCode != null) {
                if (transactions.transactionCode.contains(query, ignoreCase = true)) {
                    filteredTransactionList.add(transactions)
                }
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
        if (transactionItem.beneName.toString() != null) {
            binding.beneName.text = transactionItem.beneName.toString()
        }
        if (transactionItem.orderTypeName.toString() != null) {
            binding.orderType.text = transactionItem.orderTypeName.toString()
        }
        if (transactionItem.transactionCode.toString() != null) {
            binding.transactionCode.text = transactionItem.transactionCode.toString()
        }
        if (transactionItem.transactionDateTime12hr.toString() != null) {
            binding.transactionDate.text = transactionItem.transactionDateTime12hr.toString()
        }

        if (transactionItem.beneWalletId == 0) {
            if (transactionItem.beneBankName.toString() != null) {
                binding.bankName.text = transactionItem.beneBankName.toString()
            }
            if (transactionItem.beneAccountNo.toString() != null) {
                binding.accountNo.text = transactionItem.beneAccountNo.toString()
            }
        } else {
            if (transactionItem.walletName.toString() != null) {
                binding.bankName.text = transactionItem.walletName.toString()
            }
            if (transactionItem.beneWalletNo.toString() != null) {
                binding.accountNo.text = transactionItem.beneWalletNo.toString()
            }
        }

        if (transactionItem.beneAmount.toString() != null) {
            val beneAmount = transactionItem.beneAmount.toString()
            binding.beneAmount.text = "BDT $beneAmount"
        }

        if (transactionItem.orderStatus.toString() != null) {
            val orderStatus = transactionItem.orderStatus.toString().toInt()
            if (orderStatus == 7 || orderStatus == 8 || orderStatus == 10 || orderStatus == 11) {
                binding.cancelStatus.visibility = View.VISIBLE
                binding.successStatus.visibility = View.GONE
            } else {
                binding.cancelStatus.visibility = View.GONE
                binding.successStatus.visibility = View.VISIBLE
            }

            if (orderStatus == 21) {
                binding.btnDownloadReceipt.text = "Pay Now"
            } else {
                binding.btnDownloadReceipt.text = "Download Receipt"
            }

            val greenColor = binding.root.context.resources.getColor(R.color.green)
            val textColor = binding.root.context.resources.getColor(R.color.text_color)
            when (orderStatus) {
                1 -> {
                    binding.imgPaymentReceived.setImageResource(R.drawable.circle_check_green)
                    binding.imgOnTheWay.setImageResource(R.drawable.circle_check_white)
                    binding.imgSendToBank.setImageResource(R.drawable.circle_check_white)
                    binding.imgPaidToBeneficiary.setImageResource(R.drawable.circle_check_white)

                    binding.txtPaymentReceived.setTextColor(greenColor)
                    binding.txtOnTheWay.setTextColor(textColor)
                    binding.txtSendToBank.setTextColor(textColor)
                    binding.txtPaidToBeneficiary.setTextColor(textColor)
                }
                2, 3, 4 -> {
                    binding.imgPaymentReceived.setImageResource(R.drawable.circle_check_green)
                    binding.imgOnTheWay.setImageResource(R.drawable.circle_check_green)
                    binding.imgSendToBank.setImageResource(R.drawable.circle_check_white)
                    binding.imgPaidToBeneficiary.setImageResource(R.drawable.circle_check_white)

                    binding.txtPaymentReceived.setTextColor(greenColor)
                    binding.txtOnTheWay.setTextColor(greenColor)
                    binding.txtSendToBank.setTextColor(textColor)
                    binding.txtPaidToBeneficiary.setTextColor(textColor)
                }
                5 -> {
                    binding.imgPaymentReceived.setImageResource(R.drawable.circle_check_green)
                    binding.imgOnTheWay.setImageResource(R.drawable.circle_check_green)
                    binding.imgSendToBank.setImageResource(R.drawable.circle_check_green)
                    binding.imgPaidToBeneficiary.setImageResource(R.drawable.circle_check_white)

                    binding.txtPaymentReceived.setTextColor(greenColor)
                    binding.txtOnTheWay.setTextColor(greenColor)
                    binding.txtSendToBank.setTextColor(greenColor)
                    binding.txtPaidToBeneficiary.setTextColor(textColor)
                }
                9 -> {
                    binding.imgPaymentReceived.setImageResource(R.drawable.circle_check_green)
                    binding.imgOnTheWay.setImageResource(R.drawable.circle_check_green)
                    binding.imgSendToBank.setImageResource(R.drawable.circle_check_green)
                    binding.imgPaidToBeneficiary.setImageResource(R.drawable.circle_check_green)

                    binding.txtPaymentReceived.setTextColor(greenColor)
                    binding.txtOnTheWay.setTextColor(greenColor)
                    binding.txtSendToBank.setTextColor(greenColor)
                    binding.txtPaidToBeneficiary.setTextColor(greenColor)
                }
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