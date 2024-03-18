package com.bsel.remitngo.presentation.ui.beneficiary

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.BeneficiaryAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryData
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryItem
import com.bsel.remitngo.databinding.FragmentChooseBeneficiaryBinding
import com.bsel.remitngo.presentation.di.Injector
import javax.inject.Inject

class ChooseBeneficiaryFragment : Fragment() {
    @Inject
    lateinit var beneficiaryViewModelFactory: BeneficiaryViewModelFactory
    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    private lateinit var binding: FragmentChooseBeneficiaryBinding

    private lateinit var preferenceManager: PreferenceManager

//    private lateinit var contactsAdapter: ContactsAdapter

    private lateinit var beneficiaryAdapter: BeneficiaryAdapter

    private lateinit var orderType: String
    private lateinit var paymentType: String

    private lateinit var send_amount: String
    private lateinit var receive_amount: String

    private lateinit var bankId: String
    private lateinit var bankName: String

    private lateinit var payingAgentId: String
    private lateinit var payingAgentName: String

    private lateinit var exchangeRate: String
    private lateinit var bankCommission: String
    private lateinit var cardCommission: String

    private lateinit var deviceId: String
    private lateinit var personId: String
    private var countryId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_beneficiary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChooseBeneficiaryBinding.bind(view)

        (requireActivity().application as Injector).createBeneficiarySubComponent().inject(this)

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

        beneficiaryViewModel =
            ViewModelProvider(this, beneficiaryViewModelFactory)[BeneficiaryViewModel::class.java]

        orderType = arguments?.getString("orderType").toString()
        paymentType = arguments?.getString("paymentType").toString()

        send_amount = arguments?.getString("send_amount").toString()
        receive_amount = arguments?.getString("receive_amount").toString()

        bankId = arguments?.getString("bankId").toString()
        bankName = arguments?.getString("bankName").toString()

        payingAgentId = arguments?.getString("payingAgentId").toString()
        payingAgentName = arguments?.getString("payingAgentName").toString()

        exchangeRate = arguments?.getString("exchangeRate").toString()
        bankCommission = arguments?.getString("bankCommission").toString()
        cardCommission = arguments?.getString("cardCommission").toString()

        binding.btnBeneficiary.setOnClickListener {
            val bundle = Bundle().apply {
                putString("orderType", orderType)
                putString("paymentType", paymentType)

                putString("send_amount", send_amount)
                putString("receive_amount", receive_amount)

                putString("bankId", bankId)
                putString("bankName", bankName)

                putString("payingAgentId", payingAgentId)
                putString("payingAgentName", payingAgentName)

                putString("exchangeRate", exchangeRate)
                putString("bankCommission", bankCommission)
                putString("cardCommission", cardCommission)
            }
            findNavController().navigate(
                R.id.action_nav_choose_beneficiary_to_nav_save_beneficiary,
                bundle
            )
        }

        // Retrieve and display contacts
        //retrieveAndDisplayContacts()

        countryId = 1
        val getBeneficiaryItem = GetBeneficiaryItem(
            deviceId = deviceId,
            personId = personId.toInt(),
            orderType = orderType.toInt(),
            countryId = countryId
        )
        beneficiaryViewModel.getBeneficiary(getBeneficiaryItem)

        observeGetBeneficiaryResult()
    }

    private fun observeGetBeneficiaryResult() {
        beneficiaryViewModel.getBeneficiaryResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.beneficiaryRecyclerView.layoutManager =
                    LinearLayoutManager(requireActivity())
                beneficiaryAdapter = BeneficiaryAdapter(
                    selectedItem = { selectedItem: GetBeneficiaryData ->
                        recipientItem(selectedItem)
                        binding.beneficiarySearch.setQuery("", false)
                    }
                )
                binding.beneficiaryRecyclerView.adapter = beneficiaryAdapter
                beneficiaryAdapter.setList(result.data as List<GetBeneficiaryData>)
                beneficiaryAdapter.notifyDataSetChanged()

                binding.beneficiarySearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        beneficiaryAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
            }
        }
    }

    private fun recipientItem(selectedItem: GetBeneficiaryData) {
        val bundle = Bundle().apply {
            putString("orderType", orderType)
            putString("paymentType", paymentType)

            putString("send_amount", send_amount)
            putString("receive_amount", receive_amount)

            putString("bankId", bankId)
            putString("bankName", bankName)

            putString("payingAgentId", payingAgentId)
            putString("payingAgentName", payingAgentName)

            putString("exchangeRate", exchangeRate.toString())
            putString("bankCommission", bankCommission.toString())
            putString("cardCommission", cardCommission.toString())

            putString("cusBankInfoId", selectedItem.id.toString())
            putString("recipientName", selectedItem.name.toString())
            putString("recipientMobile", selectedItem.mobile.toString())
            putString("recipientAddress", selectedItem.address.toString())
        }
        findNavController().navigate(
            R.id.action_nav_choose_beneficiary_to_nav_choose_bank,
            bundle
        )
    }

    private fun getDeviceId(context: Context): String {
        val deviceId: String

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } else {
            @Suppress("DEPRECATION")
            deviceId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

        return deviceId
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