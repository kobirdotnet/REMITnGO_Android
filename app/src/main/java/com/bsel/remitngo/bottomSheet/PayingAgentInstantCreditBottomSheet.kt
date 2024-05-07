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
import com.bsel.remitngo.adapter.PayingAgentInstantCreditAdapter
import com.bsel.remitngo.data.interfaceses.OnCalculationSelectedListener
import com.bsel.remitngo.data.model.paying_agent.PayingAgentData
import com.bsel.remitngo.data.model.paying_agent.PayingAgentItem
import com.bsel.remitngo.databinding.PayingAgentInstandCreditBankNameLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.main.CalculationViewModel
import com.bsel.remitngo.presentation.ui.main.CalculationViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class PayingAgentInstantCreditBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var calculationViewModelFactory: CalculationViewModelFactory
    private lateinit var calculationViewModel: CalculationViewModel

    var itemSelectedListener: OnCalculationSelectedListener? = null

    private lateinit var payingAgentNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: PayingAgentInstandCreditBankNameLayoutBinding

    private lateinit var payingAgentInstantCreditAdapter: PayingAgentInstantCreditAdapter

    private lateinit var deviceId: String

    private var orderType: Int = 0
    private var beneAmount: Double = 0.0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.paying_agent_instand_credit_bank_name_layout, null)
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

        deviceId = getDeviceId(requireContext())
        val payingAgentItem = PayingAgentItem(
            deviceId = deviceId,
            fromCountryId = 4,
            toCountryId = 1,
            orderTypeId = orderType,
            amount = beneAmount.toInt()
        )
        calculationViewModel.payingAgent(payingAgentItem)

        observePayingAgentResult()

        return bottomSheet
    }

    fun setSelectedOrderType(orderType: Int, beneAmount: String) {
        this.orderType = orderType
        this.beneAmount = beneAmount.toDouble()
    }

    private fun observePayingAgentResult() {
        calculationViewModel.payingAgentResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    binding.payingAgentRecyclerView.layoutManager =
                        LinearLayoutManager(requireActivity())
                    payingAgentInstantCreditAdapter = PayingAgentInstantCreditAdapter(
                        selectedItem = { selectedItem: PayingAgentData ->
                            payingAgentItem(selectedItem)
                            binding.payingAgentSearch.setQuery("", false)
                        }
                    )
                    binding.payingAgentRecyclerView.adapter = payingAgentInstantCreditAdapter
                    payingAgentInstantCreditAdapter.setList(result.data as List<PayingAgentData>)
                    payingAgentInstantCreditAdapter.notifyDataSetChanged()

                    binding.payingAgentSearch.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            payingAgentInstantCreditAdapter.filter(newText.orEmpty())
                            return true
                        }
                    })
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun payingAgentItem(selectedItem: PayingAgentData) {
        itemSelectedListener?.onPayingAgentInstantCreditItemSelected(selectedItem)
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