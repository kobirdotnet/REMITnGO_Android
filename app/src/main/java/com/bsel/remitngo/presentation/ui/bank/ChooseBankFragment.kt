package com.bsel.remitngo.presentation.ui.bank

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.BankAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.bank.bank_account.GetBankData
import com.bsel.remitngo.data.model.bank.bank_account.GetBankItem
import com.bsel.remitngo.databinding.FragmentChooseBankBinding
import com.bsel.remitngo.presentation.di.Injector
import java.util.*
import javax.inject.Inject

class ChooseBankFragment : Fragment() {
    @Inject
    lateinit var bankViewModelFactory: BankViewModelFactory
    private lateinit var bankViewModel: BankViewModel

    private lateinit var binding: FragmentChooseBankBinding

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var bankAdapter: BankAdapter

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
    private lateinit var receiveAmount: String

    private lateinit var exchangeRate: String
    private lateinit var commission: String

    private lateinit var bankId: String
    private lateinit var branchId: String
    private lateinit var bankName: String
    private lateinit var payingAgentId: String

    private lateinit var benId: String
    private lateinit var beneficiaryId: String
    private lateinit var beneficiaryName: String
    private lateinit var beneficiaryPhoneNumber: String

    private lateinit var reasonId: String
    private lateinit var reasonName: String

    private lateinit var sourceOfIncomeId: String
    private lateinit var sourceOfIncomeName: String

    private var benePersonId: Int = 0
    private var walletId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChooseBankBinding.bind(view)

        (requireActivity().application as Injector).createBankSubComponent().inject(this)

        bankViewModel =
            ViewModelProvider(this, bankViewModelFactory)[BankViewModel::class.java]

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

        benId = arguments?.getString("benId").toString()
        beneficiaryId = arguments?.getString("beneficiaryId").toString()
        beneficiaryName = arguments?.getString("beneficiaryName").toString()
        beneficiaryPhoneNumber = arguments?.getString("beneficiaryPhoneNumber").toString()

        reasonId = arguments?.getString("reasonId").toString()
        reasonName = arguments?.getString("reasonName").toString()

        sourceOfIncomeId = arguments?.getString("sourceOfIncomeId").toString()
        sourceOfIncomeName = arguments?.getString("sourceOfIncomeName").toString()

        binding.btnBank.setOnClickListener {
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

                putString("benId", benId)
                putString("beneficiaryId", beneficiaryId)
                putString("beneficiaryName", beneficiaryName)
                putString("beneficiaryPhoneNumber", beneficiaryPhoneNumber)

                putString("reasonId", reasonId)
                putString("reasonName", reasonName)

                putString("sourceOfIncomeId", sourceOfIncomeId)
                putString("sourceOfIncomeName", sourceOfIncomeName)
            }
            findNavController().navigate(
                R.id.action_nav_choose_bank_to_nav_save_bank,
                bundle
            )
        }

        if (orderType=="null"){
            val getBankItem = GetBankItem(
                benePersonId = benePersonId,
                accountType = orderType.toInt(),
                walletId = walletId,
                bankId=bankId.toInt()
            )
            bankViewModel.getBank(getBankItem)
            observeGetBankResult()
        }else{
            try {
                val getBankItem = GetBankItem(
                    benePersonId = benePersonId,
                    accountType = orderType.toInt(),
                    walletId = walletId,
                    bankId=bankId.toInt()
                )
                bankViewModel.getBank(getBankItem)
                observeGetBankResult()
            }catch (e:NumberFormatException){
                e.localizedMessage
            }
        }

        if (orderType=="3"){
            binding.accountTxt.text = "Bank Account"
        }else if (orderType=="5"){
            binding.accountTxt.text = "Bank Account"
        }else if (orderType=="1"){
            binding.accountTxt.text = "Wallet Account"
        }
    }

    private fun observeGetBankResult() {
        bankViewModel.getBankResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.bankRecyclerView.layoutManager =
                    LinearLayoutManager(requireActivity())
                bankAdapter = BankAdapter(
                    selectedItem = { selectedItem: GetBankData ->
                        bankItem(selectedItem)
                        binding.bankSearch.setQuery("", false)
                    }
                )
                binding.bankRecyclerView.adapter = bankAdapter
                bankAdapter.setList(result.data as List<GetBankData>)
                bankAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun bankItem(selectedItem: GetBankData) {

        bankId=selectedItem.bankId.toString()
        branchId=selectedItem.branchId.toString()
        bankName=selectedItem.bankName.toString()

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

            putString("benId", benId)
            putString("beneficiaryId", beneficiaryId)
            putString("beneficiaryName", beneficiaryName)
            putString("beneficiaryPhoneNumber", beneficiaryPhoneNumber)

            putString("reasonId", reasonId)
            putString("reasonName", reasonName)

            putString("sourceOfIncomeId", sourceOfIncomeId)
            putString("sourceOfIncomeName", sourceOfIncomeName)
        }
        findNavController().navigate(
            R.id.action_nav_choose_bank_to_nav_review,
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