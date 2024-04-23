package com.bsel.remitngo.presentation.ui.registration

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.ExistingCustomerBottomSheet
import com.bsel.remitngo.bottomSheet.MarketingBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.api.TokenManager
import com.bsel.remitngo.data.interfaceses.OnMarketingItemSelectedListener
import com.bsel.remitngo.data.model.marketing.MarketingResponseItem
import com.bsel.remitngo.data.model.marketing.MarketingValue
import com.bsel.remitngo.data.model.registration.RegistrationData
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.databinding.ActivityRegistrationBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import javax.inject.Inject

class RegistrationActivity : AppCompatActivity(), OnMarketingItemSelectedListener {
    @Inject
    lateinit var registrationViewModelFactory: RegistrationViewModelFactory
    private lateinit var registrationViewModel: RegistrationViewModel

    private lateinit var binding: ActivityRegistrationBinding

    private val existingCustomerBottomSheet: ExistingCustomerBottomSheet by lazy { ExistingCustomerBottomSheet() }

    private val marketingBottomSheet: MarketingBottomSheet by lazy { MarketingBottomSheet() }

    private lateinit var deviceId: String

    private lateinit var preferenceManager: PreferenceManager

    var checkedValue = false

    var rdoEmail = false
    var rdoSMS = false
    var rdoPhone = false
    var rdoPost = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        preferenceManager = PreferenceManager(this@RegistrationActivity)

        (application as Injector).createRegistrationSubComponent().inject(this)

        registrationViewModel =
            ViewModelProvider(this, registrationViewModelFactory)[RegistrationViewModel::class.java]

        deviceId = getDeviceId(applicationContext)

        firstNameFocusListener()
        lastNameFocusListener()
        dobFocusListener()
        emailFocusListener()
        phoneFocusListener()
        passwordFocusListener()
        confirmPasswordFocusListener()

        binding.dobContainer.setEndIconOnClickListener {
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            val defaultYear = currentYear - 25
            val maxDate = Calendar.getInstance()
            maxDate.set(defaultYear, currentMonth, currentDay)
            val datePickerDialog = DatePickerDialog(
                this@RegistrationActivity, { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    if (!selectedDate.after(Calendar.getInstance())) {
                        val formattedDate =
                            "%04d-%02d-%02d".format(selectedYear, selectedMonth + 1, selectedDay)
                        binding.dob.setText(formattedDate)
                    }
                }, defaultYear, currentMonth, currentDay
            )
            datePickerDialog.datePicker.maxDate = maxDate.timeInMillis
            datePickerDialog.show()
        }

        binding.btnExistingCustomer.setOnClickListener {
            existingCustomerBottomSheet.show(
                supportFragmentManager,
                existingCustomerBottomSheet.tag
            )
        }

        checkedValue = false
        binding.termAndCondition.setOnCheckedChangeListener { checkBox, isChecked ->
            checkedValue = isChecked
        }
        binding.termAndConditionTxt.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bracsaajanexchange.com"))
            startActivity(intent)
        }

        binding.marketingRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.marketing_check -> {
                    if (!marketingBottomSheet.isAdded) {
                        marketingBottomSheet.setSelectedMarketing(rdoEmail, rdoSMS, rdoPhone, rdoPost)
                        marketingBottomSheet.itemSelectedListener = this@RegistrationActivity
                        marketingBottomSheet.show(supportFragmentManager, marketingBottomSheet.tag)
                    }
                }
                R.id.marketing_uncheck -> {
                    // Handle if needed
                }
            }
        }


        binding.btnSignUp.setOnClickListener { signUpForm() }

        observeRegistrationResult()

    }

    private fun observeRegistrationResult() {
        registrationViewModel.registrationResult.observe(this) { result ->
            result?.let { registrationResponse ->
                if (registrationResponse.code == "000") {
                    val data = registrationResponse.data
                    if (data is List<*>) {
                        val gson = Gson()
                        val registrationDataList = gson.fromJson<List<RegistrationData>>(
                            gson.toJsonTree(data),
                            object : TypeToken<List<RegistrationData>>() {}.type
                        )
                        registrationDataList.forEach { registrationData ->
                            registrationData?.let {
                                preferenceManager.saveData(
                                    "personId",
                                    it.personId?.toString() ?: ""
                                )
                                preferenceManager.saveData("firstName", it.firstName ?: "")
                                preferenceManager.saveData("lastName", it.lastName ?: "")
                                preferenceManager.saveData("email", it.email ?: "")
                                preferenceManager.saveData("mobile", it.mobile ?: "")
                                preferenceManager.saveData("dob", it.dateOfBirth ?: "")
                            }
                        }
                    } else {
                        Log.e("error", "Invalid data type for 'Data'")
                    }
                    registrationResponse.token?.let { TokenManager.setToken(it) }
                    val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.i("info", "Registration not successful. Code: ${registrationResponse.data}")
                    val parentLayout: View = findViewById(android.R.id.content)
                    val snackbar =
                        Snackbar.make(
                            parentLayout,
                            registrationResponse.data.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                    snackbar.show()
                }
            } ?: run {
                Log.e("error", "Null response received")
            }
        }
    }

    private fun signUpForm() {
        binding.firstNameContainer.helperText = validFirstName()
        binding.lastNameContainer.helperText = validLastName()
        binding.dobContainer.helperText = validDob()
        binding.emailContainer.helperText = validEmail()
        binding.phoneNumberContainer.helperText = validPhone()
        binding.passwordContainer.helperText = validPassword()
        binding.confirmPasswordContainer.helperText = validConfirmPassword()

        val validFirstName = binding.firstNameContainer.helperText == null
        val validLastName = binding.lastNameContainer.helperText == null
        val validDob = binding.dobContainer.helperText == null
        val validEmail = binding.emailContainer.helperText == null
        val validPhone = binding.phoneNumberContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        val validConfirmPassword = binding.confirmPasswordContainer.helperText == null

        if (validFirstName && validLastName && validDob && validEmail && validPhone && validPassword && validConfirmPassword) {
            submitSignUpForm()
        }
    }

    private fun submitSignUpForm() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val dob = binding.dob.text.toString()
        val email = binding.email.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()
        val password = binding.password.text.toString()
        val isOnline = 1
        val refCode = binding.referralCode.text.toString()

        val registrationItem = RegistrationItem(
            deviceId = deviceId,
            channel = "Apps",
            firstname = firstName,
            middlename = "",
            lastname = lastName,
            dob = dob,
            email = email,
            mobile = phoneNumber,
            password = password,
            refCode = refCode,
            isOnlineCustomer = isOnline,
            rdoemail = rdoEmail,
            rdophone = rdoPhone,
            rdopost = rdoPost,
            rdosms = rdoSMS
        )
        registrationViewModel.registerUser(registrationItem)
    }

    //Form validation
    private fun firstNameFocusListener() {
        binding.firstName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.firstNameContainer.helperText = validFirstName()
            }
        }
    }

    private fun validFirstName(): String? {
        val firstName = binding.firstName.text.toString()
        if (firstName.isEmpty()) {
            return "Enter first name"
        }
        return null
    }

    private fun lastNameFocusListener() {
        binding.lastName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.lastNameContainer.helperText = validLastName()
            }
        }
    }

    private fun validLastName(): String? {
        val lastName = binding.lastName.text.toString()
        if (lastName.isEmpty()) {
            return "Enter last name"
        }
        return null
    }

    private fun dobFocusListener() {
        binding.dob.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.dobContainer.helperText = validDob()
            }
        }
    }

    private fun validDob(): String? {
        val dob = binding.dob.text.toString()
        if (dob.isEmpty()) {
            return "select date of birth"
        }
        return null
    }

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
            return "Must be 10 digits"
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

    override fun onMarketingItemSelected(selectedItem: MarketingValue) {
        rdoEmail = selectedItem.rdoEmail == true
        rdoSMS = selectedItem.rdoSMS == true
        rdoPhone = selectedItem.rdoPhone == true
        rdoPost = selectedItem.rdoPost == true

        if (rdoEmail && rdoSMS && rdoPhone && rdoPost) {
            binding.marketingCheck.isChecked = true
            binding.marketingUncheck.isChecked = false
        } else {
            binding.marketingCheck.isChecked = false
            binding.marketingUncheck.isChecked = true
        }

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