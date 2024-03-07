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
import com.bsel.remitngo.adapter.CancelReasonAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonData
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonItem
import com.bsel.remitngo.databinding.CancelReasonNameLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnCancelReasonItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.cancel_request.CancelRequestViewModel
import com.bsel.remitngo.presentation.ui.cancel_request.CancelRequestViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class CancelReasonBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var cancelRequestViewModelFactory: CancelRequestViewModelFactory
    private lateinit var cancelRequestViewModel: CancelRequestViewModel

    var itemSelectedListener: OnCancelReasonItemSelectedListener? = null

    private lateinit var cancelReasonNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: CancelReasonNameLayoutBinding

    private lateinit var cancelReasonAdapter: CancelReasonAdapter

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.cancel_reason_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        cancelReasonNameBehavior = BottomSheetBehavior.from(view.parent as View)
        cancelReasonNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        cancelReasonNameBehavior.addBottomSheetCallback(object :
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

        binding.cancelButton.setOnClickListener { dismiss() }

        (requireActivity().application as Injector).createCancelRequestSubComponent().inject(this)

        cancelRequestViewModel =
            ViewModelProvider(this, cancelRequestViewModelFactory)[CancelRequestViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

        val cancelReasonItem = CancelReasonItem(
            deviceId = deviceId
        )
        cancelRequestViewModel.cancelReason(cancelReasonItem)
        observeCancelReasonResult()

        return bottomSheet
    }

    private fun observeCancelReasonResult() {
        cancelRequestViewModel.cancelReasonResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.reasonRecyclerView.layoutManager =
                    LinearLayoutManager(requireActivity())
                cancelReasonAdapter = CancelReasonAdapter(
                    selectedItem = { selectedItem: CancelReasonData ->
                        cancelReason(selectedItem)
                        binding.reasonSearch.setQuery("", false)
                    }
                )
                binding.reasonRecyclerView.adapter = cancelReasonAdapter
                cancelReasonAdapter.setList(result.data as List<CancelReasonData>)
                cancelReasonAdapter.notifyDataSetChanged()

                binding.reasonSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        cancelReasonAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
                Log.i("info", "get cancel Reason successful: $result")
            } else {
                Log.i("info", "get cancel Reason failed")
            }
        }
    }

    private fun cancelReason(selectedItem: CancelReasonData) {
        itemSelectedListener?.onCancelReasonItemSelected(selectedItem)
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
        cancelReasonNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}