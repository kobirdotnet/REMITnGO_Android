package com.bsel.remitngo.bottomSheet

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.SelectFileLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnDocumentItemSelectedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.FileOutputStream

class SelectFileBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnDocumentItemSelectedListener? = null

    private lateinit var selectFileNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: SelectFileLayoutBinding

    private val PICK_FILE_REQUEST_CODE = 123
    private var selectedFileUri: Uri? = null

    private val TAKE_PICTURE_REQUEST_CODE = 789
    private val CAMERA_PERMISSION_REQUEST_CODE = 789

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.select_file_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        selectFileNameBehavior = BottomSheetBehavior.from(view.parent as View)
        selectFileNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        selectFileNameBehavior.addBottomSheetCallback(object :
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

        binding.selectFile.setOnClickListener {
            pickFile()
        }
        binding.openCamera.setOnClickListener {
            captureFile()
        }

        return bottomSheet
    }

    private fun pickFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    private fun captureFile() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivityForResult(cameraIntent, TAKE_PICTURE_REQUEST_CODE)
        } else {
            Toast.makeText(requireContext(), "No camera app available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedFileUri = data.data
                documentFile(selectedFileUri)
            }
        }

        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.extras != null) {
                val imageBitmap = data.extras!!.get("data") as Bitmap
                selectedFileUri = convertBitmapToContentUri(imageBitmap)
                documentFile(selectedFileUri)
            }
        }

    }

    private fun convertBitmapToContentUri(bitmap: Bitmap): Uri? {
        val imagesFolder = File(requireContext().cacheDir, "images")
        imagesFolder.mkdirs()

        val imageFile = File(imagesFolder, "image.png")
        val outputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()

        return try {
            FileProvider.getUriForFile(
                requireContext(),
                "com.bsel.remitngo.fileprovider",
                imageFile
            )
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    private fun documentFile(selectedFileUri: Uri?) {
        itemSelectedListener?.onDocumentFileItemSelected(selectedFileUri)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        selectFileNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}