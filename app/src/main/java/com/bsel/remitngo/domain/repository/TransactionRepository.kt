package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem

interface TransactionRepository {

    suspend fun transaction(transactionItem: TransactionItem): TransactionResponseItem?

}

