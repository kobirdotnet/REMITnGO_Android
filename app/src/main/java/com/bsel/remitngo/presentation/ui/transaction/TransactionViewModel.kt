package com.bsel.remitngo.presentation.ui.transaction

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
import com.bsel.remitngo.data.model.encript.EncryptItemForCreateReceipt
import com.bsel.remitngo.data.model.encript.EncryptResponseItem
import com.bsel.remitngo.data.model.encript.EncryptResponseItemForCreateReceipt
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.model.profile.ProfileItem
import com.bsel.remitngo.data.model.profile.ProfileResponseItem
import com.bsel.remitngo.data.model.transaction.TransactionItem
import com.bsel.remitngo.data.model.transaction.TransactionResponseItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsResponseItem
import com.bsel.remitngo.domain.useCase.TransactionUseCase
import kotlinx.coroutines.launch

class TransactionViewModel(private val transactionUseCase: TransactionUseCase) : ViewModel() {

    private val _profileResult = MutableLiveData<ProfileResponseItem?>()
    val profileResult: LiveData<ProfileResponseItem?> = _profileResult

    fun profile(profileItem: ProfileItem) {
        viewModelScope.launch {
            val result = transactionUseCase.execute(profileItem)
            _profileResult.value = result
        }
    }

    private val _consumerResult = MutableLiveData<ConsumerResponseItem?>()
    val consumerResult: LiveData<ConsumerResponseItem?> = _consumerResult

    fun consumer(consumerItem: ConsumerItem) {
        viewModelScope.launch {
            val result = transactionUseCase.execute(consumerItem)
            _consumerResult.value = result
        }
    }

    private val _saveConsumerResult = MutableLiveData<SaveConsumerResponseItem?>()
    val saveConsumerResult: LiveData<SaveConsumerResponseItem?> = _saveConsumerResult

    fun saveConsumer(saveConsumerItem: SaveConsumerItem) {
        viewModelScope.launch {
            val result = transactionUseCase.execute(saveConsumerItem)
            _saveConsumerResult.value = result
        }
    }

    private val _empResult = MutableLiveData<EmpResponseItem?>()
    val empResult: LiveData<EmpResponseItem?> = _empResult

    fun emp(empItem: EmpItem) {
        viewModelScope.launch {
            val result = transactionUseCase.execute(empItem)
            _empResult.value = result
        }
    }

    private val _transactionResult = MutableLiveData<TransactionResponseItem?>()
    val transactionResult: LiveData<TransactionResponseItem?> = _transactionResult

    fun transaction(transactionItem: TransactionItem) {
        viewModelScope.launch {
            val result = transactionUseCase.execute(transactionItem)
            _transactionResult.value = result
        }
    }
    private val _transactionDetailsResult = MutableLiveData<TransactionDetailsResponseItem?>()
    val transactionDetailsResult: LiveData<TransactionDetailsResponseItem?> = _transactionDetailsResult

    fun transactionDetails(transactionDetailsItem: TransactionDetailsItem) {
        viewModelScope.launch {
            val result = transactionUseCase.execute(transactionDetailsItem)
            _transactionDetailsResult.value = result
        }
    }

    private val _encryptResult = MutableLiveData<EncryptResponseItem?>()
    val encryptResult: LiveData<EncryptResponseItem?> = _encryptResult

    fun encrypt(encryptItem: EncryptItem) {
        viewModelScope.launch {
            val result = transactionUseCase.execute(encryptItem)
            _encryptResult.value = result
        }
    }

    private val _encryptForCreateReceiptResult = MutableLiveData<EncryptResponseItemForCreateReceipt?>()
    val encryptForCreateReceiptResult: LiveData<EncryptResponseItemForCreateReceipt?> = _encryptForCreateReceiptResult

    fun encryptForCreateReceipt(encryptItemForCreateReceipt: EncryptItemForCreateReceipt) {
        viewModelScope.launch {
            val result = transactionUseCase.executeEncryptForCreateReceipt(encryptItemForCreateReceipt)
            _encryptForCreateReceiptResult.value = result
        }
    }

}