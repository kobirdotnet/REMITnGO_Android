package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.reason.ReasonData
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.databinding.ItemReasonBinding

class ReasonNameAdapter(
    private val selectedItem: (ReasonData) -> Unit
) : RecyclerView.Adapter<ReasonNameViewHolder>() {

    private val reasonItemList = ArrayList<ReasonData>()
    private var filteredReasonItemList = ArrayList<ReasonData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReasonNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemReasonBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_reason, parent, false)
        return ReasonNameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredReasonItemList.size
    }

    override fun onBindViewHolder(
        holder: ReasonNameViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredReasonItemList[position], selectedItem)
    }

    fun setList(reasonItem: List<ReasonData>) {
        reasonItemList.clear()
        reasonItemList.addAll(reasonItem)
        filter("")
    }

    fun filter(query: String) {
        filteredReasonItemList.clear()
        for (reasonItem in reasonItemList) {
            if (reasonItem.name!!.contains(query, ignoreCase = true)) {
                filteredReasonItemList.add(reasonItem)
            }
        }
        notifyDataSetChanged()
    }

}

class ReasonNameViewHolder(val binding: ItemReasonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        reasonItem: ReasonData,
        selectedItem: (ReasonData) -> Unit
    ) {
        binding.reasonName.text = reasonItem.name
        binding.itemReasonLayout.setOnClickListener {
            selectedItem(reasonItem)
        }
    }
}