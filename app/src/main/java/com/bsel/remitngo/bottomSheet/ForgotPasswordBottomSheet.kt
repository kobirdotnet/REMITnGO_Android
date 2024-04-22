package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Patterns
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.forgotPassword.ForgotPasswordItem
import com.bsel.remitngo.data.model.forgotPassword.OtpValidationItem
import com.bsel.remitngo.data.model.forgotPassword.SetPasswordItem
import com.bsel.remitngo.databinding.ForgotPasswordLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.login.LoginViewModel
import com.bsel.remitngo.presentation.ui.login.LoginViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class ForgotPasswordBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var forgotPasswordBehavior: BottomSheetBehavior<*>

    private lateinit var binding: ForgotPasswordLayoutBinding

    private var changeValue: Boolean = true
    private var otpSendBy: String = "Email"

    private lateinit var personId: String
    private lateinit var message: String

    private lateinit var emailAddress: String
    private lateinit var phoneNumber: String

    private lateinit var countDownTimer: CountDownTimer

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

        (requireActivity().application as Injector).createLoginSubComponent().inject(this)

        loginViewModel =
            ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        binding.verifyLayout.visibility = View.VISIBLE
        binding.validationLayout.visibility = View.GONE
        binding.setPasswordLayout.visibility = View.GONE

        emailFocusListener()
        phoneFocusListener()
        otpFocusListener()
        newPasswordFocusListener()
        confirmNewPasswordFocusListener()

        binding.btnEmail.setOnClickListener {

            binding.emailLayout.visibility = View.GONE
            binding.mobileLayout.visibility = View.VISIBLE

            binding.email.text = null

            changeValue = false
            otpSendBy = "Phone"
        }

        binding.btnMobile.setOnClickListener {

            binding.emailLayout.visibility = View.VISIBLE
            binding.mobileLayout.visibility = View.GONE

            binding.phoneNumber.text = null

            changeValue = true
            otpSendBy = "Email"
        }

        binding.btnVerify.setOnClickListener { passwordForm() }

        binding.cancelButton.setOnClickListener { dismiss() }

        observeForgotPasswordResult()
        observeOtpResult()
        observeSetPasswordResult()

        return bottomSheet
    }

    private fun observeForgotPasswordResult() {
        loginViewModel.forgotPasswordResult.observe(this) { result ->
            if (result!! != null) {
                val resultMessage = result!!.message
                val parts = resultMessage!!.split("*")
                if (parts.size >= 2) {
                    personId = parts[0]
                    message = parts.subList(1, parts.size).joinToString("*")
                } else {
                    message = result!!.message.toString()
                }

                if (::personId.isInitialized && personId != "null") {
                    binding.verifyLayout.visibility = View.GONE
                    binding.validationLayout.visibility = View.VISIBLE
                    binding.setPasswordLayout.visibility = View.GONE

                    binding.otpVerifyMessage.text = message

                    binding.btnOtpValidation.setOnClickListener { otpValidationForm() }

                    startCountDown()

                } else {
                    binding.verifyLayout.visibility = View.VISIBLE
                    binding.validationLayout.visibility = View.GONE
                    binding.setPasswordLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun observeOtpResult() {
        loginViewModel.otpValidationResult.observe(this) { result ->
            if (result!! != null) {
                if (result.code == "000") {
                    binding.verifyLayout.visibility = View.GONE
                    binding.validationLayout.visibility = View.GONE
                    binding.setPasswordLayout.visibility = View.VISIBLE

                    binding.btnSetPassword.setOnClickListener { setPasswordForm() }
                } else {

                    binding.verifyLayout.visibility = View.GONE
                    binding.validationLayout.visibility = View.VISIBLE
                    binding.setPasswordLayout.visibility = View.GONE

                    binding.otpVerifyMessage.text = result!!.data.toString()
                    binding.otpVerifyMessage.setTextColor(Color.RED)
                }
            }
        }
    }

    private fun observeSetPasswordResult() {
        loginViewModel.setPasswordResult.observe(this) { result ->
            if (result!! != null) {
                if (result!!.code == "000") {
                    binding.verifyLayout.visibility = View.VISIBLE
                    binding.validationLayout.visibility = View.GONE
                    binding.setPasswordLayout.visibility = View.GONE
                    dismiss()
                } else {
                    binding.verifyLayout.visibility = View.GONE
                    binding.validationLayout.visibility = View.GONE
                    binding.setPasswordLayout.visibility = View.VISIBLE

                    binding.setPasswordMessage.text = result!!.message.toString()
                    binding.setPasswordMessage.setTextColor(Color.RED)
                }
            }
        }
    }

    private fun startCountDown() {
        binding.otpTimeCounter.visibility=View.VISIBLE
        binding.btnOtpSendAgain.visibility=View.GONE
        countDownTimer = object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                val minutes = secondsLeft / 60
                val seconds = secondsLeft % 60
                binding.otpTimeCounter.text = "Resend OTP in ${String.format("%02d:%02d", minutes, seconds)} seconds"
            }

            override fun onFinish() {
                binding.otpTimeCounter.visibility=View.GONE
                binding.btnOtpSendAgain.visibility=View.VISIBLE
                binding.btnOtpSendAgain.setOnClickListener {passwordForm()}
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countDownTimer.isInitialized){
            countDownTimer.cancel()
        }
    }

    private fun passwordForm() {
        if (changeValue) {
            binding.emailContainer.helperText = validEmail()
            val validEmail = binding.emailContainer.helperText == null
            if (validEmail)
                submitEmail()
        } else if (!changeValue) {
            binding.phoneNumberContainer.helperText = validPhone()
            val validPhone = binding.phoneNumberContainer.helperText == null
            if (validPhone)
                submitMobile()
        }
    }

    private fun submitEmail() {
        emailAddress = binding.email.text.toString()

        val forgotPasswordItem = ForgotPasswordItem(
            isForgotByEmail = true,
            phoneOrEmail = emailAddress
        )
        loginViewModel.forgotPassword(forgotPasswordItem)
    }

    private fun submitMobile() {
        phoneNumber = binding.phoneNumber.text.toString()

        val forgotPasswordItem = ForgotPasswordItem(
            isForgotByEmail = false,
            phoneOrEmail = phoneNumber
        )
        loginViewModel.forgotPassword(forgotPasswordItem)
    }

    private fun otpValidationForm() {
        binding.otpContainer.helperText = validOtp()
        val validOtp = binding.otpContainer.helperText == null
        if (validOtp)
            submitOtp()
    }

    private fun submitOtp() {
        val otp = binding.otp.text.toString()

        val otpValidationItem = OtpValidationItem(
            otp = otp,
            otpSendBy = otpSendBy,
            otpType = 2,
            personId = personId.toInt()
        )
        loginViewModel.otpValidation(otpValidationItem)
    }

    private fun setPasswordForm() {
        binding.newPasswordContainer.helperText = validNewPassword()
        val validNewPassword = binding.newPasswordContainer.helperText == null
        binding.confirmNewPasswordContainer.helperText = validConfirmNewPassword()
        val validConfirmNewPassword = binding.confirmNewPasswordContainer.helperText == null
        if (validNewPassword && validConfirmNewPassword) {
            submitNewPassword()
        }
    }

    private fun submitNewPassword() {
        val newPassword = binding.newPassword.text.toString()
        val confirmNewPassword = binding.confirmNewPassword.text.toString()

        val setPasswordItem = SetPasswordItem(
            confirmNewPassword = confirmNewPassword,
            newPassword = newPassword,
            otpType = 2,
            personId = personId.toInt()
        )
        loginViewModel.setPassword(setPasswordItem)
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
            return "Enter OTP number"
        }
        return null
    }

    private fun newPasswordFocusListener() {
        binding.newPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.newPasswordContainer.helperText = validNewPassword()
            }
        }
    }

    private fun validNewPassword(): String? {
        val newPassword = binding.newPassword.text.toString()
        if (newPassword.isEmpty()) {
            return "Enter password"
        }
        if (newPassword.length < 8) {
            return "Minimum 8 Character Password"
        }
        if (!newPassword.matches(".*[A-Z].*".toRegex())) {
            return "Must Contain 1 Upper-case Character"
        }
        if (!newPassword.matches(".*[a-z].*".toRegex())) {
            return "Must Contain 1 Lower-case Character"
        }
        if (!newPassword.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }
        return null
    }

    private fun confirmNewPasswordFocusListener() {
        binding.confirmNewPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.confirmNewPasswordContainer.helperText = validConfirmNewPassword()
            }
        }
    }

    private fun validConfirmNewPassword(): String? {
        val confirmNewPassword = binding.confirmNewPassword.text.toString()
        if (confirmNewPassword.isEmpty()) {
            return "Enter confirm password"
        }
        if (confirmNewPassword.length < 8) {
            return "Minimum 8 Character Password"
        }
        if (!confirmNewPassword.matches(".*[A-Z].*".toRegex())) {
            return "Must Contain 1 Upper-case Character"
        }
        if (!confirmNewPassword.matches(".*[a-z].*".toRegex())) {
            return "Must Contain 1 Lower-case Character"
        }
        if (!confirmNewPassword.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }
        return null
    }


    override fun onStart() {
        super.onStart()
        forgotPasswordBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}