package com.bsel.remitngo.presentation.di.cancel_request

import com.bsel.remitngo.bottomSheet.CancelReasonBottomSheet
import com.bsel.remitngo.presentation.ui.cancel_request.CancelRequestFragment
import com.bsel.remitngo.presentation.ui.cancel_request.CancellationFragment
import com.bsel.remitngo.presentation.ui.cancel_request.GenerateCancelRequestFragment
import dagger.Subcomponent

@CancelRequestScope
@Subcomponent(modules = [CancelRequestModule::class])
interface CancelRequestSubComponent {

    fun inject(cancellationFragment: CancellationFragment)
    fun inject(cancelRequestFragment: CancelRequestFragment)
    fun inject(generateCancelRequestFragment: GenerateCancelRequestFragment)
    fun inject(cancelReasonBottomSheet: CancelReasonBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): CancelRequestSubComponent
    }

}
