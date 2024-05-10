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
        @Part file: MultipartBody.Part,
        @Part("DeviceId") deviceId: RequestBody,
        @Part("PersonId") personId: RequestBody,
        @Part("CategoryId") categoryId: RequestBody,
        @Part("DocId") docId: RequestBody,
        @Part("TypeId") typeId: RequestBody,
        @Part("DocNo") docNo: RequestBody,
        @Part("IssueBy") issueBy: RequestBody,
        @Part("IssueDate") issueDate: RequestBody,
        @Part("ExpireDate") expireDate: RequestBody,
    ): UploadDocumentResponseItem?

    suspend fun requireDocument(requireDocumentItem: RequireDocumentItem): RequireDocumentResponseItem?

}

