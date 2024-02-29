package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeData
import com.bsel.remitngo.databinding.ItemDocumentTypeBinding

class DocumentTypeAdapter(
    private val selectedItem: (DocumentTypeData) -> Unit
) : RecyclerView.Adapter<DocumentTypeViewHolder>() {

    private val documentTypeList = ArrayList<DocumentTypeData>()
    private var filteredDocumentTypeList = ArrayList<DocumentTypeData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentTypeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDocumentTypeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_document_type, parent, false)
        return DocumentTypeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredDocumentTypeList.size
    }

    override fun onBindViewHolder(
        holder: DocumentTypeViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredDocumentTypeList[position], selectedItem)
    }

    fun setList(document: List<DocumentTypeData>) {
        documentTypeList.clear()
        documentTypeList.addAll(document)
        filter("")
    }

    fun filter(query: String) {
        filteredDocumentTypeList.clear()
        for (document in documentTypeList) {
            if (document.name!!.contains(query, ignoreCase = true)) {
                filteredDocumentTypeList.add(document)
            }
        }
        notifyDataSetChanged()
    }

}

class DocumentTypeViewHolder(val binding: ItemDocumentTypeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        document: DocumentTypeData,
        selectedItem: (DocumentTypeData) -> Unit
    ) {
        binding.documentTypeName.text = document.name
        binding.itemDocumentTypeLayout.setOnClickListener {
            selectedItem(document)
        }
    }
}