package com.bsel.remitngo.presentation.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.ForgotPasswordBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.api.TokenManager
import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.databinding.ActivityLoginBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.registration.RegistrationActivity
import com.bsel.remitngo.presentation.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding

    private val forgotPasswordBottomSheet: ForgotPasswordBottomSheet by lazy { ForgotPasswordBottomSheet() }

    private lateinit var changePassword: String

    private lateinit var deviceId: String

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

//        binding.email.setText("mizan.se@outlook.com")
//        binding.password.setText("Nilasish@1994")

        preferenceManager = PreferenceManager(this@LoginActivity)

        (application as Injector).createLoginSubComponent().inject(this)

        loginViewModel =
            ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        emailFocusListener()
        passwordFocusListener()

        binding.logIn.setOnClickListener { logInForm() }

        binding.btnForgotPassword.setOnClickListener {
            changePassword = "12345"
            forgotPasswordBottomSheet.show(supportFragmentManager, forgotPasswordBottomSheet.tag)
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        changePassword = "null"

        observeLoginResult()

        deviceId = getDeviceId(applicationContext)
    }

    private fun observeLoginResult() {
        loginViewModel.loginResult.observe(this) { result ->
            if (result!!.data != null) {
                for (data in result.data!!) {
                    preferenceManager.saveData("personId", data.personId.toString())
                    preferenceManager.saveData("firstName", data.firstName.toString())
                    preferenceManager.saveData("lastName", data.lastName.toString())
                    preferenceManager.saveData("email", data.email.toString())
                    preferenceManager.saveData("mobile", data.mobile.toString())
                    preferenceManager.saveData("dob", data.dateOfBirth.toString())
                }
                TokenManager.setToken(result.token)
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.putExtra("changePassword", changePassword)
                startActivity(intent)
            } else {
                val parentLayout: View = findViewById(android.R.id.content)
                val snackbar =
                    Snackbar.make(parentLayout, result.message.toString(), Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }
    }

    private fun logInForm() {
        binding.emailContainer.helperText = validEmail()
        binding.passwordContainer.helperText = validPassword()

        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null

        if (validEmail && validPassword) {
            submitLogInForm()
        }
    }

    private fun submitLogInForm() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val channel = "Apps"

        val loginItem = LoginItem(
            channel = channel,
            deviceId = deviceId,
            password = password,
            userId = email
        )

        // Call the login method in the ViewModel
        loginViewModel.loginUser(loginItem)

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

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val v = currentFocus
        if (v != null &&
            (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) &&
            v is EditText &&
            !v.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            v.getLocationOnScreen(scrcoords)
            val x = ev.rawX + v.getLeft() - scrcoords[0]
            val y = ev.rawY + v.getTop() - scrcoords[1]
            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                hideKeyBoard(this)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun hideKeyBoard(activity: Activity?) {
        if (activity != null && activity.window != null) {
            val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }

}