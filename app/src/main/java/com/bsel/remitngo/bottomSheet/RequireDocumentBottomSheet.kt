package com.bsel.remitngo.bottomSheet

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsel.remitngo.R
import com.bsel.remitngo.adapter.RequireDocumentAdapter
import com.bsel.remitngo.data.interfaceses.OnRequireDocumentListener
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentData
import com.bsel.remitngo.data.model.document.docForTransaction.RequireDocumentItem
import com.bsel.remitngo.databinding.RequireDocumentLayoutBinding
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.ui.payment.PaymentViewModel
import com.bsel.remitngo.presentation.ui.payment.PaymentViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class RequireDocumentBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var paymentViewModelFactory: PaymentViewModelFactory
    private lateinit var paymentViewModel: PaymentViewModel

    var itemSelectedListener: OnRequireDocumentListener? = null

    private lateinit var binding: RequireDocumentLayoutBinding

    private lateinit var requireDocumentBehavior: BottomSheetBehavior<*>

    private lateinit var requireDocumentAdapter: RequireDocumentAdapter

    private val uploadRequireDocumentBottomSheet: UploadRequireDocumentBottomSheet by lazy { UploadRequireDocumentBottomSheet() }

    private var totalAmount: Double = 0.0
    private var benePersonId: Int = 0
    private var customerId: Int = 0
    private var currentDate: String? = null
    private var purposeOfTransferId: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.require_document_layout, null)
        binding = DataBindingUtil.bind(view)!!

        bottomSheet.setContentView(view)
        requireDocumentBehavior = BottomSheetBehavior.from(view.parent as View)
        requireDocumentBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels)

        requireDocumentBehavior.addBottomSheetCallback(object :
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

        (requireActivity().application as Injector).createPaymentSubComponent().inject(this)

        paymentViewModel =
            ViewModelProvider(this, paymentViewModelFactory)[PaymentViewModel::class.java]

        binding.cancelButton.setOnClickListener { dismiss() }
        binding.btnLater.setOnClickListener { paymentLater("paymentLater") }

        binding.btnUpload.setOnClickListener {
            if (!uploadRequireDocumentBottomSheet.isAdded) {
                uploadRequireDocumentBottomSheet.requireDocument(
                    totalAmount,
                    benePersonId,
                    customerId,
                    currentDate!!,
                    purposeOfTransferId
                )
                uploadRequireDocumentBottomSheet.show(
                    childFragmentManager,
                    uploadRequireDocumentBottomSheet.tag
                )
            }
        }

        paymentViewModel.requireDocMsg("3")

        val requireDocumentItem = RequireDocumentItem(
            agentId = 8082,
            amount = totalAmount,
            beneficiaryId = benePersonId,
            customerId = customerId,
            entryDate = currentDate,
            purposeOfTransferId = purposeOfTransferId,
            transactionType = 1
        )
        paymentViewModel.requireDocument(requireDocumentItem)

        observeRequireDocMsgResult()
        observeRequireDocumentResult()

        return bottomSheet
    }

    private fun paymentLater(paymentLater: String) {
        itemSelectedListener?.onRequireDocumentSelected(paymentLater)
        dismiss()
    }

    fun requireDocument(
        totalAmount: Double,
        benePersonId: Int,
        customerId: Int,
        currentDate: String,
        purposeOfTransferId: Int
    ) {
        this.totalAmount = totalAmount
        this.benePersonId = benePersonId
        this.customerId = customerId
        this.currentDate = currentDate
        this.purposeOfTransferId = purposeOfTransferId
    }

    private fun observeRequireDocMsgResult() {
        paymentViewModel.requireDocMsgResult.observe(this) { result ->
            try {
                if (result!!.code == "000") {
                    if (result.data != null) {
                        for (msg in result.data) {
                            if (msg!!.id == 1) {
                                binding.msgOne.text = msg.name
                            }
                            if (msg.id == 2) {
                                binding.msgTwo.text = msg.name
                            }
                        }
                    }
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    private fun observeRequireDocumentResult() {
        paymentViewModel.requireDocumentResult.observe(this) { result ->
            try {
                if (result!!.code == "000") {
                    if (result.data != null) {
                        binding.requireDocumentRecyclerView.layoutManager =
                            LinearLayoutManager(requireActivity())
                        requireDocumentAdapter = RequireDocumentAdapter()
                        binding.requireDocumentRecyclerView.adapter = requireDocumentAdapter
                        requireDocumentAdapter.setList(result.data as List<RequireDocumentData>)
                        requireDocumentAdapter.notifyDataSetChanged()
                    }
                }
            }catch (e:NullPointerException){
                e.localizedMessage
            }
        }
    }

    override fun onStart() {
        super.onStart()
        requireDocumentBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}