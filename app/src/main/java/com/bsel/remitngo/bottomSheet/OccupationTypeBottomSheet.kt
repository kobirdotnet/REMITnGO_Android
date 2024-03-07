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
import com.bsel.remitngo.adapter.OccupationTypeAdapter
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeData
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeItem
import com.bsel.remitngo.databinding.OccupationTypeLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnPersonalInfoItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import javax.inject.Inject

class OccupationTypeBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    var itemSelectedListener: OnPersonalInfoItemSelectedListener? = null

    private lateinit var occupationTypeBehavior: BottomSheetBehavior<*>

    private lateinit var binding: OccupationTypeLayoutBinding

    private lateinit var occupationTypeAdapter: OccupationTypeAdapter

    private lateinit var deviceId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.occupation_type_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        occupationTypeBehavior = BottomSheetBehavior.from(view.parent as View)
        occupationTypeBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        occupationTypeBehavior.addBottomSheetCallback(object :
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
        val occupationTypeItem = OccupationTypeItem(
            deviceId = deviceId,
            dropdownId = 106,
            param1 = 106,
            param2 = 0
        )
        profileViewModel.occupationType(occupationTypeItem)
        observeOccupationTypeResult()

        return bottomSheet
    }

    private fun observeOccupationTypeResult() {
        profileViewModel.occupationTypeResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.occupationTypeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                occupationTypeAdapter = OccupationTypeAdapter(
                    selectedItem = { selectedItem: OccupationTypeData ->
                        occupationType(selectedItem)
                        binding.occupationTypeSearch.setQuery("", false)
                    }
                )
                binding.occupationTypeRecyclerView.adapter = occupationTypeAdapter
                occupationTypeAdapter.setList(result.data as List<OccupationTypeData>)
                occupationTypeAdapter.notifyDataSetChanged()

                binding.occupationTypeSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        occupationTypeAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
            } else {
                Log.i("info", "occupationType failed")
            }
        }
    }

    private fun occupationType(selectedItem: OccupationTypeData) {
        itemSelectedListener?.onOccupationTypeItemSelected(selectedItem)
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
        occupationTypeBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}