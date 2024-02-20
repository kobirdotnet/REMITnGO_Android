package com.bsel.remitngo.data.repository.bank.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.bank.BankItem
import com.bsel.remitngo.data.model.bank.BankResponseItem
import com.bsel.remitngo.data.model.branch.BranchItem
import com.bsel.remitngo.data.model.branch.BranchResponseItem
import com.bsel.remitngo.data.model.district.DistrictItem
import com.bsel.remitngo.data.model.district.DistrictResponseItem
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.data.model.division.DivisionResponseItem
import com.bsel.remitngo.data.repository.bank.dataSource.BankRemoteDataSource
import retrofit2.Response

class BankRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    BankRemoteDataSource {

    override suspend fun bank(bankItem: BankItem): Response<BankResponseItem> {
        return remitNgoService.bank(bankItem)
    }

    override suspend fun division(divisionItem: DivisionItem): Response<DivisionResponseItem> {
        return remitNgoService.division(divisionItem)
    }

    override suspend fun district(districtItem: DistrictItem): Response<DistrictResponseItem> {
        return remitNgoService.district(districtItem)
    }

    override suspend fun branch(branchItem: BranchItem): Response<BranchResponseItem> {
        return remitNgoService.branch(branchItem)
    }

}







