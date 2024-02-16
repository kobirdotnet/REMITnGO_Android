package com.bsel.remitngo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyProfileBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Create an instance of AccountViewModel using ViewModelProvider
        val myProfileViewModel = ViewModelProvider(this)[MyProfileViewModel::class.java]

        // Inflate the layout for this fragment using data binding
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Reference to the TextView in the layout
//        val textView: TextView = binding.textAccount

        // Observe changes in the text data and update the UI
//        accountViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.personalInformation.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_personal_information
            )
        }

        binding.address.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_search_address
            )
        }

        binding.accountInformation.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_email
            )
        }

        binding.changePassword.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_change_password
            )
        }

        binding.mobileNumber.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_my_profile_to_nav_mobile_number
            )
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        // Intercept the back button press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Navigate to home fragment using NavController
            findNavController().navigate(R.id.nav_main)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}