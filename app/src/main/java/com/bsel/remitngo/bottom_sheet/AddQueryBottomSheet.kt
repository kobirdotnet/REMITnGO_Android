package com.bsel.remitngo.bottom_sheet

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.AddQueryLayoutBinding
import com.bsel.remitngo.interfaceses.OnQueryTypeItemSelectedListener
import com.bsel.remitngo.model.QueryType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddQueryBottomSheet : BottomSheetDialogFragment(), OnQueryTypeItemSelectedListener {

    private lateinit var addQueryBehavior: BottomSheetBehavior<*>

    private lateinit var binding: AddQueryLayoutBinding

    private val queryTypeBottomSheet: QueryTypeBottomSheet by lazy { QueryTypeBottomSheet() }

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

        queryTypeFocusListener()
        statusFocusListener()
        messageFocusListener()
        transactionNoFocusListener()

        binding.queryType.setOnClickListener {
            queryTypeBottomSheet.itemSelectedListener = this
            queryTypeBottomSheet.show(childFragmentManager, queryTypeBottomSheet.tag)
        }

        binding.btnSave.setOnClickListener { queryFrom()}

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    override fun onStart() {
        super.onStart()
        addQueryBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onQueryTypeItemSelected(selectedItem: QueryType) {
        binding.queryType.setText(selectedItem.queryTypeName)
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

        dismiss()
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

}