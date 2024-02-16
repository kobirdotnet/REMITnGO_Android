package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemCancelReasonBinding
import com.bsel.remitngo.model.CancelReasonItem

class CancelReasonAdapter(
    private val selectedItem: (CancelReasonItem) -> Unit
) : RecyclerView.Adapter<CancelReasonViewHolder>() {

    private val cancelReasonItemList = ArrayList<CancelReasonItem>()
    private var filteredCancelReasonItemList = ArrayList<CancelReasonItem>()

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

    fun setList(cancelReasonItem: List<CancelReasonItem>) {
        cancelReasonItemList.clear()
        cancelReasonItemList.addAll(cancelReasonItem)
        filter("")
    }

    fun filter(query: String) {
        filteredCancelReasonItemList.clear()
        for (cancelReasonItem in cancelReasonItemList) {
            if (cancelReasonItem.cancelReasonName!!.contains(query, ignoreCase = true)) {
                filteredCancelReasonItemList.add(cancelReasonItem)
            }
        }
        notifyDataSetChanged()
    }

}

class CancelReasonViewHolder(val binding: ItemCancelReasonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cancelReasonItem: CancelReasonItem,
        selectedItem: (CancelReasonItem) -> Unit
    ) {
        binding.reasonName.text = cancelReasonItem.cancelReasonName
        binding.itemCancelReasonLayout.setOnClickListener {
            selectedItem(cancelReasonItem)
        }
    }
}