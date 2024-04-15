package com.bsel.remitngo.data.repository.profile.dataSource

import com.bsel.remitngo.data.model.forgotPassword.ForgotPasswordItem
import com.bsel.remitngo.data.model.forgotPassword.ForgotPasswordResponseItem
import com.bsel.remitngo.data.model.forgotPassword.OtpValidationItem
import com.bsel.remitngo.data.model.forgotPassword.OtpValidationResponseItem
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
import retrofit2.Response

interface ProfileRemoteDataSource {

    suspend fun profile(profileItem: ProfileItem): Response<ProfileResponseItem>
    suspend fun updateProfile(updateProfileItem: UpdateProfileItem): Response<UpdateProfileResponseItem>
    suspend fun postCode(postCodeItem: PostCodeItem): Response<PostCodeResponseItem>
    suspend fun ukDivision(ukDivisionItem: UkDivisionItem): Response<UkDivisionResponseItem>
    suspend fun county(countyItem: CountyItem): Response<CountyResponseItem>
    suspend fun city(cityItem: CityItem): Response<CityResponseItem>

    suspend fun annualIncome(annualIncomeItem: AnnualIncomeItem): Response<AnnualIncomeResponseItem>

    suspend fun sourceOfIncome(sourceOfIncomeItem: SourceOfIncomeItem): Response<SourceOfIncomeResponseItem>

    suspend fun occupationType(occupationTypeItem: OccupationTypeItem): Response<OccupationTypeResponseItem>

    suspend fun occupation(occupationItem: OccupationItem): Response<OccupationResponseItem>

    suspend fun nationality(nationalityItem: NationalityItem): Response<NationalityResponseItem>

    suspend fun otpValidation(otpValidationItem: OtpValidationItem): Response<OtpValidationResponseItem>

    suspend fun phoneVerification(forgotPasswordItem: ForgotPasswordItem): Response<ForgotPasswordResponseItem>
    suspend fun phoneVerify(phoneVerifyItem: PhoneVerifyItem): Response<PhoneVerifyResponseItem>
    suspend fun phoneOtpVerify(phoneOtpVerifyItem: PhoneOtpVerifyItem): Response<PhoneOtpVerifyResponseItem>

}





