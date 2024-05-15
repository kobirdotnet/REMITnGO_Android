package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.paying_agent.PayingAgentData
import com.bsel.remitngo.databinding.ItemPayingAgentBankBinding

class PayingAgentInstantCreditAdapter(
    private val selectedItem: (PayingAgentData) -> Unit
) : RecyclerView.Adapter<PayingAgentInstantCreditViewHolder>() {

    private val payingAgentItemList = ArrayList<PayingAgentData>()
    private var filteredPayingAgentItemList = ArrayList<PayingAgentData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayingAgentInstantCreditViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemPayingAgentBankBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_paying_agent_bank, parent, false)
        return PayingAgentInstantCreditViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredPayingAgentItemList.size
    }

    override fun onBindViewHolder(
        holder: PayingAgentInstantCreditViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredPayingAgentItemList[position], selectedItem)
    }

    fun setList(payingAgentItem: List<PayingAgentData>) {
        payingAgentItemList.clear()
        payingAgentItemList.addAll(payingAgentItem)
        payingAgentInstantCreditFilter("")
    }

    fun payingAgentInstantCreditFilter(query: String) {
        filteredPayingAgentItemList.clear()
        for (payingAgentItem in payingAgentItemList) {
            if (payingAgentItem.name !=null){
                if (payingAgentItem.name.contains(query, ignoreCase = true)) {
                    filteredPayingAgentItemList.add(payingAgentItem)
                }
            }
        }
        notifyDataSetChanged()
    }

}

class PayingAgentInstantCreditViewHolder(val binding: ItemPayingAgentBankBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        payingAgentItem: PayingAgentData,
        selectedItem: (PayingAgentData) -> Unit
    ) {
        if (payingAgentItem.name != null) {
            binding.payingAgentName.text = payingAgentItem.name
        }

        binding.itemPayingAgentLayout.setOnClickListener {
            selectedItem(payingAgentItem)
        }
    }
}