package com.bsel.remitngo.data.repository.transaction.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem
import com.bsel.remitngo.data.repository.transaction.dataSource.TransactionRemoteDataSource
import retrofit2.Response

class TransactionRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    TransactionRemoteDataSource {

    override suspend fun transaction(transactionItem: TransactionItem): Response<TransactionResponseItem> {
        return remitNgoService.transaction(transactionItem)
    }

}







