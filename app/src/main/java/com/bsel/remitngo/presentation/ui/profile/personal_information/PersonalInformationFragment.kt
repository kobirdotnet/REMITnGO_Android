package com.bsel.remitngo.presentation.ui.profile.personal_information

import android.app.DatePickerDialog
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.*
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.data.model.profile.updateProfile.UpdateProfileItem
import com.bsel.remitngo.databinding.FragmentPersonalInformationBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import java.util.*
import javax.inject.Inject

class PersonalInformationFragment : Fragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var binding: FragmentPersonalInformationBinding

    private lateinit var preferenceManager: PreferenceManager

    var ipAddress: String? = null
    private lateinit var deviceId: String
    private lateinit var personId: String

    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var dateOfBirth: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonalInformationBinding.bind(view)

        (requireActivity().application as Injector).createProfileSubComponent().inject(this)

        profileViewModel =
            ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]

        firstNameFocusListener()
        lastNameFocusListener()
        dobFocusListener()

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        firstName = arguments?.getString("firstName").toString()
        binding.firstName.setText(firstName)

        lastName = arguments?.getString("lastName").toString()
        binding.lastName.setText(lastName)

        dateOfBirth = arguments?.getString("dateOfBirth").toString()
        binding.dob.setText(dateOfBirth)

        binding.dobContainer.setEndIconOnClickListener {
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            val defaultYear = currentYear - 25
            val maxDate = Calendar.getInstance()
            maxDate.set(defaultYear, currentMonth, currentDay)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    if (!selectedDate.after(Calendar.getInstance())) {
                        val formattedDate =
                            "%04d-%02d-%02d".format(selectedYear, selectedMonth + 1, selectedDay)
                        binding.dob.setText(formattedDate)
                    }
                },
                defaultYear,
                currentMonth,
                currentDay
            )
            datePickerDialog.datePicker.maxDate = maxDate.timeInMillis
            datePickerDialog.show()
        }

        binding.btnSave.setOnClickListener { personalInfoForm() }

        val occupationTypeItem = OccupationTypeItem(
            deviceId = deviceId,
            dropdownId = 106,
            param1 = 106,
            param2 = 0
        )
        profileViewModel.occupationType(occupationTypeItem)
        observeOccupationTypeResult()

        val occupationItem = OccupationItem(
            deviceId = deviceId,
            dropdownId = 112,
            param1 = 0,
            param2 = 0
        )
        profileViewModel.occupation(occupationItem)
        observeOccupationResult()

        val sourceOfIncomeItem = SourceOfIncomeItem(
            deviceId = deviceId,
            dropdownId = 307,
            param1 = 0,
            param2 = 0
        )
        profileViewModel.sourceOfIncome(sourceOfIncomeItem)
        observeSourceOfIncomeResult()

        val annualIncomeItem = AnnualIncomeItem(
            deviceId = deviceId,
            dropdownId = 309,
            param1 = 0,
            param2 = 0
        )
        profileViewModel.annualIncome(annualIncomeItem)
        observeAnnualIncomeResult()

        val nationalityItem = NationalityItem(
            deviceId = deviceId,
            dropdownId = 122,
            param1 = 122,
            param2 = 0
        )
        profileViewModel.nationality(nationalityItem)
        observeNationalityResult()

        observeUpdateProfileResult()
    }

    private fun observeUpdateProfileResult() {
        profileViewModel.updateProfileResult.observe(this) { result ->
            if (result != null) {
                findNavController().navigate(
                    R.id.action_nav_personal_information_to_nav_my_profile
                )
            }
        }
    }

    private fun observeOccupationTypeResult() {
        profileViewModel.occupationTypeResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info","occupationTypeResult: "+result!!.data)
            }
        }
    }

    private fun observeOccupationResult() {
        profileViewModel.occupationResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info","occupationResult: "+result!!.data)
            }
        }
    }

    private fun observeSourceOfIncomeResult() {
        profileViewModel.sourceOfIncomeResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info","sourceOfIncomeResult: "+result!!.data)
            }
        }
    }

    private fun observeAnnualIncomeResult() {
        profileViewModel.annualIncomeResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info","annualIncomeResult: "+result!!.data)
            }
        }
    }

    private fun observeNationalityResult() {
        profileViewModel.nationalityResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info","nationalityResult: "+result!!.data)
            }
        }
    }

    private fun personalInfoForm() {
        binding.firstNameContainer.helperText = validFirstName()
        binding.lastNameContainer.helperText = validLastName()
        binding.dobContainer.helperText = validDob()

        val validFirstName = binding.firstNameContainer.helperText == null
        val validLastName = binding.lastNameContainer.helperText == null
        val validDob = binding.dobContainer.helperText == null

        if (validFirstName && validLastName && validDob) {
            submitPersonalInfoForm()
        }
    }

    private fun submitPersonalInfoForm() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val dob = binding.dob.text.toString()

        val updateProfileItem = UpdateProfileItem(
            deviceId = deviceId,
            personId = personId.toInt(),
            updateType = 1,
            firstname = firstName,
            lastname = lastName,
            mobile = "",
            email = "",
            dob = dob,
            gender = 0,
            nationality = 0,
            occupationTypeId = 0,
            occupationCode = 0,
            postcode = "",
            divisionId = 0,
            districtId = 0,
            thanaId = 0,
            buildingno = "",
            housename = "",
            address = "",
            annualNetIncomeId = 0,
            sourceOfIncomeId = 0,
            sourceOfFundId = 0,
            userIPAddress = ipAddress
        )
        profileViewModel.updateProfile(updateProfileItem)
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

    private fun getIPAddress(context: Context): String? {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        return String.format(
            Locale.getDefault(),
            "%d.%d.%d.%d",
            ipAddress and 0xff,
            ipAddress shr 8 and 0xff,
            ipAddress shr 16 and 0xff,
            ipAddress shr 24 and 0xff
        )
    }

}