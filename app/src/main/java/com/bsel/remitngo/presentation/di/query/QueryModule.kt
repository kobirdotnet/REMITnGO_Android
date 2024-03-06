package com.bsel.remitngo.presentation.di.query

import com.bsel.remitngo.domain.useCase.QueryUseCase
import com.bsel.remitngo.presentation.ui.query.QueryViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class QueryModule {
    @QueryScope
    @Provides
    fun provideQueryViewModelFactory(queryUseCase: QueryUseCase): QueryViewModelFactory {
        return QueryViewModelFactory(queryUseCase)
    }
}