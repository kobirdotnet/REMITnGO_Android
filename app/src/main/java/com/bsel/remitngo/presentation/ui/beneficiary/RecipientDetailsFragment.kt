package com.bsel.remitngo.presentation.ui.beneficiary

import android.content.Context
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
import com.bsel.remitngo.adapter.ChooseOrderTypeAdapter
import com.bsel.remitngo.adapter.GenderAdapter
import com.bsel.remitngo.bottom_sheet.ReasonBottomSheet
import com.bsel.remitngo.bottom_sheet.RelationBottomSheet
import com.bsel.remitngo.data.model.beneficiary.AddBeneficiaryItem
import com.bsel.remitngo.databinding.FragmentRecipientDetailsBinding
import com.bsel.remitngo.interfaceses.OnRecipientItemSelectedListener
import com.bsel.remitngo.model.ChooseOrderType
import com.bsel.remitngo.model.GenderItem
import com.bsel.remitngo.model.ReasonItem
import com.bsel.remitngo.model.RelationItem
import com.bsel.remitngo.presentation.di.Injector
import javax.inject.Inject

class RecipientDetailsFragment : Fragment(), OnRecipientItemSelectedListener {

    @Inject
    lateinit var beneficiaryViewModelFactory: BeneficiaryViewModelFactory
    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    private lateinit var binding: FragmentRecipientDetailsBinding

    private val relationBottomSheet: RelationBottomSheet by lazy { RelationBottomSheet() }
    private val reasonBottomSheet: ReasonBottomSheet by lazy { ReasonBottomSheet() }

    private lateinit var orderType: String
    private lateinit var paymentType: String

    private lateinit var deviceId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipient_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecipientDetailsBinding.bind(view)

        (requireActivity().application as Injector).createBeneficiarySubComponent().inject(this)

        beneficiaryViewModel =
            ViewModelProvider(this, beneficiaryViewModelFactory)[BeneficiaryViewModel::class.java]

//        chooseOrderTypeFocusListener()
        recipientNameFocusListener()
        phoneNumberFocusListener()
        genderFocusListener()
        relationFocusListener()
        reasonFocusListener()
        addressFocusListener()
        countryFocusListener()

        orderType = arguments?.getString("orderType").toString()
        paymentType = arguments?.getString("paymentType").toString()

        if (orderType=="1"){
            binding.recipientBankStatus.visibility = View.GONE
            binding.recipientWalletStatus.visibility = View.VISIBLE
        }else{
            binding.recipientBankStatus.visibility = View.VISIBLE
            binding.recipientWalletStatus.visibility = View.GONE
        }

        val chooseOrderTypes = arrayOf(
            ChooseOrderType(R.drawable.bank, "Bank Account"),
            ChooseOrderType(R.drawable.bkash, "bKash")
        )
        val adapter =
            ChooseOrderTypeAdapter(requireContext(), R.layout.choose_order_type, chooseOrderTypes)
        binding.chooseOrderType.setAdapter(adapter)
        binding.chooseOrderType.setOnItemClickListener { _, _, position, _ ->
            val type = adapter.getItem(position)
            orderType = type?.orderTypeName.toString()

            if (orderType == "Bank Account") {
                binding.recipientBankStatus.visibility = View.VISIBLE
                binding.recipientWalletStatus.visibility = View.GONE
            } else if (orderType == "bKash") {
                binding.recipientBankStatus.visibility = View.GONE
                binding.recipientWalletStatus.visibility = View.VISIBLE
            }

        }

        binding.btnContinue.setOnClickListener {recipientForm()}

        binding.relation.setOnClickListener {
            relationBottomSheet.itemSelectedListener = this
            relationBottomSheet.show(childFragmentManager, relationBottomSheet.tag)
        }

        binding.reason.setOnClickListener {
            reasonBottomSheet.itemSelectedListener = this
            reasonBottomSheet.show(childFragmentManager, reasonBottomSheet.tag)
        }

        val gender = arrayOf(
            GenderItem("Male"),
            GenderItem("Female")
        )
        val genderAdapter =
            GenderAdapter(requireContext(), R.layout.gender_item, gender)
        binding.gender.setAdapter(genderAdapter)

        observeBeneficiaryResult()

        deviceId = getDeviceId(requireContext())

    }

    private fun observeBeneficiaryResult() {
        beneficiaryViewModel.addBeneficiaryResult.observe(this) { result ->
            if (result != null) {
                val bundle = Bundle().apply {
                    putString("orderType", orderType)
                    putString("paymentType", paymentType)
                }
                findNavController().navigate(
                    R.id.action_nav_recipient_details_to_nav_recipient_bank_details,
                    bundle
                )
                Log.i("info", "Add beneficiary successful: $result")
            } else {
                Log.i("info", "Add beneficiary failed")
            }
        }
    }

    fun getDeviceId(context: Context): String {
        val deviceId: String

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } else {
            @Suppress("DEPRECATION")
            deviceId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

        return deviceId
    }

    override fun onRelationItemSelected(selectedItem: RelationItem) {
        binding.relation.setText(selectedItem.relationName)
    }

    override fun onReasonItemSelected(selectedItem: ReasonItem) {
        binding.reason.setText(selectedItem.reasonName)
    }

    private fun recipientForm() {
//        binding.chooseOrderTypeContainer.helperText = validChooseOrderType()
        binding.recipientNameContainer.helperText = validRecipientName()
        binding.phoneNumberContainer.helperText = validPhoneNumber()
        binding.genderContainer.helperText = validGender()
        binding.relationContainer.helperText = validRelation()
        binding.reasonContainer.helperText = validReason()
        binding.addressContainer.helperText = validAddress()
        binding.countryContainer.helperText = validCountry()

//        val validChooseOrderType = binding.chooseOrderTypeContainer.helperText == null
        val validRecipientName = binding.recipientNameContainer.helperText == null
        val validPhoneNumber = binding.phoneNumberContainer.helperText == null
        val validGender = binding.genderContainer.helperText == null
        val validRelation = binding.relationContainer.helperText == null
        val validReason = binding.reasonContainer.helperText == null
        val validAddress = binding.addressContainer.helperText == null
        val validCountry = binding.countryContainer.helperText == null

        if (validRecipientName && validPhoneNumber && validGender && validRelation && validReason && validAddress && validCountry) {
            submitRecipientForm()
        }
    }

    private fun submitRecipientForm() {
        val chooseOrderType = binding.chooseOrderType.text.toString()
        val recipientName = binding.recipientName.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()
        val gender = binding.gender.text.toString()
        val relation = binding.relation.text.toString()
        val reason = binding.reason.text.toString()
        val address = binding.address.text.toString()
        val country = binding.country.text.toString()

        val addBeneficiaryItem = AddBeneficiaryItem(
            active = true,
            address = address,
            beneOccupation = 1,
            bic = "",
            countryID = 1,
            deviceId = deviceId,
            districtID = 1,
            divisionID = 1,
            emailId = "",
            firstname = recipientName,
            gender = 1,
            iban = "",
            identityType = 1,
            isOnlineCustomer = 1,
            lastname = "",
            middlename = "",
            mobile = phoneNumber,
            otherOccupation = "",
            personId = 1,
            relationType = 1,
            resonID = 1,
            thanaID = 1,
            userIPAddress = ""
        )

        // Call the login method in the ViewModel
        beneficiaryViewModel.addBeneficiary(addBeneficiaryItem)

    }

    //Form validation
//    private fun chooseOrderTypeFocusListener() {
//        binding.chooseOrderType.setOnFocusChangeListener { _, focused ->
//            if (!focused) {
//                binding.chooseOrderTypeContainer.helperText = validChooseOrderType()
//            }
//        }
//    }

//    private fun validChooseOrderType(): String? {
//        val chooseOrderType = binding.chooseOrderType.text.toString()
//        if (chooseOrderType.isEmpty()) {
//            return "select order type"
//        }
//        return null
//    }

    private fun recipientNameFocusListener() {
        binding.recipientName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.recipientNameContainer.helperText = validRecipientName()
            }
        }
    }

    private fun validRecipientName(): String? {
        val recipientName = binding.recipientName.text.toString()
        if (recipientName.isEmpty()) {
            return "enter recipient name"
        }
        return null
    }

    private fun phoneNumberFocusListener() {
        binding.phoneNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.phoneNumberContainer.helperText = validPhoneNumber()
            }
        }
    }

    private fun validPhoneNumber(): String? {
        val phoneNumber = binding.phoneNumber.text.toString()
        if (phoneNumber.isEmpty()) {
            return "enter phone number"
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

    private fun relationFocusListener() {
        binding.relation.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.relationContainer.helperText = validRelation()
            }
        }
    }

    private fun validRelation(): String? {
        val relation = binding.relation.text.toString()
        if (relation.isEmpty()) {
            return "select relation"
        }
        return null
    }

    private fun reasonFocusListener() {
        binding.reason.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.reasonContainer.helperText = validReason()
            }
        }
    }

    private fun validReason(): String? {
        val relation = binding.reason.text.toString()
        if (relation.isEmpty()) {
            return "select reason"
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
        val relation = binding.address.text.toString()
        if (relation.isEmpty()) {
            return "enter address"
        }
        return null
    }

    private fun countryFocusListener() {
        binding.country.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.countryContainer.helperText = validCountry()
            }
        }
    }

    private fun validCountry(): String? {
        val relation = binding.country.text.toString()
        if (relation.isEmpty()) {
            return "enter country"
        }
        return null
    }

}