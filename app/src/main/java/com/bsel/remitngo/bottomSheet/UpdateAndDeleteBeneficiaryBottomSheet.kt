package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnSaveBeneficiarySelectedListener
import com.bsel.remitngo.data.model.beneficiary.save_beneficiary.BeneficiaryItem
import com.bsel.remitngo.databinding.SaveRecipientLayoutBinding
import com.bsel.remitngo.databinding.UpdateDeleteBeneficiaryLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModel
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import javax.inject.Inject

class UpdateAndDeleteBeneficiaryBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var beneficiaryViewModelFactory: BeneficiaryViewModelFactory
    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    private lateinit var binding: UpdateDeleteBeneficiaryLayoutBinding

    var itemSelectedListener: OnSaveBeneficiarySelectedListener? = null

    private lateinit var updateDeleteBeneficiaryBehavior: BottomSheetBehavior<*>

    private lateinit var preferenceManager: PreferenceManager

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private var customerId: Int = 0
    private var personId: Int = 0
    private var customerEmail: String? = null
    private var customerMobile: String? = null

    private var beneName: String? = null
    private var benePersonId: Int = 0
    private var beneId: Int = 0
    private var countryId: Int = 0
    private var countryName: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var flagiconName: String? = null
    private var beneMobile: String? = null
    private var hasTransactions: Boolean? = false

    private var operationType: Int = 0


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.update_delete_beneficiary_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        updateDeleteBeneficiaryBehavior = BottomSheetBehavior.from(view.parent as View)
        updateDeleteBeneficiaryBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        updateDeleteBeneficiaryBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createBeneficiarySubComponent().inject(this)

        beneficiaryViewModel =
            ViewModelProvider(this, beneficiaryViewModelFactory)[BeneficiaryViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }

        preferenceManager = PreferenceManager(requireContext())
        try {
            personId = preferenceManager.loadData("personId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            customerId = preferenceManager.loadData("customerId").toString().toInt()
        } catch (e: NumberFormatException) {
            e.localizedMessage
        }
        try {
            customerEmail = preferenceManager.loadData("customerEmail").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }
        try {
            customerMobile = preferenceManager.loadData("customerMobile").toString()
        } catch (e: NullPointerException) {
            e.localizedMessage
        }

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        beneficiaryNameFocusListener()
        phoneNumberFocusListener()

        if (beneName != "null") {
            binding.beneficiaryName.setText(beneName)
        }
        if (beneMobile != "null") {
            binding.phoneNumber.setText(beneMobile)
        }
        if (hasTransactions == true) {
            binding.beneficiaryName.isFocusableInTouchMode = false
        } else if (hasTransactions == false) {
            binding.beneficiaryName.isFocusableInTouchMode = true
        }

        binding.btnUpdate.setOnClickListener {
            operationType = 1
            beneficiaryForm()
        }
        binding.btnDelete.setOnClickListener {
            operationType = 2
            beneficiaryForm()
         }

        observeUpdateAndDeleteBeneficiaryResult()

        return bottomSheet
    }

    fun setBeneficiaryData(
        beneName: String?,
        benePersonId: Int?,
        beneId: Int?,
        countryId: Int?,
        countryName: String?,
        firstName: String?,
        lastName: String?,
        flagiconName: String?,
        beneMobile: String?,
        hasTransactions: Boolean?
    ) {
        this.beneName = beneName
        this.benePersonId = benePersonId!!
        this.beneId = beneId!!
        this.countryId = countryId!!
        this.countryName = countryName
        this.firstName = firstName
        this.lastName = lastName
        this.flagiconName = flagiconName
        this.beneMobile = beneMobile
        this.hasTransactions = hasTransactions
    }

    private fun beneficiaryForm() {
        binding.beneficiaryNameContainer.helperText = validBeneficiaryName()
        binding.phoneNumberContainer.helperText = validPhoneNumber()

        val validBeneficiaryName = binding.beneficiaryNameContainer.helperText == null
        val validPhoneNumber = binding.phoneNumberContainer.helperText == null

        if (validBeneficiaryName && validPhoneNumber) {
            submitBeneficiaryForm()
        }
    }

    private fun submitBeneficiaryForm() {
        beneName = binding.beneficiaryName.text.toString()
        beneMobile = binding.phoneNumber.text.toString()

        val beneficiaryItem = BeneficiaryItem(
            address = "",
            beneficiaryId = beneId,
            beneficiaryName = beneName,
            countryID = countryId,
            deviceId = deviceId,
            districtID = 0,
            divisionID = 0,
            firstname = "",
            gender = 0,
            lastname = "",
            mobile = beneMobile,
            operationType = operationType,
            personId = personId,
            relationType = 0,
            thanaID = 0,
            userIPAddress = ipAddress
        )
        beneficiaryViewModel.beneficiary(beneficiaryItem)

    }

    private fun observeUpdateAndDeleteBeneficiaryResult() {
        beneficiaryViewModel.beneficiaryResult.observe(this) { result ->
            try {
                if (result?.data != null) {
                    updateAndDeleteBeneficiary(result?.data.toString())
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun updateAndDeleteBeneficiary(selectedItem: String) {
        itemSelectedListener?.onSaveBeneficiaryItemSelected(selectedItem)
        dismiss()
    }

    private fun beneficiaryNameFocusListener() {
        binding.beneficiaryName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.beneficiaryNameContainer.helperText = validBeneficiaryName()
            }
        }
    }

    private fun validBeneficiaryName(): String? {
        val beneficiaryName = binding.beneficiaryName.text.toString()
        if (beneficiaryName.isEmpty()) {
            return "enter beneficiary name"
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
        updateDeleteBeneficiaryBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }


}