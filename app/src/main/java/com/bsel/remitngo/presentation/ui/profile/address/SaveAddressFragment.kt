package com.bsel.remitngo.presentation.ui.profile.address

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.bottomSheet.AddressBottomSheet
import com.bsel.remitngo.bottomSheet.CityBottomSheet
import com.bsel.remitngo.bottomSheet.CountyBottomSheet
import com.bsel.remitngo.bottomSheet.UkDivisionBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.profile.city.CityData
import com.bsel.remitngo.data.model.profile.city.CityItem
import com.bsel.remitngo.data.model.profile.county.CountyData
import com.bsel.remitngo.data.model.profile.county.CountyItem
import com.bsel.remitngo.data.model.profile.postCode.PostCodeData
import com.bsel.remitngo.data.model.profile.uk_division.UkDivisionData
import com.bsel.remitngo.data.model.profile.uk_division.UkDivisionItem
import com.bsel.remitngo.data.model.profile.updateProfile.UpdateProfileItem
import com.bsel.remitngo.databinding.FragmentSaveAddressBinding
import com.bsel.remitngo.data.interfaceses.OnAddressItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import java.util.*
import javax.inject.Inject

class SaveAddressFragment : Fragment(), OnAddressItemSelectedListener {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var binding: FragmentSaveAddressBinding

    private lateinit var preferenceManager: PreferenceManager

    private val addressBottomSheet: AddressBottomSheet by lazy { AddressBottomSheet() }
    private val ukDivisionBottomSheet: UkDivisionBottomSheet by lazy { UkDivisionBottomSheet() }
    private val countyBottomSheet: CountyBottomSheet by lazy { CountyBottomSheet() }
    private val cityBottomSheet: CityBottomSheet by lazy { CityBottomSheet() }

    var ipAddress: String? = null
    private lateinit var deviceId: String
    private lateinit var personId: String

    private lateinit var postCode: String
    private lateinit var address: String

    private lateinit var ukDivisionId: String
    private lateinit var ukDivision: String

    private lateinit var countyId: String
    private lateinit var county: String

    private lateinit var cityId: String
    private lateinit var city: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_save_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSaveAddressBinding.bind(view)

        (requireActivity().application as Injector).createProfileSubComponent().inject(this)

