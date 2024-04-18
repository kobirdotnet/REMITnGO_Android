package com.bsel.remitngo.presentation.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.bsel.remitngo.data.model.document.docForTransaction.docMsg.RequireDocMsg
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
import com.bsel.remitngo.domain.useCase.PaymentUseCase
import kotlinx.coroutines.launch

class PaymentViewModel(private val paymentUseCase: PaymentUseCase) : ViewModel() {

    private val _paymentResult = MutableLiveData<PaymentResponseItem?>()
    val paymentResult: LiveData<PaymentResponseItem?> = _paymentResult

    fun payment(paymentItem: PaymentItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(paymentItem)
            _paymentResult.value = result
        }
    }

    private val _paymentStatusResult = MutableLiveData<PaymentStatusResponse?>()
    val paymentStatusResult: LiveData<PaymentStatusResponse?> = _paymentStatusResult

    fun paymentStatus(transactionCode: String) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(transactionCode)
            _paymentStatusResult.value = result
        }
    }

    private val _encryptResult = MutableLiveData<EncryptResponseItem?>()
    val encryptResult: LiveData<EncryptResponseItem?> = _encryptResult

    fun encrypt(encryptItem: EncryptItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(encryptItem)
            _encryptResult.value = result
        }
    }

    private val _empResult = MutableLiveData<EmpResponseItem?>()
    val empResult: LiveData<EmpResponseItem?> = _empResult

    fun emp(empItem: EmpItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(empItem)
            _empResult.value = result
        }
    }

    private val _saveConsumerResult = MutableLiveData<SaveConsumerResponseItem?>()
    val saveConsumerResult: LiveData<SaveConsumerResponseItem?> = _saveConsumerResult

    fun saveConsumer(saveConsumerItem: SaveConsumerItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(saveConsumerItem)
            _saveConsumerResult.value = result
        }
    }

    private val _consumerResult = MutableLiveData<ConsumerResponseItem?>()
    val consumerResult: LiveData<ConsumerResponseItem?> = _consumerResult

    fun consumer(consumerItem: ConsumerItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(consumerItem)
            _consumerResult.value = result
        }
    }

    private val _rateCalculateResult = MutableLiveData<CalculateRateResponseItem?>()
    val rateCalculateResult: LiveData<CalculateRateResponseItem?> = _rateCalculateResult

    fun rateCalculate(calculateRateItem: CalculateRateItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(calculateRateItem)
            _rateCalculateResult.value = result
        }
    }

    private val _paymentTransactionResult = MutableLiveData<TransactionDetailsResponseItem?>()
    val paymentTransactionResult: LiveData<TransactionDetailsResponseItem?> = _paymentTransactionResult

    fun paymentTransaction(transactionDetailsItem: TransactionDetailsItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(transactionDetailsItem)
            _paymentTransactionResult.value = result
        }
    }

    private val _reasonResult = MutableLiveData<ReasonResponseItem?>()
    val reasonResult: LiveData<ReasonResponseItem?> = _reasonResult

    fun reason(reasonItem: ReasonItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(reasonItem)
            _reasonResult.value = result
        }
    }

    private val _sourceOfIncomeResult = MutableLiveData<SourceOfIncomeResponseItem?>()
    val sourceOfIncomeResult: LiveData<SourceOfIncomeResponseItem?> = _sourceOfIncomeResult

    fun sourceOfIncome(sourceOfIncomeItem: SourceOfIncomeItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(sourceOfIncomeItem)
            _sourceOfIncomeResult.value = result
        }
    }

    private val _promoResult = MutableLiveData<PromoResponseItem?>()
    val promoResult: LiveData<PromoResponseItem?> = _promoResult

    fun promo(promoItem: PromoItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(promoItem)
            _promoResult.value = result
        }
    }

    private val _createReceiptResult = MutableLiveData<CreateReceiptResponse?>()
    val createReceiptResult: LiveData<CreateReceiptResponse?> = _createReceiptResult

    fun createReceipt(transactionId: String) {
        viewModelScope.launch {
            val result = paymentUseCase.executeCreateReceipt(transactionId)
            _createReceiptResult.value = result
        }
    }

    private val _profileResult = MutableLiveData<ProfileResponseItem?>()
    val profileResult: LiveData<ProfileResponseItem?> = _profileResult

    fun profile(profileItem: ProfileItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(profileItem)
            _profileResult.value = result
        }
    }

    private val _phoneVerifyResult = MutableLiveData<PhoneVerifyResponseItem?>()
    val phoneVerifyResult: LiveData<PhoneVerifyResponseItem?> = _phoneVerifyResult

    fun phoneVerify(phoneVerifyItem: PhoneVerifyItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(phoneVerifyItem)
            _phoneVerifyResult.value = result
        }
    }

    private val _phoneOtpVerifyResult = MutableLiveData<PhoneOtpVerifyResponseItem?>()
    val phoneOtpVerifyResult: LiveData<PhoneOtpVerifyResponseItem?> = _phoneOtpVerifyResult

    fun phoneOtpVerify(phoneOtpVerifyItem: PhoneOtpVerifyItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(phoneOtpVerifyItem)
            _phoneOtpVerifyResult.value = result
        }
    }

    private val _requireDocumentResult = MutableLiveData<RequireDocumentResponseItem?>()
    val requireDocumentResult: LiveData<RequireDocumentResponseItem?> = _requireDocumentResult

    fun requireDocument(requireDocumentItem: RequireDocumentItem) {
        viewModelScope.launch {
            val result = paymentUseCase.execute(requireDocumentItem)
            _requireDocumentResult.value = result
        }
    }

    private val _encryptForCreateReceiptResult = MutableLiveData<EncryptResponseItemForCreateReceipt?>()
    val encryptForCreateReceiptResult: LiveData<EncryptResponseItemForCreateReceipt?> = _encryptForCreateReceiptResult

    fun encryptForCreateReceipt(encryptItemForCreateReceipt: EncryptItemForCreateReceipt) {
        viewModelScope.launch {
            val result = paymentUseCase.executeEncryptForCreateReceipt(encryptItemForCreateReceipt)
            _encryptForCreateReceiptResult.value = result
        }
    }

    private val _bankTransactionMessageResult = MutableLiveData<BankTransactionMessage?>()
    val bankTransactionMessageResult: LiveData<BankTransactionMessage?> = _bankTransactionMessageResult

    fun bankTransactionMessage(message: String) {
        viewModelScope.launch {
            val result = paymentUseCase.executeBankTransactionMessage(message)
            _bankTransactionMessageResult.value = result
        }
    }

    private val _requireDocMsgResult = MutableLiveData<RequireDocMsg?>()
    val requireDocMsgResult: LiveData<RequireDocMsg?> = _requireDocMsgResult

    fun requireDocMsg(message: String) {
        viewModelScope.launch {
            val result = paymentUseCase.requireDocMsg(message)
            _requireDocMsgResult.value = result
        }
    }

}