package com.bsel.remitngo.data.repository.cancel_request.dataSource

import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonItem
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonResponseItem
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelResponseItem
import retrofit2.Response

interface CancelRequestRemoteDataSource {

    suspend fun cancelReason(cancelReasonItem: CancelReasonItem): Response<CancelReasonResponseItem>
    suspend fun getCancelRequest(getCancelRequestItem: GetCancelRequestItem): Response<GetCancelResponseItem>
    suspend fun populateCancel(populateCancelItem: PopulateCancelItem): Response<PopulateCancelResponseItem>
    suspend fun saveCancelRequest(saveCancelRequestItem: SaveCancelRequestItem): Response<SaveCancelResponseItem>

}





