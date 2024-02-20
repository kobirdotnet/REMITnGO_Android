package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.district.DistrictData
import com.bsel.remitngo.databinding.ItemDistrictBinding

class DistrictNameAdapter(
    private val selectedItem: (DistrictData) -> Unit
) : RecyclerView.Adapter<DistrictNameViewHolder>() {

    private val districtItemList = ArrayList<DistrictData>()
    private var filteredDistrictItemList = ArrayList<DistrictData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDistrictBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_district, parent, false)
        return DistrictNameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredDistrictItemList.size
    }

    override fun onBindViewHolder(
        holder: DistrictNameViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredDistrictItemList[position], selectedItem)
    }

    fun setList(districtItem: List<DistrictData>) {
        districtItemList.clear()
        districtItemList.addAll(districtItem)
        filter("")
    }

    fun filter(query: String) {
        filteredDistrictItemList.clear()
        for (districtItem in districtItemList) {
            if (districtItem.name!!.contains(query, ignoreCase = true)) {
                filteredDistrictItemList.add(districtItem)
            }
        }
        notifyDataSetChanged()
    }

}

class DistrictNameViewHolder(val binding: ItemDistrictBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        districtItem: DistrictData,
        selectedItem: (DistrictData) -> Unit
    ) {
        binding.districtName.text = districtItem.name
        binding.itemDistrictLayout.setOnClickListener {
            selectedItem(districtItem)
        }
    }
}