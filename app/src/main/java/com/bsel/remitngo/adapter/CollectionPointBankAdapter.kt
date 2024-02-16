package com.bsel.remitngo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bsel.remitngo.R
import com.bsel.remitngo.model.CollectionPointBank

class CollectionPointBankAdapter(
    context: Context,
    resource: Int,
    collectionPointBanks: Array<CollectionPointBank>
) :
    ArrayAdapter<CollectionPointBank>(context, resource, collectionPointBanks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.collection_point_bank, parent, false)

        val collectionPointBankItem = getItem(position)

        val collectionPointBankName: TextView = itemView.findViewById(R.id.collectionPointBank_name)
        collectionPointBankName.text = collectionPointBankItem?.name

        return itemView
    }
}