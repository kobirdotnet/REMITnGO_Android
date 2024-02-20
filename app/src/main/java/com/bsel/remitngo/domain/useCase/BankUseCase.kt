package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.bank.BankItem
import com.bsel.remitngo.data.model.bank.BankResponseItem
import com.bsel.remitngo.data.model.branch.BranchItem
import com.bsel.remitngo.data.model.branch.BranchResponseItem
import com.bsel.remitngo.data.model.district.DistrictItem
import com.bsel.remitngo.data.model.district.DistrictResponseItem
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.data.model.division.DivisionResponseItem
import com.bsel.remitngo.domain.repository.BankRepository

class BankUseCase(private val bankRepository: BankRepository) {

    suspend fun execute(bankItem: BankItem): BankResponseItem? {
        return bankRepository.bank(bankItem)
    }

    suspend fun execute(divisionItem: DivisionItem): DivisionResponseItem? {
        return bankRepository.division(divisionItem)
    }

    suspend fun execute(districtItem: DistrictItem): DistrictResponseItem? {
        return bankRepository.district(districtItem)
    }

    suspend fun execute(branchItem: BranchItem): BranchResponseItem? {
        return bankRepository.branch(branchItem)
    }

}

