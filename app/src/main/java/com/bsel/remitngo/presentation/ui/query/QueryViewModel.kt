package com.bsel.remitngo.presentation.ui.query

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.bsel.remitngo.domain.useCase.QueryUseCase
import kotlinx.coroutines.launch

class QueryViewModel(private val queryUseCase: QueryUseCase) : ViewModel() {

    private val _queryResult = MutableLiveData<QueryResponseItem?>()
    val queryResult: LiveData<QueryResponseItem?> = _queryResult

    fun query(queryItem: QueryItem) {
        viewModelScope.launch {
            val result = queryUseCase.execute(queryItem)
            _queryResult.value = result
        }
    }
    private val _queryMessageResult = MutableLiveData<QueryMessageResponseItem?>()
    val queryMessageResult: LiveData<QueryMessageResponseItem?> = _queryMessageResult

    fun queryMessage(queryMessageItem: QueryMessageItem) {
        viewModelScope.launch {
            val result = queryUseCase.execute(queryMessageItem)
            _queryMessageResult.value = result
        }
    }
    private val _queryTypeResult = MutableLiveData<QueryTypeResponseItem?>()
    val queryTypeResult: LiveData<QueryTypeResponseItem?> = _queryTypeResult

    fun queryType(queryTypeItem: QueryTypeItem) {
        viewModelScope.launch {
            val result = queryUseCase.execute(queryTypeItem)
            _queryTypeResult.value = result
        }
    }
    private val _addQueryResult = MutableLiveData<AddQueryResponseItem?>()
    val addQueryResult: LiveData<AddQueryResponseItem?> = _addQueryResult

    fun addQuery(addQueryItem: AddQueryItem) {
        viewModelScope.launch {
            val result = queryUseCase.execute(addQueryItem)
            _addQueryResult.value = result
        }
    }
    private val _addMessageResult = MutableLiveData<AddMessageResponseItem?>()
    val addMessageResult: LiveData<AddMessageResponseItem?> = _addMessageResult

    fun addMessage(addMessageItem: AddMessageItem) {
        viewModelScope.launch {
            val result = queryUseCase.execute(addMessageItem)
            _addMessageResult.value = result
        }
    }

}