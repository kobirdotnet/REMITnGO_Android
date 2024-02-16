package com.bsel.remitngo.ui.main.complete_card_transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransactionCompleteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is transaction complete Fragment"
    }
    val text: LiveData<String> = _text
}