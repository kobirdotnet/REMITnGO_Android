package com.bsel.remitngo.data.repository.document.dataSource

import com.bsel.remitngo.data.model.document.getDocument.GetDocumentItem
import com.bsel.remitngo.data.model.document.getDocument.GetDocumentResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeResponseItem
import retrofit2.Response

interface DocumentRemoteDataSource {

    suspend fun documentCategory(documentCategoryItem: DocumentCategoryItem): Response<DocumentCategoryResponseItem>
    suspend fun documentType(documentTypeItem: DocumentTypeItem): Response<DocumentTypeResponseItem>
    suspend fun getDocument(getDocumentItem: GetDocumentItem): Response<GetDocumentResponseItem>

}





