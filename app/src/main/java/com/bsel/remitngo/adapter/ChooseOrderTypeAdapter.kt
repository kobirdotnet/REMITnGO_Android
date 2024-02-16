package com.bsel.remitngo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bsel.remitngo.R
import com.bsel.remitngo.model.ChooseOrderType

class ChooseOrderTypeAdapter(context: Context, resource: Int, chooseOrderTypes: Array<ChooseOrderType>) :
    ArrayAdapter<ChooseOrderType>(context, resource, chooseOrderTypes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.choose_order_type, parent, false)

        val payoutItem = getItem(position)

        val countryFlagImageView: ImageView = itemView.findViewById(R.id.order_type_flag)
        countryFlagImageView.setImageResource(
            payoutItem?.flagDrawable ?: R.drawable.placeholder_flag
        )

        val payoutName: TextView = itemView.findViewById(R.id.order_type_name)
        payoutName.text = payoutItem?.orderTypeName

        return itemView
    }
}