package com.bsel.remitngo.ui.main.recipient_bank_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.bottom_sheet.BankBottomSheet
import com.bsel.remitngo.bottom_sheet.BankBranchBottomSheet
import com.bsel.remitngo.bottom_sheet.DivisionBottomSheet
import com.bsel.remitngo.databinding.FragmentRecipientBankDetailsBinding
import com.bsel.remitngo.interfaceses.OnBankItemSelectedListener
import com.bsel.remitngo.model.BankBranchItem
import com.bsel.remitngo.model.BankItem
import com.bsel.remitngo.model.DivisionItem

class RecipientBankDetailsFragment : Fragment(), OnBankItemSelectedListener {

    private lateinit var binding: FragmentRecipientBankDetailsBinding

    private val bankBottomSheet: BankBottomSheet by lazy { BankBottomSheet() }

    private val divisionBottomSheet: DivisionBottomSheet by lazy { DivisionBottomSheet() }

    private val bankBranchBottomSheet: BankBranchBottomSheet by lazy { BankBranchBottomSheet() }

    private lateinit var orderType: String
    private lateinit var paymentType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipient_bank_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecipientBankDetailsBinding.bind(view)

        bankAccountNameFocusListener()
        bankNameFocusListener()
        divisionNameFocusListener()
        branchNameFocusListener()
        bankAccountNumberFocusListener()
        confirmBankAccountNumberFocusListener()

        walletAccountNameFocusListener()
        phoneNumberFocusListener()

        orderType = arguments?.getString("orderType").toString()
        paymentType = arguments?.getString("paymentType").toString()

        if (orderType=="1"){
            binding.bankAccountLayout.visibility = View.GONE
            binding.walletAccountLayout.visibility = View.VISIBLE
        }else{
            binding.bankAccountLayout.visibility = View.VISIBLE
            binding.walletAccountLayout.visibility = View.GONE
        }

        binding.bankName.setOnClickListener {
            bankBottomSheet.itemSelectedListener = this
            bankBottomSheet.show(childFragmentManager, bankBottomSheet.tag)
        }

        binding.divisionName.setOnClickListener {
            divisionBottomSheet.itemSelectedListener = this
            divisionBottomSheet.show(childFragmentManager, divisionBottomSheet.tag)
        }

        binding.branchName.setOnClickListener {
            bankBranchBottomSheet.itemSelectedListener = this
            bankBranchBottomSheet.show(childFragmentManager, bankBranchBottomSheet.tag)
        }

        binding.btnBankSave.setOnClickListener { bankAccountForm() }

