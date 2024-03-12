package com.bsel.remitngo.presentation.ui.payment

import android.content.Context
import android.net.wifi.WifiManager
import android.os.*
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerItem
import com.bsel.remitngo.data.model.emp.EmpItem
import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.payment.PaymentItem
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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PaymentFragment : Fragment() {
    @Inject
    lateinit var paymentViewModelFactory: PaymentViewModelFactory
    private lateinit var paymentViewModel: PaymentViewModel

    private lateinit var binding: FragmentPaymentBinding

    private lateinit var preferenceManager: PreferenceManager

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private lateinit var orderType: String
    private lateinit var paymentType: String

    private lateinit var personId: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var customerEmail: String
    private lateinit var customerMobile: String
    private lateinit var customerdateOfBirth: String
    private lateinit var cusBankInfoId: String
    private lateinit var recipientName: String
    private lateinit var recipientMobile: String
    private lateinit var recipientAddress: String

    private lateinit var bankId: String
    private lateinit var bankName: String

    private lateinit var accountNo: String
    private lateinit var branchId: String

    private lateinit var send_amount: String
    private lateinit var receive_amount: String

    private lateinit var payingAgentId: String
    private lateinit var payingAgentName: String

    private lateinit var exchangeRate: String
    private lateinit var bankCommission: String
    private lateinit var cardCommission: String

    private lateinit var transactionCode: String
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
        customerdateOfBirth = preferenceManager.loadData("dob").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        orderType = arguments?.getString("orderType").toString()
        paymentType = arguments?.getString("paymentType").toString()

        cusBankInfoId = arguments?.getString("cusBankInfoId").toString()
        recipientName = arguments?.getString("recipientName").toString()
        recipientMobile = arguments?.getString("recipientMobile").toString()
        recipientAddress = arguments?.getString("recipientAddress").toString()

        bankId = arguments?.getString("bankId").toString()
        bankName = arguments?.getString("bankName").toString()

        accountNo = arguments?.getString("accountNo").toString()
        branchId = arguments?.getString("branchId").toString()

        send_amount = arguments?.getString("send_amount").toString()
        receive_amount = arguments?.getString("receive_amount").toString()

        payingAgentId = arguments?.getString("payingAgentId").toString()
        payingAgentName = arguments?.getString("payingAgentName").toString()

        exchangeRate = arguments?.getString("exchangeRate").toString()
        bankCommission = arguments?.getString("bankCommission").toString()
        cardCommission = arguments?.getString("cardCommission").toString()

        binding.sendAmount.setText("GBP $send_amount").toString()
        binding.transferFee.setText("GBP $cardCommission").toString()
        binding.totalSend.setText("GBP $send_amount").toString()
        binding.exchangeRate.setText("BDT $exchangeRate").toString()
        binding.receiveAmount.setText("BDT $receive_amount").toString()

        binding.bankAccountName.setText("$recipientName").toString()
        binding.bankAccountNumber.setText("$bankName").toString()

        binding.recipientName.setText("$recipientName").toString()
        binding.bankName.setText("$bankName").toString()
        binding.accountNo.setText("$accountNo").toString()

        binding.btnSend.setOnClickListener {
            val paymentItem = PaymentItem(
                deviceId = deviceId,
                userIPAddress = ipAddress,
                personID = personId,
                customerName = "$firstName $lastName",
                customerEmail = customerEmail,
                customerMobile = customerMobile,
                customerdateOfBirth = customerdateOfBirth,
                fromCountryID = "4",
                fromCurrencyID = "96",
                fromCurrencyCode = "GBP",
                benPersonID = cusBankInfoId,
                beneficiaryName = recipientName,
                beneficaryEmail = "",
                beneficarymobile = recipientMobile,
                beneficaryAddress = recipientAddress,
                bankId = bankId,
                bankName = bankName,
                accountNo = accountNo,
                benBranchId = branchId,
                collectionBankID = payingAgentId,
                collectionBankName = payingAgentName,
                sendAmount = send_amount,
                receivableAmount = receive_amount,
                rate = exchangeRate,
                commission = cardCommission,
                total = receive_amount,
                toCountryID = "1",
                toCurrencyID = "6",
                toCurrencyCode = "BDT",
                orderTypeID = orderType,
                paymentMode = paymentType,
                purposeOfTransferId = "0",
                sourceOfFundId = "0",
                isMobileTransfer = true,
                isiOS = false,
                latitude = "",
                longitude = ""
            )
            paymentViewModel.payment(paymentItem)
        }
        observePaymentResult()
        observeEncryptResult()

        val consumerItem = ConsumerItem(
            deviceId = deviceId,
            params1 = personId.toInt(),
            params2 = ""
        )
        paymentViewModel.consumer(consumerItem)
        observeConsumerResult()

        observeSaveConsumerResult()
        observeEmpResult()

    }

    private fun observePaymentResult() {
        paymentViewModel.paymentResult.observe(this) { result ->
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

    private fun observeConsumerResult() {
        paymentViewModel.consumerResult.observe(this) { result ->
            if (result!!.data != null) {
                consumerId = result.data.toString()
            }
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
            BigDecimal(send_amount),
            Currency.GBP,
            customerEmail,
            customerMobile,
            billingAddress,
            "https://uat2.remitngo.com/Emerchantpay/WPFNotificationURL.aspx",
            transactionTypes
        )

        paymentRequest.setReturnSuccessUrl("https://emptest.remitngo.com/Emerchantpay/WPFSuccessURL?tcode="+encryptCode)
        paymentRequest.setReturnFailureUrl("https://emptest.remitngo.com/Emerchantpay/WPFFailureURL?tcode="+encryptCode)
        paymentRequest.setReturnCancelUrl("https://emptest.remitngo.com/Emerchantpay/WPFCancelURL?tcode="+encryptCode)

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

//                findNavController().navigate(
//                    R.id.action_nav_review_to_nav_complete_card_transaction
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