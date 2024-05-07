package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.query.query_message.QueryMessageTable
import com.bsel.remitngo.databinding.ItemQueryMessageBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class QueryMessageAdapter(
    private val selectedItem: (QueryMessageTable) -> Unit
) : RecyclerView.Adapter<QueryMessageViewHolder>() {

    private val queryMessageList = ArrayList<QueryMessageTable>()
    private var filteredQueryMessageList = ArrayList<QueryMessageTable>()

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

    fun setList(queryMessage: List<QueryMessageTable>) {
        queryMessageList.clear()
        queryMessageList.addAll(queryMessage)
        filter("")
    }

    fun filter(query: String) {
        filteredQueryMessageList.clear()
        for (queryMessage in queryMessageList) {
            if (queryMessage.transactionCode!!.contains(query, ignoreCase = true)) {
                filteredQueryMessageList.add(queryMessage)
            }
        }
        notifyDataSetChanged()
    }

}

class QueryMessageViewHolder(val binding: ItemQueryMessageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(
        queryMessage: QueryMessageTable,
        selectedItem: (QueryMessageTable) -> Unit
    ) {
        if (queryMessage.message != null) {
            binding.message.text = queryMessage.message
        }
        if (queryMessage.name != null) {
            binding.postedBy.text = queryMessage.name
        }
        if (queryMessage.updateDate != null) {
            val dateTime =
                LocalDateTime.parse(queryMessage.updateDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val date = dateTime.toLocalDate()
            val updateDate = date.format(DateTimeFormatter.ISO_DATE)
            binding.postedDate.text = "$updateDate"
        }

        binding.itemQueryMessageLayout.setOnClickListener {
            selectedItem(queryMessage)
        }
    }
}