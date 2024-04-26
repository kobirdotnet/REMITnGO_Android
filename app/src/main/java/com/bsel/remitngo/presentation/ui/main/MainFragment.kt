package com.bsel.remitngo.presentation.ui.main

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.PayingAgentCashPickupBottomSheet
import com.bsel.remitngo.bottomSheet.PayingAgentInstantCreditBottomSheet
import com.bsel.remitngo.bottomSheet.PayingAgentWalletBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnCalculationSelectedListener
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentData
import com.bsel.remitngo.data.model.paying_agent.PayingAgentItem
import com.bsel.remitngo.data.model.percentage.PercentageItem
import com.bsel.remitngo.databinding.FragmentMainBinding
import com.bsel.remitngo.presentation.di.Injector
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject

class MainFragment : Fragment(), OnCalculationSelectedListener {
    @Inject
    lateinit var calculationViewModelFactory: CalculationViewModelFactory
    private lateinit var calculationViewModel: CalculationViewModel

    private lateinit var binding: FragmentMainBinding

    private lateinit var preferenceManager: PreferenceManager

    private val payingAgentInstantCreditBottomSheet: PayingAgentInstantCreditBottomSheet by lazy { PayingAgentInstantCreditBottomSheet() }
    private val payingAgentCashPickupBottomSheet: PayingAgentCashPickupBottomSheet by lazy { PayingAgentCashPickupBottomSheet() }
    private val payingAgentWalletBottomSheet: PayingAgentWalletBottomSheet by lazy { PayingAgentWalletBottomSheet() }

    private var customerId: Int = 0
    private var personId: Int = 0
    private var customerEmail: String? = null
    private var customerMobile: String? = null

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private val decimalFormat = DecimalFormat("#.##")

    private var paymentMode: Int = 4
    private var orderType: Int = 3

    private var sendAmount: Double = 100.0
    private var beneAmount: Double = 0.0

    private var rate: Double = 0.0
    private var commission: Double = 0.0

    private var beneWalletId: Int = 0
    private var beneBankId: Int = 0
    private var beneBranchId: Int = 0
    private var beneBankName: String? = null
    private var beneAccountNo: String? = null
    private var payingAgentId: Int = 0

    private var benePersonId: Int = 0
    private var beneId: Int = 0
    private var beneAccountName: String? = null
    private var beneMobile: String? = null

    private var purposeOfTransferId: Int = 0
    private var purposeOfTransferName: String? = null

    private var sourceOfFundId: Int = 0
    private var sourceOfFundName: String? = null

    private var calculateRate = 0.0

    private lateinit var percentageUrl: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        (requireActivity().application as Injector).createCalculationSubComponent().inject(this)

