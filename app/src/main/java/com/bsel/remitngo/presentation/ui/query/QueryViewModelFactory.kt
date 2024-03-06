package com.bsel.remitngo.presentation.ui.query

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.domain.useCase.QueryUseCase

class QueryViewModelFactory(
    private val queryUseCase: QueryUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QueryViewModel::class.java)) {
            return QueryViewModel(queryUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}