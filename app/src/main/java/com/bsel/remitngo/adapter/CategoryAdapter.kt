package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemCategoryBinding
import com.bsel.remitngo.model.Category

class CategoryAdapter(
    private val selectedItem: (Category) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

    private val categoryList = ArrayList<Category>()
    private var filteredCategoryList = ArrayList<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCategoryBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_category, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredCategoryList.size
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredCategoryList[position], selectedItem)
    }

    fun setList(category: List<Category>) {
        categoryList.clear()
        categoryList.addAll(category)
        filter("")
    }

    fun filter(query: String) {
        filteredCategoryList.clear()
        for (category in categoryList) {
            if (category.categoryName!!.contains(query, ignoreCase = true)) {
                filteredCategoryList.add(category)
            }
        }
        notifyDataSetChanged()
    }

}

class CategoryViewHolder(val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        category: Category,
        selectedItem: (Category) -> Unit
    ) {
        binding.categoryName.text = category.categoryName
        binding.itemCategoryLayout.setOnClickListener {
            selectedItem(category)
        }
    }
}