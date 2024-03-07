package com.bsel.remitngo.presentation.ui.bank

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import javax.inject.Inject

class ChooseBankFragment : Fragment() {
    @Inject
    lateinit var bankViewModelFactory: BankViewModelFactory
    private lateinit var bankViewModel: BankViewModel

    private lateinit var binding: FragmentChooseBankBinding

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var bankAdapter: BankAdapter

    private lateinit var orderType: String
    private lateinit var paymentType: String

    private lateinit var send_amount: String
    private lateinit var receive_amount: String

    private lateinit var bankId: String
    private lateinit var bankName: String

    private lateinit var payingAgentId: String
    private lateinit var payingAgentName: String

    private lateinit var exchangeRate: String
    private lateinit var bankCommission: String
    private lateinit var cardCommission: String

    private lateinit var cusBankInfoId: String
    private lateinit var recipientName: String
    private lateinit var recipientMobile: String
    private lateinit var recipientAddress: String

    private lateinit var deviceId: String
    private lateinit var personId: String

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
        deviceId = getDeviceId(requireContext())

        orderType = arguments?.getString("orderType").toString()
        paymentType = arguments?.getString("paymentType").toString()

        send_amount = arguments?.getString("send_amount").toString()
        receive_amount = arguments?.getString("receive_amount").toString()

        bankId = arguments?.getString("bankId").toString()
        bankName = arguments?.getString("bankName").toString()

        payingAgentId = arguments?.getString("payingAgentId").toString()
        payingAgentName = arguments?.getString("payingAgentName").toString()

        exchangeRate = arguments?.getString("exchangeRate").toString()
        bankCommission = arguments?.getString("bankCommission").toString()
        cardCommission = arguments?.getString("cardCommission").toString()

        cusBankInfoId = arguments?.getString("cusBankInfoId").toString()
        recipientName = arguments?.getString("recipientName").toString()
        recipientMobile = arguments?.getString("recipientMobile").toString()
        recipientAddress = arguments?.getString("recipientAddress").toString()

        binding.btnBank.setOnClickListener {
            val bundle = Bundle().apply {
                putString("orderType", orderType)
                putString("paymentType", paymentType)

                putString("send_amount", send_amount)
                putString("receive_amount", receive_amount)

                putString("bankId", bankId)
                putString("bankName", bankName)

                putString("payingAgentId", payingAgentId)
                putString("payingAgentName", payingAgentName)

                putString("exchangeRate", exchangeRate.toString())
                putString("bankCommission", bankCommission.toString())
                putString("cardCommission", cardCommission.toString())

                putString("cusBankInfoId", cusBankInfoId)
                putString("recipientName", recipientName)
                putString("recipientMobile", recipientMobile)
                putString("recipientAddress", recipientAddress)
            }
            findNavController().navigate(
                R.id.action_nav_choose_bank_to_nav_save_bank,
                bundle
            )
        }

        val getBankItem = GetBankItem(
            deviceId = deviceId,
            params1 = cusBankInfoId.toInt(),
            params2 = orderType.toInt()
        )
        bankViewModel.getBank(getBankItem)
        observeGetBankResult()
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
        val bundle = Bundle().apply {
            putString("orderType", orderType)
            putString("paymentType", paymentType)

            putString("cusBankInfoId", cusBankInfoId)
            putString("recipientName", recipientName)
            putString("recipientMobile", recipientMobile)
            putString("recipientAddress", recipientAddress)

            putString("bankId", selectedItem.bankId.toString())
            putString("bankName", selectedItem.bankName.toString())

            putString("accountNo", selectedItem.accountNo.toString())
            putString("branchId", selectedItem.branchId.toString())

            putString("send_amount", send_amount)
            putString("receive_amount", receive_amount)

            putString("payingAgentId", payingAgentId)
            putString("payingAgentName", payingAgentName)

            putString("exchangeRate", exchangeRate.toString())
            putString("bankCommission", bankCommission.toString())
            putString("cardCommission", cardCommission.toString())
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

}