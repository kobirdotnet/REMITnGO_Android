package com.bsel.remitngo.data.repository.beneficiary.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
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
import com.bsel.remitngo.data.repository.beneficiary.dataSource.BeneficiaryRemoteDataSource
import retrofit2.Response

class BeneficiaryRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    BeneficiaryRemoteDataSource {

    override suspend fun getBeneficiary(getBeneficiaryItem: GetBeneficiaryItem): Response<GetBeneficiaryResponseItem> {
        return remitNgoService.getBeneficiary(getBeneficiaryItem)
    }

    override suspend fun beneficiary(beneficiaryItem: BeneficiaryItem): Response<BeneficiaryResponseItem> {
        return remitNgoService.beneficiary(beneficiaryItem)
    }

    override suspend fun relation(relationItem: RelationItem): Response<RelationResponseItem> {
        return remitNgoService.relation(relationItem)
    }

    override suspend fun reason(reasonItem: ReasonItem): Response<ReasonResponseItem> {
        return remitNgoService.reason(reasonItem)
    }

    override suspend fun gender(genderItem: GenderItem): Response<GenderResponseItem> {
        return remitNgoService.gender(genderItem)
    }

}