        profileViewModel =
            ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]

        countryFocusListener()
        postCodeFocusListener()
        addressFocusListener()
        divisionFocusListener()
        countyFocusListener()
        cityFocusListener()

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        postCode = arguments?.getString("postCode").toString()
        binding.postCode.setText(postCode)

        address = arguments?.getString("address").toString()
        binding.address.setText(address)

        ukDivisionId = arguments?.getString("ukDivisionId").toString()
        countyId = arguments?.getString("countyId").toString()
        cityId = arguments?.getString("cityId").toString()

        binding.btnSearch.setOnClickListener { postCodeForm() }

        binding.division.setOnClickListener {
            ukDivisionBottomSheet.setSelectedUkDivision("4")
            ukDivisionBottomSheet.itemSelectedListener = this
            ukDivisionBottomSheet.show(childFragmentManager, ukDivisionBottomSheet.tag)
        }
        binding.county.setOnClickListener {
            if (::ukDivisionId.isInitialized) {
                countyBottomSheet.setSelectedCounty(ukDivisionId)
                countyBottomSheet.itemSelectedListener = this
                countyBottomSheet.show(childFragmentManager, countyBottomSheet.tag)
            }
        }
        binding.city.setOnClickListener {
            if (::countyId.isInitialized) {
                cityBottomSheet.setSelectedCity(countyId)
                cityBottomSheet.itemSelectedListener = this
                cityBottomSheet.show(childFragmentManager, cityBottomSheet.tag)
            }
        }

        binding.btnSave.setOnClickListener { addressForm() }

        val ukDivisionItem = UkDivisionItem(
            deviceId = deviceId,
            dropdownId = 2,
            param1 = 4,
            param2 = 0
        )
        profileViewModel.ukDivision(ukDivisionItem)
        observeUkDivisionResult()

        if (::ukDivisionId.isInitialized) {
            val countyItem = CountyItem(
                deviceId = deviceId,
                dropdownId = 3,
                param1 = ukDivisionId!!.toInt(),
                param2 = 0
            )
            profileViewModel.county(countyItem)
            observeCountyResult()
        }

        if (::countyId.isInitialized) {
            val cityItem = CityItem(
                deviceId = deviceId,
                dropdownId = 4,
                param1 = countyId!!.toInt(),
                param2 = 0
            )
            profileViewModel.city(cityItem)
            observeCityResult()
        }

        observeUpdateProfileResult()

    }

    private fun observeUkDivisionResult() {
        profileViewModel.ukDivisionResult.observe(this) { result ->
            if (result!!.data != null) {
                for (ukDivisionData in result.data!!) {
                    if (::ukDivisionId.isInitialized && ukDivisionId == ukDivisionData!!.id.toString()) {
                        ukDivision = ukDivisionData!!.name.toString()
                        binding.division.setText(ukDivision)
                    }
                }
            }
        }
    }

    private fun observeCountyResult() {
        profileViewModel.countyResult.observe(this) { result ->
            if (result!!.data != null) {
                for (countyData in result.data!!) {
                    if (::countyId.isInitialized && countyId == countyData!!.id.toString()) {
                        county = countyData!!.name.toString()
                        binding.county.setText(county)
                    }
                }
            }
        }
    }

    private fun observeCityResult() {
        profileViewModel.cityResult.observe(this) { result ->
            if (result!!.data != null) {
                for (cityData in result.data!!) {
                    if (::cityId.isInitialized && cityId == cityData!!.id.toString()) {
                        city = cityData!!.name.toString()
                        binding.city.setText(city)
                    }
                }
            }
        }
    }

    private fun observeUpdateProfileResult() {
        profileViewModel.updateProfileResult.observe(this) { result ->
            if (result != null) {
                findNavController().navigate(
                    R.id.action_nav_save_address_to_nav_my_profile
                )
            }
        }
    }

    private fun addressForm() {
        binding.countryContainer.helperText = validCountry()
        binding.postCodeContainer.helperText = validPostCode()
        binding.addressContainer.helperText = validAddress()
        binding.divisionContainer.helperText = validDivision()
        binding.countyContainer.helperText = validCounty()
        binding.cityContainer.helperText = validCity()

        val validCountry = binding.countryContainer.helperText == null
        val validPostCode = binding.postCodeContainer.helperText == null
        val validAddress = binding.addressContainer.helperText == null
        val validDivision = binding.divisionContainer.helperText == null
        val validCounty = binding.countyContainer.helperText == null
        val validCity = binding.cityContainer.helperText == null

        if (validCountry && validPostCode && validAddress && validDivision && validCounty && validCity) {
            submitAddressForm()
        }

    }

    private fun submitAddressForm() {
        val postCode = binding.postCode.text.toString()
        val address = binding.address.text.toString()
        val division = binding.division.text.toString()
        val county = binding.county.text.toString()
        val city = binding.city.text.toString()

        val updateProfileItem = UpdateProfileItem(
            deviceId = deviceId,
            personId = personId.toInt(),
            updateType = 2,
            firstname = "",
            lastname = "",
            mobile = "",
            email = "",
            dob = "1999-03-02",
            gender = 0,
            nationality = 0,
            occupationTypeId = 0,
            occupationCode = 0,
            postcode = postCode,
            divisionId = ukDivisionId.toInt(),
            districtId = countyId.toInt(),
            thanaId = cityId.toInt(),
            buildingno = "",
            housename = "",
            address = address,
            annualNetIncomeId = 0,
            sourceOfIncomeId = 0,
            sourceOfFundId = 0,
            userIPAddress = ipAddress
        )
        profileViewModel.updateProfile(updateProfileItem)
    }

    private fun postCodeForm() {
        val postcode = binding.postCode.text.toString()
        binding.postCodeContainer.helperText = validPostCode()
        val validPostCode = binding.postCodeContainer.helperText == null
        if (validPostCode)
            submitPostCode(postcode)
    }

    private fun submitPostCode(postcode: String) {
        addressBottomSheet.setSelectedPostCode(postcode)
        addressBottomSheet.itemSelectedListener = this
        addressBottomSheet.show(childFragmentManager, addressBottomSheet.tag)
    }

    //Form validation
    private fun countryFocusListener() {
        binding.country.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.countryContainer.helperText = validCountry()
            }
        }
    }

    private fun validCountry(): String? {
        val country = binding.country.text.toString()
        if (country.isEmpty()) {
            return "enter country"
        }
        return null
    }

    private fun postCodeFocusListener() {
        binding.postCode.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.postCodeContainer.helperText = validPostCode()
            }
        }
    }

    private fun validPostCode(): String? {
        val postCode = binding.postCode.text.toString()
        if (postCode.isEmpty()) {
            return "enter post code"
        }
        return null
    }

    private fun addressFocusListener() {
        binding.address.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.addressContainer.helperText = validAddress()
            }
        }
    }

    private fun validAddress(): String? {
        val address = binding.address.text.toString()
        if (address.isEmpty()) {
            return "enter address"
        }
        return null
    }

    private fun divisionFocusListener() {
        binding.division.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.divisionContainer.helperText = validDivision()
            }
        }
    }

    private fun validDivision(): String? {
        val division = binding.division.text.toString()
        if (division.isEmpty()) {
            return "enter division"
        }
        return null
    }

    private fun countyFocusListener() {
        binding.county.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.countyContainer.helperText = validCounty()
            }
        }
    }

    private fun validCounty(): String? {
        val county = binding.county.text.toString()
        if (county.isEmpty()) {
            return "enter county"
        }
        return null
    }

    private fun cityFocusListener() {
        binding.city.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.cityContainer.helperText = validCity()
            }
        }
    }

    private fun validCity(): String? {
        val city = binding.city.text.toString()
        if (city.isEmpty()) {
            return "enter city"
        }
        return null
    }

    override fun onAddressItemSelected(selectedItem: PostCodeData) {
        binding.postCode.setText(selectedItem.postcode)
        binding.address.setText(selectedItem.ukAddress)
    }

    override fun onUkDivisionItemSelected(selectedItem: UkDivisionData) {
        binding.division.setText(selectedItem.name)
        ukDivisionId = selectedItem.id.toString()
    }

    override fun onCountyItemSelected(selectedItem: CountyData) {
        binding.county.setText(selectedItem.name)
        countyId = selectedItem.id.toString()
    }

    override fun onCityItemSelected(selectedItem: CityData) {
        binding.city.setText(selectedItem.name)
        cityId = selectedItem.id.toString()
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