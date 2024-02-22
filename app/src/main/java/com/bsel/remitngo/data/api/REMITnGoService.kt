package com.bsel.remitngo.data.api

import com.bsel.remitngo.data.model.bank.BankItem
import com.bsel.remitngo.data.model.bank.BankResponseItem
import com.bsel.remitngo.data.model.bank.get_bank_account.GetBankItem
import com.bsel.remitngo.data.model.bank.get_bank_account.GetBankResponseItem
import com.bsel.remitngo.data.model.bank.save_bank_account.SaveBankItem
import com.bsel.remitngo.data.model.bank.save_bank_account.SaveBankResponseItem
import com.bsel.remitngo.data.model.beneficiary.get_beneficiary.GetBeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.get_beneficiary.GetBeneficiaryResponseItem
import com.bsel.remitngo.data.model.beneficiary.save_beneficiary.BeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.save_beneficiary.BeneficiaryResponseItem
import com.bsel.remitngo.data.model.branch.BranchItem
import com.bsel.remitngo.data.model.branch.BranchResponseItem
import com.bsel.remitngo.data.model.district.DistrictItem
import com.bsel.remitngo.data.model.district.DistrictResponseItem
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.data.model.division.DivisionResponseItem
import com.bsel.remitngo.data.model.gender.GenderItem
import com.bsel.remitngo.data.model.gender.GenderResponseItem
import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.data.model.reason.ReasonResponseItem
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem
import com.bsel.remitngo.data.model.relation.RelationItem
import com.bsel.remitngo.data.model.relation.RelationResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface REMITnGoService {
    @POST("api/Home/Registration")
    suspend fun registerUser(@Body registrationItem: RegistrationItem): Response<RegistrationResponseItem>

    @POST("api/Home/Login")
    suspend fun loginUser(@Body loginItem: LoginItem): Response<LoginResponseItem>

    @POST("api/Beneficiary/BenificiaryListWithBankDetails")
    suspend fun getBeneficiary(@Body getBeneficiaryItem: GetBeneficiaryItem): Response<GetBeneficiaryResponseItem>

    @POST("api/Beneficiary/AddBeneficiary")
    suspend fun beneficiary(@Body beneficiaryItem: BeneficiaryItem): Response<BeneficiaryResponseItem>

    @POST("api/General/Dropdown")
    suspend fun relation(@Body relationItem: RelationItem): Response<RelationResponseItem>

    @POST("api/General/Dropdown")
    suspend fun reason(@Body reasonItem: ReasonItem): Response<ReasonResponseItem>

    @POST("api/General/Dropdown")
    suspend fun gender(@Body genderItem: GenderItem): Response<GenderResponseItem>

    @POST("api/Transaction/BeneficiaryBankList")
    suspend fun getBank(@Body getBankItem: GetBankItem): Response<GetBankResponseItem>

    @POST("api/Transaction/BankAccountDetailsInsertUpdate")
    suspend fun saveBank(@Body saveBankItem: SaveBankItem): Response<SaveBankResponseItem>

    @POST("api/General/Dropdown")
    suspend fun bank(@Body bankItem: BankItem): Response<BankResponseItem>

    @POST("api/General/Dropdown")
    suspend fun division(@Body divisionItem: DivisionItem): Response<DivisionResponseItem>

    @POST("api/General/Dropdown")
    suspend fun district(@Body districtItem: DistrictItem): Response<DistrictResponseItem>

    @POST("api/General/LoadBranch")
    suspend fun branch(@Body branchItem: BranchItem): Response<BranchResponseItem>

}

