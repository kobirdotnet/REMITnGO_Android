package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.document.getDocument.GetDocumentItem
import com.bsel.remitngo.data.model.document.getDocument.GetDocumentResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeResponseItem

interface DocumentRepository {

    suspend fun documentCategory(documentCategoryItem: DocumentCategoryItem): DocumentCategoryResponseItem?
    suspend fun documentType(documentTypeItem: DocumentTypeItem): DocumentTypeResponseItem?
    suspend fun getDocument(getDocumentItem: GetDocumentItem): GetDocumentResponseItem?

}

