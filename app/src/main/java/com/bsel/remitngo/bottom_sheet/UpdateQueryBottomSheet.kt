package com.bsel.remitngo.bottom_sheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.StatusAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.query.add_message.AddMessageItem
import com.bsel.remitngo.databinding.UpdateQueryLayoutBinding
import com.bsel.remitngo.model.StatusItem
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.query.QueryViewModel
import com.bsel.remitngo.presentation.ui.query.QueryViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class UpdateQueryBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var queryViewModelFactory: QueryViewModelFactory
    private lateinit var queryViewModel: QueryViewModel

    private lateinit var updateQueryBehavior: BottomSheetBehavior<*>

    private lateinit var binding: UpdateQueryLayoutBinding

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

    private var complainId: String? = null
    private var queryTypeId: String? = null
    private var complainStatus: String? = null
    private var transactionNo: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.update_query_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        updateQueryBehavior = BottomSheetBehavior.from(view.parent as View)
        updateQueryBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        updateQueryBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createQuerySubComponent().inject(this)

        queryViewModel =
            ViewModelProvider(this, queryViewModelFactory)[QueryViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

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

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
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
            transactionNo = transactionNo,
            userIPAddress = ""
        )
        queryViewModel.addMessage(addMessageItem)
        observeAddMessageResult()
    }

    fun setSelectedData(
        complainID: String,
        queryTypeID: String,
        transactionCode: String
    ) {
        complainId = complainID
        queryTypeId = queryTypeID
        transactionNo = transactionCode
    }

    private fun observeAddMessageResult() {
        queryViewModel.addMessageResult.observe(this) { result ->
            if (result!!.data != null) {
                dismiss()
            } else {
                Log.i("info", " query message failed")
            }
        }
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

    override fun onStart() {
        super.onStart()
        updateQueryBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}