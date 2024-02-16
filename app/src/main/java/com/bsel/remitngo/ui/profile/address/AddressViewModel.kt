package com.bsel.remitngo.ui.profile.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddressViewModel: ViewModel() {

    // MutableLiveData to hold and observe the text data
    private val _text = MutableLiveData<String>().apply {
        value = "This is Address Fragment" // Default value for the text
    }

    // Exposing an immutable LiveData for external observation
    val text: LiveData<String> = _text
}