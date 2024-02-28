package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.profile.uk_division.UkDivisionData
import com.bsel.remitngo.databinding.ItemUkDivisionBinding

class UkDivisionAdapter(
    private val selectedItem: (UkDivisionData) -> Unit
) : RecyclerView.Adapter<UkDivisionViewHolder>() {

    private val ukDivisionItemList = ArrayList<UkDivisionData>()
    private var filteredUkDivisionItemList = ArrayList<UkDivisionData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UkDivisionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemUkDivisionBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_uk_division, parent, false)
        return UkDivisionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredUkDivisionItemList.size
    }

    override fun onBindViewHolder(
        holder: UkDivisionViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredUkDivisionItemList[position], selectedItem)
    }

    fun setList(ukDivisionItem: List<UkDivisionData>) {
        ukDivisionItemList.clear()
        ukDivisionItemList.addAll(ukDivisionItem)
        filter("")
    }

    fun filter(query: String) {
        filteredUkDivisionItemList.clear()
        for (ukDivisionItem in ukDivisionItemList) {
            if (ukDivisionItem.name!!.contains(query, ignoreCase = true)) {
                filteredUkDivisionItemList.add(ukDivisionItem)
            }
        }
        notifyDataSetChanged()
    }

}

class UkDivisionViewHolder(val binding: ItemUkDivisionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        ukDivisionItem: UkDivisionData,
        selectedItem: (UkDivisionData) -> Unit
    ) {
        binding.ukDivisionName.text = ukDivisionItem.name
        binding.itemUkDivisionLayout.setOnClickListener {
            selectedItem(ukDivisionItem)
        }
    }
}