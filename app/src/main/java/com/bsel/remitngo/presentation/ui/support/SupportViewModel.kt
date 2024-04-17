package com.bsel.remitngo.presentation.ui.support

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsel.remitngo.data.model.support.SupportResponseItem
import com.bsel.remitngo.domain.useCase.SupportUseCase
import kotlinx.coroutines.launch

class SupportViewModel(private val supportUseCase: SupportUseCase) : ViewModel() {

    private val _supportResult = MutableLiveData<SupportResponseItem?>()
    val supportResult: LiveData<SupportResponseItem?> = _supportResult

    fun support(message: String) {
        viewModelScope.launch {
            val result = supportUseCase.execute(message)
            _supportResult.value = result
        }
    }
}