package com.bsel.remitngo.presentation.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

}