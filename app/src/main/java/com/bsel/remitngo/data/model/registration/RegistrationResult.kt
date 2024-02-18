package com.bsel.remitngo.data.model.registration

sealed class RegistrationResult {
    data class Success(val registrationResponse: RegistrationResponseItem) : RegistrationResult()
    data class Error(val message: String) : RegistrationResult()
}

