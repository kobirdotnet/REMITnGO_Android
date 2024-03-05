package com.bsel.remitngo.data.repository.cancel_request.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonItem
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonResponseItem
import com.bsel.remitngo.data.model.cancel_request.get_cancel_request.GetCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.get_cancel_request.GetCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelResponseItem
import com.bsel.remitngo.data.repository.cancel_request.dataSource.CancelRequestRemoteDataSource
import retrofit2.Response

class CancelRequestRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    CancelRequestRemoteDataSource {

    override suspend fun cancelReason(cancelReasonItem: CancelReasonItem): Response<CancelReasonResponseItem> {
        return remitNgoService.cancelReason(cancelReasonItem)
    }

    override suspend fun getCancelRequest(getCancelRequestItem: GetCancelRequestItem): Response<GetCancelResponseItem> {
        return remitNgoService.getCancelRequest(getCancelRequestItem)
    }

    override suspend fun populateCancel(populateCancelItem: PopulateCancelItem): Response<PopulateCancelResponseItem> {
        return remitNgoService.populateCancel(populateCancelItem)
    }

    override suspend fun saveCancelRequest(saveCancelRequestItem: SaveCancelRequestItem): Response<SaveCancelResponseItem> {
        return remitNgoService.saveCancelRequest(saveCancelRequestItem)
    }

}







