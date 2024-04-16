package com.bsel.remitngo.presentation.ui.document

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentItem
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentResponseItem
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeResponseItem
import com.bsel.remitngo.data.model.document.document.GetDocumentItem
import com.bsel.remitngo.data.model.document.document.GetDocumentResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.UploadDocumentResponseItem
import com.bsel.remitngo.domain.useCase.DocumentUseCase
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DocumentViewModel(private val documentUseCase: DocumentUseCase) : ViewModel() {

    private val _documentCategoryResult = MutableLiveData<DocumentCategoryResponseItem?>()
    val documentCategoryResult: LiveData<DocumentCategoryResponseItem?> = _documentCategoryResult

    fun documentCategory(documentCategoryItem: DocumentCategoryItem) {
        viewModelScope.launch {
            val result = documentUseCase.execute(documentCategoryItem)
            _documentCategoryResult.value = result
        }
    }

    private val _documentTypeResult = MutableLiveData<DocumentTypeResponseItem?>()
    val documentTypeResult: LiveData<DocumentTypeResponseItem?> = _documentTypeResult

    fun documentType(documentTypeItem: DocumentTypeItem) {
        viewModelScope.launch {
            val result = documentUseCase.execute(documentTypeItem)
            _documentTypeResult.value = result
        }
    }

    private val _getDocumentResult = MutableLiveData<GetDocumentResponseItem?>()
    val getDocumentResult: LiveData<GetDocumentResponseItem?> = _getDocumentResult

    fun getDocument(getDocumentItem: GetDocumentItem) {
        viewModelScope.launch {
            val result = documentUseCase.execute(getDocumentItem)
            _getDocumentResult.value = result
        }
    }

    private val _uploadDocumentResult = MutableLiveData<UploadDocumentResponseItem?>()
    val uploadDocumentResult: LiveData<UploadDocumentResponseItem?> = _uploadDocumentResult

    fun uploadDocument(
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
    ) {
        viewModelScope.launch {
            val result = documentUseCase.execute(
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
            _uploadDocumentResult.value = result
        }
    }

    private val _requireDocumentResult = MutableLiveData<RequireDocumentResponseItem?>()
    val requireDocumentResult: LiveData<RequireDocumentResponseItem?> = _requireDocumentResult

    fun requireDocument(requireDocumentItem: RequireDocumentItem) {
        viewModelScope.launch {
            val result = documentUseCase.execute(requireDocumentItem)
            _requireDocumentResult.value = result
        }
    }

}