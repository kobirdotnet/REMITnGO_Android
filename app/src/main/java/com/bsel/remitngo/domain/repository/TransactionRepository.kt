package com.bsel.remitngo.domain.repository

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

interface TransactionRepository {

    suspend fun consumer(consumerItem: ConsumerItem): ConsumerResponseItem?
    suspend fun saveConsumer(saveConsumerItem: SaveConsumerItem): SaveConsumerResponseItem?
    suspend fun emp(empItem: EmpItem): EmpResponseItem?
    suspend fun transaction(transactionItem: TransactionItem): TransactionResponseItem?
    suspend fun transactionDetails(transactionDetailsItem: TransactionDetailsItem): TransactionDetailsResponseItem?
    suspend fun encrypt(encryptItem: EncryptItem): EncryptResponseItem?
    suspend fun encryptForCreateReceipt(encryptItemForCreateReceipt: EncryptItemForCreateReceipt): EncryptResponseItemForCreateReceipt?

}

