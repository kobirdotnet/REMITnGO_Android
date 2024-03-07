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
import com.bsel.remitngo.adapter.OccupationAdapter
import com.bsel.remitngo.data.model.profile.occupation.OccupationData
import com.bsel.remitngo.data.model.profile.occupation.OccupationItem
import com.bsel.remitngo.databinding.OccupationLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnPersonalInfoItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class OccupationBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    var itemSelectedListener: OnPersonalInfoItemSelectedListener? = null

    private lateinit var occupationBehavior: BottomSheetBehavior<*>

    private lateinit var binding: OccupationLayoutBinding

    private lateinit var occupationAdapter: OccupationAdapter

    private lateinit var deviceId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.occupation_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        occupationBehavior = BottomSheetBehavior.from(view.parent as View)
        occupationBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        occupationBehavior.addBottomSheetCallback(object :
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
        val occupationItem = OccupationItem(
            deviceId = deviceId,
            dropdownId = 112,
            param1 = 0,
            param2 = 0
        )
        profileViewModel.occupation(occupationItem)
        observeOccupationResult()

        return bottomSheet
    }

    private fun observeOccupationResult() {
        profileViewModel.occupationResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.occupationRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                occupationAdapter = OccupationAdapter(
                    selectedItem = { selectedItem: OccupationData ->
                        occupation(selectedItem)
                        binding.occupationSearch.setQuery("", false)
                    }
                )
                binding.occupationRecyclerView.adapter = occupationAdapter
                occupationAdapter.setList(result.data as List<OccupationData>)
                occupationAdapter.notifyDataSetChanged()

                binding.occupationSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        occupationAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
            } else {
                Log.i("info", "occupation failed")
            }
        }
    }

    private fun occupation(selectedItem: OccupationData) {
        itemSelectedListener?.onOccupationItemSelected(selectedItem)
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
        occupationBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}