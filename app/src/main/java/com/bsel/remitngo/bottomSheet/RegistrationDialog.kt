package com.bsel.remitngo.bottomSheet

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.data.interfaceses.OnRegistrationSelectedListener
import com.bsel.remitngo.data.model.registration.RegistrationData
import com.bsel.remitngo.databinding.RegistrationLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.login.LoginActivity
import com.bsel.remitngo.presentation.ui.registration.RegistrationViewModel
import com.bsel.remitngo.presentation.ui.registration.RegistrationViewModelFactory
import java.util.*
import javax.inject.Inject

class RegistrationDialog : DialogFragment() {
    @Inject
    lateinit var registrationViewModelFactory: RegistrationViewModelFactory
    private lateinit var registrationViewModel: RegistrationViewModel

    var itemSelectedListener: OnRegistrationSelectedListener? = null

    private lateinit var binding: RegistrationLayoutBinding


    var ipAddress: String? = null
    private lateinit var deviceId: String

    private var code: String? = null
    private var message: String? = null
    private var isLogin: Boolean? = false
    private var isMigrate: Boolean? = false
    private var personId: Int? = 0
    private var email: String? = null
    private var mobile: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.registration_layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as Injector).createRegistrationSubComponent().inject(this)

        registrationViewModel =
            ViewModelProvider(this, registrationViewModelFactory)[RegistrationViewModel::class.java]

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        if (code == "0000") {
            binding.successLayout.visibility = View.VISIBLE
            binding.failedLayout.visibility = View.GONE

            binding.successMessage.text = message

            binding.btnLogin.setOnClickListener {
                registrationStatus(
                    selectedItem = RegistrationData(
                        code, email, isLogin, isMigrate, message, mobile, personId
                    )
                )
            }
        } else {
            binding.successLayout.visibility = View.GONE
            binding.failedLayout.visibility = View.VISIBLE

            binding.failMessage.text = message

            binding.btnClose.setOnClickListener {
                registrationStatus(
                    selectedItem = RegistrationData(
                        code, email, isLogin, isMigrate, message, mobile, personId
                    )
                )
            }
            binding.btnSupport.setOnClickListener {
                registrationStatus(
                    selectedItem = RegistrationData(
                        code, email, isLogin, isMigrate, message, mobile, personId
                    )
                )
            }
        }

    }

    private fun registrationStatus(selectedItem: RegistrationData) {
        itemSelectedListener?.onRegistrationSelected(selectedItem)
        dismiss()
    }

    fun setSelectedMessage(
        code: String?,
        email: String?,
        isLogin: Boolean?,
        isMigrate: Boolean?,
        message: String?,
        mobile: String?,
        personId: Int?
    ) {
        this.code = code
        this.email = email
        this.isLogin = isLogin
        this.isMigrate = isMigrate
        this.message = message
        this.mobile = mobile
        this.personId = personId
    }

    private fun getDeviceId(context: Context): String {
        val deviceId: String

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } else {
            @Suppress("DEPRECATION") deviceId = Settings.Secure.getString(
                context.contentResolver, Settings.Secure.ANDROID_ID
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

    companion object {
        fun newInstance(): RegistrationDialog {
            return RegistrationDialog()
        }
    }

}