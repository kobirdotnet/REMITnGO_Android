package com.bsel.remitngo.data.api

import com.bsel.remitngo.data.model.bank.BankItem
import com.bsel.remitngo.data.model.bank.BankResponseItem
import com.bsel.remitngo.data.model.bank.bank_account.GetBankItem
import com.bsel.remitngo.data.model.bank.bank_account.GetBankResponseItem
import com.bsel.remitngo.data.model.bank.save_bank_account.SaveBankItem
import com.bsel.remitngo.data.model.bank.save_bank_account.SaveBankResponseItem
import com.bsel.remitngo.data.model.bankTransactionMessage.BankTransactionMessage
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryResponseItem
import com.bsel.remitngo.data.model.beneficiary.save_beneficiary.BeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.save_beneficiary.BeneficiaryResponseItem
import com.bsel.remitngo.data.model.branch.BranchItem
import com.bsel.remitngo.data.model.branch.BranchResponseItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateResponseItem
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonItem
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonResponseItem
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelResponseItem
import com.bsel.remitngo.data.model.change_password.ChangePasswordItem
import com.bsel.remitngo.data.model.change_password.ChangePasswordResponseItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerResponseItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerResponseItem
import com.bsel.remitngo.data.model.createReceipt.CreateReceiptResponse
import com.bsel.remitngo.data.model.district.DistrictItem
import com.bsel.remitngo.data.model.district.DistrictResponseItem
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.data.model.division.DivisionResponseItem
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentItem
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentResponseItem
import com.bsel.remitngo.data.model.document.docForTransaction.docMsg.RequireDocMsg
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryResponseItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeItem
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeResponseItem
import com.bsel.remitngo.data.model.document.document.GetDocumentItem
import com.bsel.remitngo.data.model.document.document.GetDocumentResponseItem
import com.bsel.remitngo.data.model.document.uploadDocument.UploadDocumentResponseItem
import com.bsel.remitngo.data.model.emp.EmpItem
import com.bsel.remitngo.data.model.emp.EmpResponseItem
import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.encript.EncryptItemForCreateReceipt
import com.bsel.remitngo.data.model.encript.EncryptResponseItem
import com.bsel.remitngo.data.model.encript.EncryptResponseItemForCreateReceipt
import com.bsel.remitngo.data.model.forgotPassword.*
import com.bsel.remitngo.data.model.gender.GenderItem
import com.bsel.remitngo.data.model.gender.GenderResponseItem
import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem
import com.bsel.remitngo.data.model.marketing.MarketingItem
import com.bsel.remitngo.data.model.marketing.MarketingResponseItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentResponseItem
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.model.payment.PaymentStatusResponse
import com.bsel.remitngo.data.model.percentage.PercentageItem
import com.bsel.remitngo.data.model.percentage.PercentageResponseItem
import com.bsel.remitngo.data.model.phoneVerification.PhoneOtpVerifyItem
import com.bsel.remitngo.data.model.phoneVerification.PhoneOtpVerifyResponseItem
import com.bsel.remitngo.data.model.phoneVerification.PhoneVerifyItem
import com.bsel.remitngo.data.model.phoneVerification.PhoneVerifyResponseItem
import com.bsel.remitngo.data.model.profile.ProfileItem
import com.bsel.remitngo.data.model.profile.ProfileResponseItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeResponseItem
import com.bsel.remitngo.data.model.profile.city.CityItem
import com.bsel.remitngo.data.model.profile.city.CityResponseItem
import com.bsel.remitngo.data.model.profile.county.CountyItem
import com.bsel.remitngo.data.model.profile.county.CountyResponseItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityResponseItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationResponseItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeResponseItem
import com.bsel.remitngo.data.model.profile.postCode.PostCodeItem
import com.bsel.remitngo.data.model.profile.postCode.PostCodeResponseItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeResponseItem
import com.bsel.remitngo.data.model.profile.uk_division.UkDivisionItem
import com.bsel.remitngo.data.model.profile.uk_division.UkDivisionResponseItem
import com.bsel.remitngo.data.model.profile.updateProfile.UpdateProfileItem
import com.bsel.remitngo.data.model.profile.updateProfile.UpdateProfileResponseItem
import com.bsel.remitngo.data.model.promoCode.PromoItem
import com.bsel.remitngo.data.model.promoCode.PromoResponseItem
import com.bsel.remitngo.data.model.query.QueryItem
import com.bsel.remitngo.data.model.query.QueryResponseItem
import com.bsel.remitngo.data.model.query.add_message.AddMessageItem
import com.bsel.remitngo.data.model.query.add_message.AddMessageResponseItem
import com.bsel.remitngo.data.model.query.add_query.AddQueryItem
import com.bsel.remitngo.data.model.query.add_query.AddQueryResponseItem
import com.bsel.remitngo.data.model.query.query_message.QueryMessageItem
import com.bsel.remitngo.data.model.query.query_message.QueryMessageResponseItem
import com.bsel.remitngo.data.model.query.query_type.QueryTypeItem
import com.bsel.remitngo.data.model.query.query_type.QueryTypeResponseItem
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.data.model.reason.ReasonResponseItem
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem
import com.bsel.remitngo.data.model.registration.migration.migrationMessage.RegistrationResponseMessage
import com.bsel.remitngo.data.model.registration.migration.sendOtp.SendOtpItem
import com.bsel.remitngo.data.model.registration.migration.sendOtp.SendOtpResponse
import com.bsel.remitngo.data.model.registration.migration.setPassword.SetPasswordItemMigration
import com.bsel.remitngo.data.model.registration.migration.setPassword.SetPasswordResponseItemMigration
import com.bsel.remitngo.data.model.relation.RelationItem
import com.bsel.remitngo.data.model.relation.RelationResponseItem
import com.bsel.remitngo.data.model.support.SupportResponseItem
import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsResponseItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface REMITnGoService {
    @POST("api/Home/Registration")
    suspend fun registerUser(@Body registrationItem: RegistrationItem): Response<RegistrationResponseItem>

    @POST("api/Home/Login")
    suspend fun loginUser(@Body loginItem: LoginItem): Response<LoginResponseItem>

    @POST("api/General/GetProfileInfo")
    suspend fun profile(@Body profileItem: ProfileItem): Response<ProfileResponseItem>

    @POST("api/General/UpdateProfileInfo")
    suspend fun updateProfile(@Body updateProfileItem: UpdateProfileItem): Response<UpdateProfileResponseItem>

    @POST("api/General/GetUKAddressByPostCode")
    suspend fun postCode(@Body postCodeItem: PostCodeItem): Response<PostCodeResponseItem>

    @POST("api/General/Dropdown")
    suspend fun ukDivision(@Body ukDivisionItem: UkDivisionItem): Response<UkDivisionResponseItem>

    @POST("api/General/Dropdown")
    suspend fun county(@Body countyItem: CountyItem): Response<CountyResponseItem>

    @POST("api/General/Dropdown")
    suspend fun city(@Body cityItem: CityItem): Response<CityResponseItem>

    @POST("api/Beneficiary/BeneficiaryList")
    suspend fun getBeneficiary(@Body getBeneficiaryItem: GetBeneficiaryItem): Response<GetBeneficiaryResponseItem>

    @POST("api/Beneficiary/AddOrUpdateBeneficiary")
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

    @POST("api/General/Dropdown")
    suspend fun branch(@Body branchItem: BranchItem): Response<BranchResponseItem>

    @POST("api/General/LoadPayingAgent")
    suspend fun payingAgent(@Body payingAgentItem: PayingAgentItem): Response<PayingAgentResponseItem>

    @POST("api/Transaction/CalculateRate")
    suspend fun calculateRate(@Body calculateRateItem: CalculateRateItem): Response<CalculateRateResponseItem>

    @POST("api/Transaction/CalculateRate")
    suspend fun rateCalculate(@Body calculateRateItem: CalculateRateItem): Response<CalculateRateResponseItem>

    @POST("api/Transaction/Payment")
    suspend fun payment(@Body paymentItem: PaymentItem): Response<PaymentResponseItem>

    @GET("api/Payment/GetPaymentStatusByTransactioncode/{transactionCode}")
    suspend fun paymentStatus(@Path("transactionCode") transactionCode: String): Response<PaymentStatusResponse>

    @POST("api/General/Dropdown")
    suspend fun annualIncome(@Body annualIncomeItem: AnnualIncomeItem): Response<AnnualIncomeResponseItem>

    @POST("api/General/Dropdown")
    suspend fun sourceOfIncome(@Body sourceOfIncomeItem: SourceOfIncomeItem): Response<SourceOfIncomeResponseItem>

    @POST("api/General/Dropdown")
    suspend fun occupationType(@Body occupationTypeItem: OccupationTypeItem): Response<OccupationTypeResponseItem>

    @POST("api/General/Dropdown")
    suspend fun occupation(@Body occupationItem: OccupationItem): Response<OccupationResponseItem>

    @POST("api/General/Dropdown")
    suspend fun nationality(@Body nationalityItem: NationalityItem): Response<NationalityResponseItem>

    @POST("api/General/Dropdown")
    suspend fun documentCategory(@Body documentCategoryItem: DocumentCategoryItem): Response<DocumentCategoryResponseItem>

    @POST("api/General/Dropdown")
    suspend fun documentType(@Body documentTypeItem: DocumentTypeItem): Response<DocumentTypeResponseItem>

    @POST("api/General/GetDocumentList")
    suspend fun getDocument(@Body getDocumentItem: GetDocumentItem): Response<GetDocumentResponseItem>

    @Multipart
    @POST("api/General/UploadDocument")
    suspend fun uploadDocument(
        @Part file: MultipartBody.Part,
        @Part("DeviceId") deviceId: RequestBody,
        @Part("PersonId") personId: RequestBody,
        @Part("CategoryId") categoryId: RequestBody,
        @Part("DocId") docId: RequestBody,
        @Part("TypeId") typeId: RequestBody,
        @Part("DocNo") docNo: RequestBody,
        @Part("IssueBy") issueBy: RequestBody,
        @Part("IssueDate") issueDate: RequestBody,
        @Part("ExpireDate") expireDate: RequestBody,
    ): Response<UploadDocumentResponseItem>

    @POST("api/Transaction/PopulateTransactionList")
    suspend fun transaction(@Body transactionItem: TransactionItem): Response<TransactionResponseItem>

    @POST("api/Transaction/GetTransactionDetailsByTransactionCode")
    suspend fun transactionDetails(@Body transactionDetailsItem: TransactionDetailsItem): Response<TransactionDetailsResponseItem>

    @POST("api/Transaction/GetTransactionDetailsByTransactionCode")
    suspend fun paymentTransaction(@Body transactionDetailsItem: TransactionDetailsItem): Response<TransactionDetailsResponseItem>

    @POST("api/General/GetCancelResionList")
    suspend fun cancelReason(@Body cancelReasonItem: CancelReasonItem): Response<CancelReasonResponseItem>

    @POST("api/General/GetCancelRequestList")
    suspend fun getCancelRequest(@Body getCancelRequestItem: GetCancelRequestItem): Response<GetCancelResponseItem>

    @POST("api/Transaction/PopulateTransactionListForCancellation")
    suspend fun populateCancel(@Body populateCancelItem: PopulateCancelItem): Response<PopulateCancelResponseItem>

    @POST("api/General/SaveCancelRequest")
    suspend fun saveCancelRequest(@Body saveCancelRequestItem: SaveCancelRequestItem): Response<SaveCancelResponseItem>

    @POST("api/General/LoadQueryList")
    suspend fun query(@Body queryItem: QueryItem): Response<QueryResponseItem>

    @POST("api/General/LoadQueryDataByComplainId")
    suspend fun queryMessage(@Body queryMessageItem: QueryMessageItem): Response<QueryMessageResponseItem>

    @POST("api/General/Dropdown")
    suspend fun queryType(@Body queryTypeItem: QueryTypeItem): Response<QueryTypeResponseItem>

    @POST("api/General/SaveQuery")
    suspend fun addQuery(@Body addQueryItem: AddQueryItem): Response<AddQueryResponseItem>

    @POST("api/General/SaveUserMessage")
    suspend fun addMessage(@Body addMessageItem: AddMessageItem): Response<AddMessageResponseItem>

    @POST("api/Home/ChangePassword")
    suspend fun changePassword(@Body changePasswordItem: ChangePasswordItem): Response<ChangePasswordResponseItem>

    @POST("api/General/CommunicationPreferencesUpdate")
    suspend fun marketing(@Body marketingItem: MarketingItem): Response<MarketingResponseItem>

    @POST("api/General/Encrypt")
    suspend fun encrypt(@Body encryptItem: EncryptItem): Response<EncryptResponseItem>

    @POST("api/General/Encrypt")
    suspend fun encryptForCreateReceipt(@Body encryptItemForCreateReceipt: EncryptItemForCreateReceipt): Response<EncryptResponseItemForCreateReceipt>

    @POST("api/Payment/EmerchantCreateTxnResponse")
    suspend fun emp(@Body empItem: EmpItem): Response<EmpResponseItem>

    @POST("api/Home/SaveConsumerId")
    suspend fun saveConsumer(@Body saveConsumerItem: SaveConsumerItem): Response<SaveConsumerResponseItem>

    @POST("api/Home/GetConsumerId")
    suspend fun consumer(@Body consumerItem: ConsumerItem): Response<ConsumerResponseItem>

    @POST("api/General/LoadSlideMessage")
    suspend fun percentage(@Body percentageItem: PercentageItem): Response<PercentageResponseItem>

    @POST("api/Transaction/ApplyPromo")
    suspend fun promo(@Body promoItem: PromoItem): Response<PromoResponseItem>

    @GET("Receipt/CreateReceipt")
    suspend fun createReceipt(@Query("TransactionId") transactionId: String): Response<CreateReceiptResponse>

    @POST("api/Home/ForgotPassword")
    suspend fun forgotPassword(@Body forgotPasswordItem: ForgotPasswordItem): Response<ForgotPasswordResponseItem>

    @POST("api/Home/OtpValidation")
    suspend fun otpValidation(@Body otpValidationItem: OtpValidationItem): Response<OtpValidationResponseItem>

    @POST("api/Home/SetPassword")
    suspend fun setPassword(@Body setPasswordItem: SetPasswordItem): Response<SetPasswordResponseItem>

    @POST("api/Home/ForgotPassword")
    suspend fun phoneVerification(@Body forgotPasswordItem: ForgotPasswordItem): Response<ForgotPasswordResponseItem>

    @POST("api/Home/GenerateOtpForMobileOrEmailVarify")
    suspend fun phoneVerify(@Body phoneVerifyItem: PhoneVerifyItem): Response<PhoneVerifyResponseItem>

    @POST("api/Home/MobileOrEmailVarificationUpdate")
    suspend fun phoneOtpVerify(@Body phoneOtpVerifyItem: PhoneOtpVerifyItem): Response<PhoneOtpVerifyResponseItem>

    @POST("api/General/IsRequireDocForTransaction")
    suspend fun requireDocument(@Body requireDocumentItem: RequireDocumentItem): Response<RequireDocumentResponseItem>

    @GET("api/General/GetDynamicMsg/{message}")
    suspend fun bankTransactionMessage(@Path("message") message: String): Response<BankTransactionMessage>

    @GET("api/General/GetDynamicMsg/{message}")
    suspend fun registrationMessage(@Path("message") message: String): Response<RegistrationResponseMessage>

    @POST("api/Home/VerifyRemitErpCusForRegistration")
    suspend fun sendMigrationOtp(@Body sendOtpItem: SendOtpItem): Response<SendOtpResponse>

    @POST("api/Home/SetPasswordRemitErpCustomer")
    suspend fun setPasswordMigration(@Body setPasswordItemMigration: SetPasswordItemMigration): Response<SetPasswordResponseItemMigration>

    @GET("api/General/GetDynamicMsg/{message}")
    suspend fun support(@Path("message") message: String): Response<SupportResponseItem>

    @GET("api/General/GetDynamicMsg/{message}")
    suspend fun requireDocMsg(@Path("message") message: String): Response<RequireDocMsg>

}

