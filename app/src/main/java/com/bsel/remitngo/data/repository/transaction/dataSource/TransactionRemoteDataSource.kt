package com.bsel.remitngo.data.repository.transaction.dataSource

import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsResponseItem
import retrofit2.Response

interface TransactionRemoteDataSource {

    suspend fun transaction(transactionItem: TransactionItem): Response<TransactionResponseItem>
    suspend fun transactionDetails(transactionDetailsItem: TransactionDetailsItem): Response<TransactionDetailsResponseItem>

}





