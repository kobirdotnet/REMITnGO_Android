package com.bsel.remitngo.presentation.ui.cancel_request

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.CancelReasonBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonData
import com.bsel.remitngo.data.model.cancel_request.save_cancel_request.SaveCancelRequestItem
import com.bsel.remitngo.databinding.FragmentGenerateCancelRequestBinding
import com.bsel.remitngo.data.interfaceses.OnCancelReasonItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import javax.inject.Inject

class GenerateCancelRequestFragment : Fragment(), OnCancelReasonItemSelectedListener {
    @Inject
    lateinit var cancelRequestViewModelFactory: CancelRequestViewModelFactory
    private lateinit var cancelRequestViewModel: CancelRequestViewModel

    private lateinit var binding: FragmentGenerateCancelRequestBinding

    private val cancelReasonBottomSheet: CancelReasonBottomSheet by lazy { CancelReasonBottomSheet() }

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

    private lateinit var cancelReasonId: String

    private lateinit var transactionCode: String
    private lateinit var transactionDate: String
    private lateinit var orderType: String
    private lateinit var beneficiaryName: String
    private lateinit var sendAmount: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_generate_cancel_request, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGenerateCancelRequestBinding.bind(view)

        (requireActivity().application as Injector).createCancelRequestSubComponent().inject(this)

        cancelRequestViewModel =
            ViewModelProvider(
                this,
                cancelRequestViewModelFactory
            )[CancelRequestViewModel::class.java]

        transactionCodeFocusListener()
        transactionDateFocusListener()
        orderTypeFocusListener()
        beneficiaryNameFocusListener()
        sendAmountFocusListener()
        cancelReasonFocusListener()
        descriptionFocusListener()

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

        transactionCode = arguments?.getString("transactionCode").toString()
        binding.transactionCode.setText(transactionCode)

        transactionDate = arguments?.getString("transactionDate").toString()
        binding.transactionDate.setText(transactionDate)

        orderType = arguments?.getString("orderType").toString()
        binding.orderType.setText(orderType)

        beneficiaryName = arguments?.getString("beneficiaryName").toString()
        binding.beneficiaryName.setText(beneficiaryName)

        sendAmount = arguments?.getString("sendAmount").toString()
        binding.sendAmount.setText("BDT $sendAmount")

        binding.cancelReason.setOnClickListener {
            cancelReasonBottomSheet.itemSelectedListener = this
            cancelReasonBottomSheet.show(childFragmentManager, cancelReasonBottomSheet.tag)
        }

        binding.btnSubmit.setOnClickListener { cancelRequestFrom() }

        observeSaveCancelRequestResult()

    }

    private fun observeSaveCancelRequestResult() {
        cancelRequestViewModel.saveCancelRequestResult.observe(this) { result ->
            if (result!!.data != null) {
                findNavController().navigate(
                    R.id.action_nav_generate_cancel_request_to_nav_cancellation
                )
            }
        }
    }

    private fun cancelRequestFrom() {
        binding.transactionCodeContainer.helperText = validTransactionCode()
        binding.transactionDateContainer.helperText = validTransactionDate()
        binding.orderTypeContainer.helperText = validOrderType()
        binding.beneficiaryNameContainer.helperText = validBeneficiaryName()
        binding.cancelReasonContainer.helperText = validCancelReason()
        binding.descriptionContainer.helperText = validDescription()

        val validTransactionCode = binding.transactionCodeContainer.helperText == null
        val validTransactionDate = binding.transactionDateContainer.helperText == null
        val validOrderType = binding.orderTypeContainer.helperText == null
        val validBeneficiaryName = binding.beneficiaryNameContainer.helperText == null
        val validCancelReason = binding.cancelReasonContainer.helperText == null
        val validDescription = binding.descriptionContainer.helperText == null

        if (validTransactionCode && validTransactionDate && validOrderType && validBeneficiaryName
            && validCancelReason && validDescription
        ) {
            submitCancelRequestFrom()
        }
    }

    private fun submitCancelRequestFrom() {
        val transactionCode = binding.transactionCode.text.toString()
        val transactionDate = binding.transactionDate.text.toString()
        val orderType = binding.orderType.text.toString()
        val beneficiaryName = binding.beneficiaryName.text.toString()
        val cancelReason = binding.cancelReason.text.toString()
        val description = binding.description.text.toString()

        val saveCancelRequestItem = SaveCancelRequestItem(
            cancelReasonId = cancelReasonId.toInt(),
            personId = personId.toInt(),
            remarks = description,
            transactionCode = transactionCode
        )
        cancelRequestViewModel.saveCancelRequest(saveCancelRequestItem)
    }

    //Form validation
    private fun transactionCodeFocusListener() {
        binding.transactionCode.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.transactionCodeContainer.helperText = validTransactionCode()
            }
        }
    }

    private fun validTransactionCode(): String? {
        val transactionCode = binding.transactionCode.text.toString()
        if (transactionCode.isEmpty()) {
            return "enter transaction code"
        }
        return null
    }

    private fun transactionDateFocusListener() {
        binding.transactionDate.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.transactionDateContainer.helperText = validTransactionDate()
            }
        }
    }

    private fun validTransactionDate(): String? {
        val transactionDate = binding.transactionDate.text.toString()
        if (transactionDate.isEmpty()) {
            return "enter transaction date"
        }
        return null
    }

    private fun orderTypeFocusListener() {
        binding.orderType.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.orderTypeContainer.helperText = validOrderType()
            }
        }
    }

    private fun validOrderType(): String? {
        val orderType = binding.orderType.text.toString()
        if (orderType.isEmpty()) {
            return "enter order type"
        }
        return null
    }

    private fun beneficiaryNameFocusListener() {
        binding.beneficiaryName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.beneficiaryNameContainer.helperText = validBeneficiaryName()
            }
        }
    }

    private fun validBeneficiaryName(): String? {
        val beneficiaryName = binding.beneficiaryName.text.toString()
        if (beneficiaryName.isEmpty()) {
            return "enter beneficiary name"
        }
        return null
    }

    private fun sendAmountFocusListener() {
        binding.sendAmount.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.sendAmountContainer.helperText = validSendAmount()
            }
        }
    }

    private fun validSendAmount(): String? {
        val sendAmount = binding.sendAmount.text.toString()
        if (sendAmount.isEmpty()) {
            return "enter send amount"
        }
        return null
    }

    private fun cancelReasonFocusListener() {
        binding.cancelReason.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.cancelReasonContainer.helperText = validCancelReason()
            }
        }
    }

    private fun validCancelReason(): String? {
        val cancelReason = binding.cancelReason.text.toString()
        if (cancelReason.isEmpty()) {
            return "select cancel reason"
        }
        return null
    }

    private fun descriptionFocusListener() {
        binding.description.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.descriptionContainer.helperText = validDescription()
            }
        }
    }

    private fun validDescription(): String? {
        val description = binding.description.text.toString()
        if (description.isEmpty()) {
            return "enter description"
        }
        return null
    }

    override fun onCancelReasonItemSelected(selectedItem: CancelReasonData) {
        binding.cancelReason.setText(selectedItem.name)
        cancelReasonId = selectedItem.id.toString()
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

}