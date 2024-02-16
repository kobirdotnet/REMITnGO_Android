package com.bsel.remitngo.ui.main.recipient_bank_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipientBankDetailsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is recipient bank details Fragment"
    }
    val text: LiveData<String> = _text
}