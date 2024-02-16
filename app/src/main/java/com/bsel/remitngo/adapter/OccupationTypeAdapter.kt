package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemOccupationTypeBinding
import com.bsel.remitngo.model.OccupationType

class OccupationTypeAdapter(
    private val selectedItem: (OccupationType) -> Unit
) : RecyclerView.Adapter<OccupationTypeViewHolder>() {

    private val occupationTypeList = ArrayList<OccupationType>()
    private var filteredOccupationTypeList = ArrayList<OccupationType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OccupationTypeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemOccupationTypeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_occupation_type, parent, false)
        return OccupationTypeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredOccupationTypeList.size
    }

    override fun onBindViewHolder(
        holder: OccupationTypeViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredOccupationTypeList[position], selectedItem)
    }

    fun setList(occupationType: List<OccupationType>) {
        occupationTypeList.clear()
        occupationTypeList.addAll(occupationType)
        filter("")
    }

    fun filter(query: String) {
        filteredOccupationTypeList.clear()
        for (occupationType in occupationTypeList) {
            if (occupationType.occupationTypeName!!.contains(query, ignoreCase = true)) {
                filteredOccupationTypeList.add(occupationType)
            }
        }
        notifyDataSetChanged()
    }

}

class OccupationTypeViewHolder(val binding: ItemOccupationTypeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        occupationType: OccupationType,
        selectedItem: (OccupationType) -> Unit
    ) {
        binding.occupationType.text = occupationType.occupationTypeName
        binding.itemOccupationTypeLayout.setOnClickListener {
            selectedItem(occupationType)
        }
    }
}