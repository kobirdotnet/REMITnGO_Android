package com.bsel.remitngo.domain.useCase

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
import com.bsel.remitngo.domain.repository.QueryRepository

class QueryUseCase(private val queryRepository: QueryRepository) {

    suspend fun execute(queryItem: QueryItem): QueryResponseItem? {
        return queryRepository.query(queryItem)
    }
    suspend fun execute(queryMessageItem: QueryMessageItem): QueryMessageResponseItem? {
        return queryRepository.queryMessage(queryMessageItem)
    }
    suspend fun execute(queryTypeItem: QueryTypeItem): QueryTypeResponseItem? {
        return queryRepository.queryType(queryTypeItem)
    }
    suspend fun execute(addQueryItem: AddQueryItem): AddQueryResponseItem? {
        return queryRepository.addQuery(addQueryItem)
    }
    suspend fun execute(addMessageItem: AddMessageItem): AddMessageResponseItem? {
        return queryRepository.addMessage(addMessageItem)
    }

}

