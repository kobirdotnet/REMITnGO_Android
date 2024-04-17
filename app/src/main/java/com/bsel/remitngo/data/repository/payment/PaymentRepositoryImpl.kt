package com.bsel.remitngo.data.repository.payment

import android.util.Log
import com.bsel.remitngo.data.model.bankTransactionMessage.BankTransactionMessage
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateResponseItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerResponseItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerResponseItem
import com.bsel.remitngo.data.model.createReceipt.CreateReceiptResponse
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentItem
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentResponseItem
import com.bsel.remitngo.data.model.emp.EmpItem
import com.bsel.remitngo.data.model.emp.EmpResponseItem
import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.encript.EncryptItemForCreateReceipt
import com.bsel.remitngo.data.model.encript.EncryptResponseItem
import com.bsel.remitngo.data.model.encript.EncryptResponseItemForCreateReceipt
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.model.payment.PaymentStatusResponse
import com.bsel.remitngo.data.model.phoneVerification.PhoneOtpVerifyItem
import com.bsel.remitngo.data.model.phoneVerification.PhoneOtpVerifyResponseItem
import com.bsel.remitngo.data.model.phoneVerification.PhoneVerifyItem
import com.bsel.remitngo.data.model.phoneVerification.PhoneVerifyResponseItem
import com.bsel.remitngo.data.model.profile.ProfileItem
import com.bsel.remitngo.data.model.profile.ProfileResponseItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeResponseItem
import com.bsel.remitngo.data.model.promoCode.PromoItem
import com.bsel.remitngo.data.model.promoCode.PromoResponseItem
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.data.model.reason.ReasonResponseItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsResponseItem
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
                Log.e("MyTag", "Failed to payment: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
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
                Log.e("MyTag", "Failed to payment status: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
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
                Log.e("MyTag", "Failed to encrypt: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            Log.e("MyTag", "Error encrypt: ${exception.message}", exception)
            null
        }
    }

    override suspend fun emp(empItem: EmpItem): EmpResponseItem? {
        return try {
            val response = paymentRemoteDataSource.emp(empItem)
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

    override suspend fun saveConsumer(saveConsumerItem: SaveConsumerItem): SaveConsumerResponseItem? {
        return try {
            val response = paymentRemoteDataSource.saveConsumer(saveConsumerItem)
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

    override suspend fun consumer(consumerItem: ConsumerItem): ConsumerResponseItem? {
        return try {
            val response = paymentRemoteDataSource.consumer(consumerItem)
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

    override suspend fun rateCalculate(calculateRateItem: CalculateRateItem): CalculateRateResponseItem? {
        return try {
            val response = paymentRemoteDataSource.rateCalculate(calculateRateItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to rateCalculate: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error rateCalculate: ${exception.message}", exception)
            null
        }
    }

    override suspend fun paymentTransaction(transactionDetailsItem: TransactionDetailsItem): TransactionDetailsResponseItem? {
        return try {
            val response = paymentRemoteDataSource.paymentTransaction(transactionDetailsItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to paymentTransaction: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error paymentTransaction: ${exception.message}", exception)
            null
        }
    }

    override suspend fun reason(reasonItem: ReasonItem): ReasonResponseItem? {
        return try {
            val response = paymentRemoteDataSource.reason(reasonItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to reason: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error reason: ${exception.message}", exception)
            null
        }
    }

    override suspend fun sourceOfIncome(sourceOfIncomeItem: SourceOfIncomeItem): SourceOfIncomeResponseItem? {
        return try {
            val response = paymentRemoteDataSource.sourceOfIncome(sourceOfIncomeItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to sourceOfIncome: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error sourceOfIncome: ${exception.message}", exception)
            null
        }
    }

    override suspend fun promo(promoItem: PromoItem): PromoResponseItem? {
        return try {
            val response = paymentRemoteDataSource.promo(promoItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to promo: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error promo: ${exception.message}", exception)
            null
        }
    }

    override suspend fun createReceipt(transactionId: String): CreateReceiptResponse? {
        return try {
            val response = paymentRemoteDataSource.createReceipt(transactionId)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to createReceipt: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error createReceipt: ${exception.message}", exception)
            null
        }
    }

    override suspend fun profile(profileItem: ProfileItem): ProfileResponseItem? {
        return try {
            val response = paymentRemoteDataSource.profile(profileItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to Profile: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error Profile: ${exception.message}", exception)
            null
        }
    }
    override suspend fun phoneVerify(phoneVerifyItem: PhoneVerifyItem): PhoneVerifyResponseItem? {
        return try {
            val response = paymentRemoteDataSource.phoneVerify(phoneVerifyItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to phoneVerify: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error phoneVerify: ${exception.message}", exception)
            null
        }
    }

    override suspend fun phoneOtpVerify(phoneOtpVerifyItem: PhoneOtpVerifyItem): PhoneOtpVerifyResponseItem? {
        return try {
            val response = paymentRemoteDataSource.phoneOtpVerify(phoneOtpVerifyItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to phoneOtpVerify: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error phoneOtpVerify: ${exception.message}", exception)
            null
        }
    }

    override suspend fun requireDocument(requireDocumentItem: RequireDocumentItem): RequireDocumentResponseItem? {
        return try {
            val response = paymentRemoteDataSource.requireDocument(requireDocumentItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to requireDocument: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error requireDocument: ${exception.message}", exception)
            null
        }
    }

    override suspend fun encryptForCreateReceipt(encryptItemForCreateReceipt: EncryptItemForCreateReceipt): EncryptResponseItemForCreateReceipt? {
        return try {
            val response = paymentRemoteDataSource.encryptForCreateReceipt(encryptItemForCreateReceipt)
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

    override suspend fun bankTransactionMessage(message: String): BankTransactionMessage? {
        return try {
            val response = paymentRemoteDataSource.bankTransactionMessage(message)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("MyTag", "Failed to message: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            Log.e("MyTag", "Error message: ${exception.message}", exception)
            null
        }
    }

}
