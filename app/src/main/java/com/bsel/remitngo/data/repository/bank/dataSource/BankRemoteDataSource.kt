package com.bsel.remitngo.data.repository.bank.dataSource

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
import retrofit2.Response

interface BankRemoteDataSource {

    suspend fun getBank(getBankItem: GetBankItem): Response<GetBankResponseItem>
    suspend fun saveBank(saveBankItem: SaveBankItem): Response<SaveBankResponseItem>
    suspend fun bank(bankItem: BankItem): Response<BankResponseItem>
    suspend fun wallet(walletItem: WalletItem): Response<WalletResponseItem>
    suspend fun division(divisionItem: DivisionItem): Response<DivisionResponseItem>
    suspend fun district(districtItem: DistrictItem): Response<DistrictResponseItem>
    suspend fun branch(branchItem: BranchItem): Response<BranchResponseItem>

}





