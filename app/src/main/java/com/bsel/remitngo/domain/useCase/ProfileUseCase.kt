package com.bsel.remitngo.domain.useCase

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
import com.bsel.remitngo.domain.repository.ProfileRepository

class ProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend fun execute(profileItem: ProfileItem): ProfileResponseItem? {
        return profileRepository.profile(profileItem)
    }

    suspend fun execute(updateProfileItem: UpdateProfileItem): UpdateProfileResponseItem? {
        return profileRepository.updateProfile(updateProfileItem)
    }
    suspend fun execute(postCodeItem: PostCodeItem): PostCodeResponseItem? {
        return profileRepository.postCode(postCodeItem)
    }

    suspend fun execute(ukDivisionItem: UkDivisionItem): UkDivisionResponseItem? {
        return profileRepository.ukDivision(ukDivisionItem)
    }
    suspend fun execute(countyItem: CountyItem): CountyResponseItem? {
        return profileRepository.county(countyItem)
    }
    suspend fun execute(cityItem: CityItem): CityResponseItem? {
        return profileRepository.city(cityItem)
    }

    suspend fun execute(annualIncomeItem: AnnualIncomeItem): AnnualIncomeResponseItem? {
        return profileRepository.annualIncome(annualIncomeItem)
    }
    suspend fun execute(sourceOfIncomeItem: SourceOfIncomeItem): SourceOfIncomeResponseItem? {
        return profileRepository.sourceOfIncome(sourceOfIncomeItem)
    }

    suspend fun execute(occupationTypeItem: OccupationTypeItem): OccupationTypeResponseItem? {
        return profileRepository.occupationType(occupationTypeItem)
    }

    suspend fun execute(occupationItem: OccupationItem): OccupationResponseItem? {
        return profileRepository.occupation(occupationItem)
    }

    suspend fun execute(nationalityItem: NationalityItem): NationalityResponseItem? {
        return profileRepository.nationality(nationalityItem)
    }

    suspend fun execute(otpValidationItem: OtpValidationItem): OtpValidationResponseItem? {
        return profileRepository.otpValidation(otpValidationItem)
    }

    suspend fun execute(forgotPasswordItem: ForgotPasswordItem): ForgotPasswordResponseItem? {
        return profileRepository.phoneVerification(forgotPasswordItem)
    }
    suspend fun execute(phoneVerifyItem: PhoneVerifyItem): PhoneVerifyResponseItem? {
        return profileRepository.phoneVerify(phoneVerifyItem)
    }
    suspend fun execute(phoneOtpVerifyItem: PhoneOtpVerifyItem): PhoneOtpVerifyResponseItem? {
        return profileRepository.phoneOtpVerify(phoneOtpVerifyItem)
    }

}

