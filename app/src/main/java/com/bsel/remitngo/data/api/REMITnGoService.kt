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
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateResponseItem
import com.bsel.remitngo.data.model.district.DistrictItem
import com.bsel.remitngo.data.model.district.DistrictResponseItem
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.data.model.division.DivisionResponseItem
import com.bsel.remitngo.data.model.gender.GenderItem
import com.bsel.remitngo.data.model.gender.GenderResponseItem
import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentResponseItem
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.model.payment.PaymentStatusResponse
import com.bsel.remitngo.data.model.profile.ProfileItem
import com.bsel.remitngo.data.model.profile.ProfileResponseItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeResponseItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityResponseItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationResponseItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeResponseItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeResponseItem
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.data.model.reason.ReasonResponseItem
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem
import com.bsel.remitngo.data.model.relation.RelationItem
import com.bsel.remitngo.data.model.relation.RelationResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface REMITnGoService {
    @POST("api/Home/Registration")
    suspend fun registerUser(@Body registrationItem: RegistrationItem): Response<RegistrationResponseItem>

    @POST("api/Home/Login")
    suspend fun loginUser(@Body loginItem: LoginItem): Response<LoginResponseItem>

    @POST("api/General/GetProfileInfo")
    suspend fun profile(@Body profileItem: ProfileItem): Response<ProfileResponseItem>

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

    @POST("api/General/LoadPayingAgent")
    suspend fun payingAgent(@Body payingAgentItem: PayingAgentItem): Response<PayingAgentResponseItem>

    @POST("api/Transaction/CalculateRate")
    suspend fun calculateRate(@Body calculateRateItem: CalculateRateItem): Response<CalculateRateResponseItem>

    @POST("api/Transaction/Payment")
    suspend fun payment(@Body paymentItem: PaymentItem): Response<PaymentResponseItem>

    @GET("api/Payment/GetPaymentStatusByTransactioncode/{transactionCode}")
    suspend fun paymentStatus(@Path("transactionCode") transactionCode: String): Response<PaymentStatusResponse>

    @POST("api/General/LoadAnnualNetIncome")
    suspend fun annualIncome(@Body annualIncomeItem: AnnualIncomeItem): Response<AnnualIncomeResponseItem>
    @POST("api/General/LoadSourceOfIncome")
    suspend fun sourceOfIncome(@Body sourceOfIncomeItem: SourceOfIncomeItem): Response<SourceOfIncomeResponseItem>

    @POST("api/General/Dropdown")
    suspend fun occupationType(@Body occupationTypeItem: OccupationTypeItem): Response<OccupationTypeResponseItem>

    @POST("api/General/Dropdown")
    suspend fun occupation(@Body occupationItem: OccupationItem): Response<OccupationResponseItem>

    @POST("api/General/Dropdown")
    suspend fun nationality(@Body nationalityItem: NationalityItem): Response<NationalityResponseItem>

}

