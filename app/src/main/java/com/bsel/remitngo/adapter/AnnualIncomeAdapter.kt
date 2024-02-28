package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeData
import com.bsel.remitngo.databinding.ItemAnnualIncomeBinding

class AnnualIncomeAdapter(
    private val selectedItem: (AnnualIncomeData) -> Unit
) : RecyclerView.Adapter<AnnualIncomeViewHolder>() {

    private val annualIncomeList = ArrayList<AnnualIncomeData>()
    private var filteredAnnualIncomeList = ArrayList<AnnualIncomeData>()

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): AnnualIncomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemAnnualIncomeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_annual_income, parent, false)
        return AnnualIncomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredAnnualIncomeList.size
    }

    override fun onBindViewHolder(
        holder: AnnualIncomeViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredAnnualIncomeList[position], selectedItem)
    }

    fun setList(annualIncome: List<AnnualIncomeData>) {
        annualIncomeList.clear()
        annualIncomeList.addAll(annualIncome)
        filter("")
    }

    fun filter(query: String) {
        filteredAnnualIncomeList.clear()
        for (annualIncome in annualIncomeList) {
            if (annualIncome.name!!.contains(query, ignoreCase = true)) {
                filteredAnnualIncomeList.add(annualIncome)
            }
        }
        notifyDataSetChanged()
    }

}

class AnnualIncomeViewHolder(val binding: ItemAnnualIncomeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        annualIncome: AnnualIncomeData,
        selectedItem: (AnnualIncomeData) -> Unit
    ) {
        binding.annualIncome.text = annualIncome.name
        binding.itemAnnualIncomeLayout.setOnClickListener {
            selectedItem(annualIncome)
        }
    }
}