package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.change_password.ChangePasswordItem
import com.bsel.remitngo.data.model.change_password.ChangePasswordResponseItem

interface SettingsRepository {

    suspend fun changePassword(changePasswordItem: ChangePasswordItem): ChangePasswordResponseItem?

}

