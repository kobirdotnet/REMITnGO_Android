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
import com.bsel.remitngo.adapter.AddressAdapter
import com.bsel.remitngo.data.model.profile.postCode.PostCodeData
import com.bsel.remitngo.data.model.profile.postCode.PostCodeItem
import com.bsel.remitngo.databinding.AddressLayoutBinding
import com.bsel.remitngo.data.interfaceses.OnAddressItemSelectedListener
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModel
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class AddressBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    var itemSelectedListener: OnAddressItemSelectedListener? = null

    private lateinit var addressNameBehavior: BottomSheetBehavior<*>

    private lateinit var binding: AddressLayoutBinding

    private lateinit var addressAdapter: AddressAdapter

    private lateinit var deviceId: String

    private var selectedPostCode: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.address_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        addressNameBehavior = BottomSheetBehavior.from(view.parent as View)
        addressNameBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        addressNameBehavior.addBottomSheetCallback(object :
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
        val postCodeItem = PostCodeItem(
            deviceId = deviceId,
            postCode = selectedPostCode
        )
        profileViewModel.postCode(postCodeItem)
        observePostCodeResult()

        return bottomSheet
    }

    fun setSelectedPostCode(postcode: String) {
        selectedPostCode = postcode
    }

    private fun observePostCodeResult() {
        profileViewModel.postCodeResult.observe(this) { result ->
            try {
                if (result!!.data != null) {
                    binding.addressRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                    addressAdapter = AddressAdapter(
                        selectedItem = { selectedItem: PostCodeData ->
                            address(selectedItem)
                            binding.addressSearch.setQuery("", false)
                        }
                    )
                    binding.addressRecyclerView.adapter = addressAdapter
                    addressAdapter.setList(result.data as List<PostCodeData>)
                    addressAdapter.notifyDataSetChanged()

                    binding.addressSearch.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            addressAdapter.addressFilter(newText.orEmpty())
                            return true
                        }
                    })
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun address(selectedItem: PostCodeData) {
        itemSelectedListener?.onAddressItemSelected(selectedItem)
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
        addressNameBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}