package com.bsel.remitngo.presentation.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReviewViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ConfirmT ransfer Fragment"
    }
    val text: LiveData<String> = _text
}