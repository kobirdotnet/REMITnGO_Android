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
import com.bsel.remitngo.adapter.ReasonNameAdapter
import com.bsel.remitngo.databinding.ReasonNameLayoutBinding
import com.bsel.remitngo.interfaceses.OnRecipientItemSelectedListener
import com.bsel.remitngo.model.ReasonItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReasonBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnRecipientItemSelectedListener? = null

    private lateinit var reasonNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: ReasonNameLayoutBinding

    private lateinit var reasonNameAdapter: ReasonNameAdapter

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

        val reasonItems = arrayOf(
            ReasonItem("Family Support"),
            ReasonItem("Education"),
            ReasonItem("Donation")
        )

        binding.reasonRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        reasonNameAdapter = ReasonNameAdapter(
            selectedItem = { selectedItem: ReasonItem ->
                reasonItem(selectedItem)
                binding.reasonSearch.setQuery("", false)
            }
        )
        binding.reasonRecyclerView.adapter = reasonNameAdapter
        reasonNameAdapter.setList(reasonItems.asList())
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

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun reasonItem(selectedItem: ReasonItem) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onReasonItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        reasonNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}