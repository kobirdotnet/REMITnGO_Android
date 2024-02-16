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
import com.bsel.remitngo.adapter.OccupationAdapter
import com.bsel.remitngo.databinding.OccupationLayoutBinding
import com.bsel.remitngo.interfaceses.OnPersonalInfoItemSelectedListener
import com.bsel.remitngo.model.Occupation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OccupationBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnPersonalInfoItemSelectedListener? = null

    private lateinit var occupationBehavior: BottomSheetBehavior<*>

    private lateinit var binding: OccupationLayoutBinding

    private lateinit var occupationAdapter: OccupationAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.occupation_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        occupationBehavior = BottomSheetBehavior.from(view.parent as View)
        occupationBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        occupationBehavior.addBottomSheetCallback(object :
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

        val occupations = arrayOf(
            Occupation("Occupation"),
            Occupation("Occupation"),
            Occupation("Occupation")
        )

        binding.occupationRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        occupationAdapter = OccupationAdapter(
            selectedItem = { selectedItem: Occupation ->
                occupation(selectedItem)
                binding.occupationSearch.setQuery("", false)
            }
        )
        binding.occupationRecyclerView.adapter = occupationAdapter
        occupationAdapter.setList(occupations.asList())
        occupationAdapter.notifyDataSetChanged()

        binding.occupationSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                occupationAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun occupation(selectedItem: Occupation) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onOccupationItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        occupationBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}