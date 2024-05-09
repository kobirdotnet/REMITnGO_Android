package com.bsel.remitngo.data.repository.registration

import android.util.Log
import com.bsel.remitngo.data.model.forgotPassword.OtpValidationItem
import com.bsel.remitngo.data.model.forgotPassword.OtpValidationResponseItem
import com.bsel.remitngo.data.model.marketing.MarketingItem
import com.bsel.remitngo.data.model.marketing.MarketingResponseItem
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.repository.registration.dataSource.RegistrationRemoteDataSource
import com.bsel.remitngo.domain.repository.RegistrationRepository
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem
import com.bsel.remitngo.data.model.registration.migration.migrationMessage.RegistrationResponseMessage
import com.bsel.remitngo.data.model.registration.migration.sendOtp.SendOtpItem
import com.bsel.remitngo.data.model.registration.migration.sendOtp.SendOtpResponse
import com.bsel.remitngo.data.model.registration.migration.setPassword.SetPasswordItemMigration
import com.bsel.remitngo.data.model.registration.migration.setPassword.SetPasswordResponseItemMigration

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

    override suspend fun registrationMessage(message: String): RegistrationResponseMessage? {
        return try {
            val response = registrationRemoteDataSource.registrationMessage(message)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to message: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error message: ${exception.message}", exception)
            null
        }
    }

    override suspend fun sendMigrationOtp(sendOtpItem: SendOtpItem): SendOtpResponse? {
        return try {
            val response = registrationRemoteDataSource.sendMigrationOtp(sendOtpItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to sendMigrationOtp: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error sendMigrationOtp: ${exception.message}", exception)
            null
        }
    }

    override suspend fun otpValidation(otpValidationItem: OtpValidationItem): OtpValidationResponseItem? {
        return try {
            val response = registrationRemoteDataSource.otpValidation(otpValidationItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to otpValidation: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error otpValidation: ${exception.message}", exception)
            null
        }
    }

    override suspend fun setPasswordMigration(setPasswordItemMigration: SetPasswordItemMigration): SetPasswordResponseItemMigration? {
        return try {
            val response = registrationRemoteDataSource.setPasswordMigration(setPasswordItemMigration)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to setPasswordMigration: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error setPasswordMigration: ${exception.message}", exception)
            null
        }
    }

}
