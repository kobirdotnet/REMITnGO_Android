package com.bsel.remitngo.data.repository.beneficiary

import android.util.Log
import com.bsel.remitngo.data.model.beneficiary.BeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.BeneficiaryResponseItem
import com.bsel.remitngo.data.model.gender.GenderItem
import com.bsel.remitngo.data.model.gender.GenderResponseItem
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.data.model.reason.ReasonResponseItem
import com.bsel.remitngo.data.model.relation.RelationItem
import com.bsel.remitngo.data.model.relation.RelationResponseItem
import com.bsel.remitngo.data.repository.beneficiary.dataSource.BeneficiaryRemoteDataSource
import com.bsel.remitngo.domain.repository.BeneficiaryRepository

class BeneficiaryRepositoryImpl(private val beneficiaryRemoteDataSource: BeneficiaryRemoteDataSource) :
    BeneficiaryRepository {

    override suspend fun beneficiary(beneficiaryItem: BeneficiaryItem): BeneficiaryResponseItem? {
        return try {
            val response = beneficiaryRemoteDataSource.beneficiary(beneficiaryItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to Beneficiary: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error Beneficiary: ${exception.message}", exception)
            null
        }
    }

    override suspend fun relation(relationItem: RelationItem): RelationResponseItem? {
        return try {
            val response = beneficiaryRemoteDataSource.relation(relationItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to relation: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error relation: ${exception.message}", exception)
            null
        }
    }

    override suspend fun reason(reasonItem: ReasonItem): ReasonResponseItem? {
        return try {
            val response = beneficiaryRemoteDataSource.reason(reasonItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to reason: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error reason: ${exception.message}", exception)
            null
        }
    }

    override suspend fun gender(genderItem: GenderItem): GenderResponseItem? {
        return try {
            val response = beneficiaryRemoteDataSource.gender(genderItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to gender: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error gender: ${exception.message}", exception)
            null
        }
    }

}
