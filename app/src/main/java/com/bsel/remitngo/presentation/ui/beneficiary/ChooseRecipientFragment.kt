package com.bsel.remitngo.presentation.ui.beneficiary

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.BeneficiaryAdapter
import com.bsel.remitngo.adapter.ContactsAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.beneficiary.beneficiary.ContactItem
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryData
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryItem
import com.bsel.remitngo.databinding.FragmentChooseRecipientBinding
import com.bsel.remitngo.presentation.di.Injector
import java.util.*
import javax.inject.Inject

class ChooseRecipientFragment : Fragment() {
    @Inject
    lateinit var beneficiaryViewModelFactory: BeneficiaryViewModelFactory
    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    private lateinit var binding: FragmentChooseRecipientBinding

    private val REQUEST_CONTACTS_PERMISSION = 1

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var contactsAdapter: ContactsAdapter

    private lateinit var beneficiaryAdapter: BeneficiaryAdapter

    private lateinit var personId: String
    private lateinit var customerId: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var customerEmail: String
    private lateinit var customerMobile: String
    private lateinit var customerDateOfBirth: String

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private lateinit var paymentType: String
    private lateinit var orderType: String

    private lateinit var sendAmount: String
    private lateinit var receiveAmount: String

    private lateinit var exchangeRate: String
    private lateinit var commission: String

    private lateinit var bankId: String
    private lateinit var branchId: String
    private lateinit var bankName: String
    private lateinit var payingAgentId: String

    private lateinit var benId: String
    private lateinit var beneficiaryId: String
    private lateinit var beneficiaryName: String
    private lateinit var beneficiaryPhoneNumber: String

    private lateinit var reasonId: String
    private lateinit var reasonName: String

    private lateinit var sourceOfIncomeId: String
    private lateinit var sourceOfIncomeName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_recipient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChooseRecipientBinding.bind(view)

        (requireActivity().application as Injector).createBeneficiarySubComponent().inject(this)

        beneficiaryViewModel =
            ViewModelProvider(this, beneficiaryViewModelFactory)[BeneficiaryViewModel::class.java]

        requestContactsPermission()

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        customerId = preferenceManager.loadData("personId").toString()
        firstName = preferenceManager.loadData("firstName").toString()
        lastName = preferenceManager.loadData("lastName").toString()
        customerEmail = preferenceManager.loadData("email").toString()
        customerMobile = preferenceManager.loadData("mobile").toString()
        customerDateOfBirth = preferenceManager.loadData("dob").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        paymentType = arguments?.getString("paymentType").toString()
        orderType = arguments?.getString("orderType").toString()

        sendAmount = arguments?.getString("sendAmount").toString()
        receiveAmount = arguments?.getString("receiveAmount").toString()

        exchangeRate = arguments?.getString("exchangeRate").toString()
        commission = arguments?.getString("commission").toString()

        bankId = arguments?.getString("bankId").toString()
        branchId = arguments?.getString("branchId").toString()
        bankName = arguments?.getString("bankName").toString()
        payingAgentId = arguments?.getString("payingAgentId").toString()

        benId = arguments?.getString("benId").toString()
        beneficiaryId = arguments?.getString("beneficiaryId").toString()
        beneficiaryName = arguments?.getString("beneficiaryName").toString()
        beneficiaryPhoneNumber = arguments?.getString("beneficiaryPhoneNumber").toString()

        reasonId = arguments?.getString("reasonId").toString()
        reasonName = arguments?.getString("reasonName").toString()

        sourceOfIncomeId = arguments?.getString("sourceOfIncomeId").toString()
        sourceOfIncomeName = arguments?.getString("sourceOfIncomeName").toString()

        binding.btnAddBeneficiary.setOnClickListener {
            val bundle = Bundle().apply {
                putString("paymentType", paymentType)
                putString("orderType", orderType)
                putString("sendAmount", sendAmount)
                putString("receiveAmount", receiveAmount)
                putString("exchangeRate", exchangeRate)
                putString("commission", commission)

                putString("bankId", bankId)
                putString("branchId", branchId)
                putString("bankName", bankName)
                putString("payingAgentId", payingAgentId)

                putString("benId", benId)
                putString("beneficiaryId", beneficiaryId)
                putString("beneficiaryName", beneficiaryName)
                putString("beneficiaryPhoneNumber", beneficiaryPhoneNumber)

                putString("reasonId", reasonId)
                putString("reasonName", reasonName)

                putString("sourceOfIncomeId", sourceOfIncomeId)
                putString("sourceOfIncomeName", sourceOfIncomeName)
            }
            findNavController().navigate(
                R.id.action_nav_choose_recipient_to_nav_save_recipient,
                bundle
            )
        }

