package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem
import com.bsel.remitngo.domain.repository.TransactionRepository

class TransactionUseCase(private val transactionRepository: TransactionRepository) {

    suspend fun execute(transactionItem: TransactionItem): TransactionResponseItem? {
        return transactionRepository.transaction(transactionItem)
    }

}

