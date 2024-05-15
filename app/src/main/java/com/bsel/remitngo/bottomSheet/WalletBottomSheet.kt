package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.WalletNameAdapter
import com.bsel.remitngo.data.interfaceses.OnBankSelectedListener
import com.bsel.remitngo.data.model.bank.WalletData
import com.bsel.remitngo.data.model.bank.WalletItem
import com.bsel.remitngo.databinding.WalletNameLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.bank.BankViewModel
import com.bsel.remitngo.presentation.ui.bank.BankViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class WalletBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var bankViewModelFactory: BankViewModelFactory
    private lateinit var bankViewModel: BankViewModel

    var itemSelectedListener: OnBankSelectedListener? = null

    private lateinit var walletNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: WalletNameLayoutBinding

    private lateinit var walletNameAdapter: WalletNameAdapter

    private lateinit var deviceId: String

    private var beneWalletId: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.wallet_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        walletNameBehavior = BottomSheetBehavior.from(view.parent as View)
        walletNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        walletNameBehavior.addBottomSheetCallback(object :
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

        deviceId = getDeviceId(requireContext())

        val walletItem = WalletItem(
            deviceId = deviceId,
            dropdownId = 311,
            param1 = beneWalletId,
            param2 = 0
        )
        bankViewModel.wallet(walletItem)
        observeWalletResult()

        return bottomSheet
    }

    fun setSelectedWallet(beneWalletId: Int) {
        this.beneWalletId = beneWalletId
    }

    private fun observeWalletResult() {
        bankViewModel.walletResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    binding.walletRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                    walletNameAdapter = WalletNameAdapter(
                        selectedItem = { selectedItem: WalletData ->
                            walletItem(selectedItem)
                            binding.walletSearch.setQuery("", false)
                        }
                    )
                    binding.walletRecyclerView.adapter = walletNameAdapter
                    walletNameAdapter.setList(result.data as List<WalletData>)
                    walletNameAdapter.notifyDataSetChanged()

                    binding.walletSearch.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            walletNameAdapter.walletFilter(newText.orEmpty())
                            return true
                        }
                    })
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun walletItem(selectedItem: WalletData) {
        itemSelectedListener?.onWalletItemSelected(selectedItem)
        dismiss()
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

    override fun onStart() {
        super.onStart()
        walletNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}