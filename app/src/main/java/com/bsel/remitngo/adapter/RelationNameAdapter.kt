package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemRelationBinding
import com.bsel.remitngo.model.RelationItem

class RelationNameAdapter(
    private val selectedItem: (RelationItem) -> Unit
) : RecyclerView.Adapter<RelationNameViewHolder>() {

    private val relationItemList = ArrayList<RelationItem>()
    private var filteredRelationItemList = ArrayList<RelationItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelationNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemRelationBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_relation, parent, false)
        return RelationNameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredRelationItemList.size
    }

    override fun onBindViewHolder(
        holder: RelationNameViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredRelationItemList[position], selectedItem)
    }

    fun setList(relationItem: List<RelationItem>) {
        relationItemList.clear()
        relationItemList.addAll(relationItem)
        filter("")
    }

    fun filter(query: String) {
        filteredRelationItemList.clear()
        for (relationItem in relationItemList) {
            if (relationItem.relationName!!.contains(query, ignoreCase = true)) {
                filteredRelationItemList.add(relationItem)
            }
        }
        notifyDataSetChanged()
    }

}

class RelationNameViewHolder(val binding: ItemRelationBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        relationItem: RelationItem,
        selectedItem: (RelationItem) -> Unit
    ) {
        binding.relationName.text = relationItem.relationName
        binding.itemRelationLayout.setOnClickListener {
            selectedItem(relationItem)
        }
    }
}