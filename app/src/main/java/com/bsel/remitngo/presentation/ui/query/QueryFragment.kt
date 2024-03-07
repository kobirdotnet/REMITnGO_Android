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
import com.bsel.remitngo.adapter.QueryAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.query.QueryItem
import com.bsel.remitngo.data.model.query.QueryTable
import com.bsel.remitngo.databinding.FragmentQueryBinding
import com.bsel.remitngo.presentation.di.Injector
import javax.inject.Inject


class QueryFragment : Fragment() {
    @Inject
    lateinit var queryViewModelFactory: QueryViewModelFactory
    private lateinit var queryViewModel: QueryViewModel

    private lateinit var binding: FragmentQueryBinding

    private lateinit var queryAdapter: QueryAdapter

    private val addQueryFragment: AddQueryFragment by lazy { AddQueryFragment() }

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_query, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQueryBinding.bind(view)

        (requireActivity().application as Injector).createQuerySubComponent().inject(this)

        queryViewModel =
            ViewModelProvider(this, queryViewModelFactory)[QueryViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

        binding.btnAddNewQuere.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_generate_query_to_nav_add_query
            )
        }

        val queryItem = QueryItem(
            deviceId = deviceId,
            params1 = personId.toInt(),
            params2 = 0
        )
        queryViewModel.query(queryItem)
        observeQueryResult()

    }

    private fun observeQueryResult() {
        queryViewModel.queryResult.observe(viewLifecycleOwner) { result ->
            result?.let { queryResponse ->
                if (queryResponse.queryData != null) {
                    val queryDataList = queryResponse.queryData.queryTable ?: emptyList()
                    binding.queryRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                    queryAdapter = QueryAdapter { selectedItem ->
                        queryItem(selectedItem)
                    }
                    binding.queryRecyclerView.adapter = queryAdapter
                    queryAdapter.setList(queryDataList as List<QueryTable>)
                    queryAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun queryItem(selectedItem: QueryTable) {
        val bundle = Bundle().apply {
            putString("complainId", selectedItem.complainID.toString())
        }
        findNavController().navigate(
            R.id.action_nav_generate_query_to_nav_update_query,
            bundle
        )
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