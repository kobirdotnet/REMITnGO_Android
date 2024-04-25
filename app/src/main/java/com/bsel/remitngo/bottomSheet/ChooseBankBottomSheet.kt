package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.BankAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.bank.bank_account.GetBankData
import com.bsel.remitngo.data.model.bank.bank_account.GetBankItem
import com.bsel.remitngo.databinding.ChooseBankLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.bank.BankViewModel
import com.bsel.remitngo.presentation.ui.bank.BankViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import javax.inject.Inject

class ChooseBankBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var bankViewModelFactory: BankViewModelFactory
    private lateinit var bankViewModel: BankViewModel

    private lateinit var binding: ChooseBankLayoutBinding

    private lateinit var chooseBankBehavior: BottomSheetBehavior<*>

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var bankAdapter: BankAdapter

    private lateinit var personId: String
    private var benePersonId: Int = 0
    private var bankId: Int = 0
    private var walletId: Int = 0

    private var beneId: Int = 0
    private var beneAccountName: String? = null
    private var beneMobile: String? = null

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private var orderType: Int=0


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.choose_bank_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        chooseBankBehavior = BottomSheetBehavior.from(view.parent as View)
        chooseBankBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        chooseBankBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull view: View, i: Int) {
                when (i) {
                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                }
            }

            override fun onSlide(@NonNull view: View, v: Float) {}
        })

        (requireActivity().application as Injector).createBankSubComponent().inject(this)

        bankViewModel =
            ViewModelProvider(this, bankViewModelFactory)[BankViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        when (orderType) {
            3 -> {
                binding.accountTxt.text = "Bank Account"
            }
            5 -> {
                binding.accountTxt.text = "Bank Account"
            }
            1 -> {
                binding.accountTxt.text = "Wallet Account"
            }
        }

        binding.btnBank.setOnClickListener {

        }

        if (orderType==1){
            val getBankItem = GetBankItem(
                benePersonId = benePersonId,
                accountType = orderType,
                walletId = walletId,
                bankId=bankId
            )
            bankViewModel.getBank(getBankItem)
        }else{
            val getBankItem = GetBankItem(
                benePersonId = benePersonId,
                accountType = 2,
                walletId = walletId,
                bankId=bankId
            )
            bankViewModel.getBank(getBankItem)
        }

        observeGetBankResult()

        return bottomSheet
    }

    fun setOrderType(orderType: Int, benePersonId: Int) {
        this.orderType =orderType
        this.benePersonId =benePersonId
    }

    private fun observeGetBankResult() {
        bankViewModel.getBankResult.observe(this) { result ->
            try {
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
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun bankItem(selectedItem: GetBankData) {

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

    override fun onStart() {
        super.onStart()
        chooseBankBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }


}