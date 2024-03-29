package com.bsel.remitngo.data.repository.beneficiary.dataSource

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
import retrofit2.Response

interface BeneficiaryRemoteDataSource {

    suspend fun getBeneficiary(getBeneficiaryItem: GetBeneficiaryItem): Response<GetBeneficiaryResponseItem>
    suspend fun beneficiary(beneficiaryItem: BeneficiaryItem): Response<BeneficiaryResponseItem>
    suspend fun relation(relationItem: RelationItem): Response<RelationResponseItem>
    suspend fun reason(reasonItem: ReasonItem): Response<ReasonResponseItem>
    suspend fun gender(genderItem: GenderItem): Response<GenderResponseItem>

}





