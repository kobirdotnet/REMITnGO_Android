package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemQueryBinding
import com.bsel.remitngo.model.QueryItem

class QueryAdapter(
    private val selectedItem: (QueryItem) -> Unit
) : RecyclerView.Adapter<QueryViewHolder>() {

    private val queryItemList = ArrayList<QueryItem>()
    private var filteredQueryItemList = ArrayList<QueryItem>()

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

    fun setList(queryItem: List<QueryItem>) {
        queryItemList.clear()
        queryItemList.addAll(queryItem)
        filter("")
    }

    fun filter(query: String) {
        filteredQueryItemList.clear()
        for (queryItem in queryItemList) {
            if (queryItem.queryName!!.contains(query, ignoreCase = true)) {
                filteredQueryItemList.add(queryItem)
            }
        }
        notifyDataSetChanged()
    }

}

class QueryViewHolder(val binding: ItemQueryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        queryItem: QueryItem,
        selectedItem: (QueryItem) -> Unit
    ) {
        binding.queryId.text = queryItem.queryName
        binding.itemQueryLayout.setOnClickListener {
            selectedItem(queryItem)
        }
    }
}