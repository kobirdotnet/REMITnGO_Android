package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonItem
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonResponseItem
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelResponseItem
import com.bsel.remitngo.domain.repository.CancelRequestRepository

class CancelRequestUseCase(private val cancelRequestRepository: CancelRequestRepository) {

    suspend fun execute(cancelReasonItem: CancelReasonItem): CancelReasonResponseItem? {
        return cancelRequestRepository.cancelReason(cancelReasonItem)
    }

    suspend fun execute(getCancelRequestItem: GetCancelRequestItem): GetCancelResponseItem? {
        return cancelRequestRepository.getCancelRequest(getCancelRequestItem)
    }

    suspend fun execute(populateCancelItem: PopulateCancelItem): PopulateCancelResponseItem? {
        return cancelRequestRepository.populateCancel(populateCancelItem)
    }

    suspend fun execute(saveCancelRequestItem: SaveCancelRequestItem): SaveCancelResponseItem? {
        return cancelRequestRepository.saveCancelRequest(saveCancelRequestItem)
    }

}

