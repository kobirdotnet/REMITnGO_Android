package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.document.getDocument.GetDocumentItem
import com.bsel.remitngo.data.model.document.getDocument.GetDocumentResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeResponseItem
import com.bsel.remitngo.domain.repository.DocumentRepository

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

}

