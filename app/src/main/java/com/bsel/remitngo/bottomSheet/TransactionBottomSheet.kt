package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.transaction.transaction_details.TransactionDetailsItem
import com.bsel.remitngo.databinding.TransactionLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.transaction.TransactionViewModel
import com.bsel.remitngo.presentation.ui.transaction.TransactionViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class TransactionBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var transactionViewModelFactory: TransactionViewModelFactory
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var preferenceManager: PreferenceManager

    private lateinit var transactionBehavior: BottomSheetBehavior<*>

    private lateinit var binding: TransactionLayoutBinding

    private lateinit var deviceId: String
    private lateinit var personId: String

    private var selectedTransactionCode: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.transaction_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        transactionBehavior = BottomSheetBehavior.from(view.parent as View)
        transactionBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        transactionBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull view: View, i: Int) {
                when (i) {
                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                }
            }

            override fun onSlide(@NonNull view: View, v: Float) {}
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        (requireActivity().application as Injector).createTransactionSubComponent().inject(this)

        transactionViewModel =
            ViewModelProvider(this, transactionViewModelFactory)[TransactionViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

        val transactionDetailsItem = TransactionDetailsItem(
            deviceId = deviceId,
            transactionCode = selectedTransactionCode
        )
        transactionViewModel.transactionDetails(transactionDetailsItem)
        observeTransactionDetailsResult()

        return bottomSheet
    }

    fun setSelectedTransactionCode(transactionCode: String) {
        selectedTransactionCode = transactionCode
    }

    private fun observeTransactionDetailsResult() {
        transactionViewModel.transactionDetailsResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    for (transactionDetailsData in result!!.data!!) {

                        if (transactionDetailsData!!.transactionCode != "null") {
                            binding.transactionCode.text = transactionDetailsData.transactionCode
                        }
                        if (transactionDetailsData.transactionDateTime12hr != "null") {
                            binding.transactionDate.text =
                                transactionDetailsData.transactionDateTime12hr
                        }
                        if (transactionDetailsData.paymentTypeName != "null") {
                            binding.paymentMode.text = transactionDetailsData.paymentTypeName
                        }
                        if (transactionDetailsData.orderTypeName != "null") {
                            binding.orderType.text = transactionDetailsData.orderTypeName
                        }
                        if (transactionDetailsData.beneName != "null") {
                            binding.beneName.text = transactionDetailsData.beneName
                        }

                        if (transactionDetailsData.beneWalletId == 0) {
                            if (transactionDetailsData.beneBankName != "null") {
                                binding.txtBankName.text = "Bank Name"
                                binding.bankName.text = transactionDetailsData.beneBankName
                            }
                            if (transactionDetailsData.beneAccountNo != "null") {
                                binding.accountNo.text = transactionDetailsData.beneAccountNo
                            }
                        } else {
                            if (transactionDetailsData.walletName != "null") {
                                binding.txtBankName.text = "Wallet Name"
                                binding.bankName.text = transactionDetailsData.walletName
                            }
                            if (transactionDetailsData.beneWalletNo != "null") {
                                binding.accountNo.text = transactionDetailsData.beneWalletNo
                            }
                        }

                        if (transactionDetailsData.sendAmount.toString() != "null") {
                            val sendAmount = transactionDetailsData.sendAmount.toString()
                            binding.sendAmount.text = "GBP $sendAmount"
                        }
                        if (transactionDetailsData.beneAmount.toString() != "null") {
                            val beneAmount = transactionDetailsData.beneAmount.toString()
                            binding.beneAmount.text = "BDT $beneAmount"
                        }
                        if (transactionDetailsData.rate.toString() != "null") {
                            val exchangeRate = transactionDetailsData.rate.toString()
                            binding.exchangeRate.text = "BDT $exchangeRate"
                        }
                        if (transactionDetailsData.transferFees.toString() != "null") {
                            val commission = transactionDetailsData.transferFees.toString()
                            binding.commission.text = "GBP $commission"
                        }
                        if (transactionDetailsData.transactionStatus.toString() != "null") {
                            binding.transactionStatus.text =
                                transactionDetailsData.transactionStatus.toString()
                        }
                        if (transactionDetailsData.paymentStatus.toString() != "null") {
                            binding.paymentStatus.text =
                                transactionDetailsData.paymentStatus.toString()
                        }
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun getDeviceId(context: Context): String {
        val deviceId: String

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } else {
            @Suppress("DEPRECATION")
            deviceId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

        return deviceId
    }

    override fun onStart() {
        super.onStart()
        transactionBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}