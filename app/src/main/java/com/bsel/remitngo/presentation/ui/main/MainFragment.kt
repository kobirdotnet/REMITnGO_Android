package com.bsel.remitngo.presentation.ui.main

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import com.bsel.remitngo.bottom_sheet.PayingAgentBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.bank.save_bank_account.SaveBankItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentData
import com.bsel.remitngo.databinding.FragmentMainBinding
import com.bsel.remitngo.interfaceses.OnCalculationSelectedListener
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

    private val payingAgentBottomSheet: PayingAgentBottomSheet by lazy { PayingAgentBottomSheet() }

    private var exchangeRate: Double = 0.0
    private val decimalFormat = DecimalFormat("#.##")

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private var personId: Int = 0
    private var orderType: Int = 0
    private var paymentType: Int = 0
    private var fromCountry: Int = 0
    private var toCountry: Int = 0
    private var mobileOrWebPlatform: Int = 0
    private lateinit var amount: String

    private var bankId: Int = 0
    private var payingAgentId: Int = 0
    private lateinit var bankName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId")!!.toInt()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        (requireActivity().application as Injector).createCalculationSubComponent().inject(this)

        calculationViewModel =
            ViewModelProvider(this, calculationViewModelFactory)[CalculationViewModel::class.java]

        binding.orderModeRadioGroup.check(R.id.bank_account)
        binding.paymentModeRadioGroup.check(R.id.card_payment)

        orderType = 3
        paymentType = 4

        binding.collectionPointBankLayout.visibility = View.GONE
        binding.collectionPointBank.text = null
        binding.collectionPointWalletLayout.visibility = View.GONE
        binding.collectionPointWallet.text = null
        binding.selectedOrderType.text = "Bank Account"

        binding.collectionPointBank.setOnClickListener {
            payingAgentBottomSheet.itemSelectedListener = this
            payingAgentBottomSheet.show(childFragmentManager, payingAgentBottomSheet.tag)
        }

        binding.orderModeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.bank_account -> {
                    binding.collectionPointBankLayout.visibility = View.GONE
                    binding.collectionPointBank.text = null
                    binding.collectionPointWalletLayout.visibility = View.GONE
                    binding.collectionPointWallet.text = null
                    binding.selectedOrderType.text = "Bank Account"
                    orderType = 3

                    bankId = 0
                    payingAgentId = 0
                    fromCountry = 4
                    toCountry = 1
                    mobileOrWebPlatform = 0
                    amount = binding.sendAmount.text.toString()

                    calculateRate(
                        deviceId,
                        personId,
                        bankId,
                        payingAgentId,
                        orderType,
                        paymentType,
                        fromCountry,
                        toCountry,
                        mobileOrWebPlatform,
                        amount
                    )
                }
                R.id.instant_credit -> {
                    binding.collectionPointBankLayout.visibility = View.VISIBLE
                    binding.collectionPointWalletLayout.visibility = View.GONE
                    binding.collectionPointWallet.text = null
                    binding.selectedOrderType.text = "Instant Credit"
                    orderType = 5

                    preferenceManager.saveData("orderType", orderType.toString())
                    preferenceManager.saveData("send_amount", amount.toString())

                    payingAgentBottomSheet.itemSelectedListener = this
                    payingAgentBottomSheet.show(childFragmentManager, payingAgentBottomSheet.tag)
                }
                R.id.cash_pickup -> {
                    binding.collectionPointBankLayout.visibility = View.VISIBLE
                    binding.collectionPointWalletLayout.visibility = View.GONE
                    binding.collectionPointWallet.text = null
                    binding.selectedOrderType.text = "Cash Pickup"
                    orderType = 2

                    preferenceManager.saveData("orderType", orderType.toString())
                    preferenceManager.saveData("send_amount", amount.toString())

                    payingAgentBottomSheet.itemSelectedListener = this
                    payingAgentBottomSheet.show(childFragmentManager, payingAgentBottomSheet.tag)
                }
                R.id.mobile_wallet -> {
                    binding.collectionPointBankLayout.visibility = View.GONE
                    binding.collectionPointBank.text = null
                    binding.collectionPointWalletLayout.visibility = View.VISIBLE
                    binding.selectedOrderType.text = "Mobile Wallet"
                    orderType = 1
                }
            }
        }
        binding.paymentModeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.card_payment -> {
                    paymentType = 4

                    bankId = 0
                    payingAgentId = 0
                    fromCountry = 4
                    toCountry = 1
                    mobileOrWebPlatform = 0
                    amount = binding.sendAmount.text.toString()

                    calculateRate(
                        deviceId,
                        personId,
                        bankId,
                        payingAgentId,
                        orderType,
                        paymentType,
                        fromCountry,
                        toCountry,
                        mobileOrWebPlatform,
                        amount
                    )
                }
                R.id.bank_transfer -> {
                    paymentType = 3

                    bankId = 0
                    payingAgentId = 0
                    fromCountry = 4
                    toCountry = 1
                    mobileOrWebPlatform = 0
                    amount = binding.sendAmount.text.toString()

                    calculateRate(
                        deviceId,
                        personId,
                        bankId,
                        payingAgentId,
                        orderType,
                        paymentType,
                        fromCountry,
                        toCountry,
                        mobileOrWebPlatform,
                        amount
                    )
                }
            }
        }

        //GBP Amount
        binding.sendAmount.setText("100")
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

        //BDT Amount
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

        binding.btnContinue.setOnClickListener {
            val bundle = Bundle().apply {
                putString("paymentType", paymentType.toString())
                putString("orderType", orderType.toString())
            }
            findNavController().navigate(
                R.id.action_nav_main_to_nav_choose_beneficiary,
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

        bankId = 0
        payingAgentId = 0
        fromCountry = 4
        toCountry = 1
        mobileOrWebPlatform = 0
        amount = binding.sendAmount.text.toString()

        calculateRate(
            deviceId,
            personId,
            bankId,
            payingAgentId,
            orderType,
            paymentType,
            fromCountry,
            toCountry,
            mobileOrWebPlatform,
            amount
        )
        observeCalculateRateResult()
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

    private fun observeCalculateRateResult() {
        calculationViewModel.calculateRateResult.observe(this) { result ->
            if (result!!.data != null) {
                for (data in result.data!!) {
                    exchangeRate = data!!.rate!!.toDouble()
                    binding.exchangeRate.text = exchangeRate.toString()

                    updateValuesGBP()
                }
                Log.i("info", "Calculate Rate successful: $result")
            } else {
                Log.i("info", "Calculate Rate failed")
            }
        }
    }

    fun updateValuesBDT() {
        val bdtValue = binding.receiveAmount.text.toString().toDoubleOrNull()
        if (bdtValue != null) {
            val gbpValue = bdtValue / exchangeRate
            val formattedGBP = decimalFormat.format(gbpValue)
            binding.sendAmount.setText(formattedGBP.toString())
        } else {
            binding.sendAmount.setText("")
        }
    }

    fun updateValuesGBP() {
        val gbpValue = binding.sendAmount.text.toString().toDoubleOrNull()
        if (gbpValue != null) {
            val bdtValue = gbpValue * exchangeRate
            val formattedBDT = decimalFormat.format(bdtValue)
            binding.receiveAmount.setText(formattedBDT.toString())
        } else {
            binding.receiveAmount.setText("")
        }
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

    override fun onPayingAgentItemSelected(selectedItem: PayingAgentData) {
        binding.collectionPointBank.setText(selectedItem.name)
        bankId = selectedItem!!.bankId!!
        payingAgentId = selectedItem.payingAgentId!!
        bankName = selectedItem.name.toString()

        preferenceManager.saveData("bankId", bankId.toString())
        preferenceManager.saveData("bankName", bankName)
        preferenceManager.saveData("payingAgentId", payingAgentId.toString())

        fromCountry = 4
        toCountry = 1
        mobileOrWebPlatform = 0
        amount = binding.sendAmount.text.toString()

        calculateRate(
            deviceId,
            personId,
            bankId,
            payingAgentId,
            orderType,
            paymentType,
            fromCountry,
            toCountry,
            mobileOrWebPlatform,
            amount
        )

    }

}
