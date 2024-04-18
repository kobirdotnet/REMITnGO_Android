package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentItem
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentResponseItem
import com.bsel.remitngo.data.model.document.docForTransaction.docMsg.RequireDocMsg
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeResponseItem
import com.bsel.remitngo.data.model.document.document.GetDocumentItem
import com.bsel.remitngo.data.model.document.document.GetDocumentResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.UploadDocumentResponseItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

interface DocumentRepository {

    suspend fun documentCategory(documentCategoryItem: DocumentCategoryItem): DocumentCategoryResponseItem?
    suspend fun documentType(documentTypeItem: DocumentTypeItem): DocumentTypeResponseItem?
    suspend fun getDocument(getDocumentItem: GetDocumentItem): GetDocumentResponseItem?
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
    ): UploadDocumentResponseItem?

    suspend fun requireDocument(requireDocumentItem: RequireDocumentItem): RequireDocumentResponseItem?

}

