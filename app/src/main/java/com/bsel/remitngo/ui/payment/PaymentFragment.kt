package com.bsel.remitngo.ui.payment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val paymentViewModel =
            ViewModelProvider(this)[PaymentViewModel::class.java]

        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textPayment
//        paymentViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.addCard.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_payment_to_nav_card
            )
        }

        binding.referralCode.setOnClickListener {
            // Create an intent to share the referral code
            val url =
                "I am recommending to you send money abroad using the new REMITnGO mobile app. Your first transaction is completely FREE - just use the code " + "CM007788" + " when you sign up. The app is free to download, get it now from https://app.remitngo.com"
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }
            // Start the activity to choose sharing method
            startActivity(Intent.createChooser(sendIntent, "REMITnGO"))
        }
        return root
    }

    override fun onResume() {
        super.onResume()

        // Intercept the back button press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Navigate to home fragment using NavController
            findNavController().navigate(R.id.nav_main)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}