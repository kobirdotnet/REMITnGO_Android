package com.bsel.remitngo.ui.cancel_request

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.CancellationAdapter
import com.bsel.remitngo.databinding.FragmentCancellationBinding
import com.bsel.remitngo.model.CancellationItem

class CancellationFragment : Fragment() {

    private lateinit var binding: FragmentCancellationBinding

    private lateinit var cancellationAdapter: CancellationAdapter

    private lateinit var cancellationItems: List<CancellationItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cancellation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCancellationBinding.bind(view)

        binding.btnNew.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_cancellation_to_nav_cancel_request
            )
        }

        cancellationItems = arrayOf(
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : "),
            CancellationItem("Transaction Code : ")
        ).toList()

        binding.cancellationRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        cancellationAdapter = CancellationAdapter(
            selectedItem = { selectedItem: CancellationItem ->
                cancellationItem(selectedItem)
            }
        )
        binding.cancellationRecyclerView.adapter = cancellationAdapter
        cancellationAdapter.setList(cancellationItems)
        cancellationAdapter.notifyDataSetChanged()

    }

    private fun cancellationItem(selectedItem: CancellationItem) {
        Log.i("info", "selectedItem: $selectedItem")
    }


}