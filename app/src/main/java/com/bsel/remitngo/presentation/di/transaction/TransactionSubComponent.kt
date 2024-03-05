package com.bsel.remitngo.presentation.di.transaction

import com.bsel.remitngo.bottom_sheet.TransactionBottomSheet
import com.bsel.remitngo.presentation.ui.transaction.TransactionFragment
import dagger.Subcomponent

@TransactionScope
@Subcomponent(modules = [TransactionModule::class])
interface TransactionSubComponent {

    fun inject(transactionFragment: TransactionFragment)
    fun inject(transactionBottomSheet: TransactionBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): TransactionSubComponent
    }

}
