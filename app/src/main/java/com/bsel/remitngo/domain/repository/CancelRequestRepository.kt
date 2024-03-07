package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonItem
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonResponseItem
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelResponseItem

interface CancelRequestRepository {

    suspend fun cancelReason(cancelReasonItem: CancelReasonItem): CancelReasonResponseItem?
    suspend fun getCancelRequest(getCancelRequestItem: GetCancelRequestItem): GetCancelResponseItem?
    suspend fun populateCancel(populateCancelItem: PopulateCancelItem): PopulateCancelResponseItem?
    suspend fun saveCancelRequest(saveCancelRequestItem: SaveCancelRequestItem): SaveCancelResponseItem?

}

