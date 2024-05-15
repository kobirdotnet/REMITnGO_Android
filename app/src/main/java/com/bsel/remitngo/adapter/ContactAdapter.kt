package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemContactBinding
import com.bsel.remitngo.data.model.beneficiary.beneficiary.Contact

class ContactAdapter(
    private val selectedItem: (Contact) -> Unit
) : RecyclerView.Adapter<ContactViewHolder>() {

    private val contactsList = ArrayList<Contact>()
    private var filteredContactsList = ArrayList<Contact>()

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

    fun setList(contact: List<Contact>) {
        contactsList.clear()
        contactsList.addAll(contact)
        contactFilter("")
    }

    fun contactFilter(query: String) {
        filteredContactsList.clear()
        for (contacts in contactsList) {
            if (contacts.name !=null){
                if (contacts.name!!.contains(query, ignoreCase = true)) {
                    filteredContactsList.add(contacts)
                }
            }
        }
        notifyDataSetChanged()
    }

}

class ContactViewHolder(val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        contact: Contact,
        selectedItem: (Contact) -> Unit
    ) {
        if (contact.name != null) {
            binding.contactName.text = contact.name
        }
        if (contact.phoneNumber != null) {
            binding.contactNumber.text = contact.phoneNumber
        }
        binding.itemContactLayout.setOnClickListener {
            selectedItem(contact)
        }
    }

}