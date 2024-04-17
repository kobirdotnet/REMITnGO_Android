package com.bsel.remitngo.data.repository.transaction

import android.util.Log
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerResponseItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerResponseItem
import com.bsel.remitngo.data.model.emp.EmpItem
import com.bsel.remitngo.data.model.emp.EmpResponseItem
import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.encript.EncryptItemForCreateReceipt
import com.bsel.remitngo.data.model.encript.EncryptResponseItem
import com.bsel.remitngo.data.model.encript.EncryptResponseItemForCreateReceipt
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsResponseItem
import com.bsel.remitngo.data.repository.transaction.dataSource.TransactionRemoteDataSource
import com.bsel.remitngo.domain.repository.TransactionRepository

class TransactionRepositoryImpl(private val transactionRemoteDataSource: TransactionRemoteDataSource) :
    TransactionRepository {

    override suspend fun consumer(consumerItem: ConsumerItem): ConsumerResponseItem? {
        return try {
            val response = transactionRemoteDataSource.consumer(consumerItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("MyTag", "Failed to consumer: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            Log.e("MyTag", "Error consumer: ${exception.message}", exception)
            null
        }
    }

    override suspend fun saveConsumer(saveConsumerItem: SaveConsumerItem): SaveConsumerResponseItem? {
        return try {
            val response = transactionRemoteDataSource.saveConsumer(saveConsumerItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("MyTag", "Failed to saveConsumer: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            Log.e("MyTag", "Error saveConsumer: ${exception.message}", exception)
            null
        }
    }

    override suspend fun emp(empItem: EmpItem): EmpResponseItem? {
        return try {
            val response = transactionRemoteDataSource.emp(empItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("MyTag", "Failed to emp: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            Log.e("MyTag", "Error emp: ${exception.message}", exception)
            null
        }
    }

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

    override suspend fun transactionDetails(transactionDetailsItem: TransactionDetailsItem): TransactionDetailsResponseItem? {
        return try {
            val response = transactionRemoteDataSource.transactionDetails(transactionDetailsItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to transactionDetails: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error transactionDetails: ${exception.message}", exception)
            null
        }
    }

    override suspend fun encrypt(encryptItem: EncryptItem): EncryptResponseItem? {
        return try {
            val response = transactionRemoteDataSource.encrypt(encryptItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("MyTag", "Failed to encrypt: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            Log.e("MyTag", "Error encrypt: ${exception.message}", exception)
            null
        }
    }
    override suspend fun encryptForCreateReceipt(encryptItemForCreateReceipt: EncryptItemForCreateReceipt): EncryptResponseItemForCreateReceipt? {
        return try {
            val response = transactionRemoteDataSource.encryptForCreateReceipt(encryptItemForCreateReceipt)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("MyTag", "Failed to encryptForCreateReceipt: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            Log.e("MyTag", "Error encryptForCreateReceipt: ${exception.message}", exception)
            null
        }
    }

}
