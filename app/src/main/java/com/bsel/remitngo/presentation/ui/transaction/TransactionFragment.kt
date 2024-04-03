package com.bsel.remitngo.presentation.ui.transaction

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import com.bsel.remitngo.adapter.TransactionAdapter
import com.bsel.remitngo.bottomSheet.TransactionBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.api.RetrofitClient
import com.bsel.remitngo.data.model.createReceipt.CreateReceiptResponse
import com.bsel.remitngo.data.model.transaction.TransactionData
import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.databinding.FragmentTransactionBinding
import com.bsel.remitngo.presentation.di.Injector
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

import java.net.HttpURLConnection
import java.net.URL

class TransactionFragment : Fragment() {
    @Inject
    lateinit var transactionViewModelFactory: TransactionViewModelFactory
    private lateinit var transactionViewModel: TransactionViewModel

    private lateinit var binding: FragmentTransactionBinding

    private lateinit var transactionAdapter: TransactionAdapter

    private val transactionBottomSheet: TransactionBottomSheet by lazy { TransactionBottomSheet() }

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

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
        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

        val transactionItem = TransactionItem(
            deviceId = deviceId,
            params1 = personId.toInt(),
            params2 = 0
        )
        transactionViewModel.transaction(transactionItem)
        observeTransactionResult()

    }

    private fun observeTransactionResult() {
        transactionViewModel.transactionResult.observe(this) { result ->
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
                        transactionAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
            }
        }
    }

    private fun transaction(selectedItem: TransactionData) {
        val transactionCode = selectedItem.transactionCode.toString()
        transactionBottomSheet.setSelectedTransactionCode(transactionCode)
        transactionBottomSheet.show(childFragmentManager, transactionBottomSheet.tag)
    }

    private fun downloadReceipt(transactionData: TransactionData) {

        val orderStatus = transactionData.orderStatus.toString()
        Log.i("info", "orderStatus: $orderStatus")

        //paymentMode
        val paymentMode = transactionData.paymentType.toString()
        Log.i("info", "paymentMode: $paymentMode")

        if(orderStatus == "21"){
            if (paymentMode=="5"){
                //complete bank payment
                val bundle = Bundle().apply {
                    putString("transactionCode", transactionData.transactionCode.toString())
                }
                findNavController().navigate(
                    R.id.action_nav_transaction_history_to_nav_complete_bank_transaction,
                    bundle
                )
            }else if (paymentMode=="4"){
                //payment gateway
                val bundle = Bundle().apply {
                    putString("transactionCode", transactionData.transactionCode.toString())
                }
                findNavController().navigate(
                    R.id.action_nav_transaction_history_to_nav_review,
                    bundle
                )
            }
        }else{
            checkApiCall(transactionData.transactionCode.toString())
        }
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
                    val response: Response<CreateReceiptResponse> =
                        RetrofitClient.apiService.createReceipt(transactionCode)
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
                }
                connection.disconnect()
            } catch (e: Exception) {
                e.message
            }
        }
    }

}