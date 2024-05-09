package com.bsel.remitngo.data.repository.registration.dataSource

import com.bsel.remitngo.data.model.forgotPassword.OtpValidationItem
import com.bsel.remitngo.data.model.forgotPassword.OtpValidationResponseItem
import com.bsel.remitngo.data.model.marketing.MarketingItem
import com.bsel.remitngo.data.model.marketing.MarketingResponseItem
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem
import com.bsel.remitngo.data.model.registration.migration.migrationMessage.RegistrationResponseMessage
import com.bsel.remitngo.data.model.registration.migration.sendOtp.SendOtpItem
import com.bsel.remitngo.data.model.registration.migration.sendOtp.SendOtpResponse
import com.bsel.remitngo.data.model.registration.migration.setPassword.SetPasswordItemMigration
import com.bsel.remitngo.data.model.registration.migration.setPassword.SetPasswordResponseItemMigration
import retrofit2.Response

interface RegistrationRemoteDataSource {
    suspend fun registerUser(registrationItem: RegistrationItem): Response<RegistrationResponseItem>
    suspend fun marketing(marketingItem: MarketingItem): Response<MarketingResponseItem>

    suspend fun registrationMessage(message: String): Response<RegistrationResponseMessage>
    suspend fun sendMigrationOtp(sendOtpItem: SendOtpItem): Response<SendOtpResponse>
    suspend fun otpValidation(otpValidationItem: OtpValidationItem): Response<OtpValidationResponseItem>
    suspend fun setPasswordMigration(setPasswordItemMigration: SetPasswordItemMigration): Response<SetPasswordResponseItemMigration>
}





