package com.bsel.remitngo.bottom_sheet

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.SelectDocumentsLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectDocumentsBottomSheet : BottomSheetDialogFragment() {

    private lateinit var selectDocumentsNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: SelectDocumentsLayoutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.select_documents_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        selectDocumentsNameBehavior = BottomSheetBehavior.from(view.parent as View)
        selectDocumentsNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        selectDocumentsNameBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
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


        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    override fun onStart() {
        super.onStart()
        selectDocumentsNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}