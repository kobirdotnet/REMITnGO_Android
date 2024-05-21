package com.bsel.remitngo.presentation.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import android.provider.Settings
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.MarketingBottomSheet
import com.bsel.remitngo.databinding.FragmentSettingsBinding
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnMarketingItemSelectedListener
import com.bsel.remitngo.data.model.marketing.MarketingValue
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executor

class SettingsFragment : Fragment(), OnMarketingItemSelectedListener {

    private lateinit var binding: FragmentSettingsBinding

    private val marketingBottomSheet: MarketingBottomSheet by lazy { MarketingBottomSheet() }

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private var isBiometricEnabled: Boolean? = false
    private var biometricValue: String? = null

    private var rdoEmail: Boolean? = false
    private var rdoSMS: Boolean? = false
    private var rdoPhone: Boolean? = false
    private var rdoPost: Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        preferenceManager = PreferenceManager(requireContext())

        binding.switchBiometric.isChecked = false
        biometricValue = preferenceManager.loadData("biometricValue").toString()
        try {
            when (biometricValue) {
                "true" -> {
                    binding.switchBiometric.isChecked = true
                }
                "false" -> {
                    binding.switchBiometric.isChecked = false
                }
            }
        }catch (e:NullPointerException){
            e.localizedMessage
        }

        binding.switchBiometric.setOnCheckedChangeListener { _, isChecked ->
            checkDeviceHasBiometric()
            isBiometricEnabled = isChecked
        }

        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence,
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Snackbar.make(
                        binding.root,
                        buildString { append("There some problem with your biometric") },
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult,
                ) {
                    super.onAuthenticationSucceeded(result)
                    if (isBiometricEnabled!!) {
                        Snackbar.make(
                            binding.root,
                            buildString { append("Authentication Successful, Biometric enable") },
                            Snackbar.LENGTH_SHORT
                        ).show()
                        preferenceManager.saveData("biometricValue", "true").toString()
                    } else {
                        Snackbar.make(
                            binding.root,
                            buildString { append("Authentication Successful, Biometric disable") },
                            Snackbar.LENGTH_SHORT
                        ).show()
                        preferenceManager.saveData("biometricValue", "false").toString()
                    }

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Snackbar.make(
                        binding.root,
                        buildString { append("Authentication failed") },
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("REMITnGO Biometric Sign On")
            .setSubtitle("Authentication Required")
            .setNegativeButtonText("Cancel")
            .build()


        binding.changePassword.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_settings_to_nav_change_password
            )
        }

        binding.setPreferences.setOnClickListener {
            if (!marketingBottomSheet.isAdded) {
                marketingBottomSheet.setSelectedMarketing(
                    rdoEmail!!,
                    rdoSMS!!,
                    rdoPhone!!,
                    rdoPost!!
                )
                marketingBottomSheet.itemSelectedListener = this
                marketingBottomSheet.show(childFragmentManager, marketingBottomSheet.tag)
            }
        }
    }

    private fun checkDeviceHasBiometric() {
        val biometricManager = BiometricManager.from(requireContext())
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Snackbar.make(
                    binding.root,
                    buildString { append("Authentication error: No biometric features available on this device.") },
                    Snackbar.LENGTH_SHORT
                ).show()
                binding.switchBiometric.isChecked = false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Snackbar.make(
                    binding.root,
                    buildString { append("Authentication error: Biometric features are currently unavailable.") },
                    Snackbar.LENGTH_SHORT
                ).show()
                binding.switchBiometric.isChecked = false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    )
                }
                startActivityForResult(enrollIntent, 100)
                binding.switchBiometric.isChecked = false
            }
        }
    }

    override fun onMarketingItemSelected(selectedItem: MarketingValue) {
        rdoEmail = selectedItem.rdoEmail == true
        rdoSMS = selectedItem.rdoSMS == true
        rdoPhone = selectedItem.rdoPhone == true
        rdoPost = selectedItem.rdoPost == true
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.nav_main)
        }
    }

}