package com.bsel.remitngo.data.repository.beneficiary

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.bsel.remitngo.data.model.beneficiary.beneficiary.Contact

class ContactRepository(private val context: Context) {
    private val contacts: List<Contact>

    init {
        contacts = ArrayList()
    }

    @SuppressLint("Range")
    fun fetchContacts(): List<Contact> {
        var contact: Contact
        val cleanList: MutableMap<String, Contact> = LinkedHashMap()
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        if (cursor?.count ?: 0 > 0) {
            while (cursor!!.moveToNext()) {
                contact = Contact()
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNo =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                Log.e("contact", "getAllContacts: $name $phoneNo")
                contact.name=name
                contact.phoneNumber= formatPhoneNumber(phoneNo)
                cleanList[contact.phoneNumber!!] = contact
            }
        }
        cursor?.close()
        return ArrayList(cleanList.values)
    }

    companion object {
        private fun formatPhoneNumber(phone: String): String {
            var formattedPhone = phone.replace(" ".toRegex(), "")
            when (formattedPhone.length) {
                13 -> {
                    formattedPhone = "0" + formattedPhone.substring(4)
                }
                12 -> {
                    formattedPhone = "0" + formattedPhone.substring(3)
                }
                10 -> {
                    return formattedPhone
                }
            }
            return formattedPhone
        }
    }
}