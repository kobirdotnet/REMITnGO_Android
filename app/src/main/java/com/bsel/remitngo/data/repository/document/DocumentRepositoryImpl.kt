package com.bsel.remitngo.data.repository.document

import android.util.Log
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
        return try {
            val response = documentRemoteDataSource.uploadDocument(
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

    override suspend fun requireDocument(requireDocumentItem: RequireDocumentItem): RequireDocumentResponseItem? {
        return try {
            val response = documentRemoteDataSource.requireDocument(requireDocumentItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to requireDocument: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error requireDocument: ${exception.message}", exception)
            null
        }
    }

}
