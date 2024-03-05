package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsResponseItem
import com.bsel.remitngo.domain.repository.TransactionRepository

class TransactionUseCase(private val transactionRepository: TransactionRepository) {

    suspend fun execute(transactionItem: TransactionItem): TransactionResponseItem? {
        return transactionRepository.transaction(transactionItem)
    }
    suspend fun execute(transactionDetailsItem: TransactionDetailsItem): TransactionDetailsResponseItem? {
        return transactionRepository.transactionDetails(transactionDetailsItem)
    }

}

