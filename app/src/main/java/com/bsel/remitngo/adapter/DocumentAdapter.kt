package com.bsel.remitngo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemDocumentsBinding
import com.bsel.remitngo.model.DocumentsItem

class DocumentAdapter(
    private val selectedItem: (DocumentsItem) -> Unit
) : RecyclerView.Adapter<DocumentsViewHolder>() {

    private val documentsList = ArrayList<DocumentsItem>()
    private var filteredDocumentsList = ArrayList<DocumentsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDocumentsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_documents, parent, false)
        return DocumentsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredDocumentsList.size
    }

    override fun onBindViewHolder(holder: DocumentsViewHolder, position: Int) {
        holder.bind(filteredDocumentsList[position], selectedItem)
    }

    fun setList(documentsItem: List<DocumentsItem>) {
        documentsList.clear()
        documentsList.addAll(documentsItem)
        filter("")
    }

    fun filter(query: String) {
        filteredDocumentsList.clear()
        for (documents in documentsList) {
            if (documents.documentsName!!.contains(query, ignoreCase = true)) {
                filteredDocumentsList.add(documents)
            }
        }
        notifyDataSetChanged()
    }

}

class DocumentsViewHolder(val binding: ItemDocumentsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        documentsItem: DocumentsItem,
        selectedItem: (DocumentsItem) -> Unit
    ) {
        binding.documentsName.text = documentsItem.documentsName
        binding.itemDocumentsLayout.setOnClickListener {
            selectedItem(documentsItem)
        }
    }

}