package com.bsel.remitngo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bsel.remitngo.R
import com.bsel.remitngo.model.GenderItem

class GenderAdapter(context: Context, resource: Int, genderItems: Array<GenderItem>) :
    ArrayAdapter<GenderItem>(context, resource, genderItems) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.gender_item, parent, false)

        val genderItem = getItem(position)

        val gender: TextView = itemView.findViewById(R.id.gender)
        gender.text = genderItem?.gender

        return itemView
    }
}