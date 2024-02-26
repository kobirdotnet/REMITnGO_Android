package com.bsel.remitngo.data.repository.payment.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.repository.payment.dataSource.PaymentRemoteDataSource
import retrofit2.Response

class PaymentRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    PaymentRemoteDataSource {

    override suspend fun payment(paymentItem: PaymentItem): Response<PaymentResponseItem> {
        return remitNgoService.payment(paymentItem)
    }

}







