package com.bsel.remitngo.bottom_sheet

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.StatusAdapter
import com.bsel.remitngo.databinding.UpdateQueryLayoutBinding
import com.bsel.remitngo.model.StatusItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpdateQueryBottomSheet : BottomSheetDialogFragment() {

    private lateinit var updateQueryBehavior: BottomSheetBehavior<*>

    private lateinit var binding: UpdateQueryLayoutBinding

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

    override fun onStart() {
        super.onStart()
        updateQueryBehavior.state = BottomSheetBehavior.STATE_EXPANDED
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
        dismiss()
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

}