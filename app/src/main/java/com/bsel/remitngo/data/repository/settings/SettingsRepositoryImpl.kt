package com.bsel.remitngo.data.repository.settings

import android.util.Log
import com.bsel.remitngo.data.model.change_password.ChangePasswordItem
import com.bsel.remitngo.data.model.change_password.ChangePasswordResponseItem
import com.bsel.remitngo.data.repository.settings.dataSource.SettingsRemoteDataSource
import com.bsel.remitngo.domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val settingsRemoteDataSource: SettingsRemoteDataSource) :
    SettingsRepository {

    override suspend fun changePassword(changePasswordItem: ChangePasswordItem): ChangePasswordResponseItem? {
        return try {
            val response = settingsRemoteDataSource.changePassword(changePasswordItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to changePassword: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error changePassword: ${exception.message}", exception)
            null
        }
    }

}
