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

    private lateinit var transactionCode: String

    var ipAddress: String? = null
    private lateinit var deviceId: String

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

        binding.paymentSuccessful.visibility=View.GONE
        binding.paymentFailed.visibility=View.GONE
        binding.paymentCancel.visibility=View.GONE
        binding.backToHomeLayout.visibility=View.GONE

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        transactionCode = arguments?.getString("transactionCode").toString()
        paymentViewModel.paymentStatus(transactionCode)

        observePaymentStatusResult()
        observeEncryptForCreateReceiptResult()

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_nav_complete_card_transaction_to_nav_main)
        }
        binding.btnDownloadReceipt.setOnClickListener {
            checkApiCall(transactionCode)
        }

    }

    private fun observePaymentStatusResult() {
        paymentViewModel.paymentStatusResult.observe(this) { result ->
            if (result!!.data == null) {
                paymentViewModel.paymentStatus(transactionCode)
                Log.i("info", "observePaymentStatusResult: ${result.data}")
                binding.paymentSuccessful.visibility=View.GONE
                binding.paymentFailed.visibility=View.GONE
                binding.paymentCancel.visibility=View.GONE
                binding.backToHomeLayout.visibility=View.GONE
            }else{
                Log.i("info", "observePaymentStatusResult: ${result.data}")
                if (result.data=="Success"){
                    binding.paymentSuccessful.visibility=View.VISIBLE
                    binding.paymentFailed.visibility=View.GONE
                    binding.paymentCancel.visibility=View.GONE
                    binding.backToHomeLayout.visibility=View.VISIBLE
                }else if (result.data=="Failed"){
                    binding.paymentSuccessful.visibility=View.GONE
                    binding.paymentFailed.visibility=View.VISIBLE
                    binding.paymentCancel.visibility=View.GONE
                    binding.backToHomeLayout.visibility=View.VISIBLE
                }else if (result.data=="Cancel"){
                    binding.paymentSuccessful.visibility=View.GONE
                    binding.paymentFailed.visibility=View.GONE
                    binding.paymentCancel.visibility=View.VISIBLE
                    binding.backToHomeLayout.visibility=View.VISIBLE
                }
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
                    val encryptForCreateReceiptItem = EncryptItemForCreateReceipt(
                        key = "bsel2024$#@!",
                        plainText = transactionCode
                    )
                    paymentViewModel.encryptForCreateReceipt(encryptForCreateReceiptItem)
                }
                connection.disconnect()
            } catch (e: Exception) {
                e.message
            }
        }
    }

    private fun observeEncryptForCreateReceiptResult() {
        paymentViewModel.encryptForCreateReceiptResult.observe(this) { result ->
            if (result!!.data != null) {
                val createReceiptCode = result.data.toString()
                createReceipt(createReceiptCode)
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

    //        Handler(Looper.getMainLooper()).postDelayed({
//
//            val timerDuration = 60 * 1000
//
//            val dialog = Dialog(requireContext())
//            dialog.setContentView(R.layout.fragment_payment_status)
//            dialog.setCancelable(false)
//            dialog.window?.setLayout(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.MATCH_PARENT
//            )
//
//            val receiveName = dialog.findViewById<TextView>(R.id.recipient_name)
//            val receiveAmount = dialog.findViewById<TextView>(R.id.receive_amount)
//            val transactionId = dialog.findViewById<TextView>(R.id.transactionCode)
//            val timer = dialog.findViewById<TextView>(R.id.timerTxt)
//
//            receiveName.text = "Your transfer to $receiveName is processing !"
//            receiveAmount.text = "BDT $receiveAmount"
//            transactionId.text = "Your Transfer ID $transactionCode"
//            dialog.show()
//
//            object : CountDownTimer(timerDuration.toLong(), 1000) {
//                override fun onTick(millisUntilFinished: Long) {
//                    val secondsRemaining = millisUntilFinished / 1000
//                    val minutes = secondsRemaining / 60
//                    val seconds = secondsRemaining % 60
//                    timer.text = String.format("%02d:%02d", minutes, seconds)
//                }
//
//                override fun onFinish() {
//                    dialog.dismiss()
//                }
//            }.start()
//
//            Handler(Looper.getMainLooper()).postDelayed({
//                dialog.dismiss()
//            }, 30000)
//        }, 30000)

}