package com.bsel.remitngo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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
    fun bind(
        cancelRequestItem: PopulateCancelData,
        selectedItem: (PopulateCancelData) -> Unit
    ) {
        binding.benName.text = cancelRequestItem.beneAccountName.toString()
//        binding.orderType.text = cancelRequestItem.orderTypeName.toString()
        binding.transactionCode.text = cancelRequestItem.transactionCode.toString()
        binding.transactionDate.text = cancelRequestItem.transactionDate.toString()

//        binding.bankName.text = cancelRequestItem.bankName.toString()
        binding.accountNo.text = cancelRequestItem.beneAccountNo.toString()
        val beneAmount = cancelRequestItem.beneAmount.toString()
        binding.currencyToAmount.text = "BDT$beneAmount"

        binding.btnGenerateRequest.setOnClickListener {
            selectedItem(cancelRequestItem)
        }
    }

}