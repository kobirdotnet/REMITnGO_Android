package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.branch.BranchData
import com.bsel.remitngo.databinding.ItemBranchBinding

class BranchNameAdapter(
    private val selectedItem: (BranchData) -> Unit
) : RecyclerView.Adapter<BranchNameViewHolder>() {

    private val branchItemList = ArrayList<BranchData>()
    private var filteredBranchItemList = ArrayList<BranchData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemBranchBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_branch, parent, false)
        return BranchNameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredBranchItemList.size
    }

    override fun onBindViewHolder(
        holder: BranchNameViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredBranchItemList[position], selectedItem)
    }

    fun setList(branchItem: List<BranchData>) {
        branchItemList.clear()
        branchItemList.addAll(branchItem)
        filter("")
    }

    fun filter(query: String) {
        filteredBranchItemList.clear()
        for (branchItem in branchItemList) {
            if (branchItem.name!!.contains(query, ignoreCase = true)) {
                filteredBranchItemList.add(branchItem)
            }
        }
        notifyDataSetChanged()
    }

}

class BranchNameViewHolder(val binding: ItemBranchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        branchItem: BranchData,
        selectedItem: (BranchData) -> Unit
    ) {
        binding.bankBranchName.text = branchItem.name
        binding.itemBranchLayout.setOnClickListener {
            selectedItem(branchItem)
        }
    }
}