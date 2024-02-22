package com.bsel.remitngo.presentation.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.CollectionPointWalletAdapter
import com.bsel.remitngo.adapter.CollectionPointBankAdapter
import com.bsel.remitngo.adapter.PaymentModeAdapter
import com.bsel.remitngo.adapter.TransactionModeAdapter
import com.bsel.remitngo.databinding.FragmentMainBinding
import com.bsel.remitngo.model.CollectionPointWallet
import com.bsel.remitngo.model.CollectionPointBank
import com.bsel.remitngo.model.PaymentMode
import com.bsel.remitngo.model.TransactionMode
import com.emerchantpay.gateway.genesisandroid.api.constants.*
import com.emerchantpay.gateway.genesisandroid.api.interfaces.financial.threedsv2.definitions.*
import com.emerchantpay.gateway.genesisandroid.api.models.*
import java.text.DecimalFormat
import java.util.*

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private var exchangeRate: Double = 150.55
    private val decimalFormat = DecimalFormat("#.##")

    private lateinit var orderType: String
    private lateinit var paymentType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.collectionPointBankLayout.visibility = View.GONE
        binding.collectionPointWalletLayout.visibility = View.GONE

        orderType = "3"
        binding.orderModeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            // checkedId is the RadioButton id that is checked
            when (checkedId) {
                R.id.bank_account -> {
                    binding.collectionPointBankLayout.visibility = View.GONE
                    binding.collectionPointBank.text = null
                    binding.collectionPointWalletLayout.visibility = View.GONE
                    binding.collectionPointWallet.text = null

                    binding.selectedTransactionMode.text = "Bank Account"
                    orderType = "3"
                }
                R.id.instant_credit -> {
                    binding.collectionPointBankLayout.visibility = View.GONE
                    binding.collectionPointBank.text = null
                    binding.collectionPointWalletLayout.visibility = View.GONE
                    binding.collectionPointWallet.text = null

                    binding.selectedTransactionMode.text = "Instant credit"
                    orderType = "5"
                }
                R.id.cash_pickup -> {
                    binding.collectionPointBankLayout.visibility = View.VISIBLE
                    binding.collectionPointWalletLayout.visibility = View.GONE
                    binding.collectionPointWallet.text = null

                    binding.selectedTransactionMode.text = "Cash pickup"
                    orderType = "2"
                }
                R.id.mobile_wallet -> {
                    binding.collectionPointBankLayout.visibility = View.GONE
                    binding.collectionPointBank.text = null
                    binding.collectionPointWalletLayout.visibility = View.VISIBLE

                    binding.selectedTransactionMode.text = "Mobile wallet"
                    orderType = "1"
                }
            }
        }

        paymentType = "Card Payment"
        binding.paymentModeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            // checkedId is the RadioButton id that is checked
            when (checkedId) {
                R.id.bank_transfer -> {
                    paymentType = "Bank Transfer"
                }
                R.id.card_payment -> {
                    paymentType = "Card Payment"
                }
            }
        }

        // Array of TransactionMode objects
        val transactionMode = arrayOf(
            TransactionMode("Bank Account"),
            TransactionMode("Cash pickup"),
            TransactionMode("Mobile wallet")
        )
        // Set up the adapter for AutoCompleteTextView
        val transactionModeAdapter =
            TransactionModeAdapter(requireContext(), R.layout.transaction_mode, transactionMode)
        binding.transactionMode.setAdapter(transactionModeAdapter)
        binding.transactionMode.setOnItemClickListener { _, _, position, _ ->
            // Get the selected item from the adapter
            val transactionMode = transactionModeAdapter.getItem(position)
            val transactionModeName = transactionMode?.name

            if (transactionModeName == "Bank Account") {
                binding.collectionPointBankLayout.visibility = View.GONE
                binding.collectionPointBank.text = null
                binding.collectionPointWalletLayout.visibility = View.GONE
                binding.collectionPointWallet.text = null

                binding.selectedTransactionMode.text = "Bank Account"

            } else if (transactionModeName == "Cash pickup") {
                binding.collectionPointBankLayout.visibility = View.VISIBLE
                binding.collectionPointWalletLayout.visibility = View.GONE
                binding.collectionPointWallet.text = null

                binding.selectedTransactionMode.text = "Cash pickup"

            } else if (transactionModeName == "Mobile wallet") {
                binding.collectionPointBankLayout.visibility = View.GONE
                binding.collectionPointBank.text = null
                binding.collectionPointWalletLayout.visibility = View.VISIBLE

                binding.selectedTransactionMode.text = "Mobile wallet"
            }

        }

        // Array of CollectionPointBank objects
        val collectionPointBank = arrayOf(
            CollectionPointBank("BRAC Bank Ltd"),
            CollectionPointBank("City Bank Ltd"),
            CollectionPointBank("Dhaka Bank Ltd")
        )
        // Set up the adapter for AutoCompleteTextView
        val collectionPointBankAdapter =
            CollectionPointBankAdapter(
                requireContext(),
                R.layout.collection_point_bank,
                collectionPointBank
            )
        binding.collectionPointBank.setAdapter(collectionPointBankAdapter)

        // Array of CollectionPoint objects
        val collectionPointWallet = arrayOf(
            CollectionPointWallet("bKash"),
            CollectionPointWallet("Nagad")
        )
        // Set up the adapter for AutoCompleteTextView
        val collectionPointWalletAdapter =
            CollectionPointWalletAdapter(requireContext(), R.layout.collection_point_wallet, collectionPointWallet)
        binding.collectionPointWallet.setAdapter(collectionPointWalletAdapter)

        // Array of PaymentMode objects
        val paymentMode = arrayOf(
            PaymentMode("Bank Transfer"),
            PaymentMode("Card Payment")
        )
        // Set up the adapter for AutoCompleteTextView
        val paymentModeAdapter =
            PaymentModeAdapter(requireContext(), R.layout.payment_mode, paymentMode)
        binding.paymentMode.setAdapter(paymentModeAdapter)
        binding.paymentMode.setOnItemClickListener { _, _, position, _ ->
            val paymentMode = paymentModeAdapter.getItem(position)
            paymentType = paymentMode?.name.toString()
        }

        //exchangeRate
        binding.exchangeRate.text = exchangeRate.toString()
        //GBP Amount
        binding.sendAmount.setText("100")
        updateValuesGBP()
        binding.sendAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!binding.sendAmount.isFocused) return
                updateValuesGBP()
            }
        })

        //BDT Amount
        binding.receiveAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!binding.receiveAmount.isFocused) return
                updateValuesBDT()
            }
        })

        binding.btnContinue.setOnClickListener {
            val bundle = Bundle().apply {
                putString("paymentType", paymentType)
                putString("orderType",orderType)
            }
            findNavController().navigate(
                R.id.action_nav_main_to_nav_choose_beneficiary,
                bundle
            )
        }

        // Handle back press event
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("App termination")
            builder.setMessage("Do you want to close the app?")
            builder.setPositiveButton("EXIT") { _: DialogInterface, _: Int ->
                ActivityCompat.finishAffinity(requireActivity())
            }
            builder.setNegativeButton("STAY") { _: DialogInterface, _: Int -> }
            val dialog = builder.create()
            dialog.show()
        }

    }

    fun updateValuesBDT() {
        val bdtValue = binding.receiveAmount.text.toString().toDoubleOrNull()
        if (bdtValue != null) {
            val gbpValue = bdtValue / exchangeRate
            val formattedGBP = decimalFormat.format(gbpValue)
            binding.sendAmount.setText(formattedGBP.toString())
        } else {
            binding.sendAmount.setText("")
        }
    }

    fun updateValuesGBP() {
        val gbpValue = binding.sendAmount.text.toString().toDoubleOrNull()
        if (gbpValue != null) {
            val bdtValue = gbpValue * exchangeRate
            val formattedBDT = decimalFormat.format(bdtValue)
            binding.receiveAmount.setText(formattedBDT.toString())
        } else {
            binding.receiveAmount.setText("")
        }
    }

}
