package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ForgotPasswordLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ForgotPasswordBottomSheet : BottomSheetDialogFragment() {

    private lateinit var forgotPasswordBehavior: BottomSheetBehavior<*>

    private lateinit var binding: ForgotPasswordLayoutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.forgot_password_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        forgotPasswordBehavior = BottomSheetBehavior.from(view.parent as View)
        forgotPasswordBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        forgotPasswordBehavior.addBottomSheetCallback(object :
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

        emailFocusListener()
        phoneFocusListener()

        binding.btnVerify.setOnClickListener { passwordForm() }

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun passwordForm() {
        binding.emailContainer.helperText = validEmail()
        binding.phoneNumberContainer.helperText = validPhone()

        val validEmail = binding.emailContainer.helperText == null
        val validPhone = binding.phoneNumberContainer.helperText == null

        if (validEmail && validPhone) {
            verifyPasswordForm()
        }
    }

    private fun verifyPasswordForm() {
        val email = binding.email.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()

        dismiss()

    }

    //Form validation
    private fun emailFocusListener() {
        binding.email.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val email = binding.email.text.toString()
        if (email.isEmpty()) {
            return "Enter valid email address"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Invalid email address"
        }
        return null
    }

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


    override fun onStart() {
        super.onStart()
        forgotPasswordBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}