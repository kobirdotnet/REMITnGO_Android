package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.phoneVerification.PhoneOtpVerifyItem
import com.bsel.remitngo.data.model.phoneVerification.PhoneVerifyItem
import com.bsel.remitngo.databinding.TransactionOtpLayoutLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import javax.inject.Inject

class TransactionOtpVerifyBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var phoneOtpVerifyBehavior: BottomSheetBehavior<*>

    private lateinit var binding: TransactionOtpLayoutLayoutBinding

    private lateinit var preferenceManager: PreferenceManager

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private lateinit var personId: String

    private lateinit var otp: String
    private lateinit var message: String

    private var phoneNumber: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.transaction_otp_layout_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        phoneOtpVerifyBehavior = BottomSheetBehavior.from(view.parent as View)
        phoneOtpVerifyBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        phoneOtpVerifyBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createProfileSubComponent().inject(this)

        profileViewModel =
            ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        binding.phoneNumber.setText(phoneNumber)

        binding.verifyLayout.visibility = View.VISIBLE
        binding.validationLayout.visibility = View.GONE

        phoneFocusListener()
        otpFocusListener()

        binding.btnVerify.setOnClickListener { phoneNumberForm() }

        binding.cancelButton.setOnClickListener { dismiss() }

        observePhoneVerifyResult()
        observePhoneOtpVerifyResult()

        return bottomSheet
    }

    fun setPhoneNumber(phone: String) {
        phoneNumber = phone
    }

    private fun observePhoneVerifyResult() {
        profileViewModel.phoneVerifyResult.observe(this) { result ->
            if (result!! != null) {
                val resultMessage = result!!.message
                val parts = resultMessage!!.split("*")
                if (parts.size >= 3) {
                    personId = parts[0]
                    otp = parts[1]
                    message = parts.subList(2, parts.size).joinToString("*")
                } else {
                    message = result!!.message.toString()
                }

                if (::personId.isInitialized && personId != "null" || ::otp.isInitialized && otp != "null") {
                    binding.verifyLayout.visibility = View.GONE
                    binding.validationLayout.visibility = View.VISIBLE

                    binding.otpVerifyMessage.text = message

                    binding.btnOtpValidation.setOnClickListener { otpValidationForm() }
                } else {
                    binding.verifyLayout.visibility = View.VISIBLE
                    binding.validationLayout.visibility = View.GONE
                }

            }
        }
    }

    private fun observePhoneOtpVerifyResult() {
        profileViewModel.phoneOtpVerifyResult.observe(this) { result ->
            if (result!!.code == "000") {
                dismiss()
                binding.verifyLayout.visibility = View.GONE
                binding.validationLayout.visibility = View.GONE
            } else {
                binding.verifyLayout.visibility = View.GONE
                binding.validationLayout.visibility = View.VISIBLE

                binding.otpVerifyMessage.text = result!!.data.toString()
                binding.otpVerifyMessage.setTextColor(Color.RED)
            }
        }
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

        val phoneVerifyItem = PhoneVerifyItem(
            isVerifiyEmail = false,
            phoneOrEmail = phoneNumber
        )
        profileViewModel.phoneVerify(phoneVerifyItem)
    }

    private fun otpValidationForm() {
        binding.otpContainer.helperText = validOtp()
        val validOtp = binding.otpContainer.helperText == null
        if (validOtp)
            submitOtp()
    }

    private fun submitOtp() {
        val otp = binding.otp.text.toString()
        val phoneOtpVerifyItem = PhoneOtpVerifyItem(
            isVerifiyEmail = false,
            otp = otp,
            personId = personId.toInt()
        )
        profileViewModel.phoneOtpVerify(phoneOtpVerifyItem)
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
        if (phone.length != 10) {
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

    private fun getDeviceId(context: Context): String {
        val deviceId: String

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } else {
            @Suppress("DEPRECATION")
            deviceId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

        return deviceId
    }

    private fun getIPAddress(context: Context): String? {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        return String.format(
            Locale.getDefault(),
            "%d.%d.%d.%d",
            ipAddress and 0xff,
            ipAddress shr 8 and 0xff,
            ipAddress shr 16 and 0xff,
            ipAddress shr 24 and 0xff
        )
    }


    override fun onStart() {
        super.onStart()
        phoneOtpVerifyBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}