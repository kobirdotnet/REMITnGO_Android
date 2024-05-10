package com.bsel.remitngo.bottomSheet

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
import com.bsel.remitngo.data.interfaceses.OnDocumentStatus
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentItem
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryData
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeData
import com.bsel.remitngo.databinding.UploadRequireDocumentLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.document.DocumentViewModel
import com.bsel.remitngo.presentation.ui.document.DocumentViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UploadRequireDocumentBottomSheet : BottomSheetDialogFragment(),
    OnDocumentItemSelectedListener {
    @Inject
    lateinit var documentViewModelFactory: DocumentViewModelFactory
    private lateinit var documentViewModel: DocumentViewModel

    var itemSelectedListener: OnDocumentStatus? = null

    private lateinit var binding: UploadRequireDocumentLayoutBinding

    private lateinit var requireDocumentBehavior: BottomSheetBehavior<*>

    private lateinit var preferenceManager: PreferenceManager

    private val requiredCategoryBottomSheet: RequiredCategoryBottomSheet by lazy { RequiredCategoryBottomSheet() }
    private val documentTypeBottomSheet: DocumentTypeBottomSheet by lazy { DocumentTypeBottomSheet() }
    private val selectFileBottomSheet: SelectFileBottomSheet by lazy { SelectFileBottomSheet() }

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private var personId: Int = 0

    private var documentCategoryId: Int = 0
    private var documentTypeId: Int = 0

    private var selectedFile: Uri? = null

    private var totalAmount: Double = 0.0
    private var benePersonId: Int = 0
    private var customerId: Int = 0
    private var currentDate: String? = null
    private var purposeOfTransferId: Int = 0

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
        try {
            personId = preferenceManager.loadData("personId").toString().toInt()
        }catch (e:NumberFormatException){
            e.localizedMessage
        }

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        categoryFocusListener()
        documentFocusListener()
        selectFileFocusListener()

        binding.documentCategory.setOnClickListener {
            requiredCategoryBottomSheet.itemSelectedListener = this
            requiredCategoryBottomSheet.requireDocument(
                totalAmount,
                benePersonId,
                customerId,
                currentDate!!,
                purposeOfTransferId
            )
            requiredCategoryBottomSheet.show(childFragmentManager, requiredCategoryBottomSheet.tag)
        }

        binding.documentType.setOnClickListener {
            documentTypeBottomSheet.setDocumentCategoryId(documentCategoryId)
            documentTypeBottomSheet.itemSelectedListener = this
            documentTypeBottomSheet.show(childFragmentManager, documentTypeBottomSheet.tag)
        }

        binding.selectFile.setOnClickListener {
            selectFileBottomSheet.itemSelectedListener = this
            selectFileBottomSheet.show(childFragmentManager, selectFileBottomSheet.tag)
        }

        binding.btnUploadDocument.setOnClickListener { documentFrom() }

        val requireDocumentItem = RequireDocumentItem(
            agentId = 8082,
            amount = totalAmount,
            beneficiaryId = benePersonId,
            customerId = customerId,
            entryDate = currentDate,
            purposeOfTransferId = purposeOfTransferId,
            transactionType = 1
        )
        documentViewModel.requireDocument(requireDocumentItem)

        observeRequireDocumentResult()
        observeUploadDocumentResult()

        return bottomSheet
    }

    fun requireDocument(
        totalAmount: Double,
        benePersonId: Int,
        customerId: Int,
        currentDate: String,
        purposeOfTransferId: Int
    ) {
        this.totalAmount = totalAmount
        this.benePersonId = benePersonId
        this.customerId = customerId
        this.currentDate = currentDate
        this.purposeOfTransferId = purposeOfTransferId
    }

    private fun observeRequireDocumentResult() {
        documentViewModel.requireDocumentResult.observe(this) { result ->
            try {
                if (result!!.code == "000") {
                    if (result!!.data != null) {
                        documentCategoryId= result!!.data!![0]!!.id!!
                        binding.documentCategory.setText(result!!.data!![0]!!.name)
                    }
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun observeUploadDocumentResult() {
        documentViewModel.uploadDocumentResult.observe(this) { result ->
            try {
                dismiss()
                itemSelectedListener?.onDocumentStatusSelected("dismiss")
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun documentFrom() {
        binding.documentCategoryContainer.helperText = validCategory()
        binding.documentTypeContainer.helperText = validDocument()
        binding.selectFileContainer.helperText = validSelectFile()

        val validCategory = binding.documentCategoryContainer.helperText == null
        val validDocument = binding.documentTypeContainer.helperText == null
        val validSelectFile = binding.selectFileContainer.helperText == null

        if (validCategory && validDocument && validSelectFile) {
            submitDocumentFrom()
        }
    }

    private fun submitDocumentFrom() {
        val category = binding.documentCategory.text.toString()
        val document = binding.documentType.text.toString()

        val fileUri = selectedFile
        if (fileUri != null) {
            val file = getFileFromUri(requireContext(), fileUri)
            file?.let { file ->
                val fileRequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)
                documentViewModel.uploadDocument(
                    filePart,
                    deviceId.toRequestBody(),
                    personId.toString().toRequestBody(),
                    documentCategoryId.toString().toRequestBody(),
                    "0".toRequestBody(),
                    documentTypeId.toString().toRequestBody(),
                    "0".toRequestBody(),
                    "xyz".toRequestBody(),
                    "2024-01-01".toRequestBody(),
                    "2024-01-01".toRequestBody(),
                )
            }
        }
    }

    override fun onDocumentCategoryItemSelected(selectedItem: DocumentCategoryData) {
        binding.documentCategory.setText(selectedItem.name)
        documentCategoryId = selectedItem.id.toString().toInt()
    }

    override fun onDocumentTypeItemSelected(selectedItem: DocumentTypeData) {
        binding.documentType.setText(selectedItem.name)
        documentTypeId = selectedItem.id.toString().toInt()
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

    private fun selectFileFocusListener() {
        binding.selectFile.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.selectFileContainer.helperText = validSelectFile()
            }
        }
    }

    private fun validSelectFile(): String? {
        val selectFile = binding.selectFile.text.toString()
        if (selectFile.isEmpty()) {
            return "select files"
        }
        return null
    }

    override fun onStart() {
        super.onStart()
        requireDocumentBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}