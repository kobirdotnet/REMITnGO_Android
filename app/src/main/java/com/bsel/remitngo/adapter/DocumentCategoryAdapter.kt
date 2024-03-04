package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryData
import com.bsel.remitngo.databinding.ItemDocumentCategoryBinding

class DocumentCategoryAdapter(
    private val selectedItem: (DocumentCategoryData) -> Unit
) : RecyclerView.Adapter<DocumentCategoryViewHolder>() {

    private val documentCategoryList = ArrayList<DocumentCategoryData>()
    private var filteredDocumentCategoryList = ArrayList<DocumentCategoryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDocumentCategoryBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_document_category, parent, false)
        return DocumentCategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredDocumentCategoryList.size
    }

    override fun onBindViewHolder(
        holder: DocumentCategoryViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredDocumentCategoryList[position], selectedItem)
    }

    fun setList(category: List<DocumentCategoryData>) {
        documentCategoryList.clear()
        documentCategoryList.addAll(category)
        filter("")
    }

    fun filter(query: String) {
        filteredDocumentCategoryList.clear()
        for (category in documentCategoryList) {
            if (category.name!!.contains(query, ignoreCase = true)) {
                filteredDocumentCategoryList.add(category)
            }
        }
        notifyDataSetChanged()
    }

}

class DocumentCategoryViewHolder(val binding: ItemDocumentCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        category: DocumentCategoryData,
        selectedItem: (DocumentCategoryData) -> Unit
    ) {
        binding.documentCategoryName.text = category.name
        binding.itemDocumentCategoryLayout.setOnClickListener {
            selectedItem(category)
        }
    }
}