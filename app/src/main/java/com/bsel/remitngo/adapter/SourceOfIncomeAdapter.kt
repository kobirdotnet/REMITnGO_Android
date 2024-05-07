package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeData
import com.bsel.remitngo.databinding.ItemSourceOfIncomeBinding

class SourceOfIncomeAdapter(
    private val selectedItem: (SourceOfIncomeData) -> Unit
) : RecyclerView.Adapter<SourceOfIncomeViewHolder>() {

    private val sourceOfIncomeList = ArrayList<SourceOfIncomeData>()
    private var filteredSourceOfIncomeList = ArrayList<SourceOfIncomeData>()

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): SourceOfIncomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemSourceOfIncomeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_source_of_income, parent, false)
        return SourceOfIncomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredSourceOfIncomeList.size
    }

    override fun onBindViewHolder(
        holder: SourceOfIncomeViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredSourceOfIncomeList[position], selectedItem)
    }

    fun setList(sourceOfIncome: List<SourceOfIncomeData>) {
        sourceOfIncomeList.clear()
        sourceOfIncomeList.addAll(sourceOfIncome)
        filter("")
    }

    fun filter(query: String) {
        filteredSourceOfIncomeList.clear()
        for (sourceOfIncome in sourceOfIncomeList) {
            if (sourceOfIncome.name!!.contains(query, ignoreCase = true)) {
                filteredSourceOfIncomeList.add(sourceOfIncome)
            }
        }
        notifyDataSetChanged()
    }

}

class SourceOfIncomeViewHolder(val binding: ItemSourceOfIncomeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        sourceOfIncome: SourceOfIncomeData,
        selectedItem: (SourceOfIncomeData) -> Unit
    ) {
        if (sourceOfIncome.name != null) {
            binding.sourceOfIncome.text = sourceOfIncome.name
        }

        binding.itemSourceOfIncomeLayout.setOnClickListener {
            selectedItem(sourceOfIncome)
        }
    }
}