package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem
import com.bsel.remitngo.domain.repository.RegistrationRepository

class RegistrationUseCase(private val registrationRepository: RegistrationRepository) {
    suspend fun execute(registrationItem: RegistrationItem): RegistrationResponseItem? {
        return registrationRepository.registerUser(registrationItem)
    }
}

