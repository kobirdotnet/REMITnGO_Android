package com.bsel.remitngo.data.repository.beneficiary.dataSource

import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryResponseItem
import retrofit2.Response

interface BeneficiaryRemoteDataSource {

    suspend fun addBeneficiary(addBeneficiaryItem: AddBeneficiaryItem): Response<AddBeneficiaryResponseItem>

}





