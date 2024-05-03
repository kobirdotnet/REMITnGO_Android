package com.bsel.remitngo.data.model.login

import java.util.*

data class AccessToken(
    val jti: String,
    val personId: String,
    val customerId: String,
    val email: String,
    val mobile: String,
    val firstName: String,
    val lastName: String,
    val DOB: String,
    val CMCode: String,
    val siq: String,
    val channel: String,
    val exp: Date
)
