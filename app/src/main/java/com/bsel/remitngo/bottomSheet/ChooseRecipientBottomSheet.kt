package com.bsel.remitngo.bottomSheet

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.content.res.Resources
import android.database.Cursor
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.view.View
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.BeneficiaryAdapter
import com.bsel.remitngo.adapter.ContactsAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnBeneficiarySelectedListener
import com.bsel.remitngo.data.interfaceses.OnSaveBeneficiarySelectedListener
import com.bsel.remitngo.data.model.beneficiary.beneficiary.ContactItem
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryData
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryItem
import com.bsel.remitngo.databinding.ChooseRecipientLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModel
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import javax.inject.Inject

class ChooseRecipientBottomSheet : BottomSheetDialogFragment(), OnSaveBeneficiarySelectedListener {
    @Inject
    lateinit var beneficiaryViewModelFactory: BeneficiaryViewModelFactory
    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    private lateinit var binding: ChooseRecipientLayoutBinding

    var itemSelectedListener: OnBeneficiarySelectedListener? = null

    private lateinit var chooseRecipientBehavior: BottomSheetBehavior<*>

    private val saveRecipientBottomSheet: SaveRecipientBottomSheet by lazy { SaveRecipientBottomSheet() }
    private val chooseBankBottomSheet: ChooseBankBottomSheet by lazy { ChooseBankBottomSheet() }

    private val REQUEST_CONTACTS_PERMISSION = 1

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var contactsAdapter: ContactsAdapter

    private lateinit var beneficiaryAdapter: BeneficiaryAdapter

    private var personId: Int = 0
    private var customerId: Int = 0
    private var benePersonId: Int = 0
    private var beneId: Int = 0
    private var beneAccountName: String? = null
    private var beneMobile: String? = null

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private var orderType: Int = 0
    private var beneBankId: Int = 0
    private var beneWalletId: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.choose_recipient_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        chooseRecipientBehavior = BottomSheetBehavior.from(view.parent as View)
        chooseRecipientBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        chooseRecipientBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull view: View, i: Int) {
                when (i) {
                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                }
            }

            override fun onSlide(@NonNull view: View, v: Float) {}
        })

        (requireActivity().application as Injector).createBeneficiarySubComponent().inject(this)

        beneficiaryViewModel =
            ViewModelProvider(this, beneficiaryViewModelFactory)[BeneficiaryViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }

        requestContactsPermission()

        preferenceManager = PreferenceManager(requireContext())
        try {
            personId = preferenceManager.loadData("personId").toString().toInt()
        }catch (e:NumberFormatException){
            e.localizedMessage
        }
        try {
            customerId = preferenceManager.loadData("customerId").toString().toInt()
        }catch (e:NumberFormatException){
            e.localizedMessage
        }

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        binding.btnAddBeneficiary.setOnClickListener {
            saveRecipientBottomSheet.itemSelectedListener = this
            saveRecipientBottomSheet.setOrderType(orderType, "", "")
            saveRecipientBottomSheet.show(childFragmentManager, saveRecipientBottomSheet.tag)
        }

        val getBeneficiaryItem = GetBeneficiaryItem(
            customerId = customerId,
            countryId = 1,
        )
        beneficiaryViewModel.getBeneficiary(getBeneficiaryItem)
        observeGetBeneficiaryResult()

        return bottomSheet
    }

    fun setOrderType(orderType: Int, benePersonId: Int, beneBankId: Int, beneWalletId: Int) {
        this.orderType = orderType
        this.benePersonId = benePersonId
        this.beneBankId = beneBankId
        this.beneWalletId = beneWalletId
    }

    private fun observeGetBeneficiaryResult() {
        beneficiaryViewModel.getBeneficiaryResult.observe(this) { result ->
            if (result == null) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setIcon(R.drawable.warning)
                builder.setTitle("Warning!")
                builder.setMessage("Recipient not loading properly.")
                builder.setPositiveButton("Close") { _: DialogInterface, _: Int ->
                    dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            } else {
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
                } catch (e: NullPointerException) {
                    e.localizedMessage
                }
            }
        }
    }

    private fun recipientItem(selectedItem: GetBeneficiaryData) {
        benePersonId = selectedItem.benePersonId!!
        beneId = selectedItem.beneficiaryId!!
        beneAccountName = selectedItem.beneName.toString()
        beneMobile = selectedItem.mobile.toString()
        if (orderType == 2) {
            itemSelectedListener?.onChooseRecipientItemSelected(selectedItem)
            dismiss()
        } else {
            chooseBankBottomSheet.setOrderType(orderType, benePersonId)
            chooseBankBottomSheet.show(childFragmentManager, chooseBankBottomSheet.tag)
//            itemSelectedListener?.onChooseRecipientItemSelected(selectedItem)
//            dismiss()
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
        beneAccountName = selectedItem.name
        beneMobile = selectedItem.phoneNumber

        saveRecipientBottomSheet.itemSelectedListener = this
        saveRecipientBottomSheet.setOrderType(orderType, beneAccountName, beneMobile)
        saveRecipientBottomSheet.show(childFragmentManager, saveRecipientBottomSheet.tag)
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

    override fun onSaveBeneficiaryItemSelected(selectedItem: String) {
        val getBeneficiaryItem = GetBeneficiaryItem(
            customerId = customerId,
            countryId = 1,
        )
        beneficiaryViewModel.getBeneficiary(getBeneficiaryItem)
    }

    override fun onStart() {
        super.onStart()
        chooseRecipientBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }


}