package com.bsel.remitngo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemCancelRequestBinding
import com.bsel.remitngo.model.CancelRequestItem

class CancelRequestAdapter(
    private val selectedItem: (CancelRequestItem) -> Unit
) : RecyclerView.Adapter<CancelRequestViewHolder>() {

    private val cancelRequestList = ArrayList<CancelRequestItem>()
    private var filteredCancelRequestList = ArrayList<CancelRequestItem>()

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

    fun setList(cancelRequestItem: List<CancelRequestItem>) {
        cancelRequestList.clear()
        cancelRequestList.addAll(cancelRequestItem)
        filter("")
    }

    fun filter(query: String) {
        filteredCancelRequestList.clear()
        for (cancelRequests in cancelRequestList) {
            if (cancelRequests.cancelRequestName!!.contains(query, ignoreCase = true)) {
                filteredCancelRequestList.add(cancelRequests)
            }
        }
        notifyDataSetChanged()
    }

}

class CancelRequestViewHolder(val binding: ItemCancelRequestBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cancelRequestItem: CancelRequestItem,
        selectedItem: (CancelRequestItem) -> Unit
    ) {
        binding.benName.text = cancelRequestItem.cancelRequestName
        binding.btnGenerateRequest.setOnClickListener {
            selectedItem(cancelRequestItem)
        }
    }

}