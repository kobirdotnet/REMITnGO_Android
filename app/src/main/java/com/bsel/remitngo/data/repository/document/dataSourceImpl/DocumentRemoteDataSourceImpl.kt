package com.bsel.remitngo.data.repository.document.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeResponseItem
import com.bsel.remitngo.data.model.document.getDocument.GetDocumentItem
import com.bsel.remitngo.data.model.document.getDocument.GetDocumentResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.UploadDocumentResponseItem
import com.bsel.remitngo.data.repository.document.dataSource.DocumentRemoteDataSource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class DocumentRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    DocumentRemoteDataSource {

    override suspend fun documentCategory(documentCategoryItem: DocumentCategoryItem): Response<DocumentCategoryResponseItem> {
        return remitNgoService.documentCategory(documentCategoryItem)
    }

    override suspend fun documentType(documentTypeItem: DocumentTypeItem): Response<DocumentTypeResponseItem> {
        return remitNgoService.documentType(documentTypeItem)
    }

    override suspend fun getDocument(getDocumentItem: GetDocumentItem): Response<GetDocumentResponseItem> {
        return remitNgoService.getDocument(getDocumentItem)
    }

    override suspend fun uploadDocument(
        deviceId: RequestBody,
        personId: RequestBody,
        categoryId: RequestBody,
        docId: RequestBody,
        typeId: RequestBody,
        proofNo: RequestBody,
        issueBy: RequestBody,
        issueDate: RequestBody,
        expireDate: RequestBody,
        updateDate: RequestBody,
        file: MultipartBody.Part
    ): Response<UploadDocumentResponseItem> {
        return remitNgoService.uploadDocument(
            deviceId,
            personId,
            categoryId,
            docId,
            typeId,
            proofNo,
            issueBy,
            issueDate,
            expireDate,
            updateDate,
            file
        )
    }

}







