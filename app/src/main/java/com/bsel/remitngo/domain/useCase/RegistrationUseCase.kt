package com.bsel.remitngo.domain.useCase

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
import com.bsel.remitngo.domain.repository.RegistrationRepository

class RegistrationUseCase(private val registrationRepository: RegistrationRepository) {
    suspend fun execute(registrationItem: RegistrationItem): RegistrationResponseItem? {
        return registrationRepository.registerUser(registrationItem)
    }
    suspend fun execute(marketingItem: MarketingItem): MarketingResponseItem? {
        return registrationRepository.marketing(marketingItem)
    }

    suspend fun executeRegistrationMessage(message: String): RegistrationResponseMessage? {
        return registrationRepository.registrationMessage(message)
    }
    suspend fun executeMigrationOtp(sendOtpItem: SendOtpItem): SendOtpResponse? {
        return registrationRepository.sendMigrationOtp(sendOtpItem)
    }

    suspend fun execute(otpValidationItem: OtpValidationItem): OtpValidationResponseItem? {
        return registrationRepository.otpValidation(otpValidationItem)
    }
    suspend fun execute(setPasswordItemMigration: SetPasswordItemMigration): SetPasswordResponseItemMigration? {
        return registrationRepository.setPasswordMigration(setPasswordItemMigration)
    }
}

