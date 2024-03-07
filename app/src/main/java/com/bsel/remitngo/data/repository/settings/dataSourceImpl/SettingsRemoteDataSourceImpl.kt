package com.bsel.remitngo.data.repository.settings.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.change_password.ChangePasswordItem
import com.bsel.remitngo.data.model.change_password.ChangePasswordResponseItem
import com.bsel.remitngo.data.repository.settings.dataSource.SettingsRemoteDataSource
import retrofit2.Response

class SettingsRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    SettingsRemoteDataSource {

    override suspend fun changePassword(changePasswordItem: ChangePasswordItem): Response<ChangePasswordResponseItem> {
        return remitNgoService.changePassword(changePasswordItem)
    }

}







