package com.bsel.remitngo.presentation.ui.beneficiary

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import com.bsel.remitngo.data.model.beneficiary.beneficiary.Contact
import com.bsel.remitngo.data.repository.beneficiary.ContactRepository

class ContactViewModel(context: Context?) : BaseObservable() {
    private val contacts: ObservableArrayList<Contact> = ObservableArrayList<Contact>()
    private val repository: ContactRepository

    init {
        repository = ContactRepository(context!!)
    }

    fun getContacts(): List<Contact> {
        contacts.addAll(repository.fetchContacts())
        return contacts
    }
}