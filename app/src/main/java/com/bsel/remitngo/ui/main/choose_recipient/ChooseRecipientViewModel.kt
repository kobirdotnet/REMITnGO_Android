package com.bsel.remitngo.ui.main.choose_recipient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChooseRecipientViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is choose recipient Fragment"
    }
    val text: LiveData<String> = _text
}