package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.profile.occupation.OccupationData
import com.bsel.remitngo.databinding.ItemOccupationBinding

class OccupationAdapter(
    private val selectedItem: (OccupationData) -> Unit
) : RecyclerView.Adapter<OccupationViewHolder>() {

    private val occupationList = ArrayList<OccupationData>()
    private var filteredOccupationList = ArrayList<OccupationData>()

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): OccupationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemOccupationBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_occupation, parent, false)
        return OccupationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredOccupationList.size
    }

    override fun onBindViewHolder(
        holder: OccupationViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredOccupationList[position], selectedItem)
    }

    fun setList(occupation: List<OccupationData>) {
        occupationList.clear()
        occupationList.addAll(occupation)
        filter("")
    }

    fun filter(query: String) {
        filteredOccupationList.clear()
        for (occupation in occupationList) {
            if (occupation.name!!.contains(query, ignoreCase = true)) {
                filteredOccupationList.add(occupation)
            }
        }
        notifyDataSetChanged()
    }

}

class OccupationViewHolder(val binding: ItemOccupationBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        occupation: OccupationData,
        selectedItem: (OccupationData) -> Unit
    ) {
        binding.occupation.text = occupation.name
        binding.itemOccupationLayout.setOnClickListener {
            selectedItem(occupation)
        }
    }
}