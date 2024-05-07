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
import com.bsel.remitngo.adapter.DistrictNameAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.district.DistrictData
import com.bsel.remitngo.data.model.district.DistrictItem
import com.bsel.remitngo.databinding.DistrictNameLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnBankSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.bank.BankViewModel
import com.bsel.remitngo.presentation.ui.bank.BankViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class DistrictBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var bankViewModelFactory: BankViewModelFactory
    private lateinit var bankViewModel: BankViewModel

    var itemSelectedListener: OnBankSelectedListener? = null

    private lateinit var districtNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: DistrictNameLayoutBinding

    private lateinit var districtNameAdapter: DistrictNameAdapter

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String

    private var selectedDivision: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.district_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        districtNameBehavior = BottomSheetBehavior.from(view.parent as View)
        districtNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        districtNameBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createBankSubComponent().inject(this)

        bankViewModel =
            ViewModelProvider(this, bankViewModelFactory)[BankViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }

        observeDistrictResult()

        deviceId = getDeviceId(requireContext())

        val districtItem = DistrictItem(
            deviceId = deviceId,
            dropdownId = 3,
            param1 = selectedDivision!!.toInt(),
            param2 = 0
        )
        bankViewModel.district(districtItem)

        return bottomSheet
    }

    fun setSelectedDivision(divisionId: String) {
        selectedDivision = divisionId
    }

    private fun observeDistrictResult() {
        bankViewModel.districtResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    binding.districtRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                    districtNameAdapter = DistrictNameAdapter(
                        selectedItem = { selectedItem: DistrictData ->
                            districtItem(selectedItem)
                            binding.districtSearch.setQuery("", false)
                        }
                    )
                    binding.districtRecyclerView.adapter = districtNameAdapter
                    districtNameAdapter.setList(result.data as List<DistrictData>)
                    districtNameAdapter.notifyDataSetChanged()

                    binding.districtSearch.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            districtNameAdapter.filter(newText.orEmpty())
                            return true
                        }
                    })

                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun districtItem(selectedItem: DistrictData) {
//        itemSelectedListener?.onDistrictItemSelected(selectedItem)
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
        districtNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}