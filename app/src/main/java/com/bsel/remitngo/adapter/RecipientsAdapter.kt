package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ItemRecipientBinding
import com.bsel.remitngo.model.RecipientItem
import kotlin.random.Random

class RecipientsAdapter(
    private val selectedItem: (RecipientItem) -> Unit
) : RecyclerView.Adapter<RecipientViewHolder>() {

    private val recipientsList = ArrayList<RecipientItem>()
    private var filteredRecipientsList = ArrayList<RecipientItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemRecipientBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_recipient, parent, false)
        return RecipientViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredRecipientsList.size
    }

    override fun onBindViewHolder(
        holder: RecipientViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredRecipientsList[position], selectedItem)
    }

    fun setList(recipientItem: List<RecipientItem>) {
        recipientsList.clear()
        recipientsList.addAll(recipientItem)
        filter("")
    }

    fun filter(query: String) {
        filteredRecipientsList.clear()
        for (recipients in recipientsList) {
            if (recipients.name!!.contains(query, ignoreCase = true)) {
                filteredRecipientsList.add(recipients)
            }
        }
        notifyDataSetChanged()
    }

}

class RecipientViewHolder(val binding: ItemRecipientBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        recipientItem: RecipientItem,
        selectedItem: (RecipientItem) -> Unit
    ) {

        val randomColor = getRandomColor()
        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.shape = GradientDrawable.OVAL
        backgroundDrawable.setColor(randomColor)

        binding.firstLetterBackground.background = backgroundDrawable
        binding.firstLetterName.setTextColor(getRandomColor())

        binding.firstLetterName.text = recipientItem.firstLetter
        binding.recipientName.text = recipientItem.name
        binding.recipientAccount.text = recipientItem.accountNumber
        binding.itemRecipientLayout.setOnClickListener {
            selectedItem(recipientItem)
        }
    }

    private fun getRandomColor(): Int {
        val random = Random.Default
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

}