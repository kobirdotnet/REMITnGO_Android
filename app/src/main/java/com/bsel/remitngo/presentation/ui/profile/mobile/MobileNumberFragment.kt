package com.bsel.remitngo.presentation.ui.profile.mobile

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.PhoneOtpVerifyBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.phoneVerification.PhoneVerifyItem
import com.bsel.remitngo.databinding.FragmentMobileNumberBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import java.util.*
import javax.inject.Inject

class MobileNumberFragment : Fragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var binding: FragmentMobileNumberBinding

    private val phoneOtpVerifyBottomSheet: PhoneOtpVerifyBottomSheet by lazy { PhoneOtpVerifyBottomSheet() }

    private lateinit var preferenceManager: PreferenceManager

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private lateinit var personId: String

    private lateinit var mobile: String
    private lateinit var message: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mobile_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMobileNumberBinding.bind(view)

        (requireActivity().application as Injector).createProfileSubComponent().inject(this)

        profileViewModel =
            ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        mobile = arguments?.getString("mobile").toString()
        binding.phoneNumber.setText(mobile)

        phoneFocusListener()

        binding.btnSave.setOnClickListener { phoneNumberForm() }

        observePhoneVerifyResult()

    }

    private fun observePhoneVerifyResult() {
        profileViewModel.phoneVerifyResult.observe(this) { result ->
            if (result!! != null) {
                message = result!!.message.toString()

                phoneOtpVerifyBottomSheet.show(childFragmentManager, phoneOtpVerifyBottomSheet.tag)
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

}