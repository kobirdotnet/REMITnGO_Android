package com.bsel.remitngo.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelData
import com.bsel.remitngo.databinding.ItemCancelRequestBinding

class CancelRequestAdapter(
    private val selectedItem: (PopulateCancelData) -> Unit
) : RecyclerView.Adapter<CancelRequestViewHolder>() {

    private val cancelRequestList = ArrayList<PopulateCancelData>()
    private var filteredCancelRequestList = ArrayList<PopulateCancelData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancelRequestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCancelRequestBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_cancel_request, parent, false)
        return CancelRequestViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredCancelRequestList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CancelRequestViewHolder, position: Int) {
        holder.bind(filteredCancelRequestList[position], selectedItem)
    }

    fun setList(cancelRequestItem: List<PopulateCancelData>) {
        cancelRequestList.clear()
        cancelRequestList.addAll(cancelRequestItem)
        cancelRequestFilter("")
    }

    fun cancelRequestFilter(query: String) {
        filteredCancelRequestList.clear()
        for (cancelRequests in cancelRequestList) {
            if (cancelRequests.transactionCode!=null){
                if (cancelRequests.transactionCode!!.contains(query, ignoreCase = true)) {
                    filteredCancelRequestList.add(cancelRequests)
                }
            }
        }
        notifyDataSetChanged()
    }

}

class CancelRequestViewHolder(val binding: ItemCancelRequestBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(
        cancelRequestItem: PopulateCancelData,
        selectedItem: (PopulateCancelData) -> Unit
    ) {
        if (cancelRequestItem.beneName != null) {
            binding.beneName.text = cancelRequestItem.beneName.toString()
        }
        if (cancelRequestItem.orderTypeName != null) {
            binding.orderType.text = cancelRequestItem.orderTypeName.toString()
        }
        if (cancelRequestItem.transactionCode != null) {
            binding.transactionCode.text = cancelRequestItem.transactionCode.toString()
        }
        if (cancelRequestItem.transactionDateTime12hr != null) {
            binding.transactionDate.text = cancelRequestItem.transactionDateTime12hr.toString()
        }
        if (cancelRequestItem.beneBankName != null) {
            binding.bankName.text = cancelRequestItem.beneBankName.toString()
        }
        if (cancelRequestItem.beneAccountNo != null) {
            binding.accountNo.text = cancelRequestItem.beneAccountNo.toString()
        }
        if (cancelRequestItem.beneAmount.toString() != null) {
            val beneAmount = cancelRequestItem.beneAmount.toString()
            binding.beneAmount.text = "BDT $beneAmount"
        }

        if (cancelRequestItem.orderStatus.toString() != null) {
            val orderStatus = cancelRequestItem.orderStatus.toString().toInt()
            if (orderStatus == 7 || orderStatus == 8 || orderStatus == 10 || orderStatus == 11) {
                binding.cancelStatus.visibility = View.VISIBLE
                binding.successStatus.visibility = View.GONE
            } else {
                binding.cancelStatus.visibility = View.GONE
                binding.successStatus.visibility = View.VISIBLE
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

        binding.btnGenerateRequest.setOnClickListener {
            selectedItem(cancelRequestItem)
        }
    }

}