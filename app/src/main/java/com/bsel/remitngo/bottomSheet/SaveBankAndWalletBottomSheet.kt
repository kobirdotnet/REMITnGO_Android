package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnBankSelectedListener
import com.bsel.remitngo.data.interfaceses.OnSaveBankAndWalletSelectedListener
import com.bsel.remitngo.data.model.bank.BankData
import com.bsel.remitngo.data.model.bank.save_bank_account.SaveBankItem
import com.bsel.remitngo.data.model.branch.BranchData
import com.bsel.remitngo.databinding.SaveBankAndWalletLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.bank.BankViewModel
import com.bsel.remitngo.presentation.ui.bank.BankViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import javax.inject.Inject

class SaveBankAndWalletBottomSheet : BottomSheetDialogFragment(), OnBankSelectedListener {
    @Inject
    lateinit var bankViewModelFactory: BankViewModelFactory
    private lateinit var bankViewModel: BankViewModel

    private lateinit var binding: SaveBankAndWalletLayoutBinding

    var itemSelectedListener: OnSaveBankAndWalletSelectedListener? = null

    private lateinit var saveBankAndWalletBehavior: BottomSheetBehavior<*>

    private val bankBottomSheet: BankBottomSheet by lazy { BankBottomSheet() }

    private val bankBranchBottomSheet: BranchBottomSheet by lazy { BranchBottomSheet() }

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

    private var beneMobile: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.save_bank_and_wallet_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        saveBankAndWalletBehavior = BottomSheetBehavior.from(view.parent as View)
        saveBankAndWalletBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        saveBankAndWalletBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createBankSubComponent().inject(this)

        bankViewModel =
            ViewModelProvider(this, bankViewModelFactory)[BankViewModel::class.java]

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

        binding.bankAccountName.setText(beneAccountName)
        binding.bankAccountNumber.setText(beneAccountNo)
        binding.walletAccountName.setText(beneAccountName)
        binding.walletAccountNumber.setText(beneAccountNo)

        Log.i("info","beneAccountName: "+beneAccountName)
        Log.i("info","beneAccountNo: "+beneAccountNo)
        Log.i("info","beneMobile: "+beneMobile)

        bankAccountNameFocusListener()
        bankNameFocusListener()
        branchNameFocusListener()
        bankAccountNumberFocusListener()

        walletAccountNameFocusListener()
        phoneNumberFocusListener()

        if (orderType == 1) {
            binding.bankAccountLayout.visibility = View.GONE
            binding.walletAccountLayout.visibility = View.VISIBLE
        } else {
            binding.bankAccountLayout.visibility = View.VISIBLE
            binding.walletAccountLayout.visibility = View.GONE
        }

        binding.bankName.setOnClickListener {
            bankBottomSheet.setSelectedBank(beneBankId)
            bankBottomSheet.itemSelectedListener = this
            bankBottomSheet.show(childFragmentManager, bankBottomSheet.tag)
        }

        binding.branchName.setOnClickListener {
            bankBranchBottomSheet.setSelectedBank(beneBankId)
            bankBranchBottomSheet.itemSelectedListener = this
            bankBranchBottomSheet.show(childFragmentManager, bankBranchBottomSheet.tag)
        }

        binding.btnBankSave.setOnClickListener { bankAccountForm() }

        binding.btnWalletSave.setOnClickListener { walletAccountForm() }

        observeSaveBankResult()

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
        beneMobile: String?,
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
        this.beneMobile = beneMobile
    }

    private fun observeSaveBankResult() {
        bankViewModel.saveBankResult.observe(this) { result ->
            try {
                if (result?.data != null) {
                    saveBankAndWallet(result?.data.toString())
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun saveBankAndWallet(selectedItem: String) {
        itemSelectedListener?.onSaveBankAndWalletItemSelected(selectedItem)
        dismiss()
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
        beneAccountName = binding.bankAccountName.text.toString()
        beneBankName = binding.bankName.text.toString()
        beneBranchName = binding.branchName.text.toString()
        beneAccountNo = binding.bankAccountNumber.text.toString()

        val saveBankItem = SaveBankItem(
            accountName = beneAccountName,
            accountNo = beneAccountNo,
            accountType = 0,
            active = true,
            bankID = beneBankId,
            benePersonId = benePersonId,
            branchID = beneBranchId,
            deviceId = deviceId,
            id = 0,
            orderType = orderType,
            userIPAddress = ipAddress,
            walletId = beneWalletId
        )
        bankViewModel.saveBank(saveBankItem)
    }

    private fun walletAccountForm() {
        binding.walletAccountNameContainer.helperText = validWalletAccountName()
        binding.walletAccountNameContainer.helperText = validPhoneNumber()

        val validWalletAccountName = binding.walletAccountNameContainer.helperText == null
        val validPhoneNumber = binding.walletAccountNameContainer.helperText == null

        if (validWalletAccountName && validPhoneNumber) {
            submitWalletAccountForm()
        }
    }

    private fun submitWalletAccountForm() {
        beneAccountName = binding.walletAccountName.text.toString()
        beneAccountNo = binding.walletAccountNumber.text.toString()

        val saveBankItem = SaveBankItem(
            accountName = beneAccountName,
            accountNo = beneAccountNo,
            accountType = 0,
            active = true,
            bankID = beneBankId,
            benePersonId = benePersonId,
            branchID = beneBranchId,
            deviceId = deviceId,
            id = 0,
            orderType = orderType,
            userIPAddress = ipAddress,
            walletId = beneWalletId
        )
        bankViewModel.saveBank(saveBankItem)
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
        binding.walletAccountNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.walletAccountNumberContainer.helperText = validPhoneNumber()
            }
        }
    }

    private fun validPhoneNumber(): String? {
        val phoneNumber = binding.walletAccountNumber.text.toString()
        if (phoneNumber.isEmpty()) {
            return "enter phone number"
        }
        return null
    }

    override fun onBankItemSelected(selectedItem: BankData) {
        beneBankId = selectedItem.id!!
        beneBankName = selectedItem.name!!
        binding.bankName.setText(beneBankName)
    }

    override fun onBranchItemSelected(selectedItem: BranchData) {
        beneBranchId = selectedItem.id!!
        beneBranchName = selectedItem.name
        binding.branchName.setText(beneBranchName)
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

    override fun onStart() {
        super.onStart()
        saveBankAndWalletBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}