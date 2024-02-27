package com.bsel.remitngo.data.repository.profile

import android.util.Log
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

}
