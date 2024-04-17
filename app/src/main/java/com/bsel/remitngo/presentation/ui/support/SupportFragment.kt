package com.bsel.remitngo.presentation.ui.support

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context.TELEPHONY_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.FragmentSupportBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.payment.PaymentViewModel
import com.bsel.remitngo.presentation.ui.payment.PaymentViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.net.URLEncoder
import javax.inject.Inject

class SupportFragment : Fragment() {
    @Inject
    lateinit var supportViewModelFactory: SupportViewModelFactory
    private lateinit var supportViewModel: SupportViewModel

    private lateinit var binding: FragmentSupportBinding

    private lateinit var phoneNumber: String
    private lateinit var email: String
    private lateinit var whatsAppUrl: String
    private lateinit var messengerUrl: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_support, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSupportBinding.bind(view)

        (requireActivity().application as Injector).createSupportSubComponent().inject(this)

        supportViewModel =
            ViewModelProvider(this, supportViewModelFactory)[SupportViewModel::class.java]

        supportViewModel.support("1")

        binding.callSupport.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.CALL_PHONE),
                        1
                    )
                } else {
                    makePhoneCall()
                }
            } else {
                makePhoneCall()
            }
        }

        binding.emailSupport.setOnClickListener {
            val subject = "Support request from user"
            val message = " "

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, message)

            if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(emailIntent)
            } else {
                Snackbar.make(
                    binding.root,
                    "No email client is installed on this device. Please install an email app to send a support request.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        binding.messageViaWhatsApp.setOnClickListener {
            val message = " "

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(
                "$whatsAppUrl$phoneNumber&text=${
                    URLEncoder.encode(
                        message,
                        "UTF-8"
                    )
                }"
            )
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Snackbar.make(
                    binding.root,
                    "WhatsApp is not installed on this device. Please install WhatsApp app to send a support message.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        binding.messageViaMessenger.setOnClickListener {
            val message = " "

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(
//                messengerUrl
                "https://m.me/example.user?ref=Hello%20from%20my%20app"
            )
            intent.putExtra(Intent.EXTRA_TEXT, message)

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Snackbar.make(
                    binding.root,
                    "Messenger is not installed on this device. Please install Messenger app to send a support message.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        observeSupportResult()
    }

    private fun observeSupportResult() {
        supportViewModel.supportResult.observe(this) { result ->
            if (result!!.data != null) {
                for (support in result.data!!) {
                    Log.i("info", "support: $support")
                    binding.phoneNumber.text = support!!.phoneNumber.toString()
                    phoneNumber = support!!.phoneNumber.toString()

                    binding.email.text = support!!.email.toString()
                    email = support!!.email.toString()

                    binding.whatsApp.text = support!!.whatsAppMsg.toString()
                    whatsAppUrl = support!!.whatsappUrl.toString()

                    binding.messenger.text = support!!.messangerMsg.toString()
                    messengerUrl = support!!.messagnerUrl.toString()
                }
            }
        }
    }

    private fun makePhoneCall() {
        val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
        startActivity(dialIntent)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.nav_main)
        }
    }
}
