package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryData
import com.bsel.remitngo.databinding.ItemBeneficiaryManagementBinding

class BeneficiaryManagementAdapter(
    private val selectedItem: (GetBeneficiaryData) -> Unit,
    private val bankInfo: (GetBeneficiaryData) -> Unit,
    private val walletInfo: (GetBeneficiaryData) -> Unit,
    private val transfer: (GetBeneficiaryData) -> Unit
) : RecyclerView.Adapter<BeneficiaryManagementViewHolder>() {

    private val beneficiaryList = ArrayList<GetBeneficiaryData>()
    private var filteredBeneficiaryList = ArrayList<GetBeneficiaryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiaryManagementViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemBeneficiaryManagementBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_beneficiary_management, parent, false)
        return BeneficiaryManagementViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredBeneficiaryList.size
    }

    override fun onBindViewHolder(
        holder: BeneficiaryManagementViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredBeneficiaryList[position], selectedItem,bankInfo,walletInfo,transfer)
    }

    fun setList(beneficiaryItem: List<GetBeneficiaryData>) {
        beneficiaryList.clear()
        beneficiaryList.addAll(beneficiaryItem)
        filter("")
    }

    fun filter(query: String) {
        filteredBeneficiaryList.clear()
        for (beneficiaries in beneficiaryList) {
            if (beneficiaries.beneName!!.contains(query, ignoreCase = true)) {
                filteredBeneficiaryList.add(beneficiaries)
            }
        }
        notifyDataSetChanged()
    }

}

class BeneficiaryManagementViewHolder(val binding: ItemBeneficiaryManagementBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        beneficiaryItem: GetBeneficiaryData,
        selectedItem: (GetBeneficiaryData) -> Unit,
        bankInfo: (GetBeneficiaryData) -> Unit,
        walletInfo: (GetBeneficiaryData) -> Unit,
        transfer: (GetBeneficiaryData) -> Unit
    ) {
        if (beneficiaryItem.beneName != null){
            binding.beneficiaryName.text = beneficiaryItem.beneName.toString()
        }
        if (beneficiaryItem.mobile != null){
            binding.beneficiaryPhone.text = beneficiaryItem.mobile.toString()
        }
        binding.bankInfo.setOnClickListener {
            bankInfo(beneficiaryItem)
        }
        binding.walletInfo.setOnClickListener {
            walletInfo(beneficiaryItem)
        }
        binding.transfer.setOnClickListener {
            transfer(beneficiaryItem)
        }
        binding.itemBeneficiaryLayout.setOnClickListener {
            selectedItem(beneficiaryItem)
        }
    }
}