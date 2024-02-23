package com.bsel.remitngo.data.repository.calculation

import android.util.Log
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateResponseItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentResponseItem
import com.bsel.remitngo.data.repository.calculation.dataSource.CalculationRemoteDataSource
import com.bsel.remitngo.domain.repository.CalculationRepository

class CalculationRepositoryImpl(private val calculationRemoteDataSource: CalculationRemoteDataSource) :
    CalculationRepository {

    override suspend fun payingAgent(payingAgentItem: PayingAgentItem): PayingAgentResponseItem? {
        return try {
            val response = calculationRemoteDataSource.payingAgent(payingAgentItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to paying agent: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error paying agent: ${exception.message}", exception)
            null
        }
    }

    override suspend fun calculateRate(calculateRateItem: CalculateRateItem): CalculateRateResponseItem? {
        return try {
            val response = calculationRemoteDataSource.calculateRate(calculateRateItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to calculate rate: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error calculate rate: ${exception.message}", exception)
            null
        }
    }

}
