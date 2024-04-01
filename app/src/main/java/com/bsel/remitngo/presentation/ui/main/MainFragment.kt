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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.PayingAgentBankBottomSheet
import com.bsel.remitngo.bottomSheet.PayingAgentWalletBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnCalculationSelectedListener
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerItem
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

    private val payingAgentBankBottomSheet: PayingAgentBankBottomSheet by lazy { PayingAgentBankBottomSheet() }
    private val payingAgentWalletBottomSheet: PayingAgentWalletBottomSheet by lazy { PayingAgentWalletBottomSheet() }

    private val decimalFormat = DecimalFormat("#.##")

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

    private lateinit var beneficiaryId: String
    private lateinit var beneficiaryName: String
    private lateinit var beneficiaryPhoneNumber: String

    private lateinit var reasonId: String
    private lateinit var reasonName: String

    private lateinit var sourceOfIncomeId: String
    private lateinit var sourceOfIncomeName: String

    private var rate = 0.0

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

        beneficiaryId = arguments?.getString("beneficiaryId").toString()
        beneficiaryName = arguments?.getString("beneficiaryName").toString()
        beneficiaryPhoneNumber = arguments?.getString("beneficiaryPhoneNumber").toString()

        reasonId = arguments?.getString("reasonId").toString()
        reasonName = arguments?.getString("reasonName").toString()

        sourceOfIncomeId = arguments?.getString("sourceOfIncomeId").toString()
        sourceOfIncomeName = arguments?.getString("sourceOfIncomeName").toString()

        if (orderType != "null") {
            if (orderType == "5") {
                binding.bankAccount.isChecked = false
                binding.instantCredit.isChecked = true
                binding.cashPickup.isChecked = false
                binding.mobileWallet.isChecked = false

                binding.collectionPointInstantCreditLayout.visibility = View.VISIBLE
//                binding.collectionPointInstantCredit.text = null
                binding.collectionPointCashPickUpLayout.visibility = View.GONE
                binding.collectionPointCashPickUp.text = null
                binding.collectionPointWalletLayout.visibility = View.GONE
                binding.collectionPointWallet.text = null
            } else if (orderType == "2") {
                binding.bankAccount.isChecked = false
                binding.instantCredit.isChecked = false
                binding.cashPickup.isChecked = true
                binding.mobileWallet.isChecked = false

                binding.collectionPointInstantCreditLayout.visibility = View.GONE
                binding.collectionPointInstantCredit.text = null
                binding.collectionPointCashPickUpLayout.visibility = View.VISIBLE
//                binding.collectionPointCashPickUp.text = null
                binding.collectionPointWalletLayout.visibility = View.GONE
                binding.collectionPointWallet.text = null
            } else if (orderType == "1") {
                binding.bankAccount.isChecked = false
                binding.instantCredit.isChecked = false
                binding.cashPickup.isChecked = false
                binding.mobileWallet.isChecked = true

                binding.collectionPointInstantCreditLayout.visibility = View.GONE
                binding.collectionPointInstantCredit.text = null
                binding.collectionPointCashPickUpLayout.visibility = View.GONE
                binding.collectionPointCashPickUp.text = null
                binding.collectionPointWalletLayout.visibility = View.VISIBLE
//                binding.collectionPointWallet.text = null
            } else if (orderType == "3") {
                binding.bankAccount.isChecked = true
                binding.instantCredit.isChecked = false
                binding.cashPickup.isChecked = false
                binding.mobileWallet.isChecked = false

                binding.collectionPointInstantCreditLayout.visibility = View.GONE
                binding.collectionPointInstantCredit.text = null
                binding.collectionPointCashPickUpLayout.visibility = View.GONE
                binding.collectionPointCashPickUp.text = null
                binding.collectionPointWalletLayout.visibility = View.GONE
                binding.collectionPointWallet.text = null
            }
        } else {
            orderType = "3"
            binding.bankAccount.isChecked = true
            binding.instantCredit.isChecked = false
            binding.cashPickup.isChecked = false
            binding.mobileWallet.isChecked = false

            binding.collectionPointInstantCreditLayout.visibility = View.GONE
            binding.collectionPointInstantCredit.text = null
            binding.collectionPointCashPickUpLayout.visibility = View.GONE
            binding.collectionPointCashPickUp.text = null
            binding.collectionPointWalletLayout.visibility = View.GONE
            binding.collectionPointWallet.text = null
        }

        if (paymentType != "null") {
            if (paymentType == "4") {
                binding.cardPayment.isChecked = true
                binding.bankPayment.isChecked = false
            } else if (paymentType == "3") {
                binding.cardPayment.isChecked = false
                binding.bankPayment.isChecked = true
            }
        } else {
            paymentType = "4"
            binding.cardPayment.isChecked = true
            binding.bankPayment.isChecked = false
        }


        binding.collectionPointInstantCredit.setOnClickListener {
            payingAgentBankBottomSheet.setSelectedOrderType("5", binding.sendAmount.text.toString())
            payingAgentBankBottomSheet.itemSelectedListener = this
            payingAgentBankBottomSheet.show(childFragmentManager, payingAgentBankBottomSheet.tag)
        }

        binding.collectionPointCashPickUp.setOnClickListener {
            payingAgentBankBottomSheet.setSelectedOrderType("2", binding.sendAmount.text.toString())
            payingAgentBankBottomSheet.itemSelectedListener = this
            payingAgentBankBottomSheet.show(childFragmentManager, payingAgentBankBottomSheet.tag)
        }

        binding.collectionPointWallet.setOnClickListener {
            payingAgentWalletBottomSheet.setSelectedOrderType(
                "1",
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
                    orderType = "3"
                    calculateRate(
                        deviceId,
                        personId.toInt(),
                        bankId.toInt(),
                        payingAgentId.toInt(),
                        orderType.toInt(),
                        paymentType.toInt(),
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
                    binding.collectionPointInstantCredit.text = null
                    binding.collectionPointCashPickUpLayout.visibility = View.GONE
                    binding.collectionPointCashPickUp.text = null
                    binding.collectionPointWalletLayout.visibility = View.GONE
                    binding.collectionPointWallet.text = null
                }
                R.id.instantCredit -> {
                    orderType = "5"

                    binding.bankAccount.isChecked = false
                    binding.instantCredit.isChecked = true
                    binding.cashPickup.isChecked = false
                    binding.mobileWallet.isChecked = false

                    binding.collectionPointInstantCreditLayout.visibility = View.VISIBLE
                    binding.collectionPointInstantCredit.text = null
                    binding.collectionPointCashPickUpLayout.visibility = View.GONE
                    binding.collectionPointCashPickUp.text = null
                    binding.collectionPointWalletLayout.visibility = View.GONE
                    binding.collectionPointWallet.text = null
                }
                R.id.cashPickup -> {
                    orderType = "2"

                    binding.bankAccount.isChecked = false
                    binding.instantCredit.isChecked = false
                    binding.cashPickup.isChecked = true
                    binding.mobileWallet.isChecked = false

                    binding.collectionPointInstantCreditLayout.visibility = View.GONE
                    binding.collectionPointInstantCredit.text = null
                    binding.collectionPointCashPickUpLayout.visibility = View.VISIBLE
                    binding.collectionPointCashPickUp.text = null
                    binding.collectionPointWalletLayout.visibility = View.GONE
                    binding.collectionPointWallet.text = null
                }
                R.id.mobileWallet -> {
                    orderType = "1"

                    binding.bankAccount.isChecked = false
                    binding.instantCredit.isChecked = false
                    binding.cashPickup.isChecked = false
                    binding.mobileWallet.isChecked = true

                    binding.collectionPointInstantCreditLayout.visibility = View.GONE
                    binding.collectionPointInstantCredit.text = null
                    binding.collectionPointCashPickUpLayout.visibility = View.GONE
                    binding.collectionPointCashPickUp.text = null
                    binding.collectionPointWalletLayout.visibility = View.VISIBLE
                    binding.collectionPointWallet.text = null
                }
            }
        }

        binding.paymentModeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.cardPayment -> {
                    paymentType = "4"

                    binding.cardPayment.isChecked = true
                    binding.bankPayment.isChecked = false
                }
                R.id.bankPayment -> {
                    paymentType = "3"

                    binding.cardPayment.isChecked = false
                    binding.bankPayment.isChecked = true
                }
            }
        }

        if (sendAmount != "null") {
            binding.sendAmount.setText(sendAmount)
        } else {
            binding.sendAmount.setText("100")
        }

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
        calculationViewModel.percentageResult.observe(this) { result ->
            if (result!!.data != null) {
                for (extraPercentage in result.data!!) {
                    binding.extraPercentage.setText(extraPercentage?.campingMessage.toString())
                    percentageUrl = extraPercentage?.url.toString()
                }
            }
        }
        binding.extraPercentage.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(percentageUrl))
            startActivity(intent)
        }

        binding.learnMore.setText("Learn more!")
        binding.learnMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bracsaajanexchange.com"))
            startActivity(intent)
        }

        binding.btnNext.setOnClickListener {

            sendAmount = binding.sendAmount.text.toString()
            receiveAmount = binding.receiveAmount.text.toString()

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

                putString("beneficiaryId", beneficiaryId)
                putString("beneficiaryName", beneficiaryName)
                putString("beneficiaryPhoneNumber", beneficiaryPhoneNumber)

                putString("reasonId", reasonId)
                putString("reasonName", reasonName)

                putString("sourceOfIncomeId", sourceOfIncomeId)
                putString("sourceOfIncomeName", sourceOfIncomeName)
            }
            findNavController().navigate(
                R.id.action_nav_main_to_nav_review,
                bundle
            )
        }

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

        if (bankId != "null" || payingAgentId != "null") {
            calculateRate(
                deviceId,
                personId.toInt(),
                bankId.toInt(),
                payingAgentId.toInt(),
                orderType.toInt(),
                paymentType.toInt(),
                4,
                1,
                0,
                binding.sendAmount.text.toString()
            )
        } else {
            calculateRate(
                deviceId,
                personId.toInt(),
                0,
                0,
                orderType.toInt(),
                paymentType.toInt(),
                4,
                1,
                0,
                binding.sendAmount.text.toString()
            )
        }

        if (bankId != "null" || payingAgentId != "null") {
            val payingAgentItem = PayingAgentItem(
                deviceId = deviceId,
                fromCountryId = 4,
                toCountryId = 1,
                orderTypeId = orderType.toInt(),
                amount = sendAmount.toInt()
            )
            calculationViewModel.payingAgent(payingAgentItem)
        }

        observeCalculateRateResult()
        observePayingAgentResult()
    }

    private fun observeCalculateRateResult() {
        calculationViewModel.calculateRateResult.observe(this) { result ->
            if (result!!.data != null) {
                for (data in result.data!!) {
                    commission = data!!.commission!!.toDouble().toString()
                    rate = data!!.rate!!.toDouble()
                    exchangeRate = data!!.rate!!.toDouble().toString()
                    binding.exchangeRate.text = "BDT " + "$exchangeRate"
                    updateValuesGBP()
                }
            }
        }
    }

    fun updateValuesBDT() {
        val bdtValue = binding.receiveAmount.text.toString().toDoubleOrNull()
        if (bdtValue != null) {
            val gbpValue = bdtValue / rate
            val formattedGBP = decimalFormat.format(gbpValue)
            binding.sendAmount.setText(formattedGBP.toString())
        } else {
            binding.sendAmount.setText("")
        }
    }

    fun updateValuesGBP() {
        val gbpValue = binding.sendAmount.text.toString().toDoubleOrNull()
        if (gbpValue != null) {
            val bdtValue = gbpValue * rate
            val formattedBDT = decimalFormat.format(bdtValue)
            binding.receiveAmount.setText(formattedBDT.toString())
        } else {
            binding.receiveAmount.setText("")
        }
    }

    override fun onPayingAgentBankItemSelected(selectedItem: PayingAgentData) {
        binding.collectionPointInstantCredit.setText(selectedItem.name)
        binding.collectionPointCashPickUp.setText(selectedItem.name)
        payingAgentId = selectedItem.payingAgentId!!.toString()
        bankId = selectedItem.bankId!!.toString()

        calculateRate(
            deviceId,
            personId.toInt(),
            bankId.toInt(),
            payingAgentId.toInt(),
            orderType.toInt(),
            paymentType.toInt(),
            4,
            1,
            0,
            binding.sendAmount.text.toString()
        )

    }

    override fun onPayingAgentWalletItemSelected(selectedItem: PayingAgentData) {
        binding.collectionPointWallet.setText(selectedItem.name)
        payingAgentId = selectedItem.payingAgentId!!.toString()
        bankId = selectedItem.bankId!!.toString()

        calculateRate(
            deviceId,
            personId.toInt(),
            bankId.toInt(),
            payingAgentId.toInt(),
            orderType.toInt(),
            paymentType.toInt(),
            4,
            1,
            0,
            binding.sendAmount.text.toString()
        )
    }

    private fun observePayingAgentResult() {
        calculationViewModel.payingAgentResult.observe(this) { result ->
            if (result!!.data != null) {
                for (item in result.data!!) {
                    if (item!!.bankId == bankId.toInt() && item!!.payingAgentId == payingAgentId.toInt()) {
                        binding.collectionPointInstantCredit.setText(item.name)
                        binding.collectionPointCashPickUp.setText(item.name)
                        binding.collectionPointWallet.setText(item.name)
                    }
                }
            }
        }
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

}
