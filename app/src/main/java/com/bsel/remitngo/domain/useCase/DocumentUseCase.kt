package com.bsel.remitngo.domain.useCase

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
import com.bsel.remitngo.domain.repository.DocumentRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DocumentUseCase(private val documentRepository: DocumentRepository) {

    suspend fun execute(documentCategoryItem: DocumentCategoryItem): DocumentCategoryResponseItem? {
        return documentRepository.documentCategory(documentCategoryItem)
    }

    suspend fun execute(documentTypeItem: DocumentTypeItem): DocumentTypeResponseItem? {
        return documentRepository.documentType(documentTypeItem)
    }

    suspend fun execute(getDocumentItem: GetDocumentItem): GetDocumentResponseItem? {
        return documentRepository.getDocument(getDocumentItem)
    }

    suspend fun execute(
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
    ): UploadDocumentResponseItem? {
        return documentRepository.uploadDocument(
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

    suspend fun execute(requireDocumentItem: RequireDocumentItem): RequireDocumentResponseItem? {
        return documentRepository.requireDocument(requireDocumentItem)
    }

}

