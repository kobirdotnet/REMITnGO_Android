package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.support.SupportResponseItem

interface SupportRepository {
    suspend fun support(message: String): SupportResponseItem?
}