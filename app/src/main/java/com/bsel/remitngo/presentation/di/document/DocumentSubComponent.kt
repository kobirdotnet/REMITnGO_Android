package com.bsel.remitngo.presentation.di.document

import com.bsel.remitngo.bottom_sheet.DocumentCategoryBottomSheet
import com.bsel.remitngo.bottom_sheet.DocumentTypeBottomSheet
import com.bsel.remitngo.presentation.ui.document.DocumentFragment
import com.bsel.remitngo.presentation.ui.document.UploadDocumentFragment
import dagger.Subcomponent

@DocumentScope
@Subcomponent(modules = [DocumentModule::class])
interface DocumentSubComponent {

    fun inject(documentFragment: DocumentFragment)
    fun inject(uploadDocumentFragment: UploadDocumentFragment)
    fun inject(documentCategoryBottomSheet: DocumentCategoryBottomSheet)
    fun inject(documentTypeBottomSheet: DocumentTypeBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DocumentSubComponent
    }

}
