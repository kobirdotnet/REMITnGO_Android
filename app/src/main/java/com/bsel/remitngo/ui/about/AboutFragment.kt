package com.bsel.remitngo.ui.about

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutBinding.bind(view)

        // Click listener for User Agreement
        binding.userAgreement.setOnClickListener {
            openUriInBrowser("https://bracsaajanexchange.com")
        }

        // Click listener for Privacy Policy
        binding.privacyPolicy.setOnClickListener {
            openUriInBrowser("https://bracsaajanexchange.com")
        }

        // Click listener for Third-Party Notices
        binding.thirdPartyNotices.setOnClickListener {
            openUriInBrowser("https://bracsaajanexchange.com")
        }

        // Click listener for Company Registration
        binding.companyRegistration.setOnClickListener {
            showAlertDialog(
                "Company Registration",
                "REMITnGO UK Limited, Registered in England and Wales, Company Number 10426868, Registered Office Epworth House, 25 City Road, Shoreditch, London, EC1Y 1AA."
            )
        }

        // Click listener for Regulatory Information
        binding.regulatoryInformation.setOnClickListener {
            openUriInBrowser("https://bracsaajanexchange.com")
        }

        // Click listener for Referral Program
        binding.referralProgram.setOnClickListener {
            openUriInBrowser("https://bracsaajanexchange.com")
        }

        // Click listener for App Version
        binding.appVersion.setOnClickListener {
            showAlertDialog("App Version", "1.1.0")
        }
    }

    override fun onResume() {
        super.onResume()
        // Handle back button press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.nav_main)
        }
    }

    // Function to open a URI in the browser
    private fun openUriInBrowser(uri: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

    // Function to show an alert dialog with the given title and message
    private fun showAlertDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}