        binding.btnWalletSave.setOnClickListener { walletAccountForm() }

    }

    override fun onBankItemSelected(selectedItem: BankItem) {
        binding.bankName.setText(selectedItem.bankName)
    }

    override fun onDivisionItemSelected(selectedItem: DivisionItem) {
        binding.divisionName.setText(selectedItem.divisionName)
    }

    override fun onBankBranchItemSelected(selectedItem: BankBranchItem) {
        binding.branchName.setText(selectedItem.bankBranchName)
    }

    private fun bankAccountForm() {
        binding.bankAccountNameContainer.helperText = validBankAccountName()
        binding.bankNameContainer.helperText = validBankName()
        binding.divisionNameContainer.helperText = validDivisionName()
        binding.branchNameContainer.helperText = validBranchName()
        binding.bankAccountNumberContainer.helperText = validBankAccountNumber()
        binding.confirmBankAccountNumberContainer.helperText = validConfirmBankAccountNumber()

        val validBankAccountName = binding.bankAccountNameContainer.helperText == null
        val validBankName = binding.bankNameContainer.helperText == null
        val validDivisionName = binding.divisionNameContainer.helperText == null
        val validBranchName = binding.branchNameContainer.helperText == null
        val validBankAccountNumber = binding.bankAccountNumberContainer.helperText == null
        val validConfirmBankAccountNumber =
            binding.confirmBankAccountNumberContainer.helperText == null

        if (validBankAccountName && validBankName && validDivisionName && validBranchName && validBankAccountNumber && validConfirmBankAccountNumber) {
            submitBankAccountForm()
        }
    }

    private fun submitBankAccountForm() {
        val bankAccountName = binding.bankAccountName.text.toString()
        val bankName = binding.bankName.text.toString()
        val divisionName = binding.divisionName.text.toString()
        val branchName = binding.branchName.text.toString()
        val bankAccountNumber = binding.bankAccountNumber.text.toString()
        val confirmBankAccountNumber = binding.confirmBankAccountNumber.text.toString()

        val bundle = Bundle().apply {
            putString("orderType", orderType)
            putString("paymentType", paymentType)
        }
        findNavController().navigate(
            R.id.action_nav_recipient_bank_details_to_nav_confirm_transfer,
            bundle
        )
    }

    private fun walletAccountForm() {
        binding.walletAccountNameContainer.helperText = validWalletAccountName()
        binding.phoneNumberContainer.helperText = validPhoneNumber()

        val validWalletAccountName = binding.walletAccountNameContainer.helperText == null
        val validPhoneNumber = binding.phoneNumberContainer.helperText == null

        if (validWalletAccountName && validPhoneNumber) {
            submitWalletAccountForm()
        }
    }

    private fun submitWalletAccountForm() {
        val walletAccountName = binding.walletAccountName.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()

        val bundle = Bundle().apply {
            putString("orderType", orderType)
            putString("paymentType", paymentType)
        }
        findNavController().navigate(
            R.id.action_nav_recipient_bank_details_to_nav_confirm_transfer,
            bundle
        )
    }

    //Form validation
    private fun bankAccountNameFocusListener() {
        binding.bankAccountName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.bankAccountNameContainer.helperText = validBankAccountName()
            }
        }
    }

    private fun validBankAccountName(): String? {
        val bankAccountName = binding.bankAccountName.text.toString()
        if (bankAccountName.isEmpty()) {
            return "enter bank account name"
        }
        return null
    }

    private fun bankNameFocusListener() {
        binding.bankName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.bankNameContainer.helperText = validBankName()
            }
        }
    }

    private fun validBankName(): String? {
        val bankName = binding.bankName.text.toString()
        if (bankName.isEmpty()) {
            return "select bank name"
        }
        return null
    }

    private fun divisionNameFocusListener() {
        binding.divisionName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.divisionNameContainer.helperText = validDivisionName()
            }
        }
    }

    private fun validDivisionName(): String? {
        val divisionName = binding.divisionName.text.toString()
        if (divisionName.isEmpty()) {
            return "select division name"
        }
        return null
    }

    private fun branchNameFocusListener() {
        binding.branchName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.branchNameContainer.helperText = validBranchName()
            }
        }
    }

    private fun validBranchName(): String? {
        val branchName = binding.branchName.text.toString()
        if (branchName.isEmpty()) {
            return "select branch name"
        }
        return null
    }

    private fun bankAccountNumberFocusListener() {
        binding.bankAccountNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.bankAccountNumberContainer.helperText = validBankAccountNumber()
            }
        }
    }

    private fun validBankAccountNumber(): String? {
        val bankAccountNumber = binding.bankAccountNumber.text.toString()
        if (bankAccountNumber.isEmpty()) {
            return "enter bank account number"
        }
        return null
    }

    private fun confirmBankAccountNumberFocusListener() {
        binding.confirmBankAccountNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.confirmBankAccountNumberContainer.helperText =
                    validConfirmBankAccountNumber()
            }
        }
    }

    private fun validConfirmBankAccountNumber(): String? {
        val confirmBankAccountNumber = binding.confirmBankAccountNumber.text.toString()
        if (confirmBankAccountNumber.isEmpty()) {
            return "enter confirm bank account number"
        }
        return null
    }

    private fun walletAccountNameFocusListener() {
        binding.walletAccountName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.walletAccountNameContainer.helperText = validWalletAccountName()
            }
        }
    }

    private fun validWalletAccountName(): String? {
        val walletAccountName = binding.walletAccountName.text.toString()
        if (walletAccountName.isEmpty()) {
            return "enter wallet account name"
        }
        return null
    }

    private fun phoneNumberFocusListener() {
        binding.phoneNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.phoneNumberContainer.helperText = validPhoneNumber()
            }
        }
    }

    private fun validPhoneNumber(): String? {
        val phoneNumber = binding.phoneNumber.text.toString()
        if (phoneNumber.isEmpty()) {
            return "enter phone number"
        }
        return null
    }

}
