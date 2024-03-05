package com.bsel.remitngo.bottom_sheet

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.CancelReasonAdapter
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonData
import com.bsel.remitngo.databinding.CancelReasonNameLayoutBinding
import com.bsel.remitngo.interfaceses.OnCancelReasonItemSelectedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CancelReasonBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnCancelReasonItemSelectedListener? = null

    private lateinit var cancelReasonNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: CancelReasonNameLayoutBinding

    private lateinit var cancelReasonAdapter: CancelReasonAdapter

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

        binding.reasonRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        cancelReasonAdapter = CancelReasonAdapter(
            selectedItem = { selectedItem: CancelReasonData ->
                reasonItem(selectedItem)
                binding.reasonSearch.setQuery("", false)
            }
        )
        binding.reasonRecyclerView.adapter = cancelReasonAdapter
//        cancelReasonAdapter.setList(cancelReasonItems.asList())
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

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun reasonItem(selectedItem: CancelReasonData) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onCancelReasonItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        cancelReasonNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}