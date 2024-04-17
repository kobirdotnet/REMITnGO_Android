package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.support.SupportResponseItem
import com.bsel.remitngo.domain.repository.SupportRepository

class SupportUseCase(private val supportRepository: SupportRepository) {
    suspend fun execute(message: String): SupportResponseItem? {
        return supportRepository.support(message)
    }
}