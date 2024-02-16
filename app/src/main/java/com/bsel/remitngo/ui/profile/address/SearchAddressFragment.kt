package com.bsel.remitngo.ui.profile.address

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.bottom_sheet.AddressBottomSheet
import com.bsel.remitngo.databinding.FragmentSearchAddressBinding
import com.bsel.remitngo.interfaceses.OnAddressItemSelectedListener
import com.bsel.remitngo.model.AddressItem

class SearchAddressFragment : Fragment(), OnAddressItemSelectedListener {

    private lateinit var binding: FragmentSearchAddressBinding

    private val addressBottomSheet: AddressBottomSheet by lazy { AddressBottomSheet() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchAddressBinding.bind(view)

        postCodeFocusListener()

        binding.btnEnterAddressManually.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_search_address_to_nav_save_address
            )
        }

        binding.btnSearch.setOnClickListener {postCodeForm()}

    }

    private fun postCodeForm() {
        binding.postCodeContainer.helperText = validPostCode()
        val validPostCode = binding.postCodeContainer.helperText == null
        if (validPostCode)
            submitPostCode()
    }

    private fun submitPostCode() {
        val postcode = binding.postCode.text.toString()
        addressBottomSheet.itemSelectedListener = this
        addressBottomSheet.show(childFragmentManager, addressBottomSheet.tag)
    }

    //Form validation
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
            return "Enter post code"
        }
        return null
    }

    override fun onAddressItemSelected(selectedItem: AddressItem) {
        binding.postCode.setText(selectedItem.addressName)
        findNavController().navigate(
            R.id.action_nav_search_address_to_nav_save_address
        )
    }

}