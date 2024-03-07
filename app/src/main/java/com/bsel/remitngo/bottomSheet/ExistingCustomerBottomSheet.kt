package com.bsel.remitngo.bottomSheet

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ExistingCustomerLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class ExistingCustomerBottomSheet : BottomSheetDialogFragment() {

    private lateinit var existingCustomerBehavior: BottomSheetBehavior<*>

    private lateinit var binding: ExistingCustomerLayoutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.existing_customer_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        existingCustomerBehavior = BottomSheetBehavior.from(view.parent as View)
        existingCustomerBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        existingCustomerBehavior.addBottomSheetCallback(object :
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

        dobFocusListener()
        cmIdMobileFocusListener()

        passwordFocusListener()
        confirmPasswordFocusListener()
        emailFocusListener()

        otpFocusListener()

        binding.submitLayout.visibility = View.VISIBLE
        binding.confirmLayout.visibility = View.GONE
        binding.verifyLayout.visibility = View.GONE

        binding.dobContainer.setEndIconOnClickListener {
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            val defaultYear = currentYear - 25
            val maxDate = Calendar.getInstance()
            maxDate.set(defaultYear, currentMonth, currentDay)
            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    if (!selectedDate.after(Calendar.getInstance())) {
                        val formattedDate =
                            "%02d/%02d/%04d".format(selectedDay, selectedMonth + 1, selectedYear)
                        binding.dob.setText(formattedDate)
                    }
                }, defaultYear, currentMonth, currentDay
            )
            datePickerDialog.datePicker.maxDate = maxDate.timeInMillis
            datePickerDialog.show()
        }

        binding.btnSubmit.setOnClickListener { cmIdDobMobileForm() }

        binding.btnConfirm.setOnClickListener { emailPasswordForm() }

        binding.btnVerify.setOnClickListener { otpForm() }

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun cmIdDobMobileForm() {
        binding.cmIdMobileContainer.helperText = validCmIdMobile()
        binding.dobContainer.helperText = validDob()

        val validCmIdMobile = binding.cmIdMobileContainer.helperText == null
        val validDob = binding.dobContainer.helperText == null

        if (validCmIdMobile && validDob) {
            submitCmIdDobMobileForm()
        }
    }

    private fun submitCmIdDobMobileForm() {
        val cmIdMobile = binding.cmIdMobile.text.toString()
        val dob = binding.dob.text.toString()

        binding.submitLayout.visibility = View.GONE
        binding.confirmLayout.visibility = View.VISIBLE
        binding.verifyLayout.visibility = View.GONE
    }

    private fun emailPasswordForm() {
        binding.passwordContainer.helperText = validPassword()
        binding.confirmPasswordContainer.helperText = validConfirmPassword()
        binding.emailContainer.helperText = validEmail()

        val validPassword = binding.passwordContainer.helperText == null
        val validConfirmPassword = binding.confirmPasswordContainer.helperText == null
        val validEmail = binding.emailContainer.helperText == null

        if (validPassword && validConfirmPassword && validEmail) {
            confirmEmailPasswordForm()
        }
    }

    private fun confirmEmailPasswordForm() {
        val password = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()
        val email = binding.email.text.toString()

        binding.submitLayout.visibility = View.GONE
        binding.confirmLayout.visibility = View.GONE
        binding.verifyLayout.visibility = View.VISIBLE
    }

    private fun otpForm() {
        binding.otpContainer.helperText = validOtp()

        val validOtp = binding.otpContainer.helperText == null

        if (validOtp) {
            verifyOtpForm()
        }
    }

    private fun verifyOtpForm() {
        val otp = binding.otp.text.toString()

        binding.submitLayout.visibility = View.GONE
        binding.confirmLayout.visibility = View.GONE
        binding.verifyLayout.visibility = View.VISIBLE

        dismiss()
    }

    //Form validation

    private fun cmIdMobileFocusListener() {
        binding.cmIdMobile.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.cmIdMobileContainer.helperText = validCmIdMobile()
            }
        }
    }

    private fun validCmIdMobile(): String? {
        val dob = binding.cmIdMobile.text.toString()
        if (dob.isEmpty()) {
            return "enter cm id or mobile number"
        }
        return null
    }

    private fun dobFocusListener() {
        binding.dob.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.dobContainer.helperText = validDob()
            }
        }
    }

    private fun validDob(): String? {
        val dob = binding.dob.text.toString()
        if (dob.isEmpty()) {
            return "select date of birth"
        }
        return null
    }

    private fun passwordFocusListener() {
        binding.password.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val password = binding.password.text.toString()
        if (password.isEmpty()) {
            return "Enter password"
        }
        if (password.length < 8) {
            return "Minimum 8 Character Password"
        }
        if (!password.matches(".*[A-Z].*".toRegex())) {
            return "Must Contain 1 Upper-case Character"
        }
        if (!password.matches(".*[a-z].*".toRegex())) {
            return "Must Contain 1 Lower-case Character"
        }
        if (!password.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }
        return null
    }

    private fun confirmPasswordFocusListener() {
        binding.confirmPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.confirmPasswordContainer.helperText = validConfirmPassword()
            }
        }
    }

    private fun validConfirmPassword(): String? {
        val confirmPassword = binding.confirmPassword.text.toString()
        if (confirmPassword.isEmpty()) {
            return "Enter confirm password"
        }
        if (confirmPassword.length < 8) {
            return "Minimum 8 Character Password"
        }
        if (!confirmPassword.matches(".*[A-Z].*".toRegex())) {
            return "Must Contain 1 Upper-case Character"
        }
        if (!confirmPassword.matches(".*[a-z].*".toRegex())) {
            return "Must Contain 1 Lower-case Character"
        }
        if (!confirmPassword.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }
        return null
    }

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

    private fun otpFocusListener() {
        binding.otp.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.otpContainer.helperText = validOtp()
            }
        }
    }

    private fun validOtp(): String? {
        val otp = binding.otp.text.toString()
        if (otp.isEmpty()) {
            return "Enter valid otp"
        }
        return null
    }

    override fun onStart() {
        super.onStart()
        existingCustomerBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}