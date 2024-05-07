package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeData
import com.bsel.remitngo.databinding.ItemOccupationTypeBinding

class OccupationTypeAdapter(
    private val selectedItem: (OccupationTypeData) -> Unit
) : RecyclerView.Adapter<OccupationTypeViewHolder>() {

    private val occupationTypeList = ArrayList<OccupationTypeData>()
    private var filteredOccupationTypeList = ArrayList<OccupationTypeData>()

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

    fun setList(occupationType: List<OccupationTypeData>) {
        occupationTypeList.clear()
        occupationTypeList.addAll(occupationType)
        filter("")
    }

    fun filter(query: String) {
        filteredOccupationTypeList.clear()
        for (occupationType in occupationTypeList) {
            if (occupationType.name!!.contains(query, ignoreCase = true)) {
                filteredOccupationTypeList.add(occupationType)
            }
        }
        notifyDataSetChanged()
    }

}

class OccupationTypeViewHolder(val binding: ItemOccupationTypeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        occupationType: OccupationTypeData,
        selectedItem: (OccupationTypeData) -> Unit
    ) {
        if (occupationType.name != null) {
            binding.occupationType.text = occupationType.name
        }

        binding.itemOccupationTypeLayout.setOnClickListener {
            selectedItem(occupationType)
        }
    }
}