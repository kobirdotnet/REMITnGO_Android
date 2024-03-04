package com.bsel.remitngo.data.repository.transaction.dataSource

import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem
import retrofit2.Response

interface TransactionRemoteDataSource {

    suspend fun transaction(transactionItem: TransactionItem): Response<TransactionResponseItem>

}





