package com.bsel.remitngo.presentation.ui.login

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
import com.bsel.remitngo.data.model.login.AccessToken
import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.databinding.ActivityLoginBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.main.MainActivity
import com.bsel.remitngo.presentation.ui.registration.RegistrationActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.*
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
        checkLocationPermissions()

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
            try {
                if (result == null) {
                    showDialog("Login Failed", "Unable to login. Please try again later.")
                } else {
                    if (result.message == "Successful") {
                        if (result.data != null) {
                            val token = result.data.accessToken
                            val accessToken = decodeJWT(token!!)
                            preferenceManager.saveData("customerId", accessToken.customerId)
                            preferenceManager.saveData("personId", accessToken.personId)
                            preferenceManager.saveData("firstName", accessToken.firstName)
                            preferenceManager.saveData("cmCode", accessToken.CMCode)
                            preferenceManager.saveData("lastName", accessToken.lastName)
                            preferenceManager.saveData("customerEmail", accessToken.email)
                            preferenceManager.saveData("customerMobile", accessToken.mobile)
                            preferenceManager.saveData("customerDob", accessToken.DOB)
                            TokenManager.setToken(result!!.data!!.accessToken)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        showDialog("Login Failed", "Failed to login user: ${result.message}")
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun showDialog(title: String, message: String) {
        val alertDialog = AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        }.create()
        alertDialog.show()
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

        val loginItem = LoginItem(
            channel = "1",
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
                val parentLayout: View = findViewById(android.R.id.content)
                val snackbar =
                    Snackbar.make(parentLayout, "Login Failed: ... ", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }

        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val parentLayout: View = findViewById(android.R.id.content)
                    val snackbar =
                        Snackbar.make(parentLayout, "Login Successful: ", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                } else {
                    val parentLayout: View = findViewById(android.R.id.content)
                    val snackbar =
                        Snackbar.make(parentLayout, "Login Failed: ", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val user = mAuth.currentUser
        if (user != null) {
            val parentLayout: View = findViewById(android.R.id.content)
            val snackbar =
                Snackbar.make(parentLayout, "Already signIn: ", Snackbar.LENGTH_SHORT)
            snackbar.show()
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

    private val REQUEST_LOCATION_PERMISSION_CODE = 1001
    private fun checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION_CODE
            )
        } else {
            // Permissions already granted
            fetchLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                fetchLocation()
            } else {
                // Permission denied
                // Handle the denial
            }
        }
    }

    private fun fetchLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted, handle it accordingly
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    // Do something with latitude and longitude
                    Log.i("LocationInfo", "Latitude: $latitude, Longitude: $longitude")
                } else {
                    Log.e("LocationInfo", "Last known location is null")

                    // Handle the case where the last known location is null,
                    // prompt the user to enable location services or provide a fallback mechanism.
                }
            }
            .addOnFailureListener { e ->
                Log.e("LocationInfo", "Error getting location: ${e.message}")
                // Handle failure
            }
    }

    fun decodeJWT(token: String): AccessToken {
        val key =
            Keys.hmacShaKeyFor("AshProgHelpSecretKeypokopkokpkokhnxgfjdfjhsdfhdfghghbdfghosdfgsfgsbgsdf".toByteArray()) // Replace "yourSecretKey" with your actual secret key
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        return AccessToken(
            jti = claims["jti"].toString(),
            personId = claims["PersonId"].toString(),
            customerId = claims["CustomerId"].toString(),
            email = claims["Email"].toString(),
            mobile = claims["Mobile"].toString(),
            firstName = claims["FirstName"].toString(),
            lastName = claims["LastName"].toString(),
            DOB = claims["DOB"].toString(),
            CMCode = claims["CMCode"].toString(),
            siq = claims["siq"].toString(),
            channel = claims["Channel"].toString(),
            exp = Date((claims["exp"] as Int).toLong() * 1000)
        )
    }


}