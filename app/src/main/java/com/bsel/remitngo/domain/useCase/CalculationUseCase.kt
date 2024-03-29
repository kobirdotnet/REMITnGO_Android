package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateResponseItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentResponseItem
import com.bsel.remitngo.data.model.percentage.PercentageItem
import com.bsel.remitngo.data.model.percentage.PercentageResponseItem
import com.bsel.remitngo.domain.repository.CalculationRepository

class CalculationUseCase(private val calculationRepository: CalculationRepository) {

    suspend fun execute(payingAgentItem: PayingAgentItem): PayingAgentResponseItem? {
        return calculationRepository.payingAgent(payingAgentItem)
    }
    suspend fun execute(calculateRateItem: CalculateRateItem): CalculateRateResponseItem? {
        return calculationRepository.calculateRate(calculateRateItem)
    }
    suspend fun execute(percentageItem: PercentageItem): PercentageResponseItem? {
        return calculationRepository.percentage(percentageItem)
    }

}

