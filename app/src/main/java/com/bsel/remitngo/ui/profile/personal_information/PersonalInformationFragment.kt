package com.bsel.remitngo.ui.profile.personal_information

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.GenderAdapter
import com.bsel.remitngo.bottom_sheet.*
import com.bsel.remitngo.data.model.gender.GenderItem
import com.bsel.remitngo.databinding.FragmentPersonalInformationBinding
import com.bsel.remitngo.interfaceses.OnPersonalInfoItemSelectedListener
import com.bsel.remitngo.model.*
import java.util.*

class PersonalInformationFragment : Fragment(), OnPersonalInfoItemSelectedListener {

    private lateinit var binding: FragmentPersonalInformationBinding

    private val occupationTypeBottomSheet: OccupationTypeBottomSheet by lazy { OccupationTypeBottomSheet() }
    private val occupationBottomSheet: OccupationBottomSheet by lazy { OccupationBottomSheet() }
    private val sourceOfIncomeBottomSheet: SourceOfIncomeBottomSheet by lazy { SourceOfIncomeBottomSheet() }
    private val annualIncomeBottomSheet: AnnualIncomeBottomSheet by lazy { AnnualIncomeBottomSheet() }
    private val nationalityBottomSheet: NationalityBottomSheet by lazy { NationalityBottomSheet() }

    private lateinit var genderId: String
    private lateinit var gender: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonalInformationBinding.bind(view)

        firstNameFocusListener()
        lastNameFocusListener()
        dobFocusListener()
        genderFocusListener()
        occupationTypeFocusListener()
        occupationFocusListener()
        sourceOfIncomeFocusListener()
        annualIncomeFocusListener()
        nationalityFocusListener()

        binding.dobContainer.setEndIconOnClickListener {
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            val defaultYear = currentYear - 25
            val maxDate = Calendar.getInstance()
            maxDate.set(defaultYear, currentMonth, currentDay)
            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    if (!selectedDate.after(Calendar.getInstance())) {
                        val formattedDate =
                            "%02d/%02d/%04d".format(selectedDay, selectedMonth + 1, selectedYear)
                        binding.dob.setText(formattedDate)
                    }
                }, defaultYear, currentMonth, currentDay
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
            gender = genderType?.gender.toString()
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

    }

    private fun personalInfoForm() {
        binding.firstNameContainer.helperText = validFirstName()
        binding.lastNameContainer.helperText = validLastName()
        binding.dobContainer.helperText = validDob()
        binding.genderContainer.helperText = validGender()
        binding.occupationTypeContainer.helperText = validOccupationType()
        binding.occupationContainer.helperText = validOccupation()
        binding.sourceOfIncomeContainer.helperText = validSourceOfIncome()
        binding.annualIncomeContainer.helperText = validAnnualIncome()
        binding.nationalityContainer.helperText = validNationality()

        val validFirstName = binding.firstNameContainer.helperText == null
        val validLastName = binding.lastNameContainer.helperText == null
        val validDob = binding.dobContainer.helperText == null
        val validGender = binding.genderContainer.helperText == null
        val validOccupationType = binding.occupationTypeContainer.helperText == null
        val validOccupation = binding.occupationContainer.helperText == null
        val validSourceOfIncome = binding.sourceOfIncomeContainer.helperText == null
        val validAnnualIncome = binding.annualIncomeContainer.helperText == null
        val validNationality = binding.nationalityContainer.helperText == null

        if (validFirstName && validLastName && validDob && validGender && validOccupationType && validOccupation && validSourceOfIncome && validAnnualIncome && validNationality) {
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

    private fun occupationFocusListener() {
        binding.occupation.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.occupationContainer.helperText = validOccupation()
            }
        }
    }

    private fun validOccupation(): String? {
        val occupation = binding.occupation.text.toString()
        if (occupation.isEmpty()) {
            return "Select occupation"
        }
        return null
    }

    private fun sourceOfIncomeFocusListener() {
        binding.sourceOfIncome.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.sourceOfIncomeContainer.helperText = validSourceOfIncome()
            }
        }
    }

    private fun validSourceOfIncome(): String? {
        val sourceOfIncome = binding.sourceOfIncome.text.toString()
        if (sourceOfIncome.isEmpty()) {
            return "Select source of income"
        }
        return null
    }

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

    override fun onOccupationTypeItemSelected(selectedItem: OccupationType) {
        binding.occupationType.setText(selectedItem.occupationTypeName)
    }

    override fun onOccupationItemSelected(selectedItem: Occupation) {
        binding.occupation.setText(selectedItem.occupationName)
    }

    override fun onSourceOfIncomeItemSelected(selectedItem: SourceOfIncome) {
        binding.sourceOfIncome.setText(selectedItem.sourceOfIncomeName)
    }

    override fun onAnnualIncomeItemSelected(selectedItem: AnnualIncome) {
        binding.annualIncome.setText(selectedItem.annualIncomeName)
    }

    override fun onNationalityItemSelected(selectedItem: Nationality) {
        binding.nationality.setText(selectedItem.nationalityName)
    }

}