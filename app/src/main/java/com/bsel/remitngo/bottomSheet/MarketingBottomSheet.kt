package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.AddressAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.profile.postCode.PostCodeData
import com.bsel.remitngo.data.model.profile.postCode.PostCodeItem
import com.bsel.remitngo.databinding.AddressLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnAddressItemSelectedListener
import com.bsel.remitngo.data.interfaceses.OnMarketingItemSelectedListener
import com.bsel.remitngo.data.model.marketing.MarketingItem
import com.bsel.remitngo.data.model.marketing.MarketingResponseItem
import com.bsel.remitngo.data.model.marketing.MarketingValue
import com.bsel.remitngo.databinding.MarketingLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import com.bsel.remitngo.presentation.ui.registration.RegistrationViewModel
import com.bsel.remitngo.presentation.ui.registration.RegistrationViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class MarketingBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnMarketingItemSelectedListener? = null

    private lateinit var marketingNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: MarketingLayoutBinding

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private var personId: Int = 0

    var rdoEmail = false
    var rdoSMS = false
    var rdoPhone = false
    var rdoPost = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.marketing_layout, null)
        binding = MarketingLayoutBinding.bind(view)

        bottomSheet.setContentView(view)
        marketingNameBehavior = BottomSheetBehavior.from(view.parent as View)
        marketingNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        marketingNameBehavior.addBottomSheetCallback(object :
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

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId")!!.toInt()

        deviceId = getDeviceId(requireContext())

        if (rdoEmail) {
            binding.switchEmail.isChecked = true
        }
        binding.switchEmail.setOnCheckedChangeListener { _, isChecked ->
            rdoEmail = isChecked
        }

        if (rdoSMS) {
            binding.switchSMS.isChecked = true
        }
        binding.switchSMS.setOnCheckedChangeListener { _, isChecked ->
            rdoSMS = isChecked
        }

        if (rdoPhone) {
            binding.switchPhone.isChecked = true
        }
        binding.switchPhone.setOnCheckedChangeListener { _, isChecked ->
            rdoPhone = isChecked
        }

        if (rdoPost) {
            binding.switchPost.isChecked = true
        }
        binding.switchPost.setOnCheckedChangeListener { _, isChecked ->
            rdoPost = isChecked
        }

        binding.btnSavePreference.setOnClickListener {
            marketing(
                selectedItem = MarketingValue(
                    rdoEmail, rdoSMS, rdoPhone, rdoPost
                )
            )
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            marketing(
                selectedItem = MarketingValue(
                    rdoEmail, rdoSMS, rdoPhone, rdoPost
                )
            )
            dismiss()
        }

        return bottomSheet
    }

    fun setSelectedMarketing(
        rdoEmail: Boolean,
        rdoSMS: Boolean,
        rdoPhone: Boolean,
        rdoPost: Boolean
    ) {
        this.rdoEmail = rdoEmail
        this.rdoSMS = rdoSMS
        this.rdoPhone = rdoPhone
        this.rdoPost = rdoPost
    }

    private fun marketing(selectedItem: MarketingValue) {
        itemSelectedListener?.onMarketingItemSelected(selectedItem)
        dismiss()
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

    override fun onStart() {
        super.onStart()
        marketingNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}