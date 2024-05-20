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
import java.io.IOException

class SelectFileBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnDocumentItemSelectedListener? = null

    private lateinit var selectFileNameBehavior: BottomSheetBehavior<*>
    private lateinit var binding: SelectFileLayoutBinding

    private val PICK_FILE_REQUEST_CODE = 123
    private var selectedFileUri: Uri? = null

    private val TAKE_PICTURE_REQUEST_CODE = 456
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
            override fun onStateChanged(@NonNull view: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                }
            }

            override fun onSlide(@NonNull view: View, slideOffset: Float) {}
        })

        binding.selectFile.setOnClickListener { pickFile() }
        binding.openCamera.setOnClickListener { captureFile() }

        return bottomSheet
    }

    private fun pickFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "*/*" }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_FILE_REQUEST_CODE -> {
                    selectedFileUri = data?.data
                    documentFile(selectedFileUri)
                }
                TAKE_PICTURE_REQUEST_CODE -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    selectedFileUri = imageBitmap?.let { convertBitmapToContentUri(it) }
                    documentFile(selectedFileUri)
                }
            }
        }
    }

    private fun convertBitmapToContentUri(bitmap: Bitmap): Uri? {
        return try {
            val imagesFolder = File(requireContext().cacheDir, "images").apply { mkdirs() }
            val imageFile = File(imagesFolder, "image.png")
            FileOutputStream(imageFile).use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }

            FileProvider.getUriForFile(
                requireContext(),
                "com.bsel.remitngo.fileprovider",
                imageFile
            )
        } catch (e: IOException) {
            Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show()
            null
        } catch (e: IllegalArgumentException) {
            Toast.makeText(requireContext(), "Invalid file URI", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun documentFile(selectedFileUri: Uri?) {
        selectedFileUri?.let {
            itemSelectedListener?.onDocumentFileItemSelected(it)
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        selectFileNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}