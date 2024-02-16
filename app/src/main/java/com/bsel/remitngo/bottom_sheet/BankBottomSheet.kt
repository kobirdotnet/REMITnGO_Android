package com.bsel.remitngo.bottom_sheet

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.BankNameAdapter
import com.bsel.remitngo.databinding.BankNameLayoutBinding
import com.bsel.remitngo.interfaceses.OnBankItemSelectedListener
import com.bsel.remitngo.model.BankItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BankBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnBankItemSelectedListener? = null

    private lateinit var bankNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: BankNameLayoutBinding

    private lateinit var bankNameAdapter: BankNameAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.bank_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        bankNameBehavior = BottomSheetBehavior.from(view.parent as View)
        bankNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        bankNameBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull view: View, i: Int) {
                when (i) {
                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                }
            }

            override fun onSlide(@NonNull view: View, v: Float) {}
        })

        val bankItems = arrayOf(
            BankItem(R.drawable.ab_bank, "AB Bank Ltd."),
            BankItem(R.drawable.agrani_bank, "Agrani Bank Ltd."),
            BankItem(R.drawable.al_arafah_islami_bank, "Al-Arafah Islami Bank Ltd."),
            BankItem(R.drawable.bangladesh_bank, "Bangladesh Bank Ltd."),
            BankItem(R.drawable.bangladesh_krishi, "Bangladesh Krishi Bank Ltd."),
            BankItem(R.drawable.brac_bank, "BRAC Bank Ltd."),
            BankItem(R.drawable.dhaka_bank, "Dhaka Bank Ltd."),
            BankItem(R.drawable.dutch_bangla_bank, "Dutch-Bangla Bank Ltd."),
            BankItem(R.drawable.eastern_bank, "Eastern Bank Ltd."),
            BankItem(R.drawable.ific_bank, "IFIC Bank Ltd."),
            BankItem(R.drawable.islami_bank_bangladesh, "Islami Bank Bangladesh Ltd."),
            BankItem(R.drawable.jamuna_bank, "Jamuna Bank Ltd."),
            BankItem(R.drawable.janata_bank, "Janata Bank Ltd."),
            BankItem(R.drawable.mutual_trust_bank, "Mutual Trust Bank Ltd."),
            BankItem(R.drawable.mercantile_bank, "Mercantile Bank Ltd."),
            BankItem(R.drawable.one_bank, "One Bank Ltd."),
            BankItem(R.drawable.prime_bank, "Prime Bank Ltd."),
            BankItem(R.drawable.pubali_bank, "Pubali Bank Ltd.")
        )

        binding.bankRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        bankNameAdapter = BankNameAdapter(
            selectedItem = { selectedItem: BankItem ->
                bankItem(selectedItem)
                binding.bankSearch.setQuery("", false)
            }
        )
        binding.bankRecyclerView.adapter = bankNameAdapter
        bankNameAdapter.setList(bankItems.asList())
        bankNameAdapter.notifyDataSetChanged()

        binding.bankSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                bankNameAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun bankItem(selectedItem: BankItem) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onBankItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        bankNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}