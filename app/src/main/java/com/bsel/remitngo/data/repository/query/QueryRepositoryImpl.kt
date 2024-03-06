package com.bsel.remitngo.data.repository.query

import android.util.Log
import com.bsel.remitngo.data.model.query.QueryItem
import com.bsel.remitngo.data.model.query.QueryResponseItem
import com.bsel.remitngo.data.model.query.add_message.AddMessageItem
import com.bsel.remitngo.data.model.query.add_message.AddMessageResponseItem
import com.bsel.remitngo.data.model.query.add_query.AddQueryItem
import com.bsel.remitngo.data.model.query.add_query.AddQueryResponseItem
import com.bsel.remitngo.data.model.query.query_message.QueryMessageItem
import com.bsel.remitngo.data.model.query.query_message.QueryMessageResponseItem
import com.bsel.remitngo.data.model.query.query_type.QueryTypeItem
import com.bsel.remitngo.data.model.query.query_type.QueryTypeResponseItem
import com.bsel.remitngo.data.repository.query.dataSource.QueryRemoteDataSource
import com.bsel.remitngo.domain.repository.QueryRepository

class QueryRepositoryImpl(private val queryRemoteDataSource: QueryRemoteDataSource) :
    QueryRepository {

    override suspend fun query(queryItem: QueryItem): QueryResponseItem? {
        return try {
            val response = queryRemoteDataSource.query(queryItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to query: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error query: ${exception.message}", exception)
            null
        }
    }

    override suspend fun queryMessage(queryMessageItem: QueryMessageItem): QueryMessageResponseItem? {
        return try {
            val response = queryRemoteDataSource.queryMessage(queryMessageItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to queryMessage: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error queryMessage: ${exception.message}", exception)
            null
        }
    }

    override suspend fun queryType(queryTypeItem: QueryTypeItem): QueryTypeResponseItem? {
        return try {
            val response = queryRemoteDataSource.queryType(queryTypeItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to queryType: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error queryType: ${exception.message}", exception)
            null
        }
    }

    override suspend fun addQuery(addQueryItem: AddQueryItem): AddQueryResponseItem? {
        return try {
            val response = queryRemoteDataSource.addQuery(addQueryItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to addQuery: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error addQuery: ${exception.message}", exception)
            null
        }
    }

    override suspend fun addMessage(addMessageItem: AddMessageItem): AddMessageResponseItem? {
        return try {
            val response = queryRemoteDataSource.addMessage(addMessageItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to addMessage: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error addMessage: ${exception.message}", exception)
            null
        }
    }

}
