package com.bsel.remitngo.data.repository.payment.dataSource

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
import retrofit2.Response

interface PaymentRemoteDataSource {

    suspend fun payment(paymentItem: PaymentItem): Response<PaymentResponseItem>
    suspend fun paymentStatus(transactionCode: String): Response<PaymentStatusResponse>
    suspend fun encrypt(encryptItem: EncryptItem): Response<EncryptResponseItem>
    suspend fun emp(empItem: EmpItem): Response<EmpResponseItem>
    suspend fun saveConsumer(saveConsumerItem: SaveConsumerItem): Response<SaveConsumerResponseItem>
    suspend fun consumer(consumerItem: ConsumerItem): Response<ConsumerResponseItem>
    suspend fun rateCalculate(calculateRateItem: CalculateRateItem): Response<CalculateRateResponseItem>
    suspend fun paymentTransaction(transactionDetailsItem: TransactionDetailsItem): Response<TransactionDetailsResponseItem>

    suspend fun reason(reasonItem: ReasonItem): Response<ReasonResponseItem>
    suspend fun sourceOfIncome(sourceOfIncomeItem: SourceOfIncomeItem): Response<SourceOfIncomeResponseItem>
    suspend fun promo(promoItem: PromoItem): Response<PromoResponseItem>
    suspend fun createReceipt(transactionId: String): Response<CreateReceiptResponse>

    suspend fun profile(profileItem: ProfileItem): Response<ProfileResponseItem>
    suspend fun phoneVerify(phoneVerifyItem: PhoneVerifyItem): Response<PhoneVerifyResponseItem>
    suspend fun phoneOtpVerify(phoneOtpVerifyItem: PhoneOtpVerifyItem): Response<PhoneOtpVerifyResponseItem>

}





