package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemQueryMessageBinding
import com.bsel.remitngo.model.QueryMessage

class QueryMessageAdapter(
    private val selectedItem: (QueryMessage) -> Unit
) : RecyclerView.Adapter<QueryMessageViewHolder>() {

    private val queryMessageList = ArrayList<QueryMessage>()
    private var filteredQueryMessageList = ArrayList<QueryMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryMessageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemQueryMessageBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_query_message, parent, false)
        return QueryMessageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredQueryMessageList.size
    }

    override fun onBindViewHolder(
        holder: QueryMessageViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredQueryMessageList[position], selectedItem)
    }

    fun setList(queryMessage: List<QueryMessage>) {
        queryMessageList.clear()
        queryMessageList.addAll(queryMessage)
        filter("")
    }

    fun filter(query: String) {
        filteredQueryMessageList.clear()
        for (queryMessage in queryMessageList) {
            if (queryMessage.queryMessage!!.contains(query, ignoreCase = true)) {
                filteredQueryMessageList.add(queryMessage)
            }
        }
        notifyDataSetChanged()
    }

}

class QueryMessageViewHolder(val binding: ItemQueryMessageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        queryMessage: QueryMessage,
        selectedItem: (QueryMessage) -> Unit
    ) {
        binding.message.text = queryMessage.queryMessage
        binding.itemQueryMessageLayout.setOnClickListener {
            selectedItem(queryMessage)
        }
    }
}