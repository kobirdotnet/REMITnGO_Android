package com.bsel.remitngo.data.repository.document

import android.util.Log
import com.bsel.remitngo.data.model.document.getDocument.GetDocumentItem
import com.bsel.remitngo.data.model.document.getDocument.GetDocumentResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeResponseItem
import com.bsel.remitngo.data.repository.document.dataSource.DocumentRemoteDataSource
import com.bsel.remitngo.domain.repository.DocumentRepository

class DocumentRepositoryImpl(private val documentRemoteDataSource: DocumentRemoteDataSource) :
    DocumentRepository {

    override suspend fun documentCategory(documentCategoryItem: DocumentCategoryItem): DocumentCategoryResponseItem? {
        return try {
            val response = documentRemoteDataSource.documentCategory(documentCategoryItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to documentCategory: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error documentCategory: ${exception.message}", exception)
            null
        }
    }

    override suspend fun documentType(documentTypeItem: DocumentTypeItem): DocumentTypeResponseItem? {
        return try {
            val response = documentRemoteDataSource.documentType(documentTypeItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to documentType: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error documentType: ${exception.message}", exception)
            null
        }
    }

    override suspend fun getDocument(getDocumentItem: GetDocumentItem): GetDocumentResponseItem? {
        return try {
            val response = documentRemoteDataSource.getDocument(getDocumentItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to getDocument: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error getDocument: ${exception.message}", exception)
            null
        }
    }

}
