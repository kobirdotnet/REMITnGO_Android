package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonData
import com.bsel.remitngo.databinding.ItemCancelReasonBinding

class CancelReasonAdapter(
    private val selectedItem: (CancelReasonData) -> Unit
) : RecyclerView.Adapter<CancelReasonViewHolder>() {

    private val cancelReasonItemList = ArrayList<CancelReasonData>()
    private var filteredCancelReasonItemList = ArrayList<CancelReasonData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancelReasonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCancelReasonBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_cancel_reason, parent, false)
        return CancelReasonViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredCancelReasonItemList.size
    }

    override fun onBindViewHolder(
        holder: CancelReasonViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredCancelReasonItemList[position], selectedItem)
    }

    fun setList(cancelReasonItem: List<CancelReasonData>) {
        cancelReasonItemList.clear()
        cancelReasonItemList.addAll(cancelReasonItem)
        filter("")
    }

    fun filter(query: String) {
        filteredCancelReasonItemList.clear()
        for (cancelReasonItem in cancelReasonItemList) {
            if (cancelReasonItem.name!!.contains(query, ignoreCase = true)) {
                filteredCancelReasonItemList.add(cancelReasonItem)
            }
        }
        notifyDataSetChanged()
    }

}

class CancelReasonViewHolder(val binding: ItemCancelReasonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cancelReasonItem: CancelReasonData,
        selectedItem: (CancelReasonData) -> Unit
    ) {
        if (cancelReasonItem.name != null){
            binding.reasonName.text = cancelReasonItem.name
        }
        binding.itemCancelReasonLayout.setOnClickListener {
            selectedItem(cancelReasonItem)
        }
    }
}