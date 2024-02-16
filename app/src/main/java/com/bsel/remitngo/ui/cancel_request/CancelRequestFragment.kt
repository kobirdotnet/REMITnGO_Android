package com.bsel.remitngo.ui.cancel_request

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.CancelRequestAdapter
import com.bsel.remitngo.databinding.FragmentCancelRequestBinding
import com.bsel.remitngo.model.CancelRequestItem

class CancelRequestFragment : Fragment() {

    private lateinit var binding: FragmentCancelRequestBinding

    private lateinit var cancelRequestAdapter: CancelRequestAdapter

    private lateinit var cancelRequestItems: List<CancelRequestItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cancel_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCancelRequestBinding.bind(view)

        cancelRequestItems = arrayOf(
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED"),
            CancelRequestItem("JUBAYER AHMED")
        ).toList()

        binding.cancelRequestRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        cancelRequestAdapter = CancelRequestAdapter(
            selectedItem = { selectedItem: CancelRequestItem ->
                cancelRequestItem(selectedItem)
            }
        )
        binding.cancelRequestRecyclerView.adapter = cancelRequestAdapter
        cancelRequestAdapter.setList(cancelRequestItems)
        cancelRequestAdapter.notifyDataSetChanged()
    }

    private fun cancelRequestItem(selectedItem: CancelRequestItem) {
        Log.i("info", "selectedItem: $selectedItem")
        findNavController().navigate(
            R.id.action_nav_cancel_request_to_nav_generate_cancel_request
        )
    }


}