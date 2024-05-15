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
import com.bsel.remitngo.data.model.bank.WalletData
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

    private val bankBranchBottomSheet: BranchBottomSheet by lazy { BranchBottomSheet() }

    private lateinit var personId: String
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
        return inflater.inflate(R.layout.fragment_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBankBinding.bind(view)

        (requireActivity().application as Injector).createBankSubComponent().inject(this)

        bankViewModel =
            ViewModelProvider(this, bankViewModelFactory)[BankViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
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

        bankAccountNameFocusListener()
        bankNameFocusListener()
        branchNameFocusListener()
        bankAccountNumberFocusListener()

        walletAccountNameFocusListener()
        phoneNumberFocusListener()

        binding.bankAccountName.setText(beneficiaryName)
        binding.walletAccountName.setText(beneficiaryName)
        binding.phoneNumber.setText(beneficiaryPhoneNumber)

        if (orderType == "1") {
            binding.bankAccountLayout.visibility = View.GONE
            binding.walletAccountLayout.visibility = View.VISIBLE
        } else {
            binding.bankAccountLayout.visibility = View.VISIBLE
            binding.walletAccountLayout.visibility = View.GONE
        }

        binding.bankName.setOnClickListener {
            bankBottomSheet.setSelectedBank(bankId.toInt())
            bankBottomSheet.itemSelectedListener = this
            bankBottomSheet.show(childFragmentManager, bankBottomSheet.tag)
        }

        binding.branchName.setOnClickListener {
            if (::bankId.isInitialized && !bankId.isNullOrEmpty()) {
                bankBranchBottomSheet.setSelectedBank(bankId.toInt())
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
            try {
                if (result != null) {
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
                        R.id.action_nav_save_bank_to_nav_choose_bank,
                        bundle
                    )
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun bankAccountForm() {
        binding.bankAccountNameContainer.helperText = validBankAccountName()
        binding.bankNameContainer.helperText = validBankName()
        binding.branchNameContainer.helperText = validBranchName()
        binding.bankAccountNumberContainer.helperText = validBankAccountNumber()

        val validBankAccountName = binding.bankAccountNameContainer.helperText == null
        val validBankName = binding.bankNameContainer.helperText == null
        val validBranchName = binding.branchNameContainer.helperText == null
        val validBankAccountNumber = binding.bankAccountNumberContainer.helperText == null

        if (validBankAccountName && validBankName && validBranchName && validBankAccountNumber) {
            submitBankAccountForm()
        }
    }

    private fun submitBankAccountForm() {
        val bankAccountName = binding.bankAccountName.text.toString()
        val bankName = binding.bankName.text.toString()
        val branchName = binding.branchName.text.toString()
        val bankAccountNumber = binding.bankAccountNumber.text.toString()

//        if (orderType=="null"){
//            val saveBankItem = SaveBankItem(
//                accountName = beneAccountName,
//                accountNo = beneAccountNo,
//                accountType = 0,
//                active = true,
//                bankID = beneBankId,
//                benePersonId = benePersonId,
//                branchID = beneBranchId,
//                deviceId = deviceId,
//                id = 0,
//                orderType = orderType,
//                userIPAddress = ipAddress,
//                walletId = beneWalletId
//            )
//            bankViewModel.saveBank(saveBankItem)
//        }else{
//            val saveBankItem = SaveBankItem(
//                accountName = beneAccountName,
//                accountNo = beneAccountNo,
//                accountType = 0,
//                active = true,
//                bankID = beneBankId,
//                benePersonId = benePersonId,
//                branchID = beneBranchId,
//                deviceId = deviceId,
//                id = 0,
//                orderType = orderType,
//                userIPAddress = ipAddress,
//                walletId = beneWalletId
//            )
//            bankViewModel.saveBank(saveBankItem)
//        }
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

//        val saveBankItem = SaveBankItem(
//            accountName = beneAccountName,
//            accountNo = beneAccountNo,
//            accountType = 0,
//            active = true,
//            bankID = beneBankId,
//            benePersonId = benePersonId,
//            branchID = beneBranchId,
//            deviceId = deviceId,
//            id = 0,
//            orderType = orderType,
//            userIPAddress = ipAddress,
//            walletId = beneWalletId
//        )
//        bankViewModel.saveBank(saveBankItem)

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
    }

    override fun onWalletItemSelected(selectedItem: WalletData) {
        binding.bankName.setText(selectedItem.name)
        bankId = selectedItem.id.toString()
    }

    override fun onBranchItemSelected(selectedItem: BranchData) {
        binding.branchName.setText(selectedItem.name)
        branchId = selectedItem.id.toString()
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
