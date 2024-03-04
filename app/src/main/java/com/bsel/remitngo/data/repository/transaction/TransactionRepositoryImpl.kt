package com.bsel.remitngo.data.repository.transaction

import android.util.Log
import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem
import com.bsel.remitngo.data.repository.transaction.dataSource.TransactionRemoteDataSource
import com.bsel.remitngo.domain.repository.TransactionRepository

class TransactionRepositoryImpl(private val transactionRemoteDataSource: TransactionRemoteDataSource) :
    TransactionRepository {

    override suspend fun transaction(transactionItem: TransactionItem): TransactionResponseItem? {
        return try {
            val response = transactionRemoteDataSource.transaction(transactionItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to transaction: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error transaction: ${exception.message}", exception)
            null
        }
    }

}
