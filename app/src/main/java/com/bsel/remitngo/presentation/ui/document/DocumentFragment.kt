package com.bsel.remitngo.presentation.ui.document

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.DocumentAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.document.document.GetDocumentData
import com.bsel.remitngo.data.model.document.document.GetDocumentItem
import com.bsel.remitngo.databinding.FragmentDocumentBinding
import com.bsel.remitngo.presentation.di.Injector
import java.util.*
import javax.inject.Inject

class DocumentFragment : Fragment() {
    @Inject
    lateinit var documentViewModelFactory: DocumentViewModelFactory
    private lateinit var documentViewModel: DocumentViewModel

    private lateinit var binding: FragmentDocumentBinding

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var documentAdapter: DocumentAdapter

    var ipAddress: String? = null
    private lateinit var deviceId: String

    private lateinit var personId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_document, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDocumentBinding.bind(view)

        (requireActivity().application as Injector).createDocumentSubComponent().inject(this)

        documentViewModel =
            ViewModelProvider(this, documentViewModelFactory)[DocumentViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()

        deviceId = getDeviceId(requireContext())
        ipAddress = getIPAddress(requireContext())

        binding.btnUploadDocument.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_documents_to_nav_upload_documents
            )
        }

        val getDocumentItem = GetDocumentItem(
            deviceId = deviceId,
            personId = personId.toInt(),
            documentId = 0
        )
        documentViewModel.getDocument(getDocumentItem)
        observeGetDocumentResult()
    }

    private fun observeGetDocumentResult() {
        documentViewModel.getDocumentResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    binding.documentRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                    documentAdapter = DocumentAdapter(
                        selectedItem = { selectedItem: GetDocumentData ->
                            documentItem(selectedItem)
                            binding.documentSearch.setQuery("", false)
                        },
                        preViewItem = { selectedItem: GetDocumentData ->
                            preViewItem(selectedItem)
                            binding.documentSearch.setQuery("", false)
                        }
                    )
                    binding.documentRecyclerView.adapter = documentAdapter
                    documentAdapter.setList(result.data as List<GetDocumentData>)
                    documentAdapter.notifyDataSetChanged()
                    binding.documentSearch.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            documentAdapter.filter(newText.orEmpty())
                            return true
                        }
                    })
                    binding.documentRecyclerView.addOnScrollListener(
                        object : RecyclerView.OnScrollListener() {
                            override fun onScrolled(
                                recyclerView: RecyclerView,
                                dx: Int,
                                dy: Int
                            ) {
                                super.onScrolled(
                                    recyclerView,
                                    dx,
                                    dy
                                )
                                if (dy > 10 && binding.btnUploadDocument.isExtended) {
                                    binding.btnUploadDocument.shrink()
                                }
                                if (dy < -10 && !binding.btnUploadDocument.isExtended) {
                                    binding.btnUploadDocument.extend()
                                }
                                if (!recyclerView.canScrollVertically(
                                        -1
                                    )
                                ) {
                                    binding.btnUploadDocument.extend()
                                }
                            }
                        })
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun documentItem(selectedItem: GetDocumentData) {
        val bundle = Bundle().apply {
            putString("personId", selectedItem.personId.toString())
            putString("status", selectedItem.status.toString())
            putString("iD", selectedItem.iD.toString())
            putString("documentCategoryId", selectedItem.categoryId.toString())
            putString("documentTypeId", selectedItem.typeId.toString())
            putString("docNo", selectedItem.docNo.toString())
            putString("issueBy", selectedItem.issueBy.toString())
            putString("issueDate", selectedItem.issueDate.toString())
            putString("expireDate", selectedItem.expireDate.toString())
            putString("fileName", selectedItem.fileName.toString())
        }
        findNavController().navigate(
            R.id.action_nav_documents_to_nav_update_documents,
            bundle
        )
    }

    private fun preViewItem(preViewItem: GetDocumentData) {
        val fileName = preViewItem.fileName
        val imageUrl =
            "https://uat.bracsaajanexchange.com/REmitERPBDUAT/UploadedFiles/PersonFiles/$fileName"
        Log.i("info", "imageUrl: $imageUrl")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(imageUrl)
        context?.startActivity(intent)
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

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.nav_main)
        }
    }

}