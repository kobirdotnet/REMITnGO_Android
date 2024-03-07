package com.bsel.remitngo.data.repository.cancel_request

import android.util.Log
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonItem
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonResponseItem
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.cancel_request.GetCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelItem
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelResponseItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelResponseItem
import com.bsel.remitngo.data.repository.cancel_request.dataSource.CancelRequestRemoteDataSource
import com.bsel.remitngo.domain.repository.CancelRequestRepository

class CancelRequestRepositoryImpl(private val cancelRequestRemoteDataSource: CancelRequestRemoteDataSource) :
    CancelRequestRepository {

    override suspend fun cancelReason(cancelReasonItem: CancelReasonItem): CancelReasonResponseItem? {
        return try {
            val response = cancelRequestRemoteDataSource.cancelReason(cancelReasonItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to cancelReason: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error cancelReason: ${exception.message}", exception)
            null
        }
    }

    override suspend fun getCancelRequest(getCancelRequestItem: GetCancelRequestItem): GetCancelResponseItem? {
        return try {
            val response = cancelRequestRemoteDataSource.getCancelRequest(getCancelRequestItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to getCancelRequest: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error getCancelRequest: ${exception.message}", exception)
            null
        }
    }

    override suspend fun populateCancel(populateCancelItem: PopulateCancelItem): PopulateCancelResponseItem? {
        return try {
            val response = cancelRequestRemoteDataSource.populateCancel(populateCancelItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to populateCancel: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error populateCancel: ${exception.message}", exception)
            null
        }
    }

    override suspend fun saveCancelRequest(saveCancelRequestItem: SaveCancelRequestItem): SaveCancelResponseItem? {
        return try {
            val response = cancelRequestRemoteDataSource.saveCancelRequest(saveCancelRequestItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to saveCancelRequest: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error saveCancelRequest: ${exception.message}", exception)
            null
        }
    }


}
