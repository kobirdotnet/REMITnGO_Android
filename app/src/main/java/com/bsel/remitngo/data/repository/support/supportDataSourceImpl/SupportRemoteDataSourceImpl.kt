package com.bsel.remitngo.data.repository.support.supportDataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.support.SupportResponseItem
import com.bsel.remitngo.data.repository.support.supportDataSource.SupportRemoteDataSource
import retrofit2.Response

class SupportRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    SupportRemoteDataSource {
    override suspend fun support(message: String): Response<SupportResponseItem> {
        return remitNgoService.support(message)
    }
}