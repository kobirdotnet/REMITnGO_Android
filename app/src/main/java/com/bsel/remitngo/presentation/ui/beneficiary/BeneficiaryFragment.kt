package com.bsel.remitngo.presentation.ui.beneficiary

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
import com.bsel.remitngo.bottom_sheet.ReasonBottomSheet
import com.bsel.remitngo.bottom_sheet.RelationBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.beneficiary.save_beneficiary.BeneficiaryItem
import com.bsel.remitngo.data.model.gender.GenderItem
import com.bsel.remitngo.data.model.reason.ReasonData
import com.bsel.remitngo.data.model.relation.RelationData
import com.bsel.remitngo.databinding.FragmentBeneficiaryBinding
import com.bsel.remitngo.interfaceses.OnBeneficiarySelectedListener
import com.bsel.remitngo.presentation.di.Injector
import java.util.*
import javax.inject.Inject

class BeneficiaryFragment : Fragment(), OnBeneficiarySelectedListener {

    @Inject
    lateinit var beneficiaryViewModelFactory: BeneficiaryViewModelFactory
    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    private lateinit var binding: FragmentBeneficiaryBinding

    private lateinit var preferenceManager: PreferenceManager

    private val relationBottomSheet: RelationBottomSheet by lazy { RelationBottomSheet() }
    private val reasonBottomSheet: ReasonBottomSheet by lazy { ReasonBottomSheet() }

    private lateinit var orderType: String
    private lateinit var paymentType: String

    private lateinit var send_amount: String
    private lateinit var receive_amount: String

    private lateinit var bankId: String
    private lateinit var bankName: String

    private lateinit var payingAgentId: String
    private lateinit var payingAgentName: String

    private lateinit var exchangeRate: String
    private lateinit var bankCommission: String
    private lateinit var cardCommission: String

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private lateinit var personId: String

    private lateinit var genderId: String
    private lateinit var gender: String
    private lateinit var relationId: String
    private lateinit var reasonId: String
    private lateinit var recipientName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beneficiary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBeneficiaryBinding.bind(view)

        (requireActivity().application as Injector).createBeneficiarySubComponent().inject(this)

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        beneficiaryViewModel =
            ViewModelProvider(this, beneficiaryViewModelFactory)[BeneficiaryViewModel::class.java]

        recipientNameFocusListener()
        phoneNumberFocusListener()
//        genderFocusListener()
//        relationFocusListener()
//        reasonFocusListener()
//        addressFocusListener()
//        countryFocusListener()

        orderType = arguments?.getString("orderType").toString()
        paymentType = arguments?.getString("paymentType").toString()

        send_amount = arguments?.getString("send_amount").toString()
        receive_amount = arguments?.getString("receive_amount").toString()

        bankId = arguments?.getString("bankId").toString()
        bankName = arguments?.getString("bankName").toString()

        payingAgentId = arguments?.getString("payingAgentId").toString()
        payingAgentName = arguments?.getString("payingAgentName").toString()

        exchangeRate = arguments?.getString("exchangeRate").toString()
        bankCommission = arguments?.getString("bankCommission").toString()
        cardCommission = arguments?.getString("cardCommission").toString()

        if (orderType == "1") {
            binding.recipientBankStatus.visibility = View.GONE
            binding.recipientWalletStatus.visibility = View.VISIBLE
        } else {
            binding.recipientBankStatus.visibility = View.VISIBLE
            binding.recipientWalletStatus.visibility = View.GONE
        }

        binding.btnContinue.setOnClickListener { recipientForm() }

        binding.relation.setOnClickListener {
            relationBottomSheet.itemSelectedListener = this
            relationBottomSheet.show(childFragmentManager, relationBottomSheet.tag)
        }

        binding.reason.setOnClickListener {
            reasonBottomSheet.itemSelectedListener = this
            reasonBottomSheet.show(childFragmentManager, reasonBottomSheet.tag)
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

        recipientName = binding.recipientName.text.toString()
        observeSaveBeneficiaryResult()
    }

    private fun observeSaveBeneficiaryResult() {
        beneficiaryViewModel.beneficiaryResult.observe(this) { result ->
            if (result?.data != null) {
                val cusBankInfoId = extractData(result.data)
                if (cusBankInfoId != null) {
                    val bundle = Bundle().apply {
                        putString("orderType", orderType)
                        putString("paymentType", paymentType)

                        putString("send_amount", send_amount)
                        putString("receive_amount", receive_amount)

                        putString("bankId", bankId)
                        putString("bankName", bankName)

                        putString("payingAgentId", payingAgentId)
                        putString("payingAgentName", payingAgentName)

                        putString("exchangeRate", exchangeRate.toString())
                        putString("bankCommission", bankCommission.toString())
                        putString("cardCommission", cardCommission.toString())

                        putString("cusBankInfoId", cusBankInfoId)
                        putString("recipientName", recipientName)
                        putString("recipientMobile", binding.phoneNumber.toString())
                        putString("recipientAddress", binding.address.toString())
                    }
                    findNavController().navigate(
                        R.id.action_nav_save_beneficiary_to_nav_save_bank,
                        bundle
                    )
                    Log.i("info", "save beneficiary successful: $result")
                } else {
                    Log.i("info", "failed to extract data")
                }
            } else {
                Log.i("info", "save beneficiary failed")
            }
        }
    }

    private fun extractData(data: String): String? {
        val parts = data.split("*")
        return if (parts.size >= 2) parts[1] else null
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

    override fun onRelationItemSelected(selectedItem: RelationData) {
        binding.relation.setText(selectedItem.name)
        relationId = selectedItem.id.toString()
    }

    override fun onReasonItemSelected(selectedItem: ReasonData) {
        binding.reason.setText(selectedItem.name)
        reasonId = selectedItem.id.toString()
    }

    private fun recipientForm() {
//        binding.chooseOrderTypeContainer.helperText = validChooseOrderType()
        binding.recipientNameContainer.helperText = validRecipientName()
        binding.phoneNumberContainer.helperText = validPhoneNumber()
//        binding.genderContainer.helperText = validGender()
//        binding.relationContainer.helperText = validRelation()
//        binding.reasonContainer.helperText = validReason()
//        binding.addressContainer.helperText = validAddress()
//        binding.countryContainer.helperText = validCountry()

//        val validChooseOrderType = binding.chooseOrderTypeContainer.helperText == null
        val validRecipientName = binding.recipientNameContainer.helperText == null
        val validPhoneNumber = binding.phoneNumberContainer.helperText == null
//        val validGender = binding.genderContainer.helperText == null
//        val validRelation = binding.relationContainer.helperText == null
//        val validReason = binding.reasonContainer.helperText == null
//        val validAddress = binding.addressContainer.helperText == null
//        val validCountry = binding.countryContainer.helperText == null

        if (validRecipientName && validPhoneNumber) {
            submitRecipientForm()
        }
    }

    private fun submitRecipientForm() {
        val chooseOrderType = binding.chooseOrderType.text.toString()
        recipientName = binding.recipientName.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()
        val gender = binding.gender.text.toString()
        val relation = binding.relation.text.toString()
        val reason = binding.reason.text.toString()
        val address = binding.address.text.toString()
        val country = binding.country.text.toString()
        val countryId = 1
        val isOnlineCustomer = 1

        val beneficiaryItem = BeneficiaryItem(
            deviceId = deviceId,
            personId = personId.toInt(),
            firstname = recipientName,
            middlename = "",
            lastname = "",
            gender = 0,
            mobile = phoneNumber,
            emailId = "",
            countryID = countryId,
            divisionID = 0,
            districtID = 0,
            thanaID = 0,
            address = "",
            active = true,
            isOnlineCustomer = isOnlineCustomer,
            userIPAddress = ipAddress.toString(),
            relationType = 0,
            resonID = 0,
            iban = "",
            bic = "",
            identityType = 0,
            beneOccupation = 0,
            otherOccupation = ""
        )
        beneficiaryViewModel.beneficiary(beneficiaryItem)

    }

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