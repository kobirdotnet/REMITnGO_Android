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
import com.bsel.remitngo.adapter.CategoryAdapter
import com.bsel.remitngo.databinding.CategoryLayoutBinding
import com.bsel.remitngo.interfaceses.OnDocumentItemSelectedListener
import com.bsel.remitngo.model.Category
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnDocumentItemSelectedListener? = null

    private lateinit var categoryBehavior: BottomSheetBehavior<*>

    private lateinit var binding: CategoryLayoutBinding

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.category_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        categoryBehavior = BottomSheetBehavior.from(view.parent as View)
        categoryBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        categoryBehavior.addBottomSheetCallback(object :
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

        val categories = arrayOf(
            Category("nid number"),
            Category("civil number"),
            Category("passport number")
        )

        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        categoryAdapter = CategoryAdapter(
            selectedItem = { selectedItem: Category ->
                category(selectedItem)
                binding.categorySearch.setQuery("", false)
            }
        )
        binding.categoryRecyclerView.adapter = categoryAdapter
        categoryAdapter.setList(categories.asList())
        categoryAdapter.notifyDataSetChanged()

        binding.categorySearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                categoryAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun category(selectedItem: Category) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onCategoryItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        categoryBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}