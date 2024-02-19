package com.bsel.remitngo.presentation.ui.beneficiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryResponseItem
import com.bsel.remitngo.domain.useCase.BeneficiaryUseCase
import kotlinx.coroutines.launch

class BeneficiaryViewModel(private val beneficiaryUseCase: BeneficiaryUseCase) : ViewModel() {

    private val _addBeneficiaryResult = MutableLiveData<AddBeneficiaryResponseItem?>()
    val addBeneficiaryResult: LiveData<AddBeneficiaryResponseItem?> = _addBeneficiaryResult

    fun addBeneficiary(addBeneficiaryItem: AddBeneficiaryItem) {
        viewModelScope.launch {
            val result = beneficiaryUseCase.execute(addBeneficiaryItem)
            _addBeneficiaryResult.value = result
        }
    }

}