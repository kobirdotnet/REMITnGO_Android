package com.bsel.remitngo.data.repository.beneficiary.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryResponseItem
import com.bsel.remitngo.data.repository.beneficiary.dataSource.BeneficiaryRemoteDataSource
import retrofit2.Response

class BeneficiaryRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    BeneficiaryRemoteDataSource {

    override suspend fun addBeneficiary(addBeneficiaryItem: AddBeneficiaryItem): Response<AddBeneficiaryResponseItem> {
        return remitNgoService.addBeneficiary(addBeneficiaryItem)
    }

}







