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

    private var sourceOfIncomeList = ArrayList<SourceOfIncomeData>()

    var ipAddress: String? = null
    private lateinit var deviceId: String
    private lateinit var personId: String

    private lateinit var firstName: String
    private lateinit var lastName: String

    private lateinit var gender: String
    private lateinit var dateOfBirth: String

    private lateinit var occupation: String
    private lateinit var annualIncome: String
    private lateinit var nationality: String

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

        binding.personalInformation.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_personal_information
            )
        }

        binding.address.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_search_address
            )
        }

        binding.accountInformation.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_email
            )
        }

        binding.changePassword.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_change_password
            )
        }

        binding.mobileNumber.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_mobile_number
            )
        }

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

    }

    private fun observeAnnualIncomeResult() {
        profileViewModel.annualIncomeResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info", "annualIncome successful: $result")
                for (annualIncomeData in result.data!!) {
                    Log.i("info", "annualIncomeData: $annualIncomeData")
                    if (annualIncome == annualIncomeData!!.id.toString()) {
                        binding.annualIncome.text = annualIncomeData!!.name.toString()
                    }
                }
            } else {
                Log.i("info", "annualIncome failed")
            }
        }
    }
    private fun observeSourceOfIncomeResult() {
        profileViewModel.sourceOfIncomeResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info", "sourceOfIncome successful: $result")
                for (sourceOfIncomeData in result.data!!) {
                    Log.i("info", "sourceOfIncomeData: $sourceOfIncomeData")
                }
            } else {
                Log.i("info", "sourceOfIncome failed")
            }
        }
    }

    private fun observeOccupationTypeResult() {
        profileViewModel.occupationTypeResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info", "occupationType successful: $result")
                for (occupationTypeData in result.data!!) {
                    Log.i("info", "occupationTypeData: $occupationTypeData")
                }
            } else {
                Log.i("info", "occupationType failed")
            }
        }
    }

    private fun observeOccupationResult() {
        profileViewModel.occupationResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info", "occupation successful: $result")
                for (occupationData in result.data!!) {
                    Log.i("info", "occupationData: $occupationData")

                    if (occupation == occupationData!!.id.toString()) {
                        binding.occupation.text = occupationData!!.name.toString()
                    }
                }
            } else {
                Log.i("info", "occupation failed")
            }
        }
    }
    private fun observeNationalityResult() {
        profileViewModel.nationalityResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info", "nationality successful: $result")
                for (nationalityData in result.data!!) {
                    Log.i("info", "nationalityData: $nationalityData")

                    if (nationality == nationalityData!!.id.toString()) {
                        binding.nationality.text = nationalityData!!.name.toString()
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
                Log.i("info", "profile successful: $result")
                for (data in result.data!!) {
                    firstName = data!!.firstName.toString()
                    lastName = data!!.lastName.toString()
                    binding.name.text = "$firstName $lastName"

                    gender = data!!.gender.toString()
                    if (gender == "1") {
                        binding.gender.text = "Male"
                    } else if (gender == "2") {
                        binding.gender.text = "Female"
                    }

                    dateOfBirth = data!!.dateOfBirth.toString()
                    val dateTime =
                        LocalDateTime.parse(dateOfBirth, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    val date = dateTime.toLocalDate()
                    val dob = date.format(DateTimeFormatter.ISO_DATE)
                    binding.dob.text = "$dob"

                    occupation = data!!.occupationCode.toString()

                    annualIncome = data!!.annualNetIncomeId.toString()

                    nationality = data!!.nationality.toString()
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