package com.bsel.remitngo.data.repository.query.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
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
import retrofit2.Response

class QueryRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    QueryRemoteDataSource {

    override suspend fun query(queryItem: QueryItem): Response<QueryResponseItem> {
        return remitNgoService.query(queryItem)
    }

    override suspend fun queryMessage(queryMessageItem: QueryMessageItem): Response<QueryMessageResponseItem> {
        return remitNgoService.queryMessage(queryMessageItem)
    }

    override suspend fun queryType(queryTypeItem: QueryTypeItem): Response<QueryTypeResponseItem> {
        return remitNgoService.queryType(queryTypeItem)
    }

    override suspend fun addQuery(addQueryItem: AddQueryItem): Response<AddQueryResponseItem> {
        return remitNgoService.addQuery(addQueryItem)
    }
    override suspend fun addMessage(addMessageItem: AddMessageItem): Response<AddMessageResponseItem> {
        return remitNgoService.addMessage(addMessageItem)
    }

}







