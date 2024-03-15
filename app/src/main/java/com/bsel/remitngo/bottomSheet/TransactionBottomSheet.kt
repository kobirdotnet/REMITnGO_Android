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
            params1 = personId.toInt(),
            params2 = selectedTransactionCode
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
            if (result!!.data != null) {
                for (transactionDetailsData in result!!.data!!) {

                    binding.transactionCode.text =
                        transactionDetailsData!!.transactionCode.toString()

                    binding.transactionDate.text =
                        transactionDetailsData!!.transactionDateTime12hr.toString()

//                    binding.paymentMode.text = transactionDetailsData!!.paymentMode.toString()

                    binding.orderType.text = transactionDetailsData!!.orderTypeName.toString()

                    binding.benName.text = transactionDetailsData!!.benName.toString()

                    binding.bankName.text = transactionDetailsData!!.bankName.toString()

                    binding.accountNo.text = transactionDetailsData!!.accountNo.toString()

//                    val totalAmount = transactionDetailsData!!.totalAmount.toString()
//                    binding.sendAmount.text = "GBP $totalAmount"

                    val benAmount = transactionDetailsData!!.benAmount.toString()
                    binding.benAmount.text = "BDT $benAmount"

                    val exchangeRate=transactionDetailsData!!.rate.toString()
                    binding.exchangeRate.text = "BDT $exchangeRate"

//                    val commission=transactionDetailsData!!.transferFees.toString()
//                    binding.commission.text = "GBP $commission"

                    binding.paymentStatus.text = transactionDetailsData!!.paymentStatus.toString()

                    binding.transactionStatus.text = transactionDetailsData!!.transactionStatus.toString()
                }
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