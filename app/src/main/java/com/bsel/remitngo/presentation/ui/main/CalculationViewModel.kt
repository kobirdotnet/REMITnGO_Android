package com.bsel.remitngo.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsel.remitngo.data.model.bank.BankItem
import com.bsel.remitngo.data.model.bank.BankResponseItem
import com.bsel.remitngo.data.model.bank.get_bank_account.GetBankItem
import com.bsel.remitngo.data.model.bank.get_bank_account.GetBankResponseItem
import com.bsel.remitngo.data.model.bank.save_bank_account.SaveBankItem
import com.bsel.remitngo.data.model.bank.save_bank_account.SaveBankResponseItem
import com.bsel.remitngo.data.model.branch.BranchItem
import com.bsel.remitngo.data.model.branch.BranchResponseItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateResponseItem
import com.bsel.remitngo.data.model.district.DistrictItem
import com.bsel.remitngo.data.model.district.DistrictResponseItem
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.data.model.division.DivisionResponseItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentItem
import com.bsel.remitngo.data.model.paying_agent.PayingAgentResponseItem
import com.bsel.remitngo.domain.useCase.BankUseCase
import com.bsel.remitngo.domain.useCase.CalculationUseCase
import kotlinx.coroutines.launch

class CalculationViewModel(private val calculationUseCase: CalculationUseCase) : ViewModel() {

    private val _payingAgentResult = MutableLiveData<PayingAgentResponseItem?>()
    val payingAgentResult: LiveData<PayingAgentResponseItem?> = _payingAgentResult

    fun payingAgent(payingAgentItem: PayingAgentItem) {
        viewModelScope.launch {
            val result = calculationUseCase.execute(payingAgentItem)
            _payingAgentResult.value = result
        }
    }

    private val _calculateRateResult = MutableLiveData<CalculateRateResponseItem?>()
    val calculateRateResult: LiveData<CalculateRateResponseItem?> = _calculateRateResult

    fun calculateRate(calculateRateItem: CalculateRateItem) {
        viewModelScope.launch {
            val result = calculationUseCase.execute(calculateRateItem)
            _calculateRateResult.value = result
        }
    }

}