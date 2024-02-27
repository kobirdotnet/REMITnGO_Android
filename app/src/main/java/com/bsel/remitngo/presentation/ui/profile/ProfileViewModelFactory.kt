package com.bsel.remitngo.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.domain.useCase.ProfileUseCase

class ProfileViewModelFactory(
    private val profileUseCase: ProfileUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(profileUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}