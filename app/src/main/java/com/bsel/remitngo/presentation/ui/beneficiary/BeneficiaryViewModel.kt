package com.bsel.remitngo.presentation.ui.beneficiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsel.remitngo.data.model.beneficiary.BeneficiaryItem
import com.bsel.remitngo.data.model.beneficiary.BeneficiaryResponseItem
import com.bsel.remitngo.data.model.gender.GenderItem
import com.bsel.remitngo.data.model.gender.GenderResponseItem
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.data.model.reason.ReasonResponseItem
import com.bsel.remitngo.data.model.relation.RelationItem
import com.bsel.remitngo.data.model.relation.RelationResponseItem
import com.bsel.remitngo.domain.useCase.BeneficiaryUseCase
import kotlinx.coroutines.launch

class BeneficiaryViewModel(private val beneficiaryUseCase: BeneficiaryUseCase) : ViewModel() {

    private val _beneficiaryResult = MutableLiveData<BeneficiaryResponseItem?>()
    val beneficiaryResult: LiveData<BeneficiaryResponseItem?> = _beneficiaryResult

    fun beneficiary(beneficiaryItem: BeneficiaryItem) {
        viewModelScope.launch {
            val result = beneficiaryUseCase.execute(beneficiaryItem)
            _beneficiaryResult.value = result
        }
    }

    private val _relationResult = MutableLiveData<RelationResponseItem?>()
    val relationResult: LiveData<RelationResponseItem?> = _relationResult

    fun relation(relationItem: RelationItem) {
        viewModelScope.launch {
            val result = beneficiaryUseCase.execute(relationItem)
            _relationResult.value = result
        }
    }

    private val _reasonResult = MutableLiveData<ReasonResponseItem?>()
    val reasonResult: LiveData<ReasonResponseItem?> = _reasonResult

    fun reason(reasonItem: ReasonItem) {
        viewModelScope.launch {
            val result = beneficiaryUseCase.execute(reasonItem)
            _reasonResult.value = result
        }
    }

    private val _genderResult = MutableLiveData<GenderResponseItem?>()
    val genderResult: LiveData<GenderResponseItem?> = _genderResult

    fun gender(genderItem: GenderItem) {
        viewModelScope.launch {
            val result = beneficiaryUseCase.execute(genderItem)
            _genderResult.value = result
        }
    }

}