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
import com.bsel.remitngo.adapter.QueryTypeAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.query.query_type.QueryTypeData
import com.bsel.remitngo.data.model.query.query_type.QueryTypeItem
import com.bsel.remitngo.databinding.QueryTypeLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnQueryTypeItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.query.QueryViewModel
import com.bsel.remitngo.presentation.ui.query.QueryViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class QueryTypeBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var queryViewModelFactory: QueryViewModelFactory
    private lateinit var queryViewModel: QueryViewModel

    var itemSelectedListener: OnQueryTypeItemSelectedListener? = null

    private lateinit var queryTypeBehavior: BottomSheetBehavior<*>

    private lateinit var binding: QueryTypeLayoutBinding

    private lateinit var queryTypeAdapter: QueryTypeAdapter

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.query_type_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        queryTypeBehavior = BottomSheetBehavior.from(view.parent as View)
        queryTypeBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        queryTypeBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createQuerySubComponent().inject(this)

        queryViewModel =
            ViewModelProvider(this, queryViewModelFactory)[QueryViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

        binding.cancelButton.setOnClickListener { dismiss() }

        val queryTypeItem = QueryTypeItem(
            deviceId = deviceId,
            dropdownId = 305,
            param1 = 0,
            param2 = 0
        )
        queryViewModel.queryType(queryTypeItem)
        observeQueryTypeResult()

        return bottomSheet
    }

    private fun observeQueryTypeResult() {
        queryViewModel.queryTypeResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    binding.queryTypeRecyclerView.layoutManager =
                        LinearLayoutManager(requireActivity())
                    queryTypeAdapter = QueryTypeAdapter(
                        selectedItem = { selectedItem: QueryTypeData ->
                            queryType(selectedItem)
                            binding.queryTypeSearch.setQuery("", false)
                        }
                    )
                    binding.queryTypeRecyclerView.adapter = queryTypeAdapter
                    queryTypeAdapter.setList(result.data as List<QueryTypeData>)
                    queryTypeAdapter.notifyDataSetChanged()

                    binding.queryTypeSearch.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            queryTypeAdapter.filter(newText.orEmpty())
                            return true
                        }
                    })
                    Log.i("info", " queryType successful: $result")
                }
            } catch (e: NullPointerException) {
                e.localizedMessage
            }
        }
    }

    private fun queryType(selectedItem: QueryTypeData) {
        itemSelectedListener?.onQueryTypeItemSelected(selectedItem)
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
        queryTypeBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}