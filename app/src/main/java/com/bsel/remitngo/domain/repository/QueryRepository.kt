package com.bsel.remitngo.domain.repository

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

interface QueryRepository {

    suspend fun query(queryItem: QueryItem): QueryResponseItem?
    suspend fun queryMessage(queryMessageItem: QueryMessageItem): QueryMessageResponseItem?
    suspend fun queryType(queryTypeItem: QueryTypeItem): QueryTypeResponseItem?
    suspend fun addQuery(addQueryItem: AddQueryItem): AddQueryResponseItem?
    suspend fun addMessage(addMessageItem: AddMessageItem): AddMessageResponseItem?

}

