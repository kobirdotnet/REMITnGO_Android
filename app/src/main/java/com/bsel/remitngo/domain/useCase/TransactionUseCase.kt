package com.bsel.remitngo.domain.useCase

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
import com.bsel.remitngo.data.model.profile.ProfileItem
import com.bsel.remitngo.data.model.profile.ProfileResponseItem
import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsResponseItem
import com.bsel.remitngo.domain.repository.TransactionRepository

class TransactionUseCase(private val transactionRepository: TransactionRepository) {
    suspend fun execute(profileItem: ProfileItem): ProfileResponseItem? {
        return transactionRepository.profile(profileItem)
    }
    suspend fun execute(consumerItem: ConsumerItem): ConsumerResponseItem? {
        return transactionRepository.consumer(consumerItem)
    }

    suspend fun execute(saveConsumerItem: SaveConsumerItem): SaveConsumerResponseItem? {
        return transactionRepository.saveConsumer(saveConsumerItem)
    }

    suspend fun execute(empItem: EmpItem): EmpResponseItem? {
        return transactionRepository.emp(empItem)
    }

    suspend fun execute(transactionItem: TransactionItem): TransactionResponseItem? {
        return transactionRepository.transaction(transactionItem)
    }
    suspend fun execute(transactionDetailsItem: TransactionDetailsItem): TransactionDetailsResponseItem? {
        return transactionRepository.transactionDetails(transactionDetailsItem)
    }
    suspend fun execute(encryptItem: EncryptItem): EncryptResponseItem? {
        return transactionRepository.encrypt(encryptItem)
    }
    suspend fun executeEncryptForCreateReceipt(encryptItemForCreateReceipt: EncryptItemForCreateReceipt): EncryptResponseItemForCreateReceipt? {
        return transactionRepository.encryptForCreateReceipt(encryptItemForCreateReceipt)
    }

}

