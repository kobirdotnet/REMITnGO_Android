package com.bsel.remitngo.data.repository.profile.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
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
import com.bsel.remitngo.data.repository.profile.dataSource.ProfileRemoteDataSource
import retrofit2.Response

class ProfileRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    ProfileRemoteDataSource {

    override suspend fun profile(profileItem: ProfileItem): Response<ProfileResponseItem> {
        return remitNgoService.profile(profileItem)
    }

    override suspend fun updateProfile(updateProfileItem: UpdateProfileItem): Response<UpdateProfileResponseItem> {
        return remitNgoService.updateProfile(updateProfileItem)
    }

    override suspend fun postCode(postCodeItem: PostCodeItem): Response<PostCodeResponseItem> {
        return remitNgoService.postCode(postCodeItem)
    }

    override suspend fun ukDivision(ukDivisionItem: UkDivisionItem): Response<UkDivisionResponseItem> {
        return remitNgoService.ukDivision(ukDivisionItem)
    }

    override suspend fun county(countyItem: CountyItem): Response<CountyResponseItem> {
        return remitNgoService.county(countyItem)
    }

    override suspend fun city(cityItem: CityItem): Response<CityResponseItem> {
        return remitNgoService.city(cityItem)
    }

    override suspend fun annualIncome(annualIncomeItem: AnnualIncomeItem): Response<AnnualIncomeResponseItem> {
        return remitNgoService.annualIncome(annualIncomeItem)
    }

    override suspend fun sourceOfIncome(sourceOfIncomeItem: SourceOfIncomeItem): Response<SourceOfIncomeResponseItem> {
        return remitNgoService.sourceOfIncome(sourceOfIncomeItem)
    }

    override suspend fun occupationType(occupationTypeItem: OccupationTypeItem): Response<OccupationTypeResponseItem> {
        return remitNgoService.occupationType(occupationTypeItem)
    }

    override suspend fun occupation(occupationItem: OccupationItem): Response<OccupationResponseItem> {
        return remitNgoService.occupation(occupationItem)
    }

    override suspend fun nationality(nationalityItem: NationalityItem): Response<NationalityResponseItem> {
        return remitNgoService.nationality(nationalityItem)
    }

    override suspend fun otpValidation(otpValidationItem: OtpValidationItem): Response<OtpValidationResponseItem> {
        return remitNgoService.otpValidation(otpValidationItem)
    }

    override suspend fun phoneVerification(forgotPasswordItem: ForgotPasswordItem): Response<ForgotPasswordResponseItem> {
        return remitNgoService.forgotPassword(forgotPasswordItem)
    }

    override suspend fun phoneVerify(phoneVerifyItem: PhoneVerifyItem): Response<PhoneVerifyResponseItem> {
        return remitNgoService.phoneVerify(phoneVerifyItem)
    }

    override suspend fun phoneOtpVerify(phoneOtpVerifyItem: PhoneOtpVerifyItem): Response<PhoneOtpVerifyResponseItem> {
        return remitNgoService.phoneOtpVerify(phoneOtpVerifyItem)
    }

}







