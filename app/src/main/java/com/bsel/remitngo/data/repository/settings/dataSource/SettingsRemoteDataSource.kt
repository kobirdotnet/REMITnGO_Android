package com.bsel.remitngo.data.repository.settings.dataSource

import com.bsel.remitngo.data.model.change_password.ChangePasswordItem
import com.bsel.remitngo.data.model.change_password.ChangePasswordResponseItem
import retrofit2.Response

interface SettingsRemoteDataSource {

    suspend fun changePassword(changePasswordItem: ChangePasswordItem): Response<ChangePasswordResponseItem>
}





