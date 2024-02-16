package com.bsel.remitngo.ui.profile.mobile_number

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MobileNumberViewModel: ViewModel() {

    // MutableLiveData to hold and observe the text data
    private val _text = MutableLiveData<String>().apply {
        value = "This is Mobile Number Fragment" // Default value for the text
    }

    // Exposing an immutable LiveData for external observation
    val text: LiveData<String> = _text
}