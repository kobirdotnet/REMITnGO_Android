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
import com.bsel.remitngo.adapter.DocumentAdapter
import com.bsel.remitngo.databinding.DocumentLayoutBinding
import com.bsel.remitngo.interfaceses.OnDocumentItemSelectedListener
import com.bsel.remitngo.model.Document
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DocumentBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnDocumentItemSelectedListener? = null

    private lateinit var documentBehavior: BottomSheetBehavior<*>

    private lateinit var binding: DocumentLayoutBinding

    private lateinit var documentAdapter: DocumentAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.document_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        documentBehavior = BottomSheetBehavior.from(view.parent as View)
        documentBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        documentBehavior.addBottomSheetCallback(object :
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

        val documents = arrayOf(
            Document("nid number"),
            Document("civil number"),
            Document("passport number")
        )

        binding.documentRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        documentAdapter = DocumentAdapter(
            selectedItem = { selectedItem: Document ->
                document(selectedItem)
                binding.documentSearch.setQuery("", false)
            }
        )
        binding.documentRecyclerView.adapter = documentAdapter
        documentAdapter.setList(documents.asList())
        documentAdapter.notifyDataSetChanged()

        binding.documentSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                documentAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun document(selectedItem: Document) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onDocumentItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        documentBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}