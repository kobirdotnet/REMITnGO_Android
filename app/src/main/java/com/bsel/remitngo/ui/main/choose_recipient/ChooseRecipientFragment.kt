package com.bsel.remitngo.ui.main.choose_recipient

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.ContactsAdapter
import com.bsel.remitngo.adapter.RecipientsAdapter
import com.bsel.remitngo.databinding.FragmentChooseRecipientBinding
import com.bsel.remitngo.model.BankItem
import com.bsel.remitngo.model.ContactItem
import com.bsel.remitngo.model.RecipientItem

class ChooseRecipientFragment : Fragment() {

    private lateinit var binding: FragmentChooseRecipientBinding

//    private lateinit var contactsAdapter: ContactsAdapter

    private lateinit var recipientsAdapter: RecipientsAdapter

    private lateinit var recipientItems: List<RecipientItem>

    private lateinit var orderType: String
    private lateinit var paymentType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_recipient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChooseRecipientBinding.bind(view)

        orderType = arguments?.getString("orderType").toString()
        paymentType = arguments?.getString("paymentType").toString()

        binding.addRecipient.setOnClickListener {
            val bundle = Bundle().apply {
                putString("orderType", orderType)
                putString("paymentType", paymentType)
            }
            findNavController().navigate(
                R.id.action_nav_choose_recipient_to_nav_recipient_details,
                bundle
            )
        }

        recipientItems = arrayOf(
            RecipientItem(0, "KOBIRUL ISLAM", "0123456789", "M"),
            RecipientItem(1, "ABDUL BARI", "0123456789", "A"),
            RecipientItem(2, "ZUBAIR ALAM", "0123456789", "Z")
        ).toList()

        binding.recipientRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recipientsAdapter = RecipientsAdapter(
            selectedItem = { selectedItem: RecipientItem ->
                recipientItem(selectedItem)
                binding.recipientSearch.setQuery("", false)
            }
        )
        binding.recipientRecyclerView.adapter = recipientsAdapter
        recipientsAdapter.setList(recipientItems)
        recipientsAdapter.notifyDataSetChanged()

        // Retrieve and display contacts
//        retrieveAndDisplayContacts()

    }

    private fun recipientItem(selectedItem: RecipientItem) {
        val bundle = Bundle().apply {
            putString("orderType", orderType)
            putString("paymentType", paymentType)
        }
        findNavController().navigate(
            R.id.action_nav_choose_recipient_to_nav_confirm_transfer,
            bundle
        )
    }

//    private fun retrieveAndDisplayContacts() {
//        try {
//            val contacts = getContacts()
//            if (!::contactsAdapter.isInitialized) {
//                binding.contactRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
//                contactsAdapter = ContactsAdapter(
//                    selectedItem = { selectedItem: ContactItem ->
//                        contactItem(selectedItem)
//                        binding.recipientSearch.setQuery("", false)
//                    }
//                )
//                binding.contactRecyclerView.adapter = contactsAdapter
//                contactsAdapter.setList(contacts)
//                contactsAdapter.notifyDataSetChanged()
//
//                binding.recipientSearch.setOnQueryTextListener(object :
//                    SearchView.OnQueryTextListener {
//                    override fun onQueryTextSubmit(query: String?): Boolean {
//                        return false
//                    }
//
//                    override fun onQueryTextChange(newText: String?): Boolean {
//                        recipientsAdapter.filter(newText.orEmpty())
//                        contactsAdapter.filter(newText.orEmpty())
//                        return true
//                    }
//                })
//
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun contactItem(selectedItem: ContactItem) {
//        val bundle = Bundle().apply {
//            putString("pMode", pMode)
//        }
//        findNavController().navigate(
//            R.id.action_nav_choose_recipient_to_nav_recipient_details,
//            bundle
//        )
//    }
//
//    @SuppressLint("Range")
//    private fun getContacts(): List<ContactItem> {
//        val contactItems = mutableListOf<ContactItem>()
//
//        // Check and request permissions if needed
//
//        val contentResolver: ContentResolver = requireContext().contentResolver
//        val cursor: Cursor? = contentResolver.query(
//            ContactsContract.Contacts.CONTENT_URI,
//            null,
//            null,
//            null,
//            null
//        )
//
//        cursor?.use {
//            var counter = 0
//            while (it.moveToNext()) {
//                val contactId = it.getLong(it.getColumnIndex(ContactsContract.Contacts._ID))
//                val nameColumnIndex =
//                    it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
//
//                // Check if the name column is present and not null
//                if (nameColumnIndex != -1 && !it.isNull(nameColumnIndex)) {
//                    val name = it.getString(nameColumnIndex)
//                    val phoneNumber = getPhoneNumber(contactId)
//                    val firstLetter = name.takeUnless { it.isNullOrEmpty() }?.get(0).toString()
//
//                    contactItems.add(ContactItem(contactId, name, phoneNumber, firstLetter))
//
//                    // Increment the counter, and if it reaches 3, break the loop
//                    counter++
//                    if (counter == 5) {
//                        break
//                    }
//                }
//            }
//        }
//
//        cursor?.close()
//        return contactItems
//    }
//
//    @SuppressLint("Range")
//    private fun getPhoneNumber(contactId: Long): String {
//        val phoneNumberCursor: Cursor? =
//            requireContext().contentResolver.query(
//                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                null,
//                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                arrayOf(contactId.toString()),
//                null
//            )
//
//        phoneNumberCursor?.use {
//            if (it.moveToFirst()) {
//                return it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//            }
//        }
//
//        phoneNumberCursor?.close()
//        return ""
//    }

}