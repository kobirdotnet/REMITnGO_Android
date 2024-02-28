package com.bsel.remitngo.presentation.ui.profile

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.beneficiary.get_beneficiary.GetBeneficiaryData
import com.bsel.remitngo.data.model.profile.ProfileItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeData
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.databinding.FragmentProfileBinding
import com.bsel.remitngo.presentation.di.Injector
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class ProfileFragment : Fragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var binding: FragmentProfileBinding

    private lateinit var preferenceManager: PreferenceManager

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

    private lateinit var address: String
    private lateinit var email: String
    private lateinit var mobile: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        (requireActivity().application as Injector).createProfileSubComponent().inject(this)

        profileViewModel =
            ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

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

        val profileItem = ProfileItem(
            deviceId = deviceId,
            personId = personId.toInt()
        )
        profileViewModel.profile(profileItem)
        observeProfileResult()

        binding.personalInformation.setOnClickListener {
            val bundle = Bundle().apply {
                putString("firstName", firstName)
                putString("lastName", lastName)

                putString("genderId", genderId)
                putString("dateOfBirth", binding.dob.text.toString())

                putString("occupationTypeId", occupationTypeId)
                putString("occupationId", occupationId)
                putString("sourceOfIncomeId", sourceOfIncomeId)

                putString("annualIncomeId", annualIncomeId)

                putString("nationalityId", nationalityId)
            }
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_personal_information,
                bundle
            )
        }

        binding.address.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_save_address
            )
        }

        binding.accountInformation.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_email
            )
        }

        binding.mobileNumber.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_mobile_number
            )
        }

        binding.changePassword.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_change_password
            )
        }

    }

    private fun observeOccupationTypeResult() {
        profileViewModel.occupationTypeResult.observe(this) { result ->
            if (result!!.data != null) {
                for (occupationTypeData in result.data!!) {
                    if (::occupationTypeId.isInitialized && occupationTypeId == occupationTypeData!!.id.toString()) {
                        occupationType = occupationTypeData!!.name.toString()
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
                        binding.occupation.text = "$occupation"
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
                        binding.annualIncome.text = "$annualIncome"
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
                        binding.nationality.text = "$nationality"
                    }
                }
            } else {
                Log.i("info", "nationality failed")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeProfileResult() {
        profileViewModel.profileResult.observe(this) { result ->
            if (result!!.data != null) {
                for (data in result.data!!) {
                    firstName = data!!.firstName.toString()
                    lastName = data!!.lastName.toString()
                    binding.name.text = "$firstName $lastName"

                    genderId = data!!.gender.toString()
                    if (genderId == "1") {
                        binding.gender.text = "Male"
                    } else if (genderId == "2") {
                        binding.gender.text = "Female"
                    }

                    dateOfBirth = data!!.dateOfBirth.toString()
                    val dateTime =
                        LocalDateTime.parse(dateOfBirth, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    val date = dateTime.toLocalDate()
                    val dob = date.format(DateTimeFormatter.ISO_DATE)
                    binding.dob.text = "$dob"

                    occupationTypeId = data!!.occupationTypeId.toString()

                    occupationId = data!!.occupationCode.toString()

                    sourceOfIncomeId = data!!.sourceOfIncomeId.toString()

                    annualIncomeId = data!!.annualNetIncomeId.toString()

                    nationalityId = data!!.nationality.toString()

                    address = data!!.address.toString()
                    binding.userAddress.text = "$address"

                    email = data!!.email.toString()
                    binding.emailAddress.text = "$email"

                    mobile = data!!.mobile.toString()
                    binding.phoneNumber.text = "$mobile"
                }
            } else {
                Log.i("info", "profile failed")
            }
        }
    }


    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.nav_main)
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