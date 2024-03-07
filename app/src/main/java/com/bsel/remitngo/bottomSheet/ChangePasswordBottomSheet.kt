package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ChangePasswordLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChangePasswordBottomSheet : BottomSheetDialogFragment() {

    private lateinit var changePasswordBehavior: BottomSheetBehavior<*>

    private lateinit var binding: ChangePasswordLayoutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.change_password_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        changePasswordBehavior = BottomSheetBehavior.from(view.parent as View)
        changePasswordBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        changePasswordBehavior.addBottomSheetCallback(object :
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

        binding.currentPassword.setOnClickListener {

        }

        binding.newPassword.setOnClickListener {

        }

        binding.confirmNewPassword.setOnClickListener {

        }

        binding.btnChangePassword.setOnClickListener {

        }

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    override fun onStart() {
        super.onStart()
        changePasswordBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}