        if (orderType=="null"){
            val getBeneficiaryItem = GetBeneficiaryItem(
                customerId = customerId.toInt(),
                countryId = 1,
            )
            beneficiaryViewModel.getBeneficiary(getBeneficiaryItem)
            observeGetBeneficiaryResult()
        }else{
            val getBeneficiaryItem = GetBeneficiaryItem(
                customerId = customerId.toInt(),
                countryId = 1,
            )
            beneficiaryViewModel.getBeneficiary(getBeneficiaryItem)
            observeGetBeneficiaryResult()
        }

    }

    private fun observeGetBeneficiaryResult() {
        beneficiaryViewModel.getBeneficiaryResult.observe(this) { result ->
            try {
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
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun recipientItem(selectedItem: GetBeneficiaryData) {

        benId=selectedItem.beneficiaryId.toString()
        beneficiaryId=selectedItem.benePersonId.toString()
        beneficiaryName=selectedItem.beneName.toString()
        beneficiaryPhoneNumber=selectedItem.mobile.toString()

        val bundle = Bundle().apply {
            putString("paymentType", paymentType)
            putString("orderType", orderType)
            putString("sendAmount", sendAmount)
            putString("receiveAmount", receiveAmount)
            putString("exchangeRate", exchangeRate)
            putString("commission", commission)

            putString("bankId", bankId)
            putString("branchId", branchId)
            putString("bankName", bankName)
            putString("payingAgentId", payingAgentId)

            putString("benId", benId)
            putString("beneficiaryId", beneficiaryId)
            putString("beneficiaryName", beneficiaryName)
            putString("beneficiaryPhoneNumber", beneficiaryPhoneNumber)

            putString("reasonId", reasonId)
            putString("reasonName", reasonName)

            putString("sourceOfIncomeId", sourceOfIncomeId)
            putString("sourceOfIncomeName", sourceOfIncomeName)
        }

        if (orderType=="2"){
            findNavController().navigate(
                R.id.action_nav_choose_recipient_to_nav_review,
                bundle
            )
        }else{
            findNavController().navigate(
                R.id.action_nav_choose_recipient_to_nav_choose_bank,
                bundle
            )
        }

    }

    private fun requestContactsPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CONTACTS_PERMISSION
            )
        } else {
            // Permission is already granted, proceed with your code
            retrieveAndDisplayContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CONTACTS_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with your code
                retrieveAndDisplayContacts()
            } else {
                // Permission denied, handle accordingly (e.g., show a message)
            }
        }
    }

    private fun retrieveAndDisplayContacts() {
        try {
            val contacts = getContacts()
            if (!::contactsAdapter.isInitialized) {
                binding.contactRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                contactsAdapter = ContactsAdapter(
                    selectedItem = { selectedItem: ContactItem ->
                        contactItem(selectedItem)
                        binding.beneficiarySearch.setQuery("", false)
                    }
                )
                binding.contactRecyclerView.adapter = contactsAdapter
                contactsAdapter.setList(contacts)
                contactsAdapter.notifyDataSetChanged()

                binding.beneficiarySearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        contactsAdapter.filter(newText.orEmpty())
                        return true
                    }
                })

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun contactItem(selectedItem: ContactItem) {
        beneficiaryName= selectedItem.name
        beneficiaryPhoneNumber= selectedItem.phoneNumber

        val bundle = Bundle().apply {
            putString("paymentType", paymentType)
            putString("orderType", orderType)
            putString("sendAmount", sendAmount)
            putString("receiveAmount", receiveAmount)
            putString("exchangeRate", exchangeRate)
            putString("commission", commission)

            putString("bankId", bankId)
            putString("branchId", branchId)
            putString("bankName", bankName)
            putString("payingAgentId", payingAgentId)

            putString("benId", benId)
            putString("beneficiaryId", beneficiaryId)
            putString("beneficiaryName", beneficiaryName)
            putString("beneficiaryPhoneNumber", beneficiaryPhoneNumber)

            putString("reasonId", reasonId)
            putString("reasonName", reasonName)

            putString("sourceOfIncomeId", sourceOfIncomeId)
            putString("sourceOfIncomeName", sourceOfIncomeName)
        }
        findNavController().navigate(
            R.id.action_nav_choose_recipient_to_nav_save_recipient,
            bundle
        )
    }

    @SuppressLint("Range")
    private fun getContacts(): List<ContactItem> {
        val contactItems = mutableListOf<ContactItem>()

        // Check and request permissions if needed

        val contentResolver: ContentResolver = requireContext().contentResolver
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            var counter = 0
            while (it.moveToNext()) {
                val contactId = it.getLong(it.getColumnIndex(ContactsContract.Contacts._ID))
                val nameColumnIndex =
                    it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

                // Check if the name column is present and not null
                if (nameColumnIndex != -1 && !it.isNull(nameColumnIndex)) {
                    val name = it.getString(nameColumnIndex)
                    val phoneNumber = getPhoneNumber(contactId)
                    val firstLetter = name.takeUnless { it.isNullOrEmpty() }?.get(0).toString()

                    contactItems.add(ContactItem(contactId, name, phoneNumber, firstLetter))

                    // Increment the counter, and if it reaches 3, break the loop
                    counter++
                    if (counter == 5) {
                        break
                    }
                }
            }
        }

        cursor?.close()
        return contactItems
    }

    @SuppressLint("Range")
    private fun getPhoneNumber(contactId: Long): String {
        val phoneNumberCursor: Cursor? =
            requireContext().contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                arrayOf(contactId.toString()),
                null
            )

        phoneNumberCursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
        }

        phoneNumberCursor?.close()
        return ""
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

    private fun getIPAddress(context: Context): String? {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        return String.format(
            Locale.getDefault(),
            "%d.%d.%d.%d",
            ipAddress and 0xff,
            ipAddress shr 8 and 0xff,
            ipAddress shr 16 and 0xff,
            ipAddress shr 24 and 0xff
        )
    }

}