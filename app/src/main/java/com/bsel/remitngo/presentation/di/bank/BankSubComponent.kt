package com.bsel.remitngo.presentation.di.bank

import com.bsel.remitngo.bottom_sheet.BankBottomSheet
import com.bsel.remitngo.bottom_sheet.BranchBottomSheet
import com.bsel.remitngo.bottom_sheet.DistrictBottomSheet
import com.bsel.remitngo.bottom_sheet.DivisionBottomSheet
import com.bsel.remitngo.presentation.ui.bank.BankFragment
import dagger.Subcomponent

@BankScope
@Subcomponent(modules = [BankModule::class])
interface BankSubComponent {

    fun inject(bankFragment: BankFragment)

    fun inject(bankBottomSheet: BankBottomSheet)

    fun inject(divisionBottomSheet: DivisionBottomSheet)

    fun inject(districtBottomSheet: DistrictBottomSheet)

    fun inject(branchBottomSheet: BranchBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): BankSubComponent
    }

}
