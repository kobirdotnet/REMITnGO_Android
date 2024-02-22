package com.bsel.remitngo.presentation.ui.bank

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
import com.bsel.remitngo.data.model.district.DistrictItem
import com.bsel.remitngo.data.model.district.DistrictResponseItem
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.data.model.division.DivisionResponseItem
import com.bsel.remitngo.domain.useCase.BankUseCase
import kotlinx.coroutines.launch

class BankViewModel(private val bankUseCase: BankUseCase) : ViewModel() {

    private val _getBankResult = MutableLiveData<GetBankResponseItem?>()
    val getBankResult: LiveData<GetBankResponseItem?> = _getBankResult

    fun getBank(getBankItem: GetBankItem) {
        viewModelScope.launch {
            val result = bankUseCase.execute(getBankItem)
            _getBankResult.value = result
        }
    }

    private val _saveBankResult = MutableLiveData<SaveBankResponseItem?>()
    val saveBankResult: LiveData<SaveBankResponseItem?> = _saveBankResult

    fun saveBank(saveBankItem: SaveBankItem) {
        viewModelScope.launch {
            val result = bankUseCase.execute(saveBankItem)
            _saveBankResult.value = result
        }
    }

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

    private val _districtResult = MutableLiveData<DistrictResponseItem?>()
    val districtResult: LiveData<DistrictResponseItem?> = _districtResult

    fun district(districtItem: DistrictItem) {
        viewModelScope.launch {
            val result = bankUseCase.execute(districtItem)
            _districtResult.value = result
        }
    }

    private val _branchResult = MutableLiveData<BranchResponseItem?>()
    val branchResult: LiveData<BranchResponseItem?> = _branchResult

    fun branch(branchItem: BranchItem) {
        viewModelScope.launch {
            val result = bankUseCase.execute(branchItem)
            _branchResult.value = result
        }
    }

}