package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemQueryTypeBinding
import com.bsel.remitngo.model.QueryType

class QueryTypeAdapter(
    private val selectedItem: (QueryType) -> Unit
) : RecyclerView.Adapter<QueryTypeViewHolder>() {

    private val queryTypeList = ArrayList<QueryType>()
    private var filteredQueryTypeList = ArrayList<QueryType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryTypeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemQueryTypeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_query_type, parent, false)
        return QueryTypeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredQueryTypeList.size
    }

    override fun onBindViewHolder(
        holder: QueryTypeViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredQueryTypeList[position], selectedItem)
    }

    fun setList(queryType: List<QueryType>) {
        queryTypeList.clear()
        queryTypeList.addAll(queryType)
        filter("")
    }

    fun filter(query: String) {
        filteredQueryTypeList.clear()
        for (queryType in queryTypeList) {
            if (queryType.queryTypeName!!.contains(query, ignoreCase = true)) {
                filteredQueryTypeList.add(queryType)
            }
        }
        notifyDataSetChanged()
    }

}

class QueryTypeViewHolder(val binding: ItemQueryTypeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        queryType: QueryType,
        selectedItem: (QueryType) -> Unit
    ) {
        binding.queryTypeName.text = queryType.queryTypeName
        binding.itemQueryTypeLayout.setOnClickListener {
            selectedItem(queryType)
        }
    }
}