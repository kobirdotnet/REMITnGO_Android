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
import com.bsel.remitngo.adapter.DivisionNameAdapter
import com.bsel.remitngo.databinding.DivisionNameLayoutBinding
import com.bsel.remitngo.interfaceses.OnBankItemSelectedListener
import com.bsel.remitngo.model.DivisionItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DivisionBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnBankItemSelectedListener? = null

    private lateinit var divisionNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: DivisionNameLayoutBinding

    private lateinit var divisionNameAdapter: DivisionNameAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.division_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        divisionNameBehavior = BottomSheetBehavior.from(view.parent as View)
        divisionNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        divisionNameBehavior.addBottomSheetCallback(object :
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

        val divisionItems = arrayOf(
            DivisionItem("Dhaka"),
            DivisionItem("Chatragram"),
            DivisionItem("Sylet"),
            DivisionItem("Barishal"),
            DivisionItem("Rajshahi"),
            DivisionItem("Mymensingh"),
            DivisionItem("Joshur"),
            DivisionItem("Magura")
        )

        binding.divisionRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        divisionNameAdapter = DivisionNameAdapter(
            selectedItem = { selectedItem: DivisionItem ->
                divisionItem(selectedItem)
                binding.divisionSearch.setQuery("", false)
            }
        )
        binding.divisionRecyclerView.adapter = divisionNameAdapter
        divisionNameAdapter.setList(divisionItems.asList())
        divisionNameAdapter.notifyDataSetChanged()

        binding.divisionSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                divisionNameAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun divisionItem(selectedItem: DivisionItem) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onDivisionItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        divisionNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}