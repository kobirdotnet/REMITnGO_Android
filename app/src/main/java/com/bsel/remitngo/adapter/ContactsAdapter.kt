package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemContactBinding
import com.bsel.remitngo.data.model.beneficiary.beneficiary.ContactItem
import kotlin.random.Random

class ContactsAdapter(
    private val selectedItem: (ContactItem) -> Unit
) : RecyclerView.Adapter<ContactViewHolder>() {

    private val contactsList = ArrayList<ContactItem>()
    private var filteredContactsList = ArrayList<ContactItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemContactBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_contact, parent, false)
        return ContactViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredContactsList.size
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredContactsList[position], selectedItem)
    }

    fun setList(contactItem: List<ContactItem>) {
        contactsList.clear()
        contactsList.addAll(contactItem)
        filter("")
    }

    fun filter(query: String) {
        filteredContactsList.clear()
        for (contacts in contactsList) {
            if (contacts.name!!.contains(query, ignoreCase = true)) {
                filteredContactsList.add(contacts)
            }
        }
        notifyDataSetChanged()
    }

}

class ContactViewHolder(val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        contactItem: ContactItem,
        selectedItem: (ContactItem) -> Unit
    ) {
        binding.contactName.text = contactItem.name
        binding.contactNumber.text = contactItem.phoneNumber
        binding.itemContactLayout.setOnClickListener {
            selectedItem(contactItem)
        }
    }

}