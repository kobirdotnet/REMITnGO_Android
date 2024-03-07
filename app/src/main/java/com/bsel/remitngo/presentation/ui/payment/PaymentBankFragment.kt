package com.bsel.remitngo.presentation.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.FragmentPaymentBankBinding

class PaymentBankFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBankBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBankBinding.bind(view)

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_nav_complete_bank_transaction_to_nav_main)
        }

    }

}