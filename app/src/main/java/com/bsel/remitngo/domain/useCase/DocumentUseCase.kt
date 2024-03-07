package com.bsel.remitngo.domain.useCase

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
    ): UploadDocumentResponseItem? {
        return documentRepository.uploadDocument(
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

