package com.bsel.remitngo.presentation.ui.settings.changePassword

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.change_password.ChangePasswordItem
import com.bsel.remitngo.databinding.FragmentChangePasswordBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.settings.SettingsViewModel
import com.bsel.remitngo.presentation.ui.settings.SettingsViewModelFactory
import javax.inject.Inject

class ChangePasswordFragment : Fragment() {
    @Inject
    lateinit var settingsViewModelFactory: SettingsViewModelFactory
    private lateinit var settingsViewModel: SettingsViewModel

    private lateinit var binding: FragmentChangePasswordBinding

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChangePasswordBinding.bind(view)

        (requireActivity().application as Injector).createSettingsSubComponent().inject(this)

        settingsViewModel =
            ViewModelProvider(this, settingsViewModelFactory)[SettingsViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

        passwordFocusListener()
        confirmPasswordFocusListener()

        binding.btnChangePassword.setOnClickListener { changePasswordForm() }

        observeChangePasswordResult()

    }

    private fun observeChangePasswordResult() {
        settingsViewModel.changePasswordResult.observe(this) { result ->
            try {
                if (result != null) {
                    Log.i("info", "Message: " + result!!.message)
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun changePasswordForm() {
        binding.newPasswordContainer.helperText = validPassword()
        binding.confirmPasswordContainer.helperText = validConfirmPassword()

        val validPassword = binding.newPasswordContainer.helperText == null
        val validConfirmPassword = binding.confirmPasswordContainer.helperText == null

        if (validPassword && validConfirmPassword) {
            submitChangePasswordForm()
        }
    }

    private fun submitChangePasswordForm() {
        val oldPassword = binding.currentPassword.text.toString()
        val newPassword = binding.newPassword.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        val changePasswordItem = ChangePasswordItem(
            deviceId = deviceId,
            personId = personId.toInt(),
            oldPassword = oldPassword,
            newPassword = newPassword,
            confirmNewPassword = confirmPassword
        )
        settingsViewModel.changePassword(changePasswordItem)
    }

    //Form validation
    private fun passwordFocusListener() {
        binding.newPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.newPasswordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val password = binding.newPassword.text.toString()
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

}