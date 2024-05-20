package com.bsel.remitngo.presentation.ui.transaction

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.TransactionAdapter
import com.bsel.remitngo.bottomSheet.TransactionBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.api.RetrofitClient
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerItem
import com.bsel.remitngo.data.model.createReceipt.CreateReceiptResponse
import com.bsel.remitngo.data.model.emp.EmpItem
import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.encript.EncryptItemForCreateReceipt
import com.bsel.remitngo.data.model.profile.ProfileItem
import com.bsel.remitngo.data.model.transaction.TransactionData
import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.databinding.FragmentTransactionBinding
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TransactionFragment : Fragment() {
    @Inject
    lateinit var transactionViewModelFactory: TransactionViewModelFactory
    private lateinit var transactionViewModel: TransactionViewModel

    private lateinit var binding: FragmentTransactionBinding

    private lateinit var transactionAdapter: TransactionAdapter

    private lateinit var preferenceManager: PreferenceManager

    private val transactionBottomSheet: TransactionBottomSheet by lazy { TransactionBottomSheet() }

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

    var ipAddress: String? = null
    private lateinit var deviceId: String

    lateinit var dialogHandler: AlertDialogHandler
    lateinit var error: GenesisError
    private lateinit var paymentRequest: PaymentRequest
    private lateinit var configuration: Configuration
    private lateinit var transactionTypes: TransactionTypesRequest
    private lateinit var billingAddress: PaymentAddress
    private lateinit var threeDsV2Params: ThreeDsV2Params
    private lateinit var genesis: Genesis
    private lateinit var consumerId: String

    private var orderStatus: Int=0
    private var paymentMode: Int=0

    private lateinit var encryptCode: String
    private lateinit var transactionCode: String
    private var sendAmount: Double=0.0

    private lateinit var transactionCodeWithChannel: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionBinding.bind(view)

        (requireActivity().application as Injector).createTransactionSubComponent().inject(this)

        transactionViewModel =
            ViewModelProvider(this, transactionViewModelFactory)[TransactionViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
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

        val profileItem = ProfileItem(
            deviceId = deviceId, personId = personId
        )
        transactionViewModel.profile(profileItem)

        val consumerItem = ConsumerItem(
            deviceId = deviceId,
            personId = personId
        )
        transactionViewModel.consumer(consumerItem)

        val transactionItem = TransactionItem(
            deviceId = deviceId,
            loadType = 0,
            personId = personId
        )
        transactionViewModel.transaction(transactionItem)

        observeProfileResult()
        observeConsumerResult()
        observeTransactionResult()
    }
    private fun observeProfileResult() {
        transactionViewModel.profileResult.observe(this) { result ->
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
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun observeConsumerResult() {
        transactionViewModel.consumerResult.observe(this) { result ->
            try {
                if (result!!.consumerData != null) {
                    consumerId = result.consumerData!!.consumerId.toString()
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }
    private fun observeTransactionResult() {
        transactionViewModel.transactionResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    binding.transactionRecyclerView.layoutManager =
                        LinearLayoutManager(requireActivity())
                    transactionAdapter = TransactionAdapter(
                        selectedItem = { selectedItem: TransactionData ->
                            transaction(selectedItem)
                            binding.transactionSearch.setQuery("", false)
                        },
                        downloadReceipt = { downloadReceipt: TransactionData ->
                            downloadReceipt(downloadReceipt)
                        },
                        sendAgain = { sendAgain: TransactionData ->
                            sendAgain(sendAgain)
                        }
                    )
                    binding.transactionRecyclerView.adapter = transactionAdapter
                    transactionAdapter.setList(result.data as List<TransactionData>)
                    transactionAdapter.notifyDataSetChanged()

                    binding.transactionSearch.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            transactionAdapter.transactionFilter(newText.orEmpty())
                            return true
                        }
                    })
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun transaction(selectedItem: TransactionData) {
        val transactionCode = selectedItem.transactionCode.toString()
        transactionBottomSheet.setSelectedTransactionCode(transactionCode)
        transactionBottomSheet.show(childFragmentManager, transactionBottomSheet.tag)
    }

    private fun sendAgain(sendAgain: TransactionData) {
        val bundle = Bundle().apply {
            putString("transactionCode", sendAgain.transactionCode.toString())
        }
        findNavController().navigate(
            R.id.action_nav_transaction_history_to_nav_review,
            bundle
        )
    }

    private fun downloadReceipt(transactionData: TransactionData) {
        transactionCode = transactionData.transactionCode.toString()
        sendAmount = transactionData.sendAmount!!
        orderStatus = transactionData.orderStatus!!
        paymentMode = transactionData.paymentMode!!
        if(orderStatus == 21){
            if (paymentMode==5){
                val bundle = Bundle().apply {
                    putString("transactionCode", transactionData.transactionCode.toString())
                    putString("sendAmount", transactionData.sendAmount.toString())
                }
                findNavController().navigate(
                    R.id.action_nav_transaction_history_to_nav_complete_bank_transaction,
                    bundle
                )
            }else if (paymentMode==4){
                transactionCodeWithChannel = "$transactionCode*1"
                val encryptItem = EncryptItem(
                    key = "bsel2024$#@!",
                    plainText = transactionCodeWithChannel
                )
                transactionViewModel.encrypt(encryptItem)
                observeEncryptResult()
            }
        }else{
            checkApiCall(transactionCode)
        }
    }

    private fun observeEncryptResult() {
        transactionViewModel.encryptResult.observe(this) { result ->
            if (result!!.data != null) {
                encryptCode = result.data.toString()
                if (::encryptCode.isInitialized) {
                    cardPayment()
                }
            }
        }
    }

    private fun cardPayment() {
        configuration = Configuration(
            "8609ffa7b710c6c74645bfb055888b82ce71c08e",
            "637c89215aa96a41ef53468296459072d809c70a",
            Environments.STAGING,
            Endpoints.EMERCHANTPAY,
            Locales.EN
        )

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

        transactionTypes = TransactionTypesRequest()
        transactionTypes.addTransaction(WPFTransactionTypes.SALE3D)

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

        paymentRequest.setReturnSuccessUrl("https://emptest.remitngo.com/emerchantpay/wpfsuccessurl?tcode=$encryptCode")
        paymentRequest.setReturnFailureUrl("https://emptest.remitngo.com/emerchantpay/wpffailureurl?tcode=$encryptCode")
        paymentRequest.setReturnCancelUrl("https://emptest.remitngo.com/emerchantpay/wpfcancelurl?tcode=$encryptCode")

        paymentRequest.setUsage("Test Staging")
        paymentRequest.setDescription("Test payment gateway")
        paymentRequest.setLifetime(60)
        paymentRequest.setRememberCard(true)

        threeDsV2Params = ThreeDsV2Params.build {
            purchaseCategory = ThreeDsV2PurchaseCategory.GOODS
            recurring = ThreeDsV2RecurringParams()
        }
        paymentRequest.setThreeDsV2Params(threeDsV2Params)

        if (::consumerId.isInitialized && consumerId != "null") {
            paymentRequest.setConsumerId(consumerId)
        }

        genesis = Genesis(requireContext(), configuration, paymentRequest)

        if (!genesis.isConnected(requireContext())!!) {
            dialogHandler =
                AlertDialogHandler(requireContext(), "Error", ErrorMessages.CONNECTION_ERROR)
            dialogHandler.show()
        }

        if (genesis.isConnected(requireContext())!! && genesis.isValidData!!) {
            genesis.push()
            val response = genesis.response!!
            if (!response.isSuccess!!) {
                error = response.error!!
                dialogHandler = AlertDialogHandler(
                    requireContext(),
                    "Failure",
                    "Code: " + error!!.code + "\nMessage: " + error!!.technicalMessage
                )
                dialogHandler.show()
            } else if (response.isSuccess!!) {
                consumerId = response.consumerId.toString()
                val saveConsumerItem = SaveConsumerItem(
                    deviceId = deviceId,
                    personId = personId,
                    consumerId = consumerId
                )
                transactionViewModel.saveConsumer(saveConsumerItem)
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
                transactionViewModel.emp(encryptItem)
                observeEmpResult()

                val bundle = Bundle().apply {
                    putString("transactionCode", transactionCode)
                }
                findNavController().navigate(
                    R.id.action_nav_review_to_nav_complete_card_transaction,
                    bundle
                )

            }
        }

        if (!genesis.isValidData!!) {
            error = genesis.error!!
            var message = error.message!!
            var technicalMessage: String = if (error.technicalMessage != null && error.technicalMessage!!.isNotEmpty()) {
                error.technicalMessage!!
            } else {
                ""
            }
            dialogHandler =
                AlertDialogHandler(requireContext(), "Invalid", "$technicalMessage $message")
            dialogHandler.show()
        }

    }

    private fun observeSaveConsumerResult() {
        transactionViewModel.saveConsumerResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    Log.i("info", "Save Consumer: " + result.data.toString())
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun observeEmpResult() {
        transactionViewModel.empResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info", "save emp data: " + result.data.toString())
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun checkApiCall(transactionCode: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val fileUrl =
                    "https://uat.bracsaajanexchange.com/REmitERPBDUAT/UploadedFiles/PersonFiles/RemitnGoMoneyReceipt/$transactionCode.pdf"
                val url = URL(fileUrl)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "HEAD"
                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val receiptUrl =
                        "https://uat.bracsaajanexchange.com/REmitERPBDUAT/UploadedFiles/PersonFiles/RemitnGoMoneyReceipt/$transactionCode.pdf"
                    Log.i("info", "receiptUrl: $receiptUrl")
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(receiptUrl)
                    context?.startActivity(intent)
                } else {
                    transactionCodeWithChannel = "$transactionCode*1"
                    val encryptForCreateReceiptItem = EncryptItemForCreateReceipt(
                        key = "bsel2024$#@!",
                        plainText = transactionCodeWithChannel
                    )
                    transactionViewModel.encryptForCreateReceipt(encryptForCreateReceiptItem)
                    observeEncryptForCreateReceiptResult()
                }
                connection.disconnect()
            } catch (e: Exception) {
                e.message
            }
        }
    }

    private fun observeEncryptForCreateReceiptResult() {
        transactionViewModel.encryptForCreateReceiptResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    val createReceiptCode = result.data.toString()
                    createReceipt(createReceiptCode)
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    fun createReceipt(createReceiptCode: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<CreateReceiptResponse> =
                    RetrofitClient.apiService.createReceipt(createReceiptCode)
                if (response.isSuccessful) {
                    val createReceiptResponse: CreateReceiptResponse? = response.body()
                    Log.i("info", "createReceiptResponse: $createReceiptResponse")
                    val receiptUrl =
                        "https://uat.bracsaajanexchange.com/REmitERPBDUAT/UploadedFiles/PersonFiles/RemitnGoMoneyReceipt/$transactionCode.pdf"
                    Log.i("info", "receiptUrl: $receiptUrl")
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(receiptUrl)
                    context?.startActivity(intent)
                }
            } catch (e: Exception) {
                e.message
            }
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

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.nav_main)
        }
    }

}