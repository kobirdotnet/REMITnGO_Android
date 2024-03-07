package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryResponseItem
import com.bsel.remitngo.data.model.beneficiary.save_beneficiary.BeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.save_beneficiary.BeneficiaryResponseItem
import com.bsel.remitngo.data.model.gender.GenderItem
import com.bsel.remitngo.data.model.gender.GenderResponseItem
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.data.model.reason.ReasonResponseItem
import com.bsel.remitngo.data.model.relation.RelationItem
import com.bsel.remitngo.data.model.relation.RelationResponseItem
import com.bsel.remitngo.domain.repository.BeneficiaryRepository

class BeneficiaryUseCase(private val beneficiaryRepository: BeneficiaryRepository) {

    suspend fun execute(getBeneficiaryItem: GetBeneficiaryItem): GetBeneficiaryResponseItem? {
        return beneficiaryRepository.getBeneficiary(getBeneficiaryItem)
    }

    suspend fun execute(beneficiaryItem: BeneficiaryItem): BeneficiaryResponseItem? {
        return beneficiaryRepository.beneficiary(beneficiaryItem)
    }

    suspend fun execute(relationItem: RelationItem): RelationResponseItem? {
        return beneficiaryRepository.relation(relationItem)
    }

    suspend fun execute(reasonItem: ReasonItem): ReasonResponseItem? {
        return beneficiaryRepository.reason(reasonItem)
    }

    suspend fun execute(genderItem: GenderItem): GenderResponseItem? {
        return beneficiaryRepository.gender(genderItem)
    }

}

