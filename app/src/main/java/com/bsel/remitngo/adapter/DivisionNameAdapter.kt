package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.division.DivisionData
import com.bsel.remitngo.databinding.ItemDivisionBinding

class DivisionNameAdapter(
    private val selectedItem: (DivisionData) -> Unit
) : RecyclerView.Adapter<DivisionNameViewHolder>() {

    private val divisionItemList = ArrayList<DivisionData>()
    private var filteredDivisionItemList = ArrayList<DivisionData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DivisionNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDivisionBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_division, parent, false)
        return DivisionNameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredDivisionItemList.size
    }

    override fun onBindViewHolder(
        holder: DivisionNameViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredDivisionItemList[position], selectedItem)
    }

    fun setList(divisionItem: List<DivisionData>) {
        divisionItemList.clear()
        divisionItemList.addAll(divisionItem)
        filter("")
    }

    fun filter(query: String) {
        filteredDivisionItemList.clear()
        for (divisionItem in divisionItemList) {
            if (divisionItem.name!!.contains(query, ignoreCase = true)) {
                filteredDivisionItemList.add(divisionItem)
            }
        }
        notifyDataSetChanged()
    }

}

class DivisionNameViewHolder(val binding: ItemDivisionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        divisionItem: DivisionData,
        selectedItem: (DivisionData) -> Unit
    ) {
        binding.divisionName.text = divisionItem.name
        binding.itemDivisionLayout.setOnClickListener {
            selectedItem(divisionItem)
        }
    }
}