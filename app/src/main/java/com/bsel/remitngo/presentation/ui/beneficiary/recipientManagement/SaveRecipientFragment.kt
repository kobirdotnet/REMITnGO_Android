package com.bsel.remitngo.presentation.ui.beneficiary.recipientManagement

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
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnBeneficiarySelectedListener
import com.bsel.remitngo.data.model.bank.bank_account.GetBankData
import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryData
import com.bsel.remitngo.data.model.beneficiary.save_beneficiary.BeneficiaryItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeData
import com.bsel.remitngo.data.model.reason.ReasonData
import com.bsel.remitngo.databinding.FragmentSaveRecipientBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModel
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModelFactory
import java.util.*
import javax.inject.Inject

class SaveRecipientFragment : Fragment(), OnBeneficiarySelectedListener {
    @Inject
    lateinit var beneficiaryViewModelFactory: BeneficiaryViewModelFactory
    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    private lateinit var binding: FragmentSaveRecipientBinding

    private lateinit var preferenceManager: PreferenceManager

//    private val relationBottomSheet: RelationBottomSheet by lazy { RelationBottomSheet() }
//    private val reasonBottomSheet: ReasonBottomSheet by lazy { ReasonBottomSheet() }

    private lateinit var personId: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var customerEmail: String
    private lateinit var customerMobile: String
    private lateinit var customerDateOfBirth: String

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private lateinit var paymentType: String
    private lateinit var orderType: String

    private lateinit var sendAmount: String
    private lateinit var receiveAmount: String

    private lateinit var exchangeRate: String
    private lateinit var commission: String

    private lateinit var bankId: String
    private lateinit var branchId: String
    private lateinit var bankName: String
    private lateinit var payingAgentId: String

    private lateinit var benId: String
    private lateinit var beneficiaryId: String
    private lateinit var beneficiaryName: String
    private lateinit var beneficiaryPhoneNumber: String

    private lateinit var reasonId: String
    private lateinit var reasonName: String

    private lateinit var sourceOfIncomeId: String
    private lateinit var sourceOfIncomeName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_save_recipient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSaveRecipientBinding.bind(view)

        (requireActivity().application as Injector).createBeneficiarySubComponent().inject(this)

        beneficiaryViewModel =
            ViewModelProvider(this, beneficiaryViewModelFactory)[BeneficiaryViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        firstName = preferenceManager.loadData("firstName").toString()
        lastName = preferenceManager.loadData("lastName").toString()
        customerEmail = preferenceManager.loadData("email").toString()
        customerMobile = preferenceManager.loadData("mobile").toString()
        customerDateOfBirth = preferenceManager.loadData("dob").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        paymentType = arguments?.getString("paymentType").toString()
        orderType = arguments?.getString("orderType").toString()

        sendAmount = arguments?.getString("sendAmount").toString()
        receiveAmount = arguments?.getString("receiveAmount").toString()

        exchangeRate = arguments?.getString("exchangeRate").toString()
        commission = arguments?.getString("commission").toString()

        bankId = arguments?.getString("bankId").toString()
        branchId = arguments?.getString("branchId").toString()
        bankName = arguments?.getString("bankName").toString()
        payingAgentId = arguments?.getString("payingAgentId").toString()

        benId = arguments?.getString("benId").toString()
        beneficiaryId = arguments?.getString("beneficiaryId").toString()
        beneficiaryName = arguments?.getString("beneficiaryName").toString()
        beneficiaryPhoneNumber = arguments?.getString("beneficiaryPhoneNumber").toString()

        reasonId = arguments?.getString("reasonId").toString()
        reasonName = arguments?.getString("reasonName").toString()

        sourceOfIncomeId = arguments?.getString("sourceOfIncomeId").toString()
        sourceOfIncomeName = arguments?.getString("sourceOfIncomeName").toString()

        if (beneficiaryName != "null") {
            binding.recipientName.setText(beneficiaryName)
        }
        if (beneficiaryPhoneNumber != "null") {
            binding.phoneNumber.setText(beneficiaryPhoneNumber)
        }

//        binding.relation.setOnClickListener {
//            relationBottomSheet.itemSelectedListener = this
//            relationBottomSheet.show(childFragmentManager, relationBottomSheet.tag)
//        }
//
//        binding.reason.setOnClickListener {
//            reasonBottomSheet.itemSelectedListener = this
//            reasonBottomSheet.show(childFragmentManager, reasonBottomSheet.tag)
//        }

        recipientNameFocusListener()
        phoneNumberFocusListener()

        if (orderType == "1") {
            binding.recipientBankStatus.visibility = View.GONE
            binding.recipientWalletStatus.visibility = View.VISIBLE
        } else {
            binding.recipientBankStatus.visibility = View.VISIBLE
            binding.recipientWalletStatus.visibility = View.GONE
        }

        binding.btnSave.setOnClickListener { recipientForm() }

        observeSaveBeneficiaryResult()
    }

    private fun observeSaveBeneficiaryResult() {
        beneficiaryViewModel.beneficiaryResult.observe(this) { result ->
            try {
                if (result?.data != null) {
                    val bundle = Bundle().apply {
                        putString("paymentType", paymentType)
                        putString("orderType", orderType)
                        putString("sendAmount", sendAmount)
                        putString("receiveAmount", receiveAmount)
                        putString("exchangeRate", exchangeRate)
                        putString("commission", commission)

                        putString("bankId", bankId)
                        putString("branchId", branchId)
                        putString("bankName", bankName)
                        putString("payingAgentId", payingAgentId)

                        putString("benId", benId)
                        putString("beneficiaryId", beneficiaryId)
                        putString("beneficiaryName", beneficiaryName)
                        putString("beneficiaryPhoneNumber", beneficiaryPhoneNumber)

                        putString("reasonId", reasonId)
                        putString("reasonName", reasonName)

                        putString("sourceOfIncomeId", sourceOfIncomeId)
                        putString("sourceOfIncomeName", sourceOfIncomeName)
                    }
                    findNavController().navigate(
                        R.id.action_nav_save_recipient_to_nav_choose_recipient,
                        bundle
                    )
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
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
        val recipientName = binding.recipientName.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()
        val gender = binding.gender.text.toString()
        val relation = binding.relation.text.toString()
        val reason = binding.reason.text.toString()
        val address = binding.address.text.toString()
        val country = binding.country.text.toString()
        val countryId = 1

        val beneficiaryItem = BeneficiaryItem(
            address="",
            beneficiaryId=0,
            beneficiaryName=recipientName,
            countryID=countryId,
            deviceId=deviceId,
            districtID=0,
            divisionID=0,
            firstname="",
            gender=0,
            lastname="",
            mobile=phoneNumber,
            operationType=0,
            personId=personId.toInt(),
            relationType=0,
            thanaID=0,
            userIPAddress=ipAddress
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

    private fun extractData(data: String): String? {
        val parts = data.split("*")
        return if (parts.size >= 2) parts[1] else null
    }

    override fun onChooseRecipientItemSelected(selectedItem: GetBeneficiaryData) {

    }

    override fun onBankAndWalletItemSelected(selectedItem: GetBankData) {


    }

    override fun onPurposeOfTransferItemSelected(selectedItem: ReasonData) {
        binding.reason.setText(selectedItem.name)
//        reasonId = selectedItem.id.toString()
    }

    override fun onSourceOfFundItemSelected(selectedItem: SourceOfIncomeData) {

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