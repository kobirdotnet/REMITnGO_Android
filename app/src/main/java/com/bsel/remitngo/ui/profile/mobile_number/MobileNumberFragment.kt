package com.bsel.remitngo.ui.profile.mobile_number

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.FragmentMobileNumberBinding

class MobileNumberFragment : Fragment() {

    private lateinit var binding: FragmentMobileNumberBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mobile_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMobileNumberBinding.bind(view)

        phoneFocusListener()

        binding.btnSave.setOnClickListener { phoneNumberForm() }

    }

    private fun phoneNumberForm() {
        binding.phoneNumberContainer.helperText = validPhone()

        val validPhone = binding.phoneNumberContainer.helperText == null

        if (validPhone) {
            submitPhoneNumberForm()
        }

    }

    private fun submitPhoneNumberForm() {
        val phoneNumber = binding.phoneNumber.text.toString()
    }

    //Form validation
    private fun phoneFocusListener() {
        binding.phoneNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.phoneNumberContainer.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String? {
        val phone = binding.phoneNumber.text.toString()
        if (phone.isEmpty()) {
            return "Enter phone number"
        }
        if (!phone.matches(".*[0-9].*".toRegex())) {
            return "Must be all digits"
        }
        if (phone.length != 11) {
            return "Must be 11 digits"
        }
        return null
    }

}