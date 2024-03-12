package com.bsel.remitngo.data.repository.payment.dataSource

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
import retrofit2.Response

interface PaymentRemoteDataSource {

    suspend fun payment(paymentItem: PaymentItem): Response<PaymentResponseItem>

    suspend fun paymentStatus(transactionCode: String): Response<PaymentStatusResponse>
    suspend fun encrypt(encryptItem: EncryptItem): Response<EncryptResponseItem>
    suspend fun emp(empItem: EmpItem): Response<EmpResponseItem>
    suspend fun saveConsumer(saveConsumerItem: SaveConsumerItem): Response<SaveConsumerResponseItem>
    suspend fun consumer(consumerItem: ConsumerItem): Response<ConsumerResponseItem>

}





