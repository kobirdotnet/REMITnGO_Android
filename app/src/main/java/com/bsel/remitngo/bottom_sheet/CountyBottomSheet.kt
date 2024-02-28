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
import com.bsel.remitngo.adapter.CountyAdapter
import com.bsel.remitngo.data.model.profile.county.CountyData
import com.bsel.remitngo.data.model.profile.county.CountyItem
import com.bsel.remitngo.databinding.CountyLayoutBinding
import com.bsel.remitngo.interfaceses.OnAddressItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class CountyBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    var itemSelectedListener: OnAddressItemSelectedListener? = null

    private lateinit var countyNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: CountyLayoutBinding

    private lateinit var countyAdapter: CountyAdapter

    private lateinit var deviceId: String

    private var selectedCounty: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.county_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        countyNameBehavior = BottomSheetBehavior.from(view.parent as View)
        countyNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        countyNameBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createProfileSubComponent().inject(this)

        profileViewModel =
            ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }

        deviceId = getDeviceId(requireContext())
        val countyItem = CountyItem(
            deviceId = deviceId,
            dropdownId=3,
            param1 = selectedCounty!!.toInt(),
            param2 = 0
        )
        profileViewModel.county(countyItem)
        observeCountyResult()

        return bottomSheet
    }

    fun setSelectedCounty(county: String) {
        selectedCounty = county
    }

    private fun observeCountyResult() {
        profileViewModel.countyResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.countyRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                countyAdapter = CountyAdapter(
                    selectedItem = { selectedItem: CountyData ->
                        county(selectedItem)
                        binding.countySearch.setQuery("", false)
                    }
                )
                binding.countyRecyclerView.adapter = countyAdapter
                countyAdapter.setList(result.data as List<CountyData>)
                countyAdapter.notifyDataSetChanged()

                binding.countySearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        countyAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
            } else {
                Log.i("info", "county failed")
            }
        }
    }

    private fun county(selectedItem: CountyData) {
        itemSelectedListener?.onCountyItemSelected(selectedItem)
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
        countyNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}