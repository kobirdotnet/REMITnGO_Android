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
import com.bsel.remitngo.adapter.UkDivisionAdapter
import com.bsel.remitngo.data.model.profile.uk_division.UkDivisionData
import com.bsel.remitngo.data.model.profile.uk_division.UkDivisionItem
import com.bsel.remitngo.databinding.UkDivisionLayoutBinding
import com.bsel.remitngo.interfaceses.OnAddressItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class UkDivisionBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    var itemSelectedListener: OnAddressItemSelectedListener? = null

    private lateinit var ukDivisionNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: UkDivisionLayoutBinding

    private lateinit var ukDivisionAdapter: UkDivisionAdapter

    private lateinit var deviceId: String

    private var selectedUkDivision: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.uk_division_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        ukDivisionNameBehavior = BottomSheetBehavior.from(view.parent as View)
        ukDivisionNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        ukDivisionNameBehavior.addBottomSheetCallback(object :
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
        val ukDivisionItem = UkDivisionItem(
            deviceId = deviceId,
            dropdownId=2,
            param1 = selectedUkDivision!!.toInt(),
            param2 = 0
        )
        profileViewModel.ukDivision(ukDivisionItem)
        observeUkDivisionResult()

        return bottomSheet
    }

    fun setSelectedUkDivision(ukDivision: String) {
        selectedUkDivision = ukDivision
    }

    private fun observeUkDivisionResult() {
        profileViewModel.ukDivisionResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.ukDivisionRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                ukDivisionAdapter = UkDivisionAdapter(
                    selectedItem = { selectedItem: UkDivisionData ->
                        ukDivision(selectedItem)
                        binding.ukDivisionSearch.setQuery("", false)
                    }
                )
                binding.ukDivisionRecyclerView.adapter = ukDivisionAdapter
                ukDivisionAdapter.setList(result.data as List<UkDivisionData>)
                ukDivisionAdapter.notifyDataSetChanged()

                binding.ukDivisionSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        ukDivisionAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
            } else {
                Log.i("info", "division failed")
            }
        }
    }

    private fun ukDivision(selectedItem: UkDivisionData) {
        itemSelectedListener?.onUkDivisionItemSelected(selectedItem)
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
        ukDivisionNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}