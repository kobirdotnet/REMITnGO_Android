package com.bsel.remitngo.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsel.remitngo.data.model.profile.ProfileItem
import com.bsel.remitngo.data.model.profile.ProfileResponseItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeResponseItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityResponseItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationResponseItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeResponseItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeResponseItem
import com.bsel.remitngo.domain.useCase.ProfileUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileUseCase: ProfileUseCase) : ViewModel() {

    private val _profileResult = MutableLiveData<ProfileResponseItem?>()
    val profileResult: LiveData<ProfileResponseItem?> = _profileResult

    fun profile(profileItem: ProfileItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(profileItem)
            _profileResult.value = result
        }
    }

    private val _annualIncomeResult = MutableLiveData<AnnualIncomeResponseItem?>()
    val annualIncomeResult: LiveData<AnnualIncomeResponseItem?> = _annualIncomeResult

    fun annualIncome(annualIncomeItem: AnnualIncomeItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(annualIncomeItem)
            _annualIncomeResult.value = result
        }
    }

    private val _sourceOfIncomeResult = MutableLiveData<SourceOfIncomeResponseItem?>()
    val sourceOfIncomeResult: LiveData<SourceOfIncomeResponseItem?> = _sourceOfIncomeResult

    fun sourceOfIncome(sourceOfIncomeItem: SourceOfIncomeItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(sourceOfIncomeItem)
            _sourceOfIncomeResult.value = result
        }
    }

    private val _occupationTypeResult = MutableLiveData<OccupationTypeResponseItem?>()
    val occupationTypeResult: LiveData<OccupationTypeResponseItem?> = _occupationTypeResult

    fun occupationType(occupationTypeItem: OccupationTypeItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(occupationTypeItem)
            _occupationTypeResult.value = result
        }
    }

    private val _occupationResult = MutableLiveData<OccupationResponseItem?>()
    val occupationResult: LiveData<OccupationResponseItem?> = _occupationResult

    fun occupation(occupationItem: OccupationItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(occupationItem)
            _occupationResult.value = result
        }
    }

    private val _nationalityResult = MutableLiveData<NationalityResponseItem?>()
    val nationalityResult: LiveData<NationalityResponseItem?> = _nationalityResult

    fun nationality(nationalityItem: NationalityItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(nationalityItem)
            _nationalityResult.value = result
        }
    }

}