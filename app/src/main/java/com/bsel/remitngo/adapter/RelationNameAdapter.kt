package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.relation.RelationData
import com.bsel.remitngo.data.model.relation.RelationItem
import com.bsel.remitngo.databinding.ItemRelationBinding

class RelationNameAdapter(
    private val selectedItem: (RelationData) -> Unit
) : RecyclerView.Adapter<RelationNameViewHolder>() {

    private val relationItemList = ArrayList<RelationData>()
    private var filteredRelationItemList = ArrayList<RelationData>()

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

    fun setList(relationItem: List<RelationData>) {
        relationItemList.clear()
        relationItemList.addAll(relationItem)
        filter("")
    }

    fun filter(query: String) {
        filteredRelationItemList.clear()
        for (relationItem in relationItemList) {
            if (relationItem.name!!.contains(query, ignoreCase = true)) {
                filteredRelationItemList.add(relationItem)
            }
        }
        notifyDataSetChanged()
    }

}

class RelationNameViewHolder(val binding: ItemRelationBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        relationItem: RelationData,
        selectedItem: (RelationData) -> Unit
    ) {
        if (relationItem.name != null) {
            binding.relationName.text = relationItem.name
        }

        binding.itemRelationLayout.setOnClickListener {
            selectedItem(relationItem)
        }
    }
}