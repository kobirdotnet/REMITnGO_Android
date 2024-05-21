package com.bsel.remitngo.presentation.ui.payment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.*
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
import com.bsel.remitngo.data.api.RetrofitClient
import com.bsel.remitngo.data.model.createReceipt.CreateReceiptResponse
import com.bsel.remitngo.data.model.encript.EncryptItemForCreateReceipt
import com.bsel.remitngo.databinding.FragmentPaymentCardBinding
import com.bsel.remitngo.presentation.di.Injector
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import javax.inject.Inject

class PaymentCardFragment : Fragment() {
    @Inject
    lateinit var paymentViewModelFactory: PaymentViewModelFactory
    private lateinit var paymentViewModel: PaymentViewModel

    private lateinit var binding: FragmentPaymentCardBinding

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private var transactionCode: String? = null

    private var transactionCodeWithChannel: String? = null
    private var receiptUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentCardBinding.bind(view)

        (requireActivity().application as Injector).createPaymentSubComponent().inject(this)

        paymentViewModel =
            ViewModelProvider(this, paymentViewModelFactory)[PaymentViewModel::class.java]

        binding.paymentSuccessful.visibility = View.GONE
        binding.paymentFailed.visibility = View.GONE
        binding.paymentCancel.visibility = View.GONE
        binding.backToHomeLayout.visibility = View.GONE

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_nav_complete_card_transaction_to_nav_main)
        }

        binding.btnDownloadReceipt.setOnClickListener {
            if (receiptUrl != null) {
                Log.i("info", "receiptUrl btnDownloadReceipt: $receiptUrl")
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(receiptUrl)
                context?.startActivity(intent)
            }
        }
        binding.btnShareReceipt.setOnClickListener {
            if (receiptUrl != null) {
                Log.i("info", "receiptUrl btnShareReceipt: $receiptUrl")
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, receiptUrl)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(sendIntent, "REMITnGO"))
            }
        }

        transactionCode = arguments?.getString("transactionCode").toString()
        paymentViewModel.paymentStatusResult.observe(this) { result ->
            try {
                if (result!!.data == null) {
                    binding.paymentSuccessful.visibility = View.GONE
                    binding.paymentFailed.visibility = View.GONE
                    binding.paymentCancel.visibility = View.VISIBLE
                    binding.backToHomeLayout.visibility = View.VISIBLE
                } else {
                    when (result.data) {
                        "Success" -> {
                            binding.paymentSuccessful.visibility = View.VISIBLE
                            binding.paymentFailed.visibility = View.GONE
                            binding.paymentCancel.visibility = View.GONE
                            binding.backToHomeLayout.visibility = View.VISIBLE
                            checkApiCall(transactionCode!!)
                            Log.i("info", "receiptUrl Success: $transactionCode")
                        }
                        "Failed" -> {
                            binding.paymentSuccessful.visibility = View.GONE
                            binding.paymentFailed.visibility = View.VISIBLE
                            binding.paymentCancel.visibility = View.GONE
                            binding.backToHomeLayout.visibility = View.VISIBLE
                            Log.i("info", "receiptUrl Failed: $transactionCode")
                        }
                        "Cancel" -> {
                            binding.paymentSuccessful.visibility = View.GONE
                            binding.paymentFailed.visibility = View.GONE
                            binding.paymentCancel.visibility = View.VISIBLE
                            binding.backToHomeLayout.visibility = View.VISIBLE
                            Log.i("info", "receiptUrl Cancel: $transactionCode")
                        }
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }

        paymentViewModel.encryptForCreateReceiptResult.observe(viewLifecycleOwner) { result ->
            try {
                if (result!!.data != null) {
                    val createReceiptCode = result.data!!
                    Log.i("info", "receiptUrl createReceiptCode: $createReceiptCode")
                    createReceipt(createReceiptCode)
                }
            } catch (e:NullPointerException) {
                e.localizedMessage
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
                    receiptUrl =
                        "https://uat.bracsaajanexchange.com/REmitERPBDUAT/UploadedFiles/PersonFiles/RemitnGoMoneyReceipt/$transactionCode.pdf"
                    Log.i("info", "receiptUrl checkApiCall HTTP_OK: $receiptUrl")
                } else {
                    Log.i("info", "receiptUrl checkApiCall HTTP_Not_OK: $receiptUrl")
                    transactionCodeWithChannel = "$transactionCode*1"
                    val encryptForCreateReceiptItem = EncryptItemForCreateReceipt(
                        key = "bsel2024$#@!",
                        plainText = transactionCodeWithChannel
                    )
                    paymentViewModel.encryptForCreateReceipt(encryptForCreateReceiptItem)
                }
                connection.disconnect()
            } catch (e: Exception) {
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
                    Log.i("info", "receiptUrl createReceiptResponse: $createReceiptResponse")
                    receiptUrl =
                        "https://uat.bracsaajanexchange.com/REmitERPBDUAT/UploadedFiles/PersonFiles/RemitnGoMoneyReceipt/$transactionCode.pdf"
                }
            } catch (e: Exception) {
                e.localizedMessage
            }
        }
    }

    override fun onResume() {
        super.onResume()
        paymentViewModel.paymentStatus(transactionCode!!)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_nav_complete_card_transaction_to_nav_main)
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