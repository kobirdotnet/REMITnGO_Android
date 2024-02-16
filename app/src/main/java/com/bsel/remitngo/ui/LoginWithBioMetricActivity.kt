package com.bsel.remitngo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ActivityLoginWithBioMetricBinding
import com.bsel.remitngo.presentation.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executor

class LoginWithBioMetricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginWithBioMetricBinding

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_with_bio_metric)

        preferenceManager = PreferenceManager(this)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence,
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Snackbar.make(
                        binding.root,
                        buildString { append("Your biometric is not registered with REMITnGO App") },
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult,
                ) {
                    super.onAuthenticationSucceeded(result)
                    Snackbar.make(
                        binding.root,
                        buildString { append("Biometric authentication Successful.") },
                        Snackbar.LENGTH_SHORT
                    ).show()

                    preferenceManager.saveData("biometricValue", "true")

                    val intent = Intent(this@LoginWithBioMetricActivity, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Snackbar.make(
                        binding.root,
                        buildString { append("Biometric authentication failed. Please try again.") },
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("REMITnGO Biometric Sign On")
            .setSubtitle("Authentication Required")
            .setNegativeButtonText("Cancel")
            .build()

        binding.loginWithBiometric.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

        binding.cancelButton.setOnClickListener {
            val intent = Intent(this@LoginWithBioMetricActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

}