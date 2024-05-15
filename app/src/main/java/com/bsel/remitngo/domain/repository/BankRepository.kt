package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.bank.BankItem
import com.bsel.remitngo.data.model.bank.BankResponseItem
import com.bsel.remitngo.data.model.bank.WalletItem
import com.bsel.remitngo.data.model.bank.WalletResponseItem
import com.bsel.remitngo.data.model.bank.bank_account.GetBankItem
import com.bsel.remitngo.data.model.bank.bank_account.GetBankResponseItem
import com.bsel.remitngo.data.model.bank.save_bank_account.SaveBankItem
import com.bsel.remitngo.data.model.bank.save_bank_account.SaveBankResponseItem
import com.bsel.remitngo.data.model.branch.BranchItem
import com.bsel.remitngo.data.model.branch.BranchResponseItem
import com.bsel.remitngo.data.model.district.DistrictItem
import com.bsel.remitngo.data.model.district.DistrictResponseItem
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.data.model.division.DivisionResponseItem

interface BankRepository {

    suspend fun getBank(getBankItem: GetBankItem): GetBankResponseItem?
    suspend fun saveBank(saveBankItem: SaveBankItem): SaveBankResponseItem?
    suspend fun bank(bankItem: BankItem): BankResponseItem?
    suspend fun wallet(walletItem: WalletItem): WalletResponseItem?
    suspend fun division(divisionItem: DivisionItem): DivisionResponseItem?
    suspend fun district(districtItem: DistrictItem): DistrictResponseItem?
    suspend fun branch(branchItem: BranchItem): BranchResponseItem?

}