        calculationViewModel =
            ViewModelProvider(this, calculationViewModelFactory)[CalculationViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString().toInt()
        customerId = preferenceManager.loadData("customerId").toString().toInt()
        customerEmail = preferenceManager.loadData("customerEmail").toString()
        customerMobile = preferenceManager.loadData("customerMobile").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        try {
            paymentMode = arguments?.getString("paymentType").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            orderType = arguments?.getString("orderType").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            sendAmount = arguments?.getString("sendAmount").toString().toDouble()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            beneAmount = arguments?.getString("receiveAmount").toString().toDouble()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            rate = arguments?.getString("exchangeRate").toString().toDouble()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            commission = arguments?.getString("commission").toString().toDouble()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            beneBankId = arguments?.getString("bankId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            beneWalletId = arguments?.getString("walletId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            beneBranchId = arguments?.getString("branchId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            payingAgentId = arguments?.getString("payingAgentId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        beneBankName = arguments?.getString("bankName").toString()
        beneAccountNo = arguments?.getString("bankName").toString()
        try {
            benePersonId = arguments?.getString("benId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            beneId = arguments?.getString("beneficiaryId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        beneAccountName = arguments?.getString("beneficiaryName").toString()
        beneMobile = arguments?.getString("beneficiaryPhoneNumber").toString()
        try {
            purposeOfTransferId = arguments?.getString("reasonId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        purposeOfTransferName = arguments?.getString("reasonName").toString()
        try {
            sourceOfFundId = arguments?.getString("sourceOfIncomeId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        sourceOfFundName = arguments?.getString("sourceOfIncomeName").toString()

        when (orderType) {
            5 -> {
                binding.bankAccount.isChecked = false
                binding.instantCredit.isChecked = true
                binding.cashPickup.isChecked = false
                binding.mobileWallet.isChecked = false

                binding.collectionPointInstantCreditLayout.visibility = View.VISIBLE
                binding.collectionPointCashPickUpLayout.visibility = View.GONE
                binding.collectionPointWalletLayout.visibility = View.GONE
            }
            2 -> {
                binding.bankAccount.isChecked = false
                binding.instantCredit.isChecked = false
                binding.cashPickup.isChecked = true
                binding.mobileWallet.isChecked = false

                binding.collectionPointInstantCreditLayout.visibility = View.GONE
                binding.collectionPointCashPickUpLayout.visibility = View.VISIBLE
                binding.collectionPointWalletLayout.visibility = View.GONE
                binding.collectionPointWallet.text = null
            }
            1 -> {
                binding.bankAccount.isChecked = false
                binding.instantCredit.isChecked = false
                binding.cashPickup.isChecked = false
                binding.mobileWallet.isChecked = true

                binding.collectionPointInstantCreditLayout.visibility = View.GONE
                binding.collectionPointCashPickUpLayout.visibility = View.GONE
                binding.collectionPointWalletLayout.visibility = View.VISIBLE
            }
            3 -> {
                binding.bankAccount.isChecked = true
                binding.instantCredit.isChecked = false
                binding.cashPickup.isChecked = false
                binding.mobileWallet.isChecked = false

                binding.collectionPointInstantCreditLayout.visibility = View.GONE
                binding.collectionPointCashPickUpLayout.visibility = View.GONE
                binding.collectionPointWalletLayout.visibility = View.GONE
            }
        }

        when (paymentMode) {
            4 -> {
                binding.cardPayment.isChecked = true
                binding.bankPayment.isChecked = false
            }
            3 -> {
                binding.cardPayment.isChecked = false
                binding.bankPayment.isChecked = true
            }
        }

        binding.collectionPointInstantCredit.setOnClickListener {
            payingAgentInstantCreditBottomSheet.setSelectedOrderType(
                5,
                binding.sendAmount.text.toString()
            )
            payingAgentInstantCreditBottomSheet.itemSelectedListener = this
            payingAgentInstantCreditBottomSheet.show(
                childFragmentManager,
                payingAgentInstantCreditBottomSheet.tag
            )
        }

        binding.collectionPointCashPickUp.setOnClickListener {
            payingAgentCashPickupBottomSheet.setSelectedOrderType(
                2,
                binding.sendAmount.text.toString()
            )
            payingAgentCashPickupBottomSheet.itemSelectedListener = this
            payingAgentCashPickupBottomSheet.show(
                childFragmentManager,
                payingAgentCashPickupBottomSheet.tag
            )
        }

        binding.collectionPointWallet.setOnClickListener {
            payingAgentWalletBottomSheet.setSelectedOrderType(
                1,
                binding.sendAmount.text.toString()
            )
            payingAgentWalletBottomSheet.itemSelectedListener = this
            payingAgentWalletBottomSheet.show(
                childFragmentManager,
                payingAgentWalletBottomSheet.tag
            )
        }

        binding.orderModeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.bankAccount -> {
                    orderType = 3
                    calculateRate(
                        deviceId,
                        personId,
                        beneBankId,
                        payingAgentId,
                        orderType,
                        paymentMode,
                        4,
                        1,
                        0,
                        binding.sendAmount.text.toString()
                    )
                    binding.bankAccount.isChecked = true
                    binding.instantCredit.isChecked = false
                    binding.cashPickup.isChecked = false
                    binding.mobileWallet.isChecked = false

                    binding.collectionPointInstantCreditLayout.visibility = View.GONE
                    binding.collectionPointCashPickUpLayout.visibility = View.GONE
                    binding.collectionPointWalletLayout.visibility = View.GONE
                }
                R.id.instantCredit -> {
                    orderType = 5

                    binding.bankAccount.isChecked = false
                    binding.instantCredit.isChecked = true
                    binding.cashPickup.isChecked = false
                    binding.mobileWallet.isChecked = false

                    binding.collectionPointInstantCreditLayout.visibility = View.VISIBLE
                    binding.collectionPointCashPickUpLayout.visibility = View.GONE
                    binding.collectionPointWalletLayout.visibility = View.GONE
                }
                R.id.cashPickup -> {
                    orderType = 2

                    binding.bankAccount.isChecked = false
                    binding.instantCredit.isChecked = false
                    binding.cashPickup.isChecked = true
                    binding.mobileWallet.isChecked = false

                    binding.collectionPointInstantCreditLayout.visibility = View.GONE
                    binding.collectionPointCashPickUpLayout.visibility = View.VISIBLE
                    binding.collectionPointWalletLayout.visibility = View.GONE
                }
                R.id.mobileWallet -> {
                    orderType = 1

                    binding.bankAccount.isChecked = false
                    binding.instantCredit.isChecked = false
                    binding.cashPickup.isChecked = false
                    binding.mobileWallet.isChecked = true

                    binding.collectionPointInstantCreditLayout.visibility = View.GONE
                    binding.collectionPointCashPickUpLayout.visibility = View.GONE
                    binding.collectionPointWalletLayout.visibility = View.VISIBLE
                }
            }
        }

        binding.paymentModeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.cardPayment -> {
                    paymentMode = 4

                    binding.cardPayment.isChecked = true
                    binding.bankPayment.isChecked = false
                }
                R.id.bankPayment -> {
                    paymentMode = 3

                    binding.cardPayment.isChecked = false
                    binding.bankPayment.isChecked = true
                }
            }
        }

        binding.sendAmount.setText(sendAmount.toString())
        updateValuesGBP()
        binding.sendAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!binding.sendAmount.isFocused) return
                updateValuesGBP()
            }
        })
        binding.receiveAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!binding.receiveAmount.isFocused) return
                updateValuesBDT()
            }
        })

        val percentageItem = PercentageItem(
            countryId = 1,
            customerId = 0
        )
        calculationViewModel.percentage(percentageItem)

        binding.learnMore.text = "Learn more!"
        binding.learnMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bracsaajanexchange.com"))
            startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            val sendAmountValue = binding.sendAmount.text.toString()
            sendAmount = sendAmountValue.replace(Regex("[^\\d.]"), "").toDouble()

            val receiveAmountValue = binding.receiveAmount.text.toString()
            beneAmount = receiveAmountValue.replace(Regex("[^\\d.]"), "").toDouble()

            val bundle = Bundle().apply {
                putString("paymentType", paymentMode.toString())
                putString("orderType", orderType.toString())
                putString("sendAmount", sendAmount.toString())
                putString("receiveAmount", beneAmount.toString())
                putString("exchangeRate", rate.toString())
                putString("commission", commission.toString())

                putString("bankId", beneBankId.toString())
                putString("walletId", beneWalletId.toString())
                putString("branchId", beneBranchId.toString())
                putString("bankName", beneBankName)
                putString("beneAccountNo", beneAccountNo)
                putString("payingAgentId", payingAgentId.toString())

                putString("benId", benePersonId.toString())
                putString("beneficiaryId", beneId.toString())
                putString("beneficiaryName", beneAccountName)
                putString("beneficiaryPhoneNumber", beneMobile)

                putString("reasonId", purposeOfTransferId.toString())
                putString("reasonName", purposeOfTransferName)
                putString("sourceOfIncomeId", sourceOfFundId.toString())
                putString("sourceOfIncomeName", sourceOfFundName)
            }
            findNavController().navigate(
                R.id.action_nav_main_to_nav_review,
                bundle
            )
        }

        calculateRate(
            deviceId,
            personId,
            beneBankId,
            payingAgentId,
            orderType,
            paymentMode,
            4,
            1,
            0,
            binding.sendAmount.text.toString()
        )

        val payingAgentItem = PayingAgentItem(
            deviceId = deviceId,
            fromCountryId = 4,
            toCountryId = 1,
            orderTypeId = orderType,
            amount = sendAmount.toInt()
        )
        calculationViewModel.payingAgent(payingAgentItem)

        observeExtraPercentageResult()
        observeCalculateRateResult()
        observePayingAgentResult()
    }

    private fun observeExtraPercentageResult() {
        calculationViewModel.percentageResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (extraPercentage in result.data!!) {
                        binding.extraPercentage.text = extraPercentage?.campingMessage.toString()
                        percentageUrl = extraPercentage?.url.toString()
                    }
                }
                binding.extraPercentage.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(percentageUrl))
                    startActivity(intent)
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun observeCalculateRateResult() {
        calculationViewModel.calculateRateResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (data in result.data!!) {
                        commission = data!!.commission.toString().toDouble()
                        calculateRate = data.rate!!.toDouble()
                        rate = data.rate!!.toDouble().toString().toDouble()
                        binding.exchangeRate.text = "BDT $rate"
                        updateValuesGBP()
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun observePayingAgentResult() {
        calculationViewModel.payingAgentResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (item in result.data!!) {
                        if (item!!.bankId == beneBankId && item.walletId == beneWalletId && item.payingAgentId == payingAgentId) {
                            binding.collectionPointInstantCredit.setText(item.name)
                            binding.collectionPointCashPickUp.setText(item.name)
                            binding.collectionPointWallet.setText(item.name)
                        }
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    fun updateValuesBDT() {
        val bdtValue = binding.receiveAmount.text.toString().toDoubleOrNull()
        if (bdtValue != null) {
            val gbpValue = bdtValue / calculateRate
            val formattedGBP = decimalFormat.format(gbpValue)
            binding.sendAmount.setText(formattedGBP.toString())
        } else {
            binding.sendAmount.setText("")
        }
    }

    fun updateValuesGBP() {
        val gbpValue = binding.sendAmount.text.toString().toDoubleOrNull()
        if (gbpValue != null) {
            val bdtValue = gbpValue * calculateRate
            val formattedBDT = decimalFormat.format(bdtValue)
            binding.receiveAmount.setText(formattedBDT.toString())
        } else {
            binding.receiveAmount.setText("")
        }
    }

    override fun onPayingAgentInstantCreditItemSelected(selectedItem: PayingAgentData) {
        binding.collectionPointInstantCredit.setText(selectedItem.name)
        payingAgentId = selectedItem.payingAgentId!!.toString().toInt()
        beneBankId = selectedItem.bankId!!.toString().toInt()
        beneWalletId = selectedItem.walletId!!.toString().toInt()

        calculateRate(
            deviceId,
            personId,
            beneBankId,
            payingAgentId,
            orderType,
            paymentMode,
            4,
            1,
            0,
            binding.sendAmount.text.toString()
        )
    }

    override fun onPayingAgentCashPickupItemSelected(selectedItem: PayingAgentData) {
        binding.collectionPointCashPickUp.setText(selectedItem.name)
        payingAgentId = selectedItem.payingAgentId!!.toString().toInt()
        beneBankId = selectedItem.bankId!!.toString().toInt()
        beneWalletId = selectedItem.walletId!!.toString().toInt()

        calculateRate(
            deviceId,
            personId,
            beneBankId,
            payingAgentId,
            orderType,
            paymentMode,
            4,
            1,
            0,
            binding.sendAmount.text.toString()
        )
    }

    override fun onPayingAgentWalletItemSelected(selectedItem: PayingAgentData) {
        binding.collectionPointWallet.setText(selectedItem.name)
        payingAgentId = selectedItem.payingAgentId!!.toString().toInt()
        beneBankId = selectedItem.bankId!!.toString().toInt()
        beneWalletId = selectedItem.walletId!!.toString().toInt()

        calculateRate(
            deviceId,
            personId,
            beneBankId,
            payingAgentId,
            orderType,
            paymentMode,
            4,
            1,
            0,
            binding.sendAmount.text.toString()
        )
    }

    private fun calculateRate(
        deviceId: String,
        personId: Int,
        bankId: Int,
        payingAgentId: Int,
        orderType: Int,
        paymentMode: Int,
        fromCountry: Int,
        toCountry: Int,
        mobileOrWebPlatform: Int,
        amount: String
    ) {
        val calculateRateItem = CalculateRateItem(
            deviceId = deviceId,
            personId = personId,
            bankId = bankId,
            payingAgentId = payingAgentId,
            orderType = orderType,
            paymentMode = paymentMode,
            fromCountry = fromCountry,
            toCountry = toCountry,
            mobileOrWebPlatform = mobileOrWebPlatform,
            amount = amount,
        )
        calculationViewModel.calculateRate(calculateRateItem)
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

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("App termination")
            builder.setMessage("Do you want to close the app?")
            builder.setPositiveButton("EXIT") { _: DialogInterface, _: Int ->
                ActivityCompat.finishAffinity(requireActivity())
            }
            builder.setNegativeButton("STAY") { _: DialogInterface, _: Int -> }
            val dialog = builder.create()
            dialog.show()
        }
    }

}
