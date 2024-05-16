package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.query.QueryData
import com.bsel.remitngo.databinding.ItemQueryBinding

class QueryAdapter(
    private val selectedItem: (QueryData) -> Unit
) : RecyclerView.Adapter<QueryViewHolder>() {

    private val queryItemList = ArrayList<QueryData>()
    private var filteredQueryItemList = ArrayList<QueryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemQueryBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_query, parent, false)
        return QueryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredQueryItemList.size
    }

    override fun onBindViewHolder(
        holder: QueryViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredQueryItemList[position], selectedItem)
    }

    fun setList(queryItem: List<QueryData>) {
        queryItemList.clear()
        queryItemList.addAll(queryItem)
        queryFilter("")
    }

    fun queryFilter(query: String) {
        filteredQueryItemList.clear()
        for (queryItem in queryItemList) {
            if (queryItem.transactionCode!=null){
                if (queryItem.transactionCode!!.contains(query, ignoreCase = true)) {
                    filteredQueryItemList.add(queryItem)
                }
            }
        }
        notifyDataSetChanged()
    }

}

class QueryViewHolder(val binding: ItemQueryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        queryItem: QueryData,
        selectedItem: (QueryData) -> Unit
    ) {
        if (queryItem.complainTypeName != null) {
            binding.query.text = queryItem.complainTypeName
        }
        if (queryItem.complainTypeName != null) {
            binding.type.text = queryItem.complainTypeName
        }
        if (queryItem.complainStatusName != null) {
            binding.status.text = queryItem.complainStatusName
        }
        if (queryItem.transactionCode != null) {
            binding.transactionCode.text = queryItem.transactionCode
        }
        if (queryItem.message != null) {
            binding.message.text = queryItem.message
        }
        binding.itemQueryLayout.setOnClickListener {
            selectedItem(queryItem)
        }
    }
}