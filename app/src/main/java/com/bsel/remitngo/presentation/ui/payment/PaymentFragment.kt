package com.bsel.remitngo.presentation.ui.payment

import android.content.Context
import android.net.wifi.WifiManager
import android.os.*
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.*
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnBeneficiarySelectedListener
import com.bsel.remitngo.data.interfaceses.OnRequireDocumentListener
import com.bsel.remitngo.data.model.bank.bank_account.GetBankData
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryData
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerItem
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentItem
import com.bsel.remitngo.data.model.emp.EmpItem
import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.profile.ProfileItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeData
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.data.model.promoCode.PromoItem
import com.bsel.remitngo.data.model.reason.ReasonData
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.databinding.FragmentPaymentBinding
import com.bsel.remitngo.presentation.di.Injector
import com.emerchantpay.gateway.genesisandroid.api.constants.*
import com.emerchantpay.gateway.genesisandroid.api.constants.recurring.RecurringCategory
import com.emerchantpay.gateway.genesisandroid.api.constants.recurring.RecurringType
import com.emerchantpay.gateway.genesisandroid.api.interfaces.financial.threedsv2.definitions.*
import com.emerchantpay.gateway.genesisandroid.api.internal.Genesis
import com.emerchantpay.gateway.genesisandroid.api.internal.request.PaymentRequest
import com.emerchantpay.gateway.genesisandroid.api.internal.request.TransactionTypesRequest
import com.emerchantpay.gateway.genesisandroid.api.models.*
import com.emerchantpay.gateway.genesisandroid.api.models.Currency
import com.emerchantpay.gateway.genesisandroid.api.models.threedsv2.ThreeDsV2CardHolderAccountParams
import com.emerchantpay.gateway.genesisandroid.api.models.threedsv2.ThreeDsV2MerchantRiskParams
import com.emerchantpay.gateway.genesisandroid.api.models.threedsv2.ThreeDsV2Params
import com.emerchantpay.gateway.genesisandroid.api.models.threedsv2.ThreeDsV2RecurringParams
import com.emerchantpay.gateway.genesisandroid.api.ui.AlertDialogHandler
import com.emerchantpay.gateway.genesisandroid.api.util.Configuration
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class PaymentFragment : Fragment(), OnBeneficiarySelectedListener, OnRequireDocumentListener {
    @Inject
    lateinit var paymentViewModelFactory: PaymentViewModelFactory
    private lateinit var paymentViewModel: PaymentViewModel

    private lateinit var binding: FragmentPaymentBinding

    private lateinit var preferenceManager: PreferenceManager

    private val chooseRecipientBottomSheet: ChooseRecipientBottomSheet by lazy { ChooseRecipientBottomSheet() }
    private val purposeOfTransferBottomSheet: PurposeOfTransferBottomSheet by lazy { PurposeOfTransferBottomSheet() }
    private val sourceOfFundBottomSheet: SourceOfFundBottomSheet by lazy { SourceOfFundBottomSheet() }
    private val transactionOtpVerifyBottomSheet: TransactionOtpVerifyBottomSheet by lazy { TransactionOtpVerifyBottomSheet() }
    private val addressVerifyBottomSheet: AddressVerifyBottomSheet by lazy { AddressVerifyBottomSheet() }
    private val requireDocumentBottomSheet: RequireDocumentBottomSheet by lazy { RequireDocumentBottomSheet() }

    private var customerId: Int = 0
    private var personId: Int = 0
    private var customerPostCode: String? = null
    private var customerFirstName: String? = null
    private var customerLastName: String? = null
    private var customerAddress: String? = null
    private var customerState: String? = null
    private var customerCity: String? = null
    private var customerEmail: String? = null
    private var customerMobile: String? = null
    private var isMobileOTPValidate: Boolean = true
    private var requireDoc: String? = null

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private lateinit var currentDate: String

    private lateinit var transactionCodeWithChannel: String
    private lateinit var encryptCode: String

    lateinit var dialogHandler: AlertDialogHandler
    lateinit var error: GenesisError
    private lateinit var paymentRequest: PaymentRequest
    private lateinit var configuration: Configuration
    private lateinit var transactionTypes: TransactionTypesRequest
    private lateinit var billingAddress: PaymentAddress
    private lateinit var riskParams: RiskParams
    private lateinit var threeDsV2Params: ThreeDsV2Params
    private lateinit var genesis: Genesis
    private lateinit var consumerId: String

    private val decimalFormat = DecimalFormat("#.##")

    private lateinit var transactionCode: String
    private var gbpValue = 0.0

    private var beneAccountName: String? = null
    private var beneBankName: String? = null
    private var beneAccountNo: String? = null
    private var beneAmount: Double = 0.0
    private var modifiedBeneAmount: Double = 0.0
    private var beneBankId: Int = 0
    private var beneBranchId: Int = 0
    private var beneBranchName: String? = null
    private var beneId: Int = 0
    private var beneMobile: String? = null
    private var benePersonId: Int = 0
    private var beneWalletId: Int = 0
    private var beneWalletName: String? = null
    private var beneWalletNo: String? = null
    private var channelId: Int = 1
    private var commission: Double = 0.0
    private var fromCountryId: Int = 4
    private var fromCurrencyId: Int = 96
    private var latitude: String? = null
    private var longitude: String? = null

    private var modifiedCommission: Double = 0.0
    private var modifiedRate: Double = 0.0
    private var modifiedSendAmount: Double = 0.0
    private var orderType: Int = 0
    private var payingAgentId: Int = 0
    private var paymentMode: Int = 0
    private var promoCode: String? = null
    private var purposeOfTransferId: Int = 0
    private var purposeOfTransferName: String? = null
    private var rate: Double = 0.0
    private var sendAmount: Double = 0.0
    private var sourceOfFundId: Int = 0
    private var sourceOfFundName: String? = null
    private var toCountryId: Int = 1
    private var toCurrencyId: Int = 6
    private var totalAmount: Double = 0.0

    private var calculationType: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(view)

        (requireActivity().application as Injector).createPaymentSubComponent().inject(this)

        paymentViewModel =
            ViewModelProvider(this, paymentViewModelFactory)[PaymentViewModel::class.java]

        receiverNameFocusListener()
        receiverAccountFocusListener()
        reasonFocusListener()
        sourceOfIncomeFocusListener()
        promoCodeFocusListener()

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

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())
        currentDate = getCurrentDate(requireContext())


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
            if (sendAmount.toString() != "null") {
                binding.sendAmount.text = "GBP $sendAmount"
            }
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            beneAmount = arguments?.getString("beneAmount").toString().toDouble()
            if (beneAmount.toString() != "null") {
                binding.receiveAmount.text = "BDT $beneAmount"
            }
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            rate = arguments?.getString("rate").toString().toDouble()
            if (rate.toString() != "null") {
                binding.exchangeRate.text = "GBP $rate"
            }
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            commission = arguments?.getString("commission").toString().toDouble()
            if (commission.toString() != "null") {
                binding.transferFee.text = "GBP $commission"
            }
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        totalAmount = sendAmount + commission
        binding.totalAmount.text = "GBP $totalAmount"

        try {
            beneBankId = arguments?.getString("beneBankId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }

        try {
            beneBankName = arguments?.getString("beneBankName").toString()
            if (beneBankName != "null") {
                binding.receiverBankName.text = beneBankName!!
            }
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
            if (beneAccountNo != "null") {
                binding.receiverAccount.text = beneAccountNo!!
            }
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
            if (beneAccountName != "null") {
                binding.receiverName.text = beneAccountName!!
            }
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
            if (purposeOfTransferName != "null") {
                binding.reason.setText("$purposeOfTransferName")
            }
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
            if (sourceOfFundName != "null") {
                binding.sourceOfIncome.setText("$sourceOfFundName")
            }
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        val consumerItem = ConsumerItem(
            deviceId = deviceId, personId = personId
        )
        paymentViewModel.consumer(consumerItem)
        paymentViewModel.consumerResult.observe(this) { result ->
            try {
                if (result!!.consumerData != null) {
                    consumerId = result.consumerData!!.consumerId.toString()
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }

        binding.reason.setOnClickListener {
            purposeOfTransferBottomSheet.itemSelectedListener = this
            purposeOfTransferBottomSheet.show(
                childFragmentManager, purposeOfTransferBottomSheet.tag
            )
        }

        binding.sourceOfIncome.setOnClickListener {
            sourceOfFundBottomSheet.itemSelectedListener = this
            sourceOfFundBottomSheet.show(childFragmentManager, sourceOfFundBottomSheet.tag)
        }

        binding.chooseReceiver.setOnClickListener {
            chooseRecipientBottomSheet.itemSelectedListener = this
            chooseRecipientBottomSheet.setOrderType(
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
                beneMobile
            )
            chooseRecipientBottomSheet.show(childFragmentManager, chooseRecipientBottomSheet.tag)
        }

        binding.modify.setOnClickListener {
            val sendAmountValue = binding.sendAmount.text.toString()
            modifiedSendAmount = sendAmountValue.replace(Regex("[^\\d.]"), "").toDouble()

            val receiveAmountValue = binding.receiveAmount.text.toString()
            modifiedBeneAmount = receiveAmountValue.replace(Regex("[^\\d.]"), "").toDouble()

            val rateValue = binding.exchangeRate.text.toString()
            modifiedRate = rateValue.replace(Regex("[^\\d.]"), "").toDouble()

            val commissionValue = binding.transferFee.text.toString()
            modifiedCommission = commissionValue.replace(Regex("[^\\d.]"), "").toDouble()

            val totalAmountValue = binding.totalAmount.text.toString()
            totalAmount = totalAmountValue.replace(Regex("[^\\d.]"), "").toDouble()

            val bundle = Bundle().apply {

                putString("paymentMode", paymentMode.toString())
                putString("orderType", orderType.toString())

                putString("sendAmount", modifiedSendAmount.toString())
                putString("beneAmount", modifiedBeneAmount.toString())

                putString("rate", modifiedRate.toString())
                putString("commission", modifiedCommission.toString())

                putString("beneBankId", beneBankId.toString())
                putString("beneBankName", beneBankName)

                putString("beneBranchId", beneBranchId.toString())
                putString("beneBranchName", beneBranchName.toString())

                putString("beneWalletId", beneWalletId.toString())
                putString("beneWalletName", beneWalletName.toString())

                putString("beneAccountNo", beneAccountNo)
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
                R.id.action_nav_review_to_nav_main, bundle
            )
        }

        binding.btnSend.setOnClickListener { paymentFrom() }

        val reasonItem = ReasonItem(
            deviceId = deviceId, dropdownId = 27, param1 = 0, param2 = 0
        )
        paymentViewModel.reason(reasonItem)
        observeReasonResult()

        val sourceOfIncomeItem = SourceOfIncomeItem(
            deviceId = deviceId,
            dropdownId = 307,
            param1 = 0,
            param2 = 0
        )
        paymentViewModel.sourceOfIncome(sourceOfIncomeItem)
        observeSourceOfIncomeResult()

        sendAgain()
        applyPromo()

        observeProfileResult()
        observePaymentResult()
        observeEncryptResult()
        observeRequireDocumentResult()
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
        this.beneMobile = beneMobile
    }

    private fun paymentFrom() {
        if (orderType == 2 || orderType == 4) {
            binding.receiverNameContainer.helperText = validReceiverName()
            binding.reasonContainer.helperText = validReason()
            binding.sourceOfIncomeContainer.helperText = validSourceOfIncome()

            val validReceiverName = binding.receiverNameContainer.helperText == null
            val validReason = binding.reasonContainer.helperText == null
            val validSourceOfIncome = binding.sourceOfIncomeContainer.helperText == null

            if (validReceiverName && validReason && validSourceOfIncome) {
                submitPaymentFrom()
            }
        } else {
            binding.receiverNameContainer.helperText = validReceiverName()
            binding.receiverAccountContainer.helperText = validReceiverAccount()
            binding.reasonContainer.helperText = validReason()
            binding.sourceOfIncomeContainer.helperText = validSourceOfIncome()

            val validReceiverName = binding.receiverNameContainer.helperText == null
            val validReceiverAccount = binding.receiverAccountContainer.helperText == null
            val validReason = binding.reasonContainer.helperText == null
            val validSourceOfIncome = binding.sourceOfIncomeContainer.helperText == null

            if (validReceiverName && validReceiverAccount && validReason && validSourceOfIncome) {
                submitPaymentFrom()
            }
        }
    }

    private fun submitPaymentFrom() {
        if (beneAccountName.equals(null)) {
            beneAccountName = ""
        }
        if (beneAccountNo.equals(null)) {
            beneAccountNo = ""
        }
        if (beneMobile.equals(null)) {
            beneMobile = ""
        }
        if (beneWalletNo.equals(null)) {
            beneWalletNo = ""
        }
        if (latitude.equals(null)) {
            latitude = ""
        }
        if (longitude.equals(null)) {
            longitude = ""
        }
        if (promoCode.equals(null)) {
            promoCode = ""
        }
        if (purposeOfTransferName.equals(null)) {
            purposeOfTransferName = ""
        }
        if (sourceOfFundName.equals(null)) {
            sourceOfFundName = ""
        }

        val profileItem = ProfileItem(
            deviceId = deviceId, personId = personId
        )
        paymentViewModel.profile(profileItem)
    }

    private fun observeProfileResult() {
        paymentViewModel.profileResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (data in result.data!!) {

                        customerPostCode = data!!.postCode.toString()
                        customerFirstName = data.firstName.toString()
                        customerLastName = data.lastName.toString()
                        customerAddress = data.address.toString()
                        customerCity = data.cityName.toString()
                        customerState = data.divisionName.toString()
                        customerEmail = data.email.toString()
                        customerMobile = data.mobile.toString()
                        isMobileOTPValidate = data.isMobileOTPValidate!!

                        if (!isMobileOTPValidate) {
                            if (!transactionOtpVerifyBottomSheet.isAdded) {
                                transactionOtpVerifyBottomSheet.setPhoneNumber(customerMobile!!)
                                transactionOtpVerifyBottomSheet.show(
                                    childFragmentManager, transactionOtpVerifyBottomSheet.tag
                                )
                            }
                        } else if (customerAddress == "" || customerAddress == "null") {
                            if (!addressVerifyBottomSheet.isAdded) {
                                addressVerifyBottomSheet.show(
                                    childFragmentManager, addressVerifyBottomSheet.tag
                                )
                            }
                        } else {

                            val sendAmountValue = binding.sendAmount.text.toString()
                            modifiedSendAmount =
                                sendAmountValue.replace(Regex("[^\\d.]"), "").toDouble()

                            val receiveAmountValue = binding.receiveAmount.text.toString()
                            modifiedBeneAmount =
                                receiveAmountValue.replace(Regex("[^\\d.]"), "").toDouble()

                            val rateValue = binding.exchangeRate.text.toString()
                            modifiedRate = rateValue.replace(Regex("[^\\d.]"), "").toDouble()

                            val commissionValue = binding.transferFee.text.toString()
                            modifiedCommission =
                                commissionValue.replace(Regex("[^\\d.]"), "").toDouble()

                            val totalAmountValue = binding.totalAmount.text.toString()
                            totalAmount = totalAmountValue.replace(Regex("[^\\d.]"), "").toDouble()

                            val paymentItem = PaymentItem(
                                beneAccountName = beneAccountName,
                                beneAccountNo = beneAccountNo,
                                beneAmount = beneAmount,
                                beneBankId = beneBankId,
                                beneBranchId = beneBranchId,
                                beneId = beneId,
                                beneMobile = beneMobile,
                                benePersonId = benePersonId,
                                beneWalletId = beneWalletId,
                                beneWalletNo = beneWalletNo,
                                channelId = channelId,
                                commission = commission,
                                fromCountryId = fromCountryId,
                                fromCurrencyId = fromCurrencyId,
                                latitude = latitude,
                                longitude = longitude,
                                modifiedBeneAmount = modifiedBeneAmount,
                                modifiedCommission = modifiedCommission,
                                modifiedRate = modifiedRate,
                                modifiedSendAmount = modifiedSendAmount,
                                orderType = orderType,
                                payingAgentId = payingAgentId,
                                paymentMode = paymentMode.toString(),
                                personId = personId,
                                promoCode = promoCode,
                                purposeOfTransferId = purposeOfTransferId,
                                rate = rate,
                                sendAmount = sendAmount,
                                sourceOfFundId = sourceOfFundId,
                                toCountryId = toCountryId,
                                toCurrencyId = toCurrencyId,
                                totalAmount = totalAmount,
                                userIPAddress = ipAddress,
                                isOtpRequireForTxn = false,
                                otpRequireReason = "",
                                isOtpValidated = false
                            )
                            paymentViewModel.payment(paymentItem)
                        }
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun observePaymentResult() {
        paymentViewModel.paymentResult.observe(this) { result ->
            try {
                if (result!!.paymentResponseData != null) {
                    transactionCode = result.paymentResponseData!!.transactionCode!!.toString()
                    transactionCodeWithChannel = "$transactionCode*1"
                    if (::transactionCodeWithChannel.isInitialized && transactionCodeWithChannel != "null") {
                        val encryptItem = EncryptItem(
                            key = "bsel2024$#@!", plainText = transactionCodeWithChannel
                        )
                        paymentViewModel.encrypt(encryptItem)
                    }
                    val totalAmountValue = binding.totalAmount.text.toString()
                    val totalAmount = totalAmountValue.replace(Regex("[^\\d.]"), "")
                    val requireDocumentItem = RequireDocumentItem(
                        agentId = 8082,
                        amount = totalAmount.toDouble(),
                        beneficiaryId = benePersonId,
                        customerId = customerId,
                        entryDate = currentDate,
                        purposeOfTransferId = purposeOfTransferId,
                        transactionType = 1
                    )
                    paymentViewModel.requireDocument(requireDocumentItem)
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun observeEncryptResult() {
        paymentViewModel.encryptResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    encryptCode = result.data.toString()
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun observeRequireDocumentResult() {
        paymentViewModel.requireDocumentResult.observe(this) { result ->
            try {
                if (result!!.code == "000") {
                    requireDoc = result.data.toString()
                    val totalAmountValue = binding.totalAmount.text.toString()
                    totalAmount = totalAmountValue.replace(Regex("[^\\d.]"), "").toDouble()
                    if (result.data != null) {
                        if (!requireDocumentBottomSheet.isAdded) {
                            requireDocumentBottomSheet.requireDocument(
                                totalAmount,
                                transactionCode,
                                benePersonId,
                                customerId,
                                currentDate,
                                purposeOfTransferId
                            )
                            requireDocumentBottomSheet.itemSelectedListener = this
                            requireDocumentBottomSheet.show(
                                childFragmentManager, requireDocumentBottomSheet.tag
                            )
                        }
                    } else {
                        if (!customerAddress.equals(null) && isMobileOTPValidate && !requireDoc.equals(
                                null
                            )
                        ) {
                            if (paymentMode == 4) {
                                cardPayment()
                            } else if (paymentMode == 5) {
                                val bundle = Bundle().apply {
                                    putString("sendAmount", totalAmount.toString())
                                    putString("transactionCode", transactionCode)
                                }
                                findNavController().navigate(
                                    R.id.action_nav_review_to_nav_complete_bank_transaction, bundle
                                )
                            }
                        }
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun cardPayment() {
        // Generate unique Id
        val uniqueId = UUID.randomUUID().toString()

        // Create configuration
        configuration = Configuration(
            "8609ffa7b710c6c74645bfb055888b82ce71c08e",
            "637c89215aa96a41ef53468296459072d809c70a",
            Environments.STAGING,
            Endpoints.EMERCHANTPAY,
            Locales.EN
        )

        // Enable Debug mode
        configuration.setDebugMode(true)

        // Create Billing PaymentAddress
        billingAddress = PaymentAddress(
            customerFirstName.toString(),
            customerLastName.toString(),
            customerAddress.toString(),
            customerAddress.toString(),
            customerPostCode.toString(),
            customerCity.toString(),
            customerState.toString(),
            Country.UnitedKingdom
        )

        // Create Transaction types
        transactionTypes = TransactionTypesRequest()
        transactionTypes.addTransaction(WPFTransactionTypes.SALE3D)

        transactionTypes.setMode(RecurringMode.AUTOMATIC)
        transactionTypes.setInterval(RecurringInterval.DAYS)
//            transactionTypes.setFirstDate(FIRST_DATE)
        transactionTypes.setFrequency(RecurringFrequency.DAYLY)
        transactionTypes.setAmountType(RecurringAmountType.MAX)
        transactionTypes.setPaymentType(RecurringPaymentType.INITIAL)
        transactionTypes.setTimeOfDay(7)
        transactionTypes.setPeriod(7)
        transactionTypes.setAmount(500)
        transactionTypes.setMaxAmount(5000)
        transactionTypes.setMaxCount(10)
        transactionTypes.setRegistrationReferenceNumber(7)

        // Init WPF API request
        paymentRequest = PaymentRequest(
            requireContext(),
            transactionCode,
            BigDecimal(totalAmount),
            Currency.GBP,
            customerEmail,
            customerMobile,
            billingAddress,
            "https://emptest.remitngo.com/emerchantpay/emernotificationresponse",
            transactionTypes
        )

        paymentRequest.setReturnSuccessUrl("https://emptest.remitngo.com/emerchantpay/wpfsuccessurl?tcode=" + encryptCode)
        paymentRequest.setReturnFailureUrl("https://emptest.remitngo.com/emerchantpay/wpffailureurl?tcode=" + encryptCode)
        paymentRequest.setReturnCancelUrl("https://emptest.remitngo.com/emerchantpay/wpfcancelurl?tcode=" + encryptCode)

        paymentRequest.setUsage("Test Staging")
        paymentRequest.setDescription("Test payment gateway")
        paymentRequest.setLifetime(60)
        paymentRequest.setRememberCard(true)
        paymentRequest.setPayLater(false)
        paymentRequest.setCrypto(false)
        paymentRequest.setGaming(false)

        paymentRequest.setRecurringType(RecurringType.INITIAL)
        paymentRequest.setRecurringCategory(RecurringCategory.SUBSCRIPTION)

        // Risk params
        riskParams = RiskParams(
            "1002547",
            "1DA53551-5C60-498C-9C18-8456BDBA74A9",
            "987-65-4320",
            "12-34-56-78-9A-BC",
            "123456",
            "emil@example.com",
            "+49301234567",
            "245.253.2.12",
            "10000000000",
            "1234",
            "100000000",
            "Mohammad",
            "Kobirul Islam",
            "US",
            "test",
            "245.25 3.2.12",
            "test",
            "test123456",
            "Bin name",
            "+49301234567"
        )
        paymentRequest.setRiskParams(riskParams)

        threeDsV2Params = ThreeDsV2Params.build {
            purchaseCategory = ThreeDsV2PurchaseCategory.GOODS

            val merchantRiskPreorderDate = SimpleDateFormat("dd-MM-yyyy").calendar.apply {
                time = Date()
                add(Calendar.DATE, 5)
            }.time

            merchantRisk = ThreeDsV2MerchantRiskParams(
                ThreeDsV2MerchantRiskShippingIndicator.DIGITAL_GOODS,
                ThreeDsV2MerchantRiskDeliveryTimeframe.SAME_DAY,
                ThreeDsV2MerchantRiskReorderItemsIndicator.REORDERED,
                ThreeDsV2MerchantRiskPreorderPurchaseIndicator.MERCHANDISE_AVAILABLE,
                merchantRiskPreorderDate,
                true,
                3
            )

            cardHolderAccount = ThreeDsV2CardHolderAccountParams(
                SimpleDateFormat("dd-MM-yyyy").parse("11-02-2021"),
                ThreeDsV2CardHolderAccountUpdateIndicator.UPDATE_30_TO_60_DAYS,
                SimpleDateFormat("dd-MM-yyyy").parse("13-02-2021"),
                ThreeDsV2CardHolderAccountPasswordChangeIndicator.PASSWORD_CHANGE_NO_CHANGE,
                SimpleDateFormat("dd-MM-yyyy").parse("10-01-2021"),
                ThreeDsV2CardHolderAccountShippingAddressUsageIndicator.ADDRESS_USAGE_MORE_THAN_60DAYS,
                SimpleDateFormat("dd-MM-yyyy").parse("10-01-2021"),
                2,
                129,
                1,
                31,
                ThreeDsV2CardHolderAccountSuspiciousActivityIndicator.NO_SUSPICIOUS_OBSERVED,
                ThreeDsV2CardHolderAccountRegistrationIndicator.REGISTRATION_30_TO_60_DAYS,
                SimpleDateFormat("dd-MM-yyyy").parse("03-01-2021")
            )

            recurring = ThreeDsV2RecurringParams()
        }
        paymentRequest.setThreeDsV2Params(threeDsV2Params)

        if (::consumerId.isInitialized && consumerId != "null") {
            paymentRequest.setConsumerId(consumerId)
        }

        genesis = Genesis(requireContext(), configuration, paymentRequest)

        if (!genesis?.isConnected(requireContext())!!) {
            dialogHandler =
                AlertDialogHandler(requireContext(), "Error", ErrorMessages.CONNECTION_ERROR)
            dialogHandler.show()
        }

        if (genesis.isConnected(requireContext())!! && genesis.isValidData!!) {
            //Execute WPF API request
            genesis.push()
            // Get response
            val response = genesis!!.response!!
            if (!response!!.isSuccess!!) {
                error = response!!.error!!
                dialogHandler = AlertDialogHandler(
                    requireContext(),
                    "Failure",
                    "Code: " + error!!.code + "\nMessage: " + error!!.technicalMessage
                )
                dialogHandler.show()
            } else if (response!!.isSuccess!!) {

                consumerId = response.consumerId.toString()

                val saveConsumerItem = SaveConsumerItem(
                    deviceId = deviceId, personId = personId, consumerId = consumerId
                )
                paymentViewModel.saveConsumer(saveConsumerItem)
                observeSaveConsumerResult()

                val encryptItem = EmpItem(
                    status = response.status.toString(),
                    uniqueId = response.uniqueId.toString(),
                    transactionId = response.transaction!!.transactionId.toString(),
                    consumerId = consumerId,
                    timestamp = response.transaction!!.timestamp.toString(),
                    amount = response.transaction!!.amount.toString(),
                    currency = response.transaction!!.currency.toString(),
                    redirectUrl = response.redirectUrl.toString(),
                    channel = "1"
                )
                paymentViewModel.emp(encryptItem)
                observeEmpResult()

                val bundle = Bundle().apply {
                    putString("transactionCode", transactionCode)
                }
                findNavController().navigate(
                    R.id.action_nav_review_to_nav_complete_card_transaction, bundle
                )

            }
        }

        if (!genesis.isValidData!!) {
            error = genesis.error!!
            var message = error.message!!
            var technicalMessage: String
            if (error.technicalMessage != null && !error.technicalMessage!!.isEmpty()) {
                technicalMessage = error.technicalMessage!!
            } else {
                technicalMessage = ""
            }
            dialogHandler =
                AlertDialogHandler(requireContext(), "Invalid", "$technicalMessage $message")
            dialogHandler.show()
        }

    }

    private fun observeSaveConsumerResult() {
        paymentViewModel.saveConsumerResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    Log.i("info", "Save Consumer: " + result.data.toString())
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun observeEmpResult() {
        paymentViewModel.empResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    Log.i("info", "observeEmpResult: " + result.data.toString())
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    override fun onChooseRecipientItemSelected(selectedItem: GetBeneficiaryData) {
        benePersonId = selectedItem.benePersonId!!.toInt()
        beneId = selectedItem.beneficiaryId!!.toInt()
        beneAccountName = selectedItem.beneName.toString()
        beneMobile = selectedItem.mobile.toString()
        binding.receiverName.text = beneAccountName
    }

    override fun onBankAndWalletItemSelected(selectedItem: GetBankData) {
        if (selectedItem.bankId != null) {
            beneBankId = selectedItem.bankId
        }
        if (selectedItem.branchId != null) {
            beneBranchId = selectedItem.branchId
        }
        if (selectedItem.walletId != null) {
            beneBranchId = selectedItem.walletId
        }
        if (selectedItem.accountName != null) {
            beneAccountName = selectedItem.accountName
            binding.receiverName.text = beneAccountName
        }
        if (selectedItem.bankName != null) {
            beneBankName = selectedItem.bankName
            binding.receiverBankName.text = beneBankName
        }
        if (selectedItem.walletName != null) {
            beneWalletName = selectedItem.walletName
            binding.receiverBankName.text = beneWalletName
        }
        if (selectedItem.accountNo != null) {
            beneAccountNo = selectedItem.accountNo
            binding.receiverAccount.text = beneAccountNo
        }
    }

    override fun onPurposeOfTransferItemSelected(selectedItem: ReasonData) {
        purposeOfTransferId = selectedItem.id.toString().toInt()
        purposeOfTransferName = selectedItem.name.toString()
        binding.reason.setText(purposeOfTransferName)
    }

    override fun onSourceOfFundItemSelected(selectedItem: SourceOfIncomeData) {
        sourceOfFundId = selectedItem.id.toString().toInt()
        sourceOfFundName = selectedItem.name.toString()
        binding.sourceOfIncome.setText(sourceOfFundName)
    }

    private fun observeReasonResult() {
        paymentViewModel.reasonResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (data in result.data!!) {
                        if (purposeOfTransferId == data?.id.toString().toInt()) {
                            binding.reason.setText(data?.name.toString())
                        }
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun observeSourceOfIncomeResult() {
        paymentViewModel.sourceOfIncomeResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (data in result.data!!) {
                        if (sourceOfFundId.toString() == data?.id.toString()) {
                            binding.sourceOfIncome.setText(data?.name.toString())
                        }
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun sendAgain() {
        try {
            transactionCode = arguments?.getString("transactionCode").toString()
            val transactionDetailsItem = TransactionDetailsItem(
                deviceId = deviceId, transactionCode = transactionCode
            )
            paymentViewModel.paymentTransaction(transactionDetailsItem)
            observeTransactionDetailsResult()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
    }

    private fun observeTransactionDetailsResult() {
        paymentViewModel.paymentTransactionResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (paymentTransactionData in result.data!!) {

                        paymentMode = paymentTransactionData?.paymentMode.toString().toInt()
                        orderType = paymentTransactionData?.orderType.toString().toInt()

                        sendAmount = paymentTransactionData?.sendAmount.toString().toDouble()
                        beneAmount = paymentTransactionData?.beneAmount.toString().toDouble()

                        rate = paymentTransactionData?.rate.toString().toDouble()
                        commission = paymentTransactionData?.transferFees.toString().toDouble()

                        beneBankId = paymentTransactionData?.beneBankId.toString().toInt()
                        beneBranchId = paymentTransactionData?.beneBranchId.toString().toInt()
                        try {
                            beneAccountNo = paymentTransactionData?.beneAccountNo.toString()
                            binding.receiverAccount.text = beneAccountNo
                        } catch (e: NullPointerException) {
                            e.localizedMessage
                        }
                        payingAgentId = paymentTransactionData?.payingAgentId.toString().toInt()

                        beneId = paymentTransactionData?.beneId.toString().toInt()
                        try {
                            beneAccountName = paymentTransactionData?.beneName.toString()
                            binding.receiverName.text = beneAccountName
                        } catch (e: NullPointerException) {
                            e.localizedMessage
                        }

                        purposeOfTransferId =
                            paymentTransactionData?.purposeOfTransferId.toString().toInt()
                        sourceOfFundId = paymentTransactionData?.sourceOfFundId.toString().toInt()

                        if (sendAmount.toString() != "null") {
                            gbpValue = sendAmount
                            binding.sendAmount.text = "GBP $sendAmount"
                        }
                        if (commission.toString() != "null") {
                            binding.transferFee.text = "GBP $commission"
                        }

                        totalAmount = sendAmount + commission
                        binding.totalAmount.text = "GBP $totalAmount"

                        if (rate.toString() != "null") {
                            binding.exchangeRate.text = "GBP $rate"
                        }
                        if (beneAmount.toString() != "null") {
                            binding.receiveAmount.text = "BDT $beneAmount"
                        }
                        if (purposeOfTransferName != "null") {
                            binding.reason.setText("$purposeOfTransferName")
                        }
                        if (sourceOfFundName != "null") {
                            binding.sourceOfIncome.setText("$sourceOfFundName")
                        }

                        val calculateRateItem = CalculateRateItem(
                            amount = sendAmount.toString(),
                            bankId = beneBankId,
                            calculationType,
                            deviceId = deviceId,
                            fromCountry = 4,
                            mobileOrWebPlatform = 0,
                            orderType = orderType,
                            payingAgentId = payingAgentId,
                            paymentMode = 0,
                            toCountry = 1
                        )
                        paymentViewModel.rateCalculate(calculateRateItem)
                        observeCalculateRateResult()
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun observeCalculateRateResult() {
        paymentViewModel.rateCalculateResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (data in result.data!!) {

                        commission = data!!.commission.toString().toDouble()
                        binding.transferFee.text = commission.toString()

                        rate = data.rate.toString().toDouble()
                        binding.exchangeRate.text = rate.toString()

                        updateValuesGBP()
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun updateValuesGBP() {
        val bdtValue = gbpValue * rate
        val formattedBDT = decimalFormat.format(bdtValue)
        binding.receiveAmount.text = "BDT $formattedBDT"
    }

    private fun applyPromo() {
        binding.btnAddPromoCode.visibility = View.VISIBLE
        binding.promoCodeContainer.visibility = View.GONE
        binding.btnApplyPromoCode.visibility = View.GONE
        binding.promoMessage.visibility = View.GONE
        binding.promoLayout.visibility = View.GONE

        binding.previousSendAmount.visibility = View.GONE
        binding.previousTransferFee.visibility = View.GONE
        binding.previousTotalAmount.visibility = View.GONE
        binding.previousRate.visibility = View.GONE
        binding.previousReceiveAmount.visibility = View.GONE

        binding.btnAddPromoCode.setOnClickListener {
            binding.btnAddPromoCode.visibility = View.GONE
            binding.promoCodeContainer.visibility = View.VISIBLE
            binding.btnApplyPromoCode.visibility = View.VISIBLE
            binding.promoMessage.visibility = View.GONE
            binding.promoLayout.visibility = View.GONE
        }

        binding.btnApplyPromoCode.setOnClickListener { promoCodeFrom() }
    }

    private fun promoCodeFrom() {
        binding.promoCodeContainer.helperText = validPromoCode()

        val validPromoCode = binding.promoCodeContainer.helperText == null

        if (validPromoCode) {
            submitPromoCodeFrom()
        }
    }

    private fun submitPromoCodeFrom() {
        promoCode = binding.promoCode.text.toString()

        val sendAmountValue = binding.sendAmount.text.toString()
        modifiedSendAmount = sendAmountValue.replace(Regex("[^\\d.]"), "").toDouble()

        val receiveAmountValue = binding.receiveAmount.text.toString()
        modifiedBeneAmount = receiveAmountValue.replace(Regex("[^\\d.]"), "").toDouble()

        val commissionValue = binding.transferFee.text.toString()
        modifiedCommission = commissionValue.replace(Regex("[^\\d.]"), "").toDouble()

        val rateValue = binding.exchangeRate.text.toString()
        modifiedRate = rateValue.replace(Regex("[^\\d.]"), "").toDouble()

        val promoItem = PromoItem(
            beneAmount = modifiedBeneAmount,
            commision = modifiedCommission,
            personId = personId,
            promoCode = promoCode,
            rate = modifiedRate,
            sendAmount = modifiedSendAmount
        )
        paymentViewModel.promo(promoItem)
        observePromoResult()
    }

    private fun observePromoResult() {
        paymentViewModel.promoResult.observe(this) { result ->
            try {
                if (result!!.message == "Successful") {
                    binding.btnAddPromoCode.visibility = View.GONE
                    binding.promoCodeContainer.visibility = View.GONE
                    binding.btnApplyPromoCode.visibility = View.GONE
                    binding.promoMessage.visibility = View.VISIBLE
                    binding.promoLayout.visibility = View.VISIBLE
                    if (result.promoResponseData?.get(0)!!.message == "Success") {
                        var applyPromoAmtFor = result.promoResponseData[0]!!.applyPromoAmtForId
                        modifiedSendAmount =
                            result.promoResponseData[0]!!.modifiedSentAmount.toString()
                                .toDouble()
                        gbpValue = modifiedSendAmount
                        binding.sendAmount.text = "GBP $modifiedSendAmount"

                        modifiedCommission =
                            result.promoResponseData[0]!!.modifiedCommision.toString()
                                .toDouble()
                        binding.transferFee.text = "GBP $modifiedCommission"

                        modifiedRate =
                            result.promoResponseData[0]!!.modifiedRate!!.toString().toDouble()
                        binding.exchangeRate.text = "BDT $modifiedRate"

                        modifiedBeneAmount =
                            result.promoResponseData[0]!!.modifiedBeneAmount!!.toString()
                                .toDouble()
                        binding.receiveAmount.text = "BDT $modifiedBeneAmount"

                        totalAmount = modifiedSendAmount + modifiedCommission
                        binding.totalAmount.text = "GBP $totalAmount"

                        when (applyPromoAmtFor) {
                            1 -> {
                                sendAmount =
                                    result.promoResponseData[0]!!.sentAmount!!.toString()
                                        .toDouble()
                                binding.previousSendAmount.visibility = View.VISIBLE
                                binding.previousSendAmount.hint = "GBP $sendAmount"

                                commission =
                                    result.promoResponseData[0]!!.commision!!.toString()
                                        .toDouble()
                                binding.previousTransferFee.visibility = View.GONE
                                binding.previousTransferFee.hint = "GBP $commission"

                                rate =
                                    result.promoResponseData[0]!!.rate!!.toString().toDouble()
                                binding.previousRate.visibility = View.GONE
                                binding.previousRate.hint = "BDT $rate"

                                beneAmount =
                                    result.promoResponseData[0]!!.beneAmount!!.toString()
                                        .toDouble()
                                binding.previousReceiveAmount.visibility = View.GONE
                                binding.previousReceiveAmount.hint = "BDT $beneAmount"

                                var previousTotalAmount = sendAmount + commission
                                binding.previousTotalAmount.visibility = View.GONE
                                binding.previousTotalAmount.hint = "GBP $previousTotalAmount"
                            }
                            2 -> {
                                sendAmount =
                                    result.promoResponseData[0]!!.sentAmount!!.toString()
                                        .toDouble()
                                binding.previousSendAmount.visibility = View.GONE
                                binding.previousSendAmount.hint = "GBP $sendAmount"

                                commission =
                                    result.promoResponseData[0]!!.commision!!.toString()
                                        .toDouble()
                                binding.previousTransferFee.visibility = View.GONE
                                binding.previousTransferFee.hint = "GBP $commission"

                                rate =
                                    result.promoResponseData[0]!!.rate!!.toString().toDouble()
                                binding.previousRate.visibility = View.GONE
                                binding.previousRate.hint = "BDT $rate"

                                beneAmount =
                                    result.promoResponseData[0]!!.beneAmount!!.toString()
                                        .toDouble()
                                binding.previousReceiveAmount.visibility = View.VISIBLE
                                binding.previousReceiveAmount.hint = "BDT $beneAmount"

                                var previousTotalAmount = sendAmount + commission
                                binding.previousTotalAmount.visibility = View.GONE
                                binding.previousTotalAmount.hint = "GBP $previousTotalAmount"
                            }
                            3 -> {
                                sendAmount =
                                    result.promoResponseData[0]!!.sentAmount!!.toString()
                                        .toDouble()
                                binding.previousSendAmount.visibility = View.GONE
                                binding.previousSendAmount.hint = "GBP $sendAmount"

                                commission =
                                    result.promoResponseData[0]!!.commision!!.toString()
                                        .toDouble()
                                binding.previousTransferFee.visibility = View.VISIBLE
                                binding.previousTransferFee.hint = "GBP $commission"

                                rate =
                                    result.promoResponseData[0]!!.rate!!.toString().toDouble()
                                binding.previousRate.visibility = View.GONE
                                binding.previousRate.hint = "BDT $rate"

                                beneAmount =
                                    result.promoResponseData[0]!!.beneAmount!!.toString()
                                        .toDouble()
                                binding.previousReceiveAmount.visibility = View.GONE
                                binding.previousReceiveAmount.hint = "BDT $beneAmount"

                                var previousTotalAmount = sendAmount + commission
                                binding.previousTotalAmount.visibility = View.VISIBLE
                                binding.previousTotalAmount.hint = "GBP $previousTotalAmount"
                            }
                            4 -> {
                                sendAmount =
                                    result.promoResponseData[0]!!.sentAmount!!.toString()
                                        .toDouble()
                                binding.previousSendAmount.visibility = View.GONE
                                binding.previousSendAmount.hint = "GBP $sendAmount"

                                commission =
                                    result.promoResponseData[0]!!.commision!!.toString()
                                        .toDouble()
                                binding.previousTransferFee.visibility = View.GONE
                                binding.previousTransferFee.hint = "GBP $commission"

                                rate =
                                    result.promoResponseData[0]!!.rate!!.toString().toDouble()
                                binding.previousRate.visibility = View.VISIBLE
                                binding.previousRate.hint = "BDT $rate"

                                beneAmount =
                                    result.promoResponseData[0]!!.beneAmount!!.toString()
                                        .toDouble()
                                binding.previousReceiveAmount.visibility = View.VISIBLE
                                binding.previousReceiveAmount.hint = "BDT $beneAmount"

                                var previousTotalAmount = sendAmount + commission
                                binding.previousTotalAmount.visibility = View.GONE
                                binding.previousTotalAmount.hint = "GBP $previousTotalAmount"
                            }
                            5 -> {
                                sendAmount =
                                    result.promoResponseData[0]!!.sentAmount!!.toString()
                                        .toDouble()
                                binding.previousSendAmount.visibility = View.GONE
                                binding.previousSendAmount.hint = "GBP $sendAmount"

                                commission =
                                    result.promoResponseData[0]!!.commision!!.toString()
                                        .toDouble()
                                binding.previousTransferFee.visibility = View.VISIBLE
                                binding.previousTransferFee.hint = "GBP $commission"

                                rate =
                                    result.promoResponseData[0]!!.rate!!.toString().toDouble()
                                binding.previousRate.visibility = View.VISIBLE
                                binding.previousRate.hint = "BDT $rate"

                                beneAmount =
                                    result.promoResponseData[0]!!.beneAmount!!.toString()
                                        .toDouble()
                                binding.previousReceiveAmount.visibility = View.VISIBLE
                                binding.previousReceiveAmount.hint = "BDT $beneAmount"

                                var previousTotalAmount = sendAmount + commission
                                binding.previousTotalAmount.visibility = View.VISIBLE
                                binding.previousTotalAmount.hint = "GBP $previousTotalAmount"
                            }
                        }


                        val promoMessage = result.promoResponseData[0]!!.promoMsg!!.toString()
                        binding.appliedPromoCode.text = "Promo applied!"
                        binding.promoMessage.text = "$promoMessage"

                        updateValuesGBP()
                    }
                } else {
                    binding.promoCodeContainer.helperText =
                        result.message!!.toString()
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate(context: Context): String {
        currentDate = LocalDate.now().toString()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDate.format(formatter)
        return formattedDate
    }

    private fun getDeviceId(context: Context): String {
        val deviceId: String

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } else {
            @Suppress("DEPRECATION") deviceId = Settings.Secure.getString(
                context.contentResolver, Settings.Secure.ANDROID_ID
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

    override fun onRequireDocumentSelected(totalAmount: Double, transactionCode: String) {
        if (paymentMode == 4) {
            cardPayment()
        } else if (paymentMode == 5) {
            val bundle = Bundle().apply {
                putString("sendAmount", totalAmount.toString())
                putString("transactionCode", transactionCode)
            }
            findNavController().navigate(
                R.id.action_nav_review_to_nav_complete_bank_transaction, bundle
            )
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.nav_main)
        }
    }

    //Form validation
    private fun reasonFocusListener() {
        binding.reason.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.reasonContainer.helperText = validReason()
            }
        }
    }

    private fun validReason(): String? {
        val reason = binding.reason.text.toString()
        if (reason.isEmpty()) {
            return "select purpose of transfer"
        }
        return null
    }

    private fun sourceOfIncomeFocusListener() {
        binding.sourceOfIncome.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.sourceOfIncomeContainer.helperText = validSourceOfIncome()
            }
        }
    }

    private fun validSourceOfIncome(): String? {
        val sourceOfIncome = binding.sourceOfIncome.text.toString()
        if (sourceOfIncome.isEmpty()) {
            return "select source of fund"
        }
        return null
    }

    private fun receiverNameFocusListener() {
        binding.receiverName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.receiverNameContainer.helperText = validReceiverName()
            }
        }
    }

    private fun validReceiverName(): String? {
        val receiverName = binding.receiverName.text.toString()
        if (receiverName.isEmpty()) {
            return "Choose receiver"
        }
        return null
    }

    private fun receiverAccountFocusListener() {
        binding.receiverAccount.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.receiverAccountContainer.helperText = validReceiverAccount()
            }
        }
    }

    private fun validReceiverAccount(): String? {
        val receiverAccount = binding.receiverAccount.text.toString()
        if (receiverAccount.isEmpty()) {
            return "Choose receiver account"
        }
        return null
    }

    private fun promoCodeFocusListener() {
        binding.promoCode.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.promoCodeContainer.helperText = validPromoCode()
            }
        }
    }

    private fun validPromoCode(): String? {
        val promoCode = binding.promoCode.text.toString()
        if (promoCode.isEmpty()) {
            return "Enter your promo code"
        }
        return null
    }

}