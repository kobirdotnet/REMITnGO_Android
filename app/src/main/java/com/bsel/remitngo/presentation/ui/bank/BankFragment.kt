package com.bsel.remitngo.presentation.ui.bank

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.*
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.bank.BankData
import com.bsel.remitngo.data.model.bank.save_bank_account.SaveBankItem
import com.bsel.remitngo.data.model.branch.BranchData
import com.bsel.remitngo.data.model.district.DistrictData
import com.bsel.remitngo.data.model.division.DivisionData
import com.bsel.remitngo.databinding.FragmentBankBinding
import com.bsel.remitngo.data.interfaceses.OnBankSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import java.util.*
import javax.inject.Inject

class BankFragment : Fragment(), OnBankSelectedListener {
    @Inject
    lateinit var bankViewModelFactory: BankViewModelFactory
    private lateinit var bankViewModel: BankViewModel

    private lateinit var binding: FragmentBankBinding

    private lateinit var preferenceManager: PreferenceManager

    private val bankBottomSheet: BankBottomSheet by lazy { BankBottomSheet() }

    private val divisionBottomSheet: DivisionBottomSheet by lazy { DivisionBottomSheet() }

    private val districtBottomSheet: DistrictBottomSheet by lazy { DistrictBottomSheet() }

    private val bankBranchBottomSheet: BranchBottomSheet by lazy { BranchBottomSheet() }

    private var id: Int = 0
    private var isVersion113: Int = 0

    var ipAddress: String? = null
    private lateinit var deviceId: String

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

    private lateinit var cusBankInfoId: String
    private lateinit var recipientName: String
    private lateinit var recipientMobile: String
    private lateinit var recipientAddress: String

    private lateinit var divisionId: String
    private lateinit var districtId: String
    private lateinit var branchId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBankBinding.bind(view)

        (requireActivity().application as Injector).createBankSubComponent().inject(this)

        bankViewModel =
            ViewModelProvider(this, bankViewModelFactory)[BankViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        bankAccountNameFocusListener()
        bankNameFocusListener()
        divisionNameFocusListener()
        branchNameFocusListener()
        bankAccountNumberFocusListener()

        walletAccountNameFocusListener()
        phoneNumberFocusListener()

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

        cusBankInfoId = arguments?.getString("cusBankInfoId").toString()
        recipientName = arguments?.getString("recipientName").toString()
        recipientMobile = arguments?.getString("recipientMobile").toString()
        recipientAddress = arguments?.getString("recipientAddress").toString()

        binding.bankAccountName.setText(recipientName)
        binding.walletAccountName.setText(recipientName)

        if (orderType == "1") {
            binding.bankAccountLayout.visibility = View.GONE
            binding.walletAccountLayout.visibility = View.VISIBLE
        } else {
            binding.bankAccountLayout.visibility = View.VISIBLE
            binding.walletAccountLayout.visibility = View.GONE
        }

        binding.bankName.setOnClickListener {
            bankBottomSheet.itemSelectedListener = this
            bankBottomSheet.show(childFragmentManager, bankBottomSheet.tag)
        }

        binding.divisionName.setOnClickListener {
            divisionBottomSheet.itemSelectedListener = this
            divisionBottomSheet.show(childFragmentManager, divisionBottomSheet.tag)
        }

        binding.districtName.setOnClickListener {
            if (::divisionId.isInitialized && !divisionId.isNullOrEmpty()) {
                districtBottomSheet.setSelectedDivision(divisionId)
                districtBottomSheet.itemSelectedListener = this
                districtBottomSheet.show(childFragmentManager, districtBottomSheet.tag)
            }
        }

        binding.branchName.setOnClickListener {
            if (::districtId.isInitialized && !districtId.isNullOrEmpty()) {
                bankBranchBottomSheet.setSelectedDistrict(districtId)
                bankBranchBottomSheet.itemSelectedListener = this
                bankBranchBottomSheet.show(childFragmentManager, bankBranchBottomSheet.tag)
            }
        }

        binding.btnBankSave.setOnClickListener { bankAccountForm() }

        binding.btnWalletSave.setOnClickListener { walletAccountForm() }

        observeSaveBankResult()

    }

    private fun observeSaveBankResult() {
        bankViewModel.saveBankResult.observe(this) { result ->
            if (result != null) {
                val bundle = Bundle().apply {
                    putString("orderType", orderType)
                    putString("paymentType", paymentType)

                    putString("cusBankInfoId", cusBankInfoId)
                    putString("recipientName", recipientName)
                    putString("recipientMobile", recipientMobile)
                    putString("recipientAddress", recipientAddress)

                    putString("bankId", bankId)
                    putString("bankName", binding.bankName.toString())

                    putString("accountNo", binding.bankAccountNumber.toString())
                    putString("branchId", branchId)

                    putString("send_amount", send_amount)
                    putString("receive_amount", receive_amount)

                    putString("payingAgentId", payingAgentId)
                    putString("payingAgentName", payingAgentName)

                    putString("exchangeRate", exchangeRate)
                    putString("bankCommission", bankCommission)
                    putString("cardCommission", cardCommission)
                }
                findNavController().navigate(
                    R.id.action_nav_save_bank_to_nav_review,
                    bundle
                )
            }
        }
    }

