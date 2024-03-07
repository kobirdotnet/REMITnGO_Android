package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.change_password.ChangePasswordItem
import com.bsel.remitngo.data.model.change_password.ChangePasswordResponseItem
import com.bsel.remitngo.domain.repository.SettingsRepository

class SettingsUseCase(private val settingsRepository: SettingsRepository) {

    suspend fun execute(changePasswordItem: ChangePasswordItem): ChangePasswordResponseItem? {
        return settingsRepository.changePassword(changePasswordItem)
    }

}

