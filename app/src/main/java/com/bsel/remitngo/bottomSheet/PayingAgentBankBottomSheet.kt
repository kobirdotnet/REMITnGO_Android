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
import com.bsel.remitngo.adapter.PayingAgentBankNameAdapter
import com.bsel.remitngo.data.interfaceses.OnCalculationSelectedListener
import com.bsel.remitngo.data.model.paying_agent.PayingAgentData
import com.bsel.remitngo.data.model.paying_agent.PayingAgentItem
import com.bsel.remitngo.databinding.PayingAgentBankNameLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.main.CalculationViewModel
import com.bsel.remitngo.presentation.ui.main.CalculationViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class PayingAgentBankBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var calculationViewModelFactory: CalculationViewModelFactory
    private lateinit var calculationViewModel: CalculationViewModel

    var itemSelectedListener: OnCalculationSelectedListener? = null

    private lateinit var payingAgentNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: PayingAgentBankNameLayoutBinding

    private lateinit var payingAgentBankNameAdapter: PayingAgentBankNameAdapter

    private lateinit var deviceId: String
    private var selectedOrderType: String? = null
    private var selectedAmount: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.paying_agent_bank_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        payingAgentNameBehavior = BottomSheetBehavior.from(view.parent as View)
        payingAgentNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        payingAgentNameBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createCalculationSubComponent().inject(this)

        calculationViewModel =
            ViewModelProvider(this, calculationViewModelFactory)[CalculationViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }

        observePayingAgentResult()

        deviceId = getDeviceId(requireContext())
        val payingAgentItem = PayingAgentItem(
            deviceId = deviceId,
            fromCountryId = 4,
            toCountryId = 1,
            orderTypeId = selectedOrderType!!.toInt(),
            amount = selectedAmount!!.toInt()
        )
        calculationViewModel.payingAgent(payingAgentItem)

        return bottomSheet
    }

    fun setSelectedOrderType(orderType: String,amount:String) {
        selectedOrderType = orderType
        selectedAmount = amount
    }

    private fun observePayingAgentResult() {
        calculationViewModel.payingAgentResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.payingAgentRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                payingAgentBankNameAdapter = PayingAgentBankNameAdapter(
                    selectedItem = { selectedItem: PayingAgentData ->
                        payingAgentItem(selectedItem)
                        binding.payingAgentSearch.setQuery("", false)
                    }
                )
                binding.payingAgentRecyclerView.adapter = payingAgentBankNameAdapter
                payingAgentBankNameAdapter.setList(result.data as List<PayingAgentData>)
                payingAgentBankNameAdapter.notifyDataSetChanged()

                binding.payingAgentSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        payingAgentBankNameAdapter.filter(newText.orEmpty())
                        return true
                    }
                })

            } else {
                Log.i("info", "paying agent failed")
            }
        }
    }

    private fun payingAgentItem(selectedItem: PayingAgentData) {
        itemSelectedListener?.onPayingAgentBankItemSelected(selectedItem)
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
        payingAgentNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}