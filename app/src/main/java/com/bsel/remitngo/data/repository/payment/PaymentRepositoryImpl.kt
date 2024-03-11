package com.bsel.remitngo.data.repository.payment

import android.util.Log
import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.encript.EncryptResponseItem
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.model.payment.PaymentStatusResponse
import com.bsel.remitngo.data.repository.payment.dataSource.PaymentRemoteDataSource
import com.bsel.remitngo.domain.repository.PaymentRepository

class PaymentRepositoryImpl(private val paymentRemoteDataSource: PaymentRemoteDataSource) :
    PaymentRepository {

    override suspend fun payment(paymentItem: PaymentItem): PaymentResponseItem? {
        return try {
            val response = paymentRemoteDataSource.payment(paymentItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to payment: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error payment: ${exception.message}", exception)
            null
        }
    }

    override suspend fun paymentStatus(transactionCode: String): PaymentStatusResponse? {
        return try {
            val response = paymentRemoteDataSource.paymentStatus(transactionCode)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to payment status: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error payment status: ${exception.message}", exception)
            null
        }
    }

    override suspend fun encrypt(encryptItem: EncryptItem): EncryptResponseItem? {
        return try {
            val response = paymentRemoteDataSource.encrypt(encryptItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to encrypt: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error encrypt: ${exception.message}", exception)
            null
        }
    }

}
