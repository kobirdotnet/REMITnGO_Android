package com.bsel.remitngo.data.repository.transaction.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
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
import retrofit2.Response

class TransactionRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    TransactionRemoteDataSource {

    override suspend fun consumer(consumerItem: ConsumerItem): Response<ConsumerResponseItem> {
        return remitNgoService.consumer(consumerItem)
    }

    override suspend fun saveConsumer(saveConsumerItem: SaveConsumerItem): Response<SaveConsumerResponseItem> {
        return remitNgoService.saveConsumer(saveConsumerItem)
    }

    override suspend fun emp(empItem: EmpItem): Response<EmpResponseItem> {
        return remitNgoService.emp(empItem)
    }

    override suspend fun transaction(transactionItem: TransactionItem): Response<TransactionResponseItem> {
        return remitNgoService.transaction(transactionItem)
    }

    override suspend fun transactionDetails(transactionDetailsItem: TransactionDetailsItem): Response<TransactionDetailsResponseItem> {
        return remitNgoService.transactionDetails(transactionDetailsItem)
    }

    override suspend fun encrypt(encryptItem: EncryptItem): Response<EncryptResponseItem> {
        return remitNgoService.encrypt(encryptItem)
    }
    override suspend fun encryptForCreateReceipt(encryptItemForCreateReceipt: EncryptItemForCreateReceipt): Response<EncryptResponseItemForCreateReceipt> {
        return remitNgoService.encryptForCreateReceipt(encryptItemForCreateReceipt)
    }

}







