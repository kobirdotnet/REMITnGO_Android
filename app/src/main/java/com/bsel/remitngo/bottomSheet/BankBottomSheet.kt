package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
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
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.BankNameAdapter
import com.bsel.remitngo.data.model.bank.BankData
import com.bsel.remitngo.data.model.bank.BankItem
import com.bsel.remitngo.databinding.BankNameLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnBankSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.bank.BankViewModel
import com.bsel.remitngo.presentation.ui.bank.BankViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class BankBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var bankViewModelFactory: BankViewModelFactory
    private lateinit var bankViewModel: BankViewModel

    var itemSelectedListener: OnBankSelectedListener? = null

    private lateinit var bankNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: BankNameLayoutBinding

    private lateinit var bankNameAdapter: BankNameAdapter

    private lateinit var deviceId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.bank_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        bankNameBehavior = BottomSheetBehavior.from(view.parent as View)
        bankNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        bankNameBehavior.addBottomSheetCallback(object :
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

        val bankItem = BankItem(
            deviceId = deviceId,
            dropdownId = 5,
            param1 = 1,
            param2 = 0
        )
        bankViewModel.bank(bankItem)
        observeBankResult()

        return bottomSheet
    }

    private fun observeBankResult() {
        bankViewModel.bankResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.bankRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                bankNameAdapter = BankNameAdapter(
                    selectedItem = { selectedItem: BankData ->
                        bankItem(selectedItem)
                        binding.bankSearch.setQuery("", false)
                    }
                )
                binding.bankRecyclerView.adapter = bankNameAdapter
                bankNameAdapter.setList(result.data as List<BankData>)
                bankNameAdapter.notifyDataSetChanged()

                binding.bankSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        bankNameAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
            }
        }
    }

    private fun bankItem(selectedItem: BankData) {
        itemSelectedListener?.onBankItemSelected(selectedItem)
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
        bankNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}