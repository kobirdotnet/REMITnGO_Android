package com.bsel.remitngo.data.repository.document

import android.util.Log
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeResponseItem
import com.bsel.remitngo.data.model.document.getDocument.GetDocumentItem
import com.bsel.remitngo.data.model.document.getDocument.GetDocumentResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.UploadDocumentResponseItem
import com.bsel.remitngo.data.repository.document.dataSource.DocumentRemoteDataSource
import com.bsel.remitngo.domain.repository.DocumentRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

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
    ): UploadDocumentResponseItem? {
        return try {
            val response = documentRemoteDataSource.uploadDocument(
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
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to uploadDocument: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error uploadDocument: ${exception.message}", exception)
            null
        }
    }

}
