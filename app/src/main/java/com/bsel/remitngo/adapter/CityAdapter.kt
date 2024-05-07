package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.profile.city.CityData
import com.bsel.remitngo.databinding.ItemCityBinding

class CityAdapter(
    private val selectedItem: (CityData) -> Unit
) : RecyclerView.Adapter<CityViewHolder>() {

    private val cityItemList = ArrayList<CityData>()
    private var filteredCityItemList = ArrayList<CityData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCityBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_city, parent, false)
        return CityViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredCityItemList.size
    }

    override fun onBindViewHolder(
        holder: CityViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredCityItemList[position], selectedItem)
    }

    fun setList(cityItem: List<CityData>) {
        cityItemList.clear()
        cityItemList.addAll(cityItem)
        filter("")
    }

    fun filter(query: String) {
        filteredCityItemList.clear()
        for (cityItem in cityItemList) {
            if (cityItem.name!!.contains(query, ignoreCase = true)) {
                filteredCityItemList.add(cityItem)
            }
        }
        notifyDataSetChanged()
    }

}

class CityViewHolder(val binding: ItemCityBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cityItem: CityData,
        selectedItem: (CityData) -> Unit
    ) {
        if (cityItem.name != null) {
            binding.cityName.text = cityItem.name
        }

        binding.itemCityLayout.setOnClickListener {
            selectedItem(cityItem)
        }
    }
}