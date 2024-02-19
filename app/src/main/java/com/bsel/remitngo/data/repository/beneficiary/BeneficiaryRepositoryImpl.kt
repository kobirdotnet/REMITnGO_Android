package com.bsel.remitngo.data.repository.beneficiary

import android.util.Log
import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryResponseItem
import com.bsel.remitngo.data.repository.beneficiary.dataSource.BeneficiaryRemoteDataSource
import com.bsel.remitngo.domain.repository.BeneficiaryRepository

class BeneficiaryRepositoryImpl(private val beneficiaryRemoteDataSource: BeneficiaryRemoteDataSource) :
    BeneficiaryRepository {

    override suspend fun addBeneficiary(addBeneficiaryItem: AddBeneficiaryItem): AddBeneficiaryResponseItem? {
        return try {
            val response = beneficiaryRemoteDataSource.addBeneficiary(addBeneficiaryItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to Add Beneficiary user: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error Add Beneficiary user: ${exception.message}", exception)
            null
        }
    }
}
