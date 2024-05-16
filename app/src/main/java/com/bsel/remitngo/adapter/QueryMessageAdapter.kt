package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.query.query_message.QueryMessageData
import com.bsel.remitngo.databinding.ItemQueryMessageBinding

class QueryMessageAdapter(
    private val selectedItem: (QueryMessageData) -> Unit
) : RecyclerView.Adapter<QueryMessageViewHolder>() {

    private val queryMessageList = ArrayList<QueryMessageData>()
    private var filteredQueryMessageList = ArrayList<QueryMessageData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryMessageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemQueryMessageBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_query_message, parent, false)
        return QueryMessageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredQueryMessageList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: QueryMessageViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredQueryMessageList[position], selectedItem)
    }

    fun setList(queryMessage: List<QueryMessageData>) {
        queryMessageList.clear()
        queryMessageList.addAll(queryMessage)
        queryMessageFilter("")
    }

    fun queryMessageFilter(query: String) {
        filteredQueryMessageList.clear()
        for (queryMessage in queryMessageList) {
            if (queryMessage.transactionCode!=null){
                if (queryMessage.transactionCode.toString().contains(query, ignoreCase = true)) {
                    filteredQueryMessageList.add(queryMessage)
                }
            }
        }
        notifyDataSetChanged()
    }

}

class QueryMessageViewHolder(val binding: ItemQueryMessageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        queryMessage: QueryMessageData,
        selectedItem: (QueryMessageData) -> Unit
    ) {
        if (queryMessage.message != null) {
            binding.message.text = queryMessage.message
        }
        if (queryMessage.name != null) {
            binding.postedBy.text = queryMessage.name
        }
        if (queryMessage.updateDate != null) {
            binding.postedDate.text = queryMessage.updateDate
        }

        binding.itemQueryMessageLayout.setOnClickListener {
            selectedItem(queryMessage)
        }
    }
}