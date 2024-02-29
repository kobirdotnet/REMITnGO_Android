package com.bsel.remitngo.presentation.ui.document

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.domain.useCase.DocumentUseCase

class DocumentViewModelFactory(
    private val documentUseCase: DocumentUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DocumentViewModel::class.java)) {
            return DocumentViewModel(documentUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}