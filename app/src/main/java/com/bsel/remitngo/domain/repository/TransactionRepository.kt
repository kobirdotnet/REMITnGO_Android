package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsResponseItem

interface TransactionRepository {

    suspend fun transaction(transactionItem: TransactionItem): TransactionResponseItem?
    suspend fun transactionDetails(transactionDetailsItem: TransactionDetailsItem): TransactionDetailsResponseItem?

}

