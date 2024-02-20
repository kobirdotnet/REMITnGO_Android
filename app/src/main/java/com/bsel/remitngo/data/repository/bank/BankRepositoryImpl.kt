package com.bsel.remitngo.data.repository.bank

import android.util.Log
import com.bsel.remitngo.data.model.bank.BankItem
import com.bsel.remitngo.data.model.bank.BankResponseItem
import com.bsel.remitngo.data.model.branch.BranchItem
import com.bsel.remitngo.data.model.branch.BranchResponseItem
import com.bsel.remitngo.data.model.district.DistrictItem
import com.bsel.remitngo.data.model.district.DistrictResponseItem
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.data.model.division.DivisionResponseItem
import com.bsel.remitngo.data.repository.bank.dataSource.BankRemoteDataSource
import com.bsel.remitngo.domain.repository.BankRepository

class BankRepositoryImpl(private val bankRemoteDataSource: BankRemoteDataSource) :
    BankRepository {

    override suspend fun bank(bankItem: BankItem): BankResponseItem? {
        return try {
            val response = bankRemoteDataSource.bank(bankItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to bank: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error bank: ${exception.message}", exception)
            null
        }
    }

    override suspend fun division(divisionItem: DivisionItem): DivisionResponseItem? {
        return try {
            val response = bankRemoteDataSource.division(divisionItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to division: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error division: ${exception.message}", exception)
            null
        }
    }

    override suspend fun district(districtItem: DistrictItem): DistrictResponseItem? {
        return try {
            val response = bankRemoteDataSource.district(districtItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to district: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error district: ${exception.message}", exception)
            null
        }
    }

    override suspend fun branch(branchItem: BranchItem): BranchResponseItem? {
        return try {
            val response = bankRemoteDataSource.branch(branchItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to branch: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error branch: ${exception.message}", exception)
            null
        }
    }

}
