package com.bsel.remitngo.presentation.ui.support

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.FragmentSupportBinding
import com.google.android.material.snackbar.Snackbar
import java.net.URLEncoder

class SupportFragment : Fragment() {

    private lateinit var binding: FragmentSupportBinding
    private val phoneNumber = "tel:+441215154008"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_support, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSupportBinding.bind(view)

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
            val recipient = "support@remitngo.com"
            val subject = "Support request from user"
            val message = " "

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
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
            val phoneNumber = "+8801535111573"
            val message = " "

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(
                "http://api.whatsapp.com/send?phone=$phoneNumber&text=${
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
                "fb-messenger://user/"
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

    }

    private fun makePhoneCall() {
        val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber))
        startActivity(dialIntent)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.nav_main)
        }
    }
}
