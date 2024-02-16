package com.bsel.remitngo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bsel.remitngo.R
import com.bsel.remitngo.model.CollectionPointWallet

class CollectionPointWalletAdapter(
    context: Context,
    resource: Int,
    collectionPointWallets: Array<CollectionPointWallet>
) :
    ArrayAdapter<CollectionPointWallet>(context, resource, collectionPointWallets) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.collection_point_wallet, parent, false)

        val collectionPointWalletItem = getItem(position)

        val collectionPointWalletName: TextView = itemView.findViewById(R.id.collectionPointWallet_name)
        collectionPointWalletName.text = collectionPointWalletItem?.name

        return itemView
    }
}