package com.bsel.remitngo.bottomSheet

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
import com.bsel.remitngo.adapter.BranchNameAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.branch.BranchData
import com.bsel.remitngo.data.model.branch.BranchItem
import com.bsel.remitngo.databinding.BranchNameLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnBankSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.bank.BankViewModel
import com.bsel.remitngo.presentation.ui.bank.BankViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class BranchBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var bankViewModelFactory: BankViewModelFactory
    private lateinit var bankViewModel: BankViewModel

    var itemSelectedListener: OnBankSelectedListener? = null

    private lateinit var bankBranchNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: BranchNameLayoutBinding

    private lateinit var branchNameAdapter: BranchNameAdapter

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private var bankId: Int = 0
    private var countryId: Int = 0
    private var divisionId: Int = 0

    private var selectedDistrict: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.branch_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        bankBranchNameBehavior = BottomSheetBehavior.from(view.parent as View)
        bankBranchNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        bankBranchNameBehavior.addBottomSheetCallback(object :
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

        preferenceManager = PreferenceManager(requireContext())
        bankId = preferenceManager.loadData("bankId")!!.toInt()
        divisionId = preferenceManager.loadData("divisionId")!!.toInt()

        (requireActivity().application as Injector).createBankSubComponent().inject(this)

        bankViewModel =
            ViewModelProvider(this, bankViewModelFactory)[BankViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }

        observeBranchResult()

        deviceId = getDeviceId(requireContext())
        countryId = 1
        val branchItem = BranchItem(
            deviceId = deviceId,
            bankId = bankId,
            toCountryId = countryId,
            divisionId = divisionId,
            districtId = selectedDistrict!!.toInt()
        )
        bankViewModel.branch(branchItem)

        return bottomSheet
    }

    fun setSelectedDistrict(districtId: String) {
        selectedDistrict = districtId
    }

    private fun observeBranchResult() {
        bankViewModel.branchResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.branchRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                branchNameAdapter = BranchNameAdapter(
                    selectedItem = { selectedItem: BranchData ->
                        branchItem(selectedItem)
                        binding.branchSearch.setQuery("", false)
                    }
                )
                binding.branchRecyclerView.adapter = branchNameAdapter
                branchNameAdapter.setList(result.data as List<BranchData>)
                branchNameAdapter.notifyDataSetChanged()

                binding.branchSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        branchNameAdapter.filter(newText.orEmpty())
                        return true
                    }
                })

            }
        }
    }

    private fun branchItem(selectedItem: BranchData) {
        itemSelectedListener?.onBranchItemSelected(selectedItem)
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
        bankBranchNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}