package com.bsel.remitngo.presentation.ui.payment

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.databinding.FragmentPaymentBankBinding
import com.bsel.remitngo.presentation.di.Injector
import java.util.*
import javax.inject.Inject

class PaymentBankFragment : Fragment() {
    @Inject
    lateinit var paymentViewModelFactory: PaymentViewModelFactory
    private lateinit var paymentViewModel: PaymentViewModel

    private lateinit var binding: FragmentPaymentBankBinding

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var personId: String

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private lateinit var sendAmount: String

    private lateinit var transactionCode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBankBinding.bind(view)

        (requireActivity().application as Injector).createPaymentSubComponent().inject(this)

        paymentViewModel =
            ViewModelProvider(this, paymentViewModelFactory)[PaymentViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        transactionCode = arguments?.getString("transactionCode").toString()
        sendAmount = arguments?.getString("sendAmount").toString()

        if (transactionCode != "null") {
            binding.transactionCode.text = "$transactionCode"
        }
        if (sendAmount != "null") {
            binding.sendAmount.text =
                "Transfer $sendAmount GBP to the following Bank Account"
        }

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_nav_complete_bank_transaction_to_nav_main)
        }
        binding.goToTransactionHistory.setOnClickListener {
            findNavController().navigate(R.id.action_nav_complete_bank_transaction_to_nav_transaction_history)
        }

        payNow()

    }

    private fun payNow() {
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

                    transactionCode = paymentTransactionData?.transactionCode.toString()
                    sendAmount = paymentTransactionData?.sendAmount.toString()

                    if (sendAmount != "null") {
                        binding.sendAmount.text =
                            "Transfer $sendAmount GBP to the following Bank Account"
                    }
                    if (transactionCode != "null") {
                        binding.transactionCode.text = "$transactionCode"
                    }
                }
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

}