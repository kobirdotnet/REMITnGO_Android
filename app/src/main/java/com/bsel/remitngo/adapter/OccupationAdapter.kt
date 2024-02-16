package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemOccupationBinding
import com.bsel.remitngo.model.Occupation

class OccupationAdapter(
    private val selectedItem: (Occupation) -> Unit
) : RecyclerView.Adapter<OccupationViewHolder>() {

    private val occupationList = ArrayList<Occupation>()
    private var filteredOccupationList = ArrayList<Occupation>()

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

    fun setList(occupation: List<Occupation>) {
        occupationList.clear()
        occupationList.addAll(occupation)
        filter("")
    }

    fun filter(query: String) {
        filteredOccupationList.clear()
        for (occupation in occupationList) {
            if (occupation.occupationName!!.contains(query, ignoreCase = true)) {
                filteredOccupationList.add(occupation)
            }
        }
        notifyDataSetChanged()
    }

}

class OccupationViewHolder(val binding: ItemOccupationBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        occupation: Occupation,
        selectedItem: (Occupation) -> Unit
    ) {
        binding.occupation.text = occupation.occupationName
        binding.itemOccupationLayout.setOnClickListener {
            selectedItem(occupation)
        }
    }
}