package com.bsel.remitngo.ui.transaction_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransactionHistoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Transaction History Fragment"
    }
    val text: LiveData<String> = _text
}