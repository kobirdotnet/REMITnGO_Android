package com.bsel.remitngo.data.interfaceses

interface OnRequireDocumentListener {
    fun onRequireDocumentSelected(totalAmount: Double, transactionCode: String)
}