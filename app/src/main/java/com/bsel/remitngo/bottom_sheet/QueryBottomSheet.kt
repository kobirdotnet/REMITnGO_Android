package com.bsel.remitngo.bottom_sheet

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
import com.bsel.remitngo.adapter.QueryAdapter
import com.bsel.remitngo.adapter.QueryTypeAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.query.QueryItem
import com.bsel.remitngo.data.model.query.QueryTable
import com.bsel.remitngo.data.model.query.add_query.AddQueryItem
import com.bsel.remitngo.data.model.query.query_type.QueryTypeData
import com.bsel.remitngo.databinding.AddQueryLayoutBinding
import com.bsel.remitngo.interfaceses.OnQueryTypeItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.query.QueryViewModel
import com.bsel.remitngo.presentation.ui.query.QueryViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class QueryBottomSheet : BottomSheetDialogFragment(), OnQueryTypeItemSelectedListener {
    @Inject
    lateinit var queryViewModelFactory: QueryViewModelFactory
    private lateinit var queryViewModel: QueryViewModel

    private lateinit var addQueryBehavior: BottomSheetBehavior<*>

    private lateinit var binding: AddQueryLayoutBinding

    private val queryTypeBottomSheet: QueryTypeBottomSheet by lazy { QueryTypeBottomSheet() }

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

    private lateinit var queryTypeId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.add_query_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        addQueryBehavior = BottomSheetBehavior.from(view.parent as View)
        addQueryBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        addQueryBehavior.addBottomSheetCallback(object :
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

        queryTypeFocusListener()
        statusFocusListener()
        messageFocusListener()
        transactionNoFocusListener()

        binding.queryType.setOnClickListener {
            queryTypeBottomSheet.itemSelectedListener = this
            queryTypeBottomSheet.show(childFragmentManager, queryTypeBottomSheet.tag)
        }

        binding.btnSave.setOnClickListener { queryFrom() }

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun queryFrom() {
        binding.queryTypeContainer.helperText = validQuery()
        binding.statusContainer.helperText = validStatus()
        binding.messageContainer.helperText = validMessage()
        binding.transactionNoContainer.helperText = validTransactionNo()

        val validQuery = binding.queryTypeContainer.helperText == null
        val validStatus = binding.statusContainer.helperText == null
        val validMessage = binding.messageContainer.helperText == null
        val validTransactionNo = binding.transactionNoContainer.helperText == null

        if (validQuery && validStatus && validMessage && validTransactionNo) {
            submitQueryFrom()
        }
    }

    private fun submitQueryFrom() {
        val queryType = binding.queryType.text.toString()
        val status = binding.status.text.toString()
        val message = binding.message.text.toString()
        val transactionNo = binding.transactionNo.text.toString()

        val addQueryItem = AddQueryItem(
            checkTranNo = true,
            complainId = 0,
            complainMessage = message,
            complainStatus = true,
            deviceId = deviceId,
            querySender = personId.toInt(),
            queryType = queryTypeId.toInt(),
            transactionNo = transactionNo,
            userIPAddress = ""
        )
        queryViewModel.addQuery(addQueryItem)
        observeAddQueryResult()
    }

    private fun observeAddQueryResult() {
        queryViewModel.addQueryResult.observe(this) { result ->
            if (result!!.data != null) {
                Log.i("info", " queryType successful: $result")
                dismiss()
            } else {
                Log.i("info", " queryType failed")
            }
        }
    }

    //Form validation
    private fun queryTypeFocusListener() {
        binding.queryType.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.queryTypeContainer.helperText = validQuery()
            }
        }
    }

    private fun validQuery(): String? {
        val queryType = binding.queryType.text.toString()
        if (queryType.isEmpty()) {
            return "select query type"
        }
        return null
    }

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
            return "enter status"
        }
        return null
    }

    private fun messageFocusListener() {
        binding.message.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.messageContainer.helperText = validMessage()
            }
        }
    }

    private fun validMessage(): String? {
        val message = binding.message.text.toString()
        if (message.isEmpty()) {
            return "enter message"
        }
        return null
    }

    private fun transactionNoFocusListener() {
        binding.transactionNo.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.transactionNoContainer.helperText = validTransactionNo()
            }
        }
    }

    private fun validTransactionNo(): String? {
        val transactionNo = binding.transactionNo.text.toString()
        if (transactionNo.isEmpty()) {
            return "enter transaction no"
        }
        return null
    }

    override fun onQueryTypeItemSelected(selectedItem: QueryTypeData) {
        binding.queryType.setText(selectedItem.name)
        queryTypeId = selectedItem.id.toString()
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
        addQueryBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}