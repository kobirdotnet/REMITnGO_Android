package com.bsel.remitngo.bottom_sheet

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.DivisionNameAdapter
import com.bsel.remitngo.data.model.division.DivisionData
import com.bsel.remitngo.data.model.division.DivisionItem
import com.bsel.remitngo.databinding.DivisionNameLayoutBinding
import com.bsel.remitngo.interfaceses.OnBankSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.bank.BankViewModel
import com.bsel.remitngo.presentation.ui.bank.BankViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class DivisionBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var bankViewModelFactory: BankViewModelFactory
    private lateinit var bankViewModel: BankViewModel

    var itemSelectedListener: OnBankSelectedListener? = null

    private lateinit var divisionNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: DivisionNameLayoutBinding

    private lateinit var divisionNameAdapter: DivisionNameAdapter

    private lateinit var deviceId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.division_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        divisionNameBehavior = BottomSheetBehavior.from(view.parent as View)
        divisionNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        divisionNameBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createBankSubComponent().inject(this)

        bankViewModel =
            ViewModelProvider(this, bankViewModelFactory)[BankViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }

        observeDivisionResult()

        deviceId = getDeviceId(requireContext())

        val divisionItem = DivisionItem(
            deviceId = deviceId,
            dropdownId = 2,
            param1 = 1,
            param2 = 0
        )
        bankViewModel.division(divisionItem)

        return bottomSheet
    }

    private fun observeDivisionResult() {
        bankViewModel.divisionResult.observe(this) { result ->
            if (result != null) {
                binding.divisionRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                divisionNameAdapter = DivisionNameAdapter(
                    selectedItem = { selectedItem: DivisionData ->
                        divisionItem(selectedItem)
                        binding.divisionSearch.setQuery("", false)
                    }
                )
                binding.divisionRecyclerView.adapter = divisionNameAdapter
                divisionNameAdapter.setList(result.data as List<DivisionData>)
                divisionNameAdapter.notifyDataSetChanged()

                binding.divisionSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        divisionNameAdapter.filter(newText.orEmpty())
                        return true
                    }
                })

            } else {
                Log.i("info", "division failed")
            }
        }
    }

    private fun divisionItem(selectedItem: DivisionData) {
        itemSelectedListener?.onDivisionItemSelected(selectedItem)
        dismiss()
    }

    private fun getDeviceId(context: Context): String {
        val deviceId: String

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } else {
            @Suppress("DEPRECATION")
            deviceId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

        return deviceId
    }

    override fun onStart() {
        super.onStart()
        divisionNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}