package com.bsel.remitngo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelResponseData
import com.bsel.remitngo.databinding.ItemCancellationBinding

class CancellationAdapter(
    private val selectedItem: (GetCancelResponseData) -> Unit
) : RecyclerView.Adapter<CancellationViewHolder>() {

    private val cancellationList = ArrayList<GetCancelResponseData>()
    private var filteredCancellationList = ArrayList<GetCancelResponseData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancellationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCancellationBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_cancellation, parent, false)
        return CancellationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredCancellationList.size
    }

    override fun onBindViewHolder(holder: CancellationViewHolder, position: Int) {
        holder.bind(filteredCancellationList[position], selectedItem)
    }

    fun setList(cancellationItem: List<GetCancelResponseData>) {
        cancellationList.clear()
        cancellationList.addAll(cancellationItem)
        filter("")
    }

    fun filter(query: String) {
        filteredCancellationList.clear()
        for (cancellations in cancellationList) {
            if (cancellations.transactionCode!!.contains(query, ignoreCase = true)) {
                filteredCancellationList.add(cancellations)
            }
        }
        notifyDataSetChanged()
    }

}

class CancellationViewHolder(val binding: ItemCancellationBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cancellationItem: GetCancelResponseData,
        selectedItem: (GetCancelResponseData) -> Unit
    ) {
        if (cancellationItem.transactionCode != null){
            binding.transactionCode.text = cancellationItem.transactionCode.toString()
        }
        if (cancellationItem.cancelReason != null){
            binding.cancellationReason.text = cancellationItem.cancelReason.toString()
        }
        if (cancellationItem.beneficiaryAmount.toString() != null){
            val beneAmount = cancellationItem.beneficiaryAmount.toString()
            binding.sendAmount.text = "BDT $beneAmount"
        }
        binding.status.text = "Pending"
        binding.itemCancellationLayout.setOnClickListener {
            selectedItem(cancellationItem)
        }
    }

}