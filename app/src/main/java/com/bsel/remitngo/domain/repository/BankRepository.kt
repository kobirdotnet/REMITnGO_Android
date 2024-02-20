package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.bank.BankItem
import com.bsel.remitngo.data.model.bank.BankResponseItem
import com.bsel.remitngo.data.model.branch.BranchItem
import com.bsel.remitngo.data.model.branch.BranchResponseItem
import com.bsel.remitngo.data.model.district.DistrictItem
import com.bsel.remitngo.data.model.district.DistrictResponseItem
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.data.model.division.DivisionResponseItem

interface BankRepository {

    suspend fun bank(bankItem: BankItem): BankResponseItem?
    suspend fun division(divisionItem: DivisionItem): DivisionResponseItem?
    suspend fun district(districtItem: DistrictItem): DistrictResponseItem?
    suspend fun branch(branchItem: BranchItem): BranchResponseItem?

}

