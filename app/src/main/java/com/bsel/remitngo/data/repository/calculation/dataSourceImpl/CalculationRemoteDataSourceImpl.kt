package com.bsel.remitngo.data.repository.calculation.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateResponseItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentResponseItem
import com.bsel.remitngo.data.repository.calculation.dataSource.CalculationRemoteDataSource
import retrofit2.Response

class CalculationRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    CalculationRemoteDataSource {

    override suspend fun payingAgent(payingAgentItem: PayingAgentItem): Response<PayingAgentResponseItem> {
        return remitNgoService.payingAgent(payingAgentItem)
    }

    override suspend fun calculateRate(calculateRateItem: CalculateRateItem): Response<CalculateRateResponseItem> {
        return remitNgoService.calculateRate(calculateRateItem)
    }

}







