package com.bsel.remitngo.data.repository.bank.dataSource

import com.bsel.remitngo.data.model.bank.BankItem
import com.bsel.remitngo.data.model.bank.BankResponseItem
import com.bsel.remitngo.data.model.branch.BranchItem
import com.bsel.remitngo.data.model.branch.BranchResponseItem
import com.bsel.remitngo.data.model.district.DistrictItem
import com.bsel.remitngo.data.model.district.DistrictResponseItem
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.data.model.division.DivisionResponseItem
import retrofit2.Response

interface BankRemoteDataSource {

    suspend fun bank(bankItem: BankItem): Response<BankResponseItem>
    suspend fun division(divisionItem: DivisionItem): Response<DivisionResponseItem>
    suspend fun district(districtItem: DistrictItem): Response<DistrictResponseItem>
    suspend fun branch(branchItem: BranchItem): Response<BranchResponseItem>

}





