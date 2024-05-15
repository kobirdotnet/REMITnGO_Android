package com.bsel.remitngo.presentation.di.bank

import com.bsel.remitngo.bottomSheet.*
import com.bsel.remitngo.presentation.ui.bank.BankFragment
import com.bsel.remitngo.presentation.ui.bank.ChooseBankFragment
import dagger.Subcomponent

@BankScope
@Subcomponent(modules = [BankModule::class])
interface BankSubComponent {

    fun inject(chooseBankFragment: ChooseBankFragment)
    fun inject(chooseBankBottomSheet: ChooseBankBottomSheet)
    fun inject(saveBankAndWalletBottomSheet: SaveBankAndWalletBottomSheet)
    fun inject(bankFragment: BankFragment)

    fun inject(bankBottomSheet: BankBottomSheet)
    fun inject(walletBottomSheet: WalletBottomSheet)

    fun inject(divisionBottomSheet: DivisionBottomSheet)

    fun inject(districtBottomSheet: DistrictBottomSheet)

    fun inject(branchBottomSheet: BranchBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): BankSubComponent
    }

}
