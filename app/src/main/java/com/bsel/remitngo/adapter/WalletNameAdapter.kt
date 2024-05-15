package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.bank.WalletData
import com.bsel.remitngo.databinding.ItemWalletBinding

class WalletNameAdapter(
    private val selectedItem: (WalletData) -> Unit
) : RecyclerView.Adapter<WalletNameViewHolder>() {

    private val walletItemList = ArrayList<WalletData>()
    private var filteredWalletItemList = ArrayList<WalletData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemWalletBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_wallet, parent, false)
        return WalletNameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredWalletItemList.size
    }

    override fun onBindViewHolder(
        holder: WalletNameViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredWalletItemList[position], selectedItem)
    }

    fun setList(walletItem: List<WalletData>) {
        walletItemList.clear()
        walletItemList.addAll(walletItem)
        walletFilter("")
    }

    fun walletFilter(query: String) {
        filteredWalletItemList.clear()
        for (walletItem in walletItemList) {
            if (walletItem.name !=null){
                if (walletItem.name.contains(query, ignoreCase = true)) {
                    filteredWalletItemList.add(walletItem)
                }
            }
        }
        notifyDataSetChanged()
    }

}

class WalletNameViewHolder(val binding: ItemWalletBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        walletItem: WalletData,
        selectedItem: (WalletData) -> Unit
    ) {
        if (walletItem.name != null){
            binding.walletName.text = walletItem.name
        }
        binding.itemWalletLayout.setOnClickListener {
            selectedItem(walletItem)
        }
    }
}