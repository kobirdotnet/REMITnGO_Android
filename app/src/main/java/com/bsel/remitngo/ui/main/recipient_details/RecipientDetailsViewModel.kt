package com.bsel.remitngo.ui.main.recipient_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipientDetailsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is recipient details Fragment"
    }
    val text: LiveData<String> = _text
}