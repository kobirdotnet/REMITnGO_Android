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
import com.bsel.remitngo.adapter.RelationNameAdapter
import com.bsel.remitngo.data.model.relation.RelationData
import com.bsel.remitngo.data.model.relation.RelationItem
import com.bsel.remitngo.databinding.RelationNameLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnBeneficiarySelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModel
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class RelationBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var beneficiaryViewModelFactory: BeneficiaryViewModelFactory
    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    var itemSelectedListener: OnBeneficiarySelectedListener? = null

    private lateinit var relationNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: RelationNameLayoutBinding

    private lateinit var relationNameAdapter: RelationNameAdapter

    private lateinit var deviceId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.relation_name_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        relationNameBehavior = BottomSheetBehavior.from(view.parent as View)
        relationNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        relationNameBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createBeneficiarySubComponent().inject(this)

        beneficiaryViewModel =
            ViewModelProvider(this, beneficiaryViewModelFactory)[BeneficiaryViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }

        observeRelationResult()

        deviceId = getDeviceId(requireContext())

        val relationItem = RelationItem(
            deviceId = deviceId,
            dropdownId = 14,
            param1 = 8,
            param2 = 0
        )
        beneficiaryViewModel.relation(relationItem)

        return bottomSheet
    }

    private fun observeRelationResult() {
        beneficiaryViewModel.relationResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.relationRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                relationNameAdapter = RelationNameAdapter(
                    selectedItem = { selectedItem: RelationData ->
                        relationItem(selectedItem)
                        binding.relationSearch.setQuery("", false)
                    }
                )
                binding.relationRecyclerView.adapter = relationNameAdapter
                relationNameAdapter.setList(result.data as List<RelationData>)
                relationNameAdapter.notifyDataSetChanged()

                binding.relationSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        relationNameAdapter.filter(newText.orEmpty())
                        return true
                    }
                })

            } else {
                Log.i("info", "relation failed")
            }
        }
    }

    private fun relationItem(selectedItem: RelationData) {
        itemSelectedListener?.onRelationItemSelected(selectedItem)
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
        relationNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}