package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemNationalityBinding
import com.bsel.remitngo.model.Nationality

class NationalityAdapter(
    private val selectedItem: (Nationality) -> Unit
) : RecyclerView.Adapter<NationalityViewHolder>() {

    private val nationalityList = ArrayList<Nationality>()
    private var filteredNationalityList = ArrayList<Nationality>()

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

    fun setList(nationality: List<Nationality>) {
        nationalityList.clear()
        nationalityList.addAll(nationality)
        filter("")
    }

    fun filter(query: String) {
        filteredNationalityList.clear()
        for (nationality in nationalityList) {
            if (nationality.nationalityName!!.contains(query, ignoreCase = true)) {
                filteredNationalityList.add(nationality)
            }
        }
        notifyDataSetChanged()
    }

}

class NationalityViewHolder(val binding: ItemNationalityBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        nationality: Nationality,
        selectedItem: (Nationality) -> Unit
    ) {
        binding.nationality.text = nationality.nationalityName
        binding.itemNationalityLayout.setOnClickListener {
            selectedItem(nationality)
        }
    }
}