package com.bsel.remitngo.bottomSheet

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.BeneficiaryAdapter
import com.bsel.remitngo.adapter.ContactAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnBankAndWalletSelectedListener
import com.bsel.remitngo.data.interfaceses.OnBeneficiarySelectedListener
import com.bsel.remitngo.data.interfaceses.OnSaveBeneficiarySelectedListener
import com.bsel.remitngo.data.model.bank.bank_account.GetBankData
import com.bsel.remitngo.data.model.beneficiary.beneficiary.Contact
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryData
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryItem
import com.bsel.remitngo.databinding.ChooseRecipientLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModel
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModelFactory
import com.bsel.remitngo.presentation.ui.beneficiary.ContactViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import javax.inject.Inject

class ChooseRecipientBottomSheet : BottomSheetDialogFragment(),
    OnBankAndWalletSelectedListener, OnSaveBeneficiarySelectedListener {
    @Inject
    lateinit var beneficiaryViewModelFactory: BeneficiaryViewModelFactory
    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    private lateinit var binding: ChooseRecipientLayoutBinding

    var itemSelectedListener: OnBeneficiarySelectedListener? = null

    private lateinit var chooseRecipientBehavior: BottomSheetBehavior<*>

    private val saveRecipientBottomSheet: SaveRecipientBottomSheet by lazy { SaveRecipientBottomSheet() }
    private val chooseBankBottomSheet: ChooseBankBottomSheet by lazy { ChooseBankBottomSheet() }

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var contactAdapter: ContactAdapter
    private val REQUEST_CONTACTS_PERMISSION = 1

    private lateinit var beneficiaryAdapter: BeneficiaryAdapter

    private lateinit var preferenceManager: PreferenceManager

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private var customerId: Int = 0
    private var personId: Int = 0
    private var customerEmail: String? = null
    private var customerMobile: String? = null

    private var beneId: Int = 0
    private var benePersonId: Int = 0

    private var orderType: Int = 0

    private var beneBankId: Int = 0
    private var beneBankName: String? = null

    private var beneBranchId: Int = 0
    private var beneBranchName: String? = null

    private var beneWalletId: Int = 0
    private var beneWalletName: String? = null

    private var beneAccountName: String? = null
    private var beneAccountNo: String? = null
    private var beneWalletNo: String? = null

    private var beneMobile: String? = null

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

        contactViewModel = ContactViewModel(requireContext().applicationContext)

        binding.cancelButton.setOnClickListener { dismiss() }

        preferenceManager = PreferenceManager(requireContext())
        try {
            personId = preferenceManager.loadData("personId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            customerId = preferenceManager.loadData("customerId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            customerEmail = preferenceManager.loadData("customerEmail").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }
        try {
            customerMobile = preferenceManager.loadData("customerMobile").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        binding.beneSearch.visibility = View.VISIBLE
        binding.beneficiaryRecyclerView.visibility = View.VISIBLE
        binding.beneficiaryRecyclerView.setBackgroundResource(R.color.white)
        binding.btnRecipient.setBackgroundResource(R.color.white)

        binding.contactSearch.visibility = View.GONE
        binding.contactRecyclerView.visibility = View.GONE
        binding.contactRecyclerView.setBackgroundResource(R.color.grey)
        binding.btnContact.setBackgroundResource(R.color.grey)

        binding.btnRecipient.setOnClickListener {
            binding.beneSearch.visibility = View.VISIBLE
            binding.beneficiaryRecyclerView.visibility = View.VISIBLE
            binding.beneficiaryRecyclerView.setBackgroundResource(R.color.white)
            binding.btnRecipient.setBackgroundResource(R.color.white)

            binding.contactSearch.visibility = View.GONE
            binding.contactRecyclerView.visibility = View.GONE
            binding.contactRecyclerView.setBackgroundResource(R.color.grey)
            binding.btnContact.setBackgroundResource(R.color.grey)
        }
        binding.btnContact.setOnClickListener {
            binding.beneSearch.visibility = View.GONE
            binding.beneficiaryRecyclerView.visibility = View.GONE
            binding.beneficiaryRecyclerView.setBackgroundResource(R.color.grey)
            binding.btnRecipient.setBackgroundResource(R.color.grey)

            binding.contactSearch.visibility = View.VISIBLE
            binding.contactRecyclerView.visibility = View.VISIBLE
            binding.contactRecyclerView.setBackgroundResource(R.color.white)
            binding.btnContact.setBackgroundResource(R.color.white)
        }

        binding.btnAddBeneficiary.setOnClickListener {
            saveRecipientBottomSheet.itemSelectedListener = this
            saveRecipientBottomSheet.setOrderType(
                orderType,
                beneBankId,
                beneBankName,
                beneBranchId,
                beneBranchName,
                beneWalletId,
                beneWalletName,
                beneId,
                benePersonId,
                beneAccountName,
                beneAccountNo,
                beneWalletNo,
                beneMobile
            )
            saveRecipientBottomSheet.show(childFragmentManager, saveRecipientBottomSheet.tag)
        }

        val getBeneficiaryItem = GetBeneficiaryItem(
            customerId = customerId,
            countryId = 1,
        )
        beneficiaryViewModel.getBeneficiary(getBeneficiaryItem)
        observeGetBeneficiaryResult()

        requestContactsPermission()

        return bottomSheet
    }

    fun setOrderType(
        orderType: Int,
        beneBankId: Int,
        beneBankName: String?,
        beneBranchId: Int,
        beneBranchName: String?,
        beneWalletId: Int,
        beneWalletName: String?,
        beneId: Int,
        benePersonId: Int,
        beneAccountName: String?,
        beneAccountNo: String?,
        beneWalletNo: String?,
        beneMobile: String?
    ) {
        this.orderType = orderType
        this.beneBankId = beneBankId
        this.beneBankName = beneBankName
        this.beneBranchId = beneBranchId
        this.beneBranchName = beneBranchName
        this.beneWalletId = beneWalletId
        this.beneWalletName = beneWalletName
        this.beneId = beneId
        this.benePersonId = benePersonId
        this.beneAccountName = beneAccountName
        this.beneAccountNo = beneAccountNo
        this.beneWalletNo = beneWalletNo
        this.beneMobile = beneMobile
    }

    private fun observeGetBeneficiaryResult() {
        beneficiaryViewModel.getBeneficiaryResult.observe(this) { result ->
            try {
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
                                    binding.beneSearch.setQuery("", false)
                                }
                            )
                            binding.beneficiaryRecyclerView.adapter = beneficiaryAdapter
                            beneficiaryAdapter.setList(result.data as List<GetBeneficiaryData>)
                            beneficiaryAdapter.notifyDataSetChanged()

                            binding.beneSearch.setOnQueryTextListener(object :
                                SearchView.OnQueryTextListener {
                                override fun onQueryTextSubmit(query: String?): Boolean {
                                    return false
                                }

                                override fun onQueryTextChange(newText: String?): Boolean {
                                    beneficiaryAdapter.beneficiaryFilter(newText.orEmpty())
                                    return true
                                }
                            })

                            binding.beneficiaryRecyclerView.addOnScrollListener(
                                object : RecyclerView.OnScrollListener() {
                                    override fun onScrolled(
                                        recyclerView: RecyclerView,
                                        dx: Int,
                                        dy: Int
                                    ) {
                                        super.onScrolled(
                                            recyclerView,
                                            dx,
                                            dy
                                        )
                                        if (dy > 10 && binding.btnAddBeneficiary.isExtended) {
                                            binding.btnAddBeneficiary.shrink()
                                        }
                                        if (dy < -10 && !binding.btnAddBeneficiary.isExtended) {
                                            binding.btnAddBeneficiary.extend()
                                        }
                                        if (!recyclerView.canScrollVertically(
                                                -1
                                            )
                                        ) {
                                            binding.btnAddBeneficiary.extend()
                                        }
                                    }
                                })

                        }
                    } catch (e: NullPointerException) {
                        e.localizedMessage
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun recipientItem(selectedItem: GetBeneficiaryData) {
        benePersonId = selectedItem.benePersonId!!
        beneId = selectedItem.beneficiaryId!!
        beneAccountName = selectedItem.beneName.toString()
        beneMobile = selectedItem.mobile.toString()
        if (orderType == 2 || orderType == 4) {
            itemSelectedListener?.onChooseRecipientItemSelected(selectedItem)
            dismiss()
        } else {
            itemSelectedListener?.onChooseRecipientItemSelected(selectedItem)
            chooseBankBottomSheet.itemSelectedListener = this
            chooseBankBottomSheet.setOrderType(
                orderType,
                beneBankId,
                beneBankName,
                beneBranchId,
                beneBranchName,
                beneWalletId,
                beneWalletName,
                beneId,
                benePersonId,
                beneAccountName,
                beneAccountNo,
                beneWalletNo,
                beneMobile
            )
            chooseBankBottomSheet.show(childFragmentManager, chooseBankBottomSheet.tag)
        }
    }

    private fun requestContactsPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CONTACTS_PERMISSION
            )
        } else {
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
                retrieveAndDisplayContacts()
            }
        }
    }

    private fun retrieveAndDisplayContacts() {
        try {
            binding.contactRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            contactAdapter = ContactAdapter(
                selectedItem = { selectedItem: Contact ->
                    contactItem(selectedItem)
                    binding.contactSearch.setQuery("", false)
                }
            )
            binding.contactRecyclerView.adapter = contactAdapter
            contactAdapter.setList((contactViewModel.getContacts()))
            contactAdapter.notifyDataSetChanged()

            binding.contactSearch.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    contactAdapter.contactFilter(newText.orEmpty())
                    return true
                }
            })

            binding.contactRecyclerView.addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(
                        recyclerView: RecyclerView,
                        dx: Int,
                        dy: Int
                    ) {
                        super.onScrolled(
                            recyclerView,
                            dx,
                            dy
                        )
                        if (dy > 10 && binding.btnAddBeneficiary.isExtended) {
                            binding.btnAddBeneficiary.shrink()
                        }
                        if (dy < -10 && !binding.btnAddBeneficiary.isExtended) {
                            binding.btnAddBeneficiary.extend()
                        }
                        if (!recyclerView.canScrollVertically(
                                -1
                            )
                        ) {
                            binding.btnAddBeneficiary.extend()
                        }
                    }
                })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun contactItem(selectedItem: Contact) {
        beneAccountName = selectedItem.name
        beneMobile = selectedItem.phoneNumber

        saveRecipientBottomSheet.itemSelectedListener = this
        saveRecipientBottomSheet.setOrderType(
            orderType,
            beneBankId,
            beneBankName,
            beneBranchId,
            beneBranchName,
            beneWalletId,
            beneWalletName,
            beneId,
            benePersonId,
            beneAccountName,
            beneAccountNo,
            beneWalletNo,
            beneMobile
        )
        saveRecipientBottomSheet.show(childFragmentManager, saveRecipientBottomSheet.tag)
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

        binding.beneSearch.visibility = View.VISIBLE
        binding.beneficiaryRecyclerView.visibility = View.VISIBLE
        binding.beneficiaryRecyclerView.setBackgroundResource(R.color.white)
        binding.btnRecipient.setBackgroundResource(R.color.white)

        binding.contactSearch.visibility = View.GONE
        binding.contactRecyclerView.visibility = View.GONE
        binding.contactRecyclerView.setBackgroundResource(R.color.grey)
        binding.btnContact.setBackgroundResource(R.color.grey)
    }

    override fun onBankAndWalletItemSelected(selectedItem: GetBankData) {
        itemSelectedListener?.onBankAndWalletItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        chooseRecipientBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}