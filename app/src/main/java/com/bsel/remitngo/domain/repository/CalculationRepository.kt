package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateResponseItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentResponseItem

interface CalculationRepository {

    suspend fun payingAgent(payingAgentItem: PayingAgentItem): PayingAgentResponseItem?

    suspend fun calculateRate(calculateRateItem: CalculateRateItem): CalculateRateResponseItem?

}

