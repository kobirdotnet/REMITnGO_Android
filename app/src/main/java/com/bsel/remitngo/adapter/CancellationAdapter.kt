package com.bsel.remitngo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemCancellationBinding
import com.bsel.remitngo.model.CancellationItem

class CancellationAdapter(
    private val selectedItem: (CancellationItem) -> Unit
) : RecyclerView.Adapter<CancellationViewHolder>() {

    private val cancellationList = ArrayList<CancellationItem>()
    private var filteredCancellationList = ArrayList<CancellationItem>()

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

    fun setList(cancellationItem: List<CancellationItem>) {
        cancellationList.clear()
        cancellationList.addAll(cancellationItem)
        filter("")
    }

    fun filter(query: String) {
        filteredCancellationList.clear()
        for (cancellations in cancellationList) {
            if (cancellations.cancellationCode!!.contains(query, ignoreCase = true)) {
                filteredCancellationList.add(cancellations)
            }
        }
        notifyDataSetChanged()
    }

}

class CancellationViewHolder(val binding: ItemCancellationBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cancellationItem: CancellationItem,
        selectedItem: (CancellationItem) -> Unit
    ) {
        binding.transactionCode.text = cancellationItem.cancellationCode
        binding.itemCancellationLayout.setOnClickListener {
            selectedItem(cancellationItem)
        }
    }

}