package com.bsel.remitngo.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentData
import com.bsel.remitngo.databinding.ItemRequireDocumentBinding

class RequireDocumentAdapter : RecyclerView.Adapter<RequireDocumentViewHolder>() {

    private val documentList = ArrayList<RequireDocumentData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequireDocumentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemRequireDocumentBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_require_document, parent, false)
        return RequireDocumentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return documentList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RequireDocumentViewHolder, position: Int) {
        holder.bind(documentList[position])
    }

    fun setList(documentItem: List<RequireDocumentData>) {
        documentList.clear()
        documentList.addAll(documentItem)
    }

}

class RequireDocumentViewHolder(val binding: ItemRequireDocumentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(
        documentItem: RequireDocumentData
    ) {
        var sl = documentItem.sl
        var name = documentItem.name
        binding.requireDocumentName.text = "$sl $name"
    }

}