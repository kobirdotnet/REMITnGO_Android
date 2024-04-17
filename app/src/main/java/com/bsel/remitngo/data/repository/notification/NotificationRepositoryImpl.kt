package com.bsel.remitngo.data.repository.notification

import com.bsel.remitngo.data.repository.notification.dataSource.NotificationRemoteDataSource
import com.bsel.remitngo.domain.repository.NotificationRepository

class NotificationRepositoryImpl(private val notificationRemoteDataSource: NotificationRemoteDataSource) :
    NotificationRepository {
}