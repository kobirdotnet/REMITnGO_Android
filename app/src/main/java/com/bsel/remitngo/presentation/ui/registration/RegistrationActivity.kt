package com.bsel.remitngo.presentation.ui.registration

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.MarketingBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnMarketingItemSelectedListener
import com.bsel.remitngo.data.model.marketing.MarketingValue
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.databinding.ActivityRegistrationBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RegistrationActivity : AppCompatActivity(), OnMarketingItemSelectedListener {
    @Inject
    lateinit var registrationViewModelFactory: RegistrationViewModelFactory
    private lateinit var registrationViewModel: RegistrationViewModel

    private lateinit var binding: ActivityRegistrationBinding

    private val marketingBottomSheet: MarketingBottomSheet by lazy { MarketingBottomSheet() }

    private lateinit var deviceId: String

    var termAndConditionChange = false

    private lateinit var preferenceManager: PreferenceManager

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

        binding.firstName.setText("MOHAMMEDNURUL")
        binding.lastName.setText("ISLAM")
        binding.dob.setText("1980-02-01")
        binding.email.setText("kobir456@gmail.com")
        binding.phoneNumber.setText("07787155001")

        binding.password.setText("Normal@222")
        binding.confirmPassword.setText("Normal@222")

        observeRegistrationResult()

    }

    @SuppressLint("MissingInflatedId")
    private fun observeRegistrationResult() {
        registrationViewModel.registrationResult.observe(this) { result ->
            try {
                if (result == null) {
                    showDialog(
                        "Registration Failed",
                        "Unable to registration. Please try again later."
                    )
                } else {
                    if (result.message == "Successful") {
                        if (result!!.data != null) {
                            val dialogView = layoutInflater.inflate(
                                R.layout.registration_successful_layout,
                                null
                            )
                            val dialog = AlertDialog.Builder(this)
                                .setView(dialogView)
                                .create()

                            dialogView.findViewById<TextView>(R.id.titleTxt).text =
                                "Registration Successful."
                            dialogView.findViewById<TextView>(R.id.messageTxt).text =
                                "${result.data!![0]!!.message}"

                            dialogView.findViewById<ImageView>(R.id.imgClose).setOnClickListener {
                                dialog.dismiss()
                                val intent =
                                    Intent(this@RegistrationActivity, LoginActivity::class.java)
                                startActivity(intent)
                            }

                            dialogView.findViewById<Button>(R.id.btnLogin).setOnClickListener {
                                dialog.dismiss()
                                val intent =
                                    Intent(this@RegistrationActivity, LoginActivity::class.java)
                                startActivity(intent)
                            }

                            dialog.setCancelable(false)
                            dialog.show()
                        }
                    } else {
                        if (result!!.data != null) {
                            val dialogView = layoutInflater.inflate(
                                R.layout.registration_failed_layout,
                                null
                            )
                            val dialog = AlertDialog.Builder(this)
                                .setView(dialogView)
                                .create()

                            dialogView.findViewById<TextView>(R.id.titleTxt).text =
                                "Registration Failed."
                            dialogView.findViewById<TextView>(R.id.messageTxt).text =
                                "${result.data!![0]!!.message}"
                            dialogView.findViewById<ImageView>(R.id.imgClose).setOnClickListener {
                                dialog.dismiss()
                            }

                            var isMobileConfirmationNedded = result.data!![0]!!.isMobileConfirmationNedded
                            var mobile = result.data!![0]!!.mobile

                            var isLogin = result.data!![0]!!.isLogin
                            var isMigrate = result.data!![0]!!.isMigrate

                            if (isLogin == true && isMigrate == false) {
                                dialogView.findViewById<LinearLayout>(R.id.loginLayout).visibility =
                                    View.VISIBLE
                                dialogView.findViewById<LinearLayout>(R.id.migrationLayout).visibility =
                                    View.GONE

                                dialogView.findViewById<Button>(R.id.btnLogin).setOnClickListener {
                                    dialog.dismiss()
                                    val intent =
                                        Intent(this@RegistrationActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                }
                            } else if (isLogin == false && isMigrate == true) {
                                dialogView.findViewById<LinearLayout>(R.id.loginLayout).visibility =
                                    View.GONE
                                dialogView.findViewById<LinearLayout>(R.id.migrationLayout).visibility =
                                    View.VISIBLE

                                dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
                                    dialog.dismiss()
                                    verifyMobile(mobile,isMobileConfirmationNedded)
                                }
                                dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
                                    dialog.dismiss()
                                }
                            } else if (isLogin == false && isMigrate == false) {
                                dialogView.findViewById<LinearLayout>(R.id.loginLayout).visibility =
                                    View.GONE
                                dialogView.findViewById<LinearLayout>(R.id.migrationLayout).visibility =
                                    View.GONE
                            }

                            dialogView.findViewById<Button>(R.id.btnHelp).setOnClickListener {
                                dialog.dismiss()
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://bracsaajanexchange.com")
                                )
                                startActivity(intent)
                            }

                            dialog.setCancelable(false)
                            dialog.show()
                        }
                    }
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun verifyMobile(mobile: String?, isMobileConfirmationNedded: Boolean?) {
        val dialogView = layoutInflater.inflate(
            R.layout.profile_verify_layout,
            null
        )
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.titleTxt).text =
            "Profile Verification."
        dialogView.findViewById<TextView>(R.id.messageTxt).text =
            "To get access exiting profile you need to verify current mobile no. associated with profile."

        var phoneNumber = mobile!!.substring(0, 3)

        if (isMobileConfirmationNedded == true){

            dialogView.findViewById<TextView>(R.id.mobileTxt).visibility =View.GONE
            dialogView.findViewById<TextView>(R.id.mobileTxt).text = "Mobile: $mobile"

            dialogView.findViewById<LinearLayout>(R.id.mobileVerifyLayout).visibility = View.VISIBLE
            dialogView.findViewById<TextView>(R.id.mobileVerifyTxt).text = "Mobile: XXXXXXXX$phoneNumber"
            dialogView.findViewById<TextInputLayout>(R.id.mobileNumberContainer).visibility = View.VISIBLE
            dialogView.findViewById<TextInputEditText>(R.id.mobileNumber).setText("")

        }else if (isMobileConfirmationNedded==false){
            dialogView.findViewById<TextView>(R.id.mobileTxt).visibility =View.VISIBLE
            dialogView.findViewById<TextView>(R.id.mobileTxt).text = "Mobile: $mobile"

            dialogView.findViewById<LinearLayout>(R.id.mobileVerifyLayout).visibility = View.GONE
            dialogView.findViewById<TextView>(R.id.mobileVerifyTxt).text = "Mobile: XXXXXXXX$phoneNumber"
            dialogView.findViewById<TextInputLayout>(R.id.mobileNumberContainer).visibility = View.GONE
            dialogView.findViewById<TextInputEditText>(R.id.mobileNumber).setText("")
        }




        dialogView.findViewById<ImageView>(R.id.imgClose).setOnClickListener {
            dialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.btnSendOtp).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.btnHelp).setOnClickListener {
            dialog.dismiss()
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://bracsaajanexchange.com")
            )
            startActivity(intent)
        }

        dialog.setCancelable(false)
        dialog.show()
    }

    private fun showDialog(title: String, message: String) {
        val alertDialog = AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        }.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
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
        if (phone.length < 10) {
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