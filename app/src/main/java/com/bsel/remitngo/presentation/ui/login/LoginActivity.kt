package com.bsel.remitngo.presentation.ui.login

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding

    private val forgotPasswordBottomSheet: ForgotPasswordBottomSheet by lazy { ForgotPasswordBottomSheet() }

    private lateinit var deviceId: String

    private lateinit var preferenceManager: PreferenceManager

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN: Int = 123
    lateinit var gso: GoogleSignInOptions
    lateinit var mAuth: FirebaseAuth

    private val REQUEST_CONTACTS_AND_CAMERA_PERMISSIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        (application as Injector).createLoginSubComponent().inject(this)
        loginViewModel =
            ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        requestContactsAndCameraPermissions()

        preferenceManager = PreferenceManager(this@LoginActivity)

        mAuth = FirebaseAuth.getInstance()
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("862286959030-7uv1qclkd3lum3nc9q3ovu2q4pqncn37.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)

        deviceId = getDeviceId(applicationContext)

        binding.email.setText("mizan.se@outlook.com")
        binding.password.setText("Nilasish@1994")

        emailFocusListener()
        passwordFocusListener()

        binding.btnForgotPassword.setOnClickListener {
            forgotPasswordBottomSheet.show(supportFragmentManager, forgotPasswordBottomSheet.tag)
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        binding.logIn.setOnClickListener { logInForm() }

        binding.googleLogin.setOnClickListener { googleLogin() }

        observeLoginResult()
    }

    private fun observeLoginResult() {
        loginViewModel.loginResult.observe(this) { result ->
            if (result!!.data != null) {
                for (data in result.data!!) {
                    preferenceManager.saveData("customerId", data.id.toString())
                    preferenceManager.saveData("personId", data.personId.toString())
                    preferenceManager.saveData("firstName", data.firstName.toString())
                    preferenceManager.saveData("lastName", data.lastName.toString())
                    preferenceManager.saveData("email", data.email.toString())
                    preferenceManager.saveData("mobile", data.mobile.toString())
                    preferenceManager.saveData("dob", data.dateOfBirth.toString())
                }
                TokenManager.setToken(result.token)
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
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
        loginViewModel.loginUser(loginItem)

    }

    private fun googleLogin() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.i("info", "loginTask email: ${task.result.email}")
            Log.i("info", "loginTask id: ${task.result.id}")
            Log.i("info", "loginTask idToken: ${task.result.idToken}")
            Log.i("info", "loginTask displayName: ${task.result.displayName}")
            Log.i("info", "loginTask familyName: ${task.result.familyName}")
            Log.i("info", "loginTask account: ${task.result.account}")
            val exception = task.exception
            Log.i("info", "exception: $exception")

            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.i("info", "loginAccount email: ${account.email}")
                Log.i("info", "loginAccount id: ${account.id}")
                Log.i("info", "loginAccount idToken: ${account.idToken}")
                Log.i("info", "loginAccount displayName: ${account.displayName}")
                Log.i("info", "loginAccount familyName: ${account.familyName}")
                Log.i("info", "loginAccount account: ${account.account}")
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this@LoginActivity, "Login Successful: ", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LoginActivity, "Login Failed: ", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun requestContactsAndCameraPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                this@LoginActivity,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.READ_CONTACTS)
        }

        if (ContextCompat.checkSelfPermission(
                this@LoginActivity,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this@LoginActivity,
                permissionsToRequest.toTypedArray(),
                REQUEST_CONTACTS_AND_CAMERA_PERMISSIONS
            )
        }
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

    override fun onStart() {
        super.onStart()

//         Check if user is signed in (non-null) and update UI accordingly.

        val user = mAuth.currentUser
        if (user != null) {
            Toast.makeText(this@LoginActivity, "already signIn: ", Toast.LENGTH_SHORT).show()
        }
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