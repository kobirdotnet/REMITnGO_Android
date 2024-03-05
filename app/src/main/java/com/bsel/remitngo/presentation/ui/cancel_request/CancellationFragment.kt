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
import com.bsel.remitngo.adapter.CancellationAdapter
import com.bsel.remitngo.data.api.PreferenceManager
import com.bsel.remitngo.data.model.cancel_request.get_cancel_request.GetCancelRequestItem
import com.bsel.remitngo.data.model.cancel_request.get_cancel_request.GetCancelResponseData
import com.bsel.remitngo.databinding.FragmentCancellationBinding
import com.bsel.remitngo.presentation.di.Injector
import javax.inject.Inject

class CancellationFragment : Fragment() {
    @Inject
    lateinit var cancelRequestViewModelFactory: CancelRequestViewModelFactory
    private lateinit var cancelRequestViewModel: CancelRequestViewModel

    private lateinit var binding: FragmentCancellationBinding

    private lateinit var cancellationAdapter: CancellationAdapter

    private lateinit var preferenceManager: PreferenceManager

    private lateinit var deviceId: String
    private lateinit var personId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cancellation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCancellationBinding.bind(view)

        (requireActivity().application as Injector).createCancelRequestSubComponent().inject(this)

        cancelRequestViewModel =
            ViewModelProvider(this, cancelRequestViewModelFactory)[CancelRequestViewModel::class.java]

        preferenceManager = PreferenceManager(requireContext())
        personId = preferenceManager.loadData("personId").toString()
        deviceId = getDeviceId(requireContext())

        binding.btnNew.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_cancellation_to_nav_cancel_request
            )
        }

        val getCancelRequestItem = GetCancelRequestItem(
            deviceId = deviceId,
            params1 = personId.toInt(),
            params2 = 0
        )
        cancelRequestViewModel.getCancelRequest(getCancelRequestItem)
        observeGetCancelRequestResult()

    }

    private fun observeGetCancelRequestResult() {
        cancelRequestViewModel.getCancelRequestResult.observe(this) { result ->
            if (result!!.data != null) {
                binding.cancellationRecyclerView.layoutManager =
                    LinearLayoutManager(requireActivity())
                cancellationAdapter = CancellationAdapter(
                    selectedItem = { selectedItem: GetCancelResponseData ->
                        cancellation(selectedItem)
                    }
                )
                binding.cancellationRecyclerView.adapter = cancellationAdapter
                cancellationAdapter.setList(result.data as List<GetCancelResponseData>)
                cancellationAdapter.notifyDataSetChanged()
                Log.i("info", "get cancel Request successful: $result")
            } else {
                Log.i("info", "get cancel Request failed")
            }
        }
    }

    private fun cancellation(selectedItem: GetCancelResponseData) {
        Log.i("info", "selectedItem: $selectedItem")
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