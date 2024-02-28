package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.profile.nationality.NationalityData
import com.bsel.remitngo.databinding.ItemNationalityBinding

class NationalityAdapter(
    private val selectedItem: (NationalityData) -> Unit
) : RecyclerView.Adapter<NationalityViewHolder>() {

    private val nationalityList = ArrayList<NationalityData>()
    private var filteredNationalityList = ArrayList<NationalityData>()

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): NationalityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemNationalityBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_nationality, parent, false)
        return NationalityViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredNationalityList.size
    }

    override fun onBindViewHolder(
        holder: NationalityViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredNationalityList[position], selectedItem)
    }

    fun setList(nationality: List<NationalityData>) {
        nationalityList.clear()
        nationalityList.addAll(nationality)
        filter("")
    }

    fun filter(query: String) {
        filteredNationalityList.clear()
        for (nationality in nationalityList) {
            if (nationality.name!!.contains(query, ignoreCase = true)) {
                filteredNationalityList.add(nationality)
            }
        }
        notifyDataSetChanged()
    }

}

class NationalityViewHolder(val binding: ItemNationalityBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        nationality: NationalityData,
        selectedItem: (NationalityData) -> Unit
    ) {
        binding.nationality.text = nationality.name
        binding.itemNationalityLayout.setOnClickListener {
            selectedItem(nationality)
        }
    }
}