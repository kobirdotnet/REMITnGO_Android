package com.bsel.remitngo.bottomSheet

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.R
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnDocumentItemSelectedListener
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryData
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeData
import com.bsel.remitngo.databinding.UploadRequireDocumentLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.document.DocumentViewModel
import com.bsel.remitngo.presentation.ui.document.DocumentViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UploadRequireDocumentBottomSheet : BottomSheetDialogFragment(), OnDocumentItemSelectedListener {
    @Inject
    lateinit var documentViewModelFactory: DocumentViewModelFactory
    private lateinit var documentViewModel: DocumentViewModel

    private lateinit var binding: UploadRequireDocumentLayoutBinding

    private lateinit var requireDocumentBehavior: BottomSheetBehavior<*>

    private lateinit var preferenceManager: PreferenceManager

    private val documentCategoryBottomSheet: DocumentCategoryBottomSheet by lazy { DocumentCategoryBottomSheet() }
    private val documentTypeBottomSheet: DocumentTypeBottomSheet by lazy { DocumentTypeBottomSheet() }
    private val selectFileBottomSheet: SelectFileBottomSheet by lazy { SelectFileBottomSheet() }

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private lateinit var personId: String

    private lateinit var documentCategoryId: String
    private lateinit var documentTypeId: String

    private var selectedFile: Uri? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.upload_require_document_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        requireDocumentBehavior = BottomSheetBehavior.from(view.parent as View)
        requireDocumentBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        requireDocumentBehavior.addBottomSheetCallback(object :
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

        binding.cancelButton.setOnClickListener { dismiss() }

        (requireActivity().application as Injector).createDocumentSubComponent().inject(this)

        documentViewModel =
            ViewModelProvider(this, documentViewModelFactory)[DocumentViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        categoryFocusListener()
        documentFocusListener()
        documentNoFocusListener()
        issueByFocusListener()
        issueDateFocusListener()
        expireDateFocusListener()

        binding.documentCategory.setOnClickListener {
            documentCategoryBottomSheet.itemSelectedListener = this
            documentCategoryBottomSheet.show(childFragmentManager, documentCategoryBottomSheet.tag)
        }

        binding.documentType.setOnClickListener {
            if (::documentCategoryId.isInitialized) {
                documentTypeBottomSheet.setSelectedDocumentType(documentCategoryId)
                documentTypeBottomSheet.itemSelectedListener = this
                documentTypeBottomSheet.show(childFragmentManager, documentTypeBottomSheet.tag)
            }
        }

        binding.issueDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    if (!selectedDate.after(Calendar.getInstance())) {
                        val formattedDate =
                            "%04d-%02d-%02d".format(selectedYear, selectedMonth + 1, selectedDay)
                        binding.issueDate.setText(formattedDate)
                    }
                },
                year,
                month,
                day
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()

        }

        binding.expireDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    if (!selectedDate.before(Calendar.getInstance())) {
                        val formattedDate =
                            "%04d-%02d-%02d".format(selectedYear, selectedMonth + 1, selectedDay)
                        binding.expireDate.setText(formattedDate)
                    }
                },
                year,
                month,
                day
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        binding.selectFile.setOnClickListener {
            selectFileBottomSheet.itemSelectedListener = this
            selectFileBottomSheet.show(childFragmentManager, selectFileBottomSheet.tag)
        }

        binding.btnUploadDocument.setOnClickListener { documentFrom() }

        observeUploadDocumentResult()

        return bottomSheet
    }


    private fun observeUploadDocumentResult() {
        documentViewModel.uploadDocumentResult.observe(this) { result ->
            if (result != null) {
                dismiss()
            }
        }
    }

    private fun documentFrom() {
        binding.documentCategoryContainer.helperText = validCategory()
        binding.documentTypeContainer.helperText = validDocument()
        binding.documentNoContainer.helperText = validDocumentNo()
        binding.issueByContainer.helperText = validIssueBy()
        binding.issueDateContainer.helperText = validIssueDate()
        binding.expireDateContainer.helperText = validExpireDate()

        val validCategory = binding.documentCategoryContainer.helperText == null
        val validDocument = binding.documentTypeContainer.helperText == null
        val validDocumentNo = binding.documentNoContainer.helperText == null
        val validIssueBy = binding.issueByContainer.helperText == null
        val validIssueDate = binding.issueDateContainer.helperText == null
        val validExpireDate = binding.expireDateContainer.helperText == null

        if (validCategory && validDocument && validDocumentNo && validIssueBy
            && validIssueDate && validExpireDate
        ) {
            submitDocumentFrom()
        }
    }

    private fun submitDocumentFrom() {
        val category = binding.documentCategory.text.toString()
        val document = binding.documentType.text.toString()
        val documentNo = binding.documentNo.text.toString()
        val issueBy = binding.issueBy.text.toString()
        val issueDate = binding.issueDate.text.toString()
        val expireDate = binding.expireDate.text.toString()

        val fileUri = selectedFile
        if (fileUri != null) {
            val file = getFileFromUri(requireContext(), fileUri)
            file?.let { file ->
                val fileRequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)
                documentViewModel.uploadDocument(
                    deviceId.toRequestBody(),
                    personId.toRequestBody(),
                    documentCategoryId.toRequestBody(),
                    "0".toRequestBody(),
                    documentTypeId.toRequestBody(),
                    documentNo.toRequestBody(),
                    issueBy.toRequestBody(),
                    issueDate.toRequestBody(),
                    expireDate.toRequestBody(),
                    issueDate.toRequestBody(),
                    filePart
                )
            }
        } else {
            Snackbar.make(view!!, "Select your file.", Snackbar.LENGTH_SHORT).show()
        }
    }

    //Form validation
    private fun categoryFocusListener() {
        binding.documentCategory.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.documentCategoryContainer.helperText = validCategory()
            }
        }
    }

    private fun validCategory(): String? {
        val category = binding.documentCategory.text.toString()
        if (category.isEmpty()) {
            return "select category"
        }
        return null
    }

    private fun documentFocusListener() {
        binding.documentType.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.documentTypeContainer.helperText = validDocument()
            }
        }
    }

    private fun validDocument(): String? {
        val document = binding.documentType.text.toString()
        if (document.isEmpty()) {
            return "select document"
        }
        return null
    }

    private fun documentNoFocusListener() {
        binding.documentNo.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.documentNoContainer.helperText = validDocumentNo()
            }
        }
    }

    private fun validDocumentNo(): String? {
        val documentNo = binding.documentNo.text.toString()
        if (documentNo.isEmpty()) {
            return "enter document no"
        }
        return null
    }

    private fun issueByFocusListener() {
        binding.issueBy.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.issueByContainer.helperText = validIssueBy()
            }
        }
    }

    private fun validIssueBy(): String? {
        val issueBy = binding.issueBy.text.toString()
        if (issueBy.isEmpty()) {
            return "enter issue by"
        }
        return null
    }

    private fun issueDateFocusListener() {
        binding.issueDate.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.issueDateContainer.helperText = validIssueDate()
            }
        }
    }

    private fun validIssueDate(): String? {
        val issueDate = binding.issueDate.text.toString()
        if (issueDate.isEmpty()) {
            return "enter issue date"
        }
        return null
    }

    private fun expireDateFocusListener() {
        binding.expireDate.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.expireDateContainer.helperText = validExpireDate()
            }
        }
    }

    private fun validExpireDate(): String? {
        val expireDate = binding.expireDate.text.toString()
        if (expireDate.isEmpty()) {
            return "enter expire date"
        }
        return null
    }

    override fun onDocumentCategoryItemSelected(selectedItem: DocumentCategoryData) {
        binding.documentCategory.setText(selectedItem.name)
        documentCategoryId = selectedItem.id.toString()
    }

    override fun onDocumentTypeItemSelected(selectedItem: DocumentTypeData) {
        binding.documentType.setText(selectedItem.name)
        documentTypeId = selectedItem.id.toString()
    }

    override fun onDocumentFileItemSelected(selectedItem: Uri?) {
        selectedFile = selectedItem
        selectedFile?.let {
            val data = generateFilename()
            binding.selectFile.text = data
        }
    }

    private fun generateFilename(): String {
        val timestamp = System.currentTimeMillis()
        return "$timestamp.jpg"
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.use { input ->
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "temp_$timeStamp.jpg"
            val tempFile = File(context.cacheDir, fileName)
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
            return tempFile
        } ?: run {
            Log.e("getFileFromUri", "Failed to open input stream for URI: $uri")
            return null
        }
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

    private fun getIPAddress(context: Context): String? {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        return String.format(
            Locale.getDefault(),
            "%d.%d.%d.%d",
            ipAddress and 0xff,
            ipAddress shr 8 and 0xff,
            ipAddress shr 16 and 0xff,
            ipAddress shr 24 and 0xff
        )
    }

    override fun onStart() {
        super.onStart()
        requireDocumentBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}