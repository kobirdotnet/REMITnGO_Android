package com.bsel.remitngo.presentation.login

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bsel.remitngo.R
import com.bsel.remitngo.bottom_sheet.ForgotPasswordBottomSheet
import com.bsel.remitngo.databinding.ActivityLoginBinding
import com.bsel.remitngo.presentation.registration.RegistrationActivity
import com.bsel.remitngo.ui.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val forgotPasswordBottomSheet: ForgotPasswordBottomSheet by lazy { ForgotPasswordBottomSheet() }

    private val CONTACTS_PERMISSION_CODE = 123

    private lateinit var changePassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        changePassword = "null"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkContactsPermission()
        } else {
            // Permissions are automatically granted on versions below Marshmallow
            // Proceed with your logic here
        }

        binding.btnForgotPassword.setOnClickListener {
            changePassword = "12345"
            forgotPasswordBottomSheet.show(supportFragmentManager, forgotPasswordBottomSheet.tag)
        }

        binding.logIn.setOnClickListener { logInForm() }

        binding.signUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        emailFocusListener()
        passwordFocusListener()

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

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra("changePassword", changePassword)
        startActivity(intent)

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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkContactsPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission has already been granted
            // Proceed with your logic here
        } else {
            // Permission hasn't been granted
            requestContactsPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestContactsPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            CONTACTS_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CONTACTS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                // Proceed with your logic here
            } else {
                // Permission denied
                // Handle the denial or inform the user about the consequences
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        // Handle touch events to hide the keyboard when clicking outside EditText
        val v = currentFocus

        // Check if the touch event is an UP or MOVE event and if the focused view is an EditText
        if (v != null &&
            (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) &&
            v is EditText &&
            !v.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            v.getLocationOnScreen(scrcoords)
            val x = ev.rawX + v.getLeft() - scrcoords[0]
            val y = ev.rawY + v.getTop() - scrcoords[1]

            // Hide the keyboard if the touch event is outside the EditText
            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                hideKeyBoard(this)
            }
        }

        // Continue with the default touch event handling
        return super.dispatchTouchEvent(ev)
    }

    fun hideKeyBoard(activity: Activity?) {
        // Hide the soft keyboard from the current window
        if (activity != null && activity.window != null) {
            val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }

}