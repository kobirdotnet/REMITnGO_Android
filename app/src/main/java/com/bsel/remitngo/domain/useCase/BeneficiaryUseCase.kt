package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryResponseItem
import com.bsel.remitngo.domain.repository.BeneficiaryRepository

class BeneficiaryUseCase(private val beneficiaryRepository: BeneficiaryRepository) {

    suspend fun execute(addBeneficiaryItem: AddBeneficiaryItem): AddBeneficiaryResponseItem? {
        return beneficiaryRepository.addBeneficiary(addBeneficiaryItem)
    }

}

