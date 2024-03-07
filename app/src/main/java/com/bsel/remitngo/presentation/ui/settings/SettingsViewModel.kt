package com.bsel.remitngo.presentation.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsel.remitngo.data.model.change_password.ChangePasswordItem
import com.bsel.remitngo.data.model.change_password.ChangePasswordResponseItem
import com.bsel.remitngo.domain.useCase.SettingsUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsUseCase: SettingsUseCase) : ViewModel() {

    private val _changePasswordResult = MutableLiveData<ChangePasswordResponseItem?>()
    val changePasswordResult: LiveData<ChangePasswordResponseItem?> = _changePasswordResult

    fun changePassword(changePasswordItem: ChangePasswordItem) {
        viewModelScope.launch {
            val result = settingsUseCase.execute(changePasswordItem)
            _changePasswordResult.value = result
        }
    }

}