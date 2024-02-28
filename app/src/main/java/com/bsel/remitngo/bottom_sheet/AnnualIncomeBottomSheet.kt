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
import com.bsel.remitngo.adapter.AnnualIncomeAdapter
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeData
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeItem
import com.bsel.remitngo.databinding.AnnualIncomeLayoutBinding
import com.bsel.remitngo.interfaceses.OnPersonalInfoItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class AnnualIncomeBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    var itemSelectedListener: OnPersonalInfoItemSelectedListener? = null

    private lateinit var annualIncomeBehavior: BottomSheetBehavior<*>

    private lateinit var binding: AnnualIncomeLayoutBinding

    private lateinit var annualIncomeAdapter: AnnualIncomeAdapter

    private lateinit var deviceId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.annual_income_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        annualIncomeBehavior = BottomSheetBehavior.from(view.parent as View)
        annualIncomeBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        annualIncomeBehavior.addBottomSheetCallback(object :
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
        val annualIncomeItem = AnnualIncomeItem(
            deviceId = deviceId
        )
        profileViewModel.annualIncome(annualIncomeItem)
        observeAnnualIncomeResult()

        return bottomSheet
    }

    private fun observeAnnualIncomeResult() {
        profileViewModel.annualIncomeResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.annualIncomeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                annualIncomeAdapter = AnnualIncomeAdapter(
                    selectedItem = { selectedItem: AnnualIncomeData ->
                        annualIncome(selectedItem)
                        binding.annualIncomeSearch.setQuery("", false)
                    }
                )
                binding.annualIncomeRecyclerView.adapter = annualIncomeAdapter
                annualIncomeAdapter.setList(result.data as List<AnnualIncomeData>)
                annualIncomeAdapter.notifyDataSetChanged()

                binding.annualIncomeSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        annualIncomeAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
            } else {
                Log.i("info", "annualIncome failed")
            }
        }
    }

    private fun annualIncome(selectedItem: AnnualIncomeData) {
        itemSelectedListener?.onAnnualIncomeItemSelected(selectedItem)
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
        annualIncomeBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}