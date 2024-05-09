package com.bsel.remitngo.presentation.ui.registration

import androidx.lifecycle.*
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
import com.bsel.remitngo.domain.useCase.RegistrationUseCase
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registrationUseCase: RegistrationUseCase) : ViewModel() {

    private val _registrationResult = MutableLiveData<RegistrationResponseItem?>()
    val registrationResult: LiveData<RegistrationResponseItem?> = _registrationResult

    fun registerUser(registrationItem: RegistrationItem) {
        viewModelScope.launch {
            val result = registrationUseCase.execute(registrationItem)
            _registrationResult.value = result
        }
    }

    private val _marketingResult = MutableLiveData<MarketingResponseItem?>()
    val marketingResult: LiveData<MarketingResponseItem?> = _marketingResult

    fun marketing(marketingItem: MarketingItem) {
        viewModelScope.launch {
            val result = registrationUseCase.execute(marketingItem)
            _marketingResult.value = result
        }
    }

    private val _registrationMessageResult = MutableLiveData<RegistrationResponseMessage?>()
    val registrationMessageResult: LiveData<RegistrationResponseMessage?> = _registrationMessageResult

    fun registrationMessage(message: String) {
        viewModelScope.launch {
            val result = registrationUseCase.executeRegistrationMessage(message)
            _registrationMessageResult.value = result
        }
    }

    private val _sendMigrationOtpResult = MutableLiveData<SendOtpResponse?>()
    val sendMigrationOtpResult: LiveData<SendOtpResponse?> = _sendMigrationOtpResult

    fun sendMigrationOtp(sendOtpItem: SendOtpItem) {
        viewModelScope.launch {
            val result = registrationUseCase.executeMigrationOtp(sendOtpItem)
            _sendMigrationOtpResult.value = result
        }
    }

    private val _otpValidationResult = MutableLiveData<OtpValidationResponseItem?>()
    val otpValidationResult: LiveData<OtpValidationResponseItem?> = _otpValidationResult

    fun otpValidation(otpValidationItem: OtpValidationItem) {
        viewModelScope.launch {
            val result = registrationUseCase.execute(otpValidationItem)
            _otpValidationResult.value = result
        }
    }
    private val _setPasswordMigrationResult = MutableLiveData<SetPasswordResponseItemMigration?>()
    val setPasswordMigrationResult: LiveData<SetPasswordResponseItemMigration?> = _setPasswordMigrationResult

    fun setPasswordMigration(setPasswordItemMigration: SetPasswordItemMigration) {
        viewModelScope.launch {
            val result = registrationUseCase.execute(setPasswordItemMigration)
            _setPasswordMigrationResult.value = result
        }
    }

}


