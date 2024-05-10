package com.bsel.remitngo.data.repository.document.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentItem
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentResponseItem
import com.bsel.remitngo.data.model.document.document.GetDocumentItem
import com.bsel.remitngo.data.model.document.document.GetDocumentResponseItem
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeResponseItem
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
        file: MultipartBody.Part,
        deviceId: RequestBody,
        personId: RequestBody,
        categoryId: RequestBody,
        docId: RequestBody,
        typeId: RequestBody,
        docNo: RequestBody,
        issueBy: RequestBody,
        issueDate: RequestBody,
        expireDate: RequestBody
    ): Response<UploadDocumentResponseItem> {
        return remitNgoService.uploadDocument(
            file,
            deviceId,
            personId,
            categoryId,
            docId,
            typeId,
            docNo,
            issueBy,
            issueDate,
            expireDate
        )
    }

    override suspend fun requireDocument(requireDocumentItem: RequireDocumentItem): Response<RequireDocumentResponseItem> {
        return remitNgoService.requireDocument(requireDocumentItem)
    }

}







