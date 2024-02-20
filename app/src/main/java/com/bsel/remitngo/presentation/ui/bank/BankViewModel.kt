package com.bsel.remitngo.presentation.ui.bank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsel.remitngo.data.model.bank.BankItem
import com.bsel.remitngo.data.model.bank.BankResponseItem
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.data.model.division.DivisionResponseItem
import com.bsel.remitngo.domain.useCase.BankUseCase
import kotlinx.coroutines.launch

class BankViewModel(private val bankUseCase: BankUseCase) : ViewModel() {

    private val _bankResult = MutableLiveData<BankResponseItem?>()
    val bankResult: LiveData<BankResponseItem?> = _bankResult

    fun bank(bankItem: BankItem) {
        viewModelScope.launch {
            val result = bankUseCase.execute(bankItem)
            _bankResult.value = result
        }
    }

    private val _divisionResult = MutableLiveData<DivisionResponseItem?>()
    val divisionResult: LiveData<DivisionResponseItem?> = _divisionResult

    fun division(divisionItem: DivisionItem) {
        viewModelScope.launch {
            val result = bankUseCase.execute(divisionItem)
            _divisionResult.value = result
        }
    }

//    private val _bankResult = MutableLiveData<BankResponseItem?>()
//    val bankResult: LiveData<BankResponseItem?> = _bankResult
//
//    fun bank(bankItem: BankItem) {
//        viewModelScope.launch {
//            val result = bankUseCase.execute(bankItem)
//            _bankResult.value = result
//        }
//    }
//
//    private val _bankResult = MutableLiveData<BankResponseItem?>()
//    val bankResult: LiveData<BankResponseItem?> = _bankResult
//
//    fun bank(bankItem: BankItem) {
//        viewModelScope.launch {
//            val result = bankUseCase.execute(bankItem)
//            _bankResult.value = result
//        }
//    }

}