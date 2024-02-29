package com.bsel.remitngo.data.repository.document.dataSource

import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeResponseItem
import retrofit2.Response

interface DocumentRemoteDataSource {

    suspend fun documentCategory(documentCategoryItem: DocumentCategoryItem): Response<DocumentCategoryResponseItem>
    suspend fun documentType(documentTypeItem: DocumentTypeItem): Response<DocumentTypeResponseItem>

}





