package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.query.query_type.QueryTypeData
import com.bsel.remitngo.databinding.ItemQueryTypeBinding

class QueryTypeAdapter(
    private val selectedItem: (QueryTypeData) -> Unit
) : RecyclerView.Adapter<QueryTypeViewHolder>() {

    private val queryTypeList = ArrayList<QueryTypeData>()
    private var filteredQueryTypeList = ArrayList<QueryTypeData>()

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

    fun setList(queryType: List<QueryTypeData>) {
        queryTypeList.clear()
        queryTypeList.addAll(queryType)
        filter("")
    }

    fun filter(query: String) {
        filteredQueryTypeList.clear()
        for (queryType in queryTypeList) {
            if (queryType.name!!.contains(query, ignoreCase = true)) {
                filteredQueryTypeList.add(queryType)
            }
        }
        notifyDataSetChanged()
    }

}

class QueryTypeViewHolder(val binding: ItemQueryTypeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        queryType: QueryTypeData,
        selectedItem: (QueryTypeData) -> Unit
    ) {
        binding.queryTypeName.text = queryType.name
        binding.itemQueryTypeLayout.setOnClickListener {
            selectedItem(queryType)
        }
    }
}