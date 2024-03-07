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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.QueryMessageAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.query.query_message.QueryMessageItem
import com.bsel.remitngo.data.model.query.query_message.QueryMessageTable
import com.bsel.remitngo.databinding.FragmentUpdateQueryBinding
import com.bsel.remitngo.presentation.di.Injector
import javax.inject.Inject

class UpdateQueryFragment : Fragment() {
    @Inject
    lateinit var queryViewModelFactory: QueryViewModelFactory
    private lateinit var queryViewModel: QueryViewModel

    private lateinit var binding: FragmentUpdateQueryBinding

    private lateinit var queryMessageAdapter: QueryMessageAdapter

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

    private lateinit var complainId: String
    private lateinit var queryTypeId: String
    private lateinit var transactionCode: String

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

        complainId = arguments?.getString("complainId").toString()

        binding.btnAddMessage.setOnClickListener {
            val bundle = Bundle().apply {
                putString("complainId", complainId)
                putString("queryTypeId", queryTypeId)
                putString("transactionCode", transactionCode)
            }
            findNavController().navigate(
                R.id.action_nav_update_query_to_nav_query_message,
                bundle
            )
        }

        val queryMessageItem = QueryMessageItem(
            deviceId = deviceId,
            params1 = personId.toInt(),
            params2 = complainId.toInt()
        )
        queryViewModel.queryMessage(queryMessageItem)
        observeQueryMessageResult()

    }

    private fun observeQueryMessageResult() {
        queryViewModel.queryMessageResult.observe(viewLifecycleOwner) { result ->
            result?.let { queryResponse ->
                if (queryResponse.queryMessageData != null) {
                    val queryDataList =
                        queryResponse.queryMessageData.queryMessageTable ?: emptyList()

                    binding.messageRecyclerView.layoutManager =
                        LinearLayoutManager(requireActivity())
                    queryMessageAdapter = QueryMessageAdapter { selectedItem ->
                        queryMessage(selectedItem)
                    }
                    binding.messageRecyclerView.adapter = queryMessageAdapter
                    queryMessageAdapter.setList(queryDataList as List<QueryMessageTable>)
                    queryMessageAdapter.notifyDataSetChanged()

                    if (queryDataList.isNotEmpty()) {
                        val queryData = queryDataList[0]
                        binding.queryType.setText(queryData.complainTypeString.toString())
                        binding.status.setText(queryData.complainStatusString.toString())
                        binding.transactionCode.setText(queryData.transactionCode.toString())
                        val complainStatus = queryData.complainStatus!!
                        if (complainStatus) {
                            binding.messageLayout.visibility = View.VISIBLE
                        } else {
                            binding.messageLayout.visibility = View.GONE
                        }

                        complainId = queryData.complainID.toString()
                        queryTypeId = queryData.complainType.toString()
                        transactionCode = queryData.transactionCode.toString()
                    }
                }
            }
        }
    }

    private fun queryMessage(selectedItem: QueryMessageTable) {
        Log.i("info", "selectedItem: $selectedItem")
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