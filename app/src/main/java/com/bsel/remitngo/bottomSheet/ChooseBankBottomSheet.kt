package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.BankAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnBankAndWalletSelectedListener
import com.bsel.remitngo.data.interfaceses.OnSaveBankAndWalletSelectedListener
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

class ChooseBankBottomSheet : BottomSheetDialogFragment(), OnSaveBankAndWalletSelectedListener {
    @Inject
    lateinit var bankViewModelFactory: BankViewModelFactory
    private lateinit var bankViewModel: BankViewModel

    private lateinit var binding: ChooseBankLayoutBinding

    var itemSelectedListener: OnBankAndWalletSelectedListener? = null

    private lateinit var chooseBankBehavior: BottomSheetBehavior<*>

    private val saveBankAndWalletBottomSheet: SaveBankAndWalletBottomSheet by lazy { SaveBankAndWalletBottomSheet() }

    private lateinit var bankAdapter: BankAdapter

    private lateinit var preferenceManager: PreferenceManager

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private var customerId: Int = 0
    private var personId: Int = 0
    private var customerEmail: String? = null
    private var customerMobile: String? = null

    private var beneId: Int = 0
    private var benePersonId: Int = 0

    private var orderType: Int = 0

    private var beneBankId: Int = 0
    private var beneBankName: String? = null

    private var beneBranchId: Int = 0
    private var beneBranchName: String? = null

    private var beneWalletId: Int = 0
    private var beneWalletName: String? = null

    private var beneAccountName: String? = null
    private var beneAccountNo: String? = null

    private var beneMobile: String? = null


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
        try {
            personId = preferenceManager.loadData("personId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            customerId = preferenceManager.loadData("customerId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            customerEmail = preferenceManager.loadData("customerEmail").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }
        try {
            customerMobile = preferenceManager.loadData("customerMobile").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        when (orderType) {
            3 -> {
                binding.accountTxt.text = "Bank Account"
                binding.nameToolbar.text="Choose Bank"
            }
            5 -> {
                binding.accountTxt.text = "Bank Account"
                binding.nameToolbar.text="Choose Bank"
            }
            1 -> {
                binding.accountTxt.text = "Wallet Account"
                binding.nameToolbar.text="Choose Wallet"
            }
        }

        if (orderType == 1) {
            val getBankItem = GetBankItem(
                benePersonId = benePersonId,
                accountType = orderType,
                walletId = beneWalletId,
                bankId = beneBankId
            )
            bankViewModel.getBank(getBankItem)
        } else {
            val getBankItem = GetBankItem(
                benePersonId = benePersonId,
                accountType = 2,
                walletId = beneWalletId,
                bankId = beneBankId
            )
            bankViewModel.getBank(getBankItem)
        }

        observeGetBankResult()

        binding.btnBank.setOnClickListener {
            saveBankAndWalletBottomSheet.itemSelectedListener = this
            saveBankAndWalletBottomSheet.setOrderType(
                orderType,
                beneBankId,
                beneBankName,
                beneBranchId,
                beneBranchName,
                beneWalletId,
                beneWalletName,
                beneId,
                benePersonId,
                beneAccountName,
                beneAccountNo,
                beneMobile
            )
            saveBankAndWalletBottomSheet.show(
                childFragmentManager,
                saveBankAndWalletBottomSheet.tag
            )
        }

        return bottomSheet
    }

    fun setOrderType(
        orderType: Int,
        beneBankId: Int,
        beneBankName: String?,
        beneBranchId: Int,
        beneBranchName: String?,
        beneWalletId: Int,
        beneWalletName: String?,
        beneId: Int,
        benePersonId: Int,
        beneAccountName: String?,
        beneAccountNo: String?,
        beneMobile: String?,
    ) {
        this.orderType = orderType
        this.beneBankId = beneBankId
        this.beneBankName = beneBankName
        this.beneBranchId = beneBranchId
        this.beneBranchName = beneBranchName
        this.beneWalletId = beneWalletId
        this.beneWalletName = beneWalletName
        this.beneId = beneId
        this.benePersonId = benePersonId
        this.beneAccountName = beneAccountName
        this.beneAccountNo = beneAccountNo
        this.beneMobile = beneMobile
    }

    private fun observeGetBankResult() {
        bankViewModel.getBankResult.observe(this) { result ->
            try {
                if (result!!.getBankData != null) {
                    binding.bankRecyclerView.layoutManager =
                        LinearLayoutManager(requireActivity())
                    bankAdapter = BankAdapter(
                        selectedItem = { selectedItem: GetBankData ->
                            bankItem(selectedItem)
                            binding.bankSearch.setQuery("", false)
                        }
                    )
                    binding.bankRecyclerView.adapter = bankAdapter
                    bankAdapter.setList(result.getBankData as List<GetBankData>)
                    bankAdapter.notifyDataSetChanged()

                    binding.bankSearch.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            bankAdapter.filter(newText.orEmpty())
                            return true
                        }
                    })

                    binding.bankRecyclerView.addOnScrollListener(
                        object : RecyclerView.OnScrollListener() {
                            override fun onScrolled(
                                recyclerView: RecyclerView,
                                dx: Int,
                                dy: Int
                            ) {
                                super.onScrolled(
                                    recyclerView,
                                    dx,
                                    dy
                                )
                                if (dy > 10 && binding.btnBank.isExtended) {
                                    binding.btnBank.shrink()
                                }
                                if (dy < -10 && !binding.btnBank.isExtended) {
                                    binding.btnBank.extend()
                                }
                                if (!recyclerView.canScrollVertically(
                                        -1
                                    )
                                ) {
                                    binding.btnBank.extend()
                                }
                            }
                        })

                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun bankItem(selectedItem: GetBankData) {
        beneBankId = selectedItem.bankId!!
        beneBankName = selectedItem.bankName!!

        beneBranchId = selectedItem.branchId!!
        beneBranchName = selectedItem.branchName!!

        beneWalletId = selectedItem.walletId!!
        try {
            beneWalletName = selectedItem.walletName!!
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        beneAccountName = selectedItem.accountName.toString()
        beneAccountNo = selectedItem.accountNo.toString()

        itemSelectedListener?.onBankAndWalletItemSelected(selectedItem)
        dismiss()
    }

    override fun onSaveBankAndWalletItemSelected(selectedItem: String) {
        if (orderType == 1) {
            val getBankItem = GetBankItem(
                benePersonId = benePersonId,
                accountType = orderType,
                walletId = beneWalletId,
                bankId = beneBankId
            )
            bankViewModel.getBank(getBankItem)
        } else {
            val getBankItem = GetBankItem(
                benePersonId = benePersonId,
                accountType = 2,
                walletId = beneWalletId,
                bankId = beneBankId
            )
            bankViewModel.getBank(getBankItem)
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

    override fun onStart() {
        super.onStart()
        chooseBankBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}