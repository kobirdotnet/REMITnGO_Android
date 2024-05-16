package com.bsel.remitngo.presentation.ui.query

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.QueryMessageAdapter
import com.bsel.remitngo.bottomSheet.QueryMessageBottomSheet
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.interfaceses.OnQueryMessageSelectedListener
import com.bsel.remitngo.data.model.query.query_message.QueryMessageData
import com.bsel.remitngo.data.model.query.query_message.QueryMessageItem
import com.bsel.remitngo.databinding.FragmentUpdateQueryBinding
import com.bsel.remitngo.presentation.di.Injector
import javax.inject.Inject

class UpdateQueryFragment : Fragment(), OnQueryMessageSelectedListener {
    @Inject
    lateinit var queryViewModelFactory: QueryViewModelFactory
    private lateinit var queryViewModel: QueryViewModel

    private lateinit var binding: FragmentUpdateQueryBinding

    private val queryMessageBottomSheet: QueryMessageBottomSheet by lazy { QueryMessageBottomSheet() }

    private lateinit var queryMessageAdapter: QueryMessageAdapter

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

    private var complainId: Int? = 0
    private var queryTypeId: Int? = 0
    private var transactionCode: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_query, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdateQueryBinding.bind(view)

        (requireActivity().application as Injector).createQuerySubComponent().inject(this)

        queryViewModel =
            ViewModelProvider(this, queryViewModelFactory)[QueryViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())

        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

        try {
            complainId = arguments?.getString("complainId").toString().toInt()
        }catch (e:NumberFormatException){
            e.localizedMessage
        }

        binding.btnAddMessage.setOnClickListener {
            queryMessageBottomSheet.itemSelectedListener = this
            queryMessageBottomSheet.setData(complainId!!,queryTypeId!!,transactionCode!!)
            queryMessageBottomSheet.show(childFragmentManager, queryMessageBottomSheet.tag)
        }

        val queryMessageItem = QueryMessageItem(
            deviceId = deviceId,
            personId = personId.toInt(),
            complainId = complainId
        )
        queryViewModel.queryMessage(queryMessageItem)
        observeQueryMessageResult()

    }

    private fun observeQueryMessageResult() {
        queryViewModel.queryMessageResult.observe(viewLifecycleOwner) { result ->
            try {
                result?.let { queryResponse ->
                    if (queryResponse.queryMessageData != null) {
                        val queryDataList =
                            queryResponse.queryMessageData

                        binding.messageRecyclerView.layoutManager =
                            LinearLayoutManager(requireActivity())
                        queryMessageAdapter = QueryMessageAdapter { selectedItem ->
                            queryMessage(selectedItem)
                        }
                        binding.messageRecyclerView.adapter = queryMessageAdapter
                        queryMessageAdapter.setList(queryDataList as List<QueryMessageData>)
                        queryMessageAdapter.notifyDataSetChanged()

                        if (queryDataList.isNotEmpty()) {
                            val queryData = queryDataList[0]
                            binding.queryType.setText(queryData.complainTypeName.toString())
                            binding.status.setText(queryData.complainStatusName.toString())
                            binding.transactionCode.setText(queryData.transactionCode.toString())
                            val complainStatus = queryData.complainStatus!!
                            if (complainStatus) {
                                binding.messageLayout.visibility = View.VISIBLE
                            } else {
                                binding.messageLayout.visibility = View.GONE
                            }

                            complainId = queryData.complainId
                            queryTypeId = queryData.complainType
                            transactionCode = queryData.transactionCode
                        }
                    }
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun queryMessage(selectedItem: QueryMessageData) {
        Log.i("info", "selectedItem: $selectedItem")
    }

    override fun onQueryMessageSelected(complainId: Int) {
        val queryMessageItem = QueryMessageItem(
            deviceId = deviceId,
            personId = personId.toInt(),
            complainId = complainId
        )
        queryViewModel.queryMessage(queryMessageItem)
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
}