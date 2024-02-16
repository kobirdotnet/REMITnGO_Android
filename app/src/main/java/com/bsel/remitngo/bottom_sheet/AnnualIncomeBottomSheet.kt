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
import com.bsel.remitngo.adapter.AnnualIncomeAdapter
import com.bsel.remitngo.databinding.AnnualIncomeLayoutBinding
import com.bsel.remitngo.interfaceses.OnPersonalInfoItemSelectedListener
import com.bsel.remitngo.model.AnnualIncome
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AnnualIncomeBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnPersonalInfoItemSelectedListener? = null

    private lateinit var annualIncomeBehavior: BottomSheetBehavior<*>

    private lateinit var binding: AnnualIncomeLayoutBinding

    private lateinit var annualIncomeAdapter: AnnualIncomeAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.annual_income_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        annualIncomeBehavior = BottomSheetBehavior.from(view.parent as View)
        annualIncomeBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        annualIncomeBehavior.addBottomSheetCallback(object :
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

        val annualIncomes = arrayOf(
            AnnualIncome("AnnualIncome"),
            AnnualIncome("AnnualIncome"),
            AnnualIncome("AnnualIncome")
        )

        binding.annualIncomeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        annualIncomeAdapter = AnnualIncomeAdapter(
            selectedItem = { selectedItem: AnnualIncome ->
                annualIncome(selectedItem)
                binding.annualIncomeSearch.setQuery("", false)
            }
        )
        binding.annualIncomeRecyclerView.adapter = annualIncomeAdapter
        annualIncomeAdapter.setList(annualIncomes.asList())
        annualIncomeAdapter.notifyDataSetChanged()

        binding.annualIncomeSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                annualIncomeAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun annualIncome(selectedItem: AnnualIncome) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onAnnualIncomeItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        annualIncomeBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}