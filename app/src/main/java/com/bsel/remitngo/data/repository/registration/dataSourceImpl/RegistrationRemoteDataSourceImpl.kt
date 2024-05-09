package com.bsel.remitngo.data.repository.registration.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
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
import com.bsel.remitngo.data.repository.registration.dataSource.RegistrationRemoteDataSource
import retrofit2.Response

class RegistrationRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) : RegistrationRemoteDataSource {
    override suspend fun registerUser(registrationItem: RegistrationItem): Response<RegistrationResponseItem> {
        return remitNgoService.registerUser(registrationItem)
    }

    override suspend fun marketing(marketingItem: MarketingItem): Response<MarketingResponseItem> {
        return remitNgoService.marketing(marketingItem)
    }

    override suspend fun registrationMessage(message: String): Response<RegistrationResponseMessage> {
        return remitNgoService.registrationMessage(message)
    }

    override suspend fun sendMigrationOtp(sendOtpItem: SendOtpItem): Response<SendOtpResponse> {
        return remitNgoService.sendMigrationOtp(sendOtpItem)
    }

    override suspend fun otpValidation(otpValidationItem: OtpValidationItem): Response<OtpValidationResponseItem> {
        return remitNgoService.otpValidation(otpValidationItem)
    }

    override suspend fun setPasswordMigration(setPasswordItemMigration: SetPasswordItemMigration): Response<SetPasswordResponseItemMigration> {
        return remitNgoService.setPasswordMigration(setPasswordItemMigration)
    }

}







