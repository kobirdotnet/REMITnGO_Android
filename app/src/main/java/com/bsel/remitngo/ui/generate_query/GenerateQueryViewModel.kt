package com.bsel.remitngo.ui.generate_query

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GenerateQueryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Generate Query Fragment"
    }
    val text: LiveData<String> = _text
}