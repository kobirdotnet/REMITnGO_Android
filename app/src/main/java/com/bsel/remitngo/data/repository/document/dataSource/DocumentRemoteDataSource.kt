package com.bsel.remitngo.data.repository.document.dataSource

import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeResponseItem
import com.bsel.remitngo.data.model.document.getDocument.GetDocumentItem
import com.bsel.remitngo.data.model.document.getDocument.GetDocumentResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.UploadDocumentResponseItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Part

interface DocumentRemoteDataSource {

    suspend fun documentCategory(documentCategoryItem: DocumentCategoryItem): Response<DocumentCategoryResponseItem>
    suspend fun documentType(documentTypeItem: DocumentTypeItem): Response<DocumentTypeResponseItem>
    suspend fun getDocument(getDocumentItem: GetDocumentItem): Response<GetDocumentResponseItem>
    suspend fun uploadDocument(
        @Part("deviceId") deviceId: RequestBody,
        @Part("personId") personId: RequestBody,
        @Part("categoryId") categoryId: RequestBody,
        @Part("docId") docId: RequestBody,
        @Part("typeId") typeId: RequestBody,
        @Part("proofNo") proofNo: RequestBody,
        @Part("issueBy") issueBy: RequestBody,
        @Part("issueDate") issueDate: RequestBody,
        @Part("expireDate") expireDate: RequestBody,
        @Part("updateDate") updateDate: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<UploadDocumentResponseItem>
}





