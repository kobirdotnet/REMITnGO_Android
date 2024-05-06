package com.bsel.remitngo.data.interfaceses

import com.bsel.remitngo.data.model.registration.RegistrationData

interface OnRegistrationSelectedListener {
    fun onRegistrationSelected(selectedItem: RegistrationData)
}