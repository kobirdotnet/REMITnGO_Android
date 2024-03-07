package com.bsel.remitngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsel.remitngo.R
import com.bsel.remitngo.data.model.bank.bank_account.GetBankData
import com.bsel.remitngo.databinding.ItemGetBankBinding

class BankAdapter(
    private val selectedItem: (GetBankData) -> Unit
) : RecyclerView.Adapter<BankViewHolder>() {

    private val bankList = ArrayList<GetBankData>()
    private var filteredBankList = ArrayList<GetBankData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemGetBankBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_get_bank, parent, false)
        return BankViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredBankList.size
    }

    override fun onBindViewHolder(
        holder: BankViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bind(filteredBankList[position], selectedItem)
    }

    fun setList(bankItem: List<GetBankData>) {
        bankList.clear()
        bankList.addAll(bankItem)
        filter("")
    }

    fun filter(query: String) {
        filteredBankList.clear()
        for (banks in bankList) {
            if (banks.bankName!!.contains(query, ignoreCase = true)) {
                filteredBankList.add(banks)
            }
        }
        notifyDataSetChanged()
    }

}

class BankViewHolder(val binding: ItemGetBankBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        bankItem: GetBankData,
        selectedItem: (GetBankData) -> Unit
    ) {

//        val randomColor = getRandomColor()
//        val backgroundDrawable = GradientDrawable()
//        backgroundDrawable.shape = GradientDrawable.OVAL
//        backgroundDrawable.setColor(randomColor)

//        binding.firstLetterBackground.background = backgroundDrawable
//        binding.firstLetterName.setTextColor(getRandomColor())
//        binding.firstLetterName.text = recipientItem.firstLetter

        binding.bankName.text = bankItem.bankName.toString()
        binding.bankAccount.text = bankItem.accountNo.toString()
        binding.itemGetBankLayout.setOnClickListener {
            selectedItem(bankItem)
        }
    }

//    private fun getRandomColor(): Int {
//        val random = Random.Default
//        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
//    }

}