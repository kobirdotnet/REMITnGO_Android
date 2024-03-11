package com.bsel.remitngo.data.repository.registration

import android.util.Log
import com.bsel.remitngo.data.model.marketing.MarketingItem
import com.bsel.remitngo.data.model.marketing.MarketingResponseItem
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.repository.registration.dataSource.RegistrationRemoteDataSource
import com.bsel.remitngo.domain.repository.RegistrationRepository
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem

class RegistrationRepositoryImpl(private val registrationRemoteDataSource: RegistrationRemoteDataSource) : RegistrationRepository {

    override suspend fun registerUser(registrationItem: RegistrationItem): RegistrationResponseItem? {
        return try {
            val response = registrationRemoteDataSource.registerUser(registrationItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to register user: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error registering user: ${exception.message}", exception)
            null
        }
    }

    override suspend fun marketing(marketingItem: MarketingItem): MarketingResponseItem? {
        return try {
            val response = registrationRemoteDataSource.marketing(marketingItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to marketing: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error marketing: ${exception.message}", exception)
            null
        }
    }
}
