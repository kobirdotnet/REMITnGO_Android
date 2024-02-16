package com.bsel.remitngo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bsel.remitngo.R
import com.bsel.remitngo.model.PaymentMode

class PaymentModeAdapter(context: Context, resource: Int, paymentModes: Array<PaymentMode>) :
    ArrayAdapter<PaymentMode>(context, resource, paymentModes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.payment_mode, parent, false)

        val paymentModeItem = getItem(position)

        val paymentModeName: TextView = itemView.findViewById(R.id.paymentMode_name)
        paymentModeName.text = paymentModeItem?.name

        return itemView
    }
}