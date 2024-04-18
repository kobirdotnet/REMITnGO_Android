package com.bsel.remitngo.presentation.di.document

import com.bsel.remitngo.bottomSheet.DocumentCategoryBottomSheet
import com.bsel.remitngo.bottomSheet.DocumentTypeBottomSheet
import com.bsel.remitngo.bottomSheet.RequireDocumentBottomSheet
import com.bsel.remitngo.bottomSheet.UploadRequireDocumentBottomSheet
import com.bsel.remitngo.presentation.ui.document.DocumentFragment
import com.bsel.remitngo.presentation.ui.document.UpdateDocumentFragment
import com.bsel.remitngo.presentation.ui.document.UploadDocumentFragment
import dagger.Subcomponent

@DocumentScope
@Subcomponent(modules = [DocumentModule::class])
interface DocumentSubComponent {

    fun inject(documentFragment: DocumentFragment)
    fun inject(uploadDocumentFragment: UploadDocumentFragment)
    fun inject(updateDocumentFragment: UpdateDocumentFragment)
    fun inject(documentCategoryBottomSheet: DocumentCategoryBottomSheet)
    fun inject(documentTypeBottomSheet: DocumentTypeBottomSheet)
    fun inject(uploadRequireDocumentBottomSheet: UploadRequireDocumentBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DocumentSubComponent
    }

}
