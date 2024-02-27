package com.bsel.remitngo.presentation.ui.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.FragmentPaymentCardBinding
import com.bsel.remitngo.presentation.di.Injector
import javax.inject.Inject

class PaymentCardFragment : Fragment() {
    @Inject
    lateinit var paymentViewModelFactory: PaymentViewModelFactory
    private lateinit var paymentViewModel: PaymentViewModel

    private lateinit var binding: FragmentPaymentCardBinding

    private lateinit var transactionCode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentCardBinding.bind(view)

        (requireActivity().application as Injector).createPaymentSubComponent().inject(this)

        paymentViewModel =
            ViewModelProvider(this, paymentViewModelFactory)[PaymentViewModel::class.java]

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_nav_complete_card_transaction_to_nav_main)
        }

        transactionCode = arguments?.getString("transactionCode").toString()
        paymentViewModel.paymentStatus(transactionCode)

        observePaymentStatusResult()

    }

    private fun observePaymentStatusResult() {
        paymentViewModel.paymentStatusResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info", "payment Status successful: $result")
            } else {
                Log.i("info", "payment Status failed")
            }
        }
    }

}