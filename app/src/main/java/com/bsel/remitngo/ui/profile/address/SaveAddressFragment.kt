package com.bsel.remitngo.ui.profile.address

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.FragmentSaveAddressBinding

class SaveAddressFragment : Fragment() {

    private lateinit var binding: FragmentSaveAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_save_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSaveAddressBinding.bind(view)

        countryFocusListener()
        postCodeFocusListener()
        addressFocusListener()
        divisionFocusListener()
        countyFocusListener()
        cityFocusListener()

        binding.btnSave.setOnClickListener { addressForm() }

    }

    private fun addressForm() {
        binding.countryContainer.helperText = validCountry()
        binding.postCodeContainer.helperText = validPostCode()
        binding.addressContainer.helperText = validAddress()
        binding.divisionContainer.helperText = validDivision()
        binding.countyContainer.helperText = validCounty()
        binding.cityContainer.helperText = validCity()
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

}