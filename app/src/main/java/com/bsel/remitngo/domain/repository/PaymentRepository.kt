package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateResponseItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerResponseItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerResponseItem
import com.bsel.remitngo.data.model.createReceipt.CreateReceiptResponse
import com.bsel.remitngo.data.model.emp.EmpItem
import com.bsel.remitngo.data.model.emp.EmpResponseItem
import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.encript.EncryptResponseItem
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.model.payment.PaymentStatusResponse
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeResponseItem
import com.bsel.remitngo.data.model.promoCode.PromoItem
import com.bsel.remitngo.data.model.promoCode.PromoResponseItem
import com.bsel.remitngo.data.model.reason.ReasonItem
import com.bsel.remitngo.data.model.reason.ReasonResponseItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsResponseItem

interface PaymentRepository {

    suspend fun payment(paymentItem: PaymentItem): PaymentResponseItem?
    suspend fun paymentStatus(transactionCode: String): PaymentStatusResponse?
    suspend fun encrypt(encryptItem: EncryptItem): EncryptResponseItem?
    suspend fun emp(empItem: EmpItem): EmpResponseItem?
    suspend fun saveConsumer(saveConsumerItem: SaveConsumerItem): SaveConsumerResponseItem?
    suspend fun consumer(consumerItem: ConsumerItem): ConsumerResponseItem?

    suspend fun rateCalculate(calculateRateItem: CalculateRateItem): CalculateRateResponseItem?

    suspend fun paymentTransaction(transactionDetailsItem: TransactionDetailsItem): TransactionDetailsResponseItem?

    suspend fun reason(reasonItem: ReasonItem): ReasonResponseItem?
    suspend fun sourceOfIncome(sourceOfIncomeItem: SourceOfIncomeItem): SourceOfIncomeResponseItem?
    suspend fun promo(promoItem: PromoItem): PromoResponseItem?

    suspend fun createReceipt(transactionId: String): CreateReceiptResponse?

}

