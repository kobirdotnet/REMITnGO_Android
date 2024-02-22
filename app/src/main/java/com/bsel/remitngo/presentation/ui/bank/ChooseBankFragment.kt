package com.bsel.remitngo.presentation.ui.bank

import android.content.Context
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
import com.bsel.remitngo.adapter.BeneficiaryAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.bank.get_bank_account.GetBankData
import com.bsel.remitngo.data.model.bank.get_bank_account.GetBankItem
import com.bsel.remitngo.data.model.beneficiary.get_beneficiary.GetBeneficiaryData
import com.bsel.remitngo.data.model.beneficiary.get_beneficiary.GetBeneficiaryItem
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
    private lateinit var cusBankInfoId: String
    private lateinit var recipientName: String

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
        cusBankInfoId = arguments?.getString("cusBankInfoId").toString()
        recipientName = arguments?.getString("recipientName").toString()
        binding.btnBank.setOnClickListener {
            val bundle = Bundle().apply {
                putString("orderType", orderType)
                putString("paymentType", paymentType)
                putString("cusBankInfoId", cusBankInfoId)
                putString("recipientName", recipientName)
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
        // Call the login method in the ViewModel
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
                Log.i("info", "get bank successful: $result")
            } else {
                Log.i("info", "get bank failed")
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

    private fun bankItem(selectedItem: GetBankData) {
        val bundle = Bundle().apply {
            putString("accountNo", selectedItem.accountNo.toString())
            putString("bankName", selectedItem.bankName.toString())
            putString("orderType", orderType)
            putString("paymentType", paymentType)
            putString("cusBankInfoId", cusBankInfoId)
            putString("recipientName", recipientName)
        }
        findNavController().navigate(
            R.id.action_nav_choose_bank_to_nav_review,
            bundle
        )
    }

}