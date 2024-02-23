package com.bsel.remitngo.data.repository.calculation.dataSource

import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateResponseItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentResponseItem
import retrofit2.Response

interface CalculationRemoteDataSource {

    suspend fun payingAgent(payingAgentItem: PayingAgentItem): Response<PayingAgentResponseItem>

    suspend fun calculateRate(calculateRateItem: CalculateRateItem): Response<CalculateRateResponseItem>

}





