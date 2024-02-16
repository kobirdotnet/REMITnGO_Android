package com.bsel.remitngo.interfaceses

import com.bsel.remitngo.model.Category
import com.bsel.remitngo.model.Document

interface OnDocumentItemSelectedListener {
    fun onCategoryItemSelected(selectedItem: Category)
    fun onDocumentItemSelected(selectedItem: Document)
}
