package com.bsel.remitngo.presentation.ui.payment

import android.content.Context
import android.net.wifi.WifiManager
import android.os.*
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.ReasonNameAdapter
import com.bsel.remitngo.adapter.SourceOfIncomeAdapter
import com.bsel.remitngo.bottomSheet.ReasonBottomSheet
import com.bsel.remitngo.bottomSheet.SourceOfIncomeBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnBeneficiarySelectedListener
import com.bsel.remitngo.data.interfaceses.OnPersonalInfoItemSelectedListener
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerItem
import com.bsel.remitngo.data.model.emp.EmpItem
import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeData
import com.bsel.remitngo.data.model.profile.nationality.NationalityData
import com.bsel.remitngo.data.model.profile.occupation.OccupationData
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeData
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeData
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.data.model.promoCode.PromoItem
import com.bsel.remitngo.data.model.reason.ReasonData
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.data.model.relation.RelationData
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
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PaymentFragment : Fragment(), OnBeneficiarySelectedListener,
    OnPersonalInfoItemSelectedListener {
    @Inject
    lateinit var paymentViewModelFactory: PaymentViewModelFactory
    private lateinit var paymentViewModel: PaymentViewModel

    private lateinit var binding: FragmentPaymentBinding

    private lateinit var preferenceManager: PreferenceManager

    private val reasonBottomSheet: ReasonBottomSheet by lazy { ReasonBottomSheet() }
    private val sourceOfIncomeBottomSheet: SourceOfIncomeBottomSheet by lazy { SourceOfIncomeBottomSheet() }

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
    private lateinit var commission: String
    private lateinit var exchangeRate: String
    private lateinit var receiveAmount: String
    private var totalAmount: Double = 0.0

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

    private var rate = 0.0
    private var gbpValue = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(view)

        (requireActivity().application as Injector).createPaymentSubComponent().inject(this)

        paymentViewModel =
            ViewModelProvider(this, paymentViewModelFactory)[PaymentViewModel::class.java]

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

        if (sendAmount != "null") {
            binding.sendAmount.text = "GBP $sendAmount"
        }
        if (commission != "null") {
            binding.transferFee.text = "GBP $commission"
        }
        if (sendAmount != "null" || commission != "null") {
            try {
                val sa = sendAmount.toDouble()
                val cm = commission.toDouble()
                totalAmount = sa + cm
                binding.totalAmount.text = "GBP $totalAmount"
            } catch (e: NumberFormatException) {
                e.message
            }
        }
        if (exchangeRate != "null") {
            binding.exchangeRate.text = "GBP $exchangeRate"
        }
        if (receiveAmount != "null") {
            binding.receiveAmount.text = "BDT $receiveAmount"
        }
        if (beneficiaryName != "null") {
            binding.receiverName.setText(beneficiaryName)
        }
        if (bankName != "null") {
            binding.receiverAccount.setText(bankName)
        }
        if (reasonName != "null") {
            binding.reason.setText("$reasonName")
        }
        if (sourceOfIncomeName != "null") {
            binding.sourceOfIncome.setText("$sourceOfIncomeName")
        }

        val consumerItem = ConsumerItem(
            deviceId = deviceId,
            params1 = personId.toInt(),
            params2 = ""
        )
        paymentViewModel.consumer(consumerItem)
        paymentViewModel.consumerResult.observe(this) { result ->
            if (result!!.data != null) {
                consumerId = result.data.toString()
            }
        }

        binding.reason.setOnClickListener {
            reasonBottomSheet.itemSelectedListener = this
            reasonBottomSheet.show(childFragmentManager, reasonBottomSheet.tag)
        }

        binding.sourceOfIncome.setOnClickListener {
            sourceOfIncomeBottomSheet.itemSelectedListener = this
            sourceOfIncomeBottomSheet.show(childFragmentManager, sourceOfIncomeBottomSheet.tag)
        }

        binding.chooseReceiver.setOnClickListener {
            val sendAmountValue = binding.sendAmount.text.toString()
            val sendAmount = sendAmountValue.replace(Regex("[^\\d.]"), "")

            val receiveAmountValue = binding.receiveAmount.text.toString()
            val receiveAmount = receiveAmountValue.replace(Regex("[^\\d.]"), "")
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
                R.id.action_nav_review_to_nav_choose_beneficiary,
                bundle
            )
        }

        binding.transferHistoryModify.setOnClickListener {
            val sendAmountValue = binding.sendAmount.text.toString()
            val sendAmount = sendAmountValue.replace(Regex("[^\\d.]"), "")

            val receiveAmountValue = binding.receiveAmount.text.toString()
            val receiveAmount = receiveAmountValue.replace(Regex("[^\\d.]"), "")

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
                R.id.action_nav_review_to_nav_main,
                bundle
            )
        }

        binding.btnSend.setOnClickListener {
            val sendAmountValue = binding.sendAmount.text.toString()
            val sendAmount = sendAmountValue.replace(Regex("[^\\d.]"), "")

            val receiveAmountValue = binding.receiveAmount.text.toString()
            val receiveAmount = receiveAmountValue.replace(Regex("[^\\d.]"), "")

            val totalAmountValue = binding.totalAmount.text.toString()
            val totalAmount = totalAmountValue.replace(Regex("[^\\d.]"), "")

            if (paymentType == "null" || reasonId == "null" || sourceOfIncomeId == "null" || sendAmount == "null") {
                val paymentItem = PaymentItem(
                    deviceId = deviceId,
                    userIPAddress = ipAddress,
                    personID = personId,
                    customerName = "$firstName $lastName",
                    customerEmail = customerEmail,
                    customerMobile = customerMobile,
                    customerdateOfBirth = customerDateOfBirth,
                    fromCountryID = "4",
                    fromCurrencyID = "96",
                    fromCurrencyCode = "GBP",
                    benPersonID = beneficiaryId,
                    beneficiaryName = beneficiaryName,
                    beneficaryEmail = "",
                    beneficarymobile = "",
                    beneficaryAddress = "",
                    bankId = bankId,
                    bankName = bankName,
                    accountNo = "",
                    benBranchId = branchId,
                    collectionBankID = "",
                    collectionBankName = "",
                    sendAmount = "0.0",
                    receivableAmount = receiveAmount,
                    rate = exchangeRate,
                    commission = commission,
                    total = totalAmount,
                    toCountryID = "1",
                    toCurrencyID = "6",
                    toCurrencyCode = "BDT",
                    orderTypeID = orderType,
                    paymentMode = "0",
                    purposeOfTransferId = "0",
                    sourceOfFundId = "0",
                    isMobileTransfer = true,
                    isiOS = false,
                    latitude = "",
                    longitude = ""
                )
                paymentViewModel.payment(paymentItem)
            } else {
                val paymentItem = PaymentItem(
                    deviceId = deviceId,
                    userIPAddress = ipAddress,
                    personID = personId,
                    customerName = "$firstName $lastName",
                    customerEmail = customerEmail,
                    customerMobile = customerMobile,
                    customerdateOfBirth = customerDateOfBirth,
                    fromCountryID = "4",
                    fromCurrencyID = "96",
                    fromCurrencyCode = "GBP",
                    benPersonID = beneficiaryId,
                    beneficiaryName = beneficiaryName,
                    beneficaryEmail = "",
                    beneficarymobile = "",
                    beneficaryAddress = "",
                    bankId = bankId,
                    bankName = bankName,
                    accountNo = "",
                    benBranchId = branchId,
                    collectionBankID = "",
                    collectionBankName = "",
                    sendAmount = sendAmount,
                    receivableAmount = receiveAmount,
                    rate = exchangeRate,
                    commission = commission,
                    total = totalAmount,
                    toCountryID = "1",
                    toCurrencyID = "6",
                    toCurrencyCode = "BDT",
                    orderTypeID = orderType,
                    paymentMode = paymentType,
                    purposeOfTransferId = reasonId,
                    sourceOfFundId = sourceOfIncomeId,
                    isMobileTransfer = true,
                    isiOS = false,
                    latitude = "",
                    longitude = ""
                )
                paymentViewModel.payment(paymentItem)
            }
        }
        observePaymentResult()
        observeEncryptResult()

        val reasonItem = ReasonItem(
            deviceId = deviceId,
            dropdownId = 27,
            param1 = 0,
            param2 = 0
        )
        paymentViewModel.reason(reasonItem)
        observeReasonResult()

        val sourceOfIncomeItem = SourceOfIncomeItem(
            deviceId = deviceId
        )
        paymentViewModel.sourceOfIncome(sourceOfIncomeItem)
        observeSourceOfIncomeResult()

        sendAgain()
        applyPromo()
    }

    private fun observePaymentResult() {
        paymentViewModel.paymentResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    transactionCode = result.data.toString()
                    transactionCodeWithChannel = "$transactionCode*1"
                    if (::transactionCodeWithChannel.isInitialized && transactionCodeWithChannel != "null") {
                        val encryptItem = EncryptItem(
                            key = "bsel2024$#@!",
                            plainText = transactionCodeWithChannel
                        )
                        paymentViewModel.encrypt(encryptItem)
                    }
                }
            } catch (e: NullPointerException) {
                e.message
            }
        }
    }

    private fun observeEncryptResult() {
        paymentViewModel.encryptResult.observe(this) { result ->
            if (result!!.data != null) {
                encryptCode = result.data.toString()
                if (paymentType == "4") {
                    cardPayment()
                } else if (paymentType == "3") {
                    findNavController().navigate(R.id.action_nav_review_to_nav_complete_bank_transaction)
                }
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
            "Mohammad",
            "Kobirul Islam",
            "Dhaka",
            "Dhaka",
            "1000",
            "Dhaka",
            "Dhaka",
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
            BigDecimal(sendAmount),
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
                    deviceId = deviceId,
                    params1 = personId.toInt(),
                    params2 = consumerId
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

//                val bundle = Bundle().apply {
//                    putString("transactionCode", transactionCode)
//                }
//                findNavController().navigate(
//                    R.id.action_nav_review_to_nav_complete_card_transaction,
//                    bundle
//                )

//                Handler(Looper.getMainLooper()).postDelayed({
//
//                    val timerDuration = 60 * 1000
//
//                    val dialog = Dialog(requireContext())
//                    dialog.setContentView(R.layout.fragment_payment_status)
//                    dialog.setCancelable(false)
//                    dialog.window?.setLayout(
//                        WindowManager.LayoutParams.MATCH_PARENT,
//                        WindowManager.LayoutParams.MATCH_PARENT
//                    )
//
//                    val receiveName = dialog.findViewById<TextView>(R.id.recipient_name)
//                    val receiveAmount = dialog.findViewById<TextView>(R.id.receive_amount)
//                    val transactionId = dialog.findViewById<TextView>(R.id.transactionCode)
//                    val timer = dialog.findViewById<TextView>(R.id.timerTxt)
//
//                    receiveName.text = "Your transfer to $recipientName is processing !"
//                    receiveAmount.text = "BDT $receive_amount"
//                    transactionId.text = "Your Transfer ID $transactionCode"
//                    dialog.show()
//
//                    object : CountDownTimer(timerDuration.toLong(), 1000) {
//                        override fun onTick(millisUntilFinished: Long) {
//                            val secondsRemaining = millisUntilFinished / 1000
//                            val minutes = secondsRemaining / 60
//                            val seconds = secondsRemaining % 60
//                            timer.text = String.format("%02d:%02d", minutes, seconds)
//                        }
//
//                        override fun onFinish() {
//                            dialog.dismiss()
//                        }
//                    }.start()
//
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        dialog.dismiss()
//                    }, 30000)
//                }, 30000)

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
            if (result!!.data != null) {
                Log.i("info", "Save Consumer: " + result.data.toString())
            }
        }
    }

    private fun observeEmpResult() {
        paymentViewModel.empResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info", "save emp data: " + result.data.toString())
            }
        }
    }

    override fun onRelationItemSelected(selectedItem: RelationData) {

    }

    override fun onReasonItemSelected(selectedItem: ReasonData) {
        reasonId = selectedItem.id.toString()
        reasonName = selectedItem.name.toString()
        binding.reason.setText(reasonName)
    }

    override fun onOccupationTypeItemSelected(selectedItem: OccupationTypeData) {
    }

    override fun onOccupationItemSelected(selectedItem: OccupationData) {
    }

    override fun onSourceOfIncomeItemSelected(selectedItem: SourceOfIncomeData) {
        sourceOfIncomeId = selectedItem.id.toString()
        sourceOfIncomeName = selectedItem.name.toString()
        binding.sourceOfIncome.setText(sourceOfIncomeName)
    }

    override fun onAnnualIncomeItemSelected(selectedItem: AnnualIncomeData) {
    }

    override fun onNationalityItemSelected(selectedItem: NationalityData) {
    }

    private fun observeReasonResult() {
        paymentViewModel.reasonResult.observe(this) { result ->
            if (result!!.data != null) {
                for (data in result.data!!) {
                    if (reasonId == data?.id.toString()) {
                        binding.reason.setText(data?.name.toString())
                    }
                }
            }
        }
    }

    private fun observeSourceOfIncomeResult() {
        paymentViewModel.sourceOfIncomeResult.observe(this) { result ->
            if (result!!.data != null) {
                for (data in result.data!!) {
                    if (sourceOfIncomeId == data?.id.toString()) {
                        binding.sourceOfIncome.setText(data?.name.toString())
                    }
                }
            }
        }
    }

    private fun sendAgain() {
        transactionCode = arguments?.getString("transactionCode").toString()
        if (::transactionCode.isInitialized && transactionCode != "null") {
            val transactionDetailsItem = TransactionDetailsItem(
                deviceId = deviceId,
                params1 = personId.toInt(),
                params2 = transactionCode
            )
            paymentViewModel.paymentTransaction(transactionDetailsItem)
            observeTransactionDetailsResult()
        }
    }

    private fun observeTransactionDetailsResult() {
        paymentViewModel.paymentTransactionResult.observe(this) { result ->
            if (result!!.data != null) {
                for (paymentTransactionData in result.data!!) {

                    paymentType = paymentTransactionData?.paymentType.toString()
                    orderType = paymentTransactionData?.orderTypeId.toString()

                    sendAmount = paymentTransactionData?.sendAmount.toString()
                    receiveAmount = paymentTransactionData?.benAmount.toString()

                    exchangeRate = paymentTransactionData?.rate.toString()
                    commission = paymentTransactionData?.transferFees.toString()

                    bankId = paymentTransactionData?.beneBankId.toString()
                    branchId = paymentTransactionData?.beneBranchId.toString()
                    bankName = paymentTransactionData?.bankName.toString()
                    payingAgentId = paymentTransactionData?.payingAgentId.toString()

                    beneficiaryId = paymentTransactionData?.beneId.toString()
                    beneficiaryName = paymentTransactionData?.benName.toString()

                    reasonId = paymentTransactionData?.purposeOfTransferId.toString()

                    sourceOfIncomeId = paymentTransactionData?.sourceOfFundId.toString()

                    if (sendAmount != "null") {
                        gbpValue = sendAmount.toDouble()
                        binding.sendAmount.text = "GBP $sendAmount"
                    }
                    if (commission != "null") {
                        binding.transferFee.text = "GBP $commission"
                    }
                    if (sendAmount != "null" || commission != "null") {
                        try {
                            val sa = sendAmount.toDouble()
                            val cm = commission.toDouble()
                            totalAmount = sa + cm
                            binding.totalAmount.text = "GBP $totalAmount"
                        } catch (e: NumberFormatException) {
                            e.message
                        }
                    }
                    if (exchangeRate != "null") {
                        binding.exchangeRate.text = "GBP $exchangeRate"
                    }
                    if (receiveAmount != "null") {
                        binding.receiveAmount.text = "BDT $receiveAmount"
                    }
                    if (beneficiaryName != "null") {
                        binding.receiverName.setText(beneficiaryName)
                    }
                    if (bankName != "null") {
                        binding.receiverAccount.setText(bankName)
                    }
                    if (reasonName != "null") {
                        binding.reason.setText("$reasonName")
                    }
                    if (sourceOfIncomeName != "null") {
                        binding.sourceOfIncome.setText("$sourceOfIncomeName")
                    }

                    val calculateRateItem = CalculateRateItem(
                        deviceId = deviceId,
                        personId = personId.toInt(),
                        bankId = bankId.toInt(),
                        payingAgentId = payingAgentId.toInt(),
                        orderType = orderType.toInt(),
                        paymentMode = 0,
                        fromCountry = 4,
                        toCountry = 1,
                        mobileOrWebPlatform = 0,
                        amount = sendAmount
                    )
                    paymentViewModel.rateCalculate(calculateRateItem)
                    observeCalculateRateResult()
                }
            }
        }
    }

    private fun observeCalculateRateResult() {
        paymentViewModel.rateCalculateResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (data in result.data!!) {
                        commission = data!!.commission.toString()
                        rate = data!!.rate!!.toDouble()
                        exchangeRate = data!!.rate!!.toString()
                        binding.exchangeRate.text = exchangeRate
                        updateValuesGBP()
                    }
                }
            } catch (e: NullPointerException) {
                e.message
            }
        }
    }

    private fun applyPromo() {
        binding.btnAddPromoCode.visibility = View.VISIBLE
        binding.promoCodeLayout.visibility = View.GONE
        binding.btnAddPromoCode.setOnClickListener {
            binding.btnAddPromoCode.visibility = View.GONE
            binding.promoCodeLayout.visibility = View.VISIBLE
        }
        binding.applyPromoCode.setOnClickListener {
            val promoCode = binding.promoCode.text.toString()

            val sendAmountValue = binding.sendAmount.text.toString()
            val sendAmount = sendAmountValue.replace(Regex("[^\\d.]"), "")

            val receiveAmountValue = binding.receiveAmount.text.toString()
            val receiveAmount = receiveAmountValue.replace(Regex("[^\\d.]"), "")

            val commissionValue = binding.transferFee.text.toString()
            val commission = commissionValue.replace(Regex("[^\\d.]"), "")

            val rateValue = binding.exchangeRate.text.toString()
            val exchangeRate = rateValue.replace(Regex("[^\\d.]"), "")

            val promoItem = PromoItem(
                beneAmount = receiveAmount.toDouble(),
                commision = commission.toDouble(),
                personId = 0,
                promoCode = promoCode,
                rate = exchangeRate.toDouble(),
                sendAmount = sendAmount.toDouble()
            )
            paymentViewModel.promo(promoItem)
            observePromoResult()
        }
    }

    private fun observePromoResult() {
        paymentViewModel.promoResult.observe(this) { result ->
            try {
                binding.btnAddPromoCode.visibility = View.VISIBLE
                binding.promoCodeLayout.visibility = View.GONE
                binding.promoCode.setText("")
                if (result!!.promoResponseData!!.promoData != null) {
                    sendAmount =
                        result!!.promoResponseData!!.promoData!!.modifiedSendAmount.toString()
                    commission =
                        result!!.promoResponseData!!.promoData!!.modifiedCommision.toString()
                    rate = result!!.promoResponseData!!.promoData!!.modifiedRate!!.toDouble()
                    exchangeRate =
                        result!!.promoResponseData!!.promoData!!.modifiedRate!!.toString()
                    receiveAmount =
                        result!!.promoResponseData!!.promoData!!.modifiedBeneAmount!!.toString()

                    if (sendAmount != "null") {
                        gbpValue = sendAmount.toDouble()
                        binding.sendAmount.text = "GBP $sendAmount"
                    }
                    if (commission != "null") {
                        binding.transferFee.text = "GBP $commission"
                    }
                    if (sendAmount != "null" || commission != "null") {
                        try {
                            val sa = sendAmount.toDouble()
                            val cm = commission.toDouble()
                            totalAmount = sa + cm
                            binding.totalAmount.text = "GBP $totalAmount"
                        } catch (e: NumberFormatException) {
                            e.message
                        }
                    }
                    if (exchangeRate != "null") {
                        binding.exchangeRate.text = "GBP $exchangeRate"
                    }
                    if (receiveAmount != "null") {
                        binding.receiveAmount.text = "BDT $receiveAmount"
                    }

                    updateValuesGBP()
                }
            } catch (e: NullPointerException) {
                e.message
            }
        }
    }

    private fun updateValuesGBP() {
        if (gbpValue != null) {
            val bdtValue = gbpValue * rate
            val formattedBDT = decimalFormat.format(bdtValue)
            binding.receiveAmount.text = "BDT $formattedBDT"
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

}