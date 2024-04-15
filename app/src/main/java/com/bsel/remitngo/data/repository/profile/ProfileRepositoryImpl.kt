package com.bsel.remitngo.data.repository.profile

import android.util.Log
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
import com.bsel.remitngo.domain.repository.ProfileRepository

class ProfileRepositoryImpl(private val profileRemoteDataSource: ProfileRemoteDataSource) :
    ProfileRepository {

    override suspend fun profile(profileItem: ProfileItem): ProfileResponseItem? {
        return try {
            val response = profileRemoteDataSource.profile(profileItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to Profile: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error Profile: ${exception.message}", exception)
            null
        }
    }

    override suspend fun updateProfile(updateProfileItem: UpdateProfileItem): UpdateProfileResponseItem? {
        return try {
            val response = profileRemoteDataSource.updateProfile(updateProfileItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to update profile: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error update profile: ${exception.message}", exception)
            null
        }
    }

    override suspend fun postCode(postCodeItem: PostCodeItem): PostCodeResponseItem? {
        return try {
            val response = profileRemoteDataSource.postCode(postCodeItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to postCode: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error postCode: ${exception.message}", exception)
            null
        }
    }

    override suspend fun ukDivision(ukDivisionItem: UkDivisionItem): UkDivisionResponseItem? {
        return try {
            val response = profileRemoteDataSource.ukDivision(ukDivisionItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to ukDivision: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error ukDivision: ${exception.message}", exception)
            null
        }
    }

    override suspend fun county(countyItem: CountyItem): CountyResponseItem? {
        return try {
            val response = profileRemoteDataSource.county(countyItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to county: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error county: ${exception.message}", exception)
            null
        }
    }

    override suspend fun city(cityItem: CityItem): CityResponseItem? {
        return try {
            val response = profileRemoteDataSource.city(cityItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to city: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error city: ${exception.message}", exception)
            null
        }
    }

    override suspend fun annualIncome(annualIncomeItem: AnnualIncomeItem): AnnualIncomeResponseItem? {
        return try {
            val response = profileRemoteDataSource.annualIncome(annualIncomeItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to annualIncome: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error annualIncome: ${exception.message}", exception)
            null
        }
    }

    override suspend fun sourceOfIncome(sourceOfIncomeItem: SourceOfIncomeItem): SourceOfIncomeResponseItem? {
        return try {
            val response = profileRemoteDataSource.sourceOfIncome(sourceOfIncomeItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to sourceOfIncome: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error sourceOfIncome: ${exception.message}", exception)
            null
        }
    }

    override suspend fun occupationType(occupationTypeItem: OccupationTypeItem): OccupationTypeResponseItem? {
        return try {
            val response = profileRemoteDataSource.occupationType(occupationTypeItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to occupationType: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error occupationType: ${exception.message}", exception)
            null
        }
    }

    override suspend fun occupation(occupationItem: OccupationItem): OccupationResponseItem? {
        return try {
            val response = profileRemoteDataSource.occupation(occupationItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to occupation: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error occupation: ${exception.message}", exception)
            null
        }
    }

    override suspend fun nationality(nationalityItem: NationalityItem): NationalityResponseItem? {
        return try {
            val response = profileRemoteDataSource.nationality(nationalityItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to nationality: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error nationality: ${exception.message}", exception)
            null
        }
    }

    override suspend fun otpValidation(otpValidationItem: OtpValidationItem): OtpValidationResponseItem? {
        return try {
            val response = profileRemoteDataSource.otpValidation(otpValidationItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to otpValidation: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error otpValidation: ${exception.message}", exception)
            null
        }
    }

    override suspend fun phoneVerification(forgotPasswordItem: ForgotPasswordItem): ForgotPasswordResponseItem? {
        return try {
            val response = profileRemoteDataSource.phoneVerification(forgotPasswordItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to phoneVerification: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error phoneVerification: ${exception.message}", exception)
            null
        }
    }

    override suspend fun phoneVerify(phoneVerifyItem: PhoneVerifyItem): PhoneVerifyResponseItem? {
        return try {
            val response = profileRemoteDataSource.phoneVerify(phoneVerifyItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to phoneVerify: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error phoneVerify: ${exception.message}", exception)
            null
        }
    }

    override suspend fun phoneOtpVerify(phoneOtpVerifyItem: PhoneOtpVerifyItem): PhoneOtpVerifyResponseItem? {
        return try {
            val response = profileRemoteDataSource.phoneOtpVerify(phoneOtpVerifyItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to phoneOtpVerify: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error phoneOtpVerify: ${exception.message}", exception)
            null
        }
    }

}
