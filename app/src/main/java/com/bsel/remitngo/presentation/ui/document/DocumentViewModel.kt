package com.bsel.remitngo.presentation.ui.document

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.uploadDocument.documentType.DocumentTypeResponseItem
import com.bsel.remitngo.domain.useCase.DocumentUseCase
import kotlinx.coroutines.launch

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

}