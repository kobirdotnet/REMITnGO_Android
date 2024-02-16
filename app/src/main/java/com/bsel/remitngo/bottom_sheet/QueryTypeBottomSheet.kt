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
import com.bsel.remitngo.adapter.QueryTypeAdapter
import com.bsel.remitngo.databinding.QueryTypeLayoutBinding
import com.bsel.remitngo.interfaceses.OnQueryTypeItemSelectedListener
import com.bsel.remitngo.model.QueryType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class QueryTypeBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnQueryTypeItemSelectedListener? = null

    private lateinit var queryTypeBehavior: BottomSheetBehavior<*>

    private lateinit var binding: QueryTypeLayoutBinding

    private lateinit var queryTypeAdapter: QueryTypeAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.query_type_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        queryTypeBehavior = BottomSheetBehavior.from(view.parent as View)
        queryTypeBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        queryTypeBehavior.addBottomSheetCallback(object :
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

        val queryTypes = arrayOf(
            QueryType("Transaction status"),
            QueryType("Refund"),
            QueryType("Other")
        )

        binding.queryTypeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        queryTypeAdapter = QueryTypeAdapter(
            selectedItem = { selectedItem: QueryType ->
                queryType(selectedItem)
                binding.queryTypeSearch.setQuery("", false)
            }
        )
        binding.queryTypeRecyclerView.adapter = queryTypeAdapter
        queryTypeAdapter.setList(queryTypes.asList())
        queryTypeAdapter.notifyDataSetChanged()

        binding.queryTypeSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                queryTypeAdapter.filter(newText.orEmpty())
                return true
            }
        })
        
        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun queryType(selectedItem: QueryType) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onQueryTypeItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        queryTypeBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}