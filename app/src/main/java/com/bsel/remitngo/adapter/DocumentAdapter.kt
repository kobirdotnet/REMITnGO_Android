package com.bsel.remitngo.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.document.document.GetDocumentData
import com.bsel.remitngo.databinding.ItemDocumentBinding
import com.bumptech.glide.Glide
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DocumentAdapter(
    private val selectedItem: (GetDocumentData) -> Unit
) : RecyclerView.Adapter<DocumentViewHolder>() {

    private val documentList = ArrayList<GetDocumentData>()
    private var filteredDocumentList = ArrayList<GetDocumentData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDocumentBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_document, parent, false)
        return DocumentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredDocumentList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bind(filteredDocumentList[position], selectedItem)
    }

    fun setList(documentItem: List<GetDocumentData>) {
        documentList.clear()
        documentList.addAll(documentItem)
        filter("")
    }

    fun filter(query: String) {
        filteredDocumentList.clear()
        for (document in documentList) {
            if (document.category!!.contains(query, ignoreCase = true)) {
                filteredDocumentList.add(document)
            }
        }
        notifyDataSetChanged()
    }

}

class DocumentViewHolder(val binding: ItemDocumentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(
        documentItem: GetDocumentData,
        selectedItem: (GetDocumentData) -> Unit
    ) {
        binding.documentName.text = documentItem.category

        val dateTime =
            LocalDateTime.parse(documentItem.expireDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val date = dateTime.toLocalDate()
        val expireDate = date.format(DateTimeFormatter.ISO_DATE)
        binding.expireDate.text = "Expire Date: $expireDate"

        val fileName=documentItem.fileName
        val imageUrl = "https://uat.bracsaajanexchange.com/REmitERPBDUAT/UploadedFiles/PersonFiles/$fileName"

        Glide.with(binding.docImage)
            .load(imageUrl)
            .into(binding.docImage)

        binding.itemDocumentLayout.setOnClickListener {
            selectedItem(documentItem)
        }

    }

}