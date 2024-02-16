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
import com.bsel.remitngo.adapter.BankBranchNameAdapter
import com.bsel.remitngo.databinding.BranchNameLayoutBinding
import com.bsel.remitngo.interfaceses.OnBankItemSelectedListener
import com.bsel.remitngo.model.BankBranchItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BankBranchBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnBankItemSelectedListener? = null

    private lateinit var bankBranchNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: BranchNameLayoutBinding

    private lateinit var bankBranchNameAdapter: BankBranchNameAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.branch_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        bankBranchNameBehavior = BottomSheetBehavior.from(view.parent as View)
        bankBranchNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        bankBranchNameBehavior.addBottomSheetCallback(object :
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

        val bankBranchItems = arrayOf(
            BankBranchItem("Dhaka branch"),
            BankBranchItem("Chatragram branch"),
            BankBranchItem("Sylet branch"),
            BankBranchItem("Barishal branch"),
            BankBranchItem("Rajshahi branch"),
            BankBranchItem("Mymensingh branch"),
            BankBranchItem("Joshur branch"),
            BankBranchItem("Magura branch")
        )

        binding.bankBranchRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        bankBranchNameAdapter = BankBranchNameAdapter(
            selectedItem = { selectedItem: BankBranchItem ->
                bankBranchItem(selectedItem)
                binding.bankBranchSearch.setQuery("", false)
            }
        )
        binding.bankBranchRecyclerView.adapter = bankBranchNameAdapter
        bankBranchNameAdapter.setList(bankBranchItems.asList())
        bankBranchNameAdapter.notifyDataSetChanged()

        binding.bankBranchSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                bankBranchNameAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun bankBranchItem(selectedItem: BankBranchItem) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onBankBranchItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        bankBranchNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}