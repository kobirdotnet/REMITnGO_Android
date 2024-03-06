package com.bsel.remitngo.data.repository.query.dataSource

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
import retrofit2.Response

interface QueryRemoteDataSource {

    suspend fun query(queryItem: QueryItem): Response<QueryResponseItem>
    suspend fun queryMessage(queryMessageItem: QueryMessageItem): Response<QueryMessageResponseItem>
    suspend fun queryType(queryTypeItem: QueryTypeItem): Response<QueryTypeResponseItem>
    suspend fun addQuery(addQueryItem: AddQueryItem): Response<AddQueryResponseItem>
    suspend fun addMessage(addMessageItem: AddMessageItem): Response<AddMessageResponseItem>

}





