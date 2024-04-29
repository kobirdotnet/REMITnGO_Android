package com.bsel.remitngo.data.interfaceses

import com.bsel.remitngo.data.model.bank.bank_account.GetBankData

interface OnBankAndWalletSelectedListener {
    fun onBankAndWalletItemSelected(selectedItem: GetBankData)
}