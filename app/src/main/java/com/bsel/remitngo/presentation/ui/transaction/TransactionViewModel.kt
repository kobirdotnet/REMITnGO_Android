package com.bsel.remitngo.presentation.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsResponseItem
import com.bsel.remitngo.domain.useCase.TransactionUseCase
import kotlinx.coroutines.launch

class TransactionViewModel(private val transactionUseCase: TransactionUseCase) : ViewModel() {

    private val _transactionResult = MutableLiveData<TransactionResponseItem?>()
    val transactionResult: LiveData<TransactionResponseItem?> = _transactionResult

    fun transaction(transactionItem: TransactionItem) {
        viewModelScope.launch {
            val result = transactionUseCase.execute(transactionItem)
            _transactionResult.value = result
        }
    }
    private val _transactionDetailsResult = MutableLiveData<TransactionDetailsResponseItem?>()
    val transactionDetailsResult: LiveData<TransactionDetailsResponseItem?> = _transactionDetailsResult

    fun transactionDetails(transactionDetailsItem: TransactionDetailsItem) {
        viewModelScope.launch {
            val result = transactionUseCase.execute(transactionDetailsItem)
            _transactionDetailsResult.value = result
        }
    }

}