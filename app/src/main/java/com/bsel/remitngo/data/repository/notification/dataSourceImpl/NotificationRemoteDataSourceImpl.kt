package com.bsel.remitngo.data.repository.notification.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.repository.notification.dataSource.NotificationRemoteDataSource

class NotificationRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    NotificationRemoteDataSource {
}