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
import com.bsel.remitngo.adapter.RelationNameAdapter
import com.bsel.remitngo.databinding.RelationNameLayoutBinding
import com.bsel.remitngo.interfaceses.OnRecipientItemSelectedListener
import com.bsel.remitngo.model.RelationItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RelationBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnRecipientItemSelectedListener? = null

    private lateinit var relationNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: RelationNameLayoutBinding

    private lateinit var relationNameAdapter: RelationNameAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.relation_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        relationNameBehavior = BottomSheetBehavior.from(view.parent as View)
        relationNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        relationNameBehavior.addBottomSheetCallback(object :
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

        val relationItems = arrayOf(
            RelationItem("Vendor"),
            RelationItem("Brother"),
            RelationItem("Sister"),
            RelationItem("Father"),
            RelationItem("Mother")
        )

        binding.relationRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        relationNameAdapter = RelationNameAdapter(
            selectedItem = { selectedItem: RelationItem ->
                relationItem(selectedItem)
                binding.relationSearch.setQuery("", false)
            }
        )
        binding.relationRecyclerView.adapter = relationNameAdapter
        relationNameAdapter.setList(relationItems.asList())
        relationNameAdapter.notifyDataSetChanged()

        binding.relationSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                relationNameAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun relationItem(selectedItem: RelationItem) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onRelationItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        relationNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}