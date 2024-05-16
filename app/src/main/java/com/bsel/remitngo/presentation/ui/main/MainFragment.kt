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

    private var paymentMode: Int = 4
    private var orderType: Int = 3

    private var sendAmount: Double = 100.0
    private var beneAmount: Double = 0.0

    private var rate: Double = 0.0
    private var commission: Double = 0.0

    private var totalAmount: Double = 0.0

    private var beneWalletId: Int = 0
    private var beneWalletName: String? = null

    private var beneBankId: Int = 0
    private var beneBankName: String? = null

    private var beneBranchId: Int = 0
    private var beneBranchName: String? = null

    private var beneAccountNo: String? = null
    private var beneWalletNo: String? = null

    private var payingAgentId: Int = 0

    private var benePersonId: Int = 0
    private var beneId: Int = 0

    private var beneAccountName: String? = null
    private var beneMobile: String? = null

    private var purposeOfTransferId: Int = 0
    private var purposeOfTransferName: String? = null

    private var sourceOfFundId: Int = 0
    private var sourceOfFundName: String? = null

    private lateinit var percentageUrl: String

    private var calculationType: Int = 1

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

        try {
            paymentMode = arguments?.getString("paymentMode").toString().toInt()
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
            beneAmount = arguments?.getString("beneAmount").toString().toDouble()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            rate = arguments?.getString("rate").toString().toDouble()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            commission = arguments?.getString("commission").toString().toDouble()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            totalAmount = arguments?.getString("totalAmount").toString().toDouble()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            beneBankId = arguments?.getString("beneBankId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            beneBankName = arguments?.getString("beneBankName").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        try {
            beneBranchId = arguments?.getString("beneBranchId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            beneBranchName = arguments?.getString("beneBranchName").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        try {
            beneWalletId = arguments?.getString("beneWalletId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            beneWalletName = arguments?.getString("beneWalletName").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        try {
            beneAccountNo = arguments?.getString("beneAccountNo").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        try {
            beneWalletNo = arguments?.getString("beneWalletNo").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        try {
            payingAgentId = arguments?.getString("payingAgentId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            benePersonId = arguments?.getString("benePersonId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            beneId = arguments?.getString("beneId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            beneAccountName = arguments?.getString("beneAccountName").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        try {
            beneMobile = arguments?.getString("beneMobile").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        try {
            purposeOfTransferId = arguments?.getString("purposeOfTransferId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            purposeOfTransferName = arguments?.getString("purposeOfTransferName").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        try {
            sourceOfFundId = arguments?.getString("sourceOfFundId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            sourceOfFundName = arguments?.getString("sourceOfFundName").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }


        when (orderType) {
            5 -> {
                binding.iconBankTransfer.setImageResource(R.drawable.bank_account_blue)
                binding.iconInstantCredit.setImageResource(R.drawable.instant_credit_white)
                binding.iconCashPickup.setImageResource(R.drawable.cash_picup_blue)
                binding.iconMobileWallet.setImageResource(R.drawable.wallet_blue)

                binding.iconBankTransfer.setBackgroundResource(R.drawable.image_border_grey)
                binding.iconInstantCredit.setBackgroundResource(R.drawable.image_border_blue)
                binding.iconCashPickup.setBackgroundResource(R.drawable.image_border_grey)
                binding.iconMobileWallet.setBackgroundResource(R.drawable.image_border_grey)

                binding.bankAccount.setBackgroundResource(R.drawable.border_gray)
                binding.instantCredit.setBackgroundResource(R.drawable.border_blue)
                binding.cashPickup.setBackgroundResource(R.drawable.border_gray)
                binding.mobileWallet.setBackgroundResource(R.drawable.border_gray)

                binding.collectionPointInstantCreditLayout.visibility = View.VISIBLE
                binding.collectionPointCashPickUpLayout.visibility = View.GONE
                binding.collectionPointWalletLayout.visibility = View.GONE
            }
            2 -> {
                binding.iconBankTransfer.setImageResource(R.drawable.bank_account_blue)
                binding.iconInstantCredit.setImageResource(R.drawable.instant_credit_blue)
                binding.iconCashPickup.setImageResource(R.drawable.cash_picup_white)
                binding.iconMobileWallet.setImageResource(R.drawable.wallet_blue)

                binding.iconBankTransfer.setBackgroundResource(R.drawable.image_border_grey)
                binding.iconInstantCredit.setBackgroundResource(R.drawable.image_border_grey)
                binding.iconCashPickup.setBackgroundResource(R.drawable.image_border_blue)
                binding.iconMobileWallet.setBackgroundResource(R.drawable.image_border_grey)

                binding.bankAccount.setBackgroundResource(R.drawable.border_gray)
                binding.instantCredit.setBackgroundResource(R.drawable.border_gray)
                binding.cashPickup.setBackgroundResource(R.drawable.border_blue)
                binding.mobileWallet.setBackgroundResource(R.drawable.border_gray)

                binding.collectionPointInstantCreditLayout.visibility = View.GONE
                binding.collectionPointCashPickUpLayout.visibility = View.VISIBLE
                binding.collectionPointWalletLayout.visibility = View.GONE
            }
            1 -> {
                binding.iconBankTransfer.setImageResource(R.drawable.bank_account_blue)
                binding.iconInstantCredit.setImageResource(R.drawable.instant_credit_blue)
                binding.iconCashPickup.setImageResource(R.drawable.cash_picup_blue)
                binding.iconMobileWallet.setImageResource(R.drawable.wallet_white)

                binding.iconBankTransfer.setBackgroundResource(R.drawable.image_border_grey)
                binding.iconInstantCredit.setBackgroundResource(R.drawable.image_border_grey)
                binding.iconCashPickup.setBackgroundResource(R.drawable.image_border_grey)
                binding.iconMobileWallet.setBackgroundResource(R.drawable.image_border_blue)

                binding.bankAccount.setBackgroundResource(R.drawable.border_gray)
                binding.instantCredit.setBackgroundResource(R.drawable.border_gray)
                binding.cashPickup.setBackgroundResource(R.drawable.border_gray)
                binding.mobileWallet.setBackgroundResource(R.drawable.border_blue)

                binding.collectionPointInstantCreditLayout.visibility = View.GONE
                binding.collectionPointCashPickUpLayout.visibility = View.GONE
                binding.collectionPointWalletLayout.visibility = View.VISIBLE
            }
            3 -> {
                binding.iconBankTransfer.setImageResource(R.drawable.bank_account_white)
                binding.iconInstantCredit.setImageResource(R.drawable.instant_credit_blue)
                binding.iconCashPickup.setImageResource(R.drawable.cash_picup_blue)
                binding.iconMobileWallet.setImageResource(R.drawable.wallet_blue)

                binding.iconBankTransfer.setBackgroundResource(R.drawable.image_border_blue)
                binding.iconInstantCredit.setBackgroundResource(R.drawable.image_border_grey)
                binding.iconCashPickup.setBackgroundResource(R.drawable.image_border_grey)
                binding.iconMobileWallet.setBackgroundResource(R.drawable.image_border_grey)

                binding.bankAccount.setBackgroundResource(R.drawable.border_blue)
                binding.instantCredit.setBackgroundResource(R.drawable.border_gray)
                binding.cashPickup.setBackgroundResource(R.drawable.border_gray)
                binding.mobileWallet.setBackgroundResource(R.drawable.border_gray)

                binding.collectionPointInstantCreditLayout.visibility = View.GONE
                binding.collectionPointCashPickUpLayout.visibility = View.GONE
                binding.collectionPointWalletLayout.visibility = View.GONE
            }
        }

        when (paymentMode) {
            4 -> {
                binding.iconCreditOrDebit.setImageResource(R.drawable.credit_debit_white)
                binding.iconBankTransferWithCommission.setImageResource(R.drawable.bank_account_blue)

                binding.iconCreditOrDebit.setBackgroundResource(R.drawable.image_border_blue)
                binding.iconBankTransferWithCommission.setBackgroundResource(R.drawable.image_border_grey)

                binding.cardPayment.setBackgroundResource(R.drawable.border_blue)
                binding.bankPayment.setBackgroundResource(R.drawable.border_gray)
            }
            5 -> {
                binding.iconCreditOrDebit.setImageResource(R.drawable.credit_debit_blue)
                binding.iconBankTransferWithCommission.setImageResource(R.drawable.bank_account_white)

                binding.iconCreditOrDebit.setBackgroundResource(R.drawable.image_border_grey)
                binding.iconBankTransferWithCommission.setBackgroundResource(R.drawable.image_border_blue)

                binding.cardPayment.setBackgroundResource(R.drawable.border_gray)
                binding.bankPayment.setBackgroundResource(R.drawable.border_blue)
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

        binding.bankAccount.setOnClickListener {
            binding.iconBankTransfer.setImageResource(R.drawable.bank_account_white)
            binding.iconInstantCredit.setImageResource(R.drawable.instant_credit_blue)
            binding.iconCashPickup.setImageResource(R.drawable.cash_picup_blue)
            binding.iconMobileWallet.setImageResource(R.drawable.wallet_blue)

            binding.iconBankTransfer.setBackgroundResource(R.drawable.image_border_blue)
            binding.iconInstantCredit.setBackgroundResource(R.drawable.image_border_grey)
            binding.iconCashPickup.setBackgroundResource(R.drawable.image_border_grey)
            binding.iconMobileWallet.setBackgroundResource(R.drawable.image_border_grey)

            binding.bankAccount.setBackgroundResource(R.drawable.border_blue)
            binding.instantCredit.setBackgroundResource(R.drawable.border_gray)
            binding.cashPickup.setBackgroundResource(R.drawable.border_gray)
            binding.mobileWallet.setBackgroundResource(R.drawable.border_gray)

            orderType = 3
            calculationType = 1
            calculateRate(
                binding.sendAmount.text.toString(),
                beneBankId,
                calculationType,
                deviceId,
                4,
                0,
                orderType,
                payingAgentId,
                paymentMode,
                1
            )

            binding.collectionPointInstantCreditLayout.visibility = View.GONE
            binding.collectionPointCashPickUpLayout.visibility = View.GONE
            binding.collectionPointWalletLayout.visibility = View.GONE
        }

        binding.instantCredit.setOnClickListener {
            binding.iconBankTransfer.setImageResource(R.drawable.bank_account_blue)
            binding.iconInstantCredit.setImageResource(R.drawable.instant_credit_white)
            binding.iconCashPickup.setImageResource(R.drawable.cash_picup_blue)
            binding.iconMobileWallet.setImageResource(R.drawable.wallet_blue)

            binding.iconBankTransfer.setBackgroundResource(R.drawable.image_border_grey)
            binding.iconInstantCredit.setBackgroundResource(R.drawable.image_border_blue)
            binding.iconCashPickup.setBackgroundResource(R.drawable.image_border_grey)
            binding.iconMobileWallet.setBackgroundResource(R.drawable.image_border_grey)

            binding.bankAccount.setBackgroundResource(R.drawable.border_gray)
            binding.instantCredit.setBackgroundResource(R.drawable.border_blue)
            binding.cashPickup.setBackgroundResource(R.drawable.border_gray)
            binding.mobileWallet.setBackgroundResource(R.drawable.border_gray)

            orderType = 5

            binding.collectionPointInstantCreditLayout.visibility = View.VISIBLE
            binding.collectionPointCashPickUpLayout.visibility = View.GONE
            binding.collectionPointWalletLayout.visibility = View.GONE
        }

        binding.cashPickup.setOnClickListener {
            binding.iconBankTransfer.setImageResource(R.drawable.bank_account_blue)
            binding.iconInstantCredit.setImageResource(R.drawable.instant_credit_blue)
            binding.iconCashPickup.setImageResource(R.drawable.cash_picup_white)
            binding.iconMobileWallet.setImageResource(R.drawable.wallet_blue)

            binding.iconBankTransfer.setBackgroundResource(R.drawable.image_border_grey)
            binding.iconInstantCredit.setBackgroundResource(R.drawable.image_border_grey)
            binding.iconCashPickup.setBackgroundResource(R.drawable.image_border_blue)
            binding.iconMobileWallet.setBackgroundResource(R.drawable.image_border_grey)

            binding.bankAccount.setBackgroundResource(R.drawable.border_gray)
            binding.instantCredit.setBackgroundResource(R.drawable.border_gray)
            binding.cashPickup.setBackgroundResource(R.drawable.border_blue)
            binding.mobileWallet.setBackgroundResource(R.drawable.border_gray)

            orderType = 2
            binding.collectionPointInstantCreditLayout.visibility = View.GONE
            binding.collectionPointCashPickUpLayout.visibility = View.VISIBLE
            binding.collectionPointWalletLayout.visibility = View.GONE
        }

        binding.mobileWallet.setOnClickListener {
            binding.iconBankTransfer.setImageResource(R.drawable.bank_account_blue)
            binding.iconInstantCredit.setImageResource(R.drawable.instant_credit_blue)
            binding.iconCashPickup.setImageResource(R.drawable.cash_picup_blue)
            binding.iconMobileWallet.setImageResource(R.drawable.wallet_white)

            binding.iconBankTransfer.setBackgroundResource(R.drawable.image_border_grey)
            binding.iconInstantCredit.setBackgroundResource(R.drawable.image_border_grey)
            binding.iconCashPickup.setBackgroundResource(R.drawable.image_border_grey)
            binding.iconMobileWallet.setBackgroundResource(R.drawable.image_border_blue)

            binding.bankAccount.setBackgroundResource(R.drawable.border_gray)
            binding.instantCredit.setBackgroundResource(R.drawable.border_gray)
            binding.cashPickup.setBackgroundResource(R.drawable.border_gray)
            binding.mobileWallet.setBackgroundResource(R.drawable.border_blue)

            orderType = 1
            binding.collectionPointInstantCreditLayout.visibility = View.GONE
            binding.collectionPointCashPickUpLayout.visibility = View.GONE
            binding.collectionPointWalletLayout.visibility = View.VISIBLE
        }

        binding.cardPayment.setOnClickListener {
            binding.iconCreditOrDebit.setImageResource(R.drawable.credit_debit_white)
            binding.iconBankTransferWithCommission.setImageResource(R.drawable.bank_account_blue)

            binding.iconCreditOrDebit.setBackgroundResource(R.drawable.image_border_blue)
            binding.iconBankTransferWithCommission.setBackgroundResource(R.drawable.image_border_grey)

            binding.cardPayment.setBackgroundResource(R.drawable.border_blue)
            binding.bankPayment.setBackgroundResource(R.drawable.border_gray)

            paymentMode = 4
            calculationType = 1
            calculateRate(
                binding.sendAmount.text.toString(),
                beneBankId,
                calculationType,
                deviceId,
                4,
                0,
                orderType,
                payingAgentId,
                paymentMode,
                1
            )
        }

        binding.bankPayment.setOnClickListener {
            binding.iconCreditOrDebit.setImageResource(R.drawable.credit_debit_blue)
            binding.iconBankTransferWithCommission.setImageResource(R.drawable.bank_account_white)

            binding.iconCreditOrDebit.setBackgroundResource(R.drawable.image_border_grey)
            binding.iconBankTransferWithCommission.setBackgroundResource(R.drawable.image_border_blue)

            binding.cardPayment.setBackgroundResource(R.drawable.border_gray)
            binding.bankPayment.setBackgroundResource(R.drawable.border_blue)

            paymentMode = 5
            calculationType = 1
            calculateRate(
                binding.sendAmount.text.toString(),
                beneBankId,
                calculationType,
                deviceId,
                4,
                0,
                orderType,
                payingAgentId,
                paymentMode,
                1
            )
        }

        binding.sendAmount.setText(sendAmount.toString())
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
                calculationType = 1
                calculateRate(
                    binding.sendAmount.text.toString(),
                    beneBankId,
                    calculationType,
                    deviceId,
                    4,
                    0,
                    orderType,
                    payingAgentId,
                    paymentMode,
                    1
                )
            }
        })
        binding.beneAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!binding.beneAmount.isFocused) return
                calculationType = 2
                calculateRate(
                    binding.beneAmount.text.toString(),
                    beneBankId,
                    calculationType,
                    deviceId,
                    4,
                    0,
                    orderType,
                    payingAgentId,
                    paymentMode,
                    1
                )
            }
        })

        val percentageItem = PercentageItem(
            messageType = 1,
            parameter1 = 1,
            parameter2 = customerId
        )
        calculationViewModel.percentage(percentageItem)

        binding.learnMore.text = "Learn more!"
        binding.learnMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bracsaajanexchange.com"))
            startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            try {
                val sendAmountValue = binding.sendAmount.text.toString()
                sendAmount = sendAmountValue.replace(Regex("[^\\d.]"), "").toDouble()
            } catch (e: NumberFormatException) {
                e.localizedMessage
            }

            try {
                val receiveAmountValue = binding.beneAmount.text.toString()
                beneAmount = receiveAmountValue.replace(Regex("[^\\d.]"), "").toDouble()
            } catch (e: NumberFormatException) {
                e.localizedMessage
            }

            try {
                val rateValue = binding.rate.text.toString()
                rate = rateValue.replace(Regex("[^\\d.]"), "").toDouble()
            } catch (e: NumberFormatException) {
                e.localizedMessage
            }

            try {
                val commissionValue = binding.commission.text.toString()
                commission = commissionValue.replace(Regex("[^\\d.]"), "").toDouble()
            } catch (e: NumberFormatException) {
                e.localizedMessage
            }


            val bundle = Bundle().apply {

                putString("paymentMode", paymentMode.toString())
                putString("orderType", orderType.toString())

                putString("sendAmount", sendAmount.toString())
                putString("beneAmount", beneAmount.toString())

                putString("rate", rate.toString())
                putString("commission", commission.toString())

                putString("totalAmount", totalAmount.toString())

                putString("beneBankId", beneBankId.toString())
                putString("beneBankName", beneBankName)

                putString("beneBranchId", beneBranchId.toString())
                putString("beneBranchName", beneBranchName.toString())

                putString("beneWalletId", beneWalletId.toString())
                putString("beneWalletName", beneWalletName.toString())

                putString("beneAccountNo", beneAccountNo)
                putString("beneWalletNo", beneWalletNo)

                putString("payingAgentId", payingAgentId.toString())

                putString("benePersonId", benePersonId.toString())
                putString("beneId", beneId.toString())

                putString("beneAccountName", beneAccountName)
                putString("beneMobile", beneMobile)

                putString("purposeOfTransferId", purposeOfTransferId.toString())
                putString("purposeOfTransferName", purposeOfTransferName)

                putString("sourceOfFundId", sourceOfFundId.toString())
                putString("sourceOfFundName", sourceOfFundName)
            }
            findNavController().navigate(
                R.id.action_nav_main_to_nav_review,
                bundle
            )
        }

        calculationType = 1
        calculateRate(
            binding.sendAmount.text.toString(),
            beneBankId,
            calculationType,
            deviceId,
            4,
            0,
            orderType,
            payingAgentId,
            paymentMode,
            1
        )

        val payingAgentItem = PayingAgentItem(
            deviceId = deviceId,
            fromCountryId = 4,
            toCountryId = 1,
            orderTypeId = orderType,
            amount = sendAmount.toInt()
        )
        calculationViewModel.payingAgent(payingAgentItem)

        observeCalculateRateResult()
        observePayingAgentResult()
        observeExtraPercentageResult()
    }

    private fun observeCalculateRateResult() {
        calculationViewModel.calculateRateResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (data in result.data!!) {

                        if (calculationType == 1) {
                            beneAmount = data!!.beneficiaryAmount!!
                            binding.beneAmount.setText("$beneAmount")
                        } else if (calculationType == 2) {
                            sendAmount = data!!.senderAmount!!
                            binding.sendAmount.setText("$sendAmount")
                        }

                        commission = data!!.commission!!
                        binding.commission.text = "Fee $commission GBP"

                        rate = data.rate!!.toDouble()
                        binding.rate.text = "BDT $rate"

                        totalAmount = data.totalAmount!!.toDouble()
                    }
                } else {
                    if (calculationType == 1) {
                        beneAmount = 0.0
                        binding.beneAmount.setText("$beneAmount")
                    } else if (calculationType == 2) {
                        sendAmount = 0.0
                        binding.sendAmount.setText("$sendAmount")
                    }

                    commission = 0.0
                    binding.commission.text = "Fee $commission GBP"

                    rate = 0.0
                    binding.rate.text = "BDT $rate"

                    totalAmount = 0.0
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

    override fun onPayingAgentInstantCreditItemSelected(selectedItem: PayingAgentData) {
        binding.collectionPointInstantCredit.setText(selectedItem.name)
        payingAgentId = selectedItem.payingAgentId!!.toString().toInt()
        beneBankId = selectedItem.bankId!!.toString().toInt()
        beneWalletId = selectedItem.walletId!!.toString().toInt()

        calculationType = 1
        calculateRate(
            binding.sendAmount.text.toString(),
            beneBankId,
            calculationType,
            deviceId,
            4,
            0,
            orderType,
            payingAgentId,
            paymentMode,
            1
        )
    }

    override fun onPayingAgentCashPickupItemSelected(selectedItem: PayingAgentData) {
        binding.collectionPointCashPickUp.setText(selectedItem.name)
        payingAgentId = selectedItem.payingAgentId!!.toString().toInt()
        beneBankId = selectedItem.bankId!!.toString().toInt()
        beneWalletId = selectedItem.walletId!!.toString().toInt()

        calculationType = 1
        calculateRate(
            binding.sendAmount.text.toString(),
            beneBankId,
            calculationType,
            deviceId,
            4,
            0,
            orderType,
            payingAgentId,
            paymentMode,
            1
        )
    }

    override fun onPayingAgentWalletItemSelected(selectedItem: PayingAgentData) {
        binding.collectionPointWallet.setText(selectedItem.name)
        payingAgentId = selectedItem.payingAgentId!!.toString().toInt()
        beneBankId = selectedItem.bankId!!.toString().toInt()
        beneWalletId = selectedItem.walletId!!.toString().toInt()

        calculationType = 1
        calculateRate(
            binding.sendAmount.text.toString(),
            beneBankId,
            calculationType,
            deviceId,
            4,
            0,
            orderType,
            payingAgentId,
            paymentMode,
            1
        )
    }

    private fun calculateRate(
        amount: String,
        bankId: Int,
        calculationType: Int,
        deviceId: String,
        fromCountry: Int,
        mobileOrWebPlatform: Int,
        orderType: Int,
        payingAgentId: Int,
        paymentMode: Int,
        toCountry: Int
    ) {
        val calculateRateItem = CalculateRateItem(
            amount = amount,
            bankId = bankId,
            calculationType = calculationType,
            deviceId = deviceId,
            fromCountry = fromCountry,
            mobileOrWebPlatform = mobileOrWebPlatform,
            orderType = orderType,
            payingAgentId = payingAgentId,
            paymentMode = paymentMode,
            toCountry = toCountry
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
