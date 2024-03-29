package com.bsel.remitngo.data.repository.payment.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateResponseItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerResponseItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerResponseItem
import com.bsel.remitngo.data.model.emp.EmpItem
import com.bsel.remitngo.data.model.emp.EmpResponseItem
import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.encript.EncryptResponseItem
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.model.payment.PaymentStatusResponse
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsResponseItem
import com.bsel.remitngo.data.repository.payment.dataSource.PaymentRemoteDataSource
import retrofit2.Response

class PaymentRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    PaymentRemoteDataSource {

    override suspend fun payment(paymentItem: PaymentItem): Response<PaymentResponseItem> {
        return remitNgoService.payment(paymentItem)
    }

    override suspend fun paymentStatus(transactionCode: String): Response<PaymentStatusResponse> {
        return remitNgoService.paymentStatus(transactionCode)
    }

    override suspend fun encrypt(encryptItem: EncryptItem): Response<EncryptResponseItem> {
        return remitNgoService.encrypt(encryptItem)
    }

    override suspend fun emp(empItem: EmpItem): Response<EmpResponseItem> {
        return remitNgoService.emp(empItem)
    }

    override suspend fun saveConsumer(saveConsumerItem: SaveConsumerItem): Response<SaveConsumerResponseItem> {
        return remitNgoService.saveConsumer(saveConsumerItem)
    }

    override suspend fun consumer(consumerItem: ConsumerItem): Response<ConsumerResponseItem> {
        return remitNgoService.consumer(consumerItem)
    }

    override suspend fun rateCalculate(calculateRateItem: CalculateRateItem): Response<CalculateRateResponseItem> {
        return remitNgoService.rateCalculate(calculateRateItem)
    }

    override suspend fun paymentTransaction(transactionDetailsItem: TransactionDetailsItem): Response<TransactionDetailsResponseItem> {
        return remitNgoService.paymentTransaction(transactionDetailsItem)
    }

}







