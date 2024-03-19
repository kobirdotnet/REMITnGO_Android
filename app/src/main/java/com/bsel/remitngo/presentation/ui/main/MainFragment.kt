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
import com.bsel.remitngo.bottomSheet.PayingAgentBankBottomSheet
import com.bsel.remitngo.bottomSheet.PayingAgentWalletBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentData
import com.bsel.remitngo.databinding.FragmentMainBinding
import com.bsel.remitngo.data.interfaceses.OnCalculationSelectedListener
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

    private var exchangeRate: Double = 0.0
    private var commission: Double = 0.0

    private val decimalFormat = DecimalFormat("#.##")

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private var personId: Int = 0

    private var orderType: Int = 3
    private var paymentType: Int = 4

    private var bankId: Int = 0
    private var payingAgentId: Int = 0

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

        binding.collectionPointInstantCreditLayout.visibility = View.GONE
        binding.collectionPointInstantCredit.text = null
        binding.collectionPointInstantCredit.setOnClickListener {
            payingAgentBankBottomSheet.setSelectedOrderType("5", binding.sendAmount.text.toString())
            payingAgentBankBottomSheet.itemSelectedListener = this
            payingAgentBankBottomSheet.show(childFragmentManager, payingAgentBankBottomSheet.tag)
        }

        binding.collectionPointCashPickUpLayout.visibility = View.GONE
        binding.collectionPointCashPickUp.text = null
        binding.collectionPointCashPickUp.setOnClickListener {
            payingAgentBankBottomSheet.setSelectedOrderType("2", binding.sendAmount.text.toString())
            payingAgentBankBottomSheet.itemSelectedListener = this
            payingAgentBankBottomSheet.show(childFragmentManager, payingAgentBankBottomSheet.tag)
        }

        binding.collectionPointWalletLayout.visibility = View.GONE
        binding.collectionPointWallet.text = null
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
                R.id.bank_account -> {
                    binding.collectionPointInstantCreditLayout.visibility = View.GONE
                    binding.collectionPointInstantCredit.text = null

                    binding.collectionPointCashPickUpLayout.visibility = View.GONE
                    binding.collectionPointCashPickUp.text = null

                    binding.collectionPointWalletLayout.visibility = View.GONE
                    binding.collectionPointWallet.text = null

                    orderType = 3

                    calculateRate(
                        deviceId,
                        personId,
                        bankId,
                        payingAgentId,
                        orderType,
                        paymentType,
                        4,
                        1,
                        0,
                        binding.sendAmount.text.toString()
                    )

                }
                R.id.instant_credit -> {
                    binding.collectionPointInstantCreditLayout.visibility = View.VISIBLE
                    binding.collectionPointInstantCredit.text = null

                    binding.collectionPointCashPickUpLayout.visibility = View.GONE
                    binding.collectionPointCashPickUp.text = null

                    binding.collectionPointWalletLayout.visibility = View.GONE
                    binding.collectionPointWallet.text = null

                    orderType = 5
                }
                R.id.cash_pickup -> {
                    binding.collectionPointInstantCreditLayout.visibility = View.GONE
                    binding.collectionPointInstantCredit.text = null

                    binding.collectionPointCashPickUpLayout.visibility = View.VISIBLE
                    binding.collectionPointCashPickUp.text = null

                    binding.collectionPointWalletLayout.visibility = View.GONE
                    binding.collectionPointWallet.text = null

                    orderType = 2
                }
                R.id.mobile_wallet -> {
                    binding.collectionPointInstantCreditLayout.visibility = View.GONE
                    binding.collectionPointInstantCredit.text = null

                    binding.collectionPointCashPickUpLayout.visibility = View.GONE
                    binding.collectionPointCashPickUp.text = null

                    binding.collectionPointWalletLayout.visibility = View.VISIBLE
                    binding.collectionPointWallet.text = null

                    orderType = 1
                }
            }
        }
        binding.paymentModeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.card_payment -> {
                    paymentType = 4
                }
                R.id.bank_transfer -> {
                    paymentType = 3
                }
            }
        }

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

        binding.extraPercentage.setText("Recipients get an extra 2.5% on their transfer!")
        binding.extraPercentage.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bracsaajanexchange.com"))
            startActivity(intent)
        }

        binding.learnMore.setText("Learn more!")
        binding.learnMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bracsaajanexchange.com"))
            startActivity(intent)
        }


        binding.btnContinue.setOnClickListener {
            val bundle = Bundle().apply {
                putString("paymentType", paymentType.toString())
                putString("orderType", orderType.toString())
                putString("sendAmount", binding.sendAmount.text.toString())
                putString("receiveAmount", binding.receiveAmount.text.toString())
                putString("exchangeRate", exchangeRate.toString())
                putString("commission", commission.toString())
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

        calculateRate(
            deviceId,
            personId,
            bankId,
            payingAgentId,
            orderType,
            paymentType,
            4,
            1,
            0,
            binding.sendAmount.text.toString()
        )
        observeCalculateRateResult()
    }

    private fun observeCalculateRateResult() {
        calculationViewModel.calculateRateResult.observe(this) { result ->
            if (result!!.data != null) {
                for (data in result.data!!) {
                    commission = data!!.commission!!.toDouble()
                    exchangeRate = data!!.rate!!.toDouble()
                    binding.exchangeRate.text = "BDT " + "$exchangeRate"
                    updateValuesGBP()
                }
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

    override fun onPayingAgentBankItemSelected(selectedItem: PayingAgentData) {
        binding.collectionPointInstantCredit.setText(selectedItem.name)
        binding.collectionPointCashPickUp.setText(selectedItem.name)
        payingAgentId = selectedItem.payingAgentId!!

        bankId = selectedItem!!.bankId!!

        calculateRate(
            deviceId,
            personId,
            bankId,
            payingAgentId,
            orderType,
            paymentType,
            4,
            1,
            0,
            binding.sendAmount.text.toString()
        )

    }

    override fun onPayingAgentWalletItemSelected(selectedItem: PayingAgentData) {
        binding.collectionPointWallet.setText(selectedItem.name)

        payingAgentId = selectedItem.payingAgentId!!
        bankId = selectedItem!!.bankId!!

        calculateRate(
            deviceId,
            personId,
            bankId,
            payingAgentId,
            orderType,
            paymentType,
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

}
