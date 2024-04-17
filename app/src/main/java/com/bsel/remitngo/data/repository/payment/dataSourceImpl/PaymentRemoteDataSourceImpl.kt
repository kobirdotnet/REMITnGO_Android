package com.bsel.remitngo.data.repository.payment.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.bankTransactionMessage.BankTransactionMessage
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateItem
import com.bsel.remitngo.data.model.calculate_rate.CalculateRateResponseItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerItem
import com.bsel.remitngo.data.model.consumer.consumer.ConsumerResponseItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerItem
import com.bsel.remitngo.data.model.consumer.save_consumer.SaveConsumerResponseItem
import com.bsel.remitngo.data.model.createReceipt.CreateReceiptResponse
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentItem
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentResponseItem
import com.bsel.remitngo.data.model.emp.EmpItem
import com.bsel.remitngo.data.model.emp.EmpResponseItem
import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.encript.EncryptItemForCreateReceipt
import com.bsel.remitngo.data.model.encript.EncryptResponseItem
import com.bsel.remitngo.data.model.encript.EncryptResponseItemForCreateReceipt
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

    override suspend fun reason(reasonItem: ReasonItem): Response<ReasonResponseItem> {
        return remitNgoService.reason(reasonItem)
    }

    override suspend fun sourceOfIncome(sourceOfIncomeItem: SourceOfIncomeItem): Response<SourceOfIncomeResponseItem> {
        return remitNgoService.sourceOfIncome(sourceOfIncomeItem)
    }

    override suspend fun promo(promoItem: PromoItem): Response<PromoResponseItem> {
        return remitNgoService.promo(promoItem)
    }

    override suspend fun createReceipt(transactionId: String): Response<CreateReceiptResponse> {
        return remitNgoService.createReceipt(transactionId)
    }

    override suspend fun profile(profileItem: ProfileItem): Response<ProfileResponseItem> {
        return remitNgoService.profile(profileItem)
    }
    override suspend fun phoneVerify(phoneVerifyItem: PhoneVerifyItem): Response<PhoneVerifyResponseItem> {
        return remitNgoService.phoneVerify(phoneVerifyItem)
    }
    override suspend fun phoneOtpVerify(phoneOtpVerifyItem: PhoneOtpVerifyItem): Response<PhoneOtpVerifyResponseItem> {
        return remitNgoService.phoneOtpVerify(phoneOtpVerifyItem)
    }

    override suspend fun requireDocument(requireDocumentItem: RequireDocumentItem): Response<RequireDocumentResponseItem> {
        return remitNgoService.requireDocument(requireDocumentItem)
    }

    override suspend fun encryptForCreateReceipt(encryptItemForCreateReceipt: EncryptItemForCreateReceipt): Response<EncryptResponseItemForCreateReceipt> {
        return remitNgoService.encryptForCreateReceipt(encryptItemForCreateReceipt)
    }

    override suspend fun bankTransactionMessage(message: String): Response<BankTransactionMessage> {
        return remitNgoService.bankTransactionMessage(message)
    }

}







