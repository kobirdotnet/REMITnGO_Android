package com.bsel.remitngo.data.repository.registration.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.marketing.MarketingItem
import com.bsel.remitngo.data.model.marketing.MarketingResponseItem
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem
import com.bsel.remitngo.data.repository.registration.dataSource.RegistrationRemoteDataSource
import retrofit2.Response

class RegistrationRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) : RegistrationRemoteDataSource {
    override suspend fun registerUser(registrationItem: RegistrationItem): Response<RegistrationResponseItem> {
        return remitNgoService.registerUser(registrationItem)
    }

    override suspend fun marketing(marketingItem: MarketingItem): Response<MarketingResponseItem> {
        return remitNgoService.marketing(marketingItem)
    }
}







