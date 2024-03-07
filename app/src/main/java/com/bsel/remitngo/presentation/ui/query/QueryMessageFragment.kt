package com.bsel.remitngo.presentation.ui.query

import android.content.Context
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
import com.bsel.remitngo.adapter.StatusAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.query.add_message.AddMessageItem
import com.bsel.remitngo.databinding.FragmentQueryMessageBinding
import com.bsel.remitngo.data.model.query.add_message.StatusItem
import com.bsel.remitngo.presentation.di.Injector
import javax.inject.Inject

class QueryMessageFragment : Fragment() {
    @Inject
    lateinit var queryViewModelFactory: QueryViewModelFactory
    private lateinit var queryViewModel: QueryViewModel

    private lateinit var binding: FragmentQueryMessageBinding

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

    private var complainId: String? = null
    private var queryTypeId: String? = null
    private var transactionCode: String? = null

    private var complainStatus: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_query_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQueryMessageBinding.bind(view)

        (requireActivity().application as Injector).createQuerySubComponent().inject(this)

        queryViewModel =
            ViewModelProvider(this, queryViewModelFactory)[QueryViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

        complainId = arguments?.getString("complainId").toString()
        queryTypeId = arguments?.getString("queryTypeId").toString()
        transactionCode = arguments?.getString("transactionCode").toString()

        statusFocusListener()
        userMassageFocusListener()

        val status = arrayOf(
            StatusItem("Open"),
            StatusItem("Closed")
        )
        val statusAdapter =
            StatusAdapter(requireContext(), R.layout.gender_item, status)
        binding.status.setAdapter(statusAdapter)

        binding.btnSaveMessage.setOnClickListener { messageFrom() }

        observeAddMessageResult()

    }

    private fun observeAddMessageResult() {
        queryViewModel.addMessageResult.observe(this) { result ->
            if (result!!.data != null) {
                val bundle = Bundle().apply {
                    putString("complainId", complainId)
                }
                findNavController().navigate(
                    R.id.action_nav_query_message_to_nav_update_query,
                    bundle
                )
            }
        }
    }

    private fun messageFrom() {
        binding.statusContainer.helperText = validStatus()
        binding.userMessageContainer.helperText = validUserMessage()

        val validStatus = binding.statusContainer.helperText == null
        val validUserMessage = binding.userMessageContainer.helperText == null

        if (validStatus && validUserMessage) {
            submitMessageFrom()
        }
    }

    private fun submitMessageFrom() {
        val status = binding.status.text.toString()
        val userMessage = binding.userMessage.text.toString()

        if (status == "Open") {
            complainStatus = "true"
        } else if (status == "Closed") {
            complainStatus = "false"
        }

        val addMessageItem = AddMessageItem(
            checkTranNo = true,
            complainId = complainId!!.toInt(),
            complainMessage = userMessage,
            complainStatus = complainStatus.toBoolean(),
            deviceId = deviceId,
            querySender = personId.toInt(),
            queryType = queryTypeId!!.toInt(),
            transactionNo = transactionCode,
            userIPAddress = ""
        )
        queryViewModel.addMessage(addMessageItem)
    }

    //Form validation
    private fun statusFocusListener() {
        binding.status.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.statusContainer.helperText = validStatus()
            }
        }
    }

    private fun validStatus(): String? {
        val status = binding.status.text.toString()
        if (status.isEmpty()) {
            return "select status"
        }
        return null
    }

    private fun userMassageFocusListener() {
        binding.userMessage.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.userMessageContainer.helperText = validUserMessage()
            }
        }
    }

    private fun validUserMessage(): String? {
        val userMessage = binding.userMessage.text.toString()
        if (userMessage.isEmpty()) {
            return "select user message"
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

}