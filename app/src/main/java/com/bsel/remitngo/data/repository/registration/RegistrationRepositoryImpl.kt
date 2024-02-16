package com.bsel.remitngo.data.repository.registration

import android.util.Log
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem
import com.bsel.remitngo.data.repository.registration.dataSource.RegistrationRemoteDataSource
import com.bsel.remitngo.domain.repository.RegistrationRepository

class RegistrationRepositoryImpl(private val registrationRemoteDataSource: RegistrationRemoteDataSource) : RegistrationRepository {
    override suspend fun registrations(): RegistrationResponseItem? {
        return getRegistrationFromAPI()
    }

    private suspend fun getRegistrationFromAPI(): RegistrationResponseItem? {
        try {
            val response = registrationRemoteDataSource.getRegistration(
                RegistrationItem(
                    "02-03-1994",
                    "k@gmail.com",
                    "k",
                    1,
                    "i",
                    "m",
                    "01535000573",
                    "Normal@222",
                    true,
                    true,
                    true,
                    true,
                    "1"
                )
            )
            return response.body()
        } catch (exception: Exception) {
            Log.i("MyTag", exception.message.toString())
        }
        return null
    }
}