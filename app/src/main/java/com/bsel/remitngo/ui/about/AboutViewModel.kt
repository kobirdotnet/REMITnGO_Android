package com.bsel.remitngo.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    // MutableLiveData for holding the text data with an initial value
    private val _text = MutableLiveData<String>().apply {
        value = "This is About Fragment"
    }

    // Expose an immutable LiveData for observing text changes
    val text: LiveData<String> = _text
}
