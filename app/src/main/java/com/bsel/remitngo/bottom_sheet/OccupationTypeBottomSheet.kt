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
import com.bsel.remitngo.adapter.OccupationTypeAdapter
import com.bsel.remitngo.databinding.OccupationTypeLayoutBinding
import com.bsel.remitngo.interfaceses.OnPersonalInfoItemSelectedListener
import com.bsel.remitngo.model.OccupationType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OccupationTypeBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnPersonalInfoItemSelectedListener? = null

    private lateinit var occupationTypeBehavior: BottomSheetBehavior<*>

    private lateinit var binding: OccupationTypeLayoutBinding

    private lateinit var occupationTypeAdapter: OccupationTypeAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.occupation_type_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        occupationTypeBehavior = BottomSheetBehavior.from(view.parent as View)
        occupationTypeBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        occupationTypeBehavior.addBottomSheetCallback(object :
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

        val occupationTypes = arrayOf(
            OccupationType("OccupationType"),
            OccupationType("OccupationType"),
            OccupationType("OccupationType")
        )

        binding.occupationTypeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        occupationTypeAdapter = OccupationTypeAdapter(
            selectedItem = { selectedItem: OccupationType ->
                occupationType(selectedItem)
                binding.occupationTypeSearch.setQuery("", false)
            }
        )
        binding.occupationTypeRecyclerView.adapter = occupationTypeAdapter
        occupationTypeAdapter.setList(occupationTypes.asList())
        occupationTypeAdapter.notifyDataSetChanged()

        binding.occupationTypeSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                occupationTypeAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun occupationType(selectedItem: OccupationType) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onOccupationTypeItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        occupationTypeBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}