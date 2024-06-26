package com.bsel.remitngo.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsel.remitngo.data.model.forgotPassword.ForgotPasswordItem
import com.bsel.remitngo.data.model.forgotPassword.ForgotPasswordResponseItem
import com.bsel.remitngo.data.model.forgotPassword.OtpValidationItem
import com.bsel.remitngo.data.model.forgotPassword.OtpValidationResponseItem
import com.bsel.remitngo.data.model.phoneVerification.PhoneOtpVerifyItem
import com.bsel.remitngo.data.model.phoneVerification.PhoneOtpVerifyResponseItem
import com.bsel.remitngo.data.model.phoneVerification.PhoneVerifyItem
import com.bsel.remitngo.data.model.phoneVerification.PhoneVerifyResponseItem
import com.bsel.remitngo.data.model.profile.ProfileItem
import com.bsel.remitngo.data.model.profile.ProfileResponseItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeResponseItem
import com.bsel.remitngo.data.model.profile.city.CityItem
import com.bsel.remitngo.data.model.profile.city.CityResponseItem
import com.bsel.remitngo.data.model.profile.county.CountyItem
import com.bsel.remitngo.data.model.profile.county.CountyResponseItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityResponseItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationResponseItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeResponseItem
import com.bsel.remitngo.data.model.profile.postCode.PostCodeItem
import com.bsel.remitngo.data.model.profile.postCode.PostCodeResponseItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeResponseItem
import com.bsel.remitngo.data.model.profile.uk_division.UkDivisionItem
import com.bsel.remitngo.data.model.profile.uk_division.UkDivisionResponseItem
import com.bsel.remitngo.data.model.profile.updateProfile.UpdateProfileItem
import com.bsel.remitngo.data.model.profile.updateProfile.UpdateProfileResponseItem
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

    private val _updateProfileResult = MutableLiveData<UpdateProfileResponseItem?>()
    val updateProfileResult: LiveData<UpdateProfileResponseItem?> = _updateProfileResult

    fun updateProfile(updateProfileItem: UpdateProfileItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(updateProfileItem)
            _updateProfileResult.value = result
        }
    }

    private val _postCodeResult = MutableLiveData<PostCodeResponseItem?>()
    val postCodeResult: LiveData<PostCodeResponseItem?> = _postCodeResult

    fun postCode(postCodeItem: PostCodeItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(postCodeItem)
            _postCodeResult.value = result
        }
    }

    private val _ukDivisionResult = MutableLiveData<UkDivisionResponseItem?>()
    val ukDivisionResult: LiveData<UkDivisionResponseItem?> = _ukDivisionResult

    fun ukDivision(ukDivisionItem: UkDivisionItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(ukDivisionItem)
            _ukDivisionResult.value = result
        }
    }

    private val _countyResult = MutableLiveData<CountyResponseItem?>()
    val countyResult: LiveData<CountyResponseItem?> = _countyResult

    fun county(countyItem: CountyItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(countyItem)
            _countyResult.value = result
        }
    }

    private val _cityResult = MutableLiveData<CityResponseItem?>()
    val cityResult: LiveData<CityResponseItem?> = _cityResult

    fun city(cityItem: CityItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(cityItem)
            _cityResult.value = result
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

    private val _otpValidationResult = MutableLiveData<OtpValidationResponseItem?>()
    val otpValidationResult: LiveData<OtpValidationResponseItem?> = _otpValidationResult

    fun otpValidation(otpValidationItem: OtpValidationItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(otpValidationItem)
            _otpValidationResult.value = result
        }
    }

    private val _phoneVerificationResult = MutableLiveData<ForgotPasswordResponseItem?>()
    val phoneVerificationResult: LiveData<ForgotPasswordResponseItem?> = _phoneVerificationResult

    fun phoneVerification(forgotPasswordItem: ForgotPasswordItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(forgotPasswordItem)
            _phoneVerificationResult.value = result
        }
    }

    private val _phoneVerifyResult = MutableLiveData<PhoneVerifyResponseItem?>()
    val phoneVerifyResult: LiveData<PhoneVerifyResponseItem?> = _phoneVerifyResult

    fun phoneVerify(phoneVerifyItem: PhoneVerifyItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(phoneVerifyItem)
            _phoneVerifyResult.value = result
        }
    }

    private val _phoneOtpVerifyResult = MutableLiveData<PhoneOtpVerifyResponseItem?>()
    val phoneOtpVerifyResult: LiveData<PhoneOtpVerifyResponseItem?> = _phoneOtpVerifyResult

    fun phoneOtpVerify(phoneOtpVerifyItem: PhoneOtpVerifyItem) {
        viewModelScope.launch {
            val result = profileUseCase.execute(phoneOtpVerifyItem)
            _phoneOtpVerifyResult.value = result
        }
    }

}