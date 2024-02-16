package com.bsel.remitngo.ui.generate_query

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.QueryMessageAdapter
import com.bsel.remitngo.bottom_sheet.UpdateQueryBottomSheet
import com.bsel.remitngo.databinding.FragmentUpdateQueryBinding
import com.bsel.remitngo.model.QueryMessage

class UpdateQueryFragment : Fragment() {

    private lateinit var binding: FragmentUpdateQueryBinding

    private val updateQueryBottomSheet: UpdateQueryBottomSheet by lazy { UpdateQueryBottomSheet() }

    private lateinit var queryMessageAdapter: QueryMessageAdapter

    private lateinit var queryMessages: List<QueryMessage>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_query, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdateQueryBinding.bind(view)

        binding.btnAddMessage.setOnClickListener {
            updateQueryBottomSheet.show(childFragmentManager, updateQueryBottomSheet.tag)
        }

        queryMessages = arrayOf(
            QueryMessage("Amendment Related Query"),
            QueryMessage("Amendment Related Query"),
            QueryMessage("Amendment Related Query"),
            QueryMessage("Amendment Related Query"),
            QueryMessage("Amendment Related Query"),
            QueryMessage("Amendment Related Query"),
            QueryMessage("Amendment Related Query"),
            QueryMessage("Amendment Related Query"),
            QueryMessage("Amendment Related Query")
        ).toList()

        binding.messageRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        queryMessageAdapter = QueryMessageAdapter(
            selectedItem = { selectedItem: QueryMessage ->
                queryMessage(selectedItem)
            }
        )
        binding.messageRecyclerView.adapter = queryMessageAdapter
        queryMessageAdapter.setList(queryMessages)
        queryMessageAdapter.notifyDataSetChanged()

    }

    private fun queryMessage(selectedItem: QueryMessage) {
        Log.i("info", "selectedItem: $selectedItem")
    }


}