package com.bsel.remitngo.data.repository.support.supportDataSource

import com.bsel.remitngo.data.model.support.SupportResponseItem
import retrofit2.Response

interface SupportRemoteDataSource {
    suspend fun support(message: String): Response<SupportResponseItem>
}