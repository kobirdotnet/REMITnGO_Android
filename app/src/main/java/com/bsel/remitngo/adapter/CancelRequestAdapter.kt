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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        filter("")
    }

    fun filter(query: String) {
        filteredCancelRequestList.clear()
        for (cancelRequests in cancelRequestList) {
            if (cancelRequests.transactionCode!!.contains(query, ignoreCase = true)) {
                filteredCancelRequestList.add(cancelRequests)
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
        binding.benName.text = cancelRequestItem.beneficiaryName.toString()
        binding.orderType.text = cancelRequestItem.orderTypeName.toString()
        binding.transactionCode.text = cancelRequestItem.transactionCode.toString()
        binding.transactionDate.text = cancelRequestItem.transactionDateTime12hr.toString()
        binding.bankName.text = cancelRequestItem.bankName.toString()
        binding.accountNo.text = cancelRequestItem.beneAccountNo.toString()
        val beneAmount = cancelRequestItem.beneAmount.toString()
        binding.benAmount.text = "BDT $beneAmount"

        val orderStatus = cancelRequestItem.orderStatus.toString()

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

        binding.btnGenerateRequest.setOnClickListener {
            selectedItem(cancelRequestItem)
        }
    }

}