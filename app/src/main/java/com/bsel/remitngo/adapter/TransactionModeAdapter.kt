package com.bsel.remitngo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bsel.remitngo.R
import com.bsel.remitngo.model.TransactionMode

class TransactionModeAdapter(
    context: Context,
    resource: Int,
    transactionModes: Array<TransactionMode>
) :
    ArrayAdapter<TransactionMode>(context, resource, transactionModes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.transaction_mode, parent, false)

        val transactionModeItem = getItem(position)

        val transactionModeName: TextView = itemView.findViewById(R.id.transactionMode_name)
        transactionModeName.text = transactionModeItem?.name

        return itemView
    }
}