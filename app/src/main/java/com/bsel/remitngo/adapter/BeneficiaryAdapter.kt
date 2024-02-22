package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.beneficiary.get_beneficiary.GetBeneficiaryData
import com.bsel.remitngo.databinding.ItemBeneficiaryBinding

class BeneficiaryAdapter(
    private val selectedItem: (GetBeneficiaryData) -> Unit
) : RecyclerView.Adapter<BeneficiaryViewHolder>() {

    private val beneficiaryList = ArrayList<GetBeneficiaryData>()
    private var filteredBeneficiaryList = ArrayList<GetBeneficiaryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiaryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemBeneficiaryBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_beneficiary, parent, false)
        return BeneficiaryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredBeneficiaryList.size
    }

    override fun onBindViewHolder(
        holder: BeneficiaryViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredBeneficiaryList[position], selectedItem)
    }

    fun setList(beneficiaryItem: List<GetBeneficiaryData>) {
        beneficiaryList.clear()
        beneficiaryList.addAll(beneficiaryItem)
        filter("")
    }

    fun filter(query: String) {
        filteredBeneficiaryList.clear()
        for (beneficiaries in beneficiaryList) {
            if (beneficiaries.name!!.contains(query, ignoreCase = true)) {
                filteredBeneficiaryList.add(beneficiaries)
            }
        }
        notifyDataSetChanged()
    }

}

class BeneficiaryViewHolder(val binding: ItemBeneficiaryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        beneficiaryItem: GetBeneficiaryData,
        selectedItem: (GetBeneficiaryData) -> Unit
    ) {

//        val randomColor = getRandomColor()
//        val backgroundDrawable = GradientDrawable()
//        backgroundDrawable.shape = GradientDrawable.OVAL
//        backgroundDrawable.setColor(randomColor)

//        binding.firstLetterBackground.background = backgroundDrawable
//        binding.firstLetterName.setTextColor(getRandomColor())
//        binding.firstLetterName.text = recipientItem.firstLetter

        binding.beneficiaryName.text = beneficiaryItem.name.toString()
        binding.beneficiaryPhone.text = beneficiaryItem.mobile.toString()
        binding.itemBeneficiaryLayout.setOnClickListener {
            selectedItem(beneficiaryItem)
        }
    }

//    private fun getRandomColor(): Int {
//        val random = Random.Default
//        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
//    }

}