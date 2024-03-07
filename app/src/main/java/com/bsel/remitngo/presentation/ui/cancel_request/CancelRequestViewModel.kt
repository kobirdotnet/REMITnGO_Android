package com.bsel.remitngo.presentation.ui.cancel_request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonItem
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonResponseItem
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelResponseItem
import com.bsel.remitngo.domain.useCase.CancelRequestUseCase
import kotlinx.coroutines.launch

class CancelRequestViewModel(private val cancelRequestUseCase: CancelRequestUseCase) : ViewModel() {

    private val _cancelReasonResult = MutableLiveData<CancelReasonResponseItem?>()
    val cancelReasonResult: LiveData<CancelReasonResponseItem?> = _cancelReasonResult

    fun cancelReason(cancelReasonItem: CancelReasonItem) {
        viewModelScope.launch {
            val result = cancelRequestUseCase.execute(cancelReasonItem)
            _cancelReasonResult.value = result
        }
    }

    private val _getCancelRequestResult = MutableLiveData<GetCancelResponseItem?>()
    val getCancelRequestResult: LiveData<GetCancelResponseItem?> = _getCancelRequestResult

    fun getCancelRequest(getCancelRequestItem: GetCancelRequestItem) {
        viewModelScope.launch {
            val result = cancelRequestUseCase.execute(getCancelRequestItem)
            _getCancelRequestResult.value = result
        }
    }

    private val _populateCancelResult = MutableLiveData<PopulateCancelResponseItem?>()
    val populateCancelResult: LiveData<PopulateCancelResponseItem?> = _populateCancelResult

    fun populateCancel(populateCancelItem: PopulateCancelItem) {
        viewModelScope.launch {
            val result = cancelRequestUseCase.execute(populateCancelItem)
            _populateCancelResult.value = result
        }
    }

    private val _saveCancelRequestResult = MutableLiveData<SaveCancelResponseItem?>()
    val saveCancelRequestResult: LiveData<SaveCancelResponseItem?> = _saveCancelRequestResult

    fun saveCancelRequest(saveCancelRequestItem: SaveCancelRequestItem) {
        viewModelScope.launch {
            val result = cancelRequestUseCase.execute(saveCancelRequestItem)
            _saveCancelRequestResult.value = result
        }
    }

}