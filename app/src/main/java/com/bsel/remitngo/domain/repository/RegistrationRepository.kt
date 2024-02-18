package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem

interface RegistrationRepository {
    suspend fun registerUser(registrationItem: RegistrationItem): RegistrationResponseItem?
}

