package com.bsel.remitngo.presentation.di.document

import com.bsel.remitngo.domain.useCase.DocumentUseCase
import com.bsel.remitngo.presentation.ui.document.DocumentViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DocumentModule {
    @DocumentScope
    @Provides
    fun provideDocumentViewModelFactory(documentUseCase: DocumentUseCase): DocumentViewModelFactory {
        return DocumentViewModelFactory(documentUseCase)
    }
}