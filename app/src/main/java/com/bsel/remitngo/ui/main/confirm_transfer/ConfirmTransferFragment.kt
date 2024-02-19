package com.bsel.remitngo.ui.main.confirm_transfer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.FragmentConfirmTransferBinding
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

class ConfirmTransferFragment : Fragment() {

    private lateinit var binding: FragmentConfirmTransferBinding

    private lateinit var orderType: String
    private lateinit var paymentType: String

    lateinit var dialogHandler: AlertDialogHandler
    lateinit var error: GenesisError

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm_transfer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfirmTransferBinding.bind(view)

        orderType = arguments?.getString("orderType").toString()
        paymentType = arguments?.getString("paymentType").toString()

        binding.btnSend.setOnClickListener {
            if (paymentType == "Card Payment") {
                cardPayment()
            } else if (paymentType == "Bank Transfer") {
                findNavController().navigate(R.id.action_nav_confirm_transfer_to_nav_complete_bank_transaction)
            }
        }

    }
    private fun cardPayment() {

        // Generate unique Id
        val uniqueId = UUID.randomUUID().toString()

        // Create configuration
        val configuration = Configuration(
            "8609ffa7b710c6c74645bfb055888b82ce71c08e",
            "637c89215aa96a41ef53468296459072d809c70a",
            Environments.STAGING,
            Endpoints.EMERCHANTPAY,
            Locales.EN
        )
        configuration.setDebugMode(true)

        // Create Billing PaymentAddress
        val billingAddress = PaymentAddress(
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
        val transactionTypes = TransactionTypesRequest()
        transactionTypes.addTransaction(WPFTransactionTypes.SALE3D)

        // Init WPF API request
        val paymentRequest = PaymentRequest(
            requireContext(),
            uniqueId,
            BigDecimal("100"),
            Currency.GBP,
            "alalkodu@gmail.com",
            "07893986598",
            billingAddress,
//            "https://uat2.remitngo.com/Emerchantpay/WPFNotificationURL.aspx",
            "https://rnguat.bracsaajanexchange.com/api/Payment/EmerchantNotification",
            transactionTypes
        )
        // Set return URLs after a delay of 30 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            paymentRequest.setReturnSuccessUrl("https://uat2.remitngo.com/Emerchantpay/WPFSuccessURL.aspx")
            paymentRequest.setReturnFailureUrl("https://uat2.remitngo.com/Emerchantpay/WPFFailureURL.aspx")
            paymentRequest.setReturnCancelUrl("https://uat2.remitngo.com/Emerchantpay/WPFCancelURL.aspx")
        }, 1000)
        findNavController().navigate(R.id.action_nav_confirm_transfer_to_nav_transaction)

        // Show WebView
//        val webView = WebView(requireContext())
//        webView.loadUrl("https://uat2.remitngo.com/Emerchantpay/WPFSuccessURL.aspx") // Load any of the URLs
//
//        val dialog = AlertDialog.Builder(context)
//            .setView(webView)
//            .create()
//
//        dialog.show()

        // Dismiss WebView after 30 seconds
//        Handler(Looper.getMainLooper()).postDelayed({
//            dialog.dismiss()
//        }, 30000)

        paymentRequest.setUsage("Test Staging")
        paymentRequest.setDescription("Test payment gateway")
        paymentRequest.setLifetime(60)
        paymentRequest.setRememberCard(true)

        paymentRequest.setRecurringType(RecurringType.INITIAL)
        paymentRequest.setRecurringCategory(RecurringCategory.STANDING_ORDER)

        // Risk params
        val riskParams = RiskParams(
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

        val threeDsV2Params = ThreeDsV2Params.build {
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
        paymentRequest.setConsumerId("157074")

        val genesis = Genesis(requireContext(), configuration, paymentRequest)

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

            // Check if response isSuccess
            if (!response!!.isSuccess!!) {
                // Get Error Handler
                error = response!!.error!!

                dialogHandler = AlertDialogHandler(
                    requireContext(),
                    "Failure",
                    "Code: " + error!!.code + "\nMessage: " + error!!.technicalMessage
                )
                dialogHandler.show()

            }else if (response!!.isSuccess!!){
                val consumerId = response.consumerId.toString()
                Log.i("info", "consumerId: $consumerId")
            }
        }

        if (!genesis.isValidData!!) {
            // Get Error Hand
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

}