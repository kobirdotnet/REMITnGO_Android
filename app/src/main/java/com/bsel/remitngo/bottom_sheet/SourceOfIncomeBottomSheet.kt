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
import com.bsel.remitngo.adapter.SourceOfIncomeAdapter
import com.bsel.remitngo.databinding.SourceOfIncomeLayoutBinding
import com.bsel.remitngo.interfaceses.OnPersonalInfoItemSelectedListener
import com.bsel.remitngo.model.SourceOfIncome
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SourceOfIncomeBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnPersonalInfoItemSelectedListener? = null

    private lateinit var sourceOfIncomeBehavior: BottomSheetBehavior<*>

    private lateinit var binding: SourceOfIncomeLayoutBinding

    private lateinit var sourceOfIncomeAdapter: SourceOfIncomeAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.source_of_income_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        sourceOfIncomeBehavior = BottomSheetBehavior.from(view.parent as View)
        sourceOfIncomeBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        sourceOfIncomeBehavior.addBottomSheetCallback(object :
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

        val sourceOfIncomes = arrayOf(
            SourceOfIncome("SourceOfIncome"),
            SourceOfIncome("SourceOfIncome"),
            SourceOfIncome("SourceOfIncome")
        )

        binding.sourceOfIncomeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        sourceOfIncomeAdapter = SourceOfIncomeAdapter(
            selectedItem = { selectedItem: SourceOfIncome ->
                sourceOfIncome(selectedItem)
                binding.sourceOfIncomeSearch.setQuery("", false)
            }
        )
        binding.sourceOfIncomeRecyclerView.adapter = sourceOfIncomeAdapter
        sourceOfIncomeAdapter.setList(sourceOfIncomes.asList())
        sourceOfIncomeAdapter.notifyDataSetChanged()

        binding.sourceOfIncomeSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                sourceOfIncomeAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun sourceOfIncome(selectedItem: SourceOfIncome) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onSourceOfIncomeItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        sourceOfIncomeBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}