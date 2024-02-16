package com.bsel.remitngo.ui.document

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.DocumentsAdapter
import com.bsel.remitngo.databinding.FragmentDocumentsBinding
import com.bsel.remitngo.model.DocumentsItem

class DocumentsFragment : Fragment() {

    private lateinit var binding: FragmentDocumentsBinding

    private lateinit var documentsAdapter: DocumentsAdapter

    private lateinit var documentsItems: List<DocumentsItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_documents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDocumentsBinding.bind(view)

        binding.btnUploadDocuments.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_documents_to_nav_upload_documents
            )
        }

        documentsItems = arrayOf(
            DocumentsItem("Box"),
            DocumentsItem("table"),
            DocumentsItem("file"),
            DocumentsItem("Box"),
            DocumentsItem("table"),
            DocumentsItem("file"),
            DocumentsItem("Box"),
            DocumentsItem("table"),
            DocumentsItem("file"),
            DocumentsItem("Box"),
            DocumentsItem("table"),
            DocumentsItem("file"),
            DocumentsItem("Box"),
            DocumentsItem("table"),
            DocumentsItem("file"),
            DocumentsItem("Box"),
            DocumentsItem("table"),
            DocumentsItem("file")
        ).toList()

        binding.documentsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        documentsAdapter = DocumentsAdapter(
            selectedItem = { selectedItem: DocumentsItem ->
                documentsItem(selectedItem)
                binding.documentsSearch.setQuery("", false)
            }
        )
        binding.documentsRecyclerView.adapter = documentsAdapter
        documentsAdapter.setList(documentsItems)
        documentsAdapter.notifyDataSetChanged()

        binding.documentsSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                documentsAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.documentsRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int
                ) {
                    super.onScrolled(
                        recyclerView,
                        dx,
                        dy
                    )
                    if (dy > 10 && binding.btnUploadDocuments.isExtended) {
                        binding.btnUploadDocuments.shrink()
                    }
                    if (dy < -10 && !binding.btnUploadDocuments.isExtended) {
                        binding.btnUploadDocuments.extend()
                    }
                    if (!recyclerView.canScrollVertically(
                            -1
                        )
                    ) {
                        binding.btnUploadDocuments.extend()
                    }
                }
            })

    }

    private fun documentsItem(selectedItem: DocumentsItem) {
        Log.i("info", "selectedItem: $selectedItem")
    }

}