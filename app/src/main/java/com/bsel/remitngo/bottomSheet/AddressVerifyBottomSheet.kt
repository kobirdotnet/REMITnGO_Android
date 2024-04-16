package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.AddressAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.profile.postCode.PostCodeData
import com.bsel.remitngo.data.model.profile.postCode.PostCodeItem
import com.bsel.remitngo.databinding.AddressLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnAddressItemSelectedListener
import com.bsel.remitngo.data.model.profile.city.CityData
import com.bsel.remitngo.data.model.profile.city.CityItem
import com.bsel.remitngo.data.model.profile.county.CountyData
import com.bsel.remitngo.data.model.profile.county.CountyItem
import com.bsel.remitngo.data.model.profile.uk_division.UkDivisionData
import com.bsel.remitngo.data.model.profile.uk_division.UkDivisionItem
import com.bsel.remitngo.data.model.profile.updateProfile.UpdateProfileItem
import com.bsel.remitngo.databinding.AddressVarifyLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import javax.inject.Inject

class AddressVerifyBottomSheet : BottomSheetDialogFragment(), OnAddressItemSelectedListener {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var binding: AddressVarifyLayoutBinding

    private lateinit var addressNameBehavior: BottomSheetBehavior<*>

    private lateinit var preferenceManager: PreferenceManager

    private val addressBottomSheet: AddressBottomSheet by lazy { AddressBottomSheet() }
    private val ukDivisionBottomSheet: UkDivisionBottomSheet by lazy { UkDivisionBottomSheet() }
    private val countyBottomSheet: CountyBottomSheet by lazy { CountyBottomSheet() }
    private val cityBottomSheet: CityBottomSheet by lazy { CityBottomSheet() }

    var ipAddress: String? = null
    private lateinit var deviceId: String
    private lateinit var personId: String

    private lateinit var ukDivisionId: String
    private lateinit var ukDivision: String

    private lateinit var countyId: String
    private lateinit var county: String

    private lateinit var cityId: String
    private lateinit var city: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.address_varify_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        addressNameBehavior = BottomSheetBehavior.from(view.parent as View)
        addressNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        addressNameBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull view: View, i: Int) {
                when (i) {
                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                }
            }

            override fun onSlide(@NonNull view: View, v: Float) {}
        })

        (requireActivity().application as Injector).createProfileSubComponent().inject(this)

        profileViewModel =
            ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }

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
            try {
                val countyItem = CountyItem(
                    deviceId = deviceId,
                    dropdownId = 3,
                    param1 = ukDivisionId.toInt(),
                    param2 = 0
                )
                profileViewModel.county(countyItem)
                observeCountyResult()
            } catch (e: NumberFormatException) {
                e.localizedMessage
            }
        }

        if (::countyId.isInitialized) {
            try {
                val cityItem = CityItem(
                    deviceId = deviceId,
                    dropdownId = 4,
                    param1 = countyId.toInt(),
                    param2 = 0
                )
                profileViewModel.city(cityItem)
                observeCityResult()
            } catch (e: NumberFormatException) {
                e.localizedMessage
            }
        }

        observeUpdateProfileResult()

        return bottomSheet
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
                if (result.code=="000"){
                    dismiss()
                }
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

    override fun onStart() {
        super.onStart()
        addressNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}