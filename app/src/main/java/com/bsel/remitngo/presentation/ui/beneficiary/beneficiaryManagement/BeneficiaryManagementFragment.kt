package com.bsel.remitngo.presentation.ui.beneficiary.beneficiaryManagement

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.BeneficiaryManagementAdapter
import com.bsel.remitngo.adapter.ContactAdapter
import com.bsel.remitngo.bottomSheet.ChooseBankBottomSheet
import com.bsel.remitngo.bottomSheet.SaveRecipientBottomSheet
import com.bsel.remitngo.bottomSheet.UpdateAndDeleteBeneficiaryBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnBankAndWalletSelectedListener
import com.bsel.remitngo.data.interfaceses.OnBeneficiarySelectedListener
import com.bsel.remitngo.data.interfaceses.OnSaveBeneficiarySelectedListener
import com.bsel.remitngo.data.model.bank.bank_account.GetBankData
import com.bsel.remitngo.data.model.beneficiary.beneficiary.Contact
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryData
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryItem
import com.bsel.remitngo.databinding.FragmentBeneficiaryManagementBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModel
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModelFactory
import com.bsel.remitngo.presentation.ui.beneficiary.ContactViewModel
import java.util.*
import javax.inject.Inject

class BeneficiaryManagementFragment : Fragment(),
    OnBankAndWalletSelectedListener, OnSaveBeneficiarySelectedListener {
    @Inject
    lateinit var beneficiaryViewModelFactory: BeneficiaryViewModelFactory
    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    private lateinit var binding: FragmentBeneficiaryManagementBinding

    var itemSelectedListener: OnBeneficiarySelectedListener? = null

    private val saveRecipientBottomSheet: SaveRecipientBottomSheet by lazy { SaveRecipientBottomSheet() }
    private val updateAndDeleteBeneficiaryBottomSheet: UpdateAndDeleteBeneficiaryBottomSheet by lazy { UpdateAndDeleteBeneficiaryBottomSheet() }
    private val chooseBankBottomSheet: ChooseBankBottomSheet by lazy { ChooseBankBottomSheet() }

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var contactAdapter: ContactAdapter
    private val REQUEST_CONTACTS_PERMISSION = 1

    private lateinit var beneficiaryManagementAdapter: BeneficiaryManagementAdapter

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beneficiary_management, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBeneficiaryManagementBinding.bind(view)

        (requireActivity().application as Injector).createBeneficiarySubComponent().inject(this)

        beneficiaryViewModel =
            ViewModelProvider(this, beneficiaryViewModelFactory)[BeneficiaryViewModel::class.java]

        contactViewModel = ContactViewModel(requireContext().applicationContext)

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
        binding.btnBeneficiary.setBackgroundResource(R.color.white)

        binding.contactSearch.visibility = View.GONE
        binding.contactRecyclerView.visibility = View.GONE
        binding.contactRecyclerView.setBackgroundResource(R.color.grey)
        binding.btnContact.setBackgroundResource(R.color.grey)

        binding.btnBeneficiary.setOnClickListener {
            binding.beneSearch.visibility = View.VISIBLE
            binding.beneficiaryRecyclerView.visibility = View.VISIBLE
            binding.beneficiaryRecyclerView.setBackgroundResource(R.color.white)
            binding.btnBeneficiary.setBackgroundResource(R.color.white)

            binding.contactSearch.visibility = View.GONE
            binding.contactRecyclerView.visibility = View.GONE
            binding.contactRecyclerView.setBackgroundResource(R.color.grey)
            binding.btnContact.setBackgroundResource(R.color.grey)
        }
        binding.btnContact.setOnClickListener {
            binding.beneSearch.visibility = View.GONE
            binding.beneficiaryRecyclerView.visibility = View.GONE
            binding.beneficiaryRecyclerView.setBackgroundResource(R.color.grey)
            binding.btnBeneficiary.setBackgroundResource(R.color.grey)

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
                    val dialog = builder.create()
                    builder.setIcon(R.drawable.warning)
                    builder.setTitle("Warning!")
                    builder.setMessage("Recipient not loading properly.")
                    builder.setPositiveButton("Close") { _: DialogInterface, _: Int ->
                        dialog.dismiss()
                    }
                    dialog.show()
                } else {
                    try {
                        if (result!!.data != null) {
                            binding.beneficiaryRecyclerView.layoutManager =
                                LinearLayoutManager(requireActivity())
                            beneficiaryManagementAdapter = BeneficiaryManagementAdapter(
                                selectedItem = { selectedItem: GetBeneficiaryData ->
                                    beneficiaryItem(selectedItem)
                                    binding.beneSearch.setQuery("", false)
                                },
                                bankInfo = {bankInfo: GetBeneficiaryData ->
                                    bankInfo(bankInfo)
                                    binding.beneSearch.setQuery("", false)
                                },
                                walletInfo = {walletInfo: GetBeneficiaryData ->
                                    walletInfo(walletInfo)
                                    binding.beneSearch.setQuery("", false)
                                },
                                transfer = {transfer: GetBeneficiaryData ->
                                    transfer(transfer)
                                    binding.beneSearch.setQuery("", false)
                                }
                            )
                            binding.beneficiaryRecyclerView.adapter = beneficiaryManagementAdapter
                            beneficiaryManagementAdapter.setList(result.data as List<GetBeneficiaryData>)
                            beneficiaryManagementAdapter.notifyDataSetChanged()

                            binding.beneSearch.setOnQueryTextListener(object :
                                SearchView.OnQueryTextListener {
                                override fun onQueryTextSubmit(query: String?): Boolean {
                                    return false
                                }

                                override fun onQueryTextChange(newText: String?): Boolean {
                                    beneficiaryManagementAdapter.filter(newText.orEmpty())
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
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun beneficiaryItem(selectedItem: GetBeneficiaryData) {
        var beneName = selectedItem.beneName!!
        var benePersonId = selectedItem.benePersonId!!
        var beneId = selectedItem.beneficiaryId!!
        var countryId = selectedItem.countryId!!
        var countryName = selectedItem.countryName!!
        var firstName = selectedItem.firstName!!
        var lastName = selectedItem.lastName!!
        var flagiconName = selectedItem.flagiconName!!
        var beneMobile = selectedItem.mobile!!
        var hasTransactions = selectedItem.hasTransactions!!

        updateAndDeleteBeneficiaryBottomSheet.itemSelectedListener = this
        updateAndDeleteBeneficiaryBottomSheet.setBeneficiaryData(
            beneName,
            benePersonId,
            beneId,
            countryId,
            countryName,
            firstName,
            lastName,
            flagiconName,
            beneMobile,
            hasTransactions
        )
        updateAndDeleteBeneficiaryBottomSheet.show(childFragmentManager, updateAndDeleteBeneficiaryBottomSheet.tag)
    }
    private fun bankInfo(bankInfo: GetBeneficiaryData) {
        benePersonId = bankInfo.benePersonId!!
        beneId = bankInfo.beneficiaryId!!
        beneAccountName = bankInfo.beneName.toString()
        beneMobile = bankInfo.mobile.toString()
        if (orderType == 2) {
            itemSelectedListener?.onChooseRecipientItemSelected(bankInfo)
        } else {
            itemSelectedListener?.onChooseRecipientItemSelected(bankInfo)
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
    private fun walletInfo(walletInfo: GetBeneficiaryData) {
        benePersonId = walletInfo.benePersonId!!
        beneId = walletInfo.beneficiaryId!!
        beneAccountName = walletInfo.beneName.toString()
        beneMobile = walletInfo.mobile.toString()
    }
    private fun transfer(transfer: GetBeneficiaryData) {
        benePersonId = transfer.benePersonId!!
        beneId = transfer.beneficiaryId!!
        beneAccountName = transfer.beneName.toString()
        beneMobile = transfer.mobile.toString()
        findNavController().navigate(R.id.action_nav_beneficiary_management_to_nav_main)
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
        binding.btnBeneficiary.setBackgroundResource(R.color.white)

        binding.contactSearch.visibility = View.GONE
        binding.contactRecyclerView.visibility = View.GONE
        binding.contactRecyclerView.setBackgroundResource(R.color.grey)
        binding.btnContact.setBackgroundResource(R.color.grey)
    }

    override fun onBankAndWalletItemSelected(selectedItem: GetBankData) {
        itemSelectedListener?.onBankAndWalletItemSelected(selectedItem)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.nav_main)
        }
    }

}