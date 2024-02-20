package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.beneficiary.BeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.BeneficiaryResponseItem
import com.bsel.remitngo.data.model.gender.GenderItem
import com.bsel.remitngo.data.model.gender.GenderResponseItem
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.data.model.reason.ReasonResponseItem
import com.bsel.remitngo.data.model.relation.RelationItem
import com.bsel.remitngo.data.model.relation.RelationResponseItem

interface BeneficiaryRepository {

    suspend fun beneficiary(beneficiaryItem: BeneficiaryItem): BeneficiaryResponseItem?
    suspend fun relation(relationItem: RelationItem): RelationResponseItem?
    suspend fun reason(reasonItem: ReasonItem): ReasonResponseItem?
    suspend fun gender(genderItem: GenderItem): GenderResponseItem?

}

