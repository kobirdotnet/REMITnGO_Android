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
import com.bsel.remitngo.adapter.CityAdapter
import com.bsel.remitngo.data.model.profile.city.CityData
import com.bsel.remitngo.data.model.profile.city.CityItem
import com.bsel.remitngo.databinding.CityLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnAddressItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class CityBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    var itemSelectedListener: OnAddressItemSelectedListener? = null

    private lateinit var cityNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: CityLayoutBinding

    private lateinit var cityAdapter: CityAdapter

    private lateinit var deviceId: String

    private var selectedCity: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.city_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        cityNameBehavior = BottomSheetBehavior.from(view.parent as View)
        cityNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        cityNameBehavior.addBottomSheetCallback(object :
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
        val cityItem = CityItem(
            deviceId = deviceId,
            dropdownId=4,
            param1 = selectedCity!!.toInt(),
            param2 = 0
        )
        profileViewModel.city(cityItem)
        observeCityResult()

        return bottomSheet
    }

    fun setSelectedCity(city: String) {
        selectedCity = city
    }

    private fun observeCityResult() {
        profileViewModel.cityResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.cityRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                cityAdapter = CityAdapter(
                    selectedItem = { selectedItem: CityData ->
                        city(selectedItem)
                        binding.citySearch.setQuery("", false)
                    }
                )
                binding.cityRecyclerView.adapter = cityAdapter
                cityAdapter.setList(result.data as List<CityData>)
                cityAdapter.notifyDataSetChanged()

                binding.citySearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        cityAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
            } else {
                Log.i("info", "city failed")
            }
        }
    }

    private fun city(selectedItem: CityData) {
        itemSelectedListener?.onCityItemSelected(selectedItem)
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
        cityNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}