package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.net.wifi.WifiManager
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
import com.bsel.remitngo.adapter.DocumentTypeAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeData
import com.bsel.remitngo.data.model.document.documentType.DocumentTypeItem
import com.bsel.remitngo.databinding.DocumentTypeLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnDocumentItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.document.DocumentViewModel
import com.bsel.remitngo.presentation.ui.document.DocumentViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import javax.inject.Inject

class DocumentTypeBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var documentViewModelFactory: DocumentViewModelFactory
    private lateinit var documentViewModel: DocumentViewModel

    var itemSelectedListener: OnDocumentItemSelectedListener? = null

    private lateinit var documentBehavior: BottomSheetBehavior<*>

    private lateinit var binding: DocumentTypeLayoutBinding

    private lateinit var documentTypeAdapter: DocumentTypeAdapter

    private lateinit var preferenceManager: PreferenceManager

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private lateinit var personId: String

    private var selectedDocumentType: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.document_type_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        documentBehavior = BottomSheetBehavior.from(view.parent as View)
        documentBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        documentBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createDocumentSubComponent().inject(this)

        documentViewModel =
            ViewModelProvider(this, documentViewModelFactory)[DocumentViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        binding.cancelButton.setOnClickListener { dismiss() }

        val documentTypeItem = DocumentTypeItem(
            deviceId = deviceId,
            params1 = selectedDocumentType!!.toInt(),
            params2 = 0
        )
        documentViewModel.documentType(documentTypeItem)
        observeDocumentTypeResult()

        return bottomSheet
    }

    fun setSelectedDocumentType(documentType: String) {
        selectedDocumentType = documentType
    }

    private fun observeDocumentTypeResult() {
        documentViewModel.documentTypeResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.documentTypeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                documentTypeAdapter = DocumentTypeAdapter(
                    selectedItem = { selectedItem: DocumentTypeData ->
                        documentType(selectedItem)
                        binding.documentTypeSearch.setQuery("", false)
                    }
                )
                binding.documentTypeRecyclerView.adapter = documentTypeAdapter
                documentTypeAdapter.setList(result.data as List<DocumentTypeData>)
                documentTypeAdapter.notifyDataSetChanged()

                binding.documentTypeSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        documentTypeAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
            } else {
                Log.i("info", "document type failed")
            }
        }
    }

    private fun documentType(selectedItem: DocumentTypeData) {
        itemSelectedListener?.onDocumentTypeItemSelected(selectedItem)
        dismiss()
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
        documentBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}