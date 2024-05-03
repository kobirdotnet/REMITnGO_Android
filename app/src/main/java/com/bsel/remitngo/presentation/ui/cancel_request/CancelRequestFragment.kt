package com.bsel.remitngo.presentation.ui.cancel_request

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.CancelRequestAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelData
import com.bsel.remitngo.data.model.cancel_request.populate_cancel_request.PopulateCancelItem
import com.bsel.remitngo.databinding.FragmentCancelRequestBinding
import com.bsel.remitngo.presentation.di.Injector
import javax.inject.Inject

class CancelRequestFragment : Fragment() {
    @Inject
    lateinit var cancelRequestViewModelFactory: CancelRequestViewModelFactory
    private lateinit var cancelRequestViewModel: CancelRequestViewModel

    private lateinit var binding: FragmentCancelRequestBinding

    private lateinit var cancelRequestAdapter: CancelRequestAdapter

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cancel_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCancelRequestBinding.bind(view)

        (requireActivity().application as Injector).createCancelRequestSubComponent().inject(this)

        cancelRequestViewModel =
            ViewModelProvider(
                this,
                cancelRequestViewModelFactory
            )[CancelRequestViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

        val populateCancelItem = PopulateCancelItem(
            deviceId = deviceId,
            personId = personId.toInt()
        )
        cancelRequestViewModel.populateCancel(populateCancelItem)
        observePopulateCancelRequestResult()

    }

    private fun observePopulateCancelRequestResult() {
        cancelRequestViewModel.populateCancelResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    binding.cancelRequestRecyclerView.layoutManager =
                        LinearLayoutManager(requireActivity())
                    cancelRequestAdapter = CancelRequestAdapter(
                        selectedItem = { selectedItem: PopulateCancelData ->
                            populateCancel(selectedItem)
                        }
                    )
                    binding.cancelRequestRecyclerView.adapter = cancelRequestAdapter
                    cancelRequestAdapter.setList(result.data as List<PopulateCancelData>)
                    cancelRequestAdapter.notifyDataSetChanged()
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun populateCancel(selectedItem: PopulateCancelData) {
        val bundle = Bundle().apply {
            putString("transactionCode", selectedItem.transactionCode.toString())
            putString("transactionDate", selectedItem.transactionDateTime12hr.toString())
            putString("orderType", selectedItem.orderTypeName.toString())
            putString("beneficiaryName", selectedItem.beneficiaryName.toString())
            putString("sendAmount", selectedItem.beneAmount.toString())
        }
        findNavController().navigate(
            R.id.action_nav_cancel_request_to_nav_generate_cancel_request,
            bundle
        )
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


}