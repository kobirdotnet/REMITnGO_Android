package com.bsel.remitngo.presentation.ui.payment

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.databinding.FragmentPaymentBankBinding
import com.bsel.remitngo.presentation.di.Injector
import java.util.*
import javax.inject.Inject

import android.content.ClipData
import android.content.ClipboardManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService

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

        binding.imgCopy.setOnClickListener {
            val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Transaction Code", binding.transactionCode.text)
            clipboardManager.setPrimaryClip(clipData)
            Log.i("info", "clipData: $clipData")
            binding.imgCopy.setImageResource(R.drawable.copy)
        }

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_nav_complete_bank_transaction_to_nav_main)
        }
        binding.goToTransactionHistory.setOnClickListener {
            findNavController().navigate(R.id.action_nav_complete_bank_transaction_to_nav_transaction_history)
        }

        paymentViewModel.bankTransactionMessage("2")

        payNow()

        observeBankTransactionMessageResult()
        observeTransactionDetailsResult()

    }

    private fun observeBankTransactionMessageResult() {
        paymentViewModel.bankTransactionMessageResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (bankTransactionMessage in result.data!!) {
                        binding.accountName.text = bankTransactionMessage!!.accountName.toString()
                        binding.accountNo.text = bankTransactionMessage.accountNumber.toString()
                        binding.shortCode.text = bankTransactionMessage.sortCode.toString()
                        binding.iban.text = bankTransactionMessage.iBAN.toString()
                        binding.message.text = bankTransactionMessage.message.toString()
                    }
                }
            }catch (e:java.lang.NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun observeTransactionDetailsResult() {
        paymentViewModel.paymentTransactionResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (paymentTransactionData in result.data!!) {

                        transactionCode = paymentTransactionData?.transactionCode.toString()
                        sendAmount = paymentTransactionData?.totalAmount.toString()

                        if (sendAmount != "null") {
                            binding.sendAmount.text =
                                "Transfer $sendAmount GBP to the following Bank Account"
                        }
                        if (transactionCode != "null") {
                            binding.transactionCode.text = "$transactionCode"
                        }
                    }
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun payNow() {
        if (::transactionCode.isInitialized && transactionCode != "null") {
            val transactionDetailsItem = TransactionDetailsItem(
                deviceId = deviceId,
                transactionCode = transactionCode
            )
            paymentViewModel.paymentTransaction(transactionDetailsItem)
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
            findNavController().navigate(R.id.action_nav_complete_bank_transaction_to_nav_main)
        }
    }

}