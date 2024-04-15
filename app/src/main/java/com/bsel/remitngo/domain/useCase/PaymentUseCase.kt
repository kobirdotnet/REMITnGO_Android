package com.bsel.remitngo.domain.useCase

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
import com.bsel.remitngo.domain.repository.PaymentRepository

class PaymentUseCase(private val paymentRepository: PaymentRepository) {

    suspend fun execute(paymentItem: PaymentItem): PaymentResponseItem? {
        return paymentRepository.payment(paymentItem)
    }

    suspend fun execute(transactionCode: String): PaymentStatusResponse? {
        return paymentRepository.paymentStatus(transactionCode)
    }
    suspend fun execute(encryptItem: EncryptItem): EncryptResponseItem? {
        return paymentRepository.encrypt(encryptItem)
    }
    suspend fun execute(empItem: EmpItem): EmpResponseItem? {
        return paymentRepository.emp(empItem)
    }
    suspend fun execute(saveConsumerItem: SaveConsumerItem): SaveConsumerResponseItem? {
        return paymentRepository.saveConsumer(saveConsumerItem)
    }
    suspend fun execute(consumerItem: ConsumerItem): ConsumerResponseItem? {
        return paymentRepository.consumer(consumerItem)
    }

    suspend fun execute(calculateRateItem: CalculateRateItem): CalculateRateResponseItem? {
        return paymentRepository.rateCalculate(calculateRateItem)
    }

    suspend fun execute(transactionDetailsItem: TransactionDetailsItem): TransactionDetailsResponseItem? {
        return paymentRepository.paymentTransaction(transactionDetailsItem)
    }

    suspend fun execute(reasonItem: ReasonItem): ReasonResponseItem? {
        return paymentRepository.reason(reasonItem)
    }

    suspend fun execute(sourceOfIncomeItem: SourceOfIncomeItem): SourceOfIncomeResponseItem? {
        return paymentRepository.sourceOfIncome(sourceOfIncomeItem)
    }
    suspend fun execute(promoItem: PromoItem): PromoResponseItem? {
        return paymentRepository.promo(promoItem)
    }

    suspend fun executeCreateReceipt(transactionId:String): CreateReceiptResponse?{
        return paymentRepository.createReceipt(transactionId)
    }

    suspend fun execute(profileItem: ProfileItem): ProfileResponseItem? {
        return paymentRepository.profile(profileItem)
    }
    suspend fun execute(phoneVerifyItem: PhoneVerifyItem): PhoneVerifyResponseItem? {
        return paymentRepository.phoneVerify(phoneVerifyItem)
    }
    suspend fun execute(phoneOtpVerifyItem: PhoneOtpVerifyItem): PhoneOtpVerifyResponseItem? {
        return paymentRepository.phoneOtpVerify(phoneOtpVerifyItem)
    }

}

