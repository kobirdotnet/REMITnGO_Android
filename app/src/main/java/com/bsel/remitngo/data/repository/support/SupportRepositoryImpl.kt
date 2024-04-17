package com.bsel.remitngo.data.repository.support

import android.util.Log
import com.bsel.remitngo.data.model.support.SupportResponseItem
import com.bsel.remitngo.data.repository.support.supportDataSource.SupportRemoteDataSource
import com.bsel.remitngo.domain.repository.SupportRepository

class SupportRepositoryImpl(private val supportRemoteDataSource: SupportRemoteDataSource) :
    SupportRepository {
    override suspend fun support(message: String): SupportResponseItem? {
        return try {
            val response = supportRemoteDataSource.support(message)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("MyTag", "Failed to message: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            Log.e("MyTag", "Error message: ${exception.message}", exception)
            null
        }
    }
}