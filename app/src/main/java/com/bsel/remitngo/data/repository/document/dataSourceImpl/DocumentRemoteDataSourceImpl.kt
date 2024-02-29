package com.bsel.remitngo.data.repository.document.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeResponseItem
import com.bsel.remitngo.data.repository.document.dataSource.DocumentRemoteDataSource
import retrofit2.Response

class DocumentRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    DocumentRemoteDataSource {

    override suspend fun documentCategory(documentCategoryItem: DocumentCategoryItem): Response<DocumentCategoryResponseItem> {
        return remitNgoService.documentCategory(documentCategoryItem)
    }

    override suspend fun documentType(documentTypeItem: DocumentTypeItem): Response<DocumentTypeResponseItem> {
        return remitNgoService.documentType(documentTypeItem)
    }

}







