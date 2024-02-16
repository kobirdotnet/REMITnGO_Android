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
import com.bsel.remitngo.adapter.NationalityAdapter
import com.bsel.remitngo.databinding.NationalityLayoutBinding
import com.bsel.remitngo.interfaceses.OnPersonalInfoItemSelectedListener
import com.bsel.remitngo.model.Nationality
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NationalityBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnPersonalInfoItemSelectedListener? = null

    private lateinit var nationalityBehavior: BottomSheetBehavior<*>

    private lateinit var binding: NationalityLayoutBinding

    private lateinit var nationalityAdapter: NationalityAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.nationality_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        nationalityBehavior = BottomSheetBehavior.from(view.parent as View)
        nationalityBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        nationalityBehavior.addBottomSheetCallback(object :
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

        val nationalitys = arrayOf(
            Nationality("Nationality"),
            Nationality("Nationality"),
            Nationality("Nationality")
        )

        binding.nationalityRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        nationalityAdapter = NationalityAdapter(
            selectedItem = { selectedItem: Nationality ->
                Nationality(selectedItem)
                binding.nationalitySearch.setQuery("", false)
            }
        )
        binding.nationalityRecyclerView.adapter = nationalityAdapter
        nationalityAdapter.setList(nationalitys.asList())
        nationalityAdapter.notifyDataSetChanged()

        binding.nationalitySearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                nationalityAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun Nationality(selectedItem: Nationality) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onNationalityItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        nationalityBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}