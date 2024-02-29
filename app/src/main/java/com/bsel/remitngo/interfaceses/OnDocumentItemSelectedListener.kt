package com.bsel.remitngo.interfaceses

import android.net.Uri
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryData
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeData

interface OnDocumentItemSelectedListener {
    fun onDocumentCategoryItemSelected(selectedItem: DocumentCategoryData)
    fun onDocumentTypeItemSelected(selectedItem: DocumentTypeData)
    fun onDocumentFileItemSelected(selectedItem: Uri?)
}
