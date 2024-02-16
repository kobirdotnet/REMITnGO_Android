package com.bsel.remitngo.ui.main.confirm_transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfirmTransferViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ConfirmT ransfer Fragment"
    }
    val text: LiveData<String> = _text
}