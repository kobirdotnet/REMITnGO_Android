package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryResponseItem

interface BeneficiaryRepository {

    suspend fun addBeneficiary(addBeneficiaryItem: AddBeneficiaryItem): AddBeneficiaryResponseItem?

}

