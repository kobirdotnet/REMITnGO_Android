package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.profile.county.CountyData
import com.bsel.remitngo.databinding.ItemCountyBinding

class CountyAdapter(
    private val selectedItem: (CountyData) -> Unit
) : RecyclerView.Adapter<CountyViewHolder>() {

    private val countyItemList = ArrayList<CountyData>()
    private var filteredCountyItemList = ArrayList<CountyData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCountyBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_county, parent, false)
        return CountyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredCountyItemList.size
    }

    override fun onBindViewHolder(
        holder: CountyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredCountyItemList[position], selectedItem)
    }

    fun setList(countyItem: List<CountyData>) {
        countyItemList.clear()
        countyItemList.addAll(countyItem)
        filter("")
    }

    fun filter(query: String) {
        filteredCountyItemList.clear()
        for (countyItem in countyItemList) {
            if (countyItem.name!!.contains(query, ignoreCase = true)) {
                filteredCountyItemList.add(countyItem)
            }
        }
        notifyDataSetChanged()
    }

}

class CountyViewHolder(val binding: ItemCountyBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        countyItem: CountyData,
        selectedItem: (CountyData) -> Unit
    ) {
        if (countyItem.name != null) {
            binding.countyName.text = countyItem.name
        }

        binding.itemCountyLayout.setOnClickListener {
            selectedItem(countyItem)
        }
    }
}