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
import com.bsel.remitngo.adapter.SourceOfIncomeAdapter
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeData
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.databinding.SourceOfIncomeLayoutBinding
import com.bsel.remitngo.interfaceses.OnPersonalInfoItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class SourceOfIncomeBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    var itemSelectedListener: OnPersonalInfoItemSelectedListener? = null

    private lateinit var sourceOfIncomeBehavior: BottomSheetBehavior<*>

    private lateinit var binding: SourceOfIncomeLayoutBinding

    private lateinit var sourceOfIncomeAdapter: SourceOfIncomeAdapter

    private lateinit var deviceId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.source_of_income_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        sourceOfIncomeBehavior = BottomSheetBehavior.from(view.parent as View)
        sourceOfIncomeBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        sourceOfIncomeBehavior.addBottomSheetCallback(object :
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
        val sourceOfIncomeItem = SourceOfIncomeItem(
            deviceId = deviceId
        )
        profileViewModel.sourceOfIncome(sourceOfIncomeItem)
        observeSourceOfIncomeResult()

        return bottomSheet
    }

    private fun observeSourceOfIncomeResult() {
        profileViewModel.sourceOfIncomeResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.sourceOfIncomeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                sourceOfIncomeAdapter = SourceOfIncomeAdapter(
                    selectedItem = { selectedItem: SourceOfIncomeData ->
                        sourceOfIncome(selectedItem)
                        binding.sourceOfIncomeSearch.setQuery("", false)
                    }
                )
                binding.sourceOfIncomeRecyclerView.adapter = sourceOfIncomeAdapter
                sourceOfIncomeAdapter.setList(result.data as List<SourceOfIncomeData>)
                sourceOfIncomeAdapter.notifyDataSetChanged()

                binding.sourceOfIncomeSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        sourceOfIncomeAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
            } else {
                Log.i("info", "sourceOfIncome failed")
            }
        }
    }

    private fun sourceOfIncome(selectedItem: SourceOfIncomeData) {
        itemSelectedListener?.onSourceOfIncomeItemSelected(selectedItem)
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
        sourceOfIncomeBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}