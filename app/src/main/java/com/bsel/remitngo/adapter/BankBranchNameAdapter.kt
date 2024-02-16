package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemBankBranchBinding
import com.bsel.remitngo.model.BankBranchItem

class BankBranchNameAdapter(
    private val selectedItem: (BankBranchItem) -> Unit
) : RecyclerView.Adapter<BankBranchNameViewHolder>() {

    private val bankBranchItemList = ArrayList<BankBranchItem>()
    private var filteredBankBranchItemList = ArrayList<BankBranchItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankBranchNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemBankBranchBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_bank_branch, parent, false)
        return BankBranchNameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredBankBranchItemList.size
    }

    override fun onBindViewHolder(
        holder: BankBranchNameViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredBankBranchItemList[position], selectedItem)
    }

    fun setList(bankBranchItem: List<BankBranchItem>) {
        bankBranchItemList.clear()
        bankBranchItemList.addAll(bankBranchItem)
        filter("")
    }

    fun filter(query: String) {
        filteredBankBranchItemList.clear()
        for (bankBranchItem in bankBranchItemList) {
            if (bankBranchItem.bankBranchName!!.contains(query, ignoreCase = true)) {
                filteredBankBranchItemList.add(bankBranchItem)
            }
        }
        notifyDataSetChanged()
    }

}

class BankBranchNameViewHolder(val binding: ItemBankBranchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        bankBranchItem: BankBranchItem,
        selectedItem: (BankBranchItem) -> Unit
    ) {
        binding.bankBranchName.text = bankBranchItem.bankBranchName
        binding.itemBankBranchLayout.setOnClickListener {
            selectedItem(bankBranchItem)
        }
    }
}