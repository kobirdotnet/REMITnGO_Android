package com.bsel.remitngo.presentation.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

}