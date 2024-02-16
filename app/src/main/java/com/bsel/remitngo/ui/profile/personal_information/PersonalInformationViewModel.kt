package com.bsel.remitngo.ui.profile.personal_information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonalInformationViewModel : ViewModel() {

    // MutableLiveData to hold and observe the text data
    private val _text = MutableLiveData<String>().apply {
        value = "This is Personal Information Fragment" // Default value for the text
    }

    // Exposing an immutable LiveData for external observation
    val text: LiveData<String> = _text
}