package com.bsel.remitngo.data.repository.payment.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.encript.EncryptResponseItem
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.model.payment.PaymentStatusResponse
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

}







