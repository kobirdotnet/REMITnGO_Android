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
import com.bsel.remitngo.adapter.ReasonNameAdapter
import com.bsel.remitngo.data.model.reason.ReasonData
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.databinding.ReasonNameLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnBeneficiarySelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModel
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class PurposeOfTransferBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var beneficiaryViewModelFactory: BeneficiaryViewModelFactory
    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    var itemSelectedListener: OnBeneficiarySelectedListener? = null

    private lateinit var reasonNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: ReasonNameLayoutBinding

    private lateinit var reasonNameAdapter: ReasonNameAdapter

    private lateinit var deviceId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.reason_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        reasonNameBehavior = BottomSheetBehavior.from(view.parent as View)
        reasonNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        reasonNameBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createBeneficiarySubComponent().inject(this)

        beneficiaryViewModel =
            ViewModelProvider(this, beneficiaryViewModelFactory)[BeneficiaryViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }

        deviceId = getDeviceId(requireContext())

        val reasonItem = ReasonItem(
            deviceId = deviceId,
            dropdownId = 27,
            param1 = 0,
            param2 = 0
        )
        beneficiaryViewModel.reason(reasonItem)
        observeReasonResult()

        return bottomSheet
    }

    private fun observeReasonResult() {
        beneficiaryViewModel.reasonResult.observe(this) { result ->
            if (result!!.data != null) {
                for (data in result.data!!) {
                    binding.reasonRecyclerView.layoutManager =
                        LinearLayoutManager(requireActivity())
                    reasonNameAdapter = ReasonNameAdapter(
                        selectedItem = { selectedItem: ReasonData ->
                            reasonItem(selectedItem)
                            binding.reasonSearch.setQuery("", false)
                        }
                    )
                    binding.reasonRecyclerView.adapter = reasonNameAdapter
                    reasonNameAdapter.setList(result.data as List<ReasonData>)
                    reasonNameAdapter.notifyDataSetChanged()

                    binding.reasonSearch.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            reasonNameAdapter.filter(newText.orEmpty())
                            return true
                        }
                    })
                }

            }
        }
    }

    private fun reasonItem(selectedItem: ReasonData) {
        itemSelectedListener?.onPurposeOfTransferItemSelected(selectedItem)
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
        reasonNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}