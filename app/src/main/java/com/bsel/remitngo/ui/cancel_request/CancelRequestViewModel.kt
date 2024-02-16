package com.bsel.remitngo.ui.cancel_request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CancelRequestViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Cancel Request Fragment"
    }
    val text: LiveData<String> = _text
}