    private fun bankAccountForm() {
        binding.bankAccountNameContainer.helperText = validBankAccountName()
        binding.bankNameContainer.helperText = validBankName()
        binding.divisionNameContainer.helperText = validDivisionName()
        binding.branchNameContainer.helperText = validBranchName()
        binding.bankAccountNumberContainer.helperText = validBankAccountNumber()

        val validBankAccountName = binding.bankAccountNameContainer.helperText == null
        val validBankName = binding.bankNameContainer.helperText == null
        val validDivisionName = binding.divisionNameContainer.helperText == null
        val validBranchName = binding.branchNameContainer.helperText == null
        val validBankAccountNumber = binding.bankAccountNumberContainer.helperText == null

        if (validBankAccountName && validBankName && validDivisionName && validBranchName && validBankAccountNumber) {
            submitBankAccountForm()
        }
    }

    private fun submitBankAccountForm() {
        val bankAccountName = binding.bankAccountName.text.toString()
        val bankName = binding.bankName.text.toString()
        val divisionName = binding.divisionName.text.toString()
        val branchName = binding.branchName.text.toString()
        val bankAccountNumber = binding.bankAccountNumber.text.toString()

        id = 0
        isVersion113 = 0
        val saveBankItem = SaveBankItem(
            id = id,
            deviceId = deviceId,
            userIPAddress = ipAddress.toString(),
            orderType = orderType.toInt(),
            cusBankInfoID = cusBankInfoId.toInt(),
            accountName = bankAccountName,
            bankID = bankId.toInt(),
            branchID = branchId.toInt(),
            accountNo = bankAccountNumber,
            isVersion113 = isVersion113,
            accountType = 0,
            active = true
        )
        bankViewModel.saveBank(saveBankItem)
    }

    private fun walletAccountForm() {
        binding.walletAccountNameContainer.helperText = validWalletAccountName()
        binding.phoneNumberContainer.helperText = validPhoneNumber()

        val validWalletAccountName = binding.walletAccountNameContainer.helperText == null
        val validPhoneNumber = binding.phoneNumberContainer.helperText == null

        if (validWalletAccountName && validPhoneNumber) {
            submitWalletAccountForm()
        }
    }

    private fun submitWalletAccountForm() {
        val walletAccountName = binding.walletAccountName.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()

        val bundle = Bundle().apply {
            putString("orderType", orderType)
            putString("paymentType", paymentType)

            putString("cusBankInfoId", cusBankInfoId)
            putString("recipientName", recipientName)
            putString("recipientMobile", recipientMobile)
            putString("recipientAddress", recipientAddress)
        }
        findNavController().navigate(
            R.id.action_nav_save_bank_to_nav_review,
            bundle
        )
    }

    //Form validation
    private fun bankAccountNameFocusListener() {
        binding.bankAccountName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.bankAccountNameContainer.helperText = validBankAccountName()
            }
        }
    }

    private fun validBankAccountName(): String? {
        val bankAccountName = binding.bankAccountName.text.toString()
        if (bankAccountName.isEmpty()) {
            return "enter bank account name"
        }
        return null
    }

    private fun bankNameFocusListener() {
        binding.bankName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.bankNameContainer.helperText = validBankName()
            }
        }
    }

    private fun validBankName(): String? {
        val bankName = binding.bankName.text.toString()
        if (bankName.isEmpty()) {
            return "select bank name"
        }
        return null
    }

    private fun divisionNameFocusListener() {
        binding.divisionName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.divisionNameContainer.helperText = validDivisionName()
            }
        }
    }

    private fun validDivisionName(): String? {
        val divisionName = binding.divisionName.text.toString()
        if (divisionName.isEmpty()) {
            return "select division name"
        }
        return null
    }

    private fun branchNameFocusListener() {
        binding.branchName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.branchNameContainer.helperText = validBranchName()
            }
        }
    }

    private fun validBranchName(): String? {
        val branchName = binding.branchName.text.toString()
        if (branchName.isEmpty()) {
            return "select branch name"
        }
        return null
    }

    private fun bankAccountNumberFocusListener() {
        binding.bankAccountNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.bankAccountNumberContainer.helperText = validBankAccountNumber()
            }
        }
    }

    private fun validBankAccountNumber(): String? {
        val bankAccountNumber = binding.bankAccountNumber.text.toString()
        if (bankAccountNumber.isEmpty()) {
            return "enter bank account number"
        }
        return null
    }

    private fun walletAccountNameFocusListener() {
        binding.walletAccountName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.walletAccountNameContainer.helperText = validWalletAccountName()
            }
        }
    }

    private fun validWalletAccountName(): String? {
        val walletAccountName = binding.walletAccountName.text.toString()
        if (walletAccountName.isEmpty()) {
            return "enter wallet account name"
        }
        return null
    }

    private fun phoneNumberFocusListener() {
        binding.phoneNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.phoneNumberContainer.helperText = validPhoneNumber()
            }
        }
    }

    private fun validPhoneNumber(): String? {
        val phoneNumber = binding.phoneNumber.text.toString()
        if (phoneNumber.isEmpty()) {
            return "enter phone number"
        }
        return null
    }

    override fun onBankItemSelected(selectedItem: BankData) {
        binding.bankName.setText(selectedItem.name)
        bankId = selectedItem.id.toString()
        preferenceManager.saveData("bankId", bankId)
    }

    override fun onDivisionItemSelected(selectedItem: DivisionData) {
        binding.divisionName.setText(selectedItem.name)
        divisionId = selectedItem.id.toString()
        preferenceManager.saveData("divisionId", divisionId)
    }

    override fun onDistrictItemSelected(selectedItem: DistrictData) {
        binding.districtName.setText(selectedItem.name)
        districtId = selectedItem.id.toString()
        preferenceManager.saveData("districtId", districtId)
    }

    override fun onBranchItemSelected(selectedItem: BranchData) {
        binding.branchName.setText(selectedItem.name)
        branchId = selectedItem.id.toString()
        preferenceManager.saveData("branchId", branchId)
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
