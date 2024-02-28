package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.profile.postCode.PostCodeData
import com.bsel.remitngo.databinding.ItemAddressBinding
import com.bsel.remitngo.model.AddressItem

class AddressAdapter(
    private val selectedItem: (PostCodeData) -> Unit
) : RecyclerView.Adapter<AddressViewHolder>() {

    private val addressItemList = ArrayList<PostCodeData>()
    private var filteredAddressItemList = ArrayList<PostCodeData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemAddressBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_address, parent, false)
        return AddressViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredAddressItemList.size
    }

    override fun onBindViewHolder(
        holder: AddressViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredAddressItemList[position], selectedItem)
    }

    fun setList(addressItem: List<PostCodeData>) {
        addressItemList.clear()
        addressItemList.addAll(addressItem)
        filter("")
    }

    fun filter(query: String) {
        filteredAddressItemList.clear()
        for (addressItem in addressItemList) {
            if (addressItem.ukAddress!!.contains(query, ignoreCase = true)) {
                filteredAddressItemList.add(addressItem)
            }
        }
        notifyDataSetChanged()
    }

}

class AddressViewHolder(val binding: ItemAddressBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        addressItem: PostCodeData,
        selectedItem: (PostCodeData) -> Unit
    ) {
        binding.addressName.text = addressItem.ukAddress
        binding.itemAddressLayout.setOnClickListener {
            selectedItem(addressItem)
        }
    }
}