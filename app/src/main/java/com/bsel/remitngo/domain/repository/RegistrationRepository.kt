package com.bsel.remitngo.domain.repository

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

interface RegistrationRepository {
    suspend fun registerUser(registrationItem: RegistrationItem): RegistrationResponseItem?
    suspend fun marketing(marketingItem: MarketingItem): MarketingResponseItem?
    suspend fun registrationMessage(message: String): RegistrationResponseMessage?
    suspend fun sendMigrationOtp(sendOtpItem: SendOtpItem): SendOtpResponse?
    suspend fun otpValidation(otpValidationItem: OtpValidationItem): OtpValidationResponseItem?
    suspend fun setPasswordMigration(setPasswordItemMigration: SetPasswordItemMigration): SetPasswordResponseItemMigration?
}

