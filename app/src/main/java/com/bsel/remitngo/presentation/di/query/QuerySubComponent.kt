package com.bsel.remitngo.presentation.di.query

import com.bsel.remitngo.bottom_sheet.QueryBottomSheet
import com.bsel.remitngo.bottom_sheet.QueryTypeBottomSheet
import com.bsel.remitngo.bottom_sheet.TransactionBottomSheet
import com.bsel.remitngo.bottom_sheet.UpdateQueryBottomSheet
import com.bsel.remitngo.presentation.ui.query.GenerateQueryFragment
import com.bsel.remitngo.presentation.ui.query.UpdateQueryFragment
import com.bsel.remitngo.presentation.ui.transaction.TransactionFragment
import dagger.Subcomponent

@QueryScope
@Subcomponent(modules = [QueryModule::class])
interface QuerySubComponent {

    fun inject(generateQueryFragment: GenerateQueryFragment)
    fun inject(updateQueryFragment: UpdateQueryFragment)
    fun inject(queryBottomSheet: QueryBottomSheet)
    fun inject(updateQueryBottomSheet: UpdateQueryBottomSheet)
    fun inject(queryTypeBottomSheet: QueryTypeBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): QuerySubComponent
    }

}
