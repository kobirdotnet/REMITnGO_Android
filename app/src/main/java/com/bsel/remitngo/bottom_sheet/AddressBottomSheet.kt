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
import com.bsel.remitngo.adapter.AddressAdapter
import com.bsel.remitngo.databinding.AddressLayoutBinding
import com.bsel.remitngo.interfaceses.OnAddressItemSelectedListener
import com.bsel.remitngo.model.AddressItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddressBottomSheet : BottomSheetDialogFragment() {

    var itemSelectedListener: OnAddressItemSelectedListener? = null

    private lateinit var addressNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: AddressLayoutBinding

    private lateinit var addressAdapter: AddressAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.address_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        addressNameBehavior = BottomSheetBehavior.from(view.parent as View)
        addressNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        addressNameBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
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

        val addressItems = arrayOf(
            AddressItem("England"),
            AddressItem("British Isles"),
            AddressItem("Channel Islands"),
            AddressItem("Northern Ireland"),
            AddressItem("Scotland"),
            AddressItem("Wales")
        )

        binding.addressRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        addressAdapter = AddressAdapter(
            selectedItem = { selectedItem: AddressItem ->
                addressItem(selectedItem)
                binding.addressSearch.setQuery("", false)
            }
        )
        binding.addressRecyclerView.adapter = addressAdapter
        addressAdapter.setList(addressItems.asList())
        addressAdapter.notifyDataSetChanged()

        binding.addressSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                addressAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.cancelButton.setOnClickListener { dismiss() }

        return bottomSheet
    }

    private fun addressItem(selectedItem: AddressItem) {
        Log.i("info", "selectedItem: $selectedItem")
        itemSelectedListener?.onAddressItemSelected(selectedItem)
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        addressNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}