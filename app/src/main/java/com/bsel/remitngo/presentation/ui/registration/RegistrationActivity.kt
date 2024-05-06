package com.bsel.remitngo.presentation.ui.registration

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.MarketingBottomSheet
import com.bsel.remitngo.bottomSheet.RegistrationDialog
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnMarketingItemSelectedListener
import com.bsel.remitngo.data.interfaceses.OnRegistrationSelectedListener
import com.bsel.remitngo.data.model.marketing.MarketingValue
import com.bsel.remitngo.data.model.registration.RegistrationData
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.databinding.ActivityRegistrationBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.login.LoginActivity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RegistrationActivity : AppCompatActivity(), OnMarketingItemSelectedListener,OnRegistrationSelectedListener {
    @Inject
    lateinit var registrationViewModelFactory: RegistrationViewModelFactory
    private lateinit var registrationViewModel: RegistrationViewModel

    private lateinit var binding: ActivityRegistrationBinding

    private val marketingBottomSheet: MarketingBottomSheet by lazy { MarketingBottomSheet() }
    private val registrationDialog: RegistrationDialog by lazy { RegistrationDialog() }

    private lateinit var deviceId: String

    var termAndConditionChange = false

    private lateinit var preferenceManager: PreferenceManager

    var rdoEmail = false
    var rdoSMS = false
    var rdoPhone = false
    var rdoPost = false

    private var code: String? = null
    private var message: String? = null
    private var isLogin: Boolean? = false
    private var isMigrate: Boolean? = false
    private var personId: Int? = 0
    private var email: String? = null
    private var mobile: String? = null

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

        binding.dob.setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            val defaultYear = currentYear - 18

            val maxDate = Calendar.getInstance()
            maxDate.set(currentYear - 18, currentMonth, currentDay)

            val datePickerDialog = Dialog(this@RegistrationActivity)
            datePickerDialog.setContentView(R.layout.custom_date_picker_dialog)

            val dayPicker = datePickerDialog.findViewById<NumberPicker>(R.id.dayPicker)
            val monthPicker = datePickerDialog.findViewById<NumberPicker>(R.id.monthPicker)
            val yearPicker = datePickerDialog.findViewById<NumberPicker>(R.id.yearPicker)
            val okButton = datePickerDialog.findViewById<Button>(R.id.okButton)
            val cancelButton = datePickerDialog.findViewById<Button>(R.id.cancelButton)

            dayPicker.minValue = 1
            dayPicker.maxValue = 31
            dayPicker.value = currentDay

            monthPicker.minValue = 1
            monthPicker.maxValue = 12
            monthPicker.displayedValues = arrayOf(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
            )
            monthPicker.value = currentMonth + 1

            yearPicker.minValue = currentYear - 100
            yearPicker.maxValue = currentYear
            yearPicker.value = defaultYear

            // Set the maximum date
            val maxDateString =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(maxDate.time)
            val minDateString = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).format(Calendar.getInstance().time)

            val minTimestamp = SimpleDateFormat("yyyy-MM-dd").parse(minDateString)?.time ?: 0
            val maxTimestamp = SimpleDateFormat("yyyy-MM-dd").parse(maxDateString)?.time ?: 0

            dayPicker.maxValue = maxDate.getActualMaximum(Calendar.DAY_OF_MONTH)
            monthPicker.maxValue = maxDate.getActualMaximum(Calendar.MONTH) + 1
            yearPicker.maxValue = maxDate.get(Calendar.YEAR)

            dayPicker.setOnValueChangedListener { _, _, _ -> }
            monthPicker.setOnValueChangedListener { _, _, _ -> }
            yearPicker.setOnValueChangedListener { _, _, _ -> }

            okButton.setOnClickListener {
                val selectedDay = dayPicker.value
                val selectedMonth = monthPicker.value
                val selectedYear = yearPicker.value
                val formattedDate =
                    "%04d-%02d-%02d".format(selectedYear, selectedMonth, selectedDay)
                binding.dob.setText(formattedDate)
                datePickerDialog.dismiss()
            }

            cancelButton.setOnClickListener {
                datePickerDialog.dismiss()
            }

            datePickerDialog.show()
        }

        binding.termAndCondition.setOnCheckedChangeListener { buttonView, isChecked ->
            termAndConditionChange = isChecked
        }
        binding.termAndConditionTxt.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bracsaajanexchange.com"))
            startActivity(intent)
        }

        binding.marketingRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.marketing_check -> {
                    if (!marketingBottomSheet.isAdded) {
                        marketingBottomSheet.setSelectedMarketing(
                            rdoEmail,
                            rdoSMS,
                            rdoPhone,
                            rdoPost
                        )
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

        binding.password.setText("Normal@222")
        binding.confirmPassword.setText("Normal@222")

        observeRegistrationResult()

    }

    private fun observeRegistrationResult() {
        registrationViewModel.registrationResult.observe(this) { result ->
            try {
                if (result!!.data != null) {

                    var code = result.data!![0]!!.code
                    var message = result.data!![0]!!.message
                    var isLogin = result.data!![0]!!.isLogin
                    var isMigrate = result.data!![0]!!.isMigrate
                    var personId = result.data!![0]!!.personId
                    var email = result.data!![0]!!.email
                    var mobile = result.data!![0]!!.mobile

                    if (!registrationDialog.isAdded) {
                        registrationDialog.setSelectedMessage(
                            code,
                            email,
                            isLogin,
                            isMigrate,
                            message,
                            mobile,
                            personId
                        )
                        registrationDialog.itemSelectedListener = this@RegistrationActivity
                        registrationDialog.show(supportFragmentManager, registrationDialog.tag)
                        registrationDialog.isCancelable=false
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    override fun onRegistrationSelected(selectedItem: RegistrationData) {
        code = selectedItem.code
        email = selectedItem.email
        isLogin = selectedItem.isLogin
        isMigrate = selectedItem.isMigrate
        message = selectedItem.message
        mobile = selectedItem.mobile
        personId = selectedItem.personId

        if (code=="0000"){
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(intent)
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

        if (validFirstName && validLastName && validDob && validEmail && validPhone && validPassword && validConfirmPassword && termAndConditionChange) {
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
        val refCode = binding.referralCode.text.toString()

        val registrationItem = RegistrationItem(
            deviceId = deviceId,
            channel = "1",
            firstname = firstName,
            lastname = lastName,
            dob = dob,
            email = email,
            mobile = phoneNumber,
            password = password,
            refCode = refCode,
            isOnlineCustomer = 1,
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
        val password = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()
        if (confirmPassword.isEmpty()) {
            return "Enter confirm password"
        }
        if (!password.equals(confirmPassword)) {
            return "Password not matched."
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