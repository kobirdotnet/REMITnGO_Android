package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemDocumentBinding
import com.bsel.remitngo.model.Document

class DocumentAdapter(
    private val selectedItem: (Document) -> Unit
) : RecyclerView.Adapter<DocumentViewHolder>() {

    private val documentList = ArrayList<Document>()
    private var filteredDocumentList = ArrayList<Document>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDocumentBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_document, parent, false)
        return DocumentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredDocumentList.size
    }

    override fun onBindViewHolder(
        holder: DocumentViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredDocumentList[position], selectedItem)
    }

    fun setList(document: List<Document>) {
        documentList.clear()
        documentList.addAll(document)
        filter("")
    }

    fun filter(query: String) {
        filteredDocumentList.clear()
        for (document in documentList) {
            if (document.documentName!!.contains(query, ignoreCase = true)) {
                filteredDocumentList.add(document)
            }
        }
        notifyDataSetChanged()
    }

}

class DocumentViewHolder(val binding: ItemDocumentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        document: Document,
        selectedItem: (Document) -> Unit
    ) {
        binding.documentName.text = document.documentName
        binding.itemDocumentLayout.setOnClickListener {
            selectedItem(document)
        }
    }
}