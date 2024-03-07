package com.bsel.remitngo.presentation.di.query

import com.bsel.remitngo.presentation.ui.query.AddQueryFragment
import com.bsel.remitngo.bottom_sheet.QueryTypeBottomSheet
import com.bsel.remitngo.presentation.ui.query.QueryMessageFragment
import com.bsel.remitngo.presentation.ui.query.QueryFragment
import com.bsel.remitngo.presentation.ui.query.UpdateQueryFragment
import dagger.Subcomponent

@QueryScope
@Subcomponent(modules = [QueryModule::class])
interface QuerySubComponent {

    fun inject(queryFragment: QueryFragment)
    fun inject(updateQueryFragment: UpdateQueryFragment)
    fun inject(addQueryFragment: AddQueryFragment)
    fun inject(queryMessageFragment: QueryMessageFragment)
    fun inject(queryTypeBottomSheet: QueryTypeBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): QuerySubComponent
    }

}
