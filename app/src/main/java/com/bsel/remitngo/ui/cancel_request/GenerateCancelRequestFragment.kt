package com.bsel.remitngo.ui.cancel_request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.bottom_sheet.CancelReasonBottomSheet
import com.bsel.remitngo.databinding.FragmentGenerateCancelRequestBinding
import com.bsel.remitngo.interfaceses.OnCancelReasonItemSelectedListener
import com.bsel.remitngo.model.CancelReasonItem

class GenerateCancelRequestFragment : Fragment(), OnCancelReasonItemSelectedListener {

    private lateinit var binding: FragmentGenerateCancelRequestBinding

    private val cancelReasonBottomSheet: CancelReasonBottomSheet by lazy { CancelReasonBottomSheet() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_generate_cancel_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGenerateCancelRequestBinding.bind(view)

        transactionCodeFocusListener()
        transactionDateFocusListener()
        orderTypeFocusListener()
        beneficiaryNameFocusListener()
        sendAmountFocusListener()
        cancelReasonFocusListener()
        descriptionFocusListener()

        binding.cancelReason.setOnClickListener {
            cancelReasonBottomSheet.itemSelectedListener = this
            cancelReasonBottomSheet.show(childFragmentManager, cancelReasonBottomSheet.tag)
        }

        binding.btnSubmit.setOnClickListener {cancelRequestFrom()}

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

        findNavController().navigate(
            R.id.action_nav_generate_cancel_request_to_nav_cancellation
        )
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

    override fun onCancelReasonItemSelected(selectedItem: CancelReasonItem) {
        binding.cancelReason.setText(selectedItem.cancelReasonName)
    }

}