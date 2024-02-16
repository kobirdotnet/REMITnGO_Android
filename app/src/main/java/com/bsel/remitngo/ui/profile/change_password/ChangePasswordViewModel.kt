package com.bsel.remitngo.ui.profile.change_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChangePasswordViewModel: ViewModel() {

    // MutableLiveData to hold and observe the text data
    private val _text = MutableLiveData<String>().apply {
        value = "This is Change Password Fragment" // Default value for the text
    }

    // Exposing an immutable LiveData for external observation
    val text: LiveData<String> = _text
}