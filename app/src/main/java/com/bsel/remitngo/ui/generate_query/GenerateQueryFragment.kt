package com.bsel.remitngo.ui.generate_query

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.QueryAdapter
import com.bsel.remitngo.bottom_sheet.AddQueryBottomSheet
import com.bsel.remitngo.databinding.FragmentGenerateQueryBinding
import com.bsel.remitngo.model.QueryItem


class GenerateQueryFragment : Fragment() {

    private lateinit var binding: FragmentGenerateQueryBinding

    private lateinit var queryAdapter: QueryAdapter

    private lateinit var queryItems: List<QueryItem>

    private val addQueryBottomSheet: AddQueryBottomSheet by lazy { AddQueryBottomSheet() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_generate_query, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGenerateQueryBinding.bind(view)

        binding.btnAddNewQuere.setOnClickListener {
            addQueryBottomSheet.show(childFragmentManager, addQueryBottomSheet.tag)
        }

        queryItems = arrayOf(
            QueryItem("Amendment Related Query"),
            QueryItem("Amendment Related Query"),
            QueryItem("Amendment Related Query"),
            QueryItem("Amendment Related Query"),
            QueryItem("Amendment Related Query"),
            QueryItem("Amendment Related Query"),
            QueryItem("Amendment Related Query"),
            QueryItem("Amendment Related Query"),
            QueryItem("Amendment Related Query"),
            QueryItem("Amendment Related Query"),
            QueryItem("Amendment Related Query")
        ).toList()

        binding.queryRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        queryAdapter = QueryAdapter(
            selectedItem = { selectedItem: QueryItem ->
                queryItem(selectedItem)
            }
        )
        binding.queryRecyclerView.adapter = queryAdapter
        queryAdapter.setList(queryItems)
        queryAdapter.notifyDataSetChanged()

    }

    private fun queryItem(selectedItem: QueryItem) {
        Log.i("info", "selectedItem: $selectedItem")
        findNavController().navigate(R.id.action_nav_generate_query_to_nav_update_query)
    }


}