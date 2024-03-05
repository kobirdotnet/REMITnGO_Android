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
import com.bsel.remitngo.adapter.GenderAdapter
import com.bsel.remitngo.bottom_sheet.*
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.bank.save_bank_account.SaveBankItem
import com.bsel.remitngo.data.model.gender.GenderItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeData
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityData
import com.bsel.remitngo.data.model.profile.nationality.NationalityItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationData
import com.bsel.remitngo.data.model.profile.occupation.OccupationItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeData
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeData
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.data.model.profile.updateProfile.UpdateProfileItem
import com.bsel.remitngo.databinding.FragmentPersonalInformationBinding
import com.bsel.remitngo.interfaceses.OnPersonalInfoItemSelectedListener
import com.bsel.remitngo.model.*
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import java.util.*
import javax.inject.Inject

class PersonalInformationFragment : Fragment(), OnPersonalInfoItemSelectedListener {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var binding: FragmentPersonalInformationBinding

    private lateinit var preferenceManager: PreferenceManager

    private val occupationTypeBottomSheet: OccupationTypeBottomSheet by lazy { OccupationTypeBottomSheet() }
    private val occupationBottomSheet: OccupationBottomSheet by lazy { OccupationBottomSheet() }
    private val sourceOfIncomeBottomSheet: SourceOfIncomeBottomSheet by lazy { SourceOfIncomeBottomSheet() }
    private val annualIncomeBottomSheet: AnnualIncomeBottomSheet by lazy { AnnualIncomeBottomSheet() }
    private val nationalityBottomSheet: NationalityBottomSheet by lazy { NationalityBottomSheet() }

    var ipAddress: String? = null
    private lateinit var deviceId: String
    private lateinit var personId: String

    private lateinit var firstName: String
    private lateinit var lastName: String

    private lateinit var genderId: String
    private lateinit var dateOfBirth: String

    private lateinit var occupationTypeId: String
    private lateinit var occupationType: String

    private lateinit var occupationId: String
    private lateinit var occupation: String

    private lateinit var sourceOfIncomeId: String
    private lateinit var sourceOfIncome: String

    private lateinit var annualIncomeId: String
    private lateinit var annualIncome: String

    private lateinit var nationalityId: String
    private lateinit var nationality: String

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

        binding.occupationLayout.visibility = View.GONE
        binding.sourceOfIncomeLayout.visibility = View.GONE

        firstNameFocusListener()
        lastNameFocusListener()
        dobFocusListener()
//        genderFocusListener()
//        occupationTypeFocusListener()
//        occupationFocusListener()
//        sourceOfIncomeFocusListener()
//        annualIncomeFocusListener()
//        nationalityFocusListener()

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        firstName = arguments?.getString("firstName").toString()
        binding.firstName.setText(firstName)

        lastName = arguments?.getString("lastName").toString()
        binding.lastName.setText(lastName)

        genderId = arguments?.getString("genderId").toString()
        if (genderId == "1") {
            binding.gender.setText("Male")
        } else if (genderId == "2") {
            binding.gender.setText("Female")
        }

        dateOfBirth = arguments?.getString("dateOfBirth").toString()
        binding.dob.setText(dateOfBirth)

        occupationTypeId = arguments?.getString("occupationTypeId").toString()
        occupationId = arguments?.getString("occupationId").toString()
        sourceOfIncomeId = arguments?.getString("sourceOfIncomeId").toString()
        annualIncomeId = arguments?.getString("annualIncomeId").toString()
        nationalityId = arguments?.getString("nationalityId").toString()

        if (::occupationTypeId.isInitialized && occupationTypeId == "1") {
            binding.occupationLayout.visibility = View.GONE
            binding.sourceOfIncomeLayout.visibility = View.GONE
            sourceOfIncomeId = "0"
        } else {
            binding.occupationLayout.visibility = View.GONE
            occupationId = "0"
            binding.sourceOfIncomeLayout.visibility = View.GONE
        }

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

        val genderItem = arrayOf(
            GenderItem("Male"),
            GenderItem("Female")
        )
        val genderAdapter =
            GenderAdapter(requireContext(), R.layout.gender_item, genderItem)
        binding.gender.setAdapter(genderAdapter)
        binding.gender.setOnItemClickListener { _, _, position, _ ->
            val genderType = genderAdapter.getItem(position)
            var gender = genderType?.gender.toString()
            if (gender == "Male") {
                genderId = "1"
            } else if (gender == "Female") {
                genderId = "2"
            }
        }

        binding.occupationType.setOnClickListener {
            occupationTypeBottomSheet.itemSelectedListener = this
            occupationTypeBottomSheet.show(childFragmentManager, occupationTypeBottomSheet.tag)
        }
        binding.occupation.setOnClickListener {
            occupationBottomSheet.itemSelectedListener = this
            occupationBottomSheet.show(childFragmentManager, occupationBottomSheet.tag)
        }
        binding.sourceOfIncome.setOnClickListener {
            sourceOfIncomeBottomSheet.itemSelectedListener = this
            sourceOfIncomeBottomSheet.show(childFragmentManager, sourceOfIncomeBottomSheet.tag)
        }
        binding.annualIncome.setOnClickListener {
            annualIncomeBottomSheet.itemSelectedListener = this
            annualIncomeBottomSheet.show(childFragmentManager, annualIncomeBottomSheet.tag)
        }
        binding.nationality.setOnClickListener {
            nationalityBottomSheet.itemSelectedListener = this
            nationalityBottomSheet.show(childFragmentManager, nationalityBottomSheet.tag)
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
            deviceId = deviceId
        )
        profileViewModel.sourceOfIncome(sourceOfIncomeItem)
        observeSourceOfIncomeResult()

        val annualIncomeItem = AnnualIncomeItem(
            deviceId = deviceId
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
                Log.i("info", "update profile successful: $result")
            } else {
                Log.i("info", "update profile failed")
            }
        }
    }

    private fun observeOccupationTypeResult() {
        profileViewModel.occupationTypeResult.observe(this) { result ->
            if (result!!.data != null) {
                for (occupationTypeData in result.data!!) {
                    if (::occupationTypeId.isInitialized && occupationTypeId == occupationTypeData!!.id.toString()) {
                        occupationType = occupationTypeData!!.name.toString()
                        binding.occupationType.setText(occupationType)
                    }
                }
            } else {
                Log.i("info", "occupationType failed")
            }
        }
    }

    private fun observeOccupationResult() {
        profileViewModel.occupationResult.observe(this) { result ->
            if (result!!.data != null) {
                for (occupationData in result.data!!) {
                    if (::occupationId.isInitialized && occupationId == occupationData!!.id.toString()) {
                        occupation = occupationData!!.name.toString()
                        binding.occupation.setText(occupation)
                    }
                }
            } else {
                Log.i("info", "occupation failed")
            }
        }
    }

    private fun observeSourceOfIncomeResult() {
        profileViewModel.sourceOfIncomeResult.observe(this) { result ->
            if (result!!.data != null) {
                for (sourceOfIncomeData in result.data!!) {
                    if (::sourceOfIncomeId.isInitialized && sourceOfIncomeId == sourceOfIncomeData!!.id.toString()) {
                        sourceOfIncome = sourceOfIncomeData!!.name.toString()
                        binding.sourceOfIncome.setText(sourceOfIncome)
                    }
                }
            } else {
                Log.i("info", "sourceOfIncome failed")
            }
        }
    }

    private fun observeAnnualIncomeResult() {
        profileViewModel.annualIncomeResult.observe(this) { result ->
            if (result!!.data != null) {
                for (annualIncomeData in result.data!!) {
                    if (::annualIncomeId.isInitialized && annualIncomeId == annualIncomeData!!.id.toString()) {
                        annualIncome = annualIncomeData!!.name.toString()
                        binding.annualIncome.setText(annualIncome)
                    }
                }
            } else {
                Log.i("info", "annualIncome failed")
            }
        }
    }

    private fun observeNationalityResult() {
        profileViewModel.nationalityResult.observe(this) { result ->
            if (result!!.data != null) {
                for (nationalityData in result.data!!) {
                    if (::nationalityId.isInitialized && nationalityId == nationalityData!!.id.toString()) {
                        nationality = nationalityData!!.name.toString()
                        binding.nationality.setText(nationality)
                    }
                }
            } else {
                Log.i("info", "nationality failed")
            }
        }
    }

    private fun personalInfoForm() {
        binding.firstNameContainer.helperText = validFirstName()
        binding.lastNameContainer.helperText = validLastName()
        binding.dobContainer.helperText = validDob()
//        binding.genderContainer.helperText = validGender()
//        binding.occupationTypeContainer.helperText = validOccupationType()
//        binding.occupationContainer.helperText = validOccupation()
//        binding.sourceOfIncomeContainer.helperText = validSourceOfIncome()
//        binding.annualIncomeContainer.helperText = validAnnualIncome()
//        binding.nationalityContainer.helperText = validNationality()

        val validFirstName = binding.firstNameContainer.helperText == null
        val validLastName = binding.lastNameContainer.helperText == null
        val validDob = binding.dobContainer.helperText == null
//        val validGender = binding.genderContainer.helperText == null
//        val validOccupationType = binding.occupationTypeContainer.helperText == null
//        val validOccupation = binding.occupationContainer.helperText == null
//        val validSourceOfIncome = binding.sourceOfIncomeContainer.helperText == null
//        val validAnnualIncome = binding.annualIncomeContainer.helperText == null
//        val validNationality = binding.nationalityContainer.helperText == null

        if (validFirstName && validLastName && validDob) {
            submitPersonalInfoForm()
        }
    }

    private fun submitPersonalInfoForm() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val dob = binding.dob.text.toString()
        val gender = binding.gender.text.toString()
        val occupationType = binding.occupationType.text.toString()
        val occupation = binding.occupation.text.toString()
        val sourceOfIncome = binding.sourceOfIncome.text.toString()
        val annualIncome = binding.annualIncome.text.toString()
        val nationality = binding.nationality.text.toString()

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

    private fun genderFocusListener() {
        binding.gender.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.genderContainer.helperText = validGender()
            }
        }
    }

    private fun validGender(): String? {
        val gender = binding.gender.text.toString()
        if (gender.isEmpty()) {
            return "select gender"
        }
        return null
    }

    private fun occupationTypeFocusListener() {
        binding.occupationType.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.occupationTypeContainer.helperText = validOccupationType()
            }
        }
    }

    private fun validOccupationType(): String? {
        val occupationType = binding.occupationType.text.toString()
        if (occupationType.isEmpty()) {
            return "Select occupation type"
        }
        return null
    }

//    private fun occupationFocusListener() {
//        binding.occupation.setOnFocusChangeListener { _, focused ->
//            if (!focused) {
//                binding.occupationContainer.helperText = validOccupation()
//            }
//        }
//    }
//
//    private fun validOccupation(): String? {
//        val occupation = binding.occupation.text.toString()
//        if (occupation.isEmpty()) {
//            return "Select occupation"
//        }
//        return null
//    }

//    private fun sourceOfIncomeFocusListener() {
//        binding.sourceOfIncome.setOnFocusChangeListener { _, focused ->
//            if (!focused) {
//                binding.sourceOfIncomeContainer.helperText = validSourceOfIncome()
//            }
//        }
//    }
//
//    private fun validSourceOfIncome(): String? {
//        val sourceOfIncome = binding.sourceOfIncome.text.toString()
//        if (sourceOfIncome.isEmpty()) {
//            return "Select source of income"
//        }
//        return null
//    }

    private fun annualIncomeFocusListener() {
        binding.annualIncome.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.annualIncomeContainer.helperText = validAnnualIncome()
            }
        }
    }

    private fun validAnnualIncome(): String? {
        val annualIncome = binding.annualIncome.text.toString()
        if (annualIncome.isEmpty()) {
            return "Select annual income"
        }
        return null
    }

    private fun nationalityFocusListener() {
        binding.nationality.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.nationalityContainer.helperText = validNationality()
            }
        }
    }

    private fun validNationality(): String? {
        val homeCountry = binding.nationality.text.toString()
        if (homeCountry.isEmpty()) {
            return "Select nationality"
        }
        return null
    }

    override fun onOccupationTypeItemSelected(selectedItem: OccupationTypeData) {
        binding.occupationType.setText(selectedItem.name)
        occupationTypeId = selectedItem.id.toString()

        if (::occupationTypeId.isInitialized && occupationTypeId == "1") {
            binding.occupationLayout.visibility = View.VISIBLE
            binding.sourceOfIncomeLayout.visibility = View.GONE
            sourceOfIncomeId = "0"
        } else {
            binding.occupationLayout.visibility = View.GONE
            occupationId = "0"
            binding.sourceOfIncomeLayout.visibility = View.VISIBLE
        }

    }

    override fun onOccupationItemSelected(selectedItem: OccupationData) {
        binding.occupation.setText(selectedItem.name)
        occupationId = selectedItem.id.toString()
    }

    override fun onSourceOfIncomeItemSelected(selectedItem: SourceOfIncomeData) {
        binding.sourceOfIncome.setText(selectedItem.name)
        sourceOfIncomeId = selectedItem.id.toString()
    }

    override fun onAnnualIncomeItemSelected(selectedItem: AnnualIncomeData) {
        binding.annualIncome.setText(selectedItem.name)
        annualIncomeId = selectedItem.id.toString()
    }

    override fun onNationalityItemSelected(selectedItem: NationalityData) {
        binding.nationality.setText(selectedItem.name)
        nationalityId = selectedItem.id.toString()
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