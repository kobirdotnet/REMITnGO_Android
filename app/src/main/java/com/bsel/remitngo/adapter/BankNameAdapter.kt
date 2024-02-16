package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemBankBinding
import com.bsel.remitngo.model.BankItem

class BankNameAdapter(
    private val selectedItem: (BankItem) -> Unit
) : RecyclerView.Adapter<BankNameViewHolder>() {

    private val bankItemList = ArrayList<BankItem>()
    private var filteredBankItemList = ArrayList<BankItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemBankBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_bank, parent, false)
        return BankNameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredBankItemList.size
    }

    override fun onBindViewHolder(
        holder: BankNameViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredBankItemList[position], selectedItem)
    }

    fun setList(bankItem: List<BankItem>) {
        bankItemList.clear()
        bankItemList.addAll(bankItem)
        filter("")
    }

    fun filter(query: String) {
        filteredBankItemList.clear()
        for (bankItem in bankItemList) {
            if (bankItem.bankName!!.contains(query, ignoreCase = true)) {
                filteredBankItemList.add(bankItem)
            }
        }
        notifyDataSetChanged()
    }

}

class BankNameViewHolder(val binding: ItemBankBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        bankItem: BankItem,
        selectedItem: (BankItem) -> Unit
    ) {
        binding.bankName.text = bankItem.bankName
        binding.bankFlag.setImageResource(bankItem.flagDrawable)
        binding.itemBankLayout.setOnClickListener {
            selectedItem(bankItem)
        }
    }
}