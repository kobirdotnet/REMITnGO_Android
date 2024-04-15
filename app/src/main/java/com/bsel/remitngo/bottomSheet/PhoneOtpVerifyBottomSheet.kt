package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.phoneVerification.PhoneOtpVerifyItem
import com.bsel.remitngo.databinding.PhoneOtpVerifyLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class PhoneOtpVerifyBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var phoneOtpVerifyBehavior: BottomSheetBehavior<*>

    private lateinit var binding: PhoneOtpVerifyLayoutBinding

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var personId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.phone_otp_verify_layout, null)
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

        otpFocusListener()

        binding.cancelButton.setOnClickListener { dismiss() }

        binding.btnOtpValidation.setOnClickListener { otpValidationForm() }
        observePhoneOtpVerifyResult()

        return bottomSheet
    }

    private fun observePhoneOtpVerifyResult() {
        profileViewModel.phoneOtpVerifyResult.observe(this) { result ->
            if (result!! != null) {
                if (result!!.code=="000"){
                    dismiss()
                    findNavController().navigate(
                        R.id.action_nav_mobile_number_to_nav_my_profile
                    )
                }else{
                    binding.otpVerifyMessage.text = result!!.data.toString()
                }
            }
        }
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


    override fun onStart() {
        super.onStart()
        phoneOtpVerifyBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}