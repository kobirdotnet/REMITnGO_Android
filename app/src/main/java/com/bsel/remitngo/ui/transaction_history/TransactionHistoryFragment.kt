package com.bsel.remitngo.ui.transaction_history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.TransactionHistoryAdapter
import com.bsel.remitngo.databinding.FragmentTransactionHistoryBinding
import com.bsel.remitngo.model.TransactionItem

class TransactionHistoryFragment : Fragment() {

    private lateinit var binding: FragmentTransactionHistoryBinding

    private lateinit var transactionHistoryAdapter: TransactionHistoryAdapter

    private lateinit var transactionItems: List<TransactionItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transaction_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionHistoryBinding.bind(view)

        transactionItems = arrayOf(
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED"),
            TransactionItem("JUBAYER AHMED")
        ).toList()

        binding.transactionHistoryRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        transactionHistoryAdapter = TransactionHistoryAdapter(
            selectedItem = { selectedItem: TransactionItem ->
                transactionItem(selectedItem)
                binding.transactionHistorySearch.setQuery("", false)
            }
        )
        binding.transactionHistoryRecyclerView.adapter = transactionHistoryAdapter
        transactionHistoryAdapter.setList(transactionItems)
        transactionHistoryAdapter.notifyDataSetChanged()

        binding.transactionHistorySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                transactionHistoryAdapter.filter(newText.orEmpty())
                return true
            }
        })

    }

    private fun transactionItem(selectedItem: TransactionItem) {
        Log.i("info", "selectedItem: $selectedItem")
    }

}