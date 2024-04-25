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
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnBeneficiarySelectedListener
import com.bsel.remitngo.data.interfaceses.OnSaveBeneficiarySelectedListener
import com.bsel.remitngo.data.model.beneficiary.save_beneficiary.BeneficiaryItem
import com.bsel.remitngo.databinding.SaveRecipientLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModel
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import javax.inject.Inject

class SaveRecipientBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var beneficiaryViewModelFactory: BeneficiaryViewModelFactory
    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    private lateinit var binding: SaveRecipientLayoutBinding

    var itemSelectedListener: OnSaveBeneficiarySelectedListener? = null

    private lateinit var saveRecipientBehavior: BottomSheetBehavior<*>

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var personId: String

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private var beneAccountName: String? = null
    private var beneMobile: String? = null

    private var orderType: Int=0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.save_recipient_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        saveRecipientBehavior = BottomSheetBehavior.from(view.parent as View)
        saveRecipientBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        saveRecipientBehavior.addBottomSheetCallback(object :
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
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        recipientNameFocusListener()
        phoneNumberFocusListener()

        if (beneAccountName != "null") {
            binding.recipientName.setText(beneAccountName)
        }
        if (beneMobile != "null") {
            binding.phoneNumber.setText(beneMobile)
        }

        if (orderType == 1) {
            binding.recipientBankStatus.visibility = View.GONE
            binding.recipientWalletStatus.visibility = View.VISIBLE
        } else {
            binding.recipientBankStatus.visibility = View.VISIBLE
            binding.recipientWalletStatus.visibility = View.GONE
        }

        binding.btnSave.setOnClickListener { recipientForm() }

        observeSaveBeneficiaryResult()

        return bottomSheet
    }

    fun setOrderType(orderType: Int, beneAccountName: String?, beneMobile: String?) {
        this.orderType =orderType
        this.beneAccountName =beneAccountName
        this.beneMobile =beneMobile
    }

    private fun recipientForm() {
        binding.recipientNameContainer.helperText = validRecipientName()
        binding.phoneNumberContainer.helperText = validPhoneNumber()

        val validRecipientName = binding.recipientNameContainer.helperText == null
        val validPhoneNumber = binding.phoneNumberContainer.helperText == null

        if (validRecipientName && validPhoneNumber) {
            submitRecipientForm()
        }
    }

    private fun submitRecipientForm() {
        beneAccountName = binding.recipientName.text.toString()
        beneMobile = binding.phoneNumber.text.toString()
        val countryId = 1
        val isOnlineCustomer = 1

        val beneficiaryItem = BeneficiaryItem(
            deviceId = deviceId,
            personId = personId.toInt(),
            firstname = beneAccountName,
            middlename = "",
            lastname = "",
            gender = 0,
            mobile = beneMobile,
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

    private fun observeSaveBeneficiaryResult() {
        beneficiaryViewModel.beneficiaryResult.observe(this) { result ->
            if (result?.data != null) {
                saveRecipient(result?.data.toString())
            }
        }
    }

    private fun saveRecipient(selectedItem: String) {
        itemSelectedListener?.onSaveBeneficiaryItemSelected(selectedItem)
        dismiss()
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
        saveRecipientBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }


}