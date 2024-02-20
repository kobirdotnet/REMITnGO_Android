package com.bsel.remitngo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bsel.remitngo.R
import com.bsel.remitngo.model.StatusItem

class StatusAdapter(context: Context, resource: Int, statusItems: Array<StatusItem>) :
    ArrayAdapter<StatusItem>(context, resource, statusItems) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.status_item, parent, false)

        val statusItem = getItem(position)

        val status: TextView = itemView.findViewById(R.id.status)
        status.text = statusItem?.status

        return itemView
    }
}