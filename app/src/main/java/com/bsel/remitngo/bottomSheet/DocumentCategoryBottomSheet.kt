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
import com.bsel.remitngo.adapter.DocumentCategoryAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryData
import com.bsel.remitngo.data.model.document.documentCategory.DocumentCategoryItem
import com.bsel.remitngo.databinding.DocumentCategoryLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnDocumentItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.document.DocumentViewModel
import com.bsel.remitngo.presentation.ui.document.DocumentViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import javax.inject.Inject

class DocumentCategoryBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var documentViewModelFactory: DocumentViewModelFactory
    private lateinit var documentViewModel: DocumentViewModel

    var itemSelectedListener: OnDocumentItemSelectedListener? = null

    private lateinit var categoryBehavior: BottomSheetBehavior<*>

    private lateinit var binding: DocumentCategoryLayoutBinding

    private lateinit var documentCategoryAdapter: DocumentCategoryAdapter

    private lateinit var preferenceManager: PreferenceManager

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private lateinit var personId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.document_category_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        categoryBehavior = BottomSheetBehavior.from(view.parent as View)
        categoryBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        categoryBehavior.addBottomSheetCallback(object :
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

        val documentCategoryItem = DocumentCategoryItem(
            deviceId = deviceId,
            dropdownId = 20000,
            param1 = 0,
            param2 = 0
        )
        documentViewModel.documentCategory(documentCategoryItem)
        observeDocumentCategoryResult()

        return bottomSheet
    }

    private fun observeDocumentCategoryResult() {
        documentViewModel.documentCategoryResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    binding.documentCategoryRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                    documentCategoryAdapter = DocumentCategoryAdapter(
                        selectedItem = { selectedItem: DocumentCategoryData ->
                            documentCategory(selectedItem)
                            binding.documentCategorySearch.setQuery("", false)
                        }
                    )
                    binding.documentCategoryRecyclerView.adapter = documentCategoryAdapter
                    documentCategoryAdapter.setList(result.data as List<DocumentCategoryData>)
                    documentCategoryAdapter.notifyDataSetChanged()

                    binding.documentCategorySearch.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            documentCategoryAdapter.categoryFilter(newText.orEmpty())
                            return true
                        }
                    })
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun documentCategory(selectedItem: DocumentCategoryData) {
        itemSelectedListener?.onDocumentCategoryItemSelected(selectedItem)
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
        categoryBